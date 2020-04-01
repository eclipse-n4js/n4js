/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.editor.contentassist.imports;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.ide.server.imports.ImportOrganizer.ImportRef;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;

/**
 *
 */
public class ContentAssistEntryWithRef extends ContentAssistEntry {

	private final List<ImportRef> importRefs = new ArrayList<>();

	public List<ImportRef> getImportRefs() {
		return importRefs;
	}
}
