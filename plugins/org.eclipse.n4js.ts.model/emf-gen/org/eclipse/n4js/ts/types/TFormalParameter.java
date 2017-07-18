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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TFormal Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TFormalParameter#isVariadic <em>Variadic</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFormalParameter#getAstInitializer <em>Ast Initializer</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TFormalParameter#isHasInitializerAssignment <em>Has Initializer Assignment</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFormalParameter()
 * @model
 * @generated
 */
public interface TFormalParameter extends IdentifiableElement, TAnnotableElement, SyntaxRelatedTElement, TTypedElement {
	/**
	 * Returns the value of the '<em><b>Variadic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variadic</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variadic</em>' attribute.
	 * @see #setVariadic(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFormalParameter_Variadic()
	 * @model unique="false"
	 * @generated
	 */
	boolean isVariadic();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFormalParameter#isVariadic <em>Variadic</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variadic</em>' attribute.
	 * @see #isVariadic()
	 * @generated
	 */
	void setVariadic(boolean value);

	/**
	 * Returns the value of the '<em><b>Ast Initializer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If and only if the TFormalParameter is used as an AST node, this property holds the initializer
	 * type reference. This occurs in a situation like this:
	 * <pre>
	 * val fn : function(p : int = undefined) => void;
	 * </pre>
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ast Initializer</em>' attribute.
	 * @see #setAstInitializer(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFormalParameter_AstInitializer()
	 * @model unique="false"
	 * @generated
	 */
	String getAstInitializer();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFormalParameter#getAstInitializer <em>Ast Initializer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ast Initializer</em>' attribute.
	 * @see #getAstInitializer()
	 * @generated
	 */
	void setAstInitializer(String value);

	/**
	 * Returns the value of the '<em><b>Has Initializer Assignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Initializer Assignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Initializer Assignment</em>' attribute.
	 * @see #setHasInitializerAssignment(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTFormalParameter_HasInitializerAssignment()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasInitializerAssignment();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TFormalParameter#isHasInitializerAssignment <em>Has Initializer Assignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Initializer Assignment</em>' attribute.
	 * @see #isHasInitializerAssignment()
	 * @generated
	 */
	void setHasInitializerAssignment(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method, returns true if this fparam has an initializer.
	 * <!-- end-model-doc -->
	 * @model unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%java.lang.String%> _astInitializer = this.getAstInitializer();\nreturn (_astInitializer != null);'"
	 * @generated
	 */
	boolean hasASTInitializer();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method, returns true if this or one of the preceding parameters have an initializer.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TFormalParameter%>> _switchResult = null;\n<%org.eclipse.emf.ecore.EObject%> _eContainer = this.eContainer();\nboolean _matched = false;\nif (_eContainer instanceof <%org.eclipse.n4js.ts.types.TFunction%>)\n{\n\t_matched=true;\n\t<%org.eclipse.emf.ecore.EObject%> _eContainer_1 = this.eContainer();\n\t_switchResult = ((<%org.eclipse.n4js.ts.types.TFunction%>) _eContainer_1).getFpars();\n}\nif (!_matched)\n{\n\tif (_eContainer instanceof <%org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef%>)\n\t{\n\t\t_matched=true;\n\t\t<%org.eclipse.emf.ecore.EObject%> _eContainer_1 = this.eContainer();\n\t\t_switchResult = ((<%org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef%>) _eContainer_1).getFpars();\n\t}\n}\nif (!_matched)\n{\n\treturn false;\n}\nfinal <%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TFormalParameter%>> fpars = _switchResult;\nfor (int i = fpars.indexOf(this); (i >= 0); i--)\n{\n\t{\n\t\tfinal <%org.eclipse.n4js.ts.types.TFormalParameter%> fpar = fpars.get(i);\n\t\tif ((fpar.isVariadic() || fpar.isHasInitializerAssignment()))\n\t\t{\n\t\t\treturn true;\n\t\t}\n\t}\n}\nreturn false;'"
	 * @generated
	 */
	boolean isOptional();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Convenience method, returns true iff this fpar is optional or variadic.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (this.isVariadic() || this.isOptional());'"
	 * @generated
	 */
	boolean isVariadicOrOptional();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of formal parameter according to syntax definition, including
	 * preceding variadic modifier and type. The parameter name is omitted. Use when displaying the
	 * parameter as part of an type or a type expression.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%java.lang.StringBuilder%> strb = new <%java.lang.StringBuilder%>();\nboolean _isVariadic = this.isVariadic();\nif (_isVariadic)\n{\n\tstrb.append(\"...\");\n}\n<%org.eclipse.n4js.ts.typeRefs.TypeRef%> _typeRef = this.getTypeRef();\nboolean _tripleNotEquals = (_typeRef != null);\nif (_tripleNotEquals)\n{\n\tstrb.append(this.getTypeRef().getTypeRefAsString());\n}\nelse\n{\n\tstrb.append(\"null\");\n}\nboolean _isHasInitializerAssignment = this.isHasInitializerAssignment();\nif (_isHasInitializerAssignment)\n{\n\tstrb.append(\"=\\u2026\");\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String getFormalParameterAsTypesString();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns string representation of formal parameter according to syntax definition, including
	 * preceding variadic modifier and colon separated type if declared.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%java.lang.StringBuilder%> strb = new <%java.lang.StringBuilder%>();\nboolean _isVariadic = this.isVariadic();\nif (_isVariadic)\n{\n\tstrb.append(\"...\");\n}\nstrb.append(this.getName());\n<%org.eclipse.n4js.ts.typeRefs.TypeRef%> _typeRef = this.getTypeRef();\nboolean _tripleNotEquals = (_typeRef != null);\nif (_tripleNotEquals)\n{\n\tstrb.append(\": \").append(this.getTypeRef().getTypeRefAsString());\n}\nboolean _isHasInitializerAssignment = this.isHasInitializerAssignment();\nif (_isHasInitializerAssignment)\n{\n\tstrb.append(\"=\\u2026\");\n}\nreturn strb.toString();'"
	 * @generated
	 */
	String getFormalParameterAsString();

} // TFormalParameter
