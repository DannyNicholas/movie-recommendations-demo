## Load Balancer creation

resource "aws_alb" "movie-ecs-alb" {
  name               = var.alb_name
  internal           = var.internal
  load_balancer_type = "application"
  security_groups    = [var.alb_sec_grp_id]
  subnets            = var.vpc_subnet_ids

  tags = {
    Name        = var.alb_name
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

resource "aws_lb_listener" "movie_recommendations-producer-lb-listener" {
  load_balancer_arn = aws_alb.movie-ecs-alb.id
  port              = 443
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = aws_acm_certificate_validation.api_cert_validation.certificate_arn

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
    Name        = var.alb_target_group_name
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

data "aws_route53_zone" "main" {
  name         = var.api_domain
  private_zone = false
}

resource "aws_route53_record" "api" {
  zone_id = data.aws_route53_zone.main.zone_id
  name    = "api.${data.aws_route53_zone.main.name}"
  type    = "A"

  alias {
    name                   = aws_alb.movie-ecs-alb.dns_name
    zone_id                = aws_alb.movie-ecs-alb.zone_id
    evaluate_target_health = true
  }
}

# Required for HTTPS
resource "aws_acm_certificate" "api_cert" {
  domain_name       = "api.${data.aws_route53_zone.main.name}"
  validation_method = "DNS"
}

resource "aws_route53_record" "cert_validation" {
  zone_id = data.aws_route53_zone.main.zone_id
  name    = aws_acm_certificate.api_cert.domain_validation_options[0].resource_record_name
  type    = aws_acm_certificate.api_cert.domain_validation_options[0].resource_record_type
  records = [aws_acm_certificate.api_cert.domain_validation_options[0].resource_record_value]
  ttl     = 60
}

resource "aws_acm_certificate_validation" "api_cert_validation" {
  certificate_arn         = aws_acm_certificate.api_cert.arn
  validation_record_fqdns = [aws_route53_record.cert_validation.fqdn]
}
