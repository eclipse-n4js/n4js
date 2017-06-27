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
package org.eclipse.n4js.jsdoc2spec.ui;

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

import org.eclipse.n4js.jsdoc2spec.SpecFile;

/**
 * This wizard page shows a compare view. All files that changed are chosable in a combo box. When chosen, the file and
 * its changes will be displayed in a compare view.
 */
public class SpecComparePage extends SpecPage {
	private static final String PREFIX_SUFFIX_PROPERTY = "org.eclipse.jdt.internal.junit.ui.CompareResultDialog.prefixSuffix"; //$NON-NLS-1$

	/**
	 * Lengths of common prefix and suffix. Note: this array is passed to the DamagerRepairer and the lengths are
	 * updated on content change.
	 */
	private final int[] fPrefixSuffix = new int[2];

	private final String docTypeName;

	CompareViewerPane pane;

	private TextMergeViewer fViewer;

	/**
	 * Files with newly generated code.
	 */
	List<SpecFile> specChanges;

	int current;
	Combo comboCurrentFile;

	/**
	 * Constructor
	 */
	public SpecComparePage(String pageName, String docTypeName) {
		super(pageName);
		this.docTypeName = docTypeName;
		setMessage("View changes.");
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		Composite controls = new Composite(composite, SWT.NONE);
		controls.setLayout(new GridLayout(3, false));

		comboCurrentFile = new Combo(controls, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboCurrentFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		comboCurrentFile.add("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		comboCurrentFile.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				current = ((Combo) e.getSource()).getSelectionIndex();
				setCompareViewerInput(current);
			}
		});

		Button prev = new Button(controls, SWT.PUSH);
		prev.setText("prev");
		prev.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (current > 0) {
					current--;
					comboCurrentFile.select(current);
					setCompareViewerInput(current);
				}
			}
		});

		Button next = new Button(controls, SWT.PUSH);
		next.setText("next");
		next.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (current < specChanges.size() - 1) {
					current++;
					comboCurrentFile.select(current);
					setCompareViewerInput(current);
				}
			}
		});

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
		compareConfiguration.setLeftLabel("Original " + docTypeName);
		compareConfiguration.setLeftEditable(false);
		compareConfiguration.setRightLabel("Updated " + docTypeName);
		compareConfiguration.setRightEditable(false);
		compareConfiguration.setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.FALSE);
		compareConfiguration.setProperty(PREFIX_SUFFIX_PROPERTY, fPrefixSuffix);

		fViewer = new TextMergeViewer(parent, SWT.NONE, compareConfiguration);
		// add initial input in order to avoid problems when disposing the viewer later:
		fViewer.setInput(new DiffNode(new TargetElementFromString(""), new TargetElementFromString("")));

		Control control = fViewer.getControl();
		control.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				compareConfiguration.dispose();
			}
		});
		return control;
	}

	/**
	 * Sets the list of all changed files.
	 */
	public void setSpecChanges(List<SpecFile> specChanges) {
		this.specChanges = specChanges;
		initcomboBox();
		comboCurrentFile.select(0);
		setCompareViewerInput(0);
	}

	private void initcomboBox() {
		String[] items = new String[specChanges.size()];
		int i = 0;
		for (SpecFile specChange : specChanges) {
			String fileName = specChange.getFile().getName();
			String module = specChange.getPackageDisplayName();
			String itemName = fileName;
			if (module != null && module.length() > 0)
				itemName += " (" + module + ")";
			items[i++] = itemName;
		}
		comboCurrentFile.setItems(items);
	}

	private void setCompareViewerInput(int dataIndex) {
		if (fViewer.getControl().isDisposed())
			return;
		if (specChanges.isEmpty())
			return;

		SpecFile specChange = specChanges.get(dataIndex);
		fViewer.setInput(new DiffNode(
				new OriginalElementFromFile(specChange.getFile()),
				new TargetElementFromString(specChange.getNewContent())));
		fViewer.refresh();
	}

}
