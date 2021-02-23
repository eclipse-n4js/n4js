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
	 *  Tells whether this type argument is a type reference. Used to avoid instanceof checks.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isTypeRef();

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns this type argument/reference as a string, usually according to N4JS syntax.
	 * <p>
	 * Both unresolved and resolved references to a type alias will be represented as a
	 * reference to a type alias (i.e. only the name of the type alias is included; the
	 * aliased/actual type is ignored).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Actual computation of the string representation of this type argument/reference.
	 * <p>
	 * Unresolved references to a type alias will be represented as such (i.e. only the
	 * name of the type alias is included; the aliased/actual type is ignored), whereas
	 * resolved references to a type alias will only include the aliased/actual type
	 * (the fact that a type alias is involved is not shown at all).
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

} // TypeArgument
