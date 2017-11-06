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
        docker { image 'docker-corp.numberfour.eu/gbastkowski/n4js-build' }
        dockerfile {
            filename 'docker-build/Dockerfile'
            label    'build-n4js'
        }
    }

    options {
        timestamps()
        ansiColor('xterm')
        disableConcurrentBuilds()
        // pipelineTriggers([[$class: 'GitHubPushTrigger']]),
        buildDiscarder(
            logRotator(
                artifactDaysToKeepStr:'10',
                artifactNumToKeepStr:'5',
                daysToKeepStr:'10',
                numToKeepStr:'5'))
    }

    stages {
        // stage("PreBuild") {
        //     //clean workspace
        //     listDir(workspace)
        //     step([$class: 'WsCleanup'])
        //     listDir(workspace)

        //     //clone
        //     timeout(time: 30, unit: 'MINUTES') {
        //         gitCheckout(n4jsDir, scm.userRemoteConfigs, scm.branches[0].name, '/home/build/git-reference-cache/n4js.git')
        //     }

        //     //prepare local repo folder
        //     echo "should create ${N4JS_SNAPSHOT_MAVEN_REPO_FOLDER_WSREL}"
        //     sh "mkdir ${N4JS_SNAPSHOT_MAVEN_REPO_FOLDER_WSREL}"
        //     listDir(workspace)

        // }

        stage('Build') {

                    // timeout(time: 3, unit: 'HOURS') {
                    //     def nodeHome = tool(name: n4jsNodejsVersion, type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation')
                    //     def xvfbBin = tool(name: 'default', type: 'org.jenkinsci.plugins.xvfb.XvfbInstallation')
                    //     withEnv(["PATH+NODEJS=${nodeHome}/bin", "PATH+XVFB=${xvfbBin}"]) {
                    //         withMaven( jdk: n4jsJavaVersion,
                    //                    maven: n4jsMavenVersion,
                    //                    mavenLocalRepo: '.repository',
                    //                    mavenSettingsConfig: '3f2fefed-80bc-4bfc-9e72-5f5cf9315f7c',
                    //                    mavenOpts: '-Xms512m -Xmx2048M') {
                    //         }
                    //     }
                    // }

            steps {
                echo 'Building ...'
                def options = "-Dmaven.test.failure.ignore -e -DWORKSPACE=${env.WORKSPACE}"
                def profiles = "-PbuildProduct,execute-plugin-tests,execute-plugin-ui-tests,execute-swtbot-tests"
                    // if (isNightly()) {
                    //     profiles = profiles + ",execute-ecma-tests,execute-performance-tests,execute-swtbot-performance-tests,execute-accesscontrol-tests"
                    // }
                sh """\
                    echo "===== checking tools versions ====="
                    mvn -v
                    node -v
                    npm -v
                    echo "==================================="
                """
                sh """xvfb-run -a --server-args="-screen 0 1024x768x24" mvn clean verify ${profiles} ${options}"""
            }
        }
        stage('Test') {
            steps {
                echo 'Testing ...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying ...'
            }
        }
    }
}

