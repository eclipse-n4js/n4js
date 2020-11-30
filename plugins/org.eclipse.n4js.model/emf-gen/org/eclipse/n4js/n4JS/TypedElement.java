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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Typed Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  A typed element, such as Variable, a Member or a NameValuePair, may provide a declared type.
 * Note firstly that the declared type may be empty, and secondly that the declared type is different from the
 * inferred type. The latter means that it is of course possible to infer the type of non-typed elements,
 * such as expressions!
 * Note that a function definition is not a typed element, as it is a type definition rather than a type reference.
 * This is indicated by possibly unbound type parameters!
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TypedElement#getDeclaredTypeRef <em>Declared Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypedElement()
 * @model abstract="true"
 * @generated
 */
public interface TypedElement extends TypeProvidingElement {
	/**
	 * Returns the value of the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type Ref</em>' containment reference.
	 * @see #setDeclaredTypeRef(TypeRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTypedElement_DeclaredTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TypedElement#getDeclaredTypeRef <em>Declared Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type Ref</em>' containment reference.
	 * @see #getDeclaredTypeRef()
	 * @generated
	 */
	void setDeclaredTypeRef(TypeRef value);

} // TypedElement
