def EnviromentArr(){
    return ["Select:selected", "DEV", "TEST", "STAGE", "PROD"]
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
      name: 'Environment',
      script: EnviromentArr()
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
