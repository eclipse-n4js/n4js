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
 * A representation of the model object '<em><b>Full Member Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Full reference to a member.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#getMemberName <em>Member Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#isStaticMember <em>Static Member</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getFullMemberReference()
 * @model
 * @generated
 */
public interface FullMemberReference extends FullTypeReference {
	/**
	 * Returns the value of the '<em><b>Member Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Member Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member Name</em>' attribute.
	 * @see #setMemberName(String)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getFullMemberReference_MemberName()
	 * @model unique="false"
	 * @generated
	 */
	String getMemberName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#getMemberName <em>Member Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Member Name</em>' attribute.
	 * @see #getMemberName()
	 * @generated
	 */
	void setMemberName(String value);

	/**
	 * Returns the value of the '<em><b>Static Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Static Member</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Static Member</em>' attribute.
	 * @see #setStaticMember(boolean)
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#getFullMemberReference_StaticMember()
	 * @model unique="false"
	 * @generated
	 */
	boolean isStaticMember();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#isStaticMember <em>Static Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static Member</em>' attribute.
	 * @see #isStaticMember()
	 * @generated
	 */
	void setStaticMember(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((this.getMemberName() != null) &amp;&amp; (!this.getMemberName().isEmpty()));'"
	 * @generated
	 */
	boolean memberNameSet();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _string = super.toString();\n&lt;%java.lang.StringBuilder%&gt; strb = new &lt;%java.lang.StringBuilder%&gt;(_string);\nboolean _memberNameSet = this.memberNameSet();\nif (_memberNameSet)\n{\n\tint _length = strb.length();\n\tboolean _greaterThan = (_length &gt; 0);\n\tif (_greaterThan)\n\t{\n\t\tboolean _isStaticMember = this.isStaticMember();\n\t\tif (_isStaticMember)\n\t\t{\n\t\t\tstrb.append(\"#\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\tstrb.append(\".\");\n\t\t}\n\t}\n\tstrb.append(this.getMemberName());\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String toString();

} // FullMemberReference
