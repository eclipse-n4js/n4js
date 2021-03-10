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
package org.eclipse.n4js.ts.typeRefs.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Wildcard;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TypeVariable;

import org.eclipse.n4js.ts.types.internal.WildcardAsStringUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Wildcard</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.WildcardImpl#getDeclaredUpperBound <em>Declared Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.WildcardImpl#getDeclaredLowerBound <em>Declared Lower Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.WildcardImpl#isUsingInOutNotation <em>Using In Out Notation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WildcardImpl extends TypeArgumentImpl implements Wildcard {
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
	 * The cached value of the '{@link #getDeclaredLowerBound() <em>Declared Lower Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredLowerBound()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredLowerBound;

	/**
	 * The default value of the '{@link #isUsingInOutNotation() <em>Using In Out Notation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUsingInOutNotation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USING_IN_OUT_NOTATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUsingInOutNotation() <em>Using In Out Notation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUsingInOutNotation()
	 * @generated
	 * @ordered
	 */
	protected boolean usingInOutNotation = USING_IN_OUT_NOTATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WildcardImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.WILDCARD;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND, oldDeclaredUpperBound, newDeclaredUpperBound);
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
				msgs = ((InternalEObject)declaredUpperBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND, null, msgs);
			if (newDeclaredUpperBound != null)
				msgs = ((InternalEObject)newDeclaredUpperBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND, null, msgs);
			msgs = basicSetDeclaredUpperBound(newDeclaredUpperBound, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND, newDeclaredUpperBound, newDeclaredUpperBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredLowerBound() {
		return declaredLowerBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredLowerBound(TypeRef newDeclaredLowerBound, NotificationChain msgs) {
		TypeRef oldDeclaredLowerBound = declaredLowerBound;
		declaredLowerBound = newDeclaredLowerBound;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND, oldDeclaredLowerBound, newDeclaredLowerBound);
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
	public void setDeclaredLowerBound(TypeRef newDeclaredLowerBound) {
		if (newDeclaredLowerBound != declaredLowerBound) {
			NotificationChain msgs = null;
			if (declaredLowerBound != null)
				msgs = ((InternalEObject)declaredLowerBound).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND, null, msgs);
			if (newDeclaredLowerBound != null)
				msgs = ((InternalEObject)newDeclaredLowerBound).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND, null, msgs);
			msgs = basicSetDeclaredLowerBound(newDeclaredLowerBound, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND, newDeclaredLowerBound, newDeclaredLowerBound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUsingInOutNotation() {
		return usingInOutNotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUsingInOutNotation(boolean newUsingInOutNotation) {
		boolean oldUsingInOutNotation = usingInOutNotation;
		usingInOutNotation = newUsingInOutNotation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.WILDCARD__USING_IN_OUT_NOTATION, oldUsingInOutNotation, usingInOutNotation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredOrImplicitUpperBound() {
		final TypeRef declUB = this.getDeclaredUpperBound();
		if ((declUB != null)) {
			return declUB;
		}
		TypeRef _declaredLowerBound = this.getDeclaredLowerBound();
		boolean _tripleNotEquals = (_declaredLowerBound != null);
		if (_tripleNotEquals) {
			return null;
		}
		final EObject parent = this.eContainer();
		if ((parent instanceof ParameterizedTypeRef)) {
			final int typeArgIndex = ((ParameterizedTypeRef)parent).getTypeArgs().indexOf(this);
			if ((typeArgIndex >= 0)) {
				final Object declType = ((ParameterizedTypeRef)parent).eGet(TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType(), false);
				if ((declType instanceof ContainerType<?>)) {
					boolean _eIsProxy = ((ContainerType<?>)declType).eIsProxy();
					boolean _not = (!_eIsProxy);
					if (_not) {
						final EList<TypeVariable> typeVars = ((ContainerType<?>)declType).getTypeVars();
						TypeVariable _xifexpression = null;
						int _size = typeVars.size();
						boolean _lessThan = (typeArgIndex < _size);
						if (_lessThan) {
							_xifexpression = typeVars.get(typeArgIndex);
						}
						else {
							_xifexpression = null;
						}
						final TypeVariable typeVar = _xifexpression;
						if ((typeVar != null)) {
							final TypeRef implicitUB = typeVar.getDeclaredUpperBound();
							return implicitUB;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isImplicitUpperBoundInEffect() {
		return (((this.getDeclaredUpperBound() == null) && (this.getDeclaredLowerBound() == null)) && (this.getDeclaredOrImplicitUpperBound() != null));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		return WildcardAsStringUtils.internalGetTypeRefAsString_workaround(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND:
				return basicSetDeclaredUpperBound(null, msgs);
			case TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND:
				return basicSetDeclaredLowerBound(null, msgs);
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
			case TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND:
				return getDeclaredUpperBound();
			case TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND:
				return getDeclaredLowerBound();
			case TypeRefsPackage.WILDCARD__USING_IN_OUT_NOTATION:
				return isUsingInOutNotation();
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
			case TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND:
				setDeclaredUpperBound((TypeRef)newValue);
				return;
			case TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND:
				setDeclaredLowerBound((TypeRef)newValue);
				return;
			case TypeRefsPackage.WILDCARD__USING_IN_OUT_NOTATION:
				setUsingInOutNotation((Boolean)newValue);
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
			case TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND:
				setDeclaredUpperBound((TypeRef)null);
				return;
			case TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND:
				setDeclaredLowerBound((TypeRef)null);
				return;
			case TypeRefsPackage.WILDCARD__USING_IN_OUT_NOTATION:
				setUsingInOutNotation(USING_IN_OUT_NOTATION_EDEFAULT);
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
			case TypeRefsPackage.WILDCARD__DECLARED_UPPER_BOUND:
				return declaredUpperBound != null;
			case TypeRefsPackage.WILDCARD__DECLARED_LOWER_BOUND:
				return declaredLowerBound != null;
			case TypeRefsPackage.WILDCARD__USING_IN_OUT_NOTATION:
				return usingInOutNotation != USING_IN_OUT_NOTATION_EDEFAULT;
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
			case TypeRefsPackage.WILDCARD___GET_DECLARED_OR_IMPLICIT_UPPER_BOUND:
				return getDeclaredOrImplicitUpperBound();
			case TypeRefsPackage.WILDCARD___IS_IMPLICIT_UPPER_BOUND_IN_EFFECT:
				return isImplicitUpperBoundInEffect();
			case TypeRefsPackage.WILDCARD___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
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
		result.append(" (usingInOutNotation: ");
		result.append(usingInOutNotation);
		result.append(')');
		return result.toString();
	}

} //WildcardImpl
