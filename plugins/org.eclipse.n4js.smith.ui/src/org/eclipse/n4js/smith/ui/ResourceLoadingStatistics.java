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
package org.eclipse.n4js.smith.ui;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.external.ExternalProject;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.xtext.builder.MonitorBasedCancelIndicator;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Class for computing and showing resource loading statistics. For details, see methods
 * {@link #computeAndShowStatsForWorkspace(PrintStream, IProgressMonitor)} and
 * {@link #computeAndShowStatsFor(ResourceSet, PrintStream)}.
 */
@Singleton
@SuppressWarnings("javadoc")
public class ResourceLoadingStatistics {

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	private static final class FileLoadInfo {
		final URI fileURI;

		int countTotal;
		int countBuiltIn;
		int countLoadedFromAST;
		int countLoadedFromIndex;
		long timeInMs;

		public FileLoadInfo(URI fileURI) {
			this.fileURI = fileURI;
		}

		void println(PrintStream out) {
			final String name = fileURI.lastSegment();
			out.printf("%-50s   %3d   (%3d = %3d + %3d + %3d) in %5d ms",
					name,
					countTotal,
					countLoadedFromAST + countLoadedFromIndex + countBuiltIn,
					countLoadedFromAST,
					countLoadedFromIndex,
					countBuiltIn,
					timeInMs);
			out.println();
		}

		static void printReport(IN4JSProject project, List<FileLoadInfo> results, PrintStream out, String elapsedTime) {
			out.println("------------------------------------------------------------------------------------");
			out.println("Resource loading per file for project: " + project.getLocation().getName() + " took "
					+ elapsedTime);
			out.println();
			final List<FileLoadInfo> othersFromAST = results.stream().filter(result -> result.countLoadedFromAST > 0)
					.collect(Collectors.toList());
			final List<FileLoadInfo> noOthersFromAST = results.stream().filter(result -> result.countLoadedFromAST == 0)
					.collect(Collectors.toList());
			out.println("Files that triggered other files being loaded from AST:");
			if (!othersFromAST.isEmpty()) {
				othersFromAST.forEach(result -> result.println(out));
			} else {
				out.println("None.");
			}
			out.println();
			out.println("Files that did *not* trigger other files being loaded from AST:");
			if (!noOthersFromAST.isEmpty()) {
				noOthersFromAST.forEach(result -> result.println(out));
			} else {
				out.println("None.");
			}
			out.println();
			out.println("Legend: countTotal (sum = countLoadedFromAST + countLoadedFromIndex + countBuiltIn)");
			out.println();
			out.println(
					"Files that triggered other files being loaded from AST        : " + othersFromAST.size());
			out.println(
					"Files that did *not* trigger other files being loaded from AST: " + noOthersFromAST.size());
			out.println("------------------------------------------------------------------------------------");
		}
	}

	/**
	 * Computes and prints resource loading statistics for all N4JS[X] projects in the workspace.
	 * <p>
	 * For each N4JS[X] file in the workspace, this method will
	 * <ol>
	 * <li>create a new, empty resource set,
	 * <li>load the file into this resource set and fully process it (parser, types builder, post-processing),
	 * <li>count how many other files/resources were automatically loaded into the resource set and if they were loaded
	 * from AST or from Xtext index,
	 * <li>print statistics to the given stream.
	 * </ol>
	 */
	public void computeAndShowStatsForWorkspace(PrintStream out, IProgressMonitor monitor) {
		final CancelIndicator cancelIndicator = new MonitorBasedCancelIndicator(monitor);

		// for proper progress reporting, first collect all URIs in all N4JS projects
		int uriCount = 0;
		final Map<IN4JSProject, List<URI>> urisPerProject = new LinkedHashMap<>();
		final Iterable<? extends IN4JSProject> projects = n4jsCore.findAllProjects();
		for (IN4JSProject project : projects) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			if (!isManagedByLibraryManager(project)) {
				final List<URI> uris = collectURIsToInvestigate(project);
				uriCount += uris.size();
				urisPerProject.put(project, uris);
			}
		}

		// now do the actual work: compute and show statistics for each project
		monitor.beginTask("Investigate projects in workspace ... ", uriCount);
		for (Entry<IN4JSProject, List<URI>> entry : urisPerProject.entrySet()) {
			operationCanceledManager.checkCanceled(cancelIndicator);
			Stopwatch stopwatch = Stopwatch.createStarted();
			final IN4JSProject project = entry.getKey();
			final List<URI> uris = entry.getValue();
			final List<FileLoadInfo> results = investigate(project, uris, out, monitor, false);
			out.println();
			out.println("SUMMARY:");
			out.println();
			FileLoadInfo.printReport(project, results, out, stopwatch.toString());
		}
	}

	/**
	 * This method assumes the given resource set has at least 1 resource and that the first resource is the main
	 * resource, i.e. the only resource for which loading and proxy resolution was triggered explicitly (and thus all
	 * other resources can be assumed to have been loaded incidentally while processing the first).
	 */
	public void computeAndShowStatsForResourceSet(ResourceSet resSet, PrintStream out) {
		final FileLoadInfo info = investigate(resSet);
		info.println(out);
	}

	private List<FileLoadInfo> investigate(IN4JSProject project, List<URI> urisToInvestigate,
			PrintStream out, IProgressMonitor monitor, boolean printProgressToOut) {
		final int urisCount = urisToInvestigate.size();
		final List<FileLoadInfo> results = new ArrayList<>(urisCount);
		for (int i = 0; i < urisCount; i++) {
			final URI uri = urisToInvestigate.get(i);
			monitor.subTask("Investigating file " + uri.lastSegment() + " ...");
			Stopwatch perResource = Stopwatch.createStarted();
			if (printProgressToOut) {
				final int progress = (int) Math.floor(((float) i) / ((float) urisCount) * 100.0f);
				out.println("Investigating file " + uri.lastSegment()
						+ " (" + (i + 1) + "/" + urisCount + ", " + progress + "%) ...");
			}
			try {
				final FileLoadInfo currResult = investigate(project, uri, monitor);
				results.add(currResult);
				currResult.timeInMs = perResource.elapsed(TimeUnit.MILLISECONDS);
				currResult.println(out); // note: print currResult always (even if !printProgressToOut)
			} catch (Throwable th) {
				if (operationCanceledManager.isOperationCanceledException(th)) {
					// don't propagate the cancel exception; instead, return the results collected thus far
					return results;
				}
				th.printStackTrace();
				// do not abort if investigation of one file fails
			} finally {
				monitor.worked(1);
			}
		}
		return results;
	}

	private FileLoadInfo investigate(IN4JSProject project, URI fileURI, IProgressMonitor monitor) {
		final CancelIndicator cancelIndicator = new MonitorBasedCancelIndicator(monitor);
		operationCanceledManager.checkCanceled(cancelIndicator);

		final ResourceSet resSet = n4jsCore.createResourceSet(Optional.of(project));
		final N4JSResource res = (N4JSResource) resSet.createResource(fileURI);
		try {
			res.load(Collections.emptyMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		res.getContents(); // trigger loading of AST
		res.resolveLazyCrossReferences(cancelIndicator);

		// now start counting what was loaded incidentally ...
		return investigate(resSet);
	}

	/** This method makes the same assumptions as {@link #computeAndShowStatsFor(ResourceSet)}. */
	private FileLoadInfo investigate(ResourceSet resSet) {
		final Resource resMain = resSet.getResources().get(0);
		final FileLoadInfo result = new FileLoadInfo(resMain.getURI());
		result.countTotal = resSet.getResources().size();
		result.countBuiltIn = countN4JSResourcesBuiltIn(resSet);
		result.countLoadedFromAST = countN4JSResourcesLoadedFromAST(resSet) - 1; // do not count 'res' itself
		result.countLoadedFromIndex = countN4JSResourcesLoadedFromIndex(resSet);
		return result;
	}

	private int countN4JSResourcesBuiltIn(ResourceSet resSet) {
		int n = 0;
		for (Resource res : resSet.getResources()) {
			if (isBuiltInResource(res)) {
				n++;
			}
		}
		return n;
	}

	private int countN4JSResourcesLoadedFromAST(ResourceSet resSet) {
		int n = 0;
		for (Resource res : resSet.getResources()) {
			if (!isBuiltInResource(res)) {
				if (res instanceof N4JSResource) {
					final N4JSResource resCasted = (N4JSResource) res;
					if (!resCasted.isLoadedFromDescription()) {
						n++;
					}
				}
			}
		}
		return n;
	}

	private int countN4JSResourcesLoadedFromIndex(ResourceSet resSet) {
		int n = 0;
		for (Resource res : resSet.getResources()) {
			if (!isBuiltInResource(res)) {
				if (res instanceof N4JSResource) {
					final N4JSResource resCasted = (N4JSResource) res;
					if (resCasted.isLoadedFromDescription()) {
						n++;
					}
				}
			}
		}
		return n;
	}

	private List<URI> collectURIsToInvestigate(IN4JSProject project) {
		final List<URI> urisToInvestigate = Lists.newArrayList();
		for (IN4JSSourceContainer container : project.getSourceContainers()) {
			for (URI uri : container) {
				final String fileExtension = uri.fileExtension();
				switch (fileExtension) {
				case "n4js":
				case "n4jsd":
				case "n4jsx":
					urisToInvestigate.add(uri);
				}
			}
		}
		return urisToInvestigate;
	}

	private boolean isBuiltInResource(Resource res) {
		return N4Scheme.isResourceWithN4Scheme(res);
	}

	private boolean isManagedByLibraryManager(IN4JSProject project) {
		if (project instanceof IN4JSEclipseProject) {
			IProject eclipseProject = ((IN4JSEclipseProject) project).getProject();
			return eclipseProject instanceof ExternalProject;
		}
		return false;
	}
}
