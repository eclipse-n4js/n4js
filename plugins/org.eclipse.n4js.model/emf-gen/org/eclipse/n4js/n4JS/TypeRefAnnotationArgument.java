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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Ref Annotation Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * AST Annotation Argument with a type reference, it is recommended to use
 * TAnnotationTypeRefArgument or corresponding type model related field
 * to access this information.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TypeRefAnnotationArgument#getTypeRefNode <em>Type Ref Node</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeRefAnnotationArgument()
 * @model
 * @generated
 */
public interface TypeRefAnnotationArgument extends AnnotationArgument {
	/**
	 * Returns the value of the '<em><b>Type Ref Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Ref Node</em>' containment reference.
	 * @see #setTypeRefNode(TypeReferenceNode)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeRefAnnotationArgument_TypeRefNode()
	 * @model containment="true"
	 * @generated
	 */
	TypeReferenceNode<TypeRef> getTypeRefNode();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TypeRefAnnotationArgument#getTypeRefNode <em>Type Ref Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Ref Node</em>' containment reference.
	 * @see #getTypeRefNode()
	 * @generated
	 */
	void setTypeRefNode(TypeReferenceNode<TypeRef> value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getTypeRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	TypeRef value();

} // TypeRefAnnotationArgument
