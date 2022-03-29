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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TExportable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TExportableElement#isDirectlyExported <em>Directly Exported</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTExportableElement()
 * @model
 * @generated
 */
public interface TExportableElement extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Directly Exported</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells whether this element is actually directly exported. This will be true
	 * iff the corresponding {@code ExportableElement} in the AST returned true
	 * from {@code #isDeclaredExported()} OR from {@code #isExportedByNamespace()}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Directly Exported</em>' attribute.
	 * @see #setDirectlyExported(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTExportableElement_DirectlyExported()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDirectlyExported();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TExportableElement#isDirectlyExported <em>Directly Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Directly Exported</em>' attribute.
	 * @see #isDirectlyExported()
	 * @generated
	 */
	void setDirectlyExported(boolean value);

} // TExportableElement
