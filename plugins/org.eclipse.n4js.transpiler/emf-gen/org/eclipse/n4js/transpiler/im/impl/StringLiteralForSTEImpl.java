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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.StringLiteral;

import org.eclipse.n4js.n4JS.impl.StringLiteralImpl;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.StringLiteralForSTE;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>String Literal For STE</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.StringLiteralForSTEImpl#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.StringLiteralForSTEImpl#isUseExportedName <em>Use Exported Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StringLiteralForSTEImpl extends StringLiteralImpl implements StringLiteralForSTE {
	/**
	 * The cached value of the '{@link #getEntry() <em>Entry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntry()
	 * @generated
	 * @ordered
	 */
	protected SymbolTableEntry entry;

	/**
	 * The default value of the '{@link #isUseExportedName() <em>Use Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseExportedName()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_EXPORTED_NAME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseExportedName() <em>Use Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseExportedName()
	 * @generated
	 * @ordered
	 */
	protected boolean useExportedName = USE_EXPORTED_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StringLiteralForSTEImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.STRING_LITERAL_FOR_STE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolTableEntry getEntry() {
		if (entry != null && entry.eIsProxy()) {
			InternalEObject oldEntry = (InternalEObject)entry;
			entry = (SymbolTableEntry)eResolveProxy(oldEntry);
			if (entry != oldEntry) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.STRING_LITERAL_FOR_STE__ENTRY, oldEntry, entry));
			}
		}
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolTableEntry basicGetEntry() {
		return entry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntry(SymbolTableEntry newEntry) {
		SymbolTableEntry oldEntry = entry;
		entry = newEntry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.STRING_LITERAL_FOR_STE__ENTRY, oldEntry, entry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseExportedName() {
		return useExportedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUseExportedName(boolean newUseExportedName) {
		boolean oldUseExportedName = useExportedName;
		useExportedName = newUseExportedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.STRING_LITERAL_FOR_STE__USE_EXPORTED_NAME, oldUseExportedName, useExportedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValueAsString() {
		boolean _isUseExportedName = this.isUseExportedName();
		if (_isUseExportedName) {
			final SymbolTableEntry e = this.getEntry();
			if ((e instanceof SymbolTableEntryOriginal)) {
				return ((SymbolTableEntryOriginal)e).getExportedName();
			}
		}
		SymbolTableEntry _entry = this.getEntry();
		String _name = null;
		if (_entry!=null) {
			_name=_entry.getName();
		}
		return _name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImPackage.STRING_LITERAL_FOR_STE__ENTRY:
				if (resolve) return getEntry();
				return basicGetEntry();
			case ImPackage.STRING_LITERAL_FOR_STE__USE_EXPORTED_NAME:
				return isUseExportedName();
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
			case ImPackage.STRING_LITERAL_FOR_STE__ENTRY:
				setEntry((SymbolTableEntry)newValue);
				return;
			case ImPackage.STRING_LITERAL_FOR_STE__USE_EXPORTED_NAME:
				setUseExportedName((Boolean)newValue);
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
			case ImPackage.STRING_LITERAL_FOR_STE__ENTRY:
				setEntry((SymbolTableEntry)null);
				return;
			case ImPackage.STRING_LITERAL_FOR_STE__USE_EXPORTED_NAME:
				setUseExportedName(USE_EXPORTED_NAME_EDEFAULT);
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
			case ImPackage.STRING_LITERAL_FOR_STE__ENTRY:
				return entry != null;
			case ImPackage.STRING_LITERAL_FOR_STE__USE_EXPORTED_NAME:
				return useExportedName != USE_EXPORTED_NAME_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == Literal.class) {
			switch (baseOperationID) {
				case N4JSPackage.LITERAL___GET_VALUE_AS_STRING: return ImPackage.STRING_LITERAL_FOR_STE___GET_VALUE_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == StringLiteral.class) {
			switch (baseOperationID) {
				case N4JSPackage.STRING_LITERAL___GET_VALUE_AS_STRING: return ImPackage.STRING_LITERAL_FOR_STE___GET_VALUE_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		return super.eDerivedOperationID(baseOperationID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ImPackage.STRING_LITERAL_FOR_STE___GET_VALUE_AS_STRING:
				return getValueAsString();
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
		result.append(" (useExportedName: ");
		result.append(useExportedName);
		result.append(')');
		return result.toString();
	}

} //StringLiteralForSTEImpl
