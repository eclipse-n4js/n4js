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
package org.eclipse.n4js.projectDescription;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Container Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Lists one or more source folders inside the project with a common intended usage (e.g. main source, tests).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.SourceContainerDescription#getSourceContainerType <em>Source Container Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.SourceContainerDescription#getPaths <em>Paths</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getSourceContainerDescription()
 * @model
 * @generated
 */
public interface SourceContainerDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Container Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.projectDescription.SourceContainerType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Container Type</em>' attribute.
	 * @see org.eclipse.n4js.projectDescription.SourceContainerType
	 * @see #setSourceContainerType(SourceContainerType)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getSourceContainerDescription_SourceContainerType()
	 * @model unique="false"
	 * @generated
	 */
	SourceContainerType getSourceContainerType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.SourceContainerDescription#getSourceContainerType <em>Source Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Container Type</em>' attribute.
	 * @see org.eclipse.n4js.projectDescription.SourceContainerType
	 * @see #getSourceContainerType()
	 * @generated
	 */
	void setSourceContainerType(SourceContainerType value);

	/**
	 * Returns the value of the '<em><b>Paths</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paths</em>' attribute list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getSourceContainerDescription_Paths()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getPaths();

} // SourceContainerDescription
