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
package org.eclipse.n4js.jsdoc.dom;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
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
 * @see org.eclipse.n4js.jsdoc.dom.DomFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel modelName='Jsdoc' modelDirectory='/org.eclipse.n4js.jsdoc/emf-gen' forceOverwrite='true' updateClasspath='false' complianceLevel='11.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js.jsdoc'"
 * @generated
 */
public interface DomPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///org.eclipse.n4js/jsdoc.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.n4js.jsdoc.dom";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomPackage eINSTANCE = org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.DocletElementImpl <em>Doclet Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DocletElementImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getDocletElement()
	 * @generated
	 */
	int DOCLET_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_ELEMENT__BEGIN = 0;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_ELEMENT__END = 1;

	/**
	 * The number of structural features of the '<em>Doclet Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_ELEMENT___SET_RANGE__INT_INT = 0;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_ELEMENT___COVERS__INT = 1;

	/**
	 * The number of operations of the '<em>Doclet Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_ELEMENT_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.JSDocNodeImpl <em>JS Doc Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.JSDocNodeImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getJSDocNode()
	 * @generated
	 */
	int JS_DOC_NODE = 3;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE__BEGIN = DOCLET_ELEMENT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE__END = DOCLET_ELEMENT__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE__MARKERS = DOCLET_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>JS Doc Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE_FEATURE_COUNT = DOCLET_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE___SET_RANGE__INT_INT = DOCLET_ELEMENT___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE___COVERS__INT = DOCLET_ELEMENT___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE___GET_MARKER_VALUE__STRING = DOCLET_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE___SET_MARKER__STRING_STRING = DOCLET_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE___IS_MARKED_AS__STRING_STRING = DOCLET_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE___TO_STRING = DOCLET_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The number of operations of the '<em>JS Doc Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JS_DOC_NODE_OPERATION_COUNT = DOCLET_ELEMENT_OPERATION_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.CompositeImpl <em>Composite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.CompositeImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getComposite()
	 * @generated
	 */
	int COMPOSITE = 2;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE__BEGIN = JS_DOC_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE__END = JS_DOC_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE__MARKERS = JS_DOC_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE__CONTENTS = JS_DOC_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FEATURE_COUNT = JS_DOC_NODE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE___SET_RANGE__INT_INT = JS_DOC_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE___COVERS__INT = JS_DOC_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE___GET_MARKER_VALUE__STRING = JS_DOC_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE___SET_MARKER__STRING_STRING = JS_DOC_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE___IS_MARKED_AS__STRING_STRING = JS_DOC_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE___TO_STRING = JS_DOC_NODE___TO_STRING;

	/**
	 * The number of operations of the '<em>Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_OPERATION_COUNT = JS_DOC_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.DocletImpl <em>Doclet</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DocletImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getDoclet()
	 * @generated
	 */
	int DOCLET = 0;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET__BEGIN = COMPOSITE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET__END = COMPOSITE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET__MARKERS = COMPOSITE__MARKERS;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET__CONTENTS = COMPOSITE__CONTENTS;

	/**
	 * The feature id for the '<em><b>Line Tags</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET__LINE_TAGS = COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Doclet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_FEATURE_COUNT = COMPOSITE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___SET_RANGE__INT_INT = COMPOSITE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___COVERS__INT = COMPOSITE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___GET_MARKER_VALUE__STRING = COMPOSITE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___SET_MARKER__STRING_STRING = COMPOSITE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___IS_MARKED_AS__STRING_STRING = COMPOSITE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___TO_STRING = COMPOSITE___TO_STRING;

	/**
	 * The operation id for the '<em>Has Line Tag</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___HAS_LINE_TAG__STRING = COMPOSITE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Line Tags</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET___LINE_TAGS__STRING = COMPOSITE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Doclet</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCLET_OPERATION_COUNT = COMPOSITE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.ContentNodeImpl <em>Content Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.ContentNodeImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getContentNode()
	 * @generated
	 */
	int CONTENT_NODE = 4;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE__BEGIN = JS_DOC_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE__END = JS_DOC_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE__MARKERS = JS_DOC_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE__OWNER = JS_DOC_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Content Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE_FEATURE_COUNT = JS_DOC_NODE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE___SET_RANGE__INT_INT = JS_DOC_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE___COVERS__INT = JS_DOC_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE___GET_MARKER_VALUE__STRING = JS_DOC_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE___SET_MARKER__STRING_STRING = JS_DOC_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE___IS_MARKED_AS__STRING_STRING = JS_DOC_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE___TO_STRING = JS_DOC_NODE___TO_STRING;

	/**
	 * The number of operations of the '<em>Content Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTENT_NODE_OPERATION_COUNT = JS_DOC_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TagImpl <em>Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.TagImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTag()
	 * @generated
	 */
	int TAG = 5;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__BEGIN = DOCLET_ELEMENT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__END = DOCLET_ELEMENT__END;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__TITLE = DOCLET_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__VALUES = DOCLET_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tag Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG__TAG_DEFINITION = DOCLET_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_FEATURE_COUNT = DOCLET_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG___SET_RANGE__INT_INT = DOCLET_ELEMENT___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG___COVERS__INT = DOCLET_ELEMENT___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Value By Key</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG___GET_VALUE_BY_KEY__STRING = DOCLET_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG___TO_STRING = DOCLET_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_OPERATION_COUNT = DOCLET_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TagValueImpl <em>Tag Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.TagValueImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTagValue()
	 * @generated
	 */
	int TAG_VALUE = 6;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE__BEGIN = COMPOSITE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE__END = COMPOSITE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE__MARKERS = COMPOSITE__MARKERS;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE__CONTENTS = COMPOSITE__CONTENTS;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE__KEY = COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Tag Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE_FEATURE_COUNT = COMPOSITE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE___SET_RANGE__INT_INT = COMPOSITE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE___COVERS__INT = COMPOSITE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE___GET_MARKER_VALUE__STRING = COMPOSITE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE___SET_MARKER__STRING_STRING = COMPOSITE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE___IS_MARKED_AS__STRING_STRING = COMPOSITE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE___TO_STRING = COMPOSITE___TO_STRING;

	/**
	 * The number of operations of the '<em>Tag Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_VALUE_OPERATION_COUNT = COMPOSITE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TagTitleImpl <em>Tag Title</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.TagTitleImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTagTitle()
	 * @generated
	 */
	int TAG_TITLE = 7;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE__BEGIN = JS_DOC_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE__END = JS_DOC_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE__MARKERS = JS_DOC_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Tag</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE__TAG = JS_DOC_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE__TITLE = JS_DOC_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Actual Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE__ACTUAL_TITLE = JS_DOC_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Tag Title</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE_FEATURE_COUNT = JS_DOC_NODE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE___SET_RANGE__INT_INT = JS_DOC_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE___COVERS__INT = JS_DOC_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE___GET_MARKER_VALUE__STRING = JS_DOC_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE___SET_MARKER__STRING_STRING = JS_DOC_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE___IS_MARKED_AS__STRING_STRING = JS_DOC_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE___TO_STRING = JS_DOC_NODE___TO_STRING;

	/**
	 * The number of operations of the '<em>Tag Title</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAG_TITLE_OPERATION_COUNT = JS_DOC_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.LineTagImpl <em>Line Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.LineTagImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getLineTag()
	 * @generated
	 */
	int LINE_TAG = 8;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG__BEGIN = TAG__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG__END = TAG__END;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG__TITLE = TAG__TITLE;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG__VALUES = TAG__VALUES;

	/**
	 * The feature id for the '<em><b>Tag Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG__TAG_DEFINITION = TAG__TAG_DEFINITION;

	/**
	 * The feature id for the '<em><b>Doclet</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG__DOCLET = TAG_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Line Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG_FEATURE_COUNT = TAG_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG___SET_RANGE__INT_INT = TAG___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG___COVERS__INT = TAG___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Value By Key</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG___GET_VALUE_BY_KEY__STRING = TAG___GET_VALUE_BY_KEY__STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG___TO_STRING = TAG___TO_STRING;

	/**
	 * The number of operations of the '<em>Line Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINE_TAG_OPERATION_COUNT = TAG_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.InlineTagImpl <em>Inline Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.InlineTagImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getInlineTag()
	 * @generated
	 */
	int INLINE_TAG = 9;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG__BEGIN = CONTENT_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG__END = CONTENT_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG__MARKERS = CONTENT_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG__OWNER = CONTENT_NODE__OWNER;

	/**
	 * The feature id for the '<em><b>Title</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG__TITLE = CONTENT_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG__VALUES = CONTENT_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tag Definition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG__TAG_DEFINITION = CONTENT_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Inline Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG_FEATURE_COUNT = CONTENT_NODE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG___SET_RANGE__INT_INT = CONTENT_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG___COVERS__INT = CONTENT_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG___GET_MARKER_VALUE__STRING = CONTENT_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG___SET_MARKER__STRING_STRING = CONTENT_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG___IS_MARKED_AS__STRING_STRING = CONTENT_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>Get Value By Key</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG___GET_VALUE_BY_KEY__STRING = CONTENT_NODE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG___TO_STRING = CONTENT_NODE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Inline Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INLINE_TAG_OPERATION_COUNT = CONTENT_NODE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TextImpl <em>Text</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.TextImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getText()
	 * @generated
	 */
	int TEXT = 10;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT__BEGIN = CONTENT_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT__END = CONTENT_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT__MARKERS = CONTENT_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT__OWNER = CONTENT_NODE__OWNER;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT__TEXT = CONTENT_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FEATURE_COUNT = CONTENT_NODE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT___SET_RANGE__INT_INT = CONTENT_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT___COVERS__INT = CONTENT_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT___GET_MARKER_VALUE__STRING = CONTENT_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT___SET_MARKER__STRING_STRING = CONTENT_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT___IS_MARKED_AS__STRING_STRING = CONTENT_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT___TO_STRING = CONTENT_NODE___TO_STRING;

	/**
	 * The number of operations of the '<em>Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_OPERATION_COUNT = CONTENT_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.SimpleTypeReferenceImpl <em>Simple Type Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.SimpleTypeReferenceImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getSimpleTypeReference()
	 * @generated
	 */
	int SIMPLE_TYPE_REFERENCE = 11;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE__BEGIN = JS_DOC_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE__END = JS_DOC_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE__MARKERS = JS_DOC_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE__OWNER = JS_DOC_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE__TYPE_NAME = JS_DOC_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Simple Type Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE_FEATURE_COUNT = JS_DOC_NODE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE___SET_RANGE__INT_INT = JS_DOC_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE___COVERS__INT = JS_DOC_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE___GET_MARKER_VALUE__STRING = JS_DOC_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE___SET_MARKER__STRING_STRING = JS_DOC_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE___IS_MARKED_AS__STRING_STRING = JS_DOC_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>Type Name Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE___TYPE_NAME_SET = JS_DOC_NODE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE___TO_STRING = JS_DOC_NODE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Simple Type Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_REFERENCE_OPERATION_COUNT = JS_DOC_NODE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.FullTypeReferenceImpl <em>Full Type Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.FullTypeReferenceImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getFullTypeReference()
	 * @generated
	 */
	int FULL_TYPE_REFERENCE = 12;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE__BEGIN = SIMPLE_TYPE_REFERENCE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE__END = SIMPLE_TYPE_REFERENCE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE__MARKERS = SIMPLE_TYPE_REFERENCE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE__OWNER = SIMPLE_TYPE_REFERENCE__OWNER;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE__TYPE_NAME = SIMPLE_TYPE_REFERENCE__TYPE_NAME;

	/**
	 * The feature id for the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE__MODULE_NAME = SIMPLE_TYPE_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Full Type Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE_FEATURE_COUNT = SIMPLE_TYPE_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___SET_RANGE__INT_INT = SIMPLE_TYPE_REFERENCE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___COVERS__INT = SIMPLE_TYPE_REFERENCE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___GET_MARKER_VALUE__STRING = SIMPLE_TYPE_REFERENCE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___SET_MARKER__STRING_STRING = SIMPLE_TYPE_REFERENCE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___IS_MARKED_AS__STRING_STRING = SIMPLE_TYPE_REFERENCE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>Type Name Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___TYPE_NAME_SET = SIMPLE_TYPE_REFERENCE___TYPE_NAME_SET;

	/**
	 * The operation id for the '<em>Module Name Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___MODULE_NAME_SET = SIMPLE_TYPE_REFERENCE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___TO_STRING = SIMPLE_TYPE_REFERENCE_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>Full Type Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE___FULL_TYPE_NAME = SIMPLE_TYPE_REFERENCE_OPERATION_COUNT + 2;

	/**
	 * The number of operations of the '<em>Full Type Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_TYPE_REFERENCE_OPERATION_COUNT = SIMPLE_TYPE_REFERENCE_OPERATION_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.FullMemberReferenceImpl <em>Full Member Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.FullMemberReferenceImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getFullMemberReference()
	 * @generated
	 */
	int FULL_MEMBER_REFERENCE = 13;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__BEGIN = FULL_TYPE_REFERENCE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__END = FULL_TYPE_REFERENCE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__MARKERS = FULL_TYPE_REFERENCE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__OWNER = FULL_TYPE_REFERENCE__OWNER;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__TYPE_NAME = FULL_TYPE_REFERENCE__TYPE_NAME;

	/**
	 * The feature id for the '<em><b>Module Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__MODULE_NAME = FULL_TYPE_REFERENCE__MODULE_NAME;

	/**
	 * The feature id for the '<em><b>Member Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__MEMBER_NAME = FULL_TYPE_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Static Member</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE__STATIC_MEMBER = FULL_TYPE_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Full Member Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE_FEATURE_COUNT = FULL_TYPE_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___SET_RANGE__INT_INT = FULL_TYPE_REFERENCE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___COVERS__INT = FULL_TYPE_REFERENCE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___GET_MARKER_VALUE__STRING = FULL_TYPE_REFERENCE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___SET_MARKER__STRING_STRING = FULL_TYPE_REFERENCE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___IS_MARKED_AS__STRING_STRING = FULL_TYPE_REFERENCE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>Type Name Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___TYPE_NAME_SET = FULL_TYPE_REFERENCE___TYPE_NAME_SET;

	/**
	 * The operation id for the '<em>Module Name Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___MODULE_NAME_SET = FULL_TYPE_REFERENCE___MODULE_NAME_SET;

	/**
	 * The operation id for the '<em>Full Type Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___FULL_TYPE_NAME = FULL_TYPE_REFERENCE___FULL_TYPE_NAME;

	/**
	 * The operation id for the '<em>Member Name Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___MEMBER_NAME_SET = FULL_TYPE_REFERENCE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE___TO_STRING = FULL_TYPE_REFERENCE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Full Member Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FULL_MEMBER_REFERENCE_OPERATION_COUNT = FULL_TYPE_REFERENCE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.VariableReferenceImpl <em>Variable Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.VariableReferenceImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getVariableReference()
	 * @generated
	 */
	int VARIABLE_REFERENCE = 14;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE__BEGIN = CONTENT_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE__END = CONTENT_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE__MARKERS = CONTENT_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE__OWNER = CONTENT_NODE__OWNER;

	/**
	 * The feature id for the '<em><b>Variable Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE__VARIABLE_NAME = CONTENT_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE_FEATURE_COUNT = CONTENT_NODE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE___SET_RANGE__INT_INT = CONTENT_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE___COVERS__INT = CONTENT_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE___GET_MARKER_VALUE__STRING = CONTENT_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE___SET_MARKER__STRING_STRING = CONTENT_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE___IS_MARKED_AS__STRING_STRING = CONTENT_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE___TO_STRING = CONTENT_NODE___TO_STRING;

	/**
	 * The number of operations of the '<em>Variable Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE_OPERATION_COUNT = CONTENT_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.GenericReferenceImpl <em>Generic Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.GenericReferenceImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getGenericReference()
	 * @generated
	 */
	int GENERIC_REFERENCE = 15;

	/**
	 * The number of structural features of the '<em>Generic Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_REFERENCE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Generic Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.LiteralImpl <em>Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.LiteralImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getLiteral()
	 * @generated
	 */
	int LITERAL = 16;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__BEGIN = CONTENT_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__END = CONTENT_NODE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__MARKERS = CONTENT_NODE__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__OWNER = CONTENT_NODE__OWNER;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__VALUE = CONTENT_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL__NAME = CONTENT_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FEATURE_COUNT = CONTENT_NODE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___SET_RANGE__INT_INT = CONTENT_NODE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___COVERS__INT = CONTENT_NODE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___GET_MARKER_VALUE__STRING = CONTENT_NODE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___SET_MARKER__STRING_STRING = CONTENT_NODE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___IS_MARKED_AS__STRING_STRING = CONTENT_NODE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL___TO_STRING = CONTENT_NODE___TO_STRING;

	/**
	 * The number of operations of the '<em>Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_OPERATION_COUNT = CONTENT_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.MarkerImpl <em>Marker</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.MarkerImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getMarker()
	 * @generated
	 */
	int MARKER = 17;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARKER__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARKER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Marker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARKER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Marker</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MARKER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.ComposedContentImpl <em>Composed Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.ComposedContentImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getComposedContent()
	 * @generated
	 */
	int COMPOSED_CONTENT = 18;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT__BEGIN = COMPOSITE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT__END = COMPOSITE__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT__MARKERS = COMPOSITE__MARKERS;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT__CONTENTS = COMPOSITE__CONTENTS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT__OWNER = COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Composed Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT_FEATURE_COUNT = COMPOSITE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT___SET_RANGE__INT_INT = COMPOSITE___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT___COVERS__INT = COMPOSITE___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT___GET_MARKER_VALUE__STRING = COMPOSITE___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT___SET_MARKER__STRING_STRING = COMPOSITE___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT___IS_MARKED_AS__STRING_STRING = COMPOSITE___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT___TO_STRING = COMPOSITE___TO_STRING;

	/**
	 * The number of operations of the '<em>Composed Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_CONTENT_OPERATION_COUNT = COMPOSITE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.jsdoc.dom.impl.StructuredTextImpl <em>Structured Text</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.dom.impl.StructuredTextImpl
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getStructuredText()
	 * @generated
	 */
	int STRUCTURED_TEXT = 19;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT__BEGIN = TEXT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT__END = TEXT__END;

	/**
	 * The feature id for the '<em><b>Markers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT__MARKERS = TEXT__MARKERS;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT__OWNER = TEXT__OWNER;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT__TEXT = TEXT__TEXT;

	/**
	 * The feature id for the '<em><b>Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT__ROOT_ELEMENT = TEXT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Structured Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT_FEATURE_COUNT = TEXT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Set Range</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT___SET_RANGE__INT_INT = TEXT___SET_RANGE__INT_INT;

	/**
	 * The operation id for the '<em>Covers</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT___COVERS__INT = TEXT___COVERS__INT;

	/**
	 * The operation id for the '<em>Get Marker Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT___GET_MARKER_VALUE__STRING = TEXT___GET_MARKER_VALUE__STRING;

	/**
	 * The operation id for the '<em>Set Marker</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT___SET_MARKER__STRING_STRING = TEXT___SET_MARKER__STRING_STRING;

	/**
	 * The operation id for the '<em>Is Marked As</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT___IS_MARKED_AS__STRING_STRING = TEXT___IS_MARKED_AS__STRING_STRING;

	/**
	 * The operation id for the '<em>To String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT___TO_STRING = TEXT___TO_STRING;

	/**
	 * The number of operations of the '<em>Structured Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TEXT_OPERATION_COUNT = TEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '<em>Tag Definition</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.jsdoc.ITagDefinition
	 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTagDefinition()
	 * @generated
	 */
	int TAG_DEFINITION = 20;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.Doclet <em>Doclet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Doclet</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Doclet
	 * @generated
	 */
	EClass getDoclet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.jsdoc.dom.Doclet#getLineTags <em>Line Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Line Tags</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Doclet#getLineTags()
	 * @see #getDoclet()
	 * @generated
	 */
	EReference getDoclet_LineTags();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.Doclet#hasLineTag(java.lang.String) <em>Has Line Tag</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Has Line Tag</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.Doclet#hasLineTag(java.lang.String)
	 * @generated
	 */
	EOperation getDoclet__HasLineTag__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.Doclet#lineTags(java.lang.String) <em>Line Tags</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Line Tags</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.Doclet#lineTags(java.lang.String)
	 * @generated
	 */
	EOperation getDoclet__LineTags__String();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.DocletElement <em>Doclet Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Doclet Element</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.DocletElement
	 * @generated
	 */
	EClass getDocletElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.DocletElement#getBegin <em>Begin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Begin</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.DocletElement#getBegin()
	 * @see #getDocletElement()
	 * @generated
	 */
	EAttribute getDocletElement_Begin();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.DocletElement#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.DocletElement#getEnd()
	 * @see #getDocletElement()
	 * @generated
	 */
	EAttribute getDocletElement_End();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.DocletElement#setRange(int, int) <em>Set Range</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Range</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.DocletElement#setRange(int, int)
	 * @generated
	 */
	EOperation getDocletElement__SetRange__int_int();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.DocletElement#covers(int) <em>Covers</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Covers</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.DocletElement#covers(int)
	 * @generated
	 */
	EOperation getDocletElement__Covers__int();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.Composite <em>Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Composite
	 * @generated
	 */
	EClass getComposite();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.jsdoc.dom.Composite#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Composite#getContents()
	 * @see #getComposite()
	 * @generated
	 */
	EReference getComposite_Contents();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.JSDocNode <em>JS Doc Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>JS Doc Node</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.JSDocNode
	 * @generated
	 */
	EClass getJSDocNode();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.jsdoc.dom.JSDocNode#getMarkers <em>Markers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Markers</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.JSDocNode#getMarkers()
	 * @see #getJSDocNode()
	 * @generated
	 */
	EReference getJSDocNode_Markers();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.JSDocNode#getMarkerValue(java.lang.String) <em>Get Marker Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Marker Value</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.JSDocNode#getMarkerValue(java.lang.String)
	 * @generated
	 */
	EOperation getJSDocNode__GetMarkerValue__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.JSDocNode#setMarker(java.lang.String, java.lang.String) <em>Set Marker</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Marker</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.JSDocNode#setMarker(java.lang.String, java.lang.String)
	 * @generated
	 */
	EOperation getJSDocNode__SetMarker__String_String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.JSDocNode#isMarkedAs(java.lang.String, java.lang.String) <em>Is Marked As</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Marked As</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.JSDocNode#isMarkedAs(java.lang.String, java.lang.String)
	 * @generated
	 */
	EOperation getJSDocNode__IsMarkedAs__String_String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.JSDocNode#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.JSDocNode#toString()
	 * @generated
	 */
	EOperation getJSDocNode__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.ContentNode <em>Content Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Content Node</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.ContentNode
	 * @generated
	 */
	EClass getContentNode();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.n4js.jsdoc.dom.ContentNode#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.ContentNode#getOwner()
	 * @see #getContentNode()
	 * @generated
	 */
	EReference getContentNode_Owner();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Tag
	 * @generated
	 */
	EClass getTag();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.jsdoc.dom.Tag#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Title</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Tag#getTitle()
	 * @see #getTag()
	 * @generated
	 */
	EReference getTag_Title();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.jsdoc.dom.Tag#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Tag#getValues()
	 * @see #getTag()
	 * @generated
	 */
	EReference getTag_Values();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.Tag#getTagDefinition <em>Tag Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tag Definition</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Tag#getTagDefinition()
	 * @see #getTag()
	 * @generated
	 */
	EAttribute getTag_TagDefinition();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.Tag#getValueByKey(java.lang.String) <em>Get Value By Key</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Value By Key</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.Tag#getValueByKey(java.lang.String)
	 * @generated
	 */
	EOperation getTag__GetValueByKey__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.Tag#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.Tag#toString()
	 * @generated
	 */
	EOperation getTag__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.TagValue <em>Tag Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag Value</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.TagValue
	 * @generated
	 */
	EClass getTagValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.TagValue#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.TagValue#getKey()
	 * @see #getTagValue()
	 * @generated
	 */
	EAttribute getTagValue_Key();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.TagTitle <em>Tag Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tag Title</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.TagTitle
	 * @generated
	 */
	EClass getTagTitle();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getTag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Tag</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.TagTitle#getTag()
	 * @see #getTagTitle()
	 * @generated
	 */
	EReference getTagTitle_Tag();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.TagTitle#getTitle()
	 * @see #getTagTitle()
	 * @generated
	 */
	EAttribute getTagTitle_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.TagTitle#getActualTitle <em>Actual Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Actual Title</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.TagTitle#getActualTitle()
	 * @see #getTagTitle()
	 * @generated
	 */
	EAttribute getTagTitle_ActualTitle();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.LineTag <em>Line Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Line Tag</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.LineTag
	 * @generated
	 */
	EClass getLineTag();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet <em>Doclet</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Doclet</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.LineTag#getDoclet()
	 * @see #getLineTag()
	 * @generated
	 */
	EReference getLineTag_Doclet();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.InlineTag <em>Inline Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inline Tag</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.InlineTag
	 * @generated
	 */
	EClass getInlineTag();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.Text <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Text
	 * @generated
	 */
	EClass getText();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.Text#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Text#getText()
	 * @see #getText()
	 * @generated
	 */
	EAttribute getText_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.SimpleTypeReference <em>Simple Type Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Type Reference</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.SimpleTypeReference
	 * @generated
	 */
	EClass getSimpleTypeReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.SimpleTypeReference#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.SimpleTypeReference#getTypeName()
	 * @see #getSimpleTypeReference()
	 * @generated
	 */
	EAttribute getSimpleTypeReference_TypeName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.SimpleTypeReference#typeNameSet() <em>Type Name Set</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Type Name Set</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.SimpleTypeReference#typeNameSet()
	 * @generated
	 */
	EOperation getSimpleTypeReference__TypeNameSet();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.SimpleTypeReference#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.SimpleTypeReference#toString()
	 * @generated
	 */
	EOperation getSimpleTypeReference__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.FullTypeReference <em>Full Type Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Full Type Reference</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.FullTypeReference
	 * @generated
	 */
	EClass getFullTypeReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.FullTypeReference#getModuleName <em>Module Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Name</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.FullTypeReference#getModuleName()
	 * @see #getFullTypeReference()
	 * @generated
	 */
	EAttribute getFullTypeReference_ModuleName();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.FullTypeReference#moduleNameSet() <em>Module Name Set</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Module Name Set</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.FullTypeReference#moduleNameSet()
	 * @generated
	 */
	EOperation getFullTypeReference__ModuleNameSet();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.FullTypeReference#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.FullTypeReference#toString()
	 * @generated
	 */
	EOperation getFullTypeReference__ToString();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.FullTypeReference#fullTypeName() <em>Full Type Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Full Type Name</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.FullTypeReference#fullTypeName()
	 * @generated
	 */
	EOperation getFullTypeReference__FullTypeName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference <em>Full Member Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Full Member Reference</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.FullMemberReference
	 * @generated
	 */
	EClass getFullMemberReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#getMemberName <em>Member Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Member Name</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.FullMemberReference#getMemberName()
	 * @see #getFullMemberReference()
	 * @generated
	 */
	EAttribute getFullMemberReference_MemberName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#isStaticMember <em>Static Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Static Member</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.FullMemberReference#isStaticMember()
	 * @see #getFullMemberReference()
	 * @generated
	 */
	EAttribute getFullMemberReference_StaticMember();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#memberNameSet() <em>Member Name Set</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Member Name Set</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.FullMemberReference#memberNameSet()
	 * @generated
	 */
	EOperation getFullMemberReference__MemberNameSet();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.jsdoc.dom.FullMemberReference#toString() <em>To String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>To String</em>' operation.
	 * @see org.eclipse.n4js.jsdoc.dom.FullMemberReference#toString()
	 * @generated
	 */
	EOperation getFullMemberReference__ToString();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.VariableReference <em>Variable Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Reference</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.VariableReference
	 * @generated
	 */
	EClass getVariableReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.VariableReference#getVariableName <em>Variable Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variable Name</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.VariableReference#getVariableName()
	 * @see #getVariableReference()
	 * @generated
	 */
	EAttribute getVariableReference_VariableName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.GenericReference <em>Generic Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Reference</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.GenericReference
	 * @generated
	 */
	EClass getGenericReference();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.Literal <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Literal
	 * @generated
	 */
	EClass getLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.Literal#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Literal#getValue()
	 * @see #getLiteral()
	 * @generated
	 */
	EAttribute getLiteral_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.Literal#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Literal#getName()
	 * @see #getLiteral()
	 * @generated
	 */
	EAttribute getLiteral_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.Marker <em>Marker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Marker</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Marker
	 * @generated
	 */
	EClass getMarker();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.Marker#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Marker#getKey()
	 * @see #getMarker()
	 * @generated
	 */
	EAttribute getMarker_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.jsdoc.dom.Marker#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.Marker#getValue()
	 * @see #getMarker()
	 * @generated
	 */
	EAttribute getMarker_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.ComposedContent <em>Composed Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Content</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.ComposedContent
	 * @generated
	 */
	EClass getComposedContent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.jsdoc.dom.StructuredText <em>Structured Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structured Text</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.StructuredText
	 * @generated
	 */
	EClass getStructuredText();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.jsdoc.dom.StructuredText#getRootElement <em>Root Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Element</em>'.
	 * @see org.eclipse.n4js.jsdoc.dom.StructuredText#getRootElement()
	 * @see #getStructuredText()
	 * @generated
	 */
	EReference getStructuredText_RootElement();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.n4js.jsdoc.ITagDefinition <em>Tag Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Tag Definition</em>'.
	 * @see org.eclipse.n4js.jsdoc.ITagDefinition
	 * @model instanceClass="org.eclipse.n4js.jsdoc.ITagDefinition"
	 * @generated
	 */
	EDataType getTagDefinition();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DomFactory getDomFactory();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.DocletImpl <em>Doclet</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DocletImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getDoclet()
		 * @generated
		 */
		EClass DOCLET = eINSTANCE.getDoclet();

		/**
		 * The meta object literal for the '<em><b>Line Tags</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCLET__LINE_TAGS = eINSTANCE.getDoclet_LineTags();

		/**
		 * The meta object literal for the '<em><b>Has Line Tag</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DOCLET___HAS_LINE_TAG__STRING = eINSTANCE.getDoclet__HasLineTag__String();

		/**
		 * The meta object literal for the '<em><b>Line Tags</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DOCLET___LINE_TAGS__STRING = eINSTANCE.getDoclet__LineTags__String();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.DocletElementImpl <em>Doclet Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DocletElementImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getDocletElement()
		 * @generated
		 */
		EClass DOCLET_ELEMENT = eINSTANCE.getDocletElement();

		/**
		 * The meta object literal for the '<em><b>Begin</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCLET_ELEMENT__BEGIN = eINSTANCE.getDocletElement_Begin();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCLET_ELEMENT__END = eINSTANCE.getDocletElement_End();

		/**
		 * The meta object literal for the '<em><b>Set Range</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DOCLET_ELEMENT___SET_RANGE__INT_INT = eINSTANCE.getDocletElement__SetRange__int_int();

		/**
		 * The meta object literal for the '<em><b>Covers</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation DOCLET_ELEMENT___COVERS__INT = eINSTANCE.getDocletElement__Covers__int();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.CompositeImpl <em>Composite</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.CompositeImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getComposite()
		 * @generated
		 */
		EClass COMPOSITE = eINSTANCE.getComposite();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE__CONTENTS = eINSTANCE.getComposite_Contents();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.JSDocNodeImpl <em>JS Doc Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.JSDocNodeImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getJSDocNode()
		 * @generated
		 */
		EClass JS_DOC_NODE = eINSTANCE.getJSDocNode();

		/**
		 * The meta object literal for the '<em><b>Markers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JS_DOC_NODE__MARKERS = eINSTANCE.getJSDocNode_Markers();

		/**
		 * The meta object literal for the '<em><b>Get Marker Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JS_DOC_NODE___GET_MARKER_VALUE__STRING = eINSTANCE.getJSDocNode__GetMarkerValue__String();

		/**
		 * The meta object literal for the '<em><b>Set Marker</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JS_DOC_NODE___SET_MARKER__STRING_STRING = eINSTANCE.getJSDocNode__SetMarker__String_String();

		/**
		 * The meta object literal for the '<em><b>Is Marked As</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JS_DOC_NODE___IS_MARKED_AS__STRING_STRING = eINSTANCE.getJSDocNode__IsMarkedAs__String_String();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation JS_DOC_NODE___TO_STRING = eINSTANCE.getJSDocNode__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.ContentNodeImpl <em>Content Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.ContentNodeImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getContentNode()
		 * @generated
		 */
		EClass CONTENT_NODE = eINSTANCE.getContentNode();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTENT_NODE__OWNER = eINSTANCE.getContentNode_Owner();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TagImpl <em>Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.TagImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTag()
		 * @generated
		 */
		EClass TAG = eINSTANCE.getTag();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAG__TITLE = eINSTANCE.getTag_Title();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAG__VALUES = eINSTANCE.getTag_Values();

		/**
		 * The meta object literal for the '<em><b>Tag Definition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG__TAG_DEFINITION = eINSTANCE.getTag_TagDefinition();

		/**
		 * The meta object literal for the '<em><b>Get Value By Key</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TAG___GET_VALUE_BY_KEY__STRING = eINSTANCE.getTag__GetValueByKey__String();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TAG___TO_STRING = eINSTANCE.getTag__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TagValueImpl <em>Tag Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.TagValueImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTagValue()
		 * @generated
		 */
		EClass TAG_VALUE = eINSTANCE.getTagValue();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG_VALUE__KEY = eINSTANCE.getTagValue_Key();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TagTitleImpl <em>Tag Title</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.TagTitleImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTagTitle()
		 * @generated
		 */
		EClass TAG_TITLE = eINSTANCE.getTagTitle();

		/**
		 * The meta object literal for the '<em><b>Tag</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAG_TITLE__TAG = eINSTANCE.getTagTitle_Tag();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG_TITLE__TITLE = eINSTANCE.getTagTitle_Title();

		/**
		 * The meta object literal for the '<em><b>Actual Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAG_TITLE__ACTUAL_TITLE = eINSTANCE.getTagTitle_ActualTitle();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.LineTagImpl <em>Line Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.LineTagImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getLineTag()
		 * @generated
		 */
		EClass LINE_TAG = eINSTANCE.getLineTag();

		/**
		 * The meta object literal for the '<em><b>Doclet</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINE_TAG__DOCLET = eINSTANCE.getLineTag_Doclet();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.InlineTagImpl <em>Inline Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.InlineTagImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getInlineTag()
		 * @generated
		 */
		EClass INLINE_TAG = eINSTANCE.getInlineTag();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.TextImpl <em>Text</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.TextImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getText()
		 * @generated
		 */
		EClass TEXT = eINSTANCE.getText();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT__TEXT = eINSTANCE.getText_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.SimpleTypeReferenceImpl <em>Simple Type Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.SimpleTypeReferenceImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getSimpleTypeReference()
		 * @generated
		 */
		EClass SIMPLE_TYPE_REFERENCE = eINSTANCE.getSimpleTypeReference();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TYPE_REFERENCE__TYPE_NAME = eINSTANCE.getSimpleTypeReference_TypeName();

		/**
		 * The meta object literal for the '<em><b>Type Name Set</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_TYPE_REFERENCE___TYPE_NAME_SET = eINSTANCE.getSimpleTypeReference__TypeNameSet();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SIMPLE_TYPE_REFERENCE___TO_STRING = eINSTANCE.getSimpleTypeReference__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.FullTypeReferenceImpl <em>Full Type Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.FullTypeReferenceImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getFullTypeReference()
		 * @generated
		 */
		EClass FULL_TYPE_REFERENCE = eINSTANCE.getFullTypeReference();

		/**
		 * The meta object literal for the '<em><b>Module Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FULL_TYPE_REFERENCE__MODULE_NAME = eINSTANCE.getFullTypeReference_ModuleName();

		/**
		 * The meta object literal for the '<em><b>Module Name Set</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FULL_TYPE_REFERENCE___MODULE_NAME_SET = eINSTANCE.getFullTypeReference__ModuleNameSet();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FULL_TYPE_REFERENCE___TO_STRING = eINSTANCE.getFullTypeReference__ToString();

		/**
		 * The meta object literal for the '<em><b>Full Type Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FULL_TYPE_REFERENCE___FULL_TYPE_NAME = eINSTANCE.getFullTypeReference__FullTypeName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.FullMemberReferenceImpl <em>Full Member Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.FullMemberReferenceImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getFullMemberReference()
		 * @generated
		 */
		EClass FULL_MEMBER_REFERENCE = eINSTANCE.getFullMemberReference();

		/**
		 * The meta object literal for the '<em><b>Member Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FULL_MEMBER_REFERENCE__MEMBER_NAME = eINSTANCE.getFullMemberReference_MemberName();

		/**
		 * The meta object literal for the '<em><b>Static Member</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FULL_MEMBER_REFERENCE__STATIC_MEMBER = eINSTANCE.getFullMemberReference_StaticMember();

		/**
		 * The meta object literal for the '<em><b>Member Name Set</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FULL_MEMBER_REFERENCE___MEMBER_NAME_SET = eINSTANCE.getFullMemberReference__MemberNameSet();

		/**
		 * The meta object literal for the '<em><b>To String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation FULL_MEMBER_REFERENCE___TO_STRING = eINSTANCE.getFullMemberReference__ToString();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.VariableReferenceImpl <em>Variable Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.VariableReferenceImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getVariableReference()
		 * @generated
		 */
		EClass VARIABLE_REFERENCE = eINSTANCE.getVariableReference();

		/**
		 * The meta object literal for the '<em><b>Variable Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_REFERENCE__VARIABLE_NAME = eINSTANCE.getVariableReference_VariableName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.GenericReferenceImpl <em>Generic Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.GenericReferenceImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getGenericReference()
		 * @generated
		 */
		EClass GENERIC_REFERENCE = eINSTANCE.getGenericReference();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.LiteralImpl <em>Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.LiteralImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getLiteral()
		 * @generated
		 */
		EClass LITERAL = eINSTANCE.getLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LITERAL__VALUE = eINSTANCE.getLiteral_Value();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LITERAL__NAME = eINSTANCE.getLiteral_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.MarkerImpl <em>Marker</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.MarkerImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getMarker()
		 * @generated
		 */
		EClass MARKER = eINSTANCE.getMarker();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MARKER__KEY = eINSTANCE.getMarker_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MARKER__VALUE = eINSTANCE.getMarker_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.ComposedContentImpl <em>Composed Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.ComposedContentImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getComposedContent()
		 * @generated
		 */
		EClass COMPOSED_CONTENT = eINSTANCE.getComposedContent();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.jsdoc.dom.impl.StructuredTextImpl <em>Structured Text</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.dom.impl.StructuredTextImpl
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getStructuredText()
		 * @generated
		 */
		EClass STRUCTURED_TEXT = eINSTANCE.getStructuredText();

		/**
		 * The meta object literal for the '<em><b>Root Element</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED_TEXT__ROOT_ELEMENT = eINSTANCE.getStructuredText_RootElement();

		/**
		 * The meta object literal for the '<em>Tag Definition</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.jsdoc.ITagDefinition
		 * @see org.eclipse.n4js.jsdoc.dom.impl.DomPackageImpl#getTagDefinition()
		 * @generated
		 */
		EDataType TAG_DEFINITION = eINSTANCE.getTagDefinition();

	}

} //DomPackage
