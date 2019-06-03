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
package org.eclipse.n4js.ts.types.internal;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.ts.types.NameAndAccess;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.TransformedIterator2;

import com.google.common.collect.Iterators;

/**
 * Rather than copying everything into a "big" hash map, a list traversal is faster for small number of entries.
 *
 * Performance tests indicated that a linear lookup has to be done quite often before the cost of instantiating a cache
 * is broke even.
 */
/* TODO it may be worthwhile to explore a local cache for successfully retrieved values. */
public class MemberByNameAndAccessMap extends AbstractMap<NameAndAccess, TMember> {

	private final List<? extends TMember> members;

	/**
	 * Create new map for the list of members.
	 */
	public MemberByNameAndAccessMap(List<? extends TMember> members) {
		this.members = members;
	}

	@Override
	public Set<Map.Entry<NameAndAccess, TMember>> entrySet() {
		return new AbstractSet<>() {
			@Override
			public Iterator<Map.Entry<NameAndAccess, TMember>> iterator() {
				return Iterators.unmodifiableIterator(
						new TransformedIterator2<TMember, Map.Entry<NameAndAccess, TMember>>(members.iterator()) {
							@Override
							protected Map.Entry<NameAndAccess, TMember> transform(TMember input) {
								final NameAndAccess[] nameAndAccess = NameAndAccess.of(input);
								if (nameAndAccess.length > 1) {
									setAdditionalElement(
											new AbstractMap.SimpleImmutableEntry<>(nameAndAccess[1], input));
								}
								return new AbstractMap.SimpleImmutableEntry<>(nameAndAccess[0], input);
							}
						});
			}

			@Override
			public int size() {
				return members.size();
			}
		};
	}

	@Override
	public int size() {
		return members.size();
	}

	@Override
	public boolean isEmpty() {
		return members.isEmpty();
	}

	@Override
	public TMember get(Object key) {
		if (key instanceof NameAndAccess) {
			for (int i = 0, size = members.size(); i < size; i++) {
				final TMember result = members.get(i);
				final NameAndAccess nameAndAccess = (NameAndAccess) key;
				if (nameAndAccess.getName().equals(result.getName())) {
					if (nameAndAccess.isStaticAccess() == result.isStatic()) {
						if ((nameAndAccess.isWriteAccess() && result.isWriteable()) ||
								(!nameAndAccess.isWriteAccess() && result.isReadable())) {
							return result;
						}
					}
				}
			}
		}
		return null;
	}
}
