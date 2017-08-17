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
 * A representation of the model object '<em><b>Switch Statement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.SwitchStatement#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.SwitchStatement#getCases <em>Cases</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getSwitchStatement()
 * @model
 * @generated
 */
public interface SwitchStatement extends Statement, VariableEnvironmentElement {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The discriminant of the switch statement.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getSwitchStatement_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.SwitchStatement#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>Cases</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.AbstractCaseClause}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * All cases of the switch statement, i.e. all CaseClauses and not more than one DefaultClause.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cases</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getSwitchStatement_Cases()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractCaseClause> getCases();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * See {@link VariableEnvironmentElement#appliesOnlyToBlockScopedElements()}.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return true;'"
	 * @generated
	 */
	boolean appliesOnlyToBlockScopedElements();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method returns default clause if switch statement defines such as clause or null.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.n4JS.AbstractCaseClause%&gt;, &lt;%java.lang.Boolean%&gt;&gt; _function = new &lt;%org.eclipse.xtext.xbase.lib.Functions.Function1%&gt;&lt;&lt;%org.eclipse.n4js.n4JS.AbstractCaseClause%&gt;, &lt;%java.lang.Boolean%&gt;&gt;()\n{\n\tpublic &lt;%java.lang.Boolean%&gt; apply(final &lt;%org.eclipse.n4js.n4JS.AbstractCaseClause%&gt; it)\n\t{\n\t\treturn &lt;%java.lang.Boolean%&gt;.valueOf((it instanceof &lt;%org.eclipse.n4js.n4JS.DefaultClause%&gt;));\n\t}\n};\n&lt;%org.eclipse.n4js.n4JS.AbstractCaseClause%&gt; _findFirst = &lt;%org.eclipse.xtext.xbase.lib.IterableExtensions%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.AbstractCaseClause%&gt;&gt;findFirst(this.getCases(), _function);\nreturn ((&lt;%org.eclipse.n4js.n4JS.DefaultClause%&gt;) _findFirst);'"
	 * @generated
	 */
	DefaultClause getDefaultClause();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method filters all CaseClauses from clauses.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.emf.common.util.ECollections%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.CaseClause%&gt;&gt;toEList(&lt;%com.google.common.collect.Iterables%&gt;.&lt;&lt;%org.eclipse.n4js.n4JS.CaseClause%&gt;&gt;filter(this.getCases(), &lt;%org.eclipse.n4js.n4JS.CaseClause%&gt;.class));'"
	 * @generated
	 */
	EList<CaseClause> getCaseClauses();

} // SwitchStatement
