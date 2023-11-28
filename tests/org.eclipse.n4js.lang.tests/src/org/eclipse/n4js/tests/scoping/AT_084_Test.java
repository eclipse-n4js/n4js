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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.n4js.validation.IssueCodes.IMP_AMBIGUOUS;
import static org.eclipse.n4js.validation.IssueCodes.IMP_LOCAL_NAME_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.IMP_NOT_EXPORTED;
import static org.eclipse.n4js.validation.IssueCodes.IMP_UNUSED_IMPORT;
import static org.eclipse.n4js.validation.IssueCodes.VIS_ILLEGAL_FUN_ACCESS;
import static org.eclipse.n4js.validation.IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS;
import static org.eclipse.n4js.validation.IssueCodes.VIS_ILLEGAL_TYPE_ACCESS;
import static org.eclipse.n4js.validation.IssueCodes.VIS_ILLEGAL_VARIABLE_ACCESS;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AT_084_Test {
	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	private Script withVendorAndProject(Script script, String vendorID, String projectID) {
		TModule module = (TModule) script.eResource().getContents().get(1);
		module.setProjectID(projectID);
		module.setVendorID(vendorID);
		return script;
	}

	/**
	 * Scenario: Two public exported types with same name from same project / vendor
	 */
	@Test
	public void test_01() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		withVendorAndProject(parseHelper.parse("""
					export public class Dup {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		withVendorAndProject(parseHelper.parse("""
					export public class Dup {}
				""", URI.createURI("B.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {Dup} from "A";
					import {Dup} from "B";
					var d = new Dup();
				""", URI.createURI("C.n4js"), rs), "A", "B");

		valTestHelper.assertError(script, N4JSPackage.Literals.IDENTIFIER_REF, IMP_AMBIGUOUS.name(),
				"The type Dup is ambiguously imported from A and B");
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				IMP_LOCAL_NAME_CONFLICT.name(), "Name Dup is already used as name for named import Dup from A.");
	}

	/**
	 * Scenario: Two exported vars with same name from same project / vendor
	 */
	@Test
	public void test_02() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("A.n4js"), rs);
		parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("B.n4js"), rs);
		Script script = parseHelper.parse("""
					import {dup} from "A";
					import {dup} from "B";
					var d = dup
				""", URI.createURI("C.n4js"), rs);

		valTestHelper.assertError(script, N4JSPackage.Literals.IDENTIFIER_REF, IMP_AMBIGUOUS.name(),
				"The variable dup is ambiguously imported from A and B");
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				IMP_LOCAL_NAME_CONFLICT.name(), "Name dup is already used as name for named import Dup from A.");
	}

	@Test
	public void test_03() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();

		parseHelper.parse("""
					var dup: String = ""
				""", URI.createURI("A.n4js"), rs);
		parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("B.n4js"), rs);
		Script script = parseHelper.parse("""
					import {dup} from "A";
					import {dup} from "B";
					var d = dup
				""", URI.createURI("C.n4js"), rs);

		valTestHelper.assertNoError(script, IMP_AMBIGUOUS.name());
		valTestHelper.assertNoIssue(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IMP_UNUSED_IMPORT.name());
		// Since A.dup cannot be linked, import is unused, but also marked with linking error;
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IMP_NOT_EXPORTED.name(),
				"Element dup is not exported.");
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				IMP_LOCAL_NAME_CONFLICT.name(), "Name dup is already used as name for named import dup from A.");
	}

	@Test
	public void test_04() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		parseHelper.parse("""
					var dup: String = ""
				""", URI.createURI("A.n4js"), rs);
		parseHelper.parse("""
					var dup: String = ""
				""", URI.createURI("B.n4js"), rs);
		Script script = parseHelper.parse("""
					import {dup} from "A";
					import {dup} from "B";
					var d = dup
				""", URI.createURI("C.n4js"), rs);

		valTestHelper.assertNoError(script, IMP_AMBIGUOUS.name());
		valTestHelper.assertNoIssue(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IMP_UNUSED_IMPORT.name());
		// Since A.dup cannot be linked, an error is shown
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IMP_NOT_EXPORTED.name(),
				"Element dup is not exported.");
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				IMP_LOCAL_NAME_CONFLICT.name(), "Name dup is already used as name for named import dup from A.");
	}

	@Test
	public void test_05() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("A.n4js"), rs), "A", "B");
		withVendorAndProject(parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("B.n4js"), rs), "B", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {dup} from "A";
					import {dup} from "B";
					var d = dup
				""", URI.createURI("C.n4js"), rs), "A", "B");
		valTestHelper.assertNoError(script, IMP_AMBIGUOUS.name());
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				VIS_ILLEGAL_VARIABLE_ACCESS.name(), "The variable dup is not visible.");
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				IMP_LOCAL_NAME_CONFLICT.name(), "Name dup is already used as name for named import dup from A.");
	}

	@Test
	public void test_06() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("A.n4js"), rs), "A", "B");
		withVendorAndProject(parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("B.n4js"), rs), "B", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {dup} from "A";
					import {dup} from "B";
					var d = dup
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertNoError(script, IMP_AMBIGUOUS.name());
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				IMP_LOCAL_NAME_CONFLICT.name(), "Name dup is already used as name for named import dup from A.");
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				VIS_ILLEGAL_VARIABLE_ACCESS.name(), "The variable dup is not visible.");
		valTestHelper.assertError(script, N4JSPackage.Literals.IDENTIFIER_REF, VIS_ILLEGAL_VARIABLE_ACCESS.name(),
				"The variable dup is not visible.");
	}

	@Test
	public void test_07() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("A.n4js"), rs), "A", "B");
		withVendorAndProject(parseHelper.parse("""
					export var dup: String = ""
				""", URI.createURI("B.n4js"), rs), "B", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {dup} from "A";
					import {dup} from "B";
					var d = dup
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertNoError(script, IMP_AMBIGUOUS.name());
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				IMP_LOCAL_NAME_CONFLICT.name(), "Name dup is already used as name for named import dup from A.");
		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER,
				VIS_ILLEGAL_VARIABLE_ACCESS.name(), "The variable dup is not visible.");
		valTestHelper.assertError(script, N4JSPackage.Literals.IDENTIFIER_REF, VIS_ILLEGAL_VARIABLE_ACCESS.name(),
				"The variable dup is not visible.");
	}

	@Test
	public void test_08() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as Namespace from "A";
					var s = ""
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_09() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {A} from "A";
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IMP_NOT_EXPORTED.name(),
				"Element A is not exported.");
	}

	@Test
	public void test_09b() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export public class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {A} from "A";
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertWarning(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IMP_UNUSED_IMPORT.name(),
				"The import of A is unused.");
	}

	@Test
	public void test_10() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var a: N.A = null
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertError(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IMP_NOT_EXPORTED.name(),
				"Element A is not exported.");
	}

	@Test
	public void test_11() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {A} from "A";
					var a: A = null
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertError(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF,
				VIS_ILLEGAL_TYPE_ACCESS.name(), "The type A is not visible");
	}

	@Test
	public void test_12() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var a = N.A
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				IMP_NOT_EXPORTED.name(), "Element A is not exported.");
	}

	@Test
	public void test_13() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {A} from "A";
					var a = A
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertError(script, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER, IMP_NOT_EXPORTED.name(),
				"Element A is not exported.");
		valTestHelper.assertError(script, N4JSPackage.Literals.IDENTIFIER_REF, VIS_ILLEGAL_TYPE_ACCESS.name(),
				"The type A is not visible");
	}

	@Test
	public void test_14() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class A {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var a: N.A = ""
				""", URI.createURI("C.n4js"), rs), "C", "B");

		valTestHelper.assertError(script, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF, IMP_NOT_EXPORTED.name(),
				"Element A is not exported.");
	}

	@Test
	public void test_15() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export class A {}
				""", URI.createURI("A.n4js?C|B"), rs), "C", "B");
		withVendorAndProject(parseHelper.parse("""
					export class A {}
				""", URI.createURI("A.n4js?A|B"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var a: N.A = ""
				""", URI.createURI("C.n4js?C|B"), rs), "C", "B");

		valTestHelper.assertNoError(script, VIS_ILLEGAL_TYPE_ACCESS.name());
		ParameterizedTypeRef typeRef = head(filter(script.eAllContents(), ParameterizedTypeRef.class));
		Assert.assertEquals("A.n4js?C|B", typeRef.getDeclaredType().eResource().getURI().toString());
	}

	@Test
	public void test_16() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		parseHelper.parse("""
					export var s: String = ""
				""", URI.createURI("A.n4js"), rs);
		Script script = parseHelper.parse("""
					import {s} from "A";
					var x = s
				""", URI.createURI("B.n4js"), rs);

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_17() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export @Internal public var s: String = ""
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var x = N.s
				""", URI.createURI("B.n4js"), rs), "B", "C");

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				VIS_ILLEGAL_VARIABLE_ACCESS.name(), "The variable s is not visible");
	}

	@Test
	public void test_18() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export @Internal public var s: String = ""
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {s} from "A";
					var x = s
				""", URI.createURI("B.n4js"), rs), "A", "C");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_19() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export public var s: String = ""
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {s} from "A";
					var x = s
				""", URI.createURI("B.n4js"), rs), "B", "C");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_20() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		parseHelper.parse("""
					export function f() {}
				""", URI.createURI("A.n4js"), rs);
		Script script = parseHelper.parse("""
					import {f} from "A";
					var x = f
				""", URI.createURI("B.n4js"), rs);

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_21() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export @Internal public function f() {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var x = N.f
				""", URI.createURI("B.n4js"), rs), "B", "C");

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				VIS_ILLEGAL_FUN_ACCESS.name(), "The function f is not visible");
	}

	@Test
	public void test_22() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export @Internal public function f() {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {f} from "A";
					var x = f
				""", URI.createURI("B.n4js"), rs), "A", "C");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_23() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export public function f() {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import {f} from "A";
					var x = f
				""", URI.createURI("B.n4js"), rs), "B", "C");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_24() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export public enum E { Literal }
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var x = N.E.Literal
				""", URI.createURI("B.n4js"), rs), "B", "C");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_25() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export @Internal public enum E { Literal }
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var x = N.E.Literal
				""", URI.createURI("B.n4js"), rs), "B", "C");

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				VIS_ILLEGAL_TYPE_ACCESS.name(), "The type E is not visible");
		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				VIS_ILLEGAL_MEMBER_ACCESS.name(), "The enum literal Literal is not visible");
	}

	@Test
	public void test_26() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export @Internal public enum E { Literal }
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var x = N.E.Literal
				""", URI.createURI("B.n4js"), rs), "A", "C");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_27() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class Base {
						@Internal public m(): void {}
					}
					export public class Sub extends Base {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var s: N.Sub;
					s.m()
				""", URI.createURI("B.n4js"), rs), "A", "C");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_28() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					class Base {
						@Internal public m(): void {}
					}
					export public class Sub extends Base {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var s: N.Sub;
					s.m()
				""", URI.createURI("B.n4js"), rs), "B", "C");

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				VIS_ILLEGAL_MEMBER_ACCESS.name(), "The method m is not visible");
	}

	@Test
	public void test_29() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export class C {
						m(): void {}
					}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var c: N.C;
					c.m()
				""", URI.createURI("B.n4js"), rs), "A", "B");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_30() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export class C {
						private m(): void {}
					}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var c: N.C;
					c.m()
				""", URI.createURI("B.n4js"), rs), "A", "B");

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				VIS_ILLEGAL_MEMBER_ACCESS.name(), "The method m is not visible");
	}

	@Test
	public void test_31() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export class A {}
					export class B extends A {
						protected m(): void {}
					}
					export class C extends B {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var c: N.C;
					c.m()
				""", URI.createURI("B.n4js"), rs), "A", "B");

		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_32() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
					export class A {}
					export class B extends A {
						protected m(): void {}
					}
					export class C extends B {}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var c: N.C;
					c.m()
				""", URI.createURI("B.n4js"), rs), "A", "C");

		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				VIS_ILLEGAL_MEMBER_ACCESS.name(), "The method m is not visible");
	}
}
