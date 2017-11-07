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
        buildDiscarder(
            logRotator(
                numToKeepStr:           '5',
                artifactDaysToKeepStr: '10',
                artifactNumToKeepStr:   '5',
                daysToKeepStr:         '10'))
        disableConcurrentBuilds()
        timeout(time: 3, unit: 'HOURS')
        timestamps()
        triggers { pollSCM('H 5/* 0 0 1-5') } // every 5 minutes on weekdays
    }
    stages {
        stage('build') {
            steps {
                sh 'xvfb-run -a --server-args="-screen 0 1024x768x24" ' +
                    'mvn clean verify ' +
                        '-PbuildProduct,execute-plugin-tests,execute-plugin-ui-tests ' +
                        '-Dmaven.test.failure.ignore ' +
                        '-e -DWORKSPACE=' + env.WORKSPACE
            }
        }
        stage('long-running-tests') {
            when {
                branch 'master'
            }
            steps {
                sh 'xvfb-run -a --server-args="-screen 0 1024x768x24" ' +
                    'mvn verify ' +
                        '-PbuildProduct,execute-plugin-tests,execute-plugin-ui-tests,execute-ecma-tests,execute-performance-tests,execute-swtbot-performance-tests,execute-accesscontrol-tests ' +
                        '-Dmaven.test.failure.ignore ' +
                        '-e -DWORKSPACE=' + env.WORKSPACE
            }
        }
    }
}
