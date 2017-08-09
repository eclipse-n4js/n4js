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
package org.eclipse.n4js.tests.typesbuilder;

import static org.junit.Assert.assertEquals;

import org.eclipse.n4js.typesbuilder.N4JSTypesBuilder;
import org.junit.Test;

/**
 * Test for static function
 */
public class TypesBuilderMD5HexTest {

	@SuppressWarnings("javadoc")
	@Test
	public void testMD5Hex() {
		assertEquals("b10a8db164e0754105b7a99be72e3fe5", N4JSTypesBuilder.md5Hex("Hello World"));
	}
}
