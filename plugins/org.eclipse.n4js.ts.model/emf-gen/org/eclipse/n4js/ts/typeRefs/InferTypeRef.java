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
 * A representation of the model object '<em><b>Infer Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  Only allowed in DTS (and only in the 'extends' clause of a conditional type).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.InferTypeRef#getTypeVarName <em>Type Var Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getInferTypeRef()
 * @model
 * @generated
 */
public interface InferTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Type Var Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Var Name</em>' attribute.
	 * @see #setTypeVarName(String)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getInferTypeRef_TypeVarName()
	 * @model unique="false"
	 * @generated
	 */
	String getTypeVarName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.InferTypeRef#getTypeVarName <em>Type Var Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Var Name</em>' attribute.
	 * @see #getTypeVarName()
	 * @generated
	 */
	void setTypeVarName(String value);

} // InferTypeRef
