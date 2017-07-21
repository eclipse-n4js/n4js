/**
 * Copyright (c) 2017 itemis, NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineFilterAndSorter.IFilter;

import com.google.inject.Inject;

/**
 * Code more or less copied from Xtend's {code ShowSyntheticMembersContribution} and adjusted to toggle inherited/owned
 * members view. Has to be bound in UI module.
 */
public class N4JSShowInheritedMembersOutlineContribution extends AbstractFilterOutlineContribution {

	public static final String PREFERENCE_KEY = "ui.outline.showInherited";

	@Inject
	private PluginImageHelper imageHelper;

	private IFilter filter;

	@Override
	public IFilter getFilter() {
		if (filter == null) {
			filter = new IFilter() {
				@Override
				public boolean apply(IOutlineNode node) {
					return N4JSShowInheritedMembersOutlineContribution.this.apply(node);
				}

				@Override
				public boolean isEnabled() {
					return !isPropertySet();
				}
			};
		}
		return filter;
	}

	@Override
	protected boolean apply(IOutlineNode node) {
		if (node instanceof N4JSEObjectNode) {
			N4JSEObjectNode n4jseObjectNode = (N4JSEObjectNode) node;
			return !n4jseObjectNode.isInherited;
		}
		return true;
	}

	@Override
	public String getPreferenceKey() {
		return PREFERENCE_KEY;
	}

	@Override
	protected void configureAction(Action action) {
		action.setText("Show Inherited Members");
		action.setDescription("Show Inherited Members");
		action.setToolTipText("Show inherited, consumed, and polyfilled members");
		action.setImageDescriptor(imageHelper.getImageDescriptor("inher_co.png"));
	}
}
