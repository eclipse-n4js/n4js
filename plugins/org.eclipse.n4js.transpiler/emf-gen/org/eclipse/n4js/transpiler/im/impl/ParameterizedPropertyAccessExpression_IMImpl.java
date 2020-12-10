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
package org.eclipse.n4js.transpiler.im.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.ReferencingElementExpression_IM;
import org.eclipse.n4js.transpiler.im.ReferencingElement_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameterized Property Access Expression IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedPropertyAccessExpression_IMImpl#getRewiredTarget <em>Rewired Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedPropertyAccessExpression_IMImpl#isAnyPlusAccess <em>Any Plus Access</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedPropertyAccessExpression_IMImpl#getNameOfAnyPlusProperty <em>Name Of Any Plus Property</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterizedPropertyAccessExpression_IMImpl extends ParameterizedPropertyAccessExpressionImpl implements ParameterizedPropertyAccessExpression_IM {
	/**
	 * The cached value of the '{@link #getRewiredTarget() <em>Rewired Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRewiredTarget()
	 * @generated
	 * @ordered
	 */
	protected SymbolTableEntry rewiredTarget;

	/**
	 * The default value of the '{@link #isAnyPlusAccess() <em>Any Plus Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAnyPlusAccess()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ANY_PLUS_ACCESS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAnyPlusAccess() <em>Any Plus Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAnyPlusAccess()
	 * @generated
	 * @ordered
	 */
	protected boolean anyPlusAccess = ANY_PLUS_ACCESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getNameOfAnyPlusProperty() <em>Name Of Any Plus Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameOfAnyPlusProperty()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_OF_ANY_PLUS_PROPERTY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNameOfAnyPlusProperty() <em>Name Of Any Plus Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameOfAnyPlusProperty()
	 * @generated
	 * @ordered
	 */
	protected String nameOfAnyPlusProperty = NAME_OF_ANY_PLUS_PROPERTY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterizedPropertyAccessExpression_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntry getRewiredTarget() {
		if (rewiredTarget != null && rewiredTarget.eIsProxy()) {
			InternalEObject oldRewiredTarget = (InternalEObject)rewiredTarget;
			rewiredTarget = (SymbolTableEntry)eResolveProxy(oldRewiredTarget);
			if (rewiredTarget != oldRewiredTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET, oldRewiredTarget, rewiredTarget));
			}
		}
		return rewiredTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbolTableEntry basicGetRewiredTarget() {
		return rewiredTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRewiredTarget(SymbolTableEntry newRewiredTarget, NotificationChain msgs) {
		SymbolTableEntry oldRewiredTarget = rewiredTarget;
		rewiredTarget = newRewiredTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET, oldRewiredTarget, newRewiredTarget);
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
	public void setRewiredTarget(SymbolTableEntry newRewiredTarget) {
		if (newRewiredTarget != rewiredTarget) {
			NotificationChain msgs = null;
			if (rewiredTarget != null)
				msgs = ((InternalEObject)rewiredTarget).eInverseRemove(this, ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS, SymbolTableEntry.class, msgs);
			if (newRewiredTarget != null)
				msgs = ((InternalEObject)newRewiredTarget).eInverseAdd(this, ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS, SymbolTableEntry.class, msgs);
			msgs = basicSetRewiredTarget(newRewiredTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET, newRewiredTarget, newRewiredTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAnyPlusAccess() {
		return anyPlusAccess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnyPlusAccess(boolean newAnyPlusAccess) {
		boolean oldAnyPlusAccess = anyPlusAccess;
		anyPlusAccess = newAnyPlusAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__ANY_PLUS_ACCESS, oldAnyPlusAccess, anyPlusAccess));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getNameOfAnyPlusProperty() {
		return nameOfAnyPlusProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNameOfAnyPlusProperty(String newNameOfAnyPlusProperty) {
		String oldNameOfAnyPlusProperty = nameOfAnyPlusProperty;
		nameOfAnyPlusProperty = newNameOfAnyPlusProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__NAME_OF_ANY_PLUS_PROPERTY, oldNameOfAnyPlusProperty, nameOfAnyPlusProperty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntry getProperty_IM() {
		return this.getRewiredTarget();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProperty_IM(final SymbolTableEntry target) {
		this.setRewiredTarget(target);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPropertyName() {
		String _xifexpression = null;
		boolean _isAnyPlusAccess = this.isAnyPlusAccess();
		if (_isAnyPlusAccess) {
			_xifexpression = this.getNameOfAnyPlusProperty();
		}
		else {
			final SymbolTableEntry e = this.getRewiredTarget();
			if ((e instanceof SymbolTableEntryOriginal)) {
				final String exName = ((SymbolTableEntryOriginal)e).getExportedName();
				if ((exName != null)) {
					return exName;
				}
				else {
					SymbolTableEntry _property_IM = this.getProperty_IM();
					String _name = null;
					if (_property_IM!=null) {
						_name=_property_IM.getName();
					}
					return _name;
				}
			}
			else {
				SymbolTableEntry _property_IM_1 = this.getProperty_IM();
				String _name_1 = null;
				if (_property_IM_1!=null) {
					_name_1=_property_IM_1.getName();
				}
				return _name_1;
			}
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getProperty() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProperty(final IdentifiableElement ix) {
		if ((ix != null)) {
			throw new IllegalArgumentException("ParameterizedPropertyAccessExpression_IM cannot accept properties. Use #property_IM.");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getOriginalTargetOfRewiredTarget() {
		final SymbolTableEntry declaredTypeSTE = this.getRewiredTarget();
		if ((declaredTypeSTE instanceof SymbolTableEntryOriginal)) {
			return ((SymbolTableEntryOriginal)declaredTypeSTE).getOriginalTarget();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET:
				if (rewiredTarget != null)
					msgs = ((InternalEObject)rewiredTarget).eInverseRemove(this, ImPackage.SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS, SymbolTableEntry.class, msgs);
				return basicSetRewiredTarget((SymbolTableEntry)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET:
				return basicSetRewiredTarget(null, msgs);
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
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET:
				if (resolve) return getRewiredTarget();
				return basicGetRewiredTarget();
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__ANY_PLUS_ACCESS:
				return isAnyPlusAccess();
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__NAME_OF_ANY_PLUS_PROPERTY:
				return getNameOfAnyPlusProperty();
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
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET:
				setRewiredTarget((SymbolTableEntry)newValue);
				return;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__ANY_PLUS_ACCESS:
				setAnyPlusAccess((Boolean)newValue);
				return;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__NAME_OF_ANY_PLUS_PROPERTY:
				setNameOfAnyPlusProperty((String)newValue);
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
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET:
				setRewiredTarget((SymbolTableEntry)null);
				return;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__ANY_PLUS_ACCESS:
				setAnyPlusAccess(ANY_PLUS_ACCESS_EDEFAULT);
				return;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__NAME_OF_ANY_PLUS_PROPERTY:
				setNameOfAnyPlusProperty(NAME_OF_ANY_PLUS_PROPERTY_EDEFAULT);
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
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET:
				return rewiredTarget != null;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__ANY_PLUS_ACCESS:
				return anyPlusAccess != ANY_PLUS_ACCESS_EDEFAULT;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__NAME_OF_ANY_PLUS_PROPERTY:
				return NAME_OF_ANY_PLUS_PROPERTY_EDEFAULT == null ? nameOfAnyPlusProperty != null : !NAME_OF_ANY_PLUS_PROPERTY_EDEFAULT.equals(nameOfAnyPlusProperty);
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
		if (baseClass == ReferencingElement_IM.class) {
			switch (derivedFeatureID) {
				case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET: return ImPackage.REFERENCING_ELEMENT_IM__REWIRED_TARGET;
				default: return -1;
			}
		}
		if (baseClass == ReferencingElementExpression_IM.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == ReferencingElement_IM.class) {
			switch (baseFeatureID) {
				case ImPackage.REFERENCING_ELEMENT_IM__REWIRED_TARGET: return ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET;
				default: return -1;
			}
		}
		if (baseClass == ReferencingElementExpression_IM.class) {
			switch (baseFeatureID) {
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
		if (baseClass == ReferencingElement_IM.class) {
			switch (baseOperationID) {
				case ImPackage.REFERENCING_ELEMENT_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET: return ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET;
				default: return -1;
			}
		}
		if (baseClass == ReferencingElementExpression_IM.class) {
			switch (baseOperationID) {
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
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY_IM:
				return getProperty_IM();
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___SET_PROPERTY_IM__SYMBOLTABLEENTRY:
				setProperty_IM((SymbolTableEntry)arguments.get(0));
				return null;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY_NAME:
				return getPropertyName();
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY:
				return getProperty();
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___SET_PROPERTY__IDENTIFIABLEELEMENT:
				setProperty((IdentifiableElement)arguments.get(0));
				return null;
			case ImPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET:
				return getOriginalTargetOfRewiredTarget();
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
		result.append(" (anyPlusAccess: ");
		result.append(anyPlusAccess);
		result.append(", nameOfAnyPlusProperty: ");
		result.append(nameOfAnyPlusProperty);
		result.append(')');
		return result.toString();
	}

} //ParameterizedPropertyAccessExpression_IMImpl
