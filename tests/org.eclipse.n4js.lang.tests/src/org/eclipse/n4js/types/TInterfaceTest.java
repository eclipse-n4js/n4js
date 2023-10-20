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
package org.eclipse.n4js.types;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.junit.Assert.assertNull;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TInterfaceTest extends AbstractContainerTypesTest<TInterface> {

	@Test
	public void testFindOwnedMember_01() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
					public exists(): C
				}
				""");

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).get(0),
				c.findOwnedMember("exists"));
	}

	@Test
	public void testFindOwnedMember_02() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
					public dup(): C
					public dup(): C
				}
				""");

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).get(0), c.findOwnedMember("dup"));
	}

	@Test
	public void testFindOwnedMember_03() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
					public exists(): C
				}
				""");

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	public void testFindOwnedMember_04() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
				}
				""");

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	public void testFindOwnedMember_05() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
					name: string;
				}
				""");

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).get(0),
				c.findOwnedMember("name"));
	}

	@Test
	public void testFindClassMember_01() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
					public exists(): C
				}
				""");

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).get(0),
				containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	public void testFindClassMember_02() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
					public dup(): C
					public dup(): C
				}
				""");

		assertSame(containerTypesHelper.fromContext(c).members(c, false, false).get(0),
				containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	public void testFindClassMember_03() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
					public exists(): C
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindClassMember_04() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindMemberOfImplicitSuperType_01() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
				}
				""");

		RuleEnvironment G = newRuleEnvironment(c);

		assertSame(
				objectType(G).findOwnedMember("toString"),
				containerTypesHelper.fromContext(c).findMember(c, "toString", false, false, true, true));
	}

	@Test
	public void testFindMemberOfImplicitSuperType_02() {
		TInterface c = parseAndGetFirstType("""
				public interface I {
				}
				""");

		RuleEnvironment G = newRuleEnvironment(c);

		assertSame(
				n4ObjectType(G).findOwnedMember("constructor"),
				containerTypesHelper.fromContext(c).findConstructor(c));
	}
}
