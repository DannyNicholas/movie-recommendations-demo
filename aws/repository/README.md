# Pushing the Service to the Amazon Elastic Container Registry

Before we can spin-up any container instances, we must provide AWS with a Docker image of the service.

The image will need to be pushed to an existing Elastic Container Registry repository specifically created for this
service.

## Creating an Elastic Container Registry (ECR) Repository

To create a repository to hold your Docker image, you must first create a repository within ECR.

- Log-in to AWS console.
- Switch to the region where you want to host/run this service (e.g. "eu-west-1").
- Navigate to Amazon ECR (part of Amazon Container Services).
- Create a repository.
- Make a note of the region, repository name and URI.

For more information, read Amazon's guide to
the [Elastic Container Registry](https://docs.aws.amazon.com/AmazonECR/latest/userguide/what-is-ecr.html).

## Creating and Pushing a Docker Image to ECR

A bash script called "pushImageToAwsEcr.sh" has been provided in this directory to:

- Build the service
- Create a Docker image of the service
- Push the image to your chosen ECR repository
- Delete any old images from the repository

Before running this script, edit the script's variables below to reference your created ECR repository:

- ECR_REGION (e.g. eu-west-1)
- ECR_REPO (e.g. movie-recommendations-api)
- ECR_HOST (e.g. 640802917860.dkr.ecr.eu-west-2.amazonaws.com)

**IMPORTANT:** It is assumed you have the AWS CLI available and AWS credentials are correctly configured using
`aws configure`. For more information of configuring your AWS CLI, read Amazon's guide to
the [Configuring the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html).

Your user will also need permissions to push to ECR (e.g. IAM permission `AmazonEC2ContainerRegistryFullAccess`).
