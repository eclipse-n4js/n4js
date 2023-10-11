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
package org.eclipse.n4js.tests.parser;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Assert;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public abstract class AbstractParserTest extends Assert {

	@Inject
	protected N4JSParseHelper parseHelper;

	protected Script parseJSSuccessfully(CharSequence js) {
		try {
			Script script = parseHelper.parseUnrestricted(js);
			EList<Diagnostic> errors = script.eResource().getErrors();
			String msg = Strings.join("\n", d -> d.getLine() + ": " + d.getMessage(), errors);
			assertTrue(msg, errors.isEmpty());
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	protected Script parseJSWithError(CharSequence js) {
		try {
			Script script = parseHelper.parseUnrestricted(js);
			List<Diagnostic> errors = script.eResource().getErrors();
			assertFalse(errors.toString(), errors.isEmpty());
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	protected Script parseESSuccessfully(CharSequence js) {
		return parseJSSuccessfully(js);
	}

	protected Script parseESWithError(CharSequence js) {
		return parseJSWithError(js);
	}

	protected Script parseN4jsSuccessfully(CharSequence js) {
		try {
			Script script = parseHelper.parseN4js(js);
			EList<Diagnostic> errors = script.eResource().getErrors();
			String msg = Strings.join("\n", d -> d.getLine() + ": " + d.getMessage(), errors);
			assertTrue(msg, errors.isEmpty());
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	protected Script parseN4jsWithError(CharSequence js) throws Exception {
		Script script = parseHelper.parseN4js(js);
		List<Diagnostic> errors = script.eResource().getErrors();
		assertFalse(errors.toString(), errors.isEmpty());
		return script;
	}

	/** Used in tests to eliminate the suspicious paren expression */
	protected Expression unwrap(Expression expr) {
		if (expr instanceof ParenExpression) {
			return ((ParenExpression) expr).getExpression();
		}
		fail("Expected ParenExpression but got" + expr);
		return null;
	}

	protected String getText(Expression expr) {
		return NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(expr));
	}

	protected String getPropertyText(ParameterizedPropertyAccessExpression ppae) {
		return NodeModelUtils.getTokenText(NodeModelUtils.findNodesForFeature(ppae,
				N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY).iterator().next());
	}

}
