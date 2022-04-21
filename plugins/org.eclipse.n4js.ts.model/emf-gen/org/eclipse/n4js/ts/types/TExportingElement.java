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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TExporting Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An element that may export one or more {@link TExportableElement}s.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TExportingElement#getExportDefinitions <em>Export Definitions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTExportingElement()
 * @model abstract="true"
 * @generated
 */
public interface TExportingElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Export Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.ExportDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Export Definitions</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTExportingElement_ExportDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExportDefinition> getExportDefinitions();

} // TExportingElement
