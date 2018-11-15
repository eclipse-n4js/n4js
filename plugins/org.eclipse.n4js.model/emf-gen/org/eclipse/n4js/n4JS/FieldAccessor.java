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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field Accessor</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for getters and setter, of either object literals (PropertyG/SetterDeclaration) or classes (N4G/SetterDeclaration).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.FieldAccessor#isDeclaredOptional <em>Declared Optional</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFieldAccessor()
 * @model abstract="true"
 * @generated
 */
public interface FieldAccessor extends FunctionOrFieldAccessor, TypeProvidingElement, PropertyNameOwner {
	/**
	 * Returns the value of the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Optional</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Optional</em>' attribute.
	 * @see #setDeclaredOptional(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getFieldAccessor_DeclaredOptional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.FieldAccessor#isDeclaredOptional <em>Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Optional</em>' attribute.
	 * @see #isDeclaredOptional()
	 * @generated
	 */
	void setDeclaredOptional(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The declared type of the field accessor, which is either the return type of the getter or the type of the formal parameter in case of a setter
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getDeclaredTypeRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns the defined FieldAccessor in the TModule, either a TGetter or a TSetter.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	org.eclipse.n4js.ts.types.FieldAccessor getDefinedAccessor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isOptional();

} // FieldAccessor
