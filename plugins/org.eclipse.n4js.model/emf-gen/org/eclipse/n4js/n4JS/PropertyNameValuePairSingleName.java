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
package org.eclipse.n4js.n4JS;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Name Value Pair Single Name</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName#getIdentifierRef <em>Identifier Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameValuePairSingleName()
 * @model
 * @generated
 */
public interface PropertyNameValuePairSingleName extends PropertyNameValuePair {
	/**
	 * Returns the value of the '<em><b>Identifier Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identifier Ref</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier Ref</em>' containment reference.
	 * @see #setIdentifierRef(IdentifierRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameValuePairSingleName_IdentifierRef()
	 * @model containment="true"
	 * @generated
	 */
	IdentifierRef getIdentifierRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName#getIdentifierRef <em>Identifier Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identifier Ref</em>' containment reference.
	 * @see #getIdentifierRef()
	 * @generated
	 */
	void setIdentifierRef(IdentifierRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Note: if this AST node was created by the parser, then super.getName() will always be null and we get
	 * the name from the parse tree; if this AST node is created programmatically (e.g. refactoring), then
	 * the name has to be set explicitly!
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _elvis = null;\n&lt;%java.lang.String%&gt; _name = super.getName();\nif (_name != null)\n{\n\t_elvis = _name;\n} else\n{\n\t&lt;%org.eclipse.n4js.n4JS.IdentifierRef%&gt; _identifierRef = this.getIdentifierRef();\n\t&lt;%java.lang.String%&gt; _idAsText = null;\n\tif (_identifierRef!=null)\n\t{\n\t\t_idAsText=_identifierRef.getIdAsText();\n\t}\n\t_elvis = _idAsText;\n}\nreturn _elvis;'"
	 * @generated
	 */
	String getName();

} // PropertyNameValuePairSingleName
