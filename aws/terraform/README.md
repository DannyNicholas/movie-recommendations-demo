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
- Create a PostgreSQL database instance for data persistence.
- Create an application load-balancer as the entry point to our service API. 



## Configuring Terraform Variables

Before running the script, ensure all the variables are correctly configured for your requirements.

The default variables are provided within `variables.tf` in this directory or within module directories. Most can be left as default but the below variables may need configuring:

`variables.tf`
- `region` (your AWS region)
- `project-name-value` (the project tag assigned to created AWS resources)
- `movie-service-image-repository-name` (name of your ECR repository that holds the movie service Docker image)
- `ec2_instance_type` (type of EC2 instances created in cluster e.g. `t2.medium`)
- `ec2_instances_desired` (desired number of EC2 instances created in cluster - must fall within `autoscale_min` and `autoscale_max`)
- `service_tasks_desired` (desired number of service tasks to create across cluster i.e. service container instances)
- `database-identifier` - identity of PostgreSQL instance within AWS.
- `database-name` - name of database within PostgreSQL instance.

`modules/ecs/variables.tf`
- `autoscale_min` (minimum number of EC2 instances allowed in cluster)
- `autoscale_max` (maximum number of EC2 instances allowed in cluster)
- `ssh_key_name` (name of key if SSH is required to EC2 instances - also needs enabling in `ecs.tf` script)

`modules/services/variables.tf`
- `ecs_movie_recommendation_api_log_group_name` (name of CloudWatch log group to be created)
- `service_discovery_domain_name` (domain name used by service discovery)


## Running Terraform Deployment Script

Once the Terraform scripts and variables have been configured, they can be run using the [Terraform CLI](https://www.terraform.io/docs/commands/index.html).


### Initialising

Before running for the first time, Terraform must be initialised. Navigate to this `terraform` directory and run the command below.

```terraform init```


### Planned Dry-Run

Once successfully initialised, it is possible to perform a planned "dry-run" deployment. A dry-run displays the resources that would be created if the deployment was being run for real. This can confirm the scripts are correctly configured before deploying. This "dry-run" plan is triggered using the `plan` command below.

```terraform plan```

**NOTE** No real AWS resources are created using `plan`.


### Deployment

If the plan ran successfully, we are now ready to deploy for real using the `apply` command below.

```terraform apply```

**NOTE** `apply` __will__ create real AWS resources and you will start being charged by AWS for any resources created.

After a successful deployment, the script will output the DNS name of the created load balancer that is the entry point of our application. For example:

```
Outputs:
application_load_balancer_dns_name = movie-api-server-alb-default-123456789.eu-west-1.elb.amazonaws.com
```


### Destroy

It is important to destroy the AWS resources when no longer needed to avoid being charged by AWS for unused resources.

All resources created during deployment can be destroyed using:
```terraform destroy```



## Testing the Application

When deployment has been completed and the application is running, it can be tested using the DNS name returned after `terraform apply`.


### Testing Service is Up

First, we can test the service is up by calling the Spring Actuator health end-point at: `<Load Balancer DNS name>/actuator/health`

For example:
```curl http://movie-api-server-alb-default-123456789.eu-west-1.elb.amazonaws.com/actuator/health```

This should return the JSON:

```
{
    "status": "UP"
}
```

If this returns: `503 Service Temporarily Unavailable` it is possible the service has started but is still initialising. Wait for a minute or two and try again.


### Testing the API

Once the service is up, we can attempt to make a real API call. For example: 
```curl http://movie-api-server-alb-default-123456789.eu-west-1.elb.amazonaws.com/api/movies/recommendations```

This should return a JSON array of movies. For example:
```
[
    {
        "name": "Star Wars",
        "genre": "Sci-Fi",
        "releaseDate": "1977-05-25"
    },
    ....
]
```

### Testing the Database

The created PostgreSQL database is publically accessible, meaning it is possible to run database queries outside of AWS. The database can be accessed via the Database URL (or domain) returned by `terraform apply`.

The database username and password credentials are stored within:
```
/modules/database/postgres.tf
```
