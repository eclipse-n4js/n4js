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
package org.eclipse.n4js.ui.wizard.dependencies;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.google.common.base.Strings;

/**
 * Page used allowing user to set options of the operation executed at the end of the wizard. Note that some some
 * options are available under certain conditions. In {@link #setVisible} we run operation that might populate page with
 * additional options to choose.
 */
public class SetupOptionsPage extends WizardPage {
	private static final String NAME = "Install options page";
	private static final String DESCRIPTION = "Please select npm install options.";

	private static final Logger LOGGER = Logger.getLogger(SetupOptionsPage.class);
	private Table tNPMRC = null;
	private Composite configsContainer = null;
	private Composite container = null;

	/** Provided {@link InstallOptions} will be populated based on user selection. */
	public SetupOptionsPage() {
		super(NAME);
		setTitle(NAME);
		setDescription(DESCRIPTION);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		new Label(container, SWT.NONE).setText("Available options");
		new Label(container, SWT.HORIZONTAL | SWT.SEPARATOR).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Composite optionsContainer = new Composite(container, SWT.NONE);
		optionsContainer.setLayout(new GridLayout(2, false));
		optionsContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		configsContainer = new Composite(container, SWT.FILL);
		configsContainer.setLayout(new GridLayout(1, true));
		configsContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// required to avoid exception thrown in {@code org.eclipse.jface.wizard.Wizard.createPageControls(Composite)}
		setControl(container);
		setPageComplete(false);

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

		data.forEach((path, name) -> {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, name);
			item.setText(1, path);
		});

		table.getColumn(0).pack();
		table.getColumn(1).pack();
		table.pack(true);
		return table;
	}

	/**
	 * Adds new controls to the UI, that allow user to select dynamically calculated configurations for pending
	 * operations.
	 */
	private void updateConfigs(Map<String, String> npmrcs) {

		configsContainer.setVisible(false);
		for (Control kid : configsContainer.getChildren()) {
			kid.dispose();
		}
		configsContainer.pack(true);

		tNPMRC = createTable(configsContainer, npmrcs, ".npmrc configurations",
				"Please select configuration for '.npmrc'.");

		container.layout();
		configsContainer.setVisible(true);
	}

	/** On page entry (re)discover config files. */
	@Override
	public void setVisible(boolean visible) {
		setPageComplete(visible);
		if (visible)
			discoverConfigFilesAndUpdate();
		super.setVisible(visible);
	}

	/** Saves user choices into provided object */
	public void saveOptions(InstallOptions options) {
		if (tNPMRC != null)
			options.npmrc = getTableItem(tNPMRC);
	}

	/** Scan file system for the relevant {@code .npmrc} files. */
	private void discoverConfigFilesAndUpdate() {
		RunnableSettingsFilesLocator runnableSettingsFilesLocator = new RunnableSettingsFilesLocator();
		try {
			getContainer().run(true, true, runnableSettingsFilesLocator);
		} catch (InvocationTargetException | InterruptedException e) {
			LOGGER.error("Error during configuration files discovery.", e);
			UIUtils.getDisplay().asyncExec(() -> {
				String title = "Error during configuration files discovery.";
				String message = "Error scanning files system for the configuration files..\n";
				message += "Please check your Error Log view for the detailed log about the failure.";
				MessageDialog.openError(UIUtils.getShell(), title, message);
			});
		}

		Collection<File> fNPMRCs = runnableSettingsFilesLocator.getCollectedConfigFiles();
		// sorted map by key (path) length in ascending order, good for the UI
		Map<String, String> npmrcs = new TreeMap<>((p1, p2) -> ((Integer) p1.length()).compareTo(p2.length()));
		fNPMRCs.forEach(f -> npmrcs.put(f.getAbsolutePath(), f.getName()));
		updateConfigs(npmrcs);
	}

	/** Expects one row selected in the table and returns value of second row. */
	private String getTableItem(Table table) {
		String result = "";
		if (table == null)
			return result;

		TableItem[] items = table.getSelection();

		if (items.length >= 2) {
			String text = Strings.nullToEmpty(table.getToolTipText());
			if (!text.isEmpty())
				text = " :: " + text;
			throw new RuntimeException("Multiple selections are not supported" + text);
		}

		if (items.length == 1 && items[0] != null)
			// first is the display name, second is data
			result = Strings.nullToEmpty(items[0].getText(1));

		return result;
	}

}
