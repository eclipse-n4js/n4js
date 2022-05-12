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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TypeVariable;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Type Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#isBinding <em>Binding</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#getDeclaredFunction <em>Declared Function</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#getDeclaredThisType <em>Declared This Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#getOwnedTypeVars <em>Owned Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#getUnboundTypeVars <em>Unbound Type Vars</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#getUnboundTypeVarsUpperBounds <em>Unbound Type Vars Upper Bounds</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#getFpars <em>Fpars</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#getReturnTypeRef <em>Return Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl#isReturnValueMarkedOptional <em>Return Value Marked Optional</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FunctionTypeExpressionImpl extends TypeRefImpl implements FunctionTypeExpression {
	/**
	 * The default value of the '{@link #isBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBinding()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BINDING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBinding()
	 * @generated
	 * @ordered
	 */
	protected boolean binding = BINDING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDeclaredFunction() <em>Declared Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredFunction()
	 * @generated
	 * @ordered
	 */
	protected TFunction declaredFunction;

	/**
	 * The cached value of the '{@link #getDeclaredThisType() <em>Declared This Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeclaredThisType()
	 * @generated
	 * @ordered
	 */
	protected TypeRef declaredThisType;

	/**
	 * The cached value of the '{@link #getOwnedTypeVars() <em>Owned Type Vars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariable> ownedTypeVars;

	/**
	 * The cached value of the '{@link #getUnboundTypeVars() <em>Unbound Type Vars</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnboundTypeVars()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeVariable> unboundTypeVars;

	/**
	 * The cached value of the '{@link #getUnboundTypeVarsUpperBounds() <em>Unbound Type Vars Upper Bounds</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnboundTypeVarsUpperBounds()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeRef> unboundTypeVarsUpperBounds;

	/**
	 * The cached value of the '{@link #getFpars() <em>Fpars</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFpars()
	 * @generated
	 * @ordered
	 */
	protected EList<TFormalParameter> fpars;

	/**
	 * The cached value of the '{@link #getReturnTypeRef() <em>Return Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnTypeRef()
	 * @generated
	 * @ordered
	 */
	protected TypeRef returnTypeRef;

	/**
	 * The default value of the '{@link #isReturnValueMarkedOptional() <em>Return Value Marked Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReturnValueMarkedOptional()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReturnValueMarkedOptional() <em>Return Value Marked Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReturnValueMarkedOptional()
	 * @generated
	 * @ordered
	 */
	protected boolean returnValueMarkedOptional = RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionTypeExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.FUNCTION_TYPE_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBinding() {
		return binding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBinding(boolean newBinding) {
		boolean oldBinding = binding;
		binding = newBinding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__BINDING, oldBinding, binding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TFunction getDeclaredFunction() {
		if (declaredFunction != null && declaredFunction.eIsProxy()) {
			InternalEObject oldDeclaredFunction = (InternalEObject)declaredFunction;
			declaredFunction = (TFunction)eResolveProxy(oldDeclaredFunction);
			if (declaredFunction != oldDeclaredFunction) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_FUNCTION, oldDeclaredFunction, declaredFunction));
			}
		}
		return declaredFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TFunction basicGetDeclaredFunction() {
		return declaredFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDeclaredFunction(TFunction newDeclaredFunction) {
		TFunction oldDeclaredFunction = declaredFunction;
		declaredFunction = newDeclaredFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_FUNCTION, oldDeclaredFunction, declaredFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredThisType() {
		return declaredThisType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclaredThisType(TypeRef newDeclaredThisType, NotificationChain msgs) {
		TypeRef oldDeclaredThisType = declaredThisType;
		declaredThisType = newDeclaredThisType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE, oldDeclaredThisType, newDeclaredThisType);
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
	public void setDeclaredThisType(TypeRef newDeclaredThisType) {
		if (newDeclaredThisType != declaredThisType) {
			NotificationChain msgs = null;
			if (declaredThisType != null)
				msgs = ((InternalEObject)declaredThisType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE, null, msgs);
			if (newDeclaredThisType != null)
				msgs = ((InternalEObject)newDeclaredThisType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE, null, msgs);
			msgs = basicSetDeclaredThisType(newDeclaredThisType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE, newDeclaredThisType, newDeclaredThisType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getOwnedTypeVars() {
		if (ownedTypeVars == null) {
			ownedTypeVars = new EObjectContainmentEList<TypeVariable>(TypeVariable.class, this, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS);
		}
		return ownedTypeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getUnboundTypeVars() {
		if (unboundTypeVars == null) {
			unboundTypeVars = new EObjectResolvingEList<TypeVariable>(TypeVariable.class, this, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS);
		}
		return unboundTypeVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeRef> getUnboundTypeVarsUpperBounds() {
		if (unboundTypeVarsUpperBounds == null) {
			unboundTypeVarsUpperBounds = new EObjectContainmentEList<TypeRef>(TypeRef.class, this, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS);
		}
		return unboundTypeVarsUpperBounds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TFormalParameter> getFpars() {
		if (fpars == null) {
			fpars = new EObjectContainmentEList<TFormalParameter>(TFormalParameter.class, this, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__FPARS);
		}
		return fpars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getReturnTypeRef() {
		return returnTypeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturnTypeRef(TypeRef newReturnTypeRef, NotificationChain msgs) {
		TypeRef oldReturnTypeRef = returnTypeRef;
		returnTypeRef = newReturnTypeRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF, oldReturnTypeRef, newReturnTypeRef);
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
	public void setReturnTypeRef(TypeRef newReturnTypeRef) {
		if (newReturnTypeRef != returnTypeRef) {
			NotificationChain msgs = null;
			if (returnTypeRef != null)
				msgs = ((InternalEObject)returnTypeRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF, null, msgs);
			if (newReturnTypeRef != null)
				msgs = ((InternalEObject)newReturnTypeRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF, null, msgs);
			msgs = basicSetReturnTypeRef(newReturnTypeRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF, newReturnTypeRef, newReturnTypeRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReturnValueMarkedOptional() {
		return returnValueMarkedOptional;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReturnValueMarkedOptional(boolean newReturnValueMarkedOptional) {
		boolean oldReturnValueMarkedOptional = returnValueMarkedOptional;
		returnValueMarkedOptional = newReturnValueMarkedOptional;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_VALUE_MARKED_OPTIONAL, oldReturnValueMarkedOptional, returnValueMarkedOptional));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReturnValueOptional() {
		return (this.isReturnValueMarkedOptional() || ((this.getReturnTypeRef() != null) && this.getReturnTypeRef().isFollowedByQuestionMark()));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		EList<TypeVariable> _xifexpression = null;
		boolean _isBinding = this.isBinding();
		if (_isBinding) {
			_xifexpression = this.getUnboundTypeVars();
		}
		else {
			EList<TypeVariable> _xifexpression_1 = null;
			TFunction _declaredFunction = this.getDeclaredFunction();
			boolean _tripleNotEquals = (_declaredFunction != null);
			if (_tripleNotEquals) {
				_xifexpression_1 = this.getDeclaredFunction().getTypeVars();
			}
			else {
				_xifexpression_1 = this.getOwnedTypeVars();
			}
			_xifexpression = _xifexpression_1;
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGeneric() {
		boolean _isEmpty = this.getTypeVars().isEmpty();
		return (!_isEmpty);
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
			final int mandatoryTypeParamsCount = IterableExtensions.size(IterableExtensions.<TypeVariable>filter(this.getTypeVars(), _function));
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
	public TFormalParameter getFparForArgIdx(final int argIndex) {
		final int fparsSize = this.getFpars().size();
		if (((argIndex >= 0) && (argIndex < fparsSize))) {
			return this.getFpars().get(argIndex);
		}
		else {
			if ((((argIndex >= fparsSize) && (fparsSize > 0)) && this.getFpars().get((fparsSize - 1)).isVariadic())) {
				return this.getFpars().get((fparsSize - 1));
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
	public String internalGetTypeRefAsString() {
		String _xifexpression = null;
		TypeRef _declaredThisType = this.getDeclaredThisType();
		boolean _tripleNotEquals = (_declaredThisType != null);
		if (_tripleNotEquals) {
			String _typeRefAsString = this.getDeclaredThisType().getTypeRefAsString();
			String _plus = ("@This(" + _typeRefAsString);
			_xifexpression = (_plus + ") ");
		}
		else {
			_xifexpression = "";
		}
		String _plus_1 = ("{" + _xifexpression);
		String _plus_2 = (_plus_1 + "function");
		String _xifexpression_1 = null;
		boolean _isGeneric = this.isGeneric();
		if (_isGeneric) {
			final Function1<TypeVariable, String> _function = new Function1<TypeVariable, String>() {
				public String apply(final TypeVariable it) {
					return it.getTypeVariableAsString(FunctionTypeExpressionImpl.this.getTypeVarUpperBound(it));
				}
			};
			String _join = IterableExtensions.join(XcoreEListExtensions.<TypeVariable, String>map(this.getTypeVars(), _function), ",");
			String _plus_3 = ("<" + _join);
			_xifexpression_1 = (_plus_3 + ">");
		}
		else {
			_xifexpression_1 = "";
		}
		String _plus_4 = (_plus_2 + _xifexpression_1);
		String _plus_5 = (_plus_4 + "(");
		final Function1<TFormalParameter, String> _function_1 = new Function1<TFormalParameter, String>() {
			public String apply(final TFormalParameter it) {
				return it.getFormalParameterAsTypesString();
			}
		};
		String _join_1 = IterableExtensions.join(XcoreEListExtensions.<TFormalParameter, String>map(this.getFpars(), _function_1), ",");
		String _plus_6 = (_plus_5 + _join_1);
		String _plus_7 = (_plus_6 + ")");
		String _xifexpression_2 = null;
		TypeRef _returnTypeRef = this.getReturnTypeRef();
		boolean _tripleNotEquals_1 = (_returnTypeRef != null);
		if (_tripleNotEquals_1) {
			String _typeRefAsString_1 = this.getReturnTypeRef().getTypeRefAsString();
			_xifexpression_2 = (":" + _typeRefAsString_1);
		}
		else {
			_xifexpression_2 = "";
		}
		String _plus_8 = (_plus_7 + _xifexpression_2);
		String _xifexpression_3 = null;
		boolean _isReturnValueOptional = this.isReturnValueOptional();
		if (_isReturnValueOptional) {
			_xifexpression_3 = "?";
		}
		else {
			_xifexpression_3 = "";
		}
		String _plus_9 = (_plus_8 + _xifexpression_3);
		String _plus_10 = (_plus_9 + "}");
		String _modifiersAsString = this.getModifiersAsString();
		return (_plus_10 + _modifiersAsString);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeVarUpperBound(final TypeVariable typeVar) {
		if ((typeVar == null)) {
			throw new IllegalArgumentException("given type variable may not be null");
		}
		final int idx = this.getUnboundTypeVars().indexOf(typeVar);
		if (((idx >= 0) && (idx < this.getUnboundTypeVarsUpperBounds().size()))) {
			final TypeRef modifiedUpperBound = this.getUnboundTypeVarsUpperBounds().get(idx);
			boolean _isUnknown = modifiedUpperBound.isUnknown();
			boolean _not = (!_isUnknown);
			if (_not) {
				return modifiedUpperBound;
			}
		}
		return typeVar.getDeclaredUpperBound();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE:
				return basicSetDeclaredThisType(null, msgs);
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS:
				return ((InternalEList<?>)getOwnedTypeVars()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS:
				return ((InternalEList<?>)getUnboundTypeVarsUpperBounds()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__FPARS:
				return ((InternalEList<?>)getFpars()).basicRemove(otherEnd, msgs);
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF:
				return basicSetReturnTypeRef(null, msgs);
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
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__BINDING:
				return isBinding();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_FUNCTION:
				if (resolve) return getDeclaredFunction();
				return basicGetDeclaredFunction();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE:
				return getDeclaredThisType();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS:
				return getOwnedTypeVars();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS:
				return getUnboundTypeVars();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS:
				return getUnboundTypeVarsUpperBounds();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__FPARS:
				return getFpars();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF:
				return getReturnTypeRef();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_VALUE_MARKED_OPTIONAL:
				return isReturnValueMarkedOptional();
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
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__BINDING:
				setBinding((Boolean)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_FUNCTION:
				setDeclaredFunction((TFunction)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE:
				setDeclaredThisType((TypeRef)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS:
				getOwnedTypeVars().clear();
				getOwnedTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS:
				getUnboundTypeVars().clear();
				getUnboundTypeVars().addAll((Collection<? extends TypeVariable>)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS:
				getUnboundTypeVarsUpperBounds().clear();
				getUnboundTypeVarsUpperBounds().addAll((Collection<? extends TypeRef>)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__FPARS:
				getFpars().clear();
				getFpars().addAll((Collection<? extends TFormalParameter>)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF:
				setReturnTypeRef((TypeRef)newValue);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_VALUE_MARKED_OPTIONAL:
				setReturnValueMarkedOptional((Boolean)newValue);
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
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__BINDING:
				setBinding(BINDING_EDEFAULT);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_FUNCTION:
				setDeclaredFunction((TFunction)null);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE:
				setDeclaredThisType((TypeRef)null);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS:
				getOwnedTypeVars().clear();
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS:
				getUnboundTypeVars().clear();
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS:
				getUnboundTypeVarsUpperBounds().clear();
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__FPARS:
				getFpars().clear();
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF:
				setReturnTypeRef((TypeRef)null);
				return;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_VALUE_MARKED_OPTIONAL:
				setReturnValueMarkedOptional(RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT);
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
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__BINDING:
				return binding != BINDING_EDEFAULT;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_FUNCTION:
				return declaredFunction != null;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE:
				return declaredThisType != null;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS:
				return ownedTypeVars != null && !ownedTypeVars.isEmpty();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS:
				return unboundTypeVars != null && !unboundTypeVars.isEmpty();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS:
				return unboundTypeVarsUpperBounds != null && !unboundTypeVarsUpperBounds.isEmpty();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__FPARS:
				return fpars != null && !fpars.isEmpty();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF:
				return returnTypeRef != null;
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION__RETURN_VALUE_MARKED_OPTIONAL:
				return returnValueMarkedOptional != RETURN_VALUE_MARKED_OPTIONAL_EDEFAULT;
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
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING: return TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___IS_GENERIC: return TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___IS_GENERIC;
				case TypeRefsPackage.TYPE_REF___IS_RAW: return TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___IS_RAW;
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
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___IS_RETURN_VALUE_OPTIONAL:
				return isReturnValueOptional();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___GET_TYPE_VARS:
				return getTypeVars();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___IS_GENERIC:
				return isGeneric();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___IS_RAW:
				return isRaw();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___GET_FPAR_FOR_ARG_IDX__INT:
				return getFparForArgIdx((Integer)arguments.get(0));
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
			case TypeRefsPackage.FUNCTION_TYPE_EXPRESSION___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE:
				return getTypeVarUpperBound((TypeVariable)arguments.get(0));
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
		result.append(" (binding: ");
		result.append(binding);
		result.append(", returnValueMarkedOptional: ");
		result.append(returnValueMarkedOptional);
		result.append(')');
		return result.toString();
	}

} //FunctionTypeExpressionImpl
