package org.eclipse.n4js.json.ui.editor.hyperlinking;

import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.xtext.resource.XtextResource;

public interface HyperlinkHelperExtension {

	IHyperlink[] getHyperlinks(XtextResource resource, int offset);

}
