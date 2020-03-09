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

import org.eclipse.n4js.ts.types.RuntimeDependency;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Runtime Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.RuntimeDependencyImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.RuntimeDependencyImpl#isLoadtimeForInheritance <em>Loadtime For Inheritance</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuntimeDependencyImpl extends ProxyResolvingEObjectImpl implements RuntimeDependency {
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected TModule target;

	/**
	 * The default value of the '{@link #isLoadtimeForInheritance() <em>Loadtime For Inheritance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadtimeForInheritance()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOADTIME_FOR_INHERITANCE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoadtimeForInheritance() <em>Loadtime For Inheritance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadtimeForInheritance()
	 * @generated
	 * @ordered
	 */
	protected boolean loadtimeForInheritance = LOADTIME_FOR_INHERITANCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuntimeDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.RUNTIME_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TModule getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (TModule)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.RUNTIME_DEPENDENCY__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TModule basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(TModule newTarget) {
		TModule oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.RUNTIME_DEPENDENCY__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isLoadtimeForInheritance() {
		return loadtimeForInheritance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLoadtimeForInheritance(boolean newLoadtimeForInheritance) {
		boolean oldLoadtimeForInheritance = loadtimeForInheritance;
		loadtimeForInheritance = newLoadtimeForInheritance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.RUNTIME_DEPENDENCY__LOADTIME_FOR_INHERITANCE, oldLoadtimeForInheritance, loadtimeForInheritance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.RUNTIME_DEPENDENCY__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case TypesPackage.RUNTIME_DEPENDENCY__LOADTIME_FOR_INHERITANCE:
				return isLoadtimeForInheritance();
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
			case TypesPackage.RUNTIME_DEPENDENCY__TARGET:
				setTarget((TModule)newValue);
				return;
			case TypesPackage.RUNTIME_DEPENDENCY__LOADTIME_FOR_INHERITANCE:
				setLoadtimeForInheritance((Boolean)newValue);
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
			case TypesPackage.RUNTIME_DEPENDENCY__TARGET:
				setTarget((TModule)null);
				return;
			case TypesPackage.RUNTIME_DEPENDENCY__LOADTIME_FOR_INHERITANCE:
				setLoadtimeForInheritance(LOADTIME_FOR_INHERITANCE_EDEFAULT);
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
			case TypesPackage.RUNTIME_DEPENDENCY__TARGET:
				return target != null;
			case TypesPackage.RUNTIME_DEPENDENCY__LOADTIME_FOR_INHERITANCE:
				return loadtimeForInheritance != LOADTIME_FOR_INHERITANCE_EDEFAULT;
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
		result.append(" (loadtimeForInheritance: ");
		result.append(loadtimeForInheritance);
		result.append(')');
		return result.toString();
	}

} //RuntimeDependencyImpl
