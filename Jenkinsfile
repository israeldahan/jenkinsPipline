def EnviromentArr(){
    return [ "DEV", "TEST", "STAGE", "PROD"]
}
def HostsInEnv(){
    List devList  = ["Select:selected", "dev1", "dev2"]
    List testList  = ["Select:selected", "test1", "test2", "test3"]
    List stageList = ["Select:selected", "stage1"]
    List prodList  = ["Select:selected", "prod1", "prod2", "prod3", "prod4"]

    List default_item = ["None"]

    if (Environment == 'DEV') {
    return devList
    } else if (Environment == 'TEST') {
    return testList
    } else if (Environment == 'STAGE') {
    return stageList
    } else if (Environment == 'PROD') {
    return prodList
    } else {
    return default_item
    }
}
properties([
    parameters([
        [
            $class: 'ChoiceParameter', 
            choiceType: 'PT_SINGLE_SELECT', 
            description: '', 
            filterable: false, 
            name: 'Release', 
            randomName: 'choice-parameter-21337077649621572', 
            script: [
                $class: 'GroovyScript', 
                fallbackScript: '', 
                script: '''// Find relevant AMIs based on their name
                    def sout = new StringBuffer(), serr = new StringBuffer()
                    def proc = '/usr/bin/aws --region eu-west-1 ec2 describe-images \
                            ' --owners OWNER --filter Name=name,Values=PATTERN \
                            ' --query Images[*].{AMI:Name} --output  text'.execute()
                    proc.consumeProcessOutput(sout, serr)
                    proc.waitForOrKill(10000)
                    return sout.tokenize()'''
            ]
        ]
    ])
])

pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo "${params.Environment}"
        echo "${params.Host}"
      }
    }
  }
}
