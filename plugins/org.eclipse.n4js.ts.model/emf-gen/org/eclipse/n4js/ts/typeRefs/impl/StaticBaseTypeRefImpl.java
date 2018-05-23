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

import org.eclipse.emf.ecore.EClass;

import org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Static Base Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class StaticBaseTypeRefImpl extends TypeRefImpl implements StaticBaseTypeRef {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StaticBaseTypeRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypeRefsPackage.Literals.STATIC_BASE_TYPE_REF;
	}

} //StaticBaseTypeRefImpl
