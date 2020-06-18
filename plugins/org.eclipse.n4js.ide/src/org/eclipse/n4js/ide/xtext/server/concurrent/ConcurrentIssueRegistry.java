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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.LSPIssue;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.ImmutableList;
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
	protected final Map<URI, ImmutableSortedSet<LSPIssue>> persistedIssues = new HashMap<>();
	protected final Map<String, Map<URI, ImmutableSortedSet<LSPIssue>>> container2persistedIssues = new HashMap<>();

	protected final List<IIssueRegistryListener> listeners = new ArrayList<>();

	public interface IIssueRegistryListener {
		public void onIssuesChanged(ImmutableList<IssueRegistryChangeEvent> event);
	}

	public static class IssueRegistryChangeEvent {
		public final boolean dirtyState;
		public final boolean persistedState;
		/** Will be <code>null</code> iff {@link #dirtyState} is <code>true</code>. */
		public final String containerHandle;
		public final URI uri;
		/**
		 * Empty set means {@link #uri} was free of issues before; <code>null</code> means {@code #uri} did not exist
		 * before.
		 */
		public final ImmutableSortedSet<LSPIssue> issuesOld;
		/**
		 * Empty set means issues for {@link #uri} were cleared; <code>null</code> means {@link #uri} was removed
		 * entirely.
		 */
		public final ImmutableSortedSet<LSPIssue> issuesNew;

		public IssueRegistryChangeEvent(boolean dirtyState, String containerHandle, URI uri,
				ImmutableSortedSet<LSPIssue> issuesOld, ImmutableSortedSet<LSPIssue> issuesNew) {
			if (dirtyState && containerHandle != null) {
				throw new IllegalArgumentException("dirty state event with non-null containerHandle");
			} else if (!dirtyState && containerHandle == null) {
				throw new IllegalArgumentException("persisted state event with null containerHandle");
			}
			this.dirtyState = dirtyState;
			this.persistedState = !dirtyState;
			this.containerHandle = containerHandle;
			this.uri = uri;
			this.issuesOld = issuesOld;
			this.issuesNew = issuesNew;
		}
	}

	public ConcurrentIssueRegistry() {
		this(defaultIssueComparator);
	}

	public ConcurrentIssueRegistry(Comparator<LSPIssue> issueComparator) {
		this.issueComparator = issueComparator;
	}

	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfDirtyOrPersistedState(URI uri) {
		ImmutableSortedSet<LSPIssue> result = getIssuesOfDirtyState(uri);
		if (result != null) {
			return result;
		}
		return getIssuesOfPersistedState(uri);
	}

	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfDirtyState(URI uri) {
		return dirtyIssues.get(uri);
	}

	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfPersistedState(URI uri) {
		return persistedIssues.get(uri);
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

	public void clear() {
		List<IssueRegistryChangeEvent> events = new ArrayList<>();
		synchronized (this) {
			events.addAll(doClearIssuesOfDirtyState());
			events.addAll(doClearIssuesOfPersistedState());
		}
		notifyListeners(events);
	}

	public void clearIssuesOfDirtyState() {
		List<IssueRegistryChangeEvent> events = doClearIssuesOfDirtyState();
		notifyListeners(events);
	}

	public synchronized List<IssueRegistryChangeEvent> doClearIssuesOfDirtyState() {
		List<IssueRegistryChangeEvent> events = dirtyIssues.entrySet().stream()
				.map(e -> eventDirty(e.getKey(), e.getValue(), null))
				.collect(Collectors.toList());
		dirtyIssues.clear();
		return events;
	}

	public boolean clearIssuesOfDirtyState(URI uri) {
		IssueRegistryChangeEvent event;
		synchronized (this) {
			ImmutableSortedSet<LSPIssue> issuesOld = dirtyIssues.remove(uri);
			event = issuesOld != null ? eventDirty(uri, issuesOld, null) : null;
		}
		if (event != null) {
			notifyListeners(event);
			return true;
		}
		return false;
	}

	public void clearIssuesOfPersistedState() {
		List<IssueRegistryChangeEvent> events = doClearIssuesOfPersistedState();
		notifyListeners(events);
	}

	public synchronized List<IssueRegistryChangeEvent> doClearIssuesOfPersistedState() {
		List<IssueRegistryChangeEvent> events = container2persistedIssues.entrySet().stream()
				.flatMap(containerHandle2IssueMap -> {
					String containerHandle = containerHandle2IssueMap.getKey();
					return containerHandle2IssueMap.getValue().entrySet().stream()
							.map(uri2issues -> eventPersisted(containerHandle,
									uri2issues.getKey(), uri2issues.getValue(), null));
				})
				.collect(Collectors.toList());
		persistedIssues.clear();
		container2persistedIssues.clear();
		return events;
	}

	public boolean clearIssuesOfPersistedState(String containerHandle) {
		List<IssueRegistryChangeEvent> events;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, false);
			if (containerIssues == null) {
				return false;
			}
			events = containerIssues.entrySet().stream()
					.map(e -> eventPersisted(containerHandle, e.getKey(), e.getValue(), null))
					.collect(Collectors.toList());
			containerIssues.keySet().forEach(persistedIssues::remove);
			container2persistedIssues.remove(containerHandle);
		}
		notifyListeners(events);
		return true;
	}

	public boolean clearIssuesOfPersistedState(String containerHandle, URI uri) {
		IssueRegistryChangeEvent event;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, false);
			if (containerIssues == null) {
				return false;
			}
			persistedIssues.remove(uri);
			ImmutableSortedSet<LSPIssue> issuesOld = containerIssues.remove(uri);
			event = issuesOld != null ? eventPersisted(containerHandle, uri, issuesOld, null) : null;
		}
		if (event != null) {
			notifyListeners(event);
			return true;
		}
		return false;
	}

	public void setIssuesOfDirtyState(URI uri, Iterable<? extends LSPIssue> issues) {
		ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.copyOf(issueComparator, issues);
		ImmutableSortedSet<LSPIssue> issuesOld;
		synchronized (this) {
			issuesOld = dirtyIssues.put(uri, issuesNew);
		}
		notifyListeners(eventDirty(uri, issuesOld, issuesNew));
	}

	public void setIssuesOfPersistedState(String containerHandle, URI uri, Iterable<? extends LSPIssue> issues) {
		ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.copyOf(issueComparator, issues);
		ImmutableSortedSet<LSPIssue> issuesOld;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, true);
			persistedIssues.put(uri, issuesNew);
			issuesOld = containerIssues.put(uri, issuesNew);
		}
		notifyListeners(eventPersisted(containerHandle, uri, issuesOld, issuesNew));
	}

	public synchronized void addIssueOfPersistedState(String containerHandle, URI uri, LSPIssue issue) {
		ImmutableSortedSet<LSPIssue> issuesOld;
		ImmutableSortedSet<LSPIssue> issuesNew;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, true);
			issuesOld = containerIssues.get(uri);
			issuesNew = ImmutableSortedSet.orderedBy(issueComparator)
					.addAll(issuesOld != null ? issuesOld : Collections.emptyList())
					.add(issue).build();
			persistedIssues.put(uri, issuesNew);
			containerIssues.put(uri, issuesNew);
		}
		notifyListeners(eventPersisted(containerHandle, uri, issuesOld, issuesNew));
	}

	protected synchronized Map<URI, ImmutableSortedSet<LSPIssue>> getContainerIssues(String containerHandle,
			boolean createIfNecessary) {

		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = container2persistedIssues.get(containerHandle);
		if (containerIssues == null && createIfNecessary) {
			containerIssues = new HashMap<>();
			container2persistedIssues.put(containerHandle, containerIssues);
		}
		return containerIssues;
	}

	public synchronized void addListener(IIssueRegistryListener l) {
		listeners.add(l);
	}

	public synchronized void removeListener(IIssueRegistryListener l) {
		listeners.remove(l);
	}

	protected void notifyListeners(IssueRegistryChangeEvent event) {
		notifyListeners(Collections.singletonList(event));
	}

	protected void notifyListeners(List<IssueRegistryChangeEvent> events) {
		List<IIssueRegistryListener> _listeners;
		synchronized (this) {
			_listeners = new ArrayList<>(listeners);
		}
		ImmutableList<IssueRegistryChangeEvent> _events = ImmutableList.copyOf(events);
		for (IIssueRegistryListener l : _listeners) {
			l.onIssuesChanged(_events);
		}
	}

	protected IssueRegistryChangeEvent eventDirty(URI uri, ImmutableSortedSet<LSPIssue> oldIssues,
			ImmutableSortedSet<LSPIssue> newIssues) {
		return new IssueRegistryChangeEvent(true, null, uri, oldIssues, newIssues);
	}

	protected IssueRegistryChangeEvent eventPersisted(String containerHandle, URI uri,
			ImmutableSortedSet<LSPIssue> issuesOld, ImmutableSortedSet<LSPIssue> issuesNew) {
		return new IssueRegistryChangeEvent(false, containerHandle, uri, issuesOld, issuesNew);
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
