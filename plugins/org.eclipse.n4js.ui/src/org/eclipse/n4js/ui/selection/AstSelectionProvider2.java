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
package org.eclipse.n4js.ui.selection;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static java.lang.Character.isWhitespace;
import static java.util.regex.Pattern.compile;
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.findActualSemanticObjectFor;
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.findLeafNodeAtOffset;

import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.selection.AstSelectionProvider;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;

/**
 * Extended AST selection provider with modified access visibility. This class was introduced to get the selected AST
 * model element from a resource using a {@link ITextRegion text region} instead of a simple offset.
 */
@SuppressWarnings("restriction")
public class AstSelectionProvider2 extends AstSelectionProvider {

	private static final Pattern VALID_IDENTIFIER_CHARS_PATTERN = compile("[a-zA-Z0-9_]");

	@Inject
	private EObjectAtOffsetHelper offsetHelper;

	/**
	 * Tries to retrieve the selected AST element from the resource based on the current selection argument. <br>
	 * Clients should be aware of the followings:
	 * <p>
	 * <ul>
	 * <li>The {@code currentSelection} will be modified. All leading and trailing whitespace will be truncated and the
	 * used selection will be based on these modification.</li>
	 * <li>This method delegates into {@link AstSelectionProvider#getSelectedAstElements(XtextResource, ITextRegion)}
	 * and returns with the fist AST element.</li>
	 * <li>If the previous approach did not find a corresponding AST element, then the leaf AST node will be looked up
	 * by the modified offset. If the leaf node cannot be found or the node cannot be resolved as a semantic object then
	 * this method will return with {@code null}. If the leaf node exists and a corresponding semantic object was found,
	 * then this method calculates the {@link ITextRegion region} of the semantic object. For the sake of consistency
	 * this method seeks the rest of the selection from the end-offset of the semantic object region and the trimmed
	 * selection based on the passed in argument. If any other object than the already found semantic object can be
	 * resolved from the rest of the selection this method will return with {@code null}.
	 * </ul>
	 * </p>
	 *
	 * @param doc
	 *            document to resolve the AST element from.
	 * @param currentSelection
	 *            the current selection.
	 * @return the AST element based on the current selection or {@code null} if the AST element cannot be resolved.
	 */
	public EObject getSelectedAstElement(final IXtextDocument doc, final ITextRegion currentSelection) {

		return doc.modify(resource -> {

			final String selectedText = doc.get(currentSelection.getOffset(), currentSelection.getLength());
			int offset = currentSelection.getOffset();
			int length = currentSelection.getLength();

			if (!isNullOrEmpty(selectedText)) {

				boolean containsNonWhiteSpace = false; // Initial pessimistic.
				for (int i = 0; i < selectedText.length(); i++) {
					if (containsNonWhiteSpace) {
						break;
					}
					containsNonWhiteSpace |= !isWhitespace(selectedText.charAt(i));
				}

				if (!containsNonWhiteSpace) {
					return null;
				}

				// Trim leading white space modify increase offset and shrink length
				for (int i = 0; i < selectedText.length(); i++) {
					if (isWhitespace(selectedText.charAt(i))) {
						offset++;
						length--;
					} else {
						break;
					}
				}

				// Trim trailing whitespace and reduce offset
				for (int i = (selectedText.length() - 1); i >= 0; i--) {
					if (isWhitespace(selectedText.charAt(i))) {
						length--;
					} else {
						break;
					}
				}
			}

			final ITextRegion trimmedRegion = new TextRegion(offset, length);
			final String trimmedText = doc.get(offset, length);
			// Ignore 0-length empty selection if surrounded by whitespace but accept if surrounded by valid identifier
			// characters such as strings, numbers and underscore from either left or right.
			if (isNullOrEmpty(trimmedText)) {
				final int leadingCharOffset = 0 == offset ? 0 : offset - 1;
				final int trailingCharOffset = leadingCharOffset == doc.getLength() ? doc.getLength()
						: leadingCharOffset + 1;
				final String leadingChar = doc.get(leadingCharOffset, 1);
				final String trailingChar = doc.get(trailingCharOffset, 1);
				if (!isValidIdentifierChar(leadingChar) && !isValidIdentifierChar(trailingChar)) {
					return null;
				}
			}

			final Pair<EObject, EObject> selectedAstElements = getSelectedAstElements(resource, trimmedRegion);
			if (null != selectedAstElements) {
				return selectedAstElements.getFirst();
			}

			final IParseResult parseResult = resource.getParseResult();
			if (null == parseResult) {
				return null;
			}

			final ILeafNode node = findLeafNodeAtOffset(parseResult.getRootNode(), trimmedRegion.getOffset());
			if (null == node) {
				return null;
			}

			final EObject objectAtOffset = findActualSemanticObjectFor(node);
			if (null == objectAtOffset) {
				return null;
			}

			final ITextRegion actualTextRegion = getTextRegion(objectAtOffset);
			for (int i = actualTextRegion.getOffset() + 1; i < getEndOffset(trimmedRegion); i++) {
				final EObject followingObject = offsetHelper.resolveElementAt(resource, i);
				if (null == followingObject || objectAtOffset != followingObject) {
					EObject typeOfObjectAtOffset = N4JSASTUtils.getCorrespondingTypeModelElement(objectAtOffset);
					if (typeOfObjectAtOffset != followingObject) {
						return null;
					}
				}
			}

			return objectAtOffset;

		});
	}

	private boolean isValidIdentifierChar(final String leadingChar) {
		return VALID_IDENTIFIER_CHARS_PATTERN.matcher(nullToEmpty(leadingChar)).matches();
	}
}
