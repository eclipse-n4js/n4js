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
                sh 'xvfb-run -a --server-args="-screen 0 1024x768x24" ' +
                    'mvn clean verify ' +
                        '-PbuildProduct,execute-plugin-tests,execute-plugin-ui-tests ' +
                        '-Dmaven.test.failure.ignore' +
                        '-e -DWORKSPACE=' + env.WORKSPACE
            }
        }
    }
}
