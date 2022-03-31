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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedExportSpecifier;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Export Specifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NamedExportSpecifierImpl#getExportedElement <em>Exported Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NamedExportSpecifierImpl#getAlias <em>Alias</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NamedExportSpecifierImpl extends ProxyResolvingEObjectImpl implements NamedExportSpecifier {
	/**
	 * The cached value of the '{@link #getExportedElement() <em>Exported Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedElement()
	 * @generated
	 * @ordered
	 */
	protected IdentifierRef exportedElement;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NamedExportSpecifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.NAMED_EXPORT_SPECIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifierRef getExportedElement() {
		return exportedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportedElement(IdentifierRef newExportedElement, NotificationChain msgs) {
		IdentifierRef oldExportedElement = exportedElement;
		exportedElement = newExportedElement;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT, oldExportedElement, newExportedElement);
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
	public void setExportedElement(IdentifierRef newExportedElement) {
		if (newExportedElement != exportedElement) {
			NotificationChain msgs = null;
			if (exportedElement != null)
				msgs = ((InternalEObject)exportedElement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT, null, msgs);
			if (newExportedElement != null)
				msgs = ((InternalEObject)newExportedElement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT, null, msgs);
			msgs = basicSetExportedElement(newExportedElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT, newExportedElement, newExportedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.NAMED_EXPORT_SPECIFIER__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT:
				return basicSetExportedElement(null, msgs);
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
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT:
				return getExportedElement();
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__ALIAS:
				return getAlias();
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
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT:
				setExportedElement((IdentifierRef)newValue);
				return;
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__ALIAS:
				setAlias((String)newValue);
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
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT:
				setExportedElement((IdentifierRef)null);
				return;
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__ALIAS:
				setAlias(ALIAS_EDEFAULT);
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
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__EXPORTED_ELEMENT:
				return exportedElement != null;
			case N4JSPackage.NAMED_EXPORT_SPECIFIER__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
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
		result.append(" (alias: ");
		result.append(alias);
		result.append(')');
		return result.toString();
	}

} //NamedExportSpecifierImpl
