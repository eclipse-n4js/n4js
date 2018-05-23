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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.eclipse.jface.viewers.ColumnWeightData.MINIMUM_WIDTH;
import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.FILL;
import static org.eclipse.swt.SWT.FULL_SELECTION;
import static org.eclipse.swt.SWT.H_SCROLL;
import static org.eclipse.swt.SWT.MULTI;
import static org.eclipse.swt.SWT.NONE;
import static org.eclipse.swt.SWT.V_SCROLL;

import java.util.List;

import org.eclipse.jface.layout.AbstractColumnLayout;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Base column viewer
 */
public abstract class ColumnViewerBuilder<CV extends ColumnViewer, CP extends IContentProvider> {

	private static final int DEFAULT_COLUMN_WIDTH_IN_PIXEL = 100;

	/* default */final List<String> columnLabels;
	private final CP contentProvider;

	/* default */boolean moveable;
	/* default */boolean headerVisible;
	/* default */boolean linesVisible;
	/* default */boolean useHashlookup;
	private boolean resizable;
	private boolean hasBorder;
	private boolean multipleSelection;
	private boolean fullSelection;
	private List<Integer> columnWeights;
	private List<Integer> columnWidthsInPixels;
	private IBaseLabelProvider labelProvider;

	/**
	 * Builds the new column viewer instance.
	 *
	 * @param parent
	 *            the parent composite for the viewer.
	 * @return the new new column viewer.
	 */
	public final CV build(final Composite parent) {

		final Composite container = new Composite(parent, NONE);
		container.setLayout(GridLayoutFactory.fillDefaults().create());
		container.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).align(FILL, FILL).create());

		final Composite composite = new Composite(container, NONE);
		composite.setLayout(GridLayoutFactory.fillDefaults().create());
		composite.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).align(FILL, FILL).create());

		final AbstractColumnLayout layout = createColumnLayout();
		composite.setLayout(layout);

		final CV viewer = createViewer(composite, layout, NONE | V_SCROLL | H_SCROLL | getStyle());

		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		viewer.setUseHashlookup(useHashlookup);

		return viewer;
	}

	/**
	 * Initializes and returns with the new column viewer instance.
	 *
	 * @param parent
	 *            the parent composite.
	 * @param layout
	 *            the column layout for the column viewer.
	 * @param style
	 *            the style bits for the viewer.
	 * @return the new column viewer instance.
	 */
	protected abstract CV createViewer(Composite parent, AbstractColumnLayout layout, int style);

	/**
	 * Creates the column layout for the new viewer.
	 *
	 * @return the new column layout instance for the new viewer.
	 */
	protected abstract AbstractColumnLayout createColumnLayout();

	/**
	 * Creates a new {@link TableViewer table viewer} builder with the given list of table column labels.
	 *
	 * @param columnLabels
	 *            a list of table column labels to use for the new table viewer. Cannot be {@code null}. Must not be
	 *            empty.
	 *
	 */
	protected ColumnViewerBuilder(final List<String> columnLabels, final CP contentProvider) {
		this.columnLabels = checkNotNull(columnLabels, "columnLabels");
		this.contentProvider = checkNotNull(contentProvider, "contentProvider");
		checkArgument(!this.columnLabels.isEmpty(),
				"Empty column labels are not allowed. At least one column should be specified.");

		headerVisible = true;
		linesVisible = true;
		useHashlookup = false;
		labelProvider = new LabelProvider();
		multipleSelection = false;
		fullSelection = true;
		resizable = true;
		hasBorder = false;
		moveable = true;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setHasBorder(final boolean hasBorder) {
		this.hasBorder = hasBorder;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setMovable(final boolean movable) {
		this.moveable = movable;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setResizable(final boolean resizable) {
		this.resizable = resizable;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setHeaderVisible(final boolean headerVisible) {
		this.headerVisible = headerVisible;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setLinesVisible(final boolean linesVisible) {
		this.linesVisible = linesVisible;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setUseHashlookup(final boolean useHashlookup) {
		this.useHashlookup = useHashlookup;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setMultipleSelection(final boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setFullSelection(final boolean fullSelection) {
		this.fullSelection = fullSelection;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setColumnWeights(final List<Integer> columnWeights) {
		this.columnWeights = columnWeights;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setColumnWidthsInPixel(final List<Integer> columnWidthsInPixel) {
		this.columnWidthsInPixels = columnWidthsInPixel;
		return this;
	}

	/***/
	public ColumnViewerBuilder<CV, CP> setLabelProvider(final IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
		return this;
	}

	/***/
	protected ColumnLayoutData createColumnLayoutData(final int columnIndex) {
		if (null == columnWeights) {
			return new ColumnPixelData(getColumnWidthInPixel(columnIndex), resizable);
		}
		return new ColumnWeightData(columnWeights.get(columnIndex), MINIMUM_WIDTH, resizable);
	}

	private int getColumnWidthInPixel(final int columnIndex) {
		return null == columnWidthsInPixels ? DEFAULT_COLUMN_WIDTH_IN_PIXEL : columnWidthsInPixels.get(columnIndex);
	}

	private int getStyle() {
		int style = 0;
		if (multipleSelection) {
			style |= MULTI;
		}
		if (fullSelection) {
			style |= FULL_SELECTION;
		}
		if (hasBorder) {
			style |= BORDER;
		}
		return style;
	}

}
