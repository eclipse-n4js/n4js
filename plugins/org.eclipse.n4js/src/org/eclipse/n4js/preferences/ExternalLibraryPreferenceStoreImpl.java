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
package org.eclipse.n4js.preferences;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.emptyList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.external.ExternalLibraryHelper;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Basic external library preference store implementation. This base class is independent from the actual
 * serialization/deserialization and persistence process.
 */
/* default */ abstract class ExternalLibraryPreferenceStoreImpl implements ExternalLibraryPreferenceStore {
	private static final Logger LOGGER = Logger.getLogger(ExternalLibraryPreferenceStoreImpl.class);

	@Inject
	private ExternalLibraryHelper externalLibraryHelper;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	private final Collection<StoreUpdatedListener> listeners;

	private ExternalLibraryPreferenceModel model;
	private ExternalLibraryPreferenceModel lastSavedModel;

	/**
	 * Creates a new external library preference store.
	 */
	protected ExternalLibraryPreferenceStoreImpl() {
		listeners = newHashSet();
		model = getOrCreateModel();
	}

	@Override
	public Collection<FileURI> getLocations() {
		List<FileURI> result = new ArrayList<>(getOrCreateModel().getExternalLibraryLocationsAsUris());
		return result;
	}

	@Override
	public Collection<FileURI> getNodeModulesLocations() {
		return getOrCreateModel().getNodeModulesLocationsAsUris();
	}

	@Override
	public void add(final FileURI location) {
		getOrCreateModel().add(location);
	}

	@Override
	public void remove(final FileURI location) {
		getOrCreateModel().remove(location);
	}

	@Override
	public void moveUp(final FileURI location) {
		getOrCreateModel().moveUp(location);
	}

	@Override
	public void moveDown(final FileURI location) {
		getOrCreateModel().moveDown(location);
	}

	@Override
	public void resetDefaults() {
		model = getDefaults();
	}

	@Override
	public void invalidate() {
		model = doLoad();
	}

	@Override
	public final IStatus save(IProgressMonitor monitor) {
		if (lastSavedModel != null && getOrCreateModel().equals(lastSavedModel)) {
			return new Status(IStatus.OK, "unknown", STATUS_CODE_NO_CHANGES, "", null);
		}

		if (null == monitor) {
			monitor = new NullProgressMonitor();
		}
		final IStatus status = doSave(getOrCreateModel());
		lastSavedModel = doLoad();
		if (null != status && status.isOK()) {
			notifyListeners(monitor);
		}
		return new Status(IStatus.OK, "unknown", STATUS_CODE_SAVED_CHANGES, "", null);
	}

	@Override
	public void addListener(final StoreUpdatedListener listener) {
		if (null != listener) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListener(final StoreUpdatedListener listener) {
		if (null != listener) {
			listeners.remove(listener);
		}
	}

	/**
	 * Attention: This method needs to be called synchronized using ExternalLibraryBuilder#getRule()
	 */
	@Override
	public IStatus synchronizeNodeModulesFolders() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<Path> projectRoots = new LinkedList<>();
		for (IProject project : projects) {
			if (project.isAccessible()) {
				Path path = project.getLocation().toFile().toPath();
				projectRoots.add(path);
			}
		}

		resetDefaults();
		List<Path> locations = nodeModulesDiscoveryHelper.findNodeModulesFolders(projectRoots);
		for (Path location : locations) {
			add(new FileURI(location.toFile()));
		}
		return save(new NullProgressMonitor());
	}

	/**
	 * Saves the given {@link ExternalLibraryPreferenceModel model} instance.
	 *
	 * @param modelToSave
	 *            the model instance to save.
	 * @return a status representing the outcome of the save operation.
	 */
	protected abstract IStatus doSave(ExternalLibraryPreferenceModel modelToSave);

	/**
	 * Returns with the {@link ExternalLibraryPreferenceModel model} instance created and initialized from the persisted
	 * state.
	 *
	 * @return the deserialized model instance.
	 */
	protected abstract ExternalLibraryPreferenceModel doLoad();

	/**
	 * Returns with the default {@link ExternalLibraryPreferenceModel model} instance.
	 *
	 * @return the model instance with the default values.
	 */
	protected ExternalLibraryPreferenceModel getDefaults() {
		return ExternalLibraryPreferenceModel.createDefault();
	}

	/**
	 * Notifies all registered listeners that the store's persistent state has been updated.
	 *
	 * @param monitor
	 *            monitor for the progress.
	 */
	protected void notifyListeners(final IProgressMonitor monitor) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, listeners.size());
		for (final StoreUpdatedListener listener : listeners) {
			listener.storeUpdated(this, subMonitor.newChild(1));
		}
	}

	/**
	 * Returns with the backing model instance. Initializes it if it is not available yet.
	 *
	 * @return the model instance.
	 */
	protected final ExternalLibraryPreferenceModel getOrCreateModel() {
		if (null == model) {
			synchronized (ExternalLibraryPreferenceStoreImpl.class) {
				if (null == model) {
					model = doLoad();
				}
			}
		}
		return model;
	}

	/**
	 * Converts the given external library root location URIs into an iterable of existing external folder locations
	 * URIs.
	 *
	 * @param externalRootLocations
	 *            an iterable of external library root locations.
	 * @return an iterable of URIs pointing to the external project locations nested in the external root locations.
	 */
	@Override
	public Iterable<FileURI> convertToProjectRootLocations(
			Iterable<FileURI> externalRootLocations) {
		return from(externalRootLocations).transformAndConcat(this::getProjectsInLocation);
	}

	/**
	 * Returns an iterable of all contained external projects in the given location.
	 *
	 * Also includes external projects that are contained in a scope-directory in the given location (e.g.
	 * {@code <location>/@scope/<project>}.
	 */
	private final Iterable<FileURI> getProjectsInLocation(FileURI location) {
		if (isExistingFolder(location)) {
			final Iterable<FileURI> directContents = getDirectProjectsInLocation(location);
			final Iterable<FileURI> scopedContents = from(getDirectoryContents(location))
					.filter(f1 -> isExistingFolder(f1))
					.filter(f2 -> externalLibraryHelper.isScopeDirectory(f2.toJavaIoFile()))
					.transformAndConcat(file -> getDirectProjectsInLocation(file));

			return Iterables.concat(scopedContents, directContents);
		}
		return emptyList();
	}

	/**
	 * Returns an iterable of all directly contained external projects in the given location.
	 *
	 * This does not include scoped project in the given location.
	 */
	private final Iterable<FileURI> getDirectProjectsInLocation(FileURI location) {
		if (isExistingFolder(location)) {
			return from(getDirectoryContents(location))
					.filter(f -> isExistingFolder(f))
					.filter(f -> externalLibraryHelper.isExternalProjectDirectory(f));
		}
		return emptyList();
	}

	private final boolean isExistingFolder(FileURI file) {
		return null != file && file.isDirectory();
	}

	private final List<FileURI> getDirectoryContents(FileURI folder) {
		if (null == folder || !folder.isDirectory()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Not a directory: " + folder + ".");
			}
			return emptyList();
		}
		List<FileURI> fileList = Lists.newArrayList(folder.getChildren());
		Collections.sort(fileList, Comparator.comparing(SafeURI::toString));
		return fileList;
	}
}
