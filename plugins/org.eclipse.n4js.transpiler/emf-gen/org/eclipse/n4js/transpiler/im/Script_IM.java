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
package org.eclipse.n4js.transpiler.im;

import org.eclipse.n4js.n4JS.Script;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Script IM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.Script_IM#getSymbolTable <em>Symbol Table</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getScript_IM()
 * @model
 * @generated
 */
public interface Script_IM extends Script {
	/**
	 * Returns the value of the '<em><b>Symbol Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbol Table</em>' containment reference.
	 * @see #setSymbolTable(SymbolTable)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getScript_IM_SymbolTable()
	 * @model containment="true"
	 * @generated
	 */
	SymbolTable getSymbolTable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.Script_IM#getSymbolTable <em>Symbol Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbol Table</em>' containment reference.
	 * @see #getSymbolTable()
	 * @generated
	 */
	void setSymbolTable(SymbolTable value);

} // Script_IM
