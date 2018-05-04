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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TFormalParameter%&gt; _fpar = this.getFpar();\n&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt; _typeRef = null;\nif (_fpar!=null)\n{\n\t_typeRef=_fpar.getTypeRef();\n}\nreturn _typeRef;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.ts.types.MemberType%&gt;.SETTER;'"
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
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _name = this.getName();\n&lt;%java.lang.String%&gt; _plus = (\"set \" + _name);\n&lt;%java.lang.String%&gt; _xifexpression = null;\nboolean _isOptional = this.isOptional();\nif (_isOptional)\n{\n\t_xifexpression = \"?\";\n}\nelse\n{\n\t_xifexpression = \"\";\n}\n&lt;%java.lang.String%&gt; _plus_1 = (_plus + _xifexpression);\n&lt;%java.lang.String%&gt; _plus_2 = (_plus_1 + \"(\");\n&lt;%org.eclipse.n4js.ts.types.TFormalParameter%&gt; _fpar = this.getFpar();\n&lt;%java.lang.String%&gt; _formalParameterAsString = null;\nif (_fpar!=null)\n{\n\t_formalParameterAsString=_fpar.getFormalParameterAsString();\n}\n&lt;%java.lang.String%&gt; _plus_3 = (_plus_2 + _formalParameterAsString);\nreturn (_plus_3 + \")\");'"
	 * @generated
	 */
	String getMemberAsString();

} // TSetter
