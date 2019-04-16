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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ConditionalExpression#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ConditionalExpression#getTrueExpression <em>True Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ConditionalExpression#getFalseExpression <em>False Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getConditionalExpression()
 * @model
 * @generated
 */
public interface ConditionalExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getConditionalExpression_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ConditionalExpression#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>True Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>True Expression</em>' containment reference.
	 * @see #setTrueExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getConditionalExpression_TrueExpression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getTrueExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ConditionalExpression#getTrueExpression <em>True Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>True Expression</em>' containment reference.
	 * @see #getTrueExpression()
	 * @generated
	 */
	void setTrueExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>False Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>False Expression</em>' containment reference.
	 * @see #setFalseExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getConditionalExpression_FalseExpression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getFalseExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ConditionalExpression#getFalseExpression <em>False Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>False Expression</em>' containment reference.
	 * @see #getFalseExpression()
	 * @generated
	 */
	void setFalseExpression(Expression value);

} // ConditionalExpression
