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

import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Enum Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getValueExpression <em>Value Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getDefinedLiteral <em>Defined Literal</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4EnumLiteral()
 * @model
 * @generated
 */
public interface N4EnumLiteral extends NamedElement, TypableElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4EnumLiteral_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Value Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Expression</em>' containment reference.
	 * @see #setValueExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4EnumLiteral_ValueExpression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getValueExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getValueExpression <em>Value Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Expression</em>' containment reference.
	 * @see #getValueExpression()
	 * @generated
	 */
	void setValueExpression(Expression value);

	/**
	 * Returns the value of the '<em><b>Defined Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined Literal</em>' reference.
	 * @see #setDefinedLiteral(TEnumLiteral)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4EnumLiteral_DefinedLiteral()
	 * @model transient="true"
	 * @generated
	 */
	TEnumLiteral getDefinedLiteral();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getDefinedLiteral <em>Defined Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Literal</em>' reference.
	 * @see #getDefinedLiteral()
	 * @generated
	 */
	void setDefinedLiteral(TEnumLiteral value);

} // N4EnumLiteral
