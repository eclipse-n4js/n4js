/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.util.OnChangeEvictingCache
 *	in bundle org.eclipse.xtext.util
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.validation.helper.IssuesProvider;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.OnChangeEvictingCache;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 */
@Singleton
public class N4JSCache extends OnChangeEvictingCache {

	static final String N4JS_AllIssues = "N4JS-AllIssues";

	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * @deprecated Use {@link #get(Resource, Provider, Object, Object...)} instead.
	 */
	@Deprecated
	@Override
	public <T> T get(Object key, Resource resource, Provider<T> provider) {
		return super.get(key, resource, provider);
	}

	/** Use this get method as a general convention. */
	public <T> T get(Resource resource, Provider<T> provider, Object firstKey, Object... moreKeys) {
		return super.get(makeKey(firstKey, moreKeys), resource, provider);
	}

	/** Create keys for the cache here. */
	static public Object makeKey(Object firstKey, Object... moreKeys) {
		Preconditions.checkNotNull(firstKey);

		try (Measurement M = N4JSDataCollectors.dcCacheMakeKeys.getMeasurementIfInactive()) {
			if (moreKeys == null || moreKeys.length == 0) {
				// must directly return first element because this could be already a key
				return firstKey;
			}
			int hashCode = 31 * firstKey.hashCode() + Objects.hash(moreKeys);
			return Arrays.asList(hashCode, firstKey, Arrays.asList(moreKeys));
		}
	}

	@Override
	public CacheAdapter getOrCreate(Resource resource) {
		// copied from super method, but we expect N4JSCacheAdapter here
		// note: if you got a cast exception here (because the adapter is not an N4JSCacheAdapter but a simple one),
		// you probably have loaded a resource via the wrong scope provider. That is, you probably tried to
		// load a non-N4JS resource with an n4js provider (maybe vice versa...).
		// In order to solve that problem, use the org.eclipse.xtext.resource.IResourceServiceProvider.Registry
		// to get the correct providers for the N4JS language.
		// a nice example is found in:
		// org.eclipse.n4js.validation.validators.N4JSProjectSetupValidator.getAllPolyfills(Resource)
		N4JSCacheAdapter adapter = (N4JSCacheAdapter) EcoreUtil.getAdapter(resource.eAdapters(), CacheAdapter.class);
		if (adapter == null) {
			adapter = new N4JSCacheAdapter(); // changed from CacheAdapter to N4JSCacheAdapter
			resource.eAdapters().add(adapter);
			adapter.setResource(resource);
		}
		return adapter;
	}

	/**
	 * Look up all issues for the given resource (as given by object URI and severity). On cache miss, compute all
	 * issues for the resource and cache them.
	 * <p>
	 * Upon cancellation, null is returned as sentinel. No entry is cached in that case. The next invocation of this
	 * method for that resource will trigger validation anew.
	 * <p>
	 * The given {@link CancelIndicator} contributes to responsiveness, but only on cache miss. In detail:
	 * <ul>
	 * <li>a caller usually performs several calls targeting this method</li>
	 * <li>of all those calls, only the first one (for a given resource) causes validations to run</li>
	 * <li>all subsequent calls (for that resource) result in cache-hit</li>
	 * </ul>
	 * <p>
	 * Concurrency usage note: the same instance of this class is intentionally reused from the Outline view (via
	 * {@code ImageDescriptionHelper}) and from the transpiler (via {@code AbstractSubGenerator}). That instance has
	 * additional clients but the focus are invokers of this method. To start, the class is not thread-safe. However for
	 * the sharing just described (between Outline and transpiler) making this method synchronized is enough.
	 * <p>
	 * Other threads besides those for outline view and transpiler could in principle access the cache. However that's a
	 * situation that's pre-existent to the just described sharing between outline and transpiler.
	 */
	public List<Issue> getOrUpdateIssues(
			IResourceValidator resourceValidator,
			Resource res,
			CheckMode checkMode,
			CancelIndicator monitor) {
		try {
			List<Issue> issues = get(res,
					new IssuesProvider(resourceValidator, res, checkMode, operationCanceledManager, monitor),
					N4JS_AllIssues + checkMode);
			return issues;
		} catch (OperationCanceledError oce) {
			// observation: the cache remains unchanged, to avoid cache corruption.
			return null;
		} catch (OperationCanceledException oce) {
			// observation: the cache remains unchanged, to avoid cache corruption.
			return null;
		}
	}

	/**
	 * Triggers validation for the given resource even if validation results are cached already. The new result will
	 * update the cache.
	 *
	 * @see #getOrUpdateIssues(IResourceValidator, Resource, CheckMode, CancelIndicator)
	 */
	public List<Issue> recreateIssues(
			IResourceValidator resourceValidator,
			Resource res,
			CheckMode checkMode,
			CancelIndicator monitor) {
		try {
			CacheAdapter adapter = getOrCreate(res);
			List<Issue> issues = new IssuesProvider(resourceValidator, res, checkMode, operationCanceledManager,
					monitor).get();
			adapter.set(N4JS_AllIssues + checkMode, issues);
			return issues;
		} catch (OperationCanceledError oce) {
			// observation: the cache remains unchanged, to avoid cache corruption.
			return null;
		} catch (OperationCanceledException oce) {
			// observation: the cache remains unchanged, to avoid cache corruption.
			return null;
		}
	}

	/** @return true iff the cache for the given resource does not contain the given key */
	public boolean contains(Resource resource, Object firstKey, Object... moreKeys) {
		CacheAdapter adapter = getOrCreate(resource);
		return adapter.get(makeKey(firstKey, moreKeys)) != null;
	}

	/** @return a cached value for the given pair of resource and key or throws an {@link IllegalStateException}. */
	@SuppressWarnings("unchecked")
	public <T> T mustGet(Resource resource, Object firstKey, Object... moreKeys) {
		CacheAdapter adapter = getOrCreate(resource);
		Object key = makeKey(firstKey, moreKeys);
		Object value = adapter.get(key);
		if (value == null) {
			throw new IllegalStateException(
					"Cache was expected to contain a value for " + key + " of resource " + resource.getURI());
		} else {
			return (T) value;
		}
	}

	/**
	 * Only purpose of sub-classing CacheAdpater here is to avoid resolving objects and thus loading the resource when
	 * this cache is added to its adapter list. Loading the resource is not desirable if the resource has been created
	 * from the type information in the xtext index.
	 */
	private final class N4JSCacheAdapter extends CacheAdapter {
		@Override
		public <T> T get(Object name) {
			try (Measurement M = N4JSDataCollectors.dcCacheGet.getMeasurementIfInactive()) {
				return super.get(name);
			}
		}

		@Override
		public void set(Object name, Object value) {
			try (Measurement M = N4JSDataCollectors.dcCachePut.getMeasurementIfInactive()) {
				super.set(name, value);
			}
		}

		@Override
		protected boolean resolve() {
			return false; // changed to false!
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return CacheAdapter.class.equals(type);
		}

		@Override
		protected void setResource(Resource resource) {
			super.setResource(resource);
		}

		@Override
		protected void setTarget(Resource target) {
			try (Measurement M = N4JSDataCollectors.dcCacheSetTarget.getMeasurementIfInactive()) {

				if (target instanceof N4JSResource) {
					// copied from super method:
					basicSetTarget(target);
					InternalEList<EObject> contents = (InternalEList<EObject>) target.getContents();
					for (int i = 0, size = contents.size(); i < size; ++i) {
						Notifier notifier = contents.basicGet(i); // changed from contents.get(i) to
																	// contents.basicGet(i)
						// to avoid resolving the object (i.e. avoid call to
						// ModuleAwareContentsList#resolve() in N4JSResource)
						addAdapter(notifier);
					}
				} else
					super.setTarget(target);
			}
		}
	}
}
