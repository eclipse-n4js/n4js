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
package org.eclipse.n4js.transpiler.im;

import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Property Access Expression IM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * SymbolTable-aware replacement for {@link ParameterizedPropertyAccessExpression}.
 * Original property {@link ParameterizedPropertyAccessExpression.property} is always {@code null}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#isAnyPlusAccess <em>Any Plus Access</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getNameOfAnyPlusProperty <em>Name Of Any Plus Property</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getParameterizedPropertyAccessExpression_IM()
 * @model
 * @generated
 */
public interface ParameterizedPropertyAccessExpression_IM extends ParameterizedPropertyAccessExpression, ReferencingElementExpression_IM {
	/**
	 * Returns the value of the '<em><b>Any Plus Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Plus Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Plus Access</em>' attribute.
	 * @see #setAnyPlusAccess(boolean)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getParameterizedPropertyAccessExpression_IM_AnyPlusAccess()
	 * @model unique="false"
	 * @generated
	 */
	boolean isAnyPlusAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#isAnyPlusAccess <em>Any Plus Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Any Plus Access</em>' attribute.
	 * @see #isAnyPlusAccess()
	 * @generated
	 */
	void setAnyPlusAccess(boolean value);

	/**
	 * Returns the value of the '<em><b>Name Of Any Plus Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Name of the accessed property in case of a <code>any+</code> property access.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name Of Any Plus Property</em>' attribute.
	 * @see #setNameOfAnyPlusProperty(String)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getParameterizedPropertyAccessExpression_IM_NameOfAnyPlusProperty()
	 * @model unique="false"
	 * @generated
	 */
	String getNameOfAnyPlusProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getNameOfAnyPlusProperty <em>Name Of Any Plus Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Of Any Plus Property</em>' attribute.
	 * @see #getNameOfAnyPlusProperty()
	 * @generated
	 */
	void setNameOfAnyPlusProperty(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	SymbolTableEntry getProperty_IM();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model targetUnique="false"
	 * @generated
	 */
	void setProperty_IM(SymbolTableEntry target);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getPropertyName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  overridden attribute access to always return null
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	IdentifiableElement getProperty();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ixUnique="false"
	 * @generated
	 */
	void setProperty(IdentifiableElement ix);

} // ParameterizedPropertyAccessExpression_IM
