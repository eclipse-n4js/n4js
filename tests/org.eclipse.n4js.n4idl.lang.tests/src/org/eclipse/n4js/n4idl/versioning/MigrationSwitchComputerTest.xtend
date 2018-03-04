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
package org.eclipse.n4js.n4idl.versioning

import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4idl.MigrationSwitchComputer
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit tests wrt to class {@link MigrationSwitchComputer}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public class MigrationSwitchComputerTest extends Assert {
	@Inject extension private TypeRefTestExtension
	
	@Inject private MigrationSwitchComputer switchComputer;
	
	@Test
	public def testBasicTypeExpressions() {
		assertEquals("Simple type expression A#1", "v instanceof A#1", compute("A#1", #["A#1"]));
		assertEquals("Simple type expression A#1 (multiple versions)", "v instanceof A#2", compute("A#2", #["A#1", "A#2"]));
	}
	
	@Test
	public def testBasicArrayExpressions() {
		assertEquals("Array type ref", "(v instanceof Array && v.length > 0 && (v[0] instanceof A#2))", compute("[A#2]", #["A#1", "A#2"]));
		assertEquals("Array type ref (using Array<>)", "(v instanceof Array && v.length > 0 && (v[0] instanceof A#2))", compute("Array<A#2>", #["A#1", "A#2"]));
	}
	
	@Test
	public def testBasicComposedTypeExpressions() {
		val preamble = '''
		class A#1 {}
		class A#2 {}
		interface I#1 {}
		interface I#2 {}
		'''
		
		assertEquals("Union type using '|'", "(v instanceof A#2) || (v instanceof A#1)", compute("A#2|A#1", preamble));
		assertEquals("Union type using 'union'", "(v instanceof A#2) || (v instanceof A#1)", compute("union{A#2, A#1}", preamble));
		
		assertEquals("Intersection type using '&'", "(v instanceof I#2) && (v instanceof I#1)", compute("I#2&I#1", preamble));
		assertEquals("Intersection type using 'intersection'", "(v instanceof I#2) && (v instanceof I#1)", compute("intersection{I#2, I#1}", preamble));
		
		assertEquals("Intersection with class and interface", "(v instanceof A#2) && (v instanceof I#1)", compute("A#2&I#1", preamble));
	}
	
	@Test
	public def testNestedComposedTypeExpressions() {
		val preamble = '''
		class A#1 {}
		class A#2 {}
		class A#3 {}
		class A#4 {}
		class A#5 {}
		
		interface I#1 {}
		interface I#2 {}
		'''
		
		assertEquals("Nested composed type: (A#2|A#1)&(A#3|A#5)", 
			"((v instanceof A#2) || (v instanceof A#1)) && ((v instanceof A#3) || (v instanceof A#5))", 
			compute("(A#2|A#1)&(A#3|A#5)", preamble));
			
		assertEquals("Nested composed type: ((A#2|A#1)&((I#1&A#3|A#5)))|I#1", 
			"(((v instanceof A#2) || (v instanceof A#1)) && (((v instanceof I#1) && (v instanceof A#3)) || (v instanceof A#5))) || (v instanceof I#1)",
			compute("((A#2|A#1)&((I#1&A#3|A#5)))|I#1", preamble));
	}
	
	@Test
	public def testNestedComposedArrayTypeExpressions() {
		val preamble = '''
		class A#1 {}
		class A#2 {}
		class A#3 {}
		class A#4 {}
		class A#5 {}
		
		interface I#1 {}
		interface I#2 {}
		'''
		
		assertEquals("Nested composed type: (A#2|[A#1])&(A#3|Array<A#5>)", 
			"((v instanceof A#2) || ((v instanceof Array && v.length > 0 && (v[0] instanceof A#1)))) && ((v instanceof A#3) || ((v instanceof Array && v.length > 0 && (v[0] instanceof A#5))))", 
			compute("(A#2|[A#1])&(A#3|Array<A#5>)", preamble));
			
		assertEquals("Nested composed type: ", 
			"(((v instanceof Array && v.length > 0 && ((v[0] instanceof A#2) || (v[0] instanceof A#1)))) && (((v instanceof I#1) && (v instanceof A#3)) || (v instanceof A#5))) || (v instanceof I#1)",
			compute("(([A#2|A#1])&((I#1&A#3|A#5)))|I#1", preamble));
	}
	
	/**
	 * Creates a {@link TypeRef} for the given type expression string.
	 * 
	 * Prepends the given preamble to the N4IDL module before parsing it.
	 * 
	 * Computes the corresponding TypeSwitchCondition and returns its string representation.
	 */
	private def String compute(String typeExpression, String preamble) {
		return switchComputer.compute(makeTypeRef(typeExpression, preamble)).toString;
	}
	
	/**
	 * Creates a {@link TypeRef} for the given type expression string.
	 * 
	 * Creates dummy types for all type names contained in {@code existingTypes}.
	 * 
	 * Computes the corresponding TypeSwitchCondition and returns its string representation.
	 */
	private def String compute(String typeExpression, List<String> existingTypes) {
		return switchComputer.compute(makeTypeRef(typeExpression, existingTypes)).toString;
	}
}
