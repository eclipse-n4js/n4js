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

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;
import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef;
import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

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
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getDeclaredTypeAsText <em>Declared Type As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getDeclaredTypeArgs <em>Declared Type Args</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#isArrayTypeExpression <em>Array Type Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#isArrayNTypeExpression <em>Array NType Expression</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl#getNamespaceLikeRefs <em>Namespace Like Refs</em>}</li>
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
	 * The default value of the '{@link #getDeclaredTypeAsText() <em>Declared Type As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAsText()
	 * @generated
	 * @ordered
	 */
	protected static final String DECLARED_TYPE_AS_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeclaredTypeAsText() <em>Declared Type As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeAsText()
	 * @generated
	 * @ordered
	 */
	protected String declaredTypeAsText = DECLARED_TYPE_AS_TEXT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDeclaredTypeArgs() <em>Declared Type Args</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredTypeArgs()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeArgument> declaredTypeArgs;

	/**
	 * The default value of the '{@link #isArrayTypeExpression() <em>Array Type Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayTypeExpression()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_TYPE_EXPRESSION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArrayTypeExpression() <em>Array Type Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayTypeExpression()
	 * @generated
	 * @ordered
	 */
	protected boolean arrayTypeExpression = ARRAY_TYPE_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #isArrayNTypeExpression() <em>Array NType Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayNTypeExpression()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_NTYPE_EXPRESSION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArrayNTypeExpression() <em>Array NType Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArrayNTypeExpression()
	 * @generated
	 * @ordered
	 */
	protected boolean arrayNTypeExpression = ARRAY_NTYPE_EXPRESSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNamespaceLikeRefs() <em>Namespace Like Refs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespaceLikeRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<NamespaceLikeRef> namespaceLikeRefs;

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
	@Override
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
	@Override
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
	@Override
	public String getDeclaredTypeAsText() {
		return declaredTypeAsText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredTypeAsText(String newDeclaredTypeAsText) {
		String oldDeclaredTypeAsText = declaredTypeAsText;
		declaredTypeAsText = newDeclaredTypeAsText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_AS_TEXT, oldDeclaredTypeAsText, declaredTypeAsText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeArgument> getDeclaredTypeArgs() {
		if (declaredTypeArgs == null) {
			declaredTypeArgs = new EObjectContainmentEList<TypeArgument>(TypeArgument.class, this, TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_ARGS);
		}
		return declaredTypeArgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isArrayTypeExpression() {
		return arrayTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArrayTypeExpression(boolean newArrayTypeExpression) {
		boolean oldArrayTypeExpression = arrayTypeExpression;
		arrayTypeExpression = newArrayTypeExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION, oldArrayTypeExpression, arrayTypeExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isArrayNTypeExpression() {
		return arrayNTypeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setArrayNTypeExpression(boolean newArrayNTypeExpression) {
		boolean oldArrayNTypeExpression = arrayNTypeExpression;
		arrayNTypeExpression = newArrayNTypeExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_NTYPE_EXPRESSION, oldArrayNTypeExpression, arrayNTypeExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NamespaceLikeRef> getNamespaceLikeRefs() {
		if (namespaceLikeRefs == null) {
			namespaceLikeRefs = new EObjectContainmentEList<NamespaceLikeRef>(NamespaceLikeRef.class, this, TypeRefsPackage.PARAMETERIZED_TYPE_REF__NAMESPACE_LIKE_REFS);
		}
		return namespaceLikeRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OptionalFieldStrategy getASTNodeOptionalFieldStrategy() {
		return aSTNodeOptionalFieldStrategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY, oldDefinedTypingStrategy, definedTypingStrategy));
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
	@Override
	public EList<TypeArgument> getTypeArgsWithDefaults() {
		final Type declType = this.getDeclaredType();
		if (((declType != null) && declType.isGeneric())) {
			final EList<TypeArgument> declTypeArgs = this.getDeclaredTypeArgs();
			final int declTypeArgsCount = declTypeArgs.size();
			final EList<TypeVariable> typeParams = declType.getTypeVars();
			final int typeParamCount = typeParams.size();
			if ((typeParamCount > declTypeArgsCount)) {
				final TypeArgument[] args = new TypeArgument[typeParamCount];
				for (int i = 0; (i < typeParamCount); i++) {
					if ((i < declTypeArgsCount)) {
						args[i] = declTypeArgs.get(i);
					}
					else {
						final TypeRef defArg = typeParams.get(i).getDefaultArgument();
						if ((defArg == null)) {
							return declTypeArgs;
						}
						args[i] = defArg;
					}
				}
				return XcoreCollectionLiterals.<TypeArgument>newImmutableEList(args);
			}
		}
		return this.getDeclaredTypeArgs();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString() {
		Type _declaredType = this.getDeclaredType();
		String _rawTypeAsString = null;
		if (_declaredType!=null) {
			_rawTypeAsString=_declaredType.getRawTypeAsString();
		}
		String _xifexpression = null;
		boolean _isEmpty = this.getDeclaredTypeArgs().isEmpty();
		if (_isEmpty) {
			_xifexpression = "";
		}
		else {
			final Function1<TypeArgument, String> _function = new Function1<TypeArgument, String>() {
				public String apply(final TypeArgument it) {
					return it.getTypeRefAsString();
				}
			};
			String _join = IterableExtensions.join(XcoreEListExtensions.<TypeArgument, String>map(this.getDeclaredTypeArgs(), _function), ",");
			String _plus = ("<" + _join);
			_xifexpression = (_plus + ">");
		}
		String _plus_1 = (_rawTypeAsString + _xifexpression);
		String _modifiersAsString = this.getModifiersAsString();
		return (_plus_1 + _modifiersAsString);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isParameterized() {
		boolean _isEmpty = this.getDeclaredTypeArgs().isEmpty();
		return (!_isEmpty);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGeneric() {
		return ((this.getDeclaredType() != null) && this.getDeclaredType().isGeneric());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isRaw() {
		boolean _isGeneric = this.isGeneric();
		if (_isGeneric) {
			final Function1<TypeVariable, Boolean> _function = new Function1<TypeVariable, Boolean>() {
				public Boolean apply(final TypeVariable it) {
					boolean _isOptional = it.isOptional();
					return Boolean.valueOf((!_isOptional));
				}
			};
			final int mandatoryTypeParamsCount = IterableExtensions.size(IterableExtensions.<TypeVariable>filter(this.getDeclaredType().getTypeVars(), _function));
			int _size = this.getDeclaredTypeArgs().size();
			return (_size < mandatoryTypeParamsCount);
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
		return ((this.getDefinedTypingStrategy() != TypingStrategy.NOMINAL) && 
			(this.getDefinedTypingStrategy() != TypingStrategy.DEFAULT));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_ARGS:
				return ((InternalEList<?>)getDeclaredTypeArgs()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__NAMESPACE_LIKE_REFS:
				return ((InternalEList<?>)getNamespaceLikeRefs()).basicRemove(otherEnd, msgs);
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_AS_TEXT:
				return getDeclaredTypeAsText();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_ARGS:
				return getDeclaredTypeArgs();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION:
				return isArrayTypeExpression();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_NTYPE_EXPRESSION:
				return isArrayNTypeExpression();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__NAMESPACE_LIKE_REFS:
				return getNamespaceLikeRefs();
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_AS_TEXT:
				setDeclaredTypeAsText((String)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_ARGS:
				getDeclaredTypeArgs().clear();
				getDeclaredTypeArgs().addAll((Collection<? extends TypeArgument>)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION:
				setArrayTypeExpression((Boolean)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_NTYPE_EXPRESSION:
				setArrayNTypeExpression((Boolean)newValue);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__NAMESPACE_LIKE_REFS:
				getNamespaceLikeRefs().clear();
				getNamespaceLikeRefs().addAll((Collection<? extends NamespaceLikeRef>)newValue);
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_AS_TEXT:
				setDeclaredTypeAsText(DECLARED_TYPE_AS_TEXT_EDEFAULT);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_ARGS:
				getDeclaredTypeArgs().clear();
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION:
				setArrayTypeExpression(ARRAY_TYPE_EXPRESSION_EDEFAULT);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_NTYPE_EXPRESSION:
				setArrayNTypeExpression(ARRAY_NTYPE_EXPRESSION_EDEFAULT);
				return;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__NAMESPACE_LIKE_REFS:
				getNamespaceLikeRefs().clear();
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_AS_TEXT:
				return DECLARED_TYPE_AS_TEXT_EDEFAULT == null ? declaredTypeAsText != null : !DECLARED_TYPE_AS_TEXT_EDEFAULT.equals(declaredTypeAsText);
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE_ARGS:
				return declaredTypeArgs != null && !declaredTypeArgs.isEmpty();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION:
				return arrayTypeExpression != ARRAY_TYPE_EXPRESSION_EDEFAULT;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_NTYPE_EXPRESSION:
				return arrayNTypeExpression != ARRAY_NTYPE_EXPRESSION_EDEFAULT;
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF__NAMESPACE_LIKE_REFS:
				return namespaceLikeRefs != null && !namespaceLikeRefs.isEmpty();
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
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___IS_GENERIC: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_GENERIC;
				case TypeRefsPackage.TYPE_REF___IS_PARAMETERIZED: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;
				case TypeRefsPackage.TYPE_REF___IS_RAW: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_RAW;
				case TypeRefsPackage.TYPE_REF___GET_TYPE_ARGS_WITH_DEFAULTS: return TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS_WITH_DEFAULTS;
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
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS_WITH_DEFAULTS:
				return getTypeArgsWithDefaults();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED:
				return isParameterized();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_GENERIC:
				return isGeneric();
			case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_RAW:
				return isRaw();
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
		result.append(" (declaredTypeAsText: ");
		result.append(declaredTypeAsText);
		result.append(", arrayTypeExpression: ");
		result.append(arrayTypeExpression);
		result.append(", arrayNTypeExpression: ");
		result.append(arrayNTypeExpression);
		result.append(", aSTNodeOptionalFieldStrategy: ");
		result.append(aSTNodeOptionalFieldStrategy);
		result.append(", definedTypingStrategy: ");
		result.append(definedTypingStrategy);
		result.append(')');
		return result.toString();
	}

} //ParameterizedTypeRefImpl
