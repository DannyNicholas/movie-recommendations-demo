  
variable "vpc_name" {
  type = string
}

variable "int_gateway_name" {
  type = string
}

variable "route_table_name" {
  type = string
}

variable "sub_net_name" {
  type = string
}

variable "sub_net_1_cidr_block" {
  type        = string
  description = "The cidr block for the first subnet"
  default     = "10.0.254.0/24"
}

variable "sub_net_2_cidr_block" {
  type        = string
  description = "The cidr block for the second subnet"
  default     = "10.0.255.0/24"
}

variable "availability_zone_1" {
  type        = string
  description = "The first availability zone"
  default     = "eu-west-2a"
}

variable "availability_zone_2" {
  type        = string
  description = "The second availability zone"
  default     = "eu-west-2c"
}

variable "project-name-value" {
  type        = string
  description = "Project tag value, used for tracking costs"
}