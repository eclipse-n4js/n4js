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

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4idl.MigrationSwitchComputer
import org.eclipse.n4js.n4idl.SwitchCondition
import org.eclipse.n4js.n4idl.tests.helper.AbstractN4IDLTypeSwitchTest
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit tests wrt to class {@link MigrationSwitchComputer}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
public class MigrationSwitchComputerTest extends AbstractN4IDLTypeSwitchTest {
	
	@Test
	public def testBasicTypeExpressions() {
		assertEquals("Simple type expression A#1", "v instanceof A#1", compute("A#1", classes("A#1")));
		assertEquals("Simple type expression A#1 (multiple versions)", "v instanceof A#2", compute("A#2", classes("A#1", "A#2")));
	}
	
	@Test
	public def testBasicArrayExpressions() {
		val preamble = classes("A#1", "A#2")
		
		assertEquals("Array type ref", "(v array with (v[0] instanceof A#2))", compute("[A#2]", preamble));
		assertEquals("Array type ref (using Array<>)", "(v array with (v[0] instanceof A#2))", compute("Array<A#2>", preamble));
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
		
		assertEquals("Nested composed type 1: (A#2|[A#1])&(A#3|Array<A#5>)", 
			"((v instanceof A#2) || ((v array with (v[0] instanceof A#1)))) && ((v instanceof A#3) || ((v array with (v[0] instanceof A#5))))", 
			compute("(A#2|[A#1])&(A#3|Array<A#5>)", preamble));
			
		assertEquals("Nested composed type 2: (([A#2|A#1])&((I#1&A#3|A#5)))|I#1", 
			"((((v array with (v[0] instanceof A#2))) || ((v array with (v[0] instanceof A#1)))) && (((v instanceof I#1) && (v instanceof A#3)) || (v instanceof A#5))) || (v instanceof I#1)",
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
			"union{intersection{union{Array<A#2>,Array<A#1>},union{intersection{I#1,A#3},A#5}},I#1}", 
			computeAndConvertToTypeRef("(([A#2|A#1])&((I#1&A#3|A#5)))|I#1", preamble))
			
		assertEquals("Parameterized composed type ref 1", 
			"union{intersection{union{Array<B#2>,Array<B#1>},union{intersection{J#2,J#2},B#3}},J#2}",
			computeAndConvertToTypeRef("(([B#2<A#2>|B#1<A#1>])&((J#2<I#1, I#2>&J#2<A#1, A#2>|B#3<B#3<A#3>>)))|J#2<I#1, I#2>", preamble))
	}
	
	/**
	 * Test which checks, that the {@link MigrationSwitchComputer#toCNF} does not produce any
	 * non-equivalent switch conditions. */
	@Test
	public def void testSwitchConditionCNFConversion() {
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
	
		assertDNFConversionDoesNotAlterType("Non-parameterized type ref", "A#1", preamble);
		assertDNFConversionDoesNotAlterType("Parameterized type ref", "B#1<A#1>", preamble);
		
		assertDNFConversionDoesNotAlterType("Simple array reference", "Array<A#1>", preamble);
		assertDNFConversionDoesNotAlterType("Simple array reference '[]' syntax", "[A#1]", preamble);
		
		assertDNFConversionDoesNotAlterType("Simple union type ref", "A#1|I#2", preamble);
		assertDNFConversionDoesNotAlterType("Simple intersection type ref ", "A#1&I#2", preamble);
		
		assertDNFConversionDoesNotAlterType("Array union type ref", "[A#1|I#2]", preamble);
		assertDNFConversionDoesNotAlterType("Array intersection type ref", "[A#1&I#2]", preamble);
		
		assertDNFConversionDoesNotAlterType("Intersection type with array argument", "[A#1]&A#2", preamble);
		assertDNFConversionDoesNotAlterType("Union type with array argument", "[A#1]|A#2", preamble);
		
		// TODO enable test case
//		assertDNFConversionDoesNotAlterType("Intersection type with composed array argument", "[A#1|I#2]&A#2", preamble);
		assertDNFConversionDoesNotAlterType("Union type with array argument", "[A#1|I#2]|A#2", preamble);
		
		assertDNFConversionDoesNotAlterType("Nested composed type reference", "(([A#1|I#2]|A#2)&A#3&(I#2|I#4))|I#3", preamble);
		
		// TODO enable the following test case
//		assertDNFConversionDoesNotAlterType("Complex nested composed type reference", "(([B#2<A#2>|B#1<A#1>])&((J#2<I#1, I#2>&J#2<A#1, A#2>|B#3<B#3<A#3>>)))|J#2<I#1, I#2>", preamble);
//		assertDNFConversionDoesNotAlterType("([B#1<A#1>|J#1<I#1>]|A#2&I#2)", preamble)
	}
	
	/**
	 * Asserts that {@link SwitchCondition} type reference inferred from the given type expression 
	 * is not altered by converting it a DNF switch condition using {@link MigrationSwitchComputer#toDNF}.
	 */
	public def void assertDNFConversionDoesNotAlterType(String testDescription, String typeExpression, String preamble) {
		val condition = computeSwitch(typeExpression, preamble)
		val dnfConditionClauses = toDNF(condition);
		
		// make sure all OR conditions have been factored out
		dnfConditionClauses.forEach[c | assertSwitchDoesNotContainOr(c) ];
		
		// create corresponding DNF clause type refs
		val typeRefs = dnfConditionClauses.map[c | toTypeRef(c) ].toList;
		// combine clauses using a union type
		val dnfTypeRef = normalize(TypeUtils.createNonSimplifiedUnionType(typeRefs));
		
		// create non-DNF type ref
		val originalTypeRef = normalize(toTypeRef(condition));
		
		val ruleEnv = condition.createRuleEnvironment;
		
		// check type-refs to be equal
		if (!typeSystem.equaltypeSucceeded(ruleEnv, dnfTypeRef, originalTypeRef)) {
			println("After DNF conversion:	" + dnfTypeRef);
			println("Original TypeRef:	" + originalTypeRef);
			fail(testDescription + " '" + typeExpression + "': DNF conversion does not maintain equivalence.");
		}
	}
	
	/** Normalizes a composed type reference by unpacking composed
	 * type refs with a single argument (e.g. union{A#1} -> A#2). 
	 * 
	 * Normally, the type system would do that on its own, however currently a bug, 
	 * prevents some tests from passing. Therefore, this normalization 
	 * works around that issue.
	 */
	private def TypeRef normalize(TypeRef ref) {
		if (ref instanceof ComposedTypeRef) {
			if (ref.typeRefs.size == 1) {
				return normalize(ref.typeRefs.get(0));
			}
		}
		return ref;
	}
}
