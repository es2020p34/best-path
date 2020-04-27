FROM maven:3.6.3-slim

#Install git
RUN apt-get update
RUN apt-get install -y git
     
# Clone from code repository

RUN mkdir bestpath
RUN cd /bestpath && git clone https://github.com/es2020p34/best-path.git && cd /bestpath/best-path
RUN ls -la

#Set working directory
WORKDIR /bestpath/best-path

# Install requirements and compile code
RUN mvn clean package spring-boot:repackage

#Run code
RUN java -jar target/bestpath-0.0.1.jar