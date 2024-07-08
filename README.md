# Get started
```bash
docker compose up -d --build
```
# Login to pgAdmin
`
localhost:5050
`
 - Email: admin@demo.com
 - Password: admin

# Connect pgAdmin to PostgreSQL
Add new server
 - Host: postgres
 - User: admin
 - Password: admin
 
# Update code 
 ```bash
docker-compose down && docker image rm -f demo-backend && docker-compose up -d
 ```

# Documents UI
`
http://127.0.0.1:8080/swagger-ui/index.html
`