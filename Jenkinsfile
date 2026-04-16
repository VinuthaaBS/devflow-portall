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
                // This creates the .jar file
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Run') {
            steps {
                // Stop and remove old container if it exists
                sh 'docker stop devflow-container || true'
                sh 'docker rm devflow-container || true'
                
                // Build the image and run it on port 8081 (to avoid Jenkins port conflict)
                sh 'docker build -t devflow-image .'
                sh 'docker run -d -p 8081:8080 --name devflow-container devflow-image'
            }
        }
    }

    post {
        success {
            echo "------------------------------------------------------------"
            echo "DEPLOYMENT SUCCESSFUL"
            echo "ACCESS YOUR WEBSITE AT: http://localhost:8081"
            echo "------------------------------------------------------------"
        }
    }
}