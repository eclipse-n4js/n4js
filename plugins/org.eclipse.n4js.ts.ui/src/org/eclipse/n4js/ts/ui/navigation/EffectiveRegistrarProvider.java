/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.ui.navigation;

import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

/**
 * Use a contribued registrar or a default value.
 */
public class EffectiveRegistrarProvider {

	private final ISharedStateContributionRegistry sharedStateContributions;
	private final BuiltInSchemeRegistrar defaultValue;

	/**
	 * Standard constructor.
	 */
	@Inject
	public EffectiveRegistrarProvider(ISharedStateContributionRegistry sharedStateContributions,
			BuiltInSchemeRegistrar defaultValue) {
		this.sharedStateContributions = sharedStateContributions;
		this.defaultValue = defaultValue;
	}

	BuiltInSchemeRegistrar get() {
		ImmutableList<? extends BuiltInSchemeRegistrar> contributions = sharedStateContributions
				.getContributedInstances(BuiltInSchemeRegistrar.class);
		switch (contributions.size()) {
		case 0:
			return defaultValue;
		case 1:
			return contributions.get(0);
		default:
			throw new IllegalStateException(
					"Too many contributions found: " + contributions.size() + " / " + contributions);
		}

	}
}
