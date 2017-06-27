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
package org.eclipse.n4js.jsdoc2spec.ui.adoc;

import org.eclipse.n4js.jsdoc2spec.ui.SpecPage.VisibilityChangedListener;

/**
 * This listener sets the computation time estimations for the html config page. The estimations are based on a
 * computation duration of 3sec per adoc file.
 */
public class ConfigOutputVisibilityListener implements VisibilityChangedListener {
	final TaskGenerateAdoc taskGenAdoc;
	final SpecConfigOutputPage configHtmlPage;

	/**
	 * Constructor
	 */
	public ConfigOutputVisibilityListener(TaskGenerateAdoc taskGenAdoc, SpecConfigOutputPage configHtmlPage) {
		this.taskGenAdoc = taskGenAdoc;
		this.configHtmlPage = configHtmlPage;
	}

	@Override
	public void isVisibleChanged(boolean visible) {
		if (visible) {
			configHtmlPage.setChkBoxWriteAdocEnabled(!taskGenAdoc.noOrEmptySpecChangeSet());
		}
	}

}
