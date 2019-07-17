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
package org.eclipse.n4js.runner.ui;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.n4js.compare.ApiImplMapping;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * A dialog for letting the user choose an implementation to run.
 */
public class ChooseImplementationDialog extends ListDialog {

	/***/
	protected final ApiImplMapping apiImplMapping;
	/***/
	protected final List<N4JSProjectName> availableImplIds;

	/**
	 * Create a dialog with default settings using the given <code>apiImplMapping</code>.
	 */
	public ChooseImplementationDialog(Shell parent, ApiImplMapping apiImplMapping) {
		super(parent);
		this.apiImplMapping = apiImplMapping;
		this.availableImplIds = apiImplMapping.getAllImplIds();

		setTitle("Choose Implementation");
		setMessage(computeMessage());
		setAddCancelButton(true);
		setHelpAvailable(false);

		setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return availableImplIds.toArray();
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// ignore
			}

			@Override
			public void dispose() {
				// ignore
			}
		});
		setLabelProvider(new LabelProvider());

		setInput(new Object());
	}

	@Override
	protected Control createDialogArea(Composite container) {
		final Control control = super.createDialogArea(container);
		getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				getOkButton().setEnabled(!getTableViewer().getSelection().isEmpty());
			}
		});
		return control;
	}

	/** Computes a default message. */
	protected String computeMessage() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Project dependencies contain the following API projects:\n");
		for (N4JSProjectName apiId : apiImplMapping.getApiIds()) {
			sb.append("- " + apiId + " ("
					+ apiImplMapping.getImplIds(apiId).stream().map(N4JSProjectName::getRawName)
							.collect(Collectors.joining(", "))
					+ ")\n");
		}
		sb.append('\n');
		sb.append("Please choose an implementation below:");
		return sb.toString();
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getOkButton().setEnabled(false);
	}
}
