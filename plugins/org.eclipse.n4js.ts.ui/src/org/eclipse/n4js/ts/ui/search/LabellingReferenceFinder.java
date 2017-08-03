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
package org.eclipse.n4js.ts.ui.search;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
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
				String name = labelProvider.getText(source);
				LabelledReferenceDescription description = new LabelledReferenceDescription(sourceURI, targetURI,
						eReference, index, name);
				accept(description);
			}
		};
	}
}
