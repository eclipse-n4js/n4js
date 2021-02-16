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

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.TInterface;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Interface Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getSuperInterfaceRefs <em>Super Interface Refs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4InterfaceDeclaration()
 * @model
 * @generated
 */
public interface N4InterfaceDeclaration extends N4ClassifierDeclaration, Versionable, VersionedElement {
	/**
	 * Returns the value of the '<em><b>Super Interface Refs</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.TypeReferenceNode}<code>&lt;org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef&gt;</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Interface Refs</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4InterfaceDeclaration_SuperInterfaceRefs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeReferenceNode<ParameterizedTypeRef>> getSuperInterfaceRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns {@link #getDefinedType()} casted to {@link TInterface}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TInterface getDefinedTypeAsInterface();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.ParameterizedTypeRefNodeIterable" unique="false"
	 * @generated
	 */
	Iterable<TypeReferenceNode<ParameterizedTypeRef>> getSuperClassifierRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.n4JS.ParameterizedTypeRefNodeIterable" unique="false"
	 * @generated
	 */
	Iterable<TypeReferenceNode<ParameterizedTypeRef>> getImplementedOrExtendedInterfaceRefs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Override VersionedElement#getVersion() to return the declared version.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	int getVersion();

} // N4InterfaceDeclaration
