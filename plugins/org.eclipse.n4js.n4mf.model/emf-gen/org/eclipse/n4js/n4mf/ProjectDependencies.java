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
 * A representation of the model object '<em><b>Project Dependencies</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Class for wrapping a collection of dependencies for the particular project.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDependencies#getProjectDependencies <em>Project Dependencies</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDependencies()
 * @model
 * @generated
 */
public interface ProjectDependencies extends EObject {
	/**
	 * Returns the value of the '<em><b>Project Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProjectDependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Dependencies</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDependencies_ProjectDependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectDependency> getProjectDependencies();

} // ProjectDependencies
