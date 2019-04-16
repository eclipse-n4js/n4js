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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.ThisTarget;
import org.eclipse.n4js.n4JS.TypeDefiningElement;

import org.eclipse.n4js.ts.types.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Object Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ObjectLiteralImpl#getDefinedType <em>Defined Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ObjectLiteralImpl#getPropertyAssignments <em>Property Assignments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObjectLiteralImpl extends PrimaryExpressionImpl implements ObjectLiteral {
	/**
	 * The cached value of the '{@link #getDefinedType() <em>Defined Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedType()
	 * @generated
	 * @ordered
	 */
	protected Type definedType;

	/**
	 * The cached value of the '{@link #getPropertyAssignments() <em>Property Assignments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyAssignments()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyAssignment> propertyAssignments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObjectLiteralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.OBJECT_LITERAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type getDefinedType() {
		if (definedType != null && definedType.eIsProxy()) {
			InternalEObject oldDefinedType = (InternalEObject)definedType;
			definedType = (Type)eResolveProxy(oldDefinedType);
			if (definedType != oldDefinedType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE, oldDefinedType, definedType));
			}
		}
		return definedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetDefinedType() {
		return definedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedType(Type newDefinedType) {
		Type oldDefinedType = definedType;
		definedType = newDefinedType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE, oldDefinedType, definedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PropertyAssignment> getPropertyAssignments() {
		if (propertyAssignments == null) {
			propertyAssignments = new EObjectContainmentEList<PropertyAssignment>(PropertyAssignment.class, this, N4JSPackage.OBJECT_LITERAL__PROPERTY_ASSIGNMENTS);
		}
		return propertyAssignments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.OBJECT_LITERAL__PROPERTY_ASSIGNMENTS:
				return ((InternalEList<?>)getPropertyAssignments()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE:
				if (resolve) return getDefinedType();
				return basicGetDefinedType();
			case N4JSPackage.OBJECT_LITERAL__PROPERTY_ASSIGNMENTS:
				return getPropertyAssignments();
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
			case N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE:
				setDefinedType((Type)newValue);
				return;
			case N4JSPackage.OBJECT_LITERAL__PROPERTY_ASSIGNMENTS:
				getPropertyAssignments().clear();
				getPropertyAssignments().addAll((Collection<? extends PropertyAssignment>)newValue);
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
			case N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE:
				setDefinedType((Type)null);
				return;
			case N4JSPackage.OBJECT_LITERAL__PROPERTY_ASSIGNMENTS:
				getPropertyAssignments().clear();
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
			case N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE:
				return definedType != null;
			case N4JSPackage.OBJECT_LITERAL__PROPERTY_ASSIGNMENTS:
				return propertyAssignments != null && !propertyAssignments.isEmpty();
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
		if (baseClass == ThisTarget.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE: return N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE;
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
		if (baseClass == ThisTarget.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == TypeDefiningElement.class) {
			switch (baseFeatureID) {
				case N4JSPackage.TYPE_DEFINING_ELEMENT__DEFINED_TYPE: return N4JSPackage.OBJECT_LITERAL__DEFINED_TYPE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //ObjectLiteralImpl
