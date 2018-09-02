package org.eclipse.n4js.ui.console;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.console.actions.CloseConsoleAction;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * @see org.eclipse.jdt.internal.debug.ui.console.JavaStackTracePageParticipant
 */
public class N4JSStackTracePageParticipant implements IConsolePageParticipant {

	private CloseConsoleAction fCloseAction;
	private UseSourceMapActionDelegate useSourceMapAction;
	private IHandlerActivation fHandlerActivation;
	private IContextActivation fContextActivation;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IConsolePageParticipant#init(org.eclipse.ui.part.IPageBookViewPage,
	 * org.eclipse.ui.console.IConsole)
	 */
	@Override
	public void init(IPageBookViewPage page, IConsole console) {
		fCloseAction = new CloseConsoleAction(console);

		IToolBarManager manager = page.getSite().getActionBars().getToolBarManager();
		manager.appendToGroup(IConsoleConstants.LAUNCH_GROUP, fCloseAction);

		// fFormatAction = new FormatStackTraceActionDelegate((JavaStackTraceConsole) console);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IConsolePageParticipant#dispose()
	 */
	@Override
	public void dispose() {
		deactivated();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IConsolePageParticipant#activated()
	 */
	@Override
	public void activated() {
		// add EOF submissions
		IWorkbench workbench = PlatformUI.getWorkbench();
		IHandlerService handlerService = workbench.getAdapter(IHandlerService.class);

		// IHandler formatHandler = new AbstractHandler() {
		// @Override
		// public Object execute(ExecutionEvent event) throws ExecutionException {
		// // useSourceMapAction.run(null);
		// return null;
		// }
		// };
		//
		// fHandlerActivation = handlerService.activateHandler("org.eclipse.jdt.ui.edit.text.java.format",
		// formatHandler); //$NON-NLS-1$
		//
		// IContextService contextService = workbench.getAdapter(IContextService.class);
		// fContextActivation = contextService.activateContext("org.eclipse.jdt.ui.javaEditorScope"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IConsolePageParticipant#deactivated()
	 */
	@Override
	public void deactivated() {
		// remove EOF submissions
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (fHandlerActivation != null) {
			IHandlerService handlerService = workbench.getAdapter(IHandlerService.class);
			handlerService.deactivateHandler(fHandlerActivation);
			fHandlerActivation = null;
		}
		if (fContextActivation != null) {
			IContextService contextService = workbench.getAdapter(IContextService.class);
			contextService.deactivateContext(fContextActivation);
			fContextActivation = null;
		}
	}

}
