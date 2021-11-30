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
package org.eclipse.n4js.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public abstract class AbstractParserTest extends Assert {
	@Inject
	protected extension N4JSParseHelper

	protected def Script parseJSSuccessfully(CharSequence js) {
		val script = js.parseUnrestricted
		assertTrue(script.eResource.errors.join('\n') [ line + ': ' + message] , script.eResource.errors.empty)
		return script
	}

	protected def parseJSWithError(CharSequence js) {
		val script = js.parseUnrestricted
		val errors = script.eResource.errors;
		assertFalse(errors.toString, errors.empty)
		return script
	}

	protected def Script parseESSuccessfully(CharSequence js) {
		return parseJSSuccessfully(js);
	}

	protected def parseESWithError(CharSequence js) {
		return parseJSWithError(js);
	}

	protected def Script parseN4jsSuccessfully(CharSequence js) {
		val script = js.parseN4js
		assertTrue(script.eResource.errors.join('\n') [ line + ': ' + message] , script.eResource.errors.empty)
		return script
	}

	protected def parseN4jsWithError(CharSequence js) {
		val script = js.parseN4js
		val errors = script.eResource.errors;
		assertFalse(errors.toString, errors.empty)
		return script
	}

	protected def Script parseN4jsdSuccessfully(CharSequence js) {
		val script = js.parse(JavaScriptVariant.external)
		assertTrue(script.eResource.errors.join('\n') [ line + ': ' + message] , script.eResource.errors.empty)
		return script
	}

	protected def parseN4jsdWithError(CharSequence js) {
		val script = js.parse(JavaScriptVariant.external)
		val errors = script.eResource.errors;
		assertFalse(errors.toString, errors.empty)
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
			NodeModelUtils.findNodesForFeature(it, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY).head
		)
	}

}
