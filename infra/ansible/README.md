# Ansible — Déploiement TicketFlow

## Exécution
```bash
cd infra/ansible
ansible-playbook -i inventories/dev/hosts.yml playbooks/site.yml --ask-vault-pass
```

## Secrets
Chaque inventaire contient un fichier `vault.yml.example` à chiffrer en `vault.yml` via :
```bash
ansible-vault create inventories/dev/group_vars/vault.yml
```
