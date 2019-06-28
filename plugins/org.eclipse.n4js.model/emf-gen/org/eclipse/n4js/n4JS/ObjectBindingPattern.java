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
 * A representation of the model object '<em><b>Object Binding Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A binding pattern used for object destructuring within a variable declaration statement, produced by code such as
 * <pre>
 * let {prop: myVar} = {prop: 'hello'};
 * </pre>
 * where the <code>{prop: myVar}</code> part will be represented in the AST as an {@code ObjectBindingPattern}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ObjectBindingPattern#getProperties <em>Properties</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getObjectBindingPattern()
 * @model
 * @generated
 */
public interface ObjectBindingPattern extends BindingPattern {
	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.BindingProperty}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getObjectBindingPattern_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<BindingProperty> getProperties();

} // ObjectBindingPattern
