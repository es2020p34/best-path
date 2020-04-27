FROM maven:3.6.3-slim

#Install git
RUN apt-get update
RUN apt-get install -y git
     
# Clone from code repository
RUN mkdir /home/bestpath
RUN ls -la 
RUN cd /home/bestpath
RUN ls -la
#RUN git clone https://github.com/es2020p34/best-path.git .

#Set working directory
WORKDIR /home/bestpath/



# Install requirements and compile code
RUN mvn clean package spring-boot:repackage

#Run code
RUN java -jar target/bestpath-0.0.1.jar