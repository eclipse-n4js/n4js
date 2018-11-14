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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A reference to the constructor of a type. The constructor
 * can be used in a NewExpression. It is a subtype of the equivalent
 * ClassifierTypeRef.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef#getTypeArg <em>Type Arg</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef#isConstructorRef <em>Constructor Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeTypeRef()
 * @model
 * @generated
 */
public interface TypeTypeRef extends BaseTypeRef {
	/**
	 * Returns the value of the '<em><b>Type Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Arg</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Arg</em>' containment reference.
	 * @see #setTypeArg(TypeArgument)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeTypeRef_TypeArg()
	 * @model containment="true"
	 * @generated
	 */
	TypeArgument getTypeArg();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef#getTypeArg <em>Type Arg</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Arg</em>' containment reference.
	 * @see #getTypeArg()
	 * @generated
	 */
	void setTypeArg(TypeArgument value);

	/**
	 * Returns the value of the '<em><b>Constructor Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if this {@link TypeTypeRef} is declared to be a reference to a constructor. This is the case if, in the
	 * source code, keyword {@code constructor} has been used instead of keyword {@code type}:
	 * <pre>
	 * type{C} // flag will be false
	 * constructor{C} // flag will be true
	 * </pre>
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Constructor Ref</em>' attribute.
	 * @see #setConstructorRef(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getTypeTypeRef_ConstructorRef()
	 * @model unique="false"
	 * @generated
	 */
	boolean isConstructorRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef#isConstructorRef <em>Constructor Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constructor Ref</em>' attribute.
	 * @see #isConstructorRef()
	 * @generated
	 */
	void setConstructorRef(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getTypeRefAsString();

} // TypeTypeRef
