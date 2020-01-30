pipeline {
    agent any
    stages{
        stage('Run Postman collection with Taurus newman executor') {
            step{
                bat 'docker run --rm ' +
                        '-v JMeter/Taurus/newman_executor/bzt-config:/bzt-configs ' +
                        '-v JMeter/Taurus/newman_executor/artifacts:/tmp/artifacts ' +
                        'blazemeter/taurus ' +
                        'JMeter/Taurus/newman_executor/bzt-config/run_postman_collection.yml'
            }
        }

    }

}