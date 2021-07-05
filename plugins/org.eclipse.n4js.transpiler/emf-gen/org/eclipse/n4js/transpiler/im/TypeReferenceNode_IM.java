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
package org.eclipse.n4js.transpiler.im;

import org.eclipse.n4js.n4JS.TypeReferenceNode;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Reference Node IM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * SymbolTable-aware replacement for {@link TypeReferenceNode}.
 * Original properties {@link TypeReferenceNode.typeRefInAST} and {@link TypeReferenceNode.cachedProcessedTypeRef} are always {@code null}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM#getTypeRefAsCode <em>Type Ref As Code</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getTypeReferenceNode_IM()
 * @model
 * @generated
 */
public interface TypeReferenceNode_IM<T extends TypeRef> extends TypeReferenceNode<T>, ManyReferencingElement_IM {

	/**
	 * Returns the value of the '<em><b>Type Ref As Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Ref As Code</em>' attribute.
	 * @see #setTypeRefAsCode(String)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getTypeReferenceNode_IM_TypeRefAsCode()
	 * @model unique="false"
	 * @generated
	 */
	String getTypeRefAsCode();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM#getTypeRefAsCode <em>Type Ref As Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Ref As Code</em>' attribute.
	 * @see #getTypeRefAsCode()
	 * @generated
	 */
	void setTypeRefAsCode(String value);

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
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getCachedProcessedTypeRef();
} // TypeReferenceNode_IM
