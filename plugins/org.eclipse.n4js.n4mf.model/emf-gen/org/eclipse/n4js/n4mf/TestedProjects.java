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
 * A representation of the model object '<em><b>Tested Projects</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Class for collecting a collection of projects tested by the current project.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.TestedProjects#getTestedProjects <em>Tested Projects</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getTestedProjects()
 * @model
 * @generated
 */
public interface TestedProjects extends EObject {
	/**
	 * Returns the value of the '<em><b>Tested Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.TestedProject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tested Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tested Projects</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getTestedProjects_TestedProjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<TestedProject> getTestedProjects();

} // TestedProjects
