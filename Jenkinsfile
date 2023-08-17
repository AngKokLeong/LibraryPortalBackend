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
            stage ('Checkout') {
                steps {
                    checkout scm
                }
            }

            /*stage ('Pre-Integration Test'){
                parallel {

                    stage ('Quality Test'){
                        agent any
                        steps {
                            echo 'On Quality Test'
                            //https://eslint.org/docs/latest/use/command-line-interface#--max-warnings
                            //purpose: to get the return value
                            //https://www.jenkins.io/doc/pipeline/steps/workflow-durable-task-step/#sh-shell-script
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


            stage ('Build') {
                

                steps {
                    script {
                        mavenHome = tool 'Maven-Installation'
                    }
                    sh "echo ${mavenHome}"

                    //run the npm build
                }
            }
            stage ('Push') {
           

                when {
                    anyOf {
                        branch '${PRODUCTION}'
                        branch '${PREPROD}'
                        branch '${DEVELOP}'
                    }
                }

                steps {
                    echo "On Push"
                    //push to sonatype
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