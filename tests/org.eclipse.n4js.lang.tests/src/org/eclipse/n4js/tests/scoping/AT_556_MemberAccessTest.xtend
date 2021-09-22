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
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_556_MemberAccessTest {
	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	private def withVendorAndProject(Script script, String vendorID, String projectID) {
		script.eResource.contents.get(1) as TModule => [
			it.projectID = projectID
			it.vendorID = vendorID
		]
		return script
	}

	private def isVisibleInSameProject(String accessModifier) {
		accessModifier.isVisibleInSameProject(true)
	}

	private def isVisibleInSameProject(String accessModifier, boolean yes) {
		val rs = resourceSetProvider.get();
		'''
			export public class C {
				«accessModifier» m(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var c: N.C;
			c.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'B')
		if (yes)
			script.assertNoErrors
		else
			script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The method m is not visible')
	}

	private def isVisibleInSameModule(String accessModifier) {
		val rs = resourceSetProvider.get();
		val script = '''
			export public class C {
				«accessModifier» m(): void {}
			}
			var c: C;
			c.m()
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		script.assertNoErrors
	}

	private def isVisibleToSameVendor(String accessModifier) {
		accessModifier.isVisibleToSameVendor(true)
	}

	private def isVisibleToSameVendor(String accessModifier, boolean yes) {
		val rs = resourceSetProvider.get();
		'''
			export public class C {
				«accessModifier» m(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var c: N.C;
			c.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')
		if (yes)
			script.assertNoErrors
		else
			script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The method m is not visible')
	}

	private def isVisibleToWorld(String accessModifier, boolean yes) {
		val rs = resourceSetProvider.get();
		'''
			export public class C {
				«accessModifier» m(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			var c: N.C;
			c.m()
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('B', 'C')
		if (yes)
			script.assertNoErrors
		else
			script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The method m is not visible')
	}

	private def isMemberProtectedVisible(String type, String member, boolean sameVendor,  boolean yes) {
		_isMemberProtectedVisible(type,member,yes,false,sameVendor);
	}
	private def isMemberProtectedInternalVisible(String type, String member, boolean sameVendor, boolean yes) {
		_isMemberProtectedVisible(type,member,yes,true,sameVendor);
	}

	/*- Inheritance:
	 *   A
	 * 	 ^
	 * 	 B
	 *   ^
	 * C   D <-- Testclass with method m(), taking Paremeter of type 'type' and calling 'type'.'member'.
	 *     ^
	 *     E
	 */
	private def _isMemberProtectedVisible(String type, String member, boolean yes, boolean internal, boolean sameVendor) {
		val intern  = if(internal) "@Internal" else ""
		val vendor = if( sameVendor ) 'A' else 'B'
		val rs = resourceSetProvider.get();
		'''
			export public class A {
				«intern» protected a1(): void {}
			}
			export public class B extends A {
				«intern» protected b1(): void {}
			}
			export public class C extends B {
				«intern» protected c1(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val scriptNoAPI = '''
			import * as N from "A"
			class D extends N.B {
				m(r: «type»): void {
					r.«member»1()
				}
			}
			class E extends D {
			}
		'''.parse(URI.createURI("B1.n4js"), rs).withVendorAndProject(vendor, 'C')
		if (yes)
			scriptNoAPI.assertNoErrors
		else
			scriptNoAPI.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, '''The method «member»1 is not visible''')
	}

	@Test
	def void test_01() {
		'project'.visibleInSameProject
	}

	@Test
	def void test_02() {
		'@Internal protected'.visibleInSameProject
	}

	@Test
	def void test_03() {
		'protected'.visibleInSameProject
	}

	@Test
	def void test_04() {
		'@Internal public'.visibleInSameProject
	}

	@Test
	def void test_05() {
		'public'.visibleInSameProject
	}

	@Test
	def void test_06() {
		'/* project is default */'.visibleInSameProject
	}

	@Test
	def void test_07() {
		'private'.isVisibleInSameProject(false)
	}

	@Test
	def void test_08() {
		'project'.visibleInSameModule
	}

	@Test
	def void test_09() {
		'@Internal protected'.visibleInSameModule
	}

	@Test
	def void test_10() {
		'protected'.visibleInSameModule
	}

	@Test
	def void test_11() {
		'@Internal public'.visibleInSameModule
	}

	@Test
	def void test_12() {
		'public'.visibleInSameModule
	}

	@Test
	def void test_13() {
		'/* project is default */'.visibleInSameModule
	}

	@Test
	def void test_14() {
		'private'.visibleInSameModule
	}

	@Test
	def void test_15() {
		'project'.isVisibleToSameVendor(false)
	}

	@Test
	def void test_16() {
		'@Internal protected'.isVisibleToSameVendor(false)
	}

	@Test
	def void test_17() {
		'protected'.isVisibleToSameVendor(false)
	}

	@Test
	def void test_18() {
		'@Internal public'.visibleToSameVendor
	}

	@Test
	def void test_19() {
		'public'.visibleToSameVendor
	}

	@Test
	def void test_20() {
		'/* project is default */'.isVisibleToSameVendor(false)
	}

	@Test
	def void test_21() {
		'private'.isVisibleToSameVendor(false)
	}

	@Test
	def void test_22() {
		'project'.isVisibleToWorld(false)
	}

	@Test
	def void test_23() {
		'@Internal protected'.isVisibleToWorld(false)
	}

	@Test
	def void test_24() {
		'protected'.isVisibleToWorld(false)
	}

	@Test
	def void test_25() {
		'@Internal public'.isVisibleToWorld(false)
	}

	@Test
	def void test_26() {
		'public'.isVisibleToWorld(true)
	}

	@Test
	def void test_27() {
		'/* project is default */'.isVisibleToWorld(false)
	}

	@Test
	def void test_28() {
		'private'.isVisibleToWorld(false)
	}

	@Test
	def void test_29a() {
		isMemberProtectedVisible('N.B', 'b', true, false)
	}

	@Test
	def void test_29a_internal() {
		isMemberProtectedInternalVisible('N.B', 'b', true, false)
	}

	@Test
	def void test_29b() {
		isMemberProtectedVisible('N.B', 'b', false, false)
	}

	@Test
	def void test_29b_internal() {
		isMemberProtectedInternalVisible('N.B', 'b', false, false)
	}

	@Test
	def void test_30a() {
		isMemberProtectedVisible('N.B', 'a', true, false)
	}
	@Test
	def void test_30a_internal() {
		isMemberProtectedInternalVisible('N.B', 'a', true,  false)
	}

	@Test
	def void test_30b() {
		isMemberProtectedVisible('N.B', 'a', false, false)
	}
	@Test
	def void test_30b_internal() {
		isMemberProtectedInternalVisible('N.B', 'a', false,  false)
	}

//	@Test
//	def void test_31() {
//		isMemberProtectedVisible('A', 'a', false)
//	}
//
//	@Test
//	def void test_32() {
//		isMemberProtectedVisible('C', 'a', false)
//	}
//
	@Test
	def void test_33a() {
		isMemberProtectedVisible('N.C', 'b', true, false)
	}
	@Test
	def void test_33a_internal() {
		isMemberProtectedInternalVisible('N.C', 'b', true, false)
	}
	@Test
	def void test_33b() {
		isMemberProtectedVisible('N.C', 'b', false, false)
	}
	@Test
	def void test_33b_internal() {
		isMemberProtectedInternalVisible('N.C', 'b', false, false)
	}
	@Test
	def void test_34a() {
		isMemberProtectedVisible('N.C', 'c', true, false)
	}
	@Test
	def void test_34a_internal() {
		isMemberProtectedInternalVisible('N.C', 'c', true, false)
	}
	@Test
	def void test_34b() {
		isMemberProtectedVisible('N.C', 'c', false, false)
	}
	@Test
	def void test_34b_internal() {
		isMemberProtectedInternalVisible('N.C', 'c', false, false)
	}

	@Test
	def void test_35a() {
		isMemberProtectedVisible('D', 'b', true, true)
	}
	@Test
	def void test_35a_internal() {
		isMemberProtectedInternalVisible('D', 'b', true, true)
	}
	@Test
	def void test_35b() {
		isMemberProtectedVisible('D', 'b', false, true)
	}
	@Test
	def void test_35b_internal() {
		isMemberProtectedInternalVisible('D', 'b', false, false)
	}

	@Test
	def void test_36a() {
		isMemberProtectedVisible('E', 'b', true, true)
	}
	@Test
	def void test_36a_internal() {
		isMemberProtectedInternalVisible('E', 'b', true, true)
	}
	@Test
	def void test_36b() {
		isMemberProtectedVisible('E', 'b', false, true)
	}
	@Test
	def void test_36b_internal() {
		isMemberProtectedInternalVisible('E', 'b', false, false)
	}


	@Test
	def void test_37() {
		val rs = resourceSetProvider.get();
		'''
			export public class A {
				@Internal protected a(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			class B extends N.A {
				m(): void {
					this.a()
				}
			}
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')
		script.assertNoErrors
	}

	@Test
	def void test_38() {
		val rs = resourceSetProvider.get();
		'''
			export public class A {
				@Internal protected a(): void {}
			}
		'''.parse(URI.createURI("A.n4js"), rs).withVendorAndProject('A', 'B')
		val script = '''
			import * as N from "A"
			class B extends N.A {
			}
			class C {
				m(b: B): void {
					b.a()
				}
			}
		'''.parse(URI.createURI("B.n4js"), rs).withVendorAndProject('A', 'C')
		script.assertError(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION, IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS, 'The method a is not visible')
	}
}
