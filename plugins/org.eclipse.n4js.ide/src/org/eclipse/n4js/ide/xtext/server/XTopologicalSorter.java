/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;

import org.eclipse.xtext.resource.impl.ProjectDescription;

@SuppressWarnings("all")
public class XTopologicalSorter {

	protected static class Entry {
		private final ProjectDescription description;
		private boolean marked;
		private boolean cyclic;

		@Override
		public String toString() {
			return description.getName();
		}

		public Entry(ProjectDescription description) {
			this.description = description;
		}
	}

	private Consumer<ProjectDescription> cyclicAcceptor;
	private SortedMap<String, Entry> name2entry;
	private LinkedHashSet<ProjectDescription> result;

	public List<ProjectDescription> sortByDependencies(Iterable<ProjectDescription> descriptions,
			Consumer<ProjectDescription> cyclicAcceptor) {

		this.cyclicAcceptor = cyclicAcceptor;
		this.name2entry = new TreeMap<>();
		this.result = new LinkedHashSet();

		for (ProjectDescription pd : descriptions) {
			name2entry.put(pd.getName(), new Entry(pd));
		}

		for (Entry entry : name2entry.values()) {
			visit(entry);
		}

		return new ArrayList<>(result);
	}

	protected boolean visit(Entry entry) {
		if (!result.contains(entry.description) && !entry.cyclic) {
			if (entry.marked) {
				markCyclic(entry);
				return false;
			}
			entry.marked = true;
			List<String> dependencies = entry.description.getDependencies();
			Collections.sort(dependencies);

			for (String depName : dependencies) {
				Entry depEntry = name2entry.get(depName);
				if (depEntry != null && !visit(depEntry)) {
					markCyclic(entry);
					return false;
				}
			}
			entry.marked = false;
			result.add(entry.description);
		}
		return true;
	}

	protected void markCyclic(Entry it) {
		if (!it.cyclic) {
			it.cyclic = true;
			cyclicAcceptor.accept(it.description);
		}
	}
}
