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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Strict Mode Relevant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.StrictModeRelevant#isStrictMode <em>Strict Mode</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getStrictModeRelevant()
 * @model abstract="true"
 * @generated
 */
public interface StrictModeRelevant extends EObject {
	/**
	 * Returns the value of the '<em><b>Strict Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strict Mode</em>' attribute.
	 * @see #setStrictMode(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getStrictModeRelevant_StrictMode()
	 * @model unique="false" transient="true" derived="true"
	 * @generated
	 */
	boolean isStrictMode();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.StrictModeRelevant#isStrictMode <em>Strict Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strict Mode</em>' attribute.
	 * @see #isStrictMode()
	 * @generated
	 */
	void setStrictMode(boolean value);

} // StrictModeRelevant
