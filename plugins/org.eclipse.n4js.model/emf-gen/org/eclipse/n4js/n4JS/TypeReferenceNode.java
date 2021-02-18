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
 * A representation of the model object '<em><b>Type Reference Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * All type references contained in the AST are represented by this class, so this provides
 * a common bridge from the AST model to the Type/TypeRefs models.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TypeReferenceNode#getCachedProcessedTypeRef <em>Cached Processed Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.TypeReferenceNode#getTypeRefInAST <em>Type Ref In AST</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeReferenceNode()
 * @model
 * @generated
 */
public interface TypeReferenceNode<T extends TypeRef> extends EObject {
	/**
	 * Returns the value of the '<em><b>Cached Processed Type Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cached Processed Type Ref</em>' reference.
	 * @see #setCachedProcessedTypeRef(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeReferenceNode_CachedProcessedTypeRef()
	 * @model transient="true"
	 * @generated
	 */
	TypeRef getCachedProcessedTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TypeReferenceNode#getCachedProcessedTypeRef <em>Cached Processed Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cached Processed Type Ref</em>' reference.
	 * @see #getCachedProcessedTypeRef()
	 * @generated
	 */
	void setCachedProcessedTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Type Ref In AST</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Ref In AST</em>' containment reference.
	 * @see #setTypeRefInAST(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypeReferenceNode_TypeRefInAST()
	 * @model containment="true"
	 * @generated
	 */
	T getTypeRefInAST();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TypeReferenceNode#getTypeRefInAST <em>Type Ref In AST</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Ref In AST</em>' containment reference.
	 * @see #getTypeRefInAST()
	 * @generated
	 */
	void setTypeRefInAST(T value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Same as {@link #getTypeRefInAST()}, but processed by the {@code TypeRefProcessor} during
	 * post-processing. Currently, this processing only includes resolution of type aliases but
	 * more may be added in the future.
	 * <p>
	 * The returned type reference may or may not be identical with the one returned from
	 * {@link #getTypeRefInAST()}, and thus it may or may not be contained in the AST.
	 * <p>
	 * Since a type reference can change from {@code ParameterizedTypeRef} to something else
	 * (e.g. {@code FunctionTypeExpression}) during type alias resolution, depending on the
	 * aliased type, the type of this property must be {@link TypeRef} instead of {@code T}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getTypeRef();

} // TypeReferenceNode
