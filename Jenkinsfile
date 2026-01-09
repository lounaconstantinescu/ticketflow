pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    tools {
        jdk 'jdk-21'
        nodejs 'node-20'
        maven 'maven'
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend - Test') {
            steps {
                dir('backend') {
                    sh 'mvn -B -ntp test'
                }
            }
        }

        stage('Backend - Package') {
            steps {
                dir('backend') {
                    sh 'mvn -B -ntp -DskipTests package'
                }
            }
        }

        stage('Frontend - Install & Test') {
            steps {
                dir('frontend') {
                    sh 'npm ci'
                    sh 'npm test'
                }
            }
        }

        stage('Frontend - Build') {
            steps {
                dir('frontend') {
                    sh 'npm run build'
                }
            }
        }

        stage('Archive artifacts') {
            steps {
                archiveArtifacts artifacts: 'backend/target/*.jar, frontend/dist/**', fingerprint: true
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '.m2/**', allowEmptyArchive: true
        }
    }
}