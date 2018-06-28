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
package org.eclipse.n4js.semver.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.junit.Assert
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet
import org.eclipse.n4js.semver.SemanticVersioningInjectorProvider

@RunWith(XtextRunner)
@InjectWith(SemanticVersioningInjectorProvider)
class ExampleTest extends Assert {

	@Inject extension ParseHelper<VersionRangeSet>

	def assertValid(CharSequence expression) {
		val parsed = expression.parse
		val errors = parsed.eResource.errors
		assertTrue(errors.toString, errors.isEmpty)
	}

}
