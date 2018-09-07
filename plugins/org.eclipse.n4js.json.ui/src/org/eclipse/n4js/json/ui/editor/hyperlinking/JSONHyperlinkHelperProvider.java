package org.eclipse.n4js.json.ui.editor.hyperlinking;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.n4js.json.ui.extension.JSONUiExtensionRegistry;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;

import com.google.inject.Inject;

public class JSONHyperlinkHelperProvider extends HyperlinkHelper {

	@Inject
	private JSONUiExtensionRegistry registry;

	@Override
	public IHyperlink[] createHyperlinksByOffset(XtextResource resource, int offset, boolean createMultipleHyperlinks) {
		List<IHyperlink> links = new LinkedList<>();

		Collection<IJSONHyperlinkHelperExtension> hyperlinkHelperExtensions = registry.getHyperlinkHelperExtensions();
		for (IJSONHyperlinkHelperExtension hhExt : hyperlinkHelperExtensions) {
			IHyperlink[] hyperlinks = null;
			if (hhExt.isResponsible(resource)) {
				hyperlinks = hhExt.getHyperlinks(resource, offset);
			}
			if (hyperlinks == null) {
				continue;
			}

			List<IHyperlink> hyperlinkList = Arrays.asList(hyperlinks);
			links.addAll(hyperlinkList);
		}

		if (links.isEmpty()) {
			return null;
		}

		return links.toArray(new IHyperlink[links.size()]);
	}
}
