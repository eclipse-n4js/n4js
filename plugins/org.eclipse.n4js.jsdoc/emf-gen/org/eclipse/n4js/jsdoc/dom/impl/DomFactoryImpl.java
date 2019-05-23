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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.jsdoc.ITagDefinition;

import org.eclipse.n4js.jsdoc.dom.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomFactoryImpl extends EFactoryImpl implements DomFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DomFactory init() {
		try {
			DomFactory theDomFactory = (DomFactory)EPackage.Registry.INSTANCE.getEFactory(DomPackage.eNS_URI);
			if (theDomFactory != null) {
				return theDomFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DomFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DomPackage.DOCLET: return createDoclet();
			case DomPackage.TAG_VALUE: return createTagValue();
			case DomPackage.TAG_TITLE: return createTagTitle();
			case DomPackage.LINE_TAG: return createLineTag();
			case DomPackage.INLINE_TAG: return createInlineTag();
			case DomPackage.TEXT: return createText();
			case DomPackage.SIMPLE_TYPE_REFERENCE: return createSimpleTypeReference();
			case DomPackage.FULL_TYPE_REFERENCE: return createFullTypeReference();
			case DomPackage.FULL_MEMBER_REFERENCE: return createFullMemberReference();
			case DomPackage.VARIABLE_REFERENCE: return createVariableReference();
			case DomPackage.GENERIC_REFERENCE: return createGenericReference();
			case DomPackage.LITERAL: return createLiteral();
			case DomPackage.MARKER: return createMarker();
			case DomPackage.COMPOSED_CONTENT: return createComposedContent();
			case DomPackage.STRUCTURED_TEXT: return createStructuredText();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DomPackage.TAG_DEFINITION:
				return createTagDefinitionFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DomPackage.TAG_DEFINITION:
				return convertTagDefinitionToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Doclet createDoclet() {
		DocletImpl doclet = new DocletImpl();
		return doclet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TagValue createTagValue() {
		TagValueImpl tagValue = new TagValueImpl();
		return tagValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TagTitle createTagTitle() {
		TagTitleImpl tagTitle = new TagTitleImpl();
		return tagTitle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LineTag createLineTag() {
		LineTagImpl lineTag = new LineTagImpl();
		return lineTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InlineTag createInlineTag() {
		InlineTagImpl inlineTag = new InlineTagImpl();
		return inlineTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Text createText() {
		TextImpl text = new TextImpl();
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleTypeReference createSimpleTypeReference() {
		SimpleTypeReferenceImpl simpleTypeReference = new SimpleTypeReferenceImpl();
		return simpleTypeReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FullTypeReference createFullTypeReference() {
		FullTypeReferenceImpl fullTypeReference = new FullTypeReferenceImpl();
		return fullTypeReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FullMemberReference createFullMemberReference() {
		FullMemberReferenceImpl fullMemberReference = new FullMemberReferenceImpl();
		return fullMemberReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VariableReference createVariableReference() {
		VariableReferenceImpl variableReference = new VariableReferenceImpl();
		return variableReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenericReference createGenericReference() {
		GenericReferenceImpl genericReference = new GenericReferenceImpl();
		return genericReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Literal createLiteral() {
		LiteralImpl literal = new LiteralImpl();
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Marker createMarker() {
		MarkerImpl marker = new MarkerImpl();
		return marker;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComposedContent createComposedContent() {
		ComposedContentImpl composedContent = new ComposedContentImpl();
		return composedContent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructuredText createStructuredText() {
		StructuredTextImpl structuredText = new StructuredTextImpl();
		return structuredText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ITagDefinition createTagDefinitionFromString(EDataType eDataType, String initialValue) {
		return (ITagDefinition)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTagDefinitionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomPackage getDomPackage() {
		return (DomPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DomPackage getPackage() {
		return DomPackage.eINSTANCE;
	}

} //DomFactoryImpl
