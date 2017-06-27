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
package org.eclipse.n4js.resource;

import java.util.Map;
import java.util.Set;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.collect.Sets;

/**
 * Customized {@link ResourceDescriptionsData} implementation that keeps the order of indexed elements (
 * {@link IEObjectDescription}) if multiple instances can be associated with the same {@link QualifiedName}.
 */
public class OrderedResourceDescriptionsData extends ResourceDescriptionsData {

	/**
	 * Creates a new {@link IResourceDescriptions} implementation that keeps the order of the indexed elements (
	 * {@link IEObjectDescription}) if more the one elements belong to the same {@link QualifiedName qualified name}.
	 *
	 * @param descriptions
	 *            the initial subset of {@link IResourceDescription resource description} instances.
	 */
	public OrderedResourceDescriptionsData(final Iterable<IResourceDescription> descriptions) {
		super(descriptions);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerDescription(final IResourceDescription description,
			final Map<QualifiedName, Object> target) {

		for (final IEObjectDescription object : description.getExportedObjects()) {
			final QualifiedName lowerCase = object.getName().toLowerCase();
			final Object existing = target.put(lowerCase, description);
			if (existing != null && existing != description) {
				Set<IResourceDescription> set = null;
				if (existing instanceof IResourceDescription) {
					// The linked hash set is the difference comparing to the super class.
					set = Sets.newLinkedHashSetWithExpectedSize(2);
					set.add((IResourceDescription) existing);
				} else {
					set = (Set<IResourceDescription>) existing;
				}
				set.add(description);
				target.put(lowerCase, set);
			}
		}
	}

}
