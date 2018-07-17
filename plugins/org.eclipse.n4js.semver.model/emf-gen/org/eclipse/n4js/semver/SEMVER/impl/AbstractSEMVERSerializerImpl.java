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
package org.eclipse.n4js.semver.SEMVER.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.semver.SEMVER.AbstractSEMVERSerializer;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;

import org.eclipse.n4js.semver.model.SEMVERSerializer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract SEMVER Serializer</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AbstractSEMVERSerializerImpl extends MinimalEObjectImpl.Container implements AbstractSEMVERSerializer {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractSEMVERSerializerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SEMVERPackage.Literals.ABSTRACT_SEMVER_SERIALIZER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		return SEMVERSerializer.toString(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case SEMVERPackage.ABSTRACT_SEMVER_SERIALIZER___TO_STRING:
				return toString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //AbstractSEMVERSerializerImpl
