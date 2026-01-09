# Backend TicketFlow (Spring Boot)

## Profils
- dev : H2 + seed data + logs DEBUG
- test : H2 + sécurité permissive (pour tests d'intégration)
- prod : PostgreSQL + secrets via env

## Lancer
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Auth
POST `/api/auth/login` :
```json
{ "username": "admin", "password": "admin" }
```

Puis utiliser l'en-tête :
`Authorization: Bearer <token>`
