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
package org.eclipse.n4js.n4mf;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Lists the filters that add special treatment to some of the files regarding
 * validation, compilation and mapping of implementation
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.ModuleFilter#getModuleFilterType <em>Module Filter Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ModuleFilter#getModuleSpecifiers <em>Module Specifiers</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getModuleFilter()
 * @model
 * @generated
 */
public interface ModuleFilter extends EObject {
	/**
	 * Returns the value of the '<em><b>Module Filter Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4mf.ModuleFilterType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Filter Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Filter Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ModuleFilterType
	 * @see #setModuleFilterType(ModuleFilterType)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getModuleFilter_ModuleFilterType()
	 * @model unique="false"
	 * @generated
	 */
	ModuleFilterType getModuleFilterType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ModuleFilter#getModuleFilterType <em>Module Filter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Filter Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ModuleFilterType
	 * @see #getModuleFilterType()
	 * @generated
	 */
	void setModuleFilterType(ModuleFilterType value);

	/**
	 * Returns the value of the '<em><b>Module Specifiers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ModuleFilterSpecifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Specifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Specifiers</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getModuleFilter_ModuleSpecifiers()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModuleFilterSpecifier> getModuleSpecifiers();

} // ModuleFilter
