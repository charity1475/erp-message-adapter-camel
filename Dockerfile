# Create the runtime image
FROM alpine/java:21-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY target/erp-message-adapter-*.jar /app/erp-message-adapter-*.jar

# Set memory and heap JVM flags
ENV JAVA_OPTS="-Xms512m -Xmx4096m -XX:MaxRAMPercentage=75.0"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/erp-message-adapter-*.jar"]