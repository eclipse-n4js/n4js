/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * Global scope for an N4JS workspace as seen by a given context project.
 * <p>
 * Does *not* include built-in types (they are handled by a {@link BuiltInTypeScope} that usually appears as parent
 * scope of this scope).
 */
public class N4JSGlobalScope extends AbstractScope {

	private final N4JSProjectConfigSnapshot contextProject;
	private final ChunkedResourceDescriptions chunkedResourceDescriptions;
	private final EClass type;
	private final Predicate<IEObjectDescription> filter;

	/** Creates a new {@link N4JSGlobalScope}. */
	public N4JSGlobalScope(IScope parent,
			N4JSProjectConfigSnapshot contextProject,
			ChunkedResourceDescriptions chunkedResourceDescriptions,
			EClass type,
			Predicate<IEObjectDescription> filter) {
		super(parent, false);
		this.contextProject = contextProject;
		this.chunkedResourceDescriptions = chunkedResourceDescriptions;
		this.type = type;
		this.filter = filter;
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
		ImmutableSet<String> deps = contextProject.getDependencies();
		List<Iterable<IEObjectDescription>> iterables = new ArrayList<>(deps.size() + 1);
		for (String currProjectId : Iterables.concat(Collections.singleton(contextProject.getName()), deps)) {
			ResourceDescriptionsData currProjectData = chunkedResourceDescriptions.getContainer(currProjectId);
			if (currProjectData != null) {
				iterables.add(currProjectData.getExportedObjects(type, name, false));
			}
		}
		Iterable<IEObjectDescription> result = Iterables.concat(iterables);
		if (filter != null) {
			result = Iterables.filter(result, filter);
		}
		return result;
	}

	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {
		ImmutableSet<String> deps = contextProject.getDependencies();
		List<Iterable<IEObjectDescription>> iterables = new ArrayList<>(deps.size() + 1);
		for (String currProjectId : Iterables.concat(Collections.singleton(contextProject.getName()), deps)) {
			ResourceDescriptionsData currProjectData = chunkedResourceDescriptions.getContainer(currProjectId);
			if (currProjectData != null) {
				iterables.add(currProjectData.getExportedObjectsByType(type));
			}
		}
		Iterable<IEObjectDescription> result = Iterables.concat(iterables);
		if (filter != null) {
			result = Iterables.filter(result, filter);
		}
		return result;
	}
}
