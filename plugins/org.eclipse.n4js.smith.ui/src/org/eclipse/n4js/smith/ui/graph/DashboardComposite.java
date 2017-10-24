/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.ui.graph;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.ui.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Composite that serves as container for displaying a performance metric.
 */
public class DashboardComposite extends Composite {
	private static final float STACK_BASE_WIDTH = 1000.0f;
	private static final float STACK_BASE_HEIGHT = 10.0f;
	private String key = null;
	private final List<VisualisationSnapshot> entries = new ArrayList<>();

	private ListViewer listViewer;
	private final VisualisationCanvas canvas;
	private final Text text;

	/** {@code key} defines which data source will be used for display. */
	public DashboardComposite(String key, Composite parent, int style) {
		super(parent, style);
		this.key = key;

		this.setLayout(new FillLayout());

		final SashForm sf = new SashForm(this, SWT.HORIZONTAL);
		sf.setLayout(new FillLayout());

		this.canvas = new VisualisationCanvas(sf, SWT.NONE);

		this.text = new Text(sf, SWT.LEFT | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
		text.setText("");

		createVisualisationControls(sf);
		sf.setWeights(new int[] { 45, 45, 10 });
	}

	/** Creates are that allows for controlling this visualization, e.g. tool bar, list of snapshots, etc. */
	private void createVisualisationControls(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));

		// tool bar
		ToolBar bar = new ToolBar(composite, SWT.NONE);
		bar.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		createToolbarActions(bar);

		// data view
		listViewer = new ListViewer(composite, SWT.MULTI | SWT.V_SCROLL);
		listViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setLabelProvider(new VisualisationLabelProvider());
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				onSelectionChanged(event);
			}
		});
		listViewer.getList().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == SWT.DEL)
					removeSelectedGraphs(false); // use tool bar action
			}
		});
	}

	private void createToolbarActions(ToolBar bar) {
		createAction(bar, SWT.PUSH,
				"Snapshot",
				"Take a data snapshot.",
				Activator.getInstance().ICON_SNAPSHOT,
				this::takeSnapshot);
		createAction(bar, SWT.PUSH,
				"Delete",
				"Delete selected snapshots from history.",
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE),
				this::deleteCurrentSelection);
	}

	private void createAction(ToolBar bar, int style, String label, String tooltip, ImageDescriptor image,
			Runnable action) {
		ToolItem item = new ToolItem(bar, style);
		item.setImage(image.createImage());
		item.setText(label);
		item.setToolTipText(tooltip);
		item.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				action.run();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void deleteCurrentSelection() {
		this.removeSelectedGraphs(false);
	}

	/** removes selected graph or all graphs if nothing selected */
	public void removeSelectedGraphs(boolean removeAllIfNothingSelected) {
		ISelection sel = listViewer.getSelection();
		if (!sel.isEmpty()) {
			removeEntries(((IStructuredSelection) sel).toList());
		} else {
			// empty selection:
			if (removeAllIfNothingSelected)
				removeEntries(new ArrayList<>(entries));
		}
	}

	/** deletes all rendered data */
	public void removeAllGraphs() {
		removeEntries(entries);
		clearRenderedData();
	}

	/** clears canvas and text */
	private void clearRenderedData() {
		canvas.clear();
		text.setText("");
	}

	private void onSelectionChanged(@SuppressWarnings("unused") SelectionChangedEvent event) {
		final VisualisationSnapshot selEntry = getSingleSelectedEntry();
		if (selEntry != null) {
			canvas.setGraph(selEntry.graph);
			text.setText(selEntry.text);
		} else {
			clearRenderedData();
		}
	}

	private void removeEntries(@SuppressWarnings("hiding") Collection<?> entries) {
		if (this.entries.removeAll(entries)) {
			refreshList();
			if (entries.stream().anyMatch(
					e -> e instanceof VisualisationSnapshot
							&& ((VisualisationSnapshot) e).graph == canvas.getGraph())) {
				clearRenderedData();
			}
		}
	}

	/** returns currently selected entry or null */
	protected VisualisationSnapshot getSingleSelectedEntry() {
		final IStructuredSelection sel = (IStructuredSelection) listViewer.getSelection();
		final Object obj = sel.size() == 1 ? sel.getFirstElement() : null;
		if (obj instanceof VisualisationSnapshot)
			return (VisualisationSnapshot) obj;
		return null;
	}

	private void takeSnapshot() {
		this.showGraph("");
	}

	/**
	 * Add the given graph to the history and show it in the view.
	 * <p>
	 * Need not be invoked from the UI thread.
	 */
	public void showGraph(String label) {
		if (this.isDisposed())
			return;

		final String prefix = getTimeStamp() + ": ";
		if (Display.getCurrent() != null) {
			// already on UI thread
			addGraph(prefix + label, true);
		} else {
			// not on UI thread
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					addGraph(prefix + label, true);
				}
			});
		}
	}

	/**
	 * creates and renders graph based on the {@link #key}. Based on the {@code select} flag, the new graph might be
	 * selected.
	 */
	public void addGraph(String label, boolean select) {
		if (CollectedDataAccess.hasNestedData(key)) {
			addEntry(StackGraphFactory.buildGraph(key, STACK_BASE_HEIGHT, STACK_BASE_WIDTH, label), select);
		} else {
			Rectangle clientArea = canvas.getClientArea();
			int chartHeight = clientArea.height;
			int chartWidth = clientArea.width;
			addEntry(ChartGraphFactory.buildGraph(key, chartHeight, chartWidth, label), select);
		}
		this.layout();
	}

	private void addEntry(VisualisationSnapshot entry, boolean select) {
		if (!entries.contains(entry)) {
			entries.add(entry);
			refreshList();
			if (select) {
				listViewer.setSelection(new StructuredSelection(entry));
				listViewer.reveal(entry);
			}
		}
	}

	private void refreshList() {
		listViewer.setInput(entries.toArray());
		listViewer.refresh();
	}

	@Override
	public boolean setFocus() {
		return listViewer.getList().setFocus();
	}

	private final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");

	/**
	 * Return time stamp to prepend to a graph's label in the list of graphs.
	 */
	private String getTimeStamp() {
		return dateFormat.format(new Date());
	}
}
