
output "movie_recommendation_api_repo_url" {
  value = "${data.aws_ecr_repository.movie_recommendation_api_repo.repository_url}"
}

output "cluster_id" {
  value = "${aws_ecs_cluster.movie_recommendation-ecs-cluster.id}"
}

output "service_role_arn" {
  value = "${aws_iam_role.movie-recommendations_ecs_cluster_service_role.arn}"
}

output "service_policy_id" {
  value = "${aws_iam_role_policy.ecs_service_role_policy.id}"
}
