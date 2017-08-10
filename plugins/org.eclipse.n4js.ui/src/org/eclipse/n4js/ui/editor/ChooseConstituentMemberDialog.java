/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.editor;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.xtext.ui.label.AbstractLabelProvider;

/**
 *
 */
public class ChooseConstituentMemberDialog extends ListDialog {

	/**
	 * @param parentShell
	 *            parent shell
	 * @param constituentMembers
	 *            the list of choices
	 */
	public ChooseConstituentMemberDialog(Shell parentShell, List<N4MemberDeclaration> constituentMembers,
			LabelProvider labelProvider) {
		super(parentShell);

		setTitle("Choose Constituent Member");
		setMessage(
				"The selected element is a composed member and thus has multiple constituent members.\nPlease choose the constituent member you want to jump to.");

		setAddCancelButton(true);
		setHelpAvailable(false);

		setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return constituentMembers.toArray();
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
		// setLabelProvider(labelProvider);
		setLabelProvider(new AbstractLabelProvider() {

			@Override
			public Image getImage(Object element) {
				return labelProvider.getImage(element);
			}

			@Override
			public String getText(Object element) {
				EObject eobj = (EObject) element;
				String text = calculateLogicallyQualifiedDisplayName(eobj);
				return text;
			}

			private boolean isShowable(EObject eobj) {
				return eobj instanceof N4MemberDeclaration || eobj instanceof N4ClassifierDefinition
						|| eobj instanceof FunctionDeclaration || eobj instanceof ExportedVariableDeclaration
						|| eobj instanceof Script;
			}

			private String calculateLogicallyQualifiedDisplayName(EObject eob) {
				// Calculate hierarchical logical name, e.g. C.m
				String text = labelProvider.getText(eob);
				EObject currContainer = eob.eContainer();
				while (currContainer != null) {
					if (isShowable(currContainer)) {
						text = labelProvider.getText(currContainer) + "." + text;
					}
					currContainer = currContainer.eContainer();
				}
				return text;
			}

		});

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

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButtonsForButtonBar(parent);
		getOkButton().setEnabled(false);
	}
}