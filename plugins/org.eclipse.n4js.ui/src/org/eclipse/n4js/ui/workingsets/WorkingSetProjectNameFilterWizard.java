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
package org.eclipse.n4js.ui.workingsets;

import static org.eclipse.swt.SWT.BORDER;
import static org.eclipse.swt.SWT.CENTER;
import static org.eclipse.swt.SWT.FILL;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Optional;

import org.eclipse.n4js.ui.workingsets.ProjectNameFilterAwareWorkingSetManager.ProjectNameFilterWorkingSet;
import org.eclipse.n4js.utils.collections.Arrays2;

/**
 * Wizard for creating and editing project name filter based working sets.
 */
public class WorkingSetProjectNameFilterWizard extends WorkingSetEditWizard {

	private static final String TITLE = "Project Name Filter Working Set";
	private static final String DESCRIPTION = "Enter a working set name and specify a project name filter using a regular expression.";

	private final AtomicReference<WorkingSet> workingSetRef = new AtomicReference<>();
	private final AtomicReference<String> originalName = new AtomicReference<>();

	@Override
	public void addPages() {
		addPage(new WizardPage("") {

			private Text nameText;
			private Text filterText;

			@Override
			public void createControl(final Composite parent) {

				final Composite composite = new Composite(parent, NONE);
				composite.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).create());
				composite.setLayoutData(GridDataFactory.fillDefaults().align(FILL, FILL).grab(true, true).create());

				new Label(composite, NONE).setText("Working set name:");
				nameText = new Text(composite, BORDER);
				nameText.setLayoutData(GridDataFactory.fillDefaults().align(FILL, CENTER).grab(true, false).create());

				new Label(composite, NONE).setText("Project name filter:");
				filterText = new Text(composite, BORDER);
				filterText.setLayoutData(GridDataFactory.fillDefaults().align(FILL, CENTER).grab(true, false).create());

				setPageComplete(false);
				setControl(composite);

				composite.getDisplay().asyncExec(() -> {
					setTitle(TITLE);
					setDescription(DESCRIPTION);
				});

				final Optional<WorkingSet> editedWorkingSet = getEditedWorkingSet();
				if (editedWorkingSet.isPresent()) {
					final ProjectNameFilterWorkingSet workingSet = (ProjectNameFilterWorkingSet) editedWorkingSet.get();
					workingSetRef.set(workingSet);
					nameText.setText(workingSet.getName());
					filterText.setText(workingSet.getFilter().pattern());
					nameText.selectAll();
					originalName.set(workingSet.getName());
				}

				nameText.addModifyListener(e -> setPageComplete(validatePage()));
				filterText.addModifyListener(e -> setPageComplete(validatePage()));

			}

			@SuppressWarnings("null")
			private boolean validatePage() {

				String errorMessage = null;

				final String name = nameText.getText();
				final String filter = filterText.getText();
				final WorkingSetManager manager = getManager();

				if (manager == null) {
					errorMessage = "No active working set manager is available.";
				}

				if (errorMessage == null) {
					if (name == null || name.trim().length() == 0) {
						errorMessage = "Working set name should be specified.";
					}
				}

				if (errorMessage == null) {
					if (!name.equals(originalName.get())
							// This case ID and name are equal. Intentionally name.
							&& Arrays2.transform(manager.getAllWorkingSets(), ws -> ws.getName()).contains(name)) {
						errorMessage = "A working set already exists with name '" + name + "'.";
					}
				}

				if (errorMessage == null) {
					if (filter == null || filter.trim().length() == 0) {
						errorMessage = "Project name filter should be specified.";
					}
				}

				Pattern pattern = null;

				if (errorMessage == null) {
					try {
						pattern = Pattern.compile(filter);
					} catch (final PatternSyntaxException e) {
						errorMessage = "Invalid project name filter. " + e.getDescription() + ".";
					}
				}

				if (errorMessage != null || pattern == null) {
					workingSetRef.set(null);
				} else {
					workingSetRef.set(new ProjectNameFilterWorkingSet(pattern, name, manager));
				}

				setMessage(errorMessage, ERROR);
				return errorMessage == null;
			}

		});
	}

	@Override
	public Optional<WorkingSet> getWorkingSet() {
		return Optional.fromNullable(workingSetRef.get());
	}

	@Override
	public boolean performFinish() {
		return null != getWorkingSet();
	}

}
