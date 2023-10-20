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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSValidationTestHelper;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests scoping for additional members defined in a use site structural type ref.
 *
 * see IDE-688
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class StructuralTypeAdditionalMemberScopingTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	N4JSValidationTestHelper valTestHelper;

	@Test
	public void testStructMemberBindingInFunction() throws Exception {
		Script script = parseHelper.parse("""
				class C {
					public s: string;
				}
				function f(p: ~C with { n: number; }) {
					p.s;
					p.n;
				}
				""");
		valTestHelper.assertNoErrors(script);
		valTestHelper.assertNoDanglingReferences(script);

		TFunction tfunc = (TFunction) head(filter(script.eAllContents(), FunctionDeclaration.class)).getDefinedType();
		TClass tclass = (TClass) ((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedType();
		EList<TStructMember> additionalMembers = ((ParameterizedTypeRefStructural) tfunc.getFpars().get(0).getTypeRef())
				.getStructuralMembers();

		Iterator<ParameterizedPropertyAccessExpression> accesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);

		assertEquals(tclass.getOwnedMembers().get(0), accesses.next().getProperty());

		// we cannot directly use
		// assertEquals(additionalMembers.get(0), refToN);
		// as. these members are part of a reference and thus, there exist more than one instance!
		IdentifiableElement refToN = accesses.next().getProperty();
		TStructMember N = additionalMembers.get(0);
		assertEquals(N.getName(), refToN.getName());
		assertEquals(N.getClass(), refToN.getClass());
		assertEquals(
				((TField) N).getTypeRef().getDeclaredType(),
				((TField) refToN).getTypeRef().getDeclaredType());

	}

	@Test
	public void testStructMemberBindingInReturn() throws Exception {
		Script script = parseHelper.parse("""
				class C {
					public s: string;
				}
				function f(): ~C with { n: number; } { return null; }
				var p = f();
				p.s;
				p.n;
				""");
		valTestHelper.assertNoErrors(script);
		valTestHelper.assertNoDanglingReferences(script);

		TClass tclass = (TClass) ((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedType();
		TFunction tfunc = (TFunction) head(filter(script.eAllContents(), FunctionDeclaration.class)).getDefinedType();
		EList<TStructMember> additionalMembers = ((ParameterizedTypeRefStructural) tfunc.getReturnTypeRef())
				.getStructuralMembers();

		Iterator<ParameterizedPropertyAccessExpression> accesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);

		assertEquals(tclass.getOwnedMembers().get(0), accesses.next().getProperty());

		// we cannot directly use
		// assertEquals(additionalMembers.get(0), refToN);
		// as. these members are part of a reference and thus, there exist more than one instance!
		IdentifiableElement refToN = accesses.next().getProperty();
		TStructMember N = additionalMembers.get(0);
		assertEquals(N.getName(), refToN.getName());
		assertEquals(N.getClass(), refToN.getClass());
		assertEquals(
				((TField) N).getTypeRef().getDeclaredType(),
				((TField) refToN).getTypeRef().getDeclaredType());

	}

	/**
	 * see IDE-691
	 */
	@Test
	public void testStructMemberBindingInConstructorWithThisWOAddMembers() throws Exception {
		Script script = parseHelper.parse("""
				class C {
					public s: string;

					constructor(p: ~this) {
						p.s;
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
		valTestHelper.assertNoDanglingReferences(script);

		TClass tclass = (TClass) ((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedType();
		Iterator<ParameterizedPropertyAccessExpression> accesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);
		assertEquals(tclass.getOwnedMembers().get(0), accesses.next().getProperty());
	}

	/**
	 * see IDE-691
	 */
	@Test
	public void testStructMemberBindingInConstructorWithThis() throws Exception {
		Script script = parseHelper.parse("""
				class C {
					public s: string;

					constructor(p: ~this with { n: number; }) {
						p.n;
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
		valTestHelper.assertNoDanglingReferences(script);

		TClass tclass = (TClass) ((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedType();
		TMethod ctor = (TMethod) tclass.getOwnedMembers().get(1);
		EList<TStructMember> additionalMembers = ((ThisTypeRef) ctor.getFpars().get(0).getTypeRef())
				.getStructuralMembers();

		Iterator<ParameterizedPropertyAccessExpression> accesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);

		// we cannot directly use
		// assertEquals(additionalMembers.get(0), refToN);
		// as. these members are part of a reference and thus, there exist more than one instance!
		IdentifiableElement refToN = accesses.next().getProperty();
		TStructMember N = additionalMembers.get(0);
		assertEquals(N.getName(), refToN.getName());
		assertEquals(N.getClass(), refToN.getClass());
		assertEquals(
				((TField) N).getTypeRef().getDeclaredType(),
				((TField) refToN).getTypeRef().getDeclaredType());

	}

	/**
	 * see IDE-691
	 */
	@Test
	public void testStructMemberBindingInConstructorWithThisTwoAccesses() throws Exception {
		Script script = parseHelper.parse("""
				class C {
					public s: string;

					constructor(p: ~this with { n: number; }) {
						p.s;
						p.n;
					}
				}
				""");
		valTestHelper.assertNoErrors(script);
		valTestHelper.assertNoDanglingReferences(script);

		TClass tclass = (TClass) ((N4ClassDeclaration) script.getScriptElements().get(0)).getDefinedType();
		TMethod ctor = (TMethod) tclass.getOwnedMembers().get(1);
		EList<TStructMember> additionalMembers = ((ThisTypeRef) ctor.getFpars().get(0).getTypeRef())
				.getStructuralMembers();

		Iterator<ParameterizedPropertyAccessExpression> accesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);

		assertEquals(tclass.getOwnedMembers().get(0), accesses.next().getProperty());

		// we cannot directly use
		// assertEquals(additionalMembers.get(0), refToN);
		// as. these members are part of a reference and thus, there exist more than one instance!
		IdentifiableElement refToN = accesses.next().getProperty();
		TStructMember N = additionalMembers.get(0);
		assertEquals(N.getName(), refToN.getName());
		assertEquals(N.getClass(), refToN.getClass());
		assertEquals(
				((TField) N).getTypeRef().getDeclaredType(),
				((TField) refToN).getTypeRef().getDeclaredType());

	}

}
