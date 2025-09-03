variable "cluster_id" {
  type = string
}

variable "region" {
  type = string
}

variable "ecs_service_policy_id" {
  type = string
}

variable "ecs_service_role_arn" {
  type = string
}

variable "vpc_id" {
  type = string
}

variable "vpc_subnet_ids" {
  type = list(string)
}

variable "ecs_sec_grp_id" {
  type = string
}

variable "ecs_movie_recommendation_api_log_group_name" {
  type        = string
  default     = "/ecs/movie-recommendations-api"
  description = "The name of the api log group to identify cloudwatch logs"
}

variable "service_discovery_domain_name" {
  type        = string
  description = "The name of the domain used by service discovery"
  default     = "movie-recommendations-api.scottlogic.com"
}

variable "tasks_desired" {
  description = "The desired number of service tasks to create"
  type        = number
  default     = 1
}

variable loadbalancer_id {
  type = string
}

variable "project-name-value" {
  type        = string
  description = "Project tag value, used for tracking costs"
}

variable "service-image-repository-name" {
  type        = string
  description = "Name of the ECR repository where the service's container image is stored"
}

variable "alb_arn" {
  type        = string
  description = "The Amazon Resource Name (ARN) of the ALB that this ECS Service will use as its load balancer."
}

variable "database_url" {
  type        = string
  description = "URL of database"
}

variable "database_username" {
  type        = string
  description = "Database username"
}

variable "database_password" {
  type        = string
  description = "Database password"
}