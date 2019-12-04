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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.types.TModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Import Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl#getImportSpecifiers <em>Import Specifiers</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl#isImportFrom <em>Import From</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl#getModule <em>Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl#getModuleSpecifierAsText <em>Module Specifier As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl#getModuleSpecifierForm <em>Module Specifier Form</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ImportDeclarationImpl extends AnnotableScriptElementImpl implements ImportDeclaration {
	/**
	 * The cached value of the '{@link #getImportSpecifiers() <em>Import Specifiers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImportSpecifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<ImportSpecifier> importSpecifiers;

	/**
	 * The default value of the '{@link #isImportFrom() <em>Import From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isImportFrom()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IMPORT_FROM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isImportFrom() <em>Import From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isImportFrom()
	 * @generated
	 * @ordered
	 */
	protected boolean importFrom = IMPORT_FROM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModule() <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModule()
	 * @generated
	 * @ordered
	 */
	protected TModule module;

	/**
	 * The default value of the '{@link #getModuleSpecifierAsText() <em>Module Specifier As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_SPECIFIER_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModuleSpecifierAsText() <em>Module Specifier As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierAsText()
	 * @generated
	 * @ordered
	 */
	protected String moduleSpecifierAsText = MODULE_SPECIFIER_AS_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getModuleSpecifierForm() <em>Module Specifier Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierForm()
	 * @generated
	 * @ordered
	 */
	protected static final ModuleSpecifierForm MODULE_SPECIFIER_FORM_EDEFAULT = ModuleSpecifierForm.UNKNOWN;

	/**
	 * The cached value of the '{@link #getModuleSpecifierForm() <em>Module Specifier Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifierForm()
	 * @generated
	 * @ordered
	 */
	protected ModuleSpecifierForm moduleSpecifierForm = MODULE_SPECIFIER_FORM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImportDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.IMPORT_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ImportSpecifier> getImportSpecifiers() {
		if (importSpecifiers == null) {
			importSpecifiers = new EObjectContainmentEList<ImportSpecifier>(ImportSpecifier.class, this, N4JSPackage.IMPORT_DECLARATION__IMPORT_SPECIFIERS);
		}
		return importSpecifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isImportFrom() {
		return importFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImportFrom(boolean newImportFrom) {
		boolean oldImportFrom = importFrom;
		importFrom = newImportFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IMPORT_DECLARATION__IMPORT_FROM, oldImportFrom, importFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getModule() {
		if (module != null && module.eIsProxy()) {
			InternalEObject oldModule = (InternalEObject)module;
			module = (TModule)eResolveProxy(oldModule);
			if (module != oldModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.IMPORT_DECLARATION__MODULE, oldModule, module));
			}
		}
		return module;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TModule basicGetModule() {
		return module;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModule(TModule newModule) {
		TModule oldModule = module;
		module = newModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IMPORT_DECLARATION__MODULE, oldModule, module));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModuleSpecifierAsText() {
		return moduleSpecifierAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleSpecifierAsText(String newModuleSpecifierAsText) {
		String oldModuleSpecifierAsText = moduleSpecifierAsText;
		moduleSpecifierAsText = newModuleSpecifierAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT, oldModuleSpecifierAsText, moduleSpecifierAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModuleSpecifierForm getModuleSpecifierForm() {
		return moduleSpecifierForm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModuleSpecifierForm(ModuleSpecifierForm newModuleSpecifierForm) {
		ModuleSpecifierForm oldModuleSpecifierForm = moduleSpecifierForm;
		moduleSpecifierForm = newModuleSpecifierForm == null ? MODULE_SPECIFIER_FORM_EDEFAULT : newModuleSpecifierForm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_FORM, oldModuleSpecifierForm, moduleSpecifierForm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBare() {
		return this.getImportSpecifiers().isEmpty();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_SPECIFIERS:
				return ((InternalEList<?>)getImportSpecifiers()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_SPECIFIERS:
				return getImportSpecifiers();
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_FROM:
				return isImportFrom();
			case N4JSPackage.IMPORT_DECLARATION__MODULE:
				if (resolve) return getModule();
				return basicGetModule();
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				return getModuleSpecifierAsText();
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				return getModuleSpecifierForm();
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
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_SPECIFIERS:
				getImportSpecifiers().clear();
				getImportSpecifiers().addAll((Collection<? extends ImportSpecifier>)newValue);
				return;
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_FROM:
				setImportFrom((Boolean)newValue);
				return;
			case N4JSPackage.IMPORT_DECLARATION__MODULE:
				setModule((TModule)newValue);
				return;
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				setModuleSpecifierAsText((String)newValue);
				return;
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				setModuleSpecifierForm((ModuleSpecifierForm)newValue);
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
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_SPECIFIERS:
				getImportSpecifiers().clear();
				return;
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_FROM:
				setImportFrom(IMPORT_FROM_EDEFAULT);
				return;
			case N4JSPackage.IMPORT_DECLARATION__MODULE:
				setModule((TModule)null);
				return;
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				setModuleSpecifierAsText(MODULE_SPECIFIER_AS_TEXT_EDEFAULT);
				return;
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				setModuleSpecifierForm(MODULE_SPECIFIER_FORM_EDEFAULT);
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
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_SPECIFIERS:
				return importSpecifiers != null && !importSpecifiers.isEmpty();
			case N4JSPackage.IMPORT_DECLARATION__IMPORT_FROM:
				return importFrom != IMPORT_FROM_EDEFAULT;
			case N4JSPackage.IMPORT_DECLARATION__MODULE:
				return module != null;
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT:
				return MODULE_SPECIFIER_AS_TEXT_EDEFAULT == null ? moduleSpecifierAsText != null : !MODULE_SPECIFIER_AS_TEXT_EDEFAULT.equals(moduleSpecifierAsText);
			case N4JSPackage.IMPORT_DECLARATION__MODULE_SPECIFIER_FORM:
				return moduleSpecifierForm != MODULE_SPECIFIER_FORM_EDEFAULT;
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
			case N4JSPackage.IMPORT_DECLARATION___IS_BARE:
				return isBare();
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
		result.append(" (importFrom: ");
		result.append(importFrom);
		result.append(", moduleSpecifierAsText: ");
		result.append(moduleSpecifierAsText);
		result.append(", moduleSpecifierForm: ");
		result.append(moduleSpecifierForm);
		result.append(')');
		return result.toString();
	}

} //ImportDeclarationImpl
