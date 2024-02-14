# Base image con Tomcat y JDK 11
FROM tomcat:9.0-jdk11-openjdk  

# Copia tu archivo WAR al directorio de webapps
COPY target/capitalsys-web.war /usr/local/tomcat/webapps/  
COPY server.xml /usr/local/tomcat/conf/

# Expón el puerto 8080 para acceso externo
EXPOSE 8090  

# Ejecuta Tomcat
CMD ["catalina.sh", "run"]  
