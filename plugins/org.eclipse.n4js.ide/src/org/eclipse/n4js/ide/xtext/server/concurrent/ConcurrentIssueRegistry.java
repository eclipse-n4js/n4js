/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.concurrent;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.LSPIssue;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;

/**
 * Simple registry for {@link LSPIssue issues} that can be shared across threads, distinguishing between persisted and
 * dirty state. In the former case, issues are recorded on a per-container basis ("container" in the sense of
 * {@link ChunkedResourceDescriptions}'s containers).
 */
@SuppressWarnings("javadoc")
public class ConcurrentIssueRegistry {

	protected final Comparator<LSPIssue> issueComparator;

	protected final Map<URI, ImmutableSortedSet<LSPIssue>> dirtyIssues = new HashMap<>();
	protected final Map<String, Map<URI, ImmutableSortedSet<LSPIssue>>> persistedIssues = new HashMap<>();

	public interface IChunkedIssueRegistryListener {
		public void onPersistedIssuesCleared(String containerHandle, URI uri);

	}

	public ConcurrentIssueRegistry() {
		this(defaultIssueComparator);
	}

	public ConcurrentIssueRegistry(Comparator<LSPIssue> issueComparator) {
		this.issueComparator = issueComparator;
	}

	public synchronized ImmutableSortedSet<LSPIssue> getIssues(URI uri) {
		ImmutableSortedSet<LSPIssue> result = dirtyIssues.get(uri);
		if (result != null) {
			return result;
		}
		for (Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues : persistedIssues.values()) {
			result = containerIssues.get(uri);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfDirtyState(URI uri) {
		return dirtyIssues.get(uri);
	}

	public synchronized ImmutableMap<URI, ImmutableSortedSet<LSPIssue>> getIssuesOfPersistedState(
			String containerHandle) {
		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, false);
		return containerIssues != null ? ImmutableMap.copyOf(containerIssues) : null;
	}

	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfPersistedState(String containerHandle, URI uri) {
		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, false);
		return containerIssues != null ? containerIssues.get(uri) : null;
	}

	public synchronized void clear() {
		clearIssuesOfDirtyState();
		clearIssuesOfPersistedState();
	}

	public synchronized void clearIssuesOfDirtyState() {
		dirtyIssues.clear();
	}

	public synchronized boolean clearIssuesOfDirtyState(URI uri) {
		return dirtyIssues.remove(uri) != null;
	}

	public synchronized void clearIssuesOfPersistedState() {
		persistedIssues.clear();
	}

	public synchronized boolean clearIssuesOfPersistedState(String containerHandle) {
		return persistedIssues.remove(containerHandle) != null;
	}

	public synchronized boolean clearIssuesOfPersistedState(String containerHandle, URI uri) {
		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, false);
		return containerIssues != null ? containerIssues.remove(uri) != null : false;
	}

	public synchronized void setIssuesOfDirtyState(URI uri, Iterable<? extends LSPIssue> issues) {
		ImmutableSortedSet<LSPIssue> issuesSorted = ImmutableSortedSet.copyOf(issueComparator, issues);
		dirtyIssues.put(uri, issuesSorted);
	}

	public synchronized void setIssuesOfPersistedState(String containerHandle, URI uri,
			Iterable<? extends LSPIssue> issues) {
		ImmutableSortedSet<LSPIssue> issuesSorted = ImmutableSortedSet.copyOf(issueComparator, issues);
		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, true);
		containerIssues.put(uri, issuesSorted);
	}

	public synchronized void addIssueOfPersistedState(String containerHandle, URI uri, LSPIssue issue) {
		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, true);
		ImmutableSortedSet<LSPIssue> issues = containerIssues.get(uri);
		ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.orderedBy(issueComparator)
				.addAll(issues != null ? issues : Collections.emptyList())
				.add(issue).build();
		containerIssues.put(uri, issuesNew);
	}

	protected synchronized Map<URI, ImmutableSortedSet<LSPIssue>> getContainerIssues(String containerHandle,
			boolean createIfNecessary) {

		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = persistedIssues.get(containerHandle);
		if (containerIssues == null && createIfNecessary) {
			containerIssues = new HashMap<>();
			persistedIssues.put(containerHandle, containerIssues);
		}
		return containerIssues;
	}

	/*
	 * Implementation note: We use a sorted map to report the issues in a stable order. The values of the the map are
	 * sorted by offset and severity and message.
	 *
	 * URI (keys in the multimap) are sorted according to their location in the file system. Turns out that the string
	 * representation yields the same result as a comparison per path segment.
	 *
	 * The sort order will look like this: /a/b, /a/b/c, /a/b/d, /a/c, /aa
	 */
	public static final Comparator<LSPIssue> defaultIssueComparator = Comparator
			.comparing(ConcurrentIssueRegistry::getOffset)
			.thenComparing(ConcurrentIssueRegistry::getSeverity)
			.thenComparing(ConcurrentIssueRegistry::getMessage)
			.thenComparing(Issue::hashCode);

	private static int getOffset(LSPIssue issue) {
		Integer result = issue.getOffset();
		if (result == null) {
			return -1;
		}
		return result;
	}

	private static Severity getSeverity(LSPIssue issue) {
		Severity result = issue.getSeverity();
		if (result == null) {
			return Severity.ERROR;
		}
		return result;
	}

	private static String getMessage(LSPIssue issue) {
		String result = issue.getMessage();
		return Strings.emptyIfNull(result);
	}
}
