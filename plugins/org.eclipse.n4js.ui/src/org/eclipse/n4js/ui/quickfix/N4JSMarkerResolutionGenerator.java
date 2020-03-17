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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.n4js.ui.changes.ChangeManager;
import org.eclipse.n4js.ui.changes.IChange;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.IssueModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;
import org.eclipse.xtext.ui.editor.quickfix.MarkerResolutionGenerator;
import org.eclipse.xtext.ui.util.IssueUtil;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Adds support for applying a single quick fix to multiple markers in one step (optional). This functionality is
 * available via the standard Eclipse Problems view (right-click a marker and select "Quick Fix" from the context menu).
 */
public class N4JSMarkerResolutionGenerator extends MarkerResolutionGenerator {

	@Inject
	private IssueUtil issueUtil;

	@Inject
	private ChangeManager changeManager;

	@Inject
	private IssueModificationContext.Factory modificationContextFactory;

	@Inject
	private IssueResolutionProvider resolutionProvider;

	@Inject(optional = true)
	private IWorkbench workbench;

	private class MultiResolutionAdapter extends WorkbenchMarkerResolution {

		private final Issue issue;
		private final IssueResolution resolution;

		private MultiResolutionAdapter(IssueResolution resolution) {
			if (!(resolution.getModificationContext() instanceof IssueModificationContext))
				throw new IllegalArgumentException(
						"the resolution's modification context must be an IssueModificationContext");
			this.issue = ((IssueModificationContext) resolution.getModificationContext()).getIssue();
			this.resolution = resolution;
		}

		@Override
		public String getLabel() {
			return resolution.getLabel();
		}

		@Override
		public Image getImage() {
			return N4JSMarkerResolutionGenerator.this.getImage(resolution);
		}

		@Override
		public String getDescription() {
			return resolution.getDescription();
		}

		/**
		 * Returns true iff the issue resolution of this MultiResolutionAdapter is based on an {@link N4Modification}.
		 */
		private boolean isBasedOnN4Modification() {
			return getN4Modification() != null;
		}

		/**
		 * Returns the modification stored in 'resolution' iff it is an instance of N4Modification, otherwise
		 * <code>null</code>.
		 */
		private N4Modification getN4Modification() {
			if (resolution.getModification() instanceof N4ModificationWrapper)
				return ((N4ModificationWrapper) resolution.getModification()).getN4Modification();
			return null;
		}

		@Override
		public void run(IMarker[] markers, IProgressMonitor monitor) {
			if (isBasedOnN4Modification()) {
				// applying an N4Modification to one or more markers

				try {
					// collect all changes
					final List<IChange> changes = new ArrayList<>();
					for (IMarker currMarker : markers) {

						if (!isMarkerStillValid(currMarker))
							continue;

						final Issue currIssue = issueUtil.createIssue(currMarker);
						final IModificationContext currContext = modificationContextFactory
								.createModificationContext(currIssue);
						final int offset = MarkerUtilities.getCharStart(currMarker);
						final int length = MarkerUtilities.getCharEnd(currMarker) - offset;
						final EObject element = getElementForMarker(currContext, currMarker);

						Collection<? extends IChange> changeSet = getN4Modification().computeChanges(
								currContext, currMarker, offset, length, element);
						changes.addAll(changeSet);
					}

					// perform changes
					changeManager.applyAll(changes);

				} catch (Exception e) {
					throw new WrappedException(
							"exception while applying resolution for quick fix '" + resolution.getLabel() + "'", e);
				}
			} else {
				// support for applying modifications other than N4Modification

				// applying a single quick fix to multiple markers only supported for N4Modifications (see
				// #findOtherMarkers(IMarker[]) below), so we assert markers.length==1 here
				if (markers.length != 1)
					throw new IllegalStateException();

				// default Xtext implementation
				resolution.apply();
			}
		}

		@Override
		public void run(IMarker marker) {
			run(new IMarker[] { marker }, null);
		}

		@Override
		public IMarker[] findOtherMarkers(IMarker[] markers) {
			if (existsDirtyEditorFor(markers)) {
				showError_UnsavedChanges();
				return new IMarker[0];
			}

			final N4Modification n4modification = getN4Modification(); // may be null!

			// check if applying a single quick fix to multiple IMarkers is supported
			if (n4modification == null // only supported for N4Modifications, not IModification or ISemanticModification
					|| !n4modification.supportsMultiApply()) {
				// not supported ...
				return new IMarker[0];
			}

			final List<IMarker> result = new ArrayList<>();
			for (IMarker currMarker : markers) {
				// NOTE:
				// 'markers' contains the marker this resolution was created for and should return 'false' in this case!
				// This is why we need !isSameProblem() in next line (otherwise the Eclipse framework will show multiple
				// entries in the dialog for applying quick fixes to multiple markers).
				if (!isSameProblem(currMarker)
						&& hasSameIssueCode(currMarker))
					result.add(currMarker);
			}
			return result.toArray(new IMarker[result.size()]);
		}

		private boolean hasSameIssueCode(IMarker marker) {
			final String myCode = issue.getCode();
			return myCode != null && myCode.equals(issueUtil.getCode(marker));
		}

		private boolean isSameProblem(IMarker marker) {
			URI myUriToProblem = issue.getUriToProblem();

			String code = issueUtil.getCode(marker);
			if (code != null && code.equals(org.eclipse.n4js.validation.IssueCodes.NON_EXISTING_PROJECT)) {
				myUriToProblem = myUriToProblem.appendFragment(Integer.toString(marker.hashCode()));
			}

			return myUriToProblem != null && myUriToProblem.equals(issueUtil.getUriToProblem(marker));
		}
	}

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		if (existsDirtyEditorFor(marker)) {
			showError_UnsavedChanges();
			return new IMarkerResolution[0];
		}
		if (marker.getResource() instanceof IProject) {
			// This happens with IssueCodes.NODE_MODULES_OUT_OF_SYNC
			Issue issue = getIssueUtil().createIssue(marker);
			Iterable<IssueResolution> result = resolutionProvider.getResolutions(issue);
			return getAdaptedResolutions(Lists.newArrayList(result));
		}
		return super.getResolutions(marker);
	}

	/*
	 * Overridden to create instances of MultiResolutionAdapter instead of MarkerResolutionGenerator#ResolutionAdapter
	 * and to filter out resolutions that do not support multi-apply in case user is attempting to apply to multiple
	 * issues.
	 */
	@Override
	protected IMarkerResolution[] getAdaptedResolutions(List<IssueResolution> resolutions) {
		// choose valid resolutions
		final List<IssueResolution> validResolutions = new ArrayList<>(resolutions.size());
		if (isMultiApplyAttempt()) {
			// only those that support multi-apply are valid
			for (IssueResolution currResolution : resolutions) {
				if (supportsMultiApply(currResolution))
					validResolutions.add(currResolution);
			}
			if (validResolutions.size() < resolutions.size())
				showError_MultiApplyNotSupported();
		} else {
			// all are valid
			validResolutions.addAll(resolutions);
		}
		// perform wrapping
		IMarkerResolution[] result = new IMarkerResolution[validResolutions.size()];
		for (int i = 0; i < validResolutions.size(); i++)
			result[i] = new MultiResolutionAdapter(validResolutions.get(i));
		return result;
	}

	/**
	 * Same as {@link #isMarkerStillValid(IMarker, IAnnotationModel)}, but obtains the annotation model from the
	 * marker's editor.
	 */
	@SuppressWarnings("deprecation")
	private boolean isMarkerStillValid(IMarker marker) {
		if (marker == null)
			return false;
		if (marker.getResource() instanceof IProject) {
			// This happens with IssueCodes.NODE_MODULES_OUT_OF_SYNC
			return true;
		}

		final XtextEditor editor = getEditor(marker.getResource());
		if (editor == null)
			return false;
		final IAnnotationModel annotationModel = editor.getDocumentProvider().getAnnotationModel(
				editor.getEditorInput());
		if (annotationModel == null)
			return false;
		return isMarkerStillValid(marker, annotationModel);
	}

	/**
	 * Returns the semantic element for the given marker or <code>null</code> if not available.
	 * <p>
	 * This method assumes that problems during lookup are not unlikely (marker regions without semantic elements,
	 * invalid regions, out-dated markers, etc.) and that <code>null</code> is an accepted return value in these cases,
	 * so this method is strictly fail-safe an will suppress all errors.
	 */
	private EObject getElementForMarker(IModificationContext context, IMarker marker) {
		try {
			return context.getXtextDocument().readOnly((XtextResource resource) -> {
				return resource.getEObject(issueUtil.getUriToProblem(marker).fragment());
			});
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Returns true iff the given resolution supports being applied to multiple issues / markers at once.
	 */
	private boolean supportsMultiApply(IssueResolution resolution) {
		// must be based on an N4Modification and #supportsMultiApply() returns true
		return resolution.getModification() instanceof N4ModificationWrapper
				&& ((N4ModificationWrapper) resolution.getModification()).getN4Modification() != null
				&& ((N4ModificationWrapper) resolution.getModification()).getN4Modification().supportsMultiApply();
	}

	/** @see #existsDirtyEditorFor(IMarker[]) */
	private boolean existsDirtyEditorFor(IMarker marker) {
		return existsDirtyEditorFor(new IMarker[] { marker });
	}

	/**
	 * Returns true iff for at least one of the given markers the corresponding editor is open and is dirty. Does not
	 * open any editors if they aren't open already.
	 */
	private boolean existsDirtyEditorFor(IMarker[] markers) {
		// look for an editor containing one of the given markers that is already open and dirty
		for (IMarker marker : markers) {
			final XtextEditor editorForMarker = findEditor(marker.getResource()); // returns null if not open already
			if (editorForMarker != null && editorForMarker.isDirty())
				return true;
		}
		return false;
	}

	/**
	 * Returns true iff the user is trying to apply quick fixes to multiple issues / markers at once.
	 * <p>
	 * Implementation note: this method assumes that the entire code of class MarkerResolutionGenerator is only invoked
	 * if quick fixes are initiated via the Problems view (not if they are initiated from within the editor). Therefore,
	 * this method simply checks whether the Problems view contains a selection of multiple, i.e. two or more, elements.
	 */
	private boolean isMultiApplyAttempt() {
		if (workbench == null)
			return false;
		try {
			// get the current selection in the problems view
			final ISelectionService service = workbench.getActiveWorkbenchWindow().getSelectionService();
			final IStructuredSelection sel = (IStructuredSelection) service.getSelection(IPageLayout.ID_PROBLEM_VIEW);
			return sel != null && sel.size() >= 2;
		} catch (Exception e) {
			return false;
		}
	}

	private void showError_UnsavedChanges() {
		if (workbench == null)
			return;
		MessageDialog
				.openInformation(
						workbench.getActiveWorkbenchWindow().getShell(),
						"Unsaved Changes",
						"You have unsaved changes in one or more N4JS editors. Please save all changes before initiating a quick fix via the Problems view.");

	}

	private void showError_MultiApplyNotSupported() {
		if (workbench == null)
			return;
		MessageDialog
				.openInformation(
						workbench.getActiveWorkbenchWindow().getShell(),
						"Cannot Apply To Multiple Issues",
						"Some quick fixes are hidden because they do not support being applied to multiple issues at once.");
	}
}
