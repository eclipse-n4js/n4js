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
package org.eclipse.n4js.ts.typeRefs;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Literal Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.LiteralTypeRef#getAstValue <em>Ast Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getLiteralTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface LiteralTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Ast Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ast Value</em>' attribute.
	 * @see #setAstValue(Object)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getLiteralTypeRef_AstValue()
	 * @model unique="false"
	 * @generated
	 */
	Object getAstValue();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.LiteralTypeRef#getAstValue <em>Ast Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ast Value</em>' attribute.
	 * @see #getAstValue()
	 * @generated
	 */
	void setAstValue(Object value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	Object getValue();

} // LiteralTypeRef
