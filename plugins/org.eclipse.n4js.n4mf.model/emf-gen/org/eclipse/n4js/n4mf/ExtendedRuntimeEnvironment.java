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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Extended Runtime Environment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Class for representing the extended runtime environment project of the current project.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getExtendedRuntimeEnvironment()
 * @model
 * @generated
 */
public interface ExtendedRuntimeEnvironment extends EObject {
	/**
	 * Returns the value of the '<em><b>Extended Runtime Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Runtime Environment</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Runtime Environment</em>' containment reference.
	 * @see #setExtendedRuntimeEnvironment(ProjectReference)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getExtendedRuntimeEnvironment_ExtendedRuntimeEnvironment()
	 * @model containment="true"
	 * @generated
	 */
	ProjectReference getExtendedRuntimeEnvironment();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Runtime Environment</em>' containment reference.
	 * @see #getExtendedRuntimeEnvironment()
	 * @generated
	 */
	void setExtendedRuntimeEnvironment(ProjectReference value);

} // ExtendedRuntimeEnvironment
