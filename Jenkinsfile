class clusterID {
    String Datacenter
    String ClusterID
    String ExternalURL
    String InternalURL
    String rssoURL
    String Subnet

    clusterID(Datacenter, ClusterID, ExternalURL, InternalURL, rssoURL, Subnet) {
        this.Datacenter = Datacenter
        this.ClusterID = ClusterID
        this.ExternalURL = ExternalURL
        this.InternalURL = InternalURL
        this.rssoURL = rssoURL
        this.Subnet = Subnet
    }
}
@NonCPS
def getFile( filePath ){
    File file = new File(filePath)
    return file
}
@NonCPS
def parseData( data ) {
    def vars
    def details
    def datacenters = []
    String Datacenter = ""
    String ClusterID = ""
    String ExternalURL = ""
    String InternalURL = ""
    String rssoURL = ""
    String Subnet = ""
    data.eachLine { line, number ->
        if (number == 1){
            vars = line.split(",")
        } else {
            println "$number $line "
             line.split(",")
             println line.split(",")


            Datacenter = line.split(",")[0]
            ClusterID = line.split(",")[1]
            ExternalURL = line.split(",")[2]
            InternalURL = line.split(",")[3]
            rssoURL = line.split(",")[4]
            Subnet = line.split(",")[5]
            println "$Datacenter, $ClusterID, $ExternalURL, $InternalURL, $rssoURL, $Subnet"
            def obj = new clusterID(Datacenter, ClusterID, ExternalURL, InternalURL, rssoURL, Subnet)
            println "obj = $obj.Datacenter"
            datacenters.add(obj.Datacenter)
        }
       println "$number $line"
    }
}

pipeline {
    environment {
           vari = ""
    }
    agent any
    stages {
        stage ("setup parameters") {
            steps {
                script {
                File data = getFile("${WORKSPACE}/mapping.csv");
                parseData( data )

                    properties([
                    parameters([
                        [$class: 'ChoiceParameter',
                            choiceType: 'PT_SINGLE_SELECT',
                            description: 'Select the Env Name from the Dropdown List',
                            filterLength: 1,
                            filterable: true,
                            name: 'Env',
                            randomName: 'choice-parameter-5631314439613978',
                            script: [
                                $class: 'GroovyScript',
                                fallbackScript: [
                                    classpath: [],
                                    sandbox: false,
                                    script:
                                        'return[\'Could not get Env\']'
                                ],
                                script: [
                                    classpath: [],
                                    sandbox: false,
                                    script:
                                        '$datacenters'
                                ]
                            ]
                        ],
                        [$class: 'CascadeChoiceParameter',
                            choiceType: 'PT_SINGLE_SELECT',
                            description: 'Select the Server from the Dropdown List',
                            filterLength: 1,
                            filterable: true,
                            name: 'Server',
                            randomName: 'choice-parameter-5631314456178619',
                            referencedParameters: 'Env',
                            script: [
                                $class: 'GroovyScript',
                                fallbackScript: [
                                    classpath: [],
                                    sandbox: false,
                                    script:
                                        'return[\'Could not get Environment from Env Param\']'
                                ],
                                script: [
                                    classpath: [],
                                    sandbox: false,
                                    script:
                                        ''' if (Env.equals("Dev")){
                                                return["devaaa001","devaaa002","devbbb001","devbbb002","devccc001","devccc002"]
                                            }
                                            else if(Env.equals("QA")){
                                                return["qaaaa001","qabbb002","qaccc003"]
                                            }
                                            else if(Env.equals("Stage")){
                                                return["staaa001","stbbb002","stccc003"]
                                            }
                                            else if(Env.equals("Prod")){
                                                return["praaa001","prbbb002","prccc003"]
                                            }
                                        '''
                                    ]
                                ]
                            ]
                        ])
                    ])
                }
            }
        }
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

