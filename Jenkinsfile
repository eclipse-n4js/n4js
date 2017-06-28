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


                    String snapshotRepo = "${workspace}/${N4JS_SNAPSHOT_MAVEN_REPO_FOLDER_WSREL}"
                    String snapshotProperty = "-Dlocal-snapshot-deploy-folder=${snapshotRepo}"
                    String siblingRepoProperty = "-DsiblingMavenRepo=file://${snapshotRepo}"
                    String targetPomFile = "--file ${workspace}/${n4jsDir}//pom.xml"
                    String commonOptions = "-Dmaven.test.failure.ignore -X -e"

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

                                sh """xvfb-run -a --server-args="-screen 0 1024x768x24" mvn clean deploy ${targetPomFile} ${commonProfiles} ${siblingRepoProperty} ${snapshotProperty} ${commonOptions}"""
                            }
                        }
                    }
                }

                stage('PostBuild') {
                    if (isMaster()) {
                        rsync("$workspace/n4js/releng/org.eclipse.n4js.targetplatform/N4JS.setup", 'devtools/setup/enfore/n4js')
                    }

                    if (isNightly()) {
                        //TODO nightly post builds steps not yet supported
                        echo "TODO nightly post builds steps not yet supported"
                    }
                }
                sendEmail("${env.JOB_NAME} (${env.BUILD_NUMBER}) succeeded", "${env.BUILD_URL} succeeded - ${env.JOB_NAME} (#${env.BUILD_NUMBER}).")
            } catch (exc) {
                sendEmail( "${env.JOB_NAME} (${env.BUILD_NUMBER}) failed", "${env.BUILD_URL} is failing - ${env.JOB_NAME} (#${env.BUILD_NUMBER}). The following exception was caught : \n ${exc.toString()}")
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
 * When running on {@code master} it sends email to {@code staff-devtools@numberfour.eu}, otherwise
 * to the developers involved in current custom branch.
 * @param subject the subject of the email
 * @param body the body of the email
 */
@NonCPS
void sendEmail(String subject, String body) {
    String robot = 'robot@numberfour.eu'
    if (isMaster()) {
        emailext subject: subject,
                body: body,
                replyTo: robot,
                from: robot,
                to: 'staff-devtools@numberfour.eu'
    } else {
        emailext subject: subject,
                body: body,
                replyTo: robot,
                from: robot,
                recipientProviders: [
                        [$class: 'CulpritsRecipientProvider'],
                        [$class: 'UpstreamComitterRecipientProvider'],
                        [$class: 'DevelopersRecipientProvider'],
                        [$class: 'FailingTestSuspectsRecipientProvider'],
                        [$class: 'FirstFailingBuildSuspectsRecipientProvider'],
                        [$class: 'RequesterRecipientProvider']]
    }
}

/**
 * Helper function that performs git checkout.
 * Note that for the remote user can pass either inherited remote config,
 * or assuming you have git url like {@code git@github.com:Profile/repo.git}, then you can in place create config
 * by passing {@code [[url: git@github.com:Profile/repo.git]]}
 *
 * @param checkoutDir string with directory name where to checkout, relative to the workspace
 * @param gitRemote scm object configuring remotes ({@code scm.userRemoteConfigs})
 * @param branch brach name to checkout (it is String but cannot infer from scm object)
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

/**
 * Helper function that performs rsync.
 *
 * @param absolute path to location used as source parameter of rsync call
 * @param relative path to location to used as destination or rsync call
 * @param base used to resolve destination or rsync call, by default {@code 'storage.corp.numberfour.eu'}
 */
@NonCPS
def rsync(String fromLocation, String toLocation, String rsyncBase = 'storage.corp.numberfour.eu') {
    String dest = "$rsyncBase/$toLocation"
    echo "-------------------------------------------------------------------------"
    echo "rsync"
    echo "     from $fromLocation"
    echo "     to   http://$dest"
    echo "-------------------------------------------------------------------------"
    sh "rsync -av $fromLocation rsync://$dest"
}

//============== NOT WORKING ====================

/**
 * Analyzes logs for downloads from external sources. When found and job is successfull, job is changed to unstable.
 *
 * Note, this requires special permisions in Jenkins configured otherwise throws
 * {@code org.jenkinsci.plugins.scriptsecurity.sandbox.RejectedAccessException: Scripts not permitted to use method org.jvnet.hudson.plugins.groovypostbuild.GroovyPostbuildRecorder$BadgeManager getBuild}
 */
@NonCPS
def checkLogsForDownloads() {
    // old jobs had special check performing following steps:
    //    # get all log-lines starting with "Downloading
    //    # then remove all "Downloading npm dependencies..."
    //    # then remove all "repository.corp.numberfour.eu"
    //    # then remove all "Downloding: file:/"
    //    # and assert that NOTHING is left! (empty log-lines)

    //now we go with regex to


    pattern = ~/(.*Downloading)(?!(: http:\/\/repository.corp.numberfour.eu|: file:\/| npm dependencies)).*/
    def list = []
    manager.build.logFile.eachLine { line ->
        matcher = pattern.matcher(line)
        if (matcher.matches()) {
            ownClass = matcher.group(1).replaceAll("/", ".")
            sunClass = matcher.group(2)
            list.add(line)
        }
    }
    if (list.size() > 0) {
        def summary = manager.createSummary("warning.gif")
        summary.appendText("Found downlaods from external sources:<ul>", false)
        list.each {
            summary.appendText("<li><b>$it</b>", false)
        }
        summary.appendText("</ul>", false)
        if (manager.getResult() == 'SUCCESS') {
            manager.buildUnstable()
        }
    }
}

