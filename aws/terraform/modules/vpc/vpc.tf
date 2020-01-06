// Put permanent vpc related resources here to remain running
// such as VPC, internet gateway for staging/prod

resource "aws_vpc" "movie_recommendations_vpc" {
  cidr_block = "10.0.0.0/16"

  tags = {
    Name        = var.vpc_name
    Project     = var.project-name-value
    Environment = terraform.workspace
  }

  enable_dns_support   = true
  enable_dns_hostnames = true
}

resource "aws_internet_gateway" "movie_recommendations_int_gateway" {
  vpc_id = aws_vpc.movie_recommendations_vpc.id

  tags = {
    Name        = var.int_gateway_name
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

## Create a route table to use with the new subnets
## (may create one per subnet for better availability?)
resource "aws_route_table" "movie_recommendations_ecs_vpc_external_route_table" {
  vpc_id = aws_vpc.movie_recommendations_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.movie_recommendations_int_gateway.id
  }

  tags = {
    Name        = var.route_table_name
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

## First subnet within the VPC in a given availability zone
resource "aws_subnet" "movie_recommendations_ecs_vpc_sub_1" {
  vpc_id            = aws_vpc.movie_recommendations_vpc.id
  cidr_block        = var.sub_net_1_cidr_block
  availability_zone = var.availability_zone_1

  tags = {
    Name        = "${var.sub_net_name}-1"
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

## Second subnet within the VPC in a given availability zone
resource "aws_subnet" "movie_recommendations_ecs_vpc_sub_2" {
  vpc_id            = aws_vpc.movie_recommendations_vpc.id
  cidr_block        = var.sub_net_2_cidr_block
  availability_zone = var.availability_zone_2

  tags = {
    Name        = "${var.sub_net_name}-2"
    Project     = var.project-name-value
    Environment = terraform.workspace
  }
}

## Route table association between the first subnet and the newly created route table
resource "aws_route_table_association" "movie_recommendations_ecs_rta_1" {
  subnet_id      = aws_subnet.movie_recommendations_ecs_vpc_sub_1.id
  route_table_id = aws_route_table.movie_recommendations_ecs_vpc_external_route_table.id
}

## Route table association between the second subnet and the newly created route table
resource "aws_route_table_association" "movie_recommendations_ecs_rta_2" {
  subnet_id      = aws_subnet.movie_recommendations_ecs_vpc_sub_2.id
  route_table_id = aws_route_table.movie_recommendations_ecs_vpc_external_route_table.id
}