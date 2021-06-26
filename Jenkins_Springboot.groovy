pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "Maven-3.6.0"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/shahnawaz-khan-pathan/Spring-Boot-API-master'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
         stage ("build1") {		//an arbitrary stage name
            steps {
                sh 'rsync -r -e  "ssh -i /home/ubuntu/key.pem" /var/lib/jenkins/workspace/project-J-java/target/SpringBootDemoProject-0.0.1-SNAPSHOT.jar ubuntu@10.0.1.239:/home/ubuntu/'	//this is where we specify which job to invoke.
                sh 'rsync -r -e  "ssh -i /home/ubuntu/key.pem" /var/lib/jenkins/workspace/javaP.service ubuntu@10.0.1.239:/home/ubuntu/'         
                sh 'rsync -r -e  "ssh -i /home/ubuntu/key.pem" /var/lib/jenkins/workspace/service.sh ubuntu@10.0.1.239:/home/ubuntu/'
                
                
                
            }


        }
    }
}
