pipeline {
    agent any

    environment {
        IMAGE_NAME = "my-thesis-app"
        TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/daniel-hopium/thesis-demo.git'
            }
        }
        stage('Build & Test') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean test bootJar'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${TAG} ."
            }
        }
        stage('Push Image to Minikube Docker') {
            steps {
                sh 'eval $(minikube -p cluster-push docker-env)'
                sh "docker build -t ${IMAGE_NAME}:${TAG} ."
            }
        }
        stage('Deploy Helm Chart') {
            steps {
                sh "helm upgrade --install ${IMAGE_NAME} ./helm-chart --set image.repository=${IMAGE_NAME} --set image.tag=${TAG}"
            }
        }
    }
}
