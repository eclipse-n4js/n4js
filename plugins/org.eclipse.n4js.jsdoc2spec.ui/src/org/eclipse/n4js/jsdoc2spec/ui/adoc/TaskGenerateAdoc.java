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

import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_ADOC_GEN;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_MODULES;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_PACKAGES;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.SEP;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.common.base.Optional;

import org.eclipse.n4js.jsdoc2spec.CheckCanceled;
import org.eclipse.n4js.jsdoc2spec.SpecFile;
import org.eclipse.n4js.jsdoc2spec.SpecInfo;
import org.eclipse.n4js.jsdoc2spec.SubMonitorMsg;
import org.eclipse.n4js.jsdoc2spec.adoc.FileSystem;
import org.eclipse.n4js.jsdoc2spec.adoc.JSDoc2ADocSpecProcessor;
import org.eclipse.n4js.jsdoc2spec.adoc.SpecIndexFile;
import org.eclipse.n4js.jsdoc2spec.adoc.SpecModuleFile;
import org.eclipse.n4js.jsdoc2spec.ui.SpecProcessPage;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.internal.N4JSEclipseProject;

/**
 * This class contains methods to generate adoc files.
 */
class TaskGenerateAdoc implements IRunnableWithProgress {
	final JSDoc2ADocSpecProcessor jsDoc2SpecProcessor;
	final IResourceSetProvider resourceSetProvider;
	final IN4JSCore n4JSCore;
	final IStructuredSelection selection;
	final SpecConfigAdocPage configAdocPage;
	final SpecProcessPage processAdocPage;
	/** Types by name extracted from selected projects. Map is filled on demand and cached. */
	final Map<String, SpecInfo> typesByName = new HashMap<>();
	private Set<SpecFile> specChangeSet;

	private ConfigAdoc configAdoc;

	TaskGenerateAdoc(JSDoc2ADocSpecProcessor jsDoc2SpecProcessor, IResourceSetProvider resourceSetProvider,
			IN4JSCore n4jsCore, IStructuredSelection selection, SpecConfigAdocPage configAdocPage,
			SpecProcessPage processAdocPage) {

		this.jsDoc2SpecProcessor = jsDoc2SpecProcessor;
		this.resourceSetProvider = resourceSetProvider;
		this.n4JSCore = n4jsCore;
		this.selection = selection;
		this.configAdocPage = configAdocPage;
		this.processAdocPage = processAdocPage;
	}

	void setConfig(ConfigAdoc configAdoc) {
		this.configAdoc = configAdoc;
	}

	Set<SpecFile> getSpecChangeSet() {
		return specChangeSet;
	}

	boolean noOrEmptySpecChangeSet() {
		return specChangeSet == null || specChangeSet.isEmpty();
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException {
		try {
			performTasks(monitor);
		} catch (InterruptedException e) {
			processAdocPage.displayMessage("User canceled tasks.");
		} catch (Exception e) {
			processAdocPage.displayMessageRed(e.getMessage());
			e.printStackTrace();
		}
	}

	int getPackagesCountTotal() {
		File docRootDir = configAdocPage.getConfig().getDocRootDir();
		String packagesDir = docRootDir + SEP + DIR_ADOC_GEN + SEP + DIR_PACKAGES;
		List<String> packageFileNames = FileSystem.getAdocFileNames(packagesDir);
		Collections.sort(packageFileNames);

		int changedPackageFiles = 0;
		int updated = 0;
		if (specChangeSet != null)
			for (SpecFile specFile : specChangeSet) {
				if (specFile instanceof SpecIndexFile) {
					// This is an approximation since e.g. the overview files are also SpecIndexFiles
					// Error: 0..+2
					changedPackageFiles++;
					if (packageFileNames.contains(specFile.getFile().getName()))
						updated++;
				}
			}

		return packageFileNames.size() + changedPackageFiles - updated;
	}

	int getModulesCountTotal() {
		File docRootDir = configAdocPage.getConfig().getDocRootDir();
		String modulesDir = docRootDir + SEP + DIR_ADOC_GEN + SEP + DIR_MODULES;
		List<String> moduleFileNames = FileSystem.getAdocFileNames(modulesDir);
		Collections.sort(moduleFileNames);

		int changedModuleFiles = 0;
		int updatedModuleFiles = 0;
		if (specChangeSet != null)
			for (SpecFile specFile : specChangeSet) {
				if (specFile instanceof SpecModuleFile) {
					changedModuleFiles++;
					if (moduleFileNames.contains(specFile.getFile().getName()))
						updatedModuleFiles++;
				}
			}

		return moduleFileNames.size() + changedModuleFiles - updatedModuleFiles;
	}

	private void performTasks(IProgressMonitor monitor) throws IOException, InterruptedException {
		jsDoc2SpecProcessor.resetIssues();
		File rootDir = configAdoc.getDocRootDir();
		FileSystem.ensureFileStructure(rootDir);

		int workload = 0;
		if (typesByName.isEmpty())
			workload += 2;
		if (specChangeSet == null)
			workload += 2;

		SubMonitor completeProgress = SubMonitor.convert(monitor, workload);
		SubMonitorMsg cmplProgAcceptor = new SubMonitorMsg(completeProgress,
				processAdocPage::displayMessage,
				processAdocPage::displayMessageRed,
				CheckCanceled::checkUserCanceled);

		if (typesByName.isEmpty()) {
			SubMonitorMsg subMonitor = cmplProgAcceptor.newChild(2);
			computeTypes(subMonitor);
			subMonitor.done();
		}
		if (specChangeSet == null) {
			SubMonitorMsg subMonitor = cmplProgAcceptor.newChild(2);
			computeChangeSet(subMonitor);
			subMonitor.done();
		}

		cmplProgAcceptor.subTask("Finished.");
		if (specChangeSet.isEmpty())
			processAdocPage.displayMessage("No Changes found.");

		completeProgress.done();
	}

	/**
	 * Side effect: sets {@link #typesByName}
	 */
	private void computeTypes(SubMonitorMsg completeProgress) throws IllegalStateException, InterruptedException {
		Set<IN4JSProject> projects = getProjects();
		if (projects.isEmpty()) {
			throw new IllegalStateException("No project selected, cannot export spec");
		}

		completeProgress.subTask("Parsing code base ...");
		Map<String, SpecInfo> m = jsDoc2SpecProcessor.readN4JSDs(projects, resourceSetMapper(), completeProgress);
		CheckCanceled.checkUserCanceled(completeProgress);

		typesByName.putAll(m);
		processAdocPage.displayMessageRed(jsDoc2SpecProcessor.getWarnings());
		jsDoc2SpecProcessor.resetIssues();
	}

	private Set<IN4JSProject> getProjects() {
		Set<IN4JSProject> projects = new HashSet<>();
		for (Object element : selection.toList()) {
			if (element instanceof IResource) {
				URI uri = URI.createPlatformResourceURI((((IResource) element)).getFullPath().toString(), true);
				Optional<? extends IN4JSProject> optProject = n4JSCore.findProject(uri);
				IN4JSProject project = optProject.get();
				projects.add(project);
			}
		}
		return projects;
	}

	private Function<IN4JSProject, ResourceSet> resourceSetMapper() {
		return (IN4JSProject p) -> resourceSetProvider.get(((N4JSEclipseProject) p).getProject());
	}

	/**
	 * Side effect: sets {@link #specChangeSet}
	 */
	private void computeChangeSet(SubMonitorMsg completeProgress) throws IOException, InterruptedException {
		specChangeSet = new TreeSet<>();

		completeProgress.subTask("Reading existing documentation ...");
		SubMonitorMsg readSpecProgress = completeProgress.newChild(1);
		jsDoc2SpecProcessor.setRootDir(configAdoc.getDocRootDir());
		CheckCanceled.checkUserCanceled(readSpecProgress);
		readSpecProgress.done();

		completeProgress.subTask("Computing updates ...");
		SubMonitorMsg compUpdtsProgress = completeProgress.newChild(1);
		Collection<SpecFile> changes = jsDoc2SpecProcessor.computeUpdates(typesByName, compUpdtsProgress);
		CheckCanceled.checkUserCanceled(compUpdtsProgress);
		compUpdtsProgress.done();

		specChangeSet.addAll(changes);
	}

}
