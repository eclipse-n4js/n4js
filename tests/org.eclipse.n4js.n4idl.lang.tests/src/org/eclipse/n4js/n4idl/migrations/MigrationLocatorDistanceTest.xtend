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
import java.util.List
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4idl.tests.helper.N4IDLTypeRefTestHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.n4idl.migrations.TypeDistanceComputer.MAX_DISTANCE

/**
 * Unit-tests with regard to {@link TypeDistanceComputer#computeDistance(List, List)}.
 * 
 * The helper methods {@link #d(List<String>, List<String>)}, {@link #args(List<String>)} and 
 * {@link #types(List<String>)} are use to make sure that all assertion statements in this test
 * are copy-paste-compatible with N4JS code and thus can be re-used by tests for the runtime type distance computer. 
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class MigrationLocatorDistanceTest extends Assert {
	@Inject private extension N4IDLTypeRefTestHelper;
	@Inject private TypeDistanceComputer distanceComputer;
	
	// Existing N4IDL types for all test cases in this class
	private final String preamble = '''
	class A#1 implements I {}
	class B#1 extends A implements I {}
	class C#1 extends B {}
	
	interface I#1 {}
	'''
	
	@Test
	public def testBasicInstanceMigrationDistance() {
		assertMigrationDistance(d(args("new A#1()"), types("A#1")), 0, "A#1 -> A#1");
		assertMigrationDistance(d(args("new B#1()"), types("A#1")), 1, "B#1 -> A#1");
		assertMigrationDistance(d(args("new C#1()"), types("A#1")), 2, "C#1 -> A#1");
		
		assertMigrationDistance(d(args("new A#1()"), types("I#1")), 1, "A#1 -> I#1");
		assertMigrationDistance(d(args("new B#1()"), types("I#1")), 1, "B#1 -> I#1");
		assertMigrationDistance(d(args("new C#1()"), types("I#1")), 2, "C#1 -> I#1");
		
		assertMigrationDistance(d(args("new A#1()"), types("C#1")), MAX_DISTANCE, "A#1 -> C#1");
		assertMigrationDistance(d(args("new A#1()"), types("B#1")), MAX_DISTANCE, "A#1 -> B#1");
		assertMigrationDistance(d(args("new B#1()"), types("C#1")), MAX_DISTANCE, "B#1 -> C#1");
		
		// two parameters
		
		assertMigrationDistance(d(args("new A#1()", "new A#1()"), types("A#1", "A#1")), 0, "A#1, A#1 -> A#1, A#1");
		assertMigrationDistance(d(args("new A#1()", "new B#1()"), types("A#1", "A#1")), 1, "A#1, B#1 -> A#1, A#1");
		assertMigrationDistance(d(args("new B#1()", "new B#1()"), types("A#1", "A#1")), 2, "B#1, B#1 -> A#1, A#1");
		assertMigrationDistance(d(args("new C#1()", "new A#1()"), types("A#1", "A#1")), 2, "C#1, A#1 -> A#1, A#1");
		assertMigrationDistance(d(args("new A#1()", "new C#1()"), types("A#1", "A#1")), 2, "A#1, C#1 -> A#1, A#1");
		assertMigrationDistance(d(args("new B#1()", "new C#1()"), types("A#1", "A#1")), 3, "B#1, C#1 -> A#1, A#1");
		assertMigrationDistance(d(args("new C#1()", "new C#1()"), types("A#1", "A#1")), 4, "C#1, C#1 -> A#1, A#1");
		
		assertMigrationDistance(d(args("new A#1()", "new A#1()"), types("B#1", "A#1")), MAX_DISTANCE, "A#1, A#1 -> B#1, A#1");
		assertMigrationDistance(d(args("new A#1()", "new A#1()"), types("B#1", "B#1")), MAX_DISTANCE, "A#1, A#1 -> B#1, B#1");
		assertMigrationDistance(d(args("new A#1()", "new A#1()"), types("C#1", "A#1")), MAX_DISTANCE, "A#1, A#1 -> C#1, B#1");
		assertMigrationDistance(d(args("new A#1()", "new A#1()"), types("C#1", "B#1")), MAX_DISTANCE, "A#1, A#1 -> C#1, B#1");
		assertMigrationDistance(d(args("new A#1()", "new A#1()"), types("C#1", "C#1")), MAX_DISTANCE, "A#1, A#1 -> C#1, C#1");
		
		assertMigrationDistance(d(args("new B#1()", "new A#1()"), types("C#1", "A#1")), MAX_DISTANCE, "B#1, A#1 -> C#1, A#1");
		assertMigrationDistance(d(args("new B#1()", "new A#1()"), types("C#1", "B#1")), MAX_DISTANCE, "B#1, A#1 -> C#1, B#1");
		assertMigrationDistance(d(args("new B#1()", "new B#1()"), types("C#1", "B#1")), MAX_DISTANCE, "B#1, B#1 -> C#1, B#1");
		assertMigrationDistance(d(args("new B#1()", "new C#1()"), types("C#1", "C#1")), MAX_DISTANCE, "B#1, C#1 -> C#1, C#1");
		assertMigrationDistance(d(args("new A#1()", "new C#1()"), types("C#1", "C#1")), MAX_DISTANCE, "A#1, C#1 -> C#1, C#1");
		
		// three parameters
		
		assertMigrationDistance(d(args("new A#1()", "new A#1()", "new A#1()"), types("A#1", "A#1", "A#1")), 0, "A#1, A#1, A#1 -> A#1, A#1, A#1");
		assertMigrationDistance(d(args("new A#1()", "new B#1()", "new A#1()"), types("A#1", "A#1", "A#1")), 1, "A#1, B#1, A#1 -> A#1, A#1, A#1");
		assertMigrationDistance(d(args("new A#1()", "new B#1()", "new B#1()"), types("A#1", "A#1", "A#1")), 2, "A#1, B#1, B#1 -> A#1, A#1, A#1");
		assertMigrationDistance(d(args("new B#1()", "new B#1()", "new B#1()"), types("A#1", "A#1", "A#1")), 3, "B#1, B#1, B#1 -> A#1, A#1, A#1");
	}
	
	@Test
	public def testPrimitiveParameterTypes() {
		// all primitive values have a distance of 0 to the corresponding 'primitive' type description
		assertMigrationDistance(d(args("1"), types("int")), 0, "1 -> int");
		assertMigrationDistance(d(args("'str'"), types("string")), 0, "'str' -> string");
		assertMigrationDistance(d(args("true"), types("boolean")), 0, "true -> boolean");
		
		// non-primitive values have a distance of MAX_DISTANCE
		
		assertMigrationDistance(d(args("new A#1()"), types("int")), MAX_DISTANCE, "new A#1() -> int");
		assertMigrationDistance(d(args("new B#1()"), types("boolean")), MAX_DISTANCE, "new B#1() -> boolean");
		assertMigrationDistance(d(args("new C#1()"), types("string")), MAX_DISTANCE, "new C#1() -> string");
		
		// primitive argument values have a distance of MAX_DISTANCE to non-primitive parameter types
		assertMigrationDistance(d(args("1"), types("A#1")), MAX_DISTANCE, "1 -> A#1");
		assertMigrationDistance(d(args("'str'"), types("A#1")), MAX_DISTANCE, "'str' -> A#1");
		assertMigrationDistance(d(args("true"), types("A#1")), MAX_DISTANCE, "true -> A#1");
	}
	
	@Test
	public def testPrimitiveAndInstanceParameterTypes() {
		// mix-in primitive arguments and parameter types
		assertMigrationDistance(d(args("new A#1()", "2", "new A#1()"), types("A#1", "int", "A#1")), 0, "A#1, 1, A#1 -> A#1, primitive, A#1");
		assertMigrationDistance(d(args("new A#1()", "'a'", "new A#1()"), types("A#1", "string", "A#1")), 0, "A#1, 'a', A#1 -> A#1, primitive, A#1");
		assertMigrationDistance(d(args("new A#1()", "true", "new A#1()"), types("A#1", "boolean", "A#1")), 0, "A#1, true, A#1 -> A#1, primitive, A#1");
		
		assertMigrationDistance(d(args("new B#1()", "2", "new C#1()"), types("A#1", "int", "A#1")), 3, "B#1, 1, C#1 -> A#1, primitive, A#1");
		assertMigrationDistance(d(args("new B#1()", "'a'", "new B#1()"), types("A#1", "string", "A#1")), 2, "B#1, 'a', B#1 -> A#1, primitive, A#1");
		assertMigrationDistance(d(args("new C#1()", "true", "new C#1()"), types("A#1", "boolean", "A#1")), 4, "C#1, true, C#1 -> A#1, primitive, A#1");
		
		// non-matching migration signatures with primitives (parameters and/or arguments)
		assertMigrationDistance(d(args("new B#1()", "1"), types("C#1", "int")), MAX_DISTANCE, "B#1, 1 -> C#1, primitive");
		assertMigrationDistance(d(args("new B#1()", "1"), types("C#1", "int")), MAX_DISTANCE, "B#1, 1 -> C#1, primitive");
		assertMigrationDistance(d(args("new B#1()", "true"), types("C#1", "B#1")), MAX_DISTANCE, "B#1, true -> C#1, B#1");
		assertMigrationDistance(d(args("new B#1()", "'a'"), types("C#1", "string")), MAX_DISTANCE, "B#1, 'a' -> C#1, primitive");
		assertMigrationDistance(d(args("new A#1()", "'a'"), types("C#1", "string")), MAX_DISTANCE, "A#1, 'a' -> C#1, primitive");
	}
	
	
	/**
	 * Convenience method for {@link #assertEquals(String, double, double, double)} with
	 * delta = 0 and re-ordered parameters. 
	 * 
	 * The parameter reordering done, in order to allow for a copy-paste-compatibility of this test with
	 * N4JS counterparts (runtime implementation of type distance computation). 
	 */
	private def void assertMigrationDistance(double distance, double expectation, String message) {
		assertEquals(message, expectation, distance, 0);
	}
	
	/** Static factory to create a {@link List<String>} from the given list of arguments. */
	private def List<String> types(String ...args) { return args.toList }
	/** Static factory to create a {@link List<String>} from the given list of type expression. */
	private def List<String> args(String ...types) { return types.toList }
	
	/**
	 * Computes the migration distance of migration arguments defined by the
	 * given value initializers and the given parameter types.
	 * 
	 * @param argumentValueInitializers The value expressions which specify the type of the migration arguments.
	 * @param parameterTypes The type expression which specify the parameter types of the migration
	 * @param preamble An N4IDL-preamble to include in the synthesized module (may contain required class declarations).
	 */
	private def double d(List<String> argumentValueInitializers, List<String> parameterTypes) {
		val refs = makeTypeRefs(argumentValueInitializers, parameterTypes, preamble);
		
		val lhsTypes = refs.take(argumentValueInitializers.size).toList;
		val rhsTypes = refs.drop(argumentValueInitializers.size).toList;
		
		return distanceComputer.computeDistance(lhsTypes, rhsTypes);
	}
}
