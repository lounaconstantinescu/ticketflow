# Publication d'artefacts (atelier)

Le pipeline CI produit :
- `artifacts/ticketflow.jar`
- `artifacts/ticketflow-frontend.tar.gz`

Pour Ansible, les URL `backend.jar_url` et `frontend.build_url` doivent pointer vers ces artefacts
(p. ex. serveur HTTP interne, Nexus/Artifactory, ou simple Nginx).
