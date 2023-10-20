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

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * @see LazyOverrideAwareMemberCollector
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class LazyOverrideAwareMembersCollectorTest {

	@Inject
	LazyOverrideAwareMemberCollector lazyOverrideAwareMemberCollector;

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ValidationTestHelper valTestHelper;

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

		List<TMember> membersA = lazyOverrideAwareMemberCollector.collectAllDeclaredMembers(A);
		assertEquals(4, membersA.size());

		List<TMember> membersB = lazyOverrideAwareMemberCollector.collectAllDeclaredMembers(B);
		assertEquals(4, membersB.size());

		assertArrayEquals(membersA.toArray(), membersB.toArray());

		List<TMember> inheritedMembersB = lazyOverrideAwareMemberCollector.collectAllDeclaredInheritedMembers(B);
		assertEquals(4, inheritedMembersB.size());

		assertArrayEquals(membersA.toArray(), inheritedMembersB.toArray());
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

		List<TMember> membersA = lazyOverrideAwareMemberCollector.collectAllDeclaredMembers(A);
		assertEquals(4, membersA.size());

		List<TMember> membersB = lazyOverrideAwareMemberCollector.collectAllDeclaredMembers(B);
		assertEquals(4, membersB.size());

		List<TMember> inheritedMembersB = lazyOverrideAwareMemberCollector.collectAllDeclaredInheritedMembers(B);
		assertEquals(4, inheritedMembersB.size());

		assertArrayEquals(membersA.toArray(), inheritedMembersB.toArray());

	}

	@Test
	public void testConsumed() throws Exception {
		Script script = parseHelper.parse("""
				interface R1 {
					f(p: number) : void{}
				}
				interface R2 {
					f(p: string) : void{}
				}
				class C implements R1, R2 {}
				"""); // only parsed, not linked
		valTestHelper.validate(script); // there may be errors

		TModule module = script.getModule();
		TClass C = (TClass) module.getTypes().get(2);

		List<TMember> membersC = lazyOverrideAwareMemberCollector.collectAllDeclaredMembers(C);
		// actually we found more than we will have later -- the validator will warn us!
		assertEquals("Members: " + join(",", map(membersC, m -> m.getName())) + ".", 2, membersC.size());
	}

	@Test
	public void testConsumedAndInherited() throws Exception {
		Script script = parseHelper.parse("""
				interface R1 {
					x: string;
				}
				class S {
					x(): void {}
				}
				class C1 extends S implements R1 {}
				"""); // only parsed, not linked
		valTestHelper.validate(script); // there may be errors
		TModule module = script.getModule();
		TClass C = (TClass) module.getTypes().get(2);
		List<TMember> membersC = lazyOverrideAwareMemberCollector.collectAllDeclaredMembers(C);
		assertEquals(2, membersC.size());
	}

}
