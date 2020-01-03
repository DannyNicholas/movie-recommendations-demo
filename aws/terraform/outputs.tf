
# Output the load-balancer DNS. Our API entry point into the cluster
output "application_load_balancer_dns_name" {
  value = "${module.alb.dns_name}"
}