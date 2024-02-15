# Base image con Tomcat y JDK 11
FROM tomcat:9.0-jdk11-openjdk  

# Copia tu archivo WAR al directorio de webapps
COPY target/capitalsys-web.war /usr/local/tomcat/webapps/  
COPY server.xml /usr/local/tomcat/conf/

# Crear la carpeta para almacenar los archivos .log
RUN mkdir -p /app/logs

# Cambiar los permisos de la carpeta
RUN chmod -R 777 /app/logs

# Expón el puerto 8080 para acceso externo
EXPOSE 8090  

# Ejecuta Tomcat
CMD ["catalina.sh", "run"]  
