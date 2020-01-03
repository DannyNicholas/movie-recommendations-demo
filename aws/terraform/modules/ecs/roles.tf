resource "aws_iam_role" "movie-recommendations_ecs_cluster_host_role" {
  name               = var.ecs_cluster_host_role_name
  assume_role_policy = file("./modules/ecs/policies/ecs-role.json")
}

## Policy for role
resource "aws_iam_role_policy" "ecs_instance_role_policy" {
  name   = "ecs_instance_role_policy"
  policy = file("./modules/ecs/policies/ecs-instance-role-policy.json")
  role   = aws_iam_role.movie-recommendations_ecs_cluster_host_role.id
}

## Role for ECS services themselves
resource "aws_iam_role" "movie-recommendations_ecs_cluster_service_role" {
  name               = var.ecs_cluster_service_role_name
  assume_role_policy = file("./modules/ecs/policies/ecs-role.json")
}

## Policy for role
resource "aws_iam_role_policy" "ecs_service_role_policy" {
  name   = "ecs_service_role_policy"
  policy = file("./modules/ecs/policies/ecs-service-role-policy.json")
  role   = aws_iam_role.movie-recommendations_ecs_cluster_service_role.id
}