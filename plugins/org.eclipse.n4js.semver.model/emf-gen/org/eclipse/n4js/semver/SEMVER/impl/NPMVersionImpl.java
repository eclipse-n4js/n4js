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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.n4js.semver.SEMVER.NPMVersion;
import org.eclipse.n4js.semver.SEMVER.SEMVERPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>NPM Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class NPMVersionImpl extends MinimalEObjectImpl.Container implements NPMVersion {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NPMVersionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SEMVERPackage.Literals.NPM_VERSION;
	}

} //NPMVersionImpl
