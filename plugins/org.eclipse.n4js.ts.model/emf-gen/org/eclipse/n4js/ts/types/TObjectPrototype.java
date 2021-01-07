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
package org.eclipse.n4js.ts.types;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TObject Prototype</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Plain JavaScript objects with super type (=prototype).
 * This is used for defining predefined types such as Object, String, Date etc.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TObjectPrototype#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TObjectPrototype#isDeclaredFinal <em>Declared Final</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTObjectPrototype()
 * @model
 * @generated
 */
public interface TObjectPrototype extends TClassifier, AccessibleTypeElement, ArrayLike {
	/**
	 * Returns the value of the '<em><b>Super Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Type</em>' containment reference.
	 * @see #setSuperType(ParameterizedTypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTObjectPrototype_SuperType()
	 * @model containment="true"
	 * @generated
	 */
	ParameterizedTypeRef getSuperType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TObjectPrototype#getSuperType <em>Super Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type</em>' containment reference.
	 * @see #getSuperType()
	 * @generated
	 */
	void setSuperType(ParameterizedTypeRef value);

	/**
	 * Returns the value of the '<em><b>Declared Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Final</em>' attribute.
	 * @see #setDeclaredFinal(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTObjectPrototype_DeclaredFinal()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredFinal();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TObjectPrototype#isDeclaredFinal <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Final</em>' attribute.
	 * @see #isDeclaredFinal()
	 * @generated
	 */
	void setDeclaredFinal(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TMethod getOwnedCtor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns value of declaredFinal attribute.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isFinal();

} // TObjectPrototype
