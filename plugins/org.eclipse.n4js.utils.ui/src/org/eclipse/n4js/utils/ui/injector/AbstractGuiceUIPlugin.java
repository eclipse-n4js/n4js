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
package org.eclipse.n4js.utils.ui.injector;

import static java.util.Collections.emptyList;

import java.util.Collections;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Abstract UI plug-in implementation that is capable of providing injectors.
 */
public abstract class AbstractGuiceUIPlugin extends AbstractUIPlugin {

	/**
	 * The shared activator instance.
	 */
	protected static AbstractGuiceUIPlugin INSTANCE;

	private final LoadingCache<String, Injector> cache = CacheBuilder.newBuilder()
			.build(new CacheLoader<String, Injector>() {

				@Override
				public Injector load(final String id) throws Exception {
					final Injector parentInjector = getParentInjector(id);
					return parentInjector.createChildInjector(getModules());
				}
			});

	@Override
	public final void start(final BundleContext context) throws Exception {
		beforeStart();
		doStart(context);
		INSTANCE = this;
		afterStart();
	}

	@Override
	public final void stop(final BundleContext context) throws Exception {
		beforeStop();
		INSTANCE = null;
		doStop(context);
		afterStop();
	}

	/**
	 * Returns with the cached injector for the given unique injector identifier. If the injector is not yet available,
	 * then this method will create and cache a new injector instance and also associates it with the unique ID
	 * argument. Clients may expand the visibility of this method by overriding it.
	 *
	 * @return the unique ID of the injector.
	 */
	protected Injector getInjector(final String id) {
		return cache.getUnchecked(id);
	}

	/**
	 * Returns with the parent injector that will be used to inherit all the bindings for the injector provided by this
	 * instance.
	 *
	 * @param id
	 *            the unique identifier of the injector.
	 *
	 * @return the parent injector to inherit all the bindings and shared singletons.
	 */
	protected abstract Injector getParentInjector(final String id);

	/**
	 * Returns with an iterable of modules that will be used to declare any additional bindings for the injector
	 * provided by this instance. Returns with an {@link Collections#emptyList() empty list} by default, hence does not
	 * contribute/declare any additional bindings for the provided injector.
	 *
	 * @return an iterable of modules that will be used to declare additional bindings.
	 */
	protected Iterable<Module> getModules() {
		return emptyList();
	}

	/**
	 * Called before the bundle is being started. Default implementation does nothing.
	 */
	protected void beforeStart() {
		// default
	}

	/**
	 * Performs the actual bundle start by invoking the {@link AbstractUIPlugin#start(BundleContext)} on the super
	 * class.
	 *
	 * @param context
	 *            the bundle context.
	 * @throws Exception
	 *             if this plug-in did not start up properly.
	 */
	protected void doStart(final BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * Called after the bundle has been started. Does nothing by default.
	 */
	protected void afterStart() {
		// default
	}

	/**
	 * Called before the bundle shutdown. Does nothing by default, clients may customize this logic.
	 */
	protected void beforeStop() {
		// default
	}

	/**
	 * Performs the actual bundle shutdown by invoking the {@link AbstractUIPlugin#stop(BundleContext)} on the super
	 * class.
	 *
	 * @param context
	 *            the bundle context.
	 * @throws Exception
	 *             if this method fails to shutdown this plug-in.
	 */
	protected void doStop(final BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Called after the bundle has been stopped. Does nothing by default.
	 */
	protected void afterStop() {
		// default
	}

}
