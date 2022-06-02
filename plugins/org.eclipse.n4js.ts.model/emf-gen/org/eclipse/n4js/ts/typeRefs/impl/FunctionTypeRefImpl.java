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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.xcore.lib.XcoreEListExtensions;

import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage.Literals;

import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class FunctionTypeRefImpl extends ParameterizedTypeRefImpl implements FunctionTypeRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.FUNCTION_TYPE_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getDeclaredThisType() {
		return this.getFunctionType().getDeclaredThisType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TypeVariable> getTypeVars() {
		return this.getFunctionType().getTypeVars();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getTypeVarUpperBound(final TypeVariable typeVar) {
		return typeVar.getDeclaredUpperBound();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TFormalParameter> getFpars() {
		return this.getFunctionType().getFpars();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeRef getReturnTypeRef() {
		return this.getFunctionType().getReturnTypeRef();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TFunction getFunctionType() {
		Type _declaredType = this.getDeclaredType();
		return ((TFunction) _declaredType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReturnValueOptional() {
		return ((this.getReturnTypeRef() != null) && this.getReturnTypeRef().isFollowedByQuestionMark());
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
	public String internalGetTypeRefAsString(final boolean resolveProxies) {
		if ((!resolveProxies)) {
			final FunctionTypeExprOrRef _this = this;
			if ((_this instanceof FunctionTypeRef)) {
				Object _eGet = ((FunctionTypeRef)_this).eGet(Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE, false);
				final EObject declType = ((EObject) _eGet);
				if (((declType != null) && declType.eIsProxy())) {
					String _declaredTypeAsText = ((FunctionTypeRef)_this).getDeclaredTypeAsText();
					boolean _tripleNotEquals = (_declaredTypeAsText != null);
					if (_tripleNotEquals) {
						String _declaredTypeAsText_1 = ((FunctionTypeRef)_this).getDeclaredTypeAsText();
						return (_declaredTypeAsText_1 + "\u00ABproxy\u00BB");
					}
					return "{function(???):???}\u00ABproxy\u00BB";
				}
			}
		}
		String _xifexpression = null;
		TypeRef _declaredThisType = this.getDeclaredThisType();
		boolean _tripleNotEquals_1 = (_declaredThisType != null);
		if (_tripleNotEquals_1) {
			String _typeRefAsString = this.getDeclaredThisType().getTypeRefAsString(resolveProxies);
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
					return it.getTypeVariableAsString(FunctionTypeRefImpl.this.getTypeVarUpperBound(it));
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
		boolean _tripleNotEquals_2 = (_returnTypeRef != null);
		if (_tripleNotEquals_2) {
			String _typeRefAsString_1 = this.getReturnTypeRef().getTypeRefAsString(resolveProxies);
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
	public int eDerivedOperationID(int baseOperationID, Class<?> baseClass) {
		if (baseClass == TypeArgument.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN: return TypeRefsPackage.FUNCTION_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___IS_GENERIC: return TypeRefsPackage.FUNCTION_TYPE_REF___IS_GENERIC;
				case TypeRefsPackage.TYPE_REF___IS_RAW: return TypeRefsPackage.FUNCTION_TYPE_REF___IS_RAW;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ParameterizedTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN: return TypeRefsPackage.FUNCTION_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_GENERIC: return TypeRefsPackage.FUNCTION_TYPE_REF___IS_GENERIC;
				case TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_RAW: return TypeRefsPackage.FUNCTION_TYPE_REF___IS_RAW;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == FunctionTypeExprOrRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_THIS_TYPE: return TypeRefsPackage.FUNCTION_TYPE_REF___GET_DECLARED_THIS_TYPE;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_FUNCTION_TYPE: return TypeRefsPackage.FUNCTION_TYPE_REF___GET_FUNCTION_TYPE;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VARS: return TypeRefsPackage.FUNCTION_TYPE_REF___GET_TYPE_VARS;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE: return TypeRefsPackage.FUNCTION_TYPE_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_FPARS: return TypeRefsPackage.FUNCTION_TYPE_REF___GET_FPARS;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___IS_RETURN_VALUE_OPTIONAL: return TypeRefsPackage.FUNCTION_TYPE_REF___IS_RETURN_VALUE_OPTIONAL;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_RETURN_TYPE_REF: return TypeRefsPackage.FUNCTION_TYPE_REF___GET_RETURN_TYPE_REF;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___IS_GENERIC: return TypeRefsPackage.FUNCTION_TYPE_REF___IS_GENERIC;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___IS_RAW: return TypeRefsPackage.FUNCTION_TYPE_REF___IS_RAW;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___GET_FPAR_FOR_ARG_IDX__INT: return TypeRefsPackage.FUNCTION_TYPE_REF___GET_FPAR_FOR_ARG_IDX__INT;
				case TypeRefsPackage.FUNCTION_TYPE_EXPR_OR_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN: return TypeRefsPackage.FUNCTION_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN;
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
			case TypeRefsPackage.FUNCTION_TYPE_REF___GET_DECLARED_THIS_TYPE:
				return getDeclaredThisType();
			case TypeRefsPackage.FUNCTION_TYPE_REF___GET_TYPE_VARS:
				return getTypeVars();
			case TypeRefsPackage.FUNCTION_TYPE_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE:
				return getTypeVarUpperBound((TypeVariable)arguments.get(0));
			case TypeRefsPackage.FUNCTION_TYPE_REF___GET_FPARS:
				return getFpars();
			case TypeRefsPackage.FUNCTION_TYPE_REF___GET_RETURN_TYPE_REF:
				return getReturnTypeRef();
			case TypeRefsPackage.FUNCTION_TYPE_REF___GET_FUNCTION_TYPE:
				return getFunctionType();
			case TypeRefsPackage.FUNCTION_TYPE_REF___IS_RETURN_VALUE_OPTIONAL:
				return isReturnValueOptional();
			case TypeRefsPackage.FUNCTION_TYPE_REF___IS_GENERIC:
				return isGeneric();
			case TypeRefsPackage.FUNCTION_TYPE_REF___IS_RAW:
				return isRaw();
			case TypeRefsPackage.FUNCTION_TYPE_REF___GET_FPAR_FOR_ARG_IDX__INT:
				return getFparForArgIdx((Integer)arguments.get(0));
			case TypeRefsPackage.FUNCTION_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN:
				return internalGetTypeRefAsString((Boolean)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //FunctionTypeRefImpl
