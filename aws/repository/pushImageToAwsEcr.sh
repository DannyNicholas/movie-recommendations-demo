#!/bin/bash
set -euo pipefail

# The values below are provided as an example only.
# You must update this for your own ECR repository.
ECR_REGION=eu-west-2
ECR_REPO=movie-recommendations-api
ECR_HOST=640802917860.dkr.ecr.eu-west-2.amazonaws.com

# Any subsequent(*) commands which fail will cause the shell script to exit immediately
set -e

# We must switch to directory containing maven POM and Docker file.
# It is possible this script is being run from another directory so relative paths will not work.
# So we must find the actual path to our script (not where it was run from) and then navigate up 2 directories.
dir_path=$(dirname ${BASH_SOURCE[0]})
cd $dir_path/../..
echo Running script from directory:
pwd

docker_file=./Dockerfile
if [ -f "$docker_file" ]; then
    echo "Docker file exists"
else
    echo "Docker file does not exist in current directory. Failed."
    exit
fi
pom_file=./pom.xml
if [ -f "$pom_file" ]; then
    echo "Maven POM file exists"
else
    echo "Maven POM file does not exist in current directory. Failed."
    exit
fi

echo "Building JAR..."
mvn clean package docker:build

echo "Login to AWS ECR..."
aws ecr get-login-password --region $ECR_REGION | docker login --username AWS --password-stdin $ECR_HOST

echo "Build Docker Image..."
docker build -t $ECR_REPO .

echo "Tag Docker Image..."
docker tag $ECR_REPO:latest $ECR_HOST/$ECR_REPO:latest

echo "Push Docker Image to ECR..."
docker push $ECR_HOST/$ECR_REPO:latest

# Delete all images except the latest one.
# This may be an index with associated referenced (child) images that must also be kept

KEEP_INDEX=$(aws ecr describe-images \
  --repository-name $ECR_REPO \
  --image-ids imageTag=latest \
  --query 'imageDetails[0].imageDigest' \
  --output text)
echo "Keeping manifest list digest: $KEEP_INDEX"

KEEP_CHILDREN=$(aws ecr batch-get-image \
  --repository-name $ECR_REPO \
  --image-ids imageDigest=$KEEP_INDEX \
  --query 'images[].imageManifest' \
  --output text | jq -r '.manifests[].digest')
echo "Keeping child digests:"
echo "$KEEP_CHILDREN"

# Collect all digests
ALL_DIGESTS=$(aws ecr list-images \
  --repository-name "$ECR_REPO" \
  --query 'imageIds[].imageDigest' \
  --output text)

# Build deletion list
DELETE_DIGESTS=()
for DIGEST in $ALL_DIGESTS; do
  if [ "$DIGEST" != "$KEEP_INDEX" ] && ! grep -q "$DIGEST" <<< "$KEEP_CHILDREN"; then
    DELETE_DIGESTS+=("{\"imageDigest\":\"$DIGEST\"}")
  fi
done

# Perform deletion of old images
if [ ${#DELETE_DIGESTS[@]} -gt 0 ]; then
  aws ecr batch-delete-image \
    --repository-name "$ECR_REPO" \
    --image-ids "$(printf '[%s]' "$(IFS=,; echo "${DELETE_DIGESTS[*]}")")"
  echo "Deleted all images except latest index + children digests."
else
  echo "No images to delete."
fi

echo "Docker ECR Images Updated."
