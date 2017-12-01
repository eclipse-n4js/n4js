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
package org.eclipse.n4js.n4jsx.tests.helper.parser;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4jsx.n4JSX.JSXChild;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.n4jsx.tests.helper.N4JSXInjectorProvider;
import org.eclipse.n4js.n4jsx.tests.helper.N4JSXParseHelper;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * N4JSX version of N4JS' AbstractParserTest
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSXInjectorProvider.class)
@SuppressWarnings("all")
public abstract class AbstractN4JSXParserTest extends Assert {
  @Inject
  @Extension
  protected N4JSXParseHelper _n4JSXParseHelper;
  
  protected Script parseSuccessfully(final CharSequence js) {
    try {
      final Script script = this._n4JSXParseHelper.parseN4JSX(js);
      final Function1<Resource.Diagnostic, CharSequence> _function = (Resource.Diagnostic it) -> {
        int _line = it.getLine();
        String _plus = (Integer.valueOf(_line) + ": ");
        String _message = it.getMessage();
        return (_plus + _message);
      };
      Assert.assertTrue(IterableExtensions.<Resource.Diagnostic>join(script.eResource().getErrors(), "\n", _function), script.eResource().getErrors().isEmpty());
      return script;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected Script parseWithError(final CharSequence js) {
    try {
      final Script script = this._n4JSXParseHelper.parseN4JSX(js);
      Assert.assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
      return script;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Used in tests to eliminate the suspicious paren expression
   */
  protected Expression unwrap(final Expression it) {
    boolean _matched = false;
    if (it instanceof ParenExpression) {
      _matched=true;
      return ((ParenExpression)it).getExpression();
    }
    Assert.fail(("Expected ParenExpression but got" + it));
    return null;
  }
  
  protected String getText(final Expression it) {
    return NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(it));
  }
  
  protected String getPropertyText(final ParameterizedPropertyAccessExpression it) {
    return NodeModelUtils.getTokenText(
      IterableExtensions.<INode>head(NodeModelUtils.findNodesForFeature(it, 
        N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY)));
  }
  
  protected void assertType(final Class<?> type, final EObject astElement) {
    String _name = type.getName();
    String _plus = ("Expected type " + _name);
    String _plus_1 = (_plus + ", was ");
    String _name_1 = astElement.getClass().getName();
    String _plus_2 = (_plus_1 + _name_1);
    Assert.assertTrue(_plus_2, type.isInstance(astElement));
  }
  
  protected void assertTagName(final String expectedName, final JSXChild jsxElement) {
    Expression _expression = ((JSXElement) jsxElement).getJsxElementName().getExpression();
    final String actualName = ((IdentifierRef) _expression).getIdAsText();
    Assert.assertEquals(expectedName, actualName);
  }
}
