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
package org.eclipse.n4js.jsdoc.dom;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage
 * @generated
 */
public interface DomFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomFactory eINSTANCE = org.eclipse.n4js.jsdoc.dom.impl.DomFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Doclet</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Doclet</em>'.
	 * @generated
	 */
	Doclet createDoclet();

	/**
	 * Returns a new object of class '<em>Tag Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tag Value</em>'.
	 * @generated
	 */
	TagValue createTagValue();

	/**
	 * Returns a new object of class '<em>Tag Title</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tag Title</em>'.
	 * @generated
	 */
	TagTitle createTagTitle();

	/**
	 * Returns a new object of class '<em>Line Tag</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Line Tag</em>'.
	 * @generated
	 */
	LineTag createLineTag();

	/**
	 * Returns a new object of class '<em>Inline Tag</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Inline Tag</em>'.
	 * @generated
	 */
	InlineTag createInlineTag();

	/**
	 * Returns a new object of class '<em>Text</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text</em>'.
	 * @generated
	 */
	Text createText();

	/**
	 * Returns a new object of class '<em>Simple Type Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Type Reference</em>'.
	 * @generated
	 */
	SimpleTypeReference createSimpleTypeReference();

	/**
	 * Returns a new object of class '<em>Full Type Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Full Type Reference</em>'.
	 * @generated
	 */
	FullTypeReference createFullTypeReference();

	/**
	 * Returns a new object of class '<em>Full Member Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Full Member Reference</em>'.
	 * @generated
	 */
	FullMemberReference createFullMemberReference();

	/**
	 * Returns a new object of class '<em>Variable Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Reference</em>'.
	 * @generated
	 */
	VariableReference createVariableReference();

	/**
	 * Returns a new object of class '<em>Generic Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Reference</em>'.
	 * @generated
	 */
	GenericReference createGenericReference();

	/**
	 * Returns a new object of class '<em>Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Literal</em>'.
	 * @generated
	 */
	Literal createLiteral();

	/**
	 * Returns a new object of class '<em>Marker</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Marker</em>'.
	 * @generated
	 */
	Marker createMarker();

	/**
	 * Returns a new object of class '<em>Composed Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composed Content</em>'.
	 * @generated
	 */
	ComposedContent createComposedContent();

	/**
	 * Returns a new object of class '<em>Structured Text</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structured Text</em>'.
	 * @generated
	 */
	StructuredText createStructuredText();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DomPackage getDomPackage();

} //DomFactory
