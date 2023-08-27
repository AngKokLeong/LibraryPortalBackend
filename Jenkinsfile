pipeline {
        parameters {
            // The space ID that we will be working with. The default space is typically Spaces-1.
            string(defaultValue: 'Spaces-1', description: '', name: 'SpaceId', trim: true)
            // The Octopus project we will be deploying.
            string(defaultValue: 'RandomQuotes', description: '', name: 'ProjectName', trim: true)
            // The environment we will be deploying to.
            string(defaultValue: 'Dev', description: '', name: 'EnvironmentName', trim: true)
            // The name of the Octopus instance in Jenkins that we will be working with. This is set in:
            // Manage Jenkins -> Configure System -> Octopus Deploy Plugin
            string(defaultValue: 'Octopus', description: '', name: 'ServerId', trim: true)
        }

        agent any

        options {
            parallelsAlwaysFailFast()
        }

        environment {
            PRODUCTION = 'master'
            PREPROD = 'preprod'
            DEVELOP = 'develop'

            NEXUS_VERSION = "nexus3"
            NEXUS_PROTOCOL = "http"
            NEXUS_URL = "192.168.18.13:32004"
            NEXUS_REPOSITORY = "maven-releases"
            NEXUS_CREDENTIAL_ID = "sonartype-credential"
        }

        stages {

            

            stage ('A: Pre-Integration Test'){
                parallel {
                    stage ('A1: Validate Project') {
                        steps {
                            script {
                                mavenHome = tool 'Maven-Installation'
                            }
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

            stage ('F: Package Project') {
                steps {
                    sh(script: "${mavenHome}/bin/mvn versions:set -DnewVersion=1.0.${BUILD_NUMBER}", returnStdout: true)
                    // Package the code
                    sh(script: "${mavenHome}/bin/mvn package -Dspring.profiles.active=dev", returnStdout: true)
                }
            }

            stage ('G: Push Artifacts to Nexus'){
                //use an agent 
                steps {
                    echo "On Deploy Develop"

                    script {
                        pom = readMavenPom file: "pom.xml";
                        filesByGlob = findFiles(glob: "target/rest-api-1.0.${BUILD_NUMBER}.${pom.packaging}");
                        echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                        artifactPath = filesByGlob[0].path;
                        artifactExists = fileExists artifactPath;
                        if(artifactExists) {
                            echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: NEXUS_URL,
                                groupId: pom.groupId,
                                version: pom.version,
                                repository: NEXUS_REPOSITORY,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: artifactPath,
                                    type: pom.packaging],
                                    [artifactId: pom.artifactId,
                                    classifier: '',
                                    file: "pom.xml",
                                    type: "pom"]
                                ]
                            );
                        } else {
                            error "*** File: ${artifactPath}, could not be found";
                        }
                    }
                    //octopusPack additionalArgs: '', includePaths: "${env.WORKSPACE}/target/rest-api-1.0.${BUILD_NUMBER}.war", outputPath: "${env.WORKSPACE}", overwriteExisting: false, packageFormat: 'zip', packageId: 'library-portal-backend', packageVersion: "1.0.${BUILD_NUMBER}", sourcePath: '', toolId: 'octopus-deploy-cli', verboseLogging: false
                    //octopusPushPackage additionalArgs: '', overwriteMode: 'FailIfExists', packagePaths: "${env.WORKSPACE}/target/randomquotes.1.0.${BUILD_NUMBER}.jar", serverId: "${ServerId}", spaceId: "${SpaceId}", toolId: 'Default'
                    //octopusPushBuildInformation additionalArgs: '', commentParser: 'GitHub', overwriteMode: 'FailIfExists', packageId: 'randomquotes', packageVersion: "1.0.${BUILD_NUMBER}", serverId: "${ServerId}", spaceId: "${SpaceId}", toolId: 'Default', verboseLogging: false, gitUrl: "${GIT_URL}", gitCommit: "${GIT_COMMIT}"
                    //octopusCreateRelease additionalArgs: '', cancelOnTimeout: false, channel: '', defaultPackageVersion: '', deployThisRelease: false, deploymentTimeout: '', environment: "${EnvironmentName}", jenkinsUrlLinkback: false, project: "${ProjectName}", releaseNotes: false, releaseNotesFile: '', releaseVersion: "1.0.${BUILD_NUMBER}", serverId: "${ServerId}", spaceId: "${SpaceId}", tenant: '', tenantTag: '', toolId: 'Default', verboseLogging: false, waitForDeployment: false
                    //octopusDeployRelease cancelOnTimeout: false, deploymentTimeout: '', environment: "${EnvironmentName}", project: "${ProjectName}", releaseVersion: "1.0.${BUILD_NUMBER}", serverId: "${ServerId}", spaceId: "${SpaceId}", tenant: '', tenantTag: '', toolId: 'Default', variables: '', verboseLogging: false, waitForDeployment: true
                }
            }
        }
}