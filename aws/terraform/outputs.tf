# API domain.
output "api_domain_name" {
  description = "Fully qualified domain name for the API"
  value       = module.alb.api_domain_name
}

# Output the load-balancer DNS. We should always use the API domain over this.
output "application_load_balancer_dns_name" {
  description = "Application Load Balancer DNS Name"
  value       = module.alb.dns_name
}

# Output the Database URL.
output "database_url" {
  description = "Database URL"
  value       = module.database.database_url
}
