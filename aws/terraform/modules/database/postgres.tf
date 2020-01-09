provider "aws" {
  region = "eu-west-1"
}

####################################
# Variables common to both instnaces
####################################
locals {
  engine            = "postgres"
  engine_version    = "11.5"
  instance_class    = "db.t2.micro"
  allocated_storage = 5
  port              = "5432"
  username          = "postgres"
  password          = "ScottLogic"
  database_url      = "jdbc:postgresql://${module.master.this_db_instance_endpoint}/${var.database-name}"
}

## Security group for PostgreSQL
resource "aws_security_group" "movie_recommendations_postgres_sec_grp" {
  name        = var.postgres-sec-grp
  description = "Allows PostgreSQL traffic"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name        = var.postgres-sec-grp
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

###########
# Master DB
###########
module "master" {
  source = "./terraform-aws-rds"

  identifier = var.database-identifier

  engine            = local.engine
  engine_version    = local.engine_version
  instance_class    = local.instance_class
  allocated_storage = local.allocated_storage

  name     = var.database-name
  username = local.username
  password = local.password
  port     = local.port

  # Enabled to allow SQL Clients to query database during development
  publicly_accessible = true

  vpc_security_group_ids = [aws_security_group.movie_recommendations_postgres_sec_grp.id]

  maintenance_window = "Mon:00:00-Mon:03:00"
  
  # DB subnet group
  subnet_ids = var.vpc_subnet_ids

  create_db_option_group    = false
  create_db_parameter_group = false

  tags = {
    Project     = var.project-name-value
    Environment = terraform.workspace  
  }
}
