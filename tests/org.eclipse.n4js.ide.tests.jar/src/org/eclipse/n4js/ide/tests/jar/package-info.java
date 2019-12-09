/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

/**
 * This package contains tests that execute the <code>n4jsc.jar</code> command line tool in a separate operating systems
 * process (cf. {@link java.lang.ProcessBuilder}), as opposed to the tests in <code>org.eclipse.n4js.ide.tests</code>
 * which directly and programmatically invoke method {@link org.eclipse.n4js.cli.N4jscMain#main(String...)} in order to
 * test N4jsc functionality.
 */
package org.eclipse.n4js.ide.tests.jar;
