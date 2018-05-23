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
package org.eclipse.n4js.n4mf.ui.editor.hyperlinking

import com.google.inject.Inject
import org.eclipse.n4js.n4mf.N4mfPackage
import org.eclipse.emf.common.util.URI
import org.eclipse.jface.text.Region
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor

import static org.eclipse.n4js.n4mf.resource.N4MFResourceDescriptionStrategy.*
import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.*

/**
 * N4JS manifest hyperlinker to support navigation through project dependencies.
 */
class N4MFHyperlinker extends HyperlinkHelper {

	@Inject
	extension ResourceDescriptionsProvider;

	extension N4mfPackage = N4mfPackage.eINSTANCE;

	override createHyperlinksByOffset(XtextResource it, int offset, IHyperlinkAcceptor acceptor) {

		if (null === parseResult) {
			return;
		}

		val leaf = findLeafNodeAtOffset(parseResult.rootNode, offset);
		val id = leaf?.text;
		if (null !== id) {
			val desc = resourceDescriptions.getExportedObjectsByType(projectDescription).findFirst[
				id == getUserData(PROJECT_ID_KEY)
			];
			if (null !== desc && null !== desc.EObjectURI) {
				acceptor.accept(toUri(desc), leaf);
			}
		}
	}

	private def accept(IHyperlinkAcceptor acceptor, URI uri, INode node) {
		acceptor.accept(hyperlinkProvider.get => [
			URI = uri;
			hyperlinkText = node.text;
			hyperlinkRegion = node.toRegion;
		]);
	}

	private def toRegion(INode it) {
		new Region(offset, length);
	}

	private def toUri(XtextResource it, IEObjectDescription id) {
		val uri = id.EObjectURI;
		if (uri.platformResource) uri else resourceSet.URIConverter.normalize(uri);
	}

}
