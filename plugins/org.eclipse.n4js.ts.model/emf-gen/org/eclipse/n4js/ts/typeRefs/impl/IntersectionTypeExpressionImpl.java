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
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

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
	public String internalGetTypeRefAsString() {
		String _internalGetTypeRefAsString = super.internalGetTypeRefAsString();
		return ("intersection" + _internalGetTypeRefAsString);
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
				case TypeRefsPackage.TYPE_ARGUMENT___INTERNAL_GET_TYPE_REF_AS_STRING: return TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING;
				default: return super.eDerivedOperationID(baseOperationID, baseClass);
			}
		}
		if (baseClass == ComposedTypeRef.class) {
			switch (baseOperationID) {
				case TypeRefsPackage.COMPOSED_TYPE_REF___INTERNAL_GET_TYPE_REF_AS_STRING: return TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING;
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
			case TypeRefsPackage.INTERSECTION_TYPE_EXPRESSION___INTERNAL_GET_TYPE_REF_AS_STRING:
				return internalGetTypeRefAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //IntersectionTypeExpressionImpl
