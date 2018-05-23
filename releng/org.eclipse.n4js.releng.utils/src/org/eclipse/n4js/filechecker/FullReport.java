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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Aggregates all {@link Report}s
 */
public class FullReport {
	final private List<Report> reports = new LinkedList<>();

	/** Adds a report */
	public void addReport(Report report) {
		reports.add(report);
	}

	/** Returns true iff there exist no reports with problems */
	public boolean hasInvalidFiles() {
		return !getInvalidReports().isEmpty();
	}

	/** Returns all valid reports */
	public Collection<Report> getValidReports() {
		return ReportUtils.filterValidReports(reports);
	}

	/** Returns all reports that do not have problems */
	public Collection<Report> getInvalidReports() {
		return ReportUtils.filterInvalidReports(reports);
	}

	/** Returns all erroneous reports */
	public Collection<Report> getErroneousFiles() {
		return ReportUtils.filterErroneousReports(reports);
	}

	/** Returns all reports that do not have a copyright header */
	public Collection<Report> getReportsNoCRH() {
		return ReportUtils.filterReportsNoCRH(reports);
	}

	/** Returns true iff there exist no erroneous reports */
	public boolean hasErroneousFiles() {
		return !getErroneousFiles().isEmpty();
	}

}
