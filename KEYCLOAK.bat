docker run -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -e DB_VENDOR=h2 --rm -it -p 8084:8084 jboss/keycloak:latest