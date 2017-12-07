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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Migration Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Migrations are like functions, except that they allow for multiple return values.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.MigrationDeclaration#getFrets <em>Frets</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getMigrationDeclaration()
 * @model
 * @generated
 */
public interface MigrationDeclaration extends FunctionDeclaration {
	/**
	 * Returns the value of the '<em><b>Frets</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.FormalParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frets</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frets</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getMigrationDeclaration_Frets()
	 * @model containment="true"
	 * @generated
	 */
	EList<FormalParameter> getFrets();

} // MigrationDeclaration
