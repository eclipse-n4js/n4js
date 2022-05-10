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
 * A representation of the model object '<em><b>Element Export Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Represents the direct or indirect (re-)export of an {@link TExportableElement exportable element}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.ElementExportDefinition#getExportedName <em>Exported Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ElementExportDefinition#getExportedElement <em>Exported Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getElementExportDefinition()
 * @model
 * @generated
 */
public interface ElementExportDefinition extends ExportDefinition {
	/**
	 * Returns the value of the '<em><b>Exported Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exported Name</em>' attribute.
	 * @see #setExportedName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getElementExportDefinition_ExportedName()
	 * @model unique="false"
	 * @generated
	 */
	String getExportedName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ElementExportDefinition#getExportedName <em>Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exported Name</em>' attribute.
	 * @see #getExportedName()
	 * @generated
	 */
	void setExportedName(String value);

	/**
	 * Returns the value of the '<em><b>Exported Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The element being exported under name {@link ExportDefinition#getExportedName() exportedName}.
	 * This element need not be contained in this definition's parent {@link TExportingElement}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Exported Element</em>' reference.
	 * @see #setExportedElement(TExportableElement)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getElementExportDefinition_ExportedElement()
	 * @model
	 * @generated
	 */
	TExportableElement getExportedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ElementExportDefinition#getExportedElement <em>Exported Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exported Element</em>' reference.
	 * @see #getExportedElement()
	 * @generated
	 */
	void setExportedElement(TExportableElement value);

} // ElementExportDefinition
