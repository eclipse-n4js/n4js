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
package org.eclipse.n4js.n4jsx.lang.tests.parser

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.JSXChild
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4jsx.tests.helper.N4JSXParseHelper
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.runner.RunWith

/**
 * N4JSX version of N4JS' AbstractParserTest
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public abstract class AbstractN4JSXParserTest extends Assert {
	@Inject
	protected extension N4JSXParseHelper

	protected def Script parseSuccessfully(CharSequence js) {
		val script = js.parseN4JSX
		assertTrue(script.eResource.errors.join('\n')[line + ': ' + message], script.eResource.errors.empty)
		return script
	}

	protected def parseWithError(CharSequence js) {
		val script = js.parseN4JSX
		assertFalse(script.eResource.errors.toString, script.eResource.errors.empty)
		return script
	}

	/**
	 * Used in tests to eliminate the suspicious paren expression
	 */
	protected def unwrap(Expression it) {
		switch it {
			ParenExpression: return expression
		}
		fail('Expected ParenExpression but got' + it)
		return null;
	}

	protected def getText(Expression it) {
		NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(it))
	}

	protected def getPropertyText(ParameterizedPropertyAccessExpression it) {
		NodeModelUtils.getTokenText(
			NodeModelUtils.findNodesForFeature(it,
				N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY).head
		)
	}

	protected def assertType(Class<?> type, EObject astElement) {
		assertTrue("Expected type " + type.name + ", was " + astElement.class.name, type.isInstance(astElement))
	}

	protected def assertTagName(String expectedName, JSXChild jsxElement) {
		val actualName = ((jsxElement as JSXElement).jsxElementName.expression as IdentifierRef).idAsText;
		assertEquals(expectedName, actualName)
	}
}
