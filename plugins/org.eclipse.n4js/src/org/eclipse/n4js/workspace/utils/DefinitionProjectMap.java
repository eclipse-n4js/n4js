/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.workspace.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;

/**
 * A mutable, bidirectional map between definition projects and their {@link ProjectDescription#getDefinesPackage()
 * defined project}.
 * <p>
 * We cannot use
 * <ol>
 * <li>a {@link BiMap} because the workspace might contain several definition projects for the same defined project, and
 * this corner case has to be handled gracefully.
 * <li>a {@link Multimap} because we need the inverse view (i.e. quickly getting from defined project to its definition
 * project).
 * </ol>
 */
public class DefinitionProjectMap {

	private final Map<String, String> definition2DefinedProject = new HashMap<>();
	private final SetMultimap<String, String> defined2DefinitionProjects = HashMultimap.create();

	/** Creates a new, empty {@link DefinitionProjectMap}. */
	public DefinitionProjectMap() {
	}

	/** Creates a new {@link DefinitionProjectMap} for the projects defined by the given project descriptions. */
	public DefinitionProjectMap(Iterable<? extends ProjectDescription> projectDescriptions) {
		for (ProjectDescription pd : projectDescriptions) {
			addProject(pd);
		}
	}

	/**
	 * Tells whether the project with the given name is a definition project known to this registry.
	 */
	public boolean isDefinitionProject(String projectName) {
		return definition2DefinedProject.containsKey(projectName);
	}

	/**
	 * Returns the name of the project defined by the definition project denoted by the given name or <code>null</code>
	 * if the project with the given name is not a definition project, does not specify a defined project in its
	 * <code>package.json</code> file, or no project exists with the given name.
	 */
	public String getDefinedProject(String nameOfDefinitionProject) {
		return definition2DefinedProject.get(nameOfDefinitionProject);
	}

	/**
	 * Returns the name of the definition project that defines the project with the given name or <code>null</code> if
	 * no such definition project exists.
	 * <p>
	 * If several such definition projects exist, this method will choose one of them in a way that is consistently
	 * reproducible.
	 */
	public String getDefinitionProject(String nameOfDefinedProject) {
		String result = null;
		for (String p : getDefinitionProjects(nameOfDefinedProject)) {
			if (result == null || result.compareTo(p) > 0) {
				result = p;
			}
		}
		return result;
	}

	/**
	 * Returns the names of all definition projects that define the project with the given name.
	 */
	public Set<String> getDefinitionProjects(String nameOfDefinedProject) {
		return defined2DefinitionProjects.get(nameOfDefinedProject);
	}

	/** Clear all entries. */
	public void clear() {
		definition2DefinedProject.clear();
		defined2DefinitionProjects.clear();
	}

	/** Add a project. */
	public void addProject(ProjectDescription pd) {
		String name = pd.getQualifiedName();
		ProjectType type = pd.getType();
		if (name == null || type == null) {
			return; // ignore invalid project descriptions
		}
		String definedProjectName = pd.getDefinesPackage() != null
				? new N4JSProjectName(pd.getDefinesPackage())
				: null;
		addProject(name, type, definedProjectName);
	}

	/** Add a project. */
	public void addProject(String name, ProjectType projectType, String definedProjectOrNull) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(projectType);
		if (projectType == ProjectType.DEFINITION) {
			String oldDefinedProjectName = definition2DefinedProject.put(name, definedProjectOrNull);
			if (oldDefinedProjectName != null) {
				defined2DefinitionProjects.remove(oldDefinedProjectName, name);
			}
			if (definedProjectOrNull != null) {
				defined2DefinitionProjects.put(definedProjectOrNull, name);
			}
		}
	}

	/** Remove a project. */
	public void removeProject(String name) {
		Objects.requireNonNull(name);
		String oldDefinedProjectName = definition2DefinedProject.remove(name);
		if (oldDefinedProjectName != null) {
			defined2DefinitionProjects.remove(oldDefinedProjectName, name);
		}
	}

	/** Notify this registry that the properties of a project have changed. */
	public void changeProject(ProjectDescription pdOld, ProjectDescription pdNew) {
		String oldName = pdOld.getQualifiedName();
		if (oldName != null && !oldName.equals(pdNew.getQualifiedName())) {
			removeProject(oldName);
		}
		addProject(pdNew);
	}
}
