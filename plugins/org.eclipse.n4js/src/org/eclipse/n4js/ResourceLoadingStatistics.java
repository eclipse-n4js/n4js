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
package org.eclipse.n4js;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.resources.ExternalProject;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Inject this somewhere and invoke method {@link #investigate()} to start gathering statistics.
 */
@Singleton
@SuppressWarnings("javadoc")
public class ResourceLoadingStatistics {

	@Inject
	private IN4JSCore n4jsCore;

	private static final class FileLoadInfo {
		final URI fileURI;

		int countTotal;
		int countBuiltIn;
		int countLoadedFromAST;
		int countLoadedFromIndex;

		public FileLoadInfo(URI fileURI) {
			this.fileURI = fileURI;
		}

		void print() {
			final String name = fileURI.lastSegment();
			System.out.printf("%-50s   %3d   (%3d = %3d + %3d + %3d)",
					name,
					countTotal,
					countLoadedFromAST + countLoadedFromIndex + countBuiltIn,
					countLoadedFromAST,
					countLoadedFromIndex,
					countBuiltIn);
			System.out.println();
		}

		static void printReport(IN4JSProject project, List<FileLoadInfo> results) {
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("Resource loading per file for project: " + project.getLocation().lastSegment());
			System.out.println();
			final List<FileLoadInfo> othersFromAST = results.stream().filter(result -> result.countLoadedFromAST > 0)
					.collect(Collectors.toList());
			final List<FileLoadInfo> noOthersFromAST = results.stream().filter(result -> result.countLoadedFromAST == 0)
					.collect(Collectors.toList());
			System.out.println("Files that triggered other files being loaded from AST:");
			othersFromAST.forEach(result -> result.print());
			System.out.println();
			System.out.println("Files that did *not* trigger other files being loaded from AST:");
			noOthersFromAST.forEach(result -> result.print());
			System.out.println();
			System.out.println(
					"Files that triggered other files being loaded from AST        : " + othersFromAST.size());
			System.out.println(
					"Files that did *not* trigger other files being loaded from AST: " + noOthersFromAST.size());
			System.out.println("------------------------------------------------------------------------------------");
		}
	}

	public void investigate() {
		final Iterable<IN4JSProject> projects = n4jsCore.findAllProjects();
		for (IN4JSProject project : projects) {
			if (!isManagedByLibraryManager(project)) {
				final List<FileLoadInfo> results = investigate(project);
				FileLoadInfo.printReport(project, results);
			}
		}
	}

	private List<FileLoadInfo> investigate(IN4JSProject project) {
		final List<URI> urisToInvestigate = Lists.newArrayList();
		for (IN4JSSourceContainer container : project.getSourceContainers()) {
			for (URI uri : container) {
				final String lastSegment = uri.lastSegment();
				if (lastSegment.endsWith(".n4js") || lastSegment.endsWith(".n4jsd") || lastSegment.endsWith(".n4jsx")) {
					urisToInvestigate.add(uri);
				}
			}
		}
		return investigate(project, urisToInvestigate);
	}

	private List<FileLoadInfo> investigate(IN4JSProject project, List<URI> urisToInvestigate) {
		final int urisCount = urisToInvestigate.size();
		final List<FileLoadInfo> results = new ArrayList<>(urisCount);
		for (int i = 0; i < urisCount; i++) {
			final URI uri = urisToInvestigate.get(i);
			final int progress = (int) Math.floor(((float) i) / ((float) urisCount) * 100.0f);
			System.out.print("Investigating file " + uri.lastSegment()
					+ " (" + (i + 1) + "/" + urisCount + ", " + progress + "%) ...");
			try {
				results.add(investigate(project, uri));
			} catch (Throwable th) {
				th.printStackTrace();
				// do not abort if investigation of one file fails
			}
			System.out.println(" done.");
		}
		return results;
	}

	private FileLoadInfo investigate(IN4JSProject project, URI fileURI) {
		final ResourceSet resSet = n4jsCore.createResourceSet(Optional.of(project));
		final N4JSResource res = (N4JSResource) resSet.createResource(fileURI);
		try {
			res.load(Collections.emptyMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
		res.getContents(); // trigger loading of AST
		res.resolveLazyCrossReferences(CancelIndicator.NullImpl);

		// now start counting what was loaded incidentally ...
		final FileLoadInfo result = new FileLoadInfo(fileURI);
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
					final Script script = resCasted.getScript();
					if (script != null && !script.eIsProxy()) {
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
					final Script script = resCasted.getScript();
					final TModule module = resCasted.getModule();
					if (script != null && script.eIsProxy() && module != null && !module.eIsProxy()) {
						n++;
					}
				}
			}
		}
		return n;
	}

	private boolean isBuiltInResource(Resource res) {
		return N4Scheme.isResourceWithN4Scheme(res);
	}

	private boolean isManagedByLibraryManager(IN4JSProject project) {
		try {
			IProject eclipseProject = (IProject) project.getClass().getMethod("getProject").invoke(project);
			return eclipseProject instanceof ExternalProject;
		} catch (Throwable th) {
			return false;
		}
	}
}
