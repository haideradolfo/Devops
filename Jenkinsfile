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
                    sh 'docker --version'

                    // USING DOCKER PIPELINE PLUGIN CORRECTLY
                    docker.withRegistry("https://${env.DOCKER_REGISTRY}", "${env.DOCKERHUB_CREDS}") {
                        docker.build("${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}")

                        sh """
                            docker tag ${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG} \\
                                   ${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:latest
                        """

                        docker.image("${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}").push()
                        docker.image("${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:latest").push()
                    }

                    sh """
                        echo "Images Docker créées :"
                        docker images | grep ${env.DOCKER_USERNAME} || true

                        echo " "
                        echo "Tester l'image :"
                        docker run --rm ${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG} java -version 2>/dev/null || echo "Test non disponible"
                    """
                }
            }
        }

        stage('Déployer sur cluster') {
            steps {
                echo '🚀 ÉTAPE 4: Déploiement sur cluster (simulation)'
                script {
                    sh """
                        echo "=== SIMULATION DE DÉPLOIEMENT ==="
                        echo "1. Vérification de l'infrastructure..."
                        echo "2. Application: ${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}"
                        echo "3. Environnement: Développement"
                        echo "4. Port: 8080"
                        echo "5. Réplicas: 1"
                        echo " "

                        echo "Commande de déploiement :"
                        echo "kubectl apply -f deployment.yaml"
                        echo "kubectl set image deployment/tp-cafe tp-cafe=${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}"
                        echo "kubectl rollout status deployment/tp-cafe"

                        echo " "
                        echo "Vérification :"
                        echo "kubectl get pods"
                        echo "kubectl get services"
                        echo "curl http://tp-cafe-service:8080/health"
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
---
apiVersion: v1
kind: Service
metadata:
  name: tp-cafe-service
spec:
  selector:
    app: tp-cafe
  ports:
  - port: 80
    targetPort: 8080
  type: LoadBalancer
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