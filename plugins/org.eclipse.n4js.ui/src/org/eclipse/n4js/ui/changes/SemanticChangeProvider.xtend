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
package org.eclipse.n4js.ui.changes

import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.text.BadLocationException
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.ModifiableElement
import org.eclipse.n4js.n4JS.ModifierUtils
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSFeatureUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.utils.nodemodel.NodeModelAccess
import org.eclipse.n4js.validation.N4JSElementKeywordProvider
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.ui.editor.model.IXtextDocument

/**
 * Collection of high-level convenience methods for creating {@link IChange}s.
 * <p>
 * By convention, all methods take an {@link IXtextDocument} as first parameter and return an instance of IChange.
 *
 * Other than ChangeProvider the methods in this class are thought to be a tool to handle
 * AST elements directly without dealing with their textual representation.
 */
public class SemanticChangeProvider {

	/** Retrieve internal annotation constant from AnnotationDefinition */
	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;

	@Inject private NodeModelAccess nodeModelAccess;

	@Inject private N4JSElementKeywordProvider elementKeywordProvider;

	/**
	 * Return the IChange to set the access modifier for the given element.
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param modifier Modifier to set
	 */
	def IChange setAccessModifiers(IXtextDocument document, ModifiableElement element,  N4Modifier modifier)
		throws BadLocationException
	{
		setAccessModifiers(document,element,modifier,false);
	}

	/** Return the IChange to set the access modifier for the given element with optional export modifier
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param modifier Modifier to set
	 * @param export Optional export modifier
	 */
	def IChange setAccessModifier(IXtextDocument document, TypeDefiningElement element, N4Modifier modifier, boolean export)
		throws BadLocationException
	{
		if ( !element.definedType.exported && export ) {
			return setAccessModifiers(document,element as ModifiableElement,modifier,true);
		}
		if (element instanceof ModifiableElement ) {
			return setAccessModifiers(document,element as ModifiableElement,modifier,false);
		}
		return null;
	}

	/** Return the IChange to set the access modifier for the given element with optional export modifier.
	 *  No validation of export parameter. (e.g. members)
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param modifier Modifier to set
	 * @param export Optional export modifier
	 */
	private def IChange setAccessModifiers(IXtextDocument document,ModifiableElement element, N4Modifier modifier, boolean export) {

		//Copy to not modify the model
		val List<N4Modifier> modifiers = new ArrayList<N4Modifier>(element.declaredModifiers);

		val List<N4Modifier> nonAccessModifier = modifiers.filter[!ModifierUtils.isAccessModifier(it)].toList()
		nonAccessModifier.addAll(modifier);

		//Add export prefix
		var exportPrefix = "";
		if ( export ) {
			exportPrefix = N4JSLanguageConstants.EXPORT_KEYWORD;
			//Add additional spacing to the other modifiers
			if ( nonAccessModifier.length > 0 ) {
				exportPrefix += " ";
			}
		}

		return setModifiers(document,element,exportPrefix + nonAccessModifier.sortedModifierString);
	}


	/**
	 * Return the IChange to add a custom modifier to the given element.
	 *
	 * Note that all existing modifiers are maintained while the new custom modifier(s) are added at the beginning of the line.
	 * No validation of a correct modifier order takes place.
	 *
	 * @param document XtextDocument to modify
	 * @param element ModifiableElement to declare as exported
	 *
	 */
	def IChange addCustomModifier( IXtextDocument document, ModifiableElement element, String modifier) {

		//Copy to avoid modifying the model
		val modifiers = new ArrayList<N4Modifier>(element.declaredModifiers);

		//Add export prefix
		var exportPrefix = modifier
		//Add additional spacing to the other modifiers
		if ( modifiers.length > 0 ) {
			exportPrefix += " ";
		}

		return setModifiers(document,element,exportPrefix + modifiers.sortedModifierString);
	}

	/**
	 * Return the IChange to set the modifiers for the given Element. Replaces all modifiers with new ones.
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param modifier New modifiers
	 */
	def IChange setModifiers( IXtextDocument document, EObject element, String modifiers) {
		var declaredModifiers =
			if (element instanceof ModifiableElement) {
				element.declaredModifiers
			} else {
				Collections.EMPTY_LIST;
			}

		var extra_whitespace = ""
		var delete_extra_whitespace = 0;

		var startOffset = element.modifierOffset;
		var endOffset = startOffset;

		//Calculate offset, length for all modifiers together
		if ( declaredModifiers.length > 0 ) {
			val endNode = ModifierUtils.getNodeForModifier(element as ModifiableElement,declaredModifiers.length-1)
			endOffset = endNode.offset + endNode.length
		}
		else if (modifiers.length > 0) { // spacing between declaration and modifier
			extra_whitespace = " ";
		}
		if ( modifiers.length == 0 ) {
			delete_extra_whitespace = 1;
		}

		val modifierLength = endOffset - startOffset + 	delete_extra_whitespace;

		ChangeProvider.replace(document,startOffset,modifierLength,modifiers + extra_whitespace )

	}

	/**
	 * Returns IChange to set the modifiers of the element
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param modifier List of N4Modifier to set
	 */
	def IChange setModifiers(IXtextDocument document, ModifiableElement element,List<N4Modifier> modifiers)
		throws BadLocationException {

		return setModifiers(document,element,modifiers.sortedModifierString );
	}

	/**
	 * Returns IChange to add modifier to element.
	 *
	 * @param document
	 * 		The xtext document to make the changes in
	 * @param element
	 * 		The element to add the modifier to
	 * @param modifier
	 * 		The modifier to add
	 */
	def IChange addModifier( IXtextDocument document, ModifiableElement element, N4Modifier modifier)
		throws BadLocationException {

		//Copy to not modify the model
		var modifiers = new ArrayList<N4Modifier>(element.declaredModifiers);
		modifiers.add(modifier);

		return setModifiers(document, element, modifiers.sortedModifierString);
	}

	/**
	 * Returns IChange to remove modifier from element.
	 * Only removes existing modifiers
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param modifier Modifier to remove
	 */
	def IChange removeModifier(IXtextDocument document,ModifiableElement element, N4Modifier modifier)
		 	throws BadLocationException {

		val modifiers = element.declaredModifiers.filter[it.name() != modifier.name()].toList();
		return setModifiers(document,element, modifiers.sortedModifierString);

	}

	/**
	 * Returns IChange to add annotation to element. Only adds not yet existing annotations.
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param annotation Annotation to add
	 */
	def IChange addAnnotation(IXtextDocument document, AnnotableElement element, String annotation)
		throws BadLocationException {

		if ( annotation.equals(INTERNAL_ANNOTATION) ) {
			return addInternalAnnotation(document,element);
		}

		var annotations = new ArrayList<Annotation>(element.annotations);

		if ( annotations.findFirst[it.name == annotation] === null ) {
			var elementNode = NodeModelUtils.findActualNodeFor(element);
			return ChangeProvider.insertLineAbove(document,elementNode.offset,"@"+annotation, true);
		}

		return IChange.IDENTITY;
	}
	/**
	 * Returns IChange to add @Internal annotation to the element.
	 */
	 private def IChange addInternalAnnotation(IXtextDocument document, AnnotableElement element) {
	 	if ( element.annotations.findFirst[it.name == INTERNAL_ANNOTATION] === null ) {

	 		if ( element instanceof ModifiableElement ) {
	 			var offset = element.internalAnnotationOffset;
	 			return ChangeProvider.replace(document,offset,0,"@"+INTERNAL_ANNOTATION + " ");
	 		}
	 	}
	 	IChange.IDENTITY
	 }
	 /**
	  * Returns the offset to place the @Internal annotation in the same line
	  */
	 private def int internalAnnotationOffset(ModifiableElement element) {
	 	if ( !(element instanceof AnnotableElement) ) {
	 		throw new IllegalArgumentException("Can't compute @Internal offset for non-annotable element");
	 	}

	 	//If element is exported put the @Internal annotation in front of the export keyword
	 	//Otherwise place it in front of the modifiers

	 	val containerExportDeclaration = element.eContainer;
		if (containerExportDeclaration instanceof ExportDeclaration) {
			val node = nodeModelAccess.nodeForKeyword(containerExportDeclaration, N4JSLanguageConstants.EXPORT_KEYWORD);
			if ( node !== null ) {
				return node.offset;
			} else {
				throw new NullPointerException("Failed to retrieve node for export keyword");
			}
		} else {
	 		return element.modifierOffset;
	 	}
	 }

	 /**
	  * Return the ast node which holds the text represented by given keyword.
	  * May be null. (e.g. keyword does not occur in element)
	  */
	 private def INode astNodeForKeyword( EObject element, String keyword ) {
	 	nodeModelAccess.nodeForKeyword(element,keyword)
	 }

	 /**
	  * Returns the modifier offset where modifiers are placed in front of a modifiable element, even if the element does
	  * not have any modifiers.
	  *
	  * @param element modifiable element to compute the modifier offset of
	  *
	  * <p>Note:
	  * - This does not include the export modifier as it is grammar wise no modifier. (offset is always after
	  *   the export keyword for export declarations)
	  *
	  * - Offset means that there is no additional space between the offset and following element.
	  *   Therefore an additional space character has to be inserted in most of the cases
	  * </p>
	  */
	 private def int modifierOffset(EObject element) {
	 	var offset = -1;

	 	//Has modifiers
	 	if (element instanceof ModifiableElement && (element as ModifiableElement).declaredModifiers.length > 0) {
			offset = ModifierUtils.getNodeForModifier(element as ModifiableElement, 0).offset;
		} else { // Otherwise
			offset = switch (element) {
				N4GetterDeclaration:
					element.astNodeForKeyword(N4JSLanguageConstants.GET_KEYWORD).offset
				N4SetterDeclaration:
					element.astNodeForKeyword(N4JSLanguageConstants.SET_KEYWORD).offset
				FunctionDeclaration,
				N4ClassDeclaration,
				N4InterfaceDeclaration,
				N4EnumDeclaration:
					element.astNodeForKeyword(elementKeywordProvider.keyword(element)).offset
				ExportedVariableStatement: {
					val exportKeyword = element.eContainer.astNodeForKeyword(N4JSLanguageConstants.EXPORT_KEYWORD);
					exportKeyword.offset + exportKeyword.length+1
				}
				N4MethodDeclaration: {
					var List<INode> nodes = null;
					nodes = NodeModelUtils.findNodesForFeature(element, N4JSPackage.Literals.GENERIC_DECLARATION__TYPE_VARS);
					if (nodes.empty)
						nodes = NodeModelUtils.findNodesForFeature(element, N4JSPackage.Literals.TYPED_ELEMENT__BOGUS_TYPE_REF);
					if (nodes.empty)
						nodes = NodeModelUtils.findNodesForFeature(element, N4JSPackage.Literals.FUNCTION_DEFINITION__GENERATOR);
					if (nodes.empty)
						nodes = NodeModelUtils.findNodesForFeature(element, N4JSPackage.Literals.FUNCTION_DEFINITION__DECLARED_ASYNC);
					if (nodes.empty)
						nodes = NodeModelUtils.findNodesForFeature(element, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME);

					if (nodes!==null && !nodes.empty) {
						val node = nodes.get(0);
						val semElem = node.semanticElement;
						if (semElem instanceof TypeVariable) {
							node.offset - 1; // this respects '<' of a type variable, e.g. '<T> foo()'
						} else {
							node.offset;
						}
					} else {
						-1;
					}
				}
				NamedElement: {
					val attr = N4JSFeatureUtils.getElementNameFeature(element);
					val nodes = NodeModelUtils.findNodesForFeature(element, attr);
					if (nodes!==null && !nodes.empty) {
						nodes.get(0).offset;
					} else {
						-1;
					}
				}
				default: -1
			}
		}
		if ( offset == -1 ) {
			throw new IllegalArgumentException("Couldn't determine element modifier offset");
		}

		return offset
	 }

	/**
	 * Returns IChange to remove annotation from element. Only removes exisiting annotations.
	 *
	 * @param document Document to modify
	 * @param element Element to modify
	 * @param annotation Annotation to remove
	 */
	def IChange removeAnnotation(IXtextDocument document, AnnotableElement element, String annotation)
		throws BadLocationException {

		var annotatedElement = element;
		var Collection<Annotation> sourceList;
		var Annotation targetAnnotation

		//Search for given annotation in the elements annotation list as well as
		//in the containing export declaration as far as it exists.
		if (annotatedElement.annotations.findFirst[it.name.equals(annotation)] !== null) {

			sourceList = annotatedElement.annotations;
			targetAnnotation = annotatedElement.annotations.findFirst[it.name.equals(annotation)];

		} else if (element.eContainer instanceof ExportDeclaration &&
				  (element.eContainer as ExportDeclaration).annotations.findFirst[it.name.equals(annotation)] !== null) {

			sourceList = (element.eContainer as ExportDeclaration).annotations;
			targetAnnotation = (element.eContainer as ExportDeclaration).annotations.findFirst[it.name.equals(annotation)];

		} else {
			return IChange.IDENTITY;
		}

		var node = NodeModelUtils.findActualNodeFor(targetAnnotation);

		var offset = node.offset;
		var length = node.length;


		//Workaround the fact that the offset of the first annotation starts
		//after the '@' symbol. This is grammar caused. (The annotation can be first in the annotations list of the
		//export declaration or of the element itself)
		if  (targetAnnotation === sourceList.head)   {
			var containerNode = NodeModelUtils.findActualNodeFor(targetAnnotation.eContainer);
			offset = containerNode.offset;
			length = node.offset+node.length-containerNode.offset;
		}

		//If the annotation is in the same line as the following element modifiers
		//also remove the additional whitespace.
		//Also consider chained one line annotations without separator
		if (element instanceof ModifiableElement &&
			document.getLineOfOffset(offset) == document.getLineOfOffset((element as ModifiableElement).modifierOffset) ) {
			//Only delete trailing white space if there is white space in front of the annotation
			//Also check if there even is a trailing white space.
			val trailingChar = document.getChar(offset+length);
			val leadingChar = document.getChar(offset-1);
			if (Character.isWhitespace(leadingChar) &&
				Character.isWhitespace(trailingChar)
			) {
				length++;
			}
		}

		return ChangeProvider.removeText(document,offset,length,true);
	}

	/** Extension method to sort modifiers */
	public def sortedModifierString(List<N4Modifier> modifierList) {
		return ModifierUtils.getSortedModifiers(modifierList).join(" ");
	}
}
