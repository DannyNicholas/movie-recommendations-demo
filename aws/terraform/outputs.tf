
# Output the load-balancer DNS. Our API entry point into the cluster
output "application_load_balancer_dns_name" {
  description = "Application Load Balancer DNS Name"
  value = "${module.alb.dns_name}"
}

# Output the Database URL.
output "database_url" {
  description = "Database URL"
  value       = "${module.database.database_url}"
}