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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Export Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ElementExportDefinitionImpl#getExportedName <em>Exported Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ElementExportDefinitionImpl#isPolyfill <em>Polyfill</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ElementExportDefinitionImpl#getExportedElement <em>Exported Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementExportDefinitionImpl extends ExportDefinitionImpl implements ElementExportDefinition {
	/**
	 * The default value of the '{@link #getExportedName() <em>Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedName()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPORTED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExportedName() <em>Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedName()
	 * @generated
	 * @ordered
	 */
	protected String exportedName = EXPORTED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isPolyfill() <em>Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPolyfill()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POLYFILL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPolyfill() <em>Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPolyfill()
	 * @generated
	 * @ordered
	 */
	protected boolean polyfill = POLYFILL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExportedElement() <em>Exported Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExportedElement()
	 * @generated
	 * @ordered
	 */
	protected TExportableElement exportedElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementExportDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.ELEMENT_EXPORT_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExportedName() {
		return exportedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExportedName(String newExportedName) {
		String oldExportedName = exportedName;
		exportedName = newExportedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_NAME, oldExportedName, exportedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPolyfill() {
		return polyfill;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPolyfill(boolean newPolyfill) {
		boolean oldPolyfill = polyfill;
		polyfill = newPolyfill;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ELEMENT_EXPORT_DEFINITION__POLYFILL, oldPolyfill, polyfill));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TExportableElement getExportedElement() {
		if (exportedElement != null && exportedElement.eIsProxy()) {
			InternalEObject oldExportedElement = (InternalEObject)exportedElement;
			exportedElement = (TExportableElement)eResolveProxy(oldExportedElement);
			if (exportedElement != oldExportedElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT, oldExportedElement, exportedElement));
			}
		}
		return exportedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TExportableElement basicGetExportedElement() {
		return exportedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExportedElement(TExportableElement newExportedElement) {
		TExportableElement oldExportedElement = exportedElement;
		exportedElement = newExportedElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT, oldExportedElement, exportedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_NAME:
				return getExportedName();
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__POLYFILL:
				return isPolyfill();
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				if (resolve) return getExportedElement();
				return basicGetExportedElement();
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
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_NAME:
				setExportedName((String)newValue);
				return;
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__POLYFILL:
				setPolyfill((Boolean)newValue);
				return;
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				setExportedElement((TExportableElement)newValue);
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
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_NAME:
				setExportedName(EXPORTED_NAME_EDEFAULT);
				return;
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__POLYFILL:
				setPolyfill(POLYFILL_EDEFAULT);
				return;
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				setExportedElement((TExportableElement)null);
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
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_NAME:
				return EXPORTED_NAME_EDEFAULT == null ? exportedName != null : !EXPORTED_NAME_EDEFAULT.equals(exportedName);
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__POLYFILL:
				return polyfill != POLYFILL_EDEFAULT;
			case TypesPackage.ELEMENT_EXPORT_DEFINITION__EXPORTED_ELEMENT:
				return exportedElement != null;
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
		result.append(" (exportedName: ");
		result.append(exportedName);
		result.append(", polyfill: ");
		result.append(polyfill);
		result.append(')');
		return result.toString();
	}

} //ElementExportDefinitionImpl
