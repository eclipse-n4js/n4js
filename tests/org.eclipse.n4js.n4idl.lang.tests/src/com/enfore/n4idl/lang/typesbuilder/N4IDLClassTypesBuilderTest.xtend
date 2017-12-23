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
package com.enfore.n4idl.lang.typesbuilder

import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.junit.Test

public class N4IDLClassTypesBuilderTest extends AbstractParserTest {

	@Test
	def void testVersionedClass_01() {
		val Script script = 'class C #1 {}'.parseSuccessfully
		val TModule module = script.module
		val TClass tclass = module.topLevelTypes.get(0) as TClass;
		assertEquals("C", tclass.name);
		assertEquals(1, tclass.version);
	}


}
