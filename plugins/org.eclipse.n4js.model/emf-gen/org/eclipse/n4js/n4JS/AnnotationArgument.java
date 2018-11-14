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
 * A representation of the model object '<em><b>Annotation Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getAnnotationArgument()
 * @model abstract="true"
 * @generated
 */
public interface AnnotationArgument extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Convenience method, returns the literal or type reference
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 * @generated
	 */
	EObject value();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenience method, returns the value of the argument as string, or null, if no such value is present.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getValueAsString();

} // AnnotationArgument
