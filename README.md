# Деплой: http://194.33.35.208:8080/home

# Cloud-file-storage
Multi-user cloud file storage. Users can upload their files and folders, store it and then download.
## Built with

![Java](https://img.shields.io/badge/java-black?style=for-the-badge&logo=java&link=https%3A%2F%2Fwww.java.com%2Fen%2F)
![Spring](https://img.shields.io/badge/Spring-black?style=for-the-badge&logo=spring&link=https%3A%2F%2Fspring.io)
![Spring Boot](https://img.shields.io/badge/Spring%20boot-black?style=for-the-badge&logo=spring%20boot&link=https%3A%2F%2Fspring.io)
![MySql](https://img.shields.io/badge/mysql-black?style=for-the-badge&logo=mysql&link=https%3A%2F%2Fwww.mysql.org)
![Docker](https://img.shields.io/badge/docker-black?style=for-the-badge&logo=docker&link=https%3A%2F%2Fwww.docker.com)
![Junit 5](https://img.shields.io/badge/Junit5-black?logo=junit5&style=for-the-badge)
![Bootstrap](https://img.shields.io/badge/Bootstrap-black?logo=bootstrap&style=for-the-badge)
![Thymeleaf](https://img.shields.io/badge/thymeleaf-black?style=for-the-badge&logo=thymeleaf&link=https%3A%2F%2Fwww.thymeleaf.org)
![Gradle](https://img.shields.io/badge/gradle-black?style=for-the-badge&logo=gradle&link=https%3A%2F%2Fgradle.org)
![Minio](https://img.shields.io/badge/minio-black?style=for-the-badge&logo=minio&link=https%3A%2F%2Fmin.io)
![Redis](https://img.shields.io/badge/redis-black?style=for-the-badge&logo=redis&link=https%3A%2F%2Fredis.io)
![Testcontainers](https://img.shields.io/badge/testcontainers-black?style=for-the-badge&logo=testcontainers&link=https%3A%2F%2Ftestcontainers.com)

## Project launch
1. Set up .env file according to this example:
```bash
MYSQL_USER=root
MYSQL_PASSWORD=example
MYSQL_DATABASE=cloud_db
APP_PORT=8081
DB_HOST=db
DB_PORT=3306
DB_NAME=cloud_db
MINIO_ACCESS_KEY=access_key_here
MINIO_SECRET_KEY=secret_key_here
MINIO_CLIENT_ENDPOINT=http://minio:9000
REDIS_PASSWORD=password
REDIS_PORT=6379
REDIS_HOST=redis
```
2. `docker compose up -d`
3. Then go to localhost:9001 and create access & secret keys for minio. (default user/password for minio is `minioadmin/minioadmin`)
4. Set this keys to .env file
5. `docker compose down`
6.  `docker compose up -d` again

App is available at https://localhost:8081/

![image](https://github.com/user-attachments/assets/ed87bd7f-bb60-42f9-88dc-579f1cbac4cc)
![image](https://github.com/user-attachments/assets/95b8a3b2-3f8c-4504-859b-e9a0027de7ba)
![image](https://github.com/user-attachments/assets/edb1cd51-e081-42a5-a56c-c7476a6ca94a)

