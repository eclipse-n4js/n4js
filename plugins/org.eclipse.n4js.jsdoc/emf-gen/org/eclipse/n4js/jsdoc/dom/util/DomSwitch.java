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
package org.eclipse.n4js.jsdoc.dom.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.n4js.jsdoc.dom.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.jsdoc.dom.DomPackage
 * @generated
 */
public class DomSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DomPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomSwitch() {
		if (modelPackage == null) {
			modelPackage = DomPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DomPackage.DOCLET: {
				Doclet doclet = (Doclet)theEObject;
				T result = caseDoclet(doclet);
				if (result == null) result = caseComposite(doclet);
				if (result == null) result = caseJSDocNode(doclet);
				if (result == null) result = caseDocletElement(doclet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.DOCLET_ELEMENT: {
				DocletElement docletElement = (DocletElement)theEObject;
				T result = caseDocletElement(docletElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.COMPOSITE: {
				Composite composite = (Composite)theEObject;
				T result = caseComposite(composite);
				if (result == null) result = caseJSDocNode(composite);
				if (result == null) result = caseDocletElement(composite);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.JS_DOC_NODE: {
				JSDocNode jsDocNode = (JSDocNode)theEObject;
				T result = caseJSDocNode(jsDocNode);
				if (result == null) result = caseDocletElement(jsDocNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.CONTENT_NODE: {
				ContentNode contentNode = (ContentNode)theEObject;
				T result = caseContentNode(contentNode);
				if (result == null) result = caseJSDocNode(contentNode);
				if (result == null) result = caseDocletElement(contentNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.TAG: {
				Tag tag = (Tag)theEObject;
				T result = caseTag(tag);
				if (result == null) result = caseDocletElement(tag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.TAG_VALUE: {
				TagValue tagValue = (TagValue)theEObject;
				T result = caseTagValue(tagValue);
				if (result == null) result = caseComposite(tagValue);
				if (result == null) result = caseJSDocNode(tagValue);
				if (result == null) result = caseDocletElement(tagValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.TAG_TITLE: {
				TagTitle tagTitle = (TagTitle)theEObject;
				T result = caseTagTitle(tagTitle);
				if (result == null) result = caseJSDocNode(tagTitle);
				if (result == null) result = caseDocletElement(tagTitle);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.LINE_TAG: {
				LineTag lineTag = (LineTag)theEObject;
				T result = caseLineTag(lineTag);
				if (result == null) result = caseTag(lineTag);
				if (result == null) result = caseDocletElement(lineTag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.INLINE_TAG: {
				InlineTag inlineTag = (InlineTag)theEObject;
				T result = caseInlineTag(inlineTag);
				if (result == null) result = caseContentNode(inlineTag);
				if (result == null) result = caseTag(inlineTag);
				if (result == null) result = caseJSDocNode(inlineTag);
				if (result == null) result = caseDocletElement(inlineTag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.TEXT: {
				Text text = (Text)theEObject;
				T result = caseText(text);
				if (result == null) result = caseContentNode(text);
				if (result == null) result = caseJSDocNode(text);
				if (result == null) result = caseDocletElement(text);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.SIMPLE_TYPE_REFERENCE: {
				SimpleTypeReference simpleTypeReference = (SimpleTypeReference)theEObject;
				T result = caseSimpleTypeReference(simpleTypeReference);
				if (result == null) result = caseContentNode(simpleTypeReference);
				if (result == null) result = caseJSDocNode(simpleTypeReference);
				if (result == null) result = caseDocletElement(simpleTypeReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.FULL_TYPE_REFERENCE: {
				FullTypeReference fullTypeReference = (FullTypeReference)theEObject;
				T result = caseFullTypeReference(fullTypeReference);
				if (result == null) result = caseSimpleTypeReference(fullTypeReference);
				if (result == null) result = caseContentNode(fullTypeReference);
				if (result == null) result = caseJSDocNode(fullTypeReference);
				if (result == null) result = caseDocletElement(fullTypeReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.FULL_MEMBER_REFERENCE: {
				FullMemberReference fullMemberReference = (FullMemberReference)theEObject;
				T result = caseFullMemberReference(fullMemberReference);
				if (result == null) result = caseFullTypeReference(fullMemberReference);
				if (result == null) result = caseSimpleTypeReference(fullMemberReference);
				if (result == null) result = caseContentNode(fullMemberReference);
				if (result == null) result = caseJSDocNode(fullMemberReference);
				if (result == null) result = caseDocletElement(fullMemberReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.VARIABLE_REFERENCE: {
				VariableReference variableReference = (VariableReference)theEObject;
				T result = caseVariableReference(variableReference);
				if (result == null) result = caseContentNode(variableReference);
				if (result == null) result = caseJSDocNode(variableReference);
				if (result == null) result = caseDocletElement(variableReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.GENERIC_REFERENCE: {
				GenericReference genericReference = (GenericReference)theEObject;
				T result = caseGenericReference(genericReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.LITERAL: {
				Literal literal = (Literal)theEObject;
				T result = caseLiteral(literal);
				if (result == null) result = caseContentNode(literal);
				if (result == null) result = caseJSDocNode(literal);
				if (result == null) result = caseDocletElement(literal);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.MARKER: {
				Marker marker = (Marker)theEObject;
				T result = caseMarker(marker);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.COMPOSED_CONTENT: {
				ComposedContent composedContent = (ComposedContent)theEObject;
				T result = caseComposedContent(composedContent);
				if (result == null) result = caseComposite(composedContent);
				if (result == null) result = caseContentNode(composedContent);
				if (result == null) result = caseJSDocNode(composedContent);
				if (result == null) result = caseDocletElement(composedContent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DomPackage.STRUCTURED_TEXT: {
				StructuredText structuredText = (StructuredText)theEObject;
				T result = caseStructuredText(structuredText);
				if (result == null) result = caseText(structuredText);
				if (result == null) result = caseContentNode(structuredText);
				if (result == null) result = caseJSDocNode(structuredText);
				if (result == null) result = caseDocletElement(structuredText);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Doclet</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Doclet</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoclet(Doclet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Doclet Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Doclet Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocletElement(DocletElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composite</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composite</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComposite(Composite object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>JS Doc Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>JS Doc Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJSDocNode(JSDocNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Content Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Content Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContentNode(ContentNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tag</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTag(Tag object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tag Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTagValue(TagValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tag Title</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tag Title</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTagTitle(TagTitle object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Line Tag</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Line Tag</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLineTag(LineTag object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inline Tag</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inline Tag</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInlineTag(InlineTag object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Text</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseText(Text object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Simple Type Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Simple Type Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSimpleTypeReference(SimpleTypeReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Full Type Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Full Type Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFullTypeReference(FullTypeReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Full Member Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Full Member Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFullMemberReference(FullMemberReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableReference(VariableReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generic Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generic Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGenericReference(GenericReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteral(Literal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Marker</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Marker</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMarker(Marker object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Composed Content</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Composed Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComposedContent(ComposedContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structured Text</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structured Text</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructuredText(StructuredText object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DomSwitch
