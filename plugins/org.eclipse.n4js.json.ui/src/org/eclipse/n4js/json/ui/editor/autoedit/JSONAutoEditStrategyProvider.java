package org.eclipse.n4js.json.ui.editor.autoedit;

import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;

/**
 * This class configures the auto-edit strategy used in the JSON editor (e.g.
 * automatic opening/closing of squared brackets).
 */
public class JSONAutoEditStrategyProvider extends DefaultAutoEditStrategyProvider {
	@Override
	protected void configure(IEditStrategyAcceptor acceptor) {
		/*
		 * Almost the default configuration but omits auto-edit behavior for parenthesis
		 * characters.
		 */
		configureIndentationEditStrategy(acceptor);
		configureStringLiteral(acceptor);
		configureSquareBrackets(acceptor);
		configureCurlyBracesBlock(acceptor);
		configureMultilineComments(acceptor);
		configureCompoundBracesBlocks(acceptor);
	}
}
