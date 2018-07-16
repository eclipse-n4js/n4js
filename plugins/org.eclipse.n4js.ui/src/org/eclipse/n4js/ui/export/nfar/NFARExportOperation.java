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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ui.containers.NfarStorageMapper;
import org.eclipse.n4js.ui.export.AbstractExportOperation;
import org.eclipse.n4js.ui.export.N4ExportMessages;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;

import com.google.common.collect.Iterators;

/**
 * Operation for exporting a resource and its children to a new .zip or .tar.gz file.
 *
 * @since 3.1
 */
@SuppressWarnings("restriction")
public class NFARExportOperation extends AbstractExportOperation {

	private ZipFileExporter exporter;

	/**
	 * Create an operation that will export the given project to the given zip file.
	 */
	public NFARExportOperation(File archiveFile, IN4JSEclipseProject project) {
		super(archiveFile, null, project);
	}

	private int countSelectedResources() {
		int result = 1; // manifest
		for (IN4JSSourceContainer container : project.getSourceContainers()) {
			int numberOfURIs = Iterators.size(container.iterator());
			numberOfURIs *= 2; // compiled variants
			result += numberOfURIs;
		}
		return result;
	}

	/**
	 * @param hasCompiledFiles
	 *            TODO this has to be evaluated
	 */
	private void exportResource(URI exportedResource, boolean hasCompiledFiles)
			throws InterruptedException {
		Path path = new Path(exportedResource.toPlatformString(true));
		IFile exportedFile = workspace.getRoot().getFile(path);
		if (!exportedFile.isAccessible()) {
			return;
		}
		URI localPath = exportedResource.deresolve(rootLocation);
		String nameInZip = localPath.toString();
		monitor.subTask(nameInZip);
		try {
			exporter.writeFile(exportedFile.getContents(), exportedFile.getLocalTimeStamp(), nameInZip);
			// todo export compiled files
		} catch (IOException e) {
			addError(NLS.bind(DataTransferMessages.DataTransfer_errorExporting, nameInZip, e.getMessage()), e);
		} catch (CoreException e) {
			addError(NLS.bind(DataTransferMessages.DataTransfer_errorExporting, nameInZip, e.getMessage()), e);
		}

		monitor.worked(1);
		ModalContext.checkCanceled(monitor);
	}

	private void exportResources() throws InterruptedException {
		boolean hasCompiledFiles = true;
		for (IN4JSSourceContainer sourceContainer : project.getSourceContainers()) {
			Iterator<URI> iterator = sourceContainer.iterator();
			while (iterator.hasNext()) {
				exportResource(iterator.next(), hasCompiledFiles);
			}
		}
		// FIXME delete entire class
		exportResource(project.getLocation().appendSegment(NfarStorageMapper.N4MF_MANIFEST), false);
	}

	private void initialize() throws IOException {
		exporter = new ZipFileExporter(targetFile);
	}

	/**
	 * Export the resources that were previously specified for export (or if a single resource was specified then export
	 * it recursively)
	 */
	@Override
	public void run(IProgressMonitor progressMonitor)
			throws InvocationTargetException, InterruptedException {
		this.monitor = progressMonitor;

		try {
			initialize();
		} catch (IOException e) {
			throw new InvocationTargetException(e, NLS.bind(N4ExportMessages.Export_cannotOpen, e.getMessage()));
		}

		// ie.- a single resource for recursive export was specified
		int totalWork = countSelectedResources();
		monitor.beginTask(DataTransferMessages.DataTransfer_exportingTitle, totalWork);

		exportResources();

		try {
			exporter.finished();
		} catch (IOException e) {
			throw new InvocationTargetException(
					e,
					NLS.bind(N4ExportMessages.Export_cannotClose, e.getMessage()));
		} finally {
			monitor.done();
		}
	}

}
