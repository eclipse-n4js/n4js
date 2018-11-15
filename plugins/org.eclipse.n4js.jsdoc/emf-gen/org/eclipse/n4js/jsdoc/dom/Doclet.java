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
 * A representation of the model object '<em><b>Doclet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.Doclet#getLineTags <em>Line Tags</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getDoclet()
 * @model
 * @generated
 */
public interface Doclet extends Composite {
	/**
	 * Returns the value of the '<em><b>Line Tags</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.jsdoc.dom.LineTag}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet <em>Doclet</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Tags</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Tags</em>' containment reference list.
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getDoclet_LineTags()
	 * @see org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet
	 * @model opposite="doclet" containment="true"
	 * @generated
	 */
	EList<LineTag> getLineTags();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" titleUnique="false"
	 * @generated
	 */
	boolean hasLineTag(String title);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" titleUnique="false"
	 * @generated
	 */
	EList<LineTag> lineTags(String title);

} // Doclet
