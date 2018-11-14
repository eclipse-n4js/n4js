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
package org.eclipse.n4js.jsdoc.dom;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JS Doc Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.JSDocNode#getMarkers <em>Markers</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getJSDocNode()
 * @model abstract="true"
 * @generated
 */
public interface JSDocNode extends DocletElement {
	/**
	 * Returns the value of the '<em><b>Markers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.jsdoc.dom.Marker}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Markers, should not be accessed directly, instead, getMarkerValue and setMarker should be used.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Markers</em>' containment reference list.
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getJSDocNode_Markers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Marker> getMarkers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the value of the first marker with the given key, or null, if no such marker is found.
	 * <!-- end-model-doc -->
	 * @model unique="false" theKeyUnique="false"
	 * @generated
	 */
	String getMarkerValue(String theKey);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Sets the value of the marker with given key, if no such marker has been defined, it is added.
	 * <!-- end-model-doc -->
	 * @model theKeyUnique="false" valueUnique="false"
	 * @generated
	 */
	void setMarker(String theKey, String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if node contains given marker with the given value
	 * <!-- end-model-doc -->
	 * @model unique="false" theKeyUnique="false" theValueUnique="false"
	 * @generated
	 */
	boolean isMarkedAs(String theKey, String theValue);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String toString();

} // JSDocNode
