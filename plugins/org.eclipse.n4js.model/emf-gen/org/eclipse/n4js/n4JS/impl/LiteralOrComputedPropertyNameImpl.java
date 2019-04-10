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

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.PropertyNameKind;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Literal Or Computed Property Name</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl#getLiteralName <em>Literal Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl#getComputedName <em>Computed Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LiteralOrComputedPropertyNameImpl extends ProxyResolvingEObjectImpl implements LiteralOrComputedPropertyName {
	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final PropertyNameKind KIND_EDEFAULT = PropertyNameKind.IDENTIFIER;

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected PropertyNameKind kind = KIND_EDEFAULT;

	/**
	 * The default value of the '{@link #getLiteralName() <em>Literal Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteralName()
	 * @generated
	 * @ordered
	 */
	protected static final String LITERAL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLiteralName() <em>Literal Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteralName()
	 * @generated
	 * @ordered
	 */
	protected String literalName = LITERAL_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getComputedName() <em>Computed Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComputedName()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPUTED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComputedName() <em>Computed Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComputedName()
	 * @generated
	 * @ordered
	 */
	protected String computedName = COMPUTED_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression expression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LiteralOrComputedPropertyNameImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.LITERAL_OR_COMPUTED_PROPERTY_NAME;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PropertyNameKind getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKind(PropertyNameKind newKind) {
		PropertyNameKind oldKind = kind;
		kind = newKind == null ? KIND_EDEFAULT : newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteralName() {
		return literalName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLiteralName(String newLiteralName) {
		String oldLiteralName = literalName;
		literalName = newLiteralName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME, oldLiteralName, literalName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getComputedName() {
		return computedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComputedName(String newComputedName) {
		String oldComputedName = computedName;
		computedName = newComputedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME, oldComputedName, computedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Expression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(Expression newExpression, NotificationChain msgs) {
		Expression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(Expression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject)expression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject)newExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasComputedPropertyName() {
		return ((this.getKind() == PropertyNameKind.COMPUTED) && (this.getExpression() != null));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		String _elvis = null;
		String _literalName = this.getLiteralName();
		if (_literalName != null) {
			_elvis = _literalName;
		} else {
			String _computedName = this.getComputedName();
			_elvis = _computedName;
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
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION:
				return basicSetExpression(null, msgs);
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
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND:
				return getKind();
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME:
				return getLiteralName();
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME:
				return getComputedName();
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION:
				return getExpression();
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
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND:
				setKind((PropertyNameKind)newValue);
				return;
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME:
				setLiteralName((String)newValue);
				return;
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME:
				setComputedName((String)newValue);
				return;
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION:
				setExpression((Expression)newValue);
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
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME:
				setLiteralName(LITERAL_NAME_EDEFAULT);
				return;
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME:
				setComputedName(COMPUTED_NAME_EDEFAULT);
				return;
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION:
				setExpression((Expression)null);
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
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND:
				return kind != KIND_EDEFAULT;
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME:
				return LITERAL_NAME_EDEFAULT == null ? literalName != null : !LITERAL_NAME_EDEFAULT.equals(literalName);
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME:
				return COMPUTED_NAME_EDEFAULT == null ? computedName != null : !COMPUTED_NAME_EDEFAULT.equals(computedName);
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION:
				return expression != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME___HAS_COMPUTED_PROPERTY_NAME:
				return hasComputedPropertyName();
			case N4JSPackage.LITERAL_OR_COMPUTED_PROPERTY_NAME___GET_NAME:
				return getName();
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
		result.append(" (kind: ");
		result.append(kind);
		result.append(", literalName: ");
		result.append(literalName);
		result.append(", computedName: ");
		result.append(computedName);
		result.append(')');
		return result.toString();
	}

} //LiteralOrComputedPropertyNameImpl
