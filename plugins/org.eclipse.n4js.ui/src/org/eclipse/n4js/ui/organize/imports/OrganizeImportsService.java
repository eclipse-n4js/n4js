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

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.ui.editor.XtextEditor;

/**
 * Service for organizing imports in files and editors. Since process of organizing imports is contextual to the
 * specific language, this class will perform injector lookup based on the provided {@link IFile} or
 * {@link XtextEditor}. Using found injector it will delegate work to {@link DocumentImportsOrganizer}.
 */
public class OrganizeImportsService {

	private static final Logger LOGGER = Logger.getLogger(OrganizeImportsService.class);

	/** Organize provided file. */
	public static void organizeImportsInFile(IFile currentFile, final Interaction interaction, SubMonitor subMon)
			throws CoreException {
		SubMonitor subSubMon = subMon.split(1, SubMonitor.SUPPRESS_NONE);
		subSubMon.setTaskName(currentFile.getName());
		DocumentImportsOrganizer imortsOrganizer = getOrganizeImports(currentFile);
		imortsOrganizer.organizeFile(currentFile, interaction, subSubMon);
	}

	/** Organize provided file. */
	public static void organizeImportsInFile(IFile currentFile, final Interaction interaction, IProgressMonitor mon)
			throws CoreException {
		DocumentImportsOrganizer imortsOrganizer = getOrganizeImports(currentFile);
		imortsOrganizer.organizeFile(currentFile, interaction, mon);
	}

	/** Organize provided editor. */
	public static void organizeImportsInEditor(XtextEditor editor, final Interaction interaction) {
		try {
			IResource resource = editor.getResource();
			DocumentImportsOrganizer imortsOrganizer = getOrganizeImports(resource);
			imortsOrganizer.organizeDocument(editor.getDocument(), interaction);
		} catch (RuntimeException re) {
			if (re.getCause() instanceof BreakException) {
				LOGGER.debug("user canceled");
			} else {
				LOGGER.warn("Unrecognized RT-exception", re);
			}

		}
	}

	private static DocumentImportsOrganizer getOrganizeImports(IFile ifile) {
		return N4LanguageUtils.getServiceForContext(ifile, DocumentImportsOrganizer.class).get();
	}

	private static DocumentImportsOrganizer getOrganizeImports(IResource iresource) {
		return N4LanguageUtils.getServiceForContext(iresource, DocumentImportsOrganizer.class).get();
	}
}
