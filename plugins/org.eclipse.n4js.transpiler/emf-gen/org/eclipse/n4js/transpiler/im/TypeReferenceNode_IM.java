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
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getTypeReferenceNode_IM()
 * @model
 * @generated
 */
public interface TypeReferenceNode_IM<T extends TypeRef> extends TypeReferenceNode<T> {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypeRef getTypeRef();
} // TypeReferenceNode_IM
