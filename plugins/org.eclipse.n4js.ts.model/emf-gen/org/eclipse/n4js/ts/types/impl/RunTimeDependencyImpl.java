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

import org.eclipse.n4js.ts.types.RunTimeDependency;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Run Time Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.RunTimeDependencyImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.RunTimeDependencyImpl#isLoadTimeForInheritance <em>Load Time For Inheritance</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RunTimeDependencyImpl extends ProxyResolvingEObjectImpl implements RunTimeDependency {
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
	 * The default value of the '{@link #isLoadTimeForInheritance() <em>Load Time For Inheritance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadTimeForInheritance()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOAD_TIME_FOR_INHERITANCE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLoadTimeForInheritance() <em>Load Time For Inheritance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLoadTimeForInheritance()
	 * @generated
	 * @ordered
	 */
	protected boolean loadTimeForInheritance = LOAD_TIME_FOR_INHERITANCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RunTimeDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.RUN_TIME_DEPENDENCY;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.RUN_TIME_DEPENDENCY__TARGET, oldTarget, target));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.RUN_TIME_DEPENDENCY__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isLoadTimeForInheritance() {
		return loadTimeForInheritance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLoadTimeForInheritance(boolean newLoadTimeForInheritance) {
		boolean oldLoadTimeForInheritance = loadTimeForInheritance;
		loadTimeForInheritance = newLoadTimeForInheritance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.RUN_TIME_DEPENDENCY__LOAD_TIME_FOR_INHERITANCE, oldLoadTimeForInheritance, loadTimeForInheritance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TypesPackage.RUN_TIME_DEPENDENCY__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case TypesPackage.RUN_TIME_DEPENDENCY__LOAD_TIME_FOR_INHERITANCE:
				return isLoadTimeForInheritance();
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
			case TypesPackage.RUN_TIME_DEPENDENCY__TARGET:
				setTarget((TModule)newValue);
				return;
			case TypesPackage.RUN_TIME_DEPENDENCY__LOAD_TIME_FOR_INHERITANCE:
				setLoadTimeForInheritance((Boolean)newValue);
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
			case TypesPackage.RUN_TIME_DEPENDENCY__TARGET:
				setTarget((TModule)null);
				return;
			case TypesPackage.RUN_TIME_DEPENDENCY__LOAD_TIME_FOR_INHERITANCE:
				setLoadTimeForInheritance(LOAD_TIME_FOR_INHERITANCE_EDEFAULT);
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
			case TypesPackage.RUN_TIME_DEPENDENCY__TARGET:
				return target != null;
			case TypesPackage.RUN_TIME_DEPENDENCY__LOAD_TIME_FOR_INHERITANCE:
				return loadTimeForInheritance != LOAD_TIME_FOR_INHERITANCE_EDEFAULT;
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
		result.append(" (loadTimeForInheritance: ");
		result.append(loadTimeForInheritance);
		result.append(')');
		return result.toString();
	}

} //RunTimeDependencyImpl
