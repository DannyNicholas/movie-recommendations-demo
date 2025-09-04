output "id" {
  value = aws_vpc.movie_recommendations_vpc.id
}
output "subnet_ids" {
  value = [aws_subnet.movie_recommendations_ecs_vpc_sub_1.id, aws_subnet.movie_recommendations_ecs_vpc_sub_2.id]
}
