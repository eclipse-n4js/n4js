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
 * A representation of the model object '<em><b>Expression With Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Abstract base class for expressions that do have a target and are potentially null safe.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ExpressionWithTarget#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ExpressionWithTarget#isOptionalChaining <em>Optional Chaining</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExpressionWithTarget()
 * @model abstract="true"
 * @generated
 */
public interface ExpressionWithTarget extends Expression {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The target of this dereferencing expression (aka receiver).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExpressionWithTarget_Target()
	 * @model containment="true"
	 * @generated
	 */
	Expression getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ExpressionWithTarget#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Expression value);

	/**
	 * Returns the value of the '<em><b>Optional Chaining</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Optional Chaining</em>' attribute.
	 * @see #setOptionalChaining(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExpressionWithTarget_OptionalChaining()
	 * @model unique="false"
	 * @generated
	 */
	boolean isOptionalChaining();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ExpressionWithTarget#isOptionalChaining <em>Optional Chaining</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Optional Chaining</em>' attribute.
	 * @see #isOptionalChaining()
	 * @generated
	 */
	void setOptionalChaining(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Tells if this expression lies within a long short-circuiting range of an optional chaining operator.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isOrHasTargetWithOptionalChaining();

} // ExpressionWithTarget
