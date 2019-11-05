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
package org.eclipse.n4js.compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.n4js.projectModel.names.N4JSProjectName;

/**
 * Comparison of one or more API projects and their corresponding implementation projects. This is mainly a tree of
 * {@link ProjectComparisonEntry ProjectComparisonEntries} that relate projects/types/members on API side to their
 * corresponding counter parts on implementation side.
 * <p>
 * The array of implementation IDs returned by method {@link #getImplIds()} defines the implementations covered within
 * this project comparison and also defines implementation indices used in this ProjectComparison and all its contained
 * project comparison entries.
 */
@SuppressWarnings("javadoc")
public class ProjectComparison {

	final N4JSProjectName[] implIds;
	final List<ProjectComparisonEntry> entries = new ArrayList<>();

	final Map<Object, ProjectComparisonEntry> objToEntry = new HashMap<>();

	public ProjectComparison(N4JSProjectName[] implIds) {
		if (implIds == null)
			throw new IllegalArgumentException();
		this.implIds = implIds;
	}

	public int getImplCount() {
		return implIds.length;
	}

	public N4JSProjectName[] getImplIds() {
		return implIds;
	}

	public N4JSProjectName getImplId(int implIndex) {
		return implIds != null && implIndex >= 0 && implIndex < implIds.length ? implIds[implIndex] : null;
	}

	public int getImplIndex(N4JSProjectName implId) {
		for (int idx = 0; idx < implIds.length; idx++)
			if (implId.equals(implIds[idx]))
				return idx;
		return -1;
	}

	/** should only be invoked from ProjectComparisonEntry */
	/* package */void internalAddRootEntry(ProjectComparisonEntry entry) {
		entries.add(entry);
	}

	/** should only be invoked from ProjectComparisonEntry */
	/* package */void onEntryAdded(ProjectComparisonEntry addedEntry) {
		objToEntry.put(addedEntry.getProjectOrElementApi(), addedEntry);
		final int len = addedEntry.getImplCount();
		for (int idx = 0; idx < len; idx++)
			objToEntry.put(addedEntry.getProjectOrElementImpl(idx), addedEntry);
	}

	public ProjectComparisonEntry[] getEntries() {
		return entries.toArray(new ProjectComparisonEntry[entries.size()]);
	}

	public Stream<ProjectComparisonEntry> getAllEntries() {
		return entries.stream().flatMap(entry -> entry.allChildren());
	}

	public ProjectComparisonEntry getEntryForObject(Object object) {
		return objToEntry.get(object);
	}

	public void clearAllCachedCompareResults() {
		getAllEntries().forEach(entry -> entry.clearCachedCompareResults());
	}
}
