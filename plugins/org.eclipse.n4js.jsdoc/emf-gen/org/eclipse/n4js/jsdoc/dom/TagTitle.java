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
 * A representation of the model object '<em><b>Tag Title</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getTag <em>Tag</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getActualTitle <em>Actual Title</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTagTitle()
 * @model
 * @generated
 */
public interface TagTitle extends JSDocNode {
	/**
	 * Returns the value of the '<em><b>Tag</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.jsdoc.dom.Tag#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tag</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag</em>' container reference.
	 * @see #setTag(Tag)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTagTitle_Tag()
	 * @see org.eclipse.n4js.jsdoc.dom.Tag#getTitle
	 * @model opposite="title" transient="false"
	 * @generated
	 */
	Tag getTag();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getTag <em>Tag</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag</em>' container reference.
	 * @see #getTag()
	 * @generated
	 */
	void setTag(Tag value);

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTagTitle_Title()
	 * @model unique="false"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Actual Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actual Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual Title</em>' attribute.
	 * @see #setActualTitle(String)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTagTitle_ActualTitle()
	 * @model unique="false"
	 * @generated
	 */
	String getActualTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getActualTitle <em>Actual Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual Title</em>' attribute.
	 * @see #getActualTitle()
	 * @generated
	 */
	void setActualTitle(String value);

} // TagTitle
