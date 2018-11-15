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

import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameterized Type Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getDeclaredType <em>Declared Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getTypeArgs <em>Type Args</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#isArrayTypeLiteral <em>Array Type Literal</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getAstNamespace <em>Ast Namespace</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getASTNodeOptionalFieldStrategy <em>AST Node Optional Field Strategy</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterizedTypeRefImpl extends BaseTypeRefImpl implements ParameterizedTypeRef {
	/**
	 * The cached value of the '{@link #getDeclaredType() <em>Declared Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredType()
	 * @generated
	 * @ordered
	 */
	protected Type declaredType;

	/**
	 * The cached value of the '{@link #getTypeArgs() <em>Type Args</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeArgument> typeArgs;

	/**
	 * The default value of the '{@link #isArrayTypeLiteral() <em>Array Type Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayTypeLiteral()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_TYPE_LITERAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArrayTypeLiteral() <em>Array Type Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayTypeLiteral()
	 * @generated
	 * @ordered
	 */
	protected boolean arrayTypeLiteral = ARRAY_TYPE_LITERAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAstNamespace() <em>Ast Namespace</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstNamespace()
	 * @generated
	 * @ordered
	 */
	protected ModuleNamespaceVirtualType astNamespace;

	/**
	 * The default value of the '{@link #getASTNodeOptionalFieldStrategy() <em>AST Node Optional Field Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getASTNodeOptionalFieldStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final OptionalFieldStrategy AST_NODE_OPTIONAL_FIELD_STRATEGY_EDEFAULT = OptionalFieldStrategy.OFF;

	/**
	 * The cached value of the '{@link #getASTNodeOptionalFieldStrategy() <em>AST Node Optional Field Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getASTNodeOptionalFieldStrategy()
	 * @generated
	 * @ordered
	 */
	protected OptionalFieldStrategy aSTNodeOptionalFieldStrategy = AST_NODE_OPTIONAL_FIELD_STRATEGY_EDEFAULT;

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
	protected ParameterizedTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getDeclaredType() {
		if (declaredType != null && declaredType.eIsProxy()) {
			InternalEObject oldDeclaredType = (InternalEObject)declaredType;
			declaredType = (Type)eResolveProxy(oldDeclaredType);
			if (declaredType != oldDeclaredType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE, oldDeclaredType, declaredType));
			}
		}
		return declaredType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetDeclaredType() {
		return declaredType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclaredType(Type newDeclaredType) {
		Type oldDeclaredType = declaredType;
		declaredType = newDeclaredType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE, oldDeclaredType, declaredType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeArgument> getTypeArgs() {
		if (typeArgs == null) {
			typeArgs = new EObjectContainmentEList<TypeArgument>(TypeArgument.class, this, TypeRefsPackage.PARAMETERIZED_TYPE_REF__TYPE_ARGS);
		}
		return typeArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArrayTypeLiteral() {
		return arrayTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrayTypeLiteral(boolean newArrayTypeLiteral) {
		boolean oldArrayTypeLiteral = arrayTypeLiteral;
		arrayTypeLiteral = newArrayTypeLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_LITERAL, oldArrayTypeLiteral, arrayTypeLiteral));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleNamespaceVirtualType getAstNamespace() {
		if (astNamespace != null && astNamespace.eIsProxy()) {
			InternalEObject oldAstNamespace = (InternalEObject)astNamespace;
			astNamespace = (ModuleNamespaceVirtualType)eResolveProxy(oldAstNamespace);
			if (astNamespace != oldAstNamespace) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NAMESPACE, oldAstNamespace, astNamespace));
			}
		}
		return astNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleNamespaceVirtualType basicGetAstNamespace() {
		return astNamespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAstNamespace(ModuleNamespaceVirtualType newAstNamespace) {
		ModuleNamespaceVirtualType oldAstNamespace = astNamespace;
		astNamespace = newAstNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NAMESPACE, oldAstNamespace, astNamespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OptionalFieldStrategy getASTNodeOptionalFieldStrategy() {
		return aSTNodeOptionalFieldStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setASTNodeOptionalFieldStrategy(OptionalFieldStrategy newASTNodeOptionalFieldStrategy) {
		OptionalFieldStrategy oldASTNodeOptionalFieldStrategy = aSTNodeOptionalFieldStrategy;
		aSTNodeOptionalFieldStrategy = newASTNodeOptionalFieldStrategy == null ? AST_NODE_OPTIONAL_FIELD_STRATEGY_EDEFAULT : newASTNodeOptionalFieldStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY, oldASTNodeOptionalFieldStrategy, aSTNodeOptionalFieldStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypingStrategy getDefinedTypingStrategy() {
		return definedTypingStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefinedTypingStrategy(TypingStrategy newDefinedTypingStrategy) {
		TypingStrategy oldDefinedTypingStrategy = definedTypingStrategy;
		definedTypingStrategy = newDefinedTypingStrategy == null ? DEFINED_TYPING_STRATEGY_EDEFAULT : newDefinedTypingStrategy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY, oldDefinedTypingStrategy, definedTypingStrategy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypingStrategy getTypingStrategy() {
		TypingStrategy _definedTypingStrategy = this.getDefinedTypingStrategy();
		boolean _tripleEquals = (_definedTypingStrategy == TypingStrategy.DEFAULT);
		if (_tripleEquals) {
			boolean _isDefSiteStructuralTyping = this.isDefSiteStructuralTyping();
			if (_isDefSiteStructuralTyping) {
				return TypingStrategy.STRUCTURAL;
			}
			else {
				return TypingStrategy.NOMINAL;
			}
		}
		return this.getDefinedTypingStrategy();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean containsWildcards() {
		return ((this.getTypeArgs().isEmpty() && (!this.getDeclaredType().isGeneric())) || IterableExtensions.<TypeArgument>exists(this.getTypeArgs(), new Function1<TypeArgument, Boolean>() {
			public Boolean apply(final TypeArgument it) {
				return Boolean.valueOf(it.containsWildcards());
			}
		}));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTypeRefAsString() {
		Type _declaredType = this.getDeclaredType();
		String _rawTypeAsString = null;
		if (_declaredType!=null) {
			_rawTypeAsString=_declaredType.getRawTypeAsString();
		}
		String _xifexpression = null;
		int _version = this.getVersion();
		boolean _greaterThan = (_version > 0);
		if (_greaterThan) {
			int _version_1 = this.getVersion();
			_xifexpression = ("#" + Integer.valueOf(_version_1));
		}
		else {
			String _xifexpression_1 = null;
			boolean _isEmpty = this.getTypeArgs().isEmpty();
			if (_isEmpty) {
				_xifexpression_1 = "";
			}
			else {
				final Function1<TypeArgument, String> _function = new Function1<TypeArgument, String>() {
					public String apply(final TypeArgument it) {
						return it.getTypeRefAsString();
					}
				};
				String _join = IterableExtensions.join(XcoreEListExtensions.<TypeArgument, String>map(this.getTypeArgs(), _function), ",");
				String _plus = ("<" + _join);
				_xifexpression_1 = (_plus + ">");
			}
			String _plus_1 = ("" + _xifexpression_1);
			String _modifiersAsString = this.getModifiersAsString();
			_xifexpression = (_plus_1 + _modifiersAsString);
		}
		return (_rawTypeAsString + _xifexpression);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isParameterized() {
		boolean _isEmpty = this.getTypeArgs().isEmpty();
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGeneric() {
		return ((this.getDeclaredType() != null) && this.getDeclaredType().isGeneric());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRaw() {
		return (this.isGeneric() && (this.getTypeArgs().size() < this.getDeclaredType().getTypeVars().size()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean containsUnboundTypeVariables() {
		return (((this.getDeclaredType() instanceof TypeVariable) || ((!this.isParameterized()) && this.getDeclaredType().isGeneric())) || IterableExtensions.<TypeArgument>exists(this.getTypeArgs(), new Function1<TypeArgument, Boolean>() {
			public Boolean apply(final TypeArgument it) {
				return Boolean.valueOf(it.containsUnboundTypeVariables());
			}
		}));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUseSiteStructuralTyping() {
		return ((this.getDefinedTypingStrategy() != TypingStrategy.NOMINAL) && 
			(this.getDefinedTypingStrategy() != TypingStrategy.DEFAULT));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDefSiteStructuralTyping() {
		Type _declaredType = this.getDeclaredType();
		if ((_declaredType instanceof TN4Classifier)) {
			Type _declaredType_1 = this.getDeclaredType();
			TypingStrategy _typingStrategy = ((TN4Classifier) _declaredType_1).getTypingStrategy();
			return (_typingStrategy == TypingStrategy.STRUCTURAL);
		}
		Type _declaredType_2 = this.getDeclaredType();
		if ((_declaredType_2 instanceof TStructuralType)) {
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
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__TYPE_ARGS:
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE:
				if (resolve) return getDeclaredType();
				return basicGetDeclaredType();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__TYPE_ARGS:
				return getTypeArgs();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_LITERAL:
				return isArrayTypeLiteral();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NAMESPACE:
				if (resolve) return getAstNamespace();
				return basicGetAstNamespace();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY:
				return getASTNodeOptionalFieldStrategy();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY:
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE:
				setDeclaredType((Type)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__TYPE_ARGS:
				getTypeArgs().clear();
				getTypeArgs().addAll((Collection<? extends TypeArgument>)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_LITERAL:
				setArrayTypeLiteral((Boolean)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NAMESPACE:
				setAstNamespace((ModuleNamespaceVirtualType)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY:
				setASTNodeOptionalFieldStrategy((OptionalFieldStrategy)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY:
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE:
				setDeclaredType((Type)null);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__TYPE_ARGS:
				getTypeArgs().clear();
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_LITERAL:
				setArrayTypeLiteral(ARRAY_TYPE_LITERAL_EDEFAULT);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NAMESPACE:
				setAstNamespace((ModuleNamespaceVirtualType)null);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY:
				setASTNodeOptionalFieldStrategy(AST_NODE_OPTIONAL_FIELD_STRATEGY_EDEFAULT);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY:
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE:
				return declaredType != null;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__TYPE_ARGS:
				return typeArgs != null && !typeArgs.isEmpty();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_LITERAL:
				return arrayTypeLiteral != ARRAY_TYPE_LITERAL_EDEFAULT;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NAMESPACE:
				return astNamespace != null;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY:
				return aSTNodeOptionalFieldStrategy != AST_NODE_OPTIONAL_FIELD_STRATEGY_EDEFAULT;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY:
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
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING;
				case TypeRefsPackage.TYPE_ARGUMENT___CONTAINS_WILDCARDS: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS;
				case TypeRefsPackage.TYPE_ARGUMENT___CONTAINS_UNBOUND_TYPE_VARIABLES: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___IS_GENERIC: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_GENERIC;
				case TypeRefsPackage.TYPE_REF___IS_PARAMETERIZED: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;
				case TypeRefsPackage.TYPE_REF___IS_RAW: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_RAW;
				case TypeRefsPackage.TYPE_REF___GET_TYPE_REF_AS_STRING: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING;
				case TypeRefsPackage.TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY;
				case TypeRefsPackage.TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;
				case TypeRefsPackage.TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY:
				return getTypingStrategy();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS:
				return containsWildcards();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING:
				return getTypeRefAsString();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED:
				return isParameterized();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_GENERIC:
				return isGeneric();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_RAW:
				return isRaw();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES:
				return containsUnboundTypeVariables();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING:
				return isUseSiteStructuralTyping();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING:
				return isDefSiteStructuralTyping();
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
		result.append(" (arrayTypeLiteral: ");
		result.append(arrayTypeLiteral);
		result.append(", aSTNodeOptionalFieldStrategy: ");
		result.append(aSTNodeOptionalFieldStrategy);
		result.append(", definedTypingStrategy: ");
		result.append(definedTypingStrategy);
		result.append(')');
		return result.toString();
	}

} //ParameterizedTypeRefImpl
