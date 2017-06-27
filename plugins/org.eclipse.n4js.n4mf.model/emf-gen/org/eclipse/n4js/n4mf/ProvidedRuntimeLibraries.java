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
 * A representation of the model object '<em><b>Provided Runtime Libraries</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Class for wrapping a collection of provided runtime environments.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProvidedRuntimeLibraries()
 * @model
 * @generated
 */
public interface ProvidedRuntimeLibraries extends EObject {
	/**
	 * Returns the value of the '<em><b>Provided Runtime Libraries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Runtime Libraries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Runtime Libraries</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProvidedRuntimeLibraries_ProvidedRuntimeLibraries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProvidedRuntimeLibraryDependency> getProvidedRuntimeLibraries();

} // ProvidedRuntimeLibraries
