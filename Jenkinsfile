/*
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
pipeline {
    agent {
        dockerfile {
            dir 'docker-build/'
        }
    }
    options {
        ansiColor('xterm')
        buildDiscarder(
            logRotator(
                numToKeepStr:          '15',
                artifactDaysToKeepStr: '30',
                artifactNumToKeepStr:  '15',
                daysToKeepStr:         '30'))
        disableConcurrentBuilds()
        timeout(time: 3, unit: 'HOURS')
        timestamps()
    }
    triggers {
        pollSCM('H/5 * * * *') // every 5 minutes
    }
    stages {
        stage('build') {
            steps {
                sh "cat ~/.m2/settings.xml"
                script {
                    def xvfb = 'xvfb-run -a --server-args="-screen 0 1024x768x24" '
                    def targets = 'clean install'
                    def options = '-Dmaven.test.failure.ignore -e -DWORKSPACE=' + env.WORKSPACE
                    def profiles = 'buildProduct,execute-plugin-tests,execute-plugin-ui-tests '

                    sh "${xvfb} mvn -U ${targets} -P${profiles} ${options}"
                }
            }
        }
        stage('long-running-tests') {
            steps {
                script {
                    def xvfb = 'xvfb-run -a --server-args="-screen 0 1024x768x24" '
                    def targets = 'clean verify'
                    def options = '-Dmaven.test.failure.ignore -e -DWORKSPACE=' + env.WORKSPACE
                    def profiles = [
                        'buildProduct',
                        'execute-plugin-tests',
                        'execute-plugin-ui-tests',
                        'execute-ecma-tests',
                        'execute-performance-tests',
                        'execute-swtbot-performance-tests',
                        'execute-accesscontrol-tests '
                    ].join(',')
                    sh "${xvfb} mvn -U ${targets} ${profiles} ${options}"
                }
            }
        }
    }
}
