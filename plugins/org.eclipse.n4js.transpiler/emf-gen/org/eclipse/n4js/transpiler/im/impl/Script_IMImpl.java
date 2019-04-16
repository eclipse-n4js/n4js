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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.impl.ScriptImpl;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.im.SymbolTable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Script IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.Script_IMImpl#getSymbolTable <em>Symbol Table</em>}</li>
 * </ul>
 *
 * @generated
 */
public class Script_IMImpl extends ScriptImpl implements Script_IM {
	/**
	 * The cached value of the '{@link #getSymbolTable() <em>Symbol Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolTable()
	 * @generated
	 * @ordered
	 */
	protected SymbolTable symbolTable;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Script_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.SCRIPT_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymbolTable(SymbolTable newSymbolTable, NotificationChain msgs) {
		SymbolTable oldSymbolTable = symbolTable;
		symbolTable = newSymbolTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ImPackage.SCRIPT_IM__SYMBOL_TABLE, oldSymbolTable, newSymbolTable);
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
	public void setSymbolTable(SymbolTable newSymbolTable) {
		if (newSymbolTable != symbolTable) {
			NotificationChain msgs = null;
			if (symbolTable != null)
				msgs = ((InternalEObject)symbolTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ImPackage.SCRIPT_IM__SYMBOL_TABLE, null, msgs);
			if (newSymbolTable != null)
				msgs = ((InternalEObject)newSymbolTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ImPackage.SCRIPT_IM__SYMBOL_TABLE, null, msgs);
			msgs = basicSetSymbolTable(newSymbolTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.SCRIPT_IM__SYMBOL_TABLE, newSymbolTable, newSymbolTable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.SCRIPT_IM__SYMBOL_TABLE:
				return basicSetSymbolTable(null, msgs);
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
			case ImPackage.SCRIPT_IM__SYMBOL_TABLE:
				return getSymbolTable();
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
			case ImPackage.SCRIPT_IM__SYMBOL_TABLE:
				setSymbolTable((SymbolTable)newValue);
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
			case ImPackage.SCRIPT_IM__SYMBOL_TABLE:
				setSymbolTable((SymbolTable)null);
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
			case ImPackage.SCRIPT_IM__SYMBOL_TABLE:
				return symbolTable != null;
		}
		return super.eIsSet(featureID);
	}

} //Script_IMImpl
