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
package org.eclipse.n4js.utils.validation.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.n4js.utils.validation.PostValidation;
import org.eclipse.n4js.utils.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Post Validation</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PostValidationImpl extends ValidationMarkerImpl implements PostValidation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PostValidationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.POST_VALIDATION;
	}

} //PostValidationImpl
