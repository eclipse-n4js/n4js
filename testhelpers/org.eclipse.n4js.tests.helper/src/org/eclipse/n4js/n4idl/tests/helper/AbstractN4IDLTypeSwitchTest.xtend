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
package org.eclipse.n4js.n4idl.tests.helper

import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.n4idl.migrations.MigrationSwitchComputer
import org.eclipse.n4js.n4idl.migrations.OrSwitchCondition
import org.eclipse.n4js.n4idl.migrations.SwitchCondition
import org.eclipse.n4js.n4idl.migrations.TypeSwitchCondition
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.junit.Assert

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * A test extension class for N4IDL-related tests, which allows to create valid
 * {@link TypeRef} instances from a given (N4IDL compliant) type-expression string.
 */
abstract class AbstractN4IDLTypeSwitchTest extends Assert {
	
	@Inject protected MigrationSwitchComputer switchComputer;
	@Inject protected N4JSTypeSystem typeSystem;
	@Inject protected extension N4IDLTypeRefTestHelper
	
	/**
	 * Computes a {@link SwitchCondition} based on the given typeExpression using the given preamble.
	 * 
	 * Converts the resulting condition back to a {@link TypeRef} using {@link MigrationSwitchComputer#toTypeRef}
	 * and returns the string representation of the result.
	 */
	public def String computeAndConvertToTypeRef(String typeExpression, String preamble) {
		val typeRef = makeTypeRef(typeExpression, preamble);
		
		val env = typeSystem.createRuleEnvironmentForContext(typeRef, typeRef.eResource);
		val result = switchComputer.toSwitchRecognizableTypeRef(env, typeRef);
		
		return result.typeRefAsString;
	}
	
	/**
	 * Creates a {@link TypeRef} for the given type expression string and
	 * computes the corresponding {@link SwitchCondition} using {@link MigrationSwitchComputer}.
	 * 
	 * Prepends the given preamble to the N4IDL module before parsing it.
	 */
	public def SwitchCondition computeSwitch(String typeExpression, String preamble) {
		return switchComputer.compute(makeTypeRef(typeExpression, preamble))
	}
	
	/** Returns the context {@link Resource} of the given {@link SwitchCondition} or
	 * {@code null} if no context can be inferred. */
	protected def Resource findContextResource(SwitchCondition condition) {
		return condition
			.filter(TypeSwitchCondition)
			.head?.type?.eResource;
	}
	
	protected def RuleEnvironment createRuleEnvironment(SwitchCondition condition) {
		return findContextResource(condition).newRuleEnvironment;
	}
	
	public def TypeRef toTypeRef(SwitchCondition condition) {
		val ruleEnv = findContextResource(condition).newRuleEnvironment;
		return switchComputer.toTypeRef(ruleEnv, condition);
	}
	
	/**
	 * Creates a {@link TypeRef} for the given type expression string.
	 * 
	 * Prepends the given preamble to the N4IDL module before parsing it.
	 * 
	 * Computes the corresponding TypeSwitchCondition and returns its string representation.
	 */
	public def String compute(String typeExpression, String preamble) {
		return computeSwitch(typeExpression, preamble).toString;
	}
	
	/** Assert that the given {@link SwitchCondition} is not an OrSwitchCondition nor
	 * does it contain any sub-conditions of that type. */
	public def void assertSwitchDoesNotContainOr(SwitchCondition condition) {
		assertSwitchDoesNotContainOr(condition, condition);
	}

	private def void assertSwitchDoesNotContainOr(SwitchCondition rootCondition, SwitchCondition condition) {
		if (condition instanceof OrSwitchCondition) {
			fail("Switch condition did contain OR condition: " + rootCondition);
		}
		condition.subConditions.forEach[c | assertSwitchDoesNotContainOr(rootCondition, c)]
	}
}
