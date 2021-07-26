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

import org.eclipse.n4js.n4JS.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Snippet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A pre-formatted code snippet that will be emitted to the target code as is.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.Snippet#getCodeToEmit <em>Code To Emit</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSnippet()
 * @model
 * @generated
 */
public interface Snippet extends Expression {
	/**
	 * Returns the value of the '<em><b>Code To Emit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code To Emit</em>' attribute.
	 * @see #setCodeToEmit(String)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getSnippet_CodeToEmit()
	 * @model unique="false"
	 * @generated
	 */
	String getCodeToEmit();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.Snippet#getCodeToEmit <em>Code To Emit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code To Emit</em>' attribute.
	 * @see #getCodeToEmit()
	 * @generated
	 */
	void setCodeToEmit(String value);

} // Snippet
