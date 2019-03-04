/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.refactoring;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDefinition;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.ui.refactoring.ui.DefaultRenameElementHandler;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

/**
 * Customer rename handler
 */
@SuppressWarnings("restriction")
public class N4JSRenameElementHandler extends DefaultRenameElementHandler {
	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			final XtextEditor editor = EditorUtils.getActiveXtextEditor(event);
			if (editor != null) {
				syncUtil.totalSync(preferences.isSaveAllBeforeRefactoring(),
						renameRefactoringController.getActiveLinkedMode() == null);
				final ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
				IRenameElementContext renameElementContext = editor.getDocument().priorityReadOnly(
						new IUnitOfWork<IRenameElementContext, XtextResource>() {
							@Override
							public IRenameElementContext exec(XtextResource resource) throws Exception {
								EObject selectedElement = eObjectAtOffsetHelper.resolveElementAt(resource,
										selection.getOffset());

								if (selectedElement instanceof LiteralOrComputedPropertyName) {
									selectedElement = selectedElement.eContainer();
								}

								if (selectedElement instanceof N4FieldDeclaration) {
									selectedElement = ((N4FieldDeclaration) selectedElement).getDefinedField();
								}

								if (selectedElement instanceof N4TypeDefinition) {
									selectedElement = ((N4TypeDefinition) selectedElement).getDefinedType();
								}

								if (selectedElement instanceof TStructField) {
									selectedElement = ((TStructField) selectedElement).getDefinedMember();
								}

								ResourceSet rs = selectedElement.eResource().getResourceSet();
								N4JSRefactoringResourceSetProvider.myGlobalResourceSet = rs;

								if (selectedElement != null) {
									@SuppressWarnings("hiding")
									IRenameElementContext renameElementContext = renameContextFactory
											.createRenameElementContext(
													selectedElement, editor, selection, resource);
									if (isRefactoringEnabled(renameElementContext, resource))
										return renameElementContext;
								}
								return null;
							}
						});
				if (renameElementContext != null) {
					startRenameElement(renameElementContext);
				}
			}
		} catch (OperationCanceledException e) {
			// cancelled by user, ok
			return null;
		} catch (InterruptedException e) {
			// cancelled by user, ok
			return null;
		} catch (Exception exc) {
			LOG.error("Error initializing refactoring", exc);
			MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error initializing refactoring",
					exc.getMessage() + "\nSee log for details");
		}
		return null;
	}

	@Override
	protected boolean isRefactoringEnabled(IRenameElementContext renameElementContext, XtextResource resource) {
		// Do not allow renaming built-in types such as int, string etc.
		if (renameElementContext.getTargetElementURI().scheme().equals("n4scheme")) {
			return false;
		}
		return super.isRefactoringEnabled(renameElementContext, resource);
	}
}
