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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.ts.types.util.Variance;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TypeVariableImpl#isDeclaredCovariant <em>Declared Covariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TypeVariableImpl#isDeclaredContravariant <em>Declared Contravariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TypeVariableImpl#getDeclaredUpperBound <em>Declared Upper Bound</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeVariableImpl extends TypeImpl implements TypeVariable {
	/**
	 * The default value of the '{@link #isDeclaredCovariant() <em>Declared Covariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredCovariant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_COVARIANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredCovariant() <em>Declared Covariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredCovariant()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredCovariant = DECLARED_COVARIANT_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredContravariant() <em>Declared Contravariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredContravariant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_CONTRAVARIANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredContravariant() <em>Declared Contravariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredContravariant()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredContravariant = DECLARED_CONTRAVARIANT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDeclaredUpperBound() <em>Declared Upper Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredUpperBound()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredUpperBound;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TYPE_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredCovariant() {
		return declaredCovariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredCovariant(boolean newDeclaredCovariant) {
		boolean oldDeclaredCovariant = declaredCovariant;
		declaredCovariant = newDeclaredCovariant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TYPE_VARIABLE__DECLARED_COVARIANT, oldDeclaredCovariant, declaredCovariant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredContravariant() {
		return declaredContravariant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredContravariant(boolean newDeclaredContravariant) {
		boolean oldDeclaredContravariant = declaredContravariant;
		declaredContravariant = newDeclaredContravariant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TYPE_VARIABLE__DECLARED_CONTRAVARIANT, oldDeclaredContravariant, declaredContravariant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredUpperBound() {
		return declaredUpperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredUpperBound(TypeRef newDeclaredUpperBound, NotificationChain msgs) {
		TypeRef oldDeclaredUpperBound = declaredUpperBound;
		declaredUpperBound = newDeclaredUpperBound;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND, oldDeclaredUpperBound, newDeclaredUpperBound);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredUpperBound(TypeRef newDeclaredUpperBound) {
		if (newDeclaredUpperBound != declaredUpperBound) {
			NotificationChain msgs = null;
			if (declaredUpperBound != null)
				msgs = ((InternalEObject)declaredUpperBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND, null, msgs);
			if (newDeclaredUpperBound != null)
				msgs = ((InternalEObject)newDeclaredUpperBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND, null, msgs);
			msgs = basicSetDeclaredUpperBound(newDeclaredUpperBound, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND, newDeclaredUpperBound, newDeclaredUpperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Variance getVariance() {
		final boolean co = this.isDeclaredCovariant();
		final boolean contra = this.isDeclaredContravariant();
		if ((co && (!contra))) {
			return Variance.CO;
		}
		else {
			if ((contra && (!co))) {
				return Variance.CONTRA;
			}
			else {
				return Variance.INV;
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		return XcoreCollectionLiterals.<TypeVariable>emptyEList();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeAsString() {
		return this.getTypeVariableAsString(this.getDeclaredUpperBound());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeVariableAsString(final TypeRef upperBound) {
		String _xifexpression = null;
		boolean _isDeclaredCovariant = this.isDeclaredCovariant();
		if (_isDeclaredCovariant) {
			_xifexpression = "out ";
		}
		else {
			String _xifexpression_1 = null;
			boolean _isDeclaredContravariant = this.isDeclaredContravariant();
			if (_isDeclaredContravariant) {
				_xifexpression_1 = "in ";
			}
			else {
				_xifexpression_1 = "";
			}
			_xifexpression = _xifexpression_1;
		}
		String _name = this.getName();
		String _plus = (_xifexpression + _name);
		String _xifexpression_2 = null;
		if ((upperBound != null)) {
			String _typeRefAsString = upperBound.getTypeRefAsString();
			_xifexpression_2 = (" extends " + _typeRefAsString);
		}
		else {
			_xifexpression_2 = "";
		}
		return (_plus + _xifexpression_2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				return basicSetDeclaredUpperBound(null, msgs);
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
			case TypesPackage.TYPE_VARIABLE__DECLARED_COVARIANT:
				return isDeclaredCovariant();
			case TypesPackage.TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				return isDeclaredContravariant();
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				return getDeclaredUpperBound();
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
			case TypesPackage.TYPE_VARIABLE__DECLARED_COVARIANT:
				setDeclaredCovariant((Boolean)newValue);
				return;
			case TypesPackage.TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				setDeclaredContravariant((Boolean)newValue);
				return;
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				setDeclaredUpperBound((TypeRef)newValue);
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
			case TypesPackage.TYPE_VARIABLE__DECLARED_COVARIANT:
				setDeclaredCovariant(DECLARED_COVARIANT_EDEFAULT);
				return;
			case TypesPackage.TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				setDeclaredContravariant(DECLARED_CONTRAVARIANT_EDEFAULT);
				return;
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				setDeclaredUpperBound((TypeRef)null);
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
			case TypesPackage.TYPE_VARIABLE__DECLARED_COVARIANT:
				return declaredCovariant != DECLARED_COVARIANT_EDEFAULT;
			case TypesPackage.TYPE_VARIABLE__DECLARED_CONTRAVARIANT:
				return declaredContravariant != DECLARED_CONTRAVARIANT_EDEFAULT;
			case TypesPackage.TYPE_VARIABLE__DECLARED_UPPER_BOUND:
				return declaredUpperBound != null;
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
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___GET_TYPE_VARS: return TypesPackage.TYPE_VARIABLE___GET_TYPE_VARS;
				case TypesPackage.TYPE___GET_TYPE_AS_STRING: return TypesPackage.TYPE_VARIABLE___GET_TYPE_AS_STRING;
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
			case TypesPackage.TYPE_VARIABLE___GET_VARIANCE:
				return getVariance();
			case TypesPackage.TYPE_VARIABLE___GET_TYPE_VARS:
				return getTypeVars();
			case TypesPackage.TYPE_VARIABLE___GET_TYPE_AS_STRING:
				return getTypeAsString();
			case TypesPackage.TYPE_VARIABLE___GET_TYPE_VARIABLE_AS_STRING__TYPEREF:
				return getTypeVariableAsString((TypeRef)arguments.get(0));
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
		result.append(" (declaredCovariant: ");
		result.append(declaredCovariant);
		result.append(", declaredContravariant: ");
		result.append(declaredContravariant);
		result.append(')');
		return result.toString();
	}

} //TypeVariableImpl
