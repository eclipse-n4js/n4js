pipeline {
    agent any

    environment {
        NODEJS_PATH= '/usr/bin' // '/shared/common/node-v10.15.3-linux-x64/bin'
        YARN_PATH  = '/usr/bin' // '/shared/common/yarn/1.15.2/bin'
        PATH       = "${PATH}"                                          +
                     ":/opt/apache-ant-1.0.6/bin"             +
                     ":/opt/apache-maven-3.6.1/bin/"
                     // ":/shared/common/node-v10.15.3-linux-x64/bin"      +
                     // ":/shared/common/java/openjdk/jdk11-x64/bin"
        MAVEN_OPTS = '-Xmx4G'
        JAVA_HOME  = '/usr/lib/jvm/java-11-openjdk-amd64'
        TIMESTAMP  = new Date().format("yyyyMMddHHmm")
   }

    stages {
        stage('Build and Test') {
            steps {
                echo 'Starting Xvnc'
                wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: true]) {
                    echo 'Building and testing..'
                // dir('lift') {
                    script {
                        def options = [
                            '--batch-mode',
                            //'--quiet',
                            '--update-snapshots',
                            '--show-version',
                            '-Dtycho.localArtifacts=ignore',
                            '-Dmaven.test.failure.ignore',
                            '-DWORKSPACE=' + env.WORKSPACE,
                            '-DexcludeJRE'
                        ].join(' ')
                        def profiles = [
                            'buildProduct',
                            'execute-plugin-tests',
                            'execute-plugin-ui-tests',
                            'execute-ecma-tests',
                            'execute-accesscontrol-tests',
                            'execute-smoke-tests'
                         //'execute-hlc-integration-tests'
                        ].join(',')

                        sh """\
                            pwd
                            git log -n 1
                            npm version
                        """
                        // sh "mvn clean verify -P${profiles} ${options}"
                        sh "mvn clean verify ${options}"

                      // sh "ls -Ral builds/org.eclipse.n4js.product.build/target/repository/"
                    }
                // } // end dir
                } // end wrap
            } // end steps
        } // end stage
        /*
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }*/
    }

    post {
        always {
         //   archiveArtifacts allowEmptyArchive: true, artifacts: '**/builds/**/target/products/*.zip'
         //   archiveArtifacts allowEmptyArchive: true, artifacts: '**/tools/**/target/n4jsc.jar'
         //   archiveArtifacts allowEmptyArchive: true, artifacts: '**/logs/*.log'
         //   archiveArtifacts allowEmptyArchive: true, artifacts: '**/tests/**/target/**/*-output.txt'

            junit '**/surefire-reports/**/*.xml'
            //junit '**/failsafe-reports/**/*.xml'
        }
        // cleanup {
            // excute here in case archiving fails in 'always'
            //mail to: 'some.one@some.where.eu',
            //     subject: "${currentBuild.result}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            //     body:  """\
            //            ${currentBuild.result}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':
            //            Check console output at '${env.BUILD_URL}'
            //            """

            // Execute after every other post condition has been evaluated, regardless of status
            // See https://jenkins.io/doc/book/pipeline/syntax/#post
            // echo 'Cleaning up workspace'
            // deleteDir()
         // }
    }
}