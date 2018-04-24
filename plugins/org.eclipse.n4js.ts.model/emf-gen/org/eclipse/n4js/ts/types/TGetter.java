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
 * A representation of the model object '<em><b>TGetter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Getter of a field, declaration (type, name) is derived from field (and its type).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TGetter#getDeclaredTypeRef <em>Declared Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTGetter()
 * @model
 * @generated
 */
public interface TGetter extends FieldAccessor, TMemberWithAccessModifier {
	/**
	 * Returns the value of the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Type Ref</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type Ref</em>' containment reference.
	 * @see #setDeclaredTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTGetter_DeclaredTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TGetter#getDeclaredTypeRef <em>Declared Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type Ref</em>' containment reference.
	 * @see #getDeclaredTypeRef()
	 * @generated
	 */
	void setDeclaredTypeRef(TypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * Always returns GETTER
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.ts.types.MemberType%&gt;.GETTER;'"
	 * @generated
	 */
	MemberType getMemberType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of getter according to syntax definition, including
	 * colon separated (return) type if declared.  Overrides TMember's method.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final &lt;%java.lang.StringBuilder%&gt; strb = new &lt;%java.lang.StringBuilder%&gt;(\"get \");\nstrb.append(this.getName());\nboolean _isOptional = this.isOptional();\nif (_isOptional)\n{\n\tstrb.append(\"?\");\n}\nstrb.append(\"()\");\n&lt;%org.eclipse.n4js.ts.typeRefs.TypeRef%&gt; _declaredTypeRef = this.getDeclaredTypeRef();\nboolean _tripleNotEquals = (_declaredTypeRef != null);\nif (_tripleNotEquals)\n{\n\tstrb.append(\": \").append(this.getDeclaredTypeRef().getTypeRefAsString());\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String getMemberAsString();

} // TGetter
