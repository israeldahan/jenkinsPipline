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
                        'return["Dev","QA","Stage","Prod"]'
                ]
            ]
        ], 
        [$class: 'DynamicReferenceParameter', 
            choiceType: 'ET_FORMATTED_HTML', 
            description: 'These are the details in HTML format', 
            name: 'DetailsInHTML', 
            omitValueField: false, 
            randomName: 'choice-parameter-5633384460832175', 
            referencedParameters: 'Env, Server',
            script: [
                  $class: 'ScriptlerScript',
                  parameters: [[$class: 'org.biouno.unochoice.model.ScriptlerScriptParameter', name: '', value: '$value']],
                  scriptlerScriptId: 'script.groovy'
            ]
        ],
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
