pipeline {
  agent none 

  stages {
    stage('build and unit test') {
      agent { docker 'maven:3-alpine' }
      stages {
        stage('build') {
          steps {
            sh 'mvn clean compile'
          }
        }
        stage('unit testing') {
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
          docker build -t esp34-bestpath --file Dockerfile .
          docker tag esp34-bestpath 192.168.160.99:5000/esp34-bestpath
          docker push 192.168.160.99:5000/esp34-bestpath
          '''
        }
      }
  }
}
