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
package org.eclipse.n4js.ts.typeRefs;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.TypeVariable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Variable Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Helper class to store a type variable mapping,
 * i.e. a particular type variable and its type argument.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeVar <em>Type Var</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeArg <em>Type Arg</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeVariableMapping()
 * @model
 * @generated
 */
public interface TypeVariableMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Type Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Var</em>' reference.
	 * @see #setTypeVar(TypeVariable)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeVariableMapping_TypeVar()
	 * @model
	 * @generated
	 */
	TypeVariable getTypeVar();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeVar <em>Type Var</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Var</em>' reference.
	 * @see #getTypeVar()
	 * @generated
	 */
	void setTypeVar(TypeVariable value);

	/**
	 * Returns the value of the '<em><b>Type Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Arg</em>' containment reference.
	 * @see #setTypeArg(TypeArgument)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeVariableMapping_TypeArg()
	 * @model containment="true"
	 * @generated
	 */
	TypeArgument getTypeArg();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeArg <em>Type Arg</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Arg</em>' containment reference.
	 * @see #getTypeArg()
	 * @generated
	 */
	void setTypeArg(TypeArgument value);

} // TypeVariableMapping
