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
package org.eclipse.n4js.ui.search;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription;
import org.eclipse.n4js.ui.N4JSHierarchicalNameComputerHelper;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;
import org.eclipse.xtext.findReferences.IReferenceFinder.Acceptor;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.ui.editor.findrefs.DelegatingReferenceFinder;
import org.eclipse.xtext.ui.editor.findrefs.ReferenceAcceptor;
import org.eclipse.xtext.util.IAcceptor;

import com.google.inject.Inject;

/**
 * A reference finder that produces results with a proper label.
 */
@SuppressWarnings("restriction")
public class LabellingReferenceFinder extends DelegatingReferenceFinder {

	@Inject
	private N4JSLabelProvider labelProvider;

	@Override
	protected Acceptor toAcceptor(IAcceptor<IReferenceDescription> acceptor) {
		return new ReferenceAcceptor(acceptor, getResourceServiceProviderRegistry()) {

			@Override
			public void accept(EObject source, URI sourceURI, EReference eReference, int index, EObject targetOrProxy,
					URI targetURI) {
				// Check if we should ignore named import specifier
				if (N4JSReferenceQueryExecutor.ignoreNamedImportSpecifier && source instanceof NamedImportSpecifier)
					return;

				EObject displayObject = calculateDisplayEObject(source);
				String logicallyQualifiedDisplayName = N4JSHierarchicalNameComputerHelper
						.calculateHierarchicalDisplayName(displayObject, labelProvider, false);
				ICompositeNode srcNode = NodeModelUtils.getNode(source);
				int line = srcNode.getStartLine();
				LabelledReferenceDescription description = new LabelledReferenceDescription(source, displayObject,
						sourceURI,
						targetOrProxy,
						targetURI,
						eReference, index, logicallyQualifiedDisplayName, line);
				accept(description);
			}
		};
	}

	private EObject calculateDisplayEObject(EObject source) {
		EObject displayObject = source;
		while (!N4JSHierarchicalNameComputerHelper.isShowable(displayObject)) {
			displayObject = displayObject.eContainer();
		}
		return displayObject;
	}
}
