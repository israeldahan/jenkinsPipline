def vars
def details
def datacenters = []
String Datacenter = ""
String ClusterID = ""
String ExternalURL = ""
String InternalURL = ""
String RssoURL = ""
String Subnet = ""

File data = new File("${WORKSPACE}/mapping.csv")
data.eachLine { line, number ->
    if (number == 1){
        vars = line.split(",")
    } else {
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
}



return ["Select:selected", "$datacenters"]