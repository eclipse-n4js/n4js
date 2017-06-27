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

import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ArrayElement#isSpread <em>Spread</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ArrayElement#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getArrayElement()
 * @model
 * @generated
 */
public interface ArrayElement extends TypableElement {
	/**
	 * Returns the value of the '<em><b>Spread</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Spread</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Spread</em>' attribute.
	 * @see #setSpread(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getArrayElement_Spread()
	 * @model unique="false"
	 * @generated
	 */
	boolean isSpread();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ArrayElement#isSpread <em>Spread</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Spread</em>' attribute.
	 * @see #isSpread()
	 * @generated
	 */
	void setSpread(boolean value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getArrayElement_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ArrayElement#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

} // ArrayElement
