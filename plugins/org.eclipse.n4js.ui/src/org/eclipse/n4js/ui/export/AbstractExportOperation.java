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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.operation.IRunnableWithProgress;

import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;

/**
 * Base class for exporting content of a given N4JS project, such as NFAR exports.
 */
public abstract class AbstractExportOperation implements IRunnableWithProgress {

	/** The file to be created. */
	protected final File targetFile;

	/** The project in which the main file is located. */
	protected final IN4JSEclipseProject project;

	/** The progess monitor used when running the operation, set in {@link #run(IProgressMonitor)}. */
	protected IProgressMonitor monitor;

	/** Errors, filled when running the operation. */
	protected final List<IStatus> errorTable = new ArrayList<>(1); // IStatus

	/** The workspace, extracted from project in the constructor */
	protected final IWorkspace workspace;

	/** The root location, that is the location of the project itself. */
	protected final URI rootLocation;

	/** The main file which is to be executed */
	protected final IFile mainFile;

	/**
	 * Create an operation that will export the given project to the given zip file.
	 */
	public AbstractExportOperation(File archiveFile, IFile mainFile, IN4JSEclipseProject project) {
		this.targetFile = archiveFile;
		this.mainFile = mainFile;
		this.project = project;
		this.workspace = project.getProject().getWorkspace();
		rootLocation = project.getLocation().appendSegment("");
	}

	/**
	 * Add a new entry to the error table with the passed information
	 */
	protected void addError(String message, Throwable e) {
		errorTable.add(new Status(IStatus.ERROR, "org.eclipse.n4js.ui", 0, message, e));
	}

	/**
	 * Returns the status of the operation. If there were any errors, the result is a status object containing
	 * individual status objects for each error. If there were no errors, the result is a status object with error code
	 * <code>OK</code>.
	 *
	 * @return the status
	 */
	public IStatus getStatus() {
		IStatus[] errors = new IStatus[errorTable.size()];
		errorTable.toArray(errors);
		return new MultiStatus(
				"org.eclipse.n4js.ui",
				IStatus.OK,
				errors,
				N4ExportMessages.FileSystemExportOperation_problemsExporting,
				null);
	}

}
