### SpringBoot-Google-Cloud-Platform-Microservices
#### Project Configurations
- SpringBoot microservices for `google cloud platform` with `kubernetes` incorporates order, product and payment services.
- A config-server for common properties on `GITHUB`, `eureka service` and an `API gateway` with spring security.
- Each service is accessed with `OKTA Auth` connected to the database.
- Config-Server on GITHUB: https://github.com/lawrencejews/gcp-microservices-config-server/tree/main
- Circuit Breakers for services down `spring-cloud-starter-circuitbreaker-reactor-resilience4j` and Call Rate Limiter `docker run --name latestredis -d -p6379:6379 redis`
- Zipkin Docker command: `docker run -d -p 9411:9411 openzipkin/zipkin`