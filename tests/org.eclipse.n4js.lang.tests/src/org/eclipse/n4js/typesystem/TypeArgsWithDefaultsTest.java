/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;

import java.util.Objects;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest;
import org.junit.Test;

/**
 * Tests method {@link ParameterizedTypeRef#getTypeArgsWithDefaults()}.
 */
public class TypeArgsWithDefaultsTest extends AbstractTypesystemTest {

	@Test
	public void testSimple() throws Exception {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				class A {}
				class B {}
				class G<T0, T1=A, T2=A> {}
				""");

		TClass B = (TClass) findFirst(script.getModule().getTypes(), t -> Objects.equals(t.getName(), "B"));
		TClass G = (TClass) findFirst(script.getModule().getTypes(), t -> Objects.equals(t.getName(), "G"));

		TypeRef typeRef01 = of(G, B); // G<B>
		TypeRef typeRef02 = of(G, B, B); // G<B,B>
		TypeRef typeRef03 = of(G, B, B, B); // G<B,B,B>

		assertEquals("B, A, A", join(", ", map(typeRef01.getTypeArgsWithDefaults(), arg -> arg.getTypeRefAsString())));
		assertEquals("B, B, A", join(", ", map(typeRef02.getTypeArgsWithDefaults(), arg -> arg.getTypeRefAsString())));
		assertEquals("B, B, B", join(", ", map(typeRef03.getTypeArgsWithDefaults(), arg -> arg.getTypeRefAsString())));
	}

	@Test
	public void testRawTypeReferences() throws Exception {
		Script script = _n4JSTestHelper.parseAndValidateSuccessfully("""
				class A {}
				class B {}
				class G<T0, T1, T2=A, T3=A> {}
				""");

		TClass B = (TClass) findFirst(script.getModule().getTypes(), t -> Objects.equals(t.getName(), "B"));
		TClass G = (TClass) findFirst(script.getModule().getTypes(), t -> Objects.equals(t.getName(), "G"));

		TypeRef typeRef01 = rawTypeRef(G); // G
		TypeRef typeRef02 = rawTypeRef(G, ref(B)); // G<B>

		// we do not want to see something like "[unknown], [unknown], A, A"
		assertEquals("", join(", ", map(typeRef01.getTypeArgsWithDefaults(), arg -> arg.getTypeRefAsString())));

		// we do not want to see something like "B, [unknown], A, A"
		assertEquals("B", join(", ", map(typeRef02.getTypeArgsWithDefaults(), arg -> arg.getTypeRefAsString())));
	}
}
