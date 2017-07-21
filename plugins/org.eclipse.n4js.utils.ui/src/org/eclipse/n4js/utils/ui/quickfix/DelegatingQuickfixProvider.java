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
package org.eclipse.n4js.utils.ui.quickfix;

import static com.google.common.base.Suppliers.memoize;
import static com.google.common.collect.FluentIterable.from;
import static org.eclipse.core.runtime.Platform.getExtensionRegistry;
import static org.eclipse.n4js.utils.ui.quickfix.QuickfixProviderSupplier.CLAZZ_PROPERTY_NAME;
import static org.eclipse.n4js.utils.ui.quickfix.QuickfixProviderSupplier.EXTENSION_POINT_ID;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolution;
import org.eclipse.xtext.validation.Issue;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Iterables;

/**
 * Quickfix provider implementation that delegates into any registered {@code quickfixProviderSupplier} extension point
 * contributions if cannot find proper resolution in the current implementation.
 *
 * This is a kind of workaround solution to provide a proper quick fix resolution between Xtext languages.
 *
 *
 * @see QuickfixProviderSupplier
 */
public class DelegatingQuickfixProvider extends DefaultQuickfixProvider {

	private static final Logger LOGGER = Logger.getLogger(DelegatingQuickfixProvider.class);

	private static final Supplier<Iterable<DefaultQuickfixProvider>> DELEGATES_SUPPLIER = memoize(
			() -> collectRegisteredProviders().build());

	private static Builder<DefaultQuickfixProvider> collectRegisteredProviders() {
		final Builder<DefaultQuickfixProvider> builder = ImmutableList.<DefaultQuickfixProvider> builder();
		if (Platform.isRunning()) {
			final IConfigurationElement[] elements = getQuickfixSupplierElements();
			for (final IConfigurationElement element : elements) {
				try {
					final Object extension = element.createExecutableExtension(CLAZZ_PROPERTY_NAME);
					if (extension instanceof QuickfixProviderSupplier) {
						builder.add(((QuickfixProviderSupplier) extension).get());
					}
				} catch (final CoreException e) {
					LOGGER.error("Error while instantiating quickfix provider supplier instance.", e);
				}
			}
		}
		return builder;
	}

	private static IConfigurationElement[] getQuickfixSupplierElements() {
		return getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID);
	}

	@Override
	public boolean hasResolutionFor(final String issueCode) {
		if (super.hasResolutionFor(issueCode)) {
			return true;
		}
		return Iterables.any(getDelegates(), p -> p.hasResolutionFor(issueCode));
	}

	@Override
	public List<IssueResolution> getResolutions(final Issue issue) {
		final List<IssueResolution> resolutions = super.getResolutions(issue);
		resolutions.addAll(from(getDelegates()).transformAndConcat(p -> p.getResolutions(issue)).toList());
		return resolutions;
	}

	private Iterable<DefaultQuickfixProvider> getDelegates() {
		return DELEGATES_SUPPLIER.get();
	}

}
