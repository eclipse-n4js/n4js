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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Namespace</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getTypes <em>Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getNamespaces <em>Namespaces</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace()
 * @model abstract="true"
 * @generated
 */
public interface AbstractNamespace extends EObject {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all types declarations in the script on the top level.
	 * These include the exported classes, interfaces, function as well
	 * as the types inferred from type defining elements that are not marked as exported.
	 * This allows for better validation messages and diagnostics in later stages
	 * of the processing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getTypes();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all top level variables in the script.
	 * These include the exported variables as well as the internal variables.
	 * Similar to #types, this allows for better validation messages and diagnostics.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<TVariable> getVariables();

	/**
	 * Returns the value of the '<em><b>Namespaces</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TNamespace}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all top level namespaces in the script.
	 * These include the exported namespaces as well as the internal namespaces.
	 * Similar to #types, this allows for better validation messages and diagnostics.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Namespaces</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace_Namespaces()
	 * @model containment="true"
	 * @generated
	 */
	EList<TNamespace> getNamespaces();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns module this element is contained in, or
	 * null if it is not contained in a module.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TModule getContainingModule();

} // AbstractNamespace
