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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Name Value Pair Single Name</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameValuePairSingleName()
 * @model
 * @generated
 */
public interface PropertyNameValuePairSingleName extends PropertyNameValuePair {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	IdentifierRef getIdentifierRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Note: if this AST node was created by the parser, then super.getName() will always be null and we get
	 * the name from the parse tree; if this AST node is created programmatically (e.g. refactoring), then
	 * the name has to be set explicitly!
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getName();

} // PropertyNameValuePairSingleName
