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
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.semver.SemanticVersioningInjectorProvider
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet

@RunWith(XtextRunner)
@InjectWith(SemanticVersioningInjectorProvider)
class ErrorTest extends AbstractErrorTests {

	@Inject extension ParseHelper<VersionRangeSet>

	override assertInvalid(CharSequence expression) {
		val parsed = expression.parse
		val errors = parsed.eResource.errors
		assertFalse(errors.toString, errors.isEmpty)
	}

	@Test
	def void test_05() {
		'''/a\/'''.assertInvalid
	}

}
