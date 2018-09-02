package org.eclipse.n4js.ui.console;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.console.IPatternMatchListenerDelegate;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @see org.eclipse.jdt.internal.debug.ui.console.JavaConsoleTracker
 */
public class N4JSExceptionConsoleTracker implements IPatternMatchListenerDelegate {

	/**
	 * The console associated with this line tracker
	 */
	private TextConsole fConsole;

	private final Provider<N4JSStackTraceHyperlink> n4JSStackTraceHyperlinkProvider;

	@Inject
	public N4JSExceptionConsoleTracker(Provider<N4JSStackTraceHyperlink> n4JSStackTraceHyperlinkProvider) {
		this.n4JSStackTraceHyperlinkProvider = n4JSStackTraceHyperlinkProvider;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#connect(org.eclipse.ui.console.IConsole)
	 */
	@Override
	public void connect(TextConsole console) {
		fConsole = console;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#disconnect()
	 */
	@Override
	public void disconnect() {
		fConsole = null;
	}

	protected TextConsole getConsole() {
		return fConsole;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#matchFound(org.eclipse.ui.console.PatternMatchEvent)
	 */
	@Override
	public void matchFound(PatternMatchEvent event) {
		try {
			int offset = event.getOffset();
			int length = event.getLength();
			N4JSStackTraceHyperlink link = n4JSStackTraceHyperlinkProvider.get();
			link.setTextConsole(fConsole);
			fConsole.addHyperlink(link, offset + 1, length - 2);
		} catch (BadLocationException e) {
		}
	}

}
