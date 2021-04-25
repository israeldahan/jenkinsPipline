properties([
    parameters([
        booleanParam(name: 'Allocating_Ip_And_Cnames', defaultValue: true, description: 'Uncheck for skipping Allocating Ip and create custom interal cname records stage.'),
        booleanParam(name: 'External_And_Internal_Cnames', defaultValue: true, description: 'Uncheck for skipping external and internal Cnames creation stage.'),
        booleanParam(name: 'Rsso_Realm_Creation', defaultValue: true, description: 'Uncheck for skipping rsso realm creation stage.'),
        booleanParam(name: 'Mailbox_Creation', defaultValue: true, description: 'Uncheck for skipping mailbox creation stage.'),
		string(defaultValue: "ansible-master", description: 'Jenkins Node to build', name: 'node_label'),
		string(defaultValue: "", description: 'Customer service name in lowercase', name: 'Servicename'),
		string(defaultValue: "", description: '''Customer name
         (This field is not mandatory)''', name: 'Customer_name'),
        string(defaultValue: "", description: 'Environment Type in lower case(example: dev, qa ,prod, etc ..)', name: 'environment'),
        booleanParam(name: 'non_prod',  defaultValue: true, description: 'if non-prod is true. \n it will have to create a CNAME DB Cname URL - "cust_env"'),
        booleanParam(name: 'dwp',  defaultValue: true, description: 'if DWP basic purchased choose True \n cust-env-dwp.onbmc.com'),
        booleanParam(name: 'int',  defaultValue: false, description: 'if int purchased choose True \n cust-env-int.onbmc.com'),
        booleanParam(name: 'atws', defaultValue: false, description: 'if atws purchased choose True \n cust-env-atws.onbmc.com '),
        booleanParam(name: 'dwpC', defaultValue: false, description: 'if DWP basic purchased choose True \n cust-env-vchat.onbmc.com \n cust-env-dwpcatalog.onbmc.com'),
        [$class: 'ChoiceParameter',
            choiceType: 'PT_SINGLE_SELECT',
            description: 'Select the DataCenter location from the Dropdown List',
            filterLength: 1,
            filterable: false,
            name: 'DataCenter',
            script: [
                $class: 'GroovyScript',
                fallbackScript: [
                    classpath: [],
                    sandbox: false,
                    script:
                        'return[\'Could not get DataCenter\']'
                ],
                script: [
                    classpath: [],
                    sandbox: false,
                    script:
                        '''
                            def data =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
                            def DataCenters = []
                            data.eachLine { line, number ->
                                if (number == 0) {
                                } else {
                                    def lineSplit = line.split(',')
                                    DataCenters.add(lineSplit[0])
                                }
                            }
                            DataCenters.unique { a, b -> a <=> b }
                            return DataCenters
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
            referencedParameters: 'DataCenter',
            script: [
                $class: 'GroovyScript',
                fallbackScript: [
                    classpath: [],
                    sandbox: false,
                    script:
                        'return[\'Could not get ClusterID from DataCenter Param\']'
                ],
                script: [
                    classpath: [],
                    sandbox: false,
                    script:
                        '''
                            def data =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
                            def ClusterID = []
                            data.eachLine { line, number ->
                                if (number == 0) {
                                } else {
                                    def lineSplit = line.split(',')
                                    if ( lineSplit[0] == DataCenter ){
                                        ClusterID.add(lineSplit[1])
                                    }
                                }
                            }
                            return  ClusterID
                        '''
                ]
            ]
        ],
        [$class: 'CascadeChoiceParameter',
         choiceType: 'PT_SINGLE_SELECT',
         description: 'GTM Record (point to URL) External url for CNAME.',
         filterLength: 1,
         filterable: false,
         name: 'ExternalPoint2URL',
         referencedParameters: 'ClusterID',
         script: [
             $class: 'GroovyScript',
             fallbackScript: [
                 classpath: [],
                 sandbox: false,
                 script:
                     'return[\'Could not get ExternalPoint2URL from ClusterID Param\']'
             ],
             script: [
                 classpath: [],
                 sandbox: false,
                 script:
                     '''
                    def data =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
                    def ExternalPoint2URL = []
                    data.eachLine { line, number ->
                        if (number == 0) {
                        } else {
                            def lineSplit = line.split(',')
                            if ( lineSplit[1] == ClusterID ){
                                ExternalPoint2URL.add(lineSplit[2])
                            }
                        }
                    }
                    return  ExternalPoint2URL
                     '''
                 ]
             ]
        ],
         [$class: 'CascadeChoiceParameter',
          choiceType: 'PT_SINGLE_SELECT',
          description: 'GTM Record (point to URL) Internal url for CNAME.',
          filterLength: 1,
          filterable: false,
          name: 'InternalPoint2URL',
          referencedParameters: 'ClusterID',
          script: [
              $class: 'GroovyScript',
              fallbackScript: [
                  classpath: [],
                  sandbox: false,
                  script:
                      'return[\'Could not get InternalPoint2URL from ClusterID Param\']'
              ],
              script: [
                  classpath: [],
                  sandbox: false,
                  script:
                      '''
                        def data =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
                        def InternalUrl = []
                        data.eachLine { line, number ->
                            if (number == 0) {
                                } else {
                                    def lineSplit = line.split(',')
                                    if ( lineSplit[1] == ClusterID ){
                                    InternalUrl.add(lineSplit[3])
                                }
                            }
                        }
                        return  InternalUrl
                      '''
                  ]
              ]
         ],
        [$class: 'CascadeChoiceParameter',
        choiceType: 'PT_SINGLE_SELECT',
        description: '',
        filterLength: 1,
        filterable: false,
        name: 'RSSOURL',
        referencedParameters: 'ClusterID',
        script: [
            $class: 'GroovyScript',
            fallbackScript: [
                classpath: [],
                sandbox: false,
                script:
                    'return[\'Could not get RSSOURL from ClusterID Param\']'
            ],
            script: [
                classpath: [],
                sandbox: false,
                script:
                    '''
                    def data =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
                    def RSSOURL = []
                    data.eachLine { line, number ->
                        if (number == 0) {
                        } else {
                            def lineSplit = line.split(',')
                            if ( lineSplit[1] == ClusterID ){
                                def RssoUrlArr = lineSplit[4].split(";")
                                RSSOURL.addAll(RssoUrlArr)
                            }
                        }
                    }
                    return  RSSOURL
                    '''
                ]
            ]
        ],
        string(name: 'RssoPort', defaultValue: '443', description: ''),
        string(name: 'email', defaultValue: '', description: 'Email for notification'),
        string(name: 'RssoUser', defaultValue: '', description: ''),
        password(name: 'RssoPassword', defaultValue: '', description: 'Enter a password')
    ])
])
pipeline {
    agent {
	    node {
		label "${params.node_label}"
	    }
	  }
    stages {

		stage("Git Checkout") {
			steps {
			    cleanWs()  //Clean workspace
			    echo "clonning git repo...."
// 			    git 'http://10.177.150.20:3000/core-remedy/helix-activation-playbooks'
			    writeSubnet(ClusterID)
			}
		}
		stage("Prepare inventory files"){
		    steps{
		        script{
		            writeFile file: "$WORKSPACE/templates/inventory.properties", text: "company=${Customer_name}\nDataCenter=${DataCenter}\nservicename=${Servicename}\nenvironment=${environment}\nClusterID=${ClusterID}"
		              }
		        sh 'chmod -R 777 scripts/'
			    dir('scripts'){
			        //Create url's file
    			        sh './createCommonUrlsandEnvs.sh $environment $Servicename $WORKSPACE/templates/template_ExternalCNAME $WORKSPACE/externalCNAME $dwp $int $atws $dwpC $non_prod'
                        sh './createCommonUrlsandEnvs.sh $environment $Servicename $WORKSPACE/templates/template_InernalDNS $WORKSPACE/InternalDns $dwp $int $atws $dwpC $non_prod'
						 //remove prod-env from externalCname file(prods urls are without prod env name)
						sh "sed -i 's/-prod//' $WORKSPACE/externalCNAME"
			                   }
			    }

		    }
		stage("Allocating IP using IPAM") {
			     when {
                //Execute this step only if data center is : Australia – Sydney , Amsterdam – Equinix , U.S. Commercial - Equinix Chicago , U.K. - Equinix , U.S. Commercial - Equinix Santa Clara.
                expression {"$Allocating_Ip_And_Cnames" == "true" && ("$DataCenter" == 'Australia - Sydney' || "$DataCenter" == 'Amsterdam - Equinix'  || "$DataCenter" == 'U.S. Commercial - Equinix Chicago' || "$DataCenter" == 'U.K. - Equinix' || "$DataCenter" == 'U.S. Commercial - Equinix Santa Clara')}
                 }
			steps {
			    dir('scripts/IPAM'){
			        echo "skipping ipam..."
			    //note: need to understand how we get Location and Customer name
			    sh 'echo Running Allocating Ip using Ipam.......'
			    sh 'python ./get_free_ip.py $WORKSPACE/ '
			    sh 'echo Hostnames with IPs are in a file at: $WORKSPACE/InternalDns1'
			    }
			}
		}
        stage("create Custom Internal Cname records(With ip's)") {
              when {
                //Execute this step only if data center is : Australia – Sydney , Amsterdam – Equinix , U.S. Commercial - Equinix Chicago , U.K. - Equinix , U.S. Commercial - Equinix Santa Clara.
                expression {"$Allocating_Ip_And_Cnames" == "true" && ("$DataCenter" == 'Australia - Sydney' || "$DataCenter" == 'Amsterdam - Equinix'  || "$DataCenter" == 'U.S. Commercial - Equinix Chicago' || "$DataCenter" == 'U.K. - Equinix' || "$DataCenter" == 'U.S. Commercial - Equinix Santa Clara')}
                 }
			steps {
			    dir('scripts'){
			      sh 'echo Entering create Custom Internal Cname records....'
			      //sh './runCreateCustomInternalRecordPlaybook.sh $WORKSPACE $email'
			    }
			}
		}
		stage("Create External and Internal Cname") {
		    when {
                expression {"$External_And_Internal_Cnames" == "true"}
            }
			steps {
			    dir('scripts'){
			     sh 'echo Entering Create External and Internal Cname ..'
			     sh './runExternalInternalCNAMES.sh $WORKSPACE $ExtermalPoint2URL $InternalPoint2URL $email'
			    }
			}
		}
		stage("RSSO Realm Creation") {
		     when {
                expression {"$Rsso_Realm_Creation" == "true"}
             }
			steps {
			    dir('scripts'){
			      sh 'python ./create_sso_realm.py --rssoUrl $RSSOURL --rssoPort $RssoPort  --workspace $WORKSPACE --customer $Servicename --envList $environment --rssoUser $RssoUser --rssoPassword $RssoPassword'
			    }
			}
		}
		stage("Mailbox Creation") {
		    when {
                expression {"$Mailbox_Creation" == "true"}
            }
			steps {
			    dir('scripts/Mailbox'){
			     sh 'echo ${email}'
			     sh "echo ${servicename}"
			     sh "python ./create_mailbox.py $WORKSPACE/ $email"
			    }
			}
		}
    }
    post {
        success  {
            script{
                print "job is successfull"
                archiveArtifacts artifacts: 'externalCNAME'
                if("$Allocating_Ip_And_Cnames" == "true"){
                    archiveArtifacts artifacts: 'InternalDns1'
                }

            }
        }
    }
}
@NonCPS
def writeSubnet(ClusterID) {
    def DCData =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/mapping.csv").getText()
    def LocationData =  new URL ("https://github.bmc.com/raw/idahan/jenkinsPipline/master/locations.csv").getText()
    def  allSubnet = getFromSubnetData(DCData, ClusterID, "allSubnet")
    def DC = getFromSubnetData (DCData, ClusterID, "DC")
    def location = getFromLocationData(DC, LocationData, "location")
    def locationDC = getFromLocationData(DC, LocationData, "locationDC")
    def locationDCSubnet = getFromLocationData(DC, LocationData, "locationDCSubnet")
    def fullSubnetDCs = getAllFullSubnet(locationDCSubnet, allSubnet)

    File subnetFile = new File("${WORKSPACE}/out.txt")
    subnetFile.write("")
    writeFileSubnet(subnetFile, location, locationDC, fullSubnetDCs, ClusterID)
    return success
}
@NonCPS
def getFromSubnetData(DCData, ClusterID, type) {
    def data
    def DC
    def allSubnet = []
    DCData.eachLine { line, number ->
       if (number == 0) {
       } else {
           def lineSplit = line.split(',')
           if ( lineSplit[1] == ClusterID ){
                switch (type){
                        case type == "DC":
                        data = lineSplit[0]
                        break
                    case type == "allSubnet":
                        data = allSubnet.addAll(lineSplit[5].split(";"))
                        break;
                }
            }
       }
   }
   return data;
}
@NonCPS
def getFromLocationData(DC, LocationData, type){
    def data
    def location
    def locationDC = []
    def locationDCSubnet = []
    LocationData.eachLine { line, number ->
        if (number == 0) {
        } else {
            def lineSplit = line.split(',')
            if ( lineSplit[0] == DC ){
                switch (type) {
                    case type == "location":
                        data = lineSplit[1]
                        break
                    case type == "locationDC":
                        data.addAll(lineSplit[2].split(";"))
                    case type == "locationDCSubnet":
                        data.addAll(lineSplit[3].split(";"))
                }
            }
        }
    }
    return data
}
@NonCPS
def getAllFullSubnet(locationDCSubnet, allSubnet) {
    locationDCSubnet.eachWithIndex { locDCSubnet, index ->
        def fullSubnetDC = []
        allSubnet.eachWithIndex { subnet, indexSub ->
            println("10.${subnet}.${locDCSubnet}.0")
            fullSubnetDC.add("10.${locDCSubnet}.${subnet}.0")
        }
        FullSubnetDCs.add(fullSubnetDC)
    }
    return FullSubnetDCs
}
@NonCPS
def writeFileSubnet(subnetFile, location, locationDC, allSubnetDCs, ClusterID) {
    if (location) {
        subnetFile.append("Location=${location}\n")
        subnetFile.append('''DC${location}=${locationDC
                .toString()
                .replace("[", "")
                .replace("]", "")
                .replace(", ", ",")}\n''')
        locationDC.eachWithIndex { it, index ->
            subnetFile.append('''Subnet${it}_${ClusterID}=${allSubnetDCs[index]
                    .toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(", ", ",")}\n''')
        }
    }
}
