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
 * A representation of the model object '<em><b>Simple Project Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Properties that makes a project unique among other projects (without considering the version).
 * The vendorId identifies the vendor of this project. The projectId identifies the project.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.SimpleProjectDescription#getDeclaredVendorId <em>Declared Vendor Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.SimpleProjectDescription#getProjectId <em>Project Id</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSimpleProjectDescription()
 * @model
 * @generated
 */
public interface SimpleProjectDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Declared Vendor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * the vendor ID
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Vendor Id</em>' attribute.
	 * @see #setDeclaredVendorId(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSimpleProjectDescription_DeclaredVendorId()
	 * @model unique="false"
	 * @generated
	 */
	String getDeclaredVendorId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.SimpleProjectDescription#getDeclaredVendorId <em>Declared Vendor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Vendor Id</em>' attribute.
	 * @see #getDeclaredVendorId()
	 * @generated
	 */
	void setDeclaredVendorId(String value);

	/**
	 * Returns the value of the '<em><b>Project Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * the project ID
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Project Id</em>' attribute.
	 * @see #setProjectId(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getSimpleProjectDescription_ProjectId()
	 * @model unique="false"
	 * @generated
	 */
	String getProjectId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.SimpleProjectDescription#getProjectId <em>Project Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Id</em>' attribute.
	 * @see #getProjectId()
	 * @generated
	 */
	void setProjectId(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _xifexpression = null;\n&lt;%java.lang.String%&gt; _declaredVendorId = this.getDeclaredVendorId();\nboolean _tripleNotEquals = (_declaredVendorId != null);\nif (_tripleNotEquals)\n{\n\t_xifexpression = this.getDeclaredVendorId();\n}\nelse\n{\n\t&lt;%org.eclipse.emf.ecore.EObject%&gt; _eContainer = this.eContainer();\n\t&lt;%org.eclipse.emf.ecore.EObject%&gt; _eContainer_1 = null;\n\tif (_eContainer!=null)\n\t{\n\t\t_eContainer_1=_eContainer.eContainer();\n\t}\n\t&lt;%org.eclipse.emf.ecore.EObject%&gt; _eContainer_2 = null;\n\tif (_eContainer_1!=null)\n\t{\n\t\t_eContainer_2=_eContainer_1.eContainer();\n\t}\n\t&lt;%java.lang.String%&gt; _declaredVendorId_1 = null;\n\tif (((&lt;%org.eclipse.n4js.n4mf.ProjectDescription%&gt;) _eContainer_2)!=null)\n\t{\n\t\t_declaredVendorId_1=((&lt;%org.eclipse.n4js.n4mf.ProjectDescription%&gt;) _eContainer_2).getDeclaredVendorId();\n\t}\n\t_xifexpression = _declaredVendorId_1;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	String getVendorId();

} // SimpleProjectDescription
