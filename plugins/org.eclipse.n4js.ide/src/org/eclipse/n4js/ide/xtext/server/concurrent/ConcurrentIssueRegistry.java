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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.LSPIssue;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.inject.Singleton;

/**
 * A registry for {@link LSPIssue issues} that can be shared across threads, distinguishing between persisted and dirty
 * state. In the former case, issues are recorded on a per-project basis.
 */
@Singleton
public class ConcurrentIssueRegistry {

	protected final Comparator<LSPIssue> issueComparator;

	protected final Map<URI, ImmutableSortedSet<LSPIssue>> dirtyIssues = new HashMap<>();
	protected final Map<URI, ImmutableSortedSet<LSPIssue>> persistedIssues = new HashMap<>();
	protected final Map<String, Map<URI, ImmutableSortedSet<LSPIssue>>> project2persistedIssues = new HashMap<>();

	protected final List<IIssueRegistryListener> listeners = new CopyOnWriteArrayList<>();

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
		public final ImmutableSortedSet<LSPIssue> dirtyIssuesOld;
		/**
		 * Empty set means issues for {@link #uri} were cleared; <code>null</code> means {@link #uri} was removed
		 * entirely.
		 */
		public final ImmutableSortedSet<LSPIssue> dirtyIssuesNew;
		/**
		 * Empty set means {@link #uri} was free of issues before; <code>null</code> means {@code #uri} did not exist
		 * before.
		 */
		public final ImmutableSortedSet<LSPIssue> persistedIssuesOld;
		/**
		 * Empty set means issues for {@link #uri} were cleared; <code>null</code> means {@link #uri} was removed
		 * entirely.
		 */
		public final ImmutableSortedSet<LSPIssue> persistedIssuesNew;

		public IssueRegistryChangeEvent(boolean dirtyState, String containerHandle, URI uri,
				ImmutableSortedSet<LSPIssue> dirtyIssuesOld, ImmutableSortedSet<LSPIssue> dirtyIssuesNew,
				ImmutableSortedSet<LSPIssue> persistedIssuesOld, ImmutableSortedSet<LSPIssue> persistedIssuesNew) {
			if (dirtyState && containerHandle != null) {
				throw new IllegalArgumentException("dirty state event with non-null containerHandle");
			} else if (!dirtyState && containerHandle == null) {
				throw new IllegalArgumentException("persisted state event with null containerHandle");
			}
			this.dirtyState = dirtyState;
			this.persistedState = !dirtyState;
			this.containerHandle = containerHandle;
			this.uri = uri;
			this.dirtyIssuesOld = dirtyIssuesOld;
			this.dirtyIssuesNew = dirtyIssuesNew;
			this.persistedIssuesOld = persistedIssuesOld;
			this.persistedIssuesNew = persistedIssuesNew;
		}
	}

	public ConcurrentIssueRegistry() {
		this(defaultIssueComparator);
	}

	public ConcurrentIssueRegistry(Comparator<LSPIssue> issueComparator) {
		this.issueComparator = issueComparator;
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
		List<IssueRegistryChangeEvent> events = project2persistedIssues.entrySet().stream()
				.flatMap(containerHandle2IssueMap -> {
					String containerHandle = containerHandle2IssueMap.getKey();
					return containerHandle2IssueMap.getValue().entrySet().stream()
							.map(uri2issues -> eventPersisted(containerHandle,
									uri2issues.getKey(), uri2issues.getValue(), null));
				})
				.collect(Collectors.toList());
		persistedIssues.clear();
		project2persistedIssues.clear();
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
			project2persistedIssues.remove(containerHandle);
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
		IssueRegistryChangeEvent event;
		synchronized (this) {
			ImmutableSortedSet<LSPIssue> issuesOld = dirtyIssues.put(uri, issuesNew);
			event = eventDirty(uri, issuesOld, issuesNew);
		}
		notifyListeners(event);
	}

	public void setIssuesOfPersistedState(String containerHandle, URI uri, Iterable<? extends LSPIssue> issues) {
		ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.copyOf(issueComparator, issues);
		IssueRegistryChangeEvent event;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, true);
			persistedIssues.put(uri, issuesNew);
			ImmutableSortedSet<LSPIssue> issuesOld = containerIssues.put(uri, issuesNew);
			event = eventPersisted(containerHandle, uri, issuesOld, issuesNew);
		}
		notifyListeners(event);
	}

	public void addIssueOfPersistedState(String containerHandle, URI uri, LSPIssue issue) {
		IssueRegistryChangeEvent event;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = getContainerIssues(containerHandle, true);
			ImmutableSortedSet<LSPIssue> issuesOld = containerIssues.get(uri);
			ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.orderedBy(issueComparator)
					.addAll(issuesOld != null ? issuesOld : Collections.emptyList())
					.add(issue).build();
			persistedIssues.put(uri, issuesNew);
			containerIssues.put(uri, issuesNew);
			event = eventPersisted(containerHandle, uri, issuesOld, issuesNew);
		}
		notifyListeners(event);
	}

	protected synchronized Map<URI, ImmutableSortedSet<LSPIssue>> getContainerIssues(String containerHandle,
			boolean createIfNecessary) {

		Map<URI, ImmutableSortedSet<LSPIssue>> containerIssues = project2persistedIssues.get(containerHandle);
		if (containerIssues == null && createIfNecessary) {
			containerIssues = new HashMap<>();
			project2persistedIssues.put(containerHandle, containerIssues);
		}
		return containerIssues;
	}

	public void addListener(IIssueRegistryListener l) {
		listeners.add(l);
	}

	public void removeListener(IIssueRegistryListener l) {
		listeners.remove(l);
	}

	protected void notifyListeners(IssueRegistryChangeEvent event) {
		notifyListeners(Collections.singletonList(event));
	}

	protected void notifyListeners(List<IssueRegistryChangeEvent> events) {
		ImmutableList<IssueRegistryChangeEvent> _events = ImmutableList.copyOf(events);
		for (IIssueRegistryListener l : listeners) {
			l.onIssuesChanged(_events);
		}
	}

	protected synchronized IssueRegistryChangeEvent eventDirty(URI uri, ImmutableSortedSet<LSPIssue> dirtyIssuesOld,
			ImmutableSortedSet<LSPIssue> dirtyIssuesNew) {
		ImmutableSortedSet<LSPIssue> persistedIssuesCurr = persistedIssues.get(uri);
		return new IssueRegistryChangeEvent(true, null, uri, dirtyIssuesOld, dirtyIssuesNew, persistedIssuesCurr,
				persistedIssuesCurr);
	}

	protected synchronized IssueRegistryChangeEvent eventPersisted(String containerHandle, URI uri,
			ImmutableSortedSet<LSPIssue> persistedIssuesOld, ImmutableSortedSet<LSPIssue> persistedIssuesNew) {
		ImmutableSortedSet<LSPIssue> dirtyIssuesCurr = dirtyIssues.get(uri);
		return new IssueRegistryChangeEvent(false, containerHandle, uri, dirtyIssuesCurr, dirtyIssuesCurr,
				persistedIssuesOld, persistedIssuesNew);
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
