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
package org.eclipse.n4js.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.n4js.ui.utils.UIUtils;
import org.eclipse.n4js.ui.wizard.classifiers.N4JSNewClassifierWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.wizards.IWizardDescriptor;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Handler for the "Create New N4JS Element in module" command.
 *
 * Opens the given wizard and if supported makes it use the currently open module file as initial selection.
 *
 */
public class CreateNewN4JSElementInModuleHandler extends AbstractHandler {

	private static final String WIZARD_ID_PARAMETER_ID = "org.eclipse.n4js.ui.wizard.CreateNewN4JSElementInModule.wizardId";
	private static final String NESTED_PARAMETER_ID = "org.eclipse.n4js.ui.wizard.CreateNewN4JSElementInModule.nested";

	@Inject
	Injector injector;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String wizardId = event.getParameter(WIZARD_ID_PARAMETER_ID);

		if (wizardId == null) {
			return null;
		}

		boolean isNested = Boolean.parseBoolean(event.getParameter(NESTED_PARAMETER_ID));

		IStructuredSelection selection;

		// Try to get editor file
		IFile editorFile = getActiveEditorFile();

		if (null != editorFile) {
			selection = new StructuredSelection(editorFile);
		} else {
			// Try to get the selection from the project explorer tree view
			IResource resourceTreeFile = getActiveTreeResourceSelection(event);
			if (null != resourceTreeFile) {
				// If it's a tree selection set nested to false
				isNested = false;
				selection = new StructuredSelection(resourceTreeFile);
			} else {
				// If not selection can be retrieved, launch the wizard with an empty selection
				selection = StructuredSelection.EMPTY;
			}
		}

		openWizardForModule(wizardId, selection, isNested);

		return null;
	}

	/**
	 * Returns the opened file of the currently active editor.
	 *
	 * Returns null if no editor is open.
	 */
	private static IFile getActiveEditorFile() {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();

		if (null == activeEditor) {
			return null;
		}

		IEditorInput input = activeEditor.getEditorInput();

		if (input instanceof FileEditorInput) {
			return ((FileEditorInput) input).getFile();
		} else {
			return null;
		}

	}

	/**
	 * Returns the active tree resource selection if there is one.
	 *
	 * Examines the active workspace selection and if it is a resource inside of a tree returns it.
	 *
	 * @param event
	 *            The execution event
	 * @returns The resource or {@code null} on failure.
	 *
	 */
	private static IResource getActiveTreeResourceSelection(ExecutionEvent event) {

		ISelection activeSelection = HandlerUtil.getCurrentSelection(event);

		if (activeSelection instanceof TreeSelection) {
			Object firstElement = ((TreeSelection) activeSelection).getFirstElement();

			if (firstElement instanceof IResource) {
				return (IResource) firstElement;
			}
		}
		return null;
	}

	/**
	 * Opens the wizard with the given id and passes it the selection.
	 *
	 * @param wizardId
	 *            The wizard id of the eclipse newWizard registry
	 * @param selection
	 *            The selection
	 */
	private void openWizardForModule(String wizardId, IStructuredSelection selection, boolean nested) {

		// Retrieve wizard from registry
		IWizardDescriptor wizardDescriptor = PlatformUI.getWorkbench().getNewWizardRegistry().findWizard(wizardId);

		if (wizardDescriptor == null) {
			return;
		}

		try {
			IWorkbenchWizard wizard = wizardDescriptor.createWizard();

			// Inject wizard members
			injector.injectMembers(wizard);

			// Create and open a new wizard dialog
			WizardDialog wizardDialog = new WizardDialog(UIUtils.getShell(), wizard);

			// If the wizard supports it, enable in module option
			if (wizard instanceof N4JSNewClassifierWizard<?>) {
				((N4JSNewClassifierWizard<?>) wizard).init(PlatformUI.getWorkbench(), selection, nested);
			} else {
				// Otherwise just pass it the initial selection
				wizard.init(PlatformUI.getWorkbench(), selection);
			}

			// wizardDialog.setTitle(wizard.getWindowTitle());
			wizardDialog.open();

		} catch (CoreException e) {
			/** Failed to create the wizard */
			Shell workbenchShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.open(MessageDialog.ERROR, workbenchShell, "Failed to launch wizard",
					String.format("Failed to launch wizard %s", wizardId), SWT.SHEET);
			return;
		}

	}

}
