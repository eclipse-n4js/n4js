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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.eclipse.n4js.jsdoc2spec.CheckCanceled;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.adoc.FileSystem;
import org.eclipse.n4js.jsdoc2spec.ui.SpecProcessPage;

/**
 * This class contains methods to compute html from adoc files.
 */
class TaskWriteFiles implements IRunnableWithProgress {
	final SpecProcessPage processOutputPage;
	final TaskGenerateAdoc taskGenAdoc;

	private ConfigAdoc configAdoc;
	private ConfigOutput configOutput;

	TaskWriteFiles(SpecProcessPage processHtmlPage, TaskGenerateAdoc taskGenAdoc) {
		this.processOutputPage = processHtmlPage;
		this.taskGenAdoc = taskGenAdoc;
	}

	void setConfig(ConfigAdoc configAdoc, ConfigOutput configOutput) {
		this.configAdoc = configAdoc;
		this.configOutput = configOutput;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException {
		try {
			performTasks(monitor);
		} catch (InterruptedException e) {
			processOutputPage.displayMessage("User canceled tasks.");
		} catch (Exception e) {
			processOutputPage.displayMessageRed(e.getMessage());
			e.printStackTrace();
		}
	}

	public void performTasks(IProgressMonitor monitor) throws IOException, InterruptedException {
		File rootDir = configAdoc.getDocRootDir();
		FileSystem.ensureFileStructure(rootDir);
		int workload = 1;

		SubMonitor subMonitor = SubMonitor.convert(monitor, workload);

		if (configOutput.writeAdocFiles) {
			writeAdocFiles(subMonitor);
		}

		subMonitor.subTask("Finished.");
		subMonitor.done();
		monitor.done();
	}

	private void writeAdocFiles(IProgressMonitor monitor)
			throws IOException, InterruptedException {

		Set<SpecFile> specChangeSet = taskGenAdoc.getSpecChangeSet();
		monitor.beginTask("Write adoc files", specChangeSet.size() + 1);

		for (SpecFile specChangeFile : specChangeSet) {
			File file = specChangeFile.getFile();
			String newContent = specChangeFile.getNewContent();

			if (file.exists()) {
				file.delete();
			}

			File parent = file.getParentFile();
			if (!parent.exists() && !parent.mkdirs()) {
				throw new IllegalStateException("Couldn't create dir: " + parent);
			}

			file.createNewFile();
			Files.write(newContent, file, Charsets.UTF_8);
			monitor.worked(1);

			CheckCanceled.checkUserCanceled(monitor);
		}

		monitor.worked(1);
		monitor.done();
	}

}
