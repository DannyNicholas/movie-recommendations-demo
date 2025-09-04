terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 2.0"
    }
  }
}

provider "aws" {
  region = var.region
}

module "vpc" {
  source             = "./modules/vpc"
  vpc_name           = var.vpc_name
  int_gateway_name   = var.int_gateway_name
  sub_net_name       = var.sub_net_name
  route_table_name   = var.route_table_name
  project-name-value = var.project-name-value
}

module "alb" {
  source                = "./modules/alb"
  vpc_id                = module.vpc.id
  vpc_subnet_ids        = module.vpc.subnet_ids
  alb_name              = "${var.alb_name}-${terraform.workspace}"
  alb_target_group_name = "${var.alb_target_group_name}-${terraform.workspace}"
  alb_sec_grp_id        = aws_security_group.movie_recommendations_ecs_load_bal_sec_grp.id
  project-name-value    = var.project-name-value
  api_domain            = var.api_domain
}

module "ecs" {
  source             = "./modules/ecs"
  vpc_subnet_ids     = module.vpc.subnet_ids
  alb_sec_grp_id     = aws_security_group.movie_recommendations_ecs_load_bal_sec_grp.id
  ecs_sec_grp_id     = aws_security_group.movie_recommendations_ecs_sec_grp.id
  instance_type      = var.ec2_instance_type
  instances_desired  = var.ec2_instances_desired
  project-name-value = var.project-name-value
}

module "services" {
  source                        = "./modules/services"
  ecs_service_policy_id         = module.ecs.service_policy_id
  cluster_id                    = module.ecs.cluster_id
  region                        = var.region
  vpc_id                        = module.vpc.id
  loadbalancer_id               = module.alb.target_group_id
  alb_arn                       = module.alb.arn
  tasks_desired                 = var.service_tasks_desired
  project-name-value            = var.project-name-value
  service-image-repository-name = var.movie-service-image-repository-name
  database_url                  = module.database.database_url
  database_username             = module.database.database_username
  database_password             = module.database.database_password
}

module "database" {
  source              = "./modules/database"
  vpc_id              = module.vpc.id
  vpc_subnet_ids      = module.vpc.subnet_ids
  database-identifier = var.database-identifier
  database-name       = var.database-name
  project-name-value  = var.project-name-value
  postgres-sec-grp    = var.postgres-sec-grp
}

## Security group for ECS instances
resource "aws_security_group" "movie_recommendations_ecs_sec_grp" {
  name        = var.ecs-sec-grp
  description = "Allows all traffic"
  vpc_id      = module.vpc.id

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = var.ecs-sec-grp
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

## Security group for the load balancer
resource "aws_security_group" "movie_recommendations_ecs_load_bal_sec_grp" {
  name        = var.lb-sec-grp
  description = "Allows all traffic"
  vpc_id      = module.vpc.id

  ingress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = var.lb-sec-grp
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}
