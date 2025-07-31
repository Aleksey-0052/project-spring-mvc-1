#FROM tomcat:10.1.31
FROM tomcat

# Копируем наше приложение в директорию webapps Tomcat
COPY ./target/root.war /usr/local/tomcat/webapps/

# http://localhost:8080/root
# Для доступа к странице необходимо ввести этот адрес, root - имя war-файла