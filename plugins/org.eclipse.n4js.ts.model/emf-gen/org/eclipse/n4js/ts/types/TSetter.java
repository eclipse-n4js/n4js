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
package org.eclipse.n4js.ts.types;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TSetter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Setter of a field, declaration (type, name) is derived from field (and its type).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TSetter#getFpar <em>Fpar</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTSetter()
 * @model
 * @generated
 */
public interface TSetter extends FieldAccessor, TMemberWithAccessModifier {
	/**
	 * Returns the value of the '<em><b>Fpar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fpar</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fpar</em>' containment reference.
	 * @see #setFpar(TFormalParameter)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTSetter_Fpar()
	 * @model containment="true"
	 * @generated
	 */
	TFormalParameter getFpar();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TSetter#getFpar <em>Fpar</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fpar</em>' containment reference.
	 * @see #getFpar()
	 * @generated
	 */
	void setFpar(TFormalParameter value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.n4js.ts.types.TFormalParameter%> _fpar = this.getFpar();\n<%org.eclipse.n4js.ts.typeRefs.TypeRef%> _typeRef = null;\nif (_fpar!=null)\n{\n\t_typeRef=_fpar.getTypeRef();\n}\nreturn _typeRef;'"
	 * @generated
	 */
	TypeRef getDeclaredTypeRef();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return false;'"
	 * @generated
	 */
	boolean isReadable();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return true;'"
	 * @generated
	 */
	boolean isWriteable();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Always returns SETTER
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%org.eclipse.n4js.ts.types.MemberType%>.SETTER;'"
	 * @generated
	 */
	MemberType getMemberType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of setter according to syntax definition.
	 * Overrides TMember's method.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.String%> _name = this.getName();\n<%java.lang.String%> _plus = (\"set \" + _name);\n<%java.lang.String%> _xifexpression = null;\nboolean _isOptional = this.isOptional();\nif (_isOptional)\n{\n\t_xifexpression = \"?\";\n}\nelse\n{\n\t_xifexpression = \"\";\n}\n<%java.lang.String%> _plus_1 = (_plus + _xifexpression);\n<%java.lang.String%> _plus_2 = (_plus_1 + \"(\");\n<%org.eclipse.n4js.ts.types.TFormalParameter%> _fpar = this.getFpar();\n<%java.lang.String%> _formalParameterAsString = null;\nif (_fpar!=null)\n{\n\t_formalParameterAsString=_fpar.getFormalParameterAsString();\n}\n<%java.lang.String%> _plus_3 = (_plus_2 + _formalParameterAsString);\nreturn (_plus_3 + \")\");'"
	 * @generated
	 */
	String getMemberAsString();

} // TSetter
