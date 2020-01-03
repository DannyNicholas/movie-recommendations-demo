# Deploying to Amazon Web Services (AWS)

The following sections explain how to deploy the Movie Recommendation service to Amazon Web Service (AWS).

Specifically, deploying to a cluster running multiple containers behind an application load balancer. Each container will run a single instance of the service. The application load balancer routes API requests to different containers in a "round-robin" fashion. 

**IMPORTANT:** It is assumed you have the AWS CLI available and AWS credentials are correctly configured using `aws configure`. For more information of configuring your AWS CLI, read Amazon's guide to the [Configuring the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html).

The AWS deployment process is broken down in two steps:

## Push Service Image to Amazon Elastic Container Registry

Before we can spin-up any container instances, we must provide the Amazon Elastic Container Registry (ECR) with a Docker image of the service.

The [Pushing the Service to the Amazon Elastic Container Registry](./repository) section explains the process of how to create a Docker image of the service and push this to the AWS Elastic Container Registry (ECR) using the provided script.

## Terraform Deployment

Once a Docker image of the service has been pushed to AWS, it can be deployed as part of a cluster using the AWS Elastic Container Service (ECS).

The deployment process has been scripted for consistency and ease-of-use using Terraform.

The [Deploying Service to AWS using Terraform](./terraform) section explains how to deploy and run the service on AWS using Terraform.