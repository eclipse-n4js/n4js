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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.MemberAccess;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedAccess;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.ComposedMemberCache;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameterized Property Access Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl#getComposedMemberCache <em>Composed Member Cache</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl#getTypeArgs <em>Type Args</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl#getPropertyAsText <em>Property As Text</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterizedPropertyAccessExpressionImpl extends ExpressionWithTargetImpl implements ParameterizedPropertyAccessExpression {
	/**
	 * The cached value of the '{@link #getComposedMemberCache() <em>Composed Member Cache</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedMemberCache()
	 * @generated
	 * @ordered
	 */
	protected ComposedMemberCache composedMemberCache;

	/**
	 * The cached value of the '{@link #getTypeArgs() <em>Type Args</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeRef> typeArgs;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected IdentifiableElement property;

	/**
	 * The default value of the '{@link #getPropertyAsText() <em>Property As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyAsText() <em>Property As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyAsText()
	 * @generated
	 * @ordered
	 */
	protected String propertyAsText = PROPERTY_AS_TEXT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterizedPropertyAccessExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComposedMemberCache getComposedMemberCache() {
		if (composedMemberCache != null && composedMemberCache.eIsProxy()) {
			InternalEObject oldComposedMemberCache = (InternalEObject)composedMemberCache;
			composedMemberCache = (ComposedMemberCache)eResolveProxy(oldComposedMemberCache);
			if (composedMemberCache != oldComposedMemberCache) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
			}
		}
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedMemberCache basicGetComposedMemberCache() {
		return composedMemberCache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setComposedMemberCache(ComposedMemberCache newComposedMemberCache) {
		ComposedMemberCache oldComposedMemberCache = composedMemberCache;
		composedMemberCache = newComposedMemberCache;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE, oldComposedMemberCache, composedMemberCache));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeRef> getTypeArgs() {
		if (typeArgs == null) {
			typeArgs = new EObjectContainmentEList<TypeRef>(TypeRef.class, this, N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS);
		}
		return typeArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdentifiableElement getProperty() {
		if (property != null && property.eIsProxy()) {
			InternalEObject oldProperty = (InternalEObject)property;
			property = (IdentifiableElement)eResolveProxy(oldProperty);
			if (property != oldProperty) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY, oldProperty, property));
			}
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IdentifiableElement basicGetProperty() {
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProperty(IdentifiableElement newProperty) {
		IdentifiableElement oldProperty = property;
		property = newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY, oldProperty, property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPropertyAsText() {
		return propertyAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPropertyAsText(String newPropertyAsText) {
		String oldPropertyAsText = propertyAsText;
		propertyAsText = newPropertyAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT, oldPropertyAsText, propertyAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isValidSimpleAssignmentTarget() {
		final Expression _target = this.getTarget();
		if (((_target instanceof ExpressionWithTarget) && (!_target.isValidSimpleAssignmentTarget()))) {
			return false;
		}
		boolean _isOptionalChaining = this.isOptionalChaining();
		return (!_isOptionalChaining);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isParameterized() {
		boolean _isEmpty = this.getTypeArgs().isEmpty();
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS:
				return ((InternalEList<?>)getTypeArgs()).basicRemove(otherEnd, msgs);
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
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				if (resolve) return getComposedMemberCache();
				return basicGetComposedMemberCache();
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS:
				return getTypeArgs();
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY:
				if (resolve) return getProperty();
				return basicGetProperty();
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT:
				return getPropertyAsText();
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
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)newValue);
				return;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS:
				getTypeArgs().clear();
				getTypeArgs().addAll((Collection<? extends TypeRef>)newValue);
				return;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY:
				setProperty((IdentifiableElement)newValue);
				return;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT:
				setPropertyAsText((String)newValue);
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
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				setComposedMemberCache((ComposedMemberCache)null);
				return;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS:
				getTypeArgs().clear();
				return;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY:
				setProperty((IdentifiableElement)null);
				return;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT:
				setPropertyAsText(PROPERTY_AS_TEXT_EDEFAULT);
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
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE:
				return composedMemberCache != null;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS:
				return typeArgs != null && !typeArgs.isEmpty();
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY:
				return property != null;
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT:
				return PROPERTY_AS_TEXT_EDEFAULT == null ? propertyAsText != null : !PROPERTY_AS_TEXT_EDEFAULT.equals(propertyAsText);
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
		if (baseClass == MemberAccess.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE: return N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedAccess.class) {
			switch (derivedFeatureID) {
				case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS: return N4JSPackage.PARAMETERIZED_ACCESS__TYPE_ARGS;
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
		if (baseClass == MemberAccess.class) {
			switch (baseFeatureID) {
				case N4JSPackage.MEMBER_ACCESS__COMPOSED_MEMBER_CACHE: return N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedAccess.class) {
			switch (baseFeatureID) {
				case N4JSPackage.PARAMETERIZED_ACCESS__TYPE_ARGS: return N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS;
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
		if (baseClass == Expression.class) {
			switch (baseOperationID) {
				case N4JSPackage.EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET: return N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == MemberAccess.class) {
			switch (baseOperationID) {
				default: return -1;
			}
		}
		if (baseClass == ParameterizedAccess.class) {
			switch (baseOperationID) {
				case N4JSPackage.PARAMETERIZED_ACCESS___IS_PARAMETERIZED: return N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_PARAMETERIZED;
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
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET:
				return isValidSimpleAssignmentTarget();
			case N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_PARAMETERIZED:
				return isParameterized();
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
		result.append(" (propertyAsText: ");
		result.append(propertyAsText);
		result.append(')');
		return result.toString();
	}

} //ParameterizedPropertyAccessExpressionImpl
