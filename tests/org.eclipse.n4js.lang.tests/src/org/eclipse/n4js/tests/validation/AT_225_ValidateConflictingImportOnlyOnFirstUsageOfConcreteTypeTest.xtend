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
package org.eclipse.n4js.tests.validation

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.tests.helper.mock.MockWorkspaceSupplier
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @todo remove wildcard import tests or rephrase them (since wildcard import is not supported anymore)
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_225_ValidateConflictingImportOnlyOnFirstUsageOfConcreteTypeTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper
	@Inject private MockWorkspaceSupplier mockWorkspaceSupplier;

	@Inject
	XtextResourceSet rs

	@Before
	def void setUp() {
		'''
			export class X {}
			export class Z {}
		'''.parse(mockWorkspaceSupplier.toTestProjectURI("a/X.n4js"), rs)
		'''
			export class Y {}
			export class Z {}
		'''.parse(mockWorkspaceSupplier.toTestProjectURI("b/Y.n4js"), rs)
	}

	@After
	def void tearDown(){
		rs.resources.clear
	}

	@Test
	@Ignore
	def void test_01() {
		val script = '''
			import * as Na from 'a/A'
			import * as Nb from 'b/A'
		'''.parse(rs)
		script.assertNoErrors
	}

	@Test
	@Ignore
	def void test_02() {
		val script = '''
			import * as Na from 'a/A'
			import * as Nb from 'b/A'
			var b: Na.B
		'''.parse(rs)
		script.assertNoErrors
	}

	@Test
	@Ignore
	def void test_03() {
		val script = '''
			import * from 'a/A'
			import * from 'b/A'
			import { A } from 'a/A'
			var a: A
		'''.parse(rs)
		script.assertNoErrors
	}

	@Test
	@Ignore
	def void test_04() {
		val script = '''
			import { A } from 'a/A'
			import * from 'a/A'
			import * from 'b/A'
			var A a
		'''.parse(rs)
		script.assertNoErrors
	}

	@Test
	@Ignore
	def void test_05() {
		val script = '''
			import * from 'a/A'
			import * from 'b/A'
			var a: A
		'''.parse(rs)
		script.assertError(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IssueCodes.IMP_AMBIGUOUS_WILDCARD, "The type A is ambiguously imported from a.A and b.A")
	}

	@Test
	@Ignore
	def void test_06() {
		val script = '''
			import * from 'b/A'
			import * from 'a/A'
			import * from 'b/A' // duplicate wildcard
			var a: A
		'''.parse(rs)
		script.assertError(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IssueCodes.IMP_AMBIGUOUS_WILDCARD, "The type A is ambiguously imported from b.A and a.A")
	}
//================

	@Test
	def void spec_DuplicatedImportStatement_A() {
		val script = '''
			import * as N from 'a/X';
			import * as N from 'a/X';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_DECLARATION, IssueCodes.IMP_STMT_DUPLICATE_NAMESPACE, "Duplicate namespace import statement with name N and imported module a/X.")
	}

	@Test
	def void spec_DuplicatedImportStatement_B() {
		val script = '''
			import { X } from 'a/X';
			import { X } from 'a/X';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_DECLARATION, IssueCodes.IMP_STMT_DUPLICATE_NAMED, "Duplicate named import statement with local name X and imported module a/X.")
	}

	@Test
	def void spec_DuplicatedImport_A() {
		val script = '''
			import * as N1 from 'a/X';
			import * as N2 from 'a/X';
			var x: N1.X;
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE_NAMESPACE, "Elements of a/X are already imported in namespace N1.")
	}

	@Test
	def void spec_DuplicatedImport_B() {
		val script = '''
			import { Z } from 'a/X';
			import { Z as Z1 } from 'a/X';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE, "Z from a/X is already imported as Z.")
	}

	@Test
	def void spec_DuplicatedImport_C() {
		val script = '''
			import { Z as Z1 } from 'a/X';
			import { Z } from 'a/X';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE, "Z from a/X is already imported as Z1.")
	}

	@Test
	def void spec_DuplicatedImport_D() {
		val script = '''
			import { Z as Z1 } from 'a/X';
			import { Z as Z2 } from 'a/X';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE, "Z from a/X is already imported as Z1.")
	}

	@Test
	def void spec_DuplicatedImport_E() {
		val script = '''
			import * as NX from 'a/X';
			import { Z } from 'a/X';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE, "Z from a/X is already imported as NX.Z.")
	}

	@Test
	def void spec_DuplicatedImport_F() {
		val script = '''
			import { Z } from 'a/X';
			import * as NX from 'a/X';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_DUPLICATE, "Z from a/X is already imported as Z.")
	}

	@Test
	def void spec_NameConflict_A() {
		val script = '''
			import * as NX from 'a/X';
			import * as NX from 'b/Y';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name NX is already used as namespace name for a/X.")
	}

	@Test
	def void spec_NameConflict_B() {
		val script = '''
			import { X } from 'a/X';
			import { Y as X } from 'b/Y';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name X is already used as name for named import X from a/X.")
	}

	@Test
	def void spec_NameConflict_C() {
		val script = '''
			import { X as N } from 'a/X';
			import * as N from 'b/Y';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name N is already used as alias name for named import X from a/X.")
	}

	@Test
	def void spec_NameConflict_D() {
		val script = '''
			import * as N from 'a/X';
			import { Y as N } from 'b/Y';
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name N is already used as namespace name for a/X.")
	}

	@Test
	def void spec_NameConflict_E() {
		val script = '''
			import { X } from 'a/X';
			class X{};
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.N4_CLASS_DECLARATION, IssueCodes.AST_NAME_DUPLICATE_ERR, "Class X duplicates named import X (line 1).")
	}

	@Test
	def void spec_NameConflict_F() {
		val script = '''
			import * as X from 'a/X';
			class X{};
		'''.parse(rs)
		script.assertError(N4JSPackage.Literals.N4_CLASS_DECLARATION, IssueCodes.AST_NAME_DUPLICATE_ERR, "Class X duplicates namespace import specifier X (line 1).")
	}

	@Test
	def void spec_LocalNamesForMultipleTypesWithTheSameName() {

		'''
			export class Z {}
		'''.parse(mockWorkspaceSupplier.toTestProjectURI("c/Z.n4js"), rs)


		val script = '''
			import { Z } from 'c/Z';
			import { Z as Z1 } from 'b/Y';
			import * as N from 'a/X';
			var a: any;
		'''.parse(rs)
		script.assertNoErrors
	}

	@Test
	def void spec_AccessAlaisedName() {
		val script = '''
			import { X as XXX } from 'a/X';
			var x: X;
		'''.parse(rs)
		script.assertError(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IssueCodes.IMP_PLAIN_ACCESS_OF_ALIASED_TYPE, "X has been imported as XXX.")
	}

	@Test
	def void spec_AccessNamespacedName() {
		val script = '''
			import *  as N from 'a/X';
			var x: X;
		'''.parse(rs)
		script.assertError(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IssueCodes.IMP_PLAIN_ACCESS_OF_NAMESPACED_TYPE, "X has been imported as N.X.")
	}

}
