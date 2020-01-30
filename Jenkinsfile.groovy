node {
   stage('') {
      //
   }
   stage('Run Postman collection with Taurus newman executor') {
        bat 'docker run --rm ' +
                '-v JMeter/Taurus/newman_executor/bzt-config:/bzt-configs ' +
                '-v JMeter/Taurus/newman_executor/artifacts:/tmp/artifacts ' +
                'blazemeter/taurus ' +
                'JMeter/Taurus/newman_executor/bzt-config/run_postman_collection.yml'
   }

   stage('Run JMeter test plan with Taurus default JMeter executor') {

   }
}