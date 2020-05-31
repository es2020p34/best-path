pipeline {
  agent none 

    stages {

      stage('build and test') {
        agent { docker 'maven:3-alpine' }
        stages {
          stage('build') {
            steps {
              sh 'mvn clean compile'
            }
          }
          stage('testing') {
            steps {
              sh 'mvn test'
            }
          }
        }
      }

      stage('register image') {
        agent any
        when { branch 'master' }
        steps{
          sh '''
            docker build -t esp34-bestpath:${BUILD_NUMBER} --file Dockerfile .
            docker tag esp34-bestpath:${BUILD_NUMBER} 192.168.160.99:5000/esp34-bestpath:${BUILD_NUMBER}
            docker push 192.168.160.99:5000/esp34-bestpath:${BUILD_NUMBER}
          '''
        }
      }

      stage('deploy on runtime'){
        agent any
        when { branch 'master' }
        steps{
          sshagent(credentials: ['esp34-ssh-deploy']) {
            sh '''
              ssh -o StrictHostKeyChecking=no -l esp34 192.168.160.103 "
              docker pull 192.168.160.99:5000/esp34-bestpath:${BUILD_NUMBER}
              docker stop esp34-bestpath && docker rm esp34-bestpath || echo No container up. Continue
              docker run -p 3480:80 -d --memory="256m" --name=esp34-bestpath 192.168.160.99:5000/esp34-bestpath:${BUILD_NUMBER}"
            '''
          }
        }
      }
    }
}
