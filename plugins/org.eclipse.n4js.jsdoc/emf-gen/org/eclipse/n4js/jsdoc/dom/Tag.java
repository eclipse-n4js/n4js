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

import org.eclipse.n4js.jsdoc.ITagDefinition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.Tag#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.Tag#getValues <em>Values</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.Tag#getTagDefinition <em>Tag Definition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTag()
 * @model abstract="true"
 * @generated
 */
public interface Tag extends DocletElement {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getTag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' containment reference.
	 * @see #setTitle(TagTitle)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTag_Title()
	 * @see org.eclipse.n4js.jsdoc.dom.TagTitle#getTag
	 * @model opposite="tag" containment="true"
	 * @generated
	 */
	TagTitle getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.Tag#getTitle <em>Title</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' containment reference.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(TagTitle value);

	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.jsdoc.dom.TagValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTag_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<TagValue> getValues();

	/**
	 * Returns the value of the '<em><b>Tag Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tag Definition</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag Definition</em>' attribute.
	 * @see #setTagDefinition(ITagDefinition)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getTag_TagDefinition()
	 * @model unique="false" dataType="org.eclipse.n4js.jsdoc.dom.TagDefinition" transient="true"
	 * @generated
	 */
	ITagDefinition getTagDefinition();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.Tag#getTagDefinition <em>Tag Definition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag Definition</em>' attribute.
	 * @see #getTagDefinition()
	 * @generated
	 */
	void setTagDefinition(ITagDefinition value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns first TagValue with given key or null, if no such key is found.
	 * <!-- end-model-doc -->
	 * @model unique="false" theKeyUnique="false"
	 * @generated
	 */
	TagValue getValueByKey(String theKey);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String toString();

} // Tag
