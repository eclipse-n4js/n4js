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

import org.eclipse.n4js.n4JS.ModuleRef;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.n4JS.N4JSPackage.Literals;

import org.eclipse.n4js.ts.types.TModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ModuleRefImpl#getModule <em>Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ModuleRefImpl#getModuleSpecifierAsText <em>Module Specifier As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ModuleRefImpl#getModuleSpecifierForm <em>Module Specifier Form</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ModuleRefImpl extends MemberAccessImpl implements ModuleRef {
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
	protected ModuleRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.MODULE_REF;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.MODULE_REF__MODULE, oldModule, module));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.MODULE_REF__MODULE, oldModule, module));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.MODULE_REF__MODULE_SPECIFIER_AS_TEXT, oldModuleSpecifierAsText, moduleSpecifierAsText));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.MODULE_REF__MODULE_SPECIFIER_FORM, oldModuleSpecifierForm, moduleSpecifierForm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReferringToOtherModule() {
		Object _eGet = this.eGet(Literals.MODULE_REF__MODULE, false);
		return (_eGet != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRetainedAtRuntime() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case N4JSPackage.MODULE_REF__MODULE:
				if (resolve) return getModule();
				return basicGetModule();
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_AS_TEXT:
				return getModuleSpecifierAsText();
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_FORM:
				return getModuleSpecifierForm();
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
			case N4JSPackage.MODULE_REF__MODULE:
				setModule((TModule)newValue);
				return;
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_AS_TEXT:
				setModuleSpecifierAsText((String)newValue);
				return;
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_FORM:
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
			case N4JSPackage.MODULE_REF__MODULE:
				setModule((TModule)null);
				return;
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_AS_TEXT:
				setModuleSpecifierAsText(MODULE_SPECIFIER_AS_TEXT_EDEFAULT);
				return;
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_FORM:
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
			case N4JSPackage.MODULE_REF__MODULE:
				return module != null;
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_AS_TEXT:
				return MODULE_SPECIFIER_AS_TEXT_EDEFAULT == null ? moduleSpecifierAsText != null : !MODULE_SPECIFIER_AS_TEXT_EDEFAULT.equals(moduleSpecifierAsText);
			case N4JSPackage.MODULE_REF__MODULE_SPECIFIER_FORM:
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
			case N4JSPackage.MODULE_REF___IS_REFERRING_TO_OTHER_MODULE:
				return isReferringToOtherModule();
			case N4JSPackage.MODULE_REF___IS_RETAINED_AT_RUNTIME:
				return isRetainedAtRuntime();
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
		result.append(" (moduleSpecifierAsText: ");
		result.append(moduleSpecifierAsText);
		result.append(", moduleSpecifierForm: ");
		result.append(moduleSpecifierForm);
		result.append(')');
		return result.toString();
	}

} //ModuleRefImpl
