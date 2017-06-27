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
 * A representation of the model object '<em><b>Exec Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Class for wrapping the execution module.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.ExecModule#getExecModule <em>Exec Module</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getExecModule()
 * @model
 * @generated
 */
public interface ExecModule extends EObject {
	/**
	 * Returns the value of the '<em><b>Exec Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exec Module</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exec Module</em>' containment reference.
	 * @see #setExecModule(BootstrapModule)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getExecModule_ExecModule()
	 * @model containment="true"
	 * @generated
	 */
	BootstrapModule getExecModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ExecModule#getExecModule <em>Exec Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exec Module</em>' containment reference.
	 * @see #getExecModule()
	 * @generated
	 */
	void setExecModule(BootstrapModule value);

} // ExecModule
