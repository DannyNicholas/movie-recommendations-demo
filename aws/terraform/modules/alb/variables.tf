variable alb_sec_grp_id {
  type = string
}

variable "alb_name" {
  type = string
}

variable "vpc_subnet_ids" {
  type = list(string)
}

variable "consistent_url" {
  type        = string
  description = "the private hosted zone URL to map to load balancer"
  default     = ""
}

variable "alb_target_group_name" {
  type = string
}

variable "alb_sec_grp_name" {
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