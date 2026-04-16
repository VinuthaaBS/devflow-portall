pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Artifact') {
            steps {
                // Use 'bat' instead of 'sh' for Windows
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Run') {
            steps {
                // Use 'bat' and Windows-style container management
                bat 'docker stop devflow-container || exit 0'
                bat 'docker rm devflow-container || exit 0'
                
                bat 'docker build -t devflow-image .'
                bat 'docker run -d -p 8081:8080 --name devflow-container devflow-image'
            }
        }
    }

    post {
        success {
            echo "=========================================================="
            echo "DEPLOYMENT COMPLETE"
            echo "VIEW PORTAL HERE: http://localhost:8081"
            echo "=========================================================="
        }
    }
}