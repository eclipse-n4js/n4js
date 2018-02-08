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

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.n4JS.NamedImportSpecifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Versioned Named Import Specifier IM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Import specifier that is aware of multiple versions of the imported type.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM#getImportedTypeVersions <em>Imported Type Versions</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM#isVersionedTypeImport <em>Versioned Type Import</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getVersionedNamedImportSpecifier_IM()
 * @model
 * @generated
 */
public interface VersionedNamedImportSpecifier_IM extends NamedImportSpecifier {
	/**
	 * Returns the value of the '<em><b>Imported Type Versions</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imported Type Versions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imported Type Versions</em>' reference list.
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getVersionedNamedImportSpecifier_IM_ImportedTypeVersions()
	 * @model
	 * @generated
	 */
	EList<SymbolTableEntryOriginal> getImportedTypeVersions();

	/**
	 * Returns the value of the '<em><b>Versioned Type Import</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns {@code true} if this import specifiers imports a
	 * versioned type.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Versioned Type Import</em>' attribute.
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getVersionedNamedImportSpecifier_IM_VersionedTypeImport()
	 * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel get='return ((this.getImportedTypeVersions().size() &gt; 0) &amp;&amp; ((this.getImportedTypeVersions().size() &gt; 1) || \n\t&lt;%org.eclipse.n4js.n4idl.versioning.VersionUtils%&gt;.isTVersionable(this.getImportedTypeVersions().get(0).getOriginalTarget())));'"
	 * @generated
	 */
	boolean isVersionedTypeImport();

} // VersionedNamedImportSpecifier_IM
