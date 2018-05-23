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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Line Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet <em>Doclet</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getLineTag()
 * @model
 * @generated
 */
public interface LineTag extends Tag {
	/**
	 * Returns the value of the '<em><b>Doclet</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.jsdoc.dom.Doclet#getLineTags <em>Line Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Doclet</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Doclet</em>' container reference.
	 * @see #setDoclet(Doclet)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getLineTag_Doclet()
	 * @see org.eclipse.n4js.jsdoc.dom.Doclet#getLineTags
	 * @model opposite="lineTags" transient="false"
	 * @generated
	 */
	Doclet getDoclet();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet <em>Doclet</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Doclet</em>' container reference.
	 * @see #getDoclet()
	 * @generated
	 */
	void setDoclet(Doclet value);

} // LineTag
