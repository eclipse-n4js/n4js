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
package org.eclipse.n4js.ui.organize.imports;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.n4js.organize.imports.FileContainerFilter;
import org.eclipse.n4js.organize.imports.FileExtensionFilter;
import org.eclipse.n4js.ui.utils.AutobuildUtils;
import org.eclipse.n4js.ui.utils.AutobuildUtils.ClosableAutobuild;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;

import com.google.inject.Inject;

/**
 * Handler used for two cases: Mass updates on files/folders in selection or organizing the current N4JS Editor.
 */
public class N4JSOrganizeImportsHandler extends AbstractHandler {

	private static final Logger LOGGER = Logger.getLogger(N4JSOrganizeImportsHandler.class);

	@Inject
	private FileExtensionFilter extensionFilter;
	@Inject
	private FileContainerFilter containerFilter;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Collection<?> callingMenus = HandlerUtil.getActiveMenus(event);
		// "#TextEditorContext" is the defined plugin.xml
		boolean fromTextContext = (callingMenus != null && callingMenus.contains("#TextEditorContext"));
		boolean fromShortCut = (callingMenus == null || callingMenus.isEmpty());

		XtextEditor editor = EditorUtils.getActiveXtextEditor(event);
		boolean haveActiveEditor = editor != null;

		ISelection selection = HandlerUtil.getCurrentSelection(event);
		boolean nonEmptyStructuredSelection = (selection != null && selection instanceof IStructuredSelection
				&& !selection.isEmpty());

		if (haveActiveEditor && (fromTextContext || fromShortCut)) {
			OrganizeImportsService.organizeImportsInEditor(editor, Interaction.queryUser);
		} else if (nonEmptyStructuredSelection) {
			// called on a tree-selection in the package-manager or whatever shows the project-structure,
			// i.e. organize files and folders
			SelectionFilesCollector filesCollector = new SelectionFilesCollector(this::filter);
			List<IFile> collectedFiles = filesCollector.collectFiles((IStructuredSelection) selection);

			if (collectedFiles.isEmpty()) {
				return null;
			}

			try (ClosableAutobuild ca = AutobuildUtils.suppressAutobuild();) {

				// Query unsaved
				IWorkbench wbench = PlatformUI.getWorkbench();
				IWorkbenchWindow activeWorkbenchWindow = wbench.getActiveWorkbenchWindow();
				boolean allSaved = wbench.saveAll(activeWorkbenchWindow, activeWorkbenchWindow, null, true);
				if (!allSaved) {
					LOGGER.warn("Optimize imports cannot save editors, aborting.");
					return null;
				}

				batchOrganize(HandlerUtil.getActiveShell(event), collectedFiles);

			} catch (InvocationTargetException e) {
				throw new ExecutionException("Error during organizing imports", e);
			} catch (InterruptedException e) {
				// user cancelled, ok
			}
		}
		return null;
	}

	/** Composed check that is using several sub filters to decide if a file should be organized. */
	private boolean filter(IFile iFile) {
		return extensionFilter.test(iFile) && containerFilter.test(iFile);
	}

	/**
	 * Creates and runs runnable that twill organize imports in all provided files. Operation will show user dialog with
	 * progress of all the tasks. This is UI blocking operation.
	 *
	 * @param shell
	 *            for which dialog for which we create runnable
	 * @param collectedFiles
	 *            files that will be processed during run
	 */
	private void batchOrganize(Shell shell, List<IFile> collectedFiles)
			throws InvocationTargetException, InterruptedException {
		new ProgressMonitorDialog(shell)
				.run(true, true, createOrganizingRunnable(collectedFiles));
	}

	/** Creates {@link IRunnableWithProgress} that will be organize imports in the provided files. */
	private IRunnableWithProgress createOrganizingRunnable(List<IFile> collectedFiles) {
		IRunnableWithProgress op = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor mon) throws InvocationTargetException, InterruptedException {
				int totalWork = collectedFiles.size();
				SubMonitor subMon = SubMonitor.convert(mon, "Organize imports.", totalWork);
				for (int i = 0; !subMon.isCanceled() && i < totalWork; i++) {
					IFile currentFile = collectedFiles.get(i);
					subMon.setTaskName("Organize imports." + " - File (" + (i + 1) + " of " + totalWork + ")");
					try {
						OrganizeImportsService.organizeImportsInFile(currentFile, Interaction.breakBuild, subMon);

					} catch (CoreException | RuntimeException e) {
						String msg = "Exception in file " + currentFile.getFullPath().toString() + ".";
						LOGGER.error(msg, e);
						boolean errorDialogShowed = ErrorDialogWithStackTraceUtil.showErrorDialogWithStackTrace(
								msg + " Hit OK to continue.", e);
						if (!errorDialogShowed) {
							throw new InvocationTargetException(e);
						}
					}
				}
				if (subMon.isCanceled()) {
					throw new InterruptedException();
				}
			}

		};
		return op;
	}

}
