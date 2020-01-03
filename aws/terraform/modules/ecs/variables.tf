variable "ecs_cluster_name" {
    type    = string
    default = "dn-movie-recommendations-cluster"
}

variable "vpc_id" {
    type = string
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
    type    = string
    default = "t2.medium"
}

variable "amis" {
  description = "Which AMI to spawn. Defaults to the AWS ECS optimized images."

  # TODO: support other regions.
  default = {
    eu-west-1 = "ami-2d386654"
  }
}

variable "region" {}

variable "autoscale_max" {
    type    = number
    default = 5
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
  default     = "dn-movie-recommendations"
}

variable "alb_sec_grp_id" {
    type = string
}

variable "ecs_sec_grp_id" {
    type = string
}