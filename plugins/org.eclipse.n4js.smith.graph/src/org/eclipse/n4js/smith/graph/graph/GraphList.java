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
package org.eclipse.n4js.smith.graph.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.n4js.smith.graph.Activator;
import org.eclipse.n4js.smith.graph.editoroverlay.EditorOverlay;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * An SWT widget showing a list of graphs plus a graph canvas to show the currently selected graph.
 */
@SuppressWarnings("javadoc")
public class GraphList extends Composite {

	protected final List<ListEntry> entries = new ArrayList<>();

	protected TableViewer listViewer;
	protected GraphCanvas canvas;

	protected static class ListEntry {
		public final String label;
		public final GraphType type;
		public final Graph<?> graph;

		public ListEntry(String label, GraphType type, Graph<?> graph) {
			this.label = label;
			this.type = type;
			this.graph = graph;
		}
	}

	protected class MyLabelProvider extends LabelProvider {
		final Image imageAST = Activator.getInstance().ICON_GRAPH_AST.createImage();
		final Image imageCFG = Activator.getInstance().ICON_GRAPH_CF.createImage();
		final Image imageDFG = Activator.getInstance().ICON_GRAPH_DF.createImage();

		@Override
		public String getText(Object element) {
			return ((ListEntry) element).label;
		}

		@Override
		public Image getImage(Object element) {
			if (((ListEntry) element).type == GraphType.AST) {
				return imageAST;
			}
			if (((ListEntry) element).type == GraphType.CFG)
				return imageCFG;
			return null;
		}
	}

	public GraphList(Composite parent, int style, EditorOverlay editorOverlay) {
		super(parent, style);

		this.setLayout(new FillLayout());

		final SashForm sf = new SashForm(this, SWT.HORIZONTAL);
		sf.setLayout(new FillLayout());

		canvas = new GraphCanvas(sf, SWT.NONE, editorOverlay);

		listViewer = new TableViewer(sf, SWT.MULTI | SWT.V_SCROLL);
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setLabelProvider(new MyLabelProvider());
		listViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				onSelectionChanged(event);
			}
		});
		listViewer.getTable().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// do nothing
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.character == SWT.DEL)
					removeSelectedGraphs(false);
			}
		});

		sf.setWeights(new int[] { 85, 15 });
	}

	public GraphCanvas getCanvas() {
		return canvas;
	}

	public void addGraph(String label, Graph<?> graph, GraphType graphType, boolean select) {
		addEntry(new ListEntry(label, graphType, graph), select);
	}

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

	protected void addEntry(ListEntry entry, boolean select) {
		if (!entries.contains(entry)) {
			entries.add(entry);
			refreshList();
			if (select) {
				listViewer.setSelection(new StructuredSelection(entry));
				listViewer.reveal(entry);
			}
		}
	}

	protected void removeEntry(ListEntry entry) {
		if (entries.remove(entry)) {
			refreshList();
			if (entry.graph == canvas.getGraph())
				canvas.clear();
		}
	}

	protected void removeEntries(@SuppressWarnings("hiding") Collection<?> entries) {
		if (this.entries.removeAll(entries)) {
			refreshList();
			if (entries.stream().anyMatch(
					e -> e instanceof ListEntry && ((ListEntry) e).graph == canvas.getGraph()))
				canvas.clear();
		}
	}

	protected void refreshList() {
		listViewer.setInput(entries.toArray());
	}

	protected ListEntry getSingleSelectedEntry() {
		final IStructuredSelection sel = (IStructuredSelection) listViewer.getSelection();
		final Object obj = sel.size() == 1 ? sel.getFirstElement() : null;
		if (obj instanceof ListEntry)
			return (ListEntry) obj;
		return null;
	}

	protected void onSelectionChanged(@SuppressWarnings("unused") SelectionChangedEvent event) {
		final ListEntry selEntry = getSingleSelectedEntry();
		if (selEntry != null) {
			canvas.setGraph(selEntry.graph);
		} else {
			canvas.clear();
		}
	}

	@Override
	public boolean setFocus() {
		return listViewer.getTable().setFocus();
	}
}
