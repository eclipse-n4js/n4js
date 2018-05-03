package org.eclipse.n4js.json.formatting2;

import org.eclipse.xtext.formatting2.AbstractFormatter2;
import org.eclipse.xtext.formatting2.IFormattableDocument;


/**
 * A formatter implementation that disables formatting of JSON resources for now.
 */
@SuppressWarnings("restriction")
public class JSONFormatter extends AbstractFormatter2 {

	@Override
	public void format(Object obj, IFormattableDocument document) {
		// do nothing for now
	}

}
