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
package org.eclipse.n4js.ui.workingsets;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.graphics.Image;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.utils.Diff;

/**
 * Working set manager that groups projects to contained working sets by applying a regular expression filter on the
 * project names.
 */
public class ProjectNameFilterAwareWorkingSetManager extends WorkingSetManagerImpl implements MutableWorkingSetManager {

	private static final Logger LOGGER = Logger.getLogger(ProjectNameFilterAwareWorkingSetManager.class);

	private static final String ORDERED_FILTERS_KEY = ".orderedFilters";

	/**
	 * Ordered list of the project name filters.
	 */
	private final List<String> orderedWorkingSetFilters = newArrayList();

	@Inject
	private Provider<WorkingSetProjectNameFilterWizard> wizardProvider;

	@Override
	public String getLabel() {
		return "Project Name Filter";
	}

	@Override
	public Optional<Image> getImage() {
		return ImageRef.VARIABLE_TAB.asImage();
	}

	@Override
	public IStatus saveState(final IProgressMonitor monitor) {
		final IStatus superSaveResult = super.saveState(monitor);

		if (superSaveResult.isOK()) {

			final Preferences node = getPreferences();
			node.put(ORDERED_FILTERS_KEY, Joiner.on(SEPARATOR).join(orderedWorkingSetFilters));

			try {
				node.flush();
			} catch (final BackingStoreException e) {
				final String message = "Error occurred while saving state to preference store.";
				LOGGER.error(message, e);
				return statusHelper.createError(message, e);
			}

			return statusHelper.OK();
		}

		return superSaveResult;
	}

	@Override
	public IStatus restoreState(final IProgressMonitor monitor) {
		final IStatus superRestoreResult = super.restoreState(monitor);

		if (superRestoreResult.isOK()) {

			final Preferences node = getPreferences();
			final String orderedFilters = node.get(ORDERED_FILTERS_KEY, EMPTY_STRING);
			if (!Strings.isNullOrEmpty(orderedFilters)) {
				orderedWorkingSetFilters.clear();
				orderedWorkingSetFilters.addAll(Arrays.asList(orderedFilters.split(SEPARATOR)));
			}

			discardWorkingSetCaches();
			return statusHelper.OK();

		}

		return superRestoreResult;
	}

	@Override
	public void updateState(final Diff<WorkingSet> diff) {
		super.updateState(diff);
		if (!diff.isEmpty()) {
			// Update ordered filters.
			orderedWorkingSetFilters.clear();

			for (final WorkingSet workingSet : diff.getNewAllItems()) {
				final ProjectNameFilterWorkingSet nameFilterWorkingSet = (ProjectNameFilterWorkingSet) workingSet;
				orderedWorkingSetFilters.add(nameFilterWorkingSet.getFilter().pattern());
			}

			discardWorkingSetCaches();
			getWorkingSetManagerBroker().fireWorkingSetManagerUpdated(getId(), diff);

		}
	}

	@Override
	protected List<WorkingSet> initializeWorkingSets() {
		checkState(orderedWorkingSetFilters.size() == orderedWorkingSetIds.size(),
				"Expected same number of working set names as working set filters."
						+ "\nNames were: " + Iterables.toString(orderedWorkingSetIds)
						+ "\nFilters were: " + Iterables.toString(orderedWorkingSetFilters));

		if (orderedWorkingSetFilters.isEmpty()) {
			orderedWorkingSetFilters.add(OTHERS_WORKING_SET_ID);
			orderedWorkingSetIds.add(OTHERS_WORKING_SET_ID);
		}

		final int size = orderedWorkingSetFilters.size();
		final WorkingSet[] workingSets = new WorkingSet[size];
		for (int i = 0; i < size; i++) {
			final String regex = orderedWorkingSetFilters.get(i);
			final String name = orderedWorkingSetIds.get(i);
			workingSets[i] = new ProjectNameFilterWorkingSet(Pattern.compile(regex), name, this);
		}

		return Arrays.asList(workingSets);
	}

	@Override
	protected void discardWorkingSetState() {
		super.discardWorkingSetState();
		orderedWorkingSetFilters.clear();
	}

	@Override
	public WorkingSetNewWizard createNewWizard() {
		return wizardProvider.get();
	}

	@Override
	public WorkingSetEditWizard createEditWizard() {
		return wizardProvider.get();
	}

	/**
	 * Working set implementation with project name filter support.
	 */
	public static final class ProjectNameFilterWorkingSet extends DefaultWorkingSetImpl {

		private final Pattern filter;

		/**
		 * Creates a new working set manager with the given name, project name pattern and the container manager.
		 *
		 * @param filter
		 *            the project name filter.
		 * @param id
		 *            the ID of the working set.
		 * @param manager
		 *            the container manager where this working set belongs to.
		 */
		@VisibleForTesting
		public ProjectNameFilterWorkingSet(final Pattern filter, final String id,
				final WorkingSetManager manager) {

			super(id, manager);
			this.filter = filter;
		}

		@Override
		public boolean apply(final IProject project) {
			return filter.matcher(project.getName()).matches();
		}

		/**
		 * Returns with the project name filter pattern.
		 *
		 * @return the project name filter pattern.
		 */
		public Pattern getFilter() {
			return filter;
		}

		@Override
		public String toString() {
			return super.toString() + " [" + filter + "]";
		}

	}

}
