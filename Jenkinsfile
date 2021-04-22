properties([
    parameters([
        [$class: 'ChoiceParameter',
            choiceType: 'PT_SINGLE_SELECT',
            description: 'Select the Datacenter Name from the Dropdown List',
            filterLength: 1,
            filterable: false,
            name: 'Datacenter',
            script: [
                $class: 'GroovyScript',
                fallbackScript: [
                    classpath: [],
                    sandbox: false,
                    script:
                        'return[\'Could not get Datacenter\']'
                ],
                script: [
                    classpath: [],
                    sandbox: false,
                    script:
                        '''
                            def data =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
                            def datacenters = ["Select:selected"]
                            data.eachLine { line, number ->
                                if (number == 0) {
                                } else {
                                    def lineSplit = line.split(',')
                                    datacenters.add(lineSplit[0])
                                }
                            }
                            datacenters.unique { a, b -> a <=> b }
                            return datacenters
                        '''
                ]
            ]
        ],
        [$class: 'CascadeChoiceParameter',
            choiceType: 'PT_SINGLE_SELECT',
            description: 'Select the ClusterID from the Dropdown List',
            filterLength: 1,
            filterable: false,
            name: 'ClusterID',
            referencedParameters: 'Datacenter',
            script: [
                $class: 'GroovyScript',
                fallbackScript: [
                    classpath: [],
                    sandbox: false,
                    script:
                        'return[\'Could not get Datacenter from Datacenter Param\']'
                ],
                script: [
                    classpath: [],
                    sandbox: false,
                    script:
                        '''
                            def data =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
                            def ClusterID = ["Select:selected"]
                            data.eachLine { line, number ->
                                if (number == 0) {
                                } else {
                                    def lineSplit = line.split(',')
                                    if ( lineSplit[0] == Datacenter ){
                                        ClusterID.add(lineSplit[1])
                                    }
                                }
                            }
                            return  ClusterID
                        '''
                ]
            ]
        ]
    ])
])

pipeline {
  environment {
         vari = ""
  }
  agent any
  stages {
      stage ("Example") {
        steps {
         script{
          echo 'Hello'
          echo "${params.Env}"
          echo "${params.Server}"
          if (params.Server.equals("Could not get Environment from Env Param")) {
              echo "Must be the first build after Pipeline deployment.  Aborting the build"
              currentBuild.result = 'ABORTED'
              return
          }
          echo "Crossed param validation"
        } }
      }
  }
}
