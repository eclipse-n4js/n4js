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
package org.eclipse.n4js.types

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TInterfaceTest extends AbstractContainerTypesTest<TInterface> {

	@Test
	def void testFindOwnedMember_01() {
		val c = '''
			public interface I {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).head,
			c.findOwnedMember("exists"));
	}

	@Test
	def void testFindOwnedMember_02() {
		val c = '''
			public interface I {
				public dup(): C
				public dup(): C
			}
		'''.parseAndGetFirstType

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).head, c.findOwnedMember("dup"));
	}

	@Test
	def void testFindOwnedMember_03() {
		val c = '''
			public interface I {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	def void testFindOwnedMember_04() {
		val c = '''
			public interface I {
			}
		'''.parseAndGetFirstType

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	def void testFindOwnedMember_05() {
		val c = '''
			public interface I {
				name: string;
			}
		'''.parseAndGetFirstType

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).head,
			c.findOwnedMember("name"));
	}

	@Test
	def void testFindClassMember_01() {
		val c = '''
			public interface I {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).head,
			containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	def void testFindClassMember_02() {
		val c = '''
			public interface I {
				public dup(): C
				public dup(): C
			}
		'''.parseAndGetFirstType

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).head,
			containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	def void testFindClassMember_03() {
		val c = '''
			public interface I {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindClassMember_04() {
		val c = '''
			public interface I {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindMemberOfImplicitSuperType_01() {
		val c = '''
			public interface I {
			}
		'''.parseAndGetFirstType

		val G = c.newRuleEnvironment

		assertSame(
			G.objectType.findOwnedMember("toString"),
			containerTypesHelper.fromContext(c).findMember(c, "toString", false, false, true, true));
	}

	@Test
	def void testFindMemberOfImplicitSuperType_02() {
		val c = '''
			public interface I {
			}
		'''.parseAndGetFirstType

		val G = c.newRuleEnvironment

		assertSame(
			G.n4ObjectType.findOwnedMember("constructor"),
			containerTypesHelper.fromContext(c).findConstructor(c));
	}
}
