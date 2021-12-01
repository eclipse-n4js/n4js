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
 * A representation of the model object '<em><b>TStruct Member</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract base class for members defined in a structural type reference or in an object literal.
 * Neither the access modifier (which is public for these members) nor static members are supported there.
 *  * TODO Introduce abstract base class for TMember and/or rename memberAccessModifier to declaredMemberAccessModifier. Currently problematic due to XCore constraints.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TStructMember#getDefinedMember <em>Defined Member</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TStructMember#getDtsComputedNameExpression <em>Dts Computed Name Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTStructMember()
 * @model abstract="true"
 * @generated
 */
public interface TStructMember extends TMember {
	/**
	 * Returns the value of the '<em><b>Defined Member</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A TStructMember can play the role of an AST node or a type model element (i.e. in a TModule).
	 * Iff the receiving TStructMember is an AST node, then this cross-reference will point to the
	 * corresponding TStructuralMember in the TStructuralType of the type model; otherwise it will
	 * be <code>null</code>.
	 * <p>
	 * This property corresponds to property 'definedType' of subclasses of TypeDefiningElement in
	 * the AST model. This property is set by the types builder and should never be changed by code
	 * outside the types builder.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defined Member</em>' reference.
	 * @see #setDefinedMember(TStructMember)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTStructMember_DefinedMember()
	 * @model transient="true"
	 * @generated
	 */
	TStructMember getDefinedMember();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TStructMember#getDefinedMember <em>Defined Member</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Member</em>' reference.
	 * @see #getDefinedMember()
	 * @generated
	 */
	void setDefinedMember(TStructMember value);

	/**
	 * Returns the value of the '<em><b>Dts Computed Name Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dts Computed Name Expression</em>' containment reference.
	 * @see #setDtsComputedNameExpression(ExpressionInTypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTStructMember_DtsComputedNameExpression()
	 * @model containment="true"
	 * @generated
	 */
	ExpressionInTypeRef getDtsComputedNameExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TStructMember#getDtsComputedNameExpression <em>Dts Computed Name Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dts Computed Name Expression</em>' containment reference.
	 * @see #getDtsComputedNameExpression()
	 * @generated
	 */
	void setDtsComputedNameExpression(ExpressionInTypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The default access modifier for struct members is public. This cannot be changed.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	MemberAccessModifier getDefaultMemberAccessModifier();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isStatic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The member access modifier for struct members is always public, this cannot be changed.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	MemberAccessModifier getMemberAccessModifier();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If this TStructMember plays the role of an AST node, this method tells whether it is a call signature;
	 * if this TStructMember is not an AST node, the return value is undefined (i.e. this method does not check
	 * whether the receiver is actually an AST node).
	 * <p>
	 * Note: this method is required because {@link TFunction#isCallSignature()} does not work for AST nodes.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isASTCallSignature();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Same as {@link TStructMember#isASTCallSignature()}, but for construct signatures.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isASTConstructSignature();

} // TStructMember
