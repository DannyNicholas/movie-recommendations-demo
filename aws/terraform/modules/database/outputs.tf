output "this_db_instance_endpoint" {
  description = "The connection endpoint"
  value       = module.master.this_db_instance_endpoint
}

output "this_db_instance_username" {
  description = "Database username"
  value       = local.username
}

output "this_db_instance_password" {
  description = "Database password"
  value       = local.password
}

output "database_url" {
  description = "Postgres URL"
  value       = local.database_url
}