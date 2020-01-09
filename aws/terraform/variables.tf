variable "region" {
  type        = string
  description = "The AWS region to create resources in."
  default     = "eu-west-1"
}

variable "vpc_name" {
  type    = string
  default = "movie-recommendations-shared-vpc"
}

variable "int_gateway_name" {
  type    = string
  default = "movie-recommendations-shared-internet-gateway"
}

variable "route_table_name" {
  type    = string
  default = "movie-recommendations-shared-route-table"
}

variable "sub_net_name" {
  type    = string
  default = "movie-recommendations-shared-subnet"
}

variable "api_gateway_name" {
  type    = string
  default = "movie-recommendations-shared-api-gateway"
}

variable "ecs-sec-grp" {
  type    = string
  default = "movie-recommendations-ecs-sec-grp"
}

variable "lb-sec-grp" {
  type    = string
  default = "movie-recommendations-lb-sec-grp"
}

variable "postgres-sec-grp" {
  type    = string
  default = "movie-recommendations-postgres-sec-grp"
}

variable "alb_name" {
  type    = string
  default = "movie-api-server-alb"
}

variable "alb_target_group_name" {
  type    = string
  default = "movie-api-server-tg"
}

variable "project-name-value" {
  type        = string
  description = "Project tag value, used for tracking costs"
  default     = "dn-movie-recommendations"
}

variable "movie-service-image-repository-name" {
  type        = string
  description = "Name of the ECR repository where the service's container image is stored"
  default     = "dn_movie_recommendation_api"
}

variable "ec2_instance_type" {
  type        = string
  description = "EC2 instance type to use within cluster"
  default     = "t2.medium"
}

variable "ec2_instances_desired" {
  type        = number
  description = "Desired number of EC2 instances within cluster (must be within cluster's autoscale min and max variables)"
  default     = 2
}

variable "service_tasks_desired" {
  type        = number
  description = "Desired number of service tasks to create within cluster"
  default     = 4
}

variable "database-identifier" {
  type        = string
  description = "Database identifier"
  default     = "dn-movies-database"
}

variable "database-name" {
  type        = string
  description = "Database name"
  default     = "movies"
}
