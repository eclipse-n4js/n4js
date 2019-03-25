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
package org.eclipse.n4js.n4JS.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.types.ComposedMemberCache;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Member Access</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.MemberAccessImpl#getComposedMemberCache <em>Composed Member Cache</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MemberAccessImpl extends ProxyResolvingEObjectImpl implements MemberAccess {
	/**
	 * The cached value of the '{@link #getComposedMemberCache() <em>Composed Member Cache</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedMemberCache()
	 * @generated
	 * @ordered
	 */
	protected ComposedMemberCache composedMemberCache;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MemberAccessImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.MEMBER_ACCESS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedMemberCache getComposedMemberCache() {
		if (composedMemberCache != null && composedMemberCache.eIsProxy()) {
			InternalEObject oldComposedMemberCache = (InternalEObject)composedMemberCache;
			composedMemberCache = (ComposedMemberCache)eResolveProxy(oldComposedMemberCache);
			if (composedMemberCache != oldComposedMemberCache) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
			}
		}
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedMemberCache basicGetComposedMemberCache() {
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComposedMemberCache(ComposedMemberCache newComposedMemberCache) {
		ComposedMemberCache oldComposedMemberCache = composedMemberCache;
		composedMemberCache = newComposedMemberCache;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE:
				if (resolve) return getComposedMemberCache();
				return basicGetComposedMemberCache();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)newValue);
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
			case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)null);
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
			case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE:
				return composedMemberCache != null;
		}
		return super.eIsSet(featureID);
	}

} //MemberAccessImpl
