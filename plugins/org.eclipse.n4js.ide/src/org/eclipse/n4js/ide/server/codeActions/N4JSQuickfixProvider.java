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
package org.eclipse.n4js.ide.server.codeActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportUtil;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportsAwareReferenceProposalCreator;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * N4JS quick fixes.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#quickfixes
 */
@SuppressWarnings("restriction")
@Singleton
public class N4JSQuickfixProvider {

	@Inject
	private ImportUtil importUtil;

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	/**
	 * Resolves missing import statements by re-using content assist and {@link ImportsAwareReferenceProposalCreator}
	 */
	@Fix(value = Diagnostic.LINKING_DIAGNOSTIC, multiFix = false)
	public void addImportForUnresolvedReference(QuickfixContext context, ICodeActionAcceptor acceptor) {
		Document doc = context.options.getDocument();
		Set<ContentAssistEntry> caEntries = importUtil.findImportCandidates(context.options);

		for (ContentAssistEntry cae : caEntries) {
			ArrayList<ReplaceRegion> replacements = cae.getTextReplacements();
			if (replacements != null && !replacements.isEmpty()) {
				String description = cae.getDescription();

				Supplier<List<TextEdit>> textEdits = () -> {
					List<TextEdit> result = new ArrayList<>();
					for (ReplaceRegion replaceRegion : replacements) {
						TextEdit textEdit = replace(doc, replaceRegion);
						result.add(textEdit);
					}
					return result;
				};

				acceptor.acceptQuickfixCodeAction(context, "Add import from module " + description, textEdits);
			}
		}
	}

	/**
	 * If a type declaration has Java-form such as 'int foo() {}', then the following quickfix proposed which transforms
	 * it to colon style: 'foo(): int {}' It searches the bogus return type (which is 'int' in the example above)
	 * beginning from the parent node (N4MethodDeclarationImpl) of the method declaration. The bogus return type removed
	 * from the old position. A colon followed by the bogus return type added to the right side of the method name.
	 */
	@Fix(IssueCodes.TYS_INVALID_TYPE_SYNTAX)
	public void transformJavaTypeAnnotationToColonStyle(QuickfixContext context, ICodeActionAcceptor acceptor) {
		Document doc = context.options.getDocument();
		XtextResource resource = context.options.getResource();
		EObject element = getEObject(doc, resource, context.options.getCodeActionParams().getRange());
		if (!(element instanceof ParameterizedTypeRef)) {
			return;
		}
		ParameterizedTypeRef typeRef = (ParameterizedTypeRef) element;
		if (!(typeRef.eContainer() instanceof N4MethodDeclaration)) {
			return;
		}
		ICompositeNode node = NodeModelUtils.getNode(element);
		ICompositeNode parentNode = findParentNode(node, N4MethodDeclaration.class);
		INode roundBracketNode = NodeModelUtilsN4.findKeywordNode(parentNode, ")");
		List<INode> nodesForFeature = NodeModelUtils.findNodesForFeature(parentNode.getSemanticElement(),
				N4JSPackage.Literals.TYPED_ELEMENT__BOGUS_TYPE_REF);
		INode bogusNode;
		if (!nodesForFeature.isEmpty()) {
			bogusNode = nodesForFeature.get(0);
		} else {
			return;
		}

		Supplier<List<TextEdit>> supplier = () -> {
			String stringOfBogusType = NodeModelUtilsN4.getTokenTextWithHiddenTokens(bogusNode);
			ILeafNode nodeAfterBogus = NodeModelUtils.findLeafNodeAtOffset(parentNode, bogusNode.getEndOffset());
			int spaceAfterBogusLength = 0;
			if (nodeAfterBogus != null && nodeAfterBogus.getText().startsWith(" ")) {
				spaceAfterBogusLength = 1;
			}
			int offsetBogusType = bogusNode.getOffset();
			int bogusTypeLength = stringOfBogusType.length();
			int offsetRoundBracket = roundBracketNode.getTotalOffset();
			List<TextEdit> textEdits = new ArrayList<>();
			// inserts the bogus type at the new location (behind the closing round bracket)
			textEdits.add(replace(doc, offsetRoundBracket + 1, 0, ": " + stringOfBogusType));
			// removes the bogus type and whitespace at the old location
			textEdits.add(replace(doc, offsetBogusType, bogusTypeLength + spaceAfterBogusLength, ""));
			return textEdits;
		};
		acceptor.acceptQuickfixCodeAction(context, "Convert to colon style", supplier);
	}

	private ICompositeNode findParentNode(ICompositeNode node, Class<?> semanticElementType) {
		ICompositeNode parentNode = node.getParent();
		while (!parentNode.hasDirectSemanticElement()
				|| !semanticElementType.isInstance(parentNode.getSemanticElement())) {
			parentNode = parentNode.getParent();
		}
		return parentNode;
	}

	private EObject getEObject(Document doc, XtextResource resource, Range range) {
		Position start = range.getStart();
		int startOffset = doc.getOffSet(start);
		return eObjectAtOffsetHelper.resolveContainedElementAt(resource, startOffset);
	}

	private TextEdit replace(Document doc, ReplaceRegion replaceRegion) {
		return replace(doc, replaceRegion.getOffset(), replaceRegion.getLength(), replaceRegion.getText());
	}

	private TextEdit replace(Document doc, int offset, int length, String newText) {
		Position posStart = doc.getPosition(offset);
		Position posEnd = doc.getPosition(offset + length);
		Range rangeOfImport = new Range(posStart, posEnd);
		TextEdit textEdit = new TextEdit(rangeOfImport, newText);
		return textEdit;
	}

}
