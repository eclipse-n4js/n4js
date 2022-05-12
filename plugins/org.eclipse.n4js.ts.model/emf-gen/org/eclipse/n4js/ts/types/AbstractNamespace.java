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
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getFunctions <em>Functions</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getExportedVariables <em>Exported Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getLocalVariables <em>Local Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getExposedLocalVariables <em>Exposed Local Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AbstractNamespace#getNamespaces <em>Namespaces</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace()
 * @model abstract="true"
 * @generated
 */
public interface AbstractNamespace extends TExportingElement {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all types declarations of this namespace.
	 * These include the exported classes, interfaces as well as the types
	 * inferred from type defining elements that are not marked as exported.
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
	 * Returns the value of the '<em><b>Functions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TFunction}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all functions of this namespace, both exported and local.
	 * This allows for better validation messages and diagnostics in later stages
	 * of the processing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Functions</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace_Functions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TFunction> getFunctions();

	/**
	 * Returns the value of the '<em><b>Exported Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all exported variables of this namespace.
	 * Exported variables are always directly contained in the namespace.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Exported Variables</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace_ExportedVariables()
	 * @model containment="true"
	 * @generated
	 */
	EList<TVariable> getExportedVariables();

	/**
	 * Returns the value of the '<em><b>Local Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all non-exported variables of this namespace.
	 * These variables may be directly contained in the namespace OR may be declared inside functions,
	 * methods, etc. contained in the namespace.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Local Variables</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace_LocalVariables()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<TVariable> getLocalVariables();

	/**
	 * Returns the value of the '<em><b>Exposed Local Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exposed Local Variables</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAbstractNamespace_ExposedLocalVariables()
	 * @model containment="true"
	 * @generated
	 */
	EList<TVariable> getExposedLocalVariables();

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
	 * Returns all exportable elements directly contained in this {@link AbstractNamespace},
	 * including those not actually exported.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.IterableOfTExportableElement" unique="false"
	 * @generated
	 */
	Iterable<? extends TExportableElement> getTypesAndFunctions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all exportable elements directly contained in this {@link AbstractNamespace},
	 * including those not actually exported.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.IterableOfTExportableElement" unique="false"
	 * @generated
	 */
	Iterable<? extends TExportableElement> getExportableElements();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.IterableOfAbstractNamespace" unique="false"
	 * @generated
	 */
	Iterable<? extends AbstractNamespace> getAllNamespaces();

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

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void clearTransientElements();

} // AbstractNamespace
