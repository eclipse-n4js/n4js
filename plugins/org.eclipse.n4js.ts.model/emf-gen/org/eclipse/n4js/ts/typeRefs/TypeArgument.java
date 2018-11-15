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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Type argument used in parameterized types.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeArgument()
 * @model abstract="true"
 * @generated
 */
public interface TypeArgument extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the type expression, usually the type name, as a string. Basically used for testing.
	 * As the returned string is used for comparison in tests, this method should not be changed.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if this type reference or one of its type arguments (transitively) contains references to type variables, or if the reference is not parameterized and the referenced type is generic.
	 * Returns false for all type arguments and type references except parameterized type references.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean containsWildcards();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns true if type reference or one of its type
	 * arguments (transitively) contains references to type variables, or if the
	 * reference is not parameterized  (or one of its nested references) but refers to a generic type.
	 * Returns false for all type references except parameterized type references.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean containsUnboundTypeVariables();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns null for all type arguments except parameterized type references.
	 * Reduces number of casts in client code.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	Type getDeclaredType();

} // TypeArgument
