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

import java.util.Map;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for types containing members, such as TClassifier and PrimitiveTypes.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getOwnedMembersByNameAndAccess <em>Owned Members By Name And Access</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getOwnedMembers <em>Owned Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getCallableCtor <em>Callable Ctor</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ContainerType#getTypeVars <em>Type Vars</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType()
 * @model abstract="true"
 * @generated
 */
public interface ContainerType<MT extends TMember> extends Type {
	/**
	 * Returns the value of the '<em><b>Owned Members By Name And Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Members By Name And Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Members By Name And Access</em>' attribute.
	 * @see #setOwnedMembersByNameAndAccess(Map)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_OwnedMembersByNameAndAccess()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	Map<NameAndAccess, ? extends TMember> getOwnedMembersByNameAndAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ContainerType#getOwnedMembersByNameAndAccess <em>Owned Members By Name And Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owned Members By Name And Access</em>' attribute.
	 * @see #getOwnedMembersByNameAndAccess()
	 * @generated
	 */
	void setOwnedMembersByNameAndAccess(Map<NameAndAccess, ? extends TMember> value);

	/**
	 * Returns the value of the '<em><b>Owned Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Members</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Members</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_OwnedMembers()
	 * @model containment="true"
	 * @generated
	 */
	EList<MT> getOwnedMembers();

	/**
	 * Returns the value of the '<em><b>Callable Ctor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Callable Ctor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Callable Ctor</em>' containment reference.
	 * @see #setCallableCtor(TMethod)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_CallableCtor()
	 * @model containment="true"
	 * @generated
	 */
	TMethod getCallableCtor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ContainerType#getCallableCtor <em>Callable Ctor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Callable Ctor</em>' containment reference.
	 * @see #getCallableCtor()
	 * @generated
	 */
	void setCallableCtor(TMethod value);

	/**
	 * Returns the value of the '<em><b>Type Vars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TypeVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * type parameters of generic types
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type Vars</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getContainerType_TypeVars()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method returning the owned constructor or <code>null</code> if not available.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TMethod getOwnedCtor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, similar to {code findMember(name, false)}.
	 * That is, this method returns fields, methods, and getters rather then setters.
	 * <!-- end-model-doc -->
	 * @model unique="false" nameUnique="false"
	 * @generated
	 */
	TMember findOwnedMember(String name);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns members with given name, writeable and static access.
	 * <!-- end-model-doc -->
	 * @model unique="false" nameUnique="false" writeAccessUnique="false" staticAccessUnique="false"
	 * @generated
	 */
	TMember findOwnedMember(String name, boolean writeAccess, boolean staticAccess);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Creates member collection mapped by static and writable access, fields are listed twice (since they are read- and
	 * writeable).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	Map<NameAndAccess, ? extends TMember> getOrCreateOwnedMembersByNameAndAccess();

} // ContainerType
