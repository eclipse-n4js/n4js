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

import java.util.Comparator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.util.Strings;
import org.osgi.service.prefs.Preferences;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/**
 * Representation of a working set manager.
 */
public interface WorkingSetManager extends Comparator<WorkingSet>, MementoAware {

	/**
	 * The unique ID of the {@code workingSetManager} extension point.
	 */
	String EXTENSION_POINT_ID = N4JSActivator.getInstance().getBundle().getSymbolicName()
			+ "." + Strings.toFirstLower(WorkingSetManager.class.getSimpleName());

	/**
	 * An empty array of working sets.
	 */
	WorkingSet[] EMPTY_ARRAY = new WorkingSet[0];

	/**
	 * Returns with the unique identifier of the working set manager.
	 *
	 * @return the unique ID.
	 */
	String getId();

	/**
	 * Returns with the human readable name of the working set manager.
	 *
	 * @return the human readable name of the manager.
	 */
	String getLabel();

	/**
	 * Returns with the image of the working set manager. By default returns with an {@link Optional#absent() absent}
	 * image, hence no image will be visible for the working set manager on the UI. Clients may override this method to
	 * contribute custom implementations.
	 *
	 * @return the image for the working set manager.
	 */
	default Optional<Image> getImage() {
		return Optional.absent();
	}

	/**
	 * Returns with an iterable of visible working sets that are manager by the current instance. Invisible working sets
	 * are excluded from the returning result.
	 *
	 * @return an iterable of visible working sets.
	 */
	WorkingSet[] getWorkingSets();

	/**
	 * Returns with an iterable of all working sets that are manager by the current instance. Invisible working sets are
	 * included in the returning result.
	 *
	 * @return an iterable of all working sets. Including visible and invisible ones as well.
	 */
	WorkingSet[] getAllWorkingSets();

	/**
	 * Updates its internal state based on the {@link Diff diff} argument. Clients should call
	 * {@link #saveState(IProgressMonitor)} if they want to persist the updated state.
	 *
	 * @param diff
	 *            the difference describing the changes were made on the actual instance.
	 */
	void updateState(Diff<WorkingSet> diff);

	/**
	 * Marks the argument working sets as a selected one. Selected working sets will be visible for the CNF (Common
	 * Navigator Framework). This method has no side effect if an already selected working set is marked to be selected
	 * again. It will keep its selected state as it was before calling that method. Also this method ignores all those
	 * working sets that are not managed by the current manager instance.
	 *
	 * @param workingSets
	 *            the working sets that has to be marked as selected ones.
	 */
	void select(Iterable<WorkingSet> workingSets);

	/**
	 * Sugar for {@link #select(Iterable)}. Marks the selected working sets to be visible.
	 *
	 * @param first
	 *            the first working set to be marked as selected.
	 * @param others
	 *            other optional working sets to be marked as visible.
	 */
	default void select(final WorkingSet first, final WorkingSet... others) {
		select(Lists.asList(first, others));
	}

	/**
	 * Marks the argument working sets as a unselected one. Unselected working sets will be invisible for the CNF
	 * (Common Navigator Framework). This method has no side effect if an already invisible working set is marked to be
	 * unselected again. It will keep its selected state as it was before calling that method. Also this method ignores
	 * all those working sets that are not managed by the current manager instance.
	 *
	 * @param workingSets
	 *            the working sets that has to be marked as unselected ones.
	 */
	void unselect(Iterable<WorkingSet> workingSets);

	/**
	 * Sugar for {@link #unselect(Iterable)}. Marks the selected working sets to be invisible.
	 *
	 * @param first
	 *            the first working set to be marked as unselected.
	 * @param others
	 *            other optional working sets to be marked as invisible.
	 */
	default void unselect(final WorkingSet first, final WorkingSet... others) {
		unselect(Lists.asList(first, others));
	}

	/**
	 * Configures the state of the working set manager.
	 */
	default void configure() {
		new WorkingSetConfigurationDialog(this).open();
	}

	/**
	 * By default returns with {@code 0}. Clients may override it.
	 *
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	default int compare(final WorkingSet left, final WorkingSet right) {
		return 0;
	}

	@Override
	default Preferences getPreferences() {
		return InstanceScope.INSTANCE.getNode(getId());
	}

	/**
	 * Returns with the working set manager broker.
	 *
	 * @return the broker for all available working set managers.
	 */
	WorkingSetManagerBroker getWorkingSetManagerBroker();

	public void discardWorkingSetCaches();
}
