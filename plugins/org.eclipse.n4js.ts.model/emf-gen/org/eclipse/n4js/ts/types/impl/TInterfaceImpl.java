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

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;

import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TInterface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TInterfaceImpl#isExternal <em>External</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TInterfaceImpl#getSuperInterfaceRefs <em>Super Interface Refs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TInterfaceImpl extends TN4ClassifierImpl implements TInterface {
	/**
	 * The default value of the '{@link #isExternal() <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExternal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXTERNAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExternal() <em>External</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExternal()
	 * @generated
	 * @ordered
	 */
	protected boolean external = EXTERNAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSuperInterfaceRefs() <em>Super Interface Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperInterfaceRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<ParameterizedTypeRef> superInterfaceRefs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TInterfaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TINTERFACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExternal() {
		return external;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExternal(boolean newExternal) {
		boolean oldExternal = external;
		external = newExternal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TINTERFACE__EXTERNAL, oldExternal, external));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ParameterizedTypeRef> getSuperInterfaceRefs() {
		if (superInterfaceRefs == null) {
			superInterfaceRefs = new EObjectContainmentEList<ParameterizedTypeRef>(ParameterizedTypeRef.class, this, TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS);
		}
		return superInterfaceRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<ParameterizedTypeRef> getSuperClassifierRefs() {
		return this.getSuperInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Iterable<ParameterizedTypeRef> getImplementedOrExtendedInterfaceRefs() {
		return this.getSuperInterfaceRefs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				return ((InternalEList<?>)getSuperInterfaceRefs()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TINTERFACE__EXTERNAL:
				return isExternal();
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				return getSuperInterfaceRefs();
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
			case TypesPackage.TINTERFACE__EXTERNAL:
				setExternal((Boolean)newValue);
				return;
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				getSuperInterfaceRefs().clear();
				getSuperInterfaceRefs().addAll((Collection<? extends ParameterizedTypeRef>)newValue);
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
			case TypesPackage.TINTERFACE__EXTERNAL:
				setExternal(EXTERNAL_EDEFAULT);
				return;
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				getSuperInterfaceRefs().clear();
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
			case TypesPackage.TINTERFACE__EXTERNAL:
				return external != EXTERNAL_EDEFAULT;
			case TypesPackage.TINTERFACE__SUPER_INTERFACE_REFS:
				return superInterfaceRefs != null && !superInterfaceRefs.isEmpty();
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
		if (baseClass == TClassifier.class) {
			switch (baseOperationID) {
				case TypesPackage.TCLASSIFIER___IS_ABSTRACT: return TypesPackage.TINTERFACE___IS_ABSTRACT;
				case TypesPackage.TCLASSIFIER___GET_SUPER_CLASSIFIER_REFS: return TypesPackage.TINTERFACE___GET_SUPER_CLASSIFIER_REFS;
				case TypesPackage.TCLASSIFIER___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS: return TypesPackage.TINTERFACE___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;
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
			case TypesPackage.TINTERFACE___IS_ABSTRACT:
				return isAbstract();
			case TypesPackage.TINTERFACE___GET_SUPER_CLASSIFIER_REFS:
				return getSuperClassifierRefs();
			case TypesPackage.TINTERFACE___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS:
				return getImplementedOrExtendedInterfaceRefs();
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
		result.append(" (external: ");
		result.append(external);
		result.append(')');
		return result.toString();
	}

} //TInterfaceImpl
