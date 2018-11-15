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
 * A representation of the model object '<em><b>Base Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.BaseTypeRef#isDynamic <em>Dynamic</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBaseTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface BaseTypeRef extends StaticBaseTypeRef {
	/**
	 * Returns the value of the '<em><b>Dynamic</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Flag indicating that new properties can be dynamically added to the type. Default value is false.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dynamic</em>' attribute.
	 * @see #setDynamic(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getBaseTypeRef_Dynamic()
	 * @model default="false" unique="false"
	 * @generated
	 */
	boolean isDynamic();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.BaseTypeRef#isDynamic <em>Dynamic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dynamic</em>' attribute.
	 * @see #isDynamic()
	 * @generated
	 */
	void setDynamic(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Override for {@link TypeRef#getModifiersAsString()}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getModifiersAsString();

} // BaseTypeRef
