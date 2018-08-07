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
package org.eclipse.n4js.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * The {@link MultiCleartriggerCache} is similar to {@link IResourceScopeCache} but provides the means to add more than
 * one clear-trigger. A clear-trigger is an {@link URI} that will cause the cached value to be deleted.
 */
@Singleton
public class MultiCleartriggerCache {
	private final Map<String, Map<URI, Object>> entryCache = new HashMap<>();
	private final Map<String, Multimap<URI, URI>> triggerCache = new HashMap<>();

	/**
	 * Provides usual {@link Provider} and the method {@link #getCleartriggers()}. A clear-trigger is an {@link URI}
	 * that will cause the cached value to be deleted.
	 */
	static public interface ResultAndTriggerProvider<Entry> extends Provider<Entry> {
		/** @return a collection of other triggers */
		public Collection<URI> getCleartriggers();
	}

	/**
	 * Returns the requested value from cache. In case the cache is empty, the given {@link Provider} is called.
	 * <p>
	 * If the given {@link Provider} is of type {@link ResultAndTriggerProvider}, additional clear-triggers are computed
	 * and added.
	 *
	 * @param provider
	 *            to compute the result and to compute other clear-triggers
	 * @param key
	 *            to select a specific kind of values on a given URI
	 * @param reference
	 *            URI for which values are cached
	 * @return cached result of the the given provider.
	 */
	public <Entry> Entry get(Provider<Entry> provider, String key, URI reference) {

		if (!entryCache.containsKey(key)) {
			entryCache.put(key, new HashMap<>());
			if (provider instanceof ResultAndTriggerProvider) {
				triggerCache.put(key, HashMultimap.create());
			}
		}
		Map<URI, Object> entryMap = entryCache.get(key);
		if (!entryMap.containsKey(reference) && provider != null) {
			Entry entry = provider.get();
			entryMap.put(reference, entry);

			if (triggerCache.containsKey(key)) {
				ResultAndTriggerProvider<Entry> ratProvider = (ResultAndTriggerProvider<Entry>) provider;
				Multimap<URI, URI> triggerMap = triggerCache.get(key);
				for (URI trigger : ratProvider.getCleartriggers()) {
					triggerMap.put(trigger, reference);
				}
			}
		}
		@SuppressWarnings("unchecked")
		Entry entry = (Entry) entryMap.get(reference);
		return entry;
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
