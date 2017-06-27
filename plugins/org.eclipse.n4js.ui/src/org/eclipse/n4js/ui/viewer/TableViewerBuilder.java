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

import java.util.List;

import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * Class for building {@link TableViewer table viewer} instance in a fluent manner.
 */
public class TableViewerBuilder extends ColumnViewerBuilder<TableViewer, IStructuredContentProvider> {

	/***/
	public TableViewerBuilder(final List<String> columnLabels) {
		super(columnLabels, ArrayContentProvider.getInstance());
	}

	@Override
	protected TableViewer createViewer(final Composite parent, final AbstractColumnLayout columnLayout, final int style) {
		final TableViewer viewer = new TableViewer(parent, style);
		final Table table = viewer.getTable();
		table.setLinesVisible(linesVisible);
		table.setHeaderVisible(headerVisible);

		int columnIndex = 0;
		for (final String columnLabel : columnLabels) {

			final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, NONE);
			final TableColumn column = viewerColumn.getColumn();
			columnLayout.setColumnData(column, createColumnLayoutData(columnIndex));
			column.setText(columnLabel);
			column.setMoveable(moveable);

			columnIndex++;
		}

		return viewer;
	}

	@Override
	protected AbstractColumnLayout createColumnLayout() {
		return new TableColumnLayout();
	}

}
