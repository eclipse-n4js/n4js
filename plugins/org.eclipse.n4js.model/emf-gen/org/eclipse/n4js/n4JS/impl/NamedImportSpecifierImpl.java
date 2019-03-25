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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;

import org.eclipse.n4js.ts.types.TExportableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Import Specifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl#getImportedElement <em>Imported Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl#getImportedElementAsText <em>Imported Element As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl#getAlias <em>Alias</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NamedImportSpecifierImpl extends ImportSpecifierImpl implements NamedImportSpecifier {
	/**
	 * The cached value of the '{@link #getImportedElement() <em>Imported Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedElement()
	 * @generated
	 * @ordered
	 */
	protected TExportableElement importedElement;

	/**
	 * The default value of the '{@link #getImportedElementAsText() <em>Imported Element As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedElementAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPORTED_ELEMENT_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImportedElementAsText() <em>Imported Element As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportedElementAsText()
	 * @generated
	 * @ordered
	 */
	protected String importedElementAsText = IMPORTED_ELEMENT_AS_TEXT_EDEFAULT;

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
	protected NamedImportSpecifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TExportableElement getImportedElement() {
		if (importedElement != null && importedElement.eIsProxy()) {
			InternalEObject oldImportedElement = (InternalEObject)importedElement;
			importedElement = (TExportableElement)eResolveProxy(oldImportedElement);
			if (importedElement != oldImportedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT, oldImportedElement, importedElement));
			}
		}
		return importedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TExportableElement basicGetImportedElement() {
		return importedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportedElement(TExportableElement newImportedElement) {
		TExportableElement oldImportedElement = importedElement;
		importedElement = newImportedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT, oldImportedElement, importedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImportedElementAsText() {
		return importedElementAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImportedElementAsText(String newImportedElementAsText) {
		String oldImportedElementAsText = importedElementAsText;
		importedElementAsText = newImportedElementAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT, oldImportedElementAsText, importedElementAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.NAMED_IMPORT_SPECIFIER__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDefaultImport() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT:
				if (resolve) return getImportedElement();
				return basicGetImportedElement();
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT:
				return getImportedElementAsText();
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__ALIAS:
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
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT:
				setImportedElement((TExportableElement)newValue);
				return;
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT:
				setImportedElementAsText((String)newValue);
				return;
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__ALIAS:
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
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT:
				setImportedElement((TExportableElement)null);
				return;
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT:
				setImportedElementAsText(IMPORTED_ELEMENT_AS_TEXT_EDEFAULT);
				return;
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__ALIAS:
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
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT:
				return importedElement != null;
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT:
				return IMPORTED_ELEMENT_AS_TEXT_EDEFAULT == null ? importedElementAsText != null : !IMPORTED_ELEMENT_AS_TEXT_EDEFAULT.equals(importedElementAsText);
			case N4JSPackage.NAMED_IMPORT_SPECIFIER__ALIAS:
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.NAMED_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT:
				return isDefaultImport();
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
		result.append(" (importedElementAsText: ");
		result.append(importedElementAsText);
		result.append(", alias: ");
		result.append(alias);
		result.append(')');
		return result.toString();
	}

} //NamedImportSpecifierImpl
