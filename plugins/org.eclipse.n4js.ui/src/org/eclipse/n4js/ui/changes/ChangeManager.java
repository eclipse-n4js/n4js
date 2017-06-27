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
package org.eclipse.n4js.ui.changes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import com.google.inject.Inject;
import com.google.inject.Injector;

import org.eclipse.n4js.utils.RecursionGuard;

/**
 * This class helps you manage all the changes in your busy every-day life.
 */
public class ChangeManager {

	// ==== BEGIN injection work-around (see N4JSUiModule#configureMarkerResolutionGenerator()) ====

	// @Inject
	// private IURIEditorOpener editorOpener;
	@Inject
	private Injector tempInjector;

	private IURIEditorOpener getEditorOpener() {
		return org.eclipse.ui.PlatformUI.isWorkbenchRunning() ? tempInjector.getInstance(IURIEditorOpener.class) : null;

	}

	// ==== END injection work-around ====

	/**
	 * Returns a flat list of all {@link IChange}s directly or indirectly contained in the given collection. In this
	 * context, "flat" means that the returned list does not contain any instances of {@link ICompositeChange}, only
	 * instances of {@link IAtomicChange}. Beware: not organized!
	 */
	public List<IAtomicChange> flatten(Collection<? extends IChange> changes) {
		return flatten(changes, new ArrayList<IAtomicChange>(), new RecursionGuard<>());
	}

	/**
	 * flatten {@link ChangeManager#flatten(Collection)} and sort in reverse order, to apply in correct order.
	 *
	 * @param changes
	 *            list of arbitrary changes.
	 * @return back to front ordered list of IAtomChanges
	 */
	public List<IAtomicChange> flattenAndOrganized(Collection<? extends IChange> changes) {
		List<IAtomicChange> unorg = flatten(changes);
		Collections.sort(unorg, Collections.reverseOrder());
		return unorg;
	}

	private List<IAtomicChange> flatten(Collection<? extends IChange> changes,
			List<IAtomicChange> addHere, RecursionGuard<IChange> guard) {
		for (IChange currChange : changes) {
			if (guard.tryNext(currChange)) {
				if (currChange instanceof ICompositeChange)
					flatten(((ICompositeChange) currChange).getChildren(), addHere, guard);
				else if (currChange instanceof IAtomicChange)
					addHere.add((IAtomicChange) currChange);
			}
		}
		return addHere;
	}

	/**
	 * Flattens the given changes, collects them per file (i.e. URI) and sorts the list of changes for each file in
	 * reverse(!) order.
	 */
	public Map<URI, List<IAtomicChange>> organize(Collection<? extends IChange> changes) {
		// flatten
		final List<IAtomicChange> flatChanges = flatten(changes);
		// collect changes per file
		final Map<URI, List<IAtomicChange>> result = new HashMap<>();
		for (IAtomicChange currChange : flatChanges) {
			final URI currURI = currChange.getURI().trimFragment();
			List<IAtomicChange> currL = result.get(currURI);
			if (currL == null) {
				currL = new ArrayList<>();
				result.put(currURI, currL);
			}
			currL.add(currChange);
		}
		// sort lists (in reverse order!)
		for (List<IAtomicChange> currL : result.values())
			Collections.sort(currL, Collections.reverseOrder());
		return result;
	}

	/**
	 * Applies all given changes.
	 */
	public void applyAll(Collection<? extends IChange> changes)
			throws BadLocationException {
		final Map<URI, List<IAtomicChange>> changesPerFile = organize(changes);
		for (URI currURI : changesPerFile.keySet()) {
			final IXtextDocument document = getDocument(currURI);
			applyAllInSameDocument(changesPerFile.get(currURI), document);
		}
	}

	/**
	 * Applies all given changes to the given document. This method assumes that 'changes' contains only changes
	 * intended for the given document; the actual URI stored in the changes is ignored.
	 */
	public void applyAllInSameDocument(Collection<? extends IAtomicChange> changes, IDocument document)
			throws BadLocationException {
		DocumentRewriteSession rewriteSession = null;
		try {
			// prepare
			if (document instanceof IDocumentExtension4) {
				rewriteSession = ((IDocumentExtension4) document).startRewriteSession(
						DocumentRewriteSessionType.UNRESTRICTED);
			}
			// perform replacements
			for (IAtomicChange currRepl : changes) {
				currRepl.apply(document);
			}
		} finally {
			// cleanup
			if (rewriteSession != null)
				((IDocumentExtension4) document).stopRewriteSession(rewriteSession);
		}
	}

	/**
	 * Obtain document for the given URI.
	 */
	private IXtextDocument getDocument(URI uri) {
		return getEditor(uri).getDocument();
	}

	private XtextEditor getEditor(URI uri) {
		// note: trimming fragment in previous line makes this more stable in case of changes to the
		// Xtext editor's contents (we are not interested in a particular element, just the editor)
		final URI uriToEditor = uri != null ? uri.trimFragment() : null;
		if (uriToEditor != null) {
			final IEditorPart editor = getEditorOpener().open(uriToEditor, false);
			if (editor instanceof XtextEditor)
				return (XtextEditor) editor;
		}
		return null;
	}
}
