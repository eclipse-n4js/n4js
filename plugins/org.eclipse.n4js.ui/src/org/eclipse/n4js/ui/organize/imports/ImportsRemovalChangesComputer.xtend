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
package org.eclipse.n4js.ui.organize.imports

import java.util.List
import org.eclipse.jface.text.BadLocationException
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.ui.changes.ChangeProvider
import org.eclipse.n4js.ui.changes.IChange
import org.eclipse.n4js.utils.nodemodel.NodeModelUtilsN4
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.editor.model.IXtextDocument

import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

import static extension org.eclipse.n4js.ui.organize.imports.XtextResourceUtils.*

/**
 * Utility that will calculate changes to remove all imports from {@link XtextResource}.
 */
class ImportsRemovalChangesComputer {

	/**
	 * Compute changes that will remove all imports.
	 *
	 * @param resource the resource to modify
	 * @param document the document connected to the xtextResource,  for textual changes.
	 * @return list of changes to the document.
	 */
	public static def List<IChange> getImportDeletionChanges(XtextResource resource, IXtextDocument document) throws BadLocationException {
		val changes = newArrayList
		val elements = resource.getScript().scriptElements

//		elements.filter(ImportDeclaration).map[findActualNodeFor(it)].forEach[changes.add(document.removeNodeButKeepComments(it))]
		for (el : elements) {
			if (el instanceof ImportDeclaration) {
				val nodeToRemove = findActualNodeFor(el)
				changes.add(document.removeNodeButKeepComments(nodeToRemove))
			}
		}

		return changes
	}

	private static def IChange removeNodeButKeepComments(IXtextDocument doc, INode importNode) throws BadLocationException {
		if (importNode === null)
			return IChange.IDENTITY;

		val offset = importNode.getOffset()
		val length = NodeModelUtilsN4.getNodeLengthWithASISupport(importNode);
		return ChangeProvider.removeText(doc, offset, length, true);
	}
}
