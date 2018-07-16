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
package org.eclipse.n4js.ui.search;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.findrefs.EditorResourceAccess;
import org.eclipse.xtext.ui.editor.findrefs.LoadingResourceAccess;
import org.eclipse.xtext.ui.editor.findrefs.OpenDocumentTracker;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

/**
 * This class provides custom resource access to handle null-state exception. It is a temporary hack. GH-234.
 */
@SuppressWarnings("restriction")
public class N4JSEditorResourceAccess extends EditorResourceAccess {

	@Inject
	private LoadingResourceAccess delegate;

	@Inject
	private OpenDocumentTracker openDocumentTracker;

	/***
	 * This method modifies the super method to handle NullPointerException when state is null.
	 */
	@Override
	public <R> R readOnly(final URI targetURI, final IUnitOfWork<R, ResourceSet> work) {
		IXtextDocument document = openDocumentTracker.getOpenDocument(targetURI.trimFragment());
		if (document != null) {
			return document.readOnly(new IUnitOfWork<R, XtextResource>() {
				@Override
				public R exec(XtextResource state) throws Exception {
					// For some reason, sometimes state can be null at this point,
					// The resource set must be retrieved by other means in delegate.readOnly
					if (state == null) {
						return delegate.readOnly(targetURI, work);
					}
					ResourceSet localContext = state.getResourceSet();
					if (localContext != null)
						return work.exec(localContext);
					return null;
				}
			});
		} else

		{
			return delegate.readOnly(targetURI, work);
		}
	}

}
