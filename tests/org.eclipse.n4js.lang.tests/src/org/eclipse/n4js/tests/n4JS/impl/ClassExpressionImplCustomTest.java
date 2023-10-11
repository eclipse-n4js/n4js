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
package org.eclipse.n4js.tests.n4JS.impl;

import static org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator.allowClassExpressions;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4ClassDefinition;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class ClassExpressionImplCustomTest {
	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ValidationTestHelper valTestHelper;

	@Inject
	ContainerTypesHelper containerTypesHelper;

	@Test
	public void testGenericParserSimpleWithBinding() throws Exception {
		Script program = parseHelper.parse(
				"""
						var C = class {
							a: any;
							b: any;
							c: any;
						};
						"""); // only parsed, not linked

		// syntax ok?
		assertTrue(program.eResource().getErrors().isEmpty());
		// bindings ok?
		allowClassExpressions(() -> valTestHelper.assertNoErrors(program));

		TClass classExpression = (TClass) head(filter(program.eAllContents(), N4ClassExpression.class))
				.getDefinedType();
		MemberList<TMember> allMembers = containerTypesHelper.fromContext(program).members(classExpression);
		for (TMember m : classExpression.getOwnedMembers()) {
			assertTrue(allMembers.contains(m));
		}
	}

	@Test
	public void testAllMembersInSync() throws Exception {
		String programAsString = """
				class X {}
				class Y {
					abc: any = null;
					foox(): any {
						this.abc;
					}
				}
				class Z extends Y {
				}
				""";
		Script program = parseHelper.parse(programAsString);
		// TODO adapt after Xcore is running
		Iterator<N4ClassDefinition> classDefs = filter(program.eAllContents(), N4ClassDefinition.class);
		N4ClassDefinition classX = classDefs.next();
		N4ClassDefinition classY = classDefs.next();
		N4ClassDefinition classZ = classDefs.next();
		TClass typeX = (TClass) classX.getDefinedType();
		TClass typeY = (TClass) classY.getDefinedType();
		TClass typeZ = (TClass) classZ.getDefinedType();

		MemberList<TMember> allMembersX = containerTypesHelper.fromContext(program).members(typeX);
		MemberList<TMember> allMembersY = containerTypesHelper.fromContext(program).members(typeY);
		MemberList<TMember> allMembersZ = containerTypesHelper.fromContext(program).members(typeZ);

		assertEquals(2, allMembersY.size() - allMembersX.size());
		assertEquals(2, allMembersZ.size() - allMembersX.size());

		Iterable<TMember> nonBaseMembers = IterableExtensions.filter(allMembersZ, it -> !allMembersX.contains(it));
		TMember firstMember = nonBaseMembers.iterator().next();
		assertEquals("abc", firstMember.getName());

		// rename abc to axc
		XtextResource resource = (XtextResource) program.eResource();
		String uriZ = resource.getURIFragment(classZ);
		resource.update(programAsString.indexOf("abc") + 1, 1, "x");

		// no partial parsing, classZ is no longer contained
		assertNull(classZ.eResource());
		classZ = (N4ClassDefinition) resource.getEObject(uriZ);
		// trigger derived state computation
		resource.getContents();

		TClass reparsedType = (TClass) classZ.getDefinedType();
		allMembersZ = containerTypesHelper.fromContext(classZ).members(reparsedType);
		assertEquals(2, allMembersZ.size() - allMembersX.size());

		nonBaseMembers = IterableExtensions.filter(allMembersZ, it -> !allMembersX.contains(it));
		firstMember = nonBaseMembers.iterator().next();
		assertEquals("axc", firstMember.getName());
	}
}
