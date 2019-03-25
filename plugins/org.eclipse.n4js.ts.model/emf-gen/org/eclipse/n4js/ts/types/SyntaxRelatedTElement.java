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
package org.eclipse.n4js.ts.types;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Syntax Related TElement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Link to AST element which defines the type related element. As this can
 * be an arbitrary element (of ECMAScript or IDL), only an EObject can
 * be specified here.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.SyntaxRelatedTElement#getAstElement <em>Ast Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getSyntaxRelatedTElement()
 * @model abstract="true"
 * @generated
 */
public interface SyntaxRelatedTElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Ast Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ast Element</em>' reference.
	 * @see #setAstElement(EObject)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getSyntaxRelatedTElement_AstElement()
	 * @model
	 * @generated
	 */
	EObject getAstElement();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.SyntaxRelatedTElement#getAstElement <em>Ast Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ast Element</em>' reference.
	 * @see #getAstElement()
	 * @generated
	 */
	void setAstElement(EObject value);

} // SyntaxRelatedTElement
