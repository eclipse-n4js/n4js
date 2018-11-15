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

import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Also see utility methods in N4JSASTUtils
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getExpression()
 * @model abstract="true"
 * @generated
 */
public interface Expression extends TypableElement, ControlFlowElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Implements the method #isValidSimpleAssignmentTarget from ES6 spec.
	 * Returns false by default.
	 * 	 * See chapters 12.1.3, 12.2.1.5, 12.2.10.3, 12.3.1.5, 12.4.3, 12.5.3, 12.6.2,
	 * 12.7.2, 12.8.2, 12.9.2, 12.10.2, 12.11.2, 12.12.2, 12.13.2, 12.14.3, 12.15.2.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidSimpleAssignmentTarget();

} // Expression
