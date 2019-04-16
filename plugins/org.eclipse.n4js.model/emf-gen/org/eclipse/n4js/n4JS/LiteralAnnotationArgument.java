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
 * A representation of the model object '<em><b>Literal Annotation Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * AST Annotation Argument with a literal, it is recommended to use
 * TAnnotationStringArgument or corresponding type model related field
 * to access this information.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.LiteralAnnotationArgument#getLiteral <em>Literal</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLiteralAnnotationArgument()
 * @model
 * @generated
 */
public interface LiteralAnnotationArgument extends AnnotationArgument {
	/**
	 * Returns the value of the '<em><b>Literal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literal</em>' containment reference.
	 * @see #setLiteral(Literal)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getLiteralAnnotationArgument_Literal()
	 * @model containment="true"
	 * @generated
	 */
	Literal getLiteral();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.LiteralAnnotationArgument#getLiteral <em>Literal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal</em>' containment reference.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(Literal value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	Literal value();

} // LiteralAnnotationArgument
