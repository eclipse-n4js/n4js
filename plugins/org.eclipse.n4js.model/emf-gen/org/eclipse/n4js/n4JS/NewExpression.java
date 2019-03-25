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
 * A representation of the model object '<em><b>New Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.NewExpression#getCallee <em>Callee</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.NewExpression#getArguments <em>Arguments</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.NewExpression#isWithArgs <em>With Args</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getNewExpression()
 * @model
 * @generated
 */
public interface NewExpression extends Expression, ParameterizedAccess {
	/**
	 * Returns the value of the '<em><b>Callee</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Callee</em>' containment reference.
	 * @see #setCallee(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getNewExpression_Callee()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCallee();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.NewExpression#getCallee <em>Callee</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Callee</em>' containment reference.
	 * @see #getCallee()
	 * @generated
	 */
	void setCallee(Expression value);

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.Argument}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getNewExpression_Arguments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Argument> getArguments();

	/**
	 * Returns the value of the '<em><b>With Args</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>With Args</em>' attribute.
	 * @see #setWithArgs(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getNewExpression_WithArgs()
	 * @model unique="false"
	 * @generated
	 */
	boolean isWithArgs();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.NewExpression#isWithArgs <em>With Args</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>With Args</em>' attribute.
	 * @see #isWithArgs()
	 * @generated
	 */
	void setWithArgs(boolean value);

} // NewExpression
