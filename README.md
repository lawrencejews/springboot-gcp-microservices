### SpringBoot-Google-Cloud-Platform-Microservices
#### Project Configurations
- SpringBoot microservices for `google cloud platform` with `kubernetes` incorporates order, product and payment services.
- A config-server for common properties on `GITHUB`, `eureka service` and an `API gateway` with spring security.
- Config-Server on GITHUB: https://github.com/lawrencejews/gcp-microservices-config-server/tree/main
- Circuit Breakers for services down `spring-cloud-starter-circuitbreaker-reactor-resilience4j` and Call Rate Limiter `docker run --name latestredis -d -p6379:6379 redis`
- Zipkin Docker command: `docker run -d -p 9411:9411 openzipkin/zipkin`
#### Okta Auth 
- User Login API is created in the API-GATEWAY where it hits the Okta Auth for Authorization and Authentication.
- Register your application with developer edition okta `https://developer.okta.com/docs/guides/`.
- Sign-in user-redirect uri: `http://localhost:9090/login/oauth2/code/okta`or `http://localhost:8080/authorization-code/callback`
- Sign-out user-redirect uri: `http://localhost:9090/login/oauth2/code/okta`
- Get your Client ID`0oagrqqyXXXXXXXXXX` and Cient Security `X1d969NDkiVrDEXY6Eg8-XXXXXXXXXXXXXXXXXXX`.
- Go to Okta security API for the authorization server issuer URI and determine which scope to use.
- Define GROUPS  for Admin and Customer -> PEOPLE attached to groups then -> use them in claims-security tab.
- `NOTE`: Create a new claim for the project by attaching the created groups.
- Add SpringBoot dependencies for okta ` <artifactId>okta-spring-boot-starter</artifactId>` and spring security `<artifactId>spring-boot-starter-security</artifactId>` .
#### Unit Testing - JUNIT
- Mockito: vital role in developing testable applications.
- wireMock: Create stable test and development environments, isolate yourself from flakey 3rd parties and simulate APIs that don’t exist yet.
- TODO