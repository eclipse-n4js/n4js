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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.utils.StructuralTypesHelper
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class StructuralTypingComputerTest_collectMembersTest extends AbstractStructuralTypingComputerTest {

	@Inject private StructuralTypesHelper structuralTypesHelper;

	@Test
	def testCollectMembers() {
		val script = assembler.setupScript(
			'''
				class C {
					public s: string;
					private n: number;
					public foo(): void {}
					private bar(): void {}
				}
				var c: C;
			''', JavaScriptVariant.n4js, 0);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val typeRef = (script.scriptElements.last as VariableStatement).varDecl.head.declaredTypeRef;

		val objMembers = ", toLocaleString(), hasOwnProperty(), isPrototypeOf(), propertyIsEnumerable(), toString(), valueOf()"
		val objFields = ""

		assertMembers("s, foo()"+objMembers,
			structuralTypesHelper.collectStructuralMembers(G, typeRef, TypingStrategy.DEFAULT))
		assertMembers("s"+objFields,
			structuralTypesHelper.collectStructuralMembers(G, typeRef, TypingStrategy.STRUCTURAL_FIELDS))

	}
}
