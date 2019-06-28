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

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
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
 *
 * This method is largely copied from the {@link DefaultRenameElementHandler#execute(ExecutionEvent)} and modified to
 * handle the fact that N4JS has TModule model in addition to AST
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

								//// Start custom code
								// LiteralOrComputedPropertyName does not have a type model but its container does
								if (selectedElement instanceof LiteralOrComputedPropertyName) {
									selectedElement = selectedElement.eContainer();
								}

								// An IdentifierRef refers to an AST FormalParameter and not TFormalParameter
								if (!(selectedElement instanceof FormalParameter)
										&& (N4JSLanguageUtils.getDefinedTypeModelElement(selectedElement) != null)) {
									selectedElement = N4JSLanguageUtils.getDefinedTypeModelElement(selectedElement);
								}

								// GH-1002: Do NOT allow renaming alias if the selected element is imported in the
								// current resource via alias.
								// This does not work in case the element is alias imported in another resource (this is
								// expensive to check).
								N4JSResource res = (N4JSResource) resource;
								Script script = (Script) res.getContents().get(0);
								List<ImportDeclaration> importDecls = script.getScriptElements().stream()
										.filter(elem -> elem instanceof ImportDeclaration)
										.map(elem -> (ImportDeclaration) elem)
										.collect(Collectors.toList());
								for (ImportDeclaration importDecl : importDecls) {
									for (ImportSpecifier importSpecified : importDecl.getImportSpecifiers()) {
										if (importSpecified instanceof NamedImportSpecifier) {
											NamedImportSpecifier namedImportSpecifier = (NamedImportSpecifier) importSpecified;
											if (namedImportSpecifier.getAlias() != null) {
												if (namedImportSpecifier.getImportedElement() == selectedElement) {
													throw new UnsupportedOperationException(
															"Renaming alias is NOT supported at the moment. Sorry.");
												}
											}
										}
									}
								}
								//// End custom code
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
		} catch (

		OperationCanceledException e) {
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
