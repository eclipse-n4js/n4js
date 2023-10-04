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
package org.eclipse.n4js.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;

import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.junit.Test;

/**
 * Tests {@link NodeModelUtilsN4}.
 */
class NodeModelUtilsN4Test extends AbstractN4JSTest {

	@Test
	public void testNodeLengthWithOptionalSemiOmitted01() throws Exception {
		assertLengthOfImportDecl("import {A} from \"A\"", 19);
	}

	@Test
	public void testNodeLengthWithOptionalSemiOmitted02() throws Exception {
		assertLengthOfImportDecl("""
					// top
					/**/import {A} from /**/ "A"    /******/
				""", 24);
	}

	@Test
	public void testNodeLengthWithOptionalSemiOmitted03() throws Exception {
		assertLengthOfImportDecl("""
					// top
					import { A } from "A"      ««« don't remove the trailing white space!


					//
					let x = 42;
				""", 21);
	}

	@Test
	public void testNodeLengthWithOptionalSemiOmitted04() throws Exception {
		assertLengthOfImportDecl("""
					// top


					import {A}
					from
					"A"


				""", 17 + 2 * System.lineSeparator().length());// node length includes two line separators!
	}

	@Test
	public void testNodeLengthWithOptionalSemiPresent01() throws Exception {
		assertLengthOfImportDecl("import {A} from \"A\";", 20);
	}

	@Test
	public void testNodeLengthWithOptionalSemiPresent02() throws Exception {
		assertLengthOfImportDecl("""
					// top
					/**/import {A} from /**/ "A";    /******/
				""", 25);
	}

	@Test
	public void testNodeLengthWithOptionalSemiPresent03() throws Exception {
		assertLengthOfImportDecl("""
					// top
					import {A} from /**/ "A" /******/ ; /**/
				""", 35);
	}

	@Test
	public void testNodeLengthWithOptionalSemiPresent04() throws Exception {
		assertLengthOfImportDecl("""
					// top
					import {A}
					from
					"A"
					;    /******/
				""", 18 + 3 * System.lineSeparator().length());// node length includes three line separators!
	}

	@Test
	public void testNodeLengthWithOptionalSemiPresent05() throws Exception {
		assertLengthOfImportDecl("""
					// top
					import {A}
					from
					"A"
					/**/
					/**/;    /******/
				""", 26 + 4 * System.lineSeparator().length()); // node length includes four line separators!
	}

	private void assertLengthOfImportDecl(CharSequence code, int expectedLength) throws Exception {
		Script script = parserHelper.parseN4js(code);

		ImportDeclaration importDecl = filter(script.getScriptElements(), ImportDeclaration.class).iterator().next();
		INode node = NodeModelUtils.findActualNodeFor(importDecl);
		int actualLength = NodeModelUtilsN4.getNodeLengthWithASISupport(node);
		assertEquals(expectedLength, actualLength);
	}
}
