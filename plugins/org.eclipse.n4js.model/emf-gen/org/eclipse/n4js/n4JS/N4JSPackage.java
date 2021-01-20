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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.n4js.ts.types.TypesPackage;

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
 * @see org.eclipse.n4js.n4JS.N4JSFactory
 * @model kind="package"
 * @generated
 */
public interface N4JSPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "n4JS";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/n4js/N4JS";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "n4JS";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	N4JSPackage eINSTANCE = org.eclipse.n4js.n4JS.impl.N4JSPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.NamedElement <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.NamedElement
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT___GET_NAME = 0;

	/**
	 * The number of operations of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.ControlFlowElement <em>Control Flow Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.ControlFlowElement
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getControlFlowElement()
	 * @generated
	 */
	int CONTROL_FLOW_ELEMENT = 1;

	/**
	 * The number of structural features of the '<em>Control Flow Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_FLOW_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Control Flow Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_FLOW_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VariableEnvironmentElementImpl <em>Variable Environment Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VariableEnvironmentElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableEnvironmentElement()
	 * @generated
	 */
	int VARIABLE_ENVIRONMENT_ELEMENT = 14;

	/**
	 * The number of structural features of the '<em>Variable Environment Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ENVIRONMENT_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = 0;

	/**
	 * The number of operations of the '<em>Variable Environment Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_ENVIRONMENT_ELEMENT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ScriptImpl <em>Script</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ScriptImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getScript()
	 * @generated
	 */
	int SCRIPT = 2;

	/**
	 * The feature id for the '<em><b>Hashbang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT__HASHBANG = VARIABLE_ENVIRONMENT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT__ANNOTATIONS = VARIABLE_ENVIRONMENT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Script Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT__SCRIPT_ELEMENTS = VARIABLE_ENVIRONMENT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT__MODULE = VARIABLE_ENVIRONMENT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Flagged Usage Marking Finished</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT__FLAGGED_USAGE_MARKING_FINISHED = VARIABLE_ENVIRONMENT_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Script</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_FEATURE_COUNT = VARIABLE_ENVIRONMENT_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT___GET_ANNOTATIONS = VARIABLE_ENVIRONMENT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT___GET_ALL_ANNOTATIONS = VARIABLE_ENVIRONMENT_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Script</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_OPERATION_COUNT = VARIABLE_ENVIRONMENT_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ScriptElementImpl <em>Script Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ScriptElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getScriptElement()
	 * @generated
	 */
	int SCRIPT_ELEMENT = 3;

	/**
	 * The number of structural features of the '<em>Script Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Script Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCRIPT_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableElementImpl <em>Annotable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotableElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableElement()
	 * @generated
	 */
	int ANNOTABLE_ELEMENT = 18;

	/**
	 * The number of structural features of the '<em>Annotable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_ELEMENT___GET_ANNOTATIONS = 0;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS = 1;

	/**
	 * The number of operations of the '<em>Annotable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_ELEMENT_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableScriptElementImpl <em>Annotable Script Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotableScriptElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableScriptElement()
	 * @generated
	 */
	int ANNOTABLE_SCRIPT_ELEMENT = 19;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Annotable Script Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_SCRIPT_ELEMENT___GET_ALL_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Annotable Script Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl <em>Export Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportDeclaration()
	 * @generated
	 */
	int EXPORT_DECLARATION = 4;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION__ANNOTATION_LIST = ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Exported Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION__EXPORTED_ELEMENT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Exported Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Named Exports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION__NAMED_EXPORTS = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Wildcard Export</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION__WILDCARD_EXPORT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Default Export</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION__DEFAULT_EXPORT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Reexported From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION__REEXPORTED_FROM = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Export Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION_FEATURE_COUNT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION___GET_ALL_ANNOTATIONS = ANNOTABLE_SCRIPT_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION___GET_ANNOTATIONS = ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The number of operations of the '<em>Export Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_DECLARATION_OPERATION_COUNT = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExportSpecifierImpl <em>Export Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExportSpecifierImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportSpecifier()
	 * @generated
	 */
	int EXPORT_SPECIFIER = 5;

	/**
	 * The feature id for the '<em><b>Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_SPECIFIER__ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_SPECIFIER__ALIAS = 1;

	/**
	 * The number of structural features of the '<em>Export Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_SPECIFIER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Export Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORT_SPECIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExportableElementImpl <em>Exportable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExportableElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportableElement()
	 * @generated
	 */
	int EXPORTABLE_ELEMENT = 6;

	/**
	 * The number of structural features of the '<em>Exportable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTABLE_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTABLE_ELEMENT___IS_EXPORTED = 0;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT = 1;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTABLE_ELEMENT___GET_EXPORTED_NAME = 2;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTABLE_ELEMENT___IS_TOPLEVEL = 3;

	/**
	 * The number of operations of the '<em>Exportable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTABLE_ELEMENT_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl <em>Import Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getImportDeclaration()
	 * @generated
	 */
	int IMPORT_DECLARATION = 7;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION__ANNOTATION_LIST = ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Import Specifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION__IMPORT_SPECIFIERS = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Import From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION__IMPORT_FROM = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION__MODULE = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Module Specifier As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Module Specifier Form</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION__MODULE_SPECIFIER_FORM = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Import Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION_FEATURE_COUNT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION___GET_ALL_ANNOTATIONS = ANNOTABLE_SCRIPT_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION___GET_ANNOTATIONS = ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Bare</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION___IS_BARE = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Retained At Runtime</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION___IS_RETAINED_AT_RUNTIME = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Import Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_DECLARATION_OPERATION_COUNT = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ImportSpecifierImpl <em>Import Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ImportSpecifierImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getImportSpecifier()
	 * @generated
	 */
	int IMPORT_SPECIFIER = 8;

	/**
	 * The feature id for the '<em><b>Flagged Used In Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE = 0;

	/**
	 * The feature id for the '<em><b>Retained At Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_SPECIFIER__RETAINED_AT_RUNTIME = 1;

	/**
	 * The number of structural features of the '<em>Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_SPECIFIER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_SPECIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl <em>Named Import Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNamedImportSpecifier()
	 * @generated
	 */
	int NAMED_IMPORT_SPECIFIER = 9;

	/**
	 * The feature id for the '<em><b>Flagged Used In Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE = IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE;

	/**
	 * The feature id for the '<em><b>Retained At Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER__RETAINED_AT_RUNTIME = IMPORT_SPECIFIER__RETAINED_AT_RUNTIME;

	/**
	 * The feature id for the '<em><b>Imported Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT = IMPORT_SPECIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Imported Element As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT = IMPORT_SPECIFIER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER__ALIAS = IMPORT_SPECIFIER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Named Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER_FEATURE_COUNT = IMPORT_SPECIFIER_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Default Import</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT = IMPORT_SPECIFIER_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Named Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_IMPORT_SPECIFIER_OPERATION_COUNT = IMPORT_SPECIFIER_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.DefaultImportSpecifierImpl <em>Default Import Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.DefaultImportSpecifierImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDefaultImportSpecifier()
	 * @generated
	 */
	int DEFAULT_IMPORT_SPECIFIER = 10;

	/**
	 * The feature id for the '<em><b>Flagged Used In Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE = NAMED_IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE;

	/**
	 * The feature id for the '<em><b>Retained At Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER__RETAINED_AT_RUNTIME = NAMED_IMPORT_SPECIFIER__RETAINED_AT_RUNTIME;

	/**
	 * The feature id for the '<em><b>Imported Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER__IMPORTED_ELEMENT = NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT;

	/**
	 * The feature id for the '<em><b>Imported Element As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT = NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER__ALIAS = NAMED_IMPORT_SPECIFIER__ALIAS;

	/**
	 * The number of structural features of the '<em>Default Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER_FEATURE_COUNT = NAMED_IMPORT_SPECIFIER_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Alias</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER___GET_ALIAS = NAMED_IMPORT_SPECIFIER_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Default Import</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT = NAMED_IMPORT_SPECIFIER_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Default Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_IMPORT_SPECIFIER_OPERATION_COUNT = NAMED_IMPORT_SPECIFIER_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.NamespaceImportSpecifierImpl <em>Namespace Import Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.NamespaceImportSpecifierImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNamespaceImportSpecifier()
	 * @generated
	 */
	int NAMESPACE_IMPORT_SPECIFIER = 11;

	/**
	 * The feature id for the '<em><b>Flagged Used In Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE = IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE;

	/**
	 * The feature id for the '<em><b>Retained At Runtime</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_IMPORT_SPECIFIER__RETAINED_AT_RUNTIME = IMPORT_SPECIFIER__RETAINED_AT_RUNTIME;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_IMPORT_SPECIFIER__DEFINED_TYPE = IMPORT_SPECIFIER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Dynamic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_IMPORT_SPECIFIER__DECLARED_DYNAMIC = IMPORT_SPECIFIER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_IMPORT_SPECIFIER__ALIAS = IMPORT_SPECIFIER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Namespace Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_IMPORT_SPECIFIER_FEATURE_COUNT = IMPORT_SPECIFIER_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Namespace Import Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMESPACE_IMPORT_SPECIFIER_OPERATION_COUNT = IMPORT_SPECIFIER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.TypeProvidingElement <em>Type Providing Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.TypeProvidingElement
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypeProvidingElement()
	 * @generated
	 */
	int TYPE_PROVIDING_ELEMENT = 12;

	/**
	 * The number of structural features of the '<em>Type Providing Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_PROVIDING_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF = 0;

	/**
	 * The number of operations of the '<em>Type Providing Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_PROVIDING_ELEMENT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.TypedElementImpl <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.TypedElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 13;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__DECLARED_TYPE_REF = TYPE_PROVIDING_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = TYPE_PROVIDING_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT___GET_DECLARED_TYPE_REF = TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF;

	/**
	 * The number of operations of the '<em>Typed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_OPERATION_COUNT = TYPE_PROVIDING_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ThisTargetImpl <em>This Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ThisTargetImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThisTarget()
	 * @generated
	 */
	int THIS_TARGET = 15;

	/**
	 * The number of structural features of the '<em>This Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TARGET_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>This Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_TARGET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ThisArgProviderImpl <em>This Arg Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ThisArgProviderImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThisArgProvider()
	 * @generated
	 */
	int THIS_ARG_PROVIDER = 16;

	/**
	 * The number of structural features of the '<em>This Arg Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_ARG_PROVIDER_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>This Arg Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_ARG_PROVIDER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VariableImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 17;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__DECLARED_TYPE_REF = TYPED_ELEMENT__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = TYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = TYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE___GET_DECLARED_TYPE_REF = TYPED_ELEMENT___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Containing Module</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE___GET_CONTAINING_MODULE = TYPED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE___GET_NAME = TYPED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE___IS_CONST = TYPED_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = TYPED_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableExpressionImpl <em>Annotable Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotableExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableExpression()
	 * @generated
	 */
	int ANNOTABLE_EXPRESSION = 20;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_EXPRESSION__ANNOTATION_LIST = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Annotable Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_EXPRESSION_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_EXPRESSION___GET_ALL_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_EXPRESSION___GET_ANNOTATIONS = ANNOTABLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Annotable Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_EXPRESSION_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AbstractAnnotationListImpl <em>Abstract Annotation List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AbstractAnnotationListImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractAnnotationList()
	 * @generated
	 */
	int ABSTRACT_ANNOTATION_LIST = 21;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ANNOTATION_LIST__ANNOTATIONS = 0;

	/**
	 * The number of structural features of the '<em>Abstract Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotationListImpl <em>Annotation List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotationListImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotationList()
	 * @generated
	 */
	int ANNOTATION_LIST = 22;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_LIST__ANNOTATIONS = ABSTRACT_ANNOTATION_LIST__ANNOTATIONS;

	/**
	 * The number of structural features of the '<em>Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_LIST_FEATURE_COUNT = ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_LIST___IS_EXPORTED = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_LIST___IS_EXPORTED_AS_DEFAULT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_LIST___GET_EXPORTED_NAME = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_LIST___IS_TOPLEVEL = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_LIST_OPERATION_COUNT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionAnnotationListImpl <em>Expression Annotation List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExpressionAnnotationListImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpressionAnnotationList()
	 * @generated
	 */
	int EXPRESSION_ANNOTATION_LIST = 23;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_ANNOTATION_LIST__ANNOTATIONS = ABSTRACT_ANNOTATION_LIST__ANNOTATIONS;

	/**
	 * The number of structural features of the '<em>Expression Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_ANNOTATION_LIST_FEATURE_COUNT = ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_ANNOTATION_LIST___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Expression Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_ANNOTATION_LIST_OPERATION_COUNT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__NAME = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ARGS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION___GET_NAME = NAMED_ELEMENT___GET_NAME;

	/**
	 * The operation id for the '<em>Get Annotated Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION___GET_ANNOTATED_ELEMENT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotationArgumentImpl <em>Annotation Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotationArgumentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotationArgument()
	 * @generated
	 */
	int ANNOTATION_ARGUMENT = 25;

	/**
	 * The number of structural features of the '<em>Annotation Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_ARGUMENT_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_ARGUMENT___VALUE = 0;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING = 1;

	/**
	 * The number of operations of the '<em>Annotation Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_ARGUMENT_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.LiteralAnnotationArgumentImpl <em>Literal Annotation Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.LiteralAnnotationArgumentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLiteralAnnotationArgument()
	 * @generated
	 */
	int LITERAL_ANNOTATION_ARGUMENT = 26;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_ANNOTATION_ARGUMENT__LITERAL = ANNOTATION_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Literal Annotation Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_ANNOTATION_ARGUMENT_FEATURE_COUNT = ANNOTATION_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING = ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_ANNOTATION_ARGUMENT___VALUE = ANNOTATION_ARGUMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Literal Annotation Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_ANNOTATION_ARGUMENT_OPERATION_COUNT = ANNOTATION_ARGUMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.TypeRefAnnotationArgumentImpl <em>Type Ref Annotation Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.TypeRefAnnotationArgumentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypeRefAnnotationArgument()
	 * @generated
	 */
	int TYPE_REF_ANNOTATION_ARGUMENT = 27;

	/**
	 * The feature id for the '<em><b>Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF_ANNOTATION_ARGUMENT__TYPE_REF = ANNOTATION_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Ref Annotation Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF_ANNOTATION_ARGUMENT_FEATURE_COUNT = ANNOTATION_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF_ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING = ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF_ANNOTATION_ARGUMENT___VALUE = ANNOTATION_ARGUMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Type Ref Annotation Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REF_ANNOTATION_ARGUMENT_OPERATION_COUNT = ANNOTATION_ARGUMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.FunctionOrFieldAccessorImpl <em>Function Or Field Accessor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.FunctionOrFieldAccessorImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionOrFieldAccessor()
	 * @generated
	 */
	int FUNCTION_OR_FIELD_ACCESSOR = 28;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR__BODY = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR__LOK = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Function Or Field Accessor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___GET_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___GET_ALL_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___GET_NAME = ANNOTABLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE = ANNOTABLE_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL = ANNOTABLE_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC = ANNOTABLE_ELEMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR = ANNOTABLE_ELEMENT_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Function Or Field Accessor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl <em>Function Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionDefinition()
	 * @generated
	 */
	int FUNCTION_DEFINITION = 29;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__BODY = FUNCTION_OR_FIELD_ACCESSOR__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__LOK = FUNCTION_OR_FIELD_ACCESSOR__LOK;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__DEFINED_TYPE = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__DECLARED_VERSION = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__FPARS = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__RETURN_TYPE_REF = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__GENERATOR = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION__DECLARED_ASYNC = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Function Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION_FEATURE_COUNT = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___GET_ANNOTATIONS = FUNCTION_OR_FIELD_ACCESSOR___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___GET_ALL_ANNOTATIONS = FUNCTION_OR_FIELD_ACCESSOR___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = FUNCTION_OR_FIELD_ACCESSOR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___GET_NAME = FUNCTION_OR_FIELD_ACCESSOR___GET_NAME;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___GET_LOCAL_ARGUMENTS_VARIABLE = FUNCTION_OR_FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___GET_DEFINED_FUNCTION_OR_ACCESSOR = FUNCTION_OR_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___HAS_DECLARED_VERSION = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___GET_DECLARED_VERSION_OR_ZERO = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___IS_ASYNC = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION___GET_DEFINED_FUNCTION = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 4;

	/**
	 * The number of operations of the '<em>Function Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DEFINITION_OPERATION_COUNT = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.FieldAccessorImpl <em>Field Accessor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.FieldAccessorImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFieldAccessor()
	 * @generated
	 */
	int FIELD_ACCESSOR = 30;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR__BODY = FUNCTION_OR_FIELD_ACCESSOR__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR__LOK = FUNCTION_OR_FIELD_ACCESSOR__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR__DECLARED_NAME = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR__DECLARED_OPTIONAL = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Field Accessor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR_FEATURE_COUNT = FUNCTION_OR_FIELD_ACCESSOR_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___GET_ANNOTATIONS = FUNCTION_OR_FIELD_ACCESSOR___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___GET_ALL_ANNOTATIONS = FUNCTION_OR_FIELD_ACCESSOR___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = FUNCTION_OR_FIELD_ACCESSOR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE = FUNCTION_OR_FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL = FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___IS_ASYNC = FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR = FUNCTION_OR_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___GET_NAME = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___HAS_COMPUTED_PROPERTY_NAME = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___IS_VALID_NAME = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___GET_DECLARED_TYPE_REF = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___GET_DEFINED_ACCESSOR = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR___IS_OPTIONAL = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>Field Accessor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_ACCESSOR_OPERATION_COUNT = FUNCTION_OR_FIELD_ACCESSOR_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl <em>Function Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionDeclaration()
	 * @generated
	 */
	int FUNCTION_DECLARATION = 31;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__ANNOTATION_LIST = ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__DECLARED_MODIFIERS = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__BODY = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__LOK = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__DEFINED_TYPE = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__DECLARED_VERSION = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__FPARS = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__RETURN_TYPE_REF = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__GENERATOR = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__DECLARED_ASYNC = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__TYPE_VARS = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__NAME = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>migration Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION__MIGRATION_CONTEXT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>Function Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION_FEATURE_COUNT = ANNOTABLE_SCRIPT_ELEMENT_FEATURE_COUNT + 12;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_ALL_ANNOTATIONS = ANNOTABLE_SCRIPT_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_ANNOTATIONS = ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___HAS_DECLARED_VERSION = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___IS_RETURN_VALUE_OPTIONAL = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___IS_ASYNC = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_DEFINED_FUNCTION = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___IS_EXPORTED = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___IS_EXPORTED_AS_DEFAULT = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_EXPORTED_NAME = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 13;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___IS_TOPLEVEL = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 14;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_NAME = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 15;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___IS_EXTERNAL = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 16;

	/**
	 * The operation id for the '<em>Get Migration Context Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION___GET_MIGRATION_CONTEXT_VARIABLE = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 17;

	/**
	 * The number of operations of the '<em>Function Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_DECLARATION_OPERATION_COUNT = ANNOTABLE_SCRIPT_ELEMENT_OPERATION_COUNT + 18;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.FunctionExpressionImpl <em>Function Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.FunctionExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionExpression()
	 * @generated
	 */
	int FUNCTION_EXPRESSION = 32;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__BODY = FUNCTION_DEFINITION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__LOK = FUNCTION_DEFINITION__LOK;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__DEFINED_TYPE = FUNCTION_DEFINITION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__DECLARED_VERSION = FUNCTION_DEFINITION__DECLARED_VERSION;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__FPARS = FUNCTION_DEFINITION__FPARS;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__RETURN_TYPE_REF = FUNCTION_DEFINITION__RETURN_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__GENERATOR = FUNCTION_DEFINITION__GENERATOR;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__DECLARED_ASYNC = FUNCTION_DEFINITION__DECLARED_ASYNC;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__ANNOTATION_LIST = FUNCTION_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__TYPE_VARS = FUNCTION_DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION__NAME = FUNCTION_DEFINITION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Function Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION_FEATURE_COUNT = FUNCTION_DEFINITION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___GET_ALL_ANNOTATIONS = FUNCTION_DEFINITION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = FUNCTION_DEFINITION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___GET_LOCAL_ARGUMENTS_VARIABLE = FUNCTION_DEFINITION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___GET_DEFINED_FUNCTION_OR_ACCESSOR = FUNCTION_DEFINITION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___HAS_DECLARED_VERSION = FUNCTION_DEFINITION___HAS_DECLARED_VERSION;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___GET_DECLARED_VERSION_OR_ZERO = FUNCTION_DEFINITION___GET_DECLARED_VERSION_OR_ZERO;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___IS_RETURN_VALUE_OPTIONAL = FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___IS_ASYNC = FUNCTION_DEFINITION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___GET_DEFINED_FUNCTION = FUNCTION_DEFINITION___GET_DEFINED_FUNCTION;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = FUNCTION_DEFINITION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___GET_ANNOTATIONS = FUNCTION_DEFINITION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___GET_NAME = FUNCTION_DEFINITION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Arrow Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION___IS_ARROW_FUNCTION = FUNCTION_DEFINITION_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Function Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_EXPRESSION_OPERATION_COUNT = FUNCTION_DEFINITION_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ArrowFunctionImpl <em>Arrow Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ArrowFunctionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrowFunction()
	 * @generated
	 */
	int ARROW_FUNCTION = 33;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__BODY = FUNCTION_EXPRESSION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__LOK = FUNCTION_EXPRESSION__LOK;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__DEFINED_TYPE = FUNCTION_EXPRESSION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__DECLARED_VERSION = FUNCTION_EXPRESSION__DECLARED_VERSION;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__FPARS = FUNCTION_EXPRESSION__FPARS;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__RETURN_TYPE_REF = FUNCTION_EXPRESSION__RETURN_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__GENERATOR = FUNCTION_EXPRESSION__GENERATOR;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__DECLARED_ASYNC = FUNCTION_EXPRESSION__DECLARED_ASYNC;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__ANNOTATION_LIST = FUNCTION_EXPRESSION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__TYPE_VARS = FUNCTION_EXPRESSION__TYPE_VARS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__NAME = FUNCTION_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Has Braces Around Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION__HAS_BRACES_AROUND_BODY = FUNCTION_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Arrow Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION_FEATURE_COUNT = FUNCTION_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_ALL_ANNOTATIONS = FUNCTION_EXPRESSION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = FUNCTION_EXPRESSION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_LOCAL_ARGUMENTS_VARIABLE = FUNCTION_EXPRESSION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_DEFINED_FUNCTION_OR_ACCESSOR = FUNCTION_EXPRESSION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___HAS_DECLARED_VERSION = FUNCTION_EXPRESSION___HAS_DECLARED_VERSION;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_DECLARED_VERSION_OR_ZERO = FUNCTION_EXPRESSION___GET_DECLARED_VERSION_OR_ZERO;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___IS_RETURN_VALUE_OPTIONAL = FUNCTION_EXPRESSION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___IS_ASYNC = FUNCTION_EXPRESSION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_DEFINED_FUNCTION = FUNCTION_EXPRESSION___GET_DEFINED_FUNCTION;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = FUNCTION_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_ANNOTATIONS = FUNCTION_EXPRESSION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_NAME = FUNCTION_EXPRESSION___GET_NAME;

	/**
	 * The operation id for the '<em>Is Arrow Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___IS_ARROW_FUNCTION = FUNCTION_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Single Expr Implicit Return</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___IS_SINGLE_EXPR_IMPLICIT_RETURN = FUNCTION_EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Single Expression</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___GET_SINGLE_EXPRESSION = FUNCTION_EXPRESSION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Implicit Return Expr</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION___IMPLICIT_RETURN_EXPR = FUNCTION_EXPRESSION_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Arrow Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARROW_FUNCTION_OPERATION_COUNT = FUNCTION_EXPRESSION_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.LocalArgumentsVariableImpl <em>Local Arguments Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.LocalArgumentsVariableImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLocalArgumentsVariable()
	 * @generated
	 */
	int LOCAL_ARGUMENTS_VARIABLE = 34;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE__DECLARED_TYPE_REF = VARIABLE__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE__NAME = VARIABLE__NAME;

	/**
	 * The number of structural features of the '<em>Local Arguments Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE___GET_DECLARED_TYPE_REF = VARIABLE___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Containing Module</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE___GET_CONTAINING_MODULE = VARIABLE___GET_CONTAINING_MODULE;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE___IS_CONST = VARIABLE___IS_CONST;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE___GET_NAME = VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Local Arguments Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_ARGUMENTS_VARIABLE_OPERATION_COUNT = VARIABLE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl <em>Formal Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.FormalParameterImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFormalParameter()
	 * @generated
	 */
	int FORMAL_PARAMETER = 35;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__DECLARED_TYPE_REF = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__NAME = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__ANNOTATIONS = ANNOTABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Variadic</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__VARIADIC = ANNOTABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Defined Type Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Has Initializer Assignment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Initializer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__INITIALIZER = ANNOTABLE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Binding Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER__BINDING_PATTERN = ANNOTABLE_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Formal Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER___GET_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER___GET_ALL_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER___GET_DECLARED_TYPE_REF = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Containing Module</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER___GET_CONTAINING_MODULE = ANNOTABLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER___GET_NAME = ANNOTABLE_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER___IS_CONST = ANNOTABLE_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Formal Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMAL_PARAMETER_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.StatementImpl <em>Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.StatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getStatement()
	 * @generated
	 */
	int STATEMENT = 37;

	/**
	 * The number of structural features of the '<em>Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_FEATURE_COUNT = SCRIPT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATEMENT_OPERATION_COUNT = SCRIPT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BlockImpl <em>Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BlockImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBlock()
	 * @generated
	 */
	int BLOCK = 36;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK__STATEMENTS = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = STATEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Expressions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_EXPRESSIONS = STATEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get All Yield Expressions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_YIELD_EXPRESSIONS = STATEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get All Void Yield Expressions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_VOID_YIELD_EXPRESSIONS = STATEMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get All Non Void Yield Expressions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_NON_VOID_YIELD_EXPRESSIONS = STATEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Has Non Void Yield</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___HAS_NON_VOID_YIELD = STATEMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get All Statements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_STATEMENTS = STATEMENT_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Get All Return Statements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_RETURN_STATEMENTS = STATEMENT_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get All Non Void Return Statements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_NON_VOID_RETURN_STATEMENTS = STATEMENT_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get All Void Return Statements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___GET_ALL_VOID_RETURN_STATEMENTS = STATEMENT_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Has Non Void Return</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK___HAS_NON_VOID_RETURN = STATEMENT_OPERATION_COUNT + 11;

	/**
	 * The number of operations of the '<em>Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLOCK_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationContainerImpl <em>Variable Declaration Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VariableDeclarationContainerImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableDeclarationContainer()
	 * @generated
	 */
	int VARIABLE_DECLARATION_CONTAINER = 38;

	/**
	 * The feature id for the '<em><b>Var Decls Or Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS = 0;

	/**
	 * The feature id for the '<em><b>Var Stmt Keyword</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD = 1;

	/**
	 * The number of structural features of the '<em>Variable Declaration Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Get Var Decl</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_CONTAINER___GET_VAR_DECL = 0;

	/**
	 * The operation id for the '<em>Is Block Scoped</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_CONTAINER___IS_BLOCK_SCOPED = 1;

	/**
	 * The number of operations of the '<em>Variable Declaration Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_CONTAINER_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VariableStatementImpl <em>Variable Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VariableStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableStatement()
	 * @generated
	 */
	int VARIABLE_STATEMENT = 39;

	/**
	 * The feature id for the '<em><b>Var Decls Or Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Var Stmt Keyword</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_STATEMENT__VAR_STMT_KEYWORD = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Var Decl</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_STATEMENT___GET_VAR_DECL = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Block Scoped</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_STATEMENT___IS_BLOCK_SCOPED = STATEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Variable Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExportedVariableStatementImpl <em>Exported Variable Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExportedVariableStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportedVariableStatement()
	 * @generated
	 */
	int EXPORTED_VARIABLE_STATEMENT = 40;

	/**
	 * The feature id for the '<em><b>Var Decls Or Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS = VARIABLE_STATEMENT__VAR_DECLS_OR_BINDINGS;

	/**
	 * The feature id for the '<em><b>Var Stmt Keyword</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT__VAR_STMT_KEYWORD = VARIABLE_STATEMENT__VAR_STMT_KEYWORD;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT__ANNOTATION_LIST = VARIABLE_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT__DECLARED_MODIFIERS = VARIABLE_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Exported Variable Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT_FEATURE_COUNT = VARIABLE_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Var Decl</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___GET_VAR_DECL = VARIABLE_STATEMENT___GET_VAR_DECL;

	/**
	 * The operation id for the '<em>Is Block Scoped</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___IS_BLOCK_SCOPED = VARIABLE_STATEMENT___IS_BLOCK_SCOPED;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___IS_EXPORTED = VARIABLE_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___IS_EXPORTED_AS_DEFAULT = VARIABLE_STATEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___GET_EXPORTED_NAME = VARIABLE_STATEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___IS_TOPLEVEL = VARIABLE_STATEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___GET_ALL_ANNOTATIONS = VARIABLE_STATEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___GET_ANNOTATIONS = VARIABLE_STATEMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT___IS_EXTERNAL = VARIABLE_STATEMENT_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>Exported Variable Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_STATEMENT_OPERATION_COUNT = VARIABLE_STATEMENT_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationOrBindingImpl <em>Variable Declaration Or Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VariableDeclarationOrBindingImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableDeclarationOrBinding()
	 * @generated
	 */
	int VARIABLE_DECLARATION_OR_BINDING = 41;

	/**
	 * The number of structural features of the '<em>Variable Declaration Or Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Variable Declarations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_OR_BINDING___GET_VARIABLE_DECLARATIONS = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Expression</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_OR_BINDING___GET_EXPRESSION = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Variable Declaration Or Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VariableBindingImpl <em>Variable Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VariableBindingImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableBinding()
	 * @generated
	 */
	int VARIABLE_BINDING = 42;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING__PATTERN = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING__EXPRESSION = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING_FEATURE_COUNT = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Variable Declarations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING___GET_VARIABLE_DECLARATIONS = VARIABLE_DECLARATION_OR_BINDING___GET_VARIABLE_DECLARATIONS;

	/**
	 * The operation id for the '<em>Get Expression</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING___GET_EXPRESSION = VARIABLE_DECLARATION_OR_BINDING___GET_EXPRESSION;

	/**
	 * The number of operations of the '<em>Variable Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_BINDING_OPERATION_COUNT = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExportedVariableBindingImpl <em>Exported Variable Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExportedVariableBindingImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportedVariableBinding()
	 * @generated
	 */
	int EXPORTED_VARIABLE_BINDING = 43;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_BINDING__PATTERN = VARIABLE_BINDING__PATTERN;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_BINDING__EXPRESSION = VARIABLE_BINDING__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Defined Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE = VARIABLE_BINDING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Exported Variable Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_BINDING_FEATURE_COUNT = VARIABLE_BINDING_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Variable Declarations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_BINDING___GET_VARIABLE_DECLARATIONS = VARIABLE_BINDING___GET_VARIABLE_DECLARATIONS;

	/**
	 * The operation id for the '<em>Get Expression</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_BINDING___GET_EXPRESSION = VARIABLE_BINDING___GET_EXPRESSION;

	/**
	 * The number of operations of the '<em>Exported Variable Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_BINDING_OPERATION_COUNT = VARIABLE_BINDING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationImpl <em>Variable Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VariableDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableDeclaration()
	 * @generated
	 */
	int VARIABLE_DECLARATION = 44;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION__DECLARED_TYPE_REF = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION__NAME = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION__ANNOTATIONS = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION__EXPRESSION = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Variable Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_FEATURE_COUNT = VARIABLE_DECLARATION_OR_BINDING_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Variable Declarations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___GET_VARIABLE_DECLARATIONS = VARIABLE_DECLARATION_OR_BINDING___GET_VARIABLE_DECLARATIONS;

	/**
	 * The operation id for the '<em>Get Expression</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___GET_EXPRESSION = VARIABLE_DECLARATION_OR_BINDING___GET_EXPRESSION;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___GET_ANNOTATIONS = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___GET_ALL_ANNOTATIONS = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___GET_DECLARED_TYPE_REF = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Containing Module</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___GET_CONTAINING_MODULE = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___GET_NAME = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION___IS_CONST = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 6;

	/**
	 * The number of operations of the '<em>Variable Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DECLARATION_OPERATION_COUNT = VARIABLE_DECLARATION_OR_BINDING_OPERATION_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExportedVariableDeclarationImpl <em>Exported Variable Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExportedVariableDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportedVariableDeclaration()
	 * @generated
	 */
	int EXPORTED_VARIABLE_DECLARATION = 45;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION__DECLARED_TYPE_REF = VARIABLE_DECLARATION__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION__NAME = VARIABLE_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION__ANNOTATIONS = VARIABLE_DECLARATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION__EXPRESSION = VARIABLE_DECLARATION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Defined Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION__DEFINED_VARIABLE = VARIABLE_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Exported Variable Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION_FEATURE_COUNT = VARIABLE_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Variable Declarations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___GET_VARIABLE_DECLARATIONS = VARIABLE_DECLARATION___GET_VARIABLE_DECLARATIONS;

	/**
	 * The operation id for the '<em>Get Expression</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___GET_EXPRESSION = VARIABLE_DECLARATION___GET_EXPRESSION;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___GET_ANNOTATIONS = VARIABLE_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___GET_ALL_ANNOTATIONS = VARIABLE_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___GET_DECLARED_TYPE_REF = VARIABLE_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Containing Module</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___GET_CONTAINING_MODULE = VARIABLE_DECLARATION___GET_CONTAINING_MODULE;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___GET_NAME = VARIABLE_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION___IS_CONST = VARIABLE_DECLARATION___IS_CONST;

	/**
	 * The number of operations of the '<em>Exported Variable Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPORTED_VARIABLE_DECLARATION_OPERATION_COUNT = VARIABLE_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.EmptyStatementImpl <em>Empty Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.EmptyStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getEmptyStatement()
	 * @generated
	 */
	int EMPTY_STATEMENT = 46;

	/**
	 * The number of structural features of the '<em>Empty Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPTY_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Empty Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMPTY_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionStatementImpl <em>Expression Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExpressionStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpressionStatement()
	 * @generated
	 */
	int EXPRESSION_STATEMENT = 47;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Expression Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.IfStatementImpl <em>If Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.IfStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIfStatement()
	 * @generated
	 */
	int IF_STATEMENT = 48;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>If Stmt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT__IF_STMT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else Stmt</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT__ELSE_STMT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>If Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>If Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IF_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.IterationStatementImpl <em>Iteration Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.IterationStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIterationStatement()
	 * @generated
	 */
	int ITERATION_STATEMENT = 49;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATION_STATEMENT__STATEMENT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATION_STATEMENT__EXPRESSION = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iteration Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATION_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Iteration Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATION_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.DoStatementImpl <em>Do Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.DoStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDoStatement()
	 * @generated
	 */
	int DO_STATEMENT = 50;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DO_STATEMENT__STATEMENT = ITERATION_STATEMENT__STATEMENT;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DO_STATEMENT__EXPRESSION = ITERATION_STATEMENT__EXPRESSION;

	/**
	 * The number of structural features of the '<em>Do Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DO_STATEMENT_FEATURE_COUNT = ITERATION_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Do Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DO_STATEMENT_OPERATION_COUNT = ITERATION_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.WhileStatementImpl <em>While Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.WhileStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getWhileStatement()
	 * @generated
	 */
	int WHILE_STATEMENT = 51;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE_STATEMENT__STATEMENT = ITERATION_STATEMENT__STATEMENT;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE_STATEMENT__EXPRESSION = ITERATION_STATEMENT__EXPRESSION;

	/**
	 * The number of structural features of the '<em>While Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE_STATEMENT_FEATURE_COUNT = ITERATION_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>While Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WHILE_STATEMENT_OPERATION_COUNT = ITERATION_STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl <em>For Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ForStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getForStatement()
	 * @generated
	 */
	int FOR_STATEMENT = 52;

	/**
	 * The feature id for the '<em><b>Var Decls Or Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__VAR_DECLS_OR_BINDINGS = VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS;

	/**
	 * The feature id for the '<em><b>Var Stmt Keyword</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__VAR_STMT_KEYWORD = VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__STATEMENT = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__EXPRESSION = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Init Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__INIT_EXPR = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Update Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__UPDATE_EXPR = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Await</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__AWAIT = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>For In</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__FOR_IN = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>For Of</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT__FOR_OF = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>For Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT_FEATURE_COUNT = VARIABLE_DECLARATION_CONTAINER_FEATURE_COUNT + 7;

	/**
	 * The operation id for the '<em>Get Var Decl</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT___GET_VAR_DECL = VARIABLE_DECLARATION_CONTAINER___GET_VAR_DECL;

	/**
	 * The operation id for the '<em>Is Block Scoped</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT___IS_BLOCK_SCOPED = VARIABLE_DECLARATION_CONTAINER___IS_BLOCK_SCOPED;

	/**
	 * The operation id for the '<em>Is For Plain</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT___IS_FOR_PLAIN = VARIABLE_DECLARATION_CONTAINER_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = VARIABLE_DECLARATION_CONTAINER_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>For Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOR_STATEMENT_OPERATION_COUNT = VARIABLE_DECLARATION_CONTAINER_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.LabelRefImpl <em>Label Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.LabelRefImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLabelRef()
	 * @generated
	 */
	int LABEL_REF = 53;

	/**
	 * The feature id for the '<em><b>Label</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_REF__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Label As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_REF__LABEL_AS_TEXT = 1;

	/**
	 * The number of structural features of the '<em>Label Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_REF_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Label Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_REF_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ContinueStatementImpl <em>Continue Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ContinueStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getContinueStatement()
	 * @generated
	 */
	int CONTINUE_STATEMENT = 54;

	/**
	 * The feature id for the '<em><b>Label</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUE_STATEMENT__LABEL = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUE_STATEMENT__LABEL_AS_TEXT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Continue Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUE_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Continue Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINUE_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BreakStatementImpl <em>Break Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BreakStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBreakStatement()
	 * @generated
	 */
	int BREAK_STATEMENT = 55;

	/**
	 * The feature id for the '<em><b>Label</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK_STATEMENT__LABEL = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK_STATEMENT__LABEL_AS_TEXT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Break Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Break Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BREAK_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ReturnStatementImpl <em>Return Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ReturnStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getReturnStatement()
	 * @generated
	 */
	int RETURN_STATEMENT = 56;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Return Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Return Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RETURN_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.WithStatementImpl <em>With Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.WithStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getWithStatement()
	 * @generated
	 */
	int WITH_STATEMENT = 57;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_STATEMENT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_STATEMENT__STATEMENT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>With Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>With Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.SwitchStatementImpl <em>Switch Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.SwitchStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getSwitchStatement()
	 * @generated
	 */
	int SWITCH_STATEMENT = 58;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_STATEMENT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cases</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_STATEMENT__CASES = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Switch Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = STATEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Default Clause</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_STATEMENT___GET_DEFAULT_CLAUSE = STATEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Case Clauses</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_STATEMENT___GET_CASE_CLAUSES = STATEMENT_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>Switch Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SWITCH_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AbstractCaseClauseImpl <em>Abstract Case Clause</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AbstractCaseClauseImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractCaseClause()
	 * @generated
	 */
	int ABSTRACT_CASE_CLAUSE = 59;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CASE_CLAUSE__STATEMENTS = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Case Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CASE_CLAUSE_FEATURE_COUNT = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Abstract Case Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CASE_CLAUSE_OPERATION_COUNT = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.CaseClauseImpl <em>Case Clause</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.CaseClauseImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCaseClause()
	 * @generated
	 */
	int CASE_CLAUSE = 60;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_CLAUSE__STATEMENTS = ABSTRACT_CASE_CLAUSE__STATEMENTS;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_CLAUSE__EXPRESSION = ABSTRACT_CASE_CLAUSE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Case Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_CLAUSE_FEATURE_COUNT = ABSTRACT_CASE_CLAUSE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Case Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CASE_CLAUSE_OPERATION_COUNT = ABSTRACT_CASE_CLAUSE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.DefaultClauseImpl <em>Default Clause</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.DefaultClauseImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDefaultClause()
	 * @generated
	 */
	int DEFAULT_CLAUSE = 61;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_CLAUSE__STATEMENTS = ABSTRACT_CASE_CLAUSE__STATEMENTS;

	/**
	 * The number of structural features of the '<em>Default Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_CLAUSE_FEATURE_COUNT = ABSTRACT_CASE_CLAUSE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Default Clause</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEFAULT_CLAUSE_OPERATION_COUNT = ABSTRACT_CASE_CLAUSE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.LabelledStatementImpl <em>Labelled Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.LabelledStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLabelledStatement()
	 * @generated
	 */
	int LABELLED_STATEMENT = 62;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABELLED_STATEMENT__NAME = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABELLED_STATEMENT__STATEMENT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Labelled Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABELLED_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABELLED_STATEMENT___GET_NAME = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Labelled Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABELLED_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ThrowStatementImpl <em>Throw Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ThrowStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThrowStatement()
	 * @generated
	 */
	int THROW_STATEMENT = 63;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROW_STATEMENT__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Throw Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROW_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Throw Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROW_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.TryStatementImpl <em>Try Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.TryStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTryStatement()
	 * @generated
	 */
	int TRY_STATEMENT = 64;

	/**
	 * The feature id for the '<em><b>Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_STATEMENT__BLOCK = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Catch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_STATEMENT__CATCH = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Finally</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_STATEMENT__FINALLY = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Try Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Try Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRY_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AbstractCatchBlockImpl <em>Abstract Catch Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AbstractCatchBlockImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractCatchBlock()
	 * @generated
	 */
	int ABSTRACT_CATCH_BLOCK = 65;

	/**
	 * The feature id for the '<em><b>Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATCH_BLOCK__BLOCK = 0;

	/**
	 * The number of structural features of the '<em>Abstract Catch Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATCH_BLOCK_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Abstract Catch Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_CATCH_BLOCK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.CatchBlockImpl <em>Catch Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.CatchBlockImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCatchBlock()
	 * @generated
	 */
	int CATCH_BLOCK = 66;

	/**
	 * The feature id for the '<em><b>Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_BLOCK__BLOCK = ABSTRACT_CATCH_BLOCK__BLOCK;

	/**
	 * The feature id for the '<em><b>Catch Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_BLOCK__CATCH_VARIABLE = ABSTRACT_CATCH_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Catch Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_BLOCK_FEATURE_COUNT = ABSTRACT_CATCH_BLOCK_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ABSTRACT_CATCH_BLOCK_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Catch Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_BLOCK_OPERATION_COUNT = ABSTRACT_CATCH_BLOCK_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.CatchVariableImpl <em>Catch Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.CatchVariableImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCatchVariable()
	 * @generated
	 */
	int CATCH_VARIABLE = 67;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE__DECLARED_TYPE_REF = VARIABLE__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE__NAME = VARIABLE__NAME;

	/**
	 * The feature id for the '<em><b>Binding Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE__BINDING_PATTERN = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Catch Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE___GET_DECLARED_TYPE_REF = VARIABLE___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Containing Module</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE___GET_CONTAINING_MODULE = VARIABLE___GET_CONTAINING_MODULE;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE___GET_NAME = VARIABLE___GET_NAME;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE___IS_CONST = VARIABLE___IS_CONST;

	/**
	 * The number of operations of the '<em>Catch Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATCH_VARIABLE_OPERATION_COUNT = VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.FinallyBlockImpl <em>Finally Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.FinallyBlockImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFinallyBlock()
	 * @generated
	 */
	int FINALLY_BLOCK = 68;

	/**
	 * The feature id for the '<em><b>Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINALLY_BLOCK__BLOCK = ABSTRACT_CATCH_BLOCK__BLOCK;

	/**
	 * The number of structural features of the '<em>Finally Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINALLY_BLOCK_FEATURE_COUNT = ABSTRACT_CATCH_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Finally Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FINALLY_BLOCK_OPERATION_COUNT = ABSTRACT_CATCH_BLOCK_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.DebuggerStatementImpl <em>Debugger Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.DebuggerStatementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDebuggerStatement()
	 * @generated
	 */
	int DEBUGGER_STATEMENT = 69;

	/**
	 * The number of structural features of the '<em>Debugger Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEBUGGER_STATEMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Debugger Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEBUGGER_STATEMENT_OPERATION_COUNT = STATEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 93;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = TypesPackage.TYPABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_OPERATION_COUNT = TypesPackage.TYPABLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PrimaryExpressionImpl <em>Primary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PrimaryExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPrimaryExpression()
	 * @generated
	 */
	int PRIMARY_EXPRESSION = 70;

	/**
	 * The number of structural features of the '<em>Primary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Primary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMARY_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ParenExpressionImpl <em>Paren Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ParenExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParenExpression()
	 * @generated
	 */
	int PAREN_EXPRESSION = 71;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAREN_EXPRESSION__EXPRESSION = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Paren Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAREN_EXPRESSION_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAREN_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Paren Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAREN_EXPRESSION_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.IdentifierRefImpl <em>Identifier Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.IdentifierRefImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIdentifierRef()
	 * @generated
	 */
	int IDENTIFIER_REF = 72;

	/**
	 * The feature id for the '<em><b>Strict Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF__STRICT_MODE = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF__ID = PRIMARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF__ID_AS_TEXT = PRIMARY_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Origin Import</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF__ORIGIN_IMPORT = PRIMARY_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Identifier Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF___GET_VERSION = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Target Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF___GET_TARGET_ELEMENT = PRIMARY_EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Identifier Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFIER_REF_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.StrictModeRelevantImpl <em>Strict Mode Relevant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.StrictModeRelevantImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getStrictModeRelevant()
	 * @generated
	 */
	int STRICT_MODE_RELEVANT = 73;

	/**
	 * The feature id for the '<em><b>Strict Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRICT_MODE_RELEVANT__STRICT_MODE = 0;

	/**
	 * The number of structural features of the '<em>Strict Mode Relevant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRICT_MODE_RELEVANT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Strict Mode Relevant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRICT_MODE_RELEVANT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.SuperLiteralImpl <em>Super Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.SuperLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getSuperLiteral()
	 * @generated
	 */
	int SUPER_LITERAL = 74;

	/**
	 * The number of structural features of the '<em>Super Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_LITERAL_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Is Super Constructor Access</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_LITERAL___IS_SUPER_CONSTRUCTOR_ACCESS = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Super Member Access</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_LITERAL___IS_SUPER_MEMBER_ACCESS = PRIMARY_EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Super Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUPER_LITERAL_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ThisLiteralImpl <em>This Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ThisLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThisLiteral()
	 * @generated
	 */
	int THIS_LITERAL = 75;

	/**
	 * The feature id for the '<em><b>Strict Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_LITERAL__STRICT_MODE = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>This Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_LITERAL_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>This Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIS_LITERAL_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ArrayLiteralImpl <em>Array Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ArrayLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayLiteral()
	 * @generated
	 */
	int ARRAY_LITERAL = 76;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_LITERAL__ELEMENTS = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Trailing Comma</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_LITERAL__TRAILING_COMMA = PRIMARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Array Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_LITERAL_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Array Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_LITERAL_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ArrayElementImpl <em>Array Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ArrayElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayElement()
	 * @generated
	 */
	int ARRAY_ELEMENT = 77;

	/**
	 * The feature id for the '<em><b>Spread</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ELEMENT__SPREAD = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ELEMENT__EXPRESSION = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Array Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ELEMENT_FEATURE_COUNT = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Array Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_ELEMENT_OPERATION_COUNT = TypesPackage.TYPABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ArrayPaddingImpl <em>Array Padding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ArrayPaddingImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayPadding()
	 * @generated
	 */
	int ARRAY_PADDING = 78;

	/**
	 * The feature id for the '<em><b>Spread</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PADDING__SPREAD = ARRAY_ELEMENT__SPREAD;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PADDING__EXPRESSION = ARRAY_ELEMENT__EXPRESSION;

	/**
	 * The number of structural features of the '<em>Array Padding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PADDING_FEATURE_COUNT = ARRAY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Array Padding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_PADDING_OPERATION_COUNT = ARRAY_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ObjectLiteralImpl <em>Object Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ObjectLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getObjectLiteral()
	 * @generated
	 */
	int OBJECT_LITERAL = 79;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_LITERAL__DEFINED_TYPE = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_LITERAL__PROPERTY_ASSIGNMENTS = PRIMARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Object Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_LITERAL_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Object Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_LITERAL_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertyAssignmentImpl <em>Property Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertyAssignmentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyAssignment()
	 * @generated
	 */
	int PROPERTY_ASSIGNMENT = 80;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT__DECLARED_NAME = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT___GET_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT___GET_ALL_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT___GET_NAME = ANNOTABLE_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT___HAS_COMPUTED_PROPERTY_NAME = ANNOTABLE_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT___GET_DEFINED_MEMBER = ANNOTABLE_ELEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT___IS_VALID_NAME = ANNOTABLE_ELEMENT_OPERATION_COUNT + 6;

	/**
	 * The number of operations of the '<em>Property Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertyNameOwnerImpl <em>Property Name Owner</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertyNameOwnerImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameOwner()
	 * @generated
	 */
	int PROPERTY_NAME_OWNER = 81;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_OWNER__DECLARED_NAME = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Name Owner</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_OWNER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_OWNER___GET_NAME = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_OWNER___HAS_COMPUTED_PROPERTY_NAME = NAMED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_OWNER___IS_VALID_NAME = NAMED_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Property Name Owner</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_OWNER_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl <em>Literal Or Computed Property Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLiteralOrComputedPropertyName()
	 * @generated
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME = 82;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND = 0;

	/**
	 * The feature id for the '<em><b>Literal Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME = 1;

	/**
	 * The feature id for the '<em><b>Computed Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME = 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION = 3;

	/**
	 * The number of structural features of the '<em>Literal Or Computed Property Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME_FEATURE_COUNT = 4;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME___HAS_COMPUTED_PROPERTY_NAME = 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME___GET_NAME = 1;

	/**
	 * The number of operations of the '<em>Literal Or Computed Property Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OR_COMPUTED_PROPERTY_NAME_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotablePropertyAssignmentImpl <em>Annotable Property Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotablePropertyAssignmentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotablePropertyAssignment()
	 * @generated
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT = 83;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT__DECLARED_NAME = PROPERTY_ASSIGNMENT__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST = PROPERTY_ASSIGNMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Annotable Property Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT = PROPERTY_ASSIGNMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ALL_ANNOTATIONS = PROPERTY_ASSIGNMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = PROPERTY_ASSIGNMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT___GET_NAME = PROPERTY_ASSIGNMENT___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT___HAS_COMPUTED_PROPERTY_NAME = PROPERTY_ASSIGNMENT___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT___GET_DEFINED_MEMBER = PROPERTY_ASSIGNMENT___GET_DEFINED_MEMBER;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT___IS_VALID_NAME = PROPERTY_ASSIGNMENT___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ANNOTATIONS = PROPERTY_ASSIGNMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Annotable Property Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT = PROPERTY_ASSIGNMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertyAssignmentAnnotationListImpl <em>Property Assignment Annotation List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertyAssignmentAnnotationListImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyAssignmentAnnotationList()
	 * @generated
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST = 84;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST__ANNOTATIONS = ABSTRACT_ANNOTATION_LIST__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST__DECLARED_NAME = ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Assignment Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST_FEATURE_COUNT = ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST___GET_ANNOTATIONS = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST___GET_ALL_ANNOTATIONS = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST___GET_NAME = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST___HAS_COMPUTED_PROPERTY_NAME = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST___IS_VALID_NAME = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST___GET_DEFINED_MEMBER = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 9;

	/**
	 * The number of operations of the '<em>Property Assignment Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_ASSIGNMENT_ANNOTATION_LIST_OPERATION_COUNT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl <em>Property Name Value Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameValuePair()
	 * @generated
	 */
	int PROPERTY_NAME_VALUE_PAIR = 85;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR__DECLARED_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR__ANNOTATION_LIST = ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Defined Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR__EXPRESSION = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Property Name Value Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_FEATURE_COUNT = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___GET_ALL_ANNOTATIONS = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_PROPERTY_ASSIGNMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___GET_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___HAS_COMPUTED_PROPERTY_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___GET_ANNOTATIONS = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___GET_DEFINED_MEMBER = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR___IS_VALID_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Property Name Value Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_OPERATION_COUNT = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairSingleNameImpl <em>Property Name Value Pair Single Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertyNameValuePairSingleNameImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameValuePairSingleName()
	 * @generated
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME = 86;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__DECLARED_NAME = PROPERTY_NAME_VALUE_PAIR__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__ANNOTATION_LIST = PROPERTY_NAME_VALUE_PAIR__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__DECLARED_TYPE_REF = PROPERTY_NAME_VALUE_PAIR__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Defined Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__DEFINED_FIELD = PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__DECLARED_OPTIONAL = PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME__EXPRESSION = PROPERTY_NAME_VALUE_PAIR__EXPRESSION;

	/**
	 * The number of structural features of the '<em>Property Name Value Pair Single Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME_FEATURE_COUNT = PROPERTY_NAME_VALUE_PAIR_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_ALL_ANNOTATIONS = PROPERTY_NAME_VALUE_PAIR___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = PROPERTY_NAME_VALUE_PAIR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___HAS_COMPUTED_PROPERTY_NAME = PROPERTY_NAME_VALUE_PAIR___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_ANNOTATIONS = PROPERTY_NAME_VALUE_PAIR___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_DECLARED_TYPE_REF = PROPERTY_NAME_VALUE_PAIR___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_DEFINED_MEMBER = PROPERTY_NAME_VALUE_PAIR___GET_DEFINED_MEMBER;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___IS_VALID_NAME = PROPERTY_NAME_VALUE_PAIR___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Get Identifier Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_IDENTIFIER_REF = PROPERTY_NAME_VALUE_PAIR_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME = PROPERTY_NAME_VALUE_PAIR_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Property Name Value Pair Single Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME_OPERATION_COUNT = PROPERTY_NAME_VALUE_PAIR_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertyMethodDeclarationImpl <em>Property Method Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertyMethodDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyMethodDeclaration()
	 * @generated
	 */
	int PROPERTY_METHOD_DECLARATION = 87;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__DECLARED_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__ANNOTATION_LIST = ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__BODY = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__LOK = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__DEFINED_TYPE = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__DECLARED_VERSION = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__FPARS = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__RETURN_TYPE_REF = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__GENERATOR = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__DECLARED_ASYNC = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__TYPE_VARS = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION__DECLARED_TYPE_REF = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Property Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION_FEATURE_COUNT = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 10;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_ALL_ANNOTATIONS = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_PROPERTY_ASSIGNMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___IS_VALID_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_ANNOTATIONS = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___HAS_DECLARED_VERSION = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___IS_ASYNC = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_DEFINED_FUNCTION = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_DECLARED_TYPE_REF = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Exists Explicit Super Call</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___IS_STATIC = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 13;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION___GET_DEFINED_MEMBER = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 14;

	/**
	 * The number of operations of the '<em>Property Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_METHOD_DECLARATION_OPERATION_COUNT = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 15;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl <em>Getter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getGetterDeclaration()
	 * @generated
	 */
	int GETTER_DECLARATION = 88;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION__BODY = FIELD_ACCESSOR__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION__LOK = FIELD_ACCESSOR__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION__DECLARED_NAME = FIELD_ACCESSOR__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION__DECLARED_OPTIONAL = FIELD_ACCESSOR__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION__DECLARED_TYPE_REF = FIELD_ACCESSOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Defined Getter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION__DEFINED_GETTER = FIELD_ACCESSOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION_FEATURE_COUNT = FIELD_ACCESSOR_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___GET_ANNOTATIONS = FIELD_ACCESSOR___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___GET_ALL_ANNOTATIONS = FIELD_ACCESSOR___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = FIELD_ACCESSOR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___IS_ASYNC = FIELD_ACCESSOR___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___GET_NAME = FIELD_ACCESSOR___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = FIELD_ACCESSOR___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___IS_VALID_NAME = FIELD_ACCESSOR___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___GET_DECLARED_TYPE_REF = FIELD_ACCESSOR___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___IS_OPTIONAL = FIELD_ACCESSOR___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION___GET_DEFINED_ACCESSOR = FIELD_ACCESSOR_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GETTER_DECLARATION_OPERATION_COUNT = FIELD_ACCESSOR_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.SetterDeclarationImpl <em>Setter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.SetterDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getSetterDeclaration()
	 * @generated
	 */
	int SETTER_DECLARATION = 89;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION__BODY = FIELD_ACCESSOR__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION__LOK = FIELD_ACCESSOR__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION__DECLARED_NAME = FIELD_ACCESSOR__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION__DECLARED_OPTIONAL = FIELD_ACCESSOR__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Defined Setter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION__DEFINED_SETTER = FIELD_ACCESSOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Fpar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION__FPAR = FIELD_ACCESSOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION_FEATURE_COUNT = FIELD_ACCESSOR_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___GET_ANNOTATIONS = FIELD_ACCESSOR___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___GET_ALL_ANNOTATIONS = FIELD_ACCESSOR___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = FIELD_ACCESSOR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___IS_ASYNC = FIELD_ACCESSOR___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___GET_NAME = FIELD_ACCESSOR___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = FIELD_ACCESSOR___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___IS_VALID_NAME = FIELD_ACCESSOR___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___IS_OPTIONAL = FIELD_ACCESSOR___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___GET_DEFINED_ACCESSOR = FIELD_ACCESSOR_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION___GET_DECLARED_TYPE_REF = FIELD_ACCESSOR_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SETTER_DECLARATION_OPERATION_COUNT = FIELD_ACCESSOR_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertyGetterDeclarationImpl <em>Property Getter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertyGetterDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyGetterDeclaration()
	 * @generated
	 */
	int PROPERTY_GETTER_DECLARATION = 90;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION__BODY = GETTER_DECLARATION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION__LOK = GETTER_DECLARATION__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION__DECLARED_NAME = GETTER_DECLARATION__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION__DECLARED_OPTIONAL = GETTER_DECLARATION__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION__DECLARED_TYPE_REF = GETTER_DECLARATION__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Defined Getter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION__DEFINED_GETTER = GETTER_DECLARATION__DEFINED_GETTER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION__ANNOTATION_LIST = GETTER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION_FEATURE_COUNT = GETTER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_ALL_ANNOTATIONS = GETTER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = GETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = GETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = GETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___IS_ASYNC = GETTER_DECLARATION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = GETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_NAME = GETTER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = GETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_DECLARED_TYPE_REF = GETTER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___IS_OPTIONAL = GETTER_DECLARATION___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_DEFINED_ACCESSOR = GETTER_DECLARATION___GET_DEFINED_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_ANNOTATIONS = GETTER_DECLARATION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Defined Getter</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_DEFINED_GETTER = GETTER_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___GET_DEFINED_MEMBER = GETTER_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION___IS_VALID_NAME = GETTER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Property Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_GETTER_DECLARATION_OPERATION_COUNT = GETTER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertySetterDeclarationImpl <em>Property Setter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertySetterDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertySetterDeclaration()
	 * @generated
	 */
	int PROPERTY_SETTER_DECLARATION = 91;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION__BODY = SETTER_DECLARATION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION__LOK = SETTER_DECLARATION__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION__DECLARED_NAME = SETTER_DECLARATION__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION__DECLARED_OPTIONAL = SETTER_DECLARATION__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Defined Setter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION__DEFINED_SETTER = SETTER_DECLARATION__DEFINED_SETTER;

	/**
	 * The feature id for the '<em><b>Fpar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION__FPAR = SETTER_DECLARATION__FPAR;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION__ANNOTATION_LIST = SETTER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION_FEATURE_COUNT = SETTER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_ALL_ANNOTATIONS = SETTER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = SETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = SETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = SETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___IS_ASYNC = SETTER_DECLARATION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = SETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_NAME = SETTER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = SETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___IS_OPTIONAL = SETTER_DECLARATION___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_DEFINED_ACCESSOR = SETTER_DECLARATION___GET_DEFINED_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_DECLARED_TYPE_REF = SETTER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_ANNOTATIONS = SETTER_DECLARATION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Defined Setter</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_DEFINED_SETTER = SETTER_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___GET_DEFINED_MEMBER = SETTER_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION___IS_VALID_NAME = SETTER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The number of operations of the '<em>Property Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SETTER_DECLARATION_OPERATION_COUNT = SETTER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PropertySpreadImpl <em>Property Spread</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PropertySpreadImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertySpread()
	 * @generated
	 */
	int PROPERTY_SPREAD = 92;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD__DECLARED_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD__ANNOTATION_LIST = ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD__EXPRESSION = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Spread</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD_FEATURE_COUNT = ANNOTABLE_PROPERTY_ASSIGNMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD___GET_ALL_ANNOTATIONS = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_PROPERTY_ASSIGNMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD___GET_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD___HAS_COMPUTED_PROPERTY_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD___IS_VALID_NAME = ANNOTABLE_PROPERTY_ASSIGNMENT___IS_VALID_NAME;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD___GET_ANNOTATIONS = ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Defined Member</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD___GET_DEFINED_MEMBER = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Property Spread</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_SPREAD_OPERATION_COUNT = ANNOTABLE_PROPERTY_ASSIGNMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.NewTargetImpl <em>New Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.NewTargetImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNewTarget()
	 * @generated
	 */
	int NEW_TARGET = 94;

	/**
	 * The number of structural features of the '<em>New Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_TARGET_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_TARGET___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>New Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_TARGET_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.NewExpressionImpl <em>New Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.NewExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNewExpression()
	 * @generated
	 */
	int NEW_EXPRESSION = 95;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION__TYPE_ARGS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Callee</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION__CALLEE = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION__ARGUMENTS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>With Args</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION__WITH_ARGS = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>New Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION___IS_PARAMETERIZED = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>New Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ParameterizedAccessImpl <em>Parameterized Access</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ParameterizedAccessImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParameterizedAccess()
	 * @generated
	 */
	int PARAMETERIZED_ACCESS = 96;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_ACCESS__TYPE_ARGS = 0;

	/**
	 * The number of structural features of the '<em>Parameterized Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_ACCESS_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_ACCESS___IS_PARAMETERIZED = 0;

	/**
	 * The number of operations of the '<em>Parameterized Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_ACCESS_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionWithTargetImpl <em>Expression With Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ExpressionWithTargetImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpressionWithTarget()
	 * @generated
	 */
	int EXPRESSION_WITH_TARGET = 97;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_WITH_TARGET__TARGET = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Optional Chaining</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Expression With Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_WITH_TARGET_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_WITH_TARGET___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Is Or Has Target With Optional Chaining</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Expression With Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_WITH_TARGET_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ParameterizedCallExpressionImpl <em>Parameterized Call Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ParameterizedCallExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParameterizedCallExpression()
	 * @generated
	 */
	int PARAMETERIZED_CALL_EXPRESSION = 98;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION__TARGET = EXPRESSION_WITH_TARGET__TARGET;

	/**
	 * The feature id for the '<em><b>Optional Chaining</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION__OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION__TYPE_ARGS = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Parameterized Call Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION_FEATURE_COUNT = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION_WITH_TARGET___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Is Or Has Target With Optional Chaining</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION___IS_PARAMETERIZED = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Receiver</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION___GET_RECEIVER = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Parameterized Call Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_CALL_EXPRESSION_OPERATION_COUNT = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ImportCallExpressionImpl <em>Import Call Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ImportCallExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getImportCallExpression()
	 * @generated
	 */
	int IMPORT_CALL_EXPRESSION = 99;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CALL_EXPRESSION__ARGUMENTS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Import Call Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CALL_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CALL_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Argument</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CALL_EXPRESSION___GET_ARGUMENT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Import Call Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_CALL_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ArgumentImpl <em>Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ArgumentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArgument()
	 * @generated
	 */
	int ARGUMENT = 100;

	/**
	 * The feature id for the '<em><b>Spread</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__SPREAD = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__EXPRESSION = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_FEATURE_COUNT = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_OPERATION_COUNT = TypesPackage.TYPABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.IndexedAccessExpressionImpl <em>Indexed Access Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.IndexedAccessExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIndexedAccessExpression()
	 * @generated
	 */
	int INDEXED_ACCESS_EXPRESSION = 101;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION__TARGET = EXPRESSION_WITH_TARGET__TARGET;

	/**
	 * The feature id for the '<em><b>Optional Chaining</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION__OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING;

	/**
	 * The feature id for the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION__INDEX = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Indexed Access Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION_FEATURE_COUNT = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Or Has Target With Optional Chaining</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Indexed Access Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDEXED_ACCESS_EXPRESSION_OPERATION_COUNT = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.TaggedTemplateStringImpl <em>Tagged Template String</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.TaggedTemplateStringImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTaggedTemplateString()
	 * @generated
	 */
	int TAGGED_TEMPLATE_STRING = 102;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAGGED_TEMPLATE_STRING__TARGET = EXPRESSION_WITH_TARGET__TARGET;

	/**
	 * The feature id for the '<em><b>Optional Chaining</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAGGED_TEMPLATE_STRING__OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAGGED_TEMPLATE_STRING__TEMPLATE = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Tagged Template String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAGGED_TEMPLATE_STRING_FEATURE_COUNT = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAGGED_TEMPLATE_STRING___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION_WITH_TARGET___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Is Or Has Target With Optional Chaining</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAGGED_TEMPLATE_STRING___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING;

	/**
	 * The number of operations of the '<em>Tagged Template String</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAGGED_TEMPLATE_STRING_OPERATION_COUNT = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.MemberAccessImpl <em>Member Access</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.MemberAccessImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMemberAccess()
	 * @generated
	 */
	int MEMBER_ACCESS = 103;

	/**
	 * The feature id for the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ACCESS__COMPOSED_MEMBER_CACHE = 0;

	/**
	 * The number of structural features of the '<em>Member Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ACCESS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Member Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ACCESS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl <em>Parameterized Property Access Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParameterizedPropertyAccessExpression()
	 * @generated
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION = 104;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TARGET = EXPRESSION_WITH_TARGET__TARGET;

	/**
	 * The feature id for the '<em><b>Optional Chaining</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING;

	/**
	 * The feature id for the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__COMPOSED_MEMBER_CACHE = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Args</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__TYPE_ARGS = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Property As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Parameterized Property Access Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_FEATURE_COUNT = EXPRESSION_WITH_TARGET_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Or Has Target With Optional Chaining</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING = EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING;

	/**
	 * The operation id for the '<em>Is Parameterized</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_PARAMETERIZED = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Parameterized Property Access Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION_OPERATION_COUNT = EXPRESSION_WITH_TARGET_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AwaitExpressionImpl <em>Await Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AwaitExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAwaitExpression()
	 * @generated
	 */
	int AWAIT_EXPRESSION = 105;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AWAIT_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Await Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AWAIT_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AWAIT_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Await Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AWAIT_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PromisifyExpressionImpl <em>Promisify Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PromisifyExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPromisifyExpression()
	 * @generated
	 */
	int PROMISIFY_EXPRESSION = 106;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROMISIFY_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Promisify Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROMISIFY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROMISIFY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Promisify Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROMISIFY_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.YieldExpressionImpl <em>Yield Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.YieldExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getYieldExpression()
	 * @generated
	 */
	int YIELD_EXPRESSION = 107;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int YIELD_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Many</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int YIELD_EXPRESSION__MANY = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Yield Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int YIELD_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int YIELD_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Yield Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int YIELD_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.LiteralImpl <em>Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.LiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLiteral()
	 * @generated
	 */
	int LITERAL = 108;

	/**
	 * The number of structural features of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___GET_VALUE_AS_STRING = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.NullLiteralImpl <em>Null Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.NullLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNullLiteral()
	 * @generated
	 */
	int NULL_LITERAL = 109;

	/**
	 * The number of structural features of the '<em>Null Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL___GET_VALUE_AS_STRING = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Null Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NULL_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BooleanLiteralImpl <em>Boolean Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BooleanLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBooleanLiteral()
	 * @generated
	 */
	int BOOLEAN_LITERAL = 110;

	/**
	 * The feature id for the '<em><b>True</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL__TRUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL___GET_VALUE_AS_STRING = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.StringLiteralImpl <em>String Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.StringLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getStringLiteral()
	 * @generated
	 */
	int STRING_LITERAL = 111;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Raw Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL__RAW_VALUE = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>String Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL___GET_VALUE_AS_STRING = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>String Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.TemplateLiteralImpl <em>Template Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.TemplateLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTemplateLiteral()
	 * @generated
	 */
	int TEMPLATE_LITERAL = 112;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_LITERAL__SEGMENTS = PRIMARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Template Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_LITERAL_FEATURE_COUNT = PRIMARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = PRIMARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_LITERAL___GET_VALUE_AS_STRING = PRIMARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Template Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_LITERAL_OPERATION_COUNT = PRIMARY_EXPRESSION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.TemplateSegmentImpl <em>Template Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.TemplateSegmentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTemplateSegment()
	 * @generated
	 */
	int TEMPLATE_SEGMENT = 113;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_SEGMENT__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Raw Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_SEGMENT__RAW_VALUE = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Template Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_SEGMENT_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_SEGMENT___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_SEGMENT___GET_VALUE_AS_STRING = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Template Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_SEGMENT_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.NumericLiteralImpl <em>Numeric Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.NumericLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNumericLiteral()
	 * @generated
	 */
	int NUMERIC_LITERAL = 114;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numeric Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LITERAL___GET_VALUE_AS_STRING = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Numeric Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.DoubleLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDoubleLiteral()
	 * @generated
	 */
	int DOUBLE_LITERAL = 115;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__VALUE = NUMERIC_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Double Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_FEATURE_COUNT = NUMERIC_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = NUMERIC_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>To Double</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL___TO_DOUBLE = NUMERIC_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL___GET_VALUE_AS_STRING = NUMERIC_LITERAL_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Double Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_OPERATION_COUNT = NUMERIC_LITERAL_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AbstractIntLiteralImpl <em>Abstract Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AbstractIntLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractIntLiteral()
	 * @generated
	 */
	int ABSTRACT_INT_LITERAL = 116;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL__VALUE = NUMERIC_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Abstract Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL_FEATURE_COUNT = NUMERIC_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = NUMERIC_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL___GET_VALUE_AS_STRING = NUMERIC_LITERAL___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>To Int</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL___TO_INT = NUMERIC_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>To Long</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL___TO_LONG = NUMERIC_LITERAL_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>To Big Integer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL___TO_BIG_INTEGER = NUMERIC_LITERAL_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Abstract Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_INT_LITERAL_OPERATION_COUNT = NUMERIC_LITERAL_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.IntLiteralImpl <em>Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.IntLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIntLiteral()
	 * @generated
	 */
	int INT_LITERAL = 117;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL__VALUE = ABSTRACT_INT_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL_FEATURE_COUNT = ABSTRACT_INT_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ABSTRACT_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL___GET_VALUE_AS_STRING = ABSTRACT_INT_LITERAL___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>To Int</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL___TO_INT = ABSTRACT_INT_LITERAL___TO_INT;

	/**
	 * The operation id for the '<em>To Long</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL___TO_LONG = ABSTRACT_INT_LITERAL___TO_LONG;

	/**
	 * The operation id for the '<em>To Big Integer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL___TO_BIG_INTEGER = ABSTRACT_INT_LITERAL___TO_BIG_INTEGER;

	/**
	 * The number of operations of the '<em>Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_LITERAL_OPERATION_COUNT = ABSTRACT_INT_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BinaryIntLiteralImpl <em>Binary Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BinaryIntLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryIntLiteral()
	 * @generated
	 */
	int BINARY_INT_LITERAL = 118;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL__VALUE = ABSTRACT_INT_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Binary Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL_FEATURE_COUNT = ABSTRACT_INT_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ABSTRACT_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL___GET_VALUE_AS_STRING = ABSTRACT_INT_LITERAL___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>To Int</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL___TO_INT = ABSTRACT_INT_LITERAL___TO_INT;

	/**
	 * The operation id for the '<em>To Long</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL___TO_LONG = ABSTRACT_INT_LITERAL___TO_LONG;

	/**
	 * The operation id for the '<em>To Big Integer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL___TO_BIG_INTEGER = ABSTRACT_INT_LITERAL___TO_BIG_INTEGER;

	/**
	 * The number of operations of the '<em>Binary Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_INT_LITERAL_OPERATION_COUNT = ABSTRACT_INT_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.OctalIntLiteralImpl <em>Octal Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.OctalIntLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getOctalIntLiteral()
	 * @generated
	 */
	int OCTAL_INT_LITERAL = 119;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL__VALUE = ABSTRACT_INT_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Octal Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL_FEATURE_COUNT = ABSTRACT_INT_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ABSTRACT_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL___GET_VALUE_AS_STRING = ABSTRACT_INT_LITERAL___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>To Int</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL___TO_INT = ABSTRACT_INT_LITERAL___TO_INT;

	/**
	 * The operation id for the '<em>To Long</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL___TO_LONG = ABSTRACT_INT_LITERAL___TO_LONG;

	/**
	 * The operation id for the '<em>To Big Integer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL___TO_BIG_INTEGER = ABSTRACT_INT_LITERAL___TO_BIG_INTEGER;

	/**
	 * The number of operations of the '<em>Octal Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCTAL_INT_LITERAL_OPERATION_COUNT = ABSTRACT_INT_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.LegacyOctalIntLiteralImpl <em>Legacy Octal Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.LegacyOctalIntLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLegacyOctalIntLiteral()
	 * @generated
	 */
	int LEGACY_OCTAL_INT_LITERAL = 120;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL__VALUE = ABSTRACT_INT_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Legacy Octal Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL_FEATURE_COUNT = ABSTRACT_INT_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ABSTRACT_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL___GET_VALUE_AS_STRING = ABSTRACT_INT_LITERAL___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>To Int</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL___TO_INT = ABSTRACT_INT_LITERAL___TO_INT;

	/**
	 * The operation id for the '<em>To Long</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL___TO_LONG = ABSTRACT_INT_LITERAL___TO_LONG;

	/**
	 * The operation id for the '<em>To Big Integer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL___TO_BIG_INTEGER = ABSTRACT_INT_LITERAL___TO_BIG_INTEGER;

	/**
	 * The number of operations of the '<em>Legacy Octal Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LEGACY_OCTAL_INT_LITERAL_OPERATION_COUNT = ABSTRACT_INT_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.HexIntLiteralImpl <em>Hex Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.HexIntLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getHexIntLiteral()
	 * @generated
	 */
	int HEX_INT_LITERAL = 121;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL__VALUE = ABSTRACT_INT_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Hex Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL_FEATURE_COUNT = ABSTRACT_INT_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ABSTRACT_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL___GET_VALUE_AS_STRING = ABSTRACT_INT_LITERAL___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>To Int</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL___TO_INT = ABSTRACT_INT_LITERAL___TO_INT;

	/**
	 * The operation id for the '<em>To Long</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL___TO_LONG = ABSTRACT_INT_LITERAL___TO_LONG;

	/**
	 * The operation id for the '<em>To Big Integer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL___TO_BIG_INTEGER = ABSTRACT_INT_LITERAL___TO_BIG_INTEGER;

	/**
	 * The number of operations of the '<em>Hex Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HEX_INT_LITERAL_OPERATION_COUNT = ABSTRACT_INT_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ScientificIntLiteralImpl <em>Scientific Int Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ScientificIntLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getScientificIntLiteral()
	 * @generated
	 */
	int SCIENTIFIC_INT_LITERAL = 122;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL__VALUE = ABSTRACT_INT_LITERAL__VALUE;

	/**
	 * The number of structural features of the '<em>Scientific Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL_FEATURE_COUNT = ABSTRACT_INT_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = ABSTRACT_INT_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL___GET_VALUE_AS_STRING = ABSTRACT_INT_LITERAL___GET_VALUE_AS_STRING;

	/**
	 * The operation id for the '<em>To Int</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL___TO_INT = ABSTRACT_INT_LITERAL___TO_INT;

	/**
	 * The operation id for the '<em>To Long</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL___TO_LONG = ABSTRACT_INT_LITERAL___TO_LONG;

	/**
	 * The operation id for the '<em>To Big Integer</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL___TO_BIG_INTEGER = ABSTRACT_INT_LITERAL___TO_BIG_INTEGER;

	/**
	 * The number of operations of the '<em>Scientific Int Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCIENTIFIC_INT_LITERAL_OPERATION_COUNT = ABSTRACT_INT_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.RegularExpressionLiteralImpl <em>Regular Expression Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.RegularExpressionLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getRegularExpressionLiteral()
	 * @generated
	 */
	int REGULAR_EXPRESSION_LITERAL = 123;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGULAR_EXPRESSION_LITERAL__VALUE = LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Regular Expression Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGULAR_EXPRESSION_LITERAL_FEATURE_COUNT = LITERAL_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGULAR_EXPRESSION_LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = LITERAL___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Get Value As String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGULAR_EXPRESSION_LITERAL___GET_VALUE_AS_STRING = LITERAL_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Regular Expression Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGULAR_EXPRESSION_LITERAL_OPERATION_COUNT = LITERAL_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.PostfixExpressionImpl <em>Postfix Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.PostfixExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPostfixExpression()
	 * @generated
	 */
	int POSTFIX_EXPRESSION = 124;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSTFIX_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSTFIX_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Postfix Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSTFIX_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSTFIX_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Postfix Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSTFIX_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.UnaryExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getUnaryExpression()
	 * @generated
	 */
	int UNARY_EXPRESSION = 125;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.CastExpressionImpl <em>Cast Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.CastExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCastExpression()
	 * @generated
	 */
	int CAST_EXPRESSION = 126;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPRESSION__TARGET_TYPE_REF = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Cast Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Cast Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAST_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.MultiplicativeExpressionImpl <em>Multiplicative Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.MultiplicativeExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMultiplicativeExpression()
	 * @generated
	 */
	int MULTIPLICATIVE_EXPRESSION = 127;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Multiplicative Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Multiplicative Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLICATIVE_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AdditiveExpressionImpl <em>Additive Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AdditiveExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAdditiveExpression()
	 * @generated
	 */
	int ADDITIVE_EXPRESSION = 128;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Additive Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Additive Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADDITIVE_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ShiftExpressionImpl <em>Shift Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ShiftExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getShiftExpression()
	 * @generated
	 */
	int SHIFT_EXPRESSION = 129;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Shift Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Shift Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHIFT_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.RelationalExpressionImpl <em>Relational Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.RelationalExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getRelationalExpression()
	 * @generated
	 */
	int RELATIONAL_EXPRESSION = 130;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Relational Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Relational Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.EqualityExpressionImpl <em>Equality Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.EqualityExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getEqualityExpression()
	 * @generated
	 */
	int EQUALITY_EXPRESSION = 131;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Equality Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Equality Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUALITY_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BinaryBitwiseExpressionImpl <em>Binary Bitwise Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BinaryBitwiseExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryBitwiseExpression()
	 * @generated
	 */
	int BINARY_BITWISE_EXPRESSION = 132;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_BITWISE_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_BITWISE_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_BITWISE_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Binary Bitwise Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_BITWISE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_BITWISE_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Binary Bitwise Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_BITWISE_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BinaryLogicalExpressionImpl <em>Binary Logical Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BinaryLogicalExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryLogicalExpression()
	 * @generated
	 */
	int BINARY_LOGICAL_EXPRESSION = 133;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LOGICAL_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LOGICAL_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LOGICAL_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Binary Logical Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LOGICAL_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LOGICAL_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Binary Logical Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_LOGICAL_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.CoalesceExpressionImpl <em>Coalesce Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.CoalesceExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCoalesceExpression()
	 * @generated
	 */
	int COALESCE_EXPRESSION = 134;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COALESCE_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COALESCE_EXPRESSION__DEFAULT_EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Coalesce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COALESCE_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COALESCE_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Coalesce Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COALESCE_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ConditionalExpressionImpl <em>Conditional Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ConditionalExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getConditionalExpression()
	 * @generated
	 */
	int CONDITIONAL_EXPRESSION = 135;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION__EXPRESSION = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>True Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION__TRUE_EXPRESSION = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>False Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION__FALSE_EXPRESSION = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Conditional Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Conditional Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITIONAL_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AssignmentExpressionImpl <em>Assignment Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AssignmentExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAssignmentExpression()
	 * @generated
	 */
	int ASSIGNMENT_EXPRESSION = 136;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__LHS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__OP = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION__RHS = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Assignment Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Assignment Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSIGNMENT_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.CommaExpressionImpl <em>Comma Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.CommaExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCommaExpression()
	 * @generated
	 */
	int COMMA_EXPRESSION = 137;

	/**
	 * The feature id for the '<em><b>Exprs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMA_EXPRESSION__EXPRS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Comma Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMA_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMA_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>Comma Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMA_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.TypeDefiningElementImpl <em>Type Defining Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.TypeDefiningElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypeDefiningElement()
	 * @generated
	 */
	int TYPE_DEFINING_ELEMENT = 138;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEFINING_ELEMENT__DEFINED_TYPE = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Defining Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEFINING_ELEMENT_FEATURE_COUNT = TypesPackage.TYPABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type Defining Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_DEFINING_ELEMENT_OPERATION_COUNT = TypesPackage.TYPABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.GenericDeclarationImpl <em>Generic Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.GenericDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getGenericDeclaration()
	 * @generated
	 */
	int GENERIC_DECLARATION = 139;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DECLARATION__DEFINED_TYPE = TYPE_DEFINING_ELEMENT__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DECLARATION__TYPE_VARS = TYPE_DEFINING_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Generic Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DECLARATION_FEATURE_COUNT = TYPE_DEFINING_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Generic Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DECLARATION_OPERATION_COUNT = TYPE_DEFINING_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4TypeDefinitionImpl <em>N4 Type Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4TypeDefinitionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4TypeDefinition()
	 * @generated
	 */
	int N4_TYPE_DEFINITION = 140;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DEFINITION__DEFINED_TYPE = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>N4 Type Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DEFINITION_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DEFINITION___GET_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DEFINITION___GET_ALL_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DEFINITION___IS_EXTERNAL = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>N4 Type Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DEFINITION_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4TypeDeclarationImpl <em>N4 Type Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4TypeDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4TypeDeclaration()
	 * @generated
	 */
	int N4_TYPE_DECLARATION = 141;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION__DEFINED_TYPE = N4_TYPE_DEFINITION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION__ANNOTATION_LIST = N4_TYPE_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION__DECLARED_MODIFIERS = N4_TYPE_DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION__NAME = N4_TYPE_DEFINITION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>N4 Type Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION_FEATURE_COUNT = N4_TYPE_DEFINITION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___GET_ALL_ANNOTATIONS = N4_TYPE_DEFINITION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___GET_ANNOTATIONS = N4_TYPE_DEFINITION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___IS_EXPORTED = N4_TYPE_DEFINITION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___IS_EXPORTED_AS_DEFAULT = N4_TYPE_DEFINITION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___GET_EXPORTED_NAME = N4_TYPE_DEFINITION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___IS_TOPLEVEL = N4_TYPE_DEFINITION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___GET_NAME = N4_TYPE_DEFINITION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION___IS_EXTERNAL = N4_TYPE_DEFINITION_OPERATION_COUNT + 6;

	/**
	 * The number of operations of the '<em>N4 Type Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_DECLARATION_OPERATION_COUNT = N4_TYPE_DEFINITION_OPERATION_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassifierDeclarationImpl <em>N4 Classifier Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4ClassifierDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassifierDeclaration()
	 * @generated
	 */
	int N4_CLASSIFIER_DECLARATION = 142;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION__DEFINED_TYPE = N4_TYPE_DECLARATION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION__ANNOTATION_LIST = N4_TYPE_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION__DECLARED_MODIFIERS = N4_TYPE_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION__NAME = N4_TYPE_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW = N4_TYPE_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION__TYPE_VARS = N4_TYPE_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY = N4_TYPE_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>N4 Classifier Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION_FEATURE_COUNT = N4_TYPE_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_ALL_ANNOTATIONS = N4_TYPE_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_ANNOTATIONS = N4_TYPE_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___IS_EXPORTED = N4_TYPE_DECLARATION___IS_EXPORTED;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___IS_EXPORTED_AS_DEFAULT = N4_TYPE_DECLARATION___IS_EXPORTED_AS_DEFAULT;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_EXPORTED_NAME = N4_TYPE_DECLARATION___GET_EXPORTED_NAME;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___IS_TOPLEVEL = N4_TYPE_DECLARATION___IS_TOPLEVEL;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_NAME = N4_TYPE_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___IS_EXTERNAL = N4_TYPE_DECLARATION___IS_EXTERNAL;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_OWNED_MEMBERS = N4_TYPE_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Owned Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_OWNED_CTOR = N4_TYPE_DECLARATION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Owned Callable Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_OWNED_CALLABLE_CTOR = N4_TYPE_DECLARATION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Owned Methods</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_OWNED_METHODS = N4_TYPE_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Owned Fields</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_OWNED_FIELDS = N4_TYPE_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Owned Getters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_OWNED_GETTERS = N4_TYPE_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Owned Setters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_OWNED_SETTERS = N4_TYPE_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Super Classifier Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_SUPER_CLASSIFIER_REFS = N4_TYPE_DECLARATION_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = N4_TYPE_DECLARATION_OPERATION_COUNT + 8;

	/**
	 * The number of operations of the '<em>N4 Classifier Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DECLARATION_OPERATION_COUNT = N4_TYPE_DECLARATION_OPERATION_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassifierDefinitionImpl <em>N4 Classifier Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4ClassifierDefinitionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassifierDefinition()
	 * @generated
	 */
	int N4_CLASSIFIER_DEFINITION = 143;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION__DEFINED_TYPE = N4_TYPE_DEFINITION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION__OWNED_MEMBERS_RAW = N4_TYPE_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>N4 Classifier Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION_FEATURE_COUNT = N4_TYPE_DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_ANNOTATIONS = N4_TYPE_DEFINITION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_ALL_ANNOTATIONS = N4_TYPE_DEFINITION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___IS_EXTERNAL = N4_TYPE_DEFINITION___IS_EXTERNAL;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_OWNED_MEMBERS = N4_TYPE_DEFINITION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Owned Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_OWNED_CTOR = N4_TYPE_DEFINITION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Owned Callable Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_OWNED_CALLABLE_CTOR = N4_TYPE_DEFINITION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Owned Methods</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_OWNED_METHODS = N4_TYPE_DEFINITION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Owned Fields</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_OWNED_FIELDS = N4_TYPE_DEFINITION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Owned Getters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_OWNED_GETTERS = N4_TYPE_DEFINITION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Owned Setters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_OWNED_SETTERS = N4_TYPE_DEFINITION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Super Classifier Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_SUPER_CLASSIFIER_REFS = N4_TYPE_DEFINITION_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = N4_TYPE_DEFINITION_OPERATION_COUNT + 8;

	/**
	 * The number of operations of the '<em>N4 Classifier Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASSIFIER_DEFINITION_OPERATION_COUNT = N4_TYPE_DEFINITION_OPERATION_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassDefinitionImpl <em>N4 Class Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4ClassDefinitionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassDefinition()
	 * @generated
	 */
	int N4_CLASS_DEFINITION = 144;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION__DEFINED_TYPE = N4_CLASSIFIER_DEFINITION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION__OWNED_MEMBERS_RAW = N4_CLASSIFIER_DEFINITION__OWNED_MEMBERS_RAW;

	/**
	 * The feature id for the '<em><b>Super Class Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION__SUPER_CLASS_REF = N4_CLASSIFIER_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Super Class Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION = N4_CLASSIFIER_DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Implemented Interface Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS = N4_CLASSIFIER_DEFINITION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>N4 Class Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION_FEATURE_COUNT = N4_CLASSIFIER_DEFINITION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_ANNOTATIONS = N4_CLASSIFIER_DEFINITION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_ALL_ANNOTATIONS = N4_CLASSIFIER_DEFINITION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___IS_EXTERNAL = N4_CLASSIFIER_DEFINITION___IS_EXTERNAL;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_OWNED_MEMBERS = N4_CLASSIFIER_DEFINITION___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>Get Owned Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_OWNED_CTOR = N4_CLASSIFIER_DEFINITION___GET_OWNED_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Callable Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_OWNED_CALLABLE_CTOR = N4_CLASSIFIER_DEFINITION___GET_OWNED_CALLABLE_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Methods</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_OWNED_METHODS = N4_CLASSIFIER_DEFINITION___GET_OWNED_METHODS;

	/**
	 * The operation id for the '<em>Get Owned Fields</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_OWNED_FIELDS = N4_CLASSIFIER_DEFINITION___GET_OWNED_FIELDS;

	/**
	 * The operation id for the '<em>Get Owned Getters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_OWNED_GETTERS = N4_CLASSIFIER_DEFINITION___GET_OWNED_GETTERS;

	/**
	 * The operation id for the '<em>Get Owned Setters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_OWNED_SETTERS = N4_CLASSIFIER_DEFINITION___GET_OWNED_SETTERS;

	/**
	 * The operation id for the '<em>Get Defined Type As Class</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_DEFINED_TYPE_AS_CLASS = N4_CLASSIFIER_DEFINITION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Super Classifier Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_SUPER_CLASSIFIER_REFS = N4_CLASSIFIER_DEFINITION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = N4_CLASSIFIER_DEFINITION_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>N4 Class Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DEFINITION_OPERATION_COUNT = N4_CLASSIFIER_DEFINITION_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl <em>N4 Class Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassDeclaration()
	 * @generated
	 */
	int N4_CLASS_DECLARATION = 145;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__DEFINED_TYPE = N4_CLASS_DEFINITION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__OWNED_MEMBERS_RAW = N4_CLASS_DEFINITION__OWNED_MEMBERS_RAW;

	/**
	 * The feature id for the '<em><b>Super Class Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__SUPER_CLASS_REF = N4_CLASS_DEFINITION__SUPER_CLASS_REF;

	/**
	 * The feature id for the '<em><b>Super Class Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__SUPER_CLASS_EXPRESSION = N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Implemented Interface Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__IMPLEMENTED_INTERFACE_REFS = N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__ANNOTATION_LIST = N4_CLASS_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__DECLARED_MODIFIERS = N4_CLASS_DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__NAME = N4_CLASS_DEFINITION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__TYPE_VARS = N4_CLASS_DEFINITION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__TYPING_STRATEGY = N4_CLASS_DEFINITION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION__DECLARED_VERSION = N4_CLASS_DEFINITION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>N4 Class Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION_FEATURE_COUNT = N4_CLASS_DEFINITION_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_ALL_ANNOTATIONS = N4_CLASS_DEFINITION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_OWNED_MEMBERS = N4_CLASS_DEFINITION___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>Get Owned Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_OWNED_CTOR = N4_CLASS_DEFINITION___GET_OWNED_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Callable Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_OWNED_CALLABLE_CTOR = N4_CLASS_DEFINITION___GET_OWNED_CALLABLE_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Methods</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_OWNED_METHODS = N4_CLASS_DEFINITION___GET_OWNED_METHODS;

	/**
	 * The operation id for the '<em>Get Owned Fields</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_OWNED_FIELDS = N4_CLASS_DEFINITION___GET_OWNED_FIELDS;

	/**
	 * The operation id for the '<em>Get Owned Getters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_OWNED_GETTERS = N4_CLASS_DEFINITION___GET_OWNED_GETTERS;

	/**
	 * The operation id for the '<em>Get Owned Setters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_OWNED_SETTERS = N4_CLASS_DEFINITION___GET_OWNED_SETTERS;

	/**
	 * The operation id for the '<em>Get Defined Type As Class</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_DEFINED_TYPE_AS_CLASS = N4_CLASS_DEFINITION___GET_DEFINED_TYPE_AS_CLASS;

	/**
	 * The operation id for the '<em>Get Super Classifier Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_SUPER_CLASSIFIER_REFS = N4_CLASS_DEFINITION___GET_SUPER_CLASSIFIER_REFS;

	/**
	 * The operation id for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = N4_CLASS_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_ANNOTATIONS = N4_CLASS_DEFINITION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___IS_EXPORTED = N4_CLASS_DEFINITION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___IS_EXPORTED_AS_DEFAULT = N4_CLASS_DEFINITION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_EXPORTED_NAME = N4_CLASS_DEFINITION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___IS_TOPLEVEL = N4_CLASS_DEFINITION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_NAME = N4_CLASS_DEFINITION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___IS_EXTERNAL = N4_CLASS_DEFINITION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___HAS_DECLARED_VERSION = N4_CLASS_DEFINITION_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = N4_CLASS_DEFINITION_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___IS_ABSTRACT = N4_CLASS_DEFINITION_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION___GET_VERSION = N4_CLASS_DEFINITION_OPERATION_COUNT + 11;

	/**
	 * The number of operations of the '<em>N4 Class Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_DECLARATION_OPERATION_COUNT = N4_CLASS_DEFINITION_OPERATION_COUNT + 12;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassExpressionImpl <em>N4 Class Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4ClassExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassExpression()
	 * @generated
	 */
	int N4_CLASS_EXPRESSION = 146;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION__DEFINED_TYPE = N4_CLASS_DEFINITION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION__OWNED_MEMBERS_RAW = N4_CLASS_DEFINITION__OWNED_MEMBERS_RAW;

	/**
	 * The feature id for the '<em><b>Super Class Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION__SUPER_CLASS_REF = N4_CLASS_DEFINITION__SUPER_CLASS_REF;

	/**
	 * The feature id for the '<em><b>Super Class Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION__SUPER_CLASS_EXPRESSION = N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Implemented Interface Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION__IMPLEMENTED_INTERFACE_REFS = N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION__ANNOTATION_LIST = N4_CLASS_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION__NAME = N4_CLASS_DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>N4 Class Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION_FEATURE_COUNT = N4_CLASS_DEFINITION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_ALL_ANNOTATIONS = N4_CLASS_DEFINITION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___IS_EXTERNAL = N4_CLASS_DEFINITION___IS_EXTERNAL;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_OWNED_MEMBERS = N4_CLASS_DEFINITION___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>Get Owned Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_OWNED_CTOR = N4_CLASS_DEFINITION___GET_OWNED_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Callable Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_OWNED_CALLABLE_CTOR = N4_CLASS_DEFINITION___GET_OWNED_CALLABLE_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Methods</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_OWNED_METHODS = N4_CLASS_DEFINITION___GET_OWNED_METHODS;

	/**
	 * The operation id for the '<em>Get Owned Fields</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_OWNED_FIELDS = N4_CLASS_DEFINITION___GET_OWNED_FIELDS;

	/**
	 * The operation id for the '<em>Get Owned Getters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_OWNED_GETTERS = N4_CLASS_DEFINITION___GET_OWNED_GETTERS;

	/**
	 * The operation id for the '<em>Get Owned Setters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_OWNED_SETTERS = N4_CLASS_DEFINITION___GET_OWNED_SETTERS;

	/**
	 * The operation id for the '<em>Get Defined Type As Class</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_DEFINED_TYPE_AS_CLASS = N4_CLASS_DEFINITION___GET_DEFINED_TYPE_AS_CLASS;

	/**
	 * The operation id for the '<em>Get Super Classifier Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_SUPER_CLASSIFIER_REFS = N4_CLASS_DEFINITION___GET_SUPER_CLASSIFIER_REFS;

	/**
	 * The operation id for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = N4_CLASS_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = N4_CLASS_DEFINITION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_ANNOTATIONS = N4_CLASS_DEFINITION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION___GET_NAME = N4_CLASS_DEFINITION_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>N4 Class Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_CLASS_EXPRESSION_OPERATION_COUNT = N4_CLASS_DEFINITION_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4InterfaceDeclarationImpl <em>N4 Interface Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4InterfaceDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4InterfaceDeclaration()
	 * @generated
	 */
	int N4_INTERFACE_DECLARATION = 147;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__DEFINED_TYPE = N4_CLASSIFIER_DECLARATION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__ANNOTATION_LIST = N4_CLASSIFIER_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__DECLARED_MODIFIERS = N4_CLASSIFIER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__NAME = N4_CLASSIFIER_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Owned Members Raw</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__OWNED_MEMBERS_RAW = N4_CLASSIFIER_DECLARATION__OWNED_MEMBERS_RAW;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__TYPE_VARS = N4_CLASSIFIER_DECLARATION__TYPE_VARS;

	/**
	 * The feature id for the '<em><b>Typing Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__TYPING_STRATEGY = N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__DECLARED_VERSION = N4_CLASSIFIER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Super Interface Refs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS = N4_CLASSIFIER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>N4 Interface Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION_FEATURE_COUNT = N4_CLASSIFIER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_ALL_ANNOTATIONS = N4_CLASSIFIER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_ANNOTATIONS = N4_CLASSIFIER_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___IS_EXPORTED = N4_CLASSIFIER_DECLARATION___IS_EXPORTED;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___IS_EXPORTED_AS_DEFAULT = N4_CLASSIFIER_DECLARATION___IS_EXPORTED_AS_DEFAULT;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_EXPORTED_NAME = N4_CLASSIFIER_DECLARATION___GET_EXPORTED_NAME;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___IS_TOPLEVEL = N4_CLASSIFIER_DECLARATION___IS_TOPLEVEL;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_NAME = N4_CLASSIFIER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___IS_EXTERNAL = N4_CLASSIFIER_DECLARATION___IS_EXTERNAL;

	/**
	 * The operation id for the '<em>Get Owned Members</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_OWNED_MEMBERS = N4_CLASSIFIER_DECLARATION___GET_OWNED_MEMBERS;

	/**
	 * The operation id for the '<em>Get Owned Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_OWNED_CTOR = N4_CLASSIFIER_DECLARATION___GET_OWNED_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Callable Ctor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_OWNED_CALLABLE_CTOR = N4_CLASSIFIER_DECLARATION___GET_OWNED_CALLABLE_CTOR;

	/**
	 * The operation id for the '<em>Get Owned Methods</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_OWNED_METHODS = N4_CLASSIFIER_DECLARATION___GET_OWNED_METHODS;

	/**
	 * The operation id for the '<em>Get Owned Fields</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_OWNED_FIELDS = N4_CLASSIFIER_DECLARATION___GET_OWNED_FIELDS;

	/**
	 * The operation id for the '<em>Get Owned Getters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_OWNED_GETTERS = N4_CLASSIFIER_DECLARATION___GET_OWNED_GETTERS;

	/**
	 * The operation id for the '<em>Get Owned Setters</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_OWNED_SETTERS = N4_CLASSIFIER_DECLARATION___GET_OWNED_SETTERS;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___HAS_DECLARED_VERSION = N4_CLASSIFIER_DECLARATION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = N4_CLASSIFIER_DECLARATION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Defined Type As Interface</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_DEFINED_TYPE_AS_INTERFACE = N4_CLASSIFIER_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Super Classifier Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_SUPER_CLASSIFIER_REFS = N4_CLASSIFIER_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = N4_CLASSIFIER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION___GET_VERSION = N4_CLASSIFIER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The number of operations of the '<em>N4 Interface Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_INTERFACE_DECLARATION_OPERATION_COUNT = N4_CLASSIFIER_DECLARATION_OPERATION_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4EnumDeclarationImpl <em>N4 Enum Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4EnumDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4EnumDeclaration()
	 * @generated
	 */
	int N4_ENUM_DECLARATION = 148;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION__DEFINED_TYPE = N4_TYPE_DECLARATION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION__ANNOTATION_LIST = N4_TYPE_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION__DECLARED_MODIFIERS = N4_TYPE_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION__NAME = N4_TYPE_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION__DECLARED_VERSION = N4_TYPE_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Literals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION__LITERALS = N4_TYPE_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>N4 Enum Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION_FEATURE_COUNT = N4_TYPE_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___GET_ALL_ANNOTATIONS = N4_TYPE_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___GET_ANNOTATIONS = N4_TYPE_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___IS_EXPORTED = N4_TYPE_DECLARATION___IS_EXPORTED;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___IS_EXPORTED_AS_DEFAULT = N4_TYPE_DECLARATION___IS_EXPORTED_AS_DEFAULT;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___GET_EXPORTED_NAME = N4_TYPE_DECLARATION___GET_EXPORTED_NAME;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___IS_TOPLEVEL = N4_TYPE_DECLARATION___IS_TOPLEVEL;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___GET_NAME = N4_TYPE_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___IS_EXTERNAL = N4_TYPE_DECLARATION___IS_EXTERNAL;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___HAS_DECLARED_VERSION = N4_TYPE_DECLARATION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = N4_TYPE_DECLARATION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Defined Type As Enum</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___GET_DEFINED_TYPE_AS_ENUM = N4_TYPE_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION___GET_VERSION = N4_TYPE_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The number of operations of the '<em>N4 Enum Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_DECLARATION_OPERATION_COUNT = N4_TYPE_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4EnumLiteralImpl <em>N4 Enum Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4EnumLiteralImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4EnumLiteral()
	 * @generated
	 */
	int N4_ENUM_LITERAL = 149;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_LITERAL__NAME = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_LITERAL__VALUE_EXPRESSION = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Defined Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_LITERAL__DEFINED_LITERAL = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>N4 Enum Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_LITERAL_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_LITERAL___GET_NAME = NAMED_ELEMENT___GET_NAME;

	/**
	 * The number of operations of the '<em>N4 Enum Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_ENUM_LITERAL_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4TypeAliasDeclarationImpl <em>N4 Type Alias Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4TypeAliasDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4TypeAliasDeclaration()
	 * @generated
	 */
	int N4_TYPE_ALIAS_DECLARATION = 150;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION__DEFINED_TYPE = N4_TYPE_DECLARATION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION__ANNOTATION_LIST = N4_TYPE_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION__DECLARED_MODIFIERS = N4_TYPE_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION__NAME = N4_TYPE_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION__TYPE_VARS = N4_TYPE_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Actual Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION__ACTUAL_TYPE_REF = N4_TYPE_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>N4 Type Alias Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION_FEATURE_COUNT = N4_TYPE_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___GET_ALL_ANNOTATIONS = N4_TYPE_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___GET_ANNOTATIONS = N4_TYPE_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Is Exported</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___IS_EXPORTED = N4_TYPE_DECLARATION___IS_EXPORTED;

	/**
	 * The operation id for the '<em>Is Exported As Default</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___IS_EXPORTED_AS_DEFAULT = N4_TYPE_DECLARATION___IS_EXPORTED_AS_DEFAULT;

	/**
	 * The operation id for the '<em>Get Exported Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___GET_EXPORTED_NAME = N4_TYPE_DECLARATION___GET_EXPORTED_NAME;

	/**
	 * The operation id for the '<em>Is Toplevel</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___IS_TOPLEVEL = N4_TYPE_DECLARATION___IS_TOPLEVEL;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___GET_NAME = N4_TYPE_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Is External</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION___IS_EXTERNAL = N4_TYPE_DECLARATION___IS_EXTERNAL;

	/**
	 * The number of operations of the '<em>N4 Type Alias Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_TYPE_ALIAS_DECLARATION_OPERATION_COUNT = N4_TYPE_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ModifiableElementImpl <em>Modifiable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ModifiableElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getModifiableElement()
	 * @generated
	 */
	int MODIFIABLE_ELEMENT = 151;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_ELEMENT__DECLARED_MODIFIERS = 0;

	/**
	 * The number of structural features of the '<em>Modifiable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Modifiable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODIFIABLE_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4MemberDeclarationImpl <em>N4 Member Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4MemberDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4MemberDeclaration()
	 * @generated
	 */
	int N4_MEMBER_DECLARATION = 152;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION__DECLARED_MODIFIERS = ANNOTABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION__OWNER = ANNOTABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>N4 Member Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION_FEATURE_COUNT = ANNOTABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___GET_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___GET_ALL_ANNOTATIONS = ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___GET_DECLARED_TYPE_REF = ANNOTABLE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___GET_NAME = ANNOTABLE_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_ABSTRACT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_DECLARED_STATIC = ANNOTABLE_ELEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_STATIC = ANNOTABLE_ELEMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_DECLARED_FINAL = ANNOTABLE_ELEMENT_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_FINAL = ANNOTABLE_ELEMENT_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_CONSTRUCTOR = ANNOTABLE_ELEMENT_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR = ANNOTABLE_ELEMENT_OPERATION_COUNT + 10;

	/**
	 * The number of operations of the '<em>N4 Member Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_DECLARATION_OPERATION_COUNT = ANNOTABLE_ELEMENT_OPERATION_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableN4MemberDeclarationImpl <em>Annotable N4 Member Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.AnnotableN4MemberDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableN4MemberDeclaration()
	 * @generated
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION = 153;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION__DECLARED_MODIFIERS = N4_MEMBER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION__OWNER = N4_MEMBER_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST = N4_MEMBER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Annotable N4 Member Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT = N4_MEMBER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___GET_ALL_ANNOTATIONS = N4_MEMBER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___GET_DECLARED_TYPE_REF = N4_MEMBER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___GET_NAME = N4_MEMBER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT = N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_ABSTRACT = N4_MEMBER_DECLARATION___IS_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_STATIC = N4_MEMBER_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_STATIC = N4_MEMBER_DECLARATION___IS_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_FINAL = N4_MEMBER_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_FINAL = N4_MEMBER_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_CONSTRUCTOR = N4_MEMBER_DECLARATION___IS_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR = N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION___GET_ANNOTATIONS = N4_MEMBER_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Annotable N4 Member Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT = N4_MEMBER_DECLARATION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4MemberAnnotationListImpl <em>N4 Member Annotation List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4MemberAnnotationListImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4MemberAnnotationList()
	 * @generated
	 */
	int N4_MEMBER_ANNOTATION_LIST = 154;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST__ANNOTATIONS = ABSTRACT_ANNOTATION_LIST__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST__DECLARED_MODIFIERS = ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST__OWNER = ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>N4 Member Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST_FEATURE_COUNT = ABSTRACT_ANNOTATION_LIST_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___GET_ANNOTATIONS = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___GET_ALL_ANNOTATIONS = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_DECLARED_ABSTRACT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_ABSTRACT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_DECLARED_STATIC = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_STATIC = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_DECLARED_FINAL = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_FINAL = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_CONSTRUCTOR = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___IS_CALLABLE_CONSTRUCTOR = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___GET_DEFINED_TYPE_ELEMENT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 13;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___GET_DECLARED_TYPE_REF = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 14;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST___GET_NAME = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 15;

	/**
	 * The number of operations of the '<em>N4 Member Annotation List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_MEMBER_ANNOTATION_LIST_OPERATION_COUNT = ABSTRACT_ANNOTATION_LIST_OPERATION_COUNT + 16;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4FieldDeclarationImpl <em>N4 Field Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4FieldDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4FieldDeclaration()
	 * @generated
	 */
	int N4_FIELD_DECLARATION = 155;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__DECLARED_MODIFIERS = ANNOTABLE_N4_MEMBER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__OWNER = ANNOTABLE_N4_MEMBER_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__ANNOTATION_LIST = ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__DECLARED_TYPE_REF = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__DECLARED_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Defined Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__DEFINED_FIELD = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__DECLARED_OPTIONAL = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION__EXPRESSION = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>N4 Field Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION_FEATURE_COUNT = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___GET_ALL_ANNOTATIONS = ANNOTABLE_N4_MEMBER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___GET_DECLARED_TYPE_REF = ANNOTABLE_N4_MEMBER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_DECLARED_ABSTRACT = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_ABSTRACT = ANNOTABLE_N4_MEMBER_DECLARATION___IS_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_DECLARED_STATIC = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_DECLARED_FINAL = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_FINAL = ANNOTABLE_N4_MEMBER_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_CONSTRUCTOR = ANNOTABLE_N4_MEMBER_DECLARATION___IS_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_CALLABLE_CONSTRUCTOR = ANNOTABLE_N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___GET_ANNOTATIONS = ANNOTABLE_N4_MEMBER_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___GET_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___GET_DEFINED_TYPE_ELEMENT = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_CONST = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_STATIC = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Valid</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_VALID = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION___IS_VALID_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>N4 Field Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_DECLARATION_OPERATION_COUNT = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl <em>Method Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMethodDeclaration()
	 * @generated
	 */
	int METHOD_DECLARATION = 156;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__BODY = FUNCTION_DEFINITION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__LOK = FUNCTION_DEFINITION__LOK;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__DEFINED_TYPE = FUNCTION_DEFINITION__DEFINED_TYPE;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__DECLARED_VERSION = FUNCTION_DEFINITION__DECLARED_VERSION;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__FPARS = FUNCTION_DEFINITION__FPARS;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__RETURN_TYPE_REF = FUNCTION_DEFINITION__RETURN_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__GENERATOR = FUNCTION_DEFINITION__GENERATOR;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__DECLARED_ASYNC = FUNCTION_DEFINITION__DECLARED_ASYNC;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__TYPE_VARS = FUNCTION_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__DECLARED_TYPE_REF = FUNCTION_DEFINITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION__DECLARED_NAME = FUNCTION_DEFINITION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION_FEATURE_COUNT = FUNCTION_DEFINITION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_ANNOTATIONS = FUNCTION_DEFINITION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_ALL_ANNOTATIONS = FUNCTION_DEFINITION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = FUNCTION_DEFINITION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = FUNCTION_DEFINITION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = FUNCTION_DEFINITION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___HAS_DECLARED_VERSION = FUNCTION_DEFINITION___HAS_DECLARED_VERSION;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = FUNCTION_DEFINITION___GET_DECLARED_VERSION_OR_ZERO;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL = FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___IS_ASYNC = FUNCTION_DEFINITION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_DEFINED_FUNCTION = FUNCTION_DEFINITION___GET_DEFINED_FUNCTION;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_DECLARED_TYPE_REF = FUNCTION_DEFINITION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_NAME = FUNCTION_DEFINITION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = FUNCTION_DEFINITION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___IS_VALID_NAME = FUNCTION_DEFINITION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Exists Explicit Super Call</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL = FUNCTION_DEFINITION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT = FUNCTION_DEFINITION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION___IS_STATIC = FUNCTION_DEFINITION_OPERATION_COUNT + 7;

	/**
	 * The number of operations of the '<em>Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_DECLARATION_OPERATION_COUNT = FUNCTION_DEFINITION_OPERATION_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl <em>N4 Method Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4MethodDeclaration()
	 * @generated
	 */
	int N4_METHOD_DECLARATION = 157;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__DECLARED_MODIFIERS = ANNOTABLE_N4_MEMBER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__OWNER = ANNOTABLE_N4_MEMBER_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__ANNOTATION_LIST = ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__BODY = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__LOK = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Defined Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__DEFINED_TYPE = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__DECLARED_VERSION = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Fpars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__FPARS = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Return Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__RETURN_TYPE_REF = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__GENERATOR = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Declared Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__DECLARED_ASYNC = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Type Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__TYPE_VARS = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__DECLARED_TYPE_REF = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION__DECLARED_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>N4 Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION_FEATURE_COUNT = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 11;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_ALL_ANNOTATIONS = ANNOTABLE_N4_MEMBER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_DECLARED_TYPE_REF = ANNOTABLE_N4_MEMBER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_DECLARED_ABSTRACT = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_DECLARED_STATIC = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_DECLARED_FINAL = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_FINAL = ANNOTABLE_N4_MEMBER_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_ANNOTATIONS = ANNOTABLE_N4_MEMBER_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___HAS_DECLARED_VERSION = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_DECLARED_VERSION_OR_ZERO = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_RETURN_VALUE_OPTIONAL = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_ASYNC = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Defined Function</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_DEFINED_FUNCTION = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Exists Explicit Super Call</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 14;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 15;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_ABSTRACT = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 17;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_CONSTRUCTOR = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 18;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_CALLABLE_CONSTRUCTOR = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 19;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_STATIC = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 20;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION___IS_VALID_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 21;

	/**
	 * The number of operations of the '<em>N4 Method Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_METHOD_DECLARATION_OPERATION_COUNT = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 22;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4FieldAccessorImpl <em>N4 Field Accessor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4FieldAccessorImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4FieldAccessor()
	 * @generated
	 */
	int N4_FIELD_ACCESSOR = 158;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR__DECLARED_MODIFIERS = ANNOTABLE_N4_MEMBER_DECLARATION__DECLARED_MODIFIERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR__OWNER = ANNOTABLE_N4_MEMBER_DECLARATION__OWNER;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR__ANNOTATION_LIST = ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR__BODY = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR__LOK = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR__DECLARED_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR__DECLARED_OPTIONAL = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>N4 Field Accessor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR_FEATURE_COUNT = ANNOTABLE_N4_MEMBER_DECLARATION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_ALL_ANNOTATIONS = ANNOTABLE_N4_MEMBER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_DEFINED_TYPE_ELEMENT = ANNOTABLE_N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_DECLARED_ABSTRACT = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_DECLARED_STATIC = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_STATIC;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_STATIC = ANNOTABLE_N4_MEMBER_DECLARATION___IS_STATIC;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_DECLARED_FINAL = ANNOTABLE_N4_MEMBER_DECLARATION___IS_DECLARED_FINAL;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_FINAL = ANNOTABLE_N4_MEMBER_DECLARATION___IS_FINAL;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_CONSTRUCTOR = ANNOTABLE_N4_MEMBER_DECLARATION___IS_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_CALLABLE_CONSTRUCTOR = ANNOTABLE_N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_ANNOTATIONS = ANNOTABLE_N4_MEMBER_DECLARATION___GET_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_ASYNC = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___HAS_COMPUTED_PROPERTY_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_DECLARED_TYPE_REF = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___GET_DEFINED_ACCESSOR = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_OPTIONAL = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_ABSTRACT = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 12;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR___IS_VALID_NAME = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 13;

	/**
	 * The number of operations of the '<em>N4 Field Accessor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_FIELD_ACCESSOR_OPERATION_COUNT = ANNOTABLE_N4_MEMBER_DECLARATION_OPERATION_COUNT + 14;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4GetterDeclarationImpl <em>N4 Getter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4GetterDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4GetterDeclaration()
	 * @generated
	 */
	int N4_GETTER_DECLARATION = 159;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__BODY = GETTER_DECLARATION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__LOK = GETTER_DECLARATION__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__DECLARED_NAME = GETTER_DECLARATION__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__DECLARED_OPTIONAL = GETTER_DECLARATION__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__DECLARED_TYPE_REF = GETTER_DECLARATION__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Defined Getter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__DEFINED_GETTER = GETTER_DECLARATION__DEFINED_GETTER;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__DECLARED_MODIFIERS = GETTER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__OWNER = GETTER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION__ANNOTATION_LIST = GETTER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>N4 Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION_FEATURE_COUNT = GETTER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_ALL_ANNOTATIONS = GETTER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = GETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = GETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = GETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_ASYNC = GETTER_DECLARATION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = GETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_NAME = GETTER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = GETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_DECLARED_TYPE_REF = GETTER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_OPTIONAL = GETTER_DECLARATION___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_DEFINED_ACCESSOR = GETTER_DECLARATION___GET_DEFINED_ACCESSOR;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_DECLARED_ABSTRACT = GETTER_DECLARATION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_DECLARED_STATIC = GETTER_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_STATIC = GETTER_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_DECLARED_FINAL = GETTER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_FINAL = GETTER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_CONSTRUCTOR = GETTER_DECLARATION_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_CALLABLE_CONSTRUCTOR = GETTER_DECLARATION_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_ANNOTATIONS = GETTER_DECLARATION_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_ABSTRACT = GETTER_DECLARATION_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___IS_VALID_NAME = GETTER_DECLARATION_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = GETTER_DECLARATION_OPERATION_COUNT + 12;

	/**
	 * The number of operations of the '<em>N4 Getter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_GETTER_DECLARATION_OPERATION_COUNT = GETTER_DECLARATION_OPERATION_COUNT + 13;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.N4SetterDeclarationImpl <em>N4 Setter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.N4SetterDeclarationImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4SetterDeclaration()
	 * @generated
	 */
	int N4_SETTER_DECLARATION = 160;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__BODY = SETTER_DECLARATION__BODY;

	/**
	 * The feature id for the '<em><b>lok</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__LOK = SETTER_DECLARATION__LOK;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__DECLARED_NAME = SETTER_DECLARATION__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__DECLARED_OPTIONAL = SETTER_DECLARATION__DECLARED_OPTIONAL;

	/**
	 * The feature id for the '<em><b>Defined Setter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__DEFINED_SETTER = SETTER_DECLARATION__DEFINED_SETTER;

	/**
	 * The feature id for the '<em><b>Fpar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__FPAR = SETTER_DECLARATION__FPAR;

	/**
	 * The feature id for the '<em><b>Declared Modifiers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__DECLARED_MODIFIERS = SETTER_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__OWNER = SETTER_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotation List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION__ANNOTATION_LIST = SETTER_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>N4 Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION_FEATURE_COUNT = SETTER_DECLARATION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get All Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_ALL_ANNOTATIONS = SETTER_DECLARATION___GET_ALL_ANNOTATIONS;

	/**
	 * The operation id for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = SETTER_DECLARATION___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS;

	/**
	 * The operation id for the '<em>Get Local Arguments Variable</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE = SETTER_DECLARATION___GET_LOCAL_ARGUMENTS_VARIABLE;

	/**
	 * The operation id for the '<em>Is Return Value Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL = SETTER_DECLARATION___IS_RETURN_VALUE_OPTIONAL;

	/**
	 * The operation id for the '<em>Is Async</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_ASYNC = SETTER_DECLARATION___IS_ASYNC;

	/**
	 * The operation id for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR = SETTER_DECLARATION___GET_DEFINED_FUNCTION_OR_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_NAME = SETTER_DECLARATION___GET_NAME;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME = SETTER_DECLARATION___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Is Optional</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_OPTIONAL = SETTER_DECLARATION___IS_OPTIONAL;

	/**
	 * The operation id for the '<em>Get Defined Accessor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_DEFINED_ACCESSOR = SETTER_DECLARATION___GET_DEFINED_ACCESSOR;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_DECLARED_TYPE_REF = SETTER_DECLARATION___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Is Declared Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_DECLARED_ABSTRACT = SETTER_DECLARATION_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Declared Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_DECLARED_STATIC = SETTER_DECLARATION_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Is Static</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_STATIC = SETTER_DECLARATION_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Declared Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_DECLARED_FINAL = SETTER_DECLARATION_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Is Final</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_FINAL = SETTER_DECLARATION_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Is Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_CONSTRUCTOR = SETTER_DECLARATION_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Is Callable Constructor</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_CALLABLE_CONSTRUCTOR = SETTER_DECLARATION_OPERATION_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Annotations</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_ANNOTATIONS = SETTER_DECLARATION_OPERATION_COUNT + 9;

	/**
	 * The operation id for the '<em>Is Abstract</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_ABSTRACT = SETTER_DECLARATION_OPERATION_COUNT + 10;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___IS_VALID_NAME = SETTER_DECLARATION_OPERATION_COUNT + 11;

	/**
	 * The operation id for the '<em>Get Defined Type Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = SETTER_DECLARATION_OPERATION_COUNT + 12;

	/**
	 * The number of operations of the '<em>N4 Setter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int N4_SETTER_DECLARATION_OPERATION_COUNT = SETTER_DECLARATION_OPERATION_COUNT + 13;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BindingPatternImpl <em>Binding Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BindingPatternImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBindingPattern()
	 * @generated
	 */
	int BINDING_PATTERN = 161;

	/**
	 * The number of structural features of the '<em>Binding Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PATTERN_FEATURE_COUNT = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Binding Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PATTERN_OPERATION_COUNT = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ObjectBindingPatternImpl <em>Object Binding Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ObjectBindingPatternImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getObjectBindingPattern()
	 * @generated
	 */
	int OBJECT_BINDING_PATTERN = 162;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_BINDING_PATTERN__PROPERTIES = BINDING_PATTERN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Object Binding Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_BINDING_PATTERN_FEATURE_COUNT = BINDING_PATTERN_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Object Binding Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_BINDING_PATTERN_OPERATION_COUNT = BINDING_PATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.ArrayBindingPatternImpl <em>Array Binding Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.ArrayBindingPatternImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayBindingPattern()
	 * @generated
	 */
	int ARRAY_BINDING_PATTERN = 163;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_BINDING_PATTERN__ELEMENTS = BINDING_PATTERN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Array Binding Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_BINDING_PATTERN_FEATURE_COUNT = BINDING_PATTERN_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Array Binding Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_BINDING_PATTERN_OPERATION_COUNT = BINDING_PATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BindingPropertyImpl <em>Binding Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BindingPropertyImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBindingProperty()
	 * @generated
	 */
	int BINDING_PROPERTY = 164;

	/**
	 * The feature id for the '<em><b>Declared Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PROPERTY__DECLARED_NAME = PROPERTY_NAME_OWNER__DECLARED_NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PROPERTY__VALUE = PROPERTY_NAME_OWNER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Binding Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PROPERTY_FEATURE_COUNT = PROPERTY_NAME_OWNER_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Has Computed Property Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PROPERTY___HAS_COMPUTED_PROPERTY_NAME = PROPERTY_NAME_OWNER___HAS_COMPUTED_PROPERTY_NAME;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PROPERTY___GET_NAME = PROPERTY_NAME_OWNER_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Is Valid Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PROPERTY___IS_VALID_NAME = PROPERTY_NAME_OWNER_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Binding Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_PROPERTY_OPERATION_COUNT = PROPERTY_NAME_OWNER_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.BindingElementImpl <em>Binding Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.BindingElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBindingElement()
	 * @generated
	 */
	int BINDING_ELEMENT = 165;

	/**
	 * The feature id for the '<em><b>Rest</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_ELEMENT__REST = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Var Decl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_ELEMENT__VAR_DECL = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Nested Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_ELEMENT__NESTED_PATTERN = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_ELEMENT__EXPRESSION = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Binding Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_ELEMENT_FEATURE_COUNT = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Elision</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_ELEMENT___IS_ELISION = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Binding Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_ELEMENT_OPERATION_COUNT = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXChildImpl <em>JSX Child</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXChildImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXChild()
	 * @generated
	 */
	int JSX_CHILD = 166;

	/**
	 * The number of structural features of the '<em>JSX Child</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_CHILD_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>JSX Child</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_CHILD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXElementNameImpl <em>JSX Element Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXElementNameImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXElementName()
	 * @generated
	 */
	int JSX_ELEMENT_NAME = 167;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_NAME__EXPRESSION = 0;

	/**
	 * The number of structural features of the '<em>JSX Element Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_NAME_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>JSX Element Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_NAME_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXTextImpl <em>JSX Text</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXTextImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXText()
	 * @generated
	 */
	int JSX_TEXT = 168;

	/**
	 * The number of structural features of the '<em>JSX Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_TEXT_FEATURE_COUNT = JSX_CHILD_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>JSX Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_TEXT_OPERATION_COUNT = JSX_CHILD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXExpressionImpl <em>JSX Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXExpressionImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXExpression()
	 * @generated
	 */
	int JSX_EXPRESSION = 169;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_EXPRESSION__EXPRESSION = JSX_CHILD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JSX Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_EXPRESSION_FEATURE_COUNT = JSX_CHILD_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>JSX Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_EXPRESSION_OPERATION_COUNT = JSX_CHILD_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXAttributeImpl <em>JSX Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXAttributeImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXAttribute()
	 * @generated
	 */
	int JSX_ATTRIBUTE = 170;

	/**
	 * The number of structural features of the '<em>JSX Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ATTRIBUTE_FEATURE_COUNT = CONTROL_FLOW_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>JSX Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ATTRIBUTE_OPERATION_COUNT = CONTROL_FLOW_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl <em>JSX Property Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXPropertyAttribute()
	 * @generated
	 */
	int JSX_PROPERTY_ATTRIBUTE = 171;

	/**
	 * The feature id for the '<em><b>Composed Member Cache</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__COMPOSED_MEMBER_CACHE = JSX_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__PROPERTY = JSX_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Property As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT = JSX_ATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Jsx Attribute Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE = JSX_ATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>JSX Property Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE_FEATURE_COUNT = JSX_ATTRIBUTE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>JSX Property Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_PROPERTY_ATTRIBUTE_OPERATION_COUNT = JSX_ATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXSpreadAttributeImpl <em>JSX Spread Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXSpreadAttributeImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXSpreadAttribute()
	 * @generated
	 */
	int JSX_SPREAD_ATTRIBUTE = 172;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_SPREAD_ATTRIBUTE__EXPRESSION = JSX_ATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JSX Spread Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_SPREAD_ATTRIBUTE_FEATURE_COUNT = JSX_ATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>JSX Spread Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_SPREAD_ATTRIBUTE_OPERATION_COUNT = JSX_ATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXAbstractElementImpl <em>JSX Abstract Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXAbstractElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXAbstractElement()
	 * @generated
	 */
	int JSX_ABSTRACT_ELEMENT = 173;

	/**
	 * The feature id for the '<em><b>Jsx Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ABSTRACT_ELEMENT__JSX_CHILDREN = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JSX Abstract Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ABSTRACT_ELEMENT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ABSTRACT_ELEMENT___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>JSX Abstract Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ABSTRACT_ELEMENT_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXElementImpl <em>JSX Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXElement()
	 * @generated
	 */
	int JSX_ELEMENT = 174;

	/**
	 * The feature id for the '<em><b>Jsx Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_CHILDREN = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Jsx Element Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_ELEMENT_NAME = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Jsx Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_ATTRIBUTES = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Jsx Closing Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT__JSX_CLOSING_NAME = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>JSX Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The number of operations of the '<em>JSX Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_ELEMENT_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.JSXFragmentImpl <em>JSX Fragment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.JSXFragmentImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXFragment()
	 * @generated
	 */
	int JSX_FRAGMENT = 175;

	/**
	 * The feature id for the '<em><b>Jsx Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_FRAGMENT__JSX_CHILDREN = JSX_CHILD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JSX Fragment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_FRAGMENT_FEATURE_COUNT = JSX_CHILD_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_FRAGMENT___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = JSX_CHILD_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>JSX Fragment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JSX_FRAGMENT_OPERATION_COUNT = JSX_CHILD_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VersionedElementImpl <em>Versioned Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VersionedElementImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVersionedElement()
	 * @generated
	 */
	int VERSIONED_ELEMENT = 176;

	/**
	 * The feature id for the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_ELEMENT__DECLARED_VERSION = 0;

	/**
	 * The number of structural features of the '<em>Versioned Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Has Declared Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_ELEMENT___HAS_DECLARED_VERSION = 0;

	/**
	 * The operation id for the '<em>Get Declared Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_ELEMENT___GET_DECLARED_VERSION_OR_ZERO = 1;

	/**
	 * The number of operations of the '<em>Versioned Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_ELEMENT_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.VersionedIdentifierRefImpl <em>Versioned Identifier Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.VersionedIdentifierRefImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVersionedIdentifierRef()
	 * @generated
	 */
	int VERSIONED_IDENTIFIER_REF = 177;

	/**
	 * The feature id for the '<em><b>Strict Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF__STRICT_MODE = IDENTIFIER_REF__STRICT_MODE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF__ID = IDENTIFIER_REF__ID;

	/**
	 * The feature id for the '<em><b>Id As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF__ID_AS_TEXT = IDENTIFIER_REF__ID_AS_TEXT;

	/**
	 * The feature id for the '<em><b>Origin Import</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF__ORIGIN_IMPORT = IDENTIFIER_REF__ORIGIN_IMPORT;

	/**
	 * The feature id for the '<em><b>Requested Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF__REQUESTED_VERSION = IDENTIFIER_REF_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Versioned Identifier Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_FEATURE_COUNT = IDENTIFIER_REF_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Target Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF___GET_TARGET_ELEMENT = IDENTIFIER_REF___GET_TARGET_ELEMENT;

	/**
	 * The operation id for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = IDENTIFIER_REF___IS_VALID_SIMPLE_ASSIGNMENT_TARGET;

	/**
	 * The operation id for the '<em>Has Requested Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF___HAS_REQUESTED_VERSION = IDENTIFIER_REF_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Requested Version Or Zero</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF___GET_REQUESTED_VERSION_OR_ZERO = IDENTIFIER_REF_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Version</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF___GET_VERSION = IDENTIFIER_REF_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Versioned Identifier Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSIONED_IDENTIFIER_REF_OPERATION_COUNT = IDENTIFIER_REF_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.impl.MigrationContextVariableImpl <em>Migration Context Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.impl.MigrationContextVariableImpl
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMigrationContextVariable()
	 * @generated
	 */
	int MIGRATION_CONTEXT_VARIABLE = 178;

	/**
	 * The feature id for the '<em><b>Declared Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE__DECLARED_TYPE_REF = VARIABLE__DECLARED_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE__NAME = VARIABLE__NAME;

	/**
	 * The number of structural features of the '<em>Migration Context Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE_FEATURE_COUNT = VARIABLE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Declared Type Ref</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE___GET_DECLARED_TYPE_REF = VARIABLE___GET_DECLARED_TYPE_REF;

	/**
	 * The operation id for the '<em>Get Containing Module</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE___GET_CONTAINING_MODULE = VARIABLE___GET_CONTAINING_MODULE;

	/**
	 * The operation id for the '<em>Is Const</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE___IS_CONST = VARIABLE___IS_CONST;

	/**
	 * The operation id for the '<em>Get Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE___GET_NAME = VARIABLE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Migration Context Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MIGRATION_CONTEXT_VARIABLE_OPERATION_COUNT = VARIABLE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.ModuleSpecifierForm <em>Module Specifier Form</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.ModuleSpecifierForm
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getModuleSpecifierForm()
	 * @generated
	 */
	int MODULE_SPECIFIER_FORM = 179;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.VariableStatementKeyword <em>Variable Statement Keyword</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.VariableStatementKeyword
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableStatementKeyword()
	 * @generated
	 */
	int VARIABLE_STATEMENT_KEYWORD = 180;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.PropertyNameKind <em>Property Name Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.PropertyNameKind
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameKind()
	 * @generated
	 */
	int PROPERTY_NAME_KIND = 181;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.PostfixOperator <em>Postfix Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.PostfixOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPostfixOperator()
	 * @generated
	 */
	int POSTFIX_OPERATOR = 182;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.UnaryOperator <em>Unary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.UnaryOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getUnaryOperator()
	 * @generated
	 */
	int UNARY_OPERATOR = 183;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.MultiplicativeOperator <em>Multiplicative Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.MultiplicativeOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMultiplicativeOperator()
	 * @generated
	 */
	int MULTIPLICATIVE_OPERATOR = 184;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.AdditiveOperator <em>Additive Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.AdditiveOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAdditiveOperator()
	 * @generated
	 */
	int ADDITIVE_OPERATOR = 185;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.RelationalOperator <em>Relational Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.RelationalOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getRelationalOperator()
	 * @generated
	 */
	int RELATIONAL_OPERATOR = 186;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.EqualityOperator <em>Equality Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.EqualityOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getEqualityOperator()
	 * @generated
	 */
	int EQUALITY_OPERATOR = 187;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.BinaryBitwiseOperator <em>Binary Bitwise Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.BinaryBitwiseOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryBitwiseOperator()
	 * @generated
	 */
	int BINARY_BITWISE_OPERATOR = 188;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.BinaryLogicalOperator <em>Binary Logical Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryLogicalOperator()
	 * @generated
	 */
	int BINARY_LOGICAL_OPERATOR = 189;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.ShiftOperator <em>Shift Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.ShiftOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getShiftOperator()
	 * @generated
	 */
	int SHIFT_OPERATOR = 190;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.AssignmentOperator <em>Assignment Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.AssignmentOperator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAssignmentOperator()
	 * @generated
	 */
	int ASSIGNMENT_OPERATOR = 191;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4JS.N4Modifier <em>N4 Modifier</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4JS.N4Modifier
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4Modifier()
	 * @generated
	 */
	int N4_MODIFIER = 192;

	/**
	 * The meta object id for the '<em>Iterator Of Expression</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Iterator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfExpression()
	 * @generated
	 */
	int ITERATOR_OF_EXPRESSION = 193;

	/**
	 * The meta object id for the '<em>Iterator Of Yield Expression</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Iterator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfYieldExpression()
	 * @generated
	 */
	int ITERATOR_OF_YIELD_EXPRESSION = 194;

	/**
	 * The meta object id for the '<em>Iterator Of Statement</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Iterator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfStatement()
	 * @generated
	 */
	int ITERATOR_OF_STATEMENT = 195;

	/**
	 * The meta object id for the '<em>Iterator Of Return Statement</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.Iterator
	 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfReturnStatement()
	 * @generated
	 */
	int ITERATOR_OF_RETURN_STATEMENT = 196;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.n4js.n4JS.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.NamedElement#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.NamedElement#getName()
	 * @generated
	 */
	EOperation getNamedElement__GetName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ControlFlowElement <em>Control Flow Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Control Flow Element</em>'.
	 * @see org.eclipse.n4js.n4JS.ControlFlowElement
	 * @generated
	 */
	EClass getControlFlowElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Script <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Script</em>'.
	 * @see org.eclipse.n4js.n4JS.Script
	 * @generated
	 */
	EClass getScript();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.Script#getHashbang <em>Hashbang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hashbang</em>'.
	 * @see org.eclipse.n4js.n4JS.Script#getHashbang()
	 * @see #getScript()
	 * @generated
	 */
	EAttribute getScript_Hashbang();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.Script#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.eclipse.n4js.n4JS.Script#getAnnotations()
	 * @see #getScript()
	 * @generated
	 */
	EReference getScript_Annotations();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.Script#getScriptElements <em>Script Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Script Elements</em>'.
	 * @see org.eclipse.n4js.n4JS.Script#getScriptElements()
	 * @see #getScript()
	 * @generated
	 */
	EReference getScript_ScriptElements();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.Script#getModule <em>Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Module</em>'.
	 * @see org.eclipse.n4js.n4JS.Script#getModule()
	 * @see #getScript()
	 * @generated
	 */
	EReference getScript_Module();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.Script#isFlaggedUsageMarkingFinished <em>Flagged Usage Marking Finished</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Flagged Usage Marking Finished</em>'.
	 * @see org.eclipse.n4js.n4JS.Script#isFlaggedUsageMarkingFinished()
	 * @see #getScript()
	 * @generated
	 */
	EAttribute getScript_FlaggedUsageMarkingFinished();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ScriptElement <em>Script Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Script Element</em>'.
	 * @see org.eclipse.n4js.n4JS.ScriptElement
	 * @generated
	 */
	EClass getScriptElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExportDeclaration <em>Export Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Export Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration
	 * @generated
	 */
	EClass getExportDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ExportDeclaration#getExportedElement <em>Exported Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exported Element</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration#getExportedElement()
	 * @see #getExportDeclaration()
	 * @generated
	 */
	EReference getExportDeclaration_ExportedElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ExportDeclaration#getDefaultExportedExpression <em>Default Exported Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Default Exported Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration#getDefaultExportedExpression()
	 * @see #getExportDeclaration()
	 * @generated
	 */
	EReference getExportDeclaration_DefaultExportedExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ExportDeclaration#getNamedExports <em>Named Exports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Named Exports</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration#getNamedExports()
	 * @see #getExportDeclaration()
	 * @generated
	 */
	EReference getExportDeclaration_NamedExports();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ExportDeclaration#isWildcardExport <em>Wildcard Export</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Wildcard Export</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration#isWildcardExport()
	 * @see #getExportDeclaration()
	 * @generated
	 */
	EAttribute getExportDeclaration_WildcardExport();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ExportDeclaration#isDefaultExport <em>Default Export</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Export</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration#isDefaultExport()
	 * @see #getExportDeclaration()
	 * @generated
	 */
	EAttribute getExportDeclaration_DefaultExport();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.ExportDeclaration#getReexportedFrom <em>Reexported From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Reexported From</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportDeclaration#getReexportedFrom()
	 * @see #getExportDeclaration()
	 * @generated
	 */
	EReference getExportDeclaration_ReexportedFrom();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExportSpecifier <em>Export Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Export Specifier</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportSpecifier
	 * @generated
	 */
	EClass getExportSpecifier();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ExportSpecifier#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Element</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportSpecifier#getElement()
	 * @see #getExportSpecifier()
	 * @generated
	 */
	EReference getExportSpecifier_Element();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ExportSpecifier#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportSpecifier#getAlias()
	 * @see #getExportSpecifier()
	 * @generated
	 */
	EAttribute getExportSpecifier_Alias();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExportableElement <em>Exportable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exportable Element</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportableElement
	 * @generated
	 */
	EClass getExportableElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ExportableElement#isExported() <em>Is Exported</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Exported</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ExportableElement#isExported()
	 * @generated
	 */
	EOperation getExportableElement__IsExported();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ExportableElement#isExportedAsDefault() <em>Is Exported As Default</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Exported As Default</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ExportableElement#isExportedAsDefault()
	 * @generated
	 */
	EOperation getExportableElement__IsExportedAsDefault();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ExportableElement#getExportedName() <em>Get Exported Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Exported Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ExportableElement#getExportedName()
	 * @generated
	 */
	EOperation getExportableElement__GetExportedName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ExportableElement#isToplevel() <em>Is Toplevel</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Toplevel</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ExportableElement#isToplevel()
	 * @generated
	 */
	EOperation getExportableElement__IsToplevel();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ImportDeclaration <em>Import Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration
	 * @generated
	 */
	EClass getImportDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ImportDeclaration#getImportSpecifiers <em>Import Specifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Import Specifiers</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration#getImportSpecifiers()
	 * @see #getImportDeclaration()
	 * @generated
	 */
	EReference getImportDeclaration_ImportSpecifiers();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ImportDeclaration#isImportFrom <em>Import From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Import From</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration#isImportFrom()
	 * @see #getImportDeclaration()
	 * @generated
	 */
	EAttribute getImportDeclaration_ImportFrom();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModule <em>Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Module</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration#getModule()
	 * @see #getImportDeclaration()
	 * @generated
	 */
	EReference getImportDeclaration_Module();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierAsText <em>Module Specifier As Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Specifier As Text</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierAsText()
	 * @see #getImportDeclaration()
	 * @generated
	 */
	EAttribute getImportDeclaration_ModuleSpecifierAsText();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierForm <em>Module Specifier Form</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Specifier Form</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierForm()
	 * @see #getImportDeclaration()
	 * @generated
	 */
	EAttribute getImportDeclaration_ModuleSpecifierForm();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ImportDeclaration#isBare() <em>Is Bare</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Bare</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration#isBare()
	 * @generated
	 */
	EOperation getImportDeclaration__IsBare();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ImportDeclaration#isRetainedAtRuntime() <em>Is Retained At Runtime</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Retained At Runtime</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ImportDeclaration#isRetainedAtRuntime()
	 * @generated
	 */
	EOperation getImportDeclaration__IsRetainedAtRuntime();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ImportSpecifier <em>Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Specifier</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportSpecifier
	 * @generated
	 */
	EClass getImportSpecifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ImportSpecifier#isFlaggedUsedInCode <em>Flagged Used In Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Flagged Used In Code</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportSpecifier#isFlaggedUsedInCode()
	 * @see #getImportSpecifier()
	 * @generated
	 */
	EAttribute getImportSpecifier_FlaggedUsedInCode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ImportSpecifier#isRetainedAtRuntime <em>Retained At Runtime</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Retained At Runtime</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportSpecifier#isRetainedAtRuntime()
	 * @see #getImportSpecifier()
	 * @generated
	 */
	EAttribute getImportSpecifier_RetainedAtRuntime();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.NamedImportSpecifier <em>Named Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Import Specifier</em>'.
	 * @see org.eclipse.n4js.n4JS.NamedImportSpecifier
	 * @generated
	 */
	EClass getNamedImportSpecifier();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.NamedImportSpecifier#getImportedElement <em>Imported Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Imported Element</em>'.
	 * @see org.eclipse.n4js.n4JS.NamedImportSpecifier#getImportedElement()
	 * @see #getNamedImportSpecifier()
	 * @generated
	 */
	EReference getNamedImportSpecifier_ImportedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.NamedImportSpecifier#getImportedElementAsText <em>Imported Element As Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Imported Element As Text</em>'.
	 * @see org.eclipse.n4js.n4JS.NamedImportSpecifier#getImportedElementAsText()
	 * @see #getNamedImportSpecifier()
	 * @generated
	 */
	EAttribute getNamedImportSpecifier_ImportedElementAsText();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.NamedImportSpecifier#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see org.eclipse.n4js.n4JS.NamedImportSpecifier#getAlias()
	 * @see #getNamedImportSpecifier()
	 * @generated
	 */
	EAttribute getNamedImportSpecifier_Alias();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.NamedImportSpecifier#isDefaultImport() <em>Is Default Import</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Default Import</em>' operation.
	 * @see org.eclipse.n4js.n4JS.NamedImportSpecifier#isDefaultImport()
	 * @generated
	 */
	EOperation getNamedImportSpecifier__IsDefaultImport();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.DefaultImportSpecifier <em>Default Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Default Import Specifier</em>'.
	 * @see org.eclipse.n4js.n4JS.DefaultImportSpecifier
	 * @generated
	 */
	EClass getDefaultImportSpecifier();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.DefaultImportSpecifier#getAlias() <em>Get Alias</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Alias</em>' operation.
	 * @see org.eclipse.n4js.n4JS.DefaultImportSpecifier#getAlias()
	 * @generated
	 */
	EOperation getDefaultImportSpecifier__GetAlias();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.DefaultImportSpecifier#isDefaultImport() <em>Is Default Import</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Default Import</em>' operation.
	 * @see org.eclipse.n4js.n4JS.DefaultImportSpecifier#isDefaultImport()
	 * @generated
	 */
	EOperation getDefaultImportSpecifier__IsDefaultImport();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.NamespaceImportSpecifier <em>Namespace Import Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Namespace Import Specifier</em>'.
	 * @see org.eclipse.n4js.n4JS.NamespaceImportSpecifier
	 * @generated
	 */
	EClass getNamespaceImportSpecifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.NamespaceImportSpecifier#isDeclaredDynamic <em>Declared Dynamic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Dynamic</em>'.
	 * @see org.eclipse.n4js.n4JS.NamespaceImportSpecifier#isDeclaredDynamic()
	 * @see #getNamespaceImportSpecifier()
	 * @generated
	 */
	EAttribute getNamespaceImportSpecifier_DeclaredDynamic();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.NamespaceImportSpecifier#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see org.eclipse.n4js.n4JS.NamespaceImportSpecifier#getAlias()
	 * @see #getNamespaceImportSpecifier()
	 * @generated
	 */
	EAttribute getNamespaceImportSpecifier_Alias();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TypeProvidingElement <em>Type Providing Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Providing Element</em>'.
	 * @see org.eclipse.n4js.n4JS.TypeProvidingElement
	 * @generated
	 */
	EClass getTypeProvidingElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.TypeProvidingElement#getDeclaredTypeRef() <em>Get Declared Type Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type Ref</em>' operation.
	 * @see org.eclipse.n4js.n4JS.TypeProvidingElement#getDeclaredTypeRef()
	 * @generated
	 */
	EOperation getTypeProvidingElement__GetDeclaredTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see org.eclipse.n4js.n4JS.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.TypedElement#getDeclaredTypeRef <em>Declared Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Declared Type Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.TypedElement#getDeclaredTypeRef()
	 * @see #getTypedElement()
	 * @generated
	 */
	EReference getTypedElement_DeclaredTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VariableEnvironmentElement <em>Variable Environment Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Environment Element</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableEnvironmentElement
	 * @generated
	 */
	EClass getVariableEnvironmentElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VariableEnvironmentElement#appliesOnlyToBlockScopedElements() <em>Applies Only To Block Scoped Elements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VariableEnvironmentElement#appliesOnlyToBlockScopedElements()
	 * @generated
	 */
	EOperation getVariableEnvironmentElement__AppliesOnlyToBlockScopedElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ThisTarget <em>This Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>This Target</em>'.
	 * @see org.eclipse.n4js.n4JS.ThisTarget
	 * @generated
	 */
	EClass getThisTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ThisArgProvider <em>This Arg Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>This Arg Provider</em>'.
	 * @see org.eclipse.n4js.n4JS.ThisArgProvider
	 * @generated
	 */
	EClass getThisArgProvider();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.eclipse.n4js.n4JS.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Variable#isConst() <em>Is Const</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Const</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Variable#isConst()
	 * @generated
	 */
	EOperation getVariable__IsConst();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AnnotableElement <em>Annotable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotable Element</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotableElement
	 * @generated
	 */
	EClass getAnnotableElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotableElement#getAnnotations() <em>Get Annotations</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotations</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotableElement#getAnnotations()
	 * @generated
	 */
	EOperation getAnnotableElement__GetAnnotations();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotableElement#getAllAnnotations() <em>Get All Annotations</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Annotations</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotableElement#getAllAnnotations()
	 * @generated
	 */
	EOperation getAnnotableElement__GetAllAnnotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AnnotableScriptElement <em>Annotable Script Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotable Script Element</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotableScriptElement
	 * @generated
	 */
	EClass getAnnotableScriptElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AnnotableScriptElement#getAnnotationList <em>Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotableScriptElement#getAnnotationList()
	 * @see #getAnnotableScriptElement()
	 * @generated
	 */
	EReference getAnnotableScriptElement_AnnotationList();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotableScriptElement#getAnnotations() <em>Get Annotations</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotations</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotableScriptElement#getAnnotations()
	 * @generated
	 */
	EOperation getAnnotableScriptElement__GetAnnotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AnnotableExpression <em>Annotable Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotable Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotableExpression
	 * @generated
	 */
	EClass getAnnotableExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AnnotableExpression#getAnnotationList <em>Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotableExpression#getAnnotationList()
	 * @see #getAnnotableExpression()
	 * @generated
	 */
	EReference getAnnotableExpression_AnnotationList();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotableExpression#getAnnotations() <em>Get Annotations</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotations</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotableExpression#getAnnotations()
	 * @generated
	 */
	EOperation getAnnotableExpression__GetAnnotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AbstractAnnotationList <em>Abstract Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.AbstractAnnotationList
	 * @generated
	 */
	EClass getAbstractAnnotationList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.AbstractAnnotationList#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.eclipse.n4js.n4JS.AbstractAnnotationList#getAnnotations()
	 * @see #getAbstractAnnotationList()
	 * @generated
	 */
	EReference getAbstractAnnotationList_Annotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AnnotationList <em>Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotationList
	 * @generated
	 */
	EClass getAnnotationList();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExpressionAnnotationList <em>Expression Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.ExpressionAnnotationList
	 * @generated
	 */
	EClass getExpressionAnnotationList();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see org.eclipse.n4js.n4JS.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.Annotation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.n4JS.Annotation#getName()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.Annotation#getArgs <em>Args</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Args</em>'.
	 * @see org.eclipse.n4js.n4JS.Annotation#getArgs()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Args();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Annotation#getAnnotatedElement() <em>Get Annotated Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotated Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Annotation#getAnnotatedElement()
	 * @generated
	 */
	EOperation getAnnotation__GetAnnotatedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AnnotationArgument <em>Annotation Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation Argument</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotationArgument
	 * @generated
	 */
	EClass getAnnotationArgument();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotationArgument#value() <em>Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Value</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotationArgument#value()
	 * @generated
	 */
	EOperation getAnnotationArgument__Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotationArgument#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotationArgument#getValueAsString()
	 * @generated
	 */
	EOperation getAnnotationArgument__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.LiteralAnnotationArgument <em>Literal Annotation Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal Annotation Argument</em>'.
	 * @see org.eclipse.n4js.n4JS.LiteralAnnotationArgument
	 * @generated
	 */
	EClass getLiteralAnnotationArgument();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.LiteralAnnotationArgument#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.LiteralAnnotationArgument#getLiteral()
	 * @see #getLiteralAnnotationArgument()
	 * @generated
	 */
	EReference getLiteralAnnotationArgument_Literal();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.LiteralAnnotationArgument#value() <em>Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Value</em>' operation.
	 * @see org.eclipse.n4js.n4JS.LiteralAnnotationArgument#value()
	 * @generated
	 */
	EOperation getLiteralAnnotationArgument__Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TypeRefAnnotationArgument <em>Type Ref Annotation Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Ref Annotation Argument</em>'.
	 * @see org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
	 * @generated
	 */
	EClass getTypeRefAnnotationArgument();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.TypeRefAnnotationArgument#getTypeRef <em>Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.TypeRefAnnotationArgument#getTypeRef()
	 * @see #getTypeRefAnnotationArgument()
	 * @generated
	 */
	EReference getTypeRefAnnotationArgument_TypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.TypeRefAnnotationArgument#value() <em>Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Value</em>' operation.
	 * @see org.eclipse.n4js.n4JS.TypeRefAnnotationArgument#value()
	 * @generated
	 */
	EOperation getTypeRefAnnotationArgument__Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor <em>Function Or Field Accessor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Or Field Accessor</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
	 * @generated
	 */
	EClass getFunctionOrFieldAccessor();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getBody()
	 * @see #getFunctionOrFieldAccessor()
	 * @generated
	 */
	EReference getFunctionOrFieldAccessor_Body();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#get_lok <em>lok</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>lok</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#get_lok()
	 * @see #getFunctionOrFieldAccessor()
	 * @generated
	 */
	EReference getFunctionOrFieldAccessor__lok();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getName()
	 * @generated
	 */
	EOperation getFunctionOrFieldAccessor__GetName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getLocalArgumentsVariable() <em>Get Local Arguments Variable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Local Arguments Variable</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getLocalArgumentsVariable()
	 * @generated
	 */
	EOperation getFunctionOrFieldAccessor__GetLocalArgumentsVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#isReturnValueOptional() <em>Is Return Value Optional</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Return Value Optional</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#isReturnValueOptional()
	 * @generated
	 */
	EOperation getFunctionOrFieldAccessor__IsReturnValueOptional();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#isAsync() <em>Is Async</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Async</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#isAsync()
	 * @generated
	 */
	EOperation getFunctionOrFieldAccessor__IsAsync();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getDefinedFunctionOrAccessor() <em>Get Defined Function Or Accessor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Function Or Accessor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionOrFieldAccessor#getDefinedFunctionOrAccessor()
	 * @generated
	 */
	EOperation getFunctionOrFieldAccessor__GetDefinedFunctionOrAccessor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.FunctionDefinition <em>Function Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Definition</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition
	 * @generated
	 */
	EClass getFunctionDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.FunctionDefinition#getFpars <em>Fpars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fpars</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition#getFpars()
	 * @see #getFunctionDefinition()
	 * @generated
	 */
	EReference getFunctionDefinition_Fpars();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.FunctionDefinition#getReturnTypeRef <em>Return Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Return Type Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition#getReturnTypeRef()
	 * @see #getFunctionDefinition()
	 * @generated
	 */
	EReference getFunctionDefinition_ReturnTypeRef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.FunctionDefinition#isGenerator <em>Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Generator</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition#isGenerator()
	 * @see #getFunctionDefinition()
	 * @generated
	 */
	EAttribute getFunctionDefinition_Generator();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.FunctionDefinition#isDeclaredAsync <em>Declared Async</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Async</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition#isDeclaredAsync()
	 * @see #getFunctionDefinition()
	 * @generated
	 */
	EAttribute getFunctionDefinition_DeclaredAsync();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionDefinition#isReturnValueOptional() <em>Is Return Value Optional</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Return Value Optional</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition#isReturnValueOptional()
	 * @generated
	 */
	EOperation getFunctionDefinition__IsReturnValueOptional();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionDefinition#isAsync() <em>Is Async</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Async</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition#isAsync()
	 * @generated
	 */
	EOperation getFunctionDefinition__IsAsync();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionDefinition#getDefinedFunction() <em>Get Defined Function</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Function</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionDefinition#getDefinedFunction()
	 * @generated
	 */
	EOperation getFunctionDefinition__GetDefinedFunction();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.FieldAccessor <em>Field Accessor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Accessor</em>'.
	 * @see org.eclipse.n4js.n4JS.FieldAccessor
	 * @generated
	 */
	EClass getFieldAccessor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.FieldAccessor#isDeclaredOptional <em>Declared Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Optional</em>'.
	 * @see org.eclipse.n4js.n4JS.FieldAccessor#isDeclaredOptional()
	 * @see #getFieldAccessor()
	 * @generated
	 */
	EAttribute getFieldAccessor_DeclaredOptional();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FieldAccessor#getDeclaredTypeRef() <em>Get Declared Type Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type Ref</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FieldAccessor#getDeclaredTypeRef()
	 * @generated
	 */
	EOperation getFieldAccessor__GetDeclaredTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FieldAccessor#getDefinedAccessor() <em>Get Defined Accessor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Accessor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FieldAccessor#getDefinedAccessor()
	 * @generated
	 */
	EOperation getFieldAccessor__GetDefinedAccessor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FieldAccessor#isOptional() <em>Is Optional</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Optional</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FieldAccessor#isOptional()
	 * @generated
	 */
	EOperation getFieldAccessor__IsOptional();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.FunctionDeclaration <em>Function Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDeclaration
	 * @generated
	 */
	EClass getFunctionDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.FunctionDeclaration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDeclaration#getName()
	 * @see #getFunctionDeclaration()
	 * @generated
	 */
	EAttribute getFunctionDeclaration_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.FunctionDeclaration#get_migrationContext <em>migration Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>migration Context</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionDeclaration#get_migrationContext()
	 * @see #getFunctionDeclaration()
	 * @generated
	 */
	EReference getFunctionDeclaration__migrationContext();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionDeclaration#isExternal() <em>Is External</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is External</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionDeclaration#isExternal()
	 * @generated
	 */
	EOperation getFunctionDeclaration__IsExternal();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionDeclaration#getMigrationContextVariable() <em>Get Migration Context Variable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Migration Context Variable</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionDeclaration#getMigrationContextVariable()
	 * @generated
	 */
	EOperation getFunctionDeclaration__GetMigrationContextVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.FunctionExpression <em>Function Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionExpression
	 * @generated
	 */
	EClass getFunctionExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.FunctionExpression#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.n4JS.FunctionExpression#getName()
	 * @see #getFunctionExpression()
	 * @generated
	 */
	EAttribute getFunctionExpression_Name();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.FunctionExpression#isArrowFunction() <em>Is Arrow Function</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Arrow Function</em>' operation.
	 * @see org.eclipse.n4js.n4JS.FunctionExpression#isArrowFunction()
	 * @generated
	 */
	EOperation getFunctionExpression__IsArrowFunction();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ArrowFunction <em>Arrow Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arrow Function</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrowFunction
	 * @generated
	 */
	EClass getArrowFunction();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ArrowFunction#isHasBracesAroundBody <em>Has Braces Around Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Braces Around Body</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrowFunction#isHasBracesAroundBody()
	 * @see #getArrowFunction()
	 * @generated
	 */
	EAttribute getArrowFunction_HasBracesAroundBody();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ArrowFunction#isArrowFunction() <em>Is Arrow Function</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Arrow Function</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ArrowFunction#isArrowFunction()
	 * @generated
	 */
	EOperation getArrowFunction__IsArrowFunction();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ArrowFunction#isSingleExprImplicitReturn() <em>Is Single Expr Implicit Return</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Single Expr Implicit Return</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ArrowFunction#isSingleExprImplicitReturn()
	 * @generated
	 */
	EOperation getArrowFunction__IsSingleExprImplicitReturn();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ArrowFunction#getSingleExpression() <em>Get Single Expression</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Single Expression</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ArrowFunction#getSingleExpression()
	 * @generated
	 */
	EOperation getArrowFunction__GetSingleExpression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ArrowFunction#implicitReturnExpr() <em>Implicit Return Expr</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Implicit Return Expr</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ArrowFunction#implicitReturnExpr()
	 * @generated
	 */
	EOperation getArrowFunction__ImplicitReturnExpr();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.LocalArgumentsVariable <em>Local Arguments Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Arguments Variable</em>'.
	 * @see org.eclipse.n4js.n4JS.LocalArgumentsVariable
	 * @generated
	 */
	EClass getLocalArgumentsVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.LocalArgumentsVariable#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.LocalArgumentsVariable#getName()
	 * @generated
	 */
	EOperation getLocalArgumentsVariable__GetName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.FormalParameter <em>Formal Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Formal Parameter</em>'.
	 * @see org.eclipse.n4js.n4JS.FormalParameter
	 * @generated
	 */
	EClass getFormalParameter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.FormalParameter#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.eclipse.n4js.n4JS.FormalParameter#getAnnotations()
	 * @see #getFormalParameter()
	 * @generated
	 */
	EReference getFormalParameter_Annotations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.FormalParameter#isVariadic <em>Variadic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variadic</em>'.
	 * @see org.eclipse.n4js.n4JS.FormalParameter#isVariadic()
	 * @see #getFormalParameter()
	 * @generated
	 */
	EAttribute getFormalParameter_Variadic();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.FormalParameter#getDefinedTypeElement <em>Defined Type Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Type Element</em>'.
	 * @see org.eclipse.n4js.n4JS.FormalParameter#getDefinedTypeElement()
	 * @see #getFormalParameter()
	 * @generated
	 */
	EReference getFormalParameter_DefinedTypeElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.FormalParameter#isHasInitializerAssignment <em>Has Initializer Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Initializer Assignment</em>'.
	 * @see org.eclipse.n4js.n4JS.FormalParameter#isHasInitializerAssignment()
	 * @see #getFormalParameter()
	 * @generated
	 */
	EAttribute getFormalParameter_HasInitializerAssignment();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.FormalParameter#getInitializer <em>Initializer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initializer</em>'.
	 * @see org.eclipse.n4js.n4JS.FormalParameter#getInitializer()
	 * @see #getFormalParameter()
	 * @generated
	 */
	EReference getFormalParameter_Initializer();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.FormalParameter#getBindingPattern <em>Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Binding Pattern</em>'.
	 * @see org.eclipse.n4js.n4JS.FormalParameter#getBindingPattern()
	 * @see #getFormalParameter()
	 * @generated
	 */
	EReference getFormalParameter_BindingPattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Block <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Block</em>'.
	 * @see org.eclipse.n4js.n4JS.Block
	 * @generated
	 */
	EClass getBlock();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.Block#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.n4js.n4JS.Block#getStatements()
	 * @see #getBlock()
	 * @generated
	 */
	EReference getBlock_Statements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#appliesOnlyToBlockScopedElements() <em>Applies Only To Block Scoped Elements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#appliesOnlyToBlockScopedElements()
	 * @generated
	 */
	EOperation getBlock__AppliesOnlyToBlockScopedElements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllExpressions() <em>Get All Expressions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Expressions</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllExpressions()
	 * @generated
	 */
	EOperation getBlock__GetAllExpressions();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllYieldExpressions() <em>Get All Yield Expressions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Yield Expressions</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllYieldExpressions()
	 * @generated
	 */
	EOperation getBlock__GetAllYieldExpressions();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllVoidYieldExpressions() <em>Get All Void Yield Expressions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Void Yield Expressions</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllVoidYieldExpressions()
	 * @generated
	 */
	EOperation getBlock__GetAllVoidYieldExpressions();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllNonVoidYieldExpressions() <em>Get All Non Void Yield Expressions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Non Void Yield Expressions</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllNonVoidYieldExpressions()
	 * @generated
	 */
	EOperation getBlock__GetAllNonVoidYieldExpressions();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#hasNonVoidYield() <em>Has Non Void Yield</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Non Void Yield</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#hasNonVoidYield()
	 * @generated
	 */
	EOperation getBlock__HasNonVoidYield();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllStatements() <em>Get All Statements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Statements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllStatements()
	 * @generated
	 */
	EOperation getBlock__GetAllStatements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllReturnStatements() <em>Get All Return Statements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Return Statements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllReturnStatements()
	 * @generated
	 */
	EOperation getBlock__GetAllReturnStatements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllNonVoidReturnStatements() <em>Get All Non Void Return Statements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Non Void Return Statements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllNonVoidReturnStatements()
	 * @generated
	 */
	EOperation getBlock__GetAllNonVoidReturnStatements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#getAllVoidReturnStatements() <em>Get All Void Return Statements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get All Void Return Statements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#getAllVoidReturnStatements()
	 * @generated
	 */
	EOperation getBlock__GetAllVoidReturnStatements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Block#hasNonVoidReturn() <em>Has Non Void Return</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Non Void Return</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Block#hasNonVoidReturn()
	 * @generated
	 */
	EOperation getBlock__HasNonVoidReturn();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Statement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.Statement
	 * @generated
	 */
	EClass getStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer <em>Variable Declaration Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Declaration Container</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationContainer
	 * @generated
	 */
	EClass getVariableDeclarationContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarDeclsOrBindings <em>Var Decls Or Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Decls Or Bindings</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarDeclsOrBindings()
	 * @see #getVariableDeclarationContainer()
	 * @generated
	 */
	EReference getVariableDeclarationContainer_VarDeclsOrBindings();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarStmtKeyword <em>Var Stmt Keyword</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Var Stmt Keyword</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarStmtKeyword()
	 * @see #getVariableDeclarationContainer()
	 * @generated
	 */
	EAttribute getVariableDeclarationContainer_VarStmtKeyword();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarDecl() <em>Get Var Decl</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Var Decl</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationContainer#getVarDecl()
	 * @generated
	 */
	EOperation getVariableDeclarationContainer__GetVarDecl();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VariableDeclarationContainer#isBlockScoped() <em>Is Block Scoped</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Block Scoped</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationContainer#isBlockScoped()
	 * @generated
	 */
	EOperation getVariableDeclarationContainer__IsBlockScoped();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VariableStatement <em>Variable Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableStatement
	 * @generated
	 */
	EClass getVariableStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExportedVariableStatement <em>Exported Variable Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exported Variable Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableStatement
	 * @generated
	 */
	EClass getExportedVariableStatement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ExportedVariableStatement#isExternal() <em>Is External</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is External</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableStatement#isExternal()
	 * @generated
	 */
	EOperation getExportedVariableStatement__IsExternal();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VariableDeclarationOrBinding <em>Variable Declaration Or Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Declaration Or Binding</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationOrBinding
	 * @generated
	 */
	EClass getVariableDeclarationOrBinding();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VariableDeclarationOrBinding#getVariableDeclarations() <em>Get Variable Declarations</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Variable Declarations</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationOrBinding#getVariableDeclarations()
	 * @generated
	 */
	EOperation getVariableDeclarationOrBinding__GetVariableDeclarations();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VariableDeclarationOrBinding#getExpression() <em>Get Expression</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Expression</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VariableDeclarationOrBinding#getExpression()
	 * @generated
	 */
	EOperation getVariableDeclarationOrBinding__GetExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VariableBinding <em>Variable Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Binding</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableBinding
	 * @generated
	 */
	EClass getVariableBinding();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.VariableBinding#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pattern</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableBinding#getPattern()
	 * @see #getVariableBinding()
	 * @generated
	 */
	EReference getVariableBinding_Pattern();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.VariableBinding#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableBinding#getExpression()
	 * @see #getVariableBinding()
	 * @generated
	 */
	EReference getVariableBinding_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExportedVariableBinding <em>Exported Variable Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exported Variable Binding</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableBinding
	 * @generated
	 */
	EClass getExportedVariableBinding();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.ExportedVariableBinding#getDefinedVariable <em>Defined Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Variable</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableBinding#getDefinedVariable()
	 * @see #getExportedVariableBinding()
	 * @generated
	 */
	EReference getExportedVariableBinding_DefinedVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VariableDeclaration <em>Variable Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableDeclaration
	 * @generated
	 */
	EClass getVariableDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.VariableDeclaration#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableDeclaration#getAnnotations()
	 * @see #getVariableDeclaration()
	 * @generated
	 */
	EReference getVariableDeclaration_Annotations();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.VariableDeclaration#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableDeclaration#getExpression()
	 * @see #getVariableDeclaration()
	 * @generated
	 */
	EReference getVariableDeclaration_Expression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VariableDeclaration#isConst() <em>Is Const</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Const</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VariableDeclaration#isConst()
	 * @generated
	 */
	EOperation getVariableDeclaration__IsConst();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExportedVariableDeclaration <em>Exported Variable Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exported Variable Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableDeclaration
	 * @generated
	 */
	EClass getExportedVariableDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.ExportedVariableDeclaration#getDefinedVariable <em>Defined Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Variable</em>'.
	 * @see org.eclipse.n4js.n4JS.ExportedVariableDeclaration#getDefinedVariable()
	 * @see #getExportedVariableDeclaration()
	 * @generated
	 */
	EReference getExportedVariableDeclaration_DefinedVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.EmptyStatement <em>Empty Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Empty Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.EmptyStatement
	 * @generated
	 */
	EClass getEmptyStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExpressionStatement <em>Expression Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.ExpressionStatement
	 * @generated
	 */
	EClass getExpressionStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ExpressionStatement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ExpressionStatement#getExpression()
	 * @see #getExpressionStatement()
	 * @generated
	 */
	EReference getExpressionStatement_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.IfStatement <em>If Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>If Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.IfStatement
	 * @generated
	 */
	EClass getIfStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.IfStatement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.IfStatement#getExpression()
	 * @see #getIfStatement()
	 * @generated
	 */
	EReference getIfStatement_Expression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.IfStatement#getIfStmt <em>If Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>If Stmt</em>'.
	 * @see org.eclipse.n4js.n4JS.IfStatement#getIfStmt()
	 * @see #getIfStatement()
	 * @generated
	 */
	EReference getIfStatement_IfStmt();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.IfStatement#getElseStmt <em>Else Stmt</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else Stmt</em>'.
	 * @see org.eclipse.n4js.n4JS.IfStatement#getElseStmt()
	 * @see #getIfStatement()
	 * @generated
	 */
	EReference getIfStatement_ElseStmt();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.IterationStatement <em>Iteration Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iteration Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.IterationStatement
	 * @generated
	 */
	EClass getIterationStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.IterationStatement#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.IterationStatement#getStatement()
	 * @see #getIterationStatement()
	 * @generated
	 */
	EReference getIterationStatement_Statement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.IterationStatement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.IterationStatement#getExpression()
	 * @see #getIterationStatement()
	 * @generated
	 */
	EReference getIterationStatement_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.DoStatement <em>Do Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Do Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.DoStatement
	 * @generated
	 */
	EClass getDoStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.WhileStatement <em>While Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>While Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.WhileStatement
	 * @generated
	 */
	EClass getWhileStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ForStatement <em>For Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>For Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.ForStatement
	 * @generated
	 */
	EClass getForStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ForStatement#getInitExpr <em>Init Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Init Expr</em>'.
	 * @see org.eclipse.n4js.n4JS.ForStatement#getInitExpr()
	 * @see #getForStatement()
	 * @generated
	 */
	EReference getForStatement_InitExpr();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ForStatement#getUpdateExpr <em>Update Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Update Expr</em>'.
	 * @see org.eclipse.n4js.n4JS.ForStatement#getUpdateExpr()
	 * @see #getForStatement()
	 * @generated
	 */
	EReference getForStatement_UpdateExpr();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ForStatement#isAwait <em>Await</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Await</em>'.
	 * @see org.eclipse.n4js.n4JS.ForStatement#isAwait()
	 * @see #getForStatement()
	 * @generated
	 */
	EAttribute getForStatement_Await();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ForStatement#isForIn <em>For In</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>For In</em>'.
	 * @see org.eclipse.n4js.n4JS.ForStatement#isForIn()
	 * @see #getForStatement()
	 * @generated
	 */
	EAttribute getForStatement_ForIn();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ForStatement#isForOf <em>For Of</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>For Of</em>'.
	 * @see org.eclipse.n4js.n4JS.ForStatement#isForOf()
	 * @see #getForStatement()
	 * @generated
	 */
	EAttribute getForStatement_ForOf();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ForStatement#isForPlain() <em>Is For Plain</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is For Plain</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ForStatement#isForPlain()
	 * @generated
	 */
	EOperation getForStatement__IsForPlain();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ForStatement#appliesOnlyToBlockScopedElements() <em>Applies Only To Block Scoped Elements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ForStatement#appliesOnlyToBlockScopedElements()
	 * @generated
	 */
	EOperation getForStatement__AppliesOnlyToBlockScopedElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.LabelRef <em>Label Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.LabelRef
	 * @generated
	 */
	EClass getLabelRef();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.LabelRef#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Label</em>'.
	 * @see org.eclipse.n4js.n4JS.LabelRef#getLabel()
	 * @see #getLabelRef()
	 * @generated
	 */
	EReference getLabelRef_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.LabelRef#getLabelAsText <em>Label As Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label As Text</em>'.
	 * @see org.eclipse.n4js.n4JS.LabelRef#getLabelAsText()
	 * @see #getLabelRef()
	 * @generated
	 */
	EAttribute getLabelRef_LabelAsText();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ContinueStatement <em>Continue Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Continue Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.ContinueStatement
	 * @generated
	 */
	EClass getContinueStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BreakStatement <em>Break Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Break Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.BreakStatement
	 * @generated
	 */
	EClass getBreakStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ReturnStatement <em>Return Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Return Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.ReturnStatement
	 * @generated
	 */
	EClass getReturnStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ReturnStatement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ReturnStatement#getExpression()
	 * @see #getReturnStatement()
	 * @generated
	 */
	EReference getReturnStatement_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.WithStatement <em>With Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>With Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.WithStatement
	 * @generated
	 */
	EClass getWithStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.WithStatement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.WithStatement#getExpression()
	 * @see #getWithStatement()
	 * @generated
	 */
	EReference getWithStatement_Expression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.WithStatement#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.WithStatement#getStatement()
	 * @see #getWithStatement()
	 * @generated
	 */
	EReference getWithStatement_Statement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.SwitchStatement <em>Switch Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Switch Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.SwitchStatement
	 * @generated
	 */
	EClass getSwitchStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.SwitchStatement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.SwitchStatement#getExpression()
	 * @see #getSwitchStatement()
	 * @generated
	 */
	EReference getSwitchStatement_Expression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.SwitchStatement#getCases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cases</em>'.
	 * @see org.eclipse.n4js.n4JS.SwitchStatement#getCases()
	 * @see #getSwitchStatement()
	 * @generated
	 */
	EReference getSwitchStatement_Cases();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.SwitchStatement#appliesOnlyToBlockScopedElements() <em>Applies Only To Block Scoped Elements</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Applies Only To Block Scoped Elements</em>' operation.
	 * @see org.eclipse.n4js.n4JS.SwitchStatement#appliesOnlyToBlockScopedElements()
	 * @generated
	 */
	EOperation getSwitchStatement__AppliesOnlyToBlockScopedElements();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.SwitchStatement#getDefaultClause() <em>Get Default Clause</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Default Clause</em>' operation.
	 * @see org.eclipse.n4js.n4JS.SwitchStatement#getDefaultClause()
	 * @generated
	 */
	EOperation getSwitchStatement__GetDefaultClause();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.SwitchStatement#getCaseClauses() <em>Get Case Clauses</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Case Clauses</em>' operation.
	 * @see org.eclipse.n4js.n4JS.SwitchStatement#getCaseClauses()
	 * @generated
	 */
	EOperation getSwitchStatement__GetCaseClauses();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AbstractCaseClause <em>Abstract Case Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Case Clause</em>'.
	 * @see org.eclipse.n4js.n4JS.AbstractCaseClause
	 * @generated
	 */
	EClass getAbstractCaseClause();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.AbstractCaseClause#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.n4js.n4JS.AbstractCaseClause#getStatements()
	 * @see #getAbstractCaseClause()
	 * @generated
	 */
	EReference getAbstractCaseClause_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.CaseClause <em>Case Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Case Clause</em>'.
	 * @see org.eclipse.n4js.n4JS.CaseClause
	 * @generated
	 */
	EClass getCaseClause();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.CaseClause#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.CaseClause#getExpression()
	 * @see #getCaseClause()
	 * @generated
	 */
	EReference getCaseClause_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.DefaultClause <em>Default Clause</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Default Clause</em>'.
	 * @see org.eclipse.n4js.n4JS.DefaultClause
	 * @generated
	 */
	EClass getDefaultClause();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.LabelledStatement <em>Labelled Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Labelled Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.LabelledStatement
	 * @generated
	 */
	EClass getLabelledStatement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.LabelledStatement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.n4JS.LabelledStatement#getName()
	 * @see #getLabelledStatement()
	 * @generated
	 */
	EAttribute getLabelledStatement_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.LabelledStatement#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.LabelledStatement#getStatement()
	 * @see #getLabelledStatement()
	 * @generated
	 */
	EReference getLabelledStatement_Statement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ThrowStatement <em>Throw Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Throw Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.ThrowStatement
	 * @generated
	 */
	EClass getThrowStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ThrowStatement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ThrowStatement#getExpression()
	 * @see #getThrowStatement()
	 * @generated
	 */
	EReference getThrowStatement_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TryStatement <em>Try Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Try Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.TryStatement
	 * @generated
	 */
	EClass getTryStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.TryStatement#getBlock <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Block</em>'.
	 * @see org.eclipse.n4js.n4JS.TryStatement#getBlock()
	 * @see #getTryStatement()
	 * @generated
	 */
	EReference getTryStatement_Block();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.TryStatement#getCatch <em>Catch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Catch</em>'.
	 * @see org.eclipse.n4js.n4JS.TryStatement#getCatch()
	 * @see #getTryStatement()
	 * @generated
	 */
	EReference getTryStatement_Catch();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.TryStatement#getFinally <em>Finally</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Finally</em>'.
	 * @see org.eclipse.n4js.n4JS.TryStatement#getFinally()
	 * @see #getTryStatement()
	 * @generated
	 */
	EReference getTryStatement_Finally();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AbstractCatchBlock <em>Abstract Catch Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Catch Block</em>'.
	 * @see org.eclipse.n4js.n4JS.AbstractCatchBlock
	 * @generated
	 */
	EClass getAbstractCatchBlock();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AbstractCatchBlock#getBlock <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Block</em>'.
	 * @see org.eclipse.n4js.n4JS.AbstractCatchBlock#getBlock()
	 * @see #getAbstractCatchBlock()
	 * @generated
	 */
	EReference getAbstractCatchBlock_Block();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.CatchBlock <em>Catch Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Catch Block</em>'.
	 * @see org.eclipse.n4js.n4JS.CatchBlock
	 * @generated
	 */
	EClass getCatchBlock();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.CatchBlock#getCatchVariable <em>Catch Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Catch Variable</em>'.
	 * @see org.eclipse.n4js.n4JS.CatchBlock#getCatchVariable()
	 * @see #getCatchBlock()
	 * @generated
	 */
	EReference getCatchBlock_CatchVariable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.CatchVariable <em>Catch Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Catch Variable</em>'.
	 * @see org.eclipse.n4js.n4JS.CatchVariable
	 * @generated
	 */
	EClass getCatchVariable();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.CatchVariable#getBindingPattern <em>Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Binding Pattern</em>'.
	 * @see org.eclipse.n4js.n4JS.CatchVariable#getBindingPattern()
	 * @see #getCatchVariable()
	 * @generated
	 */
	EReference getCatchVariable_BindingPattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.FinallyBlock <em>Finally Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Finally Block</em>'.
	 * @see org.eclipse.n4js.n4JS.FinallyBlock
	 * @generated
	 */
	EClass getFinallyBlock();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.DebuggerStatement <em>Debugger Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Debugger Statement</em>'.
	 * @see org.eclipse.n4js.n4JS.DebuggerStatement
	 * @generated
	 */
	EClass getDebuggerStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PrimaryExpression <em>Primary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primary Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.PrimaryExpression
	 * @generated
	 */
	EClass getPrimaryExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ParenExpression <em>Paren Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paren Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ParenExpression
	 * @generated
	 */
	EClass getParenExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ParenExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ParenExpression#getExpression()
	 * @see #getParenExpression()
	 * @generated
	 */
	EReference getParenExpression_Expression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ParenExpression#isValidSimpleAssignmentTarget() <em>Is Valid Simple Assignment Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ParenExpression#isValidSimpleAssignmentTarget()
	 * @generated
	 */
	EOperation getParenExpression__IsValidSimpleAssignmentTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.IdentifierRef <em>Identifier Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifier Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.IdentifierRef
	 * @generated
	 */
	EClass getIdentifierRef();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.IdentifierRef#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Id</em>'.
	 * @see org.eclipse.n4js.n4JS.IdentifierRef#getId()
	 * @see #getIdentifierRef()
	 * @generated
	 */
	EReference getIdentifierRef_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.IdentifierRef#getIdAsText <em>Id As Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id As Text</em>'.
	 * @see org.eclipse.n4js.n4JS.IdentifierRef#getIdAsText()
	 * @see #getIdentifierRef()
	 * @generated
	 */
	EAttribute getIdentifierRef_IdAsText();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.IdentifierRef#getOriginImport <em>Origin Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Origin Import</em>'.
	 * @see org.eclipse.n4js.n4JS.IdentifierRef#getOriginImport()
	 * @see #getIdentifierRef()
	 * @generated
	 */
	EReference getIdentifierRef_OriginImport();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.IdentifierRef#getTargetElement() <em>Get Target Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Target Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.IdentifierRef#getTargetElement()
	 * @generated
	 */
	EOperation getIdentifierRef__GetTargetElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.IdentifierRef#isValidSimpleAssignmentTarget() <em>Is Valid Simple Assignment Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * @see org.eclipse.n4js.n4JS.IdentifierRef#isValidSimpleAssignmentTarget()
	 * @generated
	 */
	EOperation getIdentifierRef__IsValidSimpleAssignmentTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.StrictModeRelevant <em>Strict Mode Relevant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Strict Mode Relevant</em>'.
	 * @see org.eclipse.n4js.n4JS.StrictModeRelevant
	 * @generated
	 */
	EClass getStrictModeRelevant();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.StrictModeRelevant#isStrictMode <em>Strict Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strict Mode</em>'.
	 * @see org.eclipse.n4js.n4JS.StrictModeRelevant#isStrictMode()
	 * @see #getStrictModeRelevant()
	 * @generated
	 */
	EAttribute getStrictModeRelevant_StrictMode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.SuperLiteral <em>Super Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Super Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.SuperLiteral
	 * @generated
	 */
	EClass getSuperLiteral();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.SuperLiteral#isSuperConstructorAccess() <em>Is Super Constructor Access</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Super Constructor Access</em>' operation.
	 * @see org.eclipse.n4js.n4JS.SuperLiteral#isSuperConstructorAccess()
	 * @generated
	 */
	EOperation getSuperLiteral__IsSuperConstructorAccess();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.SuperLiteral#isSuperMemberAccess() <em>Is Super Member Access</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Super Member Access</em>' operation.
	 * @see org.eclipse.n4js.n4JS.SuperLiteral#isSuperMemberAccess()
	 * @generated
	 */
	EOperation getSuperLiteral__IsSuperMemberAccess();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ThisLiteral <em>This Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>This Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.ThisLiteral
	 * @generated
	 */
	EClass getThisLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ArrayLiteral <em>Array Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayLiteral
	 * @generated
	 */
	EClass getArrayLiteral();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ArrayLiteral#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayLiteral#getElements()
	 * @see #getArrayLiteral()
	 * @generated
	 */
	EReference getArrayLiteral_Elements();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ArrayLiteral#isTrailingComma <em>Trailing Comma</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Trailing Comma</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayLiteral#isTrailingComma()
	 * @see #getArrayLiteral()
	 * @generated
	 */
	EAttribute getArrayLiteral_TrailingComma();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ArrayElement <em>Array Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Element</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayElement
	 * @generated
	 */
	EClass getArrayElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ArrayElement#isSpread <em>Spread</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Spread</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayElement#isSpread()
	 * @see #getArrayElement()
	 * @generated
	 */
	EAttribute getArrayElement_Spread();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ArrayElement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayElement#getExpression()
	 * @see #getArrayElement()
	 * @generated
	 */
	EReference getArrayElement_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ArrayPadding <em>Array Padding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Padding</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayPadding
	 * @generated
	 */
	EClass getArrayPadding();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ObjectLiteral <em>Object Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.ObjectLiteral
	 * @generated
	 */
	EClass getObjectLiteral();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ObjectLiteral#getPropertyAssignments <em>Property Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property Assignments</em>'.
	 * @see org.eclipse.n4js.n4JS.ObjectLiteral#getPropertyAssignments()
	 * @see #getObjectLiteral()
	 * @generated
	 */
	EReference getObjectLiteral_PropertyAssignments();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertyAssignment <em>Property Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Assignment</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyAssignment
	 * @generated
	 */
	EClass getPropertyAssignment();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyAssignment#getDefinedMember() <em>Get Defined Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Member</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyAssignment#getDefinedMember()
	 * @generated
	 */
	EOperation getPropertyAssignment__GetDefinedMember();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyAssignment#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyAssignment#isValidName()
	 * @generated
	 */
	EOperation getPropertyAssignment__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertyNameOwner <em>Property Name Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Name Owner</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameOwner
	 * @generated
	 */
	EClass getPropertyNameOwner();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.PropertyNameOwner#getDeclaredName <em>Declared Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Declared Name</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameOwner#getDeclaredName()
	 * @see #getPropertyNameOwner()
	 * @generated
	 */
	EReference getPropertyNameOwner_DeclaredName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyNameOwner#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyNameOwner#getName()
	 * @generated
	 */
	EOperation getPropertyNameOwner__GetName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyNameOwner#hasComputedPropertyName() <em>Has Computed Property Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Computed Property Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyNameOwner#hasComputedPropertyName()
	 * @generated
	 */
	EOperation getPropertyNameOwner__HasComputedPropertyName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyNameOwner#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyNameOwner#isValidName()
	 * @generated
	 */
	EOperation getPropertyNameOwner__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName <em>Literal Or Computed Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal Or Computed Property Name</em>'.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
	 * @generated
	 */
	EClass getLiteralOrComputedPropertyName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getKind()
	 * @see #getLiteralOrComputedPropertyName()
	 * @generated
	 */
	EAttribute getLiteralOrComputedPropertyName_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getLiteralName <em>Literal Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Literal Name</em>'.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getLiteralName()
	 * @see #getLiteralOrComputedPropertyName()
	 * @generated
	 */
	EAttribute getLiteralOrComputedPropertyName_LiteralName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getComputedName <em>Computed Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Computed Name</em>'.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getComputedName()
	 * @see #getLiteralOrComputedPropertyName()
	 * @generated
	 */
	EAttribute getLiteralOrComputedPropertyName_ComputedName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getExpression()
	 * @see #getLiteralOrComputedPropertyName()
	 * @generated
	 */
	EReference getLiteralOrComputedPropertyName_Expression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#hasComputedPropertyName() <em>Has Computed Property Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Computed Property Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#hasComputedPropertyName()
	 * @generated
	 */
	EOperation getLiteralOrComputedPropertyName__HasComputedPropertyName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName#getName()
	 * @generated
	 */
	EOperation getLiteralOrComputedPropertyName__GetName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AnnotablePropertyAssignment <em>Annotable Property Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotable Property Assignment</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotablePropertyAssignment
	 * @generated
	 */
	EClass getAnnotablePropertyAssignment();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AnnotablePropertyAssignment#getAnnotationList <em>Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotablePropertyAssignment#getAnnotationList()
	 * @see #getAnnotablePropertyAssignment()
	 * @generated
	 */
	EReference getAnnotablePropertyAssignment_AnnotationList();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotablePropertyAssignment#getAnnotations() <em>Get Annotations</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotations</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotablePropertyAssignment#getAnnotations()
	 * @generated
	 */
	EOperation getAnnotablePropertyAssignment__GetAnnotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList <em>Property Assignment Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Assignment Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList
	 * @generated
	 */
	EClass getPropertyAssignmentAnnotationList();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList#getDefinedMember() <em>Get Defined Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Member</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyAssignmentAnnotationList#getDefinedMember()
	 * @generated
	 */
	EOperation getPropertyAssignmentAnnotationList__GetDefinedMember();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair <em>Property Name Value Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Name Value Pair</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePair
	 * @generated
	 */
	EClass getPropertyNameValuePair();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#getDefinedField <em>Defined Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Field</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePair#getDefinedField()
	 * @see #getPropertyNameValuePair()
	 * @generated
	 */
	EReference getPropertyNameValuePair_DefinedField();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#isDeclaredOptional <em>Declared Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Optional</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePair#isDeclaredOptional()
	 * @see #getPropertyNameValuePair()
	 * @generated
	 */
	EAttribute getPropertyNameValuePair_DeclaredOptional();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePair#getExpression()
	 * @see #getPropertyNameValuePair()
	 * @generated
	 */
	EReference getPropertyNameValuePair_Expression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#getDefinedMember() <em>Get Defined Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Member</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePair#getDefinedMember()
	 * @generated
	 */
	EOperation getPropertyNameValuePair__GetDefinedMember();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePair#isValidName()
	 * @generated
	 */
	EOperation getPropertyNameValuePair__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName <em>Property Name Value Pair Single Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Name Value Pair Single Name</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName
	 * @generated
	 */
	EClass getPropertyNameValuePairSingleName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName#getIdentifierRef() <em>Get Identifier Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Identifier Ref</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName#getIdentifierRef()
	 * @generated
	 */
	EOperation getPropertyNameValuePairSingleName__GetIdentifierRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName#getName()
	 * @generated
	 */
	EOperation getPropertyNameValuePairSingleName__GetName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertyMethodDeclaration <em>Property Method Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Method Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyMethodDeclaration
	 * @generated
	 */
	EClass getPropertyMethodDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyMethodDeclaration#getDefinedMember() <em>Get Defined Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Member</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyMethodDeclaration#getDefinedMember()
	 * @generated
	 */
	EOperation getPropertyMethodDeclaration__GetDefinedMember();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.GetterDeclaration <em>Getter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Getter Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.GetterDeclaration
	 * @generated
	 */
	EClass getGetterDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.GetterDeclaration#getDefinedGetter <em>Defined Getter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Getter</em>'.
	 * @see org.eclipse.n4js.n4JS.GetterDeclaration#getDefinedGetter()
	 * @see #getGetterDeclaration()
	 * @generated
	 */
	EReference getGetterDeclaration_DefinedGetter();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.GetterDeclaration#getDefinedAccessor() <em>Get Defined Accessor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Accessor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.GetterDeclaration#getDefinedAccessor()
	 * @generated
	 */
	EOperation getGetterDeclaration__GetDefinedAccessor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.SetterDeclaration <em>Setter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Setter Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.SetterDeclaration
	 * @generated
	 */
	EClass getSetterDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.SetterDeclaration#getDefinedSetter <em>Defined Setter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Setter</em>'.
	 * @see org.eclipse.n4js.n4JS.SetterDeclaration#getDefinedSetter()
	 * @see #getSetterDeclaration()
	 * @generated
	 */
	EReference getSetterDeclaration_DefinedSetter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.SetterDeclaration#getFpar <em>Fpar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fpar</em>'.
	 * @see org.eclipse.n4js.n4JS.SetterDeclaration#getFpar()
	 * @see #getSetterDeclaration()
	 * @generated
	 */
	EReference getSetterDeclaration_Fpar();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.SetterDeclaration#getDefinedAccessor() <em>Get Defined Accessor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Accessor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.SetterDeclaration#getDefinedAccessor()
	 * @generated
	 */
	EOperation getSetterDeclaration__GetDefinedAccessor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.SetterDeclaration#getDeclaredTypeRef() <em>Get Declared Type Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type Ref</em>' operation.
	 * @see org.eclipse.n4js.n4JS.SetterDeclaration#getDeclaredTypeRef()
	 * @generated
	 */
	EOperation getSetterDeclaration__GetDeclaredTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertyGetterDeclaration <em>Property Getter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Getter Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyGetterDeclaration
	 * @generated
	 */
	EClass getPropertyGetterDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyGetterDeclaration#getDefinedGetter() <em>Get Defined Getter</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Getter</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyGetterDeclaration#getDefinedGetter()
	 * @generated
	 */
	EOperation getPropertyGetterDeclaration__GetDefinedGetter();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyGetterDeclaration#getDefinedMember() <em>Get Defined Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Member</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyGetterDeclaration#getDefinedMember()
	 * @generated
	 */
	EOperation getPropertyGetterDeclaration__GetDefinedMember();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertyGetterDeclaration#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertyGetterDeclaration#isValidName()
	 * @generated
	 */
	EOperation getPropertyGetterDeclaration__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertySetterDeclaration <em>Property Setter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Setter Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertySetterDeclaration
	 * @generated
	 */
	EClass getPropertySetterDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertySetterDeclaration#getDefinedSetter() <em>Get Defined Setter</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Setter</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertySetterDeclaration#getDefinedSetter()
	 * @generated
	 */
	EOperation getPropertySetterDeclaration__GetDefinedSetter();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertySetterDeclaration#getDefinedMember() <em>Get Defined Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Member</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertySetterDeclaration#getDefinedMember()
	 * @generated
	 */
	EOperation getPropertySetterDeclaration__GetDefinedMember();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertySetterDeclaration#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertySetterDeclaration#isValidName()
	 * @generated
	 */
	EOperation getPropertySetterDeclaration__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PropertySpread <em>Property Spread</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Spread</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertySpread
	 * @generated
	 */
	EClass getPropertySpread();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.PropertySpread#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertySpread#getExpression()
	 * @see #getPropertySpread()
	 * @generated
	 */
	EReference getPropertySpread_Expression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.PropertySpread#getDefinedMember() <em>Get Defined Member</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Member</em>' operation.
	 * @see org.eclipse.n4js.n4JS.PropertySpread#getDefinedMember()
	 * @generated
	 */
	EOperation getPropertySpread__GetDefinedMember();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Expression#isValidSimpleAssignmentTarget() <em>Is Valid Simple Assignment Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Expression#isValidSimpleAssignmentTarget()
	 * @generated
	 */
	EOperation getExpression__IsValidSimpleAssignmentTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.NewTarget <em>New Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>New Target</em>'.
	 * @see org.eclipse.n4js.n4JS.NewTarget
	 * @generated
	 */
	EClass getNewTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.NewExpression <em>New Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>New Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.NewExpression
	 * @generated
	 */
	EClass getNewExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.NewExpression#getCallee <em>Callee</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Callee</em>'.
	 * @see org.eclipse.n4js.n4JS.NewExpression#getCallee()
	 * @see #getNewExpression()
	 * @generated
	 */
	EReference getNewExpression_Callee();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.NewExpression#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see org.eclipse.n4js.n4JS.NewExpression#getArguments()
	 * @see #getNewExpression()
	 * @generated
	 */
	EReference getNewExpression_Arguments();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.NewExpression#isWithArgs <em>With Args</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>With Args</em>'.
	 * @see org.eclipse.n4js.n4JS.NewExpression#isWithArgs()
	 * @see #getNewExpression()
	 * @generated
	 */
	EAttribute getNewExpression_WithArgs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ParameterizedAccess <em>Parameterized Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Access</em>'.
	 * @see org.eclipse.n4js.n4JS.ParameterizedAccess
	 * @generated
	 */
	EClass getParameterizedAccess();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ParameterizedAccess#getTypeArgs <em>Type Args</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Args</em>'.
	 * @see org.eclipse.n4js.n4JS.ParameterizedAccess#getTypeArgs()
	 * @see #getParameterizedAccess()
	 * @generated
	 */
	EReference getParameterizedAccess_TypeArgs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ParameterizedAccess#isParameterized() <em>Is Parameterized</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Parameterized</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ParameterizedAccess#isParameterized()
	 * @generated
	 */
	EOperation getParameterizedAccess__IsParameterized();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ExpressionWithTarget <em>Expression With Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression With Target</em>'.
	 * @see org.eclipse.n4js.n4JS.ExpressionWithTarget
	 * @generated
	 */
	EClass getExpressionWithTarget();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ExpressionWithTarget#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.n4js.n4JS.ExpressionWithTarget#getTarget()
	 * @see #getExpressionWithTarget()
	 * @generated
	 */
	EReference getExpressionWithTarget_Target();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ExpressionWithTarget#isOptionalChaining <em>Optional Chaining</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional Chaining</em>'.
	 * @see org.eclipse.n4js.n4JS.ExpressionWithTarget#isOptionalChaining()
	 * @see #getExpressionWithTarget()
	 * @generated
	 */
	EAttribute getExpressionWithTarget_OptionalChaining();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ExpressionWithTarget#isOrHasTargetWithOptionalChaining() <em>Is Or Has Target With Optional Chaining</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Or Has Target With Optional Chaining</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ExpressionWithTarget#isOrHasTargetWithOptionalChaining()
	 * @generated
	 */
	EOperation getExpressionWithTarget__IsOrHasTargetWithOptionalChaining();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ParameterizedCallExpression <em>Parameterized Call Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Call Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ParameterizedCallExpression
	 * @generated
	 */
	EClass getParameterizedCallExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ParameterizedCallExpression#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see org.eclipse.n4js.n4JS.ParameterizedCallExpression#getArguments()
	 * @see #getParameterizedCallExpression()
	 * @generated
	 */
	EReference getParameterizedCallExpression_Arguments();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ParameterizedCallExpression#getReceiver() <em>Get Receiver</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Receiver</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ParameterizedCallExpression#getReceiver()
	 * @generated
	 */
	EOperation getParameterizedCallExpression__GetReceiver();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ImportCallExpression <em>Import Call Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Call Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportCallExpression
	 * @generated
	 */
	EClass getImportCallExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ImportCallExpression#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see org.eclipse.n4js.n4JS.ImportCallExpression#getArguments()
	 * @see #getImportCallExpression()
	 * @generated
	 */
	EReference getImportCallExpression_Arguments();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ImportCallExpression#getArgument() <em>Get Argument</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Argument</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ImportCallExpression#getArgument()
	 * @generated
	 */
	EOperation getImportCallExpression__GetArgument();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument</em>'.
	 * @see org.eclipse.n4js.n4JS.Argument
	 * @generated
	 */
	EClass getArgument();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.Argument#isSpread <em>Spread</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Spread</em>'.
	 * @see org.eclipse.n4js.n4JS.Argument#isSpread()
	 * @see #getArgument()
	 * @generated
	 */
	EAttribute getArgument_Spread();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.Argument#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.Argument#getExpression()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.IndexedAccessExpression <em>Indexed Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Indexed Access Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.IndexedAccessExpression
	 * @generated
	 */
	EClass getIndexedAccessExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.IndexedAccessExpression#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index</em>'.
	 * @see org.eclipse.n4js.n4JS.IndexedAccessExpression#getIndex()
	 * @see #getIndexedAccessExpression()
	 * @generated
	 */
	EReference getIndexedAccessExpression_Index();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.IndexedAccessExpression#isValidSimpleAssignmentTarget() <em>Is Valid Simple Assignment Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * @see org.eclipse.n4js.n4JS.IndexedAccessExpression#isValidSimpleAssignmentTarget()
	 * @generated
	 */
	EOperation getIndexedAccessExpression__IsValidSimpleAssignmentTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TaggedTemplateString <em>Tagged Template String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tagged Template String</em>'.
	 * @see org.eclipse.n4js.n4JS.TaggedTemplateString
	 * @generated
	 */
	EClass getTaggedTemplateString();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.TaggedTemplateString#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Template</em>'.
	 * @see org.eclipse.n4js.n4JS.TaggedTemplateString#getTemplate()
	 * @see #getTaggedTemplateString()
	 * @generated
	 */
	EReference getTaggedTemplateString_Template();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.MemberAccess <em>Member Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Member Access</em>'.
	 * @see org.eclipse.n4js.n4JS.MemberAccess
	 * @generated
	 */
	EClass getMemberAccess();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.MemberAccess#getComposedMemberCache <em>Composed Member Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Composed Member Cache</em>'.
	 * @see org.eclipse.n4js.n4JS.MemberAccess#getComposedMemberCache()
	 * @see #getMemberAccess()
	 * @generated
	 */
	EReference getMemberAccess_ComposedMemberCache();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression <em>Parameterized Property Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameterized Property Access Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
	 * @generated
	 */
	EClass getParameterizedPropertyAccessExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getProperty()
	 * @see #getParameterizedPropertyAccessExpression()
	 * @generated
	 */
	EReference getParameterizedPropertyAccessExpression_Property();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getPropertyAsText <em>Property As Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property As Text</em>'.
	 * @see org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#getPropertyAsText()
	 * @see #getParameterizedPropertyAccessExpression()
	 * @generated
	 */
	EAttribute getParameterizedPropertyAccessExpression_PropertyAsText();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#isValidSimpleAssignmentTarget() <em>Is Valid Simple Assignment Target</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Simple Assignment Target</em>' operation.
	 * @see org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression#isValidSimpleAssignmentTarget()
	 * @generated
	 */
	EOperation getParameterizedPropertyAccessExpression__IsValidSimpleAssignmentTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AwaitExpression <em>Await Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Await Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.AwaitExpression
	 * @generated
	 */
	EClass getAwaitExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AwaitExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.AwaitExpression#getExpression()
	 * @see #getAwaitExpression()
	 * @generated
	 */
	EReference getAwaitExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PromisifyExpression <em>Promisify Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Promisify Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.PromisifyExpression
	 * @generated
	 */
	EClass getPromisifyExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.PromisifyExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.PromisifyExpression#getExpression()
	 * @see #getPromisifyExpression()
	 * @generated
	 */
	EReference getPromisifyExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.YieldExpression <em>Yield Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Yield Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.YieldExpression
	 * @generated
	 */
	EClass getYieldExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.YieldExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.YieldExpression#getExpression()
	 * @see #getYieldExpression()
	 * @generated
	 */
	EReference getYieldExpression_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.YieldExpression#isMany <em>Many</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Many</em>'.
	 * @see org.eclipse.n4js.n4JS.YieldExpression#isMany()
	 * @see #getYieldExpression()
	 * @generated
	 */
	EAttribute getYieldExpression_Many();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.Literal <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.Literal
	 * @generated
	 */
	EClass getLiteral();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.Literal#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.Literal#getValueAsString()
	 * @generated
	 */
	EOperation getLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.NullLiteral <em>Null Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Null Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.NullLiteral
	 * @generated
	 */
	EClass getNullLiteral();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.NullLiteral#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.NullLiteral#getValueAsString()
	 * @generated
	 */
	EOperation getNullLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BooleanLiteral <em>Boolean Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.BooleanLiteral
	 * @generated
	 */
	EClass getBooleanLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.BooleanLiteral#isTrue <em>True</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>True</em>'.
	 * @see org.eclipse.n4js.n4JS.BooleanLiteral#isTrue()
	 * @see #getBooleanLiteral()
	 * @generated
	 */
	EAttribute getBooleanLiteral_True();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.BooleanLiteral#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.BooleanLiteral#getValueAsString()
	 * @generated
	 */
	EOperation getBooleanLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.StringLiteral <em>String Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.StringLiteral
	 * @generated
	 */
	EClass getStringLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.StringLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.n4JS.StringLiteral#getValue()
	 * @see #getStringLiteral()
	 * @generated
	 */
	EAttribute getStringLiteral_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.StringLiteral#getRawValue <em>Raw Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Raw Value</em>'.
	 * @see org.eclipse.n4js.n4JS.StringLiteral#getRawValue()
	 * @see #getStringLiteral()
	 * @generated
	 */
	EAttribute getStringLiteral_RawValue();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.StringLiteral#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.StringLiteral#getValueAsString()
	 * @generated
	 */
	EOperation getStringLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TemplateLiteral <em>Template Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.TemplateLiteral
	 * @generated
	 */
	EClass getTemplateLiteral();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.TemplateLiteral#getSegments <em>Segments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Segments</em>'.
	 * @see org.eclipse.n4js.n4JS.TemplateLiteral#getSegments()
	 * @see #getTemplateLiteral()
	 * @generated
	 */
	EReference getTemplateLiteral_Segments();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.TemplateLiteral#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.TemplateLiteral#getValueAsString()
	 * @generated
	 */
	EOperation getTemplateLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TemplateSegment <em>Template Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template Segment</em>'.
	 * @see org.eclipse.n4js.n4JS.TemplateSegment
	 * @generated
	 */
	EClass getTemplateSegment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.TemplateSegment#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.n4JS.TemplateSegment#getValue()
	 * @see #getTemplateSegment()
	 * @generated
	 */
	EAttribute getTemplateSegment_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.TemplateSegment#getRawValue <em>Raw Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Raw Value</em>'.
	 * @see org.eclipse.n4js.n4JS.TemplateSegment#getRawValue()
	 * @see #getTemplateSegment()
	 * @generated
	 */
	EAttribute getTemplateSegment_RawValue();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.TemplateSegment#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.TemplateSegment#getValueAsString()
	 * @generated
	 */
	EOperation getTemplateSegment__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.NumericLiteral <em>Numeric Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.NumericLiteral
	 * @generated
	 */
	EClass getNumericLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.NumericLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.n4JS.NumericLiteral#getValue()
	 * @see #getNumericLiteral()
	 * @generated
	 */
	EAttribute getNumericLiteral_Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.NumericLiteral#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.NumericLiteral#getValueAsString()
	 * @generated
	 */
	EOperation getNumericLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.DoubleLiteral
	 * @generated
	 */
	EClass getDoubleLiteral();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.DoubleLiteral#toDouble() <em>To Double</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To Double</em>' operation.
	 * @see org.eclipse.n4js.n4JS.DoubleLiteral#toDouble()
	 * @generated
	 */
	EOperation getDoubleLiteral__ToDouble();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.DoubleLiteral#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.DoubleLiteral#getValueAsString()
	 * @generated
	 */
	EOperation getDoubleLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AbstractIntLiteral <em>Abstract Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Int Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.AbstractIntLiteral
	 * @generated
	 */
	EClass getAbstractIntLiteral();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AbstractIntLiteral#toInt() <em>To Int</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To Int</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AbstractIntLiteral#toInt()
	 * @generated
	 */
	EOperation getAbstractIntLiteral__ToInt();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AbstractIntLiteral#toLong() <em>To Long</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To Long</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AbstractIntLiteral#toLong()
	 * @generated
	 */
	EOperation getAbstractIntLiteral__ToLong();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AbstractIntLiteral#toBigInteger() <em>To Big Integer</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To Big Integer</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AbstractIntLiteral#toBigInteger()
	 * @generated
	 */
	EOperation getAbstractIntLiteral__ToBigInteger();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.IntLiteral <em>Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Int Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.IntLiteral
	 * @generated
	 */
	EClass getIntLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BinaryIntLiteral <em>Binary Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Int Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryIntLiteral
	 * @generated
	 */
	EClass getBinaryIntLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.OctalIntLiteral <em>Octal Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Octal Int Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.OctalIntLiteral
	 * @generated
	 */
	EClass getOctalIntLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.LegacyOctalIntLiteral <em>Legacy Octal Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Legacy Octal Int Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.LegacyOctalIntLiteral
	 * @generated
	 */
	EClass getLegacyOctalIntLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.HexIntLiteral <em>Hex Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hex Int Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.HexIntLiteral
	 * @generated
	 */
	EClass getHexIntLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ScientificIntLiteral <em>Scientific Int Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scientific Int Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.ScientificIntLiteral
	 * @generated
	 */
	EClass getScientificIntLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.RegularExpressionLiteral <em>Regular Expression Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Regular Expression Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.RegularExpressionLiteral
	 * @generated
	 */
	EClass getRegularExpressionLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.RegularExpressionLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.n4JS.RegularExpressionLiteral#getValue()
	 * @see #getRegularExpressionLiteral()
	 * @generated
	 */
	EAttribute getRegularExpressionLiteral_Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.RegularExpressionLiteral#getValueAsString() <em>Get Value As String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value As String</em>' operation.
	 * @see org.eclipse.n4js.n4JS.RegularExpressionLiteral#getValueAsString()
	 * @generated
	 */
	EOperation getRegularExpressionLiteral__GetValueAsString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.PostfixExpression <em>Postfix Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Postfix Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.PostfixExpression
	 * @generated
	 */
	EClass getPostfixExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.PostfixExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.PostfixExpression#getExpression()
	 * @see #getPostfixExpression()
	 * @generated
	 */
	EReference getPostfixExpression_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.PostfixExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.PostfixExpression#getOp()
	 * @see #getPostfixExpression()
	 * @generated
	 */
	EAttribute getPostfixExpression_Op();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.UnaryExpression
	 * @generated
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.UnaryExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.UnaryExpression#getOp()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EAttribute getUnaryExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.UnaryExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.UnaryExpression#getExpression()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EReference getUnaryExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.CastExpression <em>Cast Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cast Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.CastExpression
	 * @generated
	 */
	EClass getCastExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.CastExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.CastExpression#getExpression()
	 * @see #getCastExpression()
	 * @generated
	 */
	EReference getCastExpression_Expression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.CastExpression#getTargetTypeRef <em>Target Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target Type Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.CastExpression#getTargetTypeRef()
	 * @see #getCastExpression()
	 * @generated
	 */
	EReference getCastExpression_TargetTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.MultiplicativeExpression <em>Multiplicative Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multiplicative Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.MultiplicativeExpression
	 * @generated
	 */
	EClass getMultiplicativeExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.MultiplicativeExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.MultiplicativeExpression#getLhs()
	 * @see #getMultiplicativeExpression()
	 * @generated
	 */
	EReference getMultiplicativeExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.MultiplicativeExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.MultiplicativeExpression#getOp()
	 * @see #getMultiplicativeExpression()
	 * @generated
	 */
	EAttribute getMultiplicativeExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.MultiplicativeExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.MultiplicativeExpression#getRhs()
	 * @see #getMultiplicativeExpression()
	 * @generated
	 */
	EReference getMultiplicativeExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AdditiveExpression <em>Additive Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Additive Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.AdditiveExpression
	 * @generated
	 */
	EClass getAdditiveExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AdditiveExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.AdditiveExpression#getLhs()
	 * @see #getAdditiveExpression()
	 * @generated
	 */
	EReference getAdditiveExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.AdditiveExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.AdditiveExpression#getOp()
	 * @see #getAdditiveExpression()
	 * @generated
	 */
	EAttribute getAdditiveExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AdditiveExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.AdditiveExpression#getRhs()
	 * @see #getAdditiveExpression()
	 * @generated
	 */
	EReference getAdditiveExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ShiftExpression <em>Shift Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shift Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ShiftExpression
	 * @generated
	 */
	EClass getShiftExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ShiftExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.ShiftExpression#getLhs()
	 * @see #getShiftExpression()
	 * @generated
	 */
	EReference getShiftExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.ShiftExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.ShiftExpression#getOp()
	 * @see #getShiftExpression()
	 * @generated
	 */
	EAttribute getShiftExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ShiftExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.ShiftExpression#getRhs()
	 * @see #getShiftExpression()
	 * @generated
	 */
	EReference getShiftExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.RelationalExpression <em>Relational Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relational Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.RelationalExpression
	 * @generated
	 */
	EClass getRelationalExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.RelationalExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.RelationalExpression#getLhs()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EReference getRelationalExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.RelationalExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.RelationalExpression#getOp()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EAttribute getRelationalExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.RelationalExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.RelationalExpression#getRhs()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EReference getRelationalExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.EqualityExpression <em>Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Equality Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.EqualityExpression
	 * @generated
	 */
	EClass getEqualityExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.EqualityExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.EqualityExpression#getLhs()
	 * @see #getEqualityExpression()
	 * @generated
	 */
	EReference getEqualityExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.EqualityExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.EqualityExpression#getOp()
	 * @see #getEqualityExpression()
	 * @generated
	 */
	EAttribute getEqualityExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.EqualityExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.EqualityExpression#getRhs()
	 * @see #getEqualityExpression()
	 * @generated
	 */
	EReference getEqualityExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BinaryBitwiseExpression <em>Binary Bitwise Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Bitwise Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryBitwiseExpression
	 * @generated
	 */
	EClass getBinaryBitwiseExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BinaryBitwiseExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryBitwiseExpression#getLhs()
	 * @see #getBinaryBitwiseExpression()
	 * @generated
	 */
	EReference getBinaryBitwiseExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.BinaryBitwiseExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryBitwiseExpression#getOp()
	 * @see #getBinaryBitwiseExpression()
	 * @generated
	 */
	EAttribute getBinaryBitwiseExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BinaryBitwiseExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryBitwiseExpression#getRhs()
	 * @see #getBinaryBitwiseExpression()
	 * @generated
	 */
	EReference getBinaryBitwiseExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression <em>Binary Logical Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Logical Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalExpression
	 * @generated
	 */
	EClass getBinaryLogicalExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalExpression#getLhs()
	 * @see #getBinaryLogicalExpression()
	 * @generated
	 */
	EReference getBinaryLogicalExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalExpression#getOp()
	 * @see #getBinaryLogicalExpression()
	 * @generated
	 */
	EAttribute getBinaryLogicalExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BinaryLogicalExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalExpression#getRhs()
	 * @see #getBinaryLogicalExpression()
	 * @generated
	 */
	EReference getBinaryLogicalExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.CoalesceExpression <em>Coalesce Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Coalesce Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.CoalesceExpression
	 * @generated
	 */
	EClass getCoalesceExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.CoalesceExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.CoalesceExpression#getExpression()
	 * @see #getCoalesceExpression()
	 * @generated
	 */
	EReference getCoalesceExpression_Expression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.CoalesceExpression#getDefaultExpression <em>Default Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Default Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.CoalesceExpression#getDefaultExpression()
	 * @see #getCoalesceExpression()
	 * @generated
	 */
	EReference getCoalesceExpression_DefaultExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ConditionalExpression <em>Conditional Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conditional Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ConditionalExpression
	 * @generated
	 */
	EClass getConditionalExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ConditionalExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ConditionalExpression#getExpression()
	 * @see #getConditionalExpression()
	 * @generated
	 */
	EReference getConditionalExpression_Expression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ConditionalExpression#getTrueExpression <em>True Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>True Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ConditionalExpression#getTrueExpression()
	 * @see #getConditionalExpression()
	 * @generated
	 */
	EReference getConditionalExpression_TrueExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.ConditionalExpression#getFalseExpression <em>False Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>False Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.ConditionalExpression#getFalseExpression()
	 * @see #getConditionalExpression()
	 * @generated
	 */
	EReference getConditionalExpression_FalseExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AssignmentExpression <em>Assignment Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assignment Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.AssignmentExpression
	 * @generated
	 */
	EClass getAssignmentExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AssignmentExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.eclipse.n4js.n4JS.AssignmentExpression#getLhs()
	 * @see #getAssignmentExpression()
	 * @generated
	 */
	EReference getAssignmentExpression_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.AssignmentExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.n4js.n4JS.AssignmentExpression#getOp()
	 * @see #getAssignmentExpression()
	 * @generated
	 */
	EAttribute getAssignmentExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AssignmentExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.eclipse.n4js.n4JS.AssignmentExpression#getRhs()
	 * @see #getAssignmentExpression()
	 * @generated
	 */
	EReference getAssignmentExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.CommaExpression <em>Comma Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comma Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.CommaExpression
	 * @generated
	 */
	EClass getCommaExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.CommaExpression#getExprs <em>Exprs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Exprs</em>'.
	 * @see org.eclipse.n4js.n4JS.CommaExpression#getExprs()
	 * @see #getCommaExpression()
	 * @generated
	 */
	EReference getCommaExpression_Exprs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.TypeDefiningElement <em>Type Defining Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Defining Element</em>'.
	 * @see org.eclipse.n4js.n4JS.TypeDefiningElement
	 * @generated
	 */
	EClass getTypeDefiningElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.TypeDefiningElement#getDefinedType <em>Defined Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Type</em>'.
	 * @see org.eclipse.n4js.n4JS.TypeDefiningElement#getDefinedType()
	 * @see #getTypeDefiningElement()
	 * @generated
	 */
	EReference getTypeDefiningElement_DefinedType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.GenericDeclaration <em>Generic Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.GenericDeclaration
	 * @generated
	 */
	EClass getGenericDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.GenericDeclaration#getTypeVars <em>Type Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Vars</em>'.
	 * @see org.eclipse.n4js.n4JS.GenericDeclaration#getTypeVars()
	 * @see #getGenericDeclaration()
	 * @generated
	 */
	EReference getGenericDeclaration_TypeVars();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4TypeDefinition <em>N4 Type Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Type Definition</em>'.
	 * @see org.eclipse.n4js.n4JS.N4TypeDefinition
	 * @generated
	 */
	EClass getN4TypeDefinition();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4TypeDefinition#isExternal() <em>Is External</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is External</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4TypeDefinition#isExternal()
	 * @generated
	 */
	EOperation getN4TypeDefinition__IsExternal();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4TypeDeclaration <em>N4 Type Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Type Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4TypeDeclaration
	 * @generated
	 */
	EClass getN4TypeDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.N4TypeDeclaration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.n4JS.N4TypeDeclaration#getName()
	 * @see #getN4TypeDeclaration()
	 * @generated
	 */
	EAttribute getN4TypeDeclaration_Name();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4TypeDeclaration#isExternal() <em>Is External</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is External</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4TypeDeclaration#isExternal()
	 * @generated
	 */
	EOperation getN4TypeDeclaration__IsExternal();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4ClassifierDeclaration <em>N4 Classifier Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Classifier Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDeclaration
	 * @generated
	 */
	EClass getN4ClassifierDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.N4ClassifierDeclaration#getTypingStrategy <em>Typing Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Typing Strategy</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDeclaration#getTypingStrategy()
	 * @see #getN4ClassifierDeclaration()
	 * @generated
	 */
	EAttribute getN4ClassifierDeclaration_TypingStrategy();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition <em>N4 Classifier Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Classifier Definition</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition
	 * @generated
	 */
	EClass getN4ClassifierDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembersRaw <em>Owned Members Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Members Raw</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembersRaw()
	 * @see #getN4ClassifierDefinition()
	 * @generated
	 */
	EReference getN4ClassifierDefinition_OwnedMembersRaw();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembers() <em>Get Owned Members</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Members</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMembers()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetOwnedMembers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedCtor() <em>Get Owned Ctor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Ctor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedCtor()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetOwnedCtor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedCallableCtor() <em>Get Owned Callable Ctor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Callable Ctor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedCallableCtor()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetOwnedCallableCtor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMethods() <em>Get Owned Methods</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Methods</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedMethods()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetOwnedMethods();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedFields() <em>Get Owned Fields</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Fields</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedFields()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetOwnedFields();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedGetters() <em>Get Owned Getters</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Getters</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedGetters()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetOwnedGetters();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedSetters() <em>Get Owned Setters</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Owned Setters</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getOwnedSetters()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetOwnedSetters();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getSuperClassifierRefs() <em>Get Super Classifier Refs</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Super Classifier Refs</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getSuperClassifierRefs()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetSuperClassifierRefs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassifierDefinition#getImplementedOrExtendedInterfaceRefs() <em>Get Implemented Or Extended Interface Refs</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassifierDefinition#getImplementedOrExtendedInterfaceRefs()
	 * @generated
	 */
	EOperation getN4ClassifierDefinition__GetImplementedOrExtendedInterfaceRefs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4ClassDefinition <em>N4 Class Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Class Definition</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition
	 * @generated
	 */
	EClass getN4ClassDefinition();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.N4ClassDefinition#getSuperClassRef <em>Super Class Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Super Class Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition#getSuperClassRef()
	 * @see #getN4ClassDefinition()
	 * @generated
	 */
	EReference getN4ClassDefinition_SuperClassRef();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.N4ClassDefinition#getSuperClassExpression <em>Super Class Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Super Class Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition#getSuperClassExpression()
	 * @see #getN4ClassDefinition()
	 * @generated
	 */
	EReference getN4ClassDefinition_SuperClassExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.N4ClassDefinition#getImplementedInterfaceRefs <em>Implemented Interface Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implemented Interface Refs</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition#getImplementedInterfaceRefs()
	 * @see #getN4ClassDefinition()
	 * @generated
	 */
	EReference getN4ClassDefinition_ImplementedInterfaceRefs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassDefinition#getDefinedTypeAsClass() <em>Get Defined Type As Class</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type As Class</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition#getDefinedTypeAsClass()
	 * @generated
	 */
	EOperation getN4ClassDefinition__GetDefinedTypeAsClass();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassDefinition#getSuperClassifierRefs() <em>Get Super Classifier Refs</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Super Classifier Refs</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition#getSuperClassifierRefs()
	 * @generated
	 */
	EOperation getN4ClassDefinition__GetSuperClassifierRefs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassDefinition#getImplementedOrExtendedInterfaceRefs() <em>Get Implemented Or Extended Interface Refs</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassDefinition#getImplementedOrExtendedInterfaceRefs()
	 * @generated
	 */
	EOperation getN4ClassDefinition__GetImplementedOrExtendedInterfaceRefs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4ClassDeclaration <em>N4 Class Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Class Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassDeclaration
	 * @generated
	 */
	EClass getN4ClassDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassDeclaration#isAbstract() <em>Is Abstract</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Abstract</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassDeclaration#isAbstract()
	 * @generated
	 */
	EOperation getN4ClassDeclaration__IsAbstract();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4ClassDeclaration#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4ClassDeclaration#getVersion()
	 * @generated
	 */
	EOperation getN4ClassDeclaration__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4ClassExpression <em>N4 Class Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Class Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassExpression
	 * @generated
	 */
	EClass getN4ClassExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.N4ClassExpression#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.n4JS.N4ClassExpression#getName()
	 * @see #getN4ClassExpression()
	 * @generated
	 */
	EAttribute getN4ClassExpression_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration <em>N4 Interface Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Interface Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4InterfaceDeclaration
	 * @generated
	 */
	EClass getN4InterfaceDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getSuperInterfaceRefs <em>Super Interface Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Super Interface Refs</em>'.
	 * @see org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getSuperInterfaceRefs()
	 * @see #getN4InterfaceDeclaration()
	 * @generated
	 */
	EReference getN4InterfaceDeclaration_SuperInterfaceRefs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getDefinedTypeAsInterface() <em>Get Defined Type As Interface</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type As Interface</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getDefinedTypeAsInterface()
	 * @generated
	 */
	EOperation getN4InterfaceDeclaration__GetDefinedTypeAsInterface();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getSuperClassifierRefs() <em>Get Super Classifier Refs</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Super Classifier Refs</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getSuperClassifierRefs()
	 * @generated
	 */
	EOperation getN4InterfaceDeclaration__GetSuperClassifierRefs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getImplementedOrExtendedInterfaceRefs() <em>Get Implemented Or Extended Interface Refs</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Implemented Or Extended Interface Refs</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getImplementedOrExtendedInterfaceRefs()
	 * @generated
	 */
	EOperation getN4InterfaceDeclaration__GetImplementedOrExtendedInterfaceRefs();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4InterfaceDeclaration#getVersion()
	 * @generated
	 */
	EOperation getN4InterfaceDeclaration__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4EnumDeclaration <em>N4 Enum Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Enum Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4EnumDeclaration
	 * @generated
	 */
	EClass getN4EnumDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.N4EnumDeclaration#getLiterals <em>Literals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Literals</em>'.
	 * @see org.eclipse.n4js.n4JS.N4EnumDeclaration#getLiterals()
	 * @see #getN4EnumDeclaration()
	 * @generated
	 */
	EReference getN4EnumDeclaration_Literals();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4EnumDeclaration#getDefinedTypeAsEnum() <em>Get Defined Type As Enum</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type As Enum</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4EnumDeclaration#getDefinedTypeAsEnum()
	 * @generated
	 */
	EOperation getN4EnumDeclaration__GetDefinedTypeAsEnum();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4EnumDeclaration#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4EnumDeclaration#getVersion()
	 * @generated
	 */
	EOperation getN4EnumDeclaration__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4EnumLiteral <em>N4 Enum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Enum Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.N4EnumLiteral
	 * @generated
	 */
	EClass getN4EnumLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.n4JS.N4EnumLiteral#getName()
	 * @see #getN4EnumLiteral()
	 * @generated
	 */
	EAttribute getN4EnumLiteral_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getValueExpression <em>Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.N4EnumLiteral#getValueExpression()
	 * @see #getN4EnumLiteral()
	 * @generated
	 */
	EReference getN4EnumLiteral_ValueExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.N4EnumLiteral#getDefinedLiteral <em>Defined Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Literal</em>'.
	 * @see org.eclipse.n4js.n4JS.N4EnumLiteral#getDefinedLiteral()
	 * @see #getN4EnumLiteral()
	 * @generated
	 */
	EReference getN4EnumLiteral_DefinedLiteral();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4TypeAliasDeclaration <em>N4 Type Alias Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Type Alias Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4TypeAliasDeclaration
	 * @generated
	 */
	EClass getN4TypeAliasDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.N4TypeAliasDeclaration#getActualTypeRef <em>Actual Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Actual Type Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.N4TypeAliasDeclaration#getActualTypeRef()
	 * @see #getN4TypeAliasDeclaration()
	 * @generated
	 */
	EReference getN4TypeAliasDeclaration_ActualTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ModifiableElement <em>Modifiable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Modifiable Element</em>'.
	 * @see org.eclipse.n4js.n4JS.ModifiableElement
	 * @generated
	 */
	EClass getModifiableElement();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.n4JS.ModifiableElement#getDeclaredModifiers <em>Declared Modifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Declared Modifiers</em>'.
	 * @see org.eclipse.n4js.n4JS.ModifiableElement#getDeclaredModifiers()
	 * @see #getModifiableElement()
	 * @generated
	 */
	EAttribute getModifiableElement_DeclaredModifiers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration <em>N4 Member Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Member Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration
	 * @generated
	 */
	EClass getN4MemberDeclaration();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#getOwner()
	 * @see #getN4MemberDeclaration()
	 * @generated
	 */
	EReference getN4MemberDeclaration_Owner();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#getDefinedTypeElement() <em>Get Defined Type Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#getDefinedTypeElement()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__GetDefinedTypeElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isDeclaredAbstract() <em>Is Declared Abstract</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Declared Abstract</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isDeclaredAbstract()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsDeclaredAbstract();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isAbstract() <em>Is Abstract</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Abstract</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isAbstract()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsAbstract();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isDeclaredStatic() <em>Is Declared Static</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Declared Static</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isDeclaredStatic()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsDeclaredStatic();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isStatic() <em>Is Static</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Static</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isStatic()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsStatic();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isDeclaredFinal() <em>Is Declared Final</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Declared Final</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isDeclaredFinal()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsDeclaredFinal();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isFinal() <em>Is Final</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Final</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isFinal()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsFinal();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isConstructor() <em>Is Constructor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Constructor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isConstructor()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsConstructor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberDeclaration#isCallableConstructor() <em>Is Callable Constructor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Callable Constructor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberDeclaration#isCallableConstructor()
	 * @generated
	 */
	EOperation getN4MemberDeclaration__IsCallableConstructor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration <em>Annotable N4 Member Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotable N4 Member Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration
	 * @generated
	 */
	EClass getAnnotableN4MemberDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration#getAnnotationList <em>Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration#getAnnotationList()
	 * @see #getAnnotableN4MemberDeclaration()
	 * @generated
	 */
	EReference getAnnotableN4MemberDeclaration_AnnotationList();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration#getAnnotations() <em>Get Annotations</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotations</em>' operation.
	 * @see org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration#getAnnotations()
	 * @generated
	 */
	EOperation getAnnotableN4MemberDeclaration__GetAnnotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4MemberAnnotationList <em>N4 Member Annotation List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Member Annotation List</em>'.
	 * @see org.eclipse.n4js.n4JS.N4MemberAnnotationList
	 * @generated
	 */
	EClass getN4MemberAnnotationList();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberAnnotationList#getDefinedTypeElement() <em>Get Defined Type Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberAnnotationList#getDefinedTypeElement()
	 * @generated
	 */
	EOperation getN4MemberAnnotationList__GetDefinedTypeElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberAnnotationList#getDeclaredTypeRef() <em>Get Declared Type Ref</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Type Ref</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberAnnotationList#getDeclaredTypeRef()
	 * @generated
	 */
	EOperation getN4MemberAnnotationList__GetDeclaredTypeRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MemberAnnotationList#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MemberAnnotationList#getName()
	 * @generated
	 */
	EOperation getN4MemberAnnotationList__GetName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration <em>N4 Field Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Field Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration
	 * @generated
	 */
	EClass getN4FieldDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#getDefinedField <em>Defined Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Defined Field</em>'.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#getDefinedField()
	 * @see #getN4FieldDeclaration()
	 * @generated
	 */
	EReference getN4FieldDeclaration_DefinedField();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#isDeclaredOptional <em>Declared Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Optional</em>'.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#isDeclaredOptional()
	 * @see #getN4FieldDeclaration()
	 * @generated
	 */
	EAttribute getN4FieldDeclaration_DeclaredOptional();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#getExpression()
	 * @see #getN4FieldDeclaration()
	 * @generated
	 */
	EReference getN4FieldDeclaration_Expression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#getDefinedTypeElement() <em>Get Defined Type Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#getDefinedTypeElement()
	 * @generated
	 */
	EOperation getN4FieldDeclaration__GetDefinedTypeElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#isConst() <em>Is Const</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Const</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#isConst()
	 * @generated
	 */
	EOperation getN4FieldDeclaration__IsConst();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#isStatic() <em>Is Static</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Static</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#isStatic()
	 * @generated
	 */
	EOperation getN4FieldDeclaration__IsStatic();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#isValid() <em>Is Valid</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#isValid()
	 * @generated
	 */
	EOperation getN4FieldDeclaration__IsValid();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4FieldDeclaration#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4FieldDeclaration#isValidName()
	 * @generated
	 */
	EOperation getN4FieldDeclaration__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.MethodDeclaration <em>Method Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.MethodDeclaration
	 * @generated
	 */
	EClass getMethodDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.MethodDeclaration#existsExplicitSuperCall() <em>Exists Explicit Super Call</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Exists Explicit Super Call</em>' operation.
	 * @see org.eclipse.n4js.n4JS.MethodDeclaration#existsExplicitSuperCall()
	 * @generated
	 */
	EOperation getMethodDeclaration__ExistsExplicitSuperCall();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.MethodDeclaration#getDefinedTypeElement() <em>Get Defined Type Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.MethodDeclaration#getDefinedTypeElement()
	 * @generated
	 */
	EOperation getMethodDeclaration__GetDefinedTypeElement();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.MethodDeclaration#isStatic() <em>Is Static</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Static</em>' operation.
	 * @see org.eclipse.n4js.n4JS.MethodDeclaration#isStatic()
	 * @generated
	 */
	EOperation getMethodDeclaration__IsStatic();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration <em>N4 Method Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Method Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4MethodDeclaration
	 * @generated
	 */
	EClass getN4MethodDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isAbstract() <em>Is Abstract</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Abstract</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MethodDeclaration#isAbstract()
	 * @generated
	 */
	EOperation getN4MethodDeclaration__IsAbstract();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isConstructor() <em>Is Constructor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Constructor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MethodDeclaration#isConstructor()
	 * @generated
	 */
	EOperation getN4MethodDeclaration__IsConstructor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isCallableConstructor() <em>Is Callable Constructor</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Callable Constructor</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MethodDeclaration#isCallableConstructor()
	 * @generated
	 */
	EOperation getN4MethodDeclaration__IsCallableConstructor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isStatic() <em>Is Static</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Static</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MethodDeclaration#isStatic()
	 * @generated
	 */
	EOperation getN4MethodDeclaration__IsStatic();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4MethodDeclaration#isValidName()
	 * @generated
	 */
	EOperation getN4MethodDeclaration__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4FieldAccessor <em>N4 Field Accessor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Field Accessor</em>'.
	 * @see org.eclipse.n4js.n4JS.N4FieldAccessor
	 * @generated
	 */
	EClass getN4FieldAccessor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4FieldAccessor#isAbstract() <em>Is Abstract</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Abstract</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4FieldAccessor#isAbstract()
	 * @generated
	 */
	EOperation getN4FieldAccessor__IsAbstract();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4FieldAccessor#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4FieldAccessor#isValidName()
	 * @generated
	 */
	EOperation getN4FieldAccessor__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4GetterDeclaration <em>N4 Getter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Getter Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4GetterDeclaration
	 * @generated
	 */
	EClass getN4GetterDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4GetterDeclaration#getDefinedTypeElement() <em>Get Defined Type Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4GetterDeclaration#getDefinedTypeElement()
	 * @generated
	 */
	EOperation getN4GetterDeclaration__GetDefinedTypeElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.N4SetterDeclaration <em>N4 Setter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>N4 Setter Declaration</em>'.
	 * @see org.eclipse.n4js.n4JS.N4SetterDeclaration
	 * @generated
	 */
	EClass getN4SetterDeclaration();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.N4SetterDeclaration#getDefinedTypeElement() <em>Get Defined Type Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Defined Type Element</em>' operation.
	 * @see org.eclipse.n4js.n4JS.N4SetterDeclaration#getDefinedTypeElement()
	 * @generated
	 */
	EOperation getN4SetterDeclaration__GetDefinedTypeElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BindingPattern <em>Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binding Pattern</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingPattern
	 * @generated
	 */
	EClass getBindingPattern();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ObjectBindingPattern <em>Object Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Binding Pattern</em>'.
	 * @see org.eclipse.n4js.n4JS.ObjectBindingPattern
	 * @generated
	 */
	EClass getObjectBindingPattern();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ObjectBindingPattern#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.eclipse.n4js.n4JS.ObjectBindingPattern#getProperties()
	 * @see #getObjectBindingPattern()
	 * @generated
	 */
	EReference getObjectBindingPattern_Properties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.ArrayBindingPattern <em>Array Binding Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Binding Pattern</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayBindingPattern
	 * @generated
	 */
	EClass getArrayBindingPattern();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.ArrayBindingPattern#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.n4js.n4JS.ArrayBindingPattern#getElements()
	 * @see #getArrayBindingPattern()
	 * @generated
	 */
	EReference getArrayBindingPattern_Elements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BindingProperty <em>Binding Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binding Property</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingProperty
	 * @generated
	 */
	EClass getBindingProperty();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BindingProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingProperty#getValue()
	 * @see #getBindingProperty()
	 * @generated
	 */
	EReference getBindingProperty_Value();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.BindingProperty#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.BindingProperty#getName()
	 * @generated
	 */
	EOperation getBindingProperty__GetName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.BindingProperty#isValidName() <em>Is Valid Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Valid Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.BindingProperty#isValidName()
	 * @generated
	 */
	EOperation getBindingProperty__IsValidName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.BindingElement <em>Binding Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binding Element</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingElement
	 * @generated
	 */
	EClass getBindingElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.BindingElement#isRest <em>Rest</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rest</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingElement#isRest()
	 * @see #getBindingElement()
	 * @generated
	 */
	EAttribute getBindingElement_Rest();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BindingElement#getVarDecl <em>Var Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Var Decl</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingElement#getVarDecl()
	 * @see #getBindingElement()
	 * @generated
	 */
	EReference getBindingElement_VarDecl();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BindingElement#getNestedPattern <em>Nested Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Nested Pattern</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingElement#getNestedPattern()
	 * @see #getBindingElement()
	 * @generated
	 */
	EReference getBindingElement_NestedPattern();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.BindingElement#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.BindingElement#getExpression()
	 * @see #getBindingElement()
	 * @generated
	 */
	EReference getBindingElement_Expression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.BindingElement#isElision() <em>Is Elision</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Elision</em>' operation.
	 * @see org.eclipse.n4js.n4JS.BindingElement#isElision()
	 * @generated
	 */
	EOperation getBindingElement__IsElision();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXChild <em>JSX Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Child</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXChild
	 * @generated
	 */
	EClass getJSXChild();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXElementName <em>JSX Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Element Name</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXElementName
	 * @generated
	 */
	EClass getJSXElementName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.JSXElementName#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXElementName#getExpression()
	 * @see #getJSXElementName()
	 * @generated
	 */
	EReference getJSXElementName_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXText <em>JSX Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Text</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXText
	 * @generated
	 */
	EClass getJSXText();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXExpression <em>JSX Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXExpression
	 * @generated
	 */
	EClass getJSXExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.JSXExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXExpression#getExpression()
	 * @see #getJSXExpression()
	 * @generated
	 */
	EReference getJSXExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXAttribute <em>JSX Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Attribute</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXAttribute
	 * @generated
	 */
	EClass getJSXAttribute();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute <em>JSX Property Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Property Attribute</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXPropertyAttribute
	 * @generated
	 */
	EClass getJSXPropertyAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXPropertyAttribute#getProperty()
	 * @see #getJSXPropertyAttribute()
	 * @generated
	 */
	EReference getJSXPropertyAttribute_Property();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getPropertyAsText <em>Property As Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property As Text</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXPropertyAttribute#getPropertyAsText()
	 * @see #getJSXPropertyAttribute()
	 * @generated
	 */
	EAttribute getJSXPropertyAttribute_PropertyAsText();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getJsxAttributeValue <em>Jsx Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Jsx Attribute Value</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXPropertyAttribute#getJsxAttributeValue()
	 * @see #getJSXPropertyAttribute()
	 * @generated
	 */
	EReference getJSXPropertyAttribute_JsxAttributeValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXSpreadAttribute <em>JSX Spread Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Spread Attribute</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXSpreadAttribute
	 * @generated
	 */
	EClass getJSXSpreadAttribute();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.JSXSpreadAttribute#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXSpreadAttribute#getExpression()
	 * @see #getJSXSpreadAttribute()
	 * @generated
	 */
	EReference getJSXSpreadAttribute_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXAbstractElement <em>JSX Abstract Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Abstract Element</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXAbstractElement
	 * @generated
	 */
	EClass getJSXAbstractElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.JSXAbstractElement#getJsxChildren <em>Jsx Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Jsx Children</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXAbstractElement#getJsxChildren()
	 * @see #getJSXAbstractElement()
	 * @generated
	 */
	EReference getJSXAbstractElement_JsxChildren();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXElement <em>JSX Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Element</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXElement
	 * @generated
	 */
	EClass getJSXElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.JSXElement#getJsxElementName <em>Jsx Element Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Jsx Element Name</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXElement#getJsxElementName()
	 * @see #getJSXElement()
	 * @generated
	 */
	EReference getJSXElement_JsxElementName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4JS.JSXElement#getJsxAttributes <em>Jsx Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Jsx Attributes</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXElement#getJsxAttributes()
	 * @see #getJSXElement()
	 * @generated
	 */
	EReference getJSXElement_JsxAttributes();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4JS.JSXElement#getJsxClosingName <em>Jsx Closing Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Jsx Closing Name</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXElement#getJsxClosingName()
	 * @see #getJSXElement()
	 * @generated
	 */
	EReference getJSXElement_JsxClosingName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.JSXFragment <em>JSX Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JSX Fragment</em>'.
	 * @see org.eclipse.n4js.n4JS.JSXFragment
	 * @generated
	 */
	EClass getJSXFragment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VersionedElement <em>Versioned Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Element</em>'.
	 * @see org.eclipse.n4js.n4JS.VersionedElement
	 * @generated
	 */
	EClass getVersionedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4JS.VersionedElement#getDeclaredVersion <em>Declared Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Version</em>'.
	 * @see org.eclipse.n4js.n4JS.VersionedElement#getDeclaredVersion()
	 * @see #getVersionedElement()
	 * @generated
	 */
	EAttribute getVersionedElement_DeclaredVersion();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VersionedElement#hasDeclaredVersion() <em>Has Declared Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Declared Version</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VersionedElement#hasDeclaredVersion()
	 * @generated
	 */
	EOperation getVersionedElement__HasDeclaredVersion();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VersionedElement#getDeclaredVersionOrZero() <em>Get Declared Version Or Zero</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Declared Version Or Zero</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VersionedElement#getDeclaredVersionOrZero()
	 * @generated
	 */
	EOperation getVersionedElement__GetDeclaredVersionOrZero();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.VersionedIdentifierRef <em>Versioned Identifier Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Versioned Identifier Ref</em>'.
	 * @see org.eclipse.n4js.n4JS.VersionedIdentifierRef
	 * @generated
	 */
	EClass getVersionedIdentifierRef();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.VersionedIdentifierRef#getVersion() <em>Get Version</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Version</em>' operation.
	 * @see org.eclipse.n4js.n4JS.VersionedIdentifierRef#getVersion()
	 * @generated
	 */
	EOperation getVersionedIdentifierRef__GetVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4JS.MigrationContextVariable <em>Migration Context Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Migration Context Variable</em>'.
	 * @see org.eclipse.n4js.n4JS.MigrationContextVariable
	 * @generated
	 */
	EClass getMigrationContextVariable();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4JS.MigrationContextVariable#getName() <em>Get Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Name</em>' operation.
	 * @see org.eclipse.n4js.n4JS.MigrationContextVariable#getName()
	 * @generated
	 */
	EOperation getMigrationContextVariable__GetName();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.ModuleSpecifierForm <em>Module Specifier Form</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Module Specifier Form</em>'.
	 * @see org.eclipse.n4js.n4JS.ModuleSpecifierForm
	 * @generated
	 */
	EEnum getModuleSpecifierForm();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.VariableStatementKeyword <em>Variable Statement Keyword</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variable Statement Keyword</em>'.
	 * @see org.eclipse.n4js.n4JS.VariableStatementKeyword
	 * @generated
	 */
	EEnum getVariableStatementKeyword();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.PropertyNameKind <em>Property Name Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Property Name Kind</em>'.
	 * @see org.eclipse.n4js.n4JS.PropertyNameKind
	 * @generated
	 */
	EEnum getPropertyNameKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.PostfixOperator <em>Postfix Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Postfix Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.PostfixOperator
	 * @generated
	 */
	EEnum getPostfixOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.UnaryOperator <em>Unary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unary Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.UnaryOperator
	 * @generated
	 */
	EEnum getUnaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.MultiplicativeOperator <em>Multiplicative Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Multiplicative Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.MultiplicativeOperator
	 * @generated
	 */
	EEnum getMultiplicativeOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.AdditiveOperator <em>Additive Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Additive Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.AdditiveOperator
	 * @generated
	 */
	EEnum getAdditiveOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.RelationalOperator <em>Relational Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Relational Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.RelationalOperator
	 * @generated
	 */
	EEnum getRelationalOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.EqualityOperator <em>Equality Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Equality Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.EqualityOperator
	 * @generated
	 */
	EEnum getEqualityOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.BinaryBitwiseOperator <em>Binary Bitwise Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binary Bitwise Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryBitwiseOperator
	 * @generated
	 */
	EEnum getBinaryBitwiseOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.BinaryLogicalOperator <em>Binary Logical Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binary Logical Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.BinaryLogicalOperator
	 * @generated
	 */
	EEnum getBinaryLogicalOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.ShiftOperator <em>Shift Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Shift Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.ShiftOperator
	 * @generated
	 */
	EEnum getShiftOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.AssignmentOperator <em>Assignment Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Assignment Operator</em>'.
	 * @see org.eclipse.n4js.n4JS.AssignmentOperator
	 * @generated
	 */
	EEnum getAssignmentOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4JS.N4Modifier <em>N4 Modifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>N4 Modifier</em>'.
	 * @see org.eclipse.n4js.n4JS.N4Modifier
	 * @generated
	 */
	EEnum getN4Modifier();

	/**
	 * Returns the meta object for data type '{@link java.util.Iterator <em>Iterator Of Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Iterator Of Expression</em>'.
	 * @see java.util.Iterator
	 * @model instanceClass="java.util.Iterator&lt;org.eclipse.n4js.n4JS.Expression&gt;"
	 * @generated
	 */
	EDataType getIteratorOfExpression();

	/**
	 * Returns the meta object for data type '{@link java.util.Iterator <em>Iterator Of Yield Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Iterator Of Yield Expression</em>'.
	 * @see java.util.Iterator
	 * @model instanceClass="java.util.Iterator&lt;org.eclipse.n4js.n4JS.YieldExpression&gt;"
	 * @generated
	 */
	EDataType getIteratorOfYieldExpression();

	/**
	 * Returns the meta object for data type '{@link java.util.Iterator <em>Iterator Of Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Iterator Of Statement</em>'.
	 * @see java.util.Iterator
	 * @model instanceClass="java.util.Iterator&lt;org.eclipse.n4js.n4JS.Statement&gt;"
	 * @generated
	 */
	EDataType getIteratorOfStatement();

	/**
	 * Returns the meta object for data type '{@link java.util.Iterator <em>Iterator Of Return Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Iterator Of Return Statement</em>'.
	 * @see java.util.Iterator
	 * @model instanceClass="java.util.Iterator&lt;org.eclipse.n4js.n4JS.ReturnStatement&gt;"
	 * @generated
	 */
	EDataType getIteratorOfReturnStatement();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	N4JSFactory getN4JSFactory();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.NamedElement <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.NamedElement
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_ELEMENT___GET_NAME = eINSTANCE.getNamedElement__GetName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.ControlFlowElement <em>Control Flow Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.ControlFlowElement
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getControlFlowElement()
		 * @generated
		 */
		EClass CONTROL_FLOW_ELEMENT = eINSTANCE.getControlFlowElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ScriptImpl <em>Script</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ScriptImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getScript()
		 * @generated
		 */
		EClass SCRIPT = eINSTANCE.getScript();

		/**
		 * The meta object literal for the '<em><b>Hashbang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPT__HASHBANG = eINSTANCE.getScript_Hashbang();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCRIPT__ANNOTATIONS = eINSTANCE.getScript_Annotations();

		/**
		 * The meta object literal for the '<em><b>Script Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCRIPT__SCRIPT_ELEMENTS = eINSTANCE.getScript_ScriptElements();

		/**
		 * The meta object literal for the '<em><b>Module</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCRIPT__MODULE = eINSTANCE.getScript_Module();

		/**
		 * The meta object literal for the '<em><b>Flagged Usage Marking Finished</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCRIPT__FLAGGED_USAGE_MARKING_FINISHED = eINSTANCE.getScript_FlaggedUsageMarkingFinished();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ScriptElementImpl <em>Script Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ScriptElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getScriptElement()
		 * @generated
		 */
		EClass SCRIPT_ELEMENT = eINSTANCE.getScriptElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl <em>Export Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExportDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportDeclaration()
		 * @generated
		 */
		EClass EXPORT_DECLARATION = eINSTANCE.getExportDeclaration();

		/**
		 * The meta object literal for the '<em><b>Exported Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_DECLARATION__EXPORTED_ELEMENT = eINSTANCE.getExportDeclaration_ExportedElement();

		/**
		 * The meta object literal for the '<em><b>Default Exported Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_DECLARATION__DEFAULT_EXPORTED_EXPRESSION = eINSTANCE.getExportDeclaration_DefaultExportedExpression();

		/**
		 * The meta object literal for the '<em><b>Named Exports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_DECLARATION__NAMED_EXPORTS = eINSTANCE.getExportDeclaration_NamedExports();

		/**
		 * The meta object literal for the '<em><b>Wildcard Export</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPORT_DECLARATION__WILDCARD_EXPORT = eINSTANCE.getExportDeclaration_WildcardExport();

		/**
		 * The meta object literal for the '<em><b>Default Export</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPORT_DECLARATION__DEFAULT_EXPORT = eINSTANCE.getExportDeclaration_DefaultExport();

		/**
		 * The meta object literal for the '<em><b>Reexported From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_DECLARATION__REEXPORTED_FROM = eINSTANCE.getExportDeclaration_ReexportedFrom();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExportSpecifierImpl <em>Export Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExportSpecifierImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportSpecifier()
		 * @generated
		 */
		EClass EXPORT_SPECIFIER = eINSTANCE.getExportSpecifier();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORT_SPECIFIER__ELEMENT = eINSTANCE.getExportSpecifier_Element();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPORT_SPECIFIER__ALIAS = eINSTANCE.getExportSpecifier_Alias();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExportableElementImpl <em>Exportable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExportableElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportableElement()
		 * @generated
		 */
		EClass EXPORTABLE_ELEMENT = eINSTANCE.getExportableElement();

		/**
		 * The meta object literal for the '<em><b>Is Exported</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPORTABLE_ELEMENT___IS_EXPORTED = eINSTANCE.getExportableElement__IsExported();

		/**
		 * The meta object literal for the '<em><b>Is Exported As Default</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPORTABLE_ELEMENT___IS_EXPORTED_AS_DEFAULT = eINSTANCE.getExportableElement__IsExportedAsDefault();

		/**
		 * The meta object literal for the '<em><b>Get Exported Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPORTABLE_ELEMENT___GET_EXPORTED_NAME = eINSTANCE.getExportableElement__GetExportedName();

		/**
		 * The meta object literal for the '<em><b>Is Toplevel</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPORTABLE_ELEMENT___IS_TOPLEVEL = eINSTANCE.getExportableElement__IsToplevel();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl <em>Import Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ImportDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getImportDeclaration()
		 * @generated
		 */
		EClass IMPORT_DECLARATION = eINSTANCE.getImportDeclaration();

		/**
		 * The meta object literal for the '<em><b>Import Specifiers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_DECLARATION__IMPORT_SPECIFIERS = eINSTANCE.getImportDeclaration_ImportSpecifiers();

		/**
		 * The meta object literal for the '<em><b>Import From</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_DECLARATION__IMPORT_FROM = eINSTANCE.getImportDeclaration_ImportFrom();

		/**
		 * The meta object literal for the '<em><b>Module</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_DECLARATION__MODULE = eINSTANCE.getImportDeclaration_Module();

		/**
		 * The meta object literal for the '<em><b>Module Specifier As Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_DECLARATION__MODULE_SPECIFIER_AS_TEXT = eINSTANCE.getImportDeclaration_ModuleSpecifierAsText();

		/**
		 * The meta object literal for the '<em><b>Module Specifier Form</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_DECLARATION__MODULE_SPECIFIER_FORM = eINSTANCE.getImportDeclaration_ModuleSpecifierForm();

		/**
		 * The meta object literal for the '<em><b>Is Bare</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IMPORT_DECLARATION___IS_BARE = eINSTANCE.getImportDeclaration__IsBare();

		/**
		 * The meta object literal for the '<em><b>Is Retained At Runtime</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IMPORT_DECLARATION___IS_RETAINED_AT_RUNTIME = eINSTANCE.getImportDeclaration__IsRetainedAtRuntime();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ImportSpecifierImpl <em>Import Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ImportSpecifierImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getImportSpecifier()
		 * @generated
		 */
		EClass IMPORT_SPECIFIER = eINSTANCE.getImportSpecifier();

		/**
		 * The meta object literal for the '<em><b>Flagged Used In Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_SPECIFIER__FLAGGED_USED_IN_CODE = eINSTANCE.getImportSpecifier_FlaggedUsedInCode();

		/**
		 * The meta object literal for the '<em><b>Retained At Runtime</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_SPECIFIER__RETAINED_AT_RUNTIME = eINSTANCE.getImportSpecifier_RetainedAtRuntime();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl <em>Named Import Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.NamedImportSpecifierImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNamedImportSpecifier()
		 * @generated
		 */
		EClass NAMED_IMPORT_SPECIFIER = eINSTANCE.getNamedImportSpecifier();

		/**
		 * The meta object literal for the '<em><b>Imported Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT = eINSTANCE.getNamedImportSpecifier_ImportedElement();

		/**
		 * The meta object literal for the '<em><b>Imported Element As Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT_AS_TEXT = eINSTANCE.getNamedImportSpecifier_ImportedElementAsText();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_IMPORT_SPECIFIER__ALIAS = eINSTANCE.getNamedImportSpecifier_Alias();

		/**
		 * The meta object literal for the '<em><b>Is Default Import</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NAMED_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT = eINSTANCE.getNamedImportSpecifier__IsDefaultImport();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.DefaultImportSpecifierImpl <em>Default Import Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.DefaultImportSpecifierImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDefaultImportSpecifier()
		 * @generated
		 */
		EClass DEFAULT_IMPORT_SPECIFIER = eINSTANCE.getDefaultImportSpecifier();

		/**
		 * The meta object literal for the '<em><b>Get Alias</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DEFAULT_IMPORT_SPECIFIER___GET_ALIAS = eINSTANCE.getDefaultImportSpecifier__GetAlias();

		/**
		 * The meta object literal for the '<em><b>Is Default Import</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DEFAULT_IMPORT_SPECIFIER___IS_DEFAULT_IMPORT = eINSTANCE.getDefaultImportSpecifier__IsDefaultImport();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.NamespaceImportSpecifierImpl <em>Namespace Import Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.NamespaceImportSpecifierImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNamespaceImportSpecifier()
		 * @generated
		 */
		EClass NAMESPACE_IMPORT_SPECIFIER = eINSTANCE.getNamespaceImportSpecifier();

		/**
		 * The meta object literal for the '<em><b>Declared Dynamic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE_IMPORT_SPECIFIER__DECLARED_DYNAMIC = eINSTANCE.getNamespaceImportSpecifier_DeclaredDynamic();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMESPACE_IMPORT_SPECIFIER__ALIAS = eINSTANCE.getNamespaceImportSpecifier_Alias();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.TypeProvidingElement <em>Type Providing Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.TypeProvidingElement
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypeProvidingElement()
		 * @generated
		 */
		EClass TYPE_PROVIDING_ELEMENT = eINSTANCE.getTypeProvidingElement();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type Ref</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_PROVIDING_ELEMENT___GET_DECLARED_TYPE_REF = eINSTANCE.getTypeProvidingElement__GetDeclaredTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.TypedElementImpl <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.TypedElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Declared Type Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_ELEMENT__DECLARED_TYPE_REF = eINSTANCE.getTypedElement_DeclaredTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VariableEnvironmentElementImpl <em>Variable Environment Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VariableEnvironmentElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableEnvironmentElement()
		 * @generated
		 */
		EClass VARIABLE_ENVIRONMENT_ELEMENT = eINSTANCE.getVariableEnvironmentElement();

		/**
		 * The meta object literal for the '<em><b>Applies Only To Block Scoped Elements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VARIABLE_ENVIRONMENT_ELEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = eINSTANCE.getVariableEnvironmentElement__AppliesOnlyToBlockScopedElements();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ThisTargetImpl <em>This Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ThisTargetImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThisTarget()
		 * @generated
		 */
		EClass THIS_TARGET = eINSTANCE.getThisTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ThisArgProviderImpl <em>This Arg Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ThisArgProviderImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThisArgProvider()
		 * @generated
		 */
		EClass THIS_ARG_PROVIDER = eINSTANCE.getThisArgProvider();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VariableImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Is Const</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VARIABLE___IS_CONST = eINSTANCE.getVariable__IsConst();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableElementImpl <em>Annotable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotableElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableElement()
		 * @generated
		 */
		EClass ANNOTABLE_ELEMENT = eINSTANCE.getAnnotableElement();

		/**
		 * The meta object literal for the '<em><b>Get Annotations</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTABLE_ELEMENT___GET_ANNOTATIONS = eINSTANCE.getAnnotableElement__GetAnnotations();

		/**
		 * The meta object literal for the '<em><b>Get All Annotations</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTABLE_ELEMENT___GET_ALL_ANNOTATIONS = eINSTANCE.getAnnotableElement__GetAllAnnotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableScriptElementImpl <em>Annotable Script Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotableScriptElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableScriptElement()
		 * @generated
		 */
		EClass ANNOTABLE_SCRIPT_ELEMENT = eINSTANCE.getAnnotableScriptElement();

		/**
		 * The meta object literal for the '<em><b>Annotation List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST = eINSTANCE.getAnnotableScriptElement_AnnotationList();

		/**
		 * The meta object literal for the '<em><b>Get Annotations</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTABLE_SCRIPT_ELEMENT___GET_ANNOTATIONS = eINSTANCE.getAnnotableScriptElement__GetAnnotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableExpressionImpl <em>Annotable Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotableExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableExpression()
		 * @generated
		 */
		EClass ANNOTABLE_EXPRESSION = eINSTANCE.getAnnotableExpression();

		/**
		 * The meta object literal for the '<em><b>Annotation List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTABLE_EXPRESSION__ANNOTATION_LIST = eINSTANCE.getAnnotableExpression_AnnotationList();

		/**
		 * The meta object literal for the '<em><b>Get Annotations</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTABLE_EXPRESSION___GET_ANNOTATIONS = eINSTANCE.getAnnotableExpression__GetAnnotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AbstractAnnotationListImpl <em>Abstract Annotation List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AbstractAnnotationListImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractAnnotationList()
		 * @generated
		 */
		EClass ABSTRACT_ANNOTATION_LIST = eINSTANCE.getAbstractAnnotationList();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_ANNOTATION_LIST__ANNOTATIONS = eINSTANCE.getAbstractAnnotationList_Annotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotationListImpl <em>Annotation List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotationListImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotationList()
		 * @generated
		 */
		EClass ANNOTATION_LIST = eINSTANCE.getAnnotationList();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionAnnotationListImpl <em>Expression Annotation List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExpressionAnnotationListImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpressionAnnotationList()
		 * @generated
		 */
		EClass EXPRESSION_ANNOTATION_LIST = eINSTANCE.getExpressionAnnotationList();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__NAME = eINSTANCE.getAnnotation_Name();

		/**
		 * The meta object literal for the '<em><b>Args</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__ARGS = eINSTANCE.getAnnotation_Args();

		/**
		 * The meta object literal for the '<em><b>Get Annotated Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTATION___GET_ANNOTATED_ELEMENT = eINSTANCE.getAnnotation__GetAnnotatedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotationArgumentImpl <em>Annotation Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotationArgumentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotationArgument()
		 * @generated
		 */
		EClass ANNOTATION_ARGUMENT = eINSTANCE.getAnnotationArgument();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTATION_ARGUMENT___VALUE = eINSTANCE.getAnnotationArgument__Value();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTATION_ARGUMENT___GET_VALUE_AS_STRING = eINSTANCE.getAnnotationArgument__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.LiteralAnnotationArgumentImpl <em>Literal Annotation Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.LiteralAnnotationArgumentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLiteralAnnotationArgument()
		 * @generated
		 */
		EClass LITERAL_ANNOTATION_ARGUMENT = eINSTANCE.getLiteralAnnotationArgument();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LITERAL_ANNOTATION_ARGUMENT__LITERAL = eINSTANCE.getLiteralAnnotationArgument_Literal();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LITERAL_ANNOTATION_ARGUMENT___VALUE = eINSTANCE.getLiteralAnnotationArgument__Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.TypeRefAnnotationArgumentImpl <em>Type Ref Annotation Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.TypeRefAnnotationArgumentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypeRefAnnotationArgument()
		 * @generated
		 */
		EClass TYPE_REF_ANNOTATION_ARGUMENT = eINSTANCE.getTypeRefAnnotationArgument();

		/**
		 * The meta object literal for the '<em><b>Type Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_REF_ANNOTATION_ARGUMENT__TYPE_REF = eINSTANCE.getTypeRefAnnotationArgument_TypeRef();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TYPE_REF_ANNOTATION_ARGUMENT___VALUE = eINSTANCE.getTypeRefAnnotationArgument__Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.FunctionOrFieldAccessorImpl <em>Function Or Field Accessor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.FunctionOrFieldAccessorImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionOrFieldAccessor()
		 * @generated
		 */
		EClass FUNCTION_OR_FIELD_ACCESSOR = eINSTANCE.getFunctionOrFieldAccessor();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_OR_FIELD_ACCESSOR__BODY = eINSTANCE.getFunctionOrFieldAccessor_Body();

		/**
		 * The meta object literal for the '<em><b>lok</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_OR_FIELD_ACCESSOR__LOK = eINSTANCE.getFunctionOrFieldAccessor__lok();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_OR_FIELD_ACCESSOR___GET_NAME = eINSTANCE.getFunctionOrFieldAccessor__GetName();

		/**
		 * The meta object literal for the '<em><b>Get Local Arguments Variable</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_OR_FIELD_ACCESSOR___GET_LOCAL_ARGUMENTS_VARIABLE = eINSTANCE.getFunctionOrFieldAccessor__GetLocalArgumentsVariable();

		/**
		 * The meta object literal for the '<em><b>Is Return Value Optional</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_OR_FIELD_ACCESSOR___IS_RETURN_VALUE_OPTIONAL = eINSTANCE.getFunctionOrFieldAccessor__IsReturnValueOptional();

		/**
		 * The meta object literal for the '<em><b>Is Async</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_OR_FIELD_ACCESSOR___IS_ASYNC = eINSTANCE.getFunctionOrFieldAccessor__IsAsync();

		/**
		 * The meta object literal for the '<em><b>Get Defined Function Or Accessor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_OR_FIELD_ACCESSOR___GET_DEFINED_FUNCTION_OR_ACCESSOR = eINSTANCE.getFunctionOrFieldAccessor__GetDefinedFunctionOrAccessor();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl <em>Function Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.FunctionDefinitionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionDefinition()
		 * @generated
		 */
		EClass FUNCTION_DEFINITION = eINSTANCE.getFunctionDefinition();

		/**
		 * The meta object literal for the '<em><b>Fpars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_DEFINITION__FPARS = eINSTANCE.getFunctionDefinition_Fpars();

		/**
		 * The meta object literal for the '<em><b>Return Type Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_DEFINITION__RETURN_TYPE_REF = eINSTANCE.getFunctionDefinition_ReturnTypeRef();

		/**
		 * The meta object literal for the '<em><b>Generator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_DEFINITION__GENERATOR = eINSTANCE.getFunctionDefinition_Generator();

		/**
		 * The meta object literal for the '<em><b>Declared Async</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_DEFINITION__DECLARED_ASYNC = eINSTANCE.getFunctionDefinition_DeclaredAsync();

		/**
		 * The meta object literal for the '<em><b>Is Return Value Optional</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_DEFINITION___IS_RETURN_VALUE_OPTIONAL = eINSTANCE.getFunctionDefinition__IsReturnValueOptional();

		/**
		 * The meta object literal for the '<em><b>Is Async</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_DEFINITION___IS_ASYNC = eINSTANCE.getFunctionDefinition__IsAsync();

		/**
		 * The meta object literal for the '<em><b>Get Defined Function</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_DEFINITION___GET_DEFINED_FUNCTION = eINSTANCE.getFunctionDefinition__GetDefinedFunction();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.FieldAccessorImpl <em>Field Accessor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.FieldAccessorImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFieldAccessor()
		 * @generated
		 */
		EClass FIELD_ACCESSOR = eINSTANCE.getFieldAccessor();

		/**
		 * The meta object literal for the '<em><b>Declared Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_ACCESSOR__DECLARED_OPTIONAL = eINSTANCE.getFieldAccessor_DeclaredOptional();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type Ref</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FIELD_ACCESSOR___GET_DECLARED_TYPE_REF = eINSTANCE.getFieldAccessor__GetDeclaredTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Defined Accessor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FIELD_ACCESSOR___GET_DEFINED_ACCESSOR = eINSTANCE.getFieldAccessor__GetDefinedAccessor();

		/**
		 * The meta object literal for the '<em><b>Is Optional</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FIELD_ACCESSOR___IS_OPTIONAL = eINSTANCE.getFieldAccessor__IsOptional();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl <em>Function Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.FunctionDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionDeclaration()
		 * @generated
		 */
		EClass FUNCTION_DECLARATION = eINSTANCE.getFunctionDeclaration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_DECLARATION__NAME = eINSTANCE.getFunctionDeclaration_Name();

		/**
		 * The meta object literal for the '<em><b>migration Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_DECLARATION__MIGRATION_CONTEXT = eINSTANCE.getFunctionDeclaration__migrationContext();

		/**
		 * The meta object literal for the '<em><b>Is External</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_DECLARATION___IS_EXTERNAL = eINSTANCE.getFunctionDeclaration__IsExternal();

		/**
		 * The meta object literal for the '<em><b>Get Migration Context Variable</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_DECLARATION___GET_MIGRATION_CONTEXT_VARIABLE = eINSTANCE.getFunctionDeclaration__GetMigrationContextVariable();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.FunctionExpressionImpl <em>Function Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.FunctionExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFunctionExpression()
		 * @generated
		 */
		EClass FUNCTION_EXPRESSION = eINSTANCE.getFunctionExpression();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_EXPRESSION__NAME = eINSTANCE.getFunctionExpression_Name();

		/**
		 * The meta object literal for the '<em><b>Is Arrow Function</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FUNCTION_EXPRESSION___IS_ARROW_FUNCTION = eINSTANCE.getFunctionExpression__IsArrowFunction();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ArrowFunctionImpl <em>Arrow Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ArrowFunctionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrowFunction()
		 * @generated
		 */
		EClass ARROW_FUNCTION = eINSTANCE.getArrowFunction();

		/**
		 * The meta object literal for the '<em><b>Has Braces Around Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARROW_FUNCTION__HAS_BRACES_AROUND_BODY = eINSTANCE.getArrowFunction_HasBracesAroundBody();

		/**
		 * The meta object literal for the '<em><b>Is Arrow Function</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ARROW_FUNCTION___IS_ARROW_FUNCTION = eINSTANCE.getArrowFunction__IsArrowFunction();

		/**
		 * The meta object literal for the '<em><b>Is Single Expr Implicit Return</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ARROW_FUNCTION___IS_SINGLE_EXPR_IMPLICIT_RETURN = eINSTANCE.getArrowFunction__IsSingleExprImplicitReturn();

		/**
		 * The meta object literal for the '<em><b>Get Single Expression</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ARROW_FUNCTION___GET_SINGLE_EXPRESSION = eINSTANCE.getArrowFunction__GetSingleExpression();

		/**
		 * The meta object literal for the '<em><b>Implicit Return Expr</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ARROW_FUNCTION___IMPLICIT_RETURN_EXPR = eINSTANCE.getArrowFunction__ImplicitReturnExpr();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.LocalArgumentsVariableImpl <em>Local Arguments Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.LocalArgumentsVariableImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLocalArgumentsVariable()
		 * @generated
		 */
		EClass LOCAL_ARGUMENTS_VARIABLE = eINSTANCE.getLocalArgumentsVariable();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LOCAL_ARGUMENTS_VARIABLE___GET_NAME = eINSTANCE.getLocalArgumentsVariable__GetName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.FormalParameterImpl <em>Formal Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.FormalParameterImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFormalParameter()
		 * @generated
		 */
		EClass FORMAL_PARAMETER = eINSTANCE.getFormalParameter();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORMAL_PARAMETER__ANNOTATIONS = eINSTANCE.getFormalParameter_Annotations();

		/**
		 * The meta object literal for the '<em><b>Variadic</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMAL_PARAMETER__VARIADIC = eINSTANCE.getFormalParameter_Variadic();

		/**
		 * The meta object literal for the '<em><b>Defined Type Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORMAL_PARAMETER__DEFINED_TYPE_ELEMENT = eINSTANCE.getFormalParameter_DefinedTypeElement();

		/**
		 * The meta object literal for the '<em><b>Has Initializer Assignment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMAL_PARAMETER__HAS_INITIALIZER_ASSIGNMENT = eINSTANCE.getFormalParameter_HasInitializerAssignment();

		/**
		 * The meta object literal for the '<em><b>Initializer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORMAL_PARAMETER__INITIALIZER = eINSTANCE.getFormalParameter_Initializer();

		/**
		 * The meta object literal for the '<em><b>Binding Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORMAL_PARAMETER__BINDING_PATTERN = eINSTANCE.getFormalParameter_BindingPattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BlockImpl <em>Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BlockImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBlock()
		 * @generated
		 */
		EClass BLOCK = eINSTANCE.getBlock();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLOCK__STATEMENTS = eINSTANCE.getBlock_Statements();

		/**
		 * The meta object literal for the '<em><b>Applies Only To Block Scoped Elements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = eINSTANCE.getBlock__AppliesOnlyToBlockScopedElements();

		/**
		 * The meta object literal for the '<em><b>Get All Expressions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_EXPRESSIONS = eINSTANCE.getBlock__GetAllExpressions();

		/**
		 * The meta object literal for the '<em><b>Get All Yield Expressions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_YIELD_EXPRESSIONS = eINSTANCE.getBlock__GetAllYieldExpressions();

		/**
		 * The meta object literal for the '<em><b>Get All Void Yield Expressions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_VOID_YIELD_EXPRESSIONS = eINSTANCE.getBlock__GetAllVoidYieldExpressions();

		/**
		 * The meta object literal for the '<em><b>Get All Non Void Yield Expressions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_NON_VOID_YIELD_EXPRESSIONS = eINSTANCE.getBlock__GetAllNonVoidYieldExpressions();

		/**
		 * The meta object literal for the '<em><b>Has Non Void Yield</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___HAS_NON_VOID_YIELD = eINSTANCE.getBlock__HasNonVoidYield();

		/**
		 * The meta object literal for the '<em><b>Get All Statements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_STATEMENTS = eINSTANCE.getBlock__GetAllStatements();

		/**
		 * The meta object literal for the '<em><b>Get All Return Statements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_RETURN_STATEMENTS = eINSTANCE.getBlock__GetAllReturnStatements();

		/**
		 * The meta object literal for the '<em><b>Get All Non Void Return Statements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_NON_VOID_RETURN_STATEMENTS = eINSTANCE.getBlock__GetAllNonVoidReturnStatements();

		/**
		 * The meta object literal for the '<em><b>Get All Void Return Statements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___GET_ALL_VOID_RETURN_STATEMENTS = eINSTANCE.getBlock__GetAllVoidReturnStatements();

		/**
		 * The meta object literal for the '<em><b>Has Non Void Return</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BLOCK___HAS_NON_VOID_RETURN = eINSTANCE.getBlock__HasNonVoidReturn();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.StatementImpl <em>Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.StatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getStatement()
		 * @generated
		 */
		EClass STATEMENT = eINSTANCE.getStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationContainerImpl <em>Variable Declaration Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VariableDeclarationContainerImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableDeclarationContainer()
		 * @generated
		 */
		EClass VARIABLE_DECLARATION_CONTAINER = eINSTANCE.getVariableDeclarationContainer();

		/**
		 * The meta object literal for the '<em><b>Var Decls Or Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_DECLARATION_CONTAINER__VAR_DECLS_OR_BINDINGS = eINSTANCE.getVariableDeclarationContainer_VarDeclsOrBindings();

		/**
		 * The meta object literal for the '<em><b>Var Stmt Keyword</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_DECLARATION_CONTAINER__VAR_STMT_KEYWORD = eINSTANCE.getVariableDeclarationContainer_VarStmtKeyword();

		/**
		 * The meta object literal for the '<em><b>Get Var Decl</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VARIABLE_DECLARATION_CONTAINER___GET_VAR_DECL = eINSTANCE.getVariableDeclarationContainer__GetVarDecl();

		/**
		 * The meta object literal for the '<em><b>Is Block Scoped</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VARIABLE_DECLARATION_CONTAINER___IS_BLOCK_SCOPED = eINSTANCE.getVariableDeclarationContainer__IsBlockScoped();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VariableStatementImpl <em>Variable Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VariableStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableStatement()
		 * @generated
		 */
		EClass VARIABLE_STATEMENT = eINSTANCE.getVariableStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExportedVariableStatementImpl <em>Exported Variable Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExportedVariableStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportedVariableStatement()
		 * @generated
		 */
		EClass EXPORTED_VARIABLE_STATEMENT = eINSTANCE.getExportedVariableStatement();

		/**
		 * The meta object literal for the '<em><b>Is External</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPORTED_VARIABLE_STATEMENT___IS_EXTERNAL = eINSTANCE.getExportedVariableStatement__IsExternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationOrBindingImpl <em>Variable Declaration Or Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VariableDeclarationOrBindingImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableDeclarationOrBinding()
		 * @generated
		 */
		EClass VARIABLE_DECLARATION_OR_BINDING = eINSTANCE.getVariableDeclarationOrBinding();

		/**
		 * The meta object literal for the '<em><b>Get Variable Declarations</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VARIABLE_DECLARATION_OR_BINDING___GET_VARIABLE_DECLARATIONS = eINSTANCE.getVariableDeclarationOrBinding__GetVariableDeclarations();

		/**
		 * The meta object literal for the '<em><b>Get Expression</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VARIABLE_DECLARATION_OR_BINDING___GET_EXPRESSION = eINSTANCE.getVariableDeclarationOrBinding__GetExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VariableBindingImpl <em>Variable Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VariableBindingImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableBinding()
		 * @generated
		 */
		EClass VARIABLE_BINDING = eINSTANCE.getVariableBinding();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_BINDING__PATTERN = eINSTANCE.getVariableBinding_Pattern();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_BINDING__EXPRESSION = eINSTANCE.getVariableBinding_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExportedVariableBindingImpl <em>Exported Variable Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExportedVariableBindingImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportedVariableBinding()
		 * @generated
		 */
		EClass EXPORTED_VARIABLE_BINDING = eINSTANCE.getExportedVariableBinding();

		/**
		 * The meta object literal for the '<em><b>Defined Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORTED_VARIABLE_BINDING__DEFINED_VARIABLE = eINSTANCE.getExportedVariableBinding_DefinedVariable();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VariableDeclarationImpl <em>Variable Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VariableDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableDeclaration()
		 * @generated
		 */
		EClass VARIABLE_DECLARATION = eINSTANCE.getVariableDeclaration();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_DECLARATION__ANNOTATIONS = eINSTANCE.getVariableDeclaration_Annotations();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_DECLARATION__EXPRESSION = eINSTANCE.getVariableDeclaration_Expression();

		/**
		 * The meta object literal for the '<em><b>Is Const</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VARIABLE_DECLARATION___IS_CONST = eINSTANCE.getVariableDeclaration__IsConst();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExportedVariableDeclarationImpl <em>Exported Variable Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExportedVariableDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExportedVariableDeclaration()
		 * @generated
		 */
		EClass EXPORTED_VARIABLE_DECLARATION = eINSTANCE.getExportedVariableDeclaration();

		/**
		 * The meta object literal for the '<em><b>Defined Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPORTED_VARIABLE_DECLARATION__DEFINED_VARIABLE = eINSTANCE.getExportedVariableDeclaration_DefinedVariable();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.EmptyStatementImpl <em>Empty Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.EmptyStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getEmptyStatement()
		 * @generated
		 */
		EClass EMPTY_STATEMENT = eINSTANCE.getEmptyStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionStatementImpl <em>Expression Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExpressionStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpressionStatement()
		 * @generated
		 */
		EClass EXPRESSION_STATEMENT = eINSTANCE.getExpressionStatement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION_STATEMENT__EXPRESSION = eINSTANCE.getExpressionStatement_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.IfStatementImpl <em>If Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.IfStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIfStatement()
		 * @generated
		 */
		EClass IF_STATEMENT = eINSTANCE.getIfStatement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_STATEMENT__EXPRESSION = eINSTANCE.getIfStatement_Expression();

		/**
		 * The meta object literal for the '<em><b>If Stmt</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_STATEMENT__IF_STMT = eINSTANCE.getIfStatement_IfStmt();

		/**
		 * The meta object literal for the '<em><b>Else Stmt</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IF_STATEMENT__ELSE_STMT = eINSTANCE.getIfStatement_ElseStmt();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.IterationStatementImpl <em>Iteration Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.IterationStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIterationStatement()
		 * @generated
		 */
		EClass ITERATION_STATEMENT = eINSTANCE.getIterationStatement();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATION_STATEMENT__STATEMENT = eINSTANCE.getIterationStatement_Statement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATION_STATEMENT__EXPRESSION = eINSTANCE.getIterationStatement_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.DoStatementImpl <em>Do Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.DoStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDoStatement()
		 * @generated
		 */
		EClass DO_STATEMENT = eINSTANCE.getDoStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.WhileStatementImpl <em>While Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.WhileStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getWhileStatement()
		 * @generated
		 */
		EClass WHILE_STATEMENT = eINSTANCE.getWhileStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ForStatementImpl <em>For Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ForStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getForStatement()
		 * @generated
		 */
		EClass FOR_STATEMENT = eINSTANCE.getForStatement();

		/**
		 * The meta object literal for the '<em><b>Init Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOR_STATEMENT__INIT_EXPR = eINSTANCE.getForStatement_InitExpr();

		/**
		 * The meta object literal for the '<em><b>Update Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FOR_STATEMENT__UPDATE_EXPR = eINSTANCE.getForStatement_UpdateExpr();

		/**
		 * The meta object literal for the '<em><b>Await</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOR_STATEMENT__AWAIT = eINSTANCE.getForStatement_Await();

		/**
		 * The meta object literal for the '<em><b>For In</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOR_STATEMENT__FOR_IN = eINSTANCE.getForStatement_ForIn();

		/**
		 * The meta object literal for the '<em><b>For Of</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FOR_STATEMENT__FOR_OF = eINSTANCE.getForStatement_ForOf();

		/**
		 * The meta object literal for the '<em><b>Is For Plain</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FOR_STATEMENT___IS_FOR_PLAIN = eINSTANCE.getForStatement__IsForPlain();

		/**
		 * The meta object literal for the '<em><b>Applies Only To Block Scoped Elements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FOR_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = eINSTANCE.getForStatement__AppliesOnlyToBlockScopedElements();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.LabelRefImpl <em>Label Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.LabelRefImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLabelRef()
		 * @generated
		 */
		EClass LABEL_REF = eINSTANCE.getLabelRef();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABEL_REF__LABEL = eINSTANCE.getLabelRef_Label();

		/**
		 * The meta object literal for the '<em><b>Label As Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL_REF__LABEL_AS_TEXT = eINSTANCE.getLabelRef_LabelAsText();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ContinueStatementImpl <em>Continue Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ContinueStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getContinueStatement()
		 * @generated
		 */
		EClass CONTINUE_STATEMENT = eINSTANCE.getContinueStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BreakStatementImpl <em>Break Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BreakStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBreakStatement()
		 * @generated
		 */
		EClass BREAK_STATEMENT = eINSTANCE.getBreakStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ReturnStatementImpl <em>Return Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ReturnStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getReturnStatement()
		 * @generated
		 */
		EClass RETURN_STATEMENT = eINSTANCE.getReturnStatement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RETURN_STATEMENT__EXPRESSION = eINSTANCE.getReturnStatement_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.WithStatementImpl <em>With Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.WithStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getWithStatement()
		 * @generated
		 */
		EClass WITH_STATEMENT = eINSTANCE.getWithStatement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WITH_STATEMENT__EXPRESSION = eINSTANCE.getWithStatement_Expression();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WITH_STATEMENT__STATEMENT = eINSTANCE.getWithStatement_Statement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.SwitchStatementImpl <em>Switch Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.SwitchStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getSwitchStatement()
		 * @generated
		 */
		EClass SWITCH_STATEMENT = eINSTANCE.getSwitchStatement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_STATEMENT__EXPRESSION = eINSTANCE.getSwitchStatement_Expression();

		/**
		 * The meta object literal for the '<em><b>Cases</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SWITCH_STATEMENT__CASES = eINSTANCE.getSwitchStatement_Cases();

		/**
		 * The meta object literal for the '<em><b>Applies Only To Block Scoped Elements</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SWITCH_STATEMENT___APPLIES_ONLY_TO_BLOCK_SCOPED_ELEMENTS = eINSTANCE.getSwitchStatement__AppliesOnlyToBlockScopedElements();

		/**
		 * The meta object literal for the '<em><b>Get Default Clause</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SWITCH_STATEMENT___GET_DEFAULT_CLAUSE = eINSTANCE.getSwitchStatement__GetDefaultClause();

		/**
		 * The meta object literal for the '<em><b>Get Case Clauses</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SWITCH_STATEMENT___GET_CASE_CLAUSES = eINSTANCE.getSwitchStatement__GetCaseClauses();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AbstractCaseClauseImpl <em>Abstract Case Clause</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AbstractCaseClauseImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractCaseClause()
		 * @generated
		 */
		EClass ABSTRACT_CASE_CLAUSE = eINSTANCE.getAbstractCaseClause();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_CASE_CLAUSE__STATEMENTS = eINSTANCE.getAbstractCaseClause_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.CaseClauseImpl <em>Case Clause</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.CaseClauseImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCaseClause()
		 * @generated
		 */
		EClass CASE_CLAUSE = eINSTANCE.getCaseClause();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CASE_CLAUSE__EXPRESSION = eINSTANCE.getCaseClause_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.DefaultClauseImpl <em>Default Clause</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.DefaultClauseImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDefaultClause()
		 * @generated
		 */
		EClass DEFAULT_CLAUSE = eINSTANCE.getDefaultClause();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.LabelledStatementImpl <em>Labelled Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.LabelledStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLabelledStatement()
		 * @generated
		 */
		EClass LABELLED_STATEMENT = eINSTANCE.getLabelledStatement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABELLED_STATEMENT__NAME = eINSTANCE.getLabelledStatement_Name();

		/**
		 * The meta object literal for the '<em><b>Statement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LABELLED_STATEMENT__STATEMENT = eINSTANCE.getLabelledStatement_Statement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ThrowStatementImpl <em>Throw Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ThrowStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThrowStatement()
		 * @generated
		 */
		EClass THROW_STATEMENT = eINSTANCE.getThrowStatement();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THROW_STATEMENT__EXPRESSION = eINSTANCE.getThrowStatement_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.TryStatementImpl <em>Try Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.TryStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTryStatement()
		 * @generated
		 */
		EClass TRY_STATEMENT = eINSTANCE.getTryStatement();

		/**
		 * The meta object literal for the '<em><b>Block</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRY_STATEMENT__BLOCK = eINSTANCE.getTryStatement_Block();

		/**
		 * The meta object literal for the '<em><b>Catch</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRY_STATEMENT__CATCH = eINSTANCE.getTryStatement_Catch();

		/**
		 * The meta object literal for the '<em><b>Finally</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRY_STATEMENT__FINALLY = eINSTANCE.getTryStatement_Finally();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AbstractCatchBlockImpl <em>Abstract Catch Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AbstractCatchBlockImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractCatchBlock()
		 * @generated
		 */
		EClass ABSTRACT_CATCH_BLOCK = eINSTANCE.getAbstractCatchBlock();

		/**
		 * The meta object literal for the '<em><b>Block</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_CATCH_BLOCK__BLOCK = eINSTANCE.getAbstractCatchBlock_Block();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.CatchBlockImpl <em>Catch Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.CatchBlockImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCatchBlock()
		 * @generated
		 */
		EClass CATCH_BLOCK = eINSTANCE.getCatchBlock();

		/**
		 * The meta object literal for the '<em><b>Catch Variable</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATCH_BLOCK__CATCH_VARIABLE = eINSTANCE.getCatchBlock_CatchVariable();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.CatchVariableImpl <em>Catch Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.CatchVariableImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCatchVariable()
		 * @generated
		 */
		EClass CATCH_VARIABLE = eINSTANCE.getCatchVariable();

		/**
		 * The meta object literal for the '<em><b>Binding Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATCH_VARIABLE__BINDING_PATTERN = eINSTANCE.getCatchVariable_BindingPattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.FinallyBlockImpl <em>Finally Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.FinallyBlockImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getFinallyBlock()
		 * @generated
		 */
		EClass FINALLY_BLOCK = eINSTANCE.getFinallyBlock();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.DebuggerStatementImpl <em>Debugger Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.DebuggerStatementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDebuggerStatement()
		 * @generated
		 */
		EClass DEBUGGER_STATEMENT = eINSTANCE.getDebuggerStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PrimaryExpressionImpl <em>Primary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PrimaryExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPrimaryExpression()
		 * @generated
		 */
		EClass PRIMARY_EXPRESSION = eINSTANCE.getPrimaryExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ParenExpressionImpl <em>Paren Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ParenExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParenExpression()
		 * @generated
		 */
		EClass PAREN_EXPRESSION = eINSTANCE.getParenExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAREN_EXPRESSION__EXPRESSION = eINSTANCE.getParenExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Is Valid Simple Assignment Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PAREN_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = eINSTANCE.getParenExpression__IsValidSimpleAssignmentTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.IdentifierRefImpl <em>Identifier Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.IdentifierRefImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIdentifierRef()
		 * @generated
		 */
		EClass IDENTIFIER_REF = eINSTANCE.getIdentifierRef();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDENTIFIER_REF__ID = eINSTANCE.getIdentifierRef_Id();

		/**
		 * The meta object literal for the '<em><b>Id As Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFIER_REF__ID_AS_TEXT = eINSTANCE.getIdentifierRef_IdAsText();

		/**
		 * The meta object literal for the '<em><b>Origin Import</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IDENTIFIER_REF__ORIGIN_IMPORT = eINSTANCE.getIdentifierRef_OriginImport();

		/**
		 * The meta object literal for the '<em><b>Get Target Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IDENTIFIER_REF___GET_TARGET_ELEMENT = eINSTANCE.getIdentifierRef__GetTargetElement();

		/**
		 * The meta object literal for the '<em><b>Is Valid Simple Assignment Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IDENTIFIER_REF___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = eINSTANCE.getIdentifierRef__IsValidSimpleAssignmentTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.StrictModeRelevantImpl <em>Strict Mode Relevant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.StrictModeRelevantImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getStrictModeRelevant()
		 * @generated
		 */
		EClass STRICT_MODE_RELEVANT = eINSTANCE.getStrictModeRelevant();

		/**
		 * The meta object literal for the '<em><b>Strict Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRICT_MODE_RELEVANT__STRICT_MODE = eINSTANCE.getStrictModeRelevant_StrictMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.SuperLiteralImpl <em>Super Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.SuperLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getSuperLiteral()
		 * @generated
		 */
		EClass SUPER_LITERAL = eINSTANCE.getSuperLiteral();

		/**
		 * The meta object literal for the '<em><b>Is Super Constructor Access</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SUPER_LITERAL___IS_SUPER_CONSTRUCTOR_ACCESS = eINSTANCE.getSuperLiteral__IsSuperConstructorAccess();

		/**
		 * The meta object literal for the '<em><b>Is Super Member Access</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SUPER_LITERAL___IS_SUPER_MEMBER_ACCESS = eINSTANCE.getSuperLiteral__IsSuperMemberAccess();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ThisLiteralImpl <em>This Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ThisLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getThisLiteral()
		 * @generated
		 */
		EClass THIS_LITERAL = eINSTANCE.getThisLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ArrayLiteralImpl <em>Array Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ArrayLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayLiteral()
		 * @generated
		 */
		EClass ARRAY_LITERAL = eINSTANCE.getArrayLiteral();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_LITERAL__ELEMENTS = eINSTANCE.getArrayLiteral_Elements();

		/**
		 * The meta object literal for the '<em><b>Trailing Comma</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_LITERAL__TRAILING_COMMA = eINSTANCE.getArrayLiteral_TrailingComma();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ArrayElementImpl <em>Array Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ArrayElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayElement()
		 * @generated
		 */
		EClass ARRAY_ELEMENT = eINSTANCE.getArrayElement();

		/**
		 * The meta object literal for the '<em><b>Spread</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_ELEMENT__SPREAD = eINSTANCE.getArrayElement_Spread();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_ELEMENT__EXPRESSION = eINSTANCE.getArrayElement_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ArrayPaddingImpl <em>Array Padding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ArrayPaddingImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayPadding()
		 * @generated
		 */
		EClass ARRAY_PADDING = eINSTANCE.getArrayPadding();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ObjectLiteralImpl <em>Object Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ObjectLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getObjectLiteral()
		 * @generated
		 */
		EClass OBJECT_LITERAL = eINSTANCE.getObjectLiteral();

		/**
		 * The meta object literal for the '<em><b>Property Assignments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_LITERAL__PROPERTY_ASSIGNMENTS = eINSTANCE.getObjectLiteral_PropertyAssignments();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertyAssignmentImpl <em>Property Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertyAssignmentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyAssignment()
		 * @generated
		 */
		EClass PROPERTY_ASSIGNMENT = eINSTANCE.getPropertyAssignment();

		/**
		 * The meta object literal for the '<em><b>Get Defined Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_ASSIGNMENT___GET_DEFINED_MEMBER = eINSTANCE.getPropertyAssignment__GetDefinedMember();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_ASSIGNMENT___IS_VALID_NAME = eINSTANCE.getPropertyAssignment__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertyNameOwnerImpl <em>Property Name Owner</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertyNameOwnerImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameOwner()
		 * @generated
		 */
		EClass PROPERTY_NAME_OWNER = eINSTANCE.getPropertyNameOwner();

		/**
		 * The meta object literal for the '<em><b>Declared Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_NAME_OWNER__DECLARED_NAME = eINSTANCE.getPropertyNameOwner_DeclaredName();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_NAME_OWNER___GET_NAME = eINSTANCE.getPropertyNameOwner__GetName();

		/**
		 * The meta object literal for the '<em><b>Has Computed Property Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_NAME_OWNER___HAS_COMPUTED_PROPERTY_NAME = eINSTANCE.getPropertyNameOwner__HasComputedPropertyName();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_NAME_OWNER___IS_VALID_NAME = eINSTANCE.getPropertyNameOwner__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl <em>Literal Or Computed Property Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.LiteralOrComputedPropertyNameImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLiteralOrComputedPropertyName()
		 * @generated
		 */
		EClass LITERAL_OR_COMPUTED_PROPERTY_NAME = eINSTANCE.getLiteralOrComputedPropertyName();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LITERAL_OR_COMPUTED_PROPERTY_NAME__KIND = eINSTANCE.getLiteralOrComputedPropertyName_Kind();

		/**
		 * The meta object literal for the '<em><b>Literal Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LITERAL_OR_COMPUTED_PROPERTY_NAME__LITERAL_NAME = eINSTANCE.getLiteralOrComputedPropertyName_LiteralName();

		/**
		 * The meta object literal for the '<em><b>Computed Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LITERAL_OR_COMPUTED_PROPERTY_NAME__COMPUTED_NAME = eINSTANCE.getLiteralOrComputedPropertyName_ComputedName();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LITERAL_OR_COMPUTED_PROPERTY_NAME__EXPRESSION = eINSTANCE.getLiteralOrComputedPropertyName_Expression();

		/**
		 * The meta object literal for the '<em><b>Has Computed Property Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LITERAL_OR_COMPUTED_PROPERTY_NAME___HAS_COMPUTED_PROPERTY_NAME = eINSTANCE.getLiteralOrComputedPropertyName__HasComputedPropertyName();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LITERAL_OR_COMPUTED_PROPERTY_NAME___GET_NAME = eINSTANCE.getLiteralOrComputedPropertyName__GetName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotablePropertyAssignmentImpl <em>Annotable Property Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotablePropertyAssignmentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotablePropertyAssignment()
		 * @generated
		 */
		EClass ANNOTABLE_PROPERTY_ASSIGNMENT = eINSTANCE.getAnnotablePropertyAssignment();

		/**
		 * The meta object literal for the '<em><b>Annotation List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST = eINSTANCE.getAnnotablePropertyAssignment_AnnotationList();

		/**
		 * The meta object literal for the '<em><b>Get Annotations</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTABLE_PROPERTY_ASSIGNMENT___GET_ANNOTATIONS = eINSTANCE.getAnnotablePropertyAssignment__GetAnnotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertyAssignmentAnnotationListImpl <em>Property Assignment Annotation List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertyAssignmentAnnotationListImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyAssignmentAnnotationList()
		 * @generated
		 */
		EClass PROPERTY_ASSIGNMENT_ANNOTATION_LIST = eINSTANCE.getPropertyAssignmentAnnotationList();

		/**
		 * The meta object literal for the '<em><b>Get Defined Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_ASSIGNMENT_ANNOTATION_LIST___GET_DEFINED_MEMBER = eINSTANCE.getPropertyAssignmentAnnotationList__GetDefinedMember();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl <em>Property Name Value Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertyNameValuePairImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameValuePair()
		 * @generated
		 */
		EClass PROPERTY_NAME_VALUE_PAIR = eINSTANCE.getPropertyNameValuePair();

		/**
		 * The meta object literal for the '<em><b>Defined Field</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_NAME_VALUE_PAIR__DEFINED_FIELD = eINSTANCE.getPropertyNameValuePair_DefinedField();

		/**
		 * The meta object literal for the '<em><b>Declared Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_NAME_VALUE_PAIR__DECLARED_OPTIONAL = eINSTANCE.getPropertyNameValuePair_DeclaredOptional();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_NAME_VALUE_PAIR__EXPRESSION = eINSTANCE.getPropertyNameValuePair_Expression();

		/**
		 * The meta object literal for the '<em><b>Get Defined Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_NAME_VALUE_PAIR___GET_DEFINED_MEMBER = eINSTANCE.getPropertyNameValuePair__GetDefinedMember();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_NAME_VALUE_PAIR___IS_VALID_NAME = eINSTANCE.getPropertyNameValuePair__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertyNameValuePairSingleNameImpl <em>Property Name Value Pair Single Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertyNameValuePairSingleNameImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameValuePairSingleName()
		 * @generated
		 */
		EClass PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME = eINSTANCE.getPropertyNameValuePairSingleName();

		/**
		 * The meta object literal for the '<em><b>Get Identifier Ref</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_IDENTIFIER_REF = eINSTANCE.getPropertyNameValuePairSingleName__GetIdentifierRef();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_NAME_VALUE_PAIR_SINGLE_NAME___GET_NAME = eINSTANCE.getPropertyNameValuePairSingleName__GetName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertyMethodDeclarationImpl <em>Property Method Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertyMethodDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyMethodDeclaration()
		 * @generated
		 */
		EClass PROPERTY_METHOD_DECLARATION = eINSTANCE.getPropertyMethodDeclaration();

		/**
		 * The meta object literal for the '<em><b>Get Defined Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_METHOD_DECLARATION___GET_DEFINED_MEMBER = eINSTANCE.getPropertyMethodDeclaration__GetDefinedMember();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl <em>Getter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.GetterDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getGetterDeclaration()
		 * @generated
		 */
		EClass GETTER_DECLARATION = eINSTANCE.getGetterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Defined Getter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GETTER_DECLARATION__DEFINED_GETTER = eINSTANCE.getGetterDeclaration_DefinedGetter();

		/**
		 * The meta object literal for the '<em><b>Get Defined Accessor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation GETTER_DECLARATION___GET_DEFINED_ACCESSOR = eINSTANCE.getGetterDeclaration__GetDefinedAccessor();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.SetterDeclarationImpl <em>Setter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.SetterDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getSetterDeclaration()
		 * @generated
		 */
		EClass SETTER_DECLARATION = eINSTANCE.getSetterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Defined Setter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SETTER_DECLARATION__DEFINED_SETTER = eINSTANCE.getSetterDeclaration_DefinedSetter();

		/**
		 * The meta object literal for the '<em><b>Fpar</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SETTER_DECLARATION__FPAR = eINSTANCE.getSetterDeclaration_Fpar();

		/**
		 * The meta object literal for the '<em><b>Get Defined Accessor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SETTER_DECLARATION___GET_DEFINED_ACCESSOR = eINSTANCE.getSetterDeclaration__GetDefinedAccessor();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type Ref</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SETTER_DECLARATION___GET_DECLARED_TYPE_REF = eINSTANCE.getSetterDeclaration__GetDeclaredTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertyGetterDeclarationImpl <em>Property Getter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertyGetterDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyGetterDeclaration()
		 * @generated
		 */
		EClass PROPERTY_GETTER_DECLARATION = eINSTANCE.getPropertyGetterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Get Defined Getter</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_GETTER_DECLARATION___GET_DEFINED_GETTER = eINSTANCE.getPropertyGetterDeclaration__GetDefinedGetter();

		/**
		 * The meta object literal for the '<em><b>Get Defined Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_GETTER_DECLARATION___GET_DEFINED_MEMBER = eINSTANCE.getPropertyGetterDeclaration__GetDefinedMember();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_GETTER_DECLARATION___IS_VALID_NAME = eINSTANCE.getPropertyGetterDeclaration__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertySetterDeclarationImpl <em>Property Setter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertySetterDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertySetterDeclaration()
		 * @generated
		 */
		EClass PROPERTY_SETTER_DECLARATION = eINSTANCE.getPropertySetterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Get Defined Setter</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_SETTER_DECLARATION___GET_DEFINED_SETTER = eINSTANCE.getPropertySetterDeclaration__GetDefinedSetter();

		/**
		 * The meta object literal for the '<em><b>Get Defined Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_SETTER_DECLARATION___GET_DEFINED_MEMBER = eINSTANCE.getPropertySetterDeclaration__GetDefinedMember();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_SETTER_DECLARATION___IS_VALID_NAME = eINSTANCE.getPropertySetterDeclaration__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PropertySpreadImpl <em>Property Spread</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PropertySpreadImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertySpread()
		 * @generated
		 */
		EClass PROPERTY_SPREAD = eINSTANCE.getPropertySpread();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_SPREAD__EXPRESSION = eINSTANCE.getPropertySpread_Expression();

		/**
		 * The meta object literal for the '<em><b>Get Defined Member</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROPERTY_SPREAD___GET_DEFINED_MEMBER = eINSTANCE.getPropertySpread__GetDefinedMember();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '<em><b>Is Valid Simple Assignment Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = eINSTANCE.getExpression__IsValidSimpleAssignmentTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.NewTargetImpl <em>New Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.NewTargetImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNewTarget()
		 * @generated
		 */
		EClass NEW_TARGET = eINSTANCE.getNewTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.NewExpressionImpl <em>New Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.NewExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNewExpression()
		 * @generated
		 */
		EClass NEW_EXPRESSION = eINSTANCE.getNewExpression();

		/**
		 * The meta object literal for the '<em><b>Callee</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEW_EXPRESSION__CALLEE = eINSTANCE.getNewExpression_Callee();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEW_EXPRESSION__ARGUMENTS = eINSTANCE.getNewExpression_Arguments();

		/**
		 * The meta object literal for the '<em><b>With Args</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEW_EXPRESSION__WITH_ARGS = eINSTANCE.getNewExpression_WithArgs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ParameterizedAccessImpl <em>Parameterized Access</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ParameterizedAccessImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParameterizedAccess()
		 * @generated
		 */
		EClass PARAMETERIZED_ACCESS = eINSTANCE.getParameterizedAccess();

		/**
		 * The meta object literal for the '<em><b>Type Args</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETERIZED_ACCESS__TYPE_ARGS = eINSTANCE.getParameterizedAccess_TypeArgs();

		/**
		 * The meta object literal for the '<em><b>Is Parameterized</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_ACCESS___IS_PARAMETERIZED = eINSTANCE.getParameterizedAccess__IsParameterized();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ExpressionWithTargetImpl <em>Expression With Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ExpressionWithTargetImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getExpressionWithTarget()
		 * @generated
		 */
		EClass EXPRESSION_WITH_TARGET = eINSTANCE.getExpressionWithTarget();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION_WITH_TARGET__TARGET = eINSTANCE.getExpressionWithTarget_Target();

		/**
		 * The meta object literal for the '<em><b>Optional Chaining</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION_WITH_TARGET__OPTIONAL_CHAINING = eINSTANCE.getExpressionWithTarget_OptionalChaining();

		/**
		 * The meta object literal for the '<em><b>Is Or Has Target With Optional Chaining</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation EXPRESSION_WITH_TARGET___IS_OR_HAS_TARGET_WITH_OPTIONAL_CHAINING = eINSTANCE.getExpressionWithTarget__IsOrHasTargetWithOptionalChaining();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ParameterizedCallExpressionImpl <em>Parameterized Call Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ParameterizedCallExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParameterizedCallExpression()
		 * @generated
		 */
		EClass PARAMETERIZED_CALL_EXPRESSION = eINSTANCE.getParameterizedCallExpression();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETERIZED_CALL_EXPRESSION__ARGUMENTS = eINSTANCE.getParameterizedCallExpression_Arguments();

		/**
		 * The meta object literal for the '<em><b>Get Receiver</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_CALL_EXPRESSION___GET_RECEIVER = eINSTANCE.getParameterizedCallExpression__GetReceiver();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ImportCallExpressionImpl <em>Import Call Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ImportCallExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getImportCallExpression()
		 * @generated
		 */
		EClass IMPORT_CALL_EXPRESSION = eINSTANCE.getImportCallExpression();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMPORT_CALL_EXPRESSION__ARGUMENTS = eINSTANCE.getImportCallExpression_Arguments();

		/**
		 * The meta object literal for the '<em><b>Get Argument</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IMPORT_CALL_EXPRESSION___GET_ARGUMENT = eINSTANCE.getImportCallExpression__GetArgument();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ArgumentImpl <em>Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ArgumentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArgument()
		 * @generated
		 */
		EClass ARGUMENT = eINSTANCE.getArgument();

		/**
		 * The meta object literal for the '<em><b>Spread</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT__SPREAD = eINSTANCE.getArgument_Spread();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT__EXPRESSION = eINSTANCE.getArgument_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.IndexedAccessExpressionImpl <em>Indexed Access Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.IndexedAccessExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIndexedAccessExpression()
		 * @generated
		 */
		EClass INDEXED_ACCESS_EXPRESSION = eINSTANCE.getIndexedAccessExpression();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INDEXED_ACCESS_EXPRESSION__INDEX = eINSTANCE.getIndexedAccessExpression_Index();

		/**
		 * The meta object literal for the '<em><b>Is Valid Simple Assignment Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation INDEXED_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = eINSTANCE.getIndexedAccessExpression__IsValidSimpleAssignmentTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.TaggedTemplateStringImpl <em>Tagged Template String</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.TaggedTemplateStringImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTaggedTemplateString()
		 * @generated
		 */
		EClass TAGGED_TEMPLATE_STRING = eINSTANCE.getTaggedTemplateString();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAGGED_TEMPLATE_STRING__TEMPLATE = eINSTANCE.getTaggedTemplateString_Template();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.MemberAccessImpl <em>Member Access</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.MemberAccessImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMemberAccess()
		 * @generated
		 */
		EClass MEMBER_ACCESS = eINSTANCE.getMemberAccess();

		/**
		 * The meta object literal for the '<em><b>Composed Member Cache</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEMBER_ACCESS__COMPOSED_MEMBER_CACHE = eINSTANCE.getMemberAccess_ComposedMemberCache();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl <em>Parameterized Property Access Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ParameterizedPropertyAccessExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getParameterizedPropertyAccessExpression()
		 * @generated
		 */
		EClass PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION = eINSTANCE.getParameterizedPropertyAccessExpression();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY = eINSTANCE.getParameterizedPropertyAccessExpression_Property();

		/**
		 * The meta object literal for the '<em><b>Property As Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY_AS_TEXT = eINSTANCE.getParameterizedPropertyAccessExpression_PropertyAsText();

		/**
		 * The meta object literal for the '<em><b>Is Valid Simple Assignment Target</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION___IS_VALID_SIMPLE_ASSIGNMENT_TARGET = eINSTANCE.getParameterizedPropertyAccessExpression__IsValidSimpleAssignmentTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AwaitExpressionImpl <em>Await Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AwaitExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAwaitExpression()
		 * @generated
		 */
		EClass AWAIT_EXPRESSION = eINSTANCE.getAwaitExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AWAIT_EXPRESSION__EXPRESSION = eINSTANCE.getAwaitExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PromisifyExpressionImpl <em>Promisify Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PromisifyExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPromisifyExpression()
		 * @generated
		 */
		EClass PROMISIFY_EXPRESSION = eINSTANCE.getPromisifyExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROMISIFY_EXPRESSION__EXPRESSION = eINSTANCE.getPromisifyExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.YieldExpressionImpl <em>Yield Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.YieldExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getYieldExpression()
		 * @generated
		 */
		EClass YIELD_EXPRESSION = eINSTANCE.getYieldExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference YIELD_EXPRESSION__EXPRESSION = eINSTANCE.getYieldExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Many</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute YIELD_EXPRESSION__MANY = eINSTANCE.getYieldExpression_Many();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.LiteralImpl <em>Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.LiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLiteral()
		 * @generated
		 */
		EClass LITERAL = eINSTANCE.getLiteral();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.NullLiteralImpl <em>Null Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.NullLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNullLiteral()
		 * @generated
		 */
		EClass NULL_LITERAL = eINSTANCE.getNullLiteral();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NULL_LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getNullLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BooleanLiteralImpl <em>Boolean Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BooleanLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBooleanLiteral()
		 * @generated
		 */
		EClass BOOLEAN_LITERAL = eINSTANCE.getBooleanLiteral();

		/**
		 * The meta object literal for the '<em><b>True</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_LITERAL__TRUE = eINSTANCE.getBooleanLiteral_True();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BOOLEAN_LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getBooleanLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.StringLiteralImpl <em>String Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.StringLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getStringLiteral()
		 * @generated
		 */
		EClass STRING_LITERAL = eINSTANCE.getStringLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_LITERAL__VALUE = eINSTANCE.getStringLiteral_Value();

		/**
		 * The meta object literal for the '<em><b>Raw Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_LITERAL__RAW_VALUE = eINSTANCE.getStringLiteral_RawValue();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation STRING_LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getStringLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.TemplateLiteralImpl <em>Template Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.TemplateLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTemplateLiteral()
		 * @generated
		 */
		EClass TEMPLATE_LITERAL = eINSTANCE.getTemplateLiteral();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_LITERAL__SEGMENTS = eINSTANCE.getTemplateLiteral_Segments();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TEMPLATE_LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getTemplateLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.TemplateSegmentImpl <em>Template Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.TemplateSegmentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTemplateSegment()
		 * @generated
		 */
		EClass TEMPLATE_SEGMENT = eINSTANCE.getTemplateSegment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_SEGMENT__VALUE = eINSTANCE.getTemplateSegment_Value();

		/**
		 * The meta object literal for the '<em><b>Raw Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_SEGMENT__RAW_VALUE = eINSTANCE.getTemplateSegment_RawValue();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TEMPLATE_SEGMENT___GET_VALUE_AS_STRING = eINSTANCE.getTemplateSegment__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.NumericLiteralImpl <em>Numeric Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.NumericLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getNumericLiteral()
		 * @generated
		 */
		EClass NUMERIC_LITERAL = eINSTANCE.getNumericLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERIC_LITERAL__VALUE = eINSTANCE.getNumericLiteral_Value();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation NUMERIC_LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getNumericLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.DoubleLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getDoubleLiteral()
		 * @generated
		 */
		EClass DOUBLE_LITERAL = eINSTANCE.getDoubleLiteral();

		/**
		 * The meta object literal for the '<em><b>To Double</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DOUBLE_LITERAL___TO_DOUBLE = eINSTANCE.getDoubleLiteral__ToDouble();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DOUBLE_LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getDoubleLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AbstractIntLiteralImpl <em>Abstract Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AbstractIntLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAbstractIntLiteral()
		 * @generated
		 */
		EClass ABSTRACT_INT_LITERAL = eINSTANCE.getAbstractIntLiteral();

		/**
		 * The meta object literal for the '<em><b>To Int</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ABSTRACT_INT_LITERAL___TO_INT = eINSTANCE.getAbstractIntLiteral__ToInt();

		/**
		 * The meta object literal for the '<em><b>To Long</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ABSTRACT_INT_LITERAL___TO_LONG = eINSTANCE.getAbstractIntLiteral__ToLong();

		/**
		 * The meta object literal for the '<em><b>To Big Integer</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ABSTRACT_INT_LITERAL___TO_BIG_INTEGER = eINSTANCE.getAbstractIntLiteral__ToBigInteger();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.IntLiteralImpl <em>Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.IntLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIntLiteral()
		 * @generated
		 */
		EClass INT_LITERAL = eINSTANCE.getIntLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BinaryIntLiteralImpl <em>Binary Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BinaryIntLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryIntLiteral()
		 * @generated
		 */
		EClass BINARY_INT_LITERAL = eINSTANCE.getBinaryIntLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.OctalIntLiteralImpl <em>Octal Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.OctalIntLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getOctalIntLiteral()
		 * @generated
		 */
		EClass OCTAL_INT_LITERAL = eINSTANCE.getOctalIntLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.LegacyOctalIntLiteralImpl <em>Legacy Octal Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.LegacyOctalIntLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getLegacyOctalIntLiteral()
		 * @generated
		 */
		EClass LEGACY_OCTAL_INT_LITERAL = eINSTANCE.getLegacyOctalIntLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.HexIntLiteralImpl <em>Hex Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.HexIntLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getHexIntLiteral()
		 * @generated
		 */
		EClass HEX_INT_LITERAL = eINSTANCE.getHexIntLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ScientificIntLiteralImpl <em>Scientific Int Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ScientificIntLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getScientificIntLiteral()
		 * @generated
		 */
		EClass SCIENTIFIC_INT_LITERAL = eINSTANCE.getScientificIntLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.RegularExpressionLiteralImpl <em>Regular Expression Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.RegularExpressionLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getRegularExpressionLiteral()
		 * @generated
		 */
		EClass REGULAR_EXPRESSION_LITERAL = eINSTANCE.getRegularExpressionLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGULAR_EXPRESSION_LITERAL__VALUE = eINSTANCE.getRegularExpressionLiteral_Value();

		/**
		 * The meta object literal for the '<em><b>Get Value As String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation REGULAR_EXPRESSION_LITERAL___GET_VALUE_AS_STRING = eINSTANCE.getRegularExpressionLiteral__GetValueAsString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.PostfixExpressionImpl <em>Postfix Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.PostfixExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPostfixExpression()
		 * @generated
		 */
		EClass POSTFIX_EXPRESSION = eINSTANCE.getPostfixExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POSTFIX_EXPRESSION__EXPRESSION = eINSTANCE.getPostfixExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSTFIX_EXPRESSION__OP = eINSTANCE.getPostfixExpression_Op();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.UnaryExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getUnaryExpression()
		 * @generated
		 */
		EClass UNARY_EXPRESSION = eINSTANCE.getUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_EXPRESSION__OP = eINSTANCE.getUnaryExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_EXPRESSION__EXPRESSION = eINSTANCE.getUnaryExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.CastExpressionImpl <em>Cast Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.CastExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCastExpression()
		 * @generated
		 */
		EClass CAST_EXPRESSION = eINSTANCE.getCastExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAST_EXPRESSION__EXPRESSION = eINSTANCE.getCastExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Target Type Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAST_EXPRESSION__TARGET_TYPE_REF = eINSTANCE.getCastExpression_TargetTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.MultiplicativeExpressionImpl <em>Multiplicative Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.MultiplicativeExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMultiplicativeExpression()
		 * @generated
		 */
		EClass MULTIPLICATIVE_EXPRESSION = eINSTANCE.getMultiplicativeExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTIPLICATIVE_EXPRESSION__LHS = eINSTANCE.getMultiplicativeExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MULTIPLICATIVE_EXPRESSION__OP = eINSTANCE.getMultiplicativeExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTIPLICATIVE_EXPRESSION__RHS = eINSTANCE.getMultiplicativeExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AdditiveExpressionImpl <em>Additive Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AdditiveExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAdditiveExpression()
		 * @generated
		 */
		EClass ADDITIVE_EXPRESSION = eINSTANCE.getAdditiveExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADDITIVE_EXPRESSION__LHS = eINSTANCE.getAdditiveExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDITIVE_EXPRESSION__OP = eINSTANCE.getAdditiveExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADDITIVE_EXPRESSION__RHS = eINSTANCE.getAdditiveExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ShiftExpressionImpl <em>Shift Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ShiftExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getShiftExpression()
		 * @generated
		 */
		EClass SHIFT_EXPRESSION = eINSTANCE.getShiftExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHIFT_EXPRESSION__LHS = eINSTANCE.getShiftExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHIFT_EXPRESSION__OP = eINSTANCE.getShiftExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHIFT_EXPRESSION__RHS = eINSTANCE.getShiftExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.RelationalExpressionImpl <em>Relational Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.RelationalExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getRelationalExpression()
		 * @generated
		 */
		EClass RELATIONAL_EXPRESSION = eINSTANCE.getRelationalExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATIONAL_EXPRESSION__LHS = eINSTANCE.getRelationalExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATIONAL_EXPRESSION__OP = eINSTANCE.getRelationalExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATIONAL_EXPRESSION__RHS = eINSTANCE.getRelationalExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.EqualityExpressionImpl <em>Equality Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.EqualityExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getEqualityExpression()
		 * @generated
		 */
		EClass EQUALITY_EXPRESSION = eINSTANCE.getEqualityExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EQUALITY_EXPRESSION__LHS = eINSTANCE.getEqualityExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EQUALITY_EXPRESSION__OP = eINSTANCE.getEqualityExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EQUALITY_EXPRESSION__RHS = eINSTANCE.getEqualityExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BinaryBitwiseExpressionImpl <em>Binary Bitwise Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BinaryBitwiseExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryBitwiseExpression()
		 * @generated
		 */
		EClass BINARY_BITWISE_EXPRESSION = eINSTANCE.getBinaryBitwiseExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_BITWISE_EXPRESSION__LHS = eINSTANCE.getBinaryBitwiseExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_BITWISE_EXPRESSION__OP = eINSTANCE.getBinaryBitwiseExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_BITWISE_EXPRESSION__RHS = eINSTANCE.getBinaryBitwiseExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BinaryLogicalExpressionImpl <em>Binary Logical Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BinaryLogicalExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryLogicalExpression()
		 * @generated
		 */
		EClass BINARY_LOGICAL_EXPRESSION = eINSTANCE.getBinaryLogicalExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_LOGICAL_EXPRESSION__LHS = eINSTANCE.getBinaryLogicalExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_LOGICAL_EXPRESSION__OP = eINSTANCE.getBinaryLogicalExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_LOGICAL_EXPRESSION__RHS = eINSTANCE.getBinaryLogicalExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.CoalesceExpressionImpl <em>Coalesce Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.CoalesceExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCoalesceExpression()
		 * @generated
		 */
		EClass COALESCE_EXPRESSION = eINSTANCE.getCoalesceExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COALESCE_EXPRESSION__EXPRESSION = eINSTANCE.getCoalesceExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Default Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COALESCE_EXPRESSION__DEFAULT_EXPRESSION = eINSTANCE.getCoalesceExpression_DefaultExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ConditionalExpressionImpl <em>Conditional Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ConditionalExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getConditionalExpression()
		 * @generated
		 */
		EClass CONDITIONAL_EXPRESSION = eINSTANCE.getConditionalExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_EXPRESSION__EXPRESSION = eINSTANCE.getConditionalExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>True Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_EXPRESSION__TRUE_EXPRESSION = eINSTANCE.getConditionalExpression_TrueExpression();

		/**
		 * The meta object literal for the '<em><b>False Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITIONAL_EXPRESSION__FALSE_EXPRESSION = eINSTANCE.getConditionalExpression_FalseExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AssignmentExpressionImpl <em>Assignment Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AssignmentExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAssignmentExpression()
		 * @generated
		 */
		EClass ASSIGNMENT_EXPRESSION = eINSTANCE.getAssignmentExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT_EXPRESSION__LHS = eINSTANCE.getAssignmentExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSIGNMENT_EXPRESSION__OP = eINSTANCE.getAssignmentExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSIGNMENT_EXPRESSION__RHS = eINSTANCE.getAssignmentExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.CommaExpressionImpl <em>Comma Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.CommaExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getCommaExpression()
		 * @generated
		 */
		EClass COMMA_EXPRESSION = eINSTANCE.getCommaExpression();

		/**
		 * The meta object literal for the '<em><b>Exprs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMA_EXPRESSION__EXPRS = eINSTANCE.getCommaExpression_Exprs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.TypeDefiningElementImpl <em>Type Defining Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.TypeDefiningElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getTypeDefiningElement()
		 * @generated
		 */
		EClass TYPE_DEFINING_ELEMENT = eINSTANCE.getTypeDefiningElement();

		/**
		 * The meta object literal for the '<em><b>Defined Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_DEFINING_ELEMENT__DEFINED_TYPE = eINSTANCE.getTypeDefiningElement_DefinedType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.GenericDeclarationImpl <em>Generic Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.GenericDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getGenericDeclaration()
		 * @generated
		 */
		EClass GENERIC_DECLARATION = eINSTANCE.getGenericDeclaration();

		/**
		 * The meta object literal for the '<em><b>Type Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_DECLARATION__TYPE_VARS = eINSTANCE.getGenericDeclaration_TypeVars();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4TypeDefinitionImpl <em>N4 Type Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4TypeDefinitionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4TypeDefinition()
		 * @generated
		 */
		EClass N4_TYPE_DEFINITION = eINSTANCE.getN4TypeDefinition();

		/**
		 * The meta object literal for the '<em><b>Is External</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_TYPE_DEFINITION___IS_EXTERNAL = eINSTANCE.getN4TypeDefinition__IsExternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4TypeDeclarationImpl <em>N4 Type Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4TypeDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4TypeDeclaration()
		 * @generated
		 */
		EClass N4_TYPE_DECLARATION = eINSTANCE.getN4TypeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute N4_TYPE_DECLARATION__NAME = eINSTANCE.getN4TypeDeclaration_Name();

		/**
		 * The meta object literal for the '<em><b>Is External</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_TYPE_DECLARATION___IS_EXTERNAL = eINSTANCE.getN4TypeDeclaration__IsExternal();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassifierDeclarationImpl <em>N4 Classifier Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4ClassifierDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassifierDeclaration()
		 * @generated
		 */
		EClass N4_CLASSIFIER_DECLARATION = eINSTANCE.getN4ClassifierDeclaration();

		/**
		 * The meta object literal for the '<em><b>Typing Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute N4_CLASSIFIER_DECLARATION__TYPING_STRATEGY = eINSTANCE.getN4ClassifierDeclaration_TypingStrategy();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassifierDefinitionImpl <em>N4 Classifier Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4ClassifierDefinitionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassifierDefinition()
		 * @generated
		 */
		EClass N4_CLASSIFIER_DEFINITION = eINSTANCE.getN4ClassifierDefinition();

		/**
		 * The meta object literal for the '<em><b>Owned Members Raw</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_CLASSIFIER_DEFINITION__OWNED_MEMBERS_RAW = eINSTANCE.getN4ClassifierDefinition_OwnedMembersRaw();

		/**
		 * The meta object literal for the '<em><b>Get Owned Members</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_OWNED_MEMBERS = eINSTANCE.getN4ClassifierDefinition__GetOwnedMembers();

		/**
		 * The meta object literal for the '<em><b>Get Owned Ctor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_OWNED_CTOR = eINSTANCE.getN4ClassifierDefinition__GetOwnedCtor();

		/**
		 * The meta object literal for the '<em><b>Get Owned Callable Ctor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_OWNED_CALLABLE_CTOR = eINSTANCE.getN4ClassifierDefinition__GetOwnedCallableCtor();

		/**
		 * The meta object literal for the '<em><b>Get Owned Methods</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_OWNED_METHODS = eINSTANCE.getN4ClassifierDefinition__GetOwnedMethods();

		/**
		 * The meta object literal for the '<em><b>Get Owned Fields</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_OWNED_FIELDS = eINSTANCE.getN4ClassifierDefinition__GetOwnedFields();

		/**
		 * The meta object literal for the '<em><b>Get Owned Getters</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_OWNED_GETTERS = eINSTANCE.getN4ClassifierDefinition__GetOwnedGetters();

		/**
		 * The meta object literal for the '<em><b>Get Owned Setters</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_OWNED_SETTERS = eINSTANCE.getN4ClassifierDefinition__GetOwnedSetters();

		/**
		 * The meta object literal for the '<em><b>Get Super Classifier Refs</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_SUPER_CLASSIFIER_REFS = eINSTANCE.getN4ClassifierDefinition__GetSuperClassifierRefs();

		/**
		 * The meta object literal for the '<em><b>Get Implemented Or Extended Interface Refs</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASSIFIER_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = eINSTANCE.getN4ClassifierDefinition__GetImplementedOrExtendedInterfaceRefs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassDefinitionImpl <em>N4 Class Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4ClassDefinitionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassDefinition()
		 * @generated
		 */
		EClass N4_CLASS_DEFINITION = eINSTANCE.getN4ClassDefinition();

		/**
		 * The meta object literal for the '<em><b>Super Class Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_CLASS_DEFINITION__SUPER_CLASS_REF = eINSTANCE.getN4ClassDefinition_SuperClassRef();

		/**
		 * The meta object literal for the '<em><b>Super Class Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_CLASS_DEFINITION__SUPER_CLASS_EXPRESSION = eINSTANCE.getN4ClassDefinition_SuperClassExpression();

		/**
		 * The meta object literal for the '<em><b>Implemented Interface Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_CLASS_DEFINITION__IMPLEMENTED_INTERFACE_REFS = eINSTANCE.getN4ClassDefinition_ImplementedInterfaceRefs();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type As Class</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASS_DEFINITION___GET_DEFINED_TYPE_AS_CLASS = eINSTANCE.getN4ClassDefinition__GetDefinedTypeAsClass();

		/**
		 * The meta object literal for the '<em><b>Get Super Classifier Refs</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASS_DEFINITION___GET_SUPER_CLASSIFIER_REFS = eINSTANCE.getN4ClassDefinition__GetSuperClassifierRefs();

		/**
		 * The meta object literal for the '<em><b>Get Implemented Or Extended Interface Refs</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASS_DEFINITION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = eINSTANCE.getN4ClassDefinition__GetImplementedOrExtendedInterfaceRefs();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl <em>N4 Class Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4ClassDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassDeclaration()
		 * @generated
		 */
		EClass N4_CLASS_DECLARATION = eINSTANCE.getN4ClassDeclaration();

		/**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASS_DECLARATION___IS_ABSTRACT = eINSTANCE.getN4ClassDeclaration__IsAbstract();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_CLASS_DECLARATION___GET_VERSION = eINSTANCE.getN4ClassDeclaration__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4ClassExpressionImpl <em>N4 Class Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4ClassExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4ClassExpression()
		 * @generated
		 */
		EClass N4_CLASS_EXPRESSION = eINSTANCE.getN4ClassExpression();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute N4_CLASS_EXPRESSION__NAME = eINSTANCE.getN4ClassExpression_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4InterfaceDeclarationImpl <em>N4 Interface Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4InterfaceDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4InterfaceDeclaration()
		 * @generated
		 */
		EClass N4_INTERFACE_DECLARATION = eINSTANCE.getN4InterfaceDeclaration();

		/**
		 * The meta object literal for the '<em><b>Super Interface Refs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_INTERFACE_DECLARATION__SUPER_INTERFACE_REFS = eINSTANCE.getN4InterfaceDeclaration_SuperInterfaceRefs();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type As Interface</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_INTERFACE_DECLARATION___GET_DEFINED_TYPE_AS_INTERFACE = eINSTANCE.getN4InterfaceDeclaration__GetDefinedTypeAsInterface();

		/**
		 * The meta object literal for the '<em><b>Get Super Classifier Refs</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_INTERFACE_DECLARATION___GET_SUPER_CLASSIFIER_REFS = eINSTANCE.getN4InterfaceDeclaration__GetSuperClassifierRefs();

		/**
		 * The meta object literal for the '<em><b>Get Implemented Or Extended Interface Refs</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_INTERFACE_DECLARATION___GET_IMPLEMENTED_OR_EXTENDED_INTERFACE_REFS = eINSTANCE.getN4InterfaceDeclaration__GetImplementedOrExtendedInterfaceRefs();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_INTERFACE_DECLARATION___GET_VERSION = eINSTANCE.getN4InterfaceDeclaration__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4EnumDeclarationImpl <em>N4 Enum Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4EnumDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4EnumDeclaration()
		 * @generated
		 */
		EClass N4_ENUM_DECLARATION = eINSTANCE.getN4EnumDeclaration();

		/**
		 * The meta object literal for the '<em><b>Literals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_ENUM_DECLARATION__LITERALS = eINSTANCE.getN4EnumDeclaration_Literals();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type As Enum</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_ENUM_DECLARATION___GET_DEFINED_TYPE_AS_ENUM = eINSTANCE.getN4EnumDeclaration__GetDefinedTypeAsEnum();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_ENUM_DECLARATION___GET_VERSION = eINSTANCE.getN4EnumDeclaration__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4EnumLiteralImpl <em>N4 Enum Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4EnumLiteralImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4EnumLiteral()
		 * @generated
		 */
		EClass N4_ENUM_LITERAL = eINSTANCE.getN4EnumLiteral();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute N4_ENUM_LITERAL__NAME = eINSTANCE.getN4EnumLiteral_Name();

		/**
		 * The meta object literal for the '<em><b>Value Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_ENUM_LITERAL__VALUE_EXPRESSION = eINSTANCE.getN4EnumLiteral_ValueExpression();

		/**
		 * The meta object literal for the '<em><b>Defined Literal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_ENUM_LITERAL__DEFINED_LITERAL = eINSTANCE.getN4EnumLiteral_DefinedLiteral();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4TypeAliasDeclarationImpl <em>N4 Type Alias Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4TypeAliasDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4TypeAliasDeclaration()
		 * @generated
		 */
		EClass N4_TYPE_ALIAS_DECLARATION = eINSTANCE.getN4TypeAliasDeclaration();

		/**
		 * The meta object literal for the '<em><b>Actual Type Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_TYPE_ALIAS_DECLARATION__ACTUAL_TYPE_REF = eINSTANCE.getN4TypeAliasDeclaration_ActualTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ModifiableElementImpl <em>Modifiable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ModifiableElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getModifiableElement()
		 * @generated
		 */
		EClass MODIFIABLE_ELEMENT = eINSTANCE.getModifiableElement();

		/**
		 * The meta object literal for the '<em><b>Declared Modifiers</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODIFIABLE_ELEMENT__DECLARED_MODIFIERS = eINSTANCE.getModifiableElement_DeclaredModifiers();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4MemberDeclarationImpl <em>N4 Member Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4MemberDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4MemberDeclaration()
		 * @generated
		 */
		EClass N4_MEMBER_DECLARATION = eINSTANCE.getN4MemberDeclaration();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_MEMBER_DECLARATION__OWNER = eINSTANCE.getN4MemberDeclaration_Owner();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = eINSTANCE.getN4MemberDeclaration__GetDefinedTypeElement();

		/**
		 * The meta object literal for the '<em><b>Is Declared Abstract</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_DECLARED_ABSTRACT = eINSTANCE.getN4MemberDeclaration__IsDeclaredAbstract();

		/**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_ABSTRACT = eINSTANCE.getN4MemberDeclaration__IsAbstract();

		/**
		 * The meta object literal for the '<em><b>Is Declared Static</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_DECLARED_STATIC = eINSTANCE.getN4MemberDeclaration__IsDeclaredStatic();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_STATIC = eINSTANCE.getN4MemberDeclaration__IsStatic();

		/**
		 * The meta object literal for the '<em><b>Is Declared Final</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_DECLARED_FINAL = eINSTANCE.getN4MemberDeclaration__IsDeclaredFinal();

		/**
		 * The meta object literal for the '<em><b>Is Final</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_FINAL = eINSTANCE.getN4MemberDeclaration__IsFinal();

		/**
		 * The meta object literal for the '<em><b>Is Constructor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_CONSTRUCTOR = eINSTANCE.getN4MemberDeclaration__IsConstructor();

		/**
		 * The meta object literal for the '<em><b>Is Callable Constructor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_DECLARATION___IS_CALLABLE_CONSTRUCTOR = eINSTANCE.getN4MemberDeclaration__IsCallableConstructor();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.AnnotableN4MemberDeclarationImpl <em>Annotable N4 Member Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.AnnotableN4MemberDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAnnotableN4MemberDeclaration()
		 * @generated
		 */
		EClass ANNOTABLE_N4_MEMBER_DECLARATION = eINSTANCE.getAnnotableN4MemberDeclaration();

		/**
		 * The meta object literal for the '<em><b>Annotation List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST = eINSTANCE.getAnnotableN4MemberDeclaration_AnnotationList();

		/**
		 * The meta object literal for the '<em><b>Get Annotations</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTABLE_N4_MEMBER_DECLARATION___GET_ANNOTATIONS = eINSTANCE.getAnnotableN4MemberDeclaration__GetAnnotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4MemberAnnotationListImpl <em>N4 Member Annotation List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4MemberAnnotationListImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4MemberAnnotationList()
		 * @generated
		 */
		EClass N4_MEMBER_ANNOTATION_LIST = eINSTANCE.getN4MemberAnnotationList();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_ANNOTATION_LIST___GET_DEFINED_TYPE_ELEMENT = eINSTANCE.getN4MemberAnnotationList__GetDefinedTypeElement();

		/**
		 * The meta object literal for the '<em><b>Get Declared Type Ref</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_ANNOTATION_LIST___GET_DECLARED_TYPE_REF = eINSTANCE.getN4MemberAnnotationList__GetDeclaredTypeRef();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_MEMBER_ANNOTATION_LIST___GET_NAME = eINSTANCE.getN4MemberAnnotationList__GetName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4FieldDeclarationImpl <em>N4 Field Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4FieldDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4FieldDeclaration()
		 * @generated
		 */
		EClass N4_FIELD_DECLARATION = eINSTANCE.getN4FieldDeclaration();

		/**
		 * The meta object literal for the '<em><b>Defined Field</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_FIELD_DECLARATION__DEFINED_FIELD = eINSTANCE.getN4FieldDeclaration_DefinedField();

		/**
		 * The meta object literal for the '<em><b>Declared Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute N4_FIELD_DECLARATION__DECLARED_OPTIONAL = eINSTANCE.getN4FieldDeclaration_DeclaredOptional();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference N4_FIELD_DECLARATION__EXPRESSION = eINSTANCE.getN4FieldDeclaration_Expression();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_FIELD_DECLARATION___GET_DEFINED_TYPE_ELEMENT = eINSTANCE.getN4FieldDeclaration__GetDefinedTypeElement();

		/**
		 * The meta object literal for the '<em><b>Is Const</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_FIELD_DECLARATION___IS_CONST = eINSTANCE.getN4FieldDeclaration__IsConst();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_FIELD_DECLARATION___IS_STATIC = eINSTANCE.getN4FieldDeclaration__IsStatic();

		/**
		 * The meta object literal for the '<em><b>Is Valid</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_FIELD_DECLARATION___IS_VALID = eINSTANCE.getN4FieldDeclaration__IsValid();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_FIELD_DECLARATION___IS_VALID_NAME = eINSTANCE.getN4FieldDeclaration__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl <em>Method Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.MethodDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMethodDeclaration()
		 * @generated
		 */
		EClass METHOD_DECLARATION = eINSTANCE.getMethodDeclaration();

		/**
		 * The meta object literal for the '<em><b>Exists Explicit Super Call</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METHOD_DECLARATION___EXISTS_EXPLICIT_SUPER_CALL = eINSTANCE.getMethodDeclaration__ExistsExplicitSuperCall();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METHOD_DECLARATION___GET_DEFINED_TYPE_ELEMENT = eINSTANCE.getMethodDeclaration__GetDefinedTypeElement();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation METHOD_DECLARATION___IS_STATIC = eINSTANCE.getMethodDeclaration__IsStatic();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl <em>N4 Method Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4MethodDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4MethodDeclaration()
		 * @generated
		 */
		EClass N4_METHOD_DECLARATION = eINSTANCE.getN4MethodDeclaration();

		/**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_METHOD_DECLARATION___IS_ABSTRACT = eINSTANCE.getN4MethodDeclaration__IsAbstract();

		/**
		 * The meta object literal for the '<em><b>Is Constructor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_METHOD_DECLARATION___IS_CONSTRUCTOR = eINSTANCE.getN4MethodDeclaration__IsConstructor();

		/**
		 * The meta object literal for the '<em><b>Is Callable Constructor</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_METHOD_DECLARATION___IS_CALLABLE_CONSTRUCTOR = eINSTANCE.getN4MethodDeclaration__IsCallableConstructor();

		/**
		 * The meta object literal for the '<em><b>Is Static</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_METHOD_DECLARATION___IS_STATIC = eINSTANCE.getN4MethodDeclaration__IsStatic();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_METHOD_DECLARATION___IS_VALID_NAME = eINSTANCE.getN4MethodDeclaration__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4FieldAccessorImpl <em>N4 Field Accessor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4FieldAccessorImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4FieldAccessor()
		 * @generated
		 */
		EClass N4_FIELD_ACCESSOR = eINSTANCE.getN4FieldAccessor();

		/**
		 * The meta object literal for the '<em><b>Is Abstract</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_FIELD_ACCESSOR___IS_ABSTRACT = eINSTANCE.getN4FieldAccessor__IsAbstract();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_FIELD_ACCESSOR___IS_VALID_NAME = eINSTANCE.getN4FieldAccessor__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4GetterDeclarationImpl <em>N4 Getter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4GetterDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4GetterDeclaration()
		 * @generated
		 */
		EClass N4_GETTER_DECLARATION = eINSTANCE.getN4GetterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_GETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = eINSTANCE.getN4GetterDeclaration__GetDefinedTypeElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.N4SetterDeclarationImpl <em>N4 Setter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.N4SetterDeclarationImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4SetterDeclaration()
		 * @generated
		 */
		EClass N4_SETTER_DECLARATION = eINSTANCE.getN4SetterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Get Defined Type Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation N4_SETTER_DECLARATION___GET_DEFINED_TYPE_ELEMENT = eINSTANCE.getN4SetterDeclaration__GetDefinedTypeElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BindingPatternImpl <em>Binding Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BindingPatternImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBindingPattern()
		 * @generated
		 */
		EClass BINDING_PATTERN = eINSTANCE.getBindingPattern();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ObjectBindingPatternImpl <em>Object Binding Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ObjectBindingPatternImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getObjectBindingPattern()
		 * @generated
		 */
		EClass OBJECT_BINDING_PATTERN = eINSTANCE.getObjectBindingPattern();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_BINDING_PATTERN__PROPERTIES = eINSTANCE.getObjectBindingPattern_Properties();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.ArrayBindingPatternImpl <em>Array Binding Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.ArrayBindingPatternImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getArrayBindingPattern()
		 * @generated
		 */
		EClass ARRAY_BINDING_PATTERN = eINSTANCE.getArrayBindingPattern();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_BINDING_PATTERN__ELEMENTS = eINSTANCE.getArrayBindingPattern_Elements();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BindingPropertyImpl <em>Binding Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BindingPropertyImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBindingProperty()
		 * @generated
		 */
		EClass BINDING_PROPERTY = eINSTANCE.getBindingProperty();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING_PROPERTY__VALUE = eINSTANCE.getBindingProperty_Value();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BINDING_PROPERTY___GET_NAME = eINSTANCE.getBindingProperty__GetName();

		/**
		 * The meta object literal for the '<em><b>Is Valid Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BINDING_PROPERTY___IS_VALID_NAME = eINSTANCE.getBindingProperty__IsValidName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.BindingElementImpl <em>Binding Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.BindingElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBindingElement()
		 * @generated
		 */
		EClass BINDING_ELEMENT = eINSTANCE.getBindingElement();

		/**
		 * The meta object literal for the '<em><b>Rest</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINDING_ELEMENT__REST = eINSTANCE.getBindingElement_Rest();

		/**
		 * The meta object literal for the '<em><b>Var Decl</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING_ELEMENT__VAR_DECL = eINSTANCE.getBindingElement_VarDecl();

		/**
		 * The meta object literal for the '<em><b>Nested Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING_ELEMENT__NESTED_PATTERN = eINSTANCE.getBindingElement_NestedPattern();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING_ELEMENT__EXPRESSION = eINSTANCE.getBindingElement_Expression();

		/**
		 * The meta object literal for the '<em><b>Is Elision</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation BINDING_ELEMENT___IS_ELISION = eINSTANCE.getBindingElement__IsElision();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXChildImpl <em>JSX Child</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXChildImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXChild()
		 * @generated
		 */
		EClass JSX_CHILD = eINSTANCE.getJSXChild();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXElementNameImpl <em>JSX Element Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXElementNameImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXElementName()
		 * @generated
		 */
		EClass JSX_ELEMENT_NAME = eINSTANCE.getJSXElementName();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT_NAME__EXPRESSION = eINSTANCE.getJSXElementName_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXTextImpl <em>JSX Text</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXTextImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXText()
		 * @generated
		 */
		EClass JSX_TEXT = eINSTANCE.getJSXText();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXExpressionImpl <em>JSX Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXExpressionImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXExpression()
		 * @generated
		 */
		EClass JSX_EXPRESSION = eINSTANCE.getJSXExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_EXPRESSION__EXPRESSION = eINSTANCE.getJSXExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXAttributeImpl <em>JSX Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXAttributeImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXAttribute()
		 * @generated
		 */
		EClass JSX_ATTRIBUTE = eINSTANCE.getJSXAttribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl <em>JSX Property Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXPropertyAttributeImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXPropertyAttribute()
		 * @generated
		 */
		EClass JSX_PROPERTY_ATTRIBUTE = eINSTANCE.getJSXPropertyAttribute();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_PROPERTY_ATTRIBUTE__PROPERTY = eINSTANCE.getJSXPropertyAttribute_Property();

		/**
		 * The meta object literal for the '<em><b>Property As Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JSX_PROPERTY_ATTRIBUTE__PROPERTY_AS_TEXT = eINSTANCE.getJSXPropertyAttribute_PropertyAsText();

		/**
		 * The meta object literal for the '<em><b>Jsx Attribute Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_PROPERTY_ATTRIBUTE__JSX_ATTRIBUTE_VALUE = eINSTANCE.getJSXPropertyAttribute_JsxAttributeValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXSpreadAttributeImpl <em>JSX Spread Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXSpreadAttributeImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXSpreadAttribute()
		 * @generated
		 */
		EClass JSX_SPREAD_ATTRIBUTE = eINSTANCE.getJSXSpreadAttribute();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_SPREAD_ATTRIBUTE__EXPRESSION = eINSTANCE.getJSXSpreadAttribute_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXAbstractElementImpl <em>JSX Abstract Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXAbstractElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXAbstractElement()
		 * @generated
		 */
		EClass JSX_ABSTRACT_ELEMENT = eINSTANCE.getJSXAbstractElement();

		/**
		 * The meta object literal for the '<em><b>Jsx Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ABSTRACT_ELEMENT__JSX_CHILDREN = eINSTANCE.getJSXAbstractElement_JsxChildren();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXElementImpl <em>JSX Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXElement()
		 * @generated
		 */
		EClass JSX_ELEMENT = eINSTANCE.getJSXElement();

		/**
		 * The meta object literal for the '<em><b>Jsx Element Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT__JSX_ELEMENT_NAME = eINSTANCE.getJSXElement_JsxElementName();

		/**
		 * The meta object literal for the '<em><b>Jsx Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT__JSX_ATTRIBUTES = eINSTANCE.getJSXElement_JsxAttributes();

		/**
		 * The meta object literal for the '<em><b>Jsx Closing Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JSX_ELEMENT__JSX_CLOSING_NAME = eINSTANCE.getJSXElement_JsxClosingName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.JSXFragmentImpl <em>JSX Fragment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.JSXFragmentImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getJSXFragment()
		 * @generated
		 */
		EClass JSX_FRAGMENT = eINSTANCE.getJSXFragment();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VersionedElementImpl <em>Versioned Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VersionedElementImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVersionedElement()
		 * @generated
		 */
		EClass VERSIONED_ELEMENT = eINSTANCE.getVersionedElement();

		/**
		 * The meta object literal for the '<em><b>Declared Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSIONED_ELEMENT__DECLARED_VERSION = eINSTANCE.getVersionedElement_DeclaredVersion();

		/**
		 * The meta object literal for the '<em><b>Has Declared Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSIONED_ELEMENT___HAS_DECLARED_VERSION = eINSTANCE.getVersionedElement__HasDeclaredVersion();

		/**
		 * The meta object literal for the '<em><b>Get Declared Version Or Zero</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSIONED_ELEMENT___GET_DECLARED_VERSION_OR_ZERO = eINSTANCE.getVersionedElement__GetDeclaredVersionOrZero();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.VersionedIdentifierRefImpl <em>Versioned Identifier Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.VersionedIdentifierRefImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVersionedIdentifierRef()
		 * @generated
		 */
		EClass VERSIONED_IDENTIFIER_REF = eINSTANCE.getVersionedIdentifierRef();

		/**
		 * The meta object literal for the '<em><b>Get Version</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VERSIONED_IDENTIFIER_REF___GET_VERSION = eINSTANCE.getVersionedIdentifierRef__GetVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.impl.MigrationContextVariableImpl <em>Migration Context Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.impl.MigrationContextVariableImpl
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMigrationContextVariable()
		 * @generated
		 */
		EClass MIGRATION_CONTEXT_VARIABLE = eINSTANCE.getMigrationContextVariable();

		/**
		 * The meta object literal for the '<em><b>Get Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MIGRATION_CONTEXT_VARIABLE___GET_NAME = eINSTANCE.getMigrationContextVariable__GetName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.ModuleSpecifierForm <em>Module Specifier Form</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.ModuleSpecifierForm
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getModuleSpecifierForm()
		 * @generated
		 */
		EEnum MODULE_SPECIFIER_FORM = eINSTANCE.getModuleSpecifierForm();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.VariableStatementKeyword <em>Variable Statement Keyword</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.VariableStatementKeyword
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getVariableStatementKeyword()
		 * @generated
		 */
		EEnum VARIABLE_STATEMENT_KEYWORD = eINSTANCE.getVariableStatementKeyword();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.PropertyNameKind <em>Property Name Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.PropertyNameKind
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPropertyNameKind()
		 * @generated
		 */
		EEnum PROPERTY_NAME_KIND = eINSTANCE.getPropertyNameKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.PostfixOperator <em>Postfix Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.PostfixOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getPostfixOperator()
		 * @generated
		 */
		EEnum POSTFIX_OPERATOR = eINSTANCE.getPostfixOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.UnaryOperator <em>Unary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.UnaryOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getUnaryOperator()
		 * @generated
		 */
		EEnum UNARY_OPERATOR = eINSTANCE.getUnaryOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.MultiplicativeOperator <em>Multiplicative Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.MultiplicativeOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getMultiplicativeOperator()
		 * @generated
		 */
		EEnum MULTIPLICATIVE_OPERATOR = eINSTANCE.getMultiplicativeOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.AdditiveOperator <em>Additive Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.AdditiveOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAdditiveOperator()
		 * @generated
		 */
		EEnum ADDITIVE_OPERATOR = eINSTANCE.getAdditiveOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.RelationalOperator <em>Relational Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.RelationalOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getRelationalOperator()
		 * @generated
		 */
		EEnum RELATIONAL_OPERATOR = eINSTANCE.getRelationalOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.EqualityOperator <em>Equality Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.EqualityOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getEqualityOperator()
		 * @generated
		 */
		EEnum EQUALITY_OPERATOR = eINSTANCE.getEqualityOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.BinaryBitwiseOperator <em>Binary Bitwise Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.BinaryBitwiseOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryBitwiseOperator()
		 * @generated
		 */
		EEnum BINARY_BITWISE_OPERATOR = eINSTANCE.getBinaryBitwiseOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.BinaryLogicalOperator <em>Binary Logical Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.BinaryLogicalOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getBinaryLogicalOperator()
		 * @generated
		 */
		EEnum BINARY_LOGICAL_OPERATOR = eINSTANCE.getBinaryLogicalOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.ShiftOperator <em>Shift Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.ShiftOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getShiftOperator()
		 * @generated
		 */
		EEnum SHIFT_OPERATOR = eINSTANCE.getShiftOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.AssignmentOperator <em>Assignment Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.AssignmentOperator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getAssignmentOperator()
		 * @generated
		 */
		EEnum ASSIGNMENT_OPERATOR = eINSTANCE.getAssignmentOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4JS.N4Modifier <em>N4 Modifier</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4JS.N4Modifier
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getN4Modifier()
		 * @generated
		 */
		EEnum N4_MODIFIER = eINSTANCE.getN4Modifier();

		/**
		 * The meta object literal for the '<em>Iterator Of Expression</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Iterator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfExpression()
		 * @generated
		 */
		EDataType ITERATOR_OF_EXPRESSION = eINSTANCE.getIteratorOfExpression();

		/**
		 * The meta object literal for the '<em>Iterator Of Yield Expression</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Iterator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfYieldExpression()
		 * @generated
		 */
		EDataType ITERATOR_OF_YIELD_EXPRESSION = eINSTANCE.getIteratorOfYieldExpression();

		/**
		 * The meta object literal for the '<em>Iterator Of Statement</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Iterator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfStatement()
		 * @generated
		 */
		EDataType ITERATOR_OF_STATEMENT = eINSTANCE.getIteratorOfStatement();

		/**
		 * The meta object literal for the '<em>Iterator Of Return Statement</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Iterator
		 * @see org.eclipse.n4js.n4JS.impl.N4JSPackageImpl#getIteratorOfReturnStatement()
		 * @generated
		 */
		EDataType ITERATOR_OF_RETURN_STATEMENT = eINSTANCE.getIteratorOfReturnStatement();

	}

} //N4JSPackage
