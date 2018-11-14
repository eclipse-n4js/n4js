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
package org.eclipse.n4js.jsdoc.dom.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.jsdoc.ITagDefinition;

import org.eclipse.n4js.jsdoc.dom.ComposedContent;
import org.eclipse.n4js.jsdoc.dom.Composite;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.DocletElement;
import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.n4js.jsdoc.dom.DomPackage;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc.dom.FullTypeReference;
import org.eclipse.n4js.jsdoc.dom.GenericReference;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.JSDocNode;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.Literal;
import org.eclipse.n4js.jsdoc.dom.Marker;
import org.eclipse.n4js.jsdoc.dom.SimpleTypeReference;
import org.eclipse.n4js.jsdoc.dom.StructuredText;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.dom.VariableReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomPackageImpl extends EPackageImpl implements DomPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass docletEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass docletElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass jsDocNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contentNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tagEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tagValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tagTitleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lineTagEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inlineTagEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleTypeReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fullTypeReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fullMemberReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass markerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass composedContentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredTextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType tagDefinitionEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.n4js.jsdoc.dom.DomPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DomPackageImpl() {
		super(eNS_URI, DomFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link DomPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DomPackage init() {
		if (isInited) return (DomPackage)EPackage.Registry.INSTANCE.getEPackage(DomPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDomPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DomPackageImpl theDomPackage = registeredDomPackage instanceof DomPackageImpl ? (DomPackageImpl)registeredDomPackage : new DomPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDomPackage.createPackageContents();

		// Initialize created meta-data
		theDomPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDomPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DomPackage.eNS_URI, theDomPackage);
		return theDomPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDoclet() {
		return docletEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDoclet_LineTags() {
		return (EReference)docletEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getDoclet__HasLineTag__String() {
		return docletEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getDoclet__LineTags__String() {
		return docletEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocletElement() {
		return docletElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocletElement_Begin() {
		return (EAttribute)docletElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocletElement_End() {
		return (EAttribute)docletElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getDocletElement__SetRange__int_int() {
		return docletElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getDocletElement__Covers__int() {
		return docletElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComposite() {
		return compositeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComposite_Contents() {
		return (EReference)compositeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJSDocNode() {
		return jsDocNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJSDocNode_Markers() {
		return (EReference)jsDocNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getJSDocNode__GetMarkerValue__String() {
		return jsDocNodeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getJSDocNode__SetMarker__String_String() {
		return jsDocNodeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getJSDocNode__IsMarkedAs__String_String() {
		return jsDocNodeEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getJSDocNode__ToString() {
		return jsDocNodeEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContentNode() {
		return contentNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContentNode_Owner() {
		return (EReference)contentNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTag() {
		return tagEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTag_Title() {
		return (EReference)tagEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTag_Values() {
		return (EReference)tagEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTag_TagDefinition() {
		return (EAttribute)tagEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTag__GetValueByKey__String() {
		return tagEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTag__ToString() {
		return tagEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTagValue() {
		return tagValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTagValue_Key() {
		return (EAttribute)tagValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTagTitle() {
		return tagTitleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTagTitle_Tag() {
		return (EReference)tagTitleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTagTitle_Title() {
		return (EAttribute)tagTitleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTagTitle_ActualTitle() {
		return (EAttribute)tagTitleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLineTag() {
		return lineTagEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLineTag_Doclet() {
		return (EReference)lineTagEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInlineTag() {
		return inlineTagEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getText() {
		return textEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getText_Text() {
		return (EAttribute)textEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleTypeReference() {
		return simpleTypeReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTypeReference_TypeName() {
		return (EAttribute)simpleTypeReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleTypeReference__TypeNameSet() {
		return simpleTypeReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSimpleTypeReference__ToString() {
		return simpleTypeReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFullTypeReference() {
		return fullTypeReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFullTypeReference_ModuleName() {
		return (EAttribute)fullTypeReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFullTypeReference__ModuleNameSet() {
		return fullTypeReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFullTypeReference__ToString() {
		return fullTypeReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFullTypeReference__FullTypeName() {
		return fullTypeReferenceEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFullMemberReference() {
		return fullMemberReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFullMemberReference_MemberName() {
		return (EAttribute)fullMemberReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFullMemberReference_StaticMember() {
		return (EAttribute)fullMemberReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFullMemberReference__MemberNameSet() {
		return fullMemberReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getFullMemberReference__ToString() {
		return fullMemberReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableReference() {
		return variableReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariableReference_VariableName() {
		return (EAttribute)variableReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericReference() {
		return genericReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLiteral() {
		return literalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLiteral_Value() {
		return (EAttribute)literalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLiteral_Name() {
		return (EAttribute)literalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMarker() {
		return markerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMarker_Key() {
		return (EAttribute)markerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMarker_Value() {
		return (EAttribute)markerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComposedContent() {
		return composedContentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructuredText() {
		return structuredTextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStructuredText_RootElement() {
		return (EReference)structuredTextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getTagDefinition() {
		return tagDefinitionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomFactory getDomFactory() {
		return (DomFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		docletEClass = createEClass(DOCLET);
		createEReference(docletEClass, DOCLET__LINE_TAGS);
		createEOperation(docletEClass, DOCLET___HAS_LINE_TAG__STRING);
		createEOperation(docletEClass, DOCLET___LINE_TAGS__STRING);

		docletElementEClass = createEClass(DOCLET_ELEMENT);
		createEAttribute(docletElementEClass, DOCLET_ELEMENT__BEGIN);
		createEAttribute(docletElementEClass, DOCLET_ELEMENT__END);
		createEOperation(docletElementEClass, DOCLET_ELEMENT___SET_RANGE__INT_INT);
		createEOperation(docletElementEClass, DOCLET_ELEMENT___COVERS__INT);

		compositeEClass = createEClass(COMPOSITE);
		createEReference(compositeEClass, COMPOSITE__CONTENTS);

		jsDocNodeEClass = createEClass(JS_DOC_NODE);
		createEReference(jsDocNodeEClass, JS_DOC_NODE__MARKERS);
		createEOperation(jsDocNodeEClass, JS_DOC_NODE___GET_MARKER_VALUE__STRING);
		createEOperation(jsDocNodeEClass, JS_DOC_NODE___SET_MARKER__STRING_STRING);
		createEOperation(jsDocNodeEClass, JS_DOC_NODE___IS_MARKED_AS__STRING_STRING);
		createEOperation(jsDocNodeEClass, JS_DOC_NODE___TO_STRING);

		contentNodeEClass = createEClass(CONTENT_NODE);
		createEReference(contentNodeEClass, CONTENT_NODE__OWNER);

		tagEClass = createEClass(TAG);
		createEReference(tagEClass, TAG__TITLE);
		createEReference(tagEClass, TAG__VALUES);
		createEAttribute(tagEClass, TAG__TAG_DEFINITION);
		createEOperation(tagEClass, TAG___GET_VALUE_BY_KEY__STRING);
		createEOperation(tagEClass, TAG___TO_STRING);

		tagValueEClass = createEClass(TAG_VALUE);
		createEAttribute(tagValueEClass, TAG_VALUE__KEY);

		tagTitleEClass = createEClass(TAG_TITLE);
		createEReference(tagTitleEClass, TAG_TITLE__TAG);
		createEAttribute(tagTitleEClass, TAG_TITLE__TITLE);
		createEAttribute(tagTitleEClass, TAG_TITLE__ACTUAL_TITLE);

		lineTagEClass = createEClass(LINE_TAG);
		createEReference(lineTagEClass, LINE_TAG__DOCLET);

		inlineTagEClass = createEClass(INLINE_TAG);

		textEClass = createEClass(TEXT);
		createEAttribute(textEClass, TEXT__TEXT);

		simpleTypeReferenceEClass = createEClass(SIMPLE_TYPE_REFERENCE);
		createEAttribute(simpleTypeReferenceEClass, SIMPLE_TYPE_REFERENCE__TYPE_NAME);
		createEOperation(simpleTypeReferenceEClass, SIMPLE_TYPE_REFERENCE___TYPE_NAME_SET);
		createEOperation(simpleTypeReferenceEClass, SIMPLE_TYPE_REFERENCE___TO_STRING);

		fullTypeReferenceEClass = createEClass(FULL_TYPE_REFERENCE);
		createEAttribute(fullTypeReferenceEClass, FULL_TYPE_REFERENCE__MODULE_NAME);
		createEOperation(fullTypeReferenceEClass, FULL_TYPE_REFERENCE___MODULE_NAME_SET);
		createEOperation(fullTypeReferenceEClass, FULL_TYPE_REFERENCE___TO_STRING);
		createEOperation(fullTypeReferenceEClass, FULL_TYPE_REFERENCE___FULL_TYPE_NAME);

		fullMemberReferenceEClass = createEClass(FULL_MEMBER_REFERENCE);
		createEAttribute(fullMemberReferenceEClass, FULL_MEMBER_REFERENCE__MEMBER_NAME);
		createEAttribute(fullMemberReferenceEClass, FULL_MEMBER_REFERENCE__STATIC_MEMBER);
		createEOperation(fullMemberReferenceEClass, FULL_MEMBER_REFERENCE___MEMBER_NAME_SET);
		createEOperation(fullMemberReferenceEClass, FULL_MEMBER_REFERENCE___TO_STRING);

		variableReferenceEClass = createEClass(VARIABLE_REFERENCE);
		createEAttribute(variableReferenceEClass, VARIABLE_REFERENCE__VARIABLE_NAME);

		genericReferenceEClass = createEClass(GENERIC_REFERENCE);

		literalEClass = createEClass(LITERAL);
		createEAttribute(literalEClass, LITERAL__VALUE);
		createEAttribute(literalEClass, LITERAL__NAME);

		markerEClass = createEClass(MARKER);
		createEAttribute(markerEClass, MARKER__KEY);
		createEAttribute(markerEClass, MARKER__VALUE);

		composedContentEClass = createEClass(COMPOSED_CONTENT);

		structuredTextEClass = createEClass(STRUCTURED_TEXT);
		createEReference(structuredTextEClass, STRUCTURED_TEXT__ROOT_ELEMENT);

		// Create data types
		tagDefinitionEDataType = createEDataType(TAG_DEFINITION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		docletEClass.getESuperTypes().add(this.getComposite());
		compositeEClass.getESuperTypes().add(this.getJSDocNode());
		jsDocNodeEClass.getESuperTypes().add(this.getDocletElement());
		contentNodeEClass.getESuperTypes().add(this.getJSDocNode());
		tagEClass.getESuperTypes().add(this.getDocletElement());
		tagValueEClass.getESuperTypes().add(this.getComposite());
		tagTitleEClass.getESuperTypes().add(this.getJSDocNode());
		lineTagEClass.getESuperTypes().add(this.getTag());
		inlineTagEClass.getESuperTypes().add(this.getContentNode());
		inlineTagEClass.getESuperTypes().add(this.getTag());
		textEClass.getESuperTypes().add(this.getContentNode());
		simpleTypeReferenceEClass.getESuperTypes().add(this.getJSDocNode());
		simpleTypeReferenceEClass.getESuperTypes().add(this.getContentNode());
		fullTypeReferenceEClass.getESuperTypes().add(this.getSimpleTypeReference());
		fullMemberReferenceEClass.getESuperTypes().add(this.getFullTypeReference());
		variableReferenceEClass.getESuperTypes().add(this.getContentNode());
		literalEClass.getESuperTypes().add(this.getContentNode());
		composedContentEClass.getESuperTypes().add(this.getComposite());
		composedContentEClass.getESuperTypes().add(this.getContentNode());
		structuredTextEClass.getESuperTypes().add(this.getText());

		// Initialize classes, features, and operations; add parameters
		initEClass(docletEClass, Doclet.class, "Doclet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDoclet_LineTags(), this.getLineTag(), this.getLineTag_Doclet(), "lineTags", null, 0, -1, Doclet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getDoclet__HasLineTag__String(), theEcorePackage.getEBoolean(), "hasLineTag", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "title", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getDoclet__LineTags__String(), this.getLineTag(), "lineTags", 0, -1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "title", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(docletElementEClass, DocletElement.class, "DocletElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocletElement_Begin(), theEcorePackage.getEInt(), "begin", null, 0, 1, DocletElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocletElement_End(), theEcorePackage.getEInt(), "end", null, 0, 1, DocletElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getDocletElement__SetRange__int_int(), null, "setRange", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "begin", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "end", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getDocletElement__Covers__int(), theEcorePackage.getEBoolean(), "covers", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEInt(), "offset", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(compositeEClass, Composite.class, "Composite", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComposite_Contents(), this.getContentNode(), this.getContentNode_Owner(), "contents", null, 0, -1, Composite.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(jsDocNodeEClass, JSDocNode.class, "JSDocNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJSDocNode_Markers(), this.getMarker(), null, "markers", null, 0, -1, JSDocNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getJSDocNode__GetMarkerValue__String(), theEcorePackage.getEString(), "getMarkerValue", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "theKey", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getJSDocNode__SetMarker__String_String(), null, "setMarker", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "theKey", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "value", 0, 1, !IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getJSDocNode__IsMarkedAs__String_String(), theEcorePackage.getEBoolean(), "isMarkedAs", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "theKey", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "theValue", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getJSDocNode__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(contentNodeEClass, ContentNode.class, "ContentNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContentNode_Owner(), this.getComposite(), this.getComposite_Contents(), "owner", null, 0, 1, ContentNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tagEClass, Tag.class, "Tag", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTag_Title(), this.getTagTitle(), this.getTagTitle_Tag(), "title", null, 0, 1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTag_Values(), this.getTagValue(), null, "values", null, 0, -1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTag_TagDefinition(), this.getTagDefinition(), "tagDefinition", null, 0, 1, Tag.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getTag__GetValueByKey__String(), this.getTagValue(), "getValueByKey", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "theKey", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getTag__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(tagValueEClass, TagValue.class, "TagValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTagValue_Key(), theEcorePackage.getEString(), "key", null, 0, 1, TagValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tagTitleEClass, TagTitle.class, "TagTitle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTagTitle_Tag(), this.getTag(), this.getTag_Title(), "tag", null, 0, 1, TagTitle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTagTitle_Title(), theEcorePackage.getEString(), "title", null, 0, 1, TagTitle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTagTitle_ActualTitle(), theEcorePackage.getEString(), "actualTitle", null, 0, 1, TagTitle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lineTagEClass, LineTag.class, "LineTag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLineTag_Doclet(), this.getDoclet(), this.getDoclet_LineTags(), "doclet", null, 0, 1, LineTag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inlineTagEClass, InlineTag.class, "InlineTag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(textEClass, Text.class, "Text", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getText_Text(), theEcorePackage.getEString(), "text", null, 0, 1, Text.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simpleTypeReferenceEClass, SimpleTypeReference.class, "SimpleTypeReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleTypeReference_TypeName(), theEcorePackage.getEString(), "typeName", null, 0, 1, SimpleTypeReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getSimpleTypeReference__TypeNameSet(), theEcorePackage.getEBoolean(), "typeNameSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSimpleTypeReference__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fullTypeReferenceEClass, FullTypeReference.class, "FullTypeReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFullTypeReference_ModuleName(), theEcorePackage.getEString(), "moduleName", null, 0, 1, FullTypeReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFullTypeReference__ModuleNameSet(), theEcorePackage.getEBoolean(), "moduleNameSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFullTypeReference__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFullTypeReference__FullTypeName(), theEcorePackage.getEString(), "fullTypeName", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(fullMemberReferenceEClass, FullMemberReference.class, "FullMemberReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFullMemberReference_MemberName(), theEcorePackage.getEString(), "memberName", null, 0, 1, FullMemberReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFullMemberReference_StaticMember(), theEcorePackage.getEBoolean(), "staticMember", null, 0, 1, FullMemberReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getFullMemberReference__MemberNameSet(), theEcorePackage.getEBoolean(), "memberNameSet", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getFullMemberReference__ToString(), theEcorePackage.getEString(), "toString", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(variableReferenceEClass, VariableReference.class, "VariableReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariableReference_VariableName(), theEcorePackage.getEString(), "variableName", null, 0, 1, VariableReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericReferenceEClass, GenericReference.class, "GenericReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(literalEClass, Literal.class, "Literal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLiteral_Value(), theEcorePackage.getEString(), "value", null, 0, 1, Literal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLiteral_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Literal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(markerEClass, Marker.class, "Marker", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMarker_Key(), theEcorePackage.getEString(), "key", null, 0, 1, Marker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMarker_Value(), theEcorePackage.getEString(), "value", null, 0, 1, Marker.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(composedContentEClass, ComposedContent.class, "ComposedContent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(structuredTextEClass, StructuredText.class, "StructuredText", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStructuredText_RootElement(), theEcorePackage.getEObject(), null, "rootElement", null, 0, 1, StructuredText.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(tagDefinitionEDataType, ITagDefinition.class, "TagDefinition", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //DomPackageImpl
