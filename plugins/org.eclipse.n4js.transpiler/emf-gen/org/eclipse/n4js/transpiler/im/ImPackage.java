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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.n4js.n4JS.N4JSPackage;

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;

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
 * @see org.eclipse.n4js.transpiler.im.ImFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel fileExtensions='n4js_im' modelDirectory='/org.eclipse.n4js.transpiler/emf-gen' forceOverwrite='true' updateClasspath='false' literalsInterface='true' loadInitialization='false' complianceLevel='8.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js.transpiler'"
 * @generated
 */
public interface ImPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "im";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/n4js/IM";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "im";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ImPackage eINSTANCE = org.eclipse.n4js.transpiler.im.impl.ImPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.Script_IMImpl <em>Script IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.Script_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getScript_IM()
	 * @generated
	 */
	int SCRIPT_IM = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM__ANNOTATIONS = N4JSPackage.SCRIPT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Script Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM__SCRIPT_ELEMENTS = N4JSPackage.SCRIPT__SCRIPT_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM__MODULE = N4JSPackage.SCRIPT__MODULE;

	/**
	 * The feature id for the '<em><b>Flagged Usage Marking Finished</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM__FLAGGED_USAGE_MARKING_FINISHED = N4JSPackage.SCRIPT__FLAGGED_USAGE_MARKING_FINISHED;

	/**
	 * The feature id for the '<em><b>Symbol Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM__SYMBOL_TABLE = N4JSPackage.SCRIPT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Script IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM_FEATURE_COUNT = N4JSPackage.SCRIPT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = N4JSPackage.SCRIPT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM___GET_ANNOTATIONS = N4JSPackage.SCRIPT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM___GET_ALL_ANNOTATIONS = N4JSPackage.SCRIPT___GET_ALL_ANNOTATIONS;

	/**
	 * The number of operations of the '<em>Script IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_IM_OPERATION_COUNT = N4JSPackage.SCRIPT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableImpl <em>Symbol Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTable()
	 * @generated
	 */
	int SYMBOL_TABLE = 1;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>Symbol Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Symbol Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryImpl <em>Symbol Table Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntry()
	 * @generated
	 */
	int SYMBOL_TABLE_ENTRY = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Elements Of This Name</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME = 1;

	/**
	 * The feature id for the '<em><b>Referencing Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS = 2;

	/**
	 * The number of structural features of the '<em>Symbol Table Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Symbol Table Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryOriginalImpl <em>Symbol Table Entry Original</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryOriginalImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntryOriginal()
	 * @generated
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL__NAME = SYMBOL_TABLE_ENTRY__NAME;

	/**
	 * The feature id for the '<em><b>Elements Of This Name</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL__ELEMENTS_OF_THIS_NAME = SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME;

	/**
	 * The feature id for the '<em><b>Referencing Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL__REFERENCING_ELEMENTS = SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Original Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET = SYMBOL_TABLE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Import Specifier</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER = SYMBOL_TABLE_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Symbol Table Entry Original</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL_FEATURE_COUNT = SYMBOL_TABLE_ENTRY_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL___GET_EXPORTED_NAME = SYMBOL_TABLE_ENTRY_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Symbol Table Entry Original</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_ORIGINAL_OPERATION_COUNT = SYMBOL_TABLE_ENTRY_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryIMOnlyImpl <em>Symbol Table Entry IM Only</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryIMOnlyImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntryIMOnly()
	 * @generated
	 */
	int SYMBOL_TABLE_ENTRY_IM_ONLY = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_IM_ONLY__NAME = SYMBOL_TABLE_ENTRY__NAME;

	/**
	 * The feature id for the '<em><b>Elements Of This Name</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_IM_ONLY__ELEMENTS_OF_THIS_NAME = SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME;

	/**
	 * The feature id for the '<em><b>Referencing Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_IM_ONLY__REFERENCING_ELEMENTS = SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS;

	/**
	 * The number of structural features of the '<em>Symbol Table Entry IM Only</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_IM_ONLY_FEATURE_COUNT = SYMBOL_TABLE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Symbol Table Entry IM Only</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_IM_ONLY_OPERATION_COUNT = SYMBOL_TABLE_ENTRY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryInternalImpl <em>Symbol Table Entry Internal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryInternalImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntryInternal()
	 * @generated
	 */
	int SYMBOL_TABLE_ENTRY_INTERNAL = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_INTERNAL__NAME = SYMBOL_TABLE_ENTRY__NAME;

	/**
	 * The feature id for the '<em><b>Elements Of This Name</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_INTERNAL__ELEMENTS_OF_THIS_NAME = SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME;

	/**
	 * The feature id for the '<em><b>Referencing Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_INTERNAL__REFERENCING_ELEMENTS = SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS;

	/**
	 * The number of structural features of the '<em>Symbol Table Entry Internal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_INTERNAL_FEATURE_COUNT = SYMBOL_TABLE_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Symbol Table Entry Internal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBOL_TABLE_ENTRY_INTERNAL_OPERATION_COUNT = SYMBOL_TABLE_ENTRY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.ReferencingElement_IMImpl <em>Referencing Element IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.ReferencingElement_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getReferencingElement_IM()
	 * @generated
	 */
	int REFERENCING_ELEMENT_IM = 6;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_IM__REWIRED_TARGET = 0;

	/**
	 * The number of structural features of the '<em>Referencing Element IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_IM_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = 0;

	/**
	 * The number of operations of the '<em>Referencing Element IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_IM_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.ReferencingElementExpression_IMImpl <em>Referencing Element Expression IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.ReferencingElementExpression_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getReferencingElementExpression_IM()
	 * @generated
	 */
	int REFERENCING_ELEMENT_EXPRESSION_IM = 7;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_EXPRESSION_IM__REWIRED_TARGET = REFERENCING_ELEMENT_IM__REWIRED_TARGET;

	/**
	 * The number of structural features of the '<em>Referencing Element Expression IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_EXPRESSION_IM_FEATURE_COUNT = REFERENCING_ELEMENT_IM_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_EXPRESSION_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = REFERENCING_ELEMENT_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_EXPRESSION_IM___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = REFERENCING_ELEMENT_IM_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Referencing Element Expression IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCING_ELEMENT_EXPRESSION_IM_OPERATION_COUNT = REFERENCING_ELEMENT_IM_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.IdentifierRef_IMImpl <em>Identifier Ref IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.IdentifierRef_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getIdentifierRef_IM()
	 * @generated
	 */
	int IDENTIFIER_REF_IM = 8;

	/**
	 * The feature id for the '<em><b>Strict Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM__STRICT_MODE = N4JSPackage.IDENTIFIER_REF__STRICT_MODE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM__ID = N4JSPackage.IDENTIFIER_REF__ID;

	/**
	 * The feature id for the '<em><b>Id As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM__ID_AS_TEXT = N4JSPackage.IDENTIFIER_REF__ID_AS_TEXT;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM__REWIRED_TARGET = N4JSPackage.IDENTIFIER_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Identifier Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM_FEATURE_COUNT = N4JSPackage.IDENTIFIER_REF_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM___GET_VERSION = N4JSPackage.IDENTIFIER_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = N4JSPackage.IDENTIFIER_REF___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = N4JSPackage.IDENTIFIER_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Id IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM___GET_ID_IM = N4JSPackage.IDENTIFIER_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Id IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM___SET_ID_IM__SYMBOLTABLEENTRY = N4JSPackage.IDENTIFIER_REF_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM___GET_ID = N4JSPackage.IDENTIFIER_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Set Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM___SET_ID__IDENTIFIABLEELEMENT = N4JSPackage.IDENTIFIER_REF_OPERATION_COUNT + 4;

	/**
	 * The number of operations of the '<em>Identifier Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_IM_OPERATION_COUNT = N4JSPackage.IDENTIFIER_REF_OPERATION_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedPropertyAccessExpression_IMImpl <em>Parameterized Property Access Expression IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.ParameterizedPropertyAccessExpression_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getParameterizedPropertyAccessExpression_IM()
	 * @generated
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM = 9;

	/**
	 * The feature id for the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__COMPOSED_MEMBER_CACHE = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__TYPE_ARGS = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__TARGET = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TARGET;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__PROPERTY = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY;

	/**
	 * The feature id for the '<em><b>Property As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__PROPERTY_AS_TEXT = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__REWIRED_TARGET = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Any Plus Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__ANY_PLUS_ACCESS = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name Of Any Plus Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__NAME_OF_ANY_PLUS_PROPERTY = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Parameterized Property Access Expression IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM_FEATURE_COUNT = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___IS_PARAMETERIZED = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Property IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY_IM = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Property IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___SET_PROPERTY_IM__SYMBOLTABLEENTRY = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY_NAME = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Property</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Set Property</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___SET_PROPERTY__IDENTIFIABLEELEMENT = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Parameterized Property Access Expression IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM_OPERATION_COUNT = N4JSPackage.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRef_IMImpl <em>Parameterized Type Ref IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRef_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getParameterizedTypeRef_IM()
	 * @generated
	 */
	int PARAMETERIZED_TYPE_REF_IM = 10;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__FOLLOWED_BY_QUESTION_MARK = TypeRefsPackage.PARAMETERIZED_TYPE_REF__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__DYNAMIC = TypeRefsPackage.PARAMETERIZED_TYPE_REF__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__DECLARED_TYPE = TypeRefsPackage.PARAMETERIZED_TYPE_REF__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__TYPE_ARGS = TypeRefsPackage.PARAMETERIZED_TYPE_REF__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__ARRAY_TYPE_EXPRESSION = TypeRefsPackage.PARAMETERIZED_TYPE_REF__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__ITERABLE_TYPE_EXPRESSION = TypeRefsPackage.PARAMETERIZED_TYPE_REF__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__AST_NAMESPACE = TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__AST_NODE_OPTIONAL_FIELD_STRATEGY = TypeRefsPackage.PARAMETERIZED_TYPE_REF__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__DEFINED_TYPING_STRATEGY = TypeRefsPackage.PARAMETERIZED_TYPE_REF__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET = TypeRefsPackage.PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Parameterized Type Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT = TypeRefsPackage.PARAMETERIZED_TYPE_REF_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_FINAL_BY_TYPE = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_ARRAY_LIKE = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_DYNAMIC = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_EXISTENTIAL = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_UPPER_BOUND = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_LOWER_BOUND = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_TYPE_ARGS = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___TO_STRING = TypeRefsPackage.PARAMETERIZED_TYPE_REF___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_TOP_TYPE = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_BOTTOM_TYPE = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_STRUCTURAL_MEMBERS = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_VERSION = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_MODIFIERS_AS_STRING = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_TYPING_STRATEGY = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___CONTAINS_WILDCARDS = TypeRefsPackage.PARAMETERIZED_TYPE_REF___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_TYPE_REF_AS_STRING = TypeRefsPackage.PARAMETERIZED_TYPE_REF___GET_TYPE_REF_AS_STRING;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_PARAMETERIZED = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_GENERIC = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_RAW = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_RAW;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___CONTAINS_UNBOUND_TYPE_VARIABLES = TypeRefsPackage.PARAMETERIZED_TYPE_REF___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_USE_SITE_STRUCTURAL_TYPING = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___IS_DEF_SITE_STRUCTURAL_TYPING = TypeRefsPackage.PARAMETERIZED_TYPE_REF___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = TypeRefsPackage.PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE_IM = TypeRefsPackage.PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY = TypeRefsPackage.PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE = TypeRefsPackage.PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Set Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE__TYPE = TypeRefsPackage.PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 4;

	/**
	 * The number of operations of the '<em>Parameterized Type Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT = TypeRefsPackage.PARAMETERIZED_TYPE_REF_OPERATION_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl <em>Parameterized Type Ref Structural IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getParameterizedTypeRefStructural_IM()
	 * @generated
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM = 11;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__FOLLOWED_BY_QUESTION_MARK = PARAMETERIZED_TYPE_REF_IM__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__DYNAMIC = PARAMETERIZED_TYPE_REF_IM__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__DECLARED_TYPE = PARAMETERIZED_TYPE_REF_IM__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__TYPE_ARGS = PARAMETERIZED_TYPE_REF_IM__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__ARRAY_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF_IM__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__ITERABLE_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF_IM__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_NAMESPACE = PARAMETERIZED_TYPE_REF_IM__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF_IM__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__DEFINED_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF_IM__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REWIRED_TARGET = PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET;

	/**
	 * The feature id for the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Parameterized Type Ref Structural IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM_FEATURE_COUNT = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_FINAL_BY_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_ARRAY_LIKE = PARAMETERIZED_TYPE_REF_IM___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_DYNAMIC = PARAMETERIZED_TYPE_REF_IM___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_EXISTENTIAL = PARAMETERIZED_TYPE_REF_IM___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_UPPER_BOUND = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_LOWER_BOUND = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_ARGS = PARAMETERIZED_TYPE_REF_IM___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___TO_STRING = PARAMETERIZED_TYPE_REF_IM___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_TOP_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_BOTTOM_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF_IM___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_VERSION = PARAMETERIZED_TYPE_REF_IM___GET_VERSION;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_MODIFIERS_AS_STRING = PARAMETERIZED_TYPE_REF_IM___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___CONTAINS_WILDCARDS = PARAMETERIZED_TYPE_REF_IM___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_PARAMETERIZED = PARAMETERIZED_TYPE_REF_IM___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_GENERIC = PARAMETERIZED_TYPE_REF_IM___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_RAW = PARAMETERIZED_TYPE_REF_IM___IS_RAW;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___CONTAINS_UNBOUND_TYPE_VARIABLES = PARAMETERIZED_TYPE_REF_IM___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_USE_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF_IM___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_DEF_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF_IM___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = PARAMETERIZED_TYPE_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET;

	/**
	 * The operation id for the '<em>Has Postponed Substitution For</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Set Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_REF_AS_STRING = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Get Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE_IM = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Set Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Set Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE__TYPE = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 11;

	/**
	 * The number of operations of the '<em>Parameterized Type Ref Structural IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_TYPE_REF_STRUCTURAL_IM_OPERATION_COUNT = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.SnippetImpl <em>Snippet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.SnippetImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSnippet()
	 * @generated
	 */
	int SNIPPET = 12;

	/**
	 * The feature id for the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET__CODE = N4JSPackage.EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Snippet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_FEATURE_COUNT = N4JSPackage.EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = N4JSPackage.EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Snippet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SNIPPET_OPERATION_COUNT = N4JSPackage.EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl <em>Delegating Member</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingMember()
	 * @generated
	 */
	int DELEGATING_MEMBER = 13;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER__DECLARED_MODIFIERS = N4JSPackage.N4_MEMBER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER__OWNER = N4JSPackage.N4_MEMBER_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Delegation Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER__DELEGATION_BASE_TYPE = N4JSPackage.N4_MEMBER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delegation Super Class Steps</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER__DELEGATION_SUPER_CLASS_STEPS = N4JSPackage.N4_MEMBER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Delegation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER__DELEGATION_TARGET = N4JSPackage.N4_MEMBER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Delegation Target Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER__DELEGATION_TARGET_IS_ABSTRACT = N4JSPackage.N4_MEMBER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Delegating Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER_FEATURE_COUNT = N4JSPackage.N4_MEMBER_DECLARATION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___GET_ANNOTATIONS = N4JSPackage.N4_MEMBER_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___GET_ALL_ANNOTATIONS = N4JSPackage.N4_MEMBER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___GET_DECLARED_TYPE_REF = N4JSPackage.N4_MEMBER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___GET_NAME = N4JSPackage.N4_MEMBER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___GET_DEFINED_TYPE_ELEMENT = N4JSPackage.N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___IS_DECLARED_STATIC = N4JSPackage.N4_MEMBER_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___IS_STATIC = N4JSPackage.N4_MEMBER_DECLARATION___IS_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___IS_DECLARED_FINAL = N4JSPackage.N4_MEMBER_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___IS_FINAL = N4JSPackage.N4_MEMBER_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___IS_CONSTRUCTOR = N4JSPackage.N4_MEMBER_DECLARATION___IS_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER___IS_CALLABLE_CONSTRUCTOR = N4JSPackage.N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR;

	/**
	 * The number of operations of the '<em>Delegating Member</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_MEMBER_OPERATION_COUNT = N4JSPackage.N4_MEMBER_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingGetterDeclarationImpl <em>Delegating Getter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingGetterDeclarationImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingGetterDeclaration()
	 * @generated
	 */
	int DELEGATING_GETTER_DECLARATION = 14;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__BODY = N4JSPackage.N4_GETTER_DECLARATION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__LOK = N4JSPackage.N4_GETTER_DECLARATION__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DECLARED_NAME = N4JSPackage.N4_GETTER_DECLARATION__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DECLARED_OPTIONAL = N4JSPackage.N4_GETTER_DECLARATION__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DECLARED_TYPE_REF = N4JSPackage.N4_GETTER_DECLARATION__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Bogus Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__BOGUS_TYPE_REF = N4JSPackage.N4_GETTER_DECLARATION__BOGUS_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Defined Getter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DEFINED_GETTER = N4JSPackage.N4_GETTER_DECLARATION__DEFINED_GETTER;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DECLARED_MODIFIERS = N4JSPackage.N4_GETTER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__OWNER = N4JSPackage.N4_GETTER_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__ANNOTATION_LIST = N4JSPackage.N4_GETTER_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Delegation Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DELEGATION_BASE_TYPE = N4JSPackage.N4_GETTER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delegation Super Class Steps</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DELEGATION_SUPER_CLASS_STEPS = N4JSPackage.N4_GETTER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Delegation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DELEGATION_TARGET = N4JSPackage.N4_GETTER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Delegation Target Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION__DELEGATION_TARGET_IS_ABSTRACT = N4JSPackage.N4_GETTER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Delegating Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION_FEATURE_COUNT = N4JSPackage.N4_GETTER_DECLARATION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_ALL_ANNOTATIONS = N4JSPackage.N4_GETTER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = N4JSPackage.N4_GETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = N4JSPackage.N4_GETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = N4JSPackage.N4_GETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_ASYNC = N4JSPackage.N4_GETTER_DECLARATION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = N4JSPackage.N4_GETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_NAME = N4JSPackage.N4_GETTER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = N4JSPackage.N4_GETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_DECLARED_TYPE_REF = N4JSPackage.N4_GETTER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_OPTIONAL = N4JSPackage.N4_GETTER_DECLARATION___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_DEFINED_ACCESSOR = N4JSPackage.N4_GETTER_DECLARATION___GET_DEFINED_ACCESSOR;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_DECLARED_STATIC = N4JSPackage.N4_GETTER_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_STATIC = N4JSPackage.N4_GETTER_DECLARATION___IS_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_DECLARED_FINAL = N4JSPackage.N4_GETTER_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_FINAL = N4JSPackage.N4_GETTER_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_CONSTRUCTOR = N4JSPackage.N4_GETTER_DECLARATION___IS_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_CALLABLE_CONSTRUCTOR = N4JSPackage.N4_GETTER_DECLARATION___IS_CALLABLE_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_ANNOTATIONS = N4JSPackage.N4_GETTER_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_ABSTRACT = N4JSPackage.N4_GETTER_DECLARATION___IS_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___IS_VALID_NAME = N4JSPackage.N4_GETTER_DECLARATION___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = N4JSPackage.N4_GETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT;

	/**
	 * The number of operations of the '<em>Delegating Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_GETTER_DECLARATION_OPERATION_COUNT = N4JSPackage.N4_GETTER_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingSetterDeclarationImpl <em>Delegating Setter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingSetterDeclarationImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingSetterDeclaration()
	 * @generated
	 */
	int DELEGATING_SETTER_DECLARATION = 15;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__BODY = N4JSPackage.N4_SETTER_DECLARATION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__LOK = N4JSPackage.N4_SETTER_DECLARATION__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DECLARED_NAME = N4JSPackage.N4_SETTER_DECLARATION__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DECLARED_OPTIONAL = N4JSPackage.N4_SETTER_DECLARATION__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Defined Setter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DEFINED_SETTER = N4JSPackage.N4_SETTER_DECLARATION__DEFINED_SETTER;

	/**
	 * The feature id for the '<em><b>Fpar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__FPAR = N4JSPackage.N4_SETTER_DECLARATION__FPAR;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DECLARED_MODIFIERS = N4JSPackage.N4_SETTER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__OWNER = N4JSPackage.N4_SETTER_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__ANNOTATION_LIST = N4JSPackage.N4_SETTER_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Delegation Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DELEGATION_BASE_TYPE = N4JSPackage.N4_SETTER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delegation Super Class Steps</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DELEGATION_SUPER_CLASS_STEPS = N4JSPackage.N4_SETTER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Delegation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DELEGATION_TARGET = N4JSPackage.N4_SETTER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Delegation Target Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION__DELEGATION_TARGET_IS_ABSTRACT = N4JSPackage.N4_SETTER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Delegating Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION_FEATURE_COUNT = N4JSPackage.N4_SETTER_DECLARATION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_ALL_ANNOTATIONS = N4JSPackage.N4_SETTER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = N4JSPackage.N4_SETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = N4JSPackage.N4_SETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = N4JSPackage.N4_SETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_ASYNC = N4JSPackage.N4_SETTER_DECLARATION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = N4JSPackage.N4_SETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_NAME = N4JSPackage.N4_SETTER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = N4JSPackage.N4_SETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_OPTIONAL = N4JSPackage.N4_SETTER_DECLARATION___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_DEFINED_ACCESSOR = N4JSPackage.N4_SETTER_DECLARATION___GET_DEFINED_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_DECLARED_TYPE_REF = N4JSPackage.N4_SETTER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_DECLARED_STATIC = N4JSPackage.N4_SETTER_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_STATIC = N4JSPackage.N4_SETTER_DECLARATION___IS_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_DECLARED_FINAL = N4JSPackage.N4_SETTER_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_FINAL = N4JSPackage.N4_SETTER_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_CONSTRUCTOR = N4JSPackage.N4_SETTER_DECLARATION___IS_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_CALLABLE_CONSTRUCTOR = N4JSPackage.N4_SETTER_DECLARATION___IS_CALLABLE_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_ANNOTATIONS = N4JSPackage.N4_SETTER_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_ABSTRACT = N4JSPackage.N4_SETTER_DECLARATION___IS_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___IS_VALID_NAME = N4JSPackage.N4_SETTER_DECLARATION___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = N4JSPackage.N4_SETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT;

	/**
	 * The number of operations of the '<em>Delegating Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_SETTER_DECLARATION_OPERATION_COUNT = N4JSPackage.N4_SETTER_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMethodDeclarationImpl <em>Delegating Method Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingMethodDeclarationImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingMethodDeclaration()
	 * @generated
	 */
	int DELEGATING_METHOD_DECLARATION = 16;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DECLARED_MODIFIERS = N4JSPackage.N4_METHOD_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__OWNER = N4JSPackage.N4_METHOD_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__ANNOTATION_LIST = N4JSPackage.N4_METHOD_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__BODY = N4JSPackage.N4_METHOD_DECLARATION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__LOK = N4JSPackage.N4_METHOD_DECLARATION__LOK;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DEFINED_TYPE = N4JSPackage.N4_METHOD_DECLARATION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DECLARED_VERSION = N4JSPackage.N4_METHOD_DECLARATION__DECLARED_VERSION;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__FPARS = N4JSPackage.N4_METHOD_DECLARATION__FPARS;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__RETURN_TYPE_REF = N4JSPackage.N4_METHOD_DECLARATION__RETURN_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__GENERATOR = N4JSPackage.N4_METHOD_DECLARATION__GENERATOR;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DECLARED_ASYNC = N4JSPackage.N4_METHOD_DECLARATION__DECLARED_ASYNC;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__TYPE_VARS = N4JSPackage.N4_METHOD_DECLARATION__TYPE_VARS;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DECLARED_TYPE_REF = N4JSPackage.N4_METHOD_DECLARATION__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Bogus Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__BOGUS_TYPE_REF = N4JSPackage.N4_METHOD_DECLARATION__BOGUS_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DECLARED_NAME = N4JSPackage.N4_METHOD_DECLARATION__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Delegation Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DELEGATION_BASE_TYPE = N4JSPackage.N4_METHOD_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Delegation Super Class Steps</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DELEGATION_SUPER_CLASS_STEPS = N4JSPackage.N4_METHOD_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Delegation Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DELEGATION_TARGET = N4JSPackage.N4_METHOD_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Delegation Target Is Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION__DELEGATION_TARGET_IS_ABSTRACT = N4JSPackage.N4_METHOD_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Delegating Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION_FEATURE_COUNT = N4JSPackage.N4_METHOD_DECLARATION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_ALL_ANNOTATIONS = N4JSPackage.N4_METHOD_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_DECLARED_TYPE_REF = N4JSPackage.N4_METHOD_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_DECLARED_STATIC = N4JSPackage.N4_METHOD_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_DECLARED_FINAL = N4JSPackage.N4_METHOD_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_FINAL = N4JSPackage.N4_METHOD_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_ANNOTATIONS = N4JSPackage.N4_METHOD_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = N4JSPackage.N4_METHOD_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = N4JSPackage.N4_METHOD_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___HAS_DECLARED_VERSION = N4JSPackage.N4_METHOD_DECLARATION___HAS_DECLARED_VERSION;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = N4JSPackage.N4_METHOD_DECLARATION___GET_DECLARED_VERSION_OR_ZERO;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL = N4JSPackage.N4_METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_ASYNC = N4JSPackage.N4_METHOD_DECLARATION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_DEFINED_FUNCTION = N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_FUNCTION;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_NAME = N4JSPackage.N4_METHOD_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = N4JSPackage.N4_METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Exists Explicit Super Call</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL = N4JSPackage.N4_METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT = N4JSPackage.N4_METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_ABSTRACT = N4JSPackage.N4_METHOD_DECLARATION___IS_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_CONSTRUCTOR = N4JSPackage.N4_METHOD_DECLARATION___IS_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_CALLABLE_CONSTRUCTOR = N4JSPackage.N4_METHOD_DECLARATION___IS_CALLABLE_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_STATIC = N4JSPackage.N4_METHOD_DECLARATION___IS_STATIC;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION___IS_VALID_NAME = N4JSPackage.N4_METHOD_DECLARATION___IS_VALID_NAME;

	/**
	 * The number of operations of the '<em>Delegating Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATING_METHOD_DECLARATION_OPERATION_COUNT = N4JSPackage.N4_METHOD_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.StringLiteralForSTEImpl <em>String Literal For STE</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.StringLiteralForSTEImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getStringLiteralForSTE()
	 * @generated
	 */
	int STRING_LITERAL_FOR_STE = 17;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE__VALUE = N4JSPackage.STRING_LITERAL__VALUE;

	/**
	 * The feature id for the '<em><b>Raw Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE__RAW_VALUE = N4JSPackage.STRING_LITERAL__RAW_VALUE;

	/**
	 * The feature id for the '<em><b>Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE__ENTRY = N4JSPackage.STRING_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Use Exported Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE__USE_EXPORTED_NAME = N4JSPackage.STRING_LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>String Literal For STE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE_FEATURE_COUNT = N4JSPackage.STRING_LITERAL_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = N4JSPackage.STRING_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE___GET_VALUE_AS_STRING = N4JSPackage.STRING_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>String Literal For STE</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FOR_STE_OPERATION_COUNT = N4JSPackage.STRING_LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRef_IMImpl <em>Versioned Parameterized Type Ref IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRef_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedParameterizedTypeRef_IM()
	 * @generated
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM = 18;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__FOLLOWED_BY_QUESTION_MARK = PARAMETERIZED_TYPE_REF_IM__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__DYNAMIC = PARAMETERIZED_TYPE_REF_IM__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__DECLARED_TYPE = PARAMETERIZED_TYPE_REF_IM__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__TYPE_ARGS = PARAMETERIZED_TYPE_REF_IM__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__ARRAY_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF_IM__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__ITERABLE_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF_IM__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__AST_NAMESPACE = PARAMETERIZED_TYPE_REF_IM__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF_IM__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__DEFINED_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF_IM__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET = PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM__REQUESTED_VERSION = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Versioned Parameterized Type Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_FINAL_BY_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_ARRAY_LIKE = PARAMETERIZED_TYPE_REF_IM___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_DYNAMIC = PARAMETERIZED_TYPE_REF_IM___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_EXISTENTIAL = PARAMETERIZED_TYPE_REF_IM___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_UPPER_BOUND = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_LOWER_BOUND = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_TYPE_ARGS = PARAMETERIZED_TYPE_REF_IM___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___TO_STRING = PARAMETERIZED_TYPE_REF_IM___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_TOP_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_BOTTOM_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_IM___GET_STRUCTURAL_MEMBERS;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF_IM___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_MODIFIERS_AS_STRING = PARAMETERIZED_TYPE_REF_IM___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF_IM___GET_TYPING_STRATEGY;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___CONTAINS_WILDCARDS = PARAMETERIZED_TYPE_REF_IM___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_TYPE_REF_AS_STRING = PARAMETERIZED_TYPE_REF_IM___GET_TYPE_REF_AS_STRING;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_PARAMETERIZED = PARAMETERIZED_TYPE_REF_IM___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_GENERIC = PARAMETERIZED_TYPE_REF_IM___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_RAW = PARAMETERIZED_TYPE_REF_IM___IS_RAW;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___CONTAINS_UNBOUND_TYPE_VARIABLES = PARAMETERIZED_TYPE_REF_IM___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_USE_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF_IM___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___IS_DEF_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF_IM___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = PARAMETERIZED_TYPE_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET;

	/**
	 * The operation id for the '<em>Get Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE_IM = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE_IM;

	/**
	 * The operation id for the '<em>Set Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY = PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Set Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE__TYPE = PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE__TYPE;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___HAS_REQUESTED_VERSION = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_REQUESTED_VERSION_OR_ZERO = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM___GET_VERSION = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Versioned Parameterized Type Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl <em>Versioned Parameterized Type Ref Structural IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedParameterizedTypeRefStructural_IM()
	 * @generated
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM = 19;

	/**
	 * The feature id for the '<em><b>Followed By Question Mark</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__FOLLOWED_BY_QUESTION_MARK = PARAMETERIZED_TYPE_REF_IM__FOLLOWED_BY_QUESTION_MARK;

	/**
	 * The feature id for the '<em><b>Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__DYNAMIC = PARAMETERIZED_TYPE_REF_IM__DYNAMIC;

	/**
	 * The feature id for the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__DECLARED_TYPE = PARAMETERIZED_TYPE_REF_IM__DECLARED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__TYPE_ARGS = PARAMETERIZED_TYPE_REF_IM__TYPE_ARGS;

	/**
	 * The feature id for the '<em><b>Array Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__ARRAY_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF_IM__ARRAY_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Iterable Type Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__ITERABLE_TYPE_EXPRESSION = PARAMETERIZED_TYPE_REF_IM__ITERABLE_TYPE_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Ast Namespace</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_NAMESPACE = PARAMETERIZED_TYPE_REF_IM__AST_NAMESPACE;

	/**
	 * The feature id for the '<em><b>AST Node Optional Field Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF_IM__AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The feature id for the '<em><b>Defined Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__DEFINED_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF_IM__DEFINED_TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REWIRED_TARGET = PARAMETERIZED_TYPE_REF_IM__REWIRED_TARGET;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__REQUESTED_VERSION = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ast Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__AST_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Structural Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__STRUCTURAL_TYPE = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Gen Structural Members</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__GEN_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Postponed Substitutions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM__POSTPONED_SUBSTITUTIONS = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Versioned Parameterized Type Ref Structural IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM_FEATURE_COUNT = PARAMETERIZED_TYPE_REF_IM_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Final By Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_FINAL_BY_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_FINAL_BY_TYPE;

	/**
	 * The operation id for the '<em>Is Array Like</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_ARRAY_LIKE = PARAMETERIZED_TYPE_REF_IM___IS_ARRAY_LIKE;

	/**
	 * The operation id for the '<em>Is Dynamic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_DYNAMIC = PARAMETERIZED_TYPE_REF_IM___IS_DYNAMIC;

	/**
	 * The operation id for the '<em>Is Existential</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_EXISTENTIAL = PARAMETERIZED_TYPE_REF_IM___IS_EXISTENTIAL;

	/**
	 * The operation id for the '<em>Get Declared Upper Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_UPPER_BOUND = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_UPPER_BOUND;

	/**
	 * The operation id for the '<em>Get Declared Lower Bound</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_LOWER_BOUND = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_LOWER_BOUND;

	/**
	 * The operation id for the '<em>Get Type Args</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_ARGS = PARAMETERIZED_TYPE_REF_IM___GET_TYPE_ARGS;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___TO_STRING = PARAMETERIZED_TYPE_REF_IM___TO_STRING;

	/**
	 * The operation id for the '<em>Is Top Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_TOP_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_TOP_TYPE;

	/**
	 * The operation id for the '<em>Is Bottom Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_BOTTOM_TYPE = PARAMETERIZED_TYPE_REF_IM___IS_BOTTOM_TYPE;

	/**
	 * The operation id for the '<em>Get AST Node Optional Field Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY = PARAMETERIZED_TYPE_REF_IM___GET_AST_NODE_OPTIONAL_FIELD_STRATEGY;

	/**
	 * The operation id for the '<em>Get Modifiers As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_MODIFIERS_AS_STRING = PARAMETERIZED_TYPE_REF_IM___GET_MODIFIERS_AS_STRING;

	/**
	 * The operation id for the '<em>Contains Wildcards</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___CONTAINS_WILDCARDS = PARAMETERIZED_TYPE_REF_IM___CONTAINS_WILDCARDS;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_PARAMETERIZED = PARAMETERIZED_TYPE_REF_IM___IS_PARAMETERIZED;

	/**
	 * The operation id for the '<em>Is Generic</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_GENERIC = PARAMETERIZED_TYPE_REF_IM___IS_GENERIC;

	/**
	 * The operation id for the '<em>Is Raw</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_RAW = PARAMETERIZED_TYPE_REF_IM___IS_RAW;

	/**
	 * The operation id for the '<em>Contains Unbound Type Variables</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___CONTAINS_UNBOUND_TYPE_VARIABLES = PARAMETERIZED_TYPE_REF_IM___CONTAINS_UNBOUND_TYPE_VARIABLES;

	/**
	 * The operation id for the '<em>Is Use Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_USE_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF_IM___IS_USE_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Is Def Site Structural Typing</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___IS_DEF_SITE_STRUCTURAL_TYPING = PARAMETERIZED_TYPE_REF_IM___IS_DEF_SITE_STRUCTURAL_TYPING;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = PARAMETERIZED_TYPE_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET;

	/**
	 * The operation id for the '<em>Get Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE_IM = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE_IM;

	/**
	 * The operation id for the '<em>Set Declared Type IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY = PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY;

	/**
	 * The operation id for the '<em>Get Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE = PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE;

	/**
	 * The operation id for the '<em>Set Declared Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE__TYPE = PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE__TYPE;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_REQUESTED_VERSION = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_REQUESTED_VERSION_OR_ZERO = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_VERSION = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Has Postponed Substitution For</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___HAS_POSTPONED_SUBSTITUTION_FOR__TYPEVARIABLE = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPING_STRATEGY = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Set Typing Strategy</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_TYPING_STRATEGY__TYPINGSTRATEGY = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Structural Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_STRUCTURAL_MEMBERS = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Type Ref As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_TYPE_REF_AS_STRING = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 10;

	/**
	 * The number of operations of the '<em>Versioned Parameterized Type Ref Structural IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM_OPERATION_COUNT = PARAMETERIZED_TYPE_REF_IM_OPERATION_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedIdentifierRef_IMImpl <em>Versioned Identifier Ref IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.VersionedIdentifierRef_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedIdentifierRef_IM()
	 * @generated
	 */
	int VERSIONED_IDENTIFIER_REF_IM = 20;

	/**
	 * The feature id for the '<em><b>Strict Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM__STRICT_MODE = IDENTIFIER_REF_IM__STRICT_MODE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM__ID = IDENTIFIER_REF_IM__ID;

	/**
	 * The feature id for the '<em><b>Id As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM__ID_AS_TEXT = IDENTIFIER_REF_IM__ID_AS_TEXT;

	/**
	 * The feature id for the '<em><b>Rewired Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM__REWIRED_TARGET = IDENTIFIER_REF_IM__REWIRED_TARGET;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM__REQUESTED_VERSION = IDENTIFIER_REF_IM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Versioned Identifier Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM_FEATURE_COUNT = IDENTIFIER_REF_IM_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = IDENTIFIER_REF_IM___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = IDENTIFIER_REF_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET;

	/**
	 * The operation id for the '<em>Get Id IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___GET_ID_IM = IDENTIFIER_REF_IM___GET_ID_IM;

	/**
	 * The operation id for the '<em>Set Id IM</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___SET_ID_IM__SYMBOLTABLEENTRY = IDENTIFIER_REF_IM___SET_ID_IM__SYMBOLTABLEENTRY;

	/**
	 * The operation id for the '<em>Get Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___GET_ID = IDENTIFIER_REF_IM___GET_ID;

	/**
	 * The operation id for the '<em>Set Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___SET_ID__IDENTIFIABLEELEMENT = IDENTIFIER_REF_IM___SET_ID__IDENTIFIABLEELEMENT;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___HAS_REQUESTED_VERSION = IDENTIFIER_REF_IM_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___GET_REQUESTED_VERSION_OR_ZERO = IDENTIFIER_REF_IM_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM___GET_VERSION = IDENTIFIER_REF_IM_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Versioned Identifier Ref IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_IM_OPERATION_COUNT = IDENTIFIER_REF_IM_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedNamedImportSpecifier_IMImpl <em>Versioned Named Import Specifier IM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.transpiler.im.impl.VersionedNamedImportSpecifier_IMImpl
	 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedNamedImportSpecifier_IM()
	 * @generated
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM = 21;

	/**
	 * The feature id for the '<em><b>Flagged Used In Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM__FLAGGED_USED_IN_CODE = N4JSPackage.NAMED_IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE;

	/**
	 * The feature id for the '<em><b>Imported Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_ELEMENT = N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Imported Element As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_ELEMENT_AS_TEXT = N4JSPackage.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM__ALIAS = N4JSPackage.NAMED_IMPORT_SPECIFIER__ALIAS;

	/**
	 * The feature id for the '<em><b>Imported Type Versions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_TYPE_VERSIONS = N4JSPackage.NAMED_IMPORT_SPECIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Versioned Type Import</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM__VERSIONED_TYPE_IMPORT = N4JSPackage.NAMED_IMPORT_SPECIFIER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Versioned Named Import Specifier IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM_FEATURE_COUNT = N4JSPackage.NAMED_IMPORT_SPECIFIER_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Default Import</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM___IS_DEFAULT_IMPORT = N4JSPackage.NAMED_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT;

	/**
	 * The number of operations of the '<em>Versioned Named Import Specifier IM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_NAMED_IMPORT_SPECIFIER_IM_OPERATION_COUNT = N4JSPackage.NAMED_IMPORT_SPECIFIER_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.Script_IM <em>Script IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Script IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.Script_IM
	 * @generated
	 */
	EClass getScript_IM();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.transpiler.im.Script_IM#getSymbolTable <em>Symbol Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symbol Table</em>'.
	 * @see org.eclipse.n4js.transpiler.im.Script_IM#getSymbolTable()
	 * @see #getScript_IM()
	 * @generated
	 */
	EReference getScript_IM_SymbolTable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.SymbolTable <em>Symbol Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol Table</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTable
	 * @generated
	 */
	EClass getSymbolTable();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.transpiler.im.SymbolTable#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTable#getEntries()
	 * @see #getSymbolTable()
	 * @generated
	 */
	EReference getSymbolTable_Entries();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry <em>Symbol Table Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol Table Entry</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntry
	 * @generated
	 */
	EClass getSymbolTableEntry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntry#getName()
	 * @see #getSymbolTableEntry()
	 * @generated
	 */
	EAttribute getSymbolTableEntry_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry#getElementsOfThisName <em>Elements Of This Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Elements Of This Name</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntry#getElementsOfThisName()
	 * @see #getSymbolTableEntry()
	 * @generated
	 */
	EReference getSymbolTableEntry_ElementsOfThisName();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntry#getReferencingElements <em>Referencing Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Referencing Elements</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntry#getReferencingElements()
	 * @see #getSymbolTableEntry()
	 * @generated
	 */
	EReference getSymbolTableEntry_ReferencingElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal <em>Symbol Table Entry Original</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol Table Entry Original</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
	 * @generated
	 */
	EClass getSymbolTableEntryOriginal();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getOriginalTarget <em>Original Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Original Target</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getOriginalTarget()
	 * @see #getSymbolTableEntryOriginal()
	 * @generated
	 */
	EReference getSymbolTableEntryOriginal_OriginalTarget();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getImportSpecifier <em>Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Import Specifier</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getImportSpecifier()
	 * @see #getSymbolTableEntryOriginal()
	 * @generated
	 */
	EReference getSymbolTableEntryOriginal_ImportSpecifier();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getExportedName() <em>Get Exported Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Exported Name</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal#getExportedName()
	 * @generated
	 */
	EOperation getSymbolTableEntryOriginal__GetExportedName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryIMOnly <em>Symbol Table Entry IM Only</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol Table Entry IM Only</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntryIMOnly
	 * @generated
	 */
	EClass getSymbolTableEntryIMOnly();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal <em>Symbol Table Entry Internal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbol Table Entry Internal</em>'.
	 * @see org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal
	 * @generated
	 */
	EClass getSymbolTableEntryInternal();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.ReferencingElement_IM <em>Referencing Element IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Referencing Element IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ReferencingElement_IM
	 * @generated
	 */
	EClass getReferencingElement_IM();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.transpiler.im.ReferencingElement_IM#getRewiredTarget <em>Rewired Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rewired Target</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ReferencingElement_IM#getRewiredTarget()
	 * @see #getReferencingElement_IM()
	 * @generated
	 */
	EReference getReferencingElement_IM_RewiredTarget();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ReferencingElement_IM#getOriginalTargetOfRewiredTarget() <em>Get Original Target Of Rewired Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Original Target Of Rewired Target</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ReferencingElement_IM#getOriginalTargetOfRewiredTarget()
	 * @generated
	 */
	EOperation getReferencingElement_IM__GetOriginalTargetOfRewiredTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.ReferencingElementExpression_IM <em>Referencing Element Expression IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Referencing Element Expression IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ReferencingElementExpression_IM
	 * @generated
	 */
	EClass getReferencingElementExpression_IM();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.IdentifierRef_IM <em>Identifier Ref IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifier Ref IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.IdentifierRef_IM
	 * @generated
	 */
	EClass getIdentifierRef_IM();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.IdentifierRef_IM#getId_IM() <em>Get Id IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Id IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.IdentifierRef_IM#getId_IM()
	 * @generated
	 */
	EOperation getIdentifierRef_IM__GetId_IM();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.IdentifierRef_IM#setId_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry) <em>Set Id IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Id IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.IdentifierRef_IM#setId_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry)
	 * @generated
	 */
	EOperation getIdentifierRef_IM__SetId_IM__SymbolTableEntry();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.IdentifierRef_IM#getId() <em>Get Id</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Id</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.IdentifierRef_IM#getId()
	 * @generated
	 */
	EOperation getIdentifierRef_IM__GetId();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.IdentifierRef_IM#setId(org.eclipse.n4js.ts.types.IdentifiableElement) <em>Set Id</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Id</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.IdentifierRef_IM#setId(org.eclipse.n4js.ts.types.IdentifiableElement)
	 * @generated
	 */
	EOperation getIdentifierRef_IM__SetId__IdentifiableElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM <em>Parameterized Property Access Expression IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Property Access Expression IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
	 * @generated
	 */
	EClass getParameterizedPropertyAccessExpression_IM();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#isAnyPlusAccess <em>Any Plus Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Any Plus Access</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#isAnyPlusAccess()
	 * @see #getParameterizedPropertyAccessExpression_IM()
	 * @generated
	 */
	EAttribute getParameterizedPropertyAccessExpression_IM_AnyPlusAccess();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getNameOfAnyPlusProperty <em>Name Of Any Plus Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name Of Any Plus Property</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getNameOfAnyPlusProperty()
	 * @see #getParameterizedPropertyAccessExpression_IM()
	 * @generated
	 */
	EAttribute getParameterizedPropertyAccessExpression_IM_NameOfAnyPlusProperty();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getProperty_IM() <em>Get Property IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Property IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getProperty_IM()
	 * @generated
	 */
	EOperation getParameterizedPropertyAccessExpression_IM__GetProperty_IM();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#setProperty_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry) <em>Set Property IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Property IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#setProperty_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry)
	 * @generated
	 */
	EOperation getParameterizedPropertyAccessExpression_IM__SetProperty_IM__SymbolTableEntry();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getPropertyName() <em>Get Property Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Property Name</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getPropertyName()
	 * @generated
	 */
	EOperation getParameterizedPropertyAccessExpression_IM__GetPropertyName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getProperty() <em>Get Property</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Property</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#getProperty()
	 * @generated
	 */
	EOperation getParameterizedPropertyAccessExpression_IM__GetProperty();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#setProperty(org.eclipse.n4js.ts.types.IdentifiableElement) <em>Set Property</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Property</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM#setProperty(org.eclipse.n4js.ts.types.IdentifiableElement)
	 * @generated
	 */
	EOperation getParameterizedPropertyAccessExpression_IM__SetProperty__IdentifiableElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM <em>Parameterized Type Ref IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Type Ref IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM
	 * @generated
	 */
	EClass getParameterizedTypeRef_IM();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#getDeclaredType_IM() <em>Get Declared Type IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#getDeclaredType_IM()
	 * @generated
	 */
	EOperation getParameterizedTypeRef_IM__GetDeclaredType_IM();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#setDeclaredType_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry) <em>Set Declared Type IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Declared Type IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#setDeclaredType_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry)
	 * @generated
	 */
	EOperation getParameterizedTypeRef_IM__SetDeclaredType_IM__SymbolTableEntry();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#getDeclaredType() <em>Get Declared Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#getDeclaredType()
	 * @generated
	 */
	EOperation getParameterizedTypeRef_IM__GetDeclaredType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#setDeclaredType(org.eclipse.n4js.ts.types.Type) <em>Set Declared Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Declared Type</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRef_IM#setDeclaredType(org.eclipse.n4js.ts.types.Type)
	 * @generated
	 */
	EOperation getParameterizedTypeRef_IM__SetDeclaredType__Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM <em>Parameterized Type Ref Structural IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Type Ref Structural IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM
	 * @generated
	 */
	EClass getParameterizedTypeRefStructural_IM();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#getDeclaredType_IM() <em>Get Declared Type IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#getDeclaredType_IM()
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural_IM__GetDeclaredType_IM();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#setDeclaredType_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry) <em>Set Declared Type IM</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Declared Type IM</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#setDeclaredType_IM(org.eclipse.n4js.transpiler.im.SymbolTableEntry)
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural_IM__SetDeclaredType_IM__SymbolTableEntry();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#getDeclaredType() <em>Get Declared Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#getDeclaredType()
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural_IM__GetDeclaredType();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#setDeclaredType(org.eclipse.n4js.ts.types.Type) <em>Set Declared Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Declared Type</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.ParameterizedTypeRefStructural_IM#setDeclaredType(org.eclipse.n4js.ts.types.Type)
	 * @generated
	 */
	EOperation getParameterizedTypeRefStructural_IM__SetDeclaredType__Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.Snippet <em>Snippet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Snippet</em>'.
	 * @see org.eclipse.n4js.transpiler.im.Snippet
	 * @generated
	 */
	EClass getSnippet();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.Snippet#getCode <em>Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code</em>'.
	 * @see org.eclipse.n4js.transpiler.im.Snippet#getCode()
	 * @see #getSnippet()
	 * @generated
	 */
	EAttribute getSnippet_Code();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.DelegatingMember <em>Delegating Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delegating Member</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingMember
	 * @generated
	 */
	EClass getDelegatingMember();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationBaseType <em>Delegation Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Delegation Base Type</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationBaseType()
	 * @see #getDelegatingMember()
	 * @generated
	 */
	EReference getDelegatingMember_DelegationBaseType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationSuperClassSteps <em>Delegation Super Class Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delegation Super Class Steps</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationSuperClassSteps()
	 * @see #getDelegatingMember()
	 * @generated
	 */
	EAttribute getDelegatingMember_DelegationSuperClassSteps();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationTarget <em>Delegation Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Delegation Target</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingMember#getDelegationTarget()
	 * @see #getDelegatingMember()
	 * @generated
	 */
	EReference getDelegatingMember_DelegationTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.DelegatingMember#isDelegationTargetIsAbstract <em>Delegation Target Is Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Delegation Target Is Abstract</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingMember#isDelegationTargetIsAbstract()
	 * @see #getDelegatingMember()
	 * @generated
	 */
	EAttribute getDelegatingMember_DelegationTargetIsAbstract();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.DelegatingGetterDeclaration <em>Delegating Getter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delegating Getter Declaration</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingGetterDeclaration
	 * @generated
	 */
	EClass getDelegatingGetterDeclaration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.DelegatingSetterDeclaration <em>Delegating Setter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delegating Setter Declaration</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingSetterDeclaration
	 * @generated
	 */
	EClass getDelegatingSetterDeclaration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.DelegatingMethodDeclaration <em>Delegating Method Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delegating Method Declaration</em>'.
	 * @see org.eclipse.n4js.transpiler.im.DelegatingMethodDeclaration
	 * @generated
	 */
	EClass getDelegatingMethodDeclaration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE <em>String Literal For STE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Literal For STE</em>'.
	 * @see org.eclipse.n4js.transpiler.im.StringLiteralForSTE
	 * @generated
	 */
	EClass getStringLiteralForSTE();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE#getEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entry</em>'.
	 * @see org.eclipse.n4js.transpiler.im.StringLiteralForSTE#getEntry()
	 * @see #getStringLiteralForSTE()
	 * @generated
	 */
	EReference getStringLiteralForSTE_Entry();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE#isUseExportedName <em>Use Exported Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Exported Name</em>'.
	 * @see org.eclipse.n4js.transpiler.im.StringLiteralForSTE#isUseExportedName()
	 * @see #getStringLiteralForSTE()
	 * @generated
	 */
	EAttribute getStringLiteralForSTE_UseExportedName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.transpiler.im.StringLiteralForSTE#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.transpiler.im.StringLiteralForSTE#getValueAsString()
	 * @generated
	 */
	EOperation getStringLiteralForSTE__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.VersionedParameterizedTypeRef_IM <em>Versioned Parameterized Type Ref IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Parameterized Type Ref IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.VersionedParameterizedTypeRef_IM
	 * @generated
	 */
	EClass getVersionedParameterizedTypeRef_IM();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.VersionedParameterizedTypeRefStructural_IM <em>Versioned Parameterized Type Ref Structural IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Parameterized Type Ref Structural IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.VersionedParameterizedTypeRefStructural_IM
	 * @generated
	 */
	EClass getVersionedParameterizedTypeRefStructural_IM();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.VersionedIdentifierRef_IM <em>Versioned Identifier Ref IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Identifier Ref IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.VersionedIdentifierRef_IM
	 * @generated
	 */
	EClass getVersionedIdentifierRef_IM();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM <em>Versioned Named Import Specifier IM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Named Import Specifier IM</em>'.
	 * @see org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM
	 * @generated
	 */
	EClass getVersionedNamedImportSpecifier_IM();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM#getImportedTypeVersions <em>Imported Type Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Imported Type Versions</em>'.
	 * @see org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM#getImportedTypeVersions()
	 * @see #getVersionedNamedImportSpecifier_IM()
	 * @generated
	 */
	EReference getVersionedNamedImportSpecifier_IM_ImportedTypeVersions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM#isVersionedTypeImport <em>Versioned Type Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Versioned Type Import</em>'.
	 * @see org.eclipse.n4js.transpiler.im.VersionedNamedImportSpecifier_IM#isVersionedTypeImport()
	 * @see #getVersionedNamedImportSpecifier_IM()
	 * @generated
	 */
	EAttribute getVersionedNamedImportSpecifier_IM_VersionedTypeImport();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ImFactory getImFactory();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.Script_IMImpl <em>Script IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.Script_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getScript_IM()
		 * @generated
		 */
		EClass SCRIPT_IM = eINSTANCE.getScript_IM();

		/**
		 * The meta object literal for the '<em><b>Symbol Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCRIPT_IM__SYMBOL_TABLE = eINSTANCE.getScript_IM_SymbolTable();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableImpl <em>Symbol Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTable()
		 * @generated
		 */
		EClass SYMBOL_TABLE = eINSTANCE.getSymbolTable();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOL_TABLE__ENTRIES = eINSTANCE.getSymbolTable_Entries();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryImpl <em>Symbol Table Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntry()
		 * @generated
		 */
		EClass SYMBOL_TABLE_ENTRY = eINSTANCE.getSymbolTableEntry();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYMBOL_TABLE_ENTRY__NAME = eINSTANCE.getSymbolTableEntry_Name();

		/**
		 * The meta object literal for the '<em><b>Elements Of This Name</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOL_TABLE_ENTRY__ELEMENTS_OF_THIS_NAME = eINSTANCE.getSymbolTableEntry_ElementsOfThisName();

		/**
		 * The meta object literal for the '<em><b>Referencing Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOL_TABLE_ENTRY__REFERENCING_ELEMENTS = eINSTANCE.getSymbolTableEntry_ReferencingElements();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryOriginalImpl <em>Symbol Table Entry Original</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryOriginalImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntryOriginal()
		 * @generated
		 */
		EClass SYMBOL_TABLE_ENTRY_ORIGINAL = eINSTANCE.getSymbolTableEntryOriginal();

		/**
		 * The meta object literal for the '<em><b>Original Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOL_TABLE_ENTRY_ORIGINAL__ORIGINAL_TARGET = eINSTANCE.getSymbolTableEntryOriginal_OriginalTarget();

		/**
		 * The meta object literal for the '<em><b>Import Specifier</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBOL_TABLE_ENTRY_ORIGINAL__IMPORT_SPECIFIER = eINSTANCE.getSymbolTableEntryOriginal_ImportSpecifier();

		/**
		 * The meta object literal for the '<em><b>Get Exported Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SYMBOL_TABLE_ENTRY_ORIGINAL___GET_EXPORTED_NAME = eINSTANCE.getSymbolTableEntryOriginal__GetExportedName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryIMOnlyImpl <em>Symbol Table Entry IM Only</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryIMOnlyImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntryIMOnly()
		 * @generated
		 */
		EClass SYMBOL_TABLE_ENTRY_IM_ONLY = eINSTANCE.getSymbolTableEntryIMOnly();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryInternalImpl <em>Symbol Table Entry Internal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.SymbolTableEntryInternalImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSymbolTableEntryInternal()
		 * @generated
		 */
		EClass SYMBOL_TABLE_ENTRY_INTERNAL = eINSTANCE.getSymbolTableEntryInternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.ReferencingElement_IMImpl <em>Referencing Element IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.ReferencingElement_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getReferencingElement_IM()
		 * @generated
		 */
		EClass REFERENCING_ELEMENT_IM = eINSTANCE.getReferencingElement_IM();

		/**
		 * The meta object literal for the '<em><b>Rewired Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCING_ELEMENT_IM__REWIRED_TARGET = eINSTANCE.getReferencingElement_IM_RewiredTarget();

		/**
		 * The meta object literal for the '<em><b>Get Original Target Of Rewired Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REFERENCING_ELEMENT_IM___GET_ORIGINAL_TARGET_OF_REWIRED_TARGET = eINSTANCE.getReferencingElement_IM__GetOriginalTargetOfRewiredTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.ReferencingElementExpression_IMImpl <em>Referencing Element Expression IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.ReferencingElementExpression_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getReferencingElementExpression_IM()
		 * @generated
		 */
		EClass REFERENCING_ELEMENT_EXPRESSION_IM = eINSTANCE.getReferencingElementExpression_IM();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.IdentifierRef_IMImpl <em>Identifier Ref IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.IdentifierRef_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getIdentifierRef_IM()
		 * @generated
		 */
		EClass IDENTIFIER_REF_IM = eINSTANCE.getIdentifierRef_IM();

		/**
		 * The meta object literal for the '<em><b>Get Id IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IDENTIFIER_REF_IM___GET_ID_IM = eINSTANCE.getIdentifierRef_IM__GetId_IM();

		/**
		 * The meta object literal for the '<em><b>Set Id IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IDENTIFIER_REF_IM___SET_ID_IM__SYMBOLTABLEENTRY = eINSTANCE.getIdentifierRef_IM__SetId_IM__SymbolTableEntry();

		/**
		 * The meta object literal for the '<em><b>Get Id</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IDENTIFIER_REF_IM___GET_ID = eINSTANCE.getIdentifierRef_IM__GetId();

		/**
		 * The meta object literal for the '<em><b>Set Id</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IDENTIFIER_REF_IM___SET_ID__IDENTIFIABLEELEMENT = eINSTANCE.getIdentifierRef_IM__SetId__IdentifiableElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedPropertyAccessExpression_IMImpl <em>Parameterized Property Access Expression IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.ParameterizedPropertyAccessExpression_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getParameterizedPropertyAccessExpression_IM()
		 * @generated
		 */
		EClass PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM = eINSTANCE.getParameterizedPropertyAccessExpression_IM();

		/**
		 * The meta object literal for the '<em><b>Any Plus Access</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__ANY_PLUS_ACCESS = eINSTANCE.getParameterizedPropertyAccessExpression_IM_AnyPlusAccess();

		/**
		 * The meta object literal for the '<em><b>Name Of Any Plus Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM__NAME_OF_ANY_PLUS_PROPERTY = eINSTANCE.getParameterizedPropertyAccessExpression_IM_NameOfAnyPlusProperty();

		/**
		 * The meta object literal for the '<em><b>Get Property IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY_IM = eINSTANCE.getParameterizedPropertyAccessExpression_IM__GetProperty_IM();

		/**
		 * The meta object literal for the '<em><b>Set Property IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___SET_PROPERTY_IM__SYMBOLTABLEENTRY = eINSTANCE.getParameterizedPropertyAccessExpression_IM__SetProperty_IM__SymbolTableEntry();

		/**
		 * The meta object literal for the '<em><b>Get Property Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY_NAME = eINSTANCE.getParameterizedPropertyAccessExpression_IM__GetPropertyName();

		/**
		 * The meta object literal for the '<em><b>Get Property</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___GET_PROPERTY = eINSTANCE.getParameterizedPropertyAccessExpression_IM__GetProperty();

		/**
		 * The meta object literal for the '<em><b>Set Property</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_IM___SET_PROPERTY__IDENTIFIABLEELEMENT = eINSTANCE.getParameterizedPropertyAccessExpression_IM__SetProperty__IdentifiableElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRef_IMImpl <em>Parameterized Type Ref IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRef_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getParameterizedTypeRef_IM()
		 * @generated
		 */
		EClass PARAMETERIZED_TYPE_REF_IM = eINSTANCE.getParameterizedTypeRef_IM();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE_IM = eINSTANCE.getParameterizedTypeRef_IM__GetDeclaredType_IM();

		/**
		 * The meta object literal for the '<em><b>Set Declared Type IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY = eINSTANCE.getParameterizedTypeRef_IM__SetDeclaredType_IM__SymbolTableEntry();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_IM___GET_DECLARED_TYPE = eINSTANCE.getParameterizedTypeRef_IM__GetDeclaredType();

		/**
		 * The meta object literal for the '<em><b>Set Declared Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_IM___SET_DECLARED_TYPE__TYPE = eINSTANCE.getParameterizedTypeRef_IM__SetDeclaredType__Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl <em>Parameterized Type Ref Structural IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.ParameterizedTypeRefStructural_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getParameterizedTypeRefStructural_IM()
		 * @generated
		 */
		EClass PARAMETERIZED_TYPE_REF_STRUCTURAL_IM = eINSTANCE.getParameterizedTypeRefStructural_IM();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE_IM = eINSTANCE.getParameterizedTypeRefStructural_IM__GetDeclaredType_IM();

		/**
		 * The meta object literal for the '<em><b>Set Declared Type IM</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE_IM__SYMBOLTABLEENTRY = eINSTANCE.getParameterizedTypeRefStructural_IM__SetDeclaredType_IM__SymbolTableEntry();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___GET_DECLARED_TYPE = eINSTANCE.getParameterizedTypeRefStructural_IM__GetDeclaredType();

		/**
		 * The meta object literal for the '<em><b>Set Declared Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_TYPE_REF_STRUCTURAL_IM___SET_DECLARED_TYPE__TYPE = eINSTANCE.getParameterizedTypeRefStructural_IM__SetDeclaredType__Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.SnippetImpl <em>Snippet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.SnippetImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getSnippet()
		 * @generated
		 */
		EClass SNIPPET = eINSTANCE.getSnippet();

		/**
		 * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SNIPPET__CODE = eINSTANCE.getSnippet_Code();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl <em>Delegating Member</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingMemberImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingMember()
		 * @generated
		 */
		EClass DELEGATING_MEMBER = eINSTANCE.getDelegatingMember();

		/**
		 * The meta object literal for the '<em><b>Delegation Base Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELEGATING_MEMBER__DELEGATION_BASE_TYPE = eINSTANCE.getDelegatingMember_DelegationBaseType();

		/**
		 * The meta object literal for the '<em><b>Delegation Super Class Steps</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELEGATING_MEMBER__DELEGATION_SUPER_CLASS_STEPS = eINSTANCE.getDelegatingMember_DelegationSuperClassSteps();

		/**
		 * The meta object literal for the '<em><b>Delegation Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DELEGATING_MEMBER__DELEGATION_TARGET = eINSTANCE.getDelegatingMember_DelegationTarget();

		/**
		 * The meta object literal for the '<em><b>Delegation Target Is Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DELEGATING_MEMBER__DELEGATION_TARGET_IS_ABSTRACT = eINSTANCE.getDelegatingMember_DelegationTargetIsAbstract();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingGetterDeclarationImpl <em>Delegating Getter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingGetterDeclarationImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingGetterDeclaration()
		 * @generated
		 */
		EClass DELEGATING_GETTER_DECLARATION = eINSTANCE.getDelegatingGetterDeclaration();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingSetterDeclarationImpl <em>Delegating Setter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingSetterDeclarationImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingSetterDeclaration()
		 * @generated
		 */
		EClass DELEGATING_SETTER_DECLARATION = eINSTANCE.getDelegatingSetterDeclaration();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.DelegatingMethodDeclarationImpl <em>Delegating Method Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.DelegatingMethodDeclarationImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getDelegatingMethodDeclaration()
		 * @generated
		 */
		EClass DELEGATING_METHOD_DECLARATION = eINSTANCE.getDelegatingMethodDeclaration();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.StringLiteralForSTEImpl <em>String Literal For STE</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.StringLiteralForSTEImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getStringLiteralForSTE()
		 * @generated
		 */
		EClass STRING_LITERAL_FOR_STE = eINSTANCE.getStringLiteralForSTE();

		/**
		 * The meta object literal for the '<em><b>Entry</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRING_LITERAL_FOR_STE__ENTRY = eINSTANCE.getStringLiteralForSTE_Entry();

		/**
		 * The meta object literal for the '<em><b>Use Exported Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_LITERAL_FOR_STE__USE_EXPORTED_NAME = eINSTANCE.getStringLiteralForSTE_UseExportedName();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STRING_LITERAL_FOR_STE___GET_VALUE_AS_STRING = eINSTANCE.getStringLiteralForSTE__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRef_IMImpl <em>Versioned Parameterized Type Ref IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRef_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedParameterizedTypeRef_IM()
		 * @generated
		 */
		EClass VERSIONED_PARAMETERIZED_TYPE_REF_IM = eINSTANCE.getVersionedParameterizedTypeRef_IM();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl <em>Versioned Parameterized Type Ref Structural IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.VersionedParameterizedTypeRefStructural_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedParameterizedTypeRefStructural_IM()
		 * @generated
		 */
		EClass VERSIONED_PARAMETERIZED_TYPE_REF_STRUCTURAL_IM = eINSTANCE.getVersionedParameterizedTypeRefStructural_IM();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedIdentifierRef_IMImpl <em>Versioned Identifier Ref IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.VersionedIdentifierRef_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedIdentifierRef_IM()
		 * @generated
		 */
		EClass VERSIONED_IDENTIFIER_REF_IM = eINSTANCE.getVersionedIdentifierRef_IM();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.transpiler.im.impl.VersionedNamedImportSpecifier_IMImpl <em>Versioned Named Import Specifier IM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.transpiler.im.impl.VersionedNamedImportSpecifier_IMImpl
		 * @see org.eclipse.n4js.transpiler.im.impl.ImPackageImpl#getVersionedNamedImportSpecifier_IM()
		 * @generated
		 */
		EClass VERSIONED_NAMED_IMPORT_SPECIFIER_IM = eINSTANCE.getVersionedNamedImportSpecifier_IM();

		/**
		 * The meta object literal for the '<em><b>Imported Type Versions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSIONED_NAMED_IMPORT_SPECIFIER_IM__IMPORTED_TYPE_VERSIONS = eINSTANCE.getVersionedNamedImportSpecifier_IM_ImportedTypeVersions();

		/**
		 * The meta object literal for the '<em><b>Versioned Type Import</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSIONED_NAMED_IMPORT_SPECIFIER_IM__VERSIONED_TYPE_IMPORT = eINSTANCE.getVersionedNamedImportSpecifier_IM_VersionedTypeImport();

	}

} //ImPackage
