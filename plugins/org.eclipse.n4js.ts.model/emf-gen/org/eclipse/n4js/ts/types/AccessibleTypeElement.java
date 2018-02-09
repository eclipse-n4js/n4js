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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Accessible Type Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.AccessibleTypeElement#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.AccessibleTypeElement#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getAccessibleTypeElement()
 * @model abstract="true"
 * @generated
 */
public interface AccessibleTypeElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Declared Type Access Modifier</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.ts.types.TypeAccessModifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Type Access Modifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type Access Modifier</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypeAccessModifier
	 * @see #setDeclaredTypeAccessModifier(TypeAccessModifier)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAccessibleTypeElement_DeclaredTypeAccessModifier()
	 * @model unique="false"
	 * @generated
	 */
	TypeAccessModifier getDeclaredTypeAccessModifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.AccessibleTypeElement#getDeclaredTypeAccessModifier <em>Declared Type Access Modifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type Access Modifier</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypeAccessModifier
	 * @see #getDeclaredTypeAccessModifier()
	 * @generated
	 */
	void setDeclaredTypeAccessModifier(TypeAccessModifier value);

	/**
	 * Returns the value of the '<em><b>Declared Provided By Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Provided By Runtime</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Provided By Runtime</em>' attribute.
	 * @see #setDeclaredProvidedByRuntime(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getAccessibleTypeElement_DeclaredProvidedByRuntime()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredProvidedByRuntime();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.AccessibleTypeElement#isDeclaredProvidedByRuntime <em>Declared Provided By Runtime</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Provided By Runtime</em>' attribute.
	 * @see #isDeclaredProvidedByRuntime()
	 * @generated
	 */
	void setDeclaredProvidedByRuntime(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns declaredProvidedByRuntime.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.isDeclaredProvidedByRuntime();'"
	 * @generated
	 */
	boolean isProvidedByRuntime();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns declared type if specified, otherwise modifier is computed (exported = project, private otherwise)
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.n4js.ts.types.TypeAccessModifier%&gt; _declaredTypeAccessModifier = this.getDeclaredTypeAccessModifier();\nboolean _equals = &lt;%com.google.common.base.Objects%&gt;.equal(_declaredTypeAccessModifier, &lt;%org.eclipse.n4js.ts.types.TypeAccessModifier%&gt;.UNDEFINED);\nif (_equals)\n{\n\tboolean _isExported = this.isExported();\n\tif (_isExported)\n\t{\n\t\treturn &lt;%org.eclipse.n4js.ts.types.TypeAccessModifier%&gt;.PROJECT;\n\t}\n\telse\n\t{\n\t\treturn &lt;%org.eclipse.n4js.ts.types.TypeAccessModifier%&gt;.PRIVATE;\n\t}\n}\nreturn this.getDeclaredTypeAccessModifier();'"
	 * @generated
	 */
	TypeAccessModifier getTypeAccessModifier();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExported();

} // AccessibleTypeElement
