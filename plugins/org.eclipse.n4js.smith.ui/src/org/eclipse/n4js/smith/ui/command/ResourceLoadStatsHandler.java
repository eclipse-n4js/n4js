package org.eclipse.n4js.smith.ui.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.n4js.ResourceLoadingStatistics;

import com.google.inject.Inject;

/**
 * Opens many files at once.
 */
public class ResourceLoadStatsHandler implements IHandler {

	@Inject
	private ResourceLoadingStatistics resourceLoadingStatistics;

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
		// final IEditorPart eddy =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		// final XtextEditor xeddy = eddy instanceof XtextEditor ? (XtextEditor) eddy : null;
		// if (xeddy != null) {
		// xeddy.getDocument().readOnly((resource) -> {
		// if (resource.getResourceSet() != null
		// && !resource.getResourceSet().getResources().isEmpty()
		// && resource == resource.getResourceSet().getResources().get(0)) {
		// ....
		// }
		// return null;
		// });
		// }
		resourceLoadingStatistics.investigate();
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
}
