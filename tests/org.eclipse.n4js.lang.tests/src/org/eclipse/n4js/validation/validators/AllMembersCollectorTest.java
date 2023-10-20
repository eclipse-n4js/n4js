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
package org.eclipse.n4js.validation.validators;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AllMembersCollectorTest {
	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ValidationTestHelper valTestHelper;

	@Inject
	ContainerTypesHelper allMembersCollectorHelper;

	@Test
	public void testInherited() throws Exception {
		Script script = parseHelper.parse("""
				class A {
					a: any;
					b(): void {}
					get c(): any { return null }
					set d(p: any) {}
				}
				class B extends A {}
				"""); // only parsed, not linked
		valTestHelper.validate(script);
		// ok?
		assertTrue(script.eResource().getErrors().isEmpty());

		TModule module = script.getModule();
		TClass A = (TClass) module.getTypes().get(0);
		TClass B = (TClass) module.getTypes().get(1);

		MemberList<TMember> membersA = allMembersCollectorHelper.fromContext(script).allMembers(A, false, false);
		MemberList<TMember> membersB = allMembersCollectorHelper.fromContext(script).allMembers(B, false, false);
		assertEquals(4, membersA.size());
		assertEquals(4, membersB.size());
		assertArrayEquals(membersA.toArray(), membersB.toArray());
	}

	@Test
	public void testInheritedAndOverridden() throws Exception {
		Script script = parseHelper.parse("""
				class A {
					a: any;
					b(): void {}
					get c(): any { return null }
					set d(p: any) {}
				}
				class B extends A {
					@Override b(): void {}
					@Override get c(): any { return null }
					@Override set d(p: any) {}
				}
				"""); // only parsed, not linked
		valTestHelper.validate(script);
		// ok?
		assertTrue(script.eResource().getErrors().isEmpty());

		TModule module = script.getModule();
		TClass A = (TClass) module.getTypes().get(0);
		TClass B = (TClass) module.getTypes().get(1);

		MemberList<TMember> membersA = allMembersCollectorHelper.fromContext(script).allMembers(A, false, false);
		MemberList<TMember> membersB = allMembersCollectorHelper.fromContext(script).allMembers(B, false, false);
		assertEquals(4, membersA.size());
		assertEquals(7, membersB.size());
		assertTrue(membersB.containsAll(membersA));
	}

}
