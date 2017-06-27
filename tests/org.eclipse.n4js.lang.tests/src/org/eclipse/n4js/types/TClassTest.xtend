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
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TClassTest extends AbstractContainerTypesTest<TClass> {

	@Test
	def void testFindOwnedMember_01() {
		val c = '''
			public class C {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertSame(c.ownedMembers.head, c.findOwnedMember("exists"));
	}

	@Test
	def void testFindOwnedMember_02() {
		val c = '''
			public class C {
				public dup(): C
				public dup(): C
			}
		'''.parseAndGetFirstType

		assertSame(c.ownedMembers.head, c.findOwnedMember("dup"));
	}

	@Test
	def void testFindOwnedMember_03() {
		val c = '''
			public class C {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	def void testFindOwnedMember_04() {
		val c = '''
			public class C {
			}
		'''.parseAndGetFirstType

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	def void testFindClassMember_01() {
		val c = '''
			public class C {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertSame(c.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	def void testFindClassMember_02() {
		val c = '''
			public class C {
				public dup(): C
				public dup(): C
			}
		'''.parseAndGetFirstType

		assertSame(c.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	def void testFindClassMember_03() {
		val c = '''
			public class C {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindClassMember_04() {
		val c = '''
			public class C {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindClassMember_05() {
		val c = '''
			public class C {
				public exists: C;
			}
		'''.parseAndGetFirstType

		assertNotNull(containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
		assertNotNull(containerTypesHelper.fromContext(c).findMember(c, "exists", true, false));
	}

	@Test
	def void testFindInheritedMember_01() {
		val c = '''
			public class C extends D {
			}
			public class D {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertSame((c.getSuperClassRef.declaredType as TClass).ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	def void testFindInheritedMember_02() {
		val c = '''
			public class C extends D {
			}
			public class D {
				public dup(): C
				public dup(): C
			}
		'''.parseAndGetFirstType

		assertSame((c.getSuperClassRef.declaredType as TClass).ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	def void testFindInheritedMember_03() {
		val c = '''
			public class C extends D {
			}
			public class D {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindInheritedMember_04() {
		val c = '''
			public class C extends D {
			}
			public class D {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindInheritedMember_05() {
		val c = '''
			public class C extends D {
			}
			public class D extends C {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindInheritedMember_06() {
		val c = '''
			public class C extends D {
			}
			public class D extends C {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindInheritedMember_07() {
		val c = '''
			public class C extends C {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindInheritedMember_08() {
		val c = '''
			public class C extends DoesNotExist {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindImplementedMember_01() {
		val c = '''
			public class C implements I {
			}
			public interface I {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertSame((c.implementedInterfaceRefs.head.declaredType as TInterface).ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	def void testFindImplementedMember_02() {
		val c = '''
			public class C implements I {
			}
			public interface I {
				public dup(): C
				public dup(): C
			}
		'''.parseAndGetFirstType

		assertSame((c.implementedInterfaceRefs.head.declaredType as TInterface).ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	def void testFindImplementedMember_03() {
		val c = '''
			public class C implements I {
			}
			public interface I {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindImplementedMember_04() {
		val c = '''
			public class C implements I {
			}
			public interface I {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindPlayedRoleMember_01() {
		val c = '''
			public class C implements R {
			}
			public interface R {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertSame((c.implementedInterfaceRefs.head.declaredType as TInterface).ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	def void testFindPlayedRoleMember_02() {
		val c = '''
			public class C implements R {
			}
			public interface R {
				public dup(): C
				public dup(): C
			}
		'''.parseAndGetFirstType

		assertSame((c.implementedInterfaceRefs.head.declaredType as TInterface).ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	def void testFindPlayedRoleMember_03() {
		val c = '''
			public class C implements R {
			}
			public interface R {
				public exists(): C
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testFindPlayedRoleMember_04() {
		val c = '''
			public class C with R {
			}
			public interface R {
			}
		'''.parseAndGetFirstType

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	def void testInheritedAndImplemented_01() {
		val types = '''
			public class C extends D implements I {
			}
			public class D {
				public m(): C
			}
			public interface I {
				public m(): C
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val d = types.get(1) as TClass
		assertSame(d.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndImplemented_02() {
		val types = '''
			public class C extends D implements J {
			}
			public class D implements I {
			}
			public interface I {
				public m(): D
			}
			public interface J extends I {
				public m(): C
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val i = types.get(2) as TInterface
		assertSame(i.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndImplemented_03() {
		val types = '''
			public class C extends D implements I {
			}
			public class D implements J {
			}
			public interface I {
				public m(): C
			}
			public interface J extends I {
				public m(): D
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val j = types.get(3) as TInterface
		assertSame(j.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndImplemented_04() {
		val types = '''
			public class C extends D implements J {
			}
			public class D implements I {
				public m(): D
			}
			public interface I {
				public m(): D
			}
			public interface J extends I {
				public m(): C
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val d = types.get(1) as TClass
		assertSame(d.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndImplemented_05() {
		val types = '''
			public class C extends D implements I {
			}
			public class D implements J {
				public m(): D
			}
			public interface I {
				public m(): C
			}
			public interface J extends I {
				public m(): D
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val d = types.get(1) as TClass
		assertSame(d.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndPlayed_01() {
		val types = '''
			public class C extends D implements R {
			}
			public class D implements I {
			}
			public interface I {
				public m(): D
			}
			public interface R extends R2, I {
			}
			public interface R2 {
				public m(): C
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val i = types.get(2) as TInterface;
		// val j = types.last as TInterface

		val expected = i.ownedMembers.head
		val actual = containerTypesHelper.fromContext(c).findMember(c, "m", false, false)

		assertSame(expected, actual);
	}

	@Test
	def void testInheritedAndPlayed_02() {
		val types = '''
			public class C extends D implements R {
			}
			public class D implements I {
				public m(): C
			}
			public interface I {
				public m(): C
			}
			public interface R2 {
				public m(): C
			}
			public interface R extends R2, I {
				public m(): C
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val d = types.get(1) as TClass
		// IDE-1236: inherited members precede mixed in
		assertSame(d.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndPlayed_03() {
		val types = '''
			public class C extends D implements I {
			}
			public class D implements R, J {
			}
			public interface I {
				public m(): C
			}
			public interface J extends I {
				public m(): D
			}
			public interface K {
				public m(): C
			}
			public interface R extends K {
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val k = types.get(4) as TInterface
		assertSame(k.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndPlayed_04() {
		val types = '''
			public class C extends D implements R {
			}
			public class D implements I {
				public m(): C
			}
			public interface I {
				public m(): C
			}
			public interface R extends R2, I {
			}
			public interface R2 {
				public m(): C
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val d = types.get(1) as TClass
		// IDE-1236: inherited members precede mixed in
		assertSame(d.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	def void testInheritedAndPlayed_05() {
		val types = '''
			public class C extends D implements R {
			}
			public class D {
				public m(): C
			}
			public interface I {
				public m(): C
			}
			public interface R extends I {
			}
		'''.parseAndGetTypes
		val c = types.head as TClass
		val d = types.get(1) as TClass
		assertSame(d.ownedMembers.head, containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}
}
