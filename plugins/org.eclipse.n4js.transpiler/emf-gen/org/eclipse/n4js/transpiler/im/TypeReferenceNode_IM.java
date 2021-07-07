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

import org.eclipse.emf.common.util.EList;
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
 *   <li>{@link org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM#getCodeToEmit <em>Code To Emit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getTypeReferenceNode_IM()
 * @model
 * @generated
 */
public interface TypeReferenceNode_IM<T extends TypeRef> extends TypeReferenceNode<T>, ManyReferencingElement_IM {
	/**
	 * Returns the value of the '<em><b>Code To Emit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The code to emit for the type reference represented by this node. This will be <code>null</code> initially
	 * and transformations are responsible for either setting this to a non-<code>null</code> value or removing
	 * this entire {@code TypeReferenceNode_IM} from the intermediate model.
	 * <p>
	 * When changing this value, property {@link TypeReferenceNode_IM#getRewiredReferences() rewiredReferences} should
	 * be updated accordingly, to ensure that functionality such as removal of unused imports can properly consider
	 * the types being referenced from within this string.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Code To Emit</em>' attribute.
	 * @see #setCodeToEmit(String)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getTypeReferenceNode_IM_CodeToEmit()
	 * @model unique="false"
	 * @generated
	 */
	String getCodeToEmit();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM#getCodeToEmit <em>Code To Emit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code To Emit</em>' attribute.
	 * @see #getCodeToEmit()
	 * @generated
	 */
	void setCodeToEmit(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The symbol table entries of the types actually being referenced from within the string {@link TypeReferenceNode_IM#getCodeToEmit() codeToEmit}.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<PlainReference> getRewiredReferences();

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
