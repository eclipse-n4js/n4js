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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.StructuralTypesHelper;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class StructuralTypingComputerTest_collectMembersTest extends AbstractStructuralTypingComputerTest {

	@Inject
	private StructuralTypesHelper structuralTypesHelper;

	@Test
	public void testCollectMembers() {
		Script script = assembler.setupScript(
				"""
						class C {
							public s: string;
							private n: number;
							public foo(): void {}
							private bar(): void {}
						}
						var c: C;
						""", JavaScriptVariant.n4js, 0);
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		TypeRef typeRef = ((VariableStatement) last(script.getScriptElements())).getVarDecl().get(0)
				.getDeclaredTypeRef();

		String objMembers = ", toLocaleString(), hasOwnProperty(), isPrototypeOf(), propertyIsEnumerable(), toString(), valueOf()";
		String objFields = "";

		assertMembers("s, foo()" + objMembers,
				structuralTypesHelper.collectStructuralMembers(G, typeRef, TypingStrategy.DEFAULT));
		assertMembers("s" + objFields,
				structuralTypesHelper.collectStructuralMembers(G, typeRef, TypingStrategy.STRUCTURAL_FIELDS));

	}
}
