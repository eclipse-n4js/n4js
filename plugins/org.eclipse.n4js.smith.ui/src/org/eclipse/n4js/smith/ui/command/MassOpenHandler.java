package org.eclipse.n4js.smith.ui.command;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Opens many files at once.
 */
public class MassOpenHandler implements IHandler {

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Mass Open",
				"Enter file names to open\n(separated by space, comma, or semicolon; no paths, just files names):",
				"", null);
		if (dlg.open() == Window.OK) {
			final Set<String> fileNames = parseNamesString(dlg.getValue());
			openEditors(fileNames);
		}
		return null;
	}

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// ignore
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// ignore
	}

	@Override
	public void dispose() {
		// ignore
	}

	private Set<String> parseNamesString(String namesStr) {
		final String[] namesArr = namesStr.split("(\\s+)|(\\s*\\,\\s*)|(\\s*\\;\\s*)");
		final Set<String> namesSet = new HashSet<>();
		for (String name : namesArr) {
			if (name.contains(".")) {
				namesSet.add(name);
			} else {
				namesSet.add(name + "." + N4JSGlobals.N4JS_FILE_EXTENSION);
				namesSet.add(name + "." + N4JSGlobals.N4JSD_FILE_EXTENSION);
				namesSet.add(name + "." + N4JSGlobals.N4JSX_FILE_EXTENSION);
				namesSet.add(name + "." + "n4idl");
			}
		}
		return namesSet;
	}

	private void openEditors(Set<String> fileNames) {
		try {
			collectFiles(fileNames, file -> {
				try {
					openEditor(file);
				} catch (PartInitException e) {
					System.err.println("Unable to open editor for file: " + file.getFullPath());
					System.err.println("    " + e.getMessage());
				}
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void openEditor(IFile file) throws PartInitException {
		final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		final IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		page.openEditor(new FileEditorInput(file), desc.getId());
	}

	private void collectFiles(Set<String> names, Consumer<IFile> consumer) throws CoreException {
		collectFiles(ResourcesPlugin.getWorkspace().getRoot(), names, consumer);
	}

	private void collectFiles(IContainer container, Set<String> names, Consumer<IFile> consumer) throws CoreException {
		final IResource[] members = container.members();
		for (IResource member : members) {
			if (member instanceof IContainer) {
				if (member instanceof IProject && !((IProject) member).isOpen()) {
					continue;
				}
				collectFiles((IContainer) member, names, consumer);
			} else if (member instanceof IFile) {
				final String fileName = member.getName();
				if (names.contains(fileName)) {
					consumer.accept((IFile) member);
				}
			}
		}
	}
}
