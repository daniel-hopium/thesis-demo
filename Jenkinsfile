pipeline {
    agent any

    environment {
        IMAGE_NAME = 'if22b041/thesis-demo-app'
        TAG = 'latest'
        DOCKERHUB_CREDENTIALS = 'docker-registry-credentials'
    }

    stages {
        stage('Checkout App Code') {
            steps {
                git url: 'https://github.com/daniel-hopium/thesis-demo.git'
            }
        }

        stage('Build and Push Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:${TAG} ."

                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin'
                    sh "docker push ${IMAGE_NAME}:${TAG}"
                }
            }
        }

        stage('Deploy to Kubernetes via Helm') {
            steps {
                dir('helm-chart') {
                    sh "helm upgrade --install ${IMAGE_NAME} . --namespace default"
                }
            }
        }
    }
}
