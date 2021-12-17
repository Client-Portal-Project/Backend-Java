pipeline {
    agent any

    options {
        buildDiscarder(logRotator(daysToKeepStr: '90', numToKeepStr: '1'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Unit Test') {
            steps {
                sh 'mvn test'
                discordSend description: ":memo: Successfully Passed Tests for ${env.JOB_NAME}", result: currentBuild.currentResult, webhookURL: env.WEBHO_JA
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -DskipTests package'
                discordSend description: ":package: Packaged .jar for ${env.JOB_NAME}", result: currentBuild.currentResult, webhookURL: env.WEBHO_JA
            }
        }

        stage('Static Analysis') {
            steps {
                sh 'echo todo'
            }
        }

        stage('Deployment Preparation') {
            steps {
                sh 'echo todo'
            }
        }

        stage('Deploy') {
            steps {
                sh 'echo todo'
            }
        }
    }
}