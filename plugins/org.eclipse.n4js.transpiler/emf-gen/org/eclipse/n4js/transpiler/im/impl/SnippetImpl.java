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
package org.eclipse.n4js.transpiler.im.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.impl.ExpressionImpl;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.Snippet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Snippet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.SnippetImpl#getCodeToEmit <em>Code To Emit</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SnippetImpl extends ExpressionImpl implements Snippet {
	/**
	 * The default value of the '{@link #getCodeToEmit() <em>Code To Emit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeToEmit()
	 * @generated
	 * @ordered
	 */
	protected static final String CODE_TO_EMIT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCodeToEmit() <em>Code To Emit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodeToEmit()
	 * @generated
	 * @ordered
	 */
	protected String codeToEmit = CODE_TO_EMIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SnippetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.SNIPPET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCodeToEmit() {
		return codeToEmit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCodeToEmit(String newCodeToEmit) {
		String oldCodeToEmit = codeToEmit;
		codeToEmit = newCodeToEmit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.SNIPPET__CODE_TO_EMIT, oldCodeToEmit, codeToEmit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImPackage.SNIPPET__CODE_TO_EMIT:
				return getCodeToEmit();
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
			case ImPackage.SNIPPET__CODE_TO_EMIT:
				setCodeToEmit((String)newValue);
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
			case ImPackage.SNIPPET__CODE_TO_EMIT:
				setCodeToEmit(CODE_TO_EMIT_EDEFAULT);
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
			case ImPackage.SNIPPET__CODE_TO_EMIT:
				return CODE_TO_EMIT_EDEFAULT == null ? codeToEmit != null : !CODE_TO_EMIT_EDEFAULT.equals(codeToEmit);
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
		result.append(" (codeToEmit: ");
		result.append(codeToEmit);
		result.append(')');
		return result.toString();
	}

} //SnippetImpl
