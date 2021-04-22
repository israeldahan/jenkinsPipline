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
def getClusterId(DataCenter){
    return ['test']
}


getClusterId('Amsterdam')
