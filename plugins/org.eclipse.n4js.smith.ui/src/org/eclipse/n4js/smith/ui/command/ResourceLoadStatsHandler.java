package org.eclipse.n4js.smith.ui.command;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.n4js.smith.ui.ResourceLoadingStatistics;
import org.eclipse.n4js.ui.utils.EclipseUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;

/**
 * Command handler for computing and showing resource loading statistics for the entire workspace.
 */
public class ResourceLoadStatsHandler implements IHandler {

	private enum Mode {
		Workspace, Editor
	}

	@Inject
	private ResourceLoadingStatistics resourceLoadingStatistics;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
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

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Mode mode = getModeFromParameters(event.getParameters());
		switch (mode) {
		case Workspace:
			executeForWorkspace();
			break;
		case Editor:
			executeForActiveEditor();
			break;
		}
		return null;
	}

	private void executeForWorkspace() {
		EclipseUtils.runInModalDialog(operationCanceledManager,
				monitor -> resourceLoadingStatistics.computeAndShowStatsForWorkspace(getConsolePrintStream(), monitor));
	}

	private void executeForActiveEditor() {
		final IEditorPart eddy = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		final XtextEditor xeddy = eddy instanceof XtextEditor ? (XtextEditor) eddy : null;
		if (xeddy != null) {
			xeddy.getDocument().readOnly((resource) -> {
				try (PrintStream ps = getConsolePrintStream()) {
					resourceLoadingStatistics.computeAndShowStatsForResourceSet(resource.getResourceSet(), ps);
				}
				return null;
			});
		} else {
			try (PrintStream ps = getConsolePrintStream()) {
				ps.println("No active N4JS editor found.");
			}
		}
	}

	private Mode getModeFromParameters(Map<?, ?> parameters) {
		for (Entry<?, ?> entry : parameters.entrySet()) {
			final Object key = entry.getKey();
			final Object value = entry.getValue();
			if (key instanceof String && ((String) key).endsWith(".scope")) {
				if ("workspace".equals(value)) {
					return Mode.Workspace;
				} else if ("editor".equals(value)) {
					return Mode.Editor;
				}
			}
		}
		// use editor as default mode
		return Mode.Editor;
	}

	private PrintStream getConsolePrintStream() {
		final MessageConsole console = EclipseUtils.getOrCreateConsole("Resource Loading Statistics", true, true);
		final PrintStream out = new PrintStream(console.newMessageStream());
		return out;
	}
}
