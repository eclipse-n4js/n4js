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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_084_Test {
	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	private def withVendorAndProject(Script script, String vendorID, String projectName) {
		script.eResource.contents.get(1) as TModule => [
			it.projectName = projectName
			it.vendorID = vendorID
		]
		return script
	}

	/**
	 * Scenario: Two public exported types with same name from same project / vendor
	 */
	@Test
	def void test_01() {
		val rs = resourceSetProvider.get();

		'''
			export public class Dup {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		'''
			export public class Dup {}
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {Dup} from "A"
			import {Dup} from "B"
			var d = new Dup();
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('A', 'B')

		script.assertError(N4JSPackage.Literals.IDENTIFIER_REF, IssueCodes.IMP_AMBIGUOUS, "The type Dup is ambiguously imported from A and B")
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name Dup is already used as name for named import Dup from A.")
	}

	/**
	 * Scenario: Two exported vars with same name from same project / vendor
	 */
	@Test
	def void test_02() {
		val rs = resourceSetProvider.get();

		'''
			export var dup: String = ""
		'''.parse(URI.createURI("A.n4js"), rs)
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("B.n4js"), rs)
		val script = '''
			import {dup} from "A"
			import {dup} from "B"
			var d = dup
		'''.parse(URI.createURI("C.n4js"), rs)

		script.assertError(N4JSPackage.Literals.IDENTIFIER_REF, IssueCodes.IMP_AMBIGUOUS, "The variable dup is ambiguously imported from A and B")
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name dup is already used as name for named import Dup from A.")
	}

	@Test
	def void test_03() {
		val rs = resourceSetProvider.get();

		'''
			var dup: String = ""
		'''.parse(URI.createURI("A.n4js"), rs)
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("B.n4js"), rs)
		val script = '''
			import {dup} from "A"
			import {dup} from "B"
			var d = dup
		'''.parse(URI.createURI("C.n4js"), rs)

		script.assertNoError(IssueCodes.IMP_AMBIGUOUS)
		//Since A.dup cannot be linked, import is unused, but also marked with linking error
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_UNRESOLVED, "Import of dup cannot be resolved.")
	}

	@Test
	def void test_04() {
		val rs = resourceSetProvider.get();
		'''
			var dup: String = ""
		'''.parse(URI.createURI("A.n4js"), rs)
		'''
			var dup: String = ""
		'''.parse(URI.createURI("B.n4js"), rs)
		val script = '''
			import {dup} from "A"
			import {dup} from "B"
			var d = dup
		'''.parse(URI.createURI("C.n4js"), rs)

		script.assertNoError(IssueCodes.IMP_AMBIGUOUS)
		script.assertNoIssue(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_UNUSED_IMPORT)
		//Since A.dup cannot be linked, an error is shown
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_UNRESOLVED, "Import of dup cannot be resolved.")
		//Local name used in second import of unresolved element conflicts with the previous one
		//but this error is not shown to avoid redundant error messages
		script.assertNoError(IssueCodes.IMP_LOCAL_NAME_CONFLICT)
	}

	@Test
	def void test_05() {
		val rs = resourceSetProvider.get();
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'B')
		val script = '''
			import {dup} from "A"
			import {dup} from "B"
			var d = dup
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('A', 'B')
		script.assertNoError(IssueCodes.IMP_AMBIGUOUS)
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS, "The variable dup is not visible.")
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name dup is already used as name for named import dup from A.")
	}

	@Test
	def void test_06() {
		val rs = resourceSetProvider.get();
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'B')
		val script = '''
			import {dup} from "A"
			import {dup} from "B"
			var d = dup
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertNoError(IssueCodes.IMP_AMBIGUOUS)
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name dup is already used as name for named import dup from A.")
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS, "The variable dup is not visible.")
		script.assertError(N4JSPackage.Literals.IDENTIFIER_REF, IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS, "The variable dup is not visible.")
	}

	@Test
	def void test_07() {
		val rs = resourceSetProvider.get();
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		'''
			export var dup: String = ""
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'B')
		val script = '''
			import {dup} from "A"
			import {dup} from "B"
			var d = dup
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertNoError(IssueCodes.IMP_AMBIGUOUS)
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_LOCAL_NAME_CONFLICT, "Name dup is already used as name for named import dup from A.")
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS, "The variable dup is not visible.")
		script.assertError(N4JSPackage.Literals.IDENTIFIER_REF, IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS, "The variable dup is not visible.")
	}

	@Test
	def void test_08() {
		val rs = resourceSetProvider.get();
		'''
			class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as Namespace from "A"
			var s = ""
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertNoErrors
	}

	@Test
	def void test_09() {
		val rs = resourceSetProvider.get();
		'''
			class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {A} from "A"
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type A is not visible.')
		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_UNRESOLVED, "Import of A cannot be resolved.")
	}
	
	@Test
	def void test_09b() {
		val rs = resourceSetProvider.get();
		'''
			export public class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {A} from "A"
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertWarning(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.IMP_UNUSED_IMPORT, "The import of A is unused.")
	}
	
	@Test
	def void test_10() {
		val rs = resourceSetProvider.get();
		'''
			class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var a: N.A = null
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertError(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type A is not visible')
	}

	@Test
	def void test_11() {
		val rs = resourceSetProvider.get();
		'''
			class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {A} from "A"
			var a: A = null
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertError(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type A is not visible')
	}

	@Test
	def void test_12() {
		val rs = resourceSetProvider.get();
		'''
			class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var a = N.A
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type A is not visible')
	}

	@Test
	def void test_13() {
		val rs = resourceSetProvider.get();
		'''
			class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {A} from "A"
			var a = A
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertError(N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type A is not visible')
		script.assertError(N4JSPackage.Literals.IDENTIFIER_REF, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type A is not visible')
	}

	@Test
	def void test_14() {
		val rs = resourceSetProvider.get();
		'''
			class A {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var a: N.A = ""
		'''.parse(URI.createURI("C.n4js"), rs).withVendorAndProject('C', 'B')

		script.assertError(TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type A is not visible')
	}

	@Test
	def void test_15() {
		val rs = resourceSetProvider.get();
		'''
			export class A {}
		'''.parse(URI.createURI("A.n4js?C|B"), rs).withVendorAndProject('C', 'B')
		'''
			export class A {}
		'''.parse(URI.createURI("A.n4js?A|B"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var a: N.A = ""
		'''.parse(URI.createURI("C.n4js?C|B"), rs).withVendorAndProject('C', 'B')

		script.assertNoError(IssueCodes.VIS_ILLEGAL_TYPE_ACCESS)
		val typeRef = script.eAllContents.filter(ParameterizedTypeRef).head
		Assert.assertEquals("A.n4js?C|B", typeRef.declaredType.eResource.URI.toString)
	}

	@Test
	def void test_16() {
		val rs = resourceSetProvider.get();
		'''
			export var s: String = ""
		'''.parse(URI.createURI("A.n4js"), rs)
		val script = '''
			import {s} from "A"
			var x = s
		'''.parse(URI.createURI("B.n4js"), rs)

		script.assertNoErrors()
	}

	@Test
	def void test_17() {
		val rs = resourceSetProvider.get();
		'''
			export @Internal public var s: String = ""
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var x = N.s
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS, 'The variable s is not visible')
	}

	@Test
	def void test_18() {
		val rs = resourceSetProvider.get();
		'''
			export @Internal public var s: String = ""
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {s} from "A"
			var x = s
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')

		script.assertNoErrors()
	}

	@Test
	def void test_19() {
		val rs = resourceSetProvider.get();
		'''
			export public var s: String = ""
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {s} from "A"
			var x = s
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')

		script.assertNoErrors()
	}

	@Test
	def void test_20() {
		val rs = resourceSetProvider.get();
		'''
			export function f() {}
		'''.parse(URI.createURI("A.n4js"), rs)
		val script = '''
			import {f} from "A"
			var x = f
		'''.parse(URI.createURI("B.n4js"), rs)

		script.assertNoErrors()
	}

	@Test
	def void test_21() {
		val rs = resourceSetProvider.get();
		'''
			export @Internal public function f() {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var x = N.f
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_FUN_ACCESS, 'The function f is not visible')
	}

	@Test
	def void test_22() {
		val rs = resourceSetProvider.get();
		'''
			export @Internal public function f() {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {f} from "A"
			var x = f
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')

		script.assertNoErrors()
	}

	@Test
	def void test_23() {
		val rs = resourceSetProvider.get();
		'''
			export public function f() {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import {f} from "A"
			var x = f
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')

		script.assertNoErrors()
	}

	@Test
	def void test_24() {
		val rs = resourceSetProvider.get();
		'''
			export public enum E { Literal }
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var x = N.E.Literal
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')

		script.assertNoErrors()
	}

	@Test
	def void test_25() {
		val rs = resourceSetProvider.get();
		'''
			export @Internal public enum E { Literal }
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var x = N.E.Literal
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_TYPE_ACCESS, 'The type E is not visible')
		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The enum literal Literal is not visible')
	}

	@Test
	def void test_26() {
		val rs = resourceSetProvider.get();
		'''
			export @Internal public enum E { Literal }
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var x = N.E.Literal
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')

		script.assertNoErrors()
	}

	@Test
	def void test_27() {
		val rs = resourceSetProvider.get();
		'''
			class Base {
				@Internal public m(): void {}
			}
			export public class Sub extends Base {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var s: N.Sub;
			s.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')

		script.assertNoErrors()
	}

	@Test
	def void test_28() {
		val rs = resourceSetProvider.get();
		'''
			class Base {
				@Internal public m(): void {}
			}
			export public class Sub extends Base {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var s: N.Sub;
			s.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The method m is not visible')
	}

	@Test
	def void test_29() {
		val rs = resourceSetProvider.get();
		'''
			export class C {
				m(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var c: N.C;
			c.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'B')

		script.assertNoErrors
	}

	@Test
	def void test_30() {
		val rs = resourceSetProvider.get();
		'''
			export class C {
				private m(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var c: N.C;
			c.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'B')

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The method m is not visible')
	}

	@Test
	def void test_31() {
		val rs = resourceSetProvider.get();
		'''
			export class A {}
			export class B extends A {
				protected m(): void {}
			}
			export class C extends B {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var c: N.C;
			c.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'B')

		script.assertNoErrors()
	}

	@Test
	def void test_32() {
		val rs = resourceSetProvider.get();
		'''
			export class A {}
			export class B extends A {
				protected m(): void {}
			}
			export class C extends B {}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var c: N.C;
			c.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')

		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The method m is not visible')
	}
}
