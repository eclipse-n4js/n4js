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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4AbstractNamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4ModuleDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.ScriptElement;

import org.eclipse.n4js.ts.types.TDeclaredModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>N4 Module Declaration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.N4ModuleDeclarationImpl#getDefinedModule <em>Defined Module</em>}</li>
 * </ul>
 *
 * @generated
 */
public class N4ModuleDeclarationImpl extends N4AbstractNamespaceDeclarationImpl implements N4ModuleDeclaration {
	/**
	 * The cached value of the '{@link #getDefinedModule() <em>Defined Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedModule()
	 * @generated
	 * @ordered
	 */
	protected TDeclaredModule definedModule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4ModuleDeclarationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.N4_MODULE_DECLARATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TDeclaredModule getDefinedModule() {
		if (definedModule != null && definedModule.eIsProxy()) {
			InternalEObject oldDefinedModule = (InternalEObject)definedModule;
			definedModule = (TDeclaredModule)eResolveProxy(oldDefinedModule);
			if (definedModule != oldDefinedModule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE, oldDefinedModule, definedModule));
			}
		}
		return definedModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TDeclaredModule basicGetDefinedModule() {
		return definedModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedModule(TDeclaredModule newDefinedModule) {
		TDeclaredModule oldDefinedModule = definedModule;
		definedModule = newDefinedModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE, oldDefinedModule, definedModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TDeclaredModule getDefinedNamespace() {
		return this.getDefinedModule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public N4NamespaceDeclaration getNamespace() {
		EObject parent = this.eContainer();
		if ((parent instanceof ExportDeclaration)) {
			parent = ((ExportDeclaration)parent).eContainer();
		}
		if ((parent instanceof N4NamespaceDeclaration)) {
			return ((N4NamespaceDeclaration)parent);
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInNamespace() {
		N4NamespaceDeclaration _namespace = this.getNamespace();
		return (_namespace != null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHollow() {
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
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				if (resolve) return getDefinedModule();
				return basicGetDefinedModule();
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
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				setDefinedModule((TDeclaredModule)newValue);
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
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				setDefinedModule((TDeclaredModule)null);
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
			case N4JSPackage.N4_MODULE_DECLARATION__DEFINED_MODULE:
				return definedModule != null;
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
		if (baseClass == N4AbstractNamespaceDeclaration.class) {
			switch (baseOperationID) {
				case N4JSPackage.N4_ABSTRACT_NAMESPACE_DECLARATION___GET_DEFINED_NAMESPACE: return N4JSPackage.N4_MODULE_DECLARATION___GET_DEFINED_NAMESPACE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ScriptElement.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == NamespaceElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMESPACE_ELEMENT___GET_NAMESPACE: return N4JSPackage.N4_MODULE_DECLARATION___GET_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_IN_NAMESPACE: return N4JSPackage.N4_MODULE_DECLARATION___IS_IN_NAMESPACE;
				case N4JSPackage.NAMESPACE_ELEMENT___IS_HOLLOW: return N4JSPackage.N4_MODULE_DECLARATION___IS_HOLLOW;
				default: return -1;
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
			case N4JSPackage.N4_MODULE_DECLARATION___GET_DEFINED_NAMESPACE:
				return getDefinedNamespace();
			case N4JSPackage.N4_MODULE_DECLARATION___GET_NAMESPACE:
				return getNamespace();
			case N4JSPackage.N4_MODULE_DECLARATION___IS_IN_NAMESPACE:
				return isInNamespace();
			case N4JSPackage.N4_MODULE_DECLARATION___IS_HOLLOW:
				return isHollow();
		}
		return super.eInvoke(operationID, arguments);
	}

} //N4ModuleDeclarationImpl
