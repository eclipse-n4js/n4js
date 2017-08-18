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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.n4JS.MemberAccess
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.members.MemberScopingHelper
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.junit.Test

/**
 * Tests behavior of member scoping related to the caching of composed members. For further information, see
 * API documentation of methods:
 * {@link MemberScopingHelper#createMemberScope(TypeRef, MemberAccess, boolean, boolean)} and
 * {@link MemberScopingHelper#createMemberScopeAllowingNonContainedMembers(TypeRef, EObject, boolean, boolean)}.
 */
class ComposedMemberCachingTest extends AbstractN4JSTest {

	@Inject
	private MemberScopingHelper memberScopingHelper;
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	val snippet = '''
			let a: Array<string> | Array<number>;
			a.length;
	''';

	@Test
	def void testNoDuplicateCacheNoDuplicateMemberCreatedForSameMembersOfEquivalentComposedTypeRefs() {
		val script = '''
			let v : Array<any> | Array<string> = (1==1)? [] : ["Hi"];
			v.join();
			v.join();
	'''.parse;
		val varDecl = script.eAllContents.filter(VariableDeclaration).head;
		val receiverType = varDecl.declaredTypeRef
		val propertyAccesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)
		val propAccess1 = propertyAccesses.next
		val propertyAccess2 = propertyAccesses.next

		val member1 = performOrdinaryMemberScoping("join", receiverType, propAccess1)
		val member2 = performOrdinaryMemberScoping("join", receiverType, propertyAccess2)
		assertEquals("There must be exactly one composed member cache", 1, script.module.composedMemberCaches.size);
		assertSame(member1, member2)
	}

	@Test
	def void testNoDuplicateCacheCreatedForEquivalentComposedTypeRefs() {
		val script = '''
			let v : Array<any> | Array<string> = (1==1)? [] : ["Hi"];
			v1.join();
			v1.filter(null);
	'''.parse;
		val varDecl = script.eAllContents.filter(VariableDeclaration).head;
		val receiverType = varDecl.declaredTypeRef
		val propertyAccesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)
		val propAccess1 = propertyAccesses.next
		val propertyAccess2 = propertyAccesses.next

		val member1 = performOrdinaryMemberScoping("join", receiverType, propAccess1)
		val member2 = performOrdinaryMemberScoping("filter", receiverType, propertyAccess2)
		assertEquals("There must be exactly one composed member cache", 1, script.module.composedMemberCaches.size);
		assertNotSame(member1, member2)
	}

	@Test
	def void testDifferentCachesCreatedForNonEquivalentComposedTypeRef() {
		val script = '''
			let v1: Array<any> | Array<int> = (1==1)? [] : [42];
			let v2 : Array<any> | Array<string> = (1==1)? [] : ["Hello"];
			v1.join();
			v2.join();
	'''.parse;
		val varDecls = script.eAllContents.filter(VariableDeclaration)
		val varDecl1 = varDecls.next;
		val varDecl2 = varDecls.next;

		val receiverType1 = varDecl1.declaredTypeRef
		val receiverType2 = varDecl2.declaredTypeRef

		val propertyAccesses = script.eAllContents.filter(ParameterizedPropertyAccessExpression)
		val propAccess1 = propertyAccesses.next
		val propertyAccess2 = propertyAccesses.next

		val member1 = performOrdinaryMemberScoping("join", receiverType1, propAccess1)
		val member2 = performOrdinaryMemberScoping("join", receiverType2, propertyAccess2)
		assertEquals("There must be exactly two composed member caches", 2, script.module.composedMemberCaches.size);
		assertEquals("The first cache must have exactly one member", 1, script.module.composedMemberCaches.get(0).cachedComposedMembers.size);
		assertEquals("The second cache must have exactly one member", 1, script.module.composedMemberCaches.get(1).cachedComposedMembers.size);
		assertNotSame(member1, member2)
	}

	@Test
	def void testContainedMemberWhenUsingContainedTypeRef() {
		val script = snippet.parse; // do not validate!
		val resource = script.eResource as N4JSResource;

		val varDecl = script.eAllContents.filter(VariableDeclaration).head;
		val propAccess = script.eAllContents.filter(ParameterizedPropertyAccessExpression).head;

		// use a receiver type reference that is contained in a resource
		val receiverTypeRef = varDecl.declaredTypeRef;
		val member = performOrdinaryMemberScoping("length", receiverTypeRef, propAccess);
		assertTrue("composed member should be contained in a resource", member.eResource!==null);
		assertSame(resource, member.eResource);
	}

	@Test
	def void testContainedMemberWhenUsingNonContainedTypeRef() {
		val script = snippet.parse; // do not validate!
		val resource = script.eResource as N4JSResource;

		val varDecl = script.eAllContents.filter(VariableDeclaration).head;
		val propAccess = script.eAllContents.filter(ParameterizedPropertyAccessExpression).head;

		// use a receiver type reference that is *NOT* contained in a resource
		val receiverTypeRef = TypeUtils.copy(varDecl.declaredTypeRef);
		assertNull(receiverTypeRef.eResource);
		val member = performOrdinaryMemberScoping("length", receiverTypeRef, propAccess);
		assertTrue("composed member should be contained in a resource", member.eResource!==null);
		assertSame(resource, member.eResource);
	}

	@Test
	def void testNoCachingHappensWhenAllowingNonContainedMembers() {
		val script = snippet.parse; // do not validate!

		val varDecl = script.eAllContents.filter(VariableDeclaration).head;
		val propAccess = script.eAllContents.filter(ParameterizedPropertyAccessExpression).head;

		val receiverTypeRef = varDecl.declaredTypeRef;
		val scope = memberScopingHelper.createMemberScopeAllowingNonContainedMembers(receiverTypeRef, propAccess, false,
			true);
		scope.getAllElements();
		assertTrue("scoping should not have added anything to composed member cache",
			script.module.composedMemberCaches.empty);
		
		// only for completeness, we test the counter example using ordinary member scoping:
		val scope2 = memberScopingHelper.createMemberScope(receiverTypeRef, propAccess, false, true);
		scope2.getAllElements();
		assertTrue("scoping should have added several members to composed member cache",
			script.module.composedMemberCaches.head.cachedComposedMembers.size>1); // at time of writing, 16 members were added (but exact count might change over time)
	}

	def private TMember performOrdinaryMemberScoping(String memberName, TypeRef receiverTypeRef, MemberAccess context) {
		val scope = memberScopingHelper.createMemberScope(receiverTypeRef, context, false, true);
		val memberDesc = scope.getSingleElement(qualifiedNameConverter.toQualifiedName(memberName));
		val member = memberDesc.getEObjectOrProxy() as TMember;
		assertFalse((context.eResource as N4JSResource).isFullyProcessed); // ensure scoping did not trigger post-processing
		assertNotNull(member);
		assertFalse(member.eIsProxy);
		assertTrue(member.isComposed);
		return member;
	}
}
