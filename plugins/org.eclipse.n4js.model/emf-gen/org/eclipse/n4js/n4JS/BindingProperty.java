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
 * A representation of the model object '<em><b>Binding Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingProperty#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingProperty()
 * @model
 * @generated
 */
public interface BindingProperty extends PropertyNameOwner {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(BindingElement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingProperty_Value()
	 * @model containment="true"
	 * @generated
	 */
	BindingElement getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingProperty#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(BindingElement value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _elvis = null;\n&lt;%java.lang.String%&gt; _name = super.getName();\nif (_name != null)\n{\n\t_elvis = _name;\n} else\n{\n\t&lt;%org.eclipse.n4js.n4JS.BindingElement%&gt; _value = this.getValue();\n\t&lt;%org.eclipse.n4js.n4JS.VariableDeclaration%&gt; _varDecl = null;\n\tif (_value!=null)\n\t{\n\t\t_varDecl=_value.getVarDecl();\n\t}\n\t&lt;%java.lang.String%&gt; _name_1 = null;\n\tif (_varDecl!=null)\n\t{\n\t\t_name_1=_varDecl.getName();\n\t}\n\t_elvis = _name_1;\n}\nreturn _elvis;'"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return true;'"
	 * @generated
	 */
	boolean isValidName();

} // BindingProperty
