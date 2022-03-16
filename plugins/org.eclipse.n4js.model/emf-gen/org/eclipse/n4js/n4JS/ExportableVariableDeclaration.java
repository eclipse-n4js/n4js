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

import org.eclipse.n4js.ts.types.TVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Exportable Variable Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ExportableVariableDeclaration#getDefinedVariable <em>Defined Variable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExportableVariableDeclaration()
 * @model
 * @generated
 */
public interface ExportableVariableDeclaration extends VariableDeclaration {
	/**
	 * Returns the value of the '<em><b>Defined Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Link to variable information stored in the Xtext index.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defined Variable</em>' reference.
	 * @see #setDefinedVariable(TVariable)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExportableVariableDeclaration_DefinedVariable()
	 * @model transient="true"
	 * @generated
	 */
	TVariable getDefinedVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ExportableVariableDeclaration#getDefinedVariable <em>Defined Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Variable</em>' reference.
	 * @see #getDefinedVariable()
	 * @generated
	 */
	void setDefinedVariable(TVariable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExported();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getExportedName();

} // ExportableVariableDeclaration
