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
import org.eclipse.n4js.ui.changes.ChangeManager;
import org.eclipse.n4js.ui.changes.IChange;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.model.edit.SemanticModificationWrapper;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.validation.Issue;

/**
 * In Xtext, an {@link ISemanticModification} is an alternative form of an {@link IModification}, but instead of letting
 * <code>ISemanticModification</code> inherit from <code>IModification</code> directly, wrapper class
 * {@link SemanticModificationWrapper} is used to turn a semantic modification into an instance of
 * <code>IModification</code> when needed.
 * <p>
 * This class follows the same pattern by wrapping an {@link N4Modification} and thus turning it into an instance of
 * <code>IModification</code>.
 */
public class N4ModificationWrapper implements IModification {

	private final N4Modification modification;
	private final Issue issue;

	private final ChangeManager changeManager;

	/**
	 * Wraps the given {@link N4Modification} to turn it into an instance of {@link IModification}.
	 */
	public N4ModificationWrapper(N4Modification modification, Issue issue, ChangeManager changeManager) {
		this.modification = modification;
		this.issue = issue;
		this.changeManager = changeManager;
	}

	/**
	 * Returns the wrapped {@link N4Modification}.
	 */
	public N4Modification getN4Modification() {
		return modification;
	}

	/**
	 * Returns the issue for which the wrapped {@link N4Modification} was created.
	 */
	public Issue getIssue() {
		return issue;
	}

	/*
	 * This method will only be called by the framework if a quick fix is applied directly from within the N4JS editor.
	 * If a quick fix is applied via an IMarker from the "Problems" view, {@link N4JSMarkerResolutionGenerator} makes
	 * sure that method {@link N4Modification#computeChanges(IModificationContext, int, int, EObject)} will be called
	 * directly and actual application of the computed changes will be handled by the marker resolution generator as
	 * well.
	 *
	 * Therefore, in the implementation of this method, we do not have to bother with changed editor state; all
	 * information in field 'issue' is always up to date.
	 */
	@Override
	public final void apply(IModificationContext context) throws Exception {
		context.getXtextDocument().modify(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource resource) throws Exception {
				final IMarker marker = issue instanceof N4JSIssue ? ((N4JSIssue) issue).getMarker() : null;
				final EObject element = resource.getEObject(issue.getUriToProblem().fragment());
				final Collection<? extends IChange> changes = modification.computeChanges(
						context,
						marker,
						issue.getOffset(),
						issue.getLength(),
						element);

				changeManager.applyAll(changes);
			}
		});
	}
}
