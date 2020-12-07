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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import static org.eclipse.n4js.validation.validators.LazyOverrideAwareMemberCollector.*

import static org.junit.Assert.*

/**
 * @see LazyOverrideAwareMemberCollector
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class LazyOverrideAwareMembersCollectorTest {
	@Inject
	extension ParseHelper<Script>

	@Inject
	extension ValidationTestHelper

	@Test
	def void testInherited() {
		val script = '''
			class A {
				a: any;
				b(): void {}
				get c(): any { return null }
				set d(p: any) {}
			}
			class B extends A {}
		'''.parse // only parsed, not linked
		script.validate();

		// ok?
		assertTrue(script.eResource.errors.empty)

		val module = script.module;
		val A = module.topLevelTypes.get(0) as TClass;
		val B = module.topLevelTypes.get(1) as TClass;

		val membersA = collectAllDeclaredMembers(A)
		assertEquals(4, membersA.size);

		val membersB = collectAllDeclaredMembers(B)
		assertEquals(4, membersB.size);

		assertArrayEquals(membersA.toArray, membersB.toArray);

		val inheritedMembersB = collectAllDeclaredInheritedMembers(B)
		assertEquals(4, inheritedMembersB.size);

		assertArrayEquals(membersA.toArray, inheritedMembersB.toArray);
	}

	@Test
	def void testInheritedAndOverridden() {
		val script = '''
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
		'''.parse // only parsed, not linked
		script.validate();

		// ok?
		assertTrue(script.eResource.errors.empty)

		val module = script.module;
		val A = module.topLevelTypes.get(0) as TClass;
		val B = module.topLevelTypes.get(1) as TClass;

		val membersA = collectAllDeclaredMembers(A)
		assertEquals(4, membersA.size);

		val membersB = collectAllDeclaredMembers(B)
		assertEquals(4, membersB.size);

		val inheritedMembersB = collectAllDeclaredInheritedMembers(B)
		assertEquals(4, inheritedMembersB.size);

		assertArrayEquals(membersA.toArray, inheritedMembersB.toArray);

	}

	@Test
	def void testConsumed() {
		val script = '''
			interface R1 {
				f(p: number) : void{}
			}
			interface R2 {
				f(p: string) : void{}
			}
			class C implements R1, R2 {}
		'''.parse // only parsed, not linked
		script.validate(); // there may be errors

		val module = script.module;
		val C = module.topLevelTypes.get(2) as TClass;

		val membersC = collectAllDeclaredMembers(C)
		// actually we found more than we will have later -- the validator will warn us!
		assertEquals("Members: " + membersC.map[it.name].join(",")+".", 2, membersC.size);
	}

	@Test
	def void testConsumedAndInherited() {
		val script = '''
			interface R1 {
				x: string;
			}
			class S {
				x(): void {}
			}
			class C1 extends S implements R1 {}
		'''.parse // only parsed, not linked
		script.validate(); // there may be errors
		val module = script.module;
		val C = module.topLevelTypes.get(2) as TClass;
		val membersC = collectAllDeclaredMembers(C)
		assertEquals(2, membersC.size);
	}

}
