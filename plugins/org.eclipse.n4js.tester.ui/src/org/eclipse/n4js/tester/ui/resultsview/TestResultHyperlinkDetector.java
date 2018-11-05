/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester.ui.resultsview;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.n4js.ui.console.JSStackTraceLocationText;

import com.google.inject.Inject;

/**
 * Detects hyperlinks in JavaScript stack trace shown in the test result view stack trace view. For each location two
 * links are created if possible: one to the code in the original location (i.e. the N4JS file) and one in the generated
 * code (i.e. the JavaScript file). If no original file (N4JS) is found, the file may not be generated at all; in this
 * case only the link to the JavaScript file is created.
 *
 * This class needs to be injected since it uses the {@link SourceMapFileLocator} which required injection.
 */
public class TestResultHyperlinkDetector extends AbstractHyperlinkDetector {

	@Inject
	private SourceMapFileLocator sourceMapFileLocator;

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();

		// extract relevant characters
		IRegion lineRegion;
		String candidate;
		try {
			lineRegion = document.getLineInformationOfOffset(offset);
			candidate = document.get(lineRegion.getOffset(), lineRegion.getLength());
		} catch (BadLocationException ex) {
			return null;
		}

		Matcher matcher = JSStackTraceLocationText.JAVASCRIPT_STRACKTRACE_LOCATION_PATTERN.matcher(candidate);
		List<IHyperlink> links = new ArrayList<>();
		while (matcher.find()) {
			JSStackTraceLocationText generatedLocation = new JSStackTraceLocationText(matcher);

			IRegion linkRegion = new Region(
					lineRegion.getOffset() + matcher.start(),
					matcher.end() - matcher.start());

			// generatedLocation.fileName.length());
			JSStackTraceLocationText originalLocation = retrieveOriginal(generatedLocation);
			if (originalLocation != null) { // prefer original location (e.g. n4js)
				links.add(new TestResultHyperlink(linkRegion, originalLocation));
				if (!canShowMultipleHyperlinks) { // and do not show any other in case of single hyper links
					break;
				}
			}
			links.add(new TestResultHyperlink(linkRegion, generatedLocation));
			if (!canShowMultipleHyperlinks) {
				break;
			}

		}
		if (links.isEmpty()) {
			return null;
		}

		IHyperlink[] hyperlinks = new IHyperlink[links.size()];
		links.toArray(hyperlinks);
		return hyperlinks;
	}

	private JSStackTraceLocationText retrieveOriginal(JSStackTraceLocationText locationText) {
		File file = new File(locationText.fileName);
		if (file.exists() && file.isFile()) {
			Path path = file.toPath();
			try {
				File sourceMapFile = sourceMapFileLocator.resolveSourceMapFromGen(path);
				if (sourceMapFile == null) {
					return null;
				}
				SourceMap sourceMap = SourceMap.loadAndResolve(sourceMapFile.toPath());
				if (sourceMap == null) {
					return null;
				}
				MappingEntry mappingEntry = sourceMap.findMappingForGenPosition(locationText.line - 1,
						locationText.column - 1);
				if (mappingEntry == null) { // no mapping found
					return null;
				}
				Path sourcePath = sourceMap.getResolvedSources().get(mappingEntry.srcIndex);
				return new JSStackTraceLocationText(sourcePath, mappingEntry.srcLine + 1, mappingEntry.srcColumn + 1);
			} catch (Exception e) {
				// we will fall back to JavaScript link
			}
		}
		return null;

	}

}
