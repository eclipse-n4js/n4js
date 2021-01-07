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

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.ArrayLike;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TObjectPrototype;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TObject Prototype</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TObjectPrototypeImpl#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TObjectPrototypeImpl#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TObjectPrototypeImpl#getDeclaredElementType <em>Declared Element Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TObjectPrototypeImpl#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TObjectPrototypeImpl#isDeclaredFinal <em>Declared Final</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TObjectPrototypeImpl extends TClassifierImpl implements TObjectPrototype {
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
	 * The cached value of the '{@link #getSuperType() <em>Super Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperType()
	 * @generated
	 * @ordered
	 */
	protected ParameterizedTypeRef superType;

	/**
	 * The default value of the '{@link #isDeclaredFinal() <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredFinal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DECLARED_FINAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeclaredFinal() <em>Declared Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeclaredFinal()
	 * @generated
	 * @ordered
	 */
	protected boolean declaredFinal = DECLARED_FINAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TObjectPrototypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TOBJECT_PROTOTYPE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TOBJECT_PROTOTYPE__DECLARED_TYPE_ACCESS_MODIFIER, oldDeclaredTypeAccessModifier, declaredTypeAccessModifier));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TOBJECT_PROTOTYPE__DECLARED_PROVIDED_BY_RUNTIME, oldDeclaredProvidedByRuntime, declaredProvidedByRuntime));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE, oldDeclaredElementType, newDeclaredElementType);
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
				msgs = ((InternalEObject)declaredElementType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE, null, msgs);
			if (newDeclaredElementType != null)
				msgs = ((InternalEObject)newDeclaredElementType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE, null, msgs);
			msgs = basicSetDeclaredElementType(newDeclaredElementType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE, newDeclaredElementType, newDeclaredElementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef getSuperType() {
		return superType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperType(ParameterizedTypeRef newSuperType, NotificationChain msgs) {
		ParameterizedTypeRef oldSuperType = superType;
		superType = newSuperType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE, oldSuperType, newSuperType);
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
	public void setSuperType(ParameterizedTypeRef newSuperType) {
		if (newSuperType != superType) {
			NotificationChain msgs = null;
			if (superType != null)
				msgs = ((InternalEObject)superType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE, null, msgs);
			if (newSuperType != null)
				msgs = ((InternalEObject)newSuperType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE, null, msgs);
			msgs = basicSetSuperType(newSuperType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE, newSuperType, newSuperType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDeclaredFinal() {
		return declaredFinal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredFinal(boolean newDeclaredFinal) {
		boolean oldDeclaredFinal = declaredFinal;
		declaredFinal = newDeclaredFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TOBJECT_PROTOTYPE__DECLARED_FINAL, oldDeclaredFinal, declaredFinal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TMethod getOwnedCtor() {
		final Function1<TMethod, Boolean> _function = new Function1<TMethod, Boolean>() {
			public Boolean apply(final TMethod it) {
				return Boolean.valueOf(it.getName().equals("constructor"));
			}
		};
		return IterableExtensions.<TMethod>findFirst(Iterables.<TMethod>filter(this.getOwnedMembers(), TMethod.class), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFinal() {
		return this.isDeclaredFinal();
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
			boolean _isExported = this.isExported();
			if (_isExported) {
				return TypeAccessModifier.PROJECT;
			}
			else {
				return TypeAccessModifier.PRIVATE;
			}
		}
		return this.getDeclaredTypeAccessModifier();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE:
				return basicSetDeclaredElementType(null, msgs);
			case TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE:
				return basicSetSuperType(null, msgs);
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
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_TYPE_ACCESS_MODIFIER:
				return getDeclaredTypeAccessModifier();
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_PROVIDED_BY_RUNTIME:
				return isDeclaredProvidedByRuntime();
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE:
				return getDeclaredElementType();
			case TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE:
				return getSuperType();
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_FINAL:
				return isDeclaredFinal();
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
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier((TypeAccessModifier)newValue);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime((Boolean)newValue);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)newValue);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE:
				setSuperType((ParameterizedTypeRef)newValue);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_FINAL:
				setDeclaredFinal((Boolean)newValue);
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
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_TYPE_ACCESS_MODIFIER:
				setDeclaredTypeAccessModifier(DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_PROVIDED_BY_RUNTIME:
				setDeclaredProvidedByRuntime(DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE:
				setDeclaredElementType((TypeRef)null);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE:
				setSuperType((ParameterizedTypeRef)null);
				return;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_FINAL:
				setDeclaredFinal(DECLARED_FINAL_EDEFAULT);
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
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_TYPE_ACCESS_MODIFIER:
				return declaredTypeAccessModifier != DECLARED_TYPE_ACCESS_MODIFIER_EDEFAULT;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_PROVIDED_BY_RUNTIME:
				return declaredProvidedByRuntime != DECLARED_PROVIDED_BY_RUNTIME_EDEFAULT;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE:
				return declaredElementType != null;
			case TypesPackage.TOBJECT_PROTOTYPE__SUPER_TYPE:
				return superType != null;
			case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_FINAL:
				return declaredFinal != DECLARED_FINAL_EDEFAULT;
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
				case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == ArrayLike.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE: return TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE;
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
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_TYPE_ACCESS_MODIFIER: return TypesPackage.TOBJECT_PROTOTYPE__DECLARED_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT__DECLARED_PROVIDED_BY_RUNTIME: return TypesPackage.TOBJECT_PROTOTYPE__DECLARED_PROVIDED_BY_RUNTIME;
				default: return -1;
			}
		}
		if (baseClass == ArrayLike.class) {
			switch (baseFeatureID) {
				case TypesPackage.ARRAY_LIKE__DECLARED_ELEMENT_TYPE: return TypesPackage.TOBJECT_PROTOTYPE__DECLARED_ELEMENT_TYPE;
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
				case TypesPackage.TYPE___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TOBJECT_PROTOTYPE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.TYPE___IS_FINAL: return TypesPackage.TOBJECT_PROTOTYPE___IS_FINAL;
				case TypesPackage.TYPE___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TOBJECT_PROTOTYPE___GET_TYPE_ACCESS_MODIFIER;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ContainerType.class) {
			switch (baseOperationID) {
				case TypesPackage.CONTAINER_TYPE___GET_OWNED_CTOR: return TypesPackage.TOBJECT_PROTOTYPE___GET_OWNED_CTOR;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TClassifier.class) {
			switch (baseOperationID) {
				case TypesPackage.TCLASSIFIER___IS_FINAL: return TypesPackage.TOBJECT_PROTOTYPE___IS_FINAL;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == AccessibleTypeElement.class) {
			switch (baseOperationID) {
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_PROVIDED_BY_RUNTIME: return TypesPackage.TOBJECT_PROTOTYPE___IS_PROVIDED_BY_RUNTIME;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___GET_TYPE_ACCESS_MODIFIER: return TypesPackage.TOBJECT_PROTOTYPE___GET_TYPE_ACCESS_MODIFIER;
				case TypesPackage.ACCESSIBLE_TYPE_ELEMENT___IS_EXPORTED: return TypesPackage.TOBJECT_PROTOTYPE___IS_EXPORTED;
				default: return -1;
			}
		}
		if (baseClass == ArrayLike.class) {
			switch (baseOperationID) {
				case TypesPackage.ARRAY_LIKE___GET_ELEMENT_TYPE: return TypesPackage.TOBJECT_PROTOTYPE___GET_ELEMENT_TYPE;
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
			case TypesPackage.TOBJECT_PROTOTYPE___GET_OWNED_CTOR:
				return getOwnedCtor();
			case TypesPackage.TOBJECT_PROTOTYPE___IS_FINAL:
				return isFinal();
			case TypesPackage.TOBJECT_PROTOTYPE___IS_PROVIDED_BY_RUNTIME:
				return isProvidedByRuntime();
			case TypesPackage.TOBJECT_PROTOTYPE___GET_TYPE_ACCESS_MODIFIER:
				return getTypeAccessModifier();
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
		result.append(", declaredFinal: ");
		result.append(declaredFinal);
		result.append(')');
		return result.toString();
	}

} //TObjectPrototypeImpl
