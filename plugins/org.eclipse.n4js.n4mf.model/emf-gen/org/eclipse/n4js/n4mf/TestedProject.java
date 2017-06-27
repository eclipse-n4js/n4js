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
 * A representation of the model object '<em><b>Tested Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Representation of a reference to a project that is declared as the tested project for a particular test project.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.TestedProject#getVersionConstraint <em>Version Constraint</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.TestedProject#getDeclaredScope <em>Declared Scope</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getTestedProject()
 * @model
 * @generated
 */
public interface TestedProject extends SimpleProjectDependency {
	/**
	 * Returns the value of the '<em><b>Version Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Constraint</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Constraint</em>' containment reference.
	 * @see #setVersionConstraint(VersionConstraint)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getTestedProject_VersionConstraint()
	 * @model containment="true"
	 * @generated
	 */
	VersionConstraint getVersionConstraint();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.TestedProject#getVersionConstraint <em>Version Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Constraint</em>' containment reference.
	 * @see #getVersionConstraint()
	 * @generated
	 */
	void setVersionConstraint(VersionConstraint value);

	/**
	 * Returns the value of the '<em><b>Declared Scope</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4mf.ProjectDependencyScope}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Scope</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Scope</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ProjectDependencyScope
	 * @see #setDeclaredScope(ProjectDependencyScope)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getTestedProject_DeclaredScope()
	 * @model unique="false"
	 * @generated
	 */
	ProjectDependencyScope getDeclaredScope();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.TestedProject#getDeclaredScope <em>Declared Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Scope</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ProjectDependencyScope
	 * @see #getDeclaredScope()
	 * @generated
	 */
	void setDeclaredScope(ProjectDependencyScope value);

} // TestedProject
