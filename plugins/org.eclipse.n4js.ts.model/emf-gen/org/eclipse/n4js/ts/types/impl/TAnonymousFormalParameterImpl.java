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

import java.lang.reflect.InvocationTargetException;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.n4js.ts.types.TAnonymousFormalParameter;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TAnonymous Formal Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class TAnonymousFormalParameterImpl extends TFormalParameterImpl implements TAnonymousFormalParameter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TAnonymousFormalParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TANONYMOUS_FORMAL_PARAMETER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		final String definedName = this.getDefinedName();
		if ((definedName != null)) {
			return definedName;
		}
		final Object containingFeature = this.eContainer().eGet(this.eContainingFeature());
		if ((containingFeature instanceof List<?>)) {
			Object _eGet = this.eContainer().eGet(this.eContainingFeature());
			final int index = ((List<?>) _eGet).indexOf(this);
			return ("_par_" + Integer.valueOf(index));
		}
		else {
			return "_par_";
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDefinedName() {
		return super.getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypesPackage.TANONYMOUS_FORMAL_PARAMETER___GET_NAME:
				return getName();
			case TypesPackage.TANONYMOUS_FORMAL_PARAMETER___GET_DEFINED_NAME:
				return getDefinedName();
		}
		return super.eInvoke(operationID, arguments);
	}

} //TAnonymousFormalParameterImpl
