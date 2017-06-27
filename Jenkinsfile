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

try {
    timestamps {
        ansiColor('xterm') {

            //get parameters provided in the environment or the local defaults
            def n4jsJavaVersion = 'jdk1.8u102'
            def n4jsMavenVersion = '3.2.2'
            def n4jsNodejsVersion = 'Node.js latest'
            def n4jsNodeLabel = "ide"

            node(n4jsNodeLabel) {

                def workspace = pwd()
                def n4jsDir = "n4js"
             stage('Check tools versions') {

                 //obtain tools to print versions
                 def javaHome = tool(name: n4jsJavaVersion, type: 'hudson.model.JDK')
                 def javaBin = javaHome + '/bin'
                 def mavenHome = tool(name: n4jsMavenVersion, type: 'hudson.tasks.Maven$MavenInstallation')
                 def mavenBin = mavenHome + '/bin'
                 def nodeHome = tool(name: n4jsNodejsVersion, type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation')
                 def nodeBin = nodeHome + '/bin'

                 withEnv(["JAVA_HOME=${javaHome}", "PATH+MAVEN=${mavenBin}:${env.mavenBin}", "PATH+NODEJS=${nodeBin}"]) {
                     sh "java -version"
                     sh "mvn -v"
                     sh '''\
                           echo "===      checking node      ==="
                           node -v
                           npm -v
                           echo "=== DONE with checking node ==="
                        '''
                }
            }
          }
        }
    }
} catch (exc) {

    // Choose recipient to notify of failing builds
    String developer = env.n4jsDeveloper ?: 'staff-devtools@numberfour.eu'

    mail subject: "${env.JOB_NAME} (${env.BUILD_NUMBER}) failed",
            body: "${env.BUILD_URL} is failing - ${env.JOB_NAME} (#${env.BUILD_NUMBER}). The following exception was caught - ${exc}",
            replyTo: developer,
            to: developer,
            from: 'noreply@ci.jenkins.io'

    //rethrow otherwise job will always be green
    throw exec
} finally {

}
