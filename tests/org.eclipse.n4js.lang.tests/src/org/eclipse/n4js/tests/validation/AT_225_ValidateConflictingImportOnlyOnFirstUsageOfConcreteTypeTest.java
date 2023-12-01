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
package org.eclipse.n4js.tests.validation;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.helper.mock.MockWorkspaceSupplier;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * @todo remove wildcard import tests or rephrase them (since wildcard import is not supported anymore)
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AT_225_ValidateConflictingImportOnlyOnFirstUsageOfConcreteTypeTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;
	@Inject
	MockWorkspaceSupplier mockWorkspaceSupplier;

	@Inject
	XtextResourceSet rs;

	@Before
	public void setUp() throws Exception {
		parseHelper.parse("""
				export class X {}
				export class Z {}
				""", mockWorkspaceSupplier.toTestProjectURI("a/X.n4js"), rs);
		parseHelper.parse("""
				export class Y {}
				export class Z {}
				""", mockWorkspaceSupplier.toTestProjectURI("b/Y.n4js"), rs);
	}

	@After
	public void tearDown() {
		rs.getResources().clear();
	}

	@Test
	@Ignore
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				import * as Na from 'a/A';
				import * as Nb from 'b/A';
				""", rs);
		valTestHelper.assertNoErrors(script);
	}

	@Test
	@Ignore
	public void test_02() throws Exception {
		Script script = parseHelper.parse("""
				import * as Na from 'a/A';
				import * as Nb from 'b/A';
				var b: Na.B
				""", rs);
		valTestHelper.assertNoErrors(script);
	}

	@Test
	@Ignore
	public void test_03() throws Exception {
		Script script = parseHelper.parse("""
				import * from 'a/A';
				import * from 'b/A';
				import { A } from 'a/A';
				var a: A
				""", rs);
		valTestHelper.assertNoErrors(script);
	}

	@Test
	@Ignore
	public void test_04() throws Exception {
		Script script = parseHelper.parse("""
				import { A } from 'a/A';
				import * from 'a/A';
				import * from 'b/A';
				var A a
				""", rs);
		valTestHelper.assertNoErrors(script);
	}

	@Test
	@Ignore
	public void test_05() throws Exception {
		Script script = parseHelper.parse("""
				import * from 'a/A';
				import * from 'b/A';
				var a: A
				""", rs);
		valTestHelper.assertError(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF,
				IssueCodes.IMP_AMBIGUOUS_WILDCARD.name(), "The type A is ambiguously imported from a.A and b.A");
	}

	@Test
	@Ignore
	public void test_06() throws Exception {
		Script script = parseHelper.parse("""
				import * from 'b/A';
				import * from 'a/A';
				import * from 'b/A' // duplicate wildcard;
				var a: A
				""", rs);
		valTestHelper.assertError(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF,
				IssueCodes.IMP_AMBIGUOUS_WILDCARD.name(), "The type A is ambiguously imported from b.A and a.A");
	}
	// ================

	@Test
	public void spec_DuplicatedImportStatement_A() throws Exception {
		Script script = parseHelper.parse("""
				import * as N from 'a/X';
				import * as N from 'a/X';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_DECLARATION,
				IssueCodes.IMP_STMT_DUPLICATE_NAMESPACE.name(),
				"Duplicate namespace import statement with name N and imported module a/X.");
	}

	@Test
	public void spec_DuplicatedImportStatement_B() throws Exception {
		Script script = parseHelper.parse("""
				import { X } from 'a/X';
				import { X } from 'a/X';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_DECLARATION,
				IssueCodes.IMP_STMT_DUPLICATE_NAMED.name(),
				"Duplicate named import statement with local name X and imported module a/X.");
	}

	@Test
	public void spec_DuplicatedImport_A() throws Exception {
		Script script = parseHelper.parse("""
				import * as N1 from 'a/X';
				import * as N2 from 'a/X';
				var x: N1.X;
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER,
				IssueCodes.IMP_DUPLICATE_NAMESPACE.name(),
				"Element N2.X of a/X is already imported in namespace N1.");
	}

	@Test
	public void spec_DuplicatedImport_B() throws Exception {
		Script script = parseHelper.parse("""
				import { Z } from 'a/X';
				import { Z as Z1 } from 'a/X';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE.name(),
				"Z from a/X is already imported as Z.");
	}

	@Test
	public void spec_DuplicatedImport_C() throws Exception {
		Script script = parseHelper.parse("""
				import { Z as Z1 } from 'a/X';
				import { Z } from 'a/X';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE.name(),
				"Z from a/X is already imported as Z1.");
	}

	@Test
	public void spec_DuplicatedImport_D() throws Exception {
		Script script = parseHelper.parse("""
				import { Z as Z1 } from 'a/X';
				import { Z as Z2 } from 'a/X';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE.name(),
				"Z from a/X is already imported as Z1.");
	}

	@Test
	public void spec_DuplicatedImport_E() throws Exception {
		Script script = parseHelper.parse("""
				import * as NX from 'a/X';
				import { Z } from 'a/X';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE.name(),
				"Z from a/X is already imported as NX.Z.");
	}

	@Test
	public void spec_DuplicatedImport_F() throws Exception {
		Script script = parseHelper.parse("""
				import { Z } from 'a/X';
				import * as NX from 'a/X';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE.name(),
				"Z from a/X is already imported as Z.");
	}

	@Test
	public void spec_NameConflict_A() throws Exception {
		Script script = parseHelper.parse("""
				import * as NX from 'a/X';
				import * as NX from 'b/Y';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER,
				IssueCodes.IMP_LOCAL_NAME_CONFLICT.name(),
				"Name NX is already used as namespace name for a/X.");
	}

	@Test
	public void spec_NameConflict_B() throws Exception {
		Script script = parseHelper.parse("""
				import { X } from 'a/X';
				import { Y as X } from 'b/Y';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER,
				IssueCodes.IMP_LOCAL_NAME_CONFLICT.name(),
				"Name X is already used as name for named import X from a/X.");
	}

	@Test
	public void spec_NameConflict_C() throws Exception {
		Script script = parseHelper.parse("""
				import { X as N } from 'a/X';
				import * as N from 'b/Y';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER,
				IssueCodes.IMP_LOCAL_NAME_CONFLICT.name(),
				"Name N is already used as alias name for named import X from a/X.");
	}

	@Test
	public void spec_NameConflict_D() throws Exception {
		Script script = parseHelper.parse("""
				import * as N from 'a/X';
				import { Y as N } from 'b/Y';
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.IMPORT_SPECIFIER,
				IssueCodes.IMP_LOCAL_NAME_CONFLICT.name(),
				"Name N is already used as namespace name for a/X.");
	}

	@Test
	public void spec_NameConflict_E() throws Exception {
		Script script = parseHelper.parse("""
				import { X } from 'a/X';
				class X{};
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.N4_CLASS_DECLARATION,
				IssueCodes.AST_NAME_DUPLICATE_ERR.name(),
				"Class X duplicates named import X (line 1).");
	}

	@Test
	public void spec_NameConflict_F() throws Exception {
		Script script = parseHelper.parse("""
				import * as X from 'a/X';
				class X{};
				""", rs);
		valTestHelper.assertError(script, N4JSPackage.Literals.N4_CLASS_DECLARATION,
				IssueCodes.AST_NAME_DUPLICATE_ERR.name(),
				"Class X duplicates namespace import specifier X (line 1).");
	}

	@Test
	public void spec_LocalNamesForMultipleTypesWithTheSameName() throws Exception {

		parseHelper.parse("""
				export class Z {}
				""", mockWorkspaceSupplier.toTestProjectURI("c/Z.n4js"), rs);

		Script script = parseHelper.parse("""
				import { Z } from 'c/Z';
				import { Z as Z1 } from 'b/Y';
				import * as N from 'a/X';
				var a: any;
				""", rs);
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void spec_AccessAlaisedName() throws Exception {
		Script script = parseHelper.parse("""
				import { X as XXX } from 'a/X';
				var x: X;
				""", rs);
		valTestHelper.assertError(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF,
				IssueCodes.IMP_PLAIN_ACCESS_OF_ALIASED_TYPE.name(), "X has been imported as XXX.");
	}

	@Test
	public void spec_AccessNamespacedName() throws Exception {
		Script script = parseHelper.parse("""
				import *  as N from 'a/X';
				var x: X;
				""", rs);
		valTestHelper.assertError(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF,
				IssueCodes.IMP_PLAIN_ACCESS_OF_NAMESPACED_TYPE.name(), "X has been imported as N.X.");
	}

}
