# Deploying to Amazon Web Services (AWS)

The following sections explain how to deploy the Movie Recommendation service to Amazon Web Service (AWS).

Specifically, deploying to a cluster running multiple containers behind an application load balancer. Each container will run a single instance of the service. The application load balancer routes API requests to different containers in a "round-robin" fashion. 

The AWS deployment process is broken down in two steps:

## Image Repository

Before we can spin-up any container instances, we must provide AWS with a Docker image of the service.

The [Image Repository section](./repository) explains the process of how to create a Docker image of the service and push this to the AWS Elastic Container Registry (ECR) using the provided script.

## Terraform Deployment

Once a Docker image of the service has been pushed to AWS, it can be deployed as part of a cluster using the AWS Elastic Container Service (ECS).

The deployment process has been scripted for consistency and ease-of-use using Terraform.

The [Terraform Deployment section](./terraform) explains how to deploy and run the service on AWS using Terraform.