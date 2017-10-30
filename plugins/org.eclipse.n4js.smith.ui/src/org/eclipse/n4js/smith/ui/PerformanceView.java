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
package org.eclipse.n4js.smith.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.ui.graph.DashboardComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * A view showing a graph of the AST and type model (i.e. TModule).
 */
public class PerformanceView extends ViewPart {
	private final Map<String, DashboardComposite> visualisation = new HashMap<>();
	DashboardComposite visualisationComposite = null;
	private boolean paused = true;
	Composite visualisationParent = null;
	final StackLayout layout = new StackLayout();

	@Override
	public void createPartControl(final Composite parent) {
		this.visualisationParent = parent;
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		parent.setLayout(layout);
		createToolbarActions();
		createMenu();
	}

	private void createToolbarActions() {

		createAction(
				"Pause", IAction.AS_CHECK_BOX,
				"Suspend collecting all data",
				Activator.getInstance().ICON_PAUSE,
				this::onPause).setChecked(paused);
		createAction(
				"Delete ALL data", IAction.AS_PUSH_BUTTON,
				"Delete ALL collected data.",
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE),
				this::onDelete);

	}

	private Action createDynamicAction(String name, Consumer<String> delegate) {
		return new Action(name) {
			@Override
			public void run() {
				delegate.accept(name);
			}
		};
	}

	private void createMenu() {
		IMenuManager mgr = getViewSite().getActionBars().getMenuManager();
		mgr.setRemoveAllWhenShown(true);
		addDynamicVisualisationSelection(mgr);
		mgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager m) {
				addDynamicVisualisationSelection(m);
			}
		});
	}

	private void addDynamicVisualisationSelection(IMenuManager menuManager) {
		Set<String> collectorsKeys = CollectedDataAccess.getCollectorsKeys();
		trashUnreachableVisualisations(collectorsKeys);

		// add actions to the menu
		collectorsKeys
				.forEach(collectorName -> menuManager.add(createDynamicAction(collectorName, this::selectDataSource)));
	}

	/** Remove visualizations that do not have corresponding data source anymore. */
	private void trashUnreachableVisualisations(Set<String> collectorsKeys) {
		Set<String> unreachableKeys = new HashSet<>();
		visualisation.keySet().forEach(key -> {
			if (!collectorsKeys.contains(key))
				unreachableKeys.add(key);
		});
		if (!unreachableKeys.isEmpty())
			unreachableKeys.forEach(visualisation::remove);
	}

	private void selectDataSource(String key) {
		if (!visualisation.containsKey(key)) {
			visualisation.put(key, new DashboardComposite(key, visualisationParent, SWT.NONE));
		}
		this.visualisationComposite = visualisation.get(key);
		this.layout.topControl = this.visualisationComposite;
		visualisationParent.layout();
	}

	private Action createAction(String label, int style, String tooltip, ImageDescriptor image,
			Consumer<Action> onRun) {
		final Action result = new Action(label, style) {
			@Override
			public void run() {
				onRun.accept(this);
			}
		};
		result.setText(label);
		result.setToolTipText(tooltip);
		result.setImageDescriptor(image);
		getViewSite().getActionBars().getToolBarManager().add(result);
		return result;
	}

	/**
	 * Stop collecting all data.
	 */
	protected void onPause(Action action) {
		this.paused = action.isChecked();
		CollectedDataAccess.setPaused(this.paused);
	}

	/**
	 * Delete all data. This removes {@code ALL} gathered data.
	 */
	protected void onDelete(@SuppressWarnings("unused") Action action) {
		// remove data previously rendered in the UI
		this.visualisation.values().forEach(v -> v.removeAllGraphs());
		this.visualisation.clear();

		// remove all data from underlying data sources
		CollectedDataAccess.purgeAllData();
	}

	@Override
	public void setFocus() {
		//
	}
}
