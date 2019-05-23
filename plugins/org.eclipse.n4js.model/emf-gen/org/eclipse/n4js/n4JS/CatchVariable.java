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
 * A representation of the model object '<em><b>Catch Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.CatchVariable#getBindingPattern <em>Binding Pattern</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCatchVariable()
 * @model
 * @generated
 */
public interface CatchVariable extends Variable {
	/**
	 * Returns the value of the '<em><b>Binding Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding Pattern</em>' containment reference.
	 * @see #setBindingPattern(BindingPattern)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCatchVariable_BindingPattern()
	 * @model containment="true"
	 * @generated
	 */
	BindingPattern getBindingPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.CatchVariable#getBindingPattern <em>Binding Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding Pattern</em>' containment reference.
	 * @see #getBindingPattern()
	 * @generated
	 */
	void setBindingPattern(BindingPattern value);

} // CatchVariable
