
resource "aws_ecs_cluster" "movie_recommendation-ecs-cluster" {
  name = var.ecs_cluster_name

  tags = {
    Project     = var.project-name-value
    Environment = terraform.workspace  
  }
}

provider "template" {
  version = "~> 2.1"
}

## template file to initialise EC2 instance on start-up.
# this is primarily used to identify the cluster that
# the EC2 instance belongs to.
data "template_file" "init" {
  template = file("./modules/ecs/cloud-init.yaml")
  vars  = {
    ecs_cluster_name = "${var.ecs_cluster_name}"
  }
}

## List of suitable Amazon Machine Images
data "aws_ami_ids" "ami" {
  owners = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-ecs-hvm*-x86_64-ebs"]
  }
}

## Profile for the instances created by ECS for easier referencing
resource "aws_iam_instance_profile" "ecs-cluster-instance-profile" {
  name = var.ecs_cluster_instance_profile_name
  path = "/"
  role = aws_iam_role.movie-recommendations_ecs_cluster_host_role.name
}

## Launch configuration for ECS
resource "aws_launch_configuration" "ecs" {
  name = "ECS ${var.ecs_cluster_name}"

  image_id                    = data.aws_ami_ids.ami.ids[0]
  instance_type               = var.instance_type
  security_groups             = [var.ecs_sec_grp_id, var.alb_sec_grp_id]
  iam_instance_profile        = aws_iam_instance_profile.ecs-cluster-instance-profile.name
  associate_public_ip_address = true

  # Uncomment the line below if you need to directly SSH to EC2 instances
  #key_name                    = var.ssh_key_name

  # associates the ec2 instance with the cluster
  user_data                   = data.template_file.init.rendered
}

resource "aws_autoscaling_group" "ecs-cluster" {
  
  name = "ECS ${var.ecs_cluster_name}"

  min_size             = var.autoscale_min
  max_size             = var.autoscale_max
  desired_capacity     = var.instances_desired
  health_check_type    = "EC2"
  launch_configuration = aws_launch_configuration.ecs.name

  vpc_zone_identifier = var.vpc_subnet_ids

  tags = [
    {
      key                 = "Project"
      value               = var.project-name-value
      propagate_at_launch = true
    },
    {
      key                 = "Environment"
      value               = terraform.workspace
      propagate_at_launch = true
    },
    {
      key                 = "Name"
      value               = "EC2 for ${var.ecs_cluster_name}"
      propagate_at_launch = true
    },
  ]
}