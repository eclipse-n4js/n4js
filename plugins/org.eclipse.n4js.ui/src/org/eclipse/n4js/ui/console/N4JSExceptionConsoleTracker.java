package org.eclipse.n4js.ui.console;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.ui.console.IPatternMatchListenerDelegate;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Console tracker which enables JavaScript/N4JS hyperlinks to the source code. Needs to be injected.
 */
public class N4JSExceptionConsoleTracker implements IPatternMatchListenerDelegate {

	/**
	 * The console associated with this line tracker
	 */
	private TextConsole console;

	/**
	 * Set by injector in constructor.
	 */
	private final Provider<N4JSStackTraceHyperlink> n4JSStackTraceHyperlinkProvider;

	/**
	 * Creates this tracker with the given hyper link provider. This constructor is called by the injector.
	 */
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
	public void connect(TextConsole textConsole) {
		this.console = textConsole;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.console.IPatternMatchListenerDelegate#disconnect()
	 */
	@Override
	public void disconnect() {
		console = null;
	}

	/**
	 * Returns the console, only available after connecting a console via {@link #connect(TextConsole)}.
	 */
	protected TextConsole getConsole() {
		return console;
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
			link.setTextConsole(console);
			console.addHyperlink(link, offset + 1, length - 2);
		} catch (BadLocationException e) {
			// no link to add
		}
	}

}
