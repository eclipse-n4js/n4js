/**
 * Copyright (c) 2017 NumberFour AG.
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
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.members.MemberScopingHelper;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Tests behavior of member scoping related to the caching of composed members. For further information, see API
 * documentation of methods: {@link MemberScopingHelper#createMemberScope(TypeRef, EObject, boolean, boolean, boolean)}
 * and
 * {@link MemberScopingHelper#createMemberScopeAllowingNonContainedMembers(TypeRef, EObject, boolean, boolean, boolean)}.
 */
public class ComposedMemberCachingTest extends AbstractN4JSTest {

	@Inject
	private MemberScopingHelper memberScopingHelper;
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	String snippet = """
			let a: Array<string> | Array<number>;
			a.length;
			""";

	@Test
	public void testNoDuplicateCacheNoDuplicateMemberCreatedForSameMembersOfEquivalentComposedTypeRefs() {
		Script script = parseAndPostProcessAndDeleteCachedComposedMembers("""
				let v : Array<any> | Array<string> = (1==1)? [] : ["Hi"];
				v.join();
				v.join();
				""");
		VariableDeclaration varDecl = IteratorExtensions.head(filter(script.eAllContents(), VariableDeclaration.class));
		TypeRef receiverType = varDecl.getDeclaredTypeRef();
		Iterator<ParameterizedPropertyAccessExpression> propertyAccesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);
		ParameterizedPropertyAccessExpression propAccess1 = propertyAccesses.next();
		ParameterizedPropertyAccessExpression propertyAccess2 = propertyAccesses.next();

		TMember member1 = performOrdinaryMemberScoping("join", receiverType, propAccess1);
		TMember member2 = performOrdinaryMemberScoping("join", receiverType, propertyAccess2);
		assertEquals("There must be exactly one composed member cache", 1,
				script.getModule().getComposedMemberCaches().size());
		assertSame(member1, member2);
	}

	@Test
	public void testNoDuplicateCacheCreatedForEquivalentComposedTypeRefs() {
		Script script = parseAndPostProcessAndDeleteCachedComposedMembers("""
				let v : Array<any> | Array<string> = (1==1)? [] : ["Hi"];
				v.join();
				v.filter(null);
				""");
		VariableDeclaration varDecl = IteratorExtensions.head(filter(script.eAllContents(), VariableDeclaration.class));
		TypeRef receiverType = varDecl.getDeclaredTypeRef();
		Iterator<ParameterizedPropertyAccessExpression> propertyAccesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);
		ParameterizedPropertyAccessExpression propAccess1 = propertyAccesses.next();
		ParameterizedPropertyAccessExpression propertyAccess2 = propertyAccesses.next();

		TMember member1 = performOrdinaryMemberScoping("join", receiverType, propAccess1);
		TMember member2 = performOrdinaryMemberScoping("filter", receiverType, propertyAccess2);
		assertEquals("There must be exactly one composed member cache", 1,
				script.getModule().getComposedMemberCaches().size());
		assertNotSame(member1, member2);
	}

	@Test
	public void testDifferentCachesCreatedForNonEquivalentComposedTypeRef() {
		Script script = parseAndPostProcessAndDeleteCachedComposedMembers("""
				let v1: Array<any> | Array<int> = (1==1)? [] : [42];
				let v2 : Array<any> | Array<string> = (1==1)? [] : ["Hello"];
				v1.join();
				v2.join();
				""");
		Iterator<VariableDeclaration> varDecls = filter(script.eAllContents(), VariableDeclaration.class);
		VariableDeclaration varDecl1 = varDecls.next();
		VariableDeclaration varDecl2 = varDecls.next();

		TypeRef receiverType1 = varDecl1.getDeclaredTypeRef();
		TypeRef receiverType2 = varDecl2.getDeclaredTypeRef();

		Iterator<ParameterizedPropertyAccessExpression> propertyAccesses = filter(script.eAllContents(),
				ParameterizedPropertyAccessExpression.class);
		ParameterizedPropertyAccessExpression propAccess1 = propertyAccesses.next();
		ParameterizedPropertyAccessExpression propertyAccess2 = propertyAccesses.next();

		TMember member1 = performOrdinaryMemberScoping("join", receiverType1, propAccess1);
		TMember member2 = performOrdinaryMemberScoping("join", receiverType2, propertyAccess2);
		assertEquals("There must be exactly two composed member caches", 2,
				script.getModule().getComposedMemberCaches().size());
		assertEquals("The first cache must have exactly one member", 1,
				script.getModule().getComposedMemberCaches().get(0).getCachedComposedMembers().size());
		assertEquals("The second cache must have exactly one member", 1,
				script.getModule().getComposedMemberCaches().get(1).getCachedComposedMembers().size());
		assertNotSame(member1, member2);
	}

	@Test
	public void testContainedMemberWhenUsingContainedTypeRef() {
		Script script = parseAndPostProcessAndDeleteCachedComposedMembers(snippet);
		N4JSResource resource = (N4JSResource) script.eResource();

		VariableDeclaration varDecl = IteratorExtensions.head(filter(script.eAllContents(), VariableDeclaration.class));
		ParameterizedPropertyAccessExpression propAccess = IteratorExtensions
				.head(filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));

		// use a receiver type reference that is contained in a resource
		TypeRef receiverTypeRef = varDecl.getDeclaredTypeRef();
		TMember member = performOrdinaryMemberScoping("length", receiverTypeRef, propAccess);
		assertTrue("composed member should be contained in a resource", member.eResource() != null);
		assertSame(resource, member.eResource());
	}

	@Test
	public void testContainedMemberWhenUsingNonContainedTypeRef() {
		Script script = parseAndPostProcessAndDeleteCachedComposedMembers(snippet);
		N4JSResource resource = (N4JSResource) script.eResource();

		VariableDeclaration varDecl = IteratorExtensions.head(filter(script.eAllContents(), VariableDeclaration.class));
		ParameterizedPropertyAccessExpression propAccess = IteratorExtensions
				.head(filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));

		// use a receiver type reference that is *NOT* contained in a resource
		TypeRef receiverTypeRef = TypeUtils.copy(varDecl.getDeclaredTypeRef());
		assertNull(receiverTypeRef.eResource());
		TMember member = performOrdinaryMemberScoping("length", receiverTypeRef, propAccess);
		assertTrue("composed member should be contained in a resource", member.eResource() != null);
		assertSame(resource, member.eResource());
	}

	@Test
	public void testNoCachingHappensWhenAllowingNonContainedMembers() {
		Script script = parseAndPostProcessAndDeleteCachedComposedMembers(snippet);

		VariableDeclaration varDecl = IteratorExtensions.head(filter(script.eAllContents(), VariableDeclaration.class));
		ParameterizedPropertyAccessExpression propAccess = IteratorExtensions
				.head(filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class));

		TypeRef receiverTypeRef = varDecl.getDeclaredTypeRef();
		IScope scope = memberScopingHelper.createMemberScopeAllowingNonContainedMembers(receiverTypeRef, propAccess,
				false, true, false);
		scope.getAllElements();
		assertTrue("scoping should not have added anything to composed member cache",
				script.getModule().getComposedMemberCaches().isEmpty());

		// only for completeness, we test the counter example using ordinary member scoping:
		IScope scope2 = memberScopingHelper.createMemberScope(receiverTypeRef, propAccess, false, true, false);
		scope2.getAllElements();
		assertTrue("scoping should have added several members to composed member cache",
				// at time of writing, 16 members were added (but exact count might change over time)
				script.getModule().getComposedMemberCaches().get(0).getCachedComposedMembers().size() > 1);
	}

	private Script parseAndPostProcessAndDeleteCachedComposedMembers(CharSequence code) {
		try {
			Script script = testHelper.parseAndValidateSuccessfully(code);
			// ensure validation did trigger post-processing;
			assertTrue(((N4JSResource) script.eResource()).isFullyProcessed());

			// While resolving the proxies during validation above, composed members have been added to the composed
			// member
			// cache in the TModule and future invocations of ComposedMemberScope will simply reuse them and won't
			// create
			// any more members. However, during the tests in this file, we want to test the creation of composed
			// members
			// (i.e. we want to test if/when/how composed members are created and added to the cache).
			// Therefore, we now clear the composed member cache to give the above tests a chance to trigger investigate
			// how certain invocations of ComposedMemberScope will (re-)create the composed members.
			for (MemberAccess memberAccess : toList(filter(script.eAllContents(), MemberAccess.class))) {
				EcoreUtilN4.doWithDeliver(false, () -> memberAccess.setComposedMemberCache(null), memberAccess);
			}
			TModule module = script.getModule();
			EcoreUtilN4.doWithDeliver(false, () -> module.getComposedMemberCaches().clear(), module);

			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	private TMember performOrdinaryMemberScoping(String memberName, TypeRef receiverTypeRef, MemberAccess context) {
		IScope scope = memberScopingHelper.createMemberScope(receiverTypeRef, context, false, true, false);
		IEObjectDescription memberDesc = scope.getSingleElement(qualifiedNameConverter.toQualifiedName(memberName));
		TMember member = (TMember) memberDesc.getEObjectOrProxy();
		assertNotNull(member);
		assertFalse(member.eIsProxy());
		assertTrue(member.isComposed());
		return member;
	}
}
