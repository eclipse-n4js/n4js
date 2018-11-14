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
 * A representation of the model object '<em><b>N4 Type Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Base class for N4 type declarations and expression. This is a general pattern we use here:
 * definition = declaration | expression.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4TypeDefinition()
 * @model abstract="true"
 * @generated
 */
public interface N4TypeDefinition extends AnnotableElement, TypeDefiningElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the classifier is only a declaration of an external implementation.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExternal();

} // N4TypeDefinition
