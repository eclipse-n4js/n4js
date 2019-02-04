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

import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.URLSemver;
import org.eclipse.n4js.semver.Semver.URLVersionRequirement;
import org.eclipse.n4js.semver.Semver.URLVersionSpecifier;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>URL Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.URLVersionRequirementImpl#getVersionSpecifier <em>Version Specifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.URLVersionRequirementImpl#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.URLVersionRequirementImpl#getUrl <em>Url</em>}</li>
 * </ul>
 *
 * @generated
 */
public class URLVersionRequirementImpl extends NPMVersionRequirementImpl implements URLVersionRequirement {
	/**
	 * The cached value of the '{@link #getVersionSpecifier() <em>Version Specifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionSpecifier()
	 * @generated
	 * @ordered
	 */
	protected URLVersionSpecifier versionSpecifier;

	/**
	 * The default value of the '{@link #getProtocol() <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
	protected static final String PROTOCOL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProtocol() <em>Protocol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocol()
	 * @generated
	 * @ordered
	 */
	protected String protocol = PROTOCOL_EDEFAULT;

	/**
	 * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected String url = URL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected URLVersionRequirementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.URL_VERSION_REQUIREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public URLVersionSpecifier getVersionSpecifier() {
		return versionSpecifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVersionSpecifier(URLVersionSpecifier newVersionSpecifier, NotificationChain msgs) {
		URLVersionSpecifier oldVersionSpecifier = versionSpecifier;
		versionSpecifier = newVersionSpecifier;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER, oldVersionSpecifier, newVersionSpecifier);
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
	public void setVersionSpecifier(URLVersionSpecifier newVersionSpecifier) {
		if (newVersionSpecifier != versionSpecifier) {
			NotificationChain msgs = null;
			if (versionSpecifier != null)
				msgs = ((InternalEObject)versionSpecifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER, null, msgs);
			if (newVersionSpecifier != null)
				msgs = ((InternalEObject)newVersionSpecifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER, null, msgs);
			msgs = basicSetVersionSpecifier(newVersionSpecifier, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER, newVersionSpecifier, newVersionSpecifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProtocol() {
		return protocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProtocol(String newProtocol) {
		String oldProtocol = protocol;
		protocol = newProtocol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.URL_VERSION_REQUIREMENT__PROTOCOL, oldProtocol, protocol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUrl(String newUrl) {
		String oldUrl = url;
		url = newUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.URL_VERSION_REQUIREMENT__URL, oldUrl, url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasSimpleVersion() {
		SimpleVersion _simpleVersion = this.getSimpleVersion();
		return (_simpleVersion != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleVersion getSimpleVersion() {
		URLVersionSpecifier _versionSpecifier = this.getVersionSpecifier();
		if ((_versionSpecifier instanceof URLSemver)) {
			URLVersionSpecifier _versionSpecifier_1 = this.getVersionSpecifier();
			final URLSemver urlSemver = ((URLSemver) _versionSpecifier_1);
			return urlSemver.getSimpleVersion();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER:
				return basicSetVersionSpecifier(null, msgs);
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
			case SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER:
				return getVersionSpecifier();
			case SemverPackage.URL_VERSION_REQUIREMENT__PROTOCOL:
				return getProtocol();
			case SemverPackage.URL_VERSION_REQUIREMENT__URL:
				return getUrl();
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
			case SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER:
				setVersionSpecifier((URLVersionSpecifier)newValue);
				return;
			case SemverPackage.URL_VERSION_REQUIREMENT__PROTOCOL:
				setProtocol((String)newValue);
				return;
			case SemverPackage.URL_VERSION_REQUIREMENT__URL:
				setUrl((String)newValue);
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
			case SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER:
				setVersionSpecifier((URLVersionSpecifier)null);
				return;
			case SemverPackage.URL_VERSION_REQUIREMENT__PROTOCOL:
				setProtocol(PROTOCOL_EDEFAULT);
				return;
			case SemverPackage.URL_VERSION_REQUIREMENT__URL:
				setUrl(URL_EDEFAULT);
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
			case SemverPackage.URL_VERSION_REQUIREMENT__VERSION_SPECIFIER:
				return versionSpecifier != null;
			case SemverPackage.URL_VERSION_REQUIREMENT__PROTOCOL:
				return PROTOCOL_EDEFAULT == null ? protocol != null : !PROTOCOL_EDEFAULT.equals(protocol);
			case SemverPackage.URL_VERSION_REQUIREMENT__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
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
			case SemverPackage.URL_VERSION_REQUIREMENT___HAS_SIMPLE_VERSION:
				return hasSimpleVersion();
			case SemverPackage.URL_VERSION_REQUIREMENT___GET_SIMPLE_VERSION:
				return getSimpleVersion();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (protocol: ");
		result.append(protocol);
		result.append(", url: ");
		result.append(url);
		result.append(')');
		return result.toString();
	}

} //URLVersionRequirementImpl
