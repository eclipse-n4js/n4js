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
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.collections.Arrays2;

import com.google.common.collect.Iterables;
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
	public Collection<URI> getLocations() {
		List<URI> result = new ArrayList<>(getOrCreateModel().getExternalLibraryLocationsAsUris());
		return result;
	}

	@Override
	public Collection<URI> getNodeModulesLocations() {
		return getOrCreateModel().getNodeModulesLocationsAsUris();
	}

	@Override
	public void add(final URI location) {
		getOrCreateModel().add(location);
	}

	@Override
	public void remove(final URI location) {
		getOrCreateModel().remove(location);
	}

	@Override
	public void moveUp(final URI location) {
		getOrCreateModel().moveUp(location);
	}

	@Override
	public void moveDown(final URI location) {
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
			add(location.toUri());
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
	public Iterable<URI> convertToProjectRootLocations(Iterable<URI> externalRootLocations) {
		return from(externalRootLocations).transformAndConcat(this::getProjectsInLocation);
	}

	/**
	 * Returns an iterable of all contained external projects in the given location.
	 *
	 * Also includes external projects that are contained in a scope-directory in the given location (e.g.
	 * {@code <location>/@scope/<project>}.
	 */
	private final Iterable<URI> getProjectsInLocation(URI location) {
		final File rootFolder = new File(location);
		if (isExistingFolder(rootFolder)) {
			final Iterable<URI> directContents = getDirectProjectsInLocation(
					rootFolder.toURI());
			final Iterable<URI> scopedContents = from(getDirectoryContents(rootFolder))
					.filter(f1 -> isExistingFolder(f1))
					.filter(f2 -> externalLibraryHelper.isScopeDirectory(f2))
					.transformAndConcat(file -> getDirectProjectsInLocation(file.toURI()));

			return Iterables.concat(scopedContents, directContents);
		}
		return emptyList();
	}

	/**
	 * Returns an iterable of all directly contained external projects in the given location.
	 *
	 * This does not include scoped project in the given location.
	 */
	private final Iterable<URI> getDirectProjectsInLocation(URI location) {
		final File rootFolder = new File(location);
		if (isExistingFolder(rootFolder)) {
			return from(getDirectoryContents(rootFolder))
					.filter(f -> isExistingFolder(f))
					.filter(f -> !Files.isSymbolicLink(f.toPath()))
					.filter(f -> externalLibraryHelper.isExternalProjectDirectory(f))
					.transform(file -> file.toURI());
		}
		return emptyList();
	}

	private final boolean isExistingFolder(File file) {
		return null != file && file.isDirectory();
	}

	private final Iterable<File> getDirectoryContents(File folder) {
		if (null == folder || !folder.isDirectory()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Not a directory: " + folder + ".");
			}
			return emptyList();
		}

		final File[] files = folder.listFiles();
		if (Arrays2.isEmpty(files)) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("No resources were found under: " + folder + ".");
			}
			return emptyList();
		}

		List<File> fileList = asList(files);
		Collections.sort(fileList);
		return fileList;
	}
}
