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
package org.eclipse.n4js.ui.organize.imports;

import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import com.google.inject.Inject;

/**
 *
 */
public class UnsafeImortsOrganizer {

	@Inject
	private DocumentImportsOrganizer importsOrganizer;

	/**
	 * Organize the imports in the N4JS document. It is unsafe to use this method if the caller was injected with
	 * injector for different language than needs to be used for the document. E.g. when caller is created through N4JS
	 * injector, but processed document is N4JSX type. It is safer for the callers to use {@link OrganizeImportsService}
	 * which will take care the injection mechanisms.
	 *
	 * @param document
	 *            N4JS document
	 * @throws RuntimeException
	 *             wrapping a BreakException in case of user-abortion ({@link Interaction#queryUser}) or
	 *             resolution-failure({@link Interaction#breakBuild} )
	 */
	public void unsafeOrganizeDocument(final IXtextDocument document, final Interaction interaction) {
		importsOrganizer.organizeDocument(document, interaction);
	}
}
