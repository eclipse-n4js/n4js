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
package org.eclipse.n4js.npmexporter.validation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 */
@SuppressWarnings("javadoc")
public class Diagnostician implements IssueConsumer {

	LinkedHashMap<IN4JSProject, List<Issue>> project2issues;
	List<Issue> currentList;
	IN4JSProject activeProject;

	/**
	 *
	 */
	public Diagnostician() {
		project2issues = new LinkedHashMap<>();
	}

	/** Register current project under investigation or {@code null} to deactivate. */
	public void setActiveProject(IN4JSProject project) {
		if (project == null) {
			currentList = null;
			activeProject = null;
			return;
		}
		activeProject = project;
		currentList = project2issues.get(project);
		if (currentList == null) {
			currentList = newArrayList();
			project2issues.put(project, currentList);
		}
	}

	public IN4JSProject getActiveProject() {
		return activeProject;
	}

	public boolean isIssueFree() {
		return project2issues.values().stream()
				.collect(Collectors.summingInt(list -> list.size())) == 0;
	}

	public List<IN4JSProject> projectsWithEntries() {
		return project2issues.keySet().stream().filter(k -> project2issues.get(k).size() > 0)
				.collect(Collectors.toList());
	}

	@Override
	public void accept(String issueCode, String msg) {
		if (currentList == null)
			throw new IllegalStateException("Must call setActiveProject() with non-null value first.");

		Severity severity = lookupSeverity(issueCode);
		if (severity == null)
			return;

		Issue i = new Issue();
		i.severity = severity;
		i.code = issueCode;
		i.message = msg;

		currentList.add(i);
	}

	static Severity lookupSeverity(String issueCode) {
		org.eclipse.xtext.diagnostics.Severity x = ExporterIssueCodes.getDefaultSeverity(issueCode);
		switch (x) {
		case ERROR:
			return Severity.error;
		case WARNING:
			return Severity.warning;
		case INFO:
			return Severity.info;
		default:
			return null;
		}
	}

	/** Represents a message */
	public static class Issue {

		public Severity severity;
		public String code;
		public String message;

		@Override
		public String toString() {
			return severity.toString() + ": " + message;
		}
	}

	/** simple levels fo severity */
	public enum Severity {
		info, warning, error
	}

	/**
	 * @return multiline error string.
	 */
	public String asErrorText() {
		StringBuffer sb = new StringBuffer();

		projectsWithEntries().stream().forEachOrdered(p -> {
			sb.append(p.getProjectId()).append("\n\t");
			sb.append(Joiner.on("\n\t").join(project2issues.get(p)));
			sb.append("\n");
		});

		return sb.toString();
	}
}
