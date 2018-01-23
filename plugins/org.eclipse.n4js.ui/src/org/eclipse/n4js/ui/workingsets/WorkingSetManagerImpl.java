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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.n4js.ui.workingsets.WorkingSet.OTHERS_WORKING_SET_ID;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.n4js.utils.StatusHelper;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Base working set manager which does not allow working set addition, removal and/or edition. The order of the working
 * sets can be modified. This implementation is not thread safe. It is the clients responsibility to synchronize on it
 * when thread safety is required.
 */
public abstract class WorkingSetManagerImpl implements WorkingSetManager, Resetable {

	private static final Logger LOGGER = Logger.getLogger(WorkingSetManagerImpl.class);

	/**
	 * Separator used when persisting key value pairs into the OSGi preference store.
	 */
	protected static final String SEPARATOR = "#";

	/**
	 * An empty string for representing not yet persisted configuration.
	 */
	protected static final String EMPTY_STRING = "";

	private static final String ORDERED_IDS_KEY = ".orderedIds";
	private static final String VISIBLE_IDS_KEY = ".visibleIds";

	/**
	 * List of all working sets. Used internally for caching purposes.
	 */
	private List<WorkingSet> allWorkingSets = null;

	/**
	 * List of visible working sets. Used internally for caching purposes.
	 */
	private List<WorkingSet> visibleWorkingSets = null;

	/**
	 * Ordered list of the working set IDs.
	 */
	protected final List<String> orderedWorkingSetIds = newArrayList();

	/**
	 * A collection of working set IDs that are configured to be visible on the UI.
	 */
	protected final Collection<String> visibleWorkingSetIds = newHashSet();

	/**
	 * Status helper for creating {@link IStatus status} instances in a convenient way.
	 */
	@Inject
	protected StatusHelper statusHelper;

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	@Override
	public String getId() {
		return getClass().getName();
	}

	@Override
	public WorkingSetManagerBroker getWorkingSetManagerBroker() {
		return workingSetManagerBroker;
	}

	@Override
	public WorkingSet[] getWorkingSets() {
		return Iterables.toArray(getOrCreateVisibleWorkingSets(), WorkingSet.class);
	}

	@Override
	public WorkingSet[] getAllWorkingSets() {
		return Iterables.toArray(getOrCreateAllWorkingSets(), WorkingSet.class);
	}

	@Override
	public void select(final Iterable<WorkingSet> workingSets) {
		visibleWorkingSetIds.addAll(from(workingSets).transform(ws -> ws.getId()).toList());
	}

	@Override
	public void unselect(final Iterable<WorkingSet> workingSets) {
		visibleWorkingSetIds.removeAll(from(workingSets).transform(ws -> ws.getId()).toList());
	}

	@Override
	public IStatus saveState(final IProgressMonitor monitor) {

		final Preferences node = getPreferences();

		// Save ordered labels.
		node.put(ORDERED_IDS_KEY, Joiner.on(SEPARATOR).join(orderedWorkingSetIds));

		// Save visible labels.
		node.put(VISIBLE_IDS_KEY, Joiner.on(SEPARATOR).join(visibleWorkingSetIds));

		try {
			node.flush();
		} catch (final BackingStoreException e) {
			final String message = "Error occurred while saving state to preference store.";
			LOGGER.error(message, e);
			return statusHelper.createError(message, e);
		}

		return statusHelper.OK();
	}

	@Override
	public IStatus restoreState(final IProgressMonitor monitor) {

		final Preferences node = getPreferences();

		// Restore ordered labels.
		final String orderedLabels = node.get(ORDERED_IDS_KEY, EMPTY_STRING);
		if (!Strings.isNullOrEmpty(orderedLabels)) {
			orderedWorkingSetIds.clear();
			orderedWorkingSetIds.addAll(Arrays.asList(orderedLabels.split(SEPARATOR)));
		}

		// Restore visible labels.
		final String visibleLabels = node.get(VISIBLE_IDS_KEY, EMPTY_STRING);
		if (!Strings.isNullOrEmpty(visibleLabels)) {
			visibleWorkingSetIds.clear();
			visibleWorkingSetIds.addAll(Arrays.asList(visibleLabels.split(SEPARATOR)));
		}

		discardWorkingSetCaches();

		return statusHelper.OK();
	}

	@Override
	public void updateState(final Diff<WorkingSet> diff) {

		if (!diff.isEmpty()) {
			// Deselect all.
			visibleWorkingSetIds.clear();

			// Select visible ones.
			select(Arrays.asList(diff.getNewItems()));

			// Update order.
			orderedWorkingSetIds.clear();
			for (final WorkingSet workingSet : diff.getNewAllItems()) {
				orderedWorkingSetIds.add(workingSet.getId());
			}

			discardWorkingSetCaches();
			getWorkingSetManagerBroker().fireWorkingSetManagerUpdated(getId(), diff);
		}

	}

	/**
	 * Orders by case sensitive name ordering, if no explicit ordering is specified yet. The
	 * {@link WorkingSet#OTHERS_WORKING_SET_ID Other Projects} reserved working set name/ID is considered to be the
	 * first one.
	 */
	@Override
	public int compare(final WorkingSet left, final WorkingSet right) {
		if (left == null) {
			return right == null ? 0 : 1;
		}

		final String rightId = right.getId();
		final String leftId = left.getId();

		checkNotNull(leftId, "The ID of the working set must not be null. Working set: " + left);
		checkNotNull(rightId, "The ID of the working set must not be null. Working set: " + right);

		if (orderedWorkingSetIds.isEmpty()) {

			if (OTHERS_WORKING_SET_ID.equals(leftId)) {
				return OTHERS_WORKING_SET_ID.equals(rightId) ? 0 : -1;
			}

			if (OTHERS_WORKING_SET_ID.equals(rightId)) {
				return OTHERS_WORKING_SET_ID.equals(leftId) ? 0 : 1;
			}

			final String rightName = right.getName();
			final String leftName = left.getName();

			checkNotNull(leftName, "The name of the working set must not be null. Working set: " + left);
			checkNotNull(rightName, "The name of the working set must not be null. Working set: " + right);

			return leftName.compareTo(rightName);
		}

		return orderedWorkingSetIds.indexOf(leftId) - orderedWorkingSetIds.indexOf(rightId);
	}

	@Override
	public void reset() {
		try {
			getPreferences().clear();
			getPreferences().flush();
			discardWorkingSetState();
			discardWorkingSetCaches();
			restoreState(new NullProgressMonitor());
		} catch (BackingStoreException e) {
			LOGGER.error("Error occurred while clearing persisted state.", e);
		}
	}

	/**
	 * Initializes and returns with all available working sets. The returning list includes the hidden ones as well.
	 *
	 * @return a list of all available working sets.
	 */
	protected abstract List<WorkingSet> initializeWorkingSets();

	/**
	 * Discards the state of caches used by the current manager.
	 */
	protected void discardWorkingSetCaches() {
		allWorkingSets = null;
		visibleWorkingSets = null;
	}

	/**
	 * Discards the state of the working set manager. This state is equivalent with the persisted state. Overriding
	 * methods should call super to make sure the entire state of the working set manager is discarded.
	 */
	protected void discardWorkingSetState() {
		visibleWorkingSetIds.clear();
		orderedWorkingSetIds.clear();
	}

	/**
	 * Returns with all working sets. On demand, creates them by calling {@link #initializeWorkingSets()} internally.
	 *
	 * @return a list of all working sets.
	 */
	protected List<WorkingSet> getOrCreateAllWorkingSets() {

		if (allWorkingSets != null) {
			return allWorkingSets;
		}

		final List<WorkingSet> workingSets = initializeWorkingSets();

		Collections.sort(workingSets, this);

		allWorkingSets = workingSets;
		// visibleWorkingSets = null; // changed allWorkingSets, so need to invalidate derived property
		// visibleWorkingSets

		return allWorkingSets;
	}

	/**
	 * Returns with a list of all visible working sets. On demand creates them, otherwise returns with the cached
	 * instances.
	 *
	 * @return a list of visible working set instances.
	 */
	protected List<WorkingSet> getOrCreateVisibleWorkingSets() {

		if (visibleWorkingSets != null) {
			return visibleWorkingSets;
		}

		// final List<WorkingSet> workingSets = getOrCreateAllWorkingSets();
		//
		// if (visibleWorkingSetIds.isEmpty()) {
		// visibleWorkingSets = newArrayList(workingSets);
		// } else {
		// visibleWorkingSets = from(workingSets).filter(ws -> visibleWorkingSetIds.contains(ws.getId()))
		// .toList();
		// }

		if (allWorkingSets == null) {
			allWorkingSets = getOrCreateAllWorkingSets();
		}

		if (visibleWorkingSetIds.isEmpty()) {
			visibleWorkingSets = newArrayList(allWorkingSets);
		} else {
			visibleWorkingSets = from(allWorkingSets).filter(ws -> visibleWorkingSetIds.contains(ws.getId()))
					.toList();
		}

		return visibleWorkingSets;
	}

	/**
	 * Reloads this working set manager by invalidating its cache and re-triggering the initialization logic.
	 */
	protected void reload() {
		discardWorkingSetCaches();
		saveState(new NullProgressMonitor());

		if (workingSetManagerBroker.isWorkingSetTopLevel()) {
			final WorkingSetManager activeManager = workingSetManagerBroker.getActiveManager();
			if (activeManager != null) {
				if (activeManager.getId().equals(getId())) {
					workingSetManagerBroker.refreshNavigator();
				}
			}
		}
	}

}
