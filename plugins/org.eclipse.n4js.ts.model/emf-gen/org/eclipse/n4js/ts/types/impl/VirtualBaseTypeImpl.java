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
package org.eclipse.n4js.ts.types.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.ArrayLike;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.VirtualBaseType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Base Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.VirtualBaseTypeImpl#getDeclaredElementType <em>Declared Element Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.VirtualBaseTypeImpl#getDeclaredOwnedMembers <em>Declared Owned Members</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VirtualBaseTypeImpl extends ContainerTypeImpl<TMember> implements VirtualBaseType {
	/**
	 * The cached value of the '{@link #getDeclaredElementType() <em>Declared Element Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredElementType()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredElementType;

	/**
	 * The cached value of the '{@link #getDeclaredOwnedMembers() <em>Declared Owned Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TMember> declaredOwnedMembers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VirtualBaseTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.VIRTUAL_BASE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef getDeclaredElementType() {
		return declaredElementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredElementType(TypeRef newDeclaredElementType, NotificationChain msgs) {
		TypeRef oldDeclaredElementType = declaredElementType;
		declaredElementType = newDeclaredElementType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE, oldDeclaredElementType, newDeclaredElementType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredElementType(TypeRef newDeclaredElementType) {
		if (newDeclaredElementType != declaredElementType) {
			NotificationChain msgs = null;
			if (declaredElementType != null)
				msgs = ((InternalEObject)declaredElementType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE, null, msgs);
			if (newDeclaredElementType != null)
				msgs = ((InternalEObject)newDeclaredElementType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE, null, msgs);
			msgs = basicSetDeclaredElementType(newDeclaredElementType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE, newDeclaredElementType, newDeclaredElementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TMember> getDeclaredOwnedMembers() {
		if (declaredOwnedMembers == null) {
			declaredOwnedMembers = new EObjectContainmentEList<TMember>(TMember.class, this, TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_OWNED_MEMBERS);
		}
		return declaredOwnedMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE:
				return basicSetDeclaredElementType(null, msgs);
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_OWNED_MEMBERS:
				return ((InternalEList<?>)getDeclaredOwnedMembers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE:
				return getDeclaredElementType();
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_OWNED_MEMBERS:
				return getDeclaredOwnedMembers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)newValue);
				return;
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_OWNED_MEMBERS:
				getDeclaredOwnedMembers().clear();
				getDeclaredOwnedMembers().addAll((Collection<? extends TMember>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)null);
				return;
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_OWNED_MEMBERS:
				getDeclaredOwnedMembers().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE:
				return declaredElementType != null;
			case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_OWNED_MEMBERS:
				return declaredOwnedMembers != null && !declaredOwnedMembers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ArrayLike.class) {
			switch (derivedFeatureID) {
				case TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE: return TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ArrayLike.class) {
			switch (baseFeatureID) {
				case TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE: return TypesPackage.VIRTUAL_BASE_TYPE__DECLARED_ELEMENT_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //VirtualBaseTypeImpl
