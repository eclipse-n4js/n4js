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
package org.eclipse.n4js.utils.injector;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Suppliers.memoize;
import static org.eclipse.n4js.utils.injector.InjectorSupplier.EXTENSION_POINT_ID;
import static java.util.Collections.emptyMap;
import static org.eclipse.core.runtime.Platform.getExtensionRegistry;
import static org.eclipse.core.runtime.Platform.isRunning;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Singleton broker for providing {@link Injector injector} instances. The injectors will be provided via
 * {@link InjectorSupplier injector suppliers} registered as extension points to the {@link Platform platform}.
 *
 * <p>
 * This class requires a running {@link Platform platform}. This class can be run without OSGi.
 */
@Singleton
public class InjectorBroker {

	private static final Logger LOGGER = Logger.getLogger(InjectorBroker.class);
	private static final String CLASS_ATTRIBUTE = "class";

	private static final Supplier<Map<String, Injector>> CACHE_SUPPLIER = memoize(() -> {

		if (!isRunning()) {
			return emptyMap();
		}
		final Builder<String, Injector> builder = ImmutableMap.builder();
		final IConfigurationElement[] elements = getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID);
		for (final IConfigurationElement element : elements) {
			try {
				final InjectorSupplier supplier = (InjectorSupplier) element.createExecutableExtension(CLASS_ATTRIBUTE);
				builder.put(supplier.getInjectorId(), supplier.get());
			} catch (final CoreException e) {
				LOGGER.error("Error while trying to instantiate injector supplier.", e);
			}
		}

		return builder.build();
	});

	/**
	 * Returns with the registered injector instance with the given unique injector ID argument. This method throws and
	 * exception if the injector ID argument is {@code null}. Also this method returns with {@code null} if no injectors
	 * are registered to the platform with the given injector identifier argument.
	 *
	 * @param injectorId
	 *            the unique ID of the injector to be provided.
	 * @return the provided injector. Or {@code null} if the injector is not found.
	 */
	public Injector getInjector(final String injectorId) {
		return CACHE_SUPPLIER.get().get(checkNotNull(injectorId, "injectorId"));
	}

}
