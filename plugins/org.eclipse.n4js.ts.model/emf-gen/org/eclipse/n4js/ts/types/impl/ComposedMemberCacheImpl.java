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

import org.eclipse.n4js.ts.types.ComposedMemberCache;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composed Member Cache</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ComposedMemberCacheImpl#getCachedComposedMembers <em>Cached Composed Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ComposedMemberCacheImpl#getComposedTypeRef <em>Composed Type Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ComposedMemberCacheImpl extends ProxyResolvingEObjectImpl implements ComposedMemberCache {
	/**
	 * The cached value of the '{@link #getCachedComposedMembers() <em>Cached Composed Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCachedComposedMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TMember> cachedComposedMembers;

	/**
	 * The cached value of the '{@link #getComposedTypeRef() <em>Composed Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef composedTypeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedMemberCacheImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.COMPOSED_MEMBER_CACHE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TMember> getCachedComposedMembers() {
		if (cachedComposedMembers == null) {
			cachedComposedMembers = new EObjectContainmentEList<TMember>(TMember.class, this, TypesPackage.COMPOSED_MEMBER_CACHE__CACHED_COMPOSED_MEMBERS);
		}
		return cachedComposedMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef getComposedTypeRef() {
		return composedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComposedTypeRef(TypeRef newComposedTypeRef, NotificationChain msgs) {
		TypeRef oldComposedTypeRef = composedTypeRef;
		composedTypeRef = newComposedTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF, oldComposedTypeRef, newComposedTypeRef);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComposedTypeRef(TypeRef newComposedTypeRef) {
		if (newComposedTypeRef != composedTypeRef) {
			NotificationChain msgs = null;
			if (composedTypeRef != null)
				msgs = ((InternalEObject)composedTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF, null, msgs);
			if (newComposedTypeRef != null)
				msgs = ((InternalEObject)newComposedTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF, null, msgs);
			msgs = basicSetComposedTypeRef(newComposedTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF, newComposedTypeRef, newComposedTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.COMPOSED_MEMBER_CACHE__CACHED_COMPOSED_MEMBERS:
				return ((InternalEList<?>)getCachedComposedMembers()).basicRemove(otherEnd, msgs);
			case TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF:
				return basicSetComposedTypeRef(null, msgs);
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
			case TypesPackage.COMPOSED_MEMBER_CACHE__CACHED_COMPOSED_MEMBERS:
				return getCachedComposedMembers();
			case TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF:
				return getComposedTypeRef();
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
			case TypesPackage.COMPOSED_MEMBER_CACHE__CACHED_COMPOSED_MEMBERS:
				getCachedComposedMembers().clear();
				getCachedComposedMembers().addAll((Collection<? extends TMember>)newValue);
				return;
			case TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF:
				setComposedTypeRef((TypeRef)newValue);
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
			case TypesPackage.COMPOSED_MEMBER_CACHE__CACHED_COMPOSED_MEMBERS:
				getCachedComposedMembers().clear();
				return;
			case TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF:
				setComposedTypeRef((TypeRef)null);
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
			case TypesPackage.COMPOSED_MEMBER_CACHE__CACHED_COMPOSED_MEMBERS:
				return cachedComposedMembers != null && !cachedComposedMembers.isEmpty();
			case TypesPackage.COMPOSED_MEMBER_CACHE__COMPOSED_TYPE_REF:
				return composedTypeRef != null;
		}
		return super.eIsSet(featureID);
	}

} //ComposedMemberCacheImpl
