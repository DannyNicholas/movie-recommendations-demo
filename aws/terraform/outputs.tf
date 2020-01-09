
# Output the load-balancer DNS. Our API entry point into the cluster
output "application_load_balancer_dns_name" {
  value = "${module.alb.dns_name}"
}

# Output the Database endpoint.
output "database_endpoint" {
  value = "${module.database.this_db_instance_endpoint}"
}

output "database_url" {
  description = "Postgres URL"
  value       = "${module.database.database_url}"
}