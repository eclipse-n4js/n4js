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
package org.eclipse.n4js.typesystem;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Helper for type system tests, generating variables with specified type expressions so that the test can use the type
 * ref of the variables in tests. <em>This helper uses assert, so use this in tests only.</em>
 * <p>
 * The test has to set the script prefix (with the type definitions) first, see
 * {@link AbstractTypeSystemHelperTests#setScriptTypeDefinitions(CharSequence)} (often done in a before method). Then,
 * usually in some custom assert, the test has to add some type references via {@link AbstractScriptAssembler
 * #prepareScriptAndCreateRuleEnvironment(TypeRef...)}. In order to retrieve the type references, the test has to call
 * {@link #getTypeRef(String)}.
 */
public class TypeRefsToVariablesAssembler extends AbstractScriptAssembler {

	/**
	 * Convenience method for method {@link #prepareScriptAndCreateRuleEnvironment(String[], String...)}.
	 */
	public RuleEnvironment prepareScriptAndCreateRuleEnvironment(String... typeExpressions) {
		return prepareScriptAndCreateRuleEnvironment(new String[0], JavaScriptVariant.n4js, typeExpressions);
	}

	/**
	 * To be called by test for appending newly generated variable declarations with given types.
	 * <p>
	 * Usage remarks:
	 * <ul>
	 * <li>If you want to create different type references for similar type expressions, then simply add comments of
	 * spaces to the type expressions.
	 * <li>no new type variables are created for type expressions starting with "local", instead, they are retrieved
	 * from predefined script. These types are accessible directly via the variable name.
	 * <li>If you expect warnings or errors to occur, you can pass the expected messages.
	 * </ul>
	 * This method uses the default n4js JavaScript variant.
	 *
	 * @return the rule environment to be used when type system rules are called.
	 */
	public RuleEnvironment prepareScriptAndCreateRuleEnvironment(String[] expectedMessages, String... typeExpressions) {
		return prepareScriptAndCreateRuleEnvironment(expectedMessages, JavaScriptVariant.n4js, typeExpressions);
	}

	public RuleEnvironment prepareScriptAndCreateRuleEnvironment(String[] expectedMessages, JavaScriptVariant variant,
			String... typeExpressions) {
		return doPrepareScriptAndCreateRuleEnvironment(expectedMessages, variant, typeExpressions).getValue();
	}

	public Pair<Script, RuleEnvironment> doPrepareScriptAndCreateRuleEnvironment(JavaScriptVariant variant,
			String... typeExpressions) {
		return doPrepareScriptAndCreateRuleEnvironment(new String[0], variant, typeExpressions);
	}

	public Pair<Script, RuleEnvironment> doPrepareScriptAndCreateRuleEnvironment(String[] expectedMessages,
			JavaScriptVariant variant, String... typeExpressions) {
		String completeScript = getScriptPrefix() + "\n" + createVariables(typeExpressions);
		@SuppressWarnings("hiding")
		Script script = setupScript(completeScript, variant, expectedMessages);
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		return Pair.of(script, G);
	}

}
