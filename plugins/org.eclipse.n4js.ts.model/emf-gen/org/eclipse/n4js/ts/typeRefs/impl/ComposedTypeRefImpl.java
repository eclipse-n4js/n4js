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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.types.TMember;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composed Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ComposedTypeRefImpl#getCachedComposedMembers <em>Cached Composed Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ComposedTypeRefImpl#getOriginalComposedTypeRef <em>Original Composed Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ComposedTypeRefImpl#getTypeRefs <em>Type Refs</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ComposedTypeRefImpl extends StaticBaseTypeRefImpl implements ComposedTypeRef {
	/**
	 * The cached value of the '{@link #getCachedComposedMembers() <em>Cached Composed Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCachedComposedMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TMember> cachedComposedMembers;

	/**
	 * The cached value of the '{@link #getOriginalComposedTypeRef() <em>Original Composed Type Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalComposedTypeRef()
	 * @generated
	 * @ordered
	 */
	protected ComposedTypeRef originalComposedTypeRef;

	/**
	 * The cached value of the '{@link #getTypeRefs() <em>Type Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeRef> typeRefs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.COMPOSED_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TMember> getCachedComposedMembers() {
		if (cachedComposedMembers == null) {
			cachedComposedMembers = new EObjectContainmentEList<TMember>(TMember.class, this, TypeRefsPackage.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS);
		}
		return cachedComposedMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedTypeRef getOriginalComposedTypeRef() {
		if (originalComposedTypeRef != null && originalComposedTypeRef.eIsProxy()) {
			InternalEObject oldOriginalComposedTypeRef = (InternalEObject)originalComposedTypeRef;
			originalComposedTypeRef = (ComposedTypeRef)eResolveProxy(oldOriginalComposedTypeRef);
			if (originalComposedTypeRef != oldOriginalComposedTypeRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.COMPOSED_TYPE_REF__ORIGINAL_COMPOSED_TYPE_REF, oldOriginalComposedTypeRef, originalComposedTypeRef));
			}
		}
		return originalComposedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedTypeRef basicGetOriginalComposedTypeRef() {
		return originalComposedTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalComposedTypeRef(ComposedTypeRef newOriginalComposedTypeRef) {
		ComposedTypeRef oldOriginalComposedTypeRef = originalComposedTypeRef;
		originalComposedTypeRef = newOriginalComposedTypeRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.COMPOSED_TYPE_REF__ORIGINAL_COMPOSED_TYPE_REF, oldOriginalComposedTypeRef, originalComposedTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeRef> getTypeRefs() {
		if (typeRefs == null) {
			typeRefs = new EObjectContainmentEList<TypeRef>(TypeRef.class, this, TypeRefsPackage.COMPOSED_TYPE_REF__TYPE_REFS);
		}
		return typeRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDynamic() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeRefAsString() {
		EList<TypeRef> _typeRefs = this.getTypeRefs();
		final Function1<TypeRef, String> _function = new Function1<TypeRef, String>() {
			public String apply(final TypeRef it) {
				return it.getTypeRefAsString();
			}
		};
		EList<String> _map = XcoreEListExtensions.<TypeRef, String>map(_typeRefs, _function);
		String _join = IterableExtensions.join(_map, ",");
		String _plus = ("{" + _join);
		String _plus_1 = (_plus + "}");
		String _modifiersAsString = this.getModifiersAsString();
		return (_plus_1 + _modifiersAsString);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS:
				return ((InternalEList<?>)getCachedComposedMembers()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.COMPOSED_TYPE_REF__TYPE_REFS:
				return ((InternalEList<?>)getTypeRefs()).basicRemove(otherEnd, msgs);
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
			case TypeRefsPackage.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS:
				return getCachedComposedMembers();
			case TypeRefsPackage.COMPOSED_TYPE_REF__ORIGINAL_COMPOSED_TYPE_REF:
				if (resolve) return getOriginalComposedTypeRef();
				return basicGetOriginalComposedTypeRef();
			case TypeRefsPackage.COMPOSED_TYPE_REF__TYPE_REFS:
				return getTypeRefs();
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
			case TypeRefsPackage.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS:
				getCachedComposedMembers().clear();
				getCachedComposedMembers().addAll((Collection<? extends TMember>)newValue);
				return;
			case TypeRefsPackage.COMPOSED_TYPE_REF__ORIGINAL_COMPOSED_TYPE_REF:
				setOriginalComposedTypeRef((ComposedTypeRef)newValue);
				return;
			case TypeRefsPackage.COMPOSED_TYPE_REF__TYPE_REFS:
				getTypeRefs().clear();
				getTypeRefs().addAll((Collection<? extends TypeRef>)newValue);
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
			case TypeRefsPackage.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS:
				getCachedComposedMembers().clear();
				return;
			case TypeRefsPackage.COMPOSED_TYPE_REF__ORIGINAL_COMPOSED_TYPE_REF:
				setOriginalComposedTypeRef((ComposedTypeRef)null);
				return;
			case TypeRefsPackage.COMPOSED_TYPE_REF__TYPE_REFS:
				getTypeRefs().clear();
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
			case TypeRefsPackage.COMPOSED_TYPE_REF__CACHED_COMPOSED_MEMBERS:
				return cachedComposedMembers != null && !cachedComposedMembers.isEmpty();
			case TypeRefsPackage.COMPOSED_TYPE_REF__ORIGINAL_COMPOSED_TYPE_REF:
				return originalComposedTypeRef != null;
			case TypeRefsPackage.COMPOSED_TYPE_REF__TYPE_REFS:
				return typeRefs != null && !typeRefs.isEmpty();
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
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.COMPOSED_TYPE_REF___GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___IS_DYNAMIC: return TypeRefsPackage.COMPOSED_TYPE_REF___IS_DYNAMIC;
				case TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.COMPOSED_TYPE_REF___GET_TYPE_REF_AS_STRING;
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
			case TypeRefsPackage.COMPOSED_TYPE_REF___IS_DYNAMIC:
				return isDynamic();
			case TypeRefsPackage.COMPOSED_TYPE_REF___GET_TYPE_REF_AS_STRING:
				return getTypeRefAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //ComposedTypeRefImpl
