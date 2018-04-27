package org.eclipse.n4js.json.formatting2;

import org.eclipse.xtext.formatting2.AbstractFormatter2;
import org.eclipse.xtext.formatting2.IFormattableDocument;


@SuppressWarnings("restriction")
public class JSONFormatter extends AbstractFormatter2 {

	@Override
	public void format(Object obj, IFormattableDocument document) {
		System.out.println("Format " + obj);
		// do nothing for now
	}


}
