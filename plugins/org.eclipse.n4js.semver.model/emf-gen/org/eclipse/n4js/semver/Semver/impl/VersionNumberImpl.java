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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.semver.Semver.Qualifier;
import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionPart;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Version Number</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl#getMajor <em>Major</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl#getMinor <em>Minor</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl#getPatch <em>Patch</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl#getExtended <em>Extended</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.VersionNumberImpl#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VersionNumberImpl extends SemverToStringableImpl implements VersionNumber {
	/**
	 * The cached value of the '{@link #getMajor() <em>Major</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMajor()
	 * @generated
	 * @ordered
	 */
	protected VersionPart major;

	/**
	 * The cached value of the '{@link #getMinor() <em>Minor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinor()
	 * @generated
	 * @ordered
	 */
	protected VersionPart minor;

	/**
	 * The cached value of the '{@link #getPatch() <em>Patch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatch()
	 * @generated
	 * @ordered
	 */
	protected VersionPart patch;

	/**
	 * The cached value of the '{@link #getExtended() <em>Extended</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtended()
	 * @generated
	 * @ordered
	 */
	protected EList<VersionPart> extended;

	/**
	 * The cached value of the '{@link #getQualifier() <em>Qualifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifier()
	 * @generated
	 * @ordered
	 */
	protected Qualifier qualifier;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VersionNumberImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.VERSION_NUMBER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionPart getMajor() {
		return major;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMajor(VersionPart newMajor, NotificationChain msgs) {
		VersionPart oldMajor = major;
		major = newMajor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__MAJOR, oldMajor, newMajor);
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
	public void setMajor(VersionPart newMajor) {
		if (newMajor != major) {
			NotificationChain msgs = null;
			if (major != null)
				msgs = ((InternalEObject)major).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__MAJOR, null, msgs);
			if (newMajor != null)
				msgs = ((InternalEObject)newMajor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__MAJOR, null, msgs);
			msgs = basicSetMajor(newMajor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__MAJOR, newMajor, newMajor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionPart getMinor() {
		return minor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMinor(VersionPart newMinor, NotificationChain msgs) {
		VersionPart oldMinor = minor;
		minor = newMinor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__MINOR, oldMinor, newMinor);
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
	public void setMinor(VersionPart newMinor) {
		if (newMinor != minor) {
			NotificationChain msgs = null;
			if (minor != null)
				msgs = ((InternalEObject)minor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__MINOR, null, msgs);
			if (newMinor != null)
				msgs = ((InternalEObject)newMinor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__MINOR, null, msgs);
			msgs = basicSetMinor(newMinor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__MINOR, newMinor, newMinor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionPart getPatch() {
		return patch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPatch(VersionPart newPatch, NotificationChain msgs) {
		VersionPart oldPatch = patch;
		patch = newPatch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__PATCH, oldPatch, newPatch);
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
	public void setPatch(VersionPart newPatch) {
		if (newPatch != patch) {
			NotificationChain msgs = null;
			if (patch != null)
				msgs = ((InternalEObject)patch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__PATCH, null, msgs);
			if (newPatch != null)
				msgs = ((InternalEObject)newPatch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__PATCH, null, msgs);
			msgs = basicSetPatch(newPatch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__PATCH, newPatch, newPatch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<VersionPart> getExtended() {
		if (extended == null) {
			extended = new EObjectContainmentEList<VersionPart>(VersionPart.class, this, SemverPackage.VERSION_NUMBER__EXTENDED);
		}
		return extended;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Qualifier getQualifier() {
		return qualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQualifier(Qualifier newQualifier, NotificationChain msgs) {
		Qualifier oldQualifier = qualifier;
		qualifier = newQualifier;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__QUALIFIER, oldQualifier, newQualifier);
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
	public void setQualifier(Qualifier newQualifier) {
		if (newQualifier != qualifier) {
			NotificationChain msgs = null;
			if (qualifier != null)
				msgs = ((InternalEObject)qualifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__QUALIFIER, null, msgs);
			if (newQualifier != null)
				msgs = ((InternalEObject)newQualifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.VERSION_NUMBER__QUALIFIER, null, msgs);
			msgs = basicSetQualifier(newQualifier, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.VERSION_NUMBER__QUALIFIER, newQualifier, newQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isWildcard() {
		boolean _xifexpression = false;
		VersionPart _major = this.getMajor();
		boolean _tripleEquals = (_major == null);
		if (_tripleEquals) {
			_xifexpression = false;
		}
		else {
			_xifexpression = this.getMajor().isWildcard();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getPreReleaseTag() {
		EList<String> _xifexpression = null;
		if (((this.getQualifier() != null) && (this.getQualifier().getPreRelease() != null))) {
			_xifexpression = this.getQualifier().getPreRelease().getParts();
		}
		else {
			_xifexpression = null;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasPreReleaseTag() {
		return ((this.getPreReleaseTag() != null) && (!this.getPreReleaseTag().isEmpty()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int length() {
		int length = 0;
		VersionPart _major = this.getMajor();
		boolean _tripleNotEquals = (_major != null);
		if (_tripleNotEquals) {
			length++;
		}
		VersionPart _minor = this.getMinor();
		boolean _tripleNotEquals_1 = (_minor != null);
		if (_tripleNotEquals_1) {
			length++;
		}
		VersionPart _patch = this.getPatch();
		boolean _tripleNotEquals_2 = (_patch != null);
		if (_tripleNotEquals_2) {
			length++;
			int _length = length;
			int _length_1 = ((Object[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(this.getExtended(), Object.class)).length;
			length = (_length + _length_1);
		}
		return length;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionPart getPart(final int idx) {
		switch (idx) {
			case 0:
				return this.getMajor();
			case 1:
				return this.getMinor();
			case 2:
				return this.getPatch();
			default:
				return this.getExtended().get((idx - 3));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean equals(final Object obj) {
		if ((!(obj instanceof VersionNumber))) {
			return false;
		}
		final VersionNumber vn = ((VersionNumber) obj);
		boolean equals = true;
		int _length = this.length();
		int _length_1 = vn.length();
		boolean _equals = (_length == _length_1);
		equals = _equals;
		for (int i = 0; (i < this.length()); i++) {
			equals = (equals && this.getPart(i).equals(vn.getPart(i)));
		}
		equals = (equals && (Boolean.valueOf((this.getQualifier() == null)) == Boolean.valueOf((vn.getQualifier() == null))));
		equals = (equals && ((this.getQualifier() == null) || this.getQualifier().equals(vn.getQualifier())));
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
			case SemverPackage.VERSION_NUMBER__MAJOR:
				return basicSetMajor(null, msgs);
			case SemverPackage.VERSION_NUMBER__MINOR:
				return basicSetMinor(null, msgs);
			case SemverPackage.VERSION_NUMBER__PATCH:
				return basicSetPatch(null, msgs);
			case SemverPackage.VERSION_NUMBER__EXTENDED:
				return ((InternalEList<?>)getExtended()).basicRemove(otherEnd, msgs);
			case SemverPackage.VERSION_NUMBER__QUALIFIER:
				return basicSetQualifier(null, msgs);
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
			case SemverPackage.VERSION_NUMBER__MAJOR:
				return getMajor();
			case SemverPackage.VERSION_NUMBER__MINOR:
				return getMinor();
			case SemverPackage.VERSION_NUMBER__PATCH:
				return getPatch();
			case SemverPackage.VERSION_NUMBER__EXTENDED:
				return getExtended();
			case SemverPackage.VERSION_NUMBER__QUALIFIER:
				return getQualifier();
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
			case SemverPackage.VERSION_NUMBER__MAJOR:
				setMajor((VersionPart)newValue);
				return;
			case SemverPackage.VERSION_NUMBER__MINOR:
				setMinor((VersionPart)newValue);
				return;
			case SemverPackage.VERSION_NUMBER__PATCH:
				setPatch((VersionPart)newValue);
				return;
			case SemverPackage.VERSION_NUMBER__EXTENDED:
				getExtended().clear();
				getExtended().addAll((Collection<? extends VersionPart>)newValue);
				return;
			case SemverPackage.VERSION_NUMBER__QUALIFIER:
				setQualifier((Qualifier)newValue);
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
			case SemverPackage.VERSION_NUMBER__MAJOR:
				setMajor((VersionPart)null);
				return;
			case SemverPackage.VERSION_NUMBER__MINOR:
				setMinor((VersionPart)null);
				return;
			case SemverPackage.VERSION_NUMBER__PATCH:
				setPatch((VersionPart)null);
				return;
			case SemverPackage.VERSION_NUMBER__EXTENDED:
				getExtended().clear();
				return;
			case SemverPackage.VERSION_NUMBER__QUALIFIER:
				setQualifier((Qualifier)null);
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
			case SemverPackage.VERSION_NUMBER__MAJOR:
				return major != null;
			case SemverPackage.VERSION_NUMBER__MINOR:
				return minor != null;
			case SemverPackage.VERSION_NUMBER__PATCH:
				return patch != null;
			case SemverPackage.VERSION_NUMBER__EXTENDED:
				return extended != null && !extended.isEmpty();
			case SemverPackage.VERSION_NUMBER__QUALIFIER:
				return qualifier != null;
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
			case SemverPackage.VERSION_NUMBER___IS_WILDCARD:
				return isWildcard();
			case SemverPackage.VERSION_NUMBER___GET_PRE_RELEASE_TAG:
				return getPreReleaseTag();
			case SemverPackage.VERSION_NUMBER___HAS_PRE_RELEASE_TAG:
				return hasPreReleaseTag();
			case SemverPackage.VERSION_NUMBER___LENGTH:
				return length();
			case SemverPackage.VERSION_NUMBER___GET_PART__INT:
				return getPart((Integer)arguments.get(0));
			case SemverPackage.VERSION_NUMBER___EQUALS__OBJECT:
				return equals(arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //VersionNumberImpl
