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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.TypeProvidingElement;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field Accessor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FieldAccessorImpl#getDeclaredName <em>Declared Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.FieldAccessorImpl#isDeclaredOptional <em>Declared Optional</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FieldAccessorImpl extends FunctionOrFieldAccessorImpl implements FieldAccessor {
	/**
	 * The cached value of the '{@link #getDeclaredName() <em>Declared Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredName()
	 * @generated
	 * @ordered
	 */
	protected LiteralOrComputedPropertyName declaredName;

	/**
	 * The default value of the '{@link #isDeclaredOptional() <em>Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredOptional() <em>Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredOptional = DECLARED_OPTIONAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldAccessorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.FIELD_ACCESSOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LiteralOrComputedPropertyName getDeclaredName() {
		return declaredName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredName(LiteralOrComputedPropertyName newDeclaredName, NotificationChain msgs) {
		LiteralOrComputedPropertyName oldDeclaredName = declaredName;
		declaredName = newDeclaredName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME, oldDeclaredName, newDeclaredName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredName(LiteralOrComputedPropertyName newDeclaredName) {
		if (newDeclaredName != declaredName) {
			NotificationChain msgs = null;
			if (declaredName != null)
				msgs = ((InternalEObject)declaredName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME, null, msgs);
			if (newDeclaredName != null)
				msgs = ((InternalEObject)newDeclaredName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME, null, msgs);
			msgs = basicSetDeclaredName(newDeclaredName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME, newDeclaredName, newDeclaredName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeclaredOptional() {
		return declaredOptional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredOptional(boolean newDeclaredOptional) {
		boolean oldDeclaredOptional = declaredOptional;
		declaredOptional = newDeclaredOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.FIELD_ACCESSOR__DECLARED_OPTIONAL, oldDeclaredOptional, declaredOptional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeRef getDeclaredTypeRef() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public org.eclipse.n4js.ts.types.FieldAccessor getDefinedAccessor() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOptional() {
		return this.isDeclaredOptional();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		LiteralOrComputedPropertyName _declaredName = this.getDeclaredName();
		String _name = null;
		if (_declaredName!=null) {
			_name=_declaredName.getName();
		}
		return _name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean hasComputedPropertyName() {
		final LiteralOrComputedPropertyName declName = this.getDeclaredName();
		return ((declName != null) && declName.hasComputedPropertyName());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValidName() {
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME:
				return basicSetDeclaredName(null, msgs);
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
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME:
				return getDeclaredName();
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_OPTIONAL:
				return isDeclaredOptional();
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
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME:
				setDeclaredName((LiteralOrComputedPropertyName)newValue);
				return;
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_OPTIONAL:
				setDeclaredOptional((Boolean)newValue);
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
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME:
				setDeclaredName((LiteralOrComputedPropertyName)null);
				return;
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_OPTIONAL:
				setDeclaredOptional(DECLARED_OPTIONAL_EDEFAULT);
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
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME:
				return declaredName != null;
			case N4JSPackage.FIELD_ACCESSOR__DECLARED_OPTIONAL:
				return declaredOptional != DECLARED_OPTIONAL_EDEFAULT;
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
		if (baseClass == TypeProvidingElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME: return N4JSPackage.PROPERTY_NAME_OWNER__DECLARED_NAME;
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
		if (baseClass == TypeProvidingElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseFeatureID) {
				case N4JSPackage.PROPERTY_NAME_OWNER__DECLARED_NAME: return N4JSPackage.FIELD_ACCESSOR__DECLARED_NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == FunctionOrFieldAccessor.class) {
			switch (baseOperationID) {
				case N4JSPackage.FUNCTION_OR_FIELD_ACCESSOR___GET_NAME: return N4JSPackage.FIELD_ACCESSOR___GET_NAME;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeProvidingElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF: return N4JSPackage.FIELD_ACCESSOR___GET_DECLARED_TYPE_REF;
				default: return -1;
			}
		}
		if (baseClass == NamedElement.class) {
			switch (baseOperationID) {
				case N4JSPackage.NAMED_ELEMENT___GET_NAME: return N4JSPackage.FIELD_ACCESSOR___GET_NAME;
				default: return -1;
			}
		}
		if (baseClass == PropertyNameOwner.class) {
			switch (baseOperationID) {
				case N4JSPackage.PROPERTY_NAME_OWNER___GET_NAME: return N4JSPackage.FIELD_ACCESSOR___GET_NAME;
				case N4JSPackage.PROPERTY_NAME_OWNER___HAS_COMPUTED_PROPERTY_NAME: return N4JSPackage.FIELD_ACCESSOR___HAS_COMPUTED_PROPERTY_NAME;
				case N4JSPackage.PROPERTY_NAME_OWNER___IS_VALID_NAME: return N4JSPackage.FIELD_ACCESSOR___IS_VALID_NAME;
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
			case N4JSPackage.FIELD_ACCESSOR___GET_DECLARED_TYPE_REF:
				return getDeclaredTypeRef();
			case N4JSPackage.FIELD_ACCESSOR___GET_DEFINED_ACCESSOR:
				return getDefinedAccessor();
			case N4JSPackage.FIELD_ACCESSOR___IS_OPTIONAL:
				return isOptional();
			case N4JSPackage.FIELD_ACCESSOR___GET_NAME:
				return getName();
			case N4JSPackage.FIELD_ACCESSOR___HAS_COMPUTED_PROPERTY_NAME:
				return hasComputedPropertyName();
			case N4JSPackage.FIELD_ACCESSOR___IS_VALID_NAME:
				return isValidName();
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
		result.append(" (declaredOptional: ");
		result.append(declaredOptional);
		result.append(')');
		return result.toString();
	}

} //FieldAccessorImpl
