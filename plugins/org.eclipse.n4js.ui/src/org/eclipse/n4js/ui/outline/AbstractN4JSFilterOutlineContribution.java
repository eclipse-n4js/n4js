/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.outline;

import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineFilterAndSorter.IFilter;

import com.google.inject.Inject;

/**
 *
 */
public abstract class AbstractN4JSFilterOutlineContribution extends AbstractFilterOutlineContribution {
	/**
	 * The filter returned in getFilter, delegates to contribution's apply method.
	 */
	protected IFilter filter = new IFilter() {

		@Override
		public boolean apply(IOutlineNode node) {
			return AbstractN4JSFilterOutlineContribution.this.apply(node);
		}

		@Override
		public boolean isEnabled() {
			if (filterByDefault()) {
				return !isPropertySet();
			}
			return isPropertySet();
		}
	};

	/**
	 * Override and return true if filter should be active by default.
	 */
	public boolean filterByDefault() {
		return false;
	}

	/**
	 * Used to retrieve images.
	 */
	@Inject
	protected PluginImageHelper imageHelper;

	@Override
	public IFilter getFilter() {
		return filter;
	}

}
