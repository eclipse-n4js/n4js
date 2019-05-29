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
package org.eclipse.n4js.ui.organize.imports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.n4js.documentation.N4JSDocumentationProvider;
import org.eclipse.n4js.parser.InternalSemicolonInjectingParser;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess;
import org.eclipse.n4js.ui.changes.ChangeManager;
import org.eclipse.n4js.ui.changes.ChangeProvider;
import org.eclipse.n4js.ui.changes.IAtomicChange;
import org.eclipse.n4js.ui.changes.IChange;
import org.eclipse.n4js.ui.changes.Replacement;
import org.eclipse.n4js.ui.organize.imports.BreakException.UserCanceledBreakException;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * This helper will analyze imports section of the provided document, and rewrite it with new computed state.
 *
 * Since many injected services depend on type of the organized document (i.e. language, e.g. N4JS) it is desired that
 * callers use {@link OrganizeImportsService} which will take care the injection mechanisms.
 */
class DocumentImportsOrganizer {

	private static final Logger LOGGER = Logger.getLogger(DocumentImportsOrganizer.class);

	@Inject
	private ImportsComputer importsComputer;

	@Inject
	private ImportsRegionHelper hImportsRegion;

	@Inject
	private ChangeManager changeManager;

	@Inject
	private XtextDocumentProvider docProvider;

	@Inject
	private TypeExpressionsGrammarAccess typeExpressionsGrammarAccess;

	@Inject
	private N4JSDocumentationProvider n4JSDocumentationProvider;

	/**
	 * Obtains {@link IXtextDocument} for the provided file and delegates to
	 * {@link #organizeDocument(IXtextDocument, Interaction)}
	 */
	void organizeFile(IFile file, final Interaction interaction, IProgressMonitor mon)
			throws CoreException {

		SubMonitor subMon = SubMonitor.convert(mon, "Organizing " + file.getName(), IProgressMonitor.UNKNOWN);

		FileEditorInput fei = new FileEditorInput(file);

		docProvider.connect(fei); // without connecting no document will be provided
		IXtextDocument document = (IXtextDocument) docProvider.getDocument(fei);

		docProvider.aboutToChange(fei);

		organizeDocument(document, interaction);

		subMon.setTaskName("Saving " + file.getName());
		docProvider.saveDocument(subMon.split(0), fei, document, true);

		docProvider.changed(fei);
		docProvider.disconnect(fei);

	}

	/**
	 * Organize the imports in the N4JS document.
	 *
	 * @param document
	 *            N4JS document
	 * @throws RuntimeException
	 *             wrapping a BreakException in case of user-abortion ({@link Interaction#queryUser}) or
	 *             resolution-failure({@link Interaction#breakBuild} )
	 */
	void organizeDocument(final IXtextDocument document, final Interaction interaction) {
		// trigger Linking
		document.readOnly((XtextResource p) -> {
			N4JSResource.postProcess(p);
			return null;
		});

		List<IChange> result = document.readOnly(prepareImportsChanges(document, interaction));

		applyChangesToDocument(document, result);

	}

	/**
	 * Modifies provided document with provided changes. If list of changes is not meaningful will not touch the
	 * document.
	 */
	private void applyChangesToDocument(final IXtextDocument document, List<IChange> result) {
		if (result != null && !result.isEmpty()) {
			// do the changes really modify anything?
			ChangeAnalysis changeAnalysis = condense(result);
			if (changeAnalysis.noRealChanges) {
				// verify again:
				String del = document.get().substring(changeAnalysis.deletion.getOffset(),
						changeAnalysis.deletion.getOffset() + changeAnalysis.deletion.getLength());
				if (changeAnalysis.newText.getText().equals(del)) {
					return;
				}
			}
			document.modify(
					new IUnitOfWork.Void<XtextResource>() {
						@Override
						public void process(XtextResource state) throws Exception {
							try {
								EcoreUtil.resolveAll(state);
								changeManager.applyAllInSameDocument(changeAnalysis.changes, document);
							} catch (BadLocationException e) {
								LOGGER.error(e);
							}
						}
					});
		}
	}

	/**
	 * Reads provided document and by analyzing it with {@link ImportsComputer} prepares list of changes to be made.
	 *
	 * @param document
	 *            to analyze
	 * @param interaction
	 *            controls dealing with multiple choices
	 */
	private IUnitOfWork<List<IChange>, XtextResource> prepareImportsChanges(final IXtextDocument document,
			final Interaction interaction) {
		return new IUnitOfWork<>() {

			@Override
			public List<IChange> exec(XtextResource xtextResource) throws Exception {
				InsertionPoint insertionPoint = hImportsRegion.getImportRegion(xtextResource);

				if (insertionPoint.offset != -1) {
					List<IChange> changes = new ArrayList<>();
					try {
						final String NL = ChangeProvider.lineDelimiter(document,
								insertionPoint.offset);

						final String organizedImportSection = importsComputer
								.getOrganizedImportSection(xtextResource, NL, interaction);

						// remove old imports
						changes.addAll(
								ImportsRemovalChangesComputer.getImportDeletionChanges(xtextResource, document));
						// ImportsRemovalChangesComputer2.getImportDeletionChanges(xtextResource, document));

						// insert new imports
						changes.addAll(getImportInsertionChanges(document, xtextResource, insertionPoint, NL,
								organizedImportSection));

						return changes;
					} catch (UserCanceledBreakException e) {
						return null; // user-triggered cancellation, nothing to report.
					} catch (BreakException e) {
						LOGGER.warn("Organize imports broke:", e);
						throw e;
					}
				}

				return null;
			}
		};
	}

	/**
	 * Computes the changes that will insert imports. If nothing is inserted (e.g. organizedImportSection is empty) then
	 * an empty list is returned.
	 *
	 * This method computes the real offset based on the information given in the passed in insertion point and creates
	 * an replacement.
	 *
	 * @param document
	 *            current Xtext document under modification
	 * @param xtextResource
	 *            associated Xtext-resource of the document
	 * @param insertionPoint
	 *            data about possible insertion-range.
	 * @param NL
	 *            current new line sequence
	 * @param organizedImportSection
	 *            text of imports
	 * @return empty list or a single-element list with an replacement.
	 */
	private List<IChange> getImportInsertionChanges(final IXtextDocument document, XtextResource xtextResource,
			InsertionPoint insertionPoint, final String NL,
			final String organizedImportSection) throws BadLocationException {
		if (organizedImportSection.isEmpty()) {
			// nothing to insert, then issue no change:
			return Collections.emptyList();
		}

		// advance ImportRegion-offset if not nil and not right before a jsdoc:
		int offset = insertionPoint.offset;
		if (offset != 0 && !insertionPoint.isBeforeJsdocDocumentation) {
			offset += NL.length();
		}
		// if the line above is part of a ML-comment, then line-break:
		IRegion lineRegion = document.getLineInformationOfOffset(offset);

		ILeafNode leafNodeAtBeginOfLine = NodeModelUtils.findLeafNodeAtOffset(
				xtextResource.getParseResult().getRootNode(), lineRegion.getOffset());

		//
		// Three cases have to be considered:
		// A) the begin of line is inside of an ML-comment.
		// B) the begin of line is some ASI overlapping some real text (e.g. a ML-comment) this is not covered in A!
		// C) the begin of line is some ordinary location.
		//
		if (leafNodeAtBeginOfLine.getGrammarElement() == typeExpressionsGrammarAccess
				.getML_COMMENTRule() // plain ML
		) {
			// CASE A)
			int insertOffset = insertionPoint.offset;
			// it is inside a ML, so we need to insert a line-break;
			boolean atStartOfLine = insertionPoint.offset == lineRegion.getOffset();
			String finalText = (atStartOfLine ? "" : NL) + organizedImportSection + NL;
			return Lists.newArrayList(new Replacement(xtextResource.getURI().trimFragment(),
					insertOffset, 0, finalText));

		} else if (UtilN4.isIgnoredSyntaxErrorNode(leafNodeAtBeginOfLine,
				InternalSemicolonInjectingParser.SEMICOLON_INSERTED)
				// ASI overlapping something
				&& (leafNodeAtBeginOfLine.getTotalOffset() < lineRegion.getOffset()
				// this ASI something starts before the beginning of the line
				)) {
			// CASE B)
			int insertOffset = insertionPoint.offset; // concrete
														// position

			if ((!insertionPoint.isBeforeJsdocDocumentation) &&
			// if this was an ASI case shadowing a jsdoc-/**-style comment
			// we should insert before this comment. Still need to double-check the
			// concrete content:
					n4JSDocumentationProvider.isDocumentationStyle(
							NodeModelUtils.getTokenText(leafNodeAtBeginOfLine))) {
				// it's an active jsdoc comment, shadowed by ASI-insertions
				insertOffset = leafNodeAtBeginOfLine.getTotalOffset();

			}
			// it is ML, so we need to insert a line-break;
			String finalText = NL + organizedImportSection + NL;
			return Lists.newArrayList(new Replacement(xtextResource.getURI().trimFragment(),
					insertOffset, 0, finalText));

		} else {
			// CASE C)
			// The line above is not part of a ML-comment, so do this:
			return Lists.newArrayList(ChangeProvider.insertLineAbove(document,
					offset, organizedImportSection, false)); // indentation doesn't work
																// with multiple lines
		}
	}

	/**
	 * Very specific to the generator: One has a text with nonzero length, all others are deletions and have zero-length
	 * texts.
	 *
	 * Find the one with text, try to condense the other into one atomic change.
	 *
	 *
	 * @param changes
	 *            list of Changes to process
	 * @return Pair of Changes, flag if nothing changes.
	 */
	private ChangeAnalysis condense(List<IChange> changes) {
		List<IAtomicChange> atomicResult = changeManager.flattenAndOrganized(changes);
		if (atomicResult.isEmpty()) {
			return new ChangeAnalysis(atomicResult, true);
		}
		// if all are from same uri and type of Replacement, then it will be condensed.
		URI uri = atomicResult.get(0).getURI();
		if (!(atomicResult.get(0) instanceof Replacement)) {
			return new ChangeAnalysis(atomicResult, false);
		}

		// Pre condition: find the one with text != ø && other have no text.
		// Pre uris must match.
		Replacement rText = null;
		for (IAtomicChange nxt : atomicResult) {
			if (!(nxt instanceof Replacement) || !uri.equals(nxt.getURI())) {
				return new ChangeAnalysis(atomicResult, false);
			}
			Replacement rplc = (Replacement) nxt;
			if (rplc.getText() != null && rplc.getText().length() > 0) {
				if (rText == null) {
					rText = rplc;
				} else {
					return new ChangeAnalysis(atomicResult, false); // more then one text-addition, pre doesn't hold
				}
			}
		}

		Replacement current = null;
		// Back to front iteration
		for (int i = atomicResult.size() - 1; i >= 0; i--) {
			IAtomicChange nxt = atomicResult.get(i);
			if (nxt == rText) {
				continue;
			}
			Replacement rplc = (Replacement) nxt;
			if (current == null) {
				current = rplc;
				continue;
			}
			// all Texts are
			if (current.getOffset() + current.getLength() == rplc.getOffset()) {
				// possible to concatenate.
				current = new Replacement(uri, current.getOffset(), current.getLength() + rplc.getLength(), "");
			} else {
				// cannot merge
				return new ChangeAnalysis(atomicResult, false);
			}
		}
		// compare length:
		if (current == null || rText == null || current.getLength() != rText.getText().length()) {
			return new ChangeAnalysis(atomicResult, false);
		}

		// keep correct order.
		List<IAtomicChange> orderedChanges = null;
		if (rText == atomicResult.get(0)) {
			orderedChanges = Arrays.asList(rText, current);
		} else if (rText == atomicResult.get(atomicResult.size() - 1)) {
			orderedChanges = Arrays.asList(current, rText);
		} else {
			return new ChangeAnalysis(atomicResult, false);
		}

		ChangeAnalysis result = new ChangeAnalysis(orderedChanges, true);
		result.deletion = current;
		result.newText = rText;
		return result;
	}

}
