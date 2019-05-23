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
package org.eclipse.n4js.n4JS.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4JS.AnnotationArgument;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.utils.emf.ProxyResolvingEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class AnnotationArgumentImpl extends ProxyResolvingEObjectImpl implements AnnotationArgument {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationArgumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return N4JSPackage.Literals.ANNOTATION_ARGUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject value() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getValueAsString() {
		final EObject value = this.value();
		if ((value == null)) {
			return null;
		}
		String _switchResult = null;
		boolean _matched = false;
		if (value instanceof Literal) {
			_matched=true;
			_switchResult = ((Literal)value).getValueAsString();
		}
		if (!_matched) {
			if (value instanceof TypeRef) {
				_matched=true;
				_switchResult = ((TypeRef)value).getTypeRefAsString();
			}
		}
		if (!_matched) {
			_switchResult = value.toString();
		}
		return _switchResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4JSPackage.ANNOTATION_ARGUMENT___VALUE:
				return value();
			case N4JSPackage.ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING:
				return getValueAsString();
		}
		return super.eInvoke(operationID, arguments);
	}

} //AnnotationArgumentImpl
