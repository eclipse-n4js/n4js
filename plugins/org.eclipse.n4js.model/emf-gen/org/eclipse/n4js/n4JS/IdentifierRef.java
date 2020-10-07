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

import org.eclipse.n4js.ts.typeRefs.Versionable;

import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Identifier Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.IdentifierRef#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.IdentifierRef#getIdAsText <em>Id As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.IdentifierRef#getOriginImport <em>Origin Import</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIdentifierRef()
 * @model
 * @generated
 */
public interface IdentifierRef extends PrimaryExpression, StrictModeRelevant, Versionable {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' reference.
	 * @see #setId(IdentifiableElement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIdentifierRef_Id()
	 * @model
	 * @generated
	 */
	IdentifiableElement getId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.IdentifierRef#getId <em>Id</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' reference.
	 * @see #getId()
	 * @generated
	 */
	void setId(IdentifiableElement value);

	/**
	 * Returns the value of the '<em><b>Id As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id As Text</em>' attribute.
	 * @see #setIdAsText(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIdentifierRef_IdAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getIdAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.IdentifierRef#getIdAsText <em>Id As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id As Text</em>' attribute.
	 * @see #getIdAsText()
	 * @generated
	 */
	void setIdAsText(String value);

	/**
	 * Returns the value of the '<em><b>Origin Import</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the element returned by {@link IdentifierRef#getId() getId()} was imported, this refers to
	 * the corresponding import. Will be set during proxy resolution of property 'id'.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Origin Import</em>' reference.
	 * @see #setOriginImport(ImportSpecifier)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getIdentifierRef_OriginImport()
	 * @model transient="true"
	 * @generated
	 */
	ImportSpecifier getOriginImport();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.IdentifierRef#getOriginImport <em>Origin Import</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin Import</em>' reference.
	 * @see #getOriginImport()
	 * @generated
	 */
	void setOriginImport(ImportSpecifier value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Similar to {@link IdentifierRef#getId() getId()}, but in case of references via the namespace of
	 * a namespace import this method returns the actually referenced element instead of the
	 * {@link ModuleNamespaceVirtualType} representing the namespace.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	IdentifiableElement getTargetElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * IdentifierReference : Identifier
	 * 1. If this IdentifierReference is contained in strict mode code and StringValue of Identifier is "eval" or "arguments", return false.
	 * 2. Return true.
	 * IdentifierReference : yield
	 * 1. Return true.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidSimpleAssignmentTarget();

} // IdentifierRef
