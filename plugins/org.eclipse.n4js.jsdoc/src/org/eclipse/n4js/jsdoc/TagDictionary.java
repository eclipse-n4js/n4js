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
package org.eclipse.n4js.jsdoc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Container for instances of {@link ITagDefinition} subtypes. It implements Hash map that contains given tag titles as
 * keys and {@link ITagDefinition} instances as values.
 */
public class TagDictionary<T extends ITagDefinition> {

	private final static TagDictionary<?> EMPTY_DICT = new TagDictionary<>() {
		@Override
		void addTagDefinition(ITagDefinition tagDefinition) {
			throw new IllegalStateException();
		}
	};

	Map<String, ITagDefinition> tagDefinitions = new HashMap<>();

	/**
	 * Create instance initiated with given {@link Collection} of {@link ITagDefinition} implementations.
	 */
	public TagDictionary(Collection<T> tags) {
		for (T tag : tags) {
			addTagDefinition(tag);
		}
	}

	/**
	 * Returns all tag definitions of the dictionary. Basically used for content assist.
	 */
	public Iterable<ITagDefinition> getTagDefinitions() {
		return tagDefinitions.values();
	}

	/**
	 * Create instance initiated with given {@link Collection} of {@link ITagDefinition} implementations.
	 */
	public TagDictionary(@SuppressWarnings("unchecked") T... tags) {
		for (T tag : tags) {
			addTagDefinition(tag);
		}
	}

	/**
	 * Default (empty) constructor.
	 */
	public TagDictionary() {
		// nop
	}

	ITagDefinition getDefinition(String title) {
		return tagDefinitions.get(title);
	}

	void addTagDefinition(ITagDefinition tagDefinition) {
		for (String title : tagDefinition.getAllTitles()) {
			tagDefinitions.put(title, tagDefinition);
		}
	}

	/**
	 * Returns casted empty dictionary.
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ITagDefinition> TagDictionary<T> emptyDict() {
		return (TagDictionary<T>) EMPTY_DICT;
	}

}
