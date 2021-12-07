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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ambient Module Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  Only allowed in DTS.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.AmbientModuleDeclaration#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.AmbientModuleDeclaration#getScriptElements <em>Script Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAmbientModuleDeclaration()
 * @model
 * @generated
 */
public interface AmbientModuleDeclaration extends ScriptElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAmbientModuleDeclaration_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.AmbientModuleDeclaration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Script Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.ScriptElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Script Elements</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAmbientModuleDeclaration_ScriptElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<ScriptElement> getScriptElements();

} // AmbientModuleDeclaration
