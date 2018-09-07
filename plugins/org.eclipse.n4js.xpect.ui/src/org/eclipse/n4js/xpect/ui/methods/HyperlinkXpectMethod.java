/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.ui.methods;

import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.util.EclipseGracefulUIShutdownEnabler;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.xpect.ui.common.XtextResourceCleanUtil;
import org.eclipse.n4js.xpect.ui.methods.contentassist.N4ContentAssistProcessorTestBuilderHelper;
import org.eclipse.n4js.xpect.ui.methods.contentassist.RegionWithCursor;
import org.eclipse.xpect.expectation.CommaSeparatedValuesExpectation;
import org.eclipse.xpect.expectation.ICommaSeparatedValuesExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.testing.ContentAssistProcessorTestBuilder;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 */
public class HyperlinkXpectMethod {

	static {
		EclipseGracefulUIShutdownEnabler.enableOnce();
	}

	@Inject
	private IHyperlinkDetector hyperlinkDetector;

	@Inject
	private N4ContentAssistProcessorTestBuilderHelper n4ContentAssistProcessorTestBuilderHelper;

	/**
	 * @param expectation
	 *            a list of expected URIs to jump to by this hyperlink
	 * @param resource
	 *            the resource under test
	 * @param region
	 *            the offset where hyperlinking should be invoked
	 * @throws Exception
	 *             some exceptions
	 */
	@ParameterParser(syntax = "('at' arg2=STRING)?")
	@Xpect
	public void hyperlinks(
			@CommaSeparatedValuesExpectation ICommaSeparatedValuesExpectation expectation, // arg0
			@ThisResource XtextResource resource, // arg1
			RegionWithCursor region // arg2
	) throws Exception {
		ContentAssistProcessorTestBuilder fixture = n4ContentAssistProcessorTestBuilderHelper
				.createTestBuilderForResource(resource);

		String currentModelToParse = resource.getParseResult().getRootNode().getText();
		IXtextDocument xtextDocument = fixture.getDocument(resource, currentModelToParse);
		// in case of cross-file hyperlinks, we have to make sure the target resources are fully resolved
		final ResourceSet resSet = resource.getResourceSet();
		for (Resource currRes : new ArrayList<>(resSet.getResources()))
			N4JSResource.postProcess(currRes);

		ITextViewer sourceViewer = fixture.getSourceViewer(currentModelToParse, xtextDocument);
		IHyperlink[] hyperlinks = hyperlinkDetector.detectHyperlinks(sourceViewer,
				new Region(region.getGlobalCursorOffset(), 0),
				true);

		// cleaned up resource, otherwise #createTestBuilder() above will fail next time this method is called
		XtextResourceCleanUtil.cleanXtextResource(resource);

		ArrayList<String> result = Lists.newArrayList();
		if (hyperlinks != null) {
			for (IHyperlink hyperlink : hyperlinks) {
				result.add(getTargetDescription(resource, hyperlink));
			}
		}

		expectation.assertEquals(result);
	}

	private String getTargetDescription(XtextResource resource, IHyperlink hyperlink) {
		final StringBuffer sb = new StringBuffer();
		// append hyperlink text. Only consider the element name and ignore the qualified part.
		String hyperlinkText = hyperlink.getHyperlinkText();
		hyperlinkText = hyperlinkText.substring(hyperlinkText.lastIndexOf('.') + 1);
		final EObject target = getTarget(resource, hyperlink);

		if (target != null) {
			if (hyperlinkText != null)
				sb.append(hyperlinkText);
			else
				sb.append("<no hyperlink text>");
			// append description of target element (path from the element to the root of the AST)
			// build chain of ancestor AST elements
			sb.append(": ");
			final int startLen = sb.length();
			EObject currTarget = target;
			while (currTarget != null) {
				if (currTarget instanceof NamedElement || currTarget instanceof IdentifiableElement) {
					if (sb.length() > startLen)
						sb.append(" in ");
					String name = currTarget instanceof NamedElement ? ((NamedElement) currTarget).getName()
							: ((IdentifiableElement) currTarget).getName();
					if (name == null || name.trim().length() == 0)
						name = "<unnamed>";
					else
						name = "\"" + name + "\"";
					sb.append(name + "(" + currTarget.eClass().getName() + ")");
				}
				currTarget = currTarget.eContainer();
			}
			// add URI of resource
			final URI targetResURI = target.eResource() != null ? target.eResource().getURI() : null;
			final String fname = targetResURI != null ? targetResURI.lastSegment() : null;
			if (fname != null && fname.trim().length() > 0) {
				sb.append(" in file ");
				sb.append(fname);
			}

		} else {
			URI uri = getURI(hyperlink);
			if (uri != null) {
				if (uri.isFile()) {
					sb.append("file:/...");
					for (int i = Math.max(0, uri.segmentCount() - 2); i < uri.segmentCount(); i++) {
						sb.append("/");
						sb.append(uri.segment(i));
					}
				} else {
					sb.append(uri);
				}
			}
		}
		return sb.toString();
	}

	private EObject getTarget(XtextResource resource, IHyperlink hyperlink) {
		final ResourceSet resourceSet = resource != null ? resource.getResourceSet() : null;
		final URI uri = getURI(hyperlink);
		final EObject target = resourceSet != null && uri != null && uri.fragment() != null
				? resourceSet.getEObject(uri, true)
				: null;
		if (target instanceof SyntaxRelatedTElement)
			return ((SyntaxRelatedTElement) target).getAstElement();
		return target;
	}

	private URI getURI(IHyperlink hyperlink) {
		final URI uri = hyperlink instanceof XtextHyperlink ? ((XtextHyperlink) hyperlink).getURI() : null;
		return uri;
	}

}
