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
 * A representation of the model object '<em><b>JSX Fragment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.JSXFragment#getJsxChildren <em>Jsx Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXFragment()
 * @model
 * @generated
 */
public interface JSXFragment extends Expression, JSXChild {
	/**
	 * Returns the value of the '<em><b>Jsx Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.JSXChild}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Children</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXFragment_JsxChildren()
	 * @model containment="true"
	 * @generated
	 */
	EList<JSXChild> getJsxChildren();

} // JSXFragment
