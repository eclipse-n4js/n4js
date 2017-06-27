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
package org.eclipse.n4js.jsdoc.dom;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Full Type Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.FullTypeReference#getModuleName <em>Module Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getFullTypeReference()
 * @model
 * @generated
 */
public interface FullTypeReference extends SimpleTypeReference {
	/**
	 * Returns the value of the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Name</em>' attribute.
	 * @see #setModuleName(String)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getFullTypeReference_ModuleName()
	 * @model unique="false"
	 * @generated
	 */
	String getModuleName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.FullTypeReference#getModuleName <em>Module Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Name</em>' attribute.
	 * @see #getModuleName()
	 * @generated
	 */
	void setModuleName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getModuleName() != null) && (!this.getModuleName().isEmpty()));'"
	 * @generated
	 */
	boolean moduleNameSet();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.StringBuilder%> strb = new <%java.lang.StringBuilder%>();\nboolean _moduleNameSet = this.moduleNameSet();\nif (_moduleNameSet)\n{\n\t<%java.lang.String%> _moduleName = this.getModuleName();\n\tstrb.append(_moduleName);\n}\nboolean _typeNameSet = this.typeNameSet();\nif (_typeNameSet)\n{\n\tint _length = strb.length();\n\tboolean _greaterThan = (_length > 0);\n\tif (_greaterThan)\n\t{\n\t\tstrb.append(\".\");\n\t}\n\t<%java.lang.String%> _typeName = this.getTypeName();\n\tstrb.append(_typeName);\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String toString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.StringBuilder%> strb = new <%java.lang.StringBuilder%>();\nboolean _moduleNameSet = this.moduleNameSet();\nif (_moduleNameSet)\n{\n\t<%java.lang.String%> _moduleName = this.getModuleName();\n\tstrb.append(_moduleName);\n}\nboolean _typeNameSet = this.typeNameSet();\nif (_typeNameSet)\n{\n\tstrb.append(\".\");\n\t<%java.lang.String%> _typeName = this.getTypeName();\n\tstrb.append(_typeName);\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String fullTypeName();

} // FullTypeReference
