variable "alb_name" {}

variable "vpc_subnet_ids" {
  type = list
}

variable "consistent_url" {
  description = "the private hosted zone URL to map to load balancer"
  default     = ""
}

# variable "private_hosted_zone_id" {}

variable "alb_target_group_name" {}

variable "vpc_id" {}
variable "internal" {
  default = "false"
}

variable "project-name-value" {
  description = "Project tag value, used for tracking costs"
  default = "dn-movie-recommendations"
}