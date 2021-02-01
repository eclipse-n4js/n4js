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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Reference In AST</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TypeReferenceInAST#getTypeRef <em>Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.TypeReferenceInAST#getTypeRefInAST <em>Type Ref In AST</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeReferenceInAST()
 * @model
 * @generated
 */
public interface TypeReferenceInAST extends EObject {
	/**
	 * Returns the value of the '<em><b>Type Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Same as {@link #getDeclaredTypeRefInAST()}, but with type aliases being resolved (if any).
	 * The returned type reference may or may not be contained in the AST.
	 * This is set during post-processing by {@code TypeRefProcessor}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Ref</em>' reference.
	 * @see #setTypeRef(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeReferenceInAST_TypeRef()
	 * @model transient="true"
	 * @generated
	 */
	TypeRef getTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TypeReferenceInAST#getTypeRef <em>Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Ref</em>' reference.
	 * @see #getTypeRef()
	 * @generated
	 */
	void setTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Type Ref In AST</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Ref In AST</em>' containment reference.
	 * @see #setTypeRefInAST(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeReferenceInAST_TypeRefInAST()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getTypeRefInAST();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TypeReferenceInAST#getTypeRefInAST <em>Type Ref In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Ref In AST</em>' containment reference.
	 * @see #getTypeRefInAST()
	 * @generated
	 */
	void setTypeRefInAST(TypeRef value);

} // TypeReferenceInAST
