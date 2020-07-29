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
package org.eclipse.n4js.ide.xtext.server.build;

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
	/*
	 * Review feedback:
	 *
	 * Used from outside of build. Maybe we should introduce a minimal interface that is required from the outside and
	 * expose that one via the BuilderFrontend (not via getter, but as methods on the BuilderFrontend).
	 */

	/** Issues are sorted, based on this comparator. See {@link #defaultIssueComparator} for motivation. */
	protected final Comparator<LSPIssue> issueComparator;

	/** Issues in resources based on their dirty state, e.g. state in an open editor. */
	protected final Map<URI, ImmutableSortedSet<LSPIssue>> dirtyIssues = new HashMap<>();
	/** Issues in resources based on their persisted state, i.e. state on disk. */
	protected final Map<URI, ImmutableSortedSet<LSPIssue>> persistedIssues = new HashMap<>();
	/** Maps project names to issues of all resources contained in the project. */
	protected final Map<String, Map<URI, ImmutableSortedSet<LSPIssue>>> project2persistedIssues = new HashMap<>();

	/***/
	protected final List<IIssueRegistryListener> listeners = new CopyOnWriteArrayList<>();

	/** A listener interested in issue changes, i.e. issues appearing in and disappearing from a resource. */
	public interface IIssueRegistryListener {
		/** Invoked when issues have changed. See {@link IssueRegistryChangeEvent}. */
		public void onIssuesChanged(ImmutableList<IssueRegistryChangeEvent> event);
	}

	/**
	 * A change of issues. Such an event applies either exclusively to the {@link #dirtyState} or exclusively to the
	 * {@link #persistedState}, never a combination of both.
	 */
	public static class IssueRegistryChangeEvent {
		/** Iff <code>true</code>, this event is related to the dirty state and only to the dirty state. */
		public final boolean dirtyState;
		/** Iff <code>true</code>, this event is related to the persisted state and only to the persisted state. */
		public final boolean persistedState;
		/** Will be <code>null</code> iff {@link #dirtyState} is <code>true</code>. */
		public final String projectName;
		/** URI of the resource containing the changed issues. */
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

		/** Creates a new {@link IssueRegistryChangeEvent}. */
		public IssueRegistryChangeEvent(boolean dirtyState, String projectName, URI uri,
				ImmutableSortedSet<LSPIssue> dirtyIssuesOld, ImmutableSortedSet<LSPIssue> dirtyIssuesNew,
				ImmutableSortedSet<LSPIssue> persistedIssuesOld, ImmutableSortedSet<LSPIssue> persistedIssuesNew) {
			if (dirtyState && projectName != null) {
				throw new IllegalArgumentException("dirty state event with non-null project name");
			} else if (!dirtyState && projectName == null) {
				throw new IllegalArgumentException("persisted state event with null project name");
			}
			this.dirtyState = dirtyState;
			this.persistedState = !dirtyState;
			this.projectName = projectName;
			this.uri = uri;
			this.dirtyIssuesOld = dirtyIssuesOld;
			this.dirtyIssuesNew = dirtyIssuesNew;
			this.persistedIssuesOld = persistedIssuesOld;
			this.persistedIssuesNew = persistedIssuesNew;
		}
	}

	/** Creates a new {@link ConcurrentIssueRegistry}. */
	public ConcurrentIssueRegistry() {
		this(defaultIssueComparator);
	}

	/** Creates a new {@link ConcurrentIssueRegistry}. */
	public ConcurrentIssueRegistry(Comparator<LSPIssue> issueComparator) {
		this.issueComparator = issueComparator;
	}

	/** Returns issues according to the dirty state of the resource with the given URI. */
	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfDirtyState(URI uri) {
		return dirtyIssues.get(uri);
	}

	/** Returns issues according to the persisted state of the resource with the given URI. */
	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfPersistedState(URI uri) {
		return persistedIssues.get(uri);
	}

	/** Return issues contained in the project with the given name. */
	public synchronized ImmutableMap<URI, ImmutableSortedSet<LSPIssue>> getIssuesOfPersistedState(
			String projectName) {
		Map<URI, ImmutableSortedSet<LSPIssue>> projectIssues = getProjectIssues(projectName, false);
		return projectIssues != null ? ImmutableMap.copyOf(projectIssues) : null;
	}

	/**
	 * Return issues contained in the resource with the given URI, but only if it is contained in the project with the
	 * given name.
	 */
	public synchronized ImmutableSortedSet<LSPIssue> getIssuesOfPersistedState(String projectName, URI uri) {
		Map<URI, ImmutableSortedSet<LSPIssue>> projectIssues = getProjectIssues(projectName, false);
		return projectIssues != null ? projectIssues.get(uri) : null;
	}

	/** Remove all information from this registry. */
	public void clear() {
		List<IssueRegistryChangeEvent> events = new ArrayList<>();
		synchronized (this) {
			events.addAll(doClearIssuesOfDirtyState());
			events.addAll(doClearIssuesOfPersistedState());
		}
		notifyListeners(events);
	}

	/** Same as {@link #clearIssuesOfDirtyState(URI)}, but for all resources. */
	public void clearIssuesOfDirtyState() {
		List<IssueRegistryChangeEvent> events = doClearIssuesOfDirtyState();
		notifyListeners(events);
	}

	/** Internal implementation of {@link #clearIssuesOfDirtyState()}. */
	protected synchronized List<IssueRegistryChangeEvent> doClearIssuesOfDirtyState() {
		List<IssueRegistryChangeEvent> events = dirtyIssues.entrySet().stream()
				.map(e -> eventDirty(e.getKey(), e.getValue(), null))
				.collect(Collectors.toList());
		dirtyIssues.clear();
		return events;
	}

	/**
	 * For the dirty state, remove the resource with the given URI from this registry entirely. If instead the
	 * resource's set of issues should be changed to the empty set, use method
	 * {@link #setIssuesOfDirtyState(URI, Iterable)}.
	 */
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

	/** Same as {@link #clearIssuesOfPersistedState(String, URI)}, but for all projects and resources */
	public void clearIssuesOfPersistedState() {
		List<IssueRegistryChangeEvent> events = doClearIssuesOfPersistedState();
		notifyListeners(events);
	}

	/** Internal implementation of {@link #clearIssuesOfPersistedState()}. */
	protected synchronized List<IssueRegistryChangeEvent> doClearIssuesOfPersistedState() {
		List<IssueRegistryChangeEvent> events = project2persistedIssues.entrySet().stream()
				.flatMap(project2IssueMap -> {
					String projectName = project2IssueMap.getKey();
					return project2IssueMap.getValue().entrySet().stream()
							.map(uri2issues -> eventPersisted(projectName,
									uri2issues.getKey(), uri2issues.getValue(), null));
				})
				.collect(Collectors.toList());
		persistedIssues.clear();
		project2persistedIssues.clear();
		return events;
	}

	/** Same as {@link #clearIssuesOfPersistedState(String, URI)}, but for all resources in the project. */
	public boolean clearIssuesOfPersistedState(String projectName) {
		List<IssueRegistryChangeEvent> events;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> projectIssues = getProjectIssues(projectName, false);
			if (projectIssues == null) {
				return false;
			}
			events = projectIssues.entrySet().stream()
					.map(e -> eventPersisted(projectName, e.getKey(), e.getValue(), null))
					.collect(Collectors.toList());
			projectIssues.keySet().forEach(persistedIssues::remove);
			project2persistedIssues.remove(projectName);
		}
		notifyListeners(events);
		return true;
	}

	/**
	 * For the persisted state, remove the resource with the given URI from this registry entirely. If instead the
	 * resource's set of issues should be changed to the empty set, use method
	 * {@link #setIssuesOfDirtyState(URI, Iterable)}.
	 */
	public boolean clearIssuesOfPersistedState(String projectName, URI uri) {
		IssueRegistryChangeEvent event;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> projectIssues = getProjectIssues(projectName, false);
			if (projectIssues == null) {
				return false;
			}
			persistedIssues.remove(uri);
			ImmutableSortedSet<LSPIssue> issuesOld = projectIssues.remove(uri);
			event = issuesOld != null ? eventPersisted(projectName, uri, issuesOld, null) : null;
		}
		if (event != null) {
			notifyListeners(event);
			return true;
		}
		return false;
	}

	/**
	 * For the dirty state, set the issues of the resource with the given URI. Existing issues will be replaced; passing
	 * in an empty list removes all existing issues.
	 * <p>
	 * If instead the resource should be removed from the registry entirely, use method
	 * {@link #clearIssuesOfDirtyState(URI)}.
	 */
	public void setIssuesOfDirtyState(URI uri, Iterable<? extends LSPIssue> issues) {
		ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.copyOf(issueComparator, issues);
		IssueRegistryChangeEvent event;
		synchronized (this) {
			ImmutableSortedSet<LSPIssue> issuesOld = dirtyIssues.put(uri, issuesNew);
			event = eventDirty(uri, issuesOld, issuesNew);
		}
		notifyListeners(event);
	}

	/**
	 * For the persisted state, set the issues of the resource with the given URI. Existing issues will be replaced;
	 * passing in an empty list removes all existing issues.
	 * <p>
	 * If instead the resource should be removed from the registry entirely, use method
	 * {@link #clearIssuesOfPersistedState(String, URI)}.
	 */
	public void setIssuesOfPersistedState(String projectName, URI uri, Iterable<? extends LSPIssue> issues) {
		ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.copyOf(issueComparator, issues);
		IssueRegistryChangeEvent event;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> projectIssues = getProjectIssues(projectName, true);
			persistedIssues.put(uri, issuesNew);
			ImmutableSortedSet<LSPIssue> issuesOld = projectIssues.put(uri, issuesNew);
			event = eventPersisted(projectName, uri, issuesOld, issuesNew);
		}
		notifyListeners(event);
	}

	/**
	 * For the persisted state, add the given issue to the currently registered issues of the resource with the given
	 * URI. Safe to use even if nothing is registered for the given project name / URI yet.
	 */
	public void addIssueOfPersistedState(String projectName, URI uri, LSPIssue issue) {
		IssueRegistryChangeEvent event;
		synchronized (this) {
			Map<URI, ImmutableSortedSet<LSPIssue>> projectIssues = getProjectIssues(projectName, true);
			ImmutableSortedSet<LSPIssue> issuesOld = projectIssues.get(uri);
			ImmutableSortedSet<LSPIssue> issuesNew = ImmutableSortedSet.orderedBy(issueComparator)
					.addAll(issuesOld != null ? issuesOld : Collections.emptyList())
					.add(issue).build();
			persistedIssues.put(uri, issuesNew);
			projectIssues.put(uri, issuesNew);
			event = eventPersisted(projectName, uri, issuesOld, issuesNew);
		}
		notifyListeners(event);
	}

	/** Returns value of map {@link #project2persistedIssues} for given project name, creating it if necessary. */
	protected synchronized Map<URI, ImmutableSortedSet<LSPIssue>> getProjectIssues(String projectName,
			boolean createIfNecessary) {

		Map<URI, ImmutableSortedSet<LSPIssue>> projectIssues = project2persistedIssues.get(projectName);
		if (projectIssues == null && createIfNecessary) {
			projectIssues = new HashMap<>();
			project2persistedIssues.put(projectName, projectIssues);
		}
		return projectIssues;
	}

	/** Add a {@link IIssueRegistryListener listener}. */
	public void addListener(IIssueRegistryListener l) {
		listeners.add(l);
	}

	/** Remove a {@link IIssueRegistryListener listener}. */
	public void removeListener(IIssueRegistryListener l) {
		listeners.remove(l);
	}

	/** Same as {@link #notifyListeners(List)}, but for a single event. */
	protected void notifyListeners(IssueRegistryChangeEvent event) {
		notifyListeners(Collections.singletonList(event));
	}

	/**
	 * Notify listeners. It's important to call this while <em>NOT</em> synchronizing on 'this', because we do not want
	 * to retain a lock on monitor 'this' while the listeners' code is being executed (mitigate dead locks).
	 */
	protected void notifyListeners(List<IssueRegistryChangeEvent> events) {
		ImmutableList<IssueRegistryChangeEvent> _events = ImmutableList.copyOf(events);
		for (IIssueRegistryListener l : listeners) {
			l.onIssuesChanged(_events);
		}
	}

	/** Create a {@link IssueRegistryChangeEvent} for changes to the dirty state. */
	protected synchronized IssueRegistryChangeEvent eventDirty(URI uri, ImmutableSortedSet<LSPIssue> dirtyIssuesOld,
			ImmutableSortedSet<LSPIssue> dirtyIssuesNew) {
		ImmutableSortedSet<LSPIssue> persistedIssuesCurr = persistedIssues.get(uri);
		return new IssueRegistryChangeEvent(true, null, uri, dirtyIssuesOld, dirtyIssuesNew, persistedIssuesCurr,
				persistedIssuesCurr);
	}

	/** Create a {@link IssueRegistryChangeEvent} for changes to the persisted state. */
	protected synchronized IssueRegistryChangeEvent eventPersisted(String projectName, URI uri,
			ImmutableSortedSet<LSPIssue> persistedIssuesOld, ImmutableSortedSet<LSPIssue> persistedIssuesNew) {
		ImmutableSortedSet<LSPIssue> dirtyIssuesCurr = dirtyIssues.get(uri);
		return new IssueRegistryChangeEvent(false, projectName, uri, dirtyIssuesCurr, dirtyIssuesCurr,
				persistedIssuesOld, persistedIssuesNew);
	}

	/**
	 * Implementation note: We use a sorted map to report the issues in a stable order. The values of the the map are
	 * sorted by offset and severity and message.
	 * <p>
	 * URI (keys in the multimap) are sorted according to their location in the file system. Turns out that the string
	 * representation yields the same result as a comparison per path segment.
	 * <p>
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
