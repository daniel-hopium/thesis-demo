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

        stage('Checkout Helm Chart Repo') {
            steps {
                dir('helm-chart') {
                    git url: 'https://github.com/daniel-hopium/thesis-demo-helm.git'
                }
            }
        }

        stage('Update Image Tag in Helm Values') {
            steps {
                dir('helm-chart') {
                    sh """
                        sed -i 's|repository:.*|repository: ${IMAGE_NAME}|' values.yaml
                        sed -i 's|tag:.*|tag: ${TAG}|' values.yaml
                    """
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
