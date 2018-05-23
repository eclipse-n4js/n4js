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

import org.eclipse.n4js.n4JS.N4MemberDeclaration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Delegating Member</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationBaseType <em>Delegation Base Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationSuperClassSteps <em>Delegation Super Class Steps</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationTarget <em>Delegation Target</em>}</li>
 *   <li>{@link org.eclipse.n4js.transpiler.im.DelegatingMember#isDelegationTargetIsAbstract <em>Delegation Target Is Abstract</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.transpiler.im.ImPackage#getDelegatingMember()
 * @model abstract="true"
 * @generated
 */
public interface DelegatingMember extends N4MemberDeclaration {
	/**
	 * Returns the value of the '<em><b>Delegation Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The base type for finding the delegation target or <code>null</code> if the this member's containing type
	 * should be used as the base. This must evaluate to the base type's constructor function, not its prototype.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delegation Base Type</em>' reference.
	 * @see #setDelegationBaseType(SymbolTableEntryOriginal)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getDelegatingMember_DelegationBaseType()
	 * @model
	 * @generated
	 */
	SymbolTableEntryOriginal getDelegationBaseType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationBaseType <em>Delegation Base Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delegation Base Type</em>' reference.
	 * @see #getDelegationBaseType()
	 * @generated
	 */
	void setDelegationBaseType(SymbolTableEntryOriginal value);

	/**
	 * Returns the value of the '<em><b>Delegation Super Class Steps</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * How often we should proceed to the super class of the base type for finding the delegation target or 0.
	 * If 'baseType' denotes an interface, this should always be 0.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delegation Super Class Steps</em>' attribute.
	 * @see #setDelegationSuperClassSteps(int)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getDelegatingMember_DelegationSuperClassSteps()
	 * @model unique="false"
	 * @generated
	 */
	int getDelegationSuperClassSteps();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationSuperClassSteps <em>Delegation Super Class Steps</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delegation Super Class Steps</em>' attribute.
	 * @see #getDelegationSuperClassSteps()
	 * @generated
	 */
	void setDelegationSuperClassSteps(int value);

	/**
	 * Returns the value of the '<em><b>Delegation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  The delegation target. Must always be non-null.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delegation Target</em>' reference.
	 * @see #setDelegationTarget(SymbolTableEntryOriginal)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getDelegatingMember_DelegationTarget()
	 * @model
	 * @generated
	 */
	SymbolTableEntryOriginal getDelegationTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationTarget <em>Delegation Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delegation Target</em>' reference.
	 * @see #getDelegationTarget()
	 * @generated
	 */
	void setDelegationTarget(SymbolTableEntryOriginal value);

	/**
	 * Returns the value of the '<em><b>Delegation Target Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  store information about the target - if true the target has no body
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Delegation Target Is Abstract</em>' attribute.
	 * @see #setDelegationTargetIsAbstract(boolean)
	 * @see org.eclipse.n4js.transpiler.im.ImPackage#getDelegatingMember_DelegationTargetIsAbstract()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDelegationTargetIsAbstract();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#isDelegationTargetIsAbstract <em>Delegation Target Is Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delegation Target Is Abstract</em>' attribute.
	 * @see #isDelegationTargetIsAbstract()
	 * @generated
	 */
	void setDelegationTargetIsAbstract(boolean value);

} // DelegatingMember
