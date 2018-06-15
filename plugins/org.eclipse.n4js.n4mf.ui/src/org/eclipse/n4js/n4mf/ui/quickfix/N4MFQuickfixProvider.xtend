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
package org.eclipse.n4js.n4mf.ui.quickfix

import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.ui.quickfix.N4JSQuickfixProvider
import org.eclipse.n4js.utils.ui.quickfix.DelegatingQuickfixProvider
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider
import org.eclipse.xtext.ui.shared.contribution.ISharedStateContributionRegistry

/**
 * Quickfix provider for N4MF language. Due to code visibility issues it delegates to N4JS implementation as well.
 */
class N4MFQuickfixProvider extends DelegatingQuickfixProvider {

	List<N4JSQuickfixProvider> delegates

	@Inject
	new(ISharedStateContributionRegistry registry) {
		this.delegates = #[registry.getSingleContributedInstance(N4JSQuickfixProvider)]
	}

	// Delegates to the N4JS quickfix provider.
	
	override protected Iterable<? extends DefaultQuickfixProvider> getDelegates() {
		return delegates
	}

}
