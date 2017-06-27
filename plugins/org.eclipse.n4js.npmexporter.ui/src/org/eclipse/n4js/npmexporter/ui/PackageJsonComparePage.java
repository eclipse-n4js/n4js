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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.WizardDataTransferPage;

import org.eclipse.n4js.npmexporter.NpmExporter.MergeResult;
import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Shows parameters to invoke the npm runner.
 */
public class PackageJsonComparePage extends WizardDataTransferPage {

	private List<MergeResult> mergeResults = newArrayList();
	private Combo comboProjects;
	// visible selection-index from comboProjects.
	private int currentIdx;

	private CompareViewerPane pane;
	private TextMergeViewer fViewer;

	/**
	 * Simple page creation.
	 */
	public PackageJsonComparePage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setMessage("Investigate changes to package.json.");
	}

	@Override
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);

		composite.setLayout(new GridLayout());

		Label label = createBoldLabel(composite, "package.json");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));

		Composite controls = new Composite(composite, SWT.NONE);
		controls.setLayout(new GridLayout(3, false));
		controls.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		{
			comboProjects = new Combo(controls, SWT.DROP_DOWN | SWT.READ_ONLY);
			comboProjects.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
			comboProjects.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					currentIdx = ((Combo) e.getSource()).getSelectionIndex();
					setCompareViewerInput(currentIdx);
				}
			});
			Button prev = new Button(controls, SWT.PUSH);
			prev.setText("prev");
			prev.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (!mergeResults.isEmpty() && currentIdx > 0) {
						currentIdx--;
						comboProjects.select(currentIdx);
						setCompareViewerInput(currentIdx);
					}
				}
			});

			Button next = new Button(controls, SWT.PUSH);
			next.setText("next");
			next.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (!mergeResults.isEmpty() &&
							currentIdx < mergeResults.size() - 1) {
						currentIdx++;
						comboProjects.select(currentIdx);
						setCompareViewerInput(currentIdx);
					}
				}
			});

		}

		pane = new CompareViewerPane(composite, SWT.BORDER | SWT.FLAT);
		pane.setText("Compare");
		GridData data = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
		data.widthHint = convertWidthInCharsToPixels(120);
		data.heightHint = convertHeightInCharsToPixels(13);
		pane.setLayoutData(data);

		Control previewer = createPreviewer(pane);
		pane.setContent(previewer);
		GridData gd = new GridData(GridData.FILL_BOTH);
		previewer.setLayoutData(gd);
		previewer.setFont(parent.getFont());

		setControl(composite);

	}

	private Control createPreviewer(Composite parent) {
		final CompareConfiguration compareConfiguration = new CompareConfiguration();
		compareConfiguration.setLeftLabel("Original package.json");
		compareConfiguration.setLeftEditable(false);
		compareConfiguration.setRightLabel("Merged package.json");
		compareConfiguration.setRightEditable(false);
		compareConfiguration.setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.FALSE);

		fViewer = new TextMergeViewer(parent, SWT.NONE, compareConfiguration);
		// add initial input in order to avoid problems when disposing the viewer later:
		fViewer.setInput(new DiffNode(new DiffElementFromString(""), new DiffElementFromString("")));

		Control control = fViewer.getControl();
		control.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				compareConfiguration.dispose();
			}
		});
		return control;
	}

	void setCompareViewerInput(int dataIndex) {
		if (!fViewer.getControl().isDisposed()) {
			MergeResult mergeResult = mergeResults.get(dataIndex);
			fViewer.setInput(new DiffNode(
					new DiffElementFromString(mergeResult.getUserPackagaJson()),
					new DiffElementFromString(mergeResult.getFinalPackageJson())));
			fViewer.refresh();
		}
	}

	@Override
	protected boolean allowNewContainerName() {
		return false;
	}

	/**
	 * Called from {@link NpmExportWizard when selection changes}
	 *
	 * @param _mergeResults
	 *            from NpmExporter
	 */
	public void updateOn(List<MergeResult> _mergeResults) {
		this.mergeResults = _mergeResults;
		comboProjects.deselectAll();
		comboProjects.clearSelection();
		for (int i = 0; i < _mergeResults.size(); i++)
			comboProjects.add(stringify(_mergeResults.get(i).getProject()));
		// finally select first;
		if (!_mergeResults.isEmpty()) {
			currentIdx = 0;
			comboProjects.select(0);
			setCompareViewerInput(0);
		}
	}

	/**
	 * string-repo of a project.
	 */
	protected String stringify(IN4JSProject project) {
		return project.toString();
	}

	@Override
	public void handleEvent(Event event) {
		// n.t.d.
	}
}
