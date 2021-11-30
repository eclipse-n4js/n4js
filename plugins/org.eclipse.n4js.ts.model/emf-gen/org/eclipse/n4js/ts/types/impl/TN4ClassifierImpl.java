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

import com.google.common.base.Objects;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.ArrayLike;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TN4 Classifier</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TN4ClassifierImpl#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TN4ClassifierImpl#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TN4ClassifierImpl#getDeclaredElementType <em>Declared Element Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TN4ClassifierImpl#isDeclaredNonStaticPolyfill <em>Declared Non Static Polyfill</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TN4ClassifierImpl#isDynamizable <em>Dynamizable</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TN4ClassifierImpl#getTypingStrategy <em>Typing Strategy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TN4ClassifierImpl extends TClassifierImpl implements TN4Classifier {
	/**
	 * The default value of the '{@link #getDeclaredTypeAccessModifier() <em>Declared Type Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected static final TypeAccessModifier DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT = TypeAccessModifier.UNDEFINED;

	/**
	 * The cached value of the '{@link #getDeclaredTypeAccessModifier() <em>Declared Type Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAccessModifier()
	 * @generated
	 * @ordered
	 */
	protected TypeAccessModifier declaredTypeAccessModifier = DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeclaredProvidedByRuntime() <em>Declared Provided By Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredProvidedByRuntime()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredProvidedByRuntime() <em>Declared Provided By Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredProvidedByRuntime()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredProvidedByRuntime = DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;

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
	 * The default value of the '{@link #isDeclaredNonStaticPolyfill() <em>Declared Non Static Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredNonStaticPolyfill()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_NON_STATIC_POLYFILL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredNonStaticPolyfill() <em>Declared Non Static Polyfill</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredNonStaticPolyfill()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredNonStaticPolyfill = DECLARED_NON_STATIC_POLYFILL_EDEFAULT;

	/**
	 * The default value of the '{@link #isDynamizable() <em>Dynamizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDynamizable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DYNAMIZABLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDynamizable() <em>Dynamizable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDynamizable()
	 * @generated
	 * @ordered
	 */
	protected boolean dynamizable = DYNAMIZABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTypingStrategy() <em>Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final TypingStrategy TYPING_STRATEGY_EDEFAULT = TypingStrategy.DEFAULT;

	/**
	 * The cached value of the '{@link #getTypingStrategy() <em>Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected TypingStrategy typingStrategy = TYPING_STRATEGY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TN4ClassifierImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TN4_CLASSIFIER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getDeclaredTypeAccessModifier() {
		return declaredTypeAccessModifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredTypeAccessModifier(TypeAccessModifier newDeclaredTypeAccessModifier) {
		TypeAccessModifier oldDeclaredTypeAccessModifier = declaredTypeAccessModifier;
		declaredTypeAccessModifier = newDeclaredTypeAccessModifier == null ? DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT : newDeclaredTypeAccessModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TN4_CLASSIFIER__DECLARED_TYPE_ACCESS_MODIFIER, oldDeclaredTypeAccessModifier, declaredTypeAccessModifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredProvidedByRuntime() {
		return declaredProvidedByRuntime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredProvidedByRuntime(boolean newDeclaredProvidedByRuntime) {
		boolean oldDeclaredProvidedByRuntime = declaredProvidedByRuntime;
		declaredProvidedByRuntime = newDeclaredProvidedByRuntime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TN4_CLASSIFIER__DECLARED_PROVIDED_BY_RUNTIME, oldDeclaredProvidedByRuntime, declaredProvidedByRuntime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE, oldDeclaredElementType, newDeclaredElementType);
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
	public void setDeclaredElementType(TypeRef newDeclaredElementType) {
		if (newDeclaredElementType != declaredElementType) {
			NotificationChain msgs = null;
			if (declaredElementType != null)
				msgs = ((InternalEObject)declaredElementType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE, null, msgs);
			if (newDeclaredElementType != null)
				msgs = ((InternalEObject)newDeclaredElementType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE, null, msgs);
			msgs = basicSetDeclaredElementType(newDeclaredElementType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE, newDeclaredElementType, newDeclaredElementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredNonStaticPolyfill() {
		return declaredNonStaticPolyfill;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredNonStaticPolyfill(boolean newDeclaredNonStaticPolyfill) {
		boolean oldDeclaredNonStaticPolyfill = declaredNonStaticPolyfill;
		declaredNonStaticPolyfill = newDeclaredNonStaticPolyfill;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TN4_CLASSIFIER__DECLARED_NON_STATIC_POLYFILL, oldDeclaredNonStaticPolyfill, declaredNonStaticPolyfill));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDynamizable() {
		return dynamizable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDynamizable(boolean newDynamizable) {
		boolean oldDynamizable = dynamizable;
		dynamizable = newDynamizable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TN4_CLASSIFIER__DYNAMIZABLE, oldDynamizable, dynamizable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getTypingStrategy() {
		return typingStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypingStrategy(TypingStrategy newTypingStrategy) {
		TypingStrategy oldTypingStrategy = typingStrategy;
		typingStrategy = newTypingStrategy == null ? TYPING_STRATEGY_EDEFAULT : newTypingStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TN4_CLASSIFIER__TYPING_STRATEGY, oldTypingStrategy, typingStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPolyfill() {
		return this.isDeclaredNonStaticPolyfill();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isProvidedByRuntime() {
		return this.isDeclaredProvidedByRuntime();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getTypeAccessModifier() {
		TypeAccessModifier _declaredTypeAccessModifier = this.getDeclaredTypeAccessModifier();
		boolean _equals = Objects.equal(_declaredTypeAccessModifier, TypeAccessModifier.UNDEFINED);
		if (_equals) {
			return this.getDefaultTypeAccessModifier();
		}
		return this.getDeclaredTypeAccessModifier();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeAccessModifier getDefaultTypeAccessModifier() {
		EObject _eContainer = this.eContainer();
		if ((_eContainer instanceof TNamespace)) {
			EObject _eContainer_1 = this.eContainer();
			return ((TNamespace) _eContainer_1).getTypeAccessModifier();
		}
		else {
			boolean _isExported = this.isExported();
			if (_isExported) {
				return TypeAccessModifier.PROJECT;
			}
			else {
				return TypeAccessModifier.PRIVATE;
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE:
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
			case TypesPackage.TN4_CLASSIFIER__DECLARED_TYPE_ACCESS_MODIFIER:
				return getDeclaredTypeAccessModifier();
			case TypesPackage.TN4_CLASSIFIER__DECLARED_PROVIDED_BY_RUNTIME:
				return isDeclaredProvidedByRuntime();
			case TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE:
				return getDeclaredElementType();
			case TypesPackage.TN4_CLASSIFIER__DECLARED_NON_STATIC_POLYFILL:
				return isDeclaredNonStaticPolyfill();
			case TypesPackage.TN4_CLASSIFIER__DYNAMIZABLE:
				return isDynamizable();
			case TypesPackage.TN4_CLASSIFIER__TYPING_STRATEGY:
				return getTypingStrategy();
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
			case TypesPackage.TN4_CLASSIFIER__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier((TypeAccessModifier)newValue);
				return;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime((Boolean)newValue);
				return;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)newValue);
				return;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_NON_STATIC_POLYFILL:
				setDeclaredNonStaticPolyfill((Boolean)newValue);
				return;
			case TypesPackage.TN4_CLASSIFIER__DYNAMIZABLE:
				setDynamizable((Boolean)newValue);
				return;
			case TypesPackage.TN4_CLASSIFIER__TYPING_STRATEGY:
				setTypingStrategy((TypingStrategy)newValue);
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
			case TypesPackage.TN4_CLASSIFIER__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier(DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT);
				return;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime(DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT);
				return;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)null);
				return;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_NON_STATIC_POLYFILL:
				setDeclaredNonStaticPolyfill(DECLARED_NON_STATIC_POLYFILL_EDEFAULT);
				return;
			case TypesPackage.TN4_CLASSIFIER__DYNAMIZABLE:
				setDynamizable(DYNAMIZABLE_EDEFAULT);
				return;
			case TypesPackage.TN4_CLASSIFIER__TYPING_STRATEGY:
				setTypingStrategy(TYPING_STRATEGY_EDEFAULT);
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
			case TypesPackage.TN4_CLASSIFIER__DECLARED_TYPE_ACCESS_MODIFIER:
				return declaredTypeAccessModifier != DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_PROVIDED_BY_RUNTIME:
				return declaredProvidedByRuntime != DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE:
				return declaredElementType != null;
			case TypesPackage.TN4_CLASSIFIER__DECLARED_NON_STATIC_POLYFILL:
				return declaredNonStaticPolyfill != DECLARED_NON_STATIC_POLYFILL_EDEFAULT;
			case TypesPackage.TN4_CLASSIFIER__DYNAMIZABLE:
				return dynamizable != DYNAMIZABLE_EDEFAULT;
			case TypesPackage.TN4_CLASSIFIER__TYPING_STRATEGY:
				return typingStrategy != TYPING_STRATEGY_EDEFAULT;
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
		if (baseClass == AccessibleTypeElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TN4_CLASSIFIER__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.TN4_CLASSIFIER__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == ArrayLike.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE: return TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE;
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
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.TN4_CLASSIFIER__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.TN4_CLASSIFIER__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == ArrayLike.class) {
			switch (baseFeatureID) {
				case TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE: return TypesPackage.TN4_CLASSIFIER__DECLARED_ELEMENT_TYPE;
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
		if (baseClass == Type.class) {
			switch (baseOperationID) {
				case TypesPackage.TYPE___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TN4_CLASSIFIER___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.TYPE___IS_POLYFILL: return TypesPackage.TN4_CLASSIFIER___IS_POLYFILL;
				case TypesPackage.TYPE___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TN4_CLASSIFIER___GET_TYPE_ACCESS_MODIFIER;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseOperationID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TN4_CLASSIFIER___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TN4_CLASSIFIER___GET_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_DEFAULT_TYPE_ACCESS_MODIFIER: return TypesPackage.TN4_CLASSIFIER___GET_DEFAULT_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_EXPORTED: return TypesPackage.TN4_CLASSIFIER___IS_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == ArrayLike.class) {
			switch (baseOperationID) {
				case TypesPackage.ARRAY_LIKE___GET_ELEMENT_TYPE: return TypesPackage.TN4_CLASSIFIER___GET_ELEMENT_TYPE;
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
			case TypesPackage.TN4_CLASSIFIER___IS_POLYFILL:
				return isPolyfill();
			case TypesPackage.TN4_CLASSIFIER___IS_PROVIDED_BY_RUNTIME:
				return isProvidedByRuntime();
			case TypesPackage.TN4_CLASSIFIER___GET_TYPE_ACCESS_MODIFIER:
				return getTypeAccessModifier();
			case TypesPackage.TN4_CLASSIFIER___GET_DEFAULT_TYPE_ACCESS_MODIFIER:
				return getDefaultTypeAccessModifier();
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
		result.append(" (declaredTypeAccessModifier: ");
		result.append(declaredTypeAccessModifier);
		result.append(", declaredProvidedByRuntime: ");
		result.append(declaredProvidedByRuntime);
		result.append(", declaredNonStaticPolyfill: ");
		result.append(declaredNonStaticPolyfill);
		result.append(", dynamizable: ");
		result.append(dynamizable);
		result.append(", typingStrategy: ");
		result.append(typingStrategy);
		result.append(')');
		return result.toString();
	}

} //TN4ClassifierImpl
