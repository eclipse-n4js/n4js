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
package org.eclipse.n4js.ui.compare;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.n4js.compare.ProjectCompareResult;
import org.eclipse.n4js.compare.ProjectComparison;
import org.eclipse.n4js.compare.ProjectComparisonEntry;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import com.google.common.base.Joiner;

/**
 * UI widget to show comparisons of API and implementation projects.
 */
public class ProjectCompareTree extends TreeViewer {

	/** Maximum number of implementations shown side-by-side if there is no focus implementation. */
	private static final int NUM_OF_IMPLEMENTATION_COLUMNS = 4;
	/** Titles of special columns showing additional information if there is a focus implementation. */
	private static final String[] SPECIAL_COLUMN_TITLES = { "Status", "Description", "Documentation" };
	/** Number of special columns showing additional information if there is a focus implementation. */
	private static final int NUM_OF_SPECIAL_COLUMNS = SPECIAL_COLUMN_TITLES.length;
	/**
	 * Number of columns (some may be hidden, depending on the number of implementations and whether there is a focus
	 * implementation).
	 */
	private static final int NUM_OF_COLUMNS = Math.max(1 + NUM_OF_IMPLEMENTATION_COLUMNS, 2 + NUM_OF_SPECIAL_COLUMNS);

	/** Color used for non-compliant differences. */
	protected final Color col_diff_error_fg;
	/** Color used for compliant differences. */
	protected final Color col_diff_conformant_fg;
	/** Background color used for highlighting classifiers. */
	protected final Color col_classifier_bg;

	/***/
	protected final ProjectCompareTreeHelper projectCompareTreeHelper;

	/**
	 * Project comparison to show or <code>null</code> to show an empty widget.
	 */
	protected ProjectComparison comparison = null;
	/**
	 * The index of the one implementation to focus on (all other implementations will be hidden; columns of index 2 and
	 * above will show additional information for the focus implementation) or -1 to show all available implementations
	 * side-by-side.
	 */
	protected int focusImplIndex = -1;

	/** Cached documentation for some entries. */
	protected Map<ProjectComparisonEntry, String> cachedDocumentation = null;

	private class MyLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

		@Override
		public String getColumnText(Object element, int columnIndex) {
			final ProjectComparisonEntry entry = (ProjectComparisonEntry) element;
			final int idx = getEntryIndexForColumnIndex(columnIndex);
			if (idx >= -1 && idx < entry.getImplCount()) {
				final StringBuffer sb = new StringBuffer();
				sb.append(columnIndex == 0 ? entry.getTextAPI() : entry.getTextImpl(idx));
				if (columnIndex == 0 ? entry.isInherited() : entry.isInherited(idx))
					sb.append(" (inherited)");
				if (columnIndex == 0 ? entry.isOverride() : entry.isOverride(idx))
					sb.append(" (override)");
				return sb.toString();
			} else if (idx == -2) {
				// special columns in focus implementation mode
				return getSpecialColumnText(entry, columnIndex - 2);
			}
			return null;
		}

		private String getSpecialColumnText(ProjectComparisonEntry entry, int specialColumnIndex) {
			if (focusImplIndex < 0 || focusImplIndex >= entry.getImplCount())
				throw new IllegalStateException(); // should never happen
			if (specialColumnIndex == 0) {
				// special column showing the compare status
				final ProjectCompareResult result = projectCompareTreeHelper.compareApiImpl(entry, focusImplIndex);
				switch (result.status) {
				case EQUAL:
					return null; // empty cell if identical
				case COMPLIANT:
					return "diff";
				case ERROR:
					return "*ERROR*";
				}
			} else if (specialColumnIndex == 1) {
				// special column showing description of comparison result
				final ProjectCompareResult result = projectCompareTreeHelper.compareApiImpl(entry, focusImplIndex);
				return result.description;
			} else if (specialColumnIndex == 2) {
				// special column showing @api tag in jsdoc of API and focus implementation
				return cachedDocumentation != null ? cachedDocumentation.get(entry) : null;
			}
			return null;
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 0) {
				final ProjectComparisonEntry entry = (ProjectComparisonEntry) element;
				final EObject api = entry.getElementAPI();
				if (api != null)
					return projectCompareTreeHelper.getImage(api);
			}
			return null;
		}

		@Override
		public Color getForeground(Object element, int columnIndex) {
			if (columnIndex == 0)
				return null;
			final int idx = getEntryIndexForColumnIndex(columnIndex);
			if (idx >= 0) {
				final ProjectCompareResult result = projectCompareTreeHelper
						.compareApiImpl((ProjectComparisonEntry) element, idx);
				switch (result.status) {
				case EQUAL:
					return null; // default color if identical
				case COMPLIANT:
					return col_diff_conformant_fg;
				case ERROR:
					return col_diff_error_fg;
				}
			}
			return null;
		}

		@Override
		public Color getBackground(Object element, int columnIndex) {
			for (EObject currElem : ((ProjectComparisonEntry) element).getAllElements())
				if (currElem instanceof TClassifier || currElem instanceof TEnum)
					return col_classifier_bg;
			return null;
		}

		private int getEntryIndexForColumnIndex(int columnIndex) {
			if (focusImplIndex >= 0) {
				// we have a focus implementation
				if (columnIndex == 0)
					return -1; // show API
				else if (columnIndex == 1)
					return focusImplIndex; // show focus implementation
				else {
					return -2; // show special columns
				}
			} else {
				// no focus implementation
				return columnIndex - 1; // show API or implementation
			}
		}
	}

	private class MyContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ((ProjectComparison) inputElement).getEntries();
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((ProjectComparisonEntry) element).hasChildren();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return ((ProjectComparisonEntry) parentElement).getChildren();
		}

		@Override
		public Object getParent(Object element) {
			return ((ProjectComparisonEntry) element).getParent();
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// ignore
		}

		@Override
		public void dispose() {
			// ignore
		}
	}

	/**
	 * Create an instance.
	 */
	public ProjectCompareTree(Composite parent, int style, ProjectCompareTreeHelper projectCompareTreeHelper) {
		super(parent, style);
		this.projectCompareTreeHelper = projectCompareTreeHelper;

		parent.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				onDispose();
			}
		});

		col_diff_error_fg = new Color(parent.getDisplay(), 255, 40, 40);
		col_diff_conformant_fg = new Color(parent.getDisplay(), 60, 127, 95);
		col_classifier_bg = new Color(parent.getDisplay(), 200, 220, 250);

		final Tree tree = getTree();
		for (int n = 0; n < NUM_OF_COLUMNS; n++) {
			final TreeColumn colN = new TreeColumn(tree, SWT.LEFT);
			if (n == 0)
				colN.setWidth(300); // make API column a bit wider
			else
				colN.setWidth(200);
		}

		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		setLabelProvider(new MyLabelProvider());
		setContentProvider(new MyContentProvider());

		setComparison(null, null, null);
	}

	/**
	 * Creates a new default comparison of all API / implementation projects in the default workspace (i.e. the one
	 * accessed via {@link IN4JSCore}) and shows this comparison in the widget.
	 */
	public void setComparison() {
		final ProgressMonitorDialog dlg = new ProgressMonitorDialog(getTree().getShell());
		try {
			dlg.run(false, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					setComparison(monitor);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			// ignore
		}
	}

	/**
	 * Like {@link #setComparison()}, but a custom progress monitor can be provided (may be <code>null</code> if no
	 * progress monitor should be used).
	 */
	public void setComparison(IProgressMonitor monitor) {
		if (monitor != null) {
			monitor.beginTask("Building comparison for API / implementation projects in workspace ...",
					IProgressMonitor.UNKNOWN);
			monitor.worked(1);
		}
		final List<String> errorMessages = new ArrayList<>();
		if (monitor != null) {
			monitor.subTask("Comparing projects in workspace ...");
			monitor.worked(1);
		}
		final ProjectComparison newComparison = projectCompareTreeHelper.createComparison(true, errorMessages);
		if (errorMessages.isEmpty()) {
			// if there is exactly one implementation, then focus on that; otherwise do not focus on an
			// implementation (i.e. show all implementations side-by-side)
			final N4JSProjectName newFocusImplId;
			if (newComparison != null && newComparison.getImplCount() == 1)
				newFocusImplId = newComparison.getImplId(0);
			else
				newFocusImplId = null;
			setComparison(newComparison, newFocusImplId, monitor);
		} else {
			setComparison(null, null, null);
			MessageDialog.openError(
					getTree().getShell(),
					"Setup Invalid",
					"The API / implementation setup is invalid:\n" + Joiner.on('\n').join(errorMessages));
		}
		if (monitor != null)
			monitor.done();
	}

	/**
	 * Show the given comparison in the widget. All arguments may be <code>null</code>.
	 *
	 * @param comparison
	 *            the comparison to show in the widget or <code>null</code> to clear the widget.
	 * @param focusImplId
	 *            the ID of a single implementation to focus on (all other implementations will be hidden; columns of
	 *            index 2 and above will show additional information for this focus implementation) or <code>null</code>
	 *            to show all available implementations side-by-side (but without additional information in separate
	 *            columns).
	 * @param monitor
	 *            the progress monitor to use or <code>null</code>.
	 */
	public void setComparison(ProjectComparison comparison, N4JSProjectName focusImplId, IProgressMonitor monitor) {
		this.comparison = comparison;
		this.focusImplIndex = comparison != null && focusImplId != null ? comparison.getImplIndex(focusImplId) : -1;
		this.cachedDocumentation = null;

		// in case of a focus implementation, read documentation for API and the focus implementation into cache
		if (comparison != null && focusImplId != null) {
			if (monitor != null) {
				monitor.subTask("Scanning jsdoc ...");
				monitor.worked(1);
			}
			final int focusImplIdx = comparison.getImplIndex(focusImplId);
			cachedDocumentation = projectCompareTreeHelper.readDocumentation(comparison, new int[] { focusImplIdx });
		}

		if (monitor != null) {
			monitor.subTask("Updating UI ...");
			monitor.worked(1);
		}
		setInput(comparison);
		refreshColumnHeaders();
		expandAll();
	}

	/**
	 * Refresh titles of columns.
	 */
	protected void refreshColumnHeaders() {
		setColumnHeader(0, "API"); // column 0 is always the API column
		// clear old titles
		for (int idx = 1; idx < NUM_OF_COLUMNS; idx++)
			setColumnHeader(idx, "");
		// set new column titles
		if (comparison != null) {
			if (focusImplIndex >= 0) {
				setColumnHeader(1, comparison.getImplId(focusImplIndex).getRawName());
				for (int specialColumnIdx = 0; specialColumnIdx < NUM_OF_SPECIAL_COLUMNS; specialColumnIdx++)
					setColumnHeader(2 + specialColumnIdx, SPECIAL_COLUMN_TITLES[specialColumnIdx]);
			} else {
				for (int implColumnIdx = 0; implColumnIdx < NUM_OF_IMPLEMENTATION_COLUMNS; implColumnIdx++)
					setColumnHeader(1 + implColumnIdx, comparison.getImplId(implColumnIdx).getRawName());
			}
		}
	}

	private void setColumnHeader(int idx, String label) {
		getTree().getColumn(idx).setText(label != null ? label : "");
	}

	/**
	 * Dispose of colors.
	 */
	protected void onDispose() {
		if (col_diff_error_fg != null)
			col_diff_error_fg.dispose();
		if (col_diff_conformant_fg != null)
			col_diff_conformant_fg.dispose();
		if (col_classifier_bg != null)
			col_classifier_bg.dispose();
	}
}
