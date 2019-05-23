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
package org.eclipse.n4js.semver.Semver.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.semver.Semver.Qualifier;
import org.eclipse.n4js.semver.Semver.QualifierTag;
import org.eclipse.n4js.semver.Semver.SemverPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Qualifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.QualifierImpl#getPreRelease <em>Pre Release</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.QualifierImpl#getBuildMetadata <em>Build Metadata</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QualifierImpl extends SemverToStringableImpl implements Qualifier {
	/**
	 * The cached value of the '{@link #getPreRelease() <em>Pre Release</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreRelease()
	 * @generated
	 * @ordered
	 */
	protected QualifierTag preRelease;

	/**
	 * The cached value of the '{@link #getBuildMetadata() <em>Build Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuildMetadata()
	 * @generated
	 * @ordered
	 */
	protected QualifierTag buildMetadata;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QualifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.QUALIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public QualifierTag getPreRelease() {
		return preRelease;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreRelease(QualifierTag newPreRelease, NotificationChain msgs) {
		QualifierTag oldPreRelease = preRelease;
		preRelease = newPreRelease;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.QUALIFIER__PRE_RELEASE, oldPreRelease, newPreRelease);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPreRelease(QualifierTag newPreRelease) {
		if (newPreRelease != preRelease) {
			NotificationChain msgs = null;
			if (preRelease != null)
				msgs = ((InternalEObject)preRelease).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.QUALIFIER__PRE_RELEASE, null, msgs);
			if (newPreRelease != null)
				msgs = ((InternalEObject)newPreRelease).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.QUALIFIER__PRE_RELEASE, null, msgs);
			msgs = basicSetPreRelease(newPreRelease, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.QUALIFIER__PRE_RELEASE, newPreRelease, newPreRelease));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public QualifierTag getBuildMetadata() {
		return buildMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBuildMetadata(QualifierTag newBuildMetadata, NotificationChain msgs) {
		QualifierTag oldBuildMetadata = buildMetadata;
		buildMetadata = newBuildMetadata;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.QUALIFIER__BUILD_METADATA, oldBuildMetadata, newBuildMetadata);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBuildMetadata(QualifierTag newBuildMetadata) {
		if (newBuildMetadata != buildMetadata) {
			NotificationChain msgs = null;
			if (buildMetadata != null)
				msgs = ((InternalEObject)buildMetadata).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.QUALIFIER__BUILD_METADATA, null, msgs);
			if (newBuildMetadata != null)
				msgs = ((InternalEObject)newBuildMetadata).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.QUALIFIER__BUILD_METADATA, null, msgs);
			msgs = basicSetBuildMetadata(newBuildMetadata, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.QUALIFIER__BUILD_METADATA, newBuildMetadata, newBuildMetadata));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean equals(final Object obj) {
		if ((!(obj instanceof Qualifier))) {
			return false;
		}
		final Qualifier q = ((Qualifier) obj);
		QualifierTag _preRelease = this.getPreRelease();
		boolean _tripleEquals = (_preRelease == null);
		QualifierTag _preRelease_1 = q.getPreRelease();
		boolean _tripleEquals_1 = (_preRelease_1 == null);
		boolean equals = (Boolean.valueOf(_tripleEquals) == Boolean.valueOf(_tripleEquals_1));
		equals = ((equals && (this.getPreRelease() == null)) || this.getPreRelease().equals(q.getPreRelease()));
		equals = (equals && (Boolean.valueOf((this.getBuildMetadata() == null)) == Boolean.valueOf((q.getBuildMetadata() == null))));
		equals = ((equals && (this.getBuildMetadata() == null)) || this.getBuildMetadata().equals(q.getBuildMetadata()));
		return equals;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SemverPackage.QUALIFIER__PRE_RELEASE:
				return basicSetPreRelease(null, msgs);
			case SemverPackage.QUALIFIER__BUILD_METADATA:
				return basicSetBuildMetadata(null, msgs);
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
			case SemverPackage.QUALIFIER__PRE_RELEASE:
				return getPreRelease();
			case SemverPackage.QUALIFIER__BUILD_METADATA:
				return getBuildMetadata();
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
			case SemverPackage.QUALIFIER__PRE_RELEASE:
				setPreRelease((QualifierTag)newValue);
				return;
			case SemverPackage.QUALIFIER__BUILD_METADATA:
				setBuildMetadata((QualifierTag)newValue);
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
			case SemverPackage.QUALIFIER__PRE_RELEASE:
				setPreRelease((QualifierTag)null);
				return;
			case SemverPackage.QUALIFIER__BUILD_METADATA:
				setBuildMetadata((QualifierTag)null);
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
			case SemverPackage.QUALIFIER__PRE_RELEASE:
				return preRelease != null;
			case SemverPackage.QUALIFIER__BUILD_METADATA:
				return buildMetadata != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SemverPackage.QUALIFIER___EQUALS__OBJECT:
				return equals(arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //QualifierImpl
