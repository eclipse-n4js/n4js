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

import com.google.common.base.Objects;

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

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.transpiler.im.ImPackage;
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM;
import org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping;

import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameterized Type Ref Structural IM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl#getAstStructuralMembers <em>Ast Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl#getStructuralType <em>Structural Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl#getGenStructuralMembers <em>Gen Structural Members</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl#getPostponedSubstitutions <em>Postponed Substitutions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterizedTypeRefStructural_IMImpl extends ParameterizedTypeRef_IMImpl implements ParameterizedTypeRefStructural_IM {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterizedTypeRefStructural_IMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImPackage.Literals.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getAstStructuralMembers() {
		if (astStructuralMembers == null) {
			astStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS);
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE, oldStructuralType, structuralType));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE, oldStructuralType, structuralType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TStructMember> getGenStructuralMembers() {
		if (genStructuralMembers == null) {
			genStructuralMembers = new EObjectContainmentEList<TStructMember>(TStructMember.class, this, ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS);
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
			postponedSubstitutions = new EObjectContainmentEList<TypeVariableMapping>(TypeVariableMapping.class, this, ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS);
		}
		return postponedSubstitutions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SymbolTableEntry getDeclaredType_IM() {
		return this.getRewiredTarget();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredType_IM(final SymbolTableEntry target) {
		this.setRewiredTarget(target);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type getDeclaredType() {
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredType(final Type ix) {
		if ((ix != null)) {
			throw new IllegalArgumentException("ParameterizedTypeRef_IM cannot accept types. Use #declaredType_IM.");
		}
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
			return TypingStrategy.STRUCTURAL;
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
		boolean _equals = Objects.equal(typingStrategy, TypingStrategy.NOMINAL);
		if (_equals) {
			throw new IllegalArgumentException("cannot set structural type reference to nominal");
		}
		this.setDefinedTypingStrategy(typingStrategy);
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
	public String getTypeRefAsString() {
		TypingStrategy _typingStrategy = this.getTypingStrategy();
		Type _declaredType = this.getDeclaredType();
		String _rawTypeAsString = null;
		if (_declaredType!=null) {
			_rawTypeAsString=_declaredType.getRawTypeAsString();
		}
		String _plus = (_typingStrategy + _rawTypeAsString);
		String _xifexpression = null;
		boolean _isEmpty = this.getTypeArgs().isEmpty();
		if (_isEmpty) {
			_xifexpression = "";
		}
		else {
			final Function1<TypeArgument, String> _function = new Function1<TypeArgument, String>() {
				public String apply(final TypeArgument it) {
					return it.getTypeRefAsString();
				}
			};
			String _join = IterableExtensions.join(XcoreEListExtensions.<TypeArgument, String>map(this.getTypeArgs(), _function), ",");
			String _plus_1 = ("<" + _join);
			_xifexpression = (_plus_1 + ">");
		}
		String _plus_2 = (_plus + _xifexpression);
		String _xifexpression_1 = null;
		boolean _isEmpty_1 = this.getStructuralMembers().isEmpty();
		if (_isEmpty_1) {
			_xifexpression_1 = "";
		}
		else {
			final Function1<TStructMember, String> _function_1 = new Function1<TStructMember, String>() {
				public String apply(final TStructMember it) {
					return it.getMemberAsString();
				}
			};
			String _join_1 = IterableExtensions.join(XcoreEListExtensions.<TStructMember, String>map(this.getStructuralMembers(), _function_1), "; ");
			String _plus_3 = (" with { " + _join_1);
			String _plus_4 = (_plus_3 + " }");
			String _xifexpression_2 = null;
			boolean _isEmpty_2 = this.getPostponedSubstitutions().isEmpty();
			if (_isEmpty_2) {
				_xifexpression_2 = "";
			}
			else {
				final Function1<TypeVariableMapping, String> _function_2 = new Function1<TypeVariableMapping, String>() {
					public String apply(final TypeVariableMapping it) {
						String _typeAsString = it.getTypeVar().getTypeAsString();
						String _plus = (_typeAsString + "->");
						String _typeRefAsString = it.getTypeArg().getTypeRefAsString();
						return (_plus + _typeRefAsString);
					}
				};
				String _join_2 = IterableExtensions.join(XcoreEListExtensions.<TypeVariableMapping, String>map(this.getPostponedSubstitutions(), _function_2), ", ");
				String _plus_5 = (" [[" + _join_2);
				_xifexpression_2 = (_plus_5 + "]]");
			}
			_xifexpression_1 = (_plus_4 + _xifexpression_2);
		}
		return (_plus_2 + _xifexpression_1);
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
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getAstStructuralMembers()).basicRemove(otherEnd, msgs);
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				return ((InternalEList<?>)getGenStructuralMembers()).basicRemove(otherEnd, msgs);
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
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
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				return getAstStructuralMembers();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				if (resolve) return getStructuralType();
				return basicGetStructuralType();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				return getGenStructuralMembers();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				return getPostponedSubstitutions();
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
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				getAstStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)newValue);
				return;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				getGenStructuralMembers().addAll((Collection<? extends TStructMember>)newValue);
				return;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
				getPostponedSubstitutions().addAll((Collection<? extends TypeVariableMapping>)newValue);
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
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				getAstStructuralMembers().clear();
				return;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				setStructuralType((TStructuralType)null);
				return;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				getGenStructuralMembers().clear();
				return;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				getPostponedSubstitutions().clear();
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
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS:
				return astStructuralMembers != null && !astStructuralMembers.isEmpty();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE:
				return structuralType != null;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS:
				return genStructuralMembers != null && !genStructuralMembers.isEmpty();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS:
				return postponedSubstitutions != null && !postponedSubstitutions.isEmpty();
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
				case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS;
				case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE: return TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE;
				case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS;
				case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS: return TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedTypeRefStructural.class) {
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
		if (baseClass == StructuralTypeRef.class) {
			switch (baseFeatureID) {
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedTypeRefStructural.class) {
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
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_REF_AS_STRING;
				case TypeRefsPackage.TYPE_ARGUMENT___GET_DECLARED_TYPE: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_REF_AS_STRING;
				case TypeRefsPackage.TYPE_REF___GET_TYPING_STRATEGY: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.TYPE_REF___GET_STRUCTURAL_MEMBERS: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ParameterizedTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ParameterizedTypeRef_IM.class) {
			switch (baseOperationID) {
				case ImPackage.PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE_IM: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE_IM;
				case ImPackage.PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY;
				case ImPackage.PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE;
				case ImPackage.PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE__TYPE: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE__TYPE;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == StructuralTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_TYPING_STRATEGY: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___GET_STRUCTURAL_MEMBERS: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.STRUCTURAL_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE;
				default: return -1;
			}
		}
		if (baseClass == ParameterizedTypeRefStructural.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPE_REF_AS_STRING: return ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_REF_AS_STRING;
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
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE_IM:
				return getDeclaredType_IM();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY:
				setDeclaredType_IM((SymbolTableEntry)arguments.get(0));
				return null;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE:
				return getDeclaredType();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE__TYPE:
				setDeclaredType((Type)arguments.get(0));
				return null;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY:
				return getTypingStrategy();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY:
				setTypingStrategy((TypingStrategy)arguments.get(0));
				return null;
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS:
				return getStructuralMembers();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_REF_AS_STRING:
				return getTypeRefAsString();
			case ImPackage.PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE:
				return hasPostponedSubstitutionFor((TypeVariable)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ParameterizedTypeRefStructural_IMImpl
