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
package org.eclipse.n4js.ui.navigator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jdt.ui.ProblemsLabelDecorator;
import org.eclipse.jdt.ui.ProblemsLabelDecorator.ProblemsLabelChangedEvent;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.N4JSExternalProject;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.navigator.internal.N4JSProjectExplorerHelper;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetLabelProvider;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.google.inject.Inject;

/**
 * Label provider extension for the N4JS specific Project Explorer view.
 */
public class N4JSProjectExplorerLabelProvider extends LabelProvider implements IStyledLabelProvider {

	@SuppressWarnings("unused")
	private static final Image SRC_FOLDER_IMG = ImageRef.SRC_FOLDER.asImage().orNull();
	private static final Image WORKING_SET_IMG = ImageRef.WORKING_SET.asImage().orNull();
	private static final Image LIB_PATH = ImageRef.LIB_PATH.asImage().orNull();
	private static final Image LIB_PATH_SCOPED = ImageRef.LIB_PATH_SCOPED.asImage().orNull();
	private static final Image EXTERNAL_LIB_PROJECT = ImageRef.EXTERNAL_LIB_PROJECT.asImage().orNull();
	private static final Image EXTERNAL_LIB_PROJECT_NOT_BUILT = ImageRef.EXTERNAL_LIB_PROJECT_NOT_BUILT.asImage()
			.orNull();

	@Inject
	@SuppressWarnings("unused")
	private N4JSProjectExplorerHelper helper;

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	@Inject
	private ExternalIndexSynchronizer indexSynchronizer;

	private final ILabelProvider delegate;
	private final ProblemsLabelDecorator decorator;
	private final ILabelProviderListener workingSetLabelProviderListener;
	private final WorkbenchLabelProvider workbenchLabelProvider;

	/**
	 * Sole constructor.
	 */
	public N4JSProjectExplorerLabelProvider() {
		decorator = new N4JSProjectExplorerProblemsDecorator();
		workbenchLabelProvider = new WorkbenchLabelProvider();
		delegate = new DecoratingLabelProvider(workbenchLabelProvider, decorator);
		workingSetLabelProviderListener = new ILabelProviderListener() {

			@Override
			public void labelProviderChanged(final LabelProviderChangedEvent event) {
				final LabelProviderChangedEvent wrapperEvent = createWorkingSetWrapperEvent(event);
				if (null != wrapperEvent) {
					UIUtils.getDisplay().asyncExec(() -> fireLabelProviderChanged(wrapperEvent));
				}
			}

		};
		delegate.addListener(workingSetLabelProviderListener);
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof WorkingSet) {
			return WorkingSetLabelProvider.INSTANCE.getText(element);
		} else {
			return delegate.getText(element);
		}
	}

	@Override
	public Image getImage(final Object element) {

		if (element instanceof WorkingSet) {
			return decorator.decorateImage(WORKING_SET_IMG, element);
		}

		if (element instanceof IFolder) {
			final IFolder folder = (IFolder) element;
			N4JSExternalProject npmProject = helper.getNodeModulesNpmProjectOrNull(folder);

			if (helper.isNodeModulesFolder(folder)) {
				return decorator.decorateImage(LIB_PATH, element);

			} else if (folder.getName().startsWith("@") && helper.isNodeModulesFolder(folder.getParent())) {
				return decorator.decorateImage(LIB_PATH_SCOPED, element);

			} else if (npmProject != null) {
				if (indexSynchronizer.isInIndex(npmProject)) {
					return decorator.decorateImage(EXTERNAL_LIB_PROJECT, element);
				} else {
					return decorator.decorateImage(EXTERNAL_LIB_PROJECT_NOT_BUILT, element);
				}

			} else if (helper.isSourceFolder(folder) || helper.isOutputFolder(folder)) {
				// (temporarily) disabled because #isSourceFolder() and #isOutputFolder() obtain a lock on the workspace
				// (e.g. they call IResource#exists() on IFolder 'element') and this seems to cause performance issues
				// with locks that egit is obtaining for doing cyclic updates (see IDE-2269):
				return decorator.decorateImage(SRC_FOLDER_IMG, element);
			}
		}

		return delegate.getImage(element);
	}

	@Override
	public void dispose() {
		super.dispose();
		delegate.removeListener(workingSetLabelProviderListener);
	}

	@Override
	public StyledString getStyledText(final Object element) {
		if (element instanceof WorkingSet) {
			return WorkingSetLabelProvider.INSTANCE.getStyledText(element);
		}

		if (element instanceof IFolder) {
			IFolder folder = (IFolder) element;
			N4JSExternalProject npmProject = helper.getNodeModulesNpmProjectOrNull(folder);
			if (npmProject != null) {
				IN4JSProject iNpmProject = npmProject.getIProject();
				return helper.getStyledTextForExternalProject(iNpmProject, folder.getName());
			}

			return new StyledString(folder.getName());
		}

		return workbenchLabelProvider.getStyledText(element);
	}

	/**
	 * Creates a wrapper label provider change event with all visible {@link IWorkingSet working sets} if the working
	 * set mode is enabled in the {@code Project Explorer}. Otherwise returns with {@code null}.
	 *
	 * @param event
	 *            the original event.
	 * @return a wrapper event with all visible working sets (if any) to trigger viewer refresh, or {@code null} if no
	 *         viewer refresh is needed.
	 */
	private LabelProviderChangedEvent createWorkingSetWrapperEvent(final LabelProviderChangedEvent event) {
		if (event instanceof ProblemsLabelChangedEvent && workingSetManagerBroker.isWorkingSetTopLevel()) {
			final WorkingSetManager manager = workingSetManagerBroker.getActiveManager();
			if (null != manager) {
				final WorkingSet[] workingSets = manager.getWorkingSets();
				if (!Arrays2.isEmpty(workingSets)) {
					return new LabelProviderChangedEvent(delegate, workingSets);
				}
			}
		}
		return null;
	}

}
