variable "alb_sec_grp_id" {
  type = string
}

variable "alb_name" {
  type = string
}

variable "vpc_subnet_ids" {
  type = list(string)
}

variable "alb_target_group_name" {
  type = string
}

variable "vpc_id" {
  type = string
}

variable "internal" {
  type    = string
  default = "false"
}

variable "project-name-value" {
  type        = string
  description = "Project tag value, used for tracking costs"
}

variable "api_domain" {
  type        = string
  description = "Wanted Route53 API domain. Must be one you own."
}
