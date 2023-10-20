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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TClassTest extends AbstractContainerTypesTest<TClass> {

	@Test
	public void testFindOwnedMember_01() {
		TClass c = parseAndGetFirstType("""
				public class C {
					public exists(): C
				}
				""");

		assertSame(c.getOwnedMembers().get(0), c.findOwnedMember("exists"));
	}

	@Test
	public void testFindOwnedMember_02() {
		TClass c = parseAndGetFirstType("""
				public class C {
					public dup(): C
					public dup(): C
				}
				""");

		assertSame(c.getOwnedMembers().get(0), c.findOwnedMember("dup"));
	}

	@Test
	public void testFindOwnedMember_03() {
		TClass c = parseAndGetFirstType("""
				public class C {
					public exists(): C
				}
				""");

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	public void testFindOwnedMember_04() {
		TClass c = parseAndGetFirstType("""
				public class C {
				}
				""");

		assertNull(c.findOwnedMember("unknown"));
	}

	@Test
	public void testFindClassMember_01() {
		TClass c = parseAndGetFirstType("""
				public class C {
					public exists(): C
				}
				""");

		assertSame(c.getOwnedMembers().get(0),
				containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	public void testFindClassMember_02() {
		TClass c = parseAndGetFirstType("""
				public class C {
					public dup(): C
					public dup(): C
				}
				""");

		assertSame(c.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	public void testFindClassMember_03() {
		TClass c = parseAndGetFirstType("""
				public class C {
					public exists(): C
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindClassMember_04() {
		TClass c = parseAndGetFirstType("""
				public class C {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindClassMember_05() {
		TClass c = parseAndGetFirstType("""
				public class C {
					public exists: C;
				}
				""");

		assertNotNull(containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
		assertNotNull(containerTypesHelper.fromContext(c).findMember(c, "exists", true, false));
	}

	@Test
	public void testFindInheritedMember_01() {
		TClass c = parseAndGetFirstType("""
				public class C extends D {
				}
				public class D {
					public exists(): C
				}
				""");

		assertSame(((TClass) c.getSuperClassRef().getDeclaredType()).getOwnedMembers().get(0),
				containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	public void testFindInheritedMember_02() {
		TClass c = parseAndGetFirstType("""
				public class C extends D {
				}
				public class D {
					public dup(): C
					public dup(): C
				}
				""");

		assertSame(((TClass) c.getSuperClassRef().getDeclaredType()).getOwnedMembers().get(0),
				containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	public void testFindInheritedMember_03() {
		TClass c = parseAndGetFirstType("""
				public class C extends D {
				}
				public class D {
					public exists(): C
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindInheritedMember_04() {
		TClass c = parseAndGetFirstType("""
				public class C extends D {
				}
				public class D {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindInheritedMember_05() {
		TClass c = parseAndGetFirstType("""
				public class C extends D {
				}
				public class D extends C {
					public exists(): C
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindInheritedMember_06() {
		TClass c = parseAndGetFirstType("""
				public class C extends D {
				}
				public class D extends C {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindInheritedMember_07() {
		TClass c = parseAndGetFirstType("""
				public class C extends C {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindInheritedMember_08() {
		TClass c = parseAndGetFirstType("""
				public class C extends DoesNotExist {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindImplementedMember_01() {
		TClass c = parseAndGetFirstType("""
				public class C implements I {
				}
				public interface I {
					public exists(): C
				}
				""");

		assertSame(((TInterface) c.getImplementedInterfaceRefs().get(0).getDeclaredType()).getOwnedMembers().get(0),
				containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	public void testFindImplementedMember_02() {
		TClass c = parseAndGetFirstType("""
				public class C implements I {
				}
				public interface I {
					public dup(): C
					public dup(): C
				}
				""");

		assertSame(((TInterface) c.getImplementedInterfaceRefs().get(0).getDeclaredType()).getOwnedMembers().get(0),
				containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	public void testFindImplementedMember_03() {
		TClass c = parseAndGetFirstType("""
				public class C implements I {
				}
				public interface I {
					public exists(): C
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindImplementedMember_04() {
		TClass c = parseAndGetFirstType("""
				public class C implements I {
				}
				public interface I {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindPlayedRoleMember_01() {
		TClass c = parseAndGetFirstType("""
				public class C implements R {
				}
				public interface R {
					public exists(): C
				}
				""");

		assertSame(((TInterface) c.getImplementedInterfaceRefs().get(0).getDeclaredType()).getOwnedMembers().get(0),
				containerTypesHelper.fromContext(c).findMember(c, "exists", false, false));
	}

	@Test
	public void testFindPlayedRoleMember_02() {
		TClass c = parseAndGetFirstType("""
				public class C implements R {
				}
				public interface R {
					public dup(): C
					public dup(): C
				}
				""");

		assertSame(((TInterface) c.getImplementedInterfaceRefs().get(0).getDeclaredType()).getOwnedMembers().get(0),
				containerTypesHelper.fromContext(c).findMember(c, "dup", false, false));
	}

	@Test
	public void testFindPlayedRoleMember_03() {
		TClass c = parseAndGetFirstType("""
				public class C implements R {
				}
				public interface R {
					public exists(): C
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testFindPlayedRoleMember_04() {
		TClass c = parseAndGetFirstType("""
				public class C with R {
				}
				public interface R {
				}
				""");

		assertNull(containerTypesHelper.fromContext(c).findMember(c, "unknown", false, false));
	}

	@Test
	public void testInheritedAndImplemented_01() {
		EList<Type> types = parseAndGetTypes("""
				public class C extends D implements I {
				}
				public class D {
					public m(): C
				}
				public interface I {
					public m(): C
				}
				""");
		TClass c = (TClass) types.get(0);
		TClass d = (TClass) types.get(1);
		assertSame(d.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndImplemented_02() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TInterface i = (TInterface) types.get(2);
		assertSame(i.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndImplemented_03() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TInterface j = (TInterface) types.get(3);
		assertSame(j.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndImplemented_04() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TClass d = (TClass) types.get(1);
		assertSame(d.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndImplemented_05() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TClass d = (TClass) types.get(1);
		assertSame(d.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndPlayed_01() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TInterface i = (TInterface) types.get(2);
		// TInterface j = (TInterface) types.last;

		TMember expected = i.getOwnedMembers().get(0);
		TMember actual = containerTypesHelper.fromContext(c).findMember(c, "m", false, false);

		assertSame(expected, actual);
	}

	@Test
	public void testInheritedAndPlayed_02() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TClass d = (TClass) types.get(1);
		// IDE-1236: inherited members precede mixed in
		assertSame(d.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndPlayed_03() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TInterface k = (TInterface) types.get(4);
		assertSame(k.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndPlayed_04() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TClass d = (TClass) types.get(1);
		// IDE-1236: inherited members precede mixed in
		assertSame(d.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}

	@Test
	public void testInheritedAndPlayed_05() {
		EList<Type> types = parseAndGetTypes("""
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
				""");
		TClass c = (TClass) types.get(0);
		TClass d = (TClass) types.get(1);
		assertSame(d.getOwnedMembers().get(0), containerTypesHelper.fromContext(c).findMember(c, "m", false, false));
	}
}
