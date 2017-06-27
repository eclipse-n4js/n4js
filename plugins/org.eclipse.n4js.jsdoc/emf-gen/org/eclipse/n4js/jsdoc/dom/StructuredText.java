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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structured Text</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.StructuredText#getRootElement <em>Root Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getStructuredText()
 * @model
 * @generated
 */
public interface StructuredText extends Text {
	/**
	 * Returns the value of the '<em><b>Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Element</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Element</em>' containment reference.
	 * @see #setRootElement(EObject)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getStructuredText_RootElement()
	 * @model containment="true"
	 * @generated
	 */
	EObject getRootElement();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.StructuredText#getRootElement <em>Root Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Element</em>' containment reference.
	 * @see #getRootElement()
	 * @generated
	 */
	void setRootElement(EObject value);

} // StructuredText
