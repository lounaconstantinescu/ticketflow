# Frontend TicketFlow

## Runtime config
En staging/prod, Ansible déploie un fichier `config.js` à la racine web qui contient :
`window.APP_CONFIG.API_BASE_URL`.

En dev, vous pouvez utiliser `.env` (Vite) :
- `VITE_API_BASE_URL=http://localhost:8080/api`

## Tests
- `npm test` : Vitest (unit/component)
- `npm run e2e` : Playwright (smoke)
