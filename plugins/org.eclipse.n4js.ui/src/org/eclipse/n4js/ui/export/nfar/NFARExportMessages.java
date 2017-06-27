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

import org.eclipse.osgi.util.NLS;

/**
 * Message bundle for the nfar export wizard.
 */
@SuppressWarnings("javadoc")
public class NFARExportMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.n4js.ui.export.nfar.messages";//$NON-NLS-1$

	public static String ArchiveExport_exportTitle;
	public static String DataTransfer_browse;
	public static String WizardTitle_export;
	public static String ArchiveExport_description;

	static {
		NLS.initializeMessages(BUNDLE_NAME, NFARExportMessages.class);
	}
}
