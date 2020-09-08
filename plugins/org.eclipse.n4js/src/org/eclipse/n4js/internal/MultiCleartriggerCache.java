/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.compare.ApiImplMapping;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Singleton;

/**
 * The {@link MultiCleartriggerCache} is similar to {@link IResourceScopeCache} but provides the means to add more than
 * one clear-trigger. A clear-trigger is an {@link URI} that will cause the cached value to be deleted.
 */
@Singleton
public class MultiCleartriggerCache {
	/** Key caching {@link ProjectDescription}s */
	public static final String CACHE_KEY_PROJECT_DESCRIPTIONS = "PROJECT_DESCRIPTIONS";
	/** Key caching sorted set of dependencies */
	public static final String CACHE_KEY_SORTED_DEPENDENCIES = "SORTED_DEPENDENCIES";
	/** Key caching {@link ApiImplMapping}s */
	public static final String CACHE_KEY_API_IMPL_MAPPING = "API_IMPL_MAPPING";

	/** Unique {@link URI} when there is only one data entry for a given key */
	private static final URI GLOBAL = URI.createURI("n4js://global");

	private final Map<String, Map<URI, Object>> entryCache = new HashMap<>();
	private final Map<String, Multimap<URI, URI>> triggerCache = new HashMap<>();

	/**
	 * Provides usual {@link Supplier} and the method {@link #getCleartriggers()}. A clear-trigger is an {@link URI}
	 * that will cause the cached value to be deleted.
	 */
	static public interface CleartriggerSupplier<Entry> extends Supplier<Entry> {
		/** @return a collection of other triggers */
		default public Collection<URI> getCleartriggers() {
			return Collections.emptyList();
		}

		/** Gets called after {@link Supplier#get()} was called and cached */
		default public void postSupply() {
			// please implement
		}
	}

	/**
	 * Like {@link #get(Supplier, String, URI)}, but can only save one entry for a given key.
	 *
	 * @param supplier
	 *            to compute the result and to compute other clear-triggers
	 * @param key
	 *            to select a specific kind of values on a given URI
	 * @return cached result of the the given provider.
	 */
	public <Entry> Entry get(Supplier<Entry> supplier, String key) {
		return get(supplier, key, GLOBAL);
	}

	/**
	 * Returns the requested value from cache. In case the cache does not contain a value for the given key, the given
	 * {@link Supplier} is called.
	 * <p>
	 * If the given {@link Supplier} is of type {@link CleartriggerSupplier}, additional clear-triggers are computed and
	 * added.
	 *
	 * @param supplier
	 *            to compute the result and to compute other clear-triggers
	 * @param key
	 *            to select a specific kind of values on a given URI
	 * @param reference
	 *            URI for which values are cached
	 * @return cached result of the the given provider.
	 */
	public <Entry> Entry get(Supplier<Entry> supplier, String key, URI reference) {
		if (!entryCache.containsKey(key)) {
			entryCache.put(key, new HashMap<>());
			if (supplier instanceof CleartriggerSupplier) {
				triggerCache.put(key, HashMultimap.create());
			}
		}
		Map<URI, Object> entryMap = entryCache.get(key);
		if (!entryMap.containsKey(reference) && supplier != null) {
			Entry entry = supplier.get();
			entryMap.put(reference, entry);

			if (triggerCache.containsKey(key)) {
				CleartriggerSupplier<Entry> ratProvider = (CleartriggerSupplier<Entry>) supplier;
				Multimap<URI, URI> triggerMap = triggerCache.get(key);
				for (URI trigger : ratProvider.getCleartriggers()) {
					if (!reference.equals(trigger)) {
						triggerMap.put(trigger, reference);
					}
				}

				ratProvider.postSupply();
			}
		}
		@SuppressWarnings("unchecked")
		Entry entry = (Entry) entryMap.get(reference);
		return entry;
	}

	/** Removes all cached values */
	public void clear() {
		entryCache.clear();
		triggerCache.clear();
	}

	/** Removes all cached values for a given key */
	public void clear(String key) {
		Map<URI, Object> map = entryCache.get(key);
		Multimap<URI, URI> triggerMap = triggerCache.get(key);
		if (map != null) {
			map.clear();
		}
		if (triggerMap != null) {
			triggerMap.clear();
		}
	}

	/** Same as {@link #clear(String, URI)}, but values for all existing keys are cleared. */
	public void clear(URI trigger) {
		Set<String> allKeys = new HashSet<>(entryCache.keySet());
		for (String key : allKeys) {
			clear(key, trigger);
		}
	}

	/** Removes the cached value for a given key and reference which can also be a trigger */
	public void clear(String key, URI trigger) {
		Map<URI, Object> map = entryCache.get(key);
		if (map != null) {
			map.remove(trigger);

			Multimap<URI, URI> triggerMap = triggerCache.get(key);
			if (triggerMap != null) {
				for (URI otherTrigger : triggerMap.get(trigger)) {
					map.remove(otherTrigger);
				}
			}
		}
	}

}
