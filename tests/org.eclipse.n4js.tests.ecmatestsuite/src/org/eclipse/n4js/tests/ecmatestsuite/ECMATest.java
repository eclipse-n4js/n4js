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
package org.eclipse.n4js.tests.ecmatestsuite;

import org.eclipse.n4js.n4jsx.ecmatestsuite.ECMA5TestInSuiteForJSX;
import org.eclipse.n4js.n4jsx.ecmatestsuite.ECMA6TestInSuiteForJSX;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 */
@SuiteClasses({
		ECMA5TestInSuite.class,
		ECMA6TestInSuite.class,
		ECMA5TestInSuiteForJSX.class,
		ECMA6TestInSuiteForJSX.class
})
@RunWith(Suite.class)
public class ECMATest {
	// empty body
}
