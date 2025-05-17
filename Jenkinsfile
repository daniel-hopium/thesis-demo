pipeline {
    agent {
            kubernetes {
                label 'docker-agent'
                yamlFile 'jenkins/agent-dind.yaml'
            }
        }
    environment {
        // Docker & Helm Registry Credentials
        DOCKER_IMAGE = 'if22b041/thesis-demo'
        DOCKER_TAG = 'v1.0.0'
        DOCKER_REGISTRY_CREDENTIALS = 'docker-registry-credentials'
        HELM_CHART_NAME = 'my-helm-chart'
    }
    stages {
    stage('Docker Version') {
                steps {
                    container('dind') {
                        sh 'dockerd-entrypoint.sh & sleep 5'
                        sh 'docker version'
                    }
                }
                }
        stage('Checkout Code') {
            steps {
                // Pull source code from GitHub
                git branch: 'master', url: 'https://github.com/daniel-hopium/thesis-demo.git'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_REGISTRY_CREDENTIALS) {
                        // Build and push Docker image
                        def app = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                        app.push()
                    }
                }
            }
        }
        stage('Deploy to Kubernetes using Helm') {
            steps {
                script {
                    sh """
                    # Update Helm chart values with new Docker image
                    helm upgrade --install ${HELM_CHART_NAME} ./helm/${HELM_CHART_NAME} \
                        --set image.repository=${DOCKER_IMAGE} \
                        --set image.tag=${DOCKER_TAG} \
                        --namespace default
                    """
                }
            }
        }
    }
    post {
        success {
            echo 'Deployment completed successfully!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}