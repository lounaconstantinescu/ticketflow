# TicketFlow — Projet fil rouge (Java Spring Boot + React) pour le module BC03

Ce dépôt monorepo fournit un projet complet (squelette exécutable) destiné à illustrer :
1. Git (branches, conflits, merges, tags)
2. Pipeline DevOps (CI/CD)
3. Tests automatisés (unit / intégration / e2e)
4. Déploiement automatisé (Ansible)
5. Gestion de configuration multi-environnements (Spring profiles + runtime config React + option Puppet)

## Pré-requis (poste dev)
- Java 17+
- Maven 3.9+
- Node.js LTS (>= 18)
- Git

## Démarrage rapide (DEV local)

### Backend
```bash
cd backend
mvn -Dspring-boot.run.profiles=dev spring-boot:run 
```

- API: http://localhost:8080/api
- Health: http://localhost:8080/actuator/health

Utilisateur DEV (seed automatique) :
- username: `admin`
- password: `admin`

### Frontend
```bash
cd frontend
npm ci
npm run dev
```

Front: http://localhost:5173

En DEV, l'API est configurée via `VITE_API_BASE_URL` (voir `.env.example`).

## Documentation pédagogique
- Guide TP : `docs/WORKSHOP_GUIDE.md`
- CI : `ci/gitlab-ci/.gitlab-ci.yml`
- Ansible : `infra/ansible/`
- Config Mgmt (option) : `infra/config-mgmt/puppet/`
