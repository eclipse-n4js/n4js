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
 * A representation of the model object '<em><b>N4 Namespace Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4NamespaceDeclaration#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.N4NamespaceDeclaration#getOwnedElementsRaw <em>Owned Elements Raw</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4NamespaceDeclaration()
 * @model
 * @generated
 */
public interface N4NamespaceDeclaration extends N4TypeDefinition, VariableEnvironmentElement, ScriptElement, ModifiableElement, ExportableElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4NamespaceDeclaration_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4NamespaceDeclaration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Owned Elements Raw</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.ScriptElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Elements Raw</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4NamespaceDeclaration_OwnedElementsRaw()
	 * @model containment="true"
	 * @generated
	 */
	EList<ScriptElement> getOwnedElementsRaw();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if type is declared as external.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExternal();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Namespaces do not have annotations
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isHollow();

} // N4NamespaceDeclaration
