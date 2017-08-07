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
import org.eclipse.n4js.ts.ui.search.ReferenceFinderLabelProvider;
import org.eclipse.xtext.findReferences.IReferenceFinder.Acceptor;
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
	private ReferenceFinderLabelProvider labelProvider;

	@Override
	protected Acceptor toAcceptor(IAcceptor<IReferenceDescription> acceptor) {
		return new ReferenceAcceptor(acceptor, getResourceServiceProviderRegistry()) {

			@Override
			public void accept(EObject source, URI sourceURI, EReference eReference, int index, EObject targetOrProxy,
					URI targetURI) {
				EObject displayObject = calculateDisplayEObject(source);

				String name = labelProvider.getText(displayObject);
				LabelledReferenceDescription description = new LabelledReferenceDescription(source, displayObject,
						sourceURI,
						targetOrProxy,
						targetURI,
						eReference, index, name);
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

	/**
	 * Check if an EObject is showable or not.
	 */
	public static boolean isShowable(EObject eobj) {
		return eobj instanceof N4MemberDeclaration || eobj instanceof N4ClassifierDefinition
				|| eobj instanceof FunctionDeclaration || eobj instanceof Script;
	}
}
