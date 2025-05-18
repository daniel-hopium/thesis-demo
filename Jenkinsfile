pipeline {
    agent any

    tools {
        jdk 'java-21'
    }

    stages {
        stage('Checkout') {
            steps {
              git url: 'https://github.com/daniel-hopium/thesis-demo.git'            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }

            post {
                always {
                    junit '**/build/test-results/test/*.xml'
                }
            }
        }
    }
}
