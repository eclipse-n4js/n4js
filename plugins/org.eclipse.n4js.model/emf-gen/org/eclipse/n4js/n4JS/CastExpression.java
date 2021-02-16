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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cast Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Type cast expression "as".
 * See 5.5.1 and 6.2.3 for details.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.CastExpression#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.CastExpression#getTargetTypeRefNode <em>Target Type Ref Node</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCastExpression()
 * @model
 * @generated
 */
public interface CastExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCastExpression_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.CastExpression#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>Target Type Ref Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Type Ref Node</em>' containment reference.
	 * @see #setTargetTypeRefNode(TypeReferenceNode)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCastExpression_TargetTypeRefNode()
	 * @model containment="true"
	 * @generated
	 */
	TypeReferenceNode<TypeRef> getTargetTypeRefNode();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.CastExpression#getTargetTypeRefNode <em>Target Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Type Ref Node</em>' containment reference.
	 * @see #getTargetTypeRefNode()
	 * @generated
	 */
	void setTargetTypeRefNode(TypeReferenceNode<TypeRef> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getTargetTypeRef();

} // CastExpression
