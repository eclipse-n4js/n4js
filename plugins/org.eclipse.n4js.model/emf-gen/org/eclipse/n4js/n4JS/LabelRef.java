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
 * A representation of the model object '<em><b>Label Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.LabelRef#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.LabelRef#getLabelAsText <em>Label As Text</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLabelRef()
 * @model abstract="true"
 * @generated
 */
public interface LabelRef extends EObject {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' reference.
	 * @see #setLabel(LabelledStatement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLabelRef_Label()
	 * @model
	 * @generated
	 */
	LabelledStatement getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.LabelRef#getLabel <em>Label</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' reference.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(LabelledStatement value);

	/**
	 * Returns the value of the '<em><b>Label As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label As Text</em>' attribute.
	 * @see #setLabelAsText(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLabelRef_LabelAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getLabelAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.LabelRef#getLabelAsText <em>Label As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label As Text</em>' attribute.
	 * @see #getLabelAsText()
	 * @generated
	 */
	void setLabelAsText(String value);

} // LabelRef
