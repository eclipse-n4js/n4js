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
package org.eclipse.n4js

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.runner.RunWith

/**
 * Base class for simple N4JS unit tests. In more special cases, consider using base classes such as
 * {@code org.eclipse.n4js.tests.parser.AbstractParserTest}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public class AbstractN4JSTest extends Assert {

	@Inject protected extension N4JSTestHelper;
	@Inject protected extension N4JSParseHelper;
	@Inject protected extension N4JSValidationTestHelper;
}
