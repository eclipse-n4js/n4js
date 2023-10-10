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
package org.eclipse.n4js.n4jsx.lang.tests.parser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.JSXChild;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXElementName;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4jsx.tests.helper.N4JSXParseHelper;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * N4JSX version of N4JS' AbstractParserTest
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public abstract class AbstractN4JSXParserTest extends Assert {
	@Inject
	protected N4JSXParseHelper parseHelper;

	protected Script parseSuccessfully(CharSequence js) throws Exception {
		Script script = parseHelper.parseN4JSX(js);
		assertTrue(Strings.join("\n", err -> err.getLine() + ": " + err.getMessage(), script.eResource().getErrors()),
				script.eResource().getErrors().isEmpty());
		return script;
	}

	protected Script parseWithError(CharSequence js) throws Exception {
		Script script = parseHelper.parseN4JSX(js);
		assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		return script;
	}

	/**
	 * Used in tests to eliminate the suspicious paren expression
	 */
	protected Expression unwrap(Expression it) {
		if (it instanceof ParenExpression) {
			return ((ParenExpression) it).getExpression();
		}
		fail("Expected ParenExpression but got" + it);
		return null;
	}

	protected String getText(Expression it) {
		return NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(it));
	}

	protected String getPropertyText(ParameterizedPropertyAccessExpression it) {
		return NodeModelUtils.getTokenText(NodeModelUtils.findNodesForFeature(it,
				N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY).get(0));
	}

	protected void assertType(Class<?> type, EObject astElement) {
		assertTrue("Expected type " + type.getName() + ", was " + astElement.getClass().getName(),
				type.isInstance(astElement));
	}

	protected void assertTagName(String expectedName, JSXChild jsxElement) {
		JSXElementName jsxElementName = ((JSXElement) jsxElement).getJsxElementName();
		String actualName = ((IdentifierRef) jsxElementName.getExpression()).getIdAsText();
		assertEquals(expectedName, actualName);
	}
}
