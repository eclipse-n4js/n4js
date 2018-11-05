package org.eclipse.n4js.ui.console;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.console.actions.CloseConsoleAction;
import org.eclipse.ui.part.IPageBookViewPage;

/**
 * Stack trace page for N4JS/Javascript error stack traces.
 */
public class N4JSStackTracePageParticipant implements IConsolePageParticipant {

	private CloseConsoleAction closeAction;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IConsolePageParticipant#init(org.eclipse.ui.part.IPageBookViewPage,
	 * org.eclipse.ui.console.IConsole)
	 */
	@Override
	public void init(IPageBookViewPage page, IConsole console) {
		closeAction = new CloseConsoleAction(console);

		IToolBarManager manager = page.getSite().getActionBars().getToolBarManager();
		manager.appendToGroup(IConsoleConstants.LAUNCH_GROUP, closeAction);
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
		// nothing to do here
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IConsolePageParticipant#deactivated()
	 */
	@Override
	public void deactivated() {
		// nothing to do here
	}

}
