/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.migrations

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4idl.tests.helper.N4IDLTypeRefTestHelper
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.n4idl.migrations.TypeDistanceComputer.MAX_DISTANCE;

/**
 * Unit-tests with regard to class {@link TypeDistanceComputer}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class TypeDistanceComputerTest extends Assert {
	@Inject extension N4IDLTypeRefTestHelper
	@Inject extension TypeDistanceComputer
	
	private val preamble = '''
	class A#1 implements I {}
	class B#1 extends A implements I {}
	class C#1 extends B {}
	class D#1 extends C implements I {}
	
	interface I#1 {}
	''';
	
	@Test
	public def void testBasicClassifierDistance() {
		assertEquals("A#1 -> A#1", 0, d("A#1", "A#1", preamble), 0);
        assertEquals("A#1 -> A#1", 0, d("A#1", "A#1", preamble), 0);
        assertEquals("B#1 -> A#1", 1, d("B#1", "A#1", preamble), 0);
        assertEquals("C#1 -> A#1", 2, d("C#1", "A#1", preamble), 0);
        
        assertEquals("A#1 -> I#1", 1, d("A#1", "I#1", preamble), 0);
        assertEquals("B#1 -> I#1", 1, d("B#1", "I#1", preamble), 0);
        assertEquals("C#1 -> I#1", 2, d("C#1", "I#1", preamble), 0);
        
        assertEquals("D#1 -> I#1", 1, d("D#1", "I#1", preamble), 0);
        
        assertEquals("A#1 -> C#1", MAX_DISTANCE, d("A#1", "C#1", preamble), 0);
        assertEquals("A#1 -> B#1", MAX_DISTANCE, d("A#1", "B#1", preamble), 0);
        assertEquals("B#1 -> C#1", MAX_DISTANCE, d("B#1", "C#1", preamble), 0);
	}
	
	@Test
	public def void testPrimitiveTypeDistance() {
		assertEquals("int -> int", 0, d("int", "int", preamble), 0);
		assertEquals("string -> string", 0, d("string", "string", preamble), 0);
		assertEquals("boolean -> boolean", 0, d("boolean", "boolean", preamble), 0);
		
		assertEquals("int -> boolean", MAX_DISTANCE, d("int", "boolean", preamble), 0);
		assertEquals("number -> int", MAX_DISTANCE, d("number", "int", preamble), 0);
	}
	
	@Test
	public def void testLiteralTypeDistance() {
		assertEquals(0, d("true", "true", preamble), 0);
		assertEquals(0, d("true", "boolean", preamble), 0);
		assertEquals(0, d("42", "42", preamble), 0);
		assertEquals(0, d("42", "int", preamble), 0);
		assertEquals(0, d("42", "number", preamble), 0);
		assertEquals(0, d("'hello'", "'hello'", preamble), 0);
		assertEquals(0, d("'hello'", "string", preamble), 0);

		assertEquals(MAX_DISTANCE, d("true", "string", preamble), 0);
		assertEquals(MAX_DISTANCE, d("42", "string", preamble), 0);
		assertEquals(MAX_DISTANCE, d("'hello'", "number", preamble), 0);
	}
	
	@Test
	public def void testBuiltInTypeDistance() {
		assertEquals("any -> any", 0, d("any", "any", preamble), 0);		
		assertEquals("any -> boolean", MAX_DISTANCE, d("any", "boolean", preamble), 0);
	}
	
	/**
	 * Parses the two string type expression as {@link TypeRef}s and computes
	 * the type distance from {@code t1} to {@code t2}.
	 */
	private def double d(String typeRef1, String typeRef2, String preamble) {
		val typeRefs = makeTypeRefs(#[typeRef1, typeRef2], preamble);
		return computeDistance(typeRefs.get(0), typeRefs.get(1));
	}
}
