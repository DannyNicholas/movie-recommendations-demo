provider "aws" {
  region  = var.region
  version = "~> 2.0"
}

module "vpc" {
  source           = "./modules/vpc"
  vpc_name         = var.vpc_name
  int_gateway_name = var.int_gateway_name
  sub_net_name     = var.sub_net_name
  route_table_name = var.route_table_name
}

module "alb" {
  source                 = "./modules/alb"
  vpc_id                 = module.vpc.id
  vpc_subnet_ids         = module.vpc.subnet_ids
  alb_name               = "${var.alb_name}-${terraform.workspace}"
  alb_target_group_name  = "${var.alb_target_group_name}-${terraform.workspace}"
  alb_sec_grp_name       = var.lb-sec-grp
  alb_sec_grp_id         = aws_security_group.movie_recommendations_ecs_load_bal_sec_grp.id
}

module "ecs" {
  source         = "./modules/ecs"
  region         = "${var.region}"
  vpc_id         = module.vpc.id
  vpc_subnet_ids = module.vpc.subnet_ids
  alb_sec_grp_id = aws_security_group.movie_recommendations_ecs_load_bal_sec_grp.id
  ecs_sec_grp_id = aws_security_group.movie_recommendations_ecs_sec_grp.id
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
