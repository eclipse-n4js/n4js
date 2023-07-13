/**
 * Copyright (c) 2023 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.editor.contentassist;

import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;

/**
 * This subclass adds information that is missing in {@link ContentAssistEntry}
 */
public class N4JSContentAssistEntry extends ContentAssistEntry {

	private String filterText;

	/***/
	public String getFilterText() {
		return filterText;
	}

	/***/
	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}

}
