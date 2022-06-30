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
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.ChunkedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractScope;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Global scope for an N4JS workspace as seen by a given context project.
 * <p>
 * Does *not* include built-in types (they are handled by a {@link BuiltInTypeScope} that usually appears as parent
 * scope of this scope).
 */
public class N4JSGlobalScope extends AbstractScope {

	private final N4JSProjectConfigSnapshot contextProject;
	private final IResourceDescriptions resourceDescriptions;
	private final EClass type;
	private final Predicate<IEObjectDescription> filter;

	/** Derived value (from {@link #contextProject} and {@link #resourceDescriptions}). */
	private final ImmutableList<ISelectable> selectables;

	/** Creates a new {@link N4JSGlobalScope}. */
	public N4JSGlobalScope(IScope parent,
			N4JSProjectConfigSnapshot contextProject,
			IResourceDescriptions resourceDescriptions,
			EClass type,
			Predicate<IEObjectDescription> filter) {
		super(parent, false);
		this.contextProject = contextProject;
		this.resourceDescriptions = resourceDescriptions;
		this.type = type;
		this.filter = filter;

		this.selectables = computeSelectables();
	}

	/** Derives the value of {@link #selectables} from {@link #contextProject} and {@link #resourceDescriptions}. */
	protected ImmutableList<ISelectable> computeSelectables() {
		if (contextProject != null && resourceDescriptions instanceof ChunkedResourceDescriptions) {
			// this will be the case in production
			Set<String> deps = contextProject.getDependencies();
			ImmutableList.Builder<ISelectable> selectablesBuilder = ImmutableList.builderWithExpectedSize(
					1 + deps.size());
			for (String currProjectId : Iterables.concat(Collections.singleton(contextProject.getName()), deps)) {
				ResourceDescriptionsData currProjectData = ((ChunkedResourceDescriptions) resourceDescriptions)
						.getContainer(currProjectId);
				if (currProjectData != null) {
					selectablesBuilder.add(currProjectData);
				}
			}
			return selectablesBuilder.build();
		} else {
			// happens in low-level unit tests
			return ImmutableList.of(resourceDescriptions);
		}
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
		List<Iterable<IEObjectDescription>> iterables = new ArrayList<>();
		for (ISelectable currProjectData : selectables) {
			iterables.add(currProjectData.getExportedObjects(type, name, false));
		}
		Iterable<IEObjectDescription> result = Iterables.concat(iterables);
		if (filter != null) {
			result = Iterables.filter(result, filter);
		}
		return result;
	}

	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {
		List<Iterable<IEObjectDescription>> iterables = new ArrayList<>();
		for (ISelectable currProjectData : selectables) {
			iterables.add(currProjectData.getExportedObjectsByType(type));
		}
		Iterable<IEObjectDescription> result = Iterables.concat(iterables);
		if (filter != null) {
			result = Iterables.filter(result, filter);
		}
		return result;
	}
}
