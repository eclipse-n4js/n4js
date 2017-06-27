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
package org.eclipse.n4js.ui.viewer;

import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.VIRTUAL;

import java.util.List;

import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

/**
 * Class for building {@link TreeViewer tree viewer} instance in a fluent manner.
 */
public class TreeViewerBuilder extends ColumnViewerBuilder<TreeViewer, IContentProvider> {

	private boolean virual;

	/***/
	public TreeViewerBuilder(final List<String> columnLabels, final IContentProvider contentProvider) {
		super(columnLabels, contentProvider);
	}

	@Override
	protected TreeViewer createViewer(final Composite parent, final AbstractColumnLayout columnLayout,
			final int style) {
		final TreeViewer viewer = new TreeViewer(parent, virual ? (style | VIRTUAL) : style);
		final Tree tree = viewer.getTree();
		tree.setLinesVisible(linesVisible);
		tree.setHeaderVisible(headerVisible);

		int columnIndex = 0;
		for (final String columnLabel : columnLabels) {

			final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, NONE);
			final TreeColumn column = viewerColumn.getColumn();
			columnLayout.setColumnData(column, createColumnLayoutData(columnIndex));
			column.setText(columnLabel);
			column.setMoveable(moveable);

			columnIndex++;
		}

		return viewer;
	}

	/***/
	public TreeViewerBuilder setVirtual(boolean virtual) {
		this.virual = virtual;
		return this;
	}

	@Override
	protected AbstractColumnLayout createColumnLayout() {
		return new TreeColumnLayout();
	}

}
