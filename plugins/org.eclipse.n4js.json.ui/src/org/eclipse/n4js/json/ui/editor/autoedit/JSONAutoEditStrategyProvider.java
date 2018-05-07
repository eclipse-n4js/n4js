package org.eclipse.n4js.json.ui.editor.autoedit;

import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;

/**
 * This class configures the auto-edit strategy used in our JSON editor 
 * (e.g. automatic opening/closing of squared brackets).
 */
public class JSONAutoEditStrategyProvider extends AbstractEditStrategyProvider {

	@Override
	protected void configure(IEditStrategyAcceptor iEditStrategyAcceptor) {
		// do not configure an auto-edit strategy for now (investigate doing this later for squared brackets and curly braces)
	}

}
