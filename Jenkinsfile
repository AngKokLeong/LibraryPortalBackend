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
            

            stage ('Build') {
                

                steps {
                    script {
                        mavenHome = tool 'Maven-Installation'
                    }
                    sh "${mavenHome}/bin/mvn clean install"

                    //run the npm build
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

          
            stage ('Static Code Analysis'){
                        steps {
                            script {
                            // requires SonarQube Scanner 2.8+
                                scannerHome = tool 'SonarQube-Scanner'
                            }
                            withSonarQubeEnv('Sonarqube-Library-Portal-Backend') {
                                sh "${scannerHome}/bin/sonar-scanner"
                            }
                        }
            }
            stage ('Quality Gate'){
                    
                        steps {
                            timeout(time: 5, unit: 'MINUTES') {
                                waitForQualityGate abortPipeline: true
                            }
                        }
            }


            
            stage ('Deploy Develop'){
                
                when {
                    anyOf {
                        branch '${DEVELOP}'
                    }
                }

                steps {
                    echo "On Deploy Develop"
                }
            }

            stage ('Deploy Pre-Production'){
                
                when {
                    anyOf {
                        branch '${PREPROD}'
                    }
                }

                steps {
                    echo "On Deploy Pre Production"
                }
            }

            stage ('Deploy Production') {
                

                when {
                    anyOf {
                        branch '${PRODUCTION}'
                    }
                }

                steps {
                    echo "On Deploy Production"
                }
            }

        }
}