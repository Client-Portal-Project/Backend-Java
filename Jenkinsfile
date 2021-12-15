pipeline {
    agent any

    options {
        buildDiscarder(logRotator(daysToKeepStr: '90', numToKeepStr: '1'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn package'
                discordSend description: ":package: *Packaged ${env.JOB_NAME}*", result: currentBuild.currentResult, webhookURL: env.WEBHO_JA
            }
        }
    }
}