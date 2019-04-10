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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.semver.Semver.SemverPackage;
import org.eclipse.n4js.semver.Semver.URLCommitISH;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>URL Commit ISH</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.URLCommitISHImpl#getCommitISH <em>Commit ISH</em>}</li>
 * </ul>
 *
 * @generated
 */
public class URLCommitISHImpl extends URLVersionSpecifierImpl implements URLCommitISH {
	/**
	 * The default value of the '{@link #getCommitISH() <em>Commit ISH</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitISH()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMIT_ISH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommitISH() <em>Commit ISH</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitISH()
	 * @generated
	 * @ordered
	 */
	protected String commitISH = COMMIT_ISH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected URLCommitISHImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.URL_COMMIT_ISH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCommitISH() {
		return commitISH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCommitISH(String newCommitISH) {
		String oldCommitISH = commitISH;
		commitISH = newCommitISH;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.URL_COMMIT_ISH__COMMIT_ISH, oldCommitISH, commitISH));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SemverPackage.URL_COMMIT_ISH__COMMIT_ISH:
				return getCommitISH();
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
			case SemverPackage.URL_COMMIT_ISH__COMMIT_ISH:
				setCommitISH((String)newValue);
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
			case SemverPackage.URL_COMMIT_ISH__COMMIT_ISH:
				setCommitISH(COMMIT_ISH_EDEFAULT);
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
			case SemverPackage.URL_COMMIT_ISH__COMMIT_ISH:
				return COMMIT_ISH_EDEFAULT == null ? commitISH != null : !COMMIT_ISH_EDEFAULT.equals(commitISH);
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (commitISH: ");
		result.append(commitISH);
		result.append(')');
		return result.toString();
	}

} //URLCommitISHImpl
