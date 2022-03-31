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
 * A representation of the model object '<em><b>Module Export Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Represents the re-export of another module of the form
 * <pre>
 * export * from "other/module"
 * </pre>
 * <p>
 * Note that this class is *not* used to represent the following two cases:
 * <ul>
 * <li>module re-exports using an alias (i.e. {@code export * as ALIAS from "other/module"}):
 *     these are represented as an {@link ElementExportDefinition} with property
 *     {@link ElementExportDefinition#getExportedElement() exportedElement}
 *     set to a {@link ModuleNamespaceVirtualType} wrapping the re-exported module.
 * <li>(re-)exports of namespaces:
 *     these are represented as an {@link ElementExportDefinition}, as well.
 * </ul>
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.ModuleExportDefinition#getExportedModule <em>Exported Module</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getModuleExportDefinition()
 * @model
 * @generated
 */
public interface ModuleExportDefinition extends ExportDefinition {
	/**
	 * Returns the value of the '<em><b>Exported Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The module being re-exported. All the module's elements will be re-exported individually under their name.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Exported Module</em>' reference.
	 * @see #setExportedModule(TModule)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getModuleExportDefinition_ExportedModule()
	 * @model
	 * @generated
	 */
	TModule getExportedModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ModuleExportDefinition#getExportedModule <em>Exported Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exported Module</em>' reference.
	 * @see #getExportedModule()
	 * @generated
	 */
	void setExportedModule(TModule value);

} // ModuleExportDefinition
