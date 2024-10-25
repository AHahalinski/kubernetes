docker build -t hahalinskidev211020/k8s-users-app:2.0.0 ./
docker login
docker push hahalinskidev211020/k8s-users-app:2.0.0
#docker run -ti -e EUREKA_URI='http://localhost:8000/eureka' -e DB_URL=':jdbc:postgresql://users_db:5432/users' -e DB_USERNAME='admin' -e DB_PASSWORD='root' -p 8001:8080 hahalinskidev211020/k8s-users-app:1.0.0