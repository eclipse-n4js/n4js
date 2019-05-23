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
 * A representation of the model object '<em><b>Catch Block</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.CatchBlock#getCatchVariable <em>Catch Variable</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCatchBlock()
 * @model
 * @generated
 */
public interface CatchBlock extends AbstractCatchBlock, VariableEnvironmentElement {
	/**
	 * Returns the value of the '<em><b>Catch Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Catch Variable</em>' containment reference.
	 * @see #setCatchVariable(CatchVariable)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getCatchBlock_CatchVariable()
	 * @model containment="true"
	 * @generated
	 */
	CatchVariable getCatchVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.CatchBlock#getCatchVariable <em>Catch Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Catch Variable</em>' containment reference.
	 * @see #getCatchVariable()
	 * @generated
	 */
	void setCatchVariable(CatchVariable value);

} // CatchBlock
