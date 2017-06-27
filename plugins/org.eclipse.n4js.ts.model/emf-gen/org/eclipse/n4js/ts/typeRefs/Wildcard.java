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
package org.eclipse.n4js.ts.typeRefs;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wildcard</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Note: Wildcard has special copy-semantics to preserve the implicit upper bound returned by method
 * {@link Wildcard#getDeclaredOrImplicitUpperBounds()}. When copied, the implicit upper bound is set
 * as declared upper bound in the copy. For details see TypeUtils#copy().
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredUpperBound <em>Declared Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredLowerBound <em>Declared Lower Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.Wildcard#isUsingInOutNotation <em>Using In Out Notation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getWildcard()
 * @model
 * @generated
 */
public interface Wildcard extends TypeArgument {
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
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getWildcard_DeclaredUpperBound()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredUpperBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredUpperBound <em>Declared Upper Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Upper Bound</em>' containment reference.
	 * @see #getDeclaredUpperBound()
	 * @generated
	 */
	void setDeclaredUpperBound(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Declared Lower Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Lower Bound</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Lower Bound</em>' containment reference.
	 * @see #setDeclaredLowerBound(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getWildcard_DeclaredLowerBound()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getDeclaredLowerBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredLowerBound <em>Declared Lower Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Lower Bound</em>' containment reference.
	 * @see #getDeclaredLowerBound()
	 * @generated
	 */
	void setDeclaredLowerBound(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Using In Out Notation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For wildcards contained in the AST, this tells if the wildcard was written in the source code using in/out
	 * notation, i.e. returns <code>true</code> for <code>G&lt;out C></code> and <code>false</code> for
	 * <code>G&lt;? extends C></code>.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Using In Out Notation</em>' attribute.
	 * @see #setUsingInOutNotation(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getWildcard_UsingInOutNotation()
	 * @model unique="false"
	 * @generated
	 */
	boolean isUsingInOutNotation();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#isUsingInOutNotation <em>Using In Out Notation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Using In Out Notation</em>' attribute.
	 * @see #isUsingInOutNotation()
	 * @generated
	 */
	void setUsingInOutNotation(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An unbounded wildcard given as type argument to a bounded type parameter is implicitly bounded.
	 * For example:
	 * <pre>
	 * class A {}
	 * class G&lt;T extends A> {}
	 * 	 * var G&lt;?> g; // "?" is actually "? extends A"
	 * class H extends G&lt;?> {} // "?" is actually "? extends A"
	 * </pre>
	 * This method returns the declared upper bound of the receiving wildcard or, if the wildcard is unbounded,
	 * tries to obtain the implicit upper bound from the corresponding type parameter of the containing
	 * ParameterizedTypeRef's declared type (if any).
	 * <p>
	 * <b>IMPORTANT:</b> Use of such implicit bounds is prone to infinite recursion and must therefore be guarded
	 * appropriately. Take these two examples:
	 * <pre>
	 * class A&lt;T extends A&lt;?>> {} // directly
	 * </pre><pre>
	 * class X&lt;T extends B&lt;?>> {}
	 * class Y&lt;T extends X&lt;?>> {}
	 * class B&lt;T extends Y&lt;?>> {} // indirectly
	 * </pre>
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='final <%org.eclipse.n4js.ts.typeRefs.TypeRef%> declUB = this.getDeclaredUpperBound();\nif ((declUB != null))\n{\n\treturn declUB;\n}\n<%org.eclipse.n4js.ts.typeRefs.TypeRef%> _declaredLowerBound = this.getDeclaredLowerBound();\nboolean _tripleNotEquals = (_declaredLowerBound != null);\nif (_tripleNotEquals)\n{\n\treturn null;\n}\nfinal <%org.eclipse.emf.ecore.EObject%> parent = this.eContainer();\nif ((parent instanceof <%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%>))\n{\n\t<%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.typeRefs.TypeArgument%>> _typeArgs = ((<%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%>)parent).getTypeArgs();\n\tfinal int typeArgIndex = _typeArgs.indexOf(this);\n\tif ((typeArgIndex >= 0))\n\t{\n\t\t<%org.eclipse.emf.ecore.EReference%> _parameterizedTypeRef_DeclaredType = <%org.eclipse.n4js.ts.typeRefs.TypeRefsPackage%>.eINSTANCE.getParameterizedTypeRef_DeclaredType();\n\t\tfinal <%java.lang.Object%> declType = ((<%org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef%>)parent).eGet(_parameterizedTypeRef_DeclaredType, false);\n\t\tif ((declType instanceof <%org.eclipse.n4js.ts.types.ContainerType%><?>))\n\t\t{\n\t\t\tboolean _eIsProxy = ((<%org.eclipse.n4js.ts.types.ContainerType%><?>)declType).eIsProxy();\n\t\t\tboolean _not = (!_eIsProxy);\n\t\t\tif (_not)\n\t\t\t{\n\t\t\t\tfinal <%org.eclipse.emf.common.util.EList%><<%org.eclipse.n4js.ts.types.TypeVariable%>> typeVars = ((<%org.eclipse.n4js.ts.types.ContainerType%><?>)declType).getTypeVars();\n\t\t\t\t<%org.eclipse.n4js.ts.types.TypeVariable%> _xifexpression = null;\n\t\t\t\tint _size = typeVars.size();\n\t\t\t\tboolean _lessThan = (typeArgIndex < _size);\n\t\t\t\tif (_lessThan)\n\t\t\t\t{\n\t\t\t\t\t_xifexpression = typeVars.get(typeArgIndex);\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\t_xifexpression = null;\n\t\t\t\t}\n\t\t\t\tfinal <%org.eclipse.n4js.ts.types.TypeVariable%> typeVar = _xifexpression;\n\t\t\t\tif ((typeVar != null))\n\t\t\t\t{\n\t\t\t\t\tfinal <%org.eclipse.n4js.ts.typeRefs.TypeRef%> implicitUB = typeVar.getDeclaredUpperBound();\n\t\t\t\t\treturn implicitUB;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n}\nreturn null;'"
	 * @generated
	 */
	TypeRef getDeclaredOrImplicitUpperBound();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if an implicit upper bound is in effect for the given wildcard, i.e. the wildcards does not have declared
	 * bounds (upper or lower) and obtains an implicit bound from the corresponding type parameter.
	 * 	 * @see #getDeclaredOrImplicitUpperBounds()
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return (((this.getDeclaredUpperBound() == null) && (this.getDeclaredLowerBound() == null)) && (this.getDeclaredOrImplicitUpperBound() != null));'"
	 * @generated
	 */
	boolean isImplicitUpperBoundInEffect();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Convenience method, returns the type expression, usually the type name, as a string. Basically used for testing.
	 * See {@link TypeRef#getTypeRefAsString()}
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return <%org.eclipse.n4js.ts.types.internal.WildcardAsStringUtils%>.getTypeRefAsString_workaround(this);'"
	 * @generated
	 */
	String getTypeRefAsString();

} // Wildcard
