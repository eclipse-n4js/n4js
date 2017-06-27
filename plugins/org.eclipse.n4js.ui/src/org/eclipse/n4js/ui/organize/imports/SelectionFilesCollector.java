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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkingSet;

import com.google.common.collect.Lists;

/**
 * Collects files from {@link IStructuredSelection}. Collects items that pass test with provided filters. Additionally
 * result is filtered for duplicates (based on {@link Set} collection).
 */
public class SelectionFilesCollector {
	private static final Logger LOGGER = Logger.getLogger(SelectionFilesCollector.class);

	private final Predicate<IFile> fileFilter;

	/** Constructs collector with provided filters. */
	public SelectionFilesCollector(Predicate<IFile> fileFilter) {
		this.fileFilter = fileFilter;
	}

	/** Collects files from provided selection. */
	public List<IFile> collectFiles(IStructuredSelection structuredSelection) {
		Set<IFile> collected = new HashSet<>();
		for (Object object : structuredSelection.toList()) {
			collectRelevantFiles(object, collected);
		}

		return Lists.newArrayList(collected);
	}

	/** Dispatches based on provided element (can call itself recursively). */
	private void collectRelevantFiles(Object element, Set<IFile> collected) {
		// order of type check matters!
		if (element instanceof IWorkingSet) {
			collectIAdaptable((IWorkingSet) element, collected);
		} else if (element instanceof IContainer) {
			collectResource((IContainer) element, collected);
		} else if (element instanceof IFile) {
			collectIFile((IFile) element, collected);
		} else {
			LOGGER.warn("Files collector ignores " + element.getClass().getName() + ".");
		}
	}

	private void collectIAdaptable(IWorkingSet workingSet, Set<IFile> collected) {
		IAdaptable[] adaptables = workingSet.getElements();
		for (IAdaptable adaptable : adaptables) {
			collectRelevantFiles(adaptable, collected);
		}
	}

	private void collectResource(IContainer container, Set<IFile> collected) {
		try {
			IResource[] resources = container.members(IContainer.EXCLUDE_DERIVED);
			for (IResource resource : resources) {
				collectRelevantFiles(resource, collected);
			}
		} catch (CoreException c) {
			LOGGER.warn("Error while collecting files", c);
		}
	}

	private void collectIFile(IFile iFile, Set<IFile> collected) {
		if (fileFilter.test(iFile))
			collected.add(iFile);
	}
}
