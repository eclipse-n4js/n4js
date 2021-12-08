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
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AllMembersCollectorTest {
	@Inject
	extension ParseHelper<Script>

	@Inject
	extension ValidationTestHelper

	@Inject ContainerTypesHelper allMembersCollectorHelper


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
		val A = module.types.get(0) as TClass;
		val B = module.types.get(1) as TClass;

		val membersA = allMembersCollectorHelper.fromContext(script).allMembers(A, false, false);
		val membersB = allMembersCollectorHelper.fromContext(script).allMembers(B, false, false);
		assertEquals(4, membersA.size);
		assertEquals(4, membersB.size);
		assertArrayEquals(membersA.toArray, membersB.toArray);
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
		val A = module.types.get(0) as TClass;
		val B = module.types.get(1) as TClass;

		val membersA = allMembersCollectorHelper.fromContext(script).allMembers(A, false, false);
		val membersB = allMembersCollectorHelper.fromContext(script).allMembers(B, false, false);
		assertEquals(4, membersA.size);
		assertEquals(7, membersB.size);
		assertTrue(membersB.containsAll(membersA));
	}

}
