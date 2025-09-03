
output "target_group_id" {
  value = aws_lb_target_group.movie_recommendations-ecs-tg.id
}

output "listener_id" {
  value = aws_lb_listener.movie_recommendations-producer-lb-listener.id
}

output "dns_name" {
  value = aws_alb.movie-ecs-alb.dns_name
}

output "arn" {
  value = aws_alb.movie-ecs-alb.arn
}
