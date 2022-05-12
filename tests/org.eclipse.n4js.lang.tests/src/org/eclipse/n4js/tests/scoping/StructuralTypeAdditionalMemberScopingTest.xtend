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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSValidationTestHelper
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests scoping for additional members defined in a use site structural type ref.
 *
 * @see IDE-688
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class StructuralTypeAdditionalMemberScopingTest {

	@Inject extension ParseHelper<Script>
	@Inject extension N4JSValidationTestHelper

	@Test
	def void testStructMemberBindingInFunction() {
		val script = '''
			class C {
				public s: string;
			}
			function f(p: ~C with { n: number; }) {
				p.s;
				p.n;
			}
		'''.parse
		script.assertNoErrors
		script.assertNoDanglingReferences

		val tfunc = script.eAllContents.filter(FunctionDeclaration).head.definedFunction
		val tclass = (script.scriptElements.get(0) as N4ClassDeclaration).definedType as TClass;
		val additionalMembers = (tfunc.fpars.get(0).typeRef as ParameterizedTypeRefStructural).structuralMembers;

		val accesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)

		assertEquals(tclass.ownedMembers.get(0), accesses.next().property);

		// we cannot directly use
		//     assertEquals(additionalMembers.get(0), refToN);
		// as these members are part of a reference and thus, there exist more than one instance!
		val refToN = accesses.next().property;
		val N = additionalMembers.get(0);
		assertEquals(N.name, refToN.name);
		assertEquals(N.class, refToN.class);
		assertEquals(
			(N as TField).typeRef.declaredType,
			(refToN as TField).typeRef.declaredType
		)

	}

	@Test
	def void testStructMemberBindingInReturn() {
		val script = '''
			class C {
				public s: string;
			}
			function f(): ~C with { n: number; } { return null; }
			var p = f();
			p.s;
			p.n;
		'''.parse
		script.assertNoErrors
		script.assertNoDanglingReferences

		val tclass = (script.scriptElements.get(0) as N4ClassDeclaration).definedType as TClass;
		val tfunc = script.eAllContents.filter(FunctionDeclaration).head.definedFunction
		val additionalMembers = (tfunc.returnTypeRef as ParameterizedTypeRefStructural).structuralMembers;

		val accesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)

		assertEquals(tclass.ownedMembers.get(0), accesses.next().property);

		// we cannot directly use
		//     assertEquals(additionalMembers.get(0), refToN);
		// as these members are part of a reference and thus, there exist more than one instance!
		val refToN = accesses.next().property;
		val N = additionalMembers.get(0);
		assertEquals(N.name, refToN.name);
		assertEquals(N.class, refToN.class);
		assertEquals(
			(N as TField).typeRef.declaredType,
			(refToN as TField).typeRef.declaredType
		)

	}

	/**
	 * @see IDE-691
	 */
	@Test
	def void testStructMemberBindingInConstructorWithThisWOAddMembers() {
		val script = '''
			class C {
				public s: string;

				constructor(p: ~this) {
					p.s;
				}
			}
		'''.parse
		script.assertNoErrors
		script.assertNoDanglingReferences

		val tclass = (script.scriptElements.get(0) as N4ClassDeclaration).definedType as TClass;
		val accesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)
		assertEquals(tclass.ownedMembers.get(0), accesses.next().property);
	}

	/**
	 * @see IDE-691
	 */
	@Test
	def void testStructMemberBindingInConstructorWithThis() {
		val script = '''
			class C {
				public s: string;

				constructor(p: ~this with { n: number; }) {
					p.n;
				}
			}
		'''.parse
		script.assertNoErrors
		script.assertNoDanglingReferences

		val tclass = (script.scriptElements.get(0) as N4ClassDeclaration).definedType as TClass;
		val ctor= tclass.ownedMembers.get(1) as TMethod
		val additionalMembers = (ctor.fpars.get(0).typeRef as ThisTypeRef).structuralMembers;

		val accesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)

		// we cannot directly use
		//     assertEquals(additionalMembers.get(0), refToN);
		// as these members are part of a reference and thus, there exist more than one instance!
		val refToN = accesses.next().property;
		val N = additionalMembers.get(0);
		assertEquals(N.name, refToN.name);
		assertEquals(N.class, refToN.class);
		assertEquals(
			(N as TField).typeRef.declaredType,
			(refToN as TField).typeRef.declaredType
		)

	}

	/**
	 * @see IDE-691
	 */
	@Test
	def void testStructMemberBindingInConstructorWithThisTwoAccesses() {
		val script = '''
			class C {
				public s: string;

				constructor(p: ~this with { n: number; }) {
					p.s;
					p.n;
				}
			}
		'''.parse
		script.assertNoErrors
		script.assertNoDanglingReferences

		val tclass = (script.scriptElements.get(0) as N4ClassDeclaration).definedType as TClass;
		val ctor= tclass.ownedMembers.get(1) as TMethod
		val additionalMembers = (ctor.fpars.get(0).typeRef as ThisTypeRef).structuralMembers;

		val accesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)

		assertEquals(tclass.ownedMembers.get(0), accesses.next().property);

		// we cannot directly use
		//     assertEquals(additionalMembers.get(0), refToN);
		// as these members are part of a reference and thus, there exist more than one instance!
		val refToN = accesses.next().property;
		val N = additionalMembers.get(0);
		assertEquals(N.name, refToN.name);
		assertEquals(N.class, refToN.class);
		assertEquals(
			(N as TField).typeRef.declaredType,
			(refToN as TField).typeRef.declaredType
		)

	}

}
