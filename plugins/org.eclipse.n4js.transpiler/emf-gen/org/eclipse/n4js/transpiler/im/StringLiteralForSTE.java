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

import org.eclipse.n4js.n4JS.StringLiteral;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>String Literal For STE</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This special kind of {@link StringLiteral} should be used if the name of a symbol table entry is required somewhere
 * in the form of a string literal. This will ensure that later changes to the symbol table entry's name will be
 * reflected in the string literal.
 * <p>
 * Use this to emit code such as the <code>"field"</code> literal in the following example:
 * <pre>
 * if(!target.hasOwnProperty("field")) {
 *     target.field = 42;
 * }
 * </pre>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE#getEntry <em>Entry</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE#isUseExportedName <em>Use Exported Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getStringLiteralForSTE()
 * @model
 * @generated
 */
public interface StringLiteralForSTE extends StringLiteral {
	/**
	 * Returns the value of the '<em><b>Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry</em>' reference.
	 * @see #setEntry(SymbolTableEntry)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getStringLiteralForSTE_Entry()
	 * @model
	 * @generated
	 */
	SymbolTableEntry getEntry();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE#getEntry <em>Entry</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' reference.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(SymbolTableEntry value);

	/**
	 * Returns the value of the '<em><b>Use Exported Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * If true, use the element's exported name instead of its actual name. If true, the {@link #entry}'s original
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Use Exported Name</em>' attribute.
	 * @see #setUseExportedName(boolean)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getStringLiteralForSTE_UseExportedName()
	 * @model unique="false"
	 * @generated
	 */
	boolean isUseExportedName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE#isUseExportedName <em>Use Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Use Exported Name</em>' attribute.
	 * @see #isUseExportedName()
	 * @generated
	 */
	void setUseExportedName(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getValueAsString();

} // StringLiteralForSTE
