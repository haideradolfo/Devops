pipeline {
    agent any
    
    environment {
        // Variables d'environnement
        DOCKER_REGISTRY = 'docker.io'
        DOCKER_USERNAME = 'haiderschenato02'  // Votre username Docker Hub
        IMAGE_NAME = 'tp-cafe-app'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        
        // Credentials IDs (à créer dans Jenkins)
        DOCKERHUB_CREDS = 'docker-hub-credentials'
        GIT_CREDS = 'github-credentials'
    }
    
    stages {
        // ÉTAPE 1: Checkout code
        stage('Checkout Code') {
            steps {
                echo '📥 ÉTAPE 1: Checkout du code source depuis Git'
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/haideradolfo/TPCafe_Schenato_Haider.git',
                        credentialsId: "${env.GIT_CREDS}"
                    ]]
                ])
                
                // Afficher les fichiers checkoutés
                sh '''
                    echo "Contenu du répertoire :"
                    ls -la
                    echo " "
                    echo "Structure du projet :"
                    find . -type f -name "*.java" | head -10
                '''
            }
        }
        
        // ÉTAPE 2: Build avec Maven
        stage('Build avec Maven') {
            steps {
                echo '🔨 ÉTAPE 2: Construction du projet avec Maven'
                sh '''
                    echo "Version de Maven :"
                    mvn --version || echo "Maven non installé"
                    
                    echo " "
                    echo "Compilation du projet..."
                    mvn clean compile
                    
                    echo " "
                    echo "Exécution des tests..."
                    mvn test
                    
                    echo " "
                    echo "Packaging..."
                    mvn package -DskipTests
                    
                    echo " "
                    echo "Fichiers générés :"
                    ls -la target/
                '''
                
                // Archiver le JAR
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        
        // ÉTAPE 3: Build & Push Docker Image
        stage('Build & Push Docker Image') {
            steps {
                echo '🐳 ÉTAPE 3: Construction et push de l\'image Docker'
                script {
                    // Vérifier Docker
                    sh 'docker --version'
                    
                    // Se connecter à Docker Hub
                    withCredentials([usernamePassword(
                        credentialsId: "${env.DOCKERHUB_CREDS}",
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh """
                            docker login -u '$DOCKER_USER' -p '$DOCKER_PASS'
                        """
                    }
                    
                    // Construire l'image Docker
                    docker.build("${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}")
                    
                    // Tagger aussi comme latest
                    sh """
                        docker tag ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG} \
                               ${DOCKER_USERNAME}/${IMAGE_NAME}:latest
                    """
                    
                    // Pousser vers Docker Hub
                    docker.withRegistry("https://${env.DOCKER_REGISTRY}", "${env.DOCKERHUB_CREDS}") {
                        docker.image("${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:${env.DOCKER_TAG}").push()
                        docker.image("${env.DOCKER_USERNAME}/${env.IMAGE_NAME}:latest").push()
                    }
                    
                    // Afficher les images
                    sh '''
                        echo "Images Docker créées :"
                        docker images | grep ${DOCKER_USERNAME} || true
                        
                        echo " "
                        echo "Tester l'image :"
                        docker run --rm ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG} java -version 2>/dev/null || echo "Test non disponible"
                    '''
                }
            }
        }
        
        // ÉTAPE 4: Déployer sur cluster
        stage('Déployer sur cluster') {
            steps {
                echo '🚀 ÉTAPE 4: Déploiement sur cluster (simulation)'
                script {
                    // Simulation de déploiement
                    sh '''
                        echo "=== SIMULATION DE DÉPLOIEMENT ==="
                        echo "1. Vérification de l'infrastructure..."
                        echo "2. Application: ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG}"
                        echo "3. Environnement: Développement"
                        echo "4. Port: 8080"
                        echo "5. Réplicas: 1"
                        echo " "
                        
                        # Commande de déploiement simulée
                        echo "Commande de déploiement :"
                        echo "kubectl apply -f deployment.yaml"
                        echo "kubectl set image deployment/tp-cafe tp-cafe=${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG}"
                        echo "kubectl rollout status deployment/tp-cafe"
                        
                        # Vérification simulée
                        echo " "
                        echo "Vérification :"
                        echo "kubectl get pods"
                        echo "kubectl get services"
                        echo "curl http://tp-cafe-service:8080/health"
                    '''
                    
                    // Créer un fichier de déploiement Kubernetes (exemple)
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
                    
                    // Afficher le fichier créé
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
            sh '''
                echo "Durée totale: ${currentBuild.durationString}"
                echo "Build #${BUILD_NUMBER}"
                echo "Statut: ${currentBuild.currentResult}"
                echo " "
                echo "Image Docker créée: ${DOCKER_USERNAME}/${IMAGE_NAME}:${DOCKER_TAG}"
                echo "URL Docker Hub: https://hub.docker.com/r/${DOCKER_USERNAME}/${IMAGE_NAME}"
            '''
        }
        
        success {
            echo '✅ PIPELINE RÉUSSI !'
            // Ici vous pouvez ajouter des notifications
            // emailext, slackSend, etc.
        }
        
        failure {
            echo '❌ PIPELINE ÉCHOUÉ !'
        }
        
        cleanup {
            // Nettoyage
            sh '''
                echo "Nettoyage des ressources temporaires..."
                docker container prune -f 2>/dev/null || true
                docker image prune -f 2>/dev/null || true
                rm -f deployment.yaml 2>/dev/null || true
            '''
        }
    }
}