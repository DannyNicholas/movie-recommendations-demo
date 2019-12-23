  
variable "vpc_name" {}

variable "int_gateway_name" {}

variable "route_table_name" {}

variable "sub_net_name" {}

variable "sub_net_1_cidr_block" {
  description = "The cidr block for the first subnet"
  default     = "10.0.254.0/24"
}

variable "sub_net_2_cidr_block" {
  description = "The cidr block for the second subnet"
  default     = "10.0.255.0/24"
}

variable "availability_zone_1" {
  description = "The first availability zone"
  default     = "eu-west-1a"
}

variable "availability_zone_2" {
  description = "The second availability zone"
  default     = "eu-west-1c"
}

variable "project-name-value" {
  description = "Project tag value, used for tracking costs"
  default = "dn-movie-recommendations"
}