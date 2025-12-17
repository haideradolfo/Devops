pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_USERNAME = 'haiderschenato02'
        IMAGE_NAME = 'tp-cafe-app'
        DOCKER_TAG = "${env.BUILD_NUMBER}"

        DOCKERHUB_CREDS = 'docker-hub-credentials'
        GIT_CREDS = 'github-credentials'
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo '📥 ÉTAPE 1: Checkout du code source depuis Git'
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/haideradolfo/Devops.git',
                        credentialsId: "${env.GIT_CREDS}"
                    ]]
                ])

                sh '''
                    echo "Contenu du répertoire :"
                    ls -la
                    echo " "
                    echo "Structure du projet :"
                    find . -type f -name "*.java" | head -10
                '''
            }
        }

        stage('Build avec Maven') {
            steps {
                echo '🔨 ÉTAPE 2: Construction du projet avec Maven'
                sh '''
                    echo "Version de Maven :"
                    mvn --version

                    echo " "
                    echo "Compilation du projet..."
                    mvn clean compile

                    echo " "
                    echo "Packaging..."
                    mvn package -DskipTests

                    echo " "
                    echo "Fichiers générés :"
                    ls -la target/
                '''

                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                echo '🐳 ÉTAPE 3: Construction et push de l\'image Docker'
                script {
                    // Build the image first
                    sh '''
                        echo "Building Docker image..."
                        docker build -t ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG} .
                        docker tag ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG} \\
                                   ${DOCKER_USERNAME}/${IMAGE_NAME}:latest
                        echo "✅ Images built and tagged"
                    '''

                    // Login and push in ONE credential context
                    withCredentials([usernamePassword(
                        credentialsId: "${env.DOCKERHUB_CREDS}",
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh '''
                            echo "Logging into Docker Hub..."
                            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                            echo "Pushing ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG}..."
                            docker push ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG}

                            echo "Pushing ${DOCKER_USERNAME}/${IMAGE_NAME}:latest..."
                            docker push ${DOCKER_USERNAME}/${IMAGE_NAME}:latest

                            echo "✅ Push successful to Docker Hub!"
                        '''
                    }

                    // Verification
                    sh '''
                        echo "=== VERIFICATION ==="
                        echo "Images on local machine:"
                        docker images | grep ${DOCKER_USERNAME} || true

                        echo " "
                        echo "Testing image execution:"
                        docker run --rm ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG} java -version 2>/dev/null || echo "Java version check skipped"
                    '''
                }
            }
        }

        stage('Déployer sur cluster') {
            steps {
                echo '🚀 ÉTAPE 4: Déploiement sur cluster (simulation)'
                script {
                    sh """
                        echo "=== SIMULATION DE DÉPLOIEMENT ==="
                        echo "1. Application: ${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}"
                        echo "2. Déploiement Kubernetes simulé"
                        echo "3. Commandes d'exemple :"
                        echo "   kubectl apply -f deployment.yaml"
                        echo "   kubectl get pods"
                    """

                    writeFile file: 'deployment.yaml', text: """
apiVersion: apps/v1
kind: Deployment
metadata:
  name: tp-cafe
spec:
  replicas: 1
  selector:
    matchLabels:
      app: tp-cafe
  template:
    metadata:
      labels:
        app: tp-cafe
    spec:
      containers:
      - name: tp-cafe
        image: ${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}
        ports:
        - containerPort: 8080
"""

                    sh '''
                        echo " "
                        echo "Fichier de déploiement généré :"
                        cat deployment.yaml
                    '''
                }
            }
        }
    }

    post {
        always {
            echo '📊 ===== RÉSUMÉ DU PIPELINE ====='
            script {
                sh """
                    echo "Durée totale: ${currentBuild.durationString}"
                    echo "Build #${BUILD_NUMBER}"
                    echo "Statut: ${currentBuild.currentResult}"
                    echo " "
                    echo "Image Docker créée: ${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}"
                    echo "URL Docker Hub: https://hub.docker.com/r/${env.DOCKER_USERNAME}/${env.IMAGE_NAME}"
                """
            }
        }

        success {
            echo '✅ PIPELINE RÉUSSI !'
        }

        failure {
            echo '❌ PIPELINE ÉCHOUÉ !'
        }

        cleanup {
            sh '''
                echo "Nettoyage des ressources temporaires..."
                docker container prune -f 2>/dev/null || true
                docker image prune -f 2>/dev/null || true
                rm -f deployment.yaml 2>/dev/null || true
            '''
        }
    }
}