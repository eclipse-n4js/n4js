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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.n4js.typesystem.SubTypeRelationForTest._sub;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.typesystem.AbstractTypeSystemHelperTests;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * N4JS Spec Test: 4.3.2. Type Variables
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class N4_03_2_TypeVariablesTest extends AbstractTypeSystemHelperTests {

	final static CharSequence scriptPrefix = """
			class A{}
			class B extends A{}
			class C extends B{}
			""";

	@Test
	public void testTypeVarInGenericWithoutBounds() {
		assembler.setScriptPrefix("""
				%s
				class G<T> {
					typevar: T;
					foo(): void {
						this.typevar;
					}
				}
				""".formatted(scriptPrefix));
		assertSubTypeOfRef("typevar", _sub, "any");
	}

	@Test
	public void testTypeVarInGenericWithBound() {
		assembler.setScriptPrefix("""
				%s
				class G<T extends A> {
					typevar: T;
				 	foo(): void {
						this.typevar;
					}
				}
				""".formatted(scriptPrefix));
		assertSubTypeOfRef("typevar", _sub, "A");
	}

	@Test
	public void testTypeVarInGenericFunctionWOBounds() {
		assembler.setScriptPrefix("""
				%s
				function <T> foo(typevar: T) {
					typevar;
				}
				""".formatted(scriptPrefix));
		assertSubTypeOfRef("typevar", _sub, "any");
	}

	@Test
	public void testTypeVarInGenericFunctionWithBounds() {
		assembler.setScriptPrefix("""
				%s
				function <T extends A> foo(typevar: T) {
					typevar;
				}
				""".formatted(scriptPrefix));
		assertSubTypeOfRef("typevar", _sub, "A");
	}

}
