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
import static com.google.common.base.Suppliers.memoize;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.n4js.ui.utils.UIUtils.getDisplay;
import static org.eclipse.n4js.ui.workingsets.WorkingSetManager.EXTENSION_POINT_ID;
import static java.util.Collections.emptyMap;
import static org.eclipse.core.runtime.Platform.getExtensionRegistry;
import static org.eclipse.core.runtime.Platform.isRunning;
import static org.eclipse.core.runtime.Status.OK_STATUS;
import static org.eclipse.ui.PlatformUI.getWorkbench;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerStateChangedListener.WorkingSetManagerChangeEvent;
import org.eclipse.n4js.utils.Diff;
import org.eclipse.n4js.utils.StatusHelper;
import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Implementation of a working set manager broker.
 */
@Singleton
public class WorkingSetManagerBrokerImpl implements WorkingSetManagerBroker {

	private static final Logger LOGGER = Logger.getLogger(WorkingSetManagerBroker.class);

	private static final String CLASS_ATTRIBUTE = "class";
	private static final String QUALIFIER = WorkingSetManagerBroker.class.getName();
	private static final String ACTIVE_MANAGER_KEY = QUALIFIER + ".activeManager";
	private static final String IS_WORKINGSET_TOP_LEVEL_KEY = QUALIFIER + ".isWorkingSetTopLevel";

	private final Injector injector;
	private final StatusHelper statusHelper;
	private final AtomicReference<WorkingSetManager> activeWorkingSetManager;
	private final AtomicBoolean workingSetTopLevel;
	private final AtomicBoolean alreadyQueuedNavigatorRefresh;
	private final Supplier<Map<String, WorkingSetManager>> contributions;
	private final Collection<TopLevelElementChangedListener> topLevelElementChangeListeners;
	private final Collection<WorkingSetManagerStateChangedListener> workingSetManagerStateChangeListeners;

	/**
	 * Creates a new working set broker instance with the given injector and status helper arguments. The injector is
	 * used to inject members into the available contributions. Also restores its most recent state from the preference
	 * store.
	 *
	 * @param injector
	 *            the injector for initializing the contributions.
	 * @param statusHelper
	 *            convenient way to create {@link IStatus status} instances.
	 *
	 */
	@Inject
	private WorkingSetManagerBrokerImpl(final Injector injector, final StatusHelper statusHelper) {
		this.injector = injector;
		this.statusHelper = statusHelper;
		this.activeWorkingSetManager = new AtomicReference<>();
		this.workingSetTopLevel = new AtomicBoolean(false);
		this.alreadyQueuedNavigatorRefresh = new AtomicBoolean(false);
		this.contributions = initContributions();
		topLevelElementChangeListeners = newHashSet();
		workingSetManagerStateChangeListeners = newHashSet();
		restoreState(new NullProgressMonitor());
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			final String pluginId = N4JSActivator.getInstance().getBundle().getSymbolicName();
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			try {
				workspace.addSaveParticipant(pluginId, new SaveParticipantAdapter() {
					@Override
					public void saving(final ISaveContext context) throws CoreException {
						saveState(new NullProgressMonitor());
					}
				});
			} catch (final CoreException e) {
				LOGGER.error("Error occurred while attaching save participant to workspace.", e);
			}
		}
	}

	@Override
	public IStatus saveState(final IProgressMonitor monitor) {
		final Collection<WorkingSetManager> managers = getWorkingSetManagers();
		final SubMonitor subMonitor = SubMonitor.convert(monitor, managers.size() + 1);
		final MultiStatus error = statusHelper.createMultiError("Error occurred while saving state.");
		for (final WorkingSetManager manager : managers) {
			final IStatus result = manager.saveState(subMonitor.newChild(1));
			if (!result.isOK()) {
				error.add(result);
			}
		}
		final IStatus result = saveState();
		if (!result.isOK()) {
			error.add(result);
		}
		return Arrays2.isEmpty(error.getChildren()) ? statusHelper.OK() : error;
	}

	@Override
	public IStatus restoreState(final IProgressMonitor monitor) {
		final Collection<WorkingSetManager> managers = getWorkingSetManagers();
		final SubMonitor subMonitor = SubMonitor.convert(monitor, managers.size() + 1);
		final MultiStatus error = statusHelper.createMultiError("Error occurred while restoring state.");
		for (final WorkingSetManager manager : managers) {
			final IStatus result = manager.restoreState(subMonitor.newChild(1));
			if (!result.isOK()) {
				error.add(result);
			}
		}
		final IStatus result = restoreState();
		if (!result.isOK()) {
			error.add(result);
		}
		return Arrays2.isEmpty(error.getChildren()) ? statusHelper.OK() : error;
	}

	@Override
	public Preferences getPreferences() {
		return InstanceScope.INSTANCE.getNode(QUALIFIER);
	}

	@Override
	public Collection<WorkingSetManager> getWorkingSetManagers() {
		return Collections.unmodifiableCollection(contributions.get().values());
	}

	@Override
	public void setActiveManager(final WorkingSetManager workingSetManager) {
		checkNotNull(workingSetManager, "workingSetManager");
		if (!workingSetManager.equals(activeWorkingSetManager.get())) {
			activeWorkingSetManager.set(workingSetManager);
			saveState();
			refreshNavigator();
		}
	}

	@Override
	public boolean isActiveManager(final WorkingSetManager workingSetManager) {
		return Objects.equal(workingSetManager, activeWorkingSetManager.get());
	}

	@Override
	public WorkingSetManager getActiveManager() {
		return activeWorkingSetManager.get();
	}

	@Override
	public boolean isWorkingSetTopLevel() {
		return workingSetTopLevel.get();
	}

	@Override
	public void setWorkingSetTopLevel(final boolean b) {
		if (b != workingSetTopLevel.get()) {
			workingSetTopLevel.set(b);
			saveState();
			for (final TopLevelElementChangedListener listener : topLevelElementChangeListeners) {
				listener.topLevelElementChanged(workingSetTopLevel.get());
			}
			refreshNavigator();
		}
	}

	@Override
	public void addTopLevelElementChangedListener(final TopLevelElementChangedListener listener) {
		topLevelElementChangeListeners.add(checkNotNull(listener, "listener"));
	}

	@Override
	public void removeTopLevelElementChangedListener(final TopLevelElementChangedListener listener) {
		topLevelElementChangeListeners.remove(checkNotNull(listener, "listener"));
	}

	@Override
	public void addWorkingSetManagerStateChangedListener(final WorkingSetManagerStateChangedListener listener) {
		workingSetManagerStateChangeListeners.add(checkNotNull(listener, "listener"));
	}

	@Override
	public void removeWorkingSetManagerStateChangedListener(final WorkingSetManagerStateChangedListener listener) {
		workingSetManagerStateChangeListeners.remove(checkNotNull(listener, "listener"));
	}

	@Override
	public void refreshNavigator() {
		refreshNavigator(false);
	}

	@Override
	public void fireWorkingSetManagerUpdated(final String id, final Diff<WorkingSet> diff) {
		if (!diff.isEmpty() && isWorkingSetTopLevel()) {
			final WorkingSetManagerChangeEvent event = new WorkingSetManagerChangeEvent(id, diff);
			getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					for (final WorkingSetManagerStateChangedListener listener : workingSetManagerStateChangeListeners) {
						listener.workingSetManagerStateChanged(event);
					}
				}

			});
		}
	}

	/**
	 * (non-API)
	 *
	 * <p>
	 * Resets the actual and the persistent state of the current broker instance and of all available working set
	 * managers. This method does not remove any registered listeners. Also refreshes the common navigator view.
	 *
	 * <p>
	 * This method is exposed only for testing and recovery purposes. It is highly discouraged to invoke from production
	 * code.
	 */
	@VisibleForTesting
	public void resetState() {

		for (final Resetable resetable : from(getWorkingSetManagers()).filter(Resetable.class)) {
			resetable.reset();
		}

		try {
			getPreferences().clear();
			getPreferences().flush();
			workingSetTopLevel.set(false);
			for (final TopLevelElementChangedListener listener : topLevelElementChangeListeners) {
				listener.topLevelElementChanged(workingSetTopLevel.get());
			}
			final Collection<WorkingSetManager> managers = getWorkingSetManagers();
			if (!managers.isEmpty()) {
				activeWorkingSetManager.set(managers.iterator().next());
			} else {
				activeWorkingSetManager.set(null);
			}

			refreshNavigator(true);
		} catch (final BackingStoreException e) {
			LOGGER.error("Error occurred while reseting persisted the state.", e);
		}

	}

	/**
	 * Sugar for {@link #refreshNavigator() refreshing the navigator}. If the {@code resetInput} argument is
	 * {@code true}, then the viewer input will be reset, otherwise just a refresh will be performed on the underlying
	 * viewer.
	 */
	private void refreshNavigator(final boolean resetInput) {
		UIUtils.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				final IWorkbench workbench = getWorkbench();
				final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					final IWorkbenchPage page = window.getActivePage();
					if (page != null) {
						final IViewPart view = page.findView(ProjectExplorer.VIEW_ID);
						if (view instanceof ProjectExplorer) {
							asyncRefreshCommonViewer((ProjectExplorer) view, resetInput);
						} else {
							if (alreadyQueuedNavigatorRefresh.compareAndSet(false, true)) {
								final IPartListener2 listener = new PartListener2Adapter() {

									@Override
									public void partActivated(final IWorkbenchPartReference partRef) {
										@SuppressWarnings("hiding")
										final IViewPart view = partRef.getPage().findView(ProjectExplorer.VIEW_ID);
										if (view instanceof ProjectExplorer) {
											asyncRefreshCommonViewer((ProjectExplorer) view, resetInput);
											// Already refreshed remove itself from the listeners.
											page.removePartListener(this);
											alreadyQueuedNavigatorRefresh.compareAndSet(true, false);
										}
									}

								};

								// Refresh on next activation.
								page.addPartListener(listener);
							}
						}
					}
				}
			}

		});
	}

	private void asyncRefreshCommonViewer(final ProjectExplorer explorer, final boolean resetInput) {

		// do deferred initialization
		this.getWorkingSetManagers().stream()
				.filter(m -> (m instanceof IDeferredInitializer))
				.map(m -> (IDeferredInitializer) m)
				.filter(m -> m.isInitializationRequired())
				.forEach(m -> {
					m.lateInit();
				});

		final CommonViewer viewer = explorer.getCommonViewer();
		final Display d = getDisplay();
		if (!d.isDisposed()) {
			if (resetInput) {
				d.asyncExec(() -> {
					if (!viewer.getTree().isDisposed())
						viewer.setInput(viewer.getInput());
				});
			} else {
				d.asyncExec(() -> {
					if (!viewer.getTree().isDisposed())
						viewer.refresh(true);
				});
			}
		}
	}

	private IStatus saveState() {

		final Preferences node = getPreferences();
		// Top level element.
		node.putBoolean(IS_WORKINGSET_TOP_LEVEL_KEY, workingSetTopLevel.get());

		// Active working set manager.
		final WorkingSetManager activeManager = getActiveManager();
		final String activeId = activeManager == null ? null : activeManager.getId();
		node.put(ACTIVE_MANAGER_KEY, Strings.nullToEmpty(activeId));

		try {
			node.flush();
			return OK_STATUS;
		} catch (final BackingStoreException e) {
			final String message = "Unexpected error when trying to persist working set broker state.";
			LOGGER.error(message, e);
			return statusHelper.createError(message, e);
		}
	}

	private IStatus restoreState() {

		final Preferences node = getPreferences();
		// Top level element.
		workingSetTopLevel.set(node.getBoolean(IS_WORKINGSET_TOP_LEVEL_KEY, false));

		// Active working set manager.
		final String value = node.get(ACTIVE_MANAGER_KEY, "");
		WorkingSetManager workingSetManager = contributions.get().get(value);
		if (workingSetManager == null) {
			if (!contributions.get().isEmpty()) {
				workingSetManager = contributions.get().values().iterator().next();
			}
		}
		if (workingSetManager != null) {
			setActiveManager(workingSetManager);
		}

		return Status.OK_STATUS;
	}

	private Supplier<Map<String, WorkingSetManager>> initContributions() {

		return memoize(() -> {

			if (!isRunning()) {
				return emptyMap();
			}

			final Builder<String, WorkingSetManager> builder = ImmutableMap.builder();
			final IConfigurationElement[] elements = getExtensionRegistry()
					.getConfigurationElementsFor(EXTENSION_POINT_ID);
			for (final IConfigurationElement element : elements) {
				try {
					final WorkingSetManager manager = (WorkingSetManager) element
							.createExecutableExtension(CLASS_ATTRIBUTE);
					injector.injectMembers(manager);
					builder.put(manager.getId(), manager);
				} catch (final CoreException e) {
					LOGGER.error("Error while trying to instantiate working set manager.", e);
				}
			}

			return builder.build();
		});
	}

}
