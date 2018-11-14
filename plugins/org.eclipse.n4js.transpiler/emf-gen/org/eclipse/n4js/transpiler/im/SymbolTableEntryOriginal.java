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

import org.eclipse.n4js.n4JS.ImportSpecifier;

import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbol Table Entry Original</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * First of three cases of a <code>SymbolTableEntry</code>. This kind of symbol table entry represents an element that
 * is visible from N4JS and is thus also represented by a corresponding TModule element or node in the original AST,
 * which is given by property <code>originalTarget</code>. For a comparison, see {@link SymbolTableEntry here}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getOriginalTarget <em>Original Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getImportSpecifier <em>Import Specifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntryOriginal()
 * @model
 * @generated
 */
public interface SymbolTableEntryOriginal extends SymbolTableEntry {
	/**
	 * Returns the value of the '<em><b>Original Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The original target element. Every reference in the intermediate model pointing to this SymbolTableEntry is
	 * actually meant as a reference to this original target element.
	 * <p>
	 * This will either be
	 * <ul>
	 * <li>a <b>TModule element</b> (in case of elements that have a representation in the TModule, i.e.
	 * exported top-level variables, classes, interfaces, members, etc.) or
	 * <li>a <b>node in the original AST</b> (in case of elements that do *not* have a representation in the TModule,
	 * i.e. non-exported top-level variables, local variables, formal parameters, etc).
	 * </ul>
	 * This value must never be <code>null</code>.
	 * <p>
	 * <b>WARNING: via this reference, you are leaving <strike>the American sector</strike> the intermediate model and
	 * enter the original AST or TModule.</b>
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Original Target</em>' reference.
	 * @see #setOriginalTarget(IdentifiableElement)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntryOriginal_OriginalTarget()
	 * @model
	 * @generated
	 */
	IdentifiableElement getOriginalTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getOriginalTarget <em>Original Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Target</em>' reference.
	 * @see #getOriginalTarget()
	 * @generated
	 */
	void setOriginalTarget(IdentifiableElement value);

	/**
	 * Returns the value of the '<em><b>Import Specifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the original target was imported via an import statement, then this points to the corresponding import
	 * specifier (in the intermediate model).
	 * <p>
	 * If non-<code>null</code>, this is either a {@link NamedImportSpecifier} or a {@link NamespaceImportSpecifier}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Import Specifier</em>' reference.
	 * @see #setImportSpecifier(ImportSpecifier)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSymbolTableEntryOriginal_ImportSpecifier()
	 * @model
	 * @generated
	 */
	ImportSpecifier getImportSpecifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getImportSpecifier <em>Import Specifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Specifier</em>' reference.
	 * @see #getImportSpecifier()
	 * @generated
	 */
	void setImportSpecifier(ImportSpecifier value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For elements defined in the resource to compile, this is the name under which this element is exported and
	 * another, remote resource can import it. For elements defined in remote resources, this is the name under which
	 * this element is exported by the remote resource and can be imported in the resource to compile.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String exportedName();

} // SymbolTableEntryOriginal
