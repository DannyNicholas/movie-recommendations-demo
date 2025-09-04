variable "ecs_cluster_name" {
  type    = string
  default = "movie-recommendations-cluster"
}

variable "ecs_cluster_host_role_name" {
  type    = string
  default = "movie-recommendations-ecs-cluster-host-role"
}

variable "ecs_cluster_service_role_name" {
  type    = string
  default = "movie-recommendations-ecs-cluster-service-role"
}

variable "ecs_cluster_instance_profile_name" {
  type    = string
  default = "movie-recommendations-ecs-instance-profile"
}

variable "autoscale_min" {
  type    = number
  default = 1
}

variable "instance_type" {
  type = string
}

variable "autoscale_max" {
  type    = number
  default = 4
}

variable "instances_desired" {
  description = "Specify the desired number of instances for the cluster"
  type        = number
  default     = 1
}

variable "vpc_subnet_ids" {
  type = list(string)
}

variable "ssh_key_name" {
  default     = "my-ssh-key-name"
  type        = string
  description = "The name of the ssh key to use on the created ec2 instances"
}

variable "project-name-value" {
  description = "Project tag value, used for tracking costs"
  type        = string
}

variable "alb_sec_grp_id" {
  type = string
}

variable "ecs_sec_grp_id" {
  type = string
}
