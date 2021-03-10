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

import static org.eclipse.n4js.ide.server.codeActions.util.ChangeProvider.replace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.n4js.ide.editor.contentassist.ImportsAwareReferenceProposalCreator;
import org.eclipse.n4js.ide.imports.ImportDescriptor;
import org.eclipse.n4js.ide.imports.ImportHelper;
import org.eclipse.n4js.ide.imports.ReferenceResolution;
import org.eclipse.n4js.ide.server.codeActions.util.ChangeProvider;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * N4JS quick fixes for LSP.
 * <p>
 * Quick fixes are code actions that apply to a particular issue in the code and have the LSP code action kind
 * {@link CodeActionKind#QuickFix QuickFix}.
 */
@SuppressWarnings("restriction")
@Singleton
public class N4JSQuickfixProvider {

	@Inject
	private ImportHelper importHelper;

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	/**
	 * Resolves missing import statements by re-using content assist and {@link ImportsAwareReferenceProposalCreator}
	 */
	@Fix(value = org.eclipse.xtext.diagnostics.Diagnostic.LINKING_DIAGNOSTIC, multiFix = false)
	public void addImportForUnresolvedReference(QuickfixContext context, ICodeActionAcceptor acceptor) {
		Script script = context.resource.getScriptResolved();
		Document doc = context.options.getDocument();
		Diagnostic diagnostic = context.getDiagnostic();
		if (script == null || doc == null || diagnostic == null) {
			return;
		}
		EObject model = getEObject(context);
		List<ReferenceResolution> resolutions = importHelper.findResolutionsForUnresolvedReference(model,
				context.options.getCancelIndicator());

		for (ReferenceResolution resolution : resolutions) {
			ImportDescriptor importToBeAdded = resolution.importToBeAdded;
			if (importToBeAdded == null) {
				continue;
			}

			ReplaceRegion replacement = importHelper.getReplacementForImport(script, importToBeAdded);
			if (replacement != null) {
				String description = resolution.description;
				TextEdit textEdit = ChangeProvider.replace(doc, replacement);
				acceptor.acceptQuickfixCodeAction(context, "Add import from module " + description,
						Collections.singletonList(textEdit));
			}
		}
	}

	/**
	 * Remove the question mark qualifier from optional fields from the old location and put it at the new location.
	 */
	@Fix(IssueCodes.CLF_FIELD_OPTIONAL_OLD_SYNTAX)
	public void fixOldSyntaxForOptionalFields(QuickfixContext context, ICodeActionAcceptor acceptor) {
		Document doc = context.options.getDocument();
		EObject element = getEObject(context);
		int offsetNameEnd = getOffsetOfNameEnd(element);
		List<TextEdit> textEdits = new ArrayList<>();
		// removes the ? at the old location
		int endOffset = doc.getOffSet(context.getDiagnostic().getRange().getEnd());
		textEdits.add(replace(doc, endOffset - 1, 1, ""));
		// inserts a ? at the new location (behind the field or accessor name)
		if (offsetNameEnd != -1) {
			textEdits.add(replace(doc, offsetNameEnd, 0, "?"));
		}
		acceptor.acceptQuickfixCodeAction(context, "Change to new syntax", textEdits);
	}

	/**
	 * Update the syntax for optional function parameters.
	 */
	@Fix(IssueCodes.FUN_PARAM_OPTIONAL_WRONG_SYNTAX)
	public void fixOldSyntaxForOptionalFpars(QuickfixContext context, ICodeActionAcceptor acceptor) {
		Document doc = context.options.getDocument();
		List<TextEdit> textEdits = new ArrayList<>();
		int endOffset = doc.getOffSet(context.getDiagnostic().getRange().getEnd());
		textEdits.add(replace(doc, endOffset - 1, 1, " = undefined"));
		acceptor.acceptQuickfixCodeAction(context, "Change to Default Parameter", textEdits);
	}

	private int getOffsetOfNameEnd(EObject element) {
		EObject parent = element.eContainer();
		if (parent instanceof TypeReferenceNode<?>) {
			parent = parent.eContainer();
		}
		INode node;
		if (parent instanceof N4FieldDeclaration) {
			N4FieldDeclaration fieldDecl = (N4FieldDeclaration) parent;
			if (fieldDecl.isDeclaredOptional()) {
				return -1;
			}
			node = NodeModelUtils.findActualNodeFor(fieldDecl.getDeclaredName());
		} else if (parent instanceof TField) {
			TField field = (TField) parent;
			if (field.isOptional()) {
				return -1;
			}
			node = NodeModelUtils.findNodesForFeature(field, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME).get(0);
		} else {
			return -1;
		}
		return node.getEndOffset();
	}

	private EObject getEObject(QuickfixContext context) {
		Document doc = context.options.getDocument();
		XtextResource resource = context.options.getResource();
		return getEObject(doc, resource, context.getDiagnostic().getRange());
	}

	private EObject getEObject(Document doc, XtextResource resource, Range range) {
		Position start = range.getStart();
		int startOffset = doc.getOffSet(start);
		return eObjectAtOffsetHelper.resolveContainedElementAt(resource, startOffset);
	}

}
