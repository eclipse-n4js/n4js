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

import com.google.common.collect.Iterables;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;

import org.eclipse.n4js.ts.types.internal.MemberByNameAndAccessMap;

import org.eclipse.n4js.utils.EcoreUtilN4;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Container Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ContainerTypeImpl#getOwnedMembersByNameAndAccess <em>Owned Members By Name And Access</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ContainerTypeImpl#getOwnedMembers <em>Owned Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ContainerTypeImpl#getCallableCtor <em>Callable Ctor</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.ContainerTypeImpl#getTypeVars <em>Type Vars</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ContainerTypeImpl<MT extends TMember> extends TypeImpl implements ContainerType<MT> {
	/**
	 * The cached value of the '{@link #getOwnedMembersByNameAndAccess() <em>Owned Members By Name And Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedMembersByNameAndAccess()
	 * @generated
	 * @ordered
	 */
	protected Map<NameAndAccess, ? extends TMember> ownedMembersByNameAndAccess;

	/**
	 * The cached value of the '{@link #getOwnedMembers() <em>Owned Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<MT> ownedMembers;

	/**
	 * The cached value of the '{@link #getCallableCtor() <em>Callable Ctor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCallableCtor()
	 * @generated
	 * @ordered
	 */
	protected TMethod callableCtor;

	/**
	 * The cached value of the '{@link #getTypeVars() <em>Type Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariable> typeVars;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainerTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.CONTAINER_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<NameAndAccess, ? extends TMember> getOwnedMembersByNameAndAccess() {
		return ownedMembersByNameAndAccess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnedMembersByNameAndAccess(Map<NameAndAccess, ? extends TMember> newOwnedMembersByNameAndAccess) {
		Map<NameAndAccess, ? extends TMember> oldOwnedMembersByNameAndAccess = ownedMembersByNameAndAccess;
		ownedMembersByNameAndAccess = newOwnedMembersByNameAndAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS_BY_NAME_AND_ACCESS, oldOwnedMembersByNameAndAccess, ownedMembersByNameAndAccess));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MT> getOwnedMembers() {
		if (ownedMembers == null) {
			ownedMembers = new EObjectContainmentEList<MT>(TMember.class, this, TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS);
		}
		return ownedMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMethod getCallableCtor() {
		return callableCtor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCallableCtor(TMethod newCallableCtor, NotificationChain msgs) {
		TMethod oldCallableCtor = callableCtor;
		callableCtor = newCallableCtor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR, oldCallableCtor, newCallableCtor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCallableCtor(TMethod newCallableCtor) {
		if (newCallableCtor != callableCtor) {
			NotificationChain msgs = null;
			if (callableCtor != null)
				msgs = ((InternalEObject)callableCtor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR, null, msgs);
			if (newCallableCtor != null)
				msgs = ((InternalEObject)newCallableCtor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR, null, msgs);
			msgs = basicSetCallableCtor(newCallableCtor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR, newCallableCtor, newCallableCtor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeVariable> getTypeVars() {
		if (typeVars == null) {
			typeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, TypesPackage.CONTAINER_TYPE__TYPE_VARS);
		}
		return typeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMethod getOwnedCtor() {
		final Function1<TMethod, Boolean> _function = new Function1<TMethod, Boolean>() {
			public Boolean apply(final TMethod it) {
				return Boolean.valueOf(it.isConstructor());
			}
		};
		return IterableExtensions.<TMethod>findFirst(Iterables.<TMethod>filter(this.getOwnedMembers(), TMethod.class), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMember findOwnedMember(final String name) {
		return this.findOwnedMember(name, false, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TMember findOwnedMember(final String name, final boolean writeAccess, final boolean staticAccess) {
		final NameAndAccess nameAndAccess = new NameAndAccess(name, writeAccess, staticAccess);
		return this.getOrCreateOwnedMembersByNameAndAccess().get(nameAndAccess);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<NameAndAccess, ? extends TMember> getOrCreateOwnedMembersByNameAndAccess() {
		Map<NameAndAccess, ? extends TMember> _ownedMembersByNameAndAccess = this.getOwnedMembersByNameAndAccess();
		boolean _tripleEquals = (_ownedMembersByNameAndAccess == null);
		if (_tripleEquals) {
			Map<NameAndAccess, ? extends TMember> _switchResult = null;
			int _size = this.getOwnedMembers().size();
			switch (_size) {
				case 0:
					_switchResult = Collections.<NameAndAccess, TMember>emptyMap();
					break;
				case 1:
					Map<NameAndAccess, ? extends TMember> _xblockexpression = null;
					{
						final MT singleMember = this.getOwnedMembers().get(0);
						final NameAndAccess[] nameAndAccess = NameAndAccess.of(singleMember);
						Map<NameAndAccess, ? extends TMember> _xifexpression = null;
						int _length = nameAndAccess.length;
						boolean _greaterThan = (_length > 1);
						if (_greaterThan) {
							Map<NameAndAccess, TMember> _xblockexpression_1 = null;
							{
								final HashMap<NameAndAccess, TMember> map = new HashMap<NameAndAccess, TMember>();
								map.put(nameAndAccess[0], singleMember);
								map.put(nameAndAccess[1], singleMember);
								_xblockexpression_1 = Collections.<NameAndAccess, TMember>unmodifiableMap(map);
							}
							_xifexpression = _xblockexpression_1;
						}
						else {
							_xifexpression = Collections.<NameAndAccess, MT>singletonMap(nameAndAccess[0], singleMember);
						}
						_xblockexpression = _xifexpression;
					}
					_switchResult = _xblockexpression;
					break;
				default:
					EList<MT> _ownedMembers = this.getOwnedMembers();
					MemberByNameAndAccessMap _memberByNameAndAccessMap = new MemberByNameAndAccessMap(_ownedMembers);
					_switchResult = Collections.<NameAndAccess, TMember>unmodifiableMap(_memberByNameAndAccessMap);
					break;
			}
			final Map<NameAndAccess, ? extends TMember> newRegistry = _switchResult;
			final Procedure0 _function = new Procedure0() {
				public void apply() {
					ContainerTypeImpl.this.setOwnedMembersByNameAndAccess(newRegistry);
				}
			};
			EcoreUtilN4.doWithDeliver(false, _function, this);
		}
		return this.getOwnedMembersByNameAndAccess();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS:
				return ((InternalEList<?>)getOwnedMembers()).basicRemove(otherEnd, msgs);
			case TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR:
				return basicSetCallableCtor(null, msgs);
			case TypesPackage.CONTAINER_TYPE__TYPE_VARS:
				return ((InternalEList<?>)getTypeVars()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS_BY_NAME_AND_ACCESS:
				return getOwnedMembersByNameAndAccess();
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS:
				return getOwnedMembers();
			case TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR:
				return getCallableCtor();
			case TypesPackage.CONTAINER_TYPE__TYPE_VARS:
				return getTypeVars();
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
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS_BY_NAME_AND_ACCESS:
				setOwnedMembersByNameAndAccess((Map<NameAndAccess, ? extends TMember>)newValue);
				return;
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS:
				getOwnedMembers().clear();
				getOwnedMembers().addAll((Collection<? extends MT>)newValue);
				return;
			case TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR:
				setCallableCtor((TMethod)newValue);
				return;
			case TypesPackage.CONTAINER_TYPE__TYPE_VARS:
				getTypeVars().clear();
				getTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
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
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS_BY_NAME_AND_ACCESS:
				setOwnedMembersByNameAndAccess((Map<NameAndAccess, ? extends TMember>)null);
				return;
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS:
				getOwnedMembers().clear();
				return;
			case TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR:
				setCallableCtor((TMethod)null);
				return;
			case TypesPackage.CONTAINER_TYPE__TYPE_VARS:
				getTypeVars().clear();
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
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS_BY_NAME_AND_ACCESS:
				return ownedMembersByNameAndAccess != null;
			case TypesPackage.CONTAINER_TYPE__OWNED_MEMBERS:
				return ownedMembers != null && !ownedMembers.isEmpty();
			case TypesPackage.CONTAINER_TYPE__CALLABLE_CTOR:
				return callableCtor != null;
			case TypesPackage.CONTAINER_TYPE__TYPE_VARS:
				return typeVars != null && !typeVars.isEmpty();
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
			case TypesPackage.CONTAINER_TYPE___GET_OWNED_CTOR:
				return getOwnedCtor();
			case TypesPackage.CONTAINER_TYPE___FIND_OWNED_MEMBER__STRING:
				return findOwnedMember((String)arguments.get(0));
			case TypesPackage.CONTAINER_TYPE___FIND_OWNED_MEMBER__STRING_BOOLEAN_BOOLEAN:
				return findOwnedMember((String)arguments.get(0), (Boolean)arguments.get(1), (Boolean)arguments.get(2));
			case TypesPackage.CONTAINER_TYPE___GET_OR_CREATE_OWNED_MEMBERS_BY_NAME_AND_ACCESS:
				return getOrCreateOwnedMembersByNameAndAccess();
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
		result.append(" (ownedMembersByNameAndAccess: ");
		result.append(ownedMembersByNameAndAccess);
		result.append(')');
		return result.toString();
	}

} //ContainerTypeImpl
