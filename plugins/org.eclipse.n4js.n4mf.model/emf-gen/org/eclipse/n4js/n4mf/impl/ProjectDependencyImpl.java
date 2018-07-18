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
package org.eclipse.n4js.n4mf.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDependencyScope;
import org.eclipse.n4js.n4mf.ProjectReference;

import org.eclipse.n4js.semver.SEMVER.VersionRangeSetRequirement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl#getVersionConstraint <em>Version Constraint</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl#getVersionConstraintString <em>Version Constraint String</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl#getDeclaredScope <em>Declared Scope</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectDependencyImpl extends ProjectReferenceImpl implements ProjectDependency {
	/**
	 * The cached value of the '{@link #getVersionConstraint() <em>Version Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionConstraint()
	 * @generated
	 * @ordered
	 */
	protected VersionRangeSetRequirement versionConstraint;

	/**
	 * The default value of the '{@link #getVersionConstraintString() <em>Version Constraint String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionConstraintString()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_CONSTRAINT_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersionConstraintString() <em>Version Constraint String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersionConstraintString()
	 * @generated
	 * @ordered
	 */
	protected String versionConstraintString = VERSION_CONSTRAINT_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getDeclaredScope() <em>Declared Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredScope()
	 * @generated
	 * @ordered
	 */
	protected static final ProjectDependencyScope DECLARED_SCOPE_EDEFAULT = ProjectDependencyScope.COMPILE;

	/**
	 * The cached value of the '{@link #getDeclaredScope() <em>Declared Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredScope()
	 * @generated
	 * @ordered
	 */
	protected ProjectDependencyScope declaredScope = DECLARED_SCOPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4mfPackage.Literals.PROJECT_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionRangeSetRequirement getVersionConstraint() {
		return versionConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVersionConstraint(VersionRangeSetRequirement newVersionConstraint, NotificationChain msgs) {
		VersionRangeSetRequirement oldVersionConstraint = versionConstraint;
		versionConstraint = newVersionConstraint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT, oldVersionConstraint, newVersionConstraint);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersionConstraint(VersionRangeSetRequirement newVersionConstraint) {
		if (newVersionConstraint != versionConstraint) {
			NotificationChain msgs = null;
			if (versionConstraint != null)
				msgs = ((InternalEObject)versionConstraint).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT, null, msgs);
			if (newVersionConstraint != null)
				msgs = ((InternalEObject)newVersionConstraint).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT, null, msgs);
			msgs = basicSetVersionConstraint(newVersionConstraint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT, newVersionConstraint, newVersionConstraint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersionConstraintString() {
		return versionConstraintString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersionConstraintString(String newVersionConstraintString) {
		String oldVersionConstraintString = versionConstraintString;
		versionConstraintString = newVersionConstraintString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT_STRING, oldVersionConstraintString, versionConstraintString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDependencyScope getDeclaredScope() {
		return declaredScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredScope(ProjectDependencyScope newDeclaredScope) {
		ProjectDependencyScope oldDeclaredScope = declaredScope;
		declaredScope = newDeclaredScope == null ? DECLARED_SCOPE_EDEFAULT : newDeclaredScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DEPENDENCY__DECLARED_SCOPE, oldDeclaredScope, declaredScope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDependencyScope getScope() {
		ProjectDependencyScope _elvis = null;
		ProjectDependencyScope _declaredScope = this.getDeclaredScope();
		if (_declaredScope != null) {
			_elvis = _declaredScope;
		} else {
			_elvis = ProjectDependencyScope.COMPILE;
		}
		return _elvis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT:
				return basicSetVersionConstraint(null, msgs);
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT:
				return getVersionConstraint();
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT_STRING:
				return getVersionConstraintString();
			case N4mfPackage.PROJECT_DEPENDENCY__DECLARED_SCOPE:
				return getDeclaredScope();
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT:
				setVersionConstraint((VersionRangeSetRequirement)newValue);
				return;
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT_STRING:
				setVersionConstraintString((String)newValue);
				return;
			case N4mfPackage.PROJECT_DEPENDENCY__DECLARED_SCOPE:
				setDeclaredScope((ProjectDependencyScope)newValue);
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT:
				setVersionConstraint((VersionRangeSetRequirement)null);
				return;
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT_STRING:
				setVersionConstraintString(VERSION_CONSTRAINT_STRING_EDEFAULT);
				return;
			case N4mfPackage.PROJECT_DEPENDENCY__DECLARED_SCOPE:
				setDeclaredScope(DECLARED_SCOPE_EDEFAULT);
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
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT:
				return versionConstraint != null;
			case N4mfPackage.PROJECT_DEPENDENCY__VERSION_CONSTRAINT_STRING:
				return VERSION_CONSTRAINT_STRING_EDEFAULT == null ? versionConstraintString != null : !VERSION_CONSTRAINT_STRING_EDEFAULT.equals(versionConstraintString);
			case N4mfPackage.PROJECT_DEPENDENCY__DECLARED_SCOPE:
				return declaredScope != DECLARED_SCOPE_EDEFAULT;
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
		if (baseClass == ProjectReference.class) {
			switch (baseOperationID) {
				case N4mfPackage.PROJECT_REFERENCE___GET_SCOPE: return N4mfPackage.PROJECT_DEPENDENCY___GET_SCOPE;
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
			case N4mfPackage.PROJECT_DEPENDENCY___GET_SCOPE:
				return getScope();
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (versionConstraintString: ");
		result.append(versionConstraintString);
		result.append(", declaredScope: ");
		result.append(declaredScope);
		result.append(')');
		return result.toString();
	}

} //ProjectDependencyImpl
