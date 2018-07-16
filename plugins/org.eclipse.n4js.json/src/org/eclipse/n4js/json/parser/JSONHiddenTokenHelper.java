package org.eclipse.n4js.json.parser;

import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.parsetree.reconstr.IHiddenTokenHelper;
import org.eclipse.xtext.parsetree.reconstr.impl.DefaultHiddenTokenHelper;

/**
 * {@link IHiddenTokenHelper} that additionally declares the {@code EOL}/{@code \n} as a hidden token.
 */
public class JSONHiddenTokenHelper extends DefaultHiddenTokenHelper {
	@Override
	public boolean isWhitespace(AbstractRule rule) {
		return rule != null && ("WS".equals(rule.getName()) || "EOL".equals(rule.getName()));
	}
}
