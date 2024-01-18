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
package org.eclipse.n4js.ide.tests.codeActions;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.AbstractOrganizeImportsTest;
import org.junit.Test;

/**
 * Like {@link OrganizeImportsTest}, but covers cases in which a missing import has to be added during organize imports.
 */
public class OrganizeImportsAddMissingTest extends AbstractOrganizeImportsTest {

	@Test
	public void testSimple() {
		test("""
				A01;
				""", List.of(
				"(Error, [0:0 - 0:3], Couldn't resolve reference to IdentifiableElement 'A01'.)"), """
						import {A01} from "A";
						A01;
						""");
	}

	@Test
	public void testAddImportForTypeAlias() {
		test("""
				let x: SomeTypeAlias;
				""", List.of(
				"(Error, [0:7 - 0:20], Couldn't resolve reference to Type 'SomeTypeAlias'.)"), """
						import {SomeTypeAlias} from "A";
						let x: SomeTypeAlias;
						""");
	}

	@Test
	public void testAddImportFromDts() {
		test("""
				let x: Dts01;
				""", List.of(
				"(Error, [0:7 - 0:12], Couldn't resolve reference to Type 'Dts01'.)"), """
						import {Dts01} from "Dts";
						let x: Dts01;
						""");
	}

	@Test
	public void testAddImportFromDts_declaredModule() {
		test("""
				let x: DtsInDeclModule;
				""", List.of(
				"(Error, [0:7 - 0:22], Couldn't resolve reference to Type 'DtsInDeclModule'.)"), """
						import {DtsInDeclModule} from "a/b/declModule";
						let x: DtsInDeclModule;
						""");
	}

	@Test
	public void testDoNotAddMultipleImportsForSameElement() {
		test("""
				A01;
				A01;
				A01;
				""", List.of(
				"(Error, [0:0 - 0:3], Couldn't resolve reference to IdentifiableElement 'A01'.)",
				"(Error, [1:0 - 1:3], Couldn't resolve reference to IdentifiableElement 'A01'.)",
				"(Error, [2:0 - 2:3], Couldn't resolve reference to IdentifiableElement 'A01'.)"), """
						import {A01} from "A";
						A01;
						A01;
						A01;
						""");
	}

	@Test
	public void testDoNotAddImportForAmbiguousUnresolvedReference() {
		test("""
				Duplicate;
				""", List.of(
				"(Error, [0:0 - 0:9], Couldn't resolve reference to IdentifiableElement 'Duplicate'.)"), """
						Duplicate;
						""");
	}

	@Test
	public void testDoNotAddImportForIncompleteReference() {
		test("""
				ClassWithALongNa;
				""", List.of(
				"(Error, [0:0 - 0:16], Couldn't resolve reference to IdentifiableElement 'ClassWithALongNa'.)"), """
						ClassWithALongNa;
						""");
	}

	/**
	 * In this case, we cannot add an import <code>import {A02} from "A"</code> because it would be in conflict with the
	 * namespace import. But we also cannot change the unresolved reference <code>A02;</code> to <code>N.A02;</code>
	 * because, for now, we do not want organize imports to touch any code except the imports.
	 * <p>
	 * Thus, organize imports should do nothing in this case.
	 */
	@Test
	public void testDoNotAddImportIfNamespaceImportAlreadyExists() {
		test("""
				import * as N from "A";
				N.A01;
				A02;
				""", List.of(
				"(Error, [2:0 - 2:3], A02 has been imported as N.A02.)"), """
						import * as N from "A";
						N.A01;
						A02;
						""");
	}
}
