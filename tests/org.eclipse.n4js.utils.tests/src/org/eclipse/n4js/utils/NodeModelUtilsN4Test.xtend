/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils

import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.junit.Test

/**
 * Tests {@link NodeModelUtilsN4}.
 */
class NodeModelUtilsN4Test extends AbstractN4JSTest {

	@Test
	def void testNodeLengthWithOptionalSemiOmitted01() throws Exception {
		'import {A} from "A"'.assertLengthOfImportDecl(19)
	}

	@Test
	def void testNodeLengthWithOptionalSemiOmitted02() throws Exception {
		'''
			// top
			/**/import {A} from /**/ "A"    /******/
		'''.assertLengthOfImportDecl(24)
	}

	@Test
	def void testNodeLengthWithOptionalSemiOmitted03() throws Exception {
		'''
			// top
			import { A } from "A"      ««« don't remove the trailing white space!


			//
			let x = 42;
		'''.assertLengthOfImportDecl(21)
	}

	@Test
	def void testNodeLengthWithOptionalSemiOmitted04() throws Exception {
		'''
			// top


			import {A}
			from
			"A"


		'''.assertLengthOfImportDecl(17 + 2 * System.lineSeparator.length) // node length includes two line separators!
	}

	@Test
	def void testNodeLengthWithOptionalSemiPresent01() throws Exception {
		'import {A} from "A";'.assertLengthOfImportDecl(20)
	}

	@Test
	def void testNodeLengthWithOptionalSemiPresent02() throws Exception {
		'''
			// top
			/**/import {A} from /**/ "A";    /******/
		'''.assertLengthOfImportDecl(25)
	}

	@Test
	def void testNodeLengthWithOptionalSemiPresent03() throws Exception {
		'''
			// top
			import {A} from /**/ "A" /******/ ; /**/
		'''.assertLengthOfImportDecl(35)
	}

	@Test
	def void testNodeLengthWithOptionalSemiPresent04() throws Exception {
		'''
			// top
			import {A}
			from
			"A"
			;    /******/
		'''.assertLengthOfImportDecl(18 + 3 * System.lineSeparator.length) // node length includes three line separators!
	}

	@Test
	def void testNodeLengthWithOptionalSemiPresent05() throws Exception {
		'''
			// top
			import {A}
			from
			"A"
			/**/
			/**/;    /******/
		'''.assertLengthOfImportDecl(26 + 4 * System.lineSeparator.length) // node length includes three line separators!
	}

	def private void assertLengthOfImportDecl(CharSequence code, int expectedLength) {
		val script = code.parseN4js;
		val importDecl = script.scriptElements.filter(ImportDeclaration).head;
		val node = NodeModelUtils.findActualNodeFor(importDecl);
		val actualLength = NodeModelUtilsN4.getNodeLengthWithASISupport(node);
		assertEquals(expectedLength, actualLength);
	}
}
