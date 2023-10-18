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
package org.eclipse.n4js.tests.scoping;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AT_245_AccessGlobalObjectPropertiesTest {

	@Inject
	N4JSParseHelper parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				this.undefined
				""", JavaScriptVariant.unrestricted);
		valTestHelper.assertNoErrors(script);
		ParameterizedPropertyAccessExpression expression = (ParameterizedPropertyAccessExpression) ((ExpressionStatement) script
				.getScriptElements().get(0)).getExpression();
		TField property = (TField) expression.getProperty();
		Assert.assertEquals("GlobalObject", ((TClass) property.eContainer()).getName());
		Assert.assertEquals("undefined", ((ParameterizedTypeRef) property.getTypeRef()).getDeclaredType().getName());
	}

	@Test
	public void test_02() throws Exception {
		Script script = parseHelper.parse("""
				undefined
				""", JavaScriptVariant.unrestricted);
		valTestHelper.assertNoErrors(script);
		IdentifierRef expression = (IdentifierRef) ((ExpressionStatement) script.getScriptElements().get(0))
				.getExpression();
		TField property = (TField) expression.getId();
		Assert.assertEquals("GlobalObject", ((TClass) property.eContainer()).getName());
		Assert.assertEquals("undefined", ((ParameterizedTypeRef) property.getTypeRef()).getDeclaredType().getName());
	}

}
