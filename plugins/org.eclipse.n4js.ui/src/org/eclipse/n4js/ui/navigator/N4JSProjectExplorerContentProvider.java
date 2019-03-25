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

import static com.google.common.collect.Maps.newConcurrentMap;
import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceStore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.navigator.internal.N4JSProjectExplorerHelper;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider2;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;
import org.eclipse.xtext.util.Arrays;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Customized content provider for tuning the Project Explorer view with N4JS specific content.
 */
@Singleton
public class N4JSProjectExplorerContentProvider extends WorkbenchContentProvider
		implements ExternalLibraryPreferenceStore.StoreUpdatedListener, IPipelinedTreeContentProvider2 {

	private static final Logger LOGGER = Logger.getLogger(N4JSProjectExplorerContentProvider.class);

	@Inject
	private N4JSProjectExplorerHelper helper;

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	private final Map<IProject, Object[]> virtualNodeCache = newConcurrentMap();

	/**
	 * Creates a new content provider for the navigator with N4JS content support.
	 *
	 * @param store
	 *            the preference store;
	 */
	@Inject
	public N4JSProjectExplorerContentProvider(final ExternalLibraryPreferenceStore store) {
		store.addListener(this);
		int eventMask = IResourceChangeEvent.PRE_BUILD | IResourceChangeEvent.POST_CHANGE;
		getWorkspace().addResourceChangeListener(this::cleanBuildOrManifestChangedEvent, eventMask);
	}

	private void cleanBuildOrManifestChangedEvent(final IResourceChangeEvent event) {
		if (null == event) {
			return;
		}

		int buildKind = event.getBuildKind();
		if (buildKind == IncrementalProjectBuilder.CLEAN_BUILD) {
			// use case: clean build

			Object obj = event.getSource();
			if (obj instanceof IProject) {
				IProject project = (IProject) obj;
				virtualNodeCache.remove(project);
			}
			if (obj instanceof IWorkspace) {
				virtualNodeCache.clear();
			}
			return;
		}

		if (null == event.getDelta()) {
			return;
		}
		try {
			event.getDelta().accept(delta -> {

				IResource resource = delta.getResource();
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					if (IN4JSProject.PACKAGE_JSON.equals(file.getName())) {
						if (delta.getKind() == IResourceDelta.REMOVED) {
							if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
								// use case: rename of a project to/from the name of a shipped library
								virtualNodeCache.clear();
							}
						}

						if (delta.getKind() == IResourceDelta.CHANGED) {
							IProject project = file.getProject();
							if (null != project && project.isAccessible()) {
								// use case: changes in a package.json file
								virtualNodeCache.remove(project);
							}
						}
					}
				}

				return true;
			});

		} catch (final CoreException e) {
			LOGGER.error("Error while refreshing virtual nodes in navigator.", e);
		}
	}

	@Override
	public Object[] getChildren(final Object element) {

		if (workingSetManagerBroker.isWorkingSetTopLevel()) {
			if (element instanceof IWorkspaceRoot) {
				final WorkingSetManager manager = workingSetManagerBroker.getActiveManager();
				if (manager != null) {
					return manager.getWorkingSets();
				}
			} else if (element instanceof WorkingSet) {
				return ((WorkingSet) element).getElements();
			}
		}

		final Object[] children = super.getChildren(element);

		if (element instanceof IProject && ((IProject) element).isAccessible()) {
			return Arrays2.add(children, getVirtualNodes((IProject) element));
		}

		return children;
	}

	@Override
	public Object getParent(Object element) {

		// Required by editor - navigator linking to locate parent item in tree.
		if (element instanceof IProject && workingSetManagerBroker.isWorkingSetTopLevel()) {
			final WorkingSetManager activeManager = workingSetManagerBroker.getActiveManager();
			if (activeManager != null) {
				for (final WorkingSet workingSet : activeManager.getWorkingSets()) {
					final IAdaptable[] elements = workingSet.getElements();
					if (!Arrays2.isEmpty(elements) && Arrays.contains(elements, element)) {
						return workingSet;
					}
				}
			}
		}

		return super.getParent(element);
	}

	@Override
	public void storeUpdated(final ExternalLibraryPreferenceStore store, final IProgressMonitor monitor) {
		final IProject[] projects = getWorkspace().getRoot().getProjects();
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 1).newChild(projects.length);
		virtualNodeCache.clear();
		for (final IProject project : projects) {
			getVirtualNodes(project);
			subMonitor.worked(1);
		}
	}

	private Object[] getVirtualNodes(final IProject project) {
		Object[] objects = virtualNodeCache.get(project);
		if (null == objects) {
			synchronized (project) {
				objects = virtualNodeCache.get(project);
				if (null == objects) {
					objects = helper.getVirtualNodesForProject(project);
					virtualNodeCache.put(project, objects);
				}
			}
		}
		return objects;
	}

	// ------------------------------------------------------------------------------------
	// IPipelinedTreeContentProvider2
	// ------------------------------------------------------------------------------------

	@Override
	public void init(ICommonContentExtensionSite aConfig) {
		// Nothing to initialize here.
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getPipelinedChildren(final Object aParent, final Set theCurrentChildren) {
		ArrayList<Object> children = Lists.newArrayList(getChildren(aParent));
		theCurrentChildren.clear();
		theCurrentChildren.addAll(children);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getPipelinedElements(final Object anInput, final Set theCurrentElements) {
		ArrayList<Object> children = Lists.newArrayList(getElements(anInput));
		theCurrentElements.clear();
		theCurrentElements.addAll(children);
	}

	@Override
	public Object getPipelinedParent(final Object anObject, final Object aSuggestedParent) {
		return getParent(anObject);
	}

	@Override
	public PipelinedShapeModification interceptAdd(final PipelinedShapeModification anAddModification) {
		return null;
	}

	@Override
	public PipelinedShapeModification interceptRemove(final PipelinedShapeModification aRemoveModification) {
		return null;
	}

	@Override
	public boolean interceptRefresh(final PipelinedViewerUpdate aRefreshSynchronization) {
		return true;
	}

	@Override
	public boolean interceptUpdate(final PipelinedViewerUpdate anUpdateSynchronization) {
		return true;
	}

	@Override
	public void restoreState(final IMemento aMemento) {
		// NOOP
	}

	@Override
	public void saveState(final IMemento aMemento) {
		// NOOP
	}

	@Override
	public boolean hasPipelinedChildren(final Object anInput, final boolean currentHasChildren) {
		return currentHasChildren || getChildren(anInput).length > 0;
	}

}
