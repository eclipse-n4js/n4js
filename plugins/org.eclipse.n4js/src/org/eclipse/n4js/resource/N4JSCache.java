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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.n4js.validation.helper.IssuesProvider;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.OnChangeEvictingCache;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 */
public class N4JSCache extends OnChangeEvictingCache {

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

	private Collection<EObject> findEObjects(Object element) {
		Set<EObject> result = Sets.newHashSet();
		findEObjects(element, result, Sets.newSetFromMap(new IdentityHashMap<>()));
		return result;
	}

	private void findEObjects(Object element, Set<EObject> result, Set<Object> visited) {
		if (element == null || !visited.add(element)) {
			return;
		}
		if (element instanceof Class || element instanceof ClassLoader || element instanceof Logger) {
			return;
		}
		if (element instanceof Injector) {
			return;
		}
		if (element instanceof EObject) {
			result.add((EObject) element);
		} else if (element instanceof Resource) {
			result.add(((Resource) element).getContents().get(0));
		} else if (element instanceof Object[]) {
			Object[] casted = (Object[]) element;
			for (Object nested : casted) {
				findEObjects(nested, result, visited);
			}
		} else {
			Class<? extends Object> type = element.getClass();
			if (!type.isArray()) {
				findEObjects(type, element, result, visited);
			}
		}
	}

	private void findEObjects(Class<?> type, Object element, Set<EObject> result, Set<Object> visited) {
		while (type != null) {
			Field[] fields = type.getDeclaredFields();
			for (Field field : fields) {
				if (field.getAnnotation(Inject.class) != null) {
					if (!field.getType().isPrimitive() && !Modifier.isStatic(field.getModifiers())) {
						field.setAccessible(true);
						try {
							findEObjects(field.get(element), result, visited);
						} catch (Exception e) {
							Exceptions.throwUncheckedException(e);
						}
					}
				}
			}
			type = type.getSuperclass();
		}
	}

	@Override
	public <T> T get(Object key, Resource resource, Provider<T> provider) {
		assertObjectsStemFrom("key", key, resource);
		assertObjectsStemFrom("provider", provider, resource);
		T result = super.get(key, resource, provider);
		assertObjectsStemFrom("result", result, resource);
		return result;
	}

	private void assertObjectsStemFrom(String description, Object element, Resource resource) {
		Collection<EObject> referencedEObjects = findEObjects(element);
		for (EObject referencedEObject : referencedEObjects) {
			Resource res = referencedEObject.eResource();
			if (res != null && res != resource) {
				RuntimeException e = new RuntimeException(
						String.format("Illegal cache attempt: %s EObjects from %s on %s: %s",
								description, res.getURI(), resource.getURI(), referencedEObject));
				e.printStackTrace();
				throw e;
			}
		}
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
	public synchronized List<Issue> getOrElseUpdateIssues(IResourceValidator resourceValidator, Resource res,
			CancelIndicator monitor) {
		try {
			List<Issue> issues = get("N4JS-IDE-AllIssues", res, new IssuesProvider(resourceValidator, res, monitor));
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
	 * Only purpose of sub-classing CacheAdpater here is to avoid resolving objects and thus loading the resource when
	 * this cache is added to its adapter list. Loading the resource is not desirable if the resource has been created
	 * from the type information in the xtext index.
	 */
	private final class N4JSCacheAdapter extends CacheAdapter {
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
			if (target instanceof N4JSResource) {
				// copied from super method:
				basicSetTarget(target);
				InternalEList<EObject> contents = (InternalEList<EObject>) target.getContents();
				for (int i = 0, size = contents.size(); i < size; ++i) {
					Notifier notifier = contents.basicGet(i); // changed from contents.get(i) to contents.basicGet(i)
					// to avoid resolving the object (i.e. avoid call to
					// ModuleAwareContentsList#resolve() in N4JSResource)
					addAdapter(notifier);
				}
			} else
				super.setTarget(target);
		}
	}
}
