locals {
  api_log_group_name = "${var.ecs_movie_recommendation_api_log_group_name}-${terraform.workspace}"
  api_container_name = "dn-movie-recommendations"
}

## ECR Repository containing service's docker image
data "aws_ecr_repository" "movie_recommendation_api_repo" {
  name = var.service-image-repository-name
}

## Cloud Watch log group for service
resource "aws_cloudwatch_log_group" "dn-movie-recommendations-log-group" {
  name = local.api_log_group_name
  tags = {
    Project = "${var.project-name-value}"
    Environment = "${terraform.workspace}"  
  }
}

## ECS Task definition for service
resource "aws_ecs_task_definition" "movie_recommendation_api" {
  family       = "dn-movie-recommendations-service"
  network_mode = "bridge"
  tags = {
    Project = var.project-name-value
    Environment = terraform.workspace  
  }

  container_definitions = <<DEFINITION
  [
    {
        "name": "${local.api_container_name}",
        "image": "${data.aws_ecr_repository.movie_recommendation_api_repo.repository_url}",
        "cpu": 256,
        "memoryReservation": 512,
        "portMappings": [
            {
            "containerPort": 8080,
            "hostPort": 0
            }
        ],
        "essential": true,
        "entryPoint": [],
        "command": [],
        "environment": [{
            "name": "SPRING_PROFILES_ACTIVE",
            "value": "stub"
        }],
        "logConfiguration": {
            "logDriver": "awslogs",
            "options": {
                "awslogs-group": "${aws_cloudwatch_log_group.dn-movie-recommendations-log-group.name}",
                "awslogs-region": "${var.region}",
                "awslogs-stream-prefix": "ecs"
            }
        }
    }
]
DEFINITION
}

## Create a null resource with the dependencies from other modules so that 
## the services can explicitly depend on them
provider "null" {
  version = "~> 2.1"
}

resource "null_resource" "movie_recommendations_ecs_service_dependencies" {
  triggers = {
    service_policy = var.ecs_service_policy_id
  }
}

resource "null_resource" "alb_exists" {
  triggers = {
    alb_name = "${var.alb_arn}"
  }
}

## Service Discovery DNS namespace
resource "aws_service_discovery_private_dns_namespace" "movie_recommendations_discovery" {
  name        = "${terraform.workspace}.${var.service_discovery_domain_name}_movies_api"
  description = "Movies Recommendations service discovery namespace"
  vpc         = var.vpc_id
}

## Movie API Discovery service
resource "aws_service_discovery_service" "movie_recommendations" {
  name = "${terraform.workspace}-movies_api"

  dns_config {
    namespace_id = aws_service_discovery_private_dns_namespace.movie_recommendations_discovery.id

    dns_records {
      ttl  = 1
      type = "SRV"
    }
  }

  health_check_custom_config {
    failure_threshold = 1
  }
}

## ECS Service definition for service
resource "aws_ecs_service" "movie_recommendations-ecs-service-api" {
  name            = "movie_recommendations-ecs-service-api-${terraform.workspace}"
  cluster         = var.cluster_id
  task_definition = aws_ecs_task_definition.movie_recommendation_api.arn
  desired_count   = var.tasks_desired

  service_registries {
    registry_arn   = aws_service_discovery_service.movie_recommendations.arn
    container_name = local.api_container_name
    container_port = 8080
  }

  load_balancer {
    target_group_arn = var.loadbalancer_id
    container_name   = local.api_container_name
    container_port   = 8080
  }

  ## Dependencies on the role policy and the ALB being available
  depends_on = [
    null_resource.movie_recommendations_ecs_service_dependencies,
    null_resource.alb_exists
  ]
  tags = {
    Project = var.project-name-value
    Environment = terraform.workspace 
  }
}