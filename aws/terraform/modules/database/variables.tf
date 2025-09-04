variable "project-name-value" {
  type        = string
  description = "Project tag value, used for tracking costs"
}

variable "database-identifier" {
  type        = string
  description = "Database identifier"
}

variable "database-name" {
  type        = string
  description = "Database name"
}

variable "vpc_subnet_ids" {
  type = list(string)
}

variable "vpc_id" {
  type = string
}

variable "postgres-sec-grp" {
  type    = string
  default = "movie-recommendations-postgres-sec-grp"
}
