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
 * A representation of the model object '<em><b>Binary Logical Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getOp <em>Op</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getRhs <em>Rhs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBinaryLogicalExpression()
 * @model
 * @generated
 */
public interface BinaryLogicalExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs</em>' containment reference.
	 * @see #setLhs(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBinaryLogicalExpression_Lhs()
	 * @model containment="true"
	 * @generated
	 */
	Expression getLhs();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getLhs <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhs</em>' containment reference.
	 * @see #getLhs()
	 * @generated
	 */
	void setLhs(Expression value);

	/**
	 * Returns the value of the '<em><b>Op</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4JS.BinaryLogicalOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Op</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalOperator
	 * @see #setOp(BinaryLogicalOperator)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBinaryLogicalExpression_Op()
	 * @model unique="false"
	 * @generated
	 */
	BinaryLogicalOperator getOp();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getOp <em>Op</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Op</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalOperator
	 * @see #getOp()
	 * @generated
	 */
	void setOp(BinaryLogicalOperator value);

	/**
	 * Returns the value of the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs</em>' containment reference.
	 * @see #setRhs(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBinaryLogicalExpression_Rhs()
	 * @model containment="true"
	 * @generated
	 */
	Expression getRhs();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getRhs <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhs</em>' containment reference.
	 * @see #getRhs()
	 * @generated
	 */
	void setRhs(Expression value);

} // BinaryLogicalExpression
