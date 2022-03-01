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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Namespace Virtual Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Virtual type for ES6 namespace imports. Not directly available for N4JS users, can be used only
 * like TypeReferenceName. See ES6::9.4.6 Module Namespace Exotic Objects
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType#getModule <em>Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType#isDeclaredDynamic <em>Declared Dynamic</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getModuleNamespaceVirtualType()
 * @model
 * @generated
 */
public interface ModuleNamespaceVirtualType extends Type, SyntaxRelatedTElement {
	/**
	 * Returns the value of the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * reference to module which give namespace represents
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Module</em>' reference.
	 * @see #setModule(TModule)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getModuleNamespaceVirtualType_Module()
	 * @model
	 * @generated
	 */
	TModule getModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType#getModule <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(TModule value);

	/**
	 * Returns the value of the '<em><b>Declared Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * If true, tmodule behaves like a dynamic type enabling access to plain js
	 * modules without n4jsd file.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Dynamic</em>' attribute.
	 * @see #setDeclaredDynamic(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getModuleNamespaceVirtualType_DeclaredDynamic()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredDynamic();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType#isDeclaredDynamic <em>Declared Dynamic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Dynamic</em>' attribute.
	 * @see #isDeclaredDynamic()
	 * @generated
	 */
	void setDeclaredDynamic(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isProvidedByRuntime();

} // ModuleNamespaceVirtualType
