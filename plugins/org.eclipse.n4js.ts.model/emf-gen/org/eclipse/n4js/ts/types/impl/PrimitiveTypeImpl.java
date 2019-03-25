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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.ArrayLike;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Primitive Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.PrimitiveTypeImpl#getDeclaredElementType <em>Declared Element Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.PrimitiveTypeImpl#getAssignmentCompatible <em>Assignment Compatible</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.PrimitiveTypeImpl#getAutoboxedType <em>Autoboxed Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PrimitiveTypeImpl extends ContainerTypeImpl<TMember> implements PrimitiveType {
	/**
	 * The cached value of the '{@link #getDeclaredElementType() <em>Declared Element Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredElementType()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredElementType;

	/**
	 * The cached value of the '{@link #getAssignmentCompatible() <em>Assignment Compatible</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignmentCompatible()
	 * @generated
	 * @ordered
	 */
	protected PrimitiveType assignmentCompatible;

	/**
	 * The cached value of the '{@link #getAutoboxedType() <em>Autoboxed Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAutoboxedType()
	 * @generated
	 * @ordered
	 */
	protected TClassifier autoboxedType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrimitiveTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.PRIMITIVE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef getDeclaredElementType() {
		return declaredElementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredElementType(TypeRef newDeclaredElementType, NotificationChain msgs) {
		TypeRef oldDeclaredElementType = declaredElementType;
		declaredElementType = newDeclaredElementType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE, oldDeclaredElementType, newDeclaredElementType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredElementType(TypeRef newDeclaredElementType) {
		if (newDeclaredElementType != declaredElementType) {
			NotificationChain msgs = null;
			if (declaredElementType != null)
				msgs = ((InternalEObject)declaredElementType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE, null, msgs);
			if (newDeclaredElementType != null)
				msgs = ((InternalEObject)newDeclaredElementType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE, null, msgs);
			msgs = basicSetDeclaredElementType(newDeclaredElementType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE, newDeclaredElementType, newDeclaredElementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimitiveType getAssignmentCompatible() {
		if (assignmentCompatible != null && assignmentCompatible.eIsProxy()) {
			InternalEObject oldAssignmentCompatible = (InternalEObject)assignmentCompatible;
			assignmentCompatible = (PrimitiveType)eResolveProxy(oldAssignmentCompatible);
			if (assignmentCompatible != oldAssignmentCompatible) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.PRIMITIVE_TYPE__ASSIGNMENT_COMPATIBLE, oldAssignmentCompatible, assignmentCompatible));
			}
		}
		return assignmentCompatible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimitiveType basicGetAssignmentCompatible() {
		return assignmentCompatible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssignmentCompatible(PrimitiveType newAssignmentCompatible) {
		PrimitiveType oldAssignmentCompatible = assignmentCompatible;
		assignmentCompatible = newAssignmentCompatible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.PRIMITIVE_TYPE__ASSIGNMENT_COMPATIBLE, oldAssignmentCompatible, assignmentCompatible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TClassifier getAutoboxedType() {
		if (autoboxedType != null && autoboxedType.eIsProxy()) {
			InternalEObject oldAutoboxedType = (InternalEObject)autoboxedType;
			autoboxedType = (TClassifier)eResolveProxy(oldAutoboxedType);
			if (autoboxedType != oldAutoboxedType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypesPackage.PRIMITIVE_TYPE__AUTOBOXED_TYPE, oldAutoboxedType, autoboxedType));
			}
		}
		return autoboxedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TClassifier basicGetAutoboxedType() {
		return autoboxedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoboxedType(TClassifier newAutoboxedType) {
		TClassifier oldAutoboxedType = autoboxedType;
		autoboxedType = newAutoboxedType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.PRIMITIVE_TYPE__AUTOBOXED_TYPE, oldAutoboxedType, autoboxedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE:
				return basicSetDeclaredElementType(null, msgs);
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
			case TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE:
				return getDeclaredElementType();
			case TypesPackage.PRIMITIVE_TYPE__ASSIGNMENT_COMPATIBLE:
				if (resolve) return getAssignmentCompatible();
				return basicGetAssignmentCompatible();
			case TypesPackage.PRIMITIVE_TYPE__AUTOBOXED_TYPE:
				if (resolve) return getAutoboxedType();
				return basicGetAutoboxedType();
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
			case TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)newValue);
				return;
			case TypesPackage.PRIMITIVE_TYPE__ASSIGNMENT_COMPATIBLE:
				setAssignmentCompatible((PrimitiveType)newValue);
				return;
			case TypesPackage.PRIMITIVE_TYPE__AUTOBOXED_TYPE:
				setAutoboxedType((TClassifier)newValue);
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
			case TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)null);
				return;
			case TypesPackage.PRIMITIVE_TYPE__ASSIGNMENT_COMPATIBLE:
				setAssignmentCompatible((PrimitiveType)null);
				return;
			case TypesPackage.PRIMITIVE_TYPE__AUTOBOXED_TYPE:
				setAutoboxedType((TClassifier)null);
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
			case TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE:
				return declaredElementType != null;
			case TypesPackage.PRIMITIVE_TYPE__ASSIGNMENT_COMPATIBLE:
				return assignmentCompatible != null;
			case TypesPackage.PRIMITIVE_TYPE__AUTOBOXED_TYPE:
				return autoboxedType != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ArrayLike.class) {
			switch (derivedFeatureID) {
				case TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE: return TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ArrayLike.class) {
			switch (baseFeatureID) {
				case TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE: return TypesPackage.PRIMITIVE_TYPE__DECLARED_ELEMENT_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //PrimitiveTypeImpl
