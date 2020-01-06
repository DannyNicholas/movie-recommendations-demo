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

variable "alb_name" {
  type    = string
  default = "movie-api-server-alb"
}

variable "alb_target_group_name" {
  type    = string
  default = "movie-api-server-tg"
}

variable "project-name-value" {
  type    = string
  description = "Project tag value, used for tracking costs"
  default = "dn-movie-recommendations"
}
