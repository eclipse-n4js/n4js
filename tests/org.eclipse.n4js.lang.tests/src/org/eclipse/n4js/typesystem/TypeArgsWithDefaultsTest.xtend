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
package org.eclipse.n4js.typesystem

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests method {@link ParameterizedTypeRef#getTypeArgsWithDefaults()}.
 */
class TypeArgsWithDefaultsTest extends AbstractTypesystemTest {

	@Test
	def void testSimple() {
		val script = '''
			class A {}
			class B {}
			class G<T0, T1=A, T2=A> {}
		'''.parseAndValidateSuccessfully;

		val B = script.module.types.findFirst[name == "B"] as TClass;
		val G = script.module.types.findFirst[name == "G"] as TClass;

		val typeRef01 = G.of(B);     // G<B>
		val typeRef02 = G.of(B,B);   // G<B,B>
		val typeRef03 = G.of(B,B,B); // G<B,B,B>

		assertEquals("B, A, A", typeRef01.typeArgsWithDefaults.map[typeRefAsString].join(", "));
		assertEquals("B, B, A", typeRef02.typeArgsWithDefaults.map[typeRefAsString].join(", "));
		assertEquals("B, B, B", typeRef03.typeArgsWithDefaults.map[typeRefAsString].join(", "));
	}

	@Test
	def void testRawTypeReferences() {
		val script = '''
			class A {}
			class B {}
			class G<T0, T1, T2=A, T3=A> {}
		'''.parseAndValidateSuccessfully;

		val B = script.module.types.findFirst[name == "B"] as TClass;
		val G = script.module.types.findFirst[name == "G"] as TClass;

		val typeRef01 = G.rawTypeRef;        // G
		val typeRef02 = G.rawTypeRef(B.ref); // G<B>

		// we do not want to see something like "[unknown], [unknown], A, A"
		assertEquals("", typeRef01.typeArgsWithDefaults.map[typeRefAsString].join(", "));

		// we do not want to see something like "B, [unknown], A, A"
		assertEquals("B", typeRef02.typeArgsWithDefaults.map[typeRefAsString].join(", "));
	}	
}
