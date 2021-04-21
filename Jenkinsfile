class clusterID {
    String Datacenter
    String ClusterID
    String ExternalURL
    String InternalURL
    String RssoURL
    String Subnet

    clusterID(Datacenter, ClusterID, ExternalURL, InternalURL, RssoURL, Subnet) {
        this.Datacenter = Datacenter
        this.ClusterID = ClusterID
        this.ExternalURL = ExternalURL
        this.InternalURL = InternalURL
        this.RssoURL = RssoURL
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
    String RssoURL = ""
    String Subnet = ""
    data.eachLine { line, number ->
        if (number == 1){
            vars = line.split(",")
        } else {
//             println "test a $number $line "
            def lineSplit = line.split(',')
            Datacenter = lineSplit[0]
            ClusterID = lineSplit[1]
            ExternalURL = lineSplit[2]
            InternalURL = lineSplit[3]
            RssoURL = lineSplit[4]
            Subnet = lineSplit[5]
            println "Datacenter = $Datacenter,"
            println "ClusterID = $ClusterID,"
            println "ClusterID = $ExternalURL,"
            println "InternalURL = $InternalURL,"
            println "RssoURL = $RssoURL,"
            println "Subnet = $Subnet"
            def obj = new clusterID(Datacenter, ClusterID, ExternalURL, InternalURL, RssoURL, Subnet)
            datacenters.add(obj.Datacenter)
        }
//        println "test c $number $line"
    }
}
@NonCPS
def getDC() {
    return ['datacenters']
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
                def datacentersProp = getDC()
                parameters {
                    string(name: 'DEPLOY_ENV', defaultValue: 'staging', description: '')
                    ChoiceParameter( name: 'Env', description: 'desc', randomName: '', script: 'datacentersProp', choiceType: 'PT_SINGLE_SELECT', filterable: False, filterLength: 0)
                    }
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

