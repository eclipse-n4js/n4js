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

import groovy.util.AntBuilder


timestamps {
    ansiColor('xterm') {

        /** keep builds/artifacts for 10 days */
        String keep10 = '10'
        /** keep up to 5 builds/artifacts */
        String keep5 = '5'

        properties([ disableConcurrentBuilds(),
                     pipelineTriggers([[$class: 'GitHubPushTrigger']]),
                     [$class  : 'BuildDiscarderProperty',
                        strategy: [$class: 'LogRotator', artifactDaysToKeepStr: keep10, artifactNumToKeepStr: keep5, daysToKeepStr: keep10, numToKeepStr: keep5]]])

        String n4jsNodeLabel = "ide"
        node(n4jsNodeLabel) {
            try {
                //get parameters provided in the environment or the local defaults
                String n4jsJavaVersion = 'jdk1.8u131'
                String n4jsMavenVersion = '3.2.2'
                String n4jsNodejsVersion = 'Node.js latest'

                String workspace = pwd()
                String n4jsDir = "n4js"

                String N4JS_SNAPSHOT_MAVEN_REPO_FOLDER_WSREL = "N4JS_SNAPSHOT_MAVEN_REPO_FOLDER_WSREL"


                stage("PreBuild") {
                    //clean workspace
                    listDir(workspace)
                    step([$class: 'WsCleanup'])
                    listDir(workspace)

                    //clone
                    timeout(time: 30, unit: 'MINUTES') {
                        gitCheckout(n4jsDir, scm.userRemoteConfigs, scm.branches[0].name, '/home/build/git-reference-cache/n4js.git')
                    }

                    //prepare local repo folder
                    echo "should create ${N4JS_SNAPSHOT_MAVEN_REPO_FOLDER_WSREL}"
                    sh "mkdir ${N4JS_SNAPSHOT_MAVEN_REPO_FOLDER_WSREL}"
                    listDir(workspace)

                }


                stage('Build') {
                    def nodeHome = tool(name: n4jsNodejsVersion, type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation')
                    def xvfbBin = tool(name: 'default', type: 'org.jenkinsci.plugins.xvfb.XvfbInstallation')

                    String targetPomFile = "--file ${workspace}/${n4jsDir}//pom.xml"
                    String commonOptions = "-Dmaven.test.failure.ignore -e -DWORKSPACE=${env.WORKSPACE}"

                    String commonProfiles = "-PbuildProduct,execute-plugin-tests,execute-plugin-ui-tests,execute-swtbot-tests"
                    if (isNightly()) {
                        commonProfiles = commonProfiles + ",execute-ecma-tests,execute-performance-tests,execute-swtbot-performance-tests,execute-accesscontrol-tests"
                    }

                    timeout(time: 3, unit: 'HOURS') {
                        withEnv(["PATH+NODEJS=${nodeHome}/bin", "PATH+XVFB=${xvfbBin}"]) {
                            withMaven( jdk: n4jsJavaVersion,
                                       maven: n4jsMavenVersion,
                                       mavenLocalRepo: '.repository',
                                       mavenSettingsConfig: '3f2fefed-80bc-4bfc-9e72-5f5cf9315f7c',
                                       mavenOpts: '-Xms512m -Xmx2048M') {

                                sh """\
                                       echo "===== checking tools versions ====="
                                       mvn -v
                                       node -v
                                       npm -v
                                       echo "==================================="
                                  """

                                sh """xvfb-run -a --server-args="-screen 0 1024x768x24" mvn clean verify ${targetPomFile} ${commonProfiles} ${commonOptions}"""
                            }
                        }
                    }
                }

                stage('PostBuild') {
                }
                //sendEmail("${env.JOB_NAME} (${env.BUILD_NUMBER}) succeeded", "${env.BUILD_URL} succeeded - ${env.JOB_NAME} (#${env.BUILD_NUMBER}).")
            } catch (exc) {
                //sendEmail( "${env.JOB_NAME} (${env.BUILD_NUMBER}) failed", "${env.BUILD_URL} is failing - ${env.JOB_NAME} (#${env.BUILD_NUMBER}). The following exception was caught : \n ${exc.toString()}")
                //rethrow otherwise job will always be green
                throw exc
            } finally {
            //checkLogsForDownloads();
            }
        }
    }
}


//=============== core functions ===============

/**
 * Lists contents of the provided dir
 * @param path to print contents
 */
void listDir(String location) {
    sh "cd $location; ls -la;"
}

//=============== util functions ===============

/**
 * Checks if current branch (based on environment property {@code env.BRANCH_NAME}) equals {@code master}.
 * @return {@code true} if on master branch
 */
@NonCPS
boolean isMaster() { return env.BRANCH_NAME == "master" }

/**
 * Checks if current build is nightly (based on environment property {@code env.NIGTHLY_BUILD}) equals {@code "true"}.
 * @return {@code true} if on master branch
 */
@NonCPS
boolean isNightly() { return env.NIGTHLY_BUILD == "true" }


/**
 * Sends email notification about job status based on the provided data.
 *
 * @param subject the subject of the email
 * @param body the body of the email
 */
@NonCPS
void sendEmail(String subject, String body) {
        emailext subject: subject,
                body: body,
                recipientProviders: [
                        [$class: 'CulpritsRecipientProvider'],
                        [$class: 'RequesterRecipientProvider']]
}

/**
 * Helper function that performs git checkout.
 * Note that for the remote user can pass either inherited remote config,
 * or assuming you have git url like {@code git@github.com:Profile/repo.git}, then you can in place create config
 * by passing {@code [[url: git@github.com:Profile/repo.git]]}
 *
 * @param checkoutDir string with directory name where to checkout, relative to the workspace
 * @param gitRemote scm object configuring remote ({@code scm.userRemoteConfigs})
 * @param branch branch name to checkout (it is String but cannot infer from scm object)
 * @param refCache string with absolute location of the git reference cache to use
 */
@NonCPS
def gitCheckout(String checkoutDir, Object gitRemote, Object branch, String refCache) {
    checkout poll: false, scm: [ $class                             : 'GitSCM'
                                , branches                         : [[name: branch]]
                                , doGenerateSubmoduleConfigurations: false
                                , extensions                       : [ [$class: 'RelativeTargetDirectory', relativeTargetDir: checkoutDir],
                                                                       [$class: 'CloneOption', depth: 0, noTags: false, reference: refCache, shallow: true]]
                                , userRemoteConfigs                : gitRemote]
}

