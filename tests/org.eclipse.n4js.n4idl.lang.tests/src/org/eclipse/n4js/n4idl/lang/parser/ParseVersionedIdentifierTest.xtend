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
package org.eclipse.n4js.n4idl.lang.parser

import org.eclipse.n4js.n4idl.lang.AbstractN4IDLParserTest
import org.junit.Test

public class ParseVersionedIdentifierTest extends AbstractN4IDLParserTest {

	@Test
	def void testVersionedIdentfier_n4TypeAccess() {
		'''class T#1{}; T#1.n4type'''.parseSuccessfully	
	}
	
	def void testVersionedIdentifier_StaticProperty() {
		'''class T#1{const a = 3;} T#1.a'''.parseSuccessfully
	}
}
