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

        stage('Install Kubectl') {
            steps {
                sh '''
                curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
                chmod +x kubectl
                mv kubectl /usr/local/bin/kubectl
                '''
            }
        }

        stage('Install Helm') {
            steps {
                sh '''
                curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
                '''
            }
        }

        stage('Deploy to Kubernetes via Helm') {
            steps {
                script {
                    withEnv(["KUBECONFIG="]) {
                        sh '''
                        helm upgrade --install my-thesis-app ./helm \
                            --kube-apiserver http://host.docker.internal:8001
                        '''
                    }
                }
            }
        }
    }
}
