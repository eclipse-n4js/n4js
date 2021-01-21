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
package org.eclipse.n4js.ts.typeRefs;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>This Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Unbound this type reference.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getThisTypeRef()
 * @model abstract="true"
 * @generated
 */
public interface ThisTypeRef extends BaseTypeRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#internalGetTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Always returns NOMINAL for simple this type references
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TypingStrategy getTypingStrategy();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns empty list for simply reference by default.
	 * Overridden in {@link ParameterizedTypeRefStructural}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<TStructMember> getStructuralMembers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Always returns false for simple this type references
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isUseSiteStructuralTyping();

} // ThisTypeRef
