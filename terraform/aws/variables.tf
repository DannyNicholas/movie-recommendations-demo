variable "region" {
  description = "The AWS region to create resources in."
  default     = "eu-west-1"
}

variable "vpc_name" {
  default = "movie-recommendations-shared-vpc"
}

variable "int_gateway_name" {
  default = "movie-recommendations-shared-internet-gateway"
}

variable "route_table_name" {
  default = "movie-recommendations-shared-route-table"
}

variable "sub_net_name" {
  default = "movie-recommendations-shared-subnet"
}

variable "api_gateway_name" {
  default = "movie-recommendations-shared-api-gateway"
}

variable "ecs-sec-grp" {
  default = "movie-recommendations-ecs-sec-grp"
}
variable "lb-sec-grp" {
  default = "movie-recommendations-lb-sec-grp"
}

variable "alb_name" {
  default = "movie-api-server-alb"
}

variable "alb_target_group_name" {
  default = "movie-api-server-tg"
}

variable "project-name-value" {
  default = "dn-movie-recommendations"
}
