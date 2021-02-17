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
package org.eclipse.n4js.xpect.methods

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4idl.migrations.MigrationSwitchComputer
import org.eclipse.n4js.n4idl.migrations.SwitchCondition
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter
import org.eclipse.xpect.XpectImport
import org.eclipse.xpect.expectation.IStringExpectation
import org.eclipse.xpect.expectation.StringExpectation
import org.eclipse.xpect.parameter.ParameterParser
import org.eclipse.xpect.runner.Xpect
import org.eclipse.n4js.n4idl.migrations.MigrationSwitchComputer.UnhandledTypeRefException
import org.eclipse.n4js.ide.tests.helper.server.xt.IEObjectCoveringRegion

/**
 * Provides X!PECT methods for testing the computation of {@link SwitchCondition}s 
 * for given {@link TypeRef}s.
 */
@XpectImport(N4JSOffsetAdapter)
class TypeSwitchXpectMethod {
	@Inject private MigrationSwitchComputer switchComputer;
	@Inject private N4JSTypeSystem typeSystem;

	/**
	 * Tests the type switch computed for the declared type ref of the variable declaration at 
	 * the given offset (arg1).
	 */
	@ParameterParser(syntax = "'of' arg1=OFFSET ")
	@Xpect
	public def void typeSwitch(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion arg1) {
		if (expectation === null)
			throw new IllegalStateException("No expectation specified, add '--> <type switch string representation>'");

		val EObject object = arg1.getEObject();
		val String typeSwitch = getTypeSwitch(object).toString;
		expectation.assertEquals(String.format("\"%s\"", typeSwitch));
	}
	
	/**
	 * Tests the type ref of the type switch computed for the declared type reference of the variable declaration at 
	 * the given offset (arg1).
	 * 
	 * This forth-and-back conversion may result in the generalization of a type reference, since switch conditions
	 * are not as expressive as compile-time type reference.
	 * 
	 * Assert string expectation {@code "unsupported"} to check that a given type reference is not supported. 
	 */
	@ParameterParser(syntax = "'of' arg1=OFFSET ")
	@Xpect
	public def void typeSwitchTypeRef(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion arg1) {
		if (expectation === null)
			throw new IllegalStateException("No expectation specified, add '--> <type switch string representation>'");

		val EObject object = arg1.getEObject();
		
		// obtain actual computed type-switch-distinguishable type reference 
		val actual = try { 
			getTypeSwitchTypeRef(object).typeRefAsString
		} catch (UnhandledTypeRefException e) {
			"unsupported"
		}
		
		expectation.assertEquals(String.format("\"%s\"", actual));
	}
	
	private def TypeRef findTypeRefAtOffset(EObject offsetObject) {
		val firstContainedTypeRef = offsetObject.eAllContents.filter(TypeRef).head
		if (null === firstContainedTypeRef) {
			throw new IllegalArgumentException("Failed to find a valid type reference at the given offset: " + offsetObject);
		}
		return firstContainedTypeRef;
	}

	/** 
	 * Computes and returns the string representation of the type switch for the variable
	 * declaration at the given offset object.
	 * 
	 * @throws IllegalArgumentException if the given typeRefObject is not a {@link TypeRef}. 
	 */
	private def SwitchCondition getTypeSwitch(EObject offsetObject) {
		return switchComputer.compute(findTypeRefAtOffset(offsetObject));
	}
	
	private def TypeRef getTypeSwitchTypeRef(EObject offsetObject) {
		val contextTypeRef = findTypeRefAtOffset(offsetObject);
		val condition = switchComputer.compute(contextTypeRef);
		
		val ruleEnv = typeSystem.createRuleEnvironmentForContext(contextTypeRef, contextTypeRef.eResource);
		return switchComputer.toTypeRef(ruleEnv, condition);
		
	}
}
