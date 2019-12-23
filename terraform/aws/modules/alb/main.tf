## Load Balancer creation

## Security group for the load balancer
data "aws_security_group" "movie_recommendations_ecs_load_bal_sec_grp" {
  name        = "movie_recommendations-lb-sec-grp"
  vpc_id      = var.vpc_id
}

resource "aws_alb" "movie-ecs-alb" {
  name               = var.alb_name
  internal           = var.internal
  load_balancer_type = "application"
  security_groups    = [data.aws_security_group.movie_recommendations_ecs_load_bal_sec_grp.id]
  subnets            = var.vpc_subnet_ids

  tags = {
    Name = var.alb_name
    Project = var.project-name-value
    Environment = terraform.workspace  
  }
}

resource "aws_lb_listener" "movie_recommendations-producer-lb-listener" {
  load_balancer_arn = aws_alb.movie-ecs-alb.id
  port              = 80
  protocol          = "HTTP"

  depends_on = [aws_alb.movie-ecs-alb, aws_lb_target_group.movie_recommendations-ecs-tg]

  default_action {
    target_group_arn = aws_lb_target_group.movie_recommendations-ecs-tg.id
    type             = "forward"
  }
}

resource "aws_lb_target_group" "movie_recommendations-ecs-tg" {
  name     = var.alb_target_group_name
  port     = 80
  protocol = "HTTP"
  vpc_id   = var.vpc_id

  health_check {
    healthy_threshold   = 2
    unhealthy_threshold = 6
    timeout             = 60
    path                = "/actuator/health"
    protocol            = "HTTP"
    interval            = 90
  }

  tags = {
    Name = var.alb_target_group_name
    Project = var.project-name-value
    Environment = terraform.workspace
  }
}

## Create the entry in the private hosted zone if passed in
# resource "aws_route53_record" "alb_private_hosted_zone_entry" {
#   count = var.consistent_url != "" ? 1 : 0

#   zone_id = var.private_hosted_zone_id
#   name    = var.consistent_url

#   type    = "CNAME"
#   ttl     = "300"
#   records = [aws_alb.movie-ecs-alb.dns_name]
# }