/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.ui.editor.hover.DispatchingEObjectTextHover
 *	in bundle org.eclipse.xtext.ui
 *	available under the terms of the Eclipse Public License 2.0
 * 	Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.editor;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.ui.editor.hover.DispatchingEObjectTextHover;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentUtil;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 * Minor customization of the default {@link DispatchingEObjectTextHover} to avoid blocking the UI thread.
 */
public class N4JSHover extends DispatchingEObjectTextHover {

	/**
	 * Method copied from super class with only a minor change: call to "readOnly" changed to "tryReadOnly".
	 */
	@Override
	public IRegion getHoverRegion(final ITextViewer textViewer, final int offset) {
		IXtextDocument xtextDocument = XtextDocumentUtil.get(textViewer);
		if (xtextDocument == null)
			return null;
		if (!(xtextDocument instanceof N4JSDocument)) {
			return super.getHoverRegion(textViewer, offset);
		}
		// TODO this is being called on change in the UI-thread. Not a good idea to do such expensive stuff.
		// returning the region on a per token basis would be better.
		try {
			return ((N4JSDocument) xtextDocument).tryReadOnly(new IUnitOfWork<IRegion, XtextResource>() {
				@Override
				public IRegion exec(XtextResource state) throws Exception {
					// resource can be null e.g. read only zip/jar entry
					if (state == null) {
						return null;
					}
					Pair<EObject, IRegion> element = getXtextElementAt(state, offset);
					if (element != null) {
						return element.getSecond();
					} else {
						return null;
					}
				}
			}, (IRegion) null);
		} catch (OperationCanceledException e) {
			return null;
		} catch (OperationCanceledError e) {
			return null;
		}
	}
}
