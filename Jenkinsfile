pipeline {
    agent any

    tools {
        jdk 'java-21'
        docker {
              image 'docker:20.10.24-dind'
              args '--privileged'  // Needed for DinD to work in container
            }

    }

    environment {
            IMAGE_NAME = 'my-java-app:latest'
        }

    stages {
        stage('Docker Build') {
            steps {
                sh 'docker version'
            }
        }

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

        stage('Docker Build') {
                    steps {
                        script {
                            sh 'docker build -t $IMAGE_NAME .'
                        }
                    }
                }
    }
}
