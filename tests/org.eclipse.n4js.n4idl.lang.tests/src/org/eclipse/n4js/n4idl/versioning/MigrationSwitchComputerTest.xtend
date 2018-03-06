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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4idl.MigrationSwitchComputer
import org.eclipse.n4js.n4idl.SwitchCondition
import org.eclipse.n4js.n4idl.tests.helper.AbstractN4IDLTypeSwitchTest
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef

/**
 * Unit tests wrt to class {@link MigrationSwitchComputer}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public class MigrationSwitchComputerTest extends AbstractN4IDLTypeSwitchTest {
	
	@Inject private TypeSystemHelper typeSystemHelper;
	
	@Test
	public def testBasicTypeExpressions() {
		assertEquals("Simple type expression A#1", "v instanceof A#1", compute("A#1", classes("A#1")));
		assertEquals("Simple type expression A#1 (multiple versions)", "v instanceof A#2", compute("A#2", classes("A#1", "A#2")));
	}
	
	@Test
	public def testBasicArrayExpressions() {
		val preamble = classes("A#1", "A#2")
		
		assertEquals("Array type ref", "(v instanceof Array && v.length > 0 && (v[0] instanceof A#2))", compute("[A#2]", preamble));
		assertEquals("Array type ref (using Array<>)", "(v instanceof Array && v.length > 0 && (v[0] instanceof A#2))", compute("Array<A#2>", preamble));
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
	
	/** Test checks the loss of information when computing {@link SwitchCondition}s and
	 * converting back to {@link TypeRef}s. */
	@Test
	public def testTypeConditionsToTypeRef() {
		val preamble = '''
		class A#1 {}
		class A#2 {}
		class A#3 {}
		class A#4 {}
		class A#5 {}
		
		class B#1<T> {}
		class B#2<T> {}
		class B#3<T> {}
		
		interface I#1 {}
		interface I#2 {}
		
		interface J#1<T> {}
		interface J#2<T1, T2> {}
		'''
		
		assertEquals("Unparameterized type ref 1", "A#1", computeAndConvertToTypeRef("A#1", preamble))
		assertEquals("Unparameterized type ref 1", "A#2", computeAndConvertToTypeRef("A#2", preamble))
		
		assertEquals("Parameterized type ref 1", "B#1", computeAndConvertToTypeRef("B#1<A#1>", preamble));
		assertEquals("Parameterized type ref 2", "Iterable2<?,?>", computeAndConvertToTypeRef("Iterable2<A#1, A#1>", preamble));
		
		assertEquals("Parameterized array type ref 1", "Array<A#1>", computeAndConvertToTypeRef("Array<A#1>", preamble));
		assertEquals("Parameterized array type ref 2 ([<typeRef>] syntax)", "Array<A#1>", computeAndConvertToTypeRef("[A#1]", preamble));
		
		
		assertEquals("Unparameterized composed type ref 1", 
			"union{intersection{Array<union{A#2,A#1}>,union{intersection{I#1,A#3},A#5}},I#1}", 
			computeAndConvertToTypeRef("(([A#2|A#1])&((I#1&A#3|A#5)))|I#1", preamble))
			
		assertEquals("Parameterized composed type ref 1", 
			"union{intersection{Array<union{B#2,B#1}>,union{intersection{J#2,J#2},B#3}},J#2}",
			computeAndConvertToTypeRef("(([B#2<A#2>|B#1<A#1>])&((J#2<I#1, I#2>&J#2<A#1, A#2>|B#3<B#3<A#3>>)))|J#2<I#1, I#2>", preamble))
	}
	
	@Test
	public def void testSwitchConditionDisambiguation() {
		val preamble = '''
		class A#1 {}
		class A#2 {}
		class A#3 {}
		class A#4 {}
		class A#5 {}
		
		class B#1<T> {}
		class B#2<T> {}
		class B#3<T> {}
		
		interface I#1 {}
		interface I#2 {}
		
		interface J#1<T> {}
		interface J#2<T1, T2> {}
		'''
//		val typeExpression = "(([B#2<A#2>|B#1<A#1>])&((J#2<I#1, I#2>&J#2<A#1, A#2>|B#3<B#3<A#3>>)))|J#2<I#1, I#2>";
		val typeExpression = "[B#1<A#1>|J#1<I#1>]";

		val condition = computeSwitch(typeExpression, preamble)
		val disambiguatedConditions = disambiguate(condition);
		// make sure all OR conditions have been factored out
		disambiguatedConditions.forEach[c | assertSwitchDoesNotContainOr(c) ];
		
		// create corresponding type refs
		val typeRefs = disambiguatedConditions.map[c | toTypeRef(c) ].toList;
		val disambiguatedTypeRef = TypeUtils.createNonSimplifiedUnionType(typeRefs);
		val ambiguousTypeRef = toTypeRef(condition);
		
		//val normAmb = typeSystemHelper.simplify(condition.createRuleEnvironment, ambiguousTypeRef as ComposedTypeRef);
		val normDisamb = typeSystemHelper.simplify(condition.createRuleEnvironment, disambiguatedTypeRef);
		
		val result = typeSystem.equaltypeSucceeded(condition.createRuleEnvironment, ambiguousTypeRef, normDisamb);
		
		println(typeRefs.join("\n"))
		println(ambiguousTypeRef);
		println(normDisamb);
		
		assertTrue("Disambiguation does not alter type reference", result);
	}
}
