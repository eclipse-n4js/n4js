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
package org.eclipse.n4js.ts.types;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composed Member Cache</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A cache for composed members. Not serialized to the Xtext index.
 * See {@link ComposedTypeRef#composedMemberCache()} for details.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.ComposedMemberCache#getCachedComposedMembers <em>Cached Composed Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ComposedMemberCache#getComposedTypeRef <em>Composed Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getComposedMemberCache()
 * @model
 * @generated
 */
public interface ComposedMemberCache extends EObject {
	/**
	 * Returns the value of the '<em><b>Cached Composed Members</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TMember}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cached Composed Members</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getComposedMemberCache_CachedComposedMembers()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<TMember> getCachedComposedMembers();

	/**
	 * Returns the value of the '<em><b>Composed Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composed Type Ref</em>' containment reference.
	 * @see #setComposedTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getComposedMemberCache_ComposedTypeRef()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	TypeRef getComposedTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ComposedMemberCache#getComposedTypeRef <em>Composed Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composed Type Ref</em>' containment reference.
	 * @see #getComposedTypeRef()
	 * @generated
	 */
	void setComposedTypeRef(TypeRef value);

} // ComposedMemberCache
