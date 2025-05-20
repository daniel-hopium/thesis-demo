pipeline {
    agent any

    tools {
        jdk 'java-21'
    }

    environment {
        IMAGE_NAME = 'my-thesis-app:latest'
        DOCKER_HUB_CREDENTIALS_ID = 'dockerhub-credentials'
        DOCKER_HUB_REPO = "if22b041/thesis-demo"
    }

    stages {
        stage('Checkout App Code') {
            steps {
                git url: 'https://github.com/daniel-hopium/thesis-demo.git'
            }
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

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("$DOCKER_HUB_REPO:latest")
                }
            }
        }

        stage('Push Image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', "$DOCKER_HUB_CREDENTIALS_ID") {
                        dockerImage.push('latest')
                    }
                }
            }
        }
    }
}
