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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Specifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ImportSpecifier#isFlaggedUsedInCode <em>Flagged Used In Code</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ImportSpecifier#isRetainedAtRunTime <em>Retained At Run Time</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportSpecifier()
 * @model abstract="true"
 * @generated
 */
public interface ImportSpecifier extends EObject {
	/**
	 * Returns the value of the '<em><b>Flagged Used In Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * True iff this import specifier is actually used during scoping.
	 * See {@code org.eclipse.n4js.scoping.imports.OriginAwareScope}
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Flagged Used In Code</em>' attribute.
	 * @see #setFlaggedUsedInCode(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportSpecifier_FlaggedUsedInCode()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	boolean isFlaggedUsedInCode();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ImportSpecifier#isFlaggedUsedInCode <em>Flagged Used In Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flagged Used In Code</em>' attribute.
	 * @see #isFlaggedUsedInCode()
	 * @generated
	 */
	void setFlaggedUsedInCode(boolean value);

	/**
	 * Returns the value of the '<em><b>Retained At Run Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells whether this import specifier will be present in the transpiled output code.
	 * Set at the end of AST traversal.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Retained At Run Time</em>' attribute.
	 * @see #setRetainedAtRunTime(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportSpecifier_RetainedAtRunTime()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	boolean isRetainedAtRunTime();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ImportSpecifier#isRetainedAtRunTime <em>Retained At Run Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retained At Run Time</em>' attribute.
	 * @see #isRetainedAtRunTime()
	 * @generated
	 */
	void setRetainedAtRunTime(boolean value);

} // ImportSpecifier
