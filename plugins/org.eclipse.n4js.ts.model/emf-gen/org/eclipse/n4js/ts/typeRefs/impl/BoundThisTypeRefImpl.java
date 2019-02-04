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

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping;
import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bound This Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl#getAstStructuralMembers <em>Ast Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl#getStructuralType <em>Structural Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl#getGenStructuralMembers <em>Gen Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl#getPostponedSubstitutions <em>Postponed Substitutions</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl#getActualThisTypeRef <em>Actual This Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BoundThisTypeRefImpl extends ThisTypeRefImpl implements BoundThisTypeRef {
	/**
	 * The cached value of the '{@link #getAstStructuralMembers() <em>Ast Structural Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstStructuralMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TStructMember> astStructuralMembers;

	/**
	 * The cached value of the '{@link #getStructuralType() <em>Structural Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructuralType()
	 * @generated
	 * @ordered
	 */
	protected TStructuralType structuralType;

	/**
	 * The cached value of the '{@link #getGenStructuralMembers() <em>Gen Structural Members</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenStructuralMembers()
	 * @generated
	 * @ordered
	 */
	protected EList<TStructMember> genStructuralMembers;

	/**
	 * The cached value of the '{@link #getPostponedSubstitutions() <em>Postponed Substitutions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostponedSubstitutions()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariableMapping> postponedSubstitutions;

	/**
	 * The cached value of the '{@link #getActualThisTypeRef() <em>Actual This Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualThisTypeRef()
	 * @generated
	 * @ordered
	 */
	protected ParameterizedTypeRef actualThisTypeRef;

	/**
	 * The default value of the '{@link #getDefinedTypingStrategy() <em>Defined Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final TypingStrategy DEFINED_TYPING_STRATEGY_EDEFAULT = TypingStrategy.DEFAULT;

	/**
	 * The cached value of the '{@link #getDefinedTypingStrategy() <em>Defined Typing Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinedTypingStrategy()
	 * @generated
	 * @ordered
	 */
	protected TypingStrategy definedTypingStrategy = DEFINED_TYPING_STRATEGY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BoundThisTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.BOUND_THIS_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getAstStructuralMembers() {
		if (astStructuralMembers == null) {
			astStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS);
		}
		return astStructuralMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TStructuralType getStructuralType() {
		if (structuralType != null && structuralType.eIsProxy()) {
			InternalEObject oldStructuralType = (InternalEObject)structuralType;
			structuralType = (TStructuralType)eResolveProxy(oldStructuralType);
			if (structuralType != oldStructuralType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE, oldStructuralType, structuralType));
			}
		}
		return structuralType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TStructuralType basicGetStructuralType() {
		return structuralType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStructuralType(TStructuralType newStructuralType) {
		TStructuralType oldStructuralType = structuralType;
		structuralType = newStructuralType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE, oldStructuralType, structuralType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getGenStructuralMembers() {
		if (genStructuralMembers == null) {
			genStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS);
		}
		return genStructuralMembers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariableMapping> getPostponedSubstitutions() {
		if (postponedSubstitutions == null) {
			postponedSubstitutions = new EObjectContainmentEList<TypeVariableMapping>(TypeVariableMapping.class, this, TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS);
		}
		return postponedSubstitutions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef getActualThisTypeRef() {
		return actualThisTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActualThisTypeRef(ParameterizedTypeRef newActualThisTypeRef, NotificationChain msgs) {
		ParameterizedTypeRef oldActualThisTypeRef = actualThisTypeRef;
		actualThisTypeRef = newActualThisTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF, oldActualThisTypeRef, newActualThisTypeRef);
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
	public void setActualThisTypeRef(ParameterizedTypeRef newActualThisTypeRef) {
		if (newActualThisTypeRef != actualThisTypeRef) {
			NotificationChain msgs = null;
			if (actualThisTypeRef != null)
				msgs = ((InternalEObject)actualThisTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF, null, msgs);
			if (newActualThisTypeRef != null)
				msgs = ((InternalEObject)newActualThisTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF, null, msgs);
			msgs = basicSetActualThisTypeRef(newActualThisTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF, newActualThisTypeRef, newActualThisTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getDefinedTypingStrategy() {
		return definedTypingStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedTypingStrategy(TypingStrategy newDefinedTypingStrategy) {
		TypingStrategy oldDefinedTypingStrategy = definedTypingStrategy;
		definedTypingStrategy = newDefinedTypingStrategy == null ? DEFINED_TYPING_STRATEGY_EDEFAULT : newDefinedTypingStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.BOUND_THIS_TYPE_REF__DEFINED_TYPING_STRATEGY, oldDefinedTypingStrategy, definedTypingStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getTypingStrategy() {
		TypingStrategy _definedTypingStrategy = this.getDefinedTypingStrategy();
		boolean _tripleEquals = (_definedTypingStrategy == TypingStrategy.DEFAULT);
		if (_tripleEquals) {
			return this.getActualThisTypeRef().getTypingStrategy();
		}
		else {
			return this.getDefinedTypingStrategy();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTypingStrategy(final TypingStrategy typingStrategy) {
		this.setDefinedTypingStrategy(typingStrategy);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTypeRefAsString() {
		TypingStrategy _typingStrategy = this.getTypingStrategy();
		String _plus = (_typingStrategy + "this[");
		String _typeRefAsString = this.getActualThisTypeRef().getTypeRefAsString();
		String _plus_1 = (_plus + _typeRefAsString);
		String _plus_2 = (_plus_1 + "]");
		String _modifiersAsString = this.getModifiersAsString();
		return (_plus_2 + _modifiersAsString);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ParameterizedTypeRef getDeclaredUpperBound() {
		return this.getActualThisTypeRef();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefSiteStructuralTyping() {
		ParameterizedTypeRef _actualThisTypeRef = this.getActualThisTypeRef();
		if ((_actualThisTypeRef instanceof TN4Classifier)) {
			ParameterizedTypeRef _actualThisTypeRef_1 = this.getActualThisTypeRef();
			TypingStrategy _typingStrategy = ((TN4Classifier) _actualThisTypeRef_1).getTypingStrategy();
			return (_typingStrategy == TypingStrategy.STRUCTURAL);
		}
		ParameterizedTypeRef _actualThisTypeRef_2 = this.getActualThisTypeRef();
		if ((_actualThisTypeRef_2 instanceof TStructuralType)) {
			return true;
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseSiteStructuralTyping() {
		return ((this.getDefinedTypingStrategy() != TypingStrategy.DEFAULT) && 
			(this.getDefinedTypingStrategy() != TypingStrategy.NOMINAL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getStructuralMembers() {
		EList<TStructMember> _xifexpression = null;
		TStructuralType _structuralType = this.getStructuralType();
		boolean _tripleNotEquals = (_structuralType != null);
		if (_tripleNotEquals) {
			_xifexpression = this.getStructuralType().getOwnedMembers();
		}
		else {
			EList<TStructMember> _xifexpression_1 = null;
			boolean _isEmpty = this.getAstStructuralMembers().isEmpty();
			boolean _not = (!_isEmpty);
			if (_not) {
				_xifexpression_1 = this.getAstStructuralMembers();
			}
			else {
				_xifexpression_1 = this.getGenStructuralMembers();
			}
			_xifexpression = _xifexpression_1;
		}
		return ECollections.<TStructMember>unmodifiableEList(_xifexpression);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getVersion() {
		return this.getActualThisTypeRef().getVersion();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean hasPostponedSubstitutionFor(final TypeVariable typeVar) {
		final Function1<TypeVariableMapping, Boolean> _function = new Function1<TypeVariableMapping, Boolean>() {
			public Boolean apply(final TypeVariableMapping m) {
				TypeVariable _typeVar = null;
				if (m!=null) {
					_typeVar=m.getTypeVar();
				}
				return Boolean.valueOf((_typeVar == typeVar));
			}
		};
		return IterableExtensions.<TypeVariableMapping>exists(this.getPostponedSubstitutions(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getAstStructuralMembers()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getGenStructuralMembers()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS:
				return ((InternalEList<?>)getPostponedSubstitutions()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF:
				return basicSetActualThisTypeRef(null, msgs);
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
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS:
				return getAstStructuralMembers();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE:
				if (resolve) return getStructuralType();
				return basicGetStructuralType();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS:
				return getGenStructuralMembers();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS:
				return getPostponedSubstitutions();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF:
				return getActualThisTypeRef();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__DEFINED_TYPING_STRATEGY:
				return getDefinedTypingStrategy();
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
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				getAstStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)newValue);
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				getGenStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
				getPostponedSubstitutions().addAll((Collection<? extends TypeVariableMapping>)newValue);
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF:
				setActualThisTypeRef((ParameterizedTypeRef)newValue);
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__DEFINED_TYPING_STRATEGY:
				setDefinedTypingStrategy((TypingStrategy)newValue);
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
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)null);
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF:
				setActualThisTypeRef((ParameterizedTypeRef)null);
				return;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__DEFINED_TYPING_STRATEGY:
				setDefinedTypingStrategy(DEFINED_TYPING_STRATEGY_EDEFAULT);
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
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS:
				return astStructuralMembers != null && !astStructuralMembers.isEmpty();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE:
				return structuralType != null;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS:
				return genStructuralMembers != null && !genStructuralMembers.isEmpty();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS:
				return postponedSubstitutions != null && !postponedSubstitutions.isEmpty();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF:
				return actualThisTypeRef != null;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF__DEFINED_TYPING_STRATEGY:
				return definedTypingStrategy != DEFINED_TYPING_STRATEGY_EDEFAULT;
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
		if (baseClass == StructuralTypeRef.class) {
			switch (derivedFeatureID) {
				case TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE: return TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE;
				case TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS;
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
		if (baseClass == StructuralTypeRef.class) {
			switch (baseFeatureID) {
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS: return TypeRefsPackage.BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE: return TypeRefsPackage.BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS: return TypeRefsPackage.BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS: return TypeRefsPackage.BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS;
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
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == Versionable.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.VERSIONABLE___GET_VERSION: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___GET_DECLARED_UPPER_BOUND: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_DECLARED_UPPER_BOUND;
				case TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPE_REF_AS_STRING;
				case TypeRefsPackage.TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPING_STRATEGY;
				case TypeRefsPackage.TYPE_REF___GET_STRUCTURAL_MEMBERS: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.BOUND_THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;
				case TypeRefsPackage.TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.BOUND_THIS_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;
				case TypeRefsPackage.TYPE_REF___GET_VERSION: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_VERSION;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ThisTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.THIS_TYPE_REF___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPE_REF_AS_STRING;
				case TypeRefsPackage.THIS_TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPING_STRATEGY;
				case TypeRefsPackage.THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.BOUND_THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == StructuralTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPING_STRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY: return TypeRefsPackage.BOUND_THIS_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_STRUCTURAL_MEMBERS: return TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE: return TypeRefsPackage.BOUND_THIS_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE;
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
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPING_STRATEGY:
				return getTypingStrategy();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY:
				setTypingStrategy((TypingStrategy)arguments.get(0));
				return null;
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_TYPE_REF_AS_STRING:
				return getTypeRefAsString();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_DECLARED_UPPER_BOUND:
				return getDeclaredUpperBound();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING:
				return isDefSiteStructuralTyping();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING:
				return isUseSiteStructuralTyping();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS:
				return getStructuralMembers();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___GET_VERSION:
				return getVersion();
			case TypeRefsPackage.BOUND_THIS_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE:
				return hasPostponedSubstitutionFor((TypeVariable)arguments.get(0));
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
		result.append(" (definedTypingStrategy: ");
		result.append(definedTypingStrategy);
		result.append(')');
		return result.toString();
	}

} //BoundThisTypeRefImpl
