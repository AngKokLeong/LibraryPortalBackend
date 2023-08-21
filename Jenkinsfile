pipeline {
        agent any

        options {
            parallelsAlwaysFailFast()
        }

        environment {
            PRODUCTION = 'master'
            PREPROD = 'preprod'
            DEVELOP = 'develop'
           
        }

        stages {

            stage ('A: Pre-Integration Test'){
                parallel {
                    stage ('A1: Validate Project') {
                        steps {
                            script {
                                mavenHome = tool 'Maven-Installation'
                            }
                            sh "echo ${mavenHome}"
                            sh "${mavenHome}/bin/mvn clean validate -Dspring.profiles.active=dev"
                        }
                    }
                    stage ('A2: Test Project') {
                        steps {
                            script {
                                mavenHome = tool 'Maven-Installation'
                            }
                            sh "${mavenHome}/bin/mvn clean test -Dspring.profiles.active=dev"
                        }
                    }
                }
            }
            

            stage ('B: Build Project') {
                steps {
                    script {
                        mavenHome = tool 'Maven-Installation'
                    }
                    sh "${mavenHome}/bin/mvn clean install -Dspring.profiles.active=dev"
                }
            }

            stage ('C: Build PRE-PROD'){
                when {
                    anyOf {
                        branch '${PREPROD}'
                    }
                }    

                steps {
                    script {
                        mavenHome = tool 'Maven-Installation'
                    }
                    sh "${mavenHome}/bin/mvn clean install -Dspring.profiles.active=preprod"
                }
            }

            

            stage ('D: SonarQube Analysis'){
                steps {
                    script {
                        scannerHome = tool 'SonarQube-Scanner'
                    }
                    withSonarQubeEnv('Sonarqube-Library-Portal-Backend') {
                        sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
            }

            stage ('E: SonarQube Quality Gate'){
                    
                steps {
                    timeout(time: 5, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }

            stage ('F: Deploy to SonarType'){
                
                steps {
                    echo "On Deploy Develop"
                }
            }
        }
}