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

import static org.eclipse.n4js.ui.preferences.external.ButtonFactoryUtil.createDisabledPushButton;
import static org.eclipse.n4js.ui.utils.DelegatingSelectionAdapter.createSelectionListener;

import java.util.Map;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.google.common.base.Strings;

/**
 * UI for the {@link LibrariesFixHandler}, which will use this class for visualizing its long running operation. Since in
 * some (user data depended) scenarios processing has to be suspended in order to get users input, instance of this
 * class will hold reference to the caller. It is assumed that, if needed, the caller will {@link #wait()} for this
 * instance to get user input, and this instance will {@link #notify()} caller when input is obtained.
 */
// TODO refactor to custom UI
// For better user experience refactor the UI to avoid wait-notify mechanisms.
public class DependneciesDialog extends ProgressMonitorDialog {

	private Table tNPMRC = null;
	private Table tN4TP = null;
	private Object suspendedCaller = null;
	private Button proceed = null;
	Composite configsContainer = null;

	String selectedNPMRC = null;
	String selectedN4TP = null;

	/** */
	public DependneciesDialog(Shell parent) {
		super(parent);
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

	/**
	 * Selection handler for user clicking {@link #proceed} button. Main responsibilities of this handler are: process
	 * state of the {@link #tNPMRC} and {@link #tN4TP} tables to allow non-UI threads to read user decision, and to
	 * notify non-UI thread that it can resume.
	 *
	 */
	private void handleUserWantsToProceed(@SuppressWarnings("unused") final SelectionEvent e) {
		if (this.suspendedCaller == null)
			return;

		proceed.setEnabled(false);
		tNPMRC.setEnabled(false);
		tN4TP.setEnabled(false);
		configsContainer.setCursor(super.dialogArea.getCursor());

		processUserSelection();

		// notify other thread to resume
		synchronized (this.suspendedCaller) {
			this.suspendedCaller.notify();
		}
	}

	/** gets user selection for {@code .npmrc}, may be {@code null} */
	public String getNPMRC() {
		return this.selectedNPMRC;
	}

	/** gets user selection for {@code *.n4tp}, may be {@code null} */
	public String getN4TP() {
		return this.selectedN4TP;
	}

	/** Process all selections to prepare data for non-UI thread */
	private void processUserSelection() {
		this.selectedN4TP = getTableItem(tN4TP);
		this.selectedNPMRC = getTableItem(tNPMRC);
	}

	private String getTableItem(Table table) {
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
		return null;
	}

	/**
	 * Calling (non-UI) thread provides data to update config tables ({@link #tNPMRC} and {@link #tN4TP}). Additionally
	 * caller passes reference to itself, so later this UI thread can notify the caller to resume processing.
	 *
	 */
	public void updateConfigs(Map<String, String> npmrcs, Map<String, String> n4tps, LibrariesFixHandler LibrariesFixHandler) {
		addConfigArea();
		if (!npmrcs.isEmpty()) {
			npmrcs.forEach((name, path) -> {
				TableItem npmrc = new TableItem(tNPMRC, SWT.NONE);
				npmrc.setText(0, name);
				npmrc.setText(1, path);
			});
			tNPMRC.getColumn(0).pack();
			tNPMRC.getColumn(1).pack();
		}
		if (!n4tps.isEmpty()) {
			n4tps.forEach((name, path) -> {
				TableItem n4tp = new TableItem(tN4TP, SWT.NONE);
				n4tp.setText(0, name);
				n4tp.setText(1, path);
			});
			tN4TP.getColumn(0).pack();
			tN4TP.getColumn(1).pack();
		}

		proceed.setEnabled(true);
		configsContainer.setVisible(true);
		configsContainer.getParent().getParent().pack(true);
		configsContainer.setCursor(arrowCursor);

		this.suspendedCaller = LibrariesFixHandler;
	}

	/**
	 * Adds new controls to the UI, that allow user to select configuration to be used in underlying operations.
	 */
	private void addConfigArea() {
		tNPMRC = createTable(configsContainer, ".npmrc configurations");
		tN4TP = createTable(configsContainer, "*.n4tp configurations");

		proceed = createDisabledPushButton(configsContainer, "Proceed",
				createSelectionListener(this::handleUserWantsToProceed));

		configsContainer.setVisible(false);
		configsContainer.getParent().getParent().layout(true, true);
	}

	private static Table createTable(Composite parent, String toolTipText) {
		Table table = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setToolTipText(toolTipText);
		TableColumn name = new TableColumn(table, SWT.NONE);
		name.setText("name");
		TableColumn location = new TableColumn(table, SWT.NONE);
		location.setText("Location");
		TableItem defaultItem = new TableItem(table, SWT.NONE);
		defaultItem.setText(0, "default");
		defaultItem.setText(1, "default settings");
		return table;
	}

}
