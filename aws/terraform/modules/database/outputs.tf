output "database_username" {
  description = "Database username"
  value       = local.username
}

output "database_password" {
  description = "Database password"
  value       = local.password
}

output "database_url" {
  description = "Postgres URL"
  value       = "jdbc:postgresql://${module.master.this_db_instance_endpoint}/${var.database-name}"
}