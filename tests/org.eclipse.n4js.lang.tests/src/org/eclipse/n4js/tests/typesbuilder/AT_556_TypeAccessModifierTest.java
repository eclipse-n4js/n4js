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
package org.eclipse.n4js.tests.typesbuilder;

import static org.eclipse.n4js.ts.types.TypeAccessModifier.PRIVATE;
import static org.eclipse.n4js.ts.types.TypeAccessModifier.PROJECT;
import static org.eclipse.n4js.ts.types.TypeAccessModifier.PUBLIC;
import static org.eclipse.n4js.ts.types.TypeAccessModifier.PUBLIC_INTERNAL;
import static org.junit.Assert.fail;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class AT_556_TypeAccessModifierTest {
	@Inject
	ParseHelper<Script> parseHelper;

	private void typeIs(CharSequence script, TypeAccessModifier mod) {
		try {
			Script parsed = parseHelper.parse(script);
			Type type = IterableExtensions.head(parsed.getModule().getTypes());
			Assert.assertEquals(mod, type.getTypeAccessModifier());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void varIs(CharSequence script, TypeAccessModifier mod) {
		try {
			Script parsed = parseHelper.parse(script);
			TVariable variable = IterableExtensions.head(parsed.getModule().getExportedVariables());
			Assert.assertEquals(mod, variable == null ? null : variable.getTypeAccessModifier());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	private void functionIs(CharSequence script, TypeAccessModifier mod) {
		try {
			Script parsed = parseHelper.parse(script);
			TFunction function = IterableExtensions.head(parsed.getModule().getFunctions());
			Assert.assertEquals(mod, function == null ? null : function.getTypeAccessModifier());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test_privateType() {
		typeIs("class C {}", PRIVATE);
	}

	@Test
	public void test_projectType() {
		typeIs("export class C {}", PROJECT);
	}

	@Test
	public void test_explicitProjectType() {
		typeIs("export project class C {}", PROJECT);
	}

	@Test
	public void test_explicitPublicType() {
		typeIs("export @Internal public class C {}", PUBLIC_INTERNAL);
	}

	@Test
	public void test_explicitPublicApiType() {
		typeIs("export public class C {}", PUBLIC);
	}

	@Test
	public void test_privateVar() {
		varIs("var any c", null);// private vars are not exposed in the module
	}

	@Test
	public void test_projectVar() {
		varIs("export var any c", PROJECT);
	}

	@Test
	public void test_explicitProjectVar() {
		varIs("export project var any c", PROJECT);
	}

	@Test
	public void test_explicitPublicVar() {
		varIs("export @Internal public var any c", PUBLIC_INTERNAL);
	}

	@Test
	public void test_explicitPublicApiVar() {
		varIs("export public var any c", PUBLIC);
	}

	@Test
	public void test_privateFunction() {
		functionIs("function c() {}", PRIVATE);
	}

	@Test
	public void test_projectFunction() {
		functionIs("export function c() {}", PROJECT);
	}

	@Test
	public void test_explicitProjectFunction() {
		functionIs("export project function c() {}", PROJECT);
	}

	@Test
	public void test_explicitPublicFunction() {
		functionIs("export @Internal public function c() {}", PUBLIC_INTERNAL);
	}

	@Test
	public void test_explicitPublicApiFunction() {
		functionIs("export public function c() {}", PUBLIC);
	}
}
