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
package org.eclipse.n4js.utils.di.scopes;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.ScopeAnnotation;

/**
 * Manager for custom scopes used during dependency injection with Google Guice.
 *
 * <h2>Scopes in Guice</h2>
 *
 * In the context of dependency injection, a "scope" is a time interval within the life-time of a system during which
 * some additional injections are available. A class that is intended to be injected can be marked with a "scope
 * annotation", to denote that values of this type can only be injected "within the scope" (i.e. during the
 * corresponding time interval) and an exception will be thrown if a value is injected at other times. Each scope gets
 * its own scope annotation.
 *
 * <h2>Conventions used in this class</h2>
 *
 * We use a single instance of this class per injector managing all scopes we have, instead of a separate instance for
 * each scope. Hence we call this class <code>ScopeManager</code> instead of <code>XYZScope</code>.
 * <p>
 * Each scope is identified by its scope annotation, a Java annotation that is itself annotated with
 * <code>@ScopeAnnotation</code> (see {@link #isScopeAnnotation(Class)}). For example:
 * <!-- @formatter:off --> <pre>
 * import com.google.inject.ScopeAnnotation;
 * &#64;Target({ TYPE, METHOD }) &#64;Retention(RUNTIME) <b>&#64;ScopeAnnotation</b>
 * public &#64;interface TransformationScoped {
 *     // empty
 * }
 * </pre> <!-- @formatter:on -->
 * When creating new scopes / scope annotations, be sure to let the injector know about them by adding them to the
 * module, see {@code org.eclipse.n4js.N4JSRuntimeModule#configure(Binder)}.
 * <p>
 * This implementation tracks all scope-related information on a per-thread basis, so this class can be used across
 * different threads and each thread will have its own scope life cycles and scoped values. <b>However, this was not
 * tested yet!</b>
 *
 * <h2>Usage</h2>
 *
 * To use custom scopes, create a scope annotation (see example above), then inject this class as usual in some
 * controller class and call methods ...
 * <ol>
 * <li>{@link #enter(Class)} to start a scope,
 * <li>{@link #bind(Class, Class, Object)} to temporarily provide additional values only available during the
 * life-time of the scope, and finally
 * <li>{@link #exit(Class)} to end the scope (the temporarily bound value will no
 * longer be available).
 * </ol>
 * The last call to exit() should be done in a finally block, to ensure consistency. For example:
 * <!-- @formatter:off --> <pre>
 * scopeManager.enter(TransformationScoped.class);
 * try {
 *     scopeManager.bind(TransformationScoped.class, TranspilerState.class, state);
 *     // create instances via providers that may inject classes annotated with &#64;TransformationScoped
 * } finally {
 *     scopeManager.exit(TransformationScoped.class);
 * }
 * </pre> <!-- @formatter:on -->
 *
 *
 * @see <a href="https://github.com/google/guice/wiki/CustomScopes">Google Guice documentation on custom scopes</a>
 */
public final class ScopeManager implements Scope {

	private final ThreadLocal<ScopeRegistry> registry = new ThreadLocal<>();

	/** Keeps track of active scopes and, for each scope separately, their values to be injected. */
	private static final class ScopeRegistry {

		public final Map<Class<? extends Annotation>, Map<Key<?>, Object>> valuesPerActiveScope = new HashMap<>();

		public boolean isScopeActive(Class<? extends Annotation> scope) {
			return valuesPerActiveScope.containsKey(scope);
		}

		public void activateScope(Class<? extends Annotation> scope) {
			valuesPerActiveScope.put(scope, new HashMap<>());
		}

		public <T> void bind(Class<? extends Annotation> scope, Key<T> key, T value) {
			valuesPerActiveScope.get(scope).put(key, value);
		}

		public <T> T getBinding(Class<? extends Annotation> scope, Key<T> key) {
			@SuppressWarnings("unchecked")
			final T resultCasted = (T) valuesPerActiveScope.get(scope).get(key);
			return resultCasted;
		}

		public void deactivateScope(Class<? extends Annotation> scope) {
			valuesPerActiveScope.remove(scope);
		}
	}

	/**
	 * Returns the scope registry for the current thread (will be created, if necessary).
	 */
	private ScopeRegistry getRegistry() {
		final ScopeRegistry reg = registry.get();
		if (reg == null) {
			final ScopeRegistry newReg = new ScopeRegistry();
			registry.set(newReg);
			return newReg;
		}
		return reg;
	}

	/**
	 * Enter the scope identified by the given scope annotation. A call to this method should immediately be followed by
	 * one or more calls to {@link #bind(Class, Class, Object)}.
	 * <p>
	 * Before this method is called, injection of classes annotated with the given scope annotation will throw an
	 * out-of-scope exception.
	 */
	public void enter(Class<? extends Annotation> scope) {
		checkScopeAnnotation(scope);
		final ScopeRegistry reg = getRegistry();
		if (reg.isScopeActive(scope))
			throw new IllegalStateException("attempt to enter an already active scope: " + scope.getName());
		reg.activateScope(scope);
	}

	/**
	 * Provide a value for injection of values of type 'key'. Must be called after {@link #enter(Class)} and before the
	 * first injection is being performed.
	 */
	public <T> void bind(Class<? extends Annotation> scope, Class<T> key, T value) {
		bind(scope, Key.get(key), value);
	}

	private <T> void bind(Class<? extends Annotation> scope, Key<T> key, T value) {
		checkScopeAnnotation(scope);
		final ScopeRegistry reg = getRegistry();
		if (!reg.isScopeActive(scope))
			throw new IllegalStateException("attempt to bind a scoped value outside its scope");
		reg.bind(scope, key, value);
	}

	/**
	 * Called by Guice during injecting values of types annotated with a scope annotation to obtain the current value
	 * within the corresponding scope for the given key.
	 */
	@Override
	public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
		final Class<?> clsToBeInjected = key.getTypeLiteral().getRawType();
		final Class<? extends Annotation> scope = getScopeFromScopedClass(clsToBeInjected);
		if (scope == null)
			throw new IllegalArgumentException(
					"not a scoped class (i.e. does not have a scope annotation): " + clsToBeInjected.getName());
		return new Provider<>() {
			@Override
			public T get() {
				final ScopeRegistry reg = getRegistry();
				if (!reg.isScopeActive(scope))
					throw new IllegalStateException("attempt to read a scoped value outside its scope");
				final T value = reg.getBinding(scope, key);
				if (value == null)
					throw new IllegalStateException(
							"attempt to read a scoped value that was not bound within its scope");
				return value;
			}
		};
	}

	/**
	 * Leave the scope identified by the given scope annotation.
	 * <p>
	 * After this method is called, injection of classes annotated with the given scope annotation will throw an
	 * out-of-scope exception.
	 */
	public void exit(Class<? extends Annotation> scope) {
		checkScopeAnnotation(scope);
		final ScopeRegistry reg = getRegistry();
		if (!reg.isScopeActive(scope))
			throw new IllegalStateException("attempt to exit an inactive scope: " + scope.getName());
		reg.deactivateScope(scope);
	}

	private static final void checkScopeAnnotation(Class<? extends Annotation> annotationClass) {
		if (!isScopeAnnotation(annotationClass))
			throw new IllegalArgumentException("given annotation must be a goolge Guice scope annotation");
	}

	/**
	 * For some class to be injected, return its scope (identified by a scope annotation) or <code>null</code> if the
	 * type is not scoped.
	 */
	public static final Class<? extends Annotation> getScopeFromScopedClass(Class<?> clsToBeInjected) {
		Annotation match = null;
		for (Annotation ann : clsToBeInjected.getAnnotations()) {
			if (isScopeAnnotation(ann.annotationType())) {
				if (match != null) {
					// already have a match -> duplicates are not allowed
					throw new IllegalStateException(
							"class has several scope annotations: " + clsToBeInjected.getName());
				}
				match = ann;
			}
		}
		return match != null ? match.annotationType() : null;
	}

	/**
	 * Tells if the given annotation is a Google Guice "scope annotation", i.e. if the annotation class is itself
	 * annotated with <code>@ScopeAnnotation</code>.
	 */
	public static final boolean isScopeAnnotation(Class<? extends Annotation> annotationClass) {
		return annotationClass.isAnnotationPresent(ScopeAnnotation.class);
	}
}
