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
package org.eclipse.n4js.utils.validation;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Marker</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Base class for pseduo-elements used by {@link org.eclipse.n4js.utils.validation.PrePostDiagnostician}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.utils.validation.ValidationMarker#getDelegateResource <em>Delegate Resource</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.utils.validation.ValidationPackage#getValidationMarker()
 * @model abstract="true"
 * @generated
 */
public interface ValidationMarker extends EObject {
	/**
	 * Returns the value of the '<em><b>Delegate Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delegate Resource</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delegate Resource</em>' attribute.
	 * @see #setDelegateResource(Resource)
	 * @see org.eclipse.n4js.utils.validation.ValidationPackage#getValidationMarker_DelegateResource()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	Resource getDelegateResource();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.utils.validation.ValidationMarker#getDelegateResource <em>Delegate Resource</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delegate Resource</em>' attribute.
	 * @see #getDelegateResource()
	 * @generated
	 */
	void setDelegateResource(Resource value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Overrides original {@link org.eclipse.emf.ecore.EObject#eResource()} method delegating to
	 * the resource currently validated.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	Resource eResource();

} // ValidationMarker
