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

import org.eclipse.n4js.ts.types.TVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.VariableBinding#getPattern <em>Pattern</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.VariableBinding#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.VariableBinding#getDefinedVariable <em>Defined Variable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableBinding()
 * @model
 * @generated
 */
public interface VariableBinding extends VariableDeclarationOrBinding {
	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern</em>' containment reference.
	 * @see #setPattern(BindingPattern)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableBinding_Pattern()
	 * @model containment="true"
	 * @generated
	 */
	BindingPattern getPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.VariableBinding#getPattern <em>Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern</em>' containment reference.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(BindingPattern value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableBinding_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.VariableBinding#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>Defined Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Link to variable information stored in the Xtext index.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defined Variable</em>' reference.
	 * @see #setDefinedVariable(TVariable)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVariableBinding_DefinedVariable()
	 * @model transient="true"
	 * @generated
	 */
	TVariable getDefinedVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.VariableBinding#getDefinedVariable <em>Defined Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Variable</em>' reference.
	 * @see #getDefinedVariable()
	 * @generated
	 */
	void setDefinedVariable(TVariable value);

} // VariableBinding
