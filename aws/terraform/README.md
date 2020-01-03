# Deploying Service to AWS using Terraform

Deploying a service to Amazon Web Services (AWS) can be a complex process. Terraform simplifies this process by scripting the deployment steps for improved consistency and ease-of-use. 

The directory (and sub-directories) hold the Terraform configuration needed to deploy our service to AWS.

For more information, read the [Terraform Getting Started Guide](https://learn.hashicorp.com/terraform/getting-started/install.html).

**IMPORTANT:** It is assumed you have the AWS CLI available and AWS credentials are correctly configured using `aws configure`. For more information of configuring your AWS CLI, read Amazon's guide to the [Configuring the AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html).

## Deployment Objectives

The deployment scripts aim to achieve the following.

- Create a VPN and associated networking.
- Create an Amazon Elastic Container Service cluster.
- Deploy multiple instances of our service within the cluster.
- Create an application load-balancer as the entry point to our service API. 

## Configuring Terraform Variables

Before running the script, ensure all the variables are correctly configured for your requirements.

The default variables are provided within `variables.tf` in this directory or within module directories. Most can be left as default but the below variables may need configuring:

`variables.tf`
- `region` (your AWS region)
- `project-name-value` (the project tag assigned to created AWS resources)

`modules/ecs/variables.tf`
- `instance_type` (type of EC2 instances created in cluster e.g. `t2.medium`)
- `instances_desired` (desired number of EC2 instances created in cluster)
- `autoscale_min` (minimum number of EC2 instances created in cluster)
- `autoscale_max` (maximum number of EC2 instances created in cluster)
- `ssh_key_name` (name of key if SSH is required to EC2 instances - also needs enabling in `ecs.tf` script)

`modules/services/variables.tf`
- `ecs_movie_recommendation_api_log_group_name` (name of CloudWatch log group to be created)
- `service_discovery_domain_name` (domain name used by service discovery)
- `tasks_desired` (desired number of service tasks i.e. service container instances)

**NOTE** need to consolidate `project-name-value`
**NOTE** need to provide variable for ECR repo name

## Running Terraform Deployment Script

Once configured the deployment script can be run.

```terraform init```

```terraform plan```

```terraform apply```

Returns load balancer DNS name. Use for testing.