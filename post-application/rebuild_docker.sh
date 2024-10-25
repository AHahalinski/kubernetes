docker build -t hahalinskidev211020/k8s-posts-app:2.1.0 ./
docker login
docker push hahalinskidev211020/k8s-posts-app:2.1.0
#docker run -ti -e EUREKA_URI='http://localhost:8000/eureka' -e DB_URL=':jdbc:postgresql://posts_db:5432/posts' -e DB_USERNAME='admin' -e DB_PASSWORD='root' -p 8002:8080 hahalinskidev211020/k8s-posts-app:1.0.0