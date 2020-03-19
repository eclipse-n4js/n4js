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
package org.eclipse.n4js.ide.server.codeActions.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.ModifierUtils;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFeatureUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.utils.nodemodel.NodeModelAccess;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Objects;
import com.google.inject.Inject;

/**
 * Collection of high-level convenience methods for creating {@link TextEdit}s.
 * <p>
 * By convention, all methods take a {@link Document} as first parameter and return an instance of {@link TextEdit}.
 *
 * Other than {@link ChangeProvider}, the methods in this class are thought to be a tool to handle AST elements directly
 * without dealing with their textual representation.
 */
public class SemanticChangeProvider {

	/** Retrieve internal annotation constant from AnnotationDefinition */
	static final String INTERNAL_ANNOTATION = AnnotationDefinition.INTERNAL.name;

	@Inject
	private NodeModelAccess nodeModelAccess;

	@Inject
	private N4JSElementKeywordProvider elementKeywordProvider;

	/**
	 * Return the IChange to set the access modifier for the given element.
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param modifier
	 *            Modifier to set
	 */
	TextEdit setAccessModifiers(Document document, ModifiableElement element, N4Modifier modifier) {
		return setAccessModifiers(document, element, modifier, false);
	}

	/**
	 * Return the IChange to set the access modifier for the given element with optional export modifier
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param modifier
	 *            Modifier to set
	 * @param export
	 *            Optional export modifier
	 */
	TextEdit setAccessModifier(Document document, TypeDefiningElement element, N4Modifier modifier, boolean export) {
		if (!element.getDefinedType().isExported() && export) {
			return setAccessModifiers(document, (ModifiableElement) element, modifier, true);
		}
		if (element instanceof ModifiableElement) {
			return setAccessModifiers(document, (ModifiableElement) element, modifier, false);
		}
		return null;
	}

	/**
	 * Return the IChange to set the access modifier for the given element with optional export modifier. No validation
	 * of export parameter. (e.g. members)
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param modifier
	 *            Modifier to set
	 * @param export
	 *            Optional export modifier
	 */
	private TextEdit setAccessModifiers(Document document, ModifiableElement element, N4Modifier modifier,
			boolean export) {

		List<N4Modifier> nonAccessModifier = new ArrayList<>();
		for (N4Modifier mod : element.getDeclaredModifiers()) {
			if (!ModifierUtils.isAccessModifier(mod)) {
				nonAccessModifier.add(mod);
			}
		}

		nonAccessModifier.add(modifier);

		// Add export prefix
		var exportPrefix = "";
		if (export) {
			exportPrefix = N4JSLanguageConstants.EXPORT_KEYWORD;
			// Add additional spacing to the other modifiers
			if (nonAccessModifier.size() > 0) {
				exportPrefix += " ";
			}
		}

		return setModifiers(document, element, exportPrefix + sortedModifierString(nonAccessModifier));
	}

	/**
	 * Return the IChange to add a custom modifier to the given element.
	 *
	 * Note that all existing modifiers are maintained while the new custom modifier(s) are added at the beginning of
	 * the line. No validation of a correct modifier order takes place.
	 *
	 * @param document
	 *            XtextDocument to modify
	 * @param element
	 *            ModifiableElement to declare as exported
	 *
	 */
	TextEdit addCustomModifier(Document document, ModifiableElement element, String modifier) {
		// Copy to avoid modifying the model
		List<N4Modifier> modifiers = new ArrayList<>(element.getDeclaredModifiers());

		// Add export prefix
		String exportPrefix = modifier;
		// Add additional spacing to the other modifiers
		if (modifiers.size() > 0) {
			exportPrefix += " ";
		}

		return setModifiers(document, element, exportPrefix + sortedModifierString(modifiers));
	}

	/**
	 * Return the IChange to set the modifiers for the given Element. Replaces all modifiers with new ones.
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param modifiers
	 *            New modifiers
	 */
	TextEdit setModifiers(Document document, EObject element, String modifiers) {
		List<N4Modifier> declaredModifiers = (element instanceof ModifiableElement)
				? ((ModifiableElement) element).getDeclaredModifiers()
				: Collections.emptyList();

		String extra_whitespace = "";
		int delete_extra_whitespace = 0;
		int startOffset = modifierOffset(element);
		int endOffset = startOffset;

		// Calculate offset, length for all modifiers together
		if (declaredModifiers.size() > 0) {
			ILeafNode endNode = ModifierUtils.getNodeForModifier((ModifiableElement) element,
					declaredModifiers.size() - 1);
			endOffset = endNode.getOffset() + endNode.getLength();
		} else if (modifiers.length() > 0) { // spacing between declaration and modifier
			extra_whitespace = " ";
		}
		if (modifiers.length() == 0) {
			delete_extra_whitespace = 1;
		}

		int modifierLength = endOffset - startOffset + delete_extra_whitespace;

		return ChangeProvider.replace(document, startOffset, modifierLength, modifiers + extra_whitespace);
	}

	/**
	 * Returns IChange to set the modifiers of the element
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param modifiers
	 *            List of N4Modifier to set
	 */
	TextEdit setModifiers(Document document, ModifiableElement element, List<N4Modifier> modifiers) {
		return setModifiers(document, element, sortedModifierString(modifiers));
	}

	/**
	 * Returns IChange to add modifier to element.
	 *
	 * @param document
	 *            The xtext document to make the changes in
	 * @param element
	 *            The element to add the modifier to
	 * @param modifier
	 *            The modifier to add
	 */
	TextEdit addModifier(Document document, ModifiableElement element, N4Modifier modifier) {
		// Copy to not modify the model
		List<N4Modifier> modifiers = new ArrayList<>(element.getDeclaredModifiers());
		modifiers.add(modifier);

		return setModifiers(document, element, sortedModifierString(modifiers));
	}

	/**
	 * Returns IChange to remove modifier from element. Only removes existing modifiers
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param modifier
	 *            Modifier to remove
	 */
	TextEdit removeModifier(Document document, ModifiableElement element, N4Modifier modifier) {
		List<N4Modifier> modifiers = new ArrayList<>();
		for (N4Modifier mod : element.getDeclaredModifiers()) {
			if (Objects.equal(mod.name(), modifier.name())) {
				modifiers.add(mod);
			}
		}
		return setModifiers(document, element, sortedModifierString(modifiers));

	}

	/**
	 * Returns IChange to add annotation to element. Only adds not yet existing annotations.
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param annotation
	 *            Annotation to add
	 */
	TextEdit addAnnotation(Document document, AnnotableElement element, String annotation) {
		if (annotation.equals(INTERNAL_ANNOTATION)) {
			return addInternalAnnotation(document, element);
		}

		if (getAnnotationWithName(element, annotation) != null) {
			return null; // Annotation already exists
		}

		var elementNode = NodeModelUtils.findActualNodeFor(element);
		return ChangeProvider.insertLinesAbove(document, elementNode.getOffset(), true, "@" + annotation);
	}

	/**
	 * Returns IChange to add @Internal annotation to the element.
	 */
	private TextEdit addInternalAnnotation(Document document, AnnotableElement element) {
		if (getAnnotationWithName(element, INTERNAL_ANNOTATION) != null) {
			return null; // Annotation already exists
		}

		if (element instanceof ModifiableElement) {
			var offset = internalAnnotationOffset((ModifiableElement) element);
			return ChangeProvider.replace(document, offset, 0, "@" + INTERNAL_ANNOTATION + " ");
		}

		return null;
	}

	/**
	 * Returns the offset to place the @Internal annotation in the same line
	 */
	private int internalAnnotationOffset(ModifiableElement element) {
		if (!(element instanceof AnnotableElement)) {
			throw new IllegalArgumentException("Can't compute @Internal offset for non-annotable element");
		}

		// If element is exported put the @Internal annotation in front of the export keyword
		// Otherwise place it in front of the modifiers

		EObject containerExportDeclaration = element.eContainer();
		if (containerExportDeclaration instanceof ExportDeclaration) {
			ILeafNode node = nodeModelAccess.nodeForKeyword(containerExportDeclaration,
					N4JSLanguageConstants.EXPORT_KEYWORD);
			if (node != null) {
				return node.getOffset();
			} else {
				throw new NullPointerException("Failed to retrieve node for export keyword");
			}
		} else {
			return modifierOffset(element);
		}
	}

	private Annotation getAnnotationWithName(AnnotableElement element, String annotationName) {
		for (Annotation anno : element.getAnnotations()) {
			if (Objects.equal(anno.getName(), annotationName)) {
				return anno;
			}
		}
		return null;
	}

	/**
	 * Return the ast node which holds the text represented by given keyword. May be null. (e.g. keyword does not occur
	 * in element)
	 */
	private INode astNodeForKeyword(EObject element, String keyword) {
		return nodeModelAccess.nodeForKeyword(element, keyword);
	}

	/**
	 * Returns the modifier offset where modifiers are placed in front of a modifiable element, even if the element does
	 * not have any modifiers.
	 *
	 * @param element
	 *            modifiable element to compute the modifier offset of
	 *
	 *            <p>
	 *            Note: - This does not include the export modifier as it is grammar wise no modifier. (offset is always
	 *            after the export keyword for export declarations)
	 *
	 *            - Offset means that there is no additional space between the offset and following element. Therefore
	 *            an additional space character has to be inserted in most of the cases
	 *            </p>
	 */
	private int modifierOffset(EObject element) {
		int offset = -1;

		// Has modifiers
		if (element instanceof ModifiableElement && ((ModifiableElement) element).getDeclaredModifiers().size() > 0) {
			offset = ModifierUtils.getNodeForModifier((ModifiableElement) element, 0).getOffset();

		} else { // Otherwise

			if (element instanceof N4GetterDeclaration) {
				offset = astNodeForKeyword(element, N4JSLanguageConstants.GET_KEYWORD).getOffset();
			}

			if (element instanceof N4SetterDeclaration) {
				offset = astNodeForKeyword(element, N4JSLanguageConstants.SET_KEYWORD).getOffset();
			}

			if (element instanceof FunctionDeclaration || element instanceof N4ClassDeclaration
					|| element instanceof N4InterfaceDeclaration || element instanceof N4EnumDeclaration) {
				offset = astNodeForKeyword(element, elementKeywordProvider.keyword(element)).getOffset();
			}

			if (element instanceof ExportedVariableStatement) {
				INode exportKeyword = astNodeForKeyword(element.eContainer(), N4JSLanguageConstants.EXPORT_KEYWORD);
				offset = exportKeyword.getOffset() + exportKeyword.getLength() + 1;
			}

			if (element instanceof N4MethodDeclaration) {
				List<INode> nodes = null;
				nodes = NodeModelUtils.findNodesForFeature(element,
						N4JSPackage.Literals.GENERIC_DECLARATION__TYPE_VARS);

				if (nodes.isEmpty())
					nodes = NodeModelUtils.findNodesForFeature(element,
							N4JSPackage.Literals.TYPED_ELEMENT__BOGUS_TYPE_REF);
				if (nodes.isEmpty())
					nodes = NodeModelUtils.findNodesForFeature(element,
							N4JSPackage.Literals.FUNCTION_DEFINITION__GENERATOR);
				if (nodes.isEmpty())
					nodes = NodeModelUtils.findNodesForFeature(element,
							N4JSPackage.Literals.FUNCTION_DEFINITION__DECLARED_ASYNC);
				if (nodes.isEmpty())
					nodes = NodeModelUtils.findNodesForFeature(element,
							N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME);

				if (nodes != null && !nodes.isEmpty()) {
					INode node = nodes.get(0);
					EObject semElem = node.getSemanticElement();
					if (semElem instanceof TypeVariable) {
						offset = node.getOffset() - 1; // this respects '<' of a type variable, e.g. '<T> foo()'
					} else {
						offset = node.getOffset();
					}
				} else {
					offset = -1;
				}
			}

			if (element instanceof NamedElement) {
				EStructuralFeature attr = N4JSFeatureUtils.attributeOfNameFeature((NamedElement) element);
				List<INode> nodes = NodeModelUtils.findNodesForFeature(element, attr);
				if (nodes != null && !nodes.isEmpty()) {
					nodes.get(0).getOffset();
				} else {
					offset = -1;
				}
			}
		}
		if (offset == -1) {
			throw new IllegalArgumentException("Couldn't determine element modifier offset");
		}

		return offset;
	}

	/**
	 * Returns IChange to remove annotation from element. Only removes exisiting annotations.
	 *
	 * @param document
	 *            Document to modify
	 * @param element
	 *            Element to modify
	 * @param annotation
	 *            Annotation to remove
	 */
	TextEdit removeAnnotation(Document document, AnnotableElement element, String annotation) {
		AnnotableElement annotatedElement = element;
		Collection<Annotation> sourceList = null;
		Annotation targetAnnotation = null;

		// Search for given annotation in the elements annotation list as well as
		// in the containing export declaration as far as it exists.
		targetAnnotation = getAnnotationWithName(annotatedElement, annotation);
		if (targetAnnotation != null) {
			sourceList = annotatedElement.getAnnotations();

		} else if (element.eContainer() instanceof ExportDeclaration) {

			ExportDeclaration expDecl = (ExportDeclaration) element.eContainer();
			targetAnnotation = getAnnotationWithName(expDecl, annotation);
			if (targetAnnotation != null) {
				sourceList = expDecl.getAnnotations();
			}
		}

		if (sourceList == null || targetAnnotation == null) {
			return null;
		}

		ICompositeNode node = NodeModelUtils.findActualNodeFor(targetAnnotation);
		int offset = node.getOffset();
		int length = node.getLength();

		// Workaround the fact that the offset of the first annotation starts
		// after the '@' symbol. This is grammar caused. (The annotation can be first in the annotations list of the
		// export declaration or of the element itself)
		if (targetAnnotation == IterableExtensions.head(sourceList)) {
			var containerNode = NodeModelUtils.findActualNodeFor(targetAnnotation.eContainer());
			offset = containerNode.getOffset();
			length = node.getOffset() + node.getLength() - containerNode.getOffset();
		}

		// If the annotation is in the same line as the following element modifiers
		// also remove the additional whitespace.
		// Also consider chained one line annotations without separator
		if (element instanceof ModifiableElement) {
			ModifiableElement mElement = (ModifiableElement) element;
			Position offsetPosition = document.getPosition(offset);
			Position mOffsetPosition = document.getPosition(modifierOffset(mElement));

			if (offsetPosition.getLine() == mOffsetPosition.getLine()) {
				// Only delete trailing white space if there is white space in front of the annotation
				// Also check if there even is a trailing white space.

				String contents = document.getContents();
				char trailingChar = contents.charAt(offset + length);
				char leadingChar = contents.charAt(offset - 1);
				if (Character.isWhitespace(leadingChar) && Character.isWhitespace(trailingChar)) {
					length++;
				}
			}
		}

		return ChangeProvider.removeText(document, offset, length, true);
	}

	/** Extension method to sort modifiers */
	public String sortedModifierString(List<N4Modifier> modifierList) {
		return IterableExtensions.join(ModifierUtils.getSortedModifiers(modifierList), " ");
	}
}
