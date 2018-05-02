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
package org.eclipse.n4js.transpiler.im;

import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;

import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Property Access Expression IM</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * SymbolTable-aware replacement for {@link ParameterizedPropertyAccessExpression}.
 * Original property {@link ParameterizedPropertyAccessExpression.property} is always {@code null}.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#isAnyPlusAccess <em>Any Plus Access</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getNameOfAnyPlusProperty <em>Name Of Any Plus Property</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getParameterizedPropertyAccessExpression_IM()
 * @model
 * @generated
 */
public interface ParameterizedPropertyAccessExpression_IM extends ParameterizedPropertyAccessExpression, ReferencingElementExpression_IM {
	/**
	 * Returns the value of the '<em><b>Any Plus Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Any Plus Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Any Plus Access</em>' attribute.
	 * @see #setAnyPlusAccess(boolean)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getParameterizedPropertyAccessExpression_IM_AnyPlusAccess()
	 * @model unique="false"
	 * @generated
	 */
	boolean isAnyPlusAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#isAnyPlusAccess <em>Any Plus Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Any Plus Access</em>' attribute.
	 * @see #isAnyPlusAccess()
	 * @generated
	 */
	void setAnyPlusAccess(boolean value);

	/**
	 * Returns the value of the '<em><b>Name Of Any Plus Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Name of the accessed property in case of a <code>any+</code> property access.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name Of Any Plus Property</em>' attribute.
	 * @see #setNameOfAnyPlusProperty(String)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getParameterizedPropertyAccessExpression_IM_NameOfAnyPlusProperty()
	 * @model unique="false"
	 * @generated
	 */
	String getNameOfAnyPlusProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getNameOfAnyPlusProperty <em>Name Of Any Plus Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Of Any Plus Property</em>' attribute.
	 * @see #getNameOfAnyPlusProperty()
	 * @generated
	 */
	void setNameOfAnyPlusProperty(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getRewiredTarget();'"
	 * @generated
	 */
	SymbolTableEntry getProperty_IM();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model targetUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='this.setRewiredTarget(target);'"
	 * @generated
	 */
	void setProperty_IM(SymbolTableEntry target);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _xifexpression = null;\nboolean _isAnyPlusAccess = this.isAnyPlusAccess();\nif (_isAnyPlusAccess)\n{\n\t_xifexpression = this.getNameOfAnyPlusProperty();\n}\nelse\n{\n\tfinal &lt;%org.eclipse.n4js.transpiler.im.SymbolTableEntry%&gt; e = this.getRewiredTarget();\n\tif ((e instanceof &lt;%org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal%&gt;))\n\t{\n\t\tfinal &lt;%java.lang.String%&gt; exName = ((&lt;%org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal%&gt;)e).exportedName();\n\t\tif ((exName != null))\n\t\t{\n\t\t\treturn exName;\n\t\t}\n\t\telse\n\t\t{\n\t\t\t&lt;%org.eclipse.n4js.transpiler.im.SymbolTableEntry%&gt; _property_IM = this.getProperty_IM();\n\t\t\t&lt;%java.lang.String%&gt; _name = null;\n\t\t\tif (_property_IM!=null)\n\t\t\t{\n\t\t\t\t_name=_property_IM.getName();\n\t\t\t}\n\t\t\treturn _name;\n\t\t}\n\t}\n\telse\n\t{\n\t\t&lt;%org.eclipse.n4js.transpiler.im.SymbolTableEntry%&gt; _property_IM_1 = this.getProperty_IM();\n\t\t&lt;%java.lang.String%&gt; _name_1 = null;\n\t\tif (_property_IM_1!=null)\n\t\t{\n\t\t\t_name_1=_property_IM_1.getName();\n\t\t}\n\t\treturn _name_1;\n\t}\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	String getPropertyName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  overridden attribute access to always return null
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return null;'"
	 * @generated
	 */
	IdentifiableElement getProperty();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ixUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='if ((ix != null))\n{\n\tthrow new &lt;%java.lang.IllegalArgumentException%&gt;(\"ParameterizedPropertyAccessExpression_IM cannot accept properties. Use #property_IM.\");\n}'"
	 * @generated
	 */
	void setProperty(IdentifiableElement ix);

} // ParameterizedPropertyAccessExpression_IM
