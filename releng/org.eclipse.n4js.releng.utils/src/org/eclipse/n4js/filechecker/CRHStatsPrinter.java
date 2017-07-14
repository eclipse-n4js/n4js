/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.filechecker;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.Multimap;

/**
 *
 */
public class CRHStatsPrinter {

	final static List<String> CANNOT_HAVE_CRH = Arrays.asList(new String[] {
			"png", "bmp", "gif", "jpeg", "class", "classpath", "properties", "prefs", "MF", "project", "_project",
			"graffle",
			"gitignore", "jar", "npmignore", "ico", "zip", "graffle", "pdf", "csv", "target", "licence", "license",
			"setup", "dict", "json", "launch", "svg", "xtextbin", /* plugin. */"xml_gen", "replacement", "bib", "ecore",
			"genmodel", "gitattributes", /* about. */"html_TEMPLATE", "icns", "ignored", "index", "map", "mappings",
			"placeholder", "product", "see", "sublime-project", "xpm", "exsd", "xlsx", "dummy", "nfar", "xdoc",
			"xcf", "ext", "cspex", "api_filters", "b3aggr" });

	static boolean canHaveCRH(String extension) {
		return !CANNOT_HAVE_CRH.contains(extension);
	}

	static boolean cannotHaveCRH(String extension) {
		return CANNOT_HAVE_CRH.contains(extension);
	}

	/** Prints copyright header statistics */
	public static void println(final FullReport fullReport) {
		Collection<Report> reportsNoCRH = fullReport.getReportsNoCRH();
		// reportsNoCRH = ReportUtils.filterReportsInN4JS(reportsNoCRH);
		Multimap<String, Report> hist = ReportUtils.getHistogram(reportsNoCRH, (report) -> report.getFileExtension());
		Map<String, Collection<Report>> sortedHist = ReportUtils.sortByListSize(hist);

		System.out.println("Total number of files without copyright header in N4JS repository: " + reportsNoCRH.size());

		printResultLine(sortedHist, CRHStatsPrinter::cannotHaveCRH,
				"-- Number of files that canNOT have a copyright header: ");

		printResultLine(sortedHist, CRHStatsPrinter::canHaveCRH,
				"-- Number of files that can have a copyright header: ");

		Set<String> interestingExt = sortedHist.keySet();
		// String[] interestingExt = { "n4js", "n4mf", "js" };

		interestingExt.removeAll(CANNOT_HAVE_CRH);
		for (String ext : interestingExt) {
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("." + ext + " files with missing copyright header are (ignored excluded):");
			Collection<Report> notIgnored = ReportUtils.filterNotIgnoredReports(hist.get(ext));
			for (Report report : notIgnored) {
				System.out.println("   " + report.path.toString());
			}
		}

		System.out.println("=====================================================================================");
	}

	private static void printResultLine(Map<String, Collection<Report>> sortedHist, Function<String, Boolean> canDo,
			String text) {

		int sumCannotHaveCRH = 0;
		for (String ext : sortedHist.keySet()) {
			if (canDo.apply(ext))
				sumCannotHaveCRH += sortedHist.get(ext).size();
		}
		System.out.println(text + sumCannotHaveCRH);

		int sumAll = 0;
		int sumIgnored = 0;
		for (String ext : sortedHist.keySet()) {
			if (canDo.apply(ext)) {
				Collection<Report> extReports = sortedHist.get(ext);
				int extCount = extReports.size();
				int ignoredCount = ReportUtils.filterIgnoredReports(extReports).size();
				String line = String.format("   %-23s: %5s", ext, extCount);
				if (ignoredCount > 0)
					line += String.format(", among them %5s ignored", ignoredCount);
				System.out.println(line);
				sumAll += extCount;
				sumIgnored += ignoredCount;
			}
		}
		System.out.println("   -----------------------------------------------------------------");
		String line = String.format("   %-23s: %5s, among them %5s ignored", "Sum", sumAll, sumIgnored);
		System.out.println(line);
		System.out.println();
	}

}
