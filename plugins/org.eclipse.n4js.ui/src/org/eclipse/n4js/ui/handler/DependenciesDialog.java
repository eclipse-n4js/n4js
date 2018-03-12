/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.handler;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.n4js.ui.utils.DelegatingSelectionAdapter;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.google.common.base.Strings;

/**
 * UI for the {@link LibrariesFixHandler}, which will use this class for visualizing its long running operation. Since
 * in some (user data depended) scenarios processing has to be suspended in order to get users input, instance of this
 * class will hold reference to the caller. It is assumed that, if needed, the caller will {@link #wait()} for this
 * instance to get user input, and this instance will {@link #notify()} caller when input is obtained.
 */
public class DependenciesDialog extends ProgressMonitorDialog {
	private static final Logger LOGGER = Logger.getLogger(DependenciesDialog.class);

	/** simple input data validation */
	private static boolean isDataValid(Map<String, String> npmrcs, Map<String, String> n4tps, Object lock) {
		boolean res = true;
		if (lock == null) {
			LOGGER.error("Passed caller lock was null.");
			res = false;
		}
		if (npmrcs == null) {
			LOGGER.error("Passed npmrcs data was null.");
			res = false;
		}
		if (n4tps == null) {
			LOGGER.error("Passed n4tps data was null.");
			res = false;
		}

		return res;
	}

	/** @return table created with the provided data and tool tip, or {@code null} if no data. */
	private static Table createTable(Composite parent, Map<String, String> data, String toolTipText, String label) {
		if (data == null || data.isEmpty())
			return null;

		new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(parent, SWT.NONE).setText(label);
		Table table = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setToolTipText(toolTipText);
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("name");
		TableColumn locationColumn = new TableColumn(table, SWT.NONE);
		locationColumn.setText("Location");
		TableItem defaultItem = new TableItem(table, SWT.NONE);
		defaultItem.setText(0, "default");
		defaultItem.setText(1, "default settings");

		data.forEach((name, path) -> {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, name);
			item.setText(1, path);
		});

		table.getColumn(0).pack();
		table.getColumn(1).pack();
		table.pack(true);

		return table;
	}

	private Composite configsContainer = null;
	private Button proceed = null;
	private String selectedN4TP = null;
	private String selectedNPMRC = null;
	private Object callerLock = null;
	private Table tN4TP = null;
	private Table tNPMRC = null;

	/** */
	public DependenciesDialog(Shell parent) {
		super(parent);
	}

	/**
	 * Adds new controls to the UI, that allow user to select configuration to be used in underlying operations. Calling
	 * (non-UI) thread provides data to update config tables ({@link #tNPMRC} and {@link #tN4TP}). Additionally caller
	 * passes reference to itself, so later this UI thread can notify the caller to resume processing.
	 */
	public void updateConfigs(Map<String, String> npmrcs, Map<String, String> n4tps, Object lock) {
		if (!isDataValid(npmrcs, n4tps, lock)) {
			getProgressMonitor().setCanceled(true);
			notifyCaller();
			return;
		}

		callerLock = lock;

		tNPMRC = createTable(configsContainer, npmrcs, ".npmrc configurations",
				"Please select configuration for '.npmrc'.");

		tN4TP = createTable(configsContainer, n4tps, "*.n4tp configurations",
				"Please select configuration for '.n4tp'.");

		configsContainer.setVisible(true);
		configsContainer.getParent().getParent().pack(true);
		configsContainer.setCursor(arrowCursor);
	}

	/** gets user selection for {@code *.n4tp}, may be {@code null} */
	public String getN4TP() {
		return this.selectedN4TP;
	}

	/** gets user selection for {@code .npmrc}, may be {@code null} */
	public String getNPMRC() {
		return this.selectedNPMRC;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		if (arrowCursor == null) {
			arrowCursor = new Cursor(UIUtils.getDisplay(), SWT.CURSOR_ARROW);
		}
		cancel = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
		cancel.addSelectionListener(new DelegatingSelectionAdapter(this::handleUserWantsToCancel, null));
		cancel.setCursor(arrowCursor);

		proceed = createButton(parent, IDialogConstants.PROCEED_ID, IDialogConstants.PROCEED_LABEL, true);
		proceed.addSelectionListener(new DelegatingSelectionAdapter(this::handleUserWantsToProceed, null));
		proceed.setCursor(arrowCursor);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.FILL);

		container.setLayout(new GridLayout(1, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite superDialogArea = new Composite(container, SWT.LEFT);
		superDialogArea.setLayout(new GridLayout(2, false));
		superDialogArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		super.createDialogArea(superDialogArea);

		configsContainer = new Composite(container, SWT.FILL);
		configsContainer.setLayout(new GridLayout(1, true));
		configsContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		return parent;
	}

	private String getTableItem(Table table) {
		if (table != null) {
			TableItem[] items = table.getSelection();
			if (items.length >= 2) {
				String text = Strings.nullToEmpty(table.getToolTipText());
				if (!text.isEmpty())
					text = " :: " + text;
				throw new RuntimeException("Multiple selections not supported" + text);
			}
			if (items.length == 1) {
				TableItem item = items[0];
				if (item != null) {
					// first is the display name, second is data
					String npmrc = item.getText(1);
					if (!Strings.isNullOrEmpty(npmrc)) {
						return npmrc;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Selection handler for user clicking {@link #cancel} button. Main responsibilities of this handler are: set
	 * canceled flag to allow non-UI threads to read user decision, and to notify non-UI thread that it can resume.
	 *
	 */
	private void handleUserWantsToCancel(@SuppressWarnings("unused") final SelectionEvent e) {
		LOGGER.info("User has canceled the operation.");
		getProgressMonitor().setCanceled(true);
		notifyCaller();
	}

	/**
	 * Selection handler for user clicking {@link #proceed} button. Main responsibilities of this handler are: process
	 * state of the {@link #tNPMRC} and {@link #tN4TP} tables to allow non-UI threads to read user decision, and to
	 * notify non-UI thread that it can resume.
	 *
	 */
	private void handleUserWantsToProceed(@SuppressWarnings("unused") final SelectionEvent e) {
		proceed.setEnabled(false);
		if (tNPMRC != null)
			tNPMRC.setEnabled(false);
		if (tN4TP != null)
			tN4TP.setEnabled(false);
		configsContainer.setCursor(super.dialogArea.getCursor());

		this.selectedN4TP = getTableItem(tN4TP);
		this.selectedNPMRC = getTableItem(tNPMRC);

		notifyCaller();
	}

	private void notifyCaller() {
		if (callerLock == null)
			return;

		synchronized (callerLock) {
			this.callerLock.notify();
		}
	}
}
