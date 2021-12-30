def CURR = 'Pre-Pipeline'
def CMD = 'No command given'
def ERR = 'NONE'

pipeline {
    agent any

    options {
        buildDiscarder(logRotator(daysToKeepStr: '90', numToKeepStr: '1'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Unit Test') {
            steps {
                script{
                    CURR = "Unit Testing"
                    CMD = 'mvn test > result'
                    sh (script: CMD)
                }
                discordSend description: ":memo: Successfully Passed Tests for ${JOB_NAME}", result: currentBuild.currentResult, webhookURL: WEBHO_JA
            }
        }
        stage('Package') {
            steps {
                script {
                    CURR = 'Packaging'
                    CMD = 'mvn -DskipTests package > result'
                    sh (script: CMD)
                }
                discordSend description: ":package: Packaged .jar for ${JOB_NAME}", result: currentBuild.currentResult, webhookURL: WEBHO_JA
            }
        }
        stage('Static Analysis') {
            environment {
                SCAN = tool 'sonarcloud'
                ORG = 'client-portal-project'
                NAME = 'Backend-Java'
            }
            steps {
                script {
                    CURR = 'Static Analysis'
                    CMD = '''$SCAN/bin/sonar-scanner -Dsonar.organization=$ORG -Dsonar.projectKey=$NAME \
                             -Dsonar.java.binaries=target/classes/com/projectx/ \
                             -Dsonar.java.source=8 -Dsonar.sources=. '''
                }
                withSonarQubeEnv('sonarserve') {
                    sh "${CMD}"
                }
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        ERR = waitForQualityGate abortPipeline: false
                        if (ERR.status != 'OK') {
                            writeFile(file: 'result', text: "https://sonarcloud.io/dashboard?id=Backend-Java")
                            error('Quality Gate Failed')
                        }
                        discordSend description: ":unlock: Passed Static Analysis of ${JOB_NAME}", result: currentBuild.currentResult, webhookURL: WEBHO_JA
                    }
                }
            }
        }
    }
    post {
        failure {
            script {
                CMD = CMD.split(' > ')[0].trim()
                ERR = readFile('result').trim()
            }
            discordSend title: "**:boom: ${env.JOB_NAME} Failure in ${CURR} Stage**",
                        description: "*${CMD}*\n\n${ERR}",
                        footer: "Follow title URL for full console output",
                        link: BUILD_URL + "console", image: 'https://jenkins.io/images/logos/fire/256.png',
                        result: currentBuild.currentResult, webhookURL: WEBHO_JA
        }
    }
}