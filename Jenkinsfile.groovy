pipeline {
    agent any
    stages{
        stage('Run Postman collection with Taurus newman executor') {
            steps{
                sh 'sudo docker run --privileged=true --rm  ' +
                        '-v JMeter/Taurus/newman_executor/bzt-config:/bzt-configs ' +
                        '-v JMeter/Taurus/newman_executor/artefacts:/tmp/artifacts ' +
                        'blazemeter/taurus ' +
                        'JMeter/Taurus/newman_executor/bzt-config/run_postman_collection.yml'
            }
        }

    }

}