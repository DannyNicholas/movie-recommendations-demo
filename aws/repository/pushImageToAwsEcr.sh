#!/bin/bash

# The values below are provided as an example only.
# You must update this for your own ECR repository.
ECR_REGION=eu-west-1
ECR_REPO=dn_movie_recommendation_api
ECR_HOST=423299723934.dkr.ecr.eu-west-1.amazonaws.com

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
mvn clean package

echo "Login to AWS ECR..."
# stripping out https:// from login prevents a credentials manager bug with Docker for Windows
# see: https://docs.aws.amazon.com/AmazonECR/latest/userguide/common-errors-docker.html#error-403
eval $(aws ecr get-login --no-include-email --region $ECR_REGION | sed 's|https://||')

echo "Build Docker Image..."
docker build -t $ECR_REPO .

echo "Tag Docker Image..."
docker tag $ECR_REPO:latest $ECR_HOST/$ECR_REPO:latest

echo "Push Docker Image to ECR..."
docker push $ECR_HOST/$ECR_REPO:latest

echo "Delete Untagged Images"
IMAGES_TO_DELETE=$( aws ecr list-images --region $ECR_REGION --repository-name $ECR_REPO --filter "tagStatus=UNTAGGED" --query 'imageIds[*]' --output json )
EMPTY_RESPONSE=[]
if [ "$IMAGES_TO_DELETE" == "$EMPTY_RESPONSE" ]; then
    echo "Nothing to delete"
else
    echo "Deleting..."
    aws ecr batch-delete-image --region $ECR_REGION --repository-name $ECR_REPO --image-ids "$IMAGES_TO_DELETE"
fi

echo "Docker ECR Images Updated."
