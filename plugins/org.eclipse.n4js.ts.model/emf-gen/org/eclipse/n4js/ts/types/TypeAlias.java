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
 * A representation of the model object '<em><b>Type Alias</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A type alias.
 * <p>
 * Note that references to type aliases are tricky, see {@link TypeRef#isAliasResolved()}.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeAlias()
 * @model
 * @generated
 */
public interface TypeAlias extends GenericType, AccessibleTypeElement, TTypedElement, SyntaxRelatedTElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAlias();

} // TypeAlias
