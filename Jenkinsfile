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

            stage ('Pre-Integration Test'){
                parallel {
                    stage ('Validate Project') {
                        steps {
                            script {
                                mavenHome = tool 'Maven-Installation'
                            }
                            sh "echo ${mavenHome}"
                            sh "${mavenHome}/bin/mvn clean validate -Dspring.profiles.active=dev"
                        }
                    }
                    stage ('Test Project') {
                        steps {
                            script {
                                mavenHome = tool 'Maven-Installation'
                            }
                            sh "${mavenHome}/bin/mvn clean test -Dspring.profiles.active=dev"
                        }
                    }
                }
            }
            

            stage ('Build Project') {
                steps {
                    script {
                        mavenHome = tool 'Maven-Installation'
                    }
                    sh "${mavenHome}/bin/mvn clean install -Dspring.profiles.active=dev"
                }
            }

            stage ('Build PRE-PROD'){
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

            

            stage ('SonarQube Analysis'){
                steps {
                    script {
                        scannerHome = tool 'SonarQube-Scanner'
                    }
                    withSonarQubeEnv('Sonarqube-Library-Portal-Backend') {
                        sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
            }

            stage ('SonarQube Quality Gate'){
                    
                steps {
                    timeout(time: 5, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                }
            }

            stage ('Deploy to SonarType'){
                
                steps {
                    echo "On Deploy Develop"
                }
            }
        }
}