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

import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AT_556_MemberAccessTest {
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

	private void isVisibleInSameProject(String accessModifier) {
		isVisibleInSameProject(accessModifier, true);
	}

	private void isVisibleInSameProject(String accessModifier, boolean yes) {
		XtextResourceSet rs = resourceSetProvider.get();
		try {

			withVendorAndProject(parseHelper.parse("""
					export public class C {
						%s m(): void {}
					}
					""".formatted(accessModifier), URI.createURI("A.n4js"), rs), "A", "B");
			Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var c: N.C;
					c.m()
					""", URI.createURI("B.n4js"), rs), "A", "B");
			if (yes)
				valTestHelper.assertNoErrors(script);
			else
				valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
						IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, "The method m is not visible");
		} catch (Exception e) {
			fail();
		}
	}

	private void isVisibleInSameModule(String accessModifier) {
		XtextResourceSet rs = resourceSetProvider.get();
		try {
			Script script = withVendorAndProject(parseHelper.parse("""
					export public class C {
						%s m(): void {}
					}
					var c: C;
					c.m()
					""".formatted(accessModifier), URI.createURI("A.n4js"), rs), "A", "B");
			valTestHelper.assertNoErrors(script);
		} catch (Exception e) {
			fail();
		}
	}

	private void isVisibleToSameVendor(String accessModifier) {
		isVisibleToSameVendor(accessModifier, true);
	}

	private void isVisibleToSameVendor(String accessModifier, boolean yes) {
		XtextResourceSet rs = resourceSetProvider.get();
		try {
			withVendorAndProject(parseHelper.parse("""
					export public class C {
						%s m(): void {}
					}
					""".formatted(accessModifier), URI.createURI("A.n4js"), rs), "A", "B");
			Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var c: N.C;
					c.m()
					""", URI.createURI("B.n4js"), rs), "A", "C");
			if (yes)
				valTestHelper.assertNoErrors(script);
			else
				valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
						IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, "The method m is not visible");

		} catch (Exception e) {
			fail();
		}
	}

	private void isVisibleToWorld(String accessModifier, boolean yes) {
		XtextResourceSet rs = resourceSetProvider.get();
		try {
			withVendorAndProject(parseHelper.parse("""
					export public class C {
						%s m(): void {}
					}
					""".formatted(accessModifier), URI.createURI("A.n4js"), rs), "A", "B");
			Script script = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					var c: N.C;
					c.m()
					""", URI.createURI("B.n4js"), rs), "B", "C");
			if (yes)
				valTestHelper.assertNoErrors(script);
			else
				valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
						IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, "The method m is not visible");

		} catch (Exception e) {
			fail();
		}
	}

	private void isMemberProtectedVisible(String type, String member, boolean sameVendor, boolean yes) {
		_isMemberProtectedVisible(type, member, yes, false, sameVendor);
	}

	private void isMemberProtectedInternalVisible(String type, String member, boolean sameVendor, boolean yes) {
		_isMemberProtectedVisible(type, member, yes, true, sameVendor);
	}

	/*- Inheritance:
	 *   A
	 * 	 ^
	 * 	 B
	 *   ^
	 * C   D <-- Testclass with method m(), taking Paremeter of type "type" and calling "type"."member".
	 *     ^
	 *     E
	 */
	private void _isMemberProtectedVisible(String type, String member, boolean yes, boolean internal,
			boolean sameVendor) {
		String intern = internal ? "@Internal" : "";
		String vendor = sameVendor ? "A" : "B";
		XtextResourceSet rs = resourceSetProvider.get();
		try {

			withVendorAndProject(parseHelper.parse("""
					export public class A {
						%s protected a1(): void {}
					}
					export public class B extends A {
						%s protected b1(): void {}
					}
					export public class C extends B {
						%s protected c1(): void {}
					}
					""".formatted(intern, intern, intern), URI.createURI("A.n4js"), rs), "A", "B");
			Script scriptNoAPI = withVendorAndProject(parseHelper.parse("""
					import * as N from "A";
					class D extends N.B {
						m(r: %s): void {
							r.%s1()
						}
					}
					class E extends D {
					}
					""".formatted(type, member), URI.createURI("B1.n4js"), rs), vendor, "C");
			if (yes)
				valTestHelper.assertNoErrors(scriptNoAPI);
			else
				valTestHelper.assertError(scriptNoAPI, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
						IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, "The method %s1 is not visible".formatted(member));

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test_01() {
		isVisibleInSameProject("project");
	}

	@Test
	public void test_02() {
		isVisibleInSameProject("@Internal protected");
	}

	@Test
	public void test_03() {
		isVisibleInSameProject("protected");
	}

	@Test
	public void test_04() {
		isVisibleInSameProject("@Internal public");
	}

	@Test
	public void test_05() {
		isVisibleInSameProject("public");
	}

	@Test
	public void test_06() {
		isVisibleInSameProject("/* project is default */");
	}

	@Test
	public void test_07() {
		isVisibleInSameProject("private", false);
	}

	@Test
	public void test_08() {
		isVisibleInSameModule("project");
	}

	@Test
	public void test_09() {
		isVisibleInSameModule("@Internal protected");
	}

	@Test
	public void test_10() {
		isVisibleInSameModule("protected");
	}

	@Test
	public void test_11() {
		isVisibleInSameModule("@Internal public");
	}

	@Test
	public void test_12() {
		isVisibleInSameModule("public");
	}

	@Test
	public void test_13() {
		isVisibleInSameModule("/* project is default */");
	}

	@Test
	public void test_14() {
		isVisibleInSameModule("private");
	}

	@Test
	public void test_15() {
		isVisibleToSameVendor("project", false);
	}

	@Test
	public void test_16() {
		isVisibleToSameVendor("@Internal protected", false);
	}

	@Test
	public void test_17() {
		isVisibleToSameVendor("protected", false);
	}

	@Test
	public void test_18() {
		isVisibleToSameVendor("@Internal public");
	}

	@Test
	public void test_19() {
		isVisibleToSameVendor("public");
	}

	@Test
	public void test_20() {
		isVisibleToSameVendor("/* project is default */", false);
	}

	@Test
	public void test_21() {
		isVisibleToSameVendor("private", false);
	}

	@Test
	public void test_22() {
		isVisibleToWorld("project", false);
	}

	@Test
	public void test_23() {
		isVisibleToWorld("@Internal protected", false);
	}

	@Test
	public void test_24() {
		isVisibleToWorld("protected", false);
	}

	@Test
	public void test_25() {
		isVisibleToWorld("@Internal public", false);
	}

	@Test
	public void test_26() {
		isVisibleToWorld("public", true);
	}

	@Test
	public void test_27() {
		isVisibleToWorld("/* project is default */", false);
	}

	@Test
	public void test_28() {
		isVisibleToWorld("private", false);
	}

	@Test
	public void test_29a() {
		isMemberProtectedVisible("N.B", "b", true, false);
	}

	@Test
	public void test_29a_internal() {
		isMemberProtectedInternalVisible("N.B", "b", true, false);
	}

	@Test
	public void test_29b() {
		isMemberProtectedVisible("N.B", "b", false, false);
	}

	@Test
	public void test_29b_internal() {
		isMemberProtectedInternalVisible("N.B", "b", false, false);
	}

	@Test
	public void test_30a() {
		isMemberProtectedVisible("N.B", "a", true, false);
	}

	@Test
	public void test_30a_internal() {
		isMemberProtectedInternalVisible("N.B", "a", true, false);
	}

	@Test
	public void test_30b() {
		isMemberProtectedVisible("N.B", "a", false, false);
	}

	@Test
	public void test_30b_internal() {
		isMemberProtectedInternalVisible("N.B", "a", false, false);
	}

	// @Test
	// def void test_31() {
	// isMemberProtectedVisible("A", "a", false);
	// }
	//
	// @Test
	// def void test_32() {
	// isMemberProtectedVisible("C", "a", false);
	// }
	//
	@Test
	public void test_33a() {
		isMemberProtectedVisible("N.C", "b", true, false);
	}

	@Test
	public void test_33a_internal() {
		isMemberProtectedInternalVisible("N.C", "b", true, false);
	}

	@Test
	public void test_33b() {
		isMemberProtectedVisible("N.C", "b", false, false);
	}

	@Test
	public void test_33b_internal() {
		isMemberProtectedInternalVisible("N.C", "b", false, false);
	}

	@Test
	public void test_34a() {
		isMemberProtectedVisible("N.C", "c", true, false);
	}

	@Test
	public void test_34a_internal() {
		isMemberProtectedInternalVisible("N.C", "c", true, false);
	}

	@Test
	public void test_34b() {
		isMemberProtectedVisible("N.C", "c", false, false);
	}

	@Test
	public void test_34b_internal() {
		isMemberProtectedInternalVisible("N.C", "c", false, false);
	}

	@Test
	public void test_35a() {
		isMemberProtectedVisible("D", "b", true, true);
	}

	@Test
	public void test_35a_internal() {
		isMemberProtectedInternalVisible("D", "b", true, true);
	}

	@Test
	public void test_35b() {
		isMemberProtectedVisible("D", "b", false, true);
	}

	@Test
	public void test_35b_internal() {
		isMemberProtectedInternalVisible("D", "b", false, false);
	}

	@Test
	public void test_36a() {
		isMemberProtectedVisible("E", "b", true, true);
	}

	@Test
	public void test_36a_internal() {
		isMemberProtectedInternalVisible("E", "b", true, true);
	}

	@Test
	public void test_36b() {
		isMemberProtectedVisible("E", "b", false, true);
	}

	@Test
	public void test_36b_internal() {
		isMemberProtectedInternalVisible("E", "b", false, false);
	}

	@Test
	public void test_37() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
				export public class A {
					@Internal protected a(): void {}
				}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
				import * as N from "A";
				class B extends N.A {
					m(): void {
						this.a()
					}
				}
				""", URI.createURI("B.n4js"), rs), "A", "C");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_38() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		withVendorAndProject(parseHelper.parse("""
				export public class A {
					@Internal protected a(): void {}
				}
				""", URI.createURI("A.n4js"), rs), "A", "B");
		Script script = withVendorAndProject(parseHelper.parse("""
				import * as N from "A";
				class B extends N.A {
				}
				class C {
					m(b: B): void {
						b.a()
					}
				}
				""", URI.createURI("B.n4js"), rs), "A", "C");
		valTestHelper.assertError(script, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION,
				IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, "The method a is not visible");
	}
}
