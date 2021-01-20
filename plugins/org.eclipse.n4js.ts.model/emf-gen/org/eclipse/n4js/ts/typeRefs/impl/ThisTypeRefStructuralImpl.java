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

import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping;

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>This Type Ref Structural</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl#getAstStructuralMembers <em>Ast Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl#getStructuralType <em>Structural Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl#getGenStructuralMembers <em>Gen Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl#getPostponedSubstitutions <em>Postponed Substitutions</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ThisTypeRefStructuralImpl extends ThisTypeRefImpl implements ThisTypeRefStructural {
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
	protected ThisTypeRefStructuralImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.THIS_TYPE_REF_STRUCTURAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getAstStructuralMembers() {
		if (astStructuralMembers == null) {
			astStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS);
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE, oldStructuralType, structuralType));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE, oldStructuralType, structuralType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getGenStructuralMembers() {
		if (genStructuralMembers == null) {
			genStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS);
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
			postponedSubstitutions = new EObjectContainmentEList<TypeVariableMapping>(TypeVariableMapping.class, this, TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS);
		}
		return postponedSubstitutions;
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY, oldDefinedTypingStrategy, definedTypingStrategy));
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
			return TypingStrategy.NOMINAL;
		}
		return this.getDefinedTypingStrategy();
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
	public boolean isUseSiteStructuralTyping() {
		return ((this.getTypingStrategy() != TypingStrategy.NOMINAL) && (this.getTypingStrategy() != TypingStrategy.DEFAULT));
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
	public String internalGetTypeRefAsString() {
		TypingStrategy _typingStrategy = this.getTypingStrategy();
		String _plus = (_typingStrategy + "this");
		String _modifiersAsString = this.getModifiersAsString();
		return (_plus + _modifiersAsString);
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
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getAstStructuralMembers()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getGenStructuralMembers()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS:
				return ((InternalEList<?>)getPostponedSubstitutions()).basicRemove(otherEnd, msgs);
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
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS:
				return getAstStructuralMembers();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE:
				if (resolve) return getStructuralType();
				return basicGetStructuralType();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS:
				return getGenStructuralMembers();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS:
				return getPostponedSubstitutions();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY:
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
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				getAstStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)newValue);
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				getGenStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
				getPostponedSubstitutions().addAll((Collection<? extends TypeVariableMapping>)newValue);
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY:
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
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)null);
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
				return;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY:
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
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS:
				return astStructuralMembers != null && !astStructuralMembers.isEmpty();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE:
				return structuralType != null;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS:
				return genStructuralMembers != null && !genStructuralMembers.isEmpty();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS:
				return postponedSubstitutions != null && !postponedSubstitutions.isEmpty();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY:
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
				case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE: return TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE;
				case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS;
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
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS;
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
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY;
				case TypeRefsPackage.TYPE_REF___GET_STRUCTURAL_MEMBERS: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___IS_USE_SITE_STRUCTURAL_TYPING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ThisTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.THIS_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___INTERNAL_GET_TYPE_REF_AS_STRING;
				case TypeRefsPackage.THIS_TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY;
				case TypeRefsPackage.THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___IS_USE_SITE_STRUCTURAL_TYPING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == StructuralTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_STRUCTURAL_MEMBERS: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE: return TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE;
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
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY:
				return getTypingStrategy();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY:
				setTypingStrategy((TypingStrategy)arguments.get(0));
				return null;
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___IS_USE_SITE_STRUCTURAL_TYPING:
				return isUseSiteStructuralTyping();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS:
				return getStructuralMembers();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
			case TypeRefsPackage.THIS_TYPE_REF_STRUCTURAL___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE:
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

} //ThisTypeRefStructuralImpl
