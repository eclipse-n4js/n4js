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
package org.eclipse.n4js.ui.export;

import org.eclipse.osgi.util.NLS;

/**
 * Message bundle for the nfar export wizard.
 */
@SuppressWarnings("javadoc")
public class N4ExportMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.n4js.ui.export.messages";//$NON-NLS-1$

	public static String ArchiveExport_exportTitle;

	public static String DataTransfer_browse;

	public static String WizardTitle_export;

	public static String DataTransfer_createTargetDirectory;
	public static String DataTransfer_directoryCreationError;
	public static String DataTransfer_exportProblems;
	public static String DataTransfer_errorExporting;
	public static String DataTransfer_exportingTitle;
	public static String ExportFile_overwriteExisting;
	public static String FileSystemExportOperation_problemsExporting;
	public static String FileExport_noneSelected;
	public static String FileExport_conflictingContainer;
	public static String FileExport_selectDestinationTitle;
	public static String FileExport_selectDestinationMessage;
	public static String FileExport_toDirectory;
	public static String Export_mustBeFile;
	public static String Export_alreadyExists;
	public static String Export_alreadyExistsError;
	public static String Export_cannotOpen;
	public static String Export_cannotClose;
	public static String ArchiveExport_description;
	public static String ArchiveExport_destinationEmpty;

	static {
		NLS.initializeMessages(BUNDLE_NAME, N4ExportMessages.class);
	}
}
