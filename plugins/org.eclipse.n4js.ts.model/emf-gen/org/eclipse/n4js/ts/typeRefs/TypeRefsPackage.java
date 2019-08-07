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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * *
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  * Contributors:
 *   NumberFour AG - Initial API and implementation
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
 * @model kind="package"
 * @generated
 */
public interface TypeRefsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "typeRefs";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/ts/TypeRefs";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "typeRefs";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypeRefsPackage eINSTANCE = org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionableImpl <em>Versionable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionableImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionable()
	 * @generated
	 */
	int VERSIONABLE = 0;

	/**
	 * The number of structural features of the '<em>Versionable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONABLE_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONABLE___GET_VERSION = 0;

	/**
	 * The number of operations of the '<em>Versionable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONABLE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeArgumentImpl <em>Type Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeArgumentImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeArgument()
	 * @generated
	 */
	int TYPE_ARGUMENT = 17;

	/**
	 * The number of structural features of the '<em>Type Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING = 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT___CONTAINS_WILDCARDS = 1;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT___CONTAINS_UNBOUND_TYPE_VARIABLES = 2;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT___GET_DECLARED_TYPE = 3;

	/**
	 * The number of operations of the '<em>Type Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ARGUMENT_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeRefImpl <em>Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeRef()
	 * @generated
	 */
	int TYPE_REF = 1;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF__FOLLOWED_BY_QUESTION_MARK = TYPE_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF_FEATURE_COUNT = TYPE_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___CONTAINS_WILDCARDS = TYPE_ARGUMENT___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = TYPE_ARGUMENT___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_DECLARED_TYPE = TYPE_ARGUMENT___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_MODIFIERS_AS_STRING = TYPE_ARGUMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_FINAL_BY_TYPE = TYPE_ARGUMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_ARRAY_LIKE = TYPE_ARGUMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_DYNAMIC = TYPE_ARGUMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_EXISTENTIAL = TYPE_ARGUMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_GENERIC = TYPE_ARGUMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_PARAMETERIZED = TYPE_ARGUMENT_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_RAW = TYPE_ARGUMENT_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_DECLARED_UPPER_BOUND = TYPE_ARGUMENT_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_DECLARED_LOWER_BOUND = TYPE_ARGUMENT_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_TYPE_ARGS = TYPE_ARGUMENT_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_TYPE_REF_AS_STRING = TYPE_ARGUMENT_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___TO_STRING = TYPE_ARGUMENT_OPERATION_COUNT + 13;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_TOP_TYPE = TYPE_ARGUMENT_OPERATION_COUNT + 14;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_BOTTOM_TYPE = TYPE_ARGUMENT_OPERATION_COUNT + 15;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_TYPING_STRATEGY = TYPE_ARGUMENT_OPERATION_COUNT + 16;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_STRUCTURAL_MEMBERS = TYPE_ARGUMENT_OPERATION_COUNT + 17;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = TYPE_ARGUMENT_OPERATION_COUNT + 18;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = TYPE_ARGUMENT_OPERATION_COUNT + 19;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = TYPE_ARGUMENT_OPERATION_COUNT + 20;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF___GET_VERSION = TYPE_ARGUMENT_OPERATION_COUNT + 21;

	/**
	 * The number of operations of the '<em>Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF_OPERATION_COUNT = TYPE_ARGUMENT_OPERATION_COUNT + 22;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.StaticBaseTypeRefImpl <em>Static Base Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.StaticBaseTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getStaticBaseTypeRef()
	 * @generated
	 */
	int STATIC_BASE_TYPE_REF = 2;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The number of structural features of the '<em>Static Base Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF_FEATURE_COUNT = TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___CONTAINS_WILDCARDS = TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_DECLARED_TYPE = TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_MODIFIERS_AS_STRING = TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_FINAL_BY_TYPE = TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_ARRAY_LIKE = TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_DYNAMIC = TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_EXISTENTIAL = TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_GENERIC = TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_PARAMETERIZED = TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_RAW = TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND = TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND = TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_TYPE_ARGS = TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_TYPE_REF_AS_STRING = TYPE_REF___GET_TYPE_REF_AS_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___TO_STRING = TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_TOP_TYPE = TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_BOTTOM_TYPE = TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_TYPING_STRATEGY = TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_STRUCTURAL_MEMBERS = TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF___GET_VERSION = TYPE_REF___GET_VERSION;

	/**
	 * The number of operations of the '<em>Static Base Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATIC_BASE_TYPE_REF_OPERATION_COUNT = TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.BaseTypeRefImpl <em>Base Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.BaseTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getBaseTypeRef()
	 * @generated
	 */
	int BASE_TYPE_REF = 3;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = STATIC_BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF__DYNAMIC = STATIC_BASE_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Base Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF_FEATURE_COUNT = STATIC_BASE_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___CONTAINS_WILDCARDS = STATIC_BASE_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = STATIC_BASE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_DECLARED_TYPE = STATIC_BASE_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_FINAL_BY_TYPE = STATIC_BASE_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_ARRAY_LIKE = STATIC_BASE_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_DYNAMIC = STATIC_BASE_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_EXISTENTIAL = STATIC_BASE_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_GENERIC = STATIC_BASE_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_PARAMETERIZED = STATIC_BASE_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_RAW = STATIC_BASE_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND = STATIC_BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND = STATIC_BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_TYPE_ARGS = STATIC_BASE_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_TYPE_REF_AS_STRING = STATIC_BASE_TYPE_REF___GET_TYPE_REF_AS_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___TO_STRING = STATIC_BASE_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_TOP_TYPE = STATIC_BASE_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_BOTTOM_TYPE = STATIC_BASE_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_TYPING_STRATEGY = STATIC_BASE_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_STRUCTURAL_MEMBERS = STATIC_BASE_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = STATIC_BASE_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = STATIC_BASE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = STATIC_BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_VERSION = STATIC_BASE_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF___GET_MODIFIERS_AS_STRING = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Base Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_TYPE_REF_OPERATION_COUNT = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ComposedTypeRefImpl <em>Composed Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.ComposedTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getComposedTypeRef()
	 * @generated
	 */
	int COMPOSED_TYPE_REF = 4;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = STATIC_BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Type Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF__TYPE_REFS = STATIC_BASE_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Composed Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF_FEATURE_COUNT = STATIC_BASE_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___CONTAINS_WILDCARDS = STATIC_BASE_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = STATIC_BASE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_DECLARED_TYPE = STATIC_BASE_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_MODIFIERS_AS_STRING = STATIC_BASE_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_FINAL_BY_TYPE = STATIC_BASE_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_ARRAY_LIKE = STATIC_BASE_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_EXISTENTIAL = STATIC_BASE_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_GENERIC = STATIC_BASE_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_PARAMETERIZED = STATIC_BASE_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_RAW = STATIC_BASE_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_DECLARED_UPPER_BOUND = STATIC_BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_DECLARED_LOWER_BOUND = STATIC_BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_TYPE_ARGS = STATIC_BASE_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___TO_STRING = STATIC_BASE_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_TOP_TYPE = STATIC_BASE_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_BOTTOM_TYPE = STATIC_BASE_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_TYPING_STRATEGY = STATIC_BASE_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_STRUCTURAL_MEMBERS = STATIC_BASE_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = STATIC_BASE_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = STATIC_BASE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = STATIC_BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_VERSION = STATIC_BASE_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___IS_DYNAMIC = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF___GET_TYPE_REF_AS_STRING = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Composed Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_TYPE_REF_OPERATION_COUNT = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.UnionTypeExpressionImpl <em>Union Type Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.UnionTypeExpressionImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getUnionTypeExpression()
	 * @generated
	 */
	int UNION_TYPE_EXPRESSION = 5;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION__FOLLOWED_BY_QUESTION_MARK = COMPOSED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Type Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION__TYPE_REFS = COMPOSED_TYPE_REF__TYPE_REFS;

	/**
	 * The number of structural features of the '<em>Union Type Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION_FEATURE_COUNT = COMPOSED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___CONTAINS_WILDCARDS = COMPOSED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___CONTAINS_UNBOUND_TYPE_VARIABLES = COMPOSED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_DECLARED_TYPE = COMPOSED_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_MODIFIERS_AS_STRING = COMPOSED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_FINAL_BY_TYPE = COMPOSED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_ARRAY_LIKE = COMPOSED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_EXISTENTIAL = COMPOSED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_GENERIC = COMPOSED_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_PARAMETERIZED = COMPOSED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_RAW = COMPOSED_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_DECLARED_UPPER_BOUND = COMPOSED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_DECLARED_LOWER_BOUND = COMPOSED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_TYPE_ARGS = COMPOSED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___TO_STRING = COMPOSED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_TOP_TYPE = COMPOSED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_BOTTOM_TYPE = COMPOSED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_TYPING_STRATEGY = COMPOSED_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_STRUCTURAL_MEMBERS = COMPOSED_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_USE_SITE_STRUCTURAL_TYPING = COMPOSED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_DEF_SITE_STRUCTURAL_TYPING = COMPOSED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = COMPOSED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_VERSION = COMPOSED_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___IS_DYNAMIC = COMPOSED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION___GET_TYPE_REF_AS_STRING = COMPOSED_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Union Type Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNION_TYPE_EXPRESSION_OPERATION_COUNT = COMPOSED_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.IntersectionTypeExpressionImpl <em>Intersection Type Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.IntersectionTypeExpressionImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getIntersectionTypeExpression()
	 * @generated
	 */
	int INTERSECTION_TYPE_EXPRESSION = 6;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION__FOLLOWED_BY_QUESTION_MARK = COMPOSED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Type Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION__TYPE_REFS = COMPOSED_TYPE_REF__TYPE_REFS;

	/**
	 * The number of structural features of the '<em>Intersection Type Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION_FEATURE_COUNT = COMPOSED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___CONTAINS_WILDCARDS = COMPOSED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___CONTAINS_UNBOUND_TYPE_VARIABLES = COMPOSED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_DECLARED_TYPE = COMPOSED_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_MODIFIERS_AS_STRING = COMPOSED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_FINAL_BY_TYPE = COMPOSED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_ARRAY_LIKE = COMPOSED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_EXISTENTIAL = COMPOSED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_GENERIC = COMPOSED_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_PARAMETERIZED = COMPOSED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_RAW = COMPOSED_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_DECLARED_UPPER_BOUND = COMPOSED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_DECLARED_LOWER_BOUND = COMPOSED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_TYPE_ARGS = COMPOSED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___TO_STRING = COMPOSED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_TOP_TYPE = COMPOSED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_BOTTOM_TYPE = COMPOSED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_TYPING_STRATEGY = COMPOSED_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_STRUCTURAL_MEMBERS = COMPOSED_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_USE_SITE_STRUCTURAL_TYPING = COMPOSED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_DEF_SITE_STRUCTURAL_TYPING = COMPOSED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = COMPOSED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_VERSION = COMPOSED_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___IS_DYNAMIC = COMPOSED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION___GET_TYPE_REF_AS_STRING = COMPOSED_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Intersection Type Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERSECTION_TYPE_EXPRESSION_OPERATION_COUNT = COMPOSED_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefImpl <em>This Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getThisTypeRef()
	 * @generated
	 */
	int THIS_TYPE_REF = 7;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF__DYNAMIC = BASE_TYPE_REF__DYNAMIC;

	/**
	 * The number of structural features of the '<em>This Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_FEATURE_COUNT = BASE_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___CONTAINS_WILDCARDS = BASE_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = BASE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_DECLARED_TYPE = BASE_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_FINAL_BY_TYPE = BASE_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_ARRAY_LIKE = BASE_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_DYNAMIC = BASE_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_EXISTENTIAL = BASE_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_GENERIC = BASE_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_PARAMETERIZED = BASE_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_RAW = BASE_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_DECLARED_UPPER_BOUND = BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_DECLARED_LOWER_BOUND = BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_TYPE_ARGS = BASE_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___TO_STRING = BASE_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_TOP_TYPE = BASE_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_BOTTOM_TYPE = BASE_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = BASE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_VERSION = BASE_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_MODIFIERS_AS_STRING = BASE_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_TYPE_REF_AS_STRING = BASE_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_TYPING_STRATEGY = BASE_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS = BASE_TYPE_REF_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = BASE_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>This Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_OPERATION_COUNT = BASE_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefNominalImpl <em>This Type Ref Nominal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefNominalImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getThisTypeRefNominal()
	 * @generated
	 */
	int THIS_TYPE_REF_NOMINAL = 8;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL__FOLLOWED_BY_QUESTION_MARK = THIS_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL__DYNAMIC = THIS_TYPE_REF__DYNAMIC;

	/**
	 * The number of structural features of the '<em>This Type Ref Nominal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL_FEATURE_COUNT = THIS_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___CONTAINS_WILDCARDS = THIS_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___CONTAINS_UNBOUND_TYPE_VARIABLES = THIS_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_DECLARED_TYPE = THIS_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_FINAL_BY_TYPE = THIS_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_ARRAY_LIKE = THIS_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_DYNAMIC = THIS_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_EXISTENTIAL = THIS_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_GENERIC = THIS_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_PARAMETERIZED = THIS_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_RAW = THIS_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_DECLARED_UPPER_BOUND = THIS_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_DECLARED_LOWER_BOUND = THIS_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_TYPE_ARGS = THIS_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___TO_STRING = THIS_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_TOP_TYPE = THIS_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_BOTTOM_TYPE = THIS_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_DEF_SITE_STRUCTURAL_TYPING = THIS_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = THIS_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_VERSION = THIS_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_MODIFIERS_AS_STRING = THIS_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_TYPE_REF_AS_STRING = THIS_TYPE_REF___GET_TYPE_REF_AS_STRING;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_TYPING_STRATEGY = THIS_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___GET_STRUCTURAL_MEMBERS = THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL___IS_USE_SITE_STRUCTURAL_TYPING = THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The number of operations of the '<em>This Type Ref Nominal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_NOMINAL_OPERATION_COUNT = THIS_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl <em>This Type Ref Structural</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getThisTypeRefStructural()
	 * @generated
	 */
	int THIS_TYPE_REF_STRUCTURAL = 9;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL__FOLLOWED_BY_QUESTION_MARK = THIS_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL__DYNAMIC = THIS_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS = THIS_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE = THIS_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS = THIS_TYPE_REF_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS = THIS_TYPE_REF_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY = THIS_TYPE_REF_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>This Type Ref Structural</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL_FEATURE_COUNT = THIS_TYPE_REF_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___CONTAINS_WILDCARDS = THIS_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___CONTAINS_UNBOUND_TYPE_VARIABLES = THIS_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_DECLARED_TYPE = THIS_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_FINAL_BY_TYPE = THIS_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_ARRAY_LIKE = THIS_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_DYNAMIC = THIS_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_EXISTENTIAL = THIS_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_GENERIC = THIS_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_PARAMETERIZED = THIS_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_RAW = THIS_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_DECLARED_UPPER_BOUND = THIS_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_DECLARED_LOWER_BOUND = THIS_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_TYPE_ARGS = THIS_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___TO_STRING = THIS_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_TOP_TYPE = THIS_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_BOTTOM_TYPE = THIS_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_DEF_SITE_STRUCTURAL_TYPING = THIS_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = THIS_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_VERSION = THIS_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_MODIFIERS_AS_STRING = THIS_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Has Postponed Substitution For</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = THIS_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY = THIS_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Set Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY = THIS_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___IS_USE_SITE_STRUCTURAL_TYPING = THIS_TYPE_REF_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS = THIS_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL___GET_TYPE_REF_AS_STRING = THIS_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The number of operations of the '<em>This Type Ref Structural</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TYPE_REF_STRUCTURAL_OPERATION_COUNT = THIS_TYPE_REF_OPERATION_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl <em>Bound This Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getBoundThisTypeRef()
	 * @generated
	 */
	int BOUND_THIS_TYPE_REF = 10;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = THIS_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__DYNAMIC = THIS_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__AST_STRUCTURAL_MEMBERS = THIS_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__STRUCTURAL_TYPE = THIS_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__GEN_STRUCTURAL_MEMBERS = THIS_TYPE_REF_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__POSTPONED_SUBSTITUTIONS = THIS_TYPE_REF_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Actual This Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF = THIS_TYPE_REF_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF__DEFINED_TYPING_STRATEGY = THIS_TYPE_REF_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Bound This Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF_FEATURE_COUNT = THIS_TYPE_REF_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___CONTAINS_WILDCARDS = THIS_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = THIS_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_DECLARED_TYPE = THIS_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_FINAL_BY_TYPE = THIS_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_ARRAY_LIKE = THIS_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_DYNAMIC = THIS_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_EXISTENTIAL = THIS_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_GENERIC = THIS_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_PARAMETERIZED = THIS_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_RAW = THIS_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_DECLARED_LOWER_BOUND = THIS_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_TYPE_ARGS = THIS_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___TO_STRING = THIS_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_TOP_TYPE = THIS_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_BOTTOM_TYPE = THIS_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = THIS_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_MODIFIERS_AS_STRING = THIS_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Has Postponed Substitution For</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = THIS_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_TYPING_STRATEGY = THIS_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Set Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY = THIS_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_TYPE_REF_AS_STRING = THIS_TYPE_REF_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_DECLARED_UPPER_BOUND = THIS_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = THIS_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = THIS_TYPE_REF_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS = THIS_TYPE_REF_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF___GET_VERSION = THIS_TYPE_REF_OPERATION_COUNT + 11;

	/**
	 * The number of operations of the '<em>Bound This Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOUND_THIS_TYPE_REF_OPERATION_COUNT = THIS_TYPE_REF_OPERATION_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl <em>Parameterized Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getParameterizedTypeRef()
	 * @generated
	 */
	int PARAMETERIZED_TYPE_REF = 11;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__DYNAMIC = BASE_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__DECLARED_TYPE = BASE_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__TYPE_ARGS = BASE_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION = BASE_TYPE_REF_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION = BASE_TYPE_REF_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__AST_NAMESPACE = BASE_TYPE_REF_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY = BASE_TYPE_REF_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY = BASE_TYPE_REF_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Parameterized Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_FEATURE_COUNT = BASE_TYPE_REF_FEATURE_COUNT + 7;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_DECLARED_TYPE = BASE_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE = BASE_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE = BASE_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_DYNAMIC = BASE_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL = BASE_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND = BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND = BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS = BASE_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___TO_STRING = BASE_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_TOP_TYPE = BASE_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE = BASE_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_STRUCTURAL_MEMBERS = BASE_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_VERSION = BASE_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING = BASE_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY = BASE_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS = BASE_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING = BASE_TYPE_REF_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED = BASE_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_GENERIC = BASE_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_RAW = BASE_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = BASE_TYPE_REF_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = BASE_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = BASE_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The number of operations of the '<em>Parameterized Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_OPERATION_COUNT = BASE_TYPE_REF_OPERATION_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.StructuralTypeRefImpl <em>Structural Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.StructuralTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getStructuralTypeRef()
	 * @generated
	 */
	int STRUCTURAL_TYPE_REF = 12;

	/**
	 * The feature id for the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS = 0;

	/**
	 * The feature id for the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS = 2;

	/**
	 * The feature id for the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS = 3;

	/**
	 * The number of structural features of the '<em>Structural Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF___GET_TYPING_STRATEGY = 0;

	/**
	 * The operation id for the '<em>Set Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY = 1;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF___GET_STRUCTURAL_MEMBERS = 2;

	/**
	 * The operation id for the '<em>Has Postponed Substitution For</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = 3;

	/**
	 * The number of operations of the '<em>Structural Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_TYPE_REF_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefStructuralImpl <em>Parameterized Type Ref Structural</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefStructuralImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getParameterizedTypeRefStructural()
	 * @generated
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL = 13;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__FOLLOWED_BY_QUESTION_MARK = PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__DYNAMIC = PARAMETERIZED_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__DECLARED_TYPE = PARAMETERIZED_TYPE_REF__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__TYPE_ARGS = PARAMETERIZED_TYPE_REF__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__ARRAY_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__ITERABLE_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__AST_NAMESPACE = PARAMETERIZED_TYPE_REF__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Parameterized Type Ref Structural</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_FEATURE_COUNT = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_DECLARED_TYPE = PARAMETERIZED_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_FINAL_BY_TYPE = PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_ARRAY_LIKE = PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_DYNAMIC = PARAMETERIZED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_EXISTENTIAL = PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_DECLARED_UPPER_BOUND = PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_DECLARED_LOWER_BOUND = PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPE_ARGS = PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___TO_STRING = PARAMETERIZED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_TOP_TYPE = PARAMETERIZED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_BOTTOM_TYPE = PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_VERSION = PARAMETERIZED_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_MODIFIERS_AS_STRING = PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___CONTAINS_WILDCARDS = PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_PARAMETERIZED = PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_GENERIC = PARAMETERIZED_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_RAW = PARAMETERIZED_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___CONTAINS_UNBOUND_TYPE_VARIABLES = PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_USE_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_DEF_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Has Postponed Substitution For</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Set Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPE_REF_AS_STRING = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>Parameterized Type Ref Structural</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_OPERATION_COUNT = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ExistentialTypeRefImpl <em>Existential Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.ExistentialTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getExistentialTypeRef()
	 * @generated
	 */
	int EXISTENTIAL_TYPE_REF = 14;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF__ID = TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Reopened</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF__REOPENED = TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Wildcard</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF__WILDCARD = TYPE_REF_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Bound Type Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF__BOUND_TYPE_VARIABLE = TYPE_REF_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Existential Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF_FEATURE_COUNT = TYPE_REF_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___CONTAINS_WILDCARDS = TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_DECLARED_TYPE = TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_MODIFIERS_AS_STRING = TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_FINAL_BY_TYPE = TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_ARRAY_LIKE = TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_DYNAMIC = TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_RAW = TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_DECLARED_UPPER_BOUND = TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_DECLARED_LOWER_BOUND = TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_TYPE_ARGS = TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___TO_STRING = TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_TOP_TYPE = TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_BOTTOM_TYPE = TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_TYPING_STRATEGY = TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_STRUCTURAL_MEMBERS = TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_VERSION = TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_EXISTENTIAL = TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_GENERIC = TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___IS_PARAMETERIZED = TYPE_REF_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF___GET_TYPE_REF_AS_STRING = TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Existential Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTENTIAL_TYPE_REF_OPERATION_COUNT = TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.UnknownTypeRefImpl <em>Unknown Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.UnknownTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getUnknownTypeRef()
	 * @generated
	 */
	int UNKNOWN_TYPE_REF = 15;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The number of structural features of the '<em>Unknown Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF_FEATURE_COUNT = TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___CONTAINS_WILDCARDS = TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_DECLARED_TYPE = TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_MODIFIERS_AS_STRING = TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_FINAL_BY_TYPE = TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_ARRAY_LIKE = TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_DYNAMIC = TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_EXISTENTIAL = TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_GENERIC = TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_PARAMETERIZED = TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_RAW = TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_DECLARED_UPPER_BOUND = TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_DECLARED_LOWER_BOUND = TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_TYPE_ARGS = TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___TO_STRING = TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_TOP_TYPE = TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_BOTTOM_TYPE = TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_TYPING_STRATEGY = TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_STRUCTURAL_MEMBERS = TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_VERSION = TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF___GET_TYPE_REF_AS_STRING = TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Unknown Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_TYPE_REF_OPERATION_COUNT = TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeTypeRefImpl <em>Type Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeTypeRef()
	 * @generated
	 */
	int TYPE_TYPE_REF = 16;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF__DYNAMIC = BASE_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Type Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF__TYPE_ARG = BASE_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constructor Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF__CONSTRUCTOR_REF = BASE_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Type Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF_FEATURE_COUNT = BASE_TYPE_REF_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___CONTAINS_WILDCARDS = BASE_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = BASE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_DECLARED_TYPE = BASE_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_FINAL_BY_TYPE = BASE_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_ARRAY_LIKE = BASE_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_DYNAMIC = BASE_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_EXISTENTIAL = BASE_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_GENERIC = BASE_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_PARAMETERIZED = BASE_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_RAW = BASE_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_DECLARED_UPPER_BOUND = BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_DECLARED_LOWER_BOUND = BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_TYPE_ARGS = BASE_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___TO_STRING = BASE_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_TOP_TYPE = BASE_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_BOTTOM_TYPE = BASE_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_TYPING_STRATEGY = BASE_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_STRUCTURAL_MEMBERS = BASE_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = BASE_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = BASE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_VERSION = BASE_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_MODIFIERS_AS_STRING = BASE_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF___GET_TYPE_REF_AS_STRING = BASE_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Type Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_TYPE_REF_OPERATION_COUNT = BASE_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.WildcardImpl <em>Wildcard</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.WildcardImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getWildcard()
	 * @generated
	 */
	int WILDCARD = 18;

	/**
	 * The feature id for the '<em><b>Declared Upper Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD__DECLARED_UPPER_BOUND = TYPE_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Lower Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD__DECLARED_LOWER_BOUND = TYPE_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Using In Out Notation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD__USING_IN_OUT_NOTATION = TYPE_ARGUMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Wildcard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD_FEATURE_COUNT = TYPE_ARGUMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD___CONTAINS_WILDCARDS = TYPE_ARGUMENT___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD___CONTAINS_UNBOUND_TYPE_VARIABLES = TYPE_ARGUMENT___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD___GET_DECLARED_TYPE = TYPE_ARGUMENT___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Declared Or Implicit Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD___GET_DECLARED_OR_IMPLICIT_UPPER_BOUND = TYPE_ARGUMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Implicit Upper Bound In Effect</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD___IS_IMPLICIT_UPPER_BOUND_IN_EFFECT = TYPE_ARGUMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD___GET_TYPE_REF_AS_STRING = TYPE_ARGUMENT_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Wildcard</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WILDCARD_OPERATION_COUNT = TYPE_ARGUMENT_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExprOrRefImpl <em>Function Type Expr Or Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExprOrRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getFunctionTypeExprOrRef()
	 * @generated
	 */
	int FUNCTION_TYPE_EXPR_OR_REF = 19;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF__FOLLOWED_BY_QUESTION_MARK = STATIC_BASE_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The number of structural features of the '<em>Function Type Expr Or Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT = STATIC_BASE_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___CONTAINS_WILDCARDS = STATIC_BASE_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = STATIC_BASE_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_TYPE = STATIC_BASE_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_MODIFIERS_AS_STRING = STATIC_BASE_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_FINAL_BY_TYPE = STATIC_BASE_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_ARRAY_LIKE = STATIC_BASE_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_DYNAMIC = STATIC_BASE_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_EXISTENTIAL = STATIC_BASE_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_PARAMETERIZED = STATIC_BASE_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_UPPER_BOUND = STATIC_BASE_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_LOWER_BOUND = STATIC_BASE_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_ARGS = STATIC_BASE_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___TO_STRING = STATIC_BASE_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_TOP_TYPE = STATIC_BASE_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_BOTTOM_TYPE = STATIC_BASE_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_TYPING_STRATEGY = STATIC_BASE_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_STRUCTURAL_MEMBERS = STATIC_BASE_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_USE_SITE_STRUCTURAL_TYPING = STATIC_BASE_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_DEF_SITE_STRUCTURAL_TYPING = STATIC_BASE_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = STATIC_BASE_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_VERSION = STATIC_BASE_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Declared This Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_THIS_TYPE = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Function Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_FUNCTION_TYPE = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Type Vars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VARS = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Type Var Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Fpars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_FPARS = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_RETURN_VALUE_OPTIONAL = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Return Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_RETURN_TYPE_REF = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_GENERIC = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___IS_RAW = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Fpar For Arg Idx</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_FPAR_FOR_ARG_IDX__INT = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_REF_AS_STRING = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 10;

	/**
	 * The number of operations of the '<em>Function Type Expr Or Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPR_OR_REF_OPERATION_COUNT = STATIC_BASE_TYPE_REF_OPERATION_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeRefImpl <em>Function Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getFunctionTypeRef()
	 * @generated
	 */
	int FUNCTION_TYPE_REF = 20;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__DYNAMIC = PARAMETERIZED_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__DECLARED_TYPE = PARAMETERIZED_TYPE_REF__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__TYPE_ARGS = PARAMETERIZED_TYPE_REF__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__ARRAY_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__ITERABLE_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__AST_NAMESPACE = PARAMETERIZED_TYPE_REF__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF__DEFINED_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY;

	/**
	 * The number of structural features of the '<em>Function Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF_FEATURE_COUNT = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_DECLARED_TYPE = PARAMETERIZED_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_FINAL_BY_TYPE = PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_ARRAY_LIKE = PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_DYNAMIC = PARAMETERIZED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_EXISTENTIAL = PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_DECLARED_UPPER_BOUND = PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_DECLARED_LOWER_BOUND = PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_TYPE_ARGS = PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___TO_STRING = PARAMETERIZED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_TOP_TYPE = PARAMETERIZED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_BOTTOM_TYPE = PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_VERSION = PARAMETERIZED_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_MODIFIERS_AS_STRING = PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___CONTAINS_WILDCARDS = PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_PARAMETERIZED = PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get Function Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_FUNCTION_TYPE = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_RETURN_VALUE_OPTIONAL = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_GENERIC = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___IS_RAW = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Fpar For Arg Idx</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_FPAR_FOR_ARG_IDX__INT = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_TYPE_REF_AS_STRING = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Get Declared This Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_DECLARED_THIS_TYPE = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Get Type Vars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_TYPE_VARS = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Get Type Var Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 13;

	/**
	 * The operation id for the '<em>Get Fpars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_FPARS = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 14;

	/**
	 * The operation id for the '<em>Get Return Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF___GET_RETURN_TYPE_REF = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 15;

	/**
	 * The number of operations of the '<em>Function Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_REF_OPERATION_COUNT = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 16;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl <em>Function Type Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getFunctionTypeExpression()
	 * @generated
	 */
	int FUNCTION_TYPE_EXPRESSION = 21;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__FOLLOWED_BY_QUESTION_MARK = FUNCTION_TYPE_EXPR_OR_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__BINDING = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__DECLARED_TYPE = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Declared This Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Owned Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Unbound Type Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Unbound Type Vars Upper Bounds</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__FPARS = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Return Value Marked Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__RETURN_VALUE_MARKED_OPTIONAL = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Function Type Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION_FEATURE_COUNT = FUNCTION_TYPE_EXPR_OR_REF_FEATURE_COUNT + 9;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___CONTAINS_WILDCARDS = FUNCTION_TYPE_EXPR_OR_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___CONTAINS_UNBOUND_TYPE_VARIABLES = FUNCTION_TYPE_EXPR_OR_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_DECLARED_TYPE = FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_MODIFIERS_AS_STRING = FUNCTION_TYPE_EXPR_OR_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_FINAL_BY_TYPE = FUNCTION_TYPE_EXPR_OR_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_ARRAY_LIKE = FUNCTION_TYPE_EXPR_OR_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_DYNAMIC = FUNCTION_TYPE_EXPR_OR_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_EXISTENTIAL = FUNCTION_TYPE_EXPR_OR_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_PARAMETERIZED = FUNCTION_TYPE_EXPR_OR_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_DECLARED_UPPER_BOUND = FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_DECLARED_LOWER_BOUND = FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_TYPE_ARGS = FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___TO_STRING = FUNCTION_TYPE_EXPR_OR_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_TOP_TYPE = FUNCTION_TYPE_EXPR_OR_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_BOTTOM_TYPE = FUNCTION_TYPE_EXPR_OR_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_TYPING_STRATEGY = FUNCTION_TYPE_EXPR_OR_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_STRUCTURAL_MEMBERS = FUNCTION_TYPE_EXPR_OR_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_USE_SITE_STRUCTURAL_TYPING = FUNCTION_TYPE_EXPR_OR_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_DEF_SITE_STRUCTURAL_TYPING = FUNCTION_TYPE_EXPR_OR_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = FUNCTION_TYPE_EXPR_OR_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_VERSION = FUNCTION_TYPE_EXPR_OR_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Declared This Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_DECLARED_THIS_TYPE = FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_THIS_TYPE;

	/**
	 * The operation id for the '<em>Get Function Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_FUNCTION_TYPE = FUNCTION_TYPE_EXPR_OR_REF___GET_FUNCTION_TYPE;

	/**
	 * The operation id for the '<em>Get Fpars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_FPARS = FUNCTION_TYPE_EXPR_OR_REF___GET_FPARS;

	/**
	 * The operation id for the '<em>Get Return Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_RETURN_TYPE_REF = FUNCTION_TYPE_EXPR_OR_REF___GET_RETURN_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_GENERIC = FUNCTION_TYPE_EXPR_OR_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_RAW = FUNCTION_TYPE_EXPR_OR_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Fpar For Arg Idx</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_FPAR_FOR_ARG_IDX__INT = FUNCTION_TYPE_EXPR_OR_REF___GET_FPAR_FOR_ARG_IDX__INT;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_TYPE_REF_AS_STRING = FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_REF_AS_STRING;

	/**
	 * The operation id for the '<em>Get Type Vars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_TYPE_VARS = FUNCTION_TYPE_EXPR_OR_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Type Var Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE = FUNCTION_TYPE_EXPR_OR_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION___IS_RETURN_VALUE_OPTIONAL = FUNCTION_TYPE_EXPR_OR_REF_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Function Type Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_EXPRESSION_OPERATION_COUNT = FUNCTION_TYPE_EXPR_OR_REF_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.DeferredTypeRefImpl <em>Deferred Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.DeferredTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getDeferredTypeRef()
	 * @generated
	 */
	int DEFERRED_TYPE_REF = 22;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The number of structural features of the '<em>Deferred Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF_FEATURE_COUNT = TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___CONTAINS_WILDCARDS = TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_DECLARED_TYPE = TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_MODIFIERS_AS_STRING = TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_FINAL_BY_TYPE = TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_ARRAY_LIKE = TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_DYNAMIC = TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_EXISTENTIAL = TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_GENERIC = TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_PARAMETERIZED = TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_RAW = TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_DECLARED_UPPER_BOUND = TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_DECLARED_LOWER_BOUND = TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_TYPE_ARGS = TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___TO_STRING = TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_TOP_TYPE = TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_BOTTOM_TYPE = TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_TYPING_STRATEGY = TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_STRUCTURAL_MEMBERS = TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_VERSION = TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF___GET_TYPE_REF_AS_STRING = TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Deferred Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFERRED_TYPE_REF_OPERATION_COUNT = TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeVariableMappingImpl <em>Type Variable Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeVariableMappingImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeVariableMapping()
	 * @generated
	 */
	int TYPE_VARIABLE_MAPPING = 23;

	/**
	 * The feature id for the '<em><b>Type Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VARIABLE_MAPPING__TYPE_VAR = 0;

	/**
	 * The feature id for the '<em><b>Type Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VARIABLE_MAPPING__TYPE_ARG = 1;

	/**
	 * The number of structural features of the '<em>Type Variable Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VARIABLE_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Type Variable Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_VARIABLE_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedReferenceImpl <em>Versioned Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedReferenceImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedReference()
	 * @generated
	 */
	int VERSIONED_REFERENCE = 24;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_REFERENCE__REQUESTED_VERSION = 0;

	/**
	 * The number of structural features of the '<em>Versioned Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_REFERENCE_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_REFERENCE___HAS_REQUESTED_VERSION = 0;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_REFERENCE___GET_REQUESTED_VERSION_OR_ZERO = 1;

	/**
	 * The number of operations of the '<em>Versioned Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_REFERENCE_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefImpl <em>Versioned Parameterized Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedParameterizedTypeRef()
	 * @generated
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF = 25;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__DYNAMIC = PARAMETERIZED_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__DECLARED_TYPE = PARAMETERIZED_TYPE_REF__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__TYPE_ARGS = PARAMETERIZED_TYPE_REF__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__AST_NAMESPACE = PARAMETERIZED_TYPE_REF__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF__REQUESTED_VERSION = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Versioned Parameterized Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_FEATURE_COUNT = PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_TYPE = PARAMETERIZED_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE = PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE = PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_DYNAMIC = PARAMETERIZED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL = PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND = PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND = PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS = PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___TO_STRING = PARAMETERIZED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_TOP_TYPE = PARAMETERIZED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE = PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING = PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS = PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING = PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED = PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_GENERIC = PARAMETERIZED_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_RAW = PARAMETERIZED_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___HAS_REQUESTED_VERSION = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_REQUESTED_VERSION_OR_ZERO = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF___GET_VERSION = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Versioned Parameterized Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT = PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedFunctionTypeRefImpl <em>Versioned Function Type Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedFunctionTypeRefImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedFunctionTypeRef()
	 * @generated
	 */
	int VERSIONED_FUNCTION_TYPE_REF = 26;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__FOLLOWED_BY_QUESTION_MARK = VERSIONED_PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__DYNAMIC = VERSIONED_PARAMETERIZED_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__DECLARED_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__TYPE_ARGS = VERSIONED_PARAMETERIZED_TYPE_REF__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__ARRAY_TYPE_EXPRESSION = VERSIONED_PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__ITERABLE_TYPE_EXPRESSION = VERSIONED_PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__AST_NAMESPACE = VERSIONED_PARAMETERIZED_TYPE_REF__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__DEFINED_TYPING_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF__REQUESTED_VERSION = VERSIONED_PARAMETERIZED_TYPE_REF__REQUESTED_VERSION;

	/**
	 * The number of structural features of the '<em>Versioned Function Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF_FEATURE_COUNT = VERSIONED_PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_DECLARED_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_FINAL_BY_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_ARRAY_LIKE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_DYNAMIC = VERSIONED_PARAMETERIZED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_EXISTENTIAL = VERSIONED_PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_DECLARED_UPPER_BOUND = VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_DECLARED_LOWER_BOUND = VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_TYPE_ARGS = VERSIONED_PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___TO_STRING = VERSIONED_PARAMETERIZED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_TOP_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_BOTTOM_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_STRUCTURAL_MEMBERS = VERSIONED_PARAMETERIZED_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_MODIFIERS_AS_STRING = VERSIONED_PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_TYPING_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___CONTAINS_WILDCARDS = VERSIONED_PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_PARAMETERIZED = VERSIONED_PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = VERSIONED_PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = VERSIONED_PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = VERSIONED_PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___HAS_REQUESTED_VERSION = VERSIONED_PARAMETERIZED_TYPE_REF___HAS_REQUESTED_VERSION;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_REQUESTED_VERSION_OR_ZERO = VERSIONED_PARAMETERIZED_TYPE_REF___GET_REQUESTED_VERSION_OR_ZERO;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_VERSION = VERSIONED_PARAMETERIZED_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Function Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_FUNCTION_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_RETURN_VALUE_OPTIONAL = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_GENERIC = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___IS_RAW = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Fpar For Arg Idx</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_FPAR_FOR_ARG_IDX__INT = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_TYPE_REF_AS_STRING = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Get Declared This Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_DECLARED_THIS_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Get Type Vars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_TYPE_VARS = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Get Type Var Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 13;

	/**
	 * The operation id for the '<em>Get Fpars</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_FPARS = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 14;

	/**
	 * The operation id for the '<em>Get Return Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF___GET_RETURN_TYPE_REF = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 15;

	/**
	 * The number of operations of the '<em>Versioned Function Type Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_FUNCTION_TYPE_REF_OPERATION_COUNT = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 16;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefStructuralImpl <em>Versioned Parameterized Type Ref Structural</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefStructuralImpl
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedParameterizedTypeRefStructural()
	 * @generated
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL = 27;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__FOLLOWED_BY_QUESTION_MARK = VERSIONED_PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__DYNAMIC = VERSIONED_PARAMETERIZED_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__DECLARED_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__TYPE_ARGS = VERSIONED_PARAMETERIZED_TYPE_REF__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__ARRAY_TYPE_EXPRESSION = VERSIONED_PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__ITERABLE_TYPE_EXPRESSION = VERSIONED_PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__AST_NAMESPACE = VERSIONED_PARAMETERIZED_TYPE_REF__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__AST_NODE_OPTIONAL_FIELD_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__REQUESTED_VERSION = VERSIONED_PARAMETERIZED_TYPE_REF__REQUESTED_VERSION;

	/**
	 * The feature id for the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__AST_STRUCTURAL_MEMBERS = VERSIONED_PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__STRUCTURAL_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__GEN_STRUCTURAL_MEMBERS = VERSIONED_PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL__POSTPONED_SUBSTITUTIONS = VERSIONED_PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Versioned Parameterized Type Ref Structural</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_FEATURE_COUNT = VERSIONED_PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_DECLARED_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_FINAL_BY_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_ARRAY_LIKE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_DYNAMIC = VERSIONED_PARAMETERIZED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_EXISTENTIAL = VERSIONED_PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_DECLARED_UPPER_BOUND = VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_DECLARED_LOWER_BOUND = VERSIONED_PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPE_ARGS = VERSIONED_PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___TO_STRING = VERSIONED_PARAMETERIZED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_TOP_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_BOTTOM_TYPE = VERSIONED_PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_MODIFIERS_AS_STRING = VERSIONED_PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___CONTAINS_WILDCARDS = VERSIONED_PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_PARAMETERIZED = VERSIONED_PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_GENERIC = VERSIONED_PARAMETERIZED_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_RAW = VERSIONED_PARAMETERIZED_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___CONTAINS_UNBOUND_TYPE_VARIABLES = VERSIONED_PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_USE_SITE_STRUCTURAL_TYPING = VERSIONED_PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___IS_DEF_SITE_STRUCTURAL_TYPING = VERSIONED_PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___HAS_REQUESTED_VERSION = VERSIONED_PARAMETERIZED_TYPE_REF___HAS_REQUESTED_VERSION;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_REQUESTED_VERSION_OR_ZERO = VERSIONED_PARAMETERIZED_TYPE_REF___GET_REQUESTED_VERSION_OR_ZERO;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_VERSION = VERSIONED_PARAMETERIZED_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Has Postponed Substitution For</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Set Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPE_REF_AS_STRING = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>Versioned Parameterized Type Ref Structural</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_OPERATION_COUNT = VERSIONED_PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy <em>Optional Field Strategy</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy
	 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getOptionalFieldStrategy()
	 * @generated
	 */
	int OPTIONAL_FIELD_STRATEGY = 28;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.Versionable <em>Versionable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versionable</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.Versionable
	 * @generated
	 */
	EClass getVersionable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.Versionable#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.Versionable#getVersion()
	 * @generated
	 */
	EOperation getVersionable__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.TypeRef <em>Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef
	 * @generated
	 */
	EClass getTypeRef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isFollowedByQuestionMark <em>Followed By Question Mark</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Followed By Question Mark</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isFollowedByQuestionMark()
	 * @see #getTypeRef()
	 * @generated
	 */
	EAttribute getTypeRef_FollowedByQuestionMark();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getModifiersAsString() <em>Get Modifiers As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Modifiers As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getModifiersAsString()
	 * @generated
	 */
	EOperation getTypeRef__GetModifiersAsString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isFinalByType() <em>Is Final By Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Final By Type</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isFinalByType()
	 * @generated
	 */
	EOperation getTypeRef__IsFinalByType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isArrayLike() <em>Is Array Like</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Array Like</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isArrayLike()
	 * @generated
	 */
	EOperation getTypeRef__IsArrayLike();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isDynamic() <em>Is Dynamic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Dynamic</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isDynamic()
	 * @generated
	 */
	EOperation getTypeRef__IsDynamic();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isExistential() <em>Is Existential</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Existential</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isExistential()
	 * @generated
	 */
	EOperation getTypeRef__IsExistential();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isGeneric() <em>Is Generic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Generic</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isGeneric()
	 * @generated
	 */
	EOperation getTypeRef__IsGeneric();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isParameterized() <em>Is Parameterized</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Parameterized</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isParameterized()
	 * @generated
	 */
	EOperation getTypeRef__IsParameterized();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isRaw() <em>Is Raw</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Raw</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isRaw()
	 * @generated
	 */
	EOperation getTypeRef__IsRaw();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getDeclaredUpperBound() <em>Get Declared Upper Bound</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Upper Bound</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getDeclaredUpperBound()
	 * @generated
	 */
	EOperation getTypeRef__GetDeclaredUpperBound();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getDeclaredLowerBound() <em>Get Declared Lower Bound</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Lower Bound</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getDeclaredLowerBound()
	 * @generated
	 */
	EOperation getTypeRef__GetDeclaredLowerBound();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getTypeArgs() <em>Get Type Args</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Args</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getTypeArgs()
	 * @generated
	 */
	EOperation getTypeRef__GetTypeArgs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#toString()
	 * @generated
	 */
	EOperation getTypeRef__ToString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isTopType() <em>Is Top Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Top Type</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isTopType()
	 * @generated
	 */
	EOperation getTypeRef__IsTopType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isBottomType() <em>Is Bottom Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Bottom Type</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isBottomType()
	 * @generated
	 */
	EOperation getTypeRef__IsBottomType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getTypingStrategy() <em>Get Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getTypingStrategy()
	 * @generated
	 */
	EOperation getTypeRef__GetTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getStructuralMembers() <em>Get Structural Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Structural Members</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getStructuralMembers()
	 * @generated
	 */
	EOperation getTypeRef__GetStructuralMembers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isUseSiteStructuralTyping() <em>Is Use Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Use Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isUseSiteStructuralTyping()
	 * @generated
	 */
	EOperation getTypeRef__IsUseSiteStructuralTyping();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#isDefSiteStructuralTyping() <em>Is Def Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Def Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#isDefSiteStructuralTyping()
	 * @generated
	 */
	EOperation getTypeRef__IsDefSiteStructuralTyping();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getASTNodeOptionalFieldStrategy() <em>Get AST Node Optional Field Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getASTNodeOptionalFieldStrategy()
	 * @generated
	 */
	EOperation getTypeRef__GetASTNodeOptionalFieldStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeRef#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRef#getVersion()
	 * @generated
	 */
	EOperation getTypeRef__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef <em>Static Base Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Static Base Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.StaticBaseTypeRef
	 * @generated
	 */
	EClass getStaticBaseTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.BaseTypeRef <em>Base Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.BaseTypeRef
	 * @generated
	 */
	EClass getBaseTypeRef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.BaseTypeRef#isDynamic <em>Dynamic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dynamic</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.BaseTypeRef#isDynamic()
	 * @see #getBaseTypeRef()
	 * @generated
	 */
	EAttribute getBaseTypeRef_Dynamic();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BaseTypeRef#getModifiersAsString() <em>Get Modifiers As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Modifiers As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BaseTypeRef#getModifiersAsString()
	 * @generated
	 */
	EOperation getBaseTypeRef__GetModifiersAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef <em>Composed Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
	 * @generated
	 */
	EClass getComposedTypeRef();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#getTypeRefs <em>Type Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Refs</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#getTypeRefs()
	 * @see #getComposedTypeRef()
	 * @generated
	 */
	EReference getComposedTypeRef_TypeRefs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#isDynamic() <em>Is Dynamic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Dynamic</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#isDynamic()
	 * @generated
	 */
	EOperation getComposedTypeRef__IsDynamic();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ComposedTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getComposedTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.UnionTypeExpression <em>Union Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Union Type Expression</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
	 * @generated
	 */
	EClass getUnionTypeExpression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.UnionTypeExpression#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.UnionTypeExpression#getTypeRefAsString()
	 * @generated
	 */
	EOperation getUnionTypeExpression__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression <em>Intersection Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Intersection Type Expression</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
	 * @generated
	 */
	EClass getIntersectionTypeExpression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression#getTypeRefAsString()
	 * @generated
	 */
	EOperation getIntersectionTypeExpression__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRef <em>This Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>This Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRef
	 * @generated
	 */
	EClass getThisTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getThisTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRef#getTypingStrategy() <em>Get Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRef#getTypingStrategy()
	 * @generated
	 */
	EOperation getThisTypeRef__GetTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRef#getStructuralMembers() <em>Get Structural Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Structural Members</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRef#getStructuralMembers()
	 * @generated
	 */
	EOperation getThisTypeRef__GetStructuralMembers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRef#isUseSiteStructuralTyping() <em>Is Use Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Use Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRef#isUseSiteStructuralTyping()
	 * @generated
	 */
	EOperation getThisTypeRef__IsUseSiteStructuralTyping();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefNominal <em>This Type Ref Nominal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>This Type Ref Nominal</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefNominal
	 * @generated
	 */
	EClass getThisTypeRefNominal();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural <em>This Type Ref Structural</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>This Type Ref Structural</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural
	 * @generated
	 */
	EClass getThisTypeRefStructural();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Defined Typing Strategy</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getDefinedTypingStrategy()
	 * @see #getThisTypeRefStructural()
	 * @generated
	 */
	EAttribute getThisTypeRefStructural_DefinedTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getTypingStrategy() <em>Get Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getTypingStrategy()
	 * @generated
	 */
	EOperation getThisTypeRefStructural__GetTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy) <em>Set Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy)
	 * @generated
	 */
	EOperation getThisTypeRefStructural__SetTypingStrategy__TypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#isUseSiteStructuralTyping() <em>Is Use Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Use Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#isUseSiteStructuralTyping()
	 * @generated
	 */
	EOperation getThisTypeRefStructural__IsUseSiteStructuralTyping();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getStructuralMembers() <em>Get Structural Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Structural Members</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getStructuralMembers()
	 * @generated
	 */
	EOperation getThisTypeRefStructural__GetStructuralMembers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural#getTypeRefAsString()
	 * @generated
	 */
	EOperation getThisTypeRefStructural__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef <em>Bound This Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bound This Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
	 * @generated
	 */
	EClass getBoundThisTypeRef();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getActualThisTypeRef <em>Actual This Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Actual This Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getActualThisTypeRef()
	 * @see #getBoundThisTypeRef()
	 * @generated
	 */
	EReference getBoundThisTypeRef_ActualThisTypeRef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Defined Typing Strategy</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDefinedTypingStrategy()
	 * @see #getBoundThisTypeRef()
	 * @generated
	 */
	EAttribute getBoundThisTypeRef_DefinedTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getTypingStrategy() <em>Get Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getTypingStrategy()
	 * @generated
	 */
	EOperation getBoundThisTypeRef__GetTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy) <em>Set Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy)
	 * @generated
	 */
	EOperation getBoundThisTypeRef__SetTypingStrategy__TypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getBoundThisTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDeclaredUpperBound() <em>Get Declared Upper Bound</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Upper Bound</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getDeclaredUpperBound()
	 * @generated
	 */
	EOperation getBoundThisTypeRef__GetDeclaredUpperBound();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#isDefSiteStructuralTyping() <em>Is Def Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Def Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#isDefSiteStructuralTyping()
	 * @generated
	 */
	EOperation getBoundThisTypeRef__IsDefSiteStructuralTyping();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#isUseSiteStructuralTyping() <em>Is Use Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Use Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#isUseSiteStructuralTyping()
	 * @generated
	 */
	EOperation getBoundThisTypeRef__IsUseSiteStructuralTyping();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getStructuralMembers() <em>Get Structural Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Structural Members</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getStructuralMembers()
	 * @generated
	 */
	EOperation getBoundThisTypeRef__GetStructuralMembers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef#getVersion()
	 * @generated
	 */
	EOperation getBoundThisTypeRef__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef <em>Parameterized Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
	 * @generated
	 */
	EClass getParameterizedTypeRef();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDeclaredType <em>Declared Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Declared Type</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDeclaredType()
	 * @see #getParameterizedTypeRef()
	 * @generated
	 */
	EReference getParameterizedTypeRef_DeclaredType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getTypeArgs <em>Type Args</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Args</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getTypeArgs()
	 * @see #getParameterizedTypeRef()
	 * @generated
	 */
	EReference getParameterizedTypeRef_TypeArgs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayTypeExpression <em>Array Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Type Expression</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isArrayTypeExpression()
	 * @see #getParameterizedTypeRef()
	 * @generated
	 */
	EAttribute getParameterizedTypeRef_ArrayTypeExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isIterableTypeExpression <em>Iterable Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Iterable Type Expression</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isIterableTypeExpression()
	 * @see #getParameterizedTypeRef()
	 * @generated
	 */
	EAttribute getParameterizedTypeRef_IterableTypeExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getAstNamespace <em>Ast Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ast Namespace</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getAstNamespace()
	 * @see #getParameterizedTypeRef()
	 * @generated
	 */
	EReference getParameterizedTypeRef_AstNamespace();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getASTNodeOptionalFieldStrategy <em>AST Node Optional Field Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>AST Node Optional Field Strategy</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getASTNodeOptionalFieldStrategy()
	 * @see #getParameterizedTypeRef()
	 * @generated
	 */
	EAttribute getParameterizedTypeRef_ASTNodeOptionalFieldStrategy();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDefinedTypingStrategy <em>Defined Typing Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Defined Typing Strategy</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getDefinedTypingStrategy()
	 * @see #getParameterizedTypeRef()
	 * @generated
	 */
	EAttribute getParameterizedTypeRef_DefinedTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getTypingStrategy() <em>Get Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getTypingStrategy()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__GetTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#containsWildcards() <em>Contains Wildcards</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Contains Wildcards</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#containsWildcards()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__ContainsWildcards();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isParameterized() <em>Is Parameterized</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Parameterized</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isParameterized()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__IsParameterized();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isGeneric() <em>Is Generic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Generic</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isGeneric()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__IsGeneric();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isRaw() <em>Is Raw</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Raw</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isRaw()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__IsRaw();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#containsUnboundTypeVariables() <em>Contains Unbound Type Variables</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Contains Unbound Type Variables</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#containsUnboundTypeVariables()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__ContainsUnboundTypeVariables();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isUseSiteStructuralTyping() <em>Is Use Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Use Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isUseSiteStructuralTyping()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__IsUseSiteStructuralTyping();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isDefSiteStructuralTyping() <em>Is Def Site Structural Typing</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Def Site Structural Typing</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef#isDefSiteStructuralTyping()
	 * @generated
	 */
	EOperation getParameterizedTypeRef__IsDefSiteStructuralTyping();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef <em>Structural Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structural Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
	 * @generated
	 */
	EClass getStructuralTypeRef();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getAstStructuralMembers <em>Ast Structural Members</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ast Structural Members</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getAstStructuralMembers()
	 * @see #getStructuralTypeRef()
	 * @generated
	 */
	EReference getStructuralTypeRef_AstStructuralMembers();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getStructuralType <em>Structural Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Structural Type</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getStructuralType()
	 * @see #getStructuralTypeRef()
	 * @generated
	 */
	EReference getStructuralTypeRef_StructuralType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getGenStructuralMembers <em>Gen Structural Members</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gen Structural Members</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getGenStructuralMembers()
	 * @see #getStructuralTypeRef()
	 * @generated
	 */
	EReference getStructuralTypeRef_GenStructuralMembers();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getPostponedSubstitutions <em>Postponed Substitutions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Postponed Substitutions</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getPostponedSubstitutions()
	 * @see #getStructuralTypeRef()
	 * @generated
	 */
	EReference getStructuralTypeRef_PostponedSubstitutions();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getTypingStrategy() <em>Get Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getTypingStrategy()
	 * @generated
	 */
	EOperation getStructuralTypeRef__GetTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy) <em>Set Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy)
	 * @generated
	 */
	EOperation getStructuralTypeRef__SetTypingStrategy__TypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getStructuralMembers() <em>Get Structural Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Structural Members</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#getStructuralMembers()
	 * @generated
	 */
	EOperation getStructuralTypeRef__GetStructuralMembers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#hasPostponedSubstitutionFor(org.eclipse.n4js.ts.types.TypeVariable) <em>Has Postponed Substitution For</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Postponed Substitution For</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.StructuralTypeRef#hasPostponedSubstitutionFor(org.eclipse.n4js.ts.types.TypeVariable)
	 * @generated
	 */
	EOperation getStructuralTypeRef__HasPostponedSubstitutionFor__TypeVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural <em>Parameterized Type Ref Structural</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Type Ref Structural</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
	 * @generated
	 */
	EClass getParameterizedTypeRefStructural();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#getTypingStrategy() <em>Get Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#getTypingStrategy()
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural__GetTypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy) <em>Set Typing Strategy</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Typing Strategy</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#setTypingStrategy(org.eclipse.n4js.ts.types.TypingStrategy)
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural__SetTypingStrategy__TypingStrategy();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#getStructuralMembers() <em>Get Structural Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Structural Members</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#getStructuralMembers()
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural__GetStructuralMembers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural#getTypeRefAsString()
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef <em>Existential Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Existential Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef
	 * @generated
	 */
	EClass getExistentialTypeRef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getId()
	 * @see #getExistentialTypeRef()
	 * @generated
	 */
	EAttribute getExistentialTypeRef_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isReopened <em>Reopened</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reopened</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isReopened()
	 * @see #getExistentialTypeRef()
	 * @generated
	 */
	EAttribute getExistentialTypeRef_Reopened();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getWildcard <em>Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Wildcard</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getWildcard()
	 * @see #getExistentialTypeRef()
	 * @generated
	 */
	EReference getExistentialTypeRef_Wildcard();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getBoundTypeVariable <em>Bound Type Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Bound Type Variable</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getBoundTypeVariable()
	 * @see #getExistentialTypeRef()
	 * @generated
	 */
	EReference getExistentialTypeRef_BoundTypeVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isExistential() <em>Is Existential</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Existential</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isExistential()
	 * @generated
	 */
	EOperation getExistentialTypeRef__IsExistential();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isGeneric() <em>Is Generic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Generic</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isGeneric()
	 * @generated
	 */
	EOperation getExistentialTypeRef__IsGeneric();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isParameterized() <em>Is Parameterized</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Parameterized</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#isParameterized()
	 * @generated
	 */
	EOperation getExistentialTypeRef__IsParameterized();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getExistentialTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.UnknownTypeRef <em>Unknown Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unknown Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
	 * @generated
	 */
	EClass getUnknownTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.UnknownTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.UnknownTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getUnknownTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef <em>Type Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeTypeRef
	 * @generated
	 */
	EClass getTypeTypeRef();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef#getTypeArg <em>Type Arg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Arg</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeTypeRef#getTypeArg()
	 * @see #getTypeTypeRef()
	 * @generated
	 */
	EReference getTypeTypeRef_TypeArg();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef#isConstructorRef <em>Constructor Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constructor Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeTypeRef#isConstructorRef()
	 * @see #getTypeTypeRef()
	 * @generated
	 */
	EAttribute getTypeTypeRef_ConstructorRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getTypeTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.TypeArgument <em>Type Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Argument</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeArgument
	 * @generated
	 */
	EClass getTypeArgument();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeArgument#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeArgument#getTypeRefAsString()
	 * @generated
	 */
	EOperation getTypeArgument__GetTypeRefAsString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeArgument#containsWildcards() <em>Contains Wildcards</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Contains Wildcards</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeArgument#containsWildcards()
	 * @generated
	 */
	EOperation getTypeArgument__ContainsWildcards();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeArgument#containsUnboundTypeVariables() <em>Contains Unbound Type Variables</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Contains Unbound Type Variables</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeArgument#containsUnboundTypeVariables()
	 * @generated
	 */
	EOperation getTypeArgument__ContainsUnboundTypeVariables();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.TypeArgument#getDeclaredType() <em>Get Declared Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeArgument#getDeclaredType()
	 * @generated
	 */
	EOperation getTypeArgument__GetDeclaredType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.Wildcard <em>Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wildcard</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard
	 * @generated
	 */
	EClass getWildcard();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredUpperBound <em>Declared Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Declared Upper Bound</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredUpperBound()
	 * @see #getWildcard()
	 * @generated
	 */
	EReference getWildcard_DeclaredUpperBound();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredLowerBound <em>Declared Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Declared Lower Bound</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredLowerBound()
	 * @see #getWildcard()
	 * @generated
	 */
	EReference getWildcard_DeclaredLowerBound();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#isUsingInOutNotation <em>Using In Out Notation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Using In Out Notation</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard#isUsingInOutNotation()
	 * @see #getWildcard()
	 * @generated
	 */
	EAttribute getWildcard_UsingInOutNotation();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredOrImplicitUpperBound() <em>Get Declared Or Implicit Upper Bound</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Or Implicit Upper Bound</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard#getDeclaredOrImplicitUpperBound()
	 * @generated
	 */
	EOperation getWildcard__GetDeclaredOrImplicitUpperBound();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#isImplicitUpperBoundInEffect() <em>Is Implicit Upper Bound In Effect</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Implicit Upper Bound In Effect</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard#isImplicitUpperBoundInEffect()
	 * @generated
	 */
	EOperation getWildcard__IsImplicitUpperBoundInEffect();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.Wildcard#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.Wildcard#getTypeRefAsString()
	 * @generated
	 */
	EOperation getWildcard__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef <em>Function Type Expr Or Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Type Expr Or Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
	 * @generated
	 */
	EClass getFunctionTypeExprOrRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getDeclaredThisType() <em>Get Declared This Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared This Type</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getDeclaredThisType()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetDeclaredThisType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getFunctionType() <em>Get Function Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Function Type</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getFunctionType()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetFunctionType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getTypeVars() <em>Get Type Vars</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Vars</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getTypeVars()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetTypeVars();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getTypeVarUpperBound(org.eclipse.n4js.ts.types.TypeVariable) <em>Get Type Var Upper Bound</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Var Upper Bound</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getTypeVarUpperBound(org.eclipse.n4js.ts.types.TypeVariable)
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetTypeVarUpperBound__TypeVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getFpars() <em>Get Fpars</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Fpars</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getFpars()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetFpars();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#isReturnValueOptional() <em>Is Return Value Optional</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Return Value Optional</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#isReturnValueOptional()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__IsReturnValueOptional();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getReturnTypeRef() <em>Get Return Type Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Return Type Ref</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getReturnTypeRef()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetReturnTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#isGeneric() <em>Is Generic</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Generic</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#isGeneric()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__IsGeneric();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#isRaw() <em>Is Raw</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Raw</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#isRaw()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__IsRaw();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getFparForArgIdx(int) <em>Get Fpar For Arg Idx</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Fpar For Arg Idx</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getFparForArgIdx(int)
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetFparForArgIdx__int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getFunctionTypeExprOrRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeRef <em>Function Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
	 * @generated
	 */
	EClass getFunctionTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getDeclaredThisType() <em>Get Declared This Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared This Type</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getDeclaredThisType()
	 * @generated
	 */
	EOperation getFunctionTypeRef__GetDeclaredThisType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getTypeVars() <em>Get Type Vars</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Vars</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getTypeVars()
	 * @generated
	 */
	EOperation getFunctionTypeRef__GetTypeVars();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getTypeVarUpperBound(org.eclipse.n4js.ts.types.TypeVariable) <em>Get Type Var Upper Bound</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Var Upper Bound</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getTypeVarUpperBound(org.eclipse.n4js.ts.types.TypeVariable)
	 * @generated
	 */
	EOperation getFunctionTypeRef__GetTypeVarUpperBound__TypeVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getFpars() <em>Get Fpars</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Fpars</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getFpars()
	 * @generated
	 */
	EOperation getFunctionTypeRef__GetFpars();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getReturnTypeRef() <em>Get Return Type Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Return Type Ref</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeRef#getReturnTypeRef()
	 * @generated
	 */
	EOperation getFunctionTypeRef__GetReturnTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression <em>Function Type Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Type Expression</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
	 * @generated
	 */
	EClass getFunctionTypeExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#isBinding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#isBinding()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EAttribute getFunctionTypeExpression_Binding();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getDeclaredType <em>Declared Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Declared Type</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getDeclaredType()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EReference getFunctionTypeExpression_DeclaredType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getDeclaredThisType <em>Declared This Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Declared This Type</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getDeclaredThisType()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EReference getFunctionTypeExpression_DeclaredThisType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getOwnedTypeVars <em>Owned Type Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Type Vars</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getOwnedTypeVars()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EReference getFunctionTypeExpression_OwnedTypeVars();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getUnboundTypeVars <em>Unbound Type Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Unbound Type Vars</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getUnboundTypeVars()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EReference getFunctionTypeExpression_UnboundTypeVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getUnboundTypeVarsUpperBounds <em>Unbound Type Vars Upper Bounds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Unbound Type Vars Upper Bounds</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getUnboundTypeVarsUpperBounds()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EReference getFunctionTypeExpression_UnboundTypeVarsUpperBounds();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getFpars <em>Fpars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fpars</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getFpars()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EReference getFunctionTypeExpression_Fpars();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#isReturnValueMarkedOptional <em>Return Value Marked Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Value Marked Optional</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#isReturnValueMarkedOptional()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EAttribute getFunctionTypeExpression_ReturnValueMarkedOptional();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getReturnTypeRef <em>Return Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Return Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getReturnTypeRef()
	 * @see #getFunctionTypeExpression()
	 * @generated
	 */
	EReference getFunctionTypeExpression_ReturnTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getTypeVars() <em>Get Type Vars</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Vars</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getTypeVars()
	 * @generated
	 */
	EOperation getFunctionTypeExpression__GetTypeVars();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getTypeVarUpperBound(org.eclipse.n4js.ts.types.TypeVariable) <em>Get Type Var Upper Bound</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Var Upper Bound</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#getTypeVarUpperBound(org.eclipse.n4js.ts.types.TypeVariable)
	 * @generated
	 */
	EOperation getFunctionTypeExpression__GetTypeVarUpperBound__TypeVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#isReturnValueOptional() <em>Is Return Value Optional</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Return Value Optional</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression#isReturnValueOptional()
	 * @generated
	 */
	EOperation getFunctionTypeExpression__IsReturnValueOptional();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.DeferredTypeRef <em>Deferred Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deferred Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
	 * @generated
	 */
	EClass getDeferredTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.DeferredTypeRef#getTypeRefAsString() <em>Get Type Ref As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Type Ref As String</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.DeferredTypeRef#getTypeRefAsString()
	 * @generated
	 */
	EOperation getDeferredTypeRef__GetTypeRefAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping <em>Type Variable Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Variable Mapping</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeVariableMapping
	 * @generated
	 */
	EClass getTypeVariableMapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeVar <em>Type Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Var</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeVar()
	 * @see #getTypeVariableMapping()
	 * @generated
	 */
	EReference getTypeVariableMapping_TypeVar();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeArg <em>Type Arg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Arg</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.TypeVariableMapping#getTypeArg()
	 * @see #getTypeVariableMapping()
	 * @generated
	 */
	EReference getTypeVariableMapping_TypeArg();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.VersionedReference <em>Versioned Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Reference</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedReference
	 * @generated
	 */
	EClass getVersionedReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.ts.typeRefs.VersionedReference#getRequestedVersion <em>Requested Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Requested Version</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedReference#getRequestedVersion()
	 * @see #getVersionedReference()
	 * @generated
	 */
	EAttribute getVersionedReference_RequestedVersion();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.VersionedReference#hasRequestedVersion() <em>Has Requested Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Requested Version</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedReference#hasRequestedVersion()
	 * @generated
	 */
	EOperation getVersionedReference__HasRequestedVersion();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.VersionedReference#getRequestedVersionOrZero() <em>Get Requested Version Or Zero</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Requested Version Or Zero</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedReference#getRequestedVersionOrZero()
	 * @generated
	 */
	EOperation getVersionedReference__GetRequestedVersionOrZero();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef <em>Versioned Parameterized Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Parameterized Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef
	 * @generated
	 */
	EClass getVersionedParameterizedTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef#getVersion()
	 * @generated
	 */
	EOperation getVersionedParameterizedTypeRef__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.VersionedFunctionTypeRef <em>Versioned Function Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Function Type Ref</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedFunctionTypeRef
	 * @generated
	 */
	EClass getVersionedFunctionTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRefStructural <em>Versioned Parameterized Type Ref Structural</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Parameterized Type Ref Structural</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRefStructural
	 * @generated
	 */
	EClass getVersionedParameterizedTypeRefStructural();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy <em>Optional Field Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Optional Field Strategy</em>'.
	 * @see org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy
	 * @generated
	 */
	EEnum getOptionalFieldStrategy();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TypeRefsFactory getTypeRefsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionableImpl <em>Versionable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionableImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionable()
		 * @generated
		 */
		EClass VERSIONABLE = eINSTANCE.getVersionable();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSIONABLE___GET_VERSION = eINSTANCE.getVersionable__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeRefImpl <em>Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeRef()
		 * @generated
		 */
		EClass TYPE_REF = eINSTANCE.getTypeRef();

		/**
		 * The meta object literal for the '<em><b>Followed By Question Mark</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_REF__FOLLOWED_BY_QUESTION_MARK = eINSTANCE.getTypeRef_FollowedByQuestionMark();

		/**
		 * The meta object literal for the '<em><b>Get Modifiers As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_MODIFIERS_AS_STRING = eINSTANCE.getTypeRef__GetModifiersAsString();

		/**
		 * The meta object literal for the '<em><b>Is Final By Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_FINAL_BY_TYPE = eINSTANCE.getTypeRef__IsFinalByType();

		/**
		 * The meta object literal for the '<em><b>Is Array Like</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_ARRAY_LIKE = eINSTANCE.getTypeRef__IsArrayLike();

		/**
		 * The meta object literal for the '<em><b>Is Dynamic</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_DYNAMIC = eINSTANCE.getTypeRef__IsDynamic();

		/**
		 * The meta object literal for the '<em><b>Is Existential</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_EXISTENTIAL = eINSTANCE.getTypeRef__IsExistential();

		/**
		 * The meta object literal for the '<em><b>Is Generic</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_GENERIC = eINSTANCE.getTypeRef__IsGeneric();

		/**
		 * The meta object literal for the '<em><b>Is Parameterized</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_PARAMETERIZED = eINSTANCE.getTypeRef__IsParameterized();

		/**
		 * The meta object literal for the '<em><b>Is Raw</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_RAW = eINSTANCE.getTypeRef__IsRaw();

		/**
		 * The meta object literal for the '<em><b>Get Declared Upper Bound</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_DECLARED_UPPER_BOUND = eINSTANCE.getTypeRef__GetDeclaredUpperBound();

		/**
		 * The meta object literal for the '<em><b>Get Declared Lower Bound</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_DECLARED_LOWER_BOUND = eINSTANCE.getTypeRef__GetDeclaredLowerBound();

		/**
		 * The meta object literal for the '<em><b>Get Type Args</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_TYPE_ARGS = eINSTANCE.getTypeRef__GetTypeArgs();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___TO_STRING = eINSTANCE.getTypeRef__ToString();

		/**
		 * The meta object literal for the '<em><b>Is Top Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_TOP_TYPE = eINSTANCE.getTypeRef__IsTopType();

		/**
		 * The meta object literal for the '<em><b>Is Bottom Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_BOTTOM_TYPE = eINSTANCE.getTypeRef__IsBottomType();

		/**
		 * The meta object literal for the '<em><b>Get Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_TYPING_STRATEGY = eINSTANCE.getTypeRef__GetTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Structural Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_STRUCTURAL_MEMBERS = eINSTANCE.getTypeRef__GetStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Is Use Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = eINSTANCE.getTypeRef__IsUseSiteStructuralTyping();

		/**
		 * The meta object literal for the '<em><b>Is Def Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = eINSTANCE.getTypeRef__IsDefSiteStructuralTyping();

		/**
		 * The meta object literal for the '<em><b>Get AST Node Optional Field Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = eINSTANCE.getTypeRef__GetASTNodeOptionalFieldStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF___GET_VERSION = eINSTANCE.getTypeRef__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.StaticBaseTypeRefImpl <em>Static Base Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.StaticBaseTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getStaticBaseTypeRef()
		 * @generated
		 */
		EClass STATIC_BASE_TYPE_REF = eINSTANCE.getStaticBaseTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.BaseTypeRefImpl <em>Base Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.BaseTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getBaseTypeRef()
		 * @generated
		 */
		EClass BASE_TYPE_REF = eINSTANCE.getBaseTypeRef();

		/**
		 * The meta object literal for the '<em><b>Dynamic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_TYPE_REF__DYNAMIC = eINSTANCE.getBaseTypeRef_Dynamic();

		/**
		 * The meta object literal for the '<em><b>Get Modifiers As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BASE_TYPE_REF___GET_MODIFIERS_AS_STRING = eINSTANCE.getBaseTypeRef__GetModifiersAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ComposedTypeRefImpl <em>Composed Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.ComposedTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getComposedTypeRef()
		 * @generated
		 */
		EClass COMPOSED_TYPE_REF = eINSTANCE.getComposedTypeRef();

		/**
		 * The meta object literal for the '<em><b>Type Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSED_TYPE_REF__TYPE_REFS = eINSTANCE.getComposedTypeRef_TypeRefs();

		/**
		 * The meta object literal for the '<em><b>Is Dynamic</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOSED_TYPE_REF___IS_DYNAMIC = eINSTANCE.getComposedTypeRef__IsDynamic();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation COMPOSED_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getComposedTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.UnionTypeExpressionImpl <em>Union Type Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.UnionTypeExpressionImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getUnionTypeExpression()
		 * @generated
		 */
		EClass UNION_TYPE_EXPRESSION = eINSTANCE.getUnionTypeExpression();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation UNION_TYPE_EXPRESSION___GET_TYPE_REF_AS_STRING = eINSTANCE.getUnionTypeExpression__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.IntersectionTypeExpressionImpl <em>Intersection Type Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.IntersectionTypeExpressionImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getIntersectionTypeExpression()
		 * @generated
		 */
		EClass INTERSECTION_TYPE_EXPRESSION = eINSTANCE.getIntersectionTypeExpression();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation INTERSECTION_TYPE_EXPRESSION___GET_TYPE_REF_AS_STRING = eINSTANCE.getIntersectionTypeExpression__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefImpl <em>This Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getThisTypeRef()
		 * @generated
		 */
		EClass THIS_TYPE_REF = eINSTANCE.getThisTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getThisTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '<em><b>Get Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF___GET_TYPING_STRATEGY = eINSTANCE.getThisTypeRef__GetTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Structural Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS = eINSTANCE.getThisTypeRef__GetStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Is Use Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = eINSTANCE.getThisTypeRef__IsUseSiteStructuralTyping();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefNominalImpl <em>This Type Ref Nominal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefNominalImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getThisTypeRefNominal()
		 * @generated
		 */
		EClass THIS_TYPE_REF_NOMINAL = eINSTANCE.getThisTypeRefNominal();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl <em>This Type Ref Structural</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.ThisTypeRefStructuralImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getThisTypeRefStructural()
		 * @generated
		 */
		EClass THIS_TYPE_REF_STRUCTURAL = eINSTANCE.getThisTypeRefStructural();

		/**
		 * The meta object literal for the '<em><b>Defined Typing Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THIS_TYPE_REF_STRUCTURAL__DEFINED_TYPING_STRATEGY = eINSTANCE.getThisTypeRefStructural_DefinedTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY = eINSTANCE.getThisTypeRefStructural__GetTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Set Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY = eINSTANCE.getThisTypeRefStructural__SetTypingStrategy__TypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Is Use Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF_STRUCTURAL___IS_USE_SITE_STRUCTURAL_TYPING = eINSTANCE.getThisTypeRefStructural__IsUseSiteStructuralTyping();

		/**
		 * The meta object literal for the '<em><b>Get Structural Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS = eINSTANCE.getThisTypeRefStructural__GetStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation THIS_TYPE_REF_STRUCTURAL___GET_TYPE_REF_AS_STRING = eINSTANCE.getThisTypeRefStructural__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl <em>Bound This Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.BoundThisTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getBoundThisTypeRef()
		 * @generated
		 */
		EClass BOUND_THIS_TYPE_REF = eINSTANCE.getBoundThisTypeRef();

		/**
		 * The meta object literal for the '<em><b>Actual This Type Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOUND_THIS_TYPE_REF__ACTUAL_THIS_TYPE_REF = eINSTANCE.getBoundThisTypeRef_ActualThisTypeRef();

		/**
		 * The meta object literal for the '<em><b>Defined Typing Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOUND_THIS_TYPE_REF__DEFINED_TYPING_STRATEGY = eINSTANCE.getBoundThisTypeRef_DefinedTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___GET_TYPING_STRATEGY = eINSTANCE.getBoundThisTypeRef__GetTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Set Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY = eINSTANCE.getBoundThisTypeRef__SetTypingStrategy__TypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getBoundThisTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '<em><b>Get Declared Upper Bound</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___GET_DECLARED_UPPER_BOUND = eINSTANCE.getBoundThisTypeRef__GetDeclaredUpperBound();

		/**
		 * The meta object literal for the '<em><b>Is Def Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = eINSTANCE.getBoundThisTypeRef__IsDefSiteStructuralTyping();

		/**
		 * The meta object literal for the '<em><b>Is Use Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = eINSTANCE.getBoundThisTypeRef__IsUseSiteStructuralTyping();

		/**
		 * The meta object literal for the '<em><b>Get Structural Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___GET_STRUCTURAL_MEMBERS = eINSTANCE.getBoundThisTypeRef__GetStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOUND_THIS_TYPE_REF___GET_VERSION = eINSTANCE.getBoundThisTypeRef__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl <em>Parameterized Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getParameterizedTypeRef()
		 * @generated
		 */
		EClass PARAMETERIZED_TYPE_REF = eINSTANCE.getParameterizedTypeRef();

		/**
		 * The meta object literal for the '<em><b>Declared Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETERIZED_TYPE_REF__DECLARED_TYPE = eINSTANCE.getParameterizedTypeRef_DeclaredType();

		/**
		 * The meta object literal for the '<em><b>Type Args</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETERIZED_TYPE_REF__TYPE_ARGS = eINSTANCE.getParameterizedTypeRef_TypeArgs();

		/**
		 * The meta object literal for the '<em><b>Array Type Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION = eINSTANCE.getParameterizedTypeRef_ArrayTypeExpression();

		/**
		 * The meta object literal for the '<em><b>Iterable Type Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION = eINSTANCE.getParameterizedTypeRef_IterableTypeExpression();

		/**
		 * The meta object literal for the '<em><b>Ast Namespace</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETERIZED_TYPE_REF__AST_NAMESPACE = eINSTANCE.getParameterizedTypeRef_AstNamespace();

		/**
		 * The meta object literal for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY = eINSTANCE.getParameterizedTypeRef_ASTNodeOptionalFieldStrategy();

		/**
		 * The meta object literal for the '<em><b>Defined Typing Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY = eINSTANCE.getParameterizedTypeRef_DefinedTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY = eINSTANCE.getParameterizedTypeRef__GetTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Contains Wildcards</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS = eINSTANCE.getParameterizedTypeRef__ContainsWildcards();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getParameterizedTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '<em><b>Is Parameterized</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED = eINSTANCE.getParameterizedTypeRef__IsParameterized();

		/**
		 * The meta object literal for the '<em><b>Is Generic</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___IS_GENERIC = eINSTANCE.getParameterizedTypeRef__IsGeneric();

		/**
		 * The meta object literal for the '<em><b>Is Raw</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___IS_RAW = eINSTANCE.getParameterizedTypeRef__IsRaw();

		/**
		 * The meta object literal for the '<em><b>Contains Unbound Type Variables</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES = eINSTANCE.getParameterizedTypeRef__ContainsUnboundTypeVariables();

		/**
		 * The meta object literal for the '<em><b>Is Use Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING = eINSTANCE.getParameterizedTypeRef__IsUseSiteStructuralTyping();

		/**
		 * The meta object literal for the '<em><b>Is Def Site Structural Typing</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING = eINSTANCE.getParameterizedTypeRef__IsDefSiteStructuralTyping();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.StructuralTypeRefImpl <em>Structural Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.StructuralTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getStructuralTypeRef()
		 * @generated
		 */
		EClass STRUCTURAL_TYPE_REF = eINSTANCE.getStructuralTypeRef();

		/**
		 * The meta object literal for the '<em><b>Ast Structural Members</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS = eINSTANCE.getStructuralTypeRef_AstStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Structural Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE = eINSTANCE.getStructuralTypeRef_StructuralType();

		/**
		 * The meta object literal for the '<em><b>Gen Structural Members</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS = eINSTANCE.getStructuralTypeRef_GenStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Postponed Substitutions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_TYPE_REF__POSTPONED_SUBSTITUTIONS = eINSTANCE.getStructuralTypeRef_PostponedSubstitutions();

		/**
		 * The meta object literal for the '<em><b>Get Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STRUCTURAL_TYPE_REF___GET_TYPING_STRATEGY = eINSTANCE.getStructuralTypeRef__GetTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Set Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STRUCTURAL_TYPE_REF___SET_TYPING_STRATEGY__TYPINGSTRATEGY = eINSTANCE.getStructuralTypeRef__SetTypingStrategy__TypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Structural Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STRUCTURAL_TYPE_REF___GET_STRUCTURAL_MEMBERS = eINSTANCE.getStructuralTypeRef__GetStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Has Postponed Substitution For</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STRUCTURAL_TYPE_REF___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = eINSTANCE.getStructuralTypeRef__HasPostponedSubstitutionFor__TypeVariable();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefStructuralImpl <em>Parameterized Type Ref Structural</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.ParameterizedTypeRefStructuralImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getParameterizedTypeRefStructural()
		 * @generated
		 */
		EClass PARAMETERIZED_TYPE_REF_STRUCTURAL = eINSTANCE.getParameterizedTypeRefStructural();

		/**
		 * The meta object literal for the '<em><b>Get Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPING_STRATEGY = eINSTANCE.getParameterizedTypeRefStructural__GetTypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Set Typing Strategy</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL___SET_TYPING_STRATEGY__TYPINGSTRATEGY = eINSTANCE.getParameterizedTypeRefStructural__SetTypingStrategy__TypingStrategy();

		/**
		 * The meta object literal for the '<em><b>Get Structural Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_STRUCTURAL_MEMBERS = eINSTANCE.getParameterizedTypeRefStructural__GetStructuralMembers();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL___GET_TYPE_REF_AS_STRING = eINSTANCE.getParameterizedTypeRefStructural__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.ExistentialTypeRefImpl <em>Existential Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.ExistentialTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getExistentialTypeRef()
		 * @generated
		 */
		EClass EXISTENTIAL_TYPE_REF = eINSTANCE.getExistentialTypeRef();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXISTENTIAL_TYPE_REF__ID = eINSTANCE.getExistentialTypeRef_Id();

		/**
		 * The meta object literal for the '<em><b>Reopened</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXISTENTIAL_TYPE_REF__REOPENED = eINSTANCE.getExistentialTypeRef_Reopened();

		/**
		 * The meta object literal for the '<em><b>Wildcard</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXISTENTIAL_TYPE_REF__WILDCARD = eINSTANCE.getExistentialTypeRef_Wildcard();

		/**
		 * The meta object literal for the '<em><b>Bound Type Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXISTENTIAL_TYPE_REF__BOUND_TYPE_VARIABLE = eINSTANCE.getExistentialTypeRef_BoundTypeVariable();

		/**
		 * The meta object literal for the '<em><b>Is Existential</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXISTENTIAL_TYPE_REF___IS_EXISTENTIAL = eINSTANCE.getExistentialTypeRef__IsExistential();

		/**
		 * The meta object literal for the '<em><b>Is Generic</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXISTENTIAL_TYPE_REF___IS_GENERIC = eINSTANCE.getExistentialTypeRef__IsGeneric();

		/**
		 * The meta object literal for the '<em><b>Is Parameterized</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXISTENTIAL_TYPE_REF___IS_PARAMETERIZED = eINSTANCE.getExistentialTypeRef__IsParameterized();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXISTENTIAL_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getExistentialTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.UnknownTypeRefImpl <em>Unknown Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.UnknownTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getUnknownTypeRef()
		 * @generated
		 */
		EClass UNKNOWN_TYPE_REF = eINSTANCE.getUnknownTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation UNKNOWN_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getUnknownTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeTypeRefImpl <em>Type Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeTypeRef()
		 * @generated
		 */
		EClass TYPE_TYPE_REF = eINSTANCE.getTypeTypeRef();

		/**
		 * The meta object literal for the '<em><b>Type Arg</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_TYPE_REF__TYPE_ARG = eINSTANCE.getTypeTypeRef_TypeArg();

		/**
		 * The meta object literal for the '<em><b>Constructor Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_TYPE_REF__CONSTRUCTOR_REF = eINSTANCE.getTypeTypeRef_ConstructorRef();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getTypeTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeArgumentImpl <em>Type Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeArgumentImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeArgument()
		 * @generated
		 */
		EClass TYPE_ARGUMENT = eINSTANCE.getTypeArgument();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_ARGUMENT___GET_TYPE_REF_AS_STRING = eINSTANCE.getTypeArgument__GetTypeRefAsString();

		/**
		 * The meta object literal for the '<em><b>Contains Wildcards</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_ARGUMENT___CONTAINS_WILDCARDS = eINSTANCE.getTypeArgument__ContainsWildcards();

		/**
		 * The meta object literal for the '<em><b>Contains Unbound Type Variables</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_ARGUMENT___CONTAINS_UNBOUND_TYPE_VARIABLES = eINSTANCE.getTypeArgument__ContainsUnboundTypeVariables();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_ARGUMENT___GET_DECLARED_TYPE = eINSTANCE.getTypeArgument__GetDeclaredType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.WildcardImpl <em>Wildcard</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.WildcardImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getWildcard()
		 * @generated
		 */
		EClass WILDCARD = eINSTANCE.getWildcard();

		/**
		 * The meta object literal for the '<em><b>Declared Upper Bound</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WILDCARD__DECLARED_UPPER_BOUND = eINSTANCE.getWildcard_DeclaredUpperBound();

		/**
		 * The meta object literal for the '<em><b>Declared Lower Bound</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WILDCARD__DECLARED_LOWER_BOUND = eINSTANCE.getWildcard_DeclaredLowerBound();

		/**
		 * The meta object literal for the '<em><b>Using In Out Notation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WILDCARD__USING_IN_OUT_NOTATION = eINSTANCE.getWildcard_UsingInOutNotation();

		/**
		 * The meta object literal for the '<em><b>Get Declared Or Implicit Upper Bound</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WILDCARD___GET_DECLARED_OR_IMPLICIT_UPPER_BOUND = eINSTANCE.getWildcard__GetDeclaredOrImplicitUpperBound();

		/**
		 * The meta object literal for the '<em><b>Is Implicit Upper Bound In Effect</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WILDCARD___IS_IMPLICIT_UPPER_BOUND_IN_EFFECT = eINSTANCE.getWildcard__IsImplicitUpperBoundInEffect();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation WILDCARD___GET_TYPE_REF_AS_STRING = eINSTANCE.getWildcard__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExprOrRefImpl <em>Function Type Expr Or Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExprOrRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getFunctionTypeExprOrRef()
		 * @generated
		 */
		EClass FUNCTION_TYPE_EXPR_OR_REF = eINSTANCE.getFunctionTypeExprOrRef();

		/**
		 * The meta object literal for the '<em><b>Get Declared This Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_DECLARED_THIS_TYPE = eINSTANCE.getFunctionTypeExprOrRef__GetDeclaredThisType();

		/**
		 * The meta object literal for the '<em><b>Get Function Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_FUNCTION_TYPE = eINSTANCE.getFunctionTypeExprOrRef__GetFunctionType();

		/**
		 * The meta object literal for the '<em><b>Get Type Vars</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VARS = eINSTANCE.getFunctionTypeExprOrRef__GetTypeVars();

		/**
		 * The meta object literal for the '<em><b>Get Type Var Upper Bound</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE = eINSTANCE.getFunctionTypeExprOrRef__GetTypeVarUpperBound__TypeVariable();

		/**
		 * The meta object literal for the '<em><b>Get Fpars</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_FPARS = eINSTANCE.getFunctionTypeExprOrRef__GetFpars();

		/**
		 * The meta object literal for the '<em><b>Is Return Value Optional</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___IS_RETURN_VALUE_OPTIONAL = eINSTANCE.getFunctionTypeExprOrRef__IsReturnValueOptional();

		/**
		 * The meta object literal for the '<em><b>Get Return Type Ref</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_RETURN_TYPE_REF = eINSTANCE.getFunctionTypeExprOrRef__GetReturnTypeRef();

		/**
		 * The meta object literal for the '<em><b>Is Generic</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___IS_GENERIC = eINSTANCE.getFunctionTypeExprOrRef__IsGeneric();

		/**
		 * The meta object literal for the '<em><b>Is Raw</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___IS_RAW = eINSTANCE.getFunctionTypeExprOrRef__IsRaw();

		/**
		 * The meta object literal for the '<em><b>Get Fpar For Arg Idx</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_FPAR_FOR_ARG_IDX__INT = eINSTANCE.getFunctionTypeExprOrRef__GetFparForArgIdx__int();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPR_OR_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getFunctionTypeExprOrRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeRefImpl <em>Function Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getFunctionTypeRef()
		 * @generated
		 */
		EClass FUNCTION_TYPE_REF = eINSTANCE.getFunctionTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Declared This Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_REF___GET_DECLARED_THIS_TYPE = eINSTANCE.getFunctionTypeRef__GetDeclaredThisType();

		/**
		 * The meta object literal for the '<em><b>Get Type Vars</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_REF___GET_TYPE_VARS = eINSTANCE.getFunctionTypeRef__GetTypeVars();

		/**
		 * The meta object literal for the '<em><b>Get Type Var Upper Bound</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_REF___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE = eINSTANCE.getFunctionTypeRef__GetTypeVarUpperBound__TypeVariable();

		/**
		 * The meta object literal for the '<em><b>Get Fpars</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_REF___GET_FPARS = eINSTANCE.getFunctionTypeRef__GetFpars();

		/**
		 * The meta object literal for the '<em><b>Get Return Type Ref</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_REF___GET_RETURN_TYPE_REF = eINSTANCE.getFunctionTypeRef__GetReturnTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl <em>Function Type Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.FunctionTypeExpressionImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getFunctionTypeExpression()
		 * @generated
		 */
		EClass FUNCTION_TYPE_EXPRESSION = eINSTANCE.getFunctionTypeExpression();

		/**
		 * The meta object literal for the '<em><b>Binding</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_TYPE_EXPRESSION__BINDING = eINSTANCE.getFunctionTypeExpression_Binding();

		/**
		 * The meta object literal for the '<em><b>Declared Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE_EXPRESSION__DECLARED_TYPE = eINSTANCE.getFunctionTypeExpression_DeclaredType();

		/**
		 * The meta object literal for the '<em><b>Declared This Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE_EXPRESSION__DECLARED_THIS_TYPE = eINSTANCE.getFunctionTypeExpression_DeclaredThisType();

		/**
		 * The meta object literal for the '<em><b>Owned Type Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE_EXPRESSION__OWNED_TYPE_VARS = eINSTANCE.getFunctionTypeExpression_OwnedTypeVars();

		/**
		 * The meta object literal for the '<em><b>Unbound Type Vars</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS = eINSTANCE.getFunctionTypeExpression_UnboundTypeVars();

		/**
		 * The meta object literal for the '<em><b>Unbound Type Vars Upper Bounds</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE_EXPRESSION__UNBOUND_TYPE_VARS_UPPER_BOUNDS = eINSTANCE.getFunctionTypeExpression_UnboundTypeVarsUpperBounds();

		/**
		 * The meta object literal for the '<em><b>Fpars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE_EXPRESSION__FPARS = eINSTANCE.getFunctionTypeExpression_Fpars();

		/**
		 * The meta object literal for the '<em><b>Return Value Marked Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_TYPE_EXPRESSION__RETURN_VALUE_MARKED_OPTIONAL = eINSTANCE.getFunctionTypeExpression_ReturnValueMarkedOptional();

		/**
		 * The meta object literal for the '<em><b>Return Type Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_TYPE_EXPRESSION__RETURN_TYPE_REF = eINSTANCE.getFunctionTypeExpression_ReturnTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Type Vars</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPRESSION___GET_TYPE_VARS = eINSTANCE.getFunctionTypeExpression__GetTypeVars();

		/**
		 * The meta object literal for the '<em><b>Get Type Var Upper Bound</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPRESSION___GET_TYPE_VAR_UPPER_BOUND__TYPEVARIABLE = eINSTANCE.getFunctionTypeExpression__GetTypeVarUpperBound__TypeVariable();

		/**
		 * The meta object literal for the '<em><b>Is Return Value Optional</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_TYPE_EXPRESSION___IS_RETURN_VALUE_OPTIONAL = eINSTANCE.getFunctionTypeExpression__IsReturnValueOptional();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.DeferredTypeRefImpl <em>Deferred Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.DeferredTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getDeferredTypeRef()
		 * @generated
		 */
		EClass DEFERRED_TYPE_REF = eINSTANCE.getDeferredTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Type Ref As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DEFERRED_TYPE_REF___GET_TYPE_REF_AS_STRING = eINSTANCE.getDeferredTypeRef__GetTypeRefAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.TypeVariableMappingImpl <em>Type Variable Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeVariableMappingImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getTypeVariableMapping()
		 * @generated
		 */
		EClass TYPE_VARIABLE_MAPPING = eINSTANCE.getTypeVariableMapping();

		/**
		 * The meta object literal for the '<em><b>Type Var</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_VARIABLE_MAPPING__TYPE_VAR = eINSTANCE.getTypeVariableMapping_TypeVar();

		/**
		 * The meta object literal for the '<em><b>Type Arg</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_VARIABLE_MAPPING__TYPE_ARG = eINSTANCE.getTypeVariableMapping_TypeArg();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedReferenceImpl <em>Versioned Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedReferenceImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedReference()
		 * @generated
		 */
		EClass VERSIONED_REFERENCE = eINSTANCE.getVersionedReference();

		/**
		 * The meta object literal for the '<em><b>Requested Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSIONED_REFERENCE__REQUESTED_VERSION = eINSTANCE.getVersionedReference_RequestedVersion();

		/**
		 * The meta object literal for the '<em><b>Has Requested Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSIONED_REFERENCE___HAS_REQUESTED_VERSION = eINSTANCE.getVersionedReference__HasRequestedVersion();

		/**
		 * The meta object literal for the '<em><b>Get Requested Version Or Zero</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSIONED_REFERENCE___GET_REQUESTED_VERSION_OR_ZERO = eINSTANCE.getVersionedReference__GetRequestedVersionOrZero();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefImpl <em>Versioned Parameterized Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedParameterizedTypeRef()
		 * @generated
		 */
		EClass VERSIONED_PARAMETERIZED_TYPE_REF = eINSTANCE.getVersionedParameterizedTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSIONED_PARAMETERIZED_TYPE_REF___GET_VERSION = eINSTANCE.getVersionedParameterizedTypeRef__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedFunctionTypeRefImpl <em>Versioned Function Type Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedFunctionTypeRefImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedFunctionTypeRef()
		 * @generated
		 */
		EClass VERSIONED_FUNCTION_TYPE_REF = eINSTANCE.getVersionedFunctionTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefStructuralImpl <em>Versioned Parameterized Type Ref Structural</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.impl.VersionedParameterizedTypeRefStructuralImpl
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getVersionedParameterizedTypeRefStructural()
		 * @generated
		 */
		EClass VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL = eINSTANCE.getVersionedParameterizedTypeRefStructural();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy <em>Optional Field Strategy</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy
		 * @see org.eclipse.n4js.ts.typeRefs.impl.TypeRefsPackageImpl#getOptionalFieldStrategy()
		 * @generated
		 */
		EEnum OPTIONAL_FIELD_STRATEGY = eINSTANCE.getOptionalFieldStrategy();

	}

} //TypeRefsPackage
