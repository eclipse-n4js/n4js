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

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.typeRefs.TypeRef;

import org.eclipse.n4js.ts.types.util.Variance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredCovariant <em>Declared Covariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredContravariant <em>Declared Contravariant</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeVariable#getDeclaredUpperBound <em>Declared Upper Bound</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable()
 * @model
 * @generated
 */
public interface TypeVariable extends Type {
	/**
	 * Returns the value of the '<em><b>Declared Covariant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Covariant</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Covariant</em>' attribute.
	 * @see #setDeclaredCovariant(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable_DeclaredCovariant()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredCovariant();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredCovariant <em>Declared Covariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Covariant</em>' attribute.
	 * @see #isDeclaredCovariant()
	 * @generated
	 */
	void setDeclaredCovariant(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Contravariant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Contravariant</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Contravariant</em>' attribute.
	 * @see #setDeclaredContravariant(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable_DeclaredContravariant()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredContravariant();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeVariable#isDeclaredContravariant <em>Declared Contravariant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Contravariant</em>' attribute.
	 * @see #isDeclaredContravariant()
	 * @generated
	 */
	void setDeclaredContravariant(boolean value);

	/**
	 * Returns the value of the '<em><b>Declared Upper Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Upper Bound</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Upper Bound</em>' containment reference.
	 * @see #setDeclaredUpperBound(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeVariable_DeclaredUpperBound()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredUpperBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeVariable#getDeclaredUpperBound <em>Declared Upper Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Upper Bound</em>' containment reference.
	 * @see #getDeclaredUpperBound()
	 * @generated
	 */
	void setDeclaredUpperBound(TypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns this type variable's {@link org.eclipse.n4js.ts.types.util.Variance variance}. Always returns
	 * invariant, unless the type variable was explicitly declared on definition site to be co- or contravariant.
	 * <!-- end-model-doc -->
	 * @model kind="operation" dataType="org.eclipse.n4js.ts.types.Variance" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final boolean co = this.isDeclaredCovariant();\nfinal boolean contra = this.isDeclaredContravariant();\nif ((co &amp;&amp; (!contra)))\n{\n\treturn &lt;%org.eclipse.n4js.ts.types.util.Variance%&gt;.CO;\n}\nelse\n{\n\tif ((contra &amp;&amp; (!co)))\n\t{\n\t\treturn &lt;%org.eclipse.n4js.ts.types.util.Variance%&gt;.CONTRA;\n\t}\n\telse\n\t{\n\t\treturn &lt;%org.eclipse.n4js.ts.types.util.Variance%&gt;.INV;\n\t}\n}'"
	 * @generated
	 */
	Variance getVariance();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.ts.types.TypeVariable%&gt;&gt;emptyEList();'"
	 * @generated
	 */
	EList<TypeVariable> getTypeVars();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getTypeVariableAsString(this.getDeclaredUpperBound());'"
	 * @generated
	 */
	String getTypeAsString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" upperBoundUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _xifexpression = null;\nboolean _isDeclaredCovariant = this.isDeclaredCovariant();\nif (_isDeclaredCovariant)\n{\n\t_xifexpression = \"out \";\n}\nelse\n{\n\t&lt;%java.lang.String%&gt; _xifexpression_1 = null;\n\tboolean _isDeclaredContravariant = this.isDeclaredContravariant();\n\tif (_isDeclaredContravariant)\n\t{\n\t\t_xifexpression_1 = \"in \";\n\t}\n\telse\n\t{\n\t\t_xifexpression_1 = \"\";\n\t}\n\t_xifexpression = _xifexpression_1;\n}\n&lt;%java.lang.String%&gt; _name = this.getName();\n&lt;%java.lang.String%&gt; _plus = (_xifexpression + _name);\n&lt;%java.lang.String%&gt; _xifexpression_2 = null;\nif ((upperBound != null))\n{\n\t&lt;%java.lang.String%&gt; _typeRefAsString = upperBound.getTypeRefAsString();\n\t_xifexpression_2 = (\" extends \" + _typeRefAsString);\n}\nelse\n{\n\t_xifexpression_2 = \"\";\n}\nreturn (_plus + _xifexpression_2);'"
	 * @generated
	 */
	String getTypeVariableAsString(TypeRef upperBound);

} // TypeVariable
