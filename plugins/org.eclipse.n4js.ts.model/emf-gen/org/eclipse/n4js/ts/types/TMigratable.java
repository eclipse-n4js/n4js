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
package org.eclipse.n4js.ts.types;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TMigratable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TMigratable#getMigrations <em>Migrations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigratable()
 * @model abstract="true"
 * @generated
 */
public interface TMigratable extends EObject {
	/**
	 * Returns the value of the '<em><b>Migrations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TMigration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The declared migrations for this {@link TMigratable} element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Migrations</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTMigratable_Migrations()
	 * @model
	 * @generated
	 */
	EList<TMigration> getMigrations();

} // TMigratable
