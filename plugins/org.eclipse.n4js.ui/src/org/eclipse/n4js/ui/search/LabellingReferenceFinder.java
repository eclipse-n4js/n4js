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
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.ui.search.LabelledReferenceDescription;
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
				EObject displayObject = calculateDisplayEObject(source);

				String logicallyQualifiedDisplayName = calculateLogicallyQualifiedDisplayName(displayObject);
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
		while (!isShowable(displayObject)) {
			displayObject = displayObject.eContainer();
		}
		return displayObject;
	}

	private String calculateLogicallyQualifiedDisplayName(EObject eob) {
		// Calculate hierarchical logical name, e.g. C.m
		String text = labelProvider.getText(eob);
		EObject currContainer = eob.eContainer();
		while (currContainer != null && !(currContainer instanceof Script)) {
			if (isShowable(currContainer)) {
				text = labelProvider.getText(currContainer) + "." + text;
			}
			currContainer = currContainer.eContainer();
		}
		return text;
	}

	private boolean isShowable(EObject eobj) {
		return eobj instanceof N4MemberDeclaration || eobj instanceof N4ClassifierDefinition
				|| eobj instanceof FunctionDeclaration || eobj instanceof Script;
	}
}
