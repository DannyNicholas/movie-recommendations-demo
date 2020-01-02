# output "sec_grp_id" {
#   value = "${data.aws_security_group.movie_recommendations_ecs_load_bal_sec_grp.id}"
# }

output "target_group_id" {
  value = "${aws_lb_target_group.movie_recommendations-ecs-tg.id}"
}

output "listener_id" {
  value = "${aws_lb_listener.movie_recommendations-producer-lb-listener.id}"
}

output "dns_name" {
  value = "${aws_alb.movie-ecs-alb.dns_name}"
}