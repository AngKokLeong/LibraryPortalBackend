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
            stage ('Validate Project') {
                steps {
                    script {
                        mavenHome = tool 'Maven-Installation'
                    }
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

            /*
            stage ('Pre-Integration Test'){
                parallel {

                    stage ('Quality Test'){
                        agent any
                        steps {
                            echo 'On Quality Test'
                            sh 'npx eslint ./src'
                        }
                    }
                    stage ('Unit Test'){
                        agent any
                        steps {
                            echo 'On Unit Test'
                            //run the unit test
                        }
                    }
                    stage ('Security Test'){
                        agent any
                        steps {
                            echo 'On Security Test'
                        }
                    }
                }
                
            }*/

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