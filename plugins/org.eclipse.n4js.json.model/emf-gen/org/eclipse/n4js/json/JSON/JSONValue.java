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
package org.eclipse.n4js.json.JSON;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Common supertype to all values that may exist in JSON.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.json.JSON.JSONPackage#getJSONValue()
 * @model abstract="true"
 * @generated
 */
public interface JSONValue extends EObject {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns {@code true} iff the given {@link JSONValue} may contain children (e.g. object, array) and does not just
	 * represent a primitive value (e.g. string, boolean).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isContainer();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the children of this value.
	 * 	 * Returns an empty list for non-container {@link JSONValue}s.
	 * 	 * @See {@link #isContainer(JSONValue)}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<EObject> getChildren();
} // JSONValue
