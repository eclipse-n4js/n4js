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
 * A representation of the model object '<em><b>Annotable N4 Member Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration#getAnnotationList <em>Annotation List</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAnnotableN4MemberDeclaration()
 * @model abstract="true"
 * @generated
 */
public interface AnnotableN4MemberDeclaration extends N4MemberDeclaration {
	/**
	 * Returns the value of the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotation List</em>' containment reference.
	 * @see #setAnnotationList(N4MemberAnnotationList)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAnnotableN4MemberDeclaration_AnnotationList()
	 * @model containment="true"
	 * @generated
	 */
	N4MemberAnnotationList getAnnotationList();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration#getAnnotationList <em>Annotation List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotation List</em>' containment reference.
	 * @see #getAnnotationList()
	 * @generated
	 */
	void setAnnotationList(N4MemberAnnotationList value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

} // AnnotableN4MemberDeclaration
