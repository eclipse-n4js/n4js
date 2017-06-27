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
package org.eclipse.n4js.jsdoc2spec.ui;

import java.util.Set;

import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.ui.SpecPage.VisibilityChangedListener;

/**
 * This listener initializes the summary view.
 */
public class SummaryPageVisibilityListener implements VisibilityChangedListener {
	final SpecChangeSetProvider csp;
	final SpecExportCodeSummaryPage summaryPage;

	/**
	 * Constructor
	 */
	public SummaryPageVisibilityListener(SpecChangeSetProvider csp, SpecExportCodeSummaryPage summaryPage) {
		this.csp = csp;
		this.summaryPage = summaryPage;
	}

	@Override
	public void isVisibleChanged(boolean visible) {
		if (visible) {
			Set<SpecFile> specChangeSet = csp.getSpecChangeSet();

			if (specChangeSet == null || specChangeSet.isEmpty()) {
				summaryPage.setText("No files were changed");
			} else {
				StringBuilder strb = new StringBuilder("The following files will be updated on finish:\n");
				for (SpecFile specChange : specChangeSet) {
					strb.append("\n  ").append(specChange.getFileKey().toString());
				}
				summaryPage.setText(strb.toString());
			}
		}
	}

}
