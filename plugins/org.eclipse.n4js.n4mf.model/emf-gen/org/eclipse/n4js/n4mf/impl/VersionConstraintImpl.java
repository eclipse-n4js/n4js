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
package org.eclipse.n4js.n4mf.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.VersionConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.VersionConstraintImpl#isExclLowerBound <em>Excl Lower Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.VersionConstraintImpl#getLowerVersion <em>Lower Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.VersionConstraintImpl#isExclUpperBound <em>Excl Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.VersionConstraintImpl#getUpperVersion <em>Upper Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionConstraintImpl extends MinimalEObjectImpl.Container implements VersionConstraint {
	/**
	 * The default value of the '{@link #isExclLowerBound() <em>Excl Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExclLowerBound()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCL_LOWER_BOUND_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExclLowerBound() <em>Excl Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExclLowerBound()
	 * @generated
	 * @ordered
	 */
	protected boolean exclLowerBound = EXCL_LOWER_BOUND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLowerVersion() <em>Lower Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerVersion()
	 * @generated
	 * @ordered
	 */
	protected DeclaredVersion lowerVersion;

	/**
	 * The default value of the '{@link #isExclUpperBound() <em>Excl Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExclUpperBound()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXCL_UPPER_BOUND_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExclUpperBound() <em>Excl Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExclUpperBound()
	 * @generated
	 * @ordered
	 */
	protected boolean exclUpperBound = EXCL_UPPER_BOUND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUpperVersion() <em>Upper Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperVersion()
	 * @generated
	 * @ordered
	 */
	protected DeclaredVersion upperVersion;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.VERSION_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExclLowerBound() {
		return exclLowerBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExclLowerBound(boolean newExclLowerBound) {
		boolean oldExclLowerBound = exclLowerBound;
		exclLowerBound = newExclLowerBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.VERSION_CONSTRAINT__EXCL_LOWER_BOUND, oldExclLowerBound, exclLowerBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclaredVersion getLowerVersion() {
		return lowerVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLowerVersion(DeclaredVersion newLowerVersion, NotificationChain msgs) {
		DeclaredVersion oldLowerVersion = lowerVersion;
		lowerVersion = newLowerVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION, oldLowerVersion, newLowerVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerVersion(DeclaredVersion newLowerVersion) {
		if (newLowerVersion != lowerVersion) {
			NotificationChain msgs = null;
			if (lowerVersion != null)
				msgs = ((InternalEObject)lowerVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION, null, msgs);
			if (newLowerVersion != null)
				msgs = ((InternalEObject)newLowerVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION, null, msgs);
			msgs = basicSetLowerVersion(newLowerVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION, newLowerVersion, newLowerVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExclUpperBound() {
		return exclUpperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExclUpperBound(boolean newExclUpperBound) {
		boolean oldExclUpperBound = exclUpperBound;
		exclUpperBound = newExclUpperBound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.VERSION_CONSTRAINT__EXCL_UPPER_BOUND, oldExclUpperBound, exclUpperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclaredVersion getUpperVersion() {
		return upperVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUpperVersion(DeclaredVersion newUpperVersion, NotificationChain msgs) {
		DeclaredVersion oldUpperVersion = upperVersion;
		upperVersion = newUpperVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION, oldUpperVersion, newUpperVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperVersion(DeclaredVersion newUpperVersion) {
		if (newUpperVersion != upperVersion) {
			NotificationChain msgs = null;
			if (upperVersion != null)
				msgs = ((InternalEObject)upperVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION, null, msgs);
			if (newUpperVersion != null)
				msgs = ((InternalEObject)newUpperVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION, null, msgs);
			msgs = basicSetUpperVersion(newUpperVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION, newUpperVersion, newUpperVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION:
				return basicSetLowerVersion(null, msgs);
			case N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION:
				return basicSetUpperVersion(null, msgs);
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
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_LOWER_BOUND:
				return isExclLowerBound();
			case N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION:
				return getLowerVersion();
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_UPPER_BOUND:
				return isExclUpperBound();
			case N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION:
				return getUpperVersion();
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
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_LOWER_BOUND:
				setExclLowerBound((Boolean)newValue);
				return;
			case N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION:
				setLowerVersion((DeclaredVersion)newValue);
				return;
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_UPPER_BOUND:
				setExclUpperBound((Boolean)newValue);
				return;
			case N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION:
				setUpperVersion((DeclaredVersion)newValue);
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
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_LOWER_BOUND:
				setExclLowerBound(EXCL_LOWER_BOUND_EDEFAULT);
				return;
			case N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION:
				setLowerVersion((DeclaredVersion)null);
				return;
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_UPPER_BOUND:
				setExclUpperBound(EXCL_UPPER_BOUND_EDEFAULT);
				return;
			case N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION:
				setUpperVersion((DeclaredVersion)null);
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
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_LOWER_BOUND:
				return exclLowerBound != EXCL_LOWER_BOUND_EDEFAULT;
			case N4mfPackage.VERSION_CONSTRAINT__LOWER_VERSION:
				return lowerVersion != null;
			case N4mfPackage.VERSION_CONSTRAINT__EXCL_UPPER_BOUND:
				return exclUpperBound != EXCL_UPPER_BOUND_EDEFAULT;
			case N4mfPackage.VERSION_CONSTRAINT__UPPER_VERSION:
				return upperVersion != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (exclLowerBound: ");
		result.append(exclLowerBound);
		result.append(", exclUpperBound: ");
		result.append(exclUpperBound);
		result.append(')');
		return result.toString();
	}

} //VersionConstraintImpl
