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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Project Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSimpleProjectDependency()
 * @model
 * @generated
 */
public interface SimpleProjectDependency extends ProjectReference {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * COMPILE by default
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.n4mf.ProjectDependencyScope%&gt;.COMPILE;'"
	 * @generated
	 */
	ProjectDependencyScope getScope();

} // SimpleProjectDependency
