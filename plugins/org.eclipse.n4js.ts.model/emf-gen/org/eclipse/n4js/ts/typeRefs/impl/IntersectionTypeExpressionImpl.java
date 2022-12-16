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

import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

import org.eclipse.n4js.ts.types.TypingStrategy;

import org.eclipse.xtext.xbase.lib.Functions.Function1;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Intersection Type Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class IntersectionTypeExpressionImpl extends ComposedTypeRefImpl implements IntersectionTypeExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IntersectionTypeExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.INTERSECTION_TYPE_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String internalGetTypeRefAsString(final boolean resolveProxies) {
		String _internalGetTypeRefAsString = super.internalGetTypeRefAsString(resolveProxies);
		return ("intersection" + _internalGetTypeRefAsString);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseSiteStructuralTyping() {
		final Function1<TypeRef, Boolean> _function = new Function1<TypeRef, Boolean>() {
			public Boolean apply(final TypeRef it) {
				return Boolean.valueOf(it.isUseSiteStructuralTyping());
			}
		};
		return IterableExtensions.<TypeRef>forall(this.getTypeRefs(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefSiteStructuralTyping() {
		final Function1<TypeRef, Boolean> _function = new Function1<TypeRef, Boolean>() {
			public Boolean apply(final TypeRef it) {
				return Boolean.valueOf(it.isDefSiteStructuralTyping());
			}
		};
		return IterableExtensions.<TypeRef>forall(this.getTypeRefs(), _function);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypingStrategy getTypingStrategy() {
		boolean _isDefSiteStructuralTyping = this.isDefSiteStructuralTyping();
		if (_isDefSiteStructuralTyping) {
			return TypingStrategy.STRUCTURAL;
		}
		return TypingStrategy.NOMINAL;
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
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN: return TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == TypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.TYPE_REF___GET_TYPING_STRATEGY: return TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___GET_TYPING_STRATEGY;
				case TypeRefsPackage.TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___IS_USE_SITE_STRUCTURAL_TYPING;
				case TypeRefsPackage.TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING: return TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___IS_DEF_SITE_STRUCTURAL_TYPING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ComposedTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.COMPOSED_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN: return TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN;
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
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING__BOOLEAN:
				return internalGetTypeRefAsString((Boolean)arguments.get(0));
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___IS_USE_SITE_STRUCTURAL_TYPING:
				return isUseSiteStructuralTyping();
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___IS_DEF_SITE_STRUCTURAL_TYPING:
				return isDefSiteStructuralTyping();
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___GET_TYPING_STRATEGY:
				return getTypingStrategy();
		}
		return super.eInvoke(operationID, arguments);
	}

} //IntersectionTypeExpressionImpl
