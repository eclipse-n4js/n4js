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
package org.eclipse.n4js.ui.quickfix;

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ui.changes.IChange;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;

/**
 * Quick fixes define possible modifications by implementing {@link IModification}. This abstract class serves the same
 * purpose as IModification but with several customizations:
 * <ol>
 * <li>when being applied, N4Modifications do not change an IDocument directly but instead produce {@link IChange}s and
 * return them. Actual application of these changes is to be performed by the caller.
 * <li>when being applied, N4Modifications do not only get the {@link IModificationContext} as a basis for computation
 * (as is the case with IModifications) but also the marker, offset and length the modification is to be applied to.
 * This supports applying a single quick fix to many problems and allows the framework to update the offset / length if
 * changes to the IMarker have occurred after saving (NOTE: updating of offset / length not supported yet!).
 * </ol>
 * Implementation note: this interface does not extend interface {@link IModification}, following an Xtext design
 * pattern; for more info refer to {@link N4ModificationWrapper}.
 */
public abstract class N4Modification {

	/**
	 * Applies the modification represented by the receiving instance to the {@link IXtextDocument} of the given
	 * modification context. However, the document <b>must not be changed</b> directly within this method; instead, one
	 * or more {@link IChange}s are to be returned that will realize the modification.
	 * <p>
	 * Note that the marker, offset and length may refer to a different marker than the one the N4Modification has been
	 * created for (in case the resolution is being applied to multiple markers in one step). Therefore, always use the
	 * values provided as argument instead of any cached values!
	 * <p>
	 * Is called iff this N4Modification is applied once only.
	 *
	 * @param context
	 *            the modification context (basically a pointer to an {@link IXtextDocument}).
	 * @param marker
	 *            the marker the N4Modification should be applied to or <code>null</code> if not available.
	 * @param offset
	 *            the offset of the issue the N4Modification should be applied to.
	 * @param length
	 *            the length of the issue the N4Modification should be applied to.
	 * @param element
	 *            the semantic element at the region defined by offset / length or <code>null</code> if not available.
	 * @return one or more textual changes.
	 * @throws Exception
	 *             any failure.
	 */
	public abstract Collection<? extends IChange> computeChanges(
			IModificationContext context,
			IMarker marker,
			int offset,
			int length,
			EObject element) throws Exception;

	/**
	 * Checks if the receiving N4Modification supports multi-apply, i.e. being applied to multiple issues / markers at
	 * once. Returns <code>true</code> by default; override and return <code>false</code> to turn off multi-apply
	 * support for this modification.
	 */
	public boolean supportsMultiApply() {
		return true;
	}
}
