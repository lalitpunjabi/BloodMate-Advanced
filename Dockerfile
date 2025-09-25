FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu

# Install Maven
RUN apt-get update \
    && apt-get install -y --no-install-recommends maven \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy pom and resolve dependencies
COPY pom.xml ./
RUN mvn -B -e -DskipTests dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn -B -e clean package -DskipTests

# Create a non-root user
RUN groupadd -r bloodmate && useradd -r -g bloodmate bloodmate

# Change ownership of the app directory
RUN chown -R bloodmate:bloodmate /app

# Switch to non-root user
USER bloodmate

# Expose port 8080
EXPOSE 8080

# Set JVM options for production
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar target/bloodmate-1.0-SNAPSHOT.jar"]
