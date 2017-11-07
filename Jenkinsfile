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
    stages {
        stage('build') {
            steps {
                def xvfb     =  'xvfb-run -a --server-args="-screen 0 1024x768x24"'
                def targets  = 'clean verify'
                def profiles = 'buildProduct,execute-plugin-tests,execute-plugin-ui-tests,execute-swtbot-tests'
                def options  = '-Dmaven.test.failure.ignore -e -DWORKSPACE=${env.WORKSPACE}'
                sh "$xvfb mvn ${targets} -P${profiles} ${options}"
            }
        }
    }
}
