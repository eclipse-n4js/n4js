/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
/**
 * This package contains a parameterized test case that tests the interplay of access control modifiers at the type and
 * member level.
 *
 * The test case generates test scenarios which consist of at least one N4JS project which are then compiled using the
 * headless compiler, n4jsc. The input for the test case generation is a CSV file, located at
 * <code>testdata/accesscontrol/Matrix.csv</code>, that contains the parameters for each test case along with an
 * expectation.
 *
 * During compilation, the compiler collects all compilation issues, which are then compared with the expectations from
 * the CSV file. If a test case does not satisfy the expectations, it will fail.
 *
 *
 * The other classes and enums in this package are only used internally by the test case. They have been factored out to
 * reduce the size of the test case, but they are not intended for use outside of this package.
 *
 * @see org.eclipse.n4js.accesscontrol.tests.AccessControlTest test input data and its expected
 *      format
 */
package org.eclipse.n4js.accesscontrol.tests;
