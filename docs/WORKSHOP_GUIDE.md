# BC03 — Usine Logicielle — Guide de TP (5 jours / 35h)

Ce guide est conçu pour exploiter le projet **TicketFlow** afin de réaliser les 5 exercices du module.

---

## 0. Contexte pédagogique

**TicketFlow** est une application web interne de gestion de tickets :
- Backend : Java 17 / Spring Boot (API REST + JWT)
- Frontend : React + Vite (SPA)
- Reverse proxy : Nginx (sert le front et proxifie `/api` vers le backend)
- Déploiement : Ansible (inventaires `dev/staging/prod`)
- Gestion de config : Spring profiles + runtime config React + option Puppet

---

## 1. Exercice 1 — Pratique Git : branches, conflits, merges

### Objectifs
- Manipuler : init, add, commit, push/pull, branch, merge, rebase (option), tag
- Résoudre des conflits
- Appliquer des conventions de branches + commits
- (Option) Travailler en MR/PR avec relecture

### Mise en situation (scénario fil rouge)
Vous êtes en équipe. Deux features doivent être développées en parallèle :
- **Feature A** : priorités de tickets (P1/P2/P3)
- **Feature B** : filtres (par statut/priorité)

### Étapes demandées
1. Initialiser un dépôt Git vide puis pousser ce monorepo.
2. Créer `develop` à partir de `main`.
3. Créer deux branches :
   - `feature/ticket-priority`
   - `feature/ticket-filter`
4. Répartir le travail :
   - A modifie backend + UI (priorité)
   - B modifie backend + UI (filtres)
5. Créer volontairement un conflit :
   - les deux branches modifient `backend/src/main/java/.../TicketDto.java` **ou**
     `frontend/src/pages/TicketListPage.tsx`
6. Résoudre le conflit, valider par tests, puis merger dans `develop`
7. Préparer une release :
   - merge `develop` → `main`
   - tag : `v1.0.0`

### Livrables
- Historique de commits lisible (`feat:`, `fix:`, `test:`, `chore:`)
- Conflit résolu (preuve : MR + diff)
- Tag `v1.0.0`

---

## 2. Exercice 2 — Mise en place d’un pipeline DevOps (CI/CD)

### Objectifs
- Définir un pipeline build/test/package/deploy
- Produire des artefacts
- Mettre en place environnements (staging/prod) + variables CI
- Déclencher un déploiement automatique vers staging, manuel vers prod

### Travail demandé
1. Choisir un moteur CI : GitLab CI (fourni) ou Jenkins (option)
2. Activer le pipeline :
   - build backend
   - tests backend
   - build frontend
   - tests frontend
3. Produire des artefacts :
   - `backend/target/ticketflow.jar`
   - `frontend/dist` (packagé en tar.gz)
4. Ajouter un job de déploiement staging via Ansible :
   - `ansible-playbook ... -i inventories/staging`
5. Ajouter un job de déploiement prod en **manuel** (approbation)

---

## 3. Exercice 3 — Intégration de tests automatisés dans un pipeline CI/CD

### Objectifs
- Mettre en place : unit, intégration, API tests, UI tests
- Publier résultats (JUnit) et coverage (JaCoCo)
- Bloquer la promotion si tests échouent

### Travail demandé
1. Backend :
   - compléter des tests unitaires (Mockito) pour `TicketService`
   - compléter des tests d’intégration `TicketControllerIT` via MockMvc
   - activer JaCoCo et un seuil (ex : 60% au démarrage, puis augmenter)
2. Frontend :
   - compléter tests composants avec React Testing Library (Vitest)
3. E2E :
   - ajouter un test Playwright (ou Selenium option) : login → création ticket → vérif liste
4. Pipeline :
   - publier rapports JUnit
   - publier coverage JaCoCo

---

## 4. Exercice 4 — Automatisation du déploiement avec Ansible

### Objectifs
- Écrire des playbooks idempotents
- Gérer inventaires multi-env
- Déployer backend + frontend + nginx
- Vérifier état applicatif (healthcheck)

### Travail demandé
1. Exécuter le playbook `infra/ansible/playbooks/site.yml` sur un serveur Linux
2. Vérifier :
   - service `ticketflow` actif (`systemctl status`)
   - Nginx sert le front
   - `/actuator/health` OK
3. Mettre en œuvre un **rolling update** (serial=1) sur 2 hosts (staging)

---

## 5. Exercice 5 — Gestion des configurations dans un environnement CI/CD

### Objectifs
- Isoler dev/test/prod (config, secrets, endpoints)
- Traçabilité des changements de configuration
- Gestion sécurisée des secrets (vault, variables protégées)
- (Option) Utiliser un outil dédié (Puppet/Chef)

### Travail demandé
1. Spring profiles :
   - `dev` : H2, logs DEBUG, seed data
   - `test` : H2, sécurité permissive pour tests
   - `prod` : PostgreSQL, logs INFO, secrets externalisés
2. React runtime config :
   - générer `config.js` via Ansible (API URL selon env)
3. Secrets :
   - chiffrer `vault.yml` avec ansible-vault
   - injecter secrets via variables CI protégées (staging/prod)
4. Option Puppet :
   - appliquer le manifest fourni (ou compléter) pour gérer packages + conf + services

---

## Annexes — Commandes utiles

### Note compilation Java
Ce projet active la conservation des noms de paramètres Java via l'option `-parameters` (Maven compiler plugin), afin d'éviter les erreurs Spring MVC sur les `@RequestParam`.

### Backend — exécuter tests
```bash
cd backend
mvn test
```

### Frontend — tests
```bash
cd frontend
npm test
```

### Ansible — déployer staging
```bash
cd infra/ansible
ansible-playbook -i inventories/staging/hosts.yml playbooks/site.yml --ask-vault-pass
```
