properties([
    parameters([
        choice(
            choices: ['ONE', 'TWO'],
            name: 'PARAMETER_01'
        ),
        booleanParam(
            defaultValue: true,
            description: '',
            name: 'BOOLEAN'
        ),
        text(
            defaultValue: '''
            this is a multi-line
            string parameter example
            ''',
             name: 'MULTI-LINE-STRING'
        ),
        string(
            defaultValue: 'scriptcrunch',
            name: 'STRING-PARAMETER',
            trim: true
        ),
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?'),
        password(name: 'PASSWORD', defaultValue: 'SECRET', description: 'Enter a password'),
        [$class: 'ChoiceParameter',
            choiceType: 'PT_SINGLE_SELECT',
            description: 'Select the DataCenter Name from the Dropdown List',
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
                        'return[\'Could not get DataCenter from DataCenter Param\']'
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
                    def RssoUrl = []
                    data.eachLine { line, number ->
                        if (number == 0) {
                        } else {
                            def lineSplit = line.split(',')
                            if ( lineSplit[1] == ClusterID ){
                                def RssoUrlArr = lineSplit[4].split(";")
                                RssoUrl.addAll(RssoUrlArr)
                            }
                        }
                    }
                    return  RssoUrl
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
