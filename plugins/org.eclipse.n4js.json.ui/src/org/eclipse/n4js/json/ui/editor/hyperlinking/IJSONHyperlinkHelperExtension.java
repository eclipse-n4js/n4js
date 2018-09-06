package org.eclipse.n4js.json.ui.editor.hyperlinking;

import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.xtext.resource.XtextResource;

public interface IJSONHyperlinkHelperExtension {

	/**
	 * @return true iff this hyperlink extension is active on the given
	 *         {@link XtextResource}
	 */
	boolean isResponsible(XtextResource resource);

	/** @return an array of {@link IHyperlink}s */
	IHyperlink[] getHyperlinks(XtextResource resource, int offset);

}
