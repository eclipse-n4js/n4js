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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A template expression as defined for ECMA6
 * http://people.mozilla.org/~jorendorff/es6-draft.html#sec-template-literals
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TemplateLiteral#getSegments <em>Segments</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTemplateLiteral()
 * @model
 * @generated
 */
public interface TemplateLiteral extends PrimaryExpression {
	/**
	 * Returns the value of the '<em><b>Segments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Segments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Segments</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTemplateLiteral_Segments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getSegments();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%java.lang.StringBuilder%> result = new <%java.lang.StringBuilder%>(\"`\");\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.Expression%>, <%java.lang.StringBuilder%>> _function = new <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.Expression%>, <%java.lang.StringBuilder%>>()\n{\n\tpublic <%java.lang.StringBuilder%> apply(final <%org.eclipse.n4js.n4JS.Expression%> expr)\n\t{\n\t\t<%java.lang.StringBuilder%> _switchResult = null;\n\t\tboolean _matched = false;\n\t\tif (expr instanceof <%org.eclipse.n4js.n4JS.Literal%>)\n\t\t{\n\t\t\t_matched=true;\n\t\t\t_switchResult = result.append(<%this%>.getValueAsString());\n\t\t}\n\t\tif (!_matched)\n\t\t{\n\t\t\t_switchResult = result.append(\"<<\").append(<%this%>.eClass().getName()).append(\">>\");\n\t\t}\n\t\treturn _switchResult;\n\t}\n};\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function1%><<%org.eclipse.n4js.n4JS.Expression%>, <%java.lang.StringBuilder%>> appender = _function;\nfinal <%org.eclipse.xtext.xbase.lib.Functions.Function2%><<%java.lang.Boolean%>, <%org.eclipse.n4js.n4JS.Expression%>, <%java.lang.Boolean%>> _function_1 = new <%org.eclipse.xtext.xbase.lib.Functions.Function2%><<%java.lang.Boolean%>, <%org.eclipse.n4js.n4JS.Expression%>, <%java.lang.Boolean%>>()\n{\n\tpublic <%java.lang.Boolean%> apply(final <%java.lang.Boolean%> isRaw, final <%org.eclipse.n4js.n4JS.Expression%> expression)\n\t{\n\t\tif ((!(isRaw).booleanValue()))\n\t\t{\n\t\t\tresult.append(\"${\");\n\t\t\tappender.apply(expression);\n\t\t\tresult.append(\"}\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\tappender.apply(expression);\n\t\t}\n\t\treturn <%java.lang.Boolean%>.valueOf((!(isRaw).booleanValue()));\n\t}\n};\n<%org.eclipse.xtext.xbase.lib.IterableExtensions%>.<<%org.eclipse.n4js.n4JS.Expression%>, <%java.lang.Boolean%>>fold(this.getSegments(), <%java.lang.Boolean%>.valueOf(true), _function_1);\nresult.append(\"`\");\nreturn result.toString();'"
	 * @generated
	 */
	String getValueAsString();

} // TemplateLiteral
