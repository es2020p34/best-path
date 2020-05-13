FROM maven:3.6.3-slim

# Install git
RUN apt-get update
RUN apt-get install -y git
     
# Clone from code repository
RUN mkdir /home/bestpath
RUN cd /home/bestpath && git clone https://github.com/es2020p34/best-path.git

# Set working directory
WORKDIR /home/bestpath/best-path

# Install requirements and compile code
RUN mvn clean package spring-boot:repackage -DskipTests

# Run code
ENTRYPOINT ["java","-jar","target/bestpath-0.0.1.jar"]