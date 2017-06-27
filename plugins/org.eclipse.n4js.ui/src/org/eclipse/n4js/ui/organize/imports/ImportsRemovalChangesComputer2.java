/**
 * Copyright (c) 2017 NumberFour AG.
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
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.ui.changes.ChangeProvider;
import org.eclipse.n4js.ui.changes.IChange;

/**
 * Utility that will calculate changes to remove all imports from {@link XtextResource}. Simplified version og
 * {@link ImportsRemovalChangesComputer}. Neither of them covers all cases of comments nested in imports. If decided it
 * is worth the effort, refactor both to handle all desired cases.
 */
class ImportsRemovalChangesComputer2 {

	/**
	 * Compute changes that will remove all imports.
	 *
	 * @param resource
	 *            the resource to modify
	 * @param document
	 *            the document connected to the xtextResource, for textual changes.
	 * @return list of changes to the document.
	 */
	public static List<IChange> getImportDeletionChanges(XtextResource resource, IXtextDocument document)
			throws BadLocationException {
		List<IChange> changes = new ArrayList<>();
		List<ScriptElement> elements = XtextResourceUtils.getScript(resource).getScriptElements();
		// elements.filter(ImportDeclaration).map[findActualNodeFor(it)].forEach[changes.add(document.removeNodeButKeepComments(it))]
		for (ScriptElement el : elements) {
			if (el instanceof ImportDeclaration) {
				INode nodeToRemove = NodeModelUtils.findActualNodeFor(el);
				changes.add(removeNodeButKeepComments(document, nodeToRemove));
			}
		}
		return changes;
	}

	private static IChange removeNodeButKeepComments(IXtextDocument doc, INode importNode) throws BadLocationException {
		if (importNode == null)
			return IChange.IDENTITY;

		int end = importNode.getEndOffset();
		int offset = importNode.getOffset();
		return ChangeProvider.removeText(doc, offset, end - offset, true);
	}

}
