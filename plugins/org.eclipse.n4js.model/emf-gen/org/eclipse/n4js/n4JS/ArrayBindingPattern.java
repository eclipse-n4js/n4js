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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Binding Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A binding pattern used for array destructuring within a variable declaration statement, produced by code such as
 * <pre>
 * let [myVar] = ['hello'];
 * </pre>
 * where the <code>[myVar]</code> part will be represented in the AST as an {@code ArrayBindingPattern}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ArrayBindingPattern#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getArrayBindingPattern()
 * @model
 * @generated
 */
public interface ArrayBindingPattern extends BindingPattern {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.BindingElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getArrayBindingPattern_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<BindingElement> getElements();

} // ArrayBindingPattern
