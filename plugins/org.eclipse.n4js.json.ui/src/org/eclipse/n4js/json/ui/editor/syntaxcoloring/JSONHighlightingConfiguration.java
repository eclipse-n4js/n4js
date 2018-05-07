package org.eclipse.n4js.json.ui.editor.syntaxcoloring;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

/**
 * Custom highlighting configuration for JSON files. 
 */
public class JSONHighlightingConfiguration extends DefaultHighlightingConfiguration {
	public static final String PROPERTY_NAME_ID = "propertyName";
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(PROPERTY_NAME_ID, "Property Name", propertyNameTextStyle());
		super.configure(acceptor);
	}
	
	/** TextStyle for property names of JSON objects */
	private TextStyle propertyNameTextStyle() {
		return this.defaultTextStyle();
	}
}
