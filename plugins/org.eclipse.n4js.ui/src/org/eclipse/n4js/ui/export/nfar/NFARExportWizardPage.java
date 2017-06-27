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
package org.eclipse.n4js.ui.export.nfar;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.n4js.ui.export.AbstractExportOperation;
import org.eclipse.n4js.ui.export.AbstractExportToSingleFileWizardPage;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
//import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
//import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
//import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileExportOperation;
//import org.eclipse.ui.internal.wizards.datatransfer.IDataTransferHelpContextIds;

/**
 *
 */
public class NFARExportWizardPage extends AbstractExportToSingleFileWizardPage {

	private final static String STORE_DESTINATION_NAMES_ID = "NFARExportWizardPage.STORE_DESTINATION_NAMES_ID"; //$NON-NLS-1$

	private static final String STORE_OVERWRITE_EXISTING_FILES_ID = "NFARExportWizardPage.STORE_OVERWRITE_EXISTING_FILES_ID"; //$NON-NLS-1$

	@Override
	protected String getStoreDestinationNamesID() {
		return STORE_DESTINATION_NAMES_ID;
	}

	@Override
	protected String getStoreOverwriteExistingFilesID() {
		return STORE_OVERWRITE_EXISTING_FILES_ID;
	}

	/**
	 * Create an instance of this class. It may be configured with a selection of {@link IResource resources}. This
	 * constraint is declared in the plugin.xml.
	 *
	 * @param selection
	 *            the selection which contains IResources
	 */
	public NFARExportWizardPage(IStructuredSelection selection) {
		super(selection, "NFARExportWizardPage", NFARExportMessages.ArchiveExport_exportTitle,
				NFARExportMessages.ArchiveExport_description);
	}

	@Override
	protected AbstractExportOperation createExportOperation(
			File archiveFile, IFile mainFile,
			IN4JSEclipseProject project) {
		return new NFARExportOperation(archiveFile, project);
	}

	@Override
	protected String getOutputSuffix() {
		return ".nfar";
	}

}
