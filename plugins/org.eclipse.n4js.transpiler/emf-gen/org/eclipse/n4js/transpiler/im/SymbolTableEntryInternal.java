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

import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbol Table Entry Internal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Internal symbol table entries represent internal low-level identifiable elements that are not visible from N4JS and
 * do not have a representation in the intermediate model (there are only references to this element in the intermediate
 * model!). Therefore, they don't have an originalTarget and no elementsOfThisName. For a comparison, see
 * {@link SymbolTableEntry here}.
 * <p>
 * Examples include <code>$makeClass</code>, <code>$n4export</code> but also SystemJS' <code>System</code> and
 * <code>System.register</code>.
 * <p>
 * Background: sometimes the transpiler has to emit low-level code that uses some global objects that are not
 * visible from N4JS. Therefore, we do not have any representation for these elements, neither in a TModule (or in
 * the original AST, e.g. in case of non-exported variable declarations) nor in the intermediate model. For these
 * cases, the special case of SymbolTableEntryInternal. Internal symbol table entries are the only STE's that are
 * primarily identified by name. So when creating one or searching for them you only have to provide a name.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal#getImportSpecifier <em>Import Specifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntryInternal()
 * @model
 * @generated
 */
public interface SymbolTableEntryInternal extends SymbolTableEntry {

	/**
	 * Returns the value of the '<em><b>Import Specifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * In case a namespace import is added programmatically by a transformation, then we do not have a
	 * {@link ModuleNamespaceVirtualType} we can use as original target, so we use a {@code SymbolTableEntryInternal}.
	 * In such a case, this references points to the corresponding {@link NamespaceImportSpecifier}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Import Specifier</em>' reference.
	 * @see #setImportSpecifier(NamespaceImportSpecifier)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntryInternal_ImportSpecifier()
	 * @model
	 * @generated
	 */
	NamespaceImportSpecifier getImportSpecifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal#getImportSpecifier <em>Import Specifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Specifier</em>' reference.
	 * @see #getImportSpecifier()
	 * @generated
	 */
	void setImportSpecifier(NamespaceImportSpecifier value);
} // SymbolTableEntryInternal
