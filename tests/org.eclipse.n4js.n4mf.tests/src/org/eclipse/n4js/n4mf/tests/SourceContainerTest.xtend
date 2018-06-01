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
package org.eclipse.n4js.n4mf.tests

import org.junit.Assert
import org.eclipse.n4js.internal.N4JSProjectSourceContainer
import org.junit.Test
import org.eclipse.n4js.n4mf.SourceContainerType

/**
 * Tests, if all available source containers are recognized.
 */
class SourceContainerTest {

	@Test
	def void testSourceContainer() {
		val location = "dummy"
		for(value : SourceContainerType.enumConstants) {
			// this shouldn't throw an exception
			val testContainer = new TestContainer(value, location)
			Assert.assertEquals(location, testContainer.relativeLocation)
		}
	}
}

class TestContainer extends N4JSProjectSourceContainer {

	protected new(SourceContainerType type, String relativeLocation) {
		super(null, type, relativeLocation)
	}
}
