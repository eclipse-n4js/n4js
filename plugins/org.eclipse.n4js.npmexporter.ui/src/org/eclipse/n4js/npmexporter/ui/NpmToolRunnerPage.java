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
package org.eclipse.n4js.npmexporter.ui;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardDataTransferPage;

import com.google.common.collect.Lists;

/**
 * Shows parameters to invoke the npm runner.
 */
public class NpmToolRunnerPage extends WizardDataTransferPage {

	private final static String STORE_RUN_NPM_TOOL = "org.eclipse.n4js.npmexporter.ui.NpmToolRunnerPage_RunTool";
	private final static String STORE_NPM_GOAL = "org.eclipse.n4js.npmexporter.ui.NpmToolRunnerPage_NpmGoal";

	private Text textProcessOut;
	private Button runNpmCheckbox;
	private Combo npmGoalSelection;

	/**
	 * Simple page creation.
	 */
	public NpmToolRunnerPage(String pageName) {
		super(pageName);
		setTitle(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		runNpmCheckbox = new Button(composite, SWT.CHECK | SWT.LEFT);
		runNpmCheckbox.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
		runNpmCheckbox.setText("Run npm tool for each project");
		runNpmCheckbox.setFont(parent.getFont());
		runNpmCheckbox.addListener(SWT.Selection, this);

		Label label = createPlainLabel(composite, "npm");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		createOptionsGroup(composite);

		textProcessOut = new Text(composite, SWT.MULTI | SWT.READ_ONLY);
		textProcessOut.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		restoreWidgetValues();

		setControl(composite);

		// sync the page completion
		updatePageCompletion();
	}

	@Override
	protected void createOptionsGroupButtons(Group optionsGroup) {

		npmGoalSelection = new Combo(optionsGroup, SWT.SINGLE
				| SWT.BORDER);
		npmGoalSelection.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		npmGoalSelection.addListener(SWT.Modify, this);
		npmGoalSelection.addListener(SWT.Selection, this);

	}

	@Override
	public void handleEvent(Event event) {
		if (event.widget == runNpmCheckbox ||
				event.widget == npmGoalSelection) {
			updatePageCompletion();
		}
	}

	@Override
	protected boolean allowNewContainerName() {
		return false;
	}

	@Override
	protected boolean validateOptionsGroup() {
		// if not running, this page is complete
		if (!runNpmCheckbox.getSelection())
			return true;

		// should run --> goal should not be empty
		if (npmGoalSelection.getText().trim().length() == 0) {
			// if run is checked there should be something to pass.
			return false;
		}

		return super.validateOptionsGroup();
	}

	/**
	 * @return current goal selection
	 */
	private String getGoalValue() {
		return npmGoalSelection.getText().trim();
	}

	/**
	 * @param npmArguments
	 *            to goal - combobox
	 */
	private void addGoalItem(String npmArguments) {
		npmGoalSelection.add(npmArguments);
	}

	/**
	 * @param npmArguments
	 *            current active arguments
	 */
	private void setGoalValue(String npmArguments) {
		npmGoalSelection.setText(npmArguments);
	}

	/**
	 * Called, if overall export succeeded.
	 */
	public void finish() {
		// store values in success case.
		internalSaveWidgetValues();
	}

	/** save for next usage */
	protected void internalSaveWidgetValues() {

		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			// update goals history
			String[] npmGoalLines = settings
					.getArray(STORE_NPM_GOAL);
			if (npmGoalLines == null) {
				npmGoalLines = new String[0];
			}

			npmGoalLines = addToHistory(npmGoalLines, getGoalValue());
			settings.put(STORE_NPM_GOAL, npmGoalLines);

			// store checkbox - compress
			settings.put(STORE_RUN_NPM_TOOL, runNpmCheckbox.getSelection());
		}

	}

	@Override
	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] npmGoalLines = settings
					.getArray(STORE_NPM_GOAL);
			if (npmGoalLines == null || npmGoalLines.length == 0) {
				// ie.- no settings stored
			} else {

				// destination
				setGoalValue(npmGoalLines[0]);
				for (int i = 0; i < npmGoalLines.length; i++) {
					addGoalItem(npmGoalLines[i]);
				}
			}
			runNpmCheckbox.setSelection(settings.getBoolean(STORE_RUN_NPM_TOOL));
		}
	}

	/** @return true if the tool should be run */
	public boolean isToolrunRequested() {
		return runNpmCheckbox.getSelection();
	}

	/**
	 * Ask the user again.
	 */
	public boolean queryRunTool() {
		String goal = "'npm " + npmGoalSelection.getText() + "'";
		boolean answer = queryYesNoQuestion("Shall we really run the npm command \n\t'" + goal
				+ "'\non every project?");
		System.out.println("should run after user-quest: " + answer);
		return answer;
	}

	/**
	 * @resturn "npm","..."
	 */
	public List<String> getCommand() {
		return Lists.newArrayList("npm", npmGoalSelection.getText());
	}

	/**
	 * Append a line to console. Prefixes with '[stdout] '. Can be called from non-UI-thread.
	 */
	public void appendConsoleOut(String line) {
		appendConsoleRaw(String.format("[stdout] %s%n", line));
	}

	/**
	 * Append a line to console. Prefixes with '[stderr] '. Can be called from non-UI-thread.
	 */
	public void appendConsoleErr(String line) {
		appendConsoleRaw(String.format("[stdERR] %s%n", line));
	}

	/**
	 * Append a line to console. No prefixing. Can be called from non-UI-thread.
	 */
	public void appendText(String line) {
		appendConsoleRaw(String.format("%s%n", line));
	}

	// handles the append via runnable on ui-thread.
	private void appendConsoleRaw(final String line) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				textProcessOut.append(line);
			}
		});
	}

	/**
	 * Blocking-dialog to keep the console in view.
	 */
	public void queryCloseDialog() {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				queryYesNoQuestion("Process is done.");
			}
		});
	}
}
