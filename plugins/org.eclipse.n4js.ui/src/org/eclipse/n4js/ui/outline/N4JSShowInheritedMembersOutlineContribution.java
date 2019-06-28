/**
 * Copyright (c) 2017 itemis, NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtend.ide.outline.ShowSyntheticMembersContribution
 *	in bundle org.eclipse.xtend.ide
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2012 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.outline;

import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;

/**
 * Code more or less copied from Xtend's {code ShowSyntheticMembersContribution} and adjusted to toggle inherited/owned
 * members view. Has to be bound in UI module.
 */
public class N4JSShowInheritedMembersOutlineContribution extends AbstractN4JSFilterOutlineContribution {

	/**
	 * Preference key to store default mode.
	 */
	public static final String PREFERENCE_KEY = "ui.outline.showInherited";

	@Override
	protected boolean apply(IOutlineNode node) {
		if (node instanceof N4JSEObjectNode) {
			N4JSEObjectNode n4jseObjectNode = (N4JSEObjectNode) node;
			return !n4jseObjectNode.isInherited;
		}
		return true;
	}

	/**
	 * Filter inherited members by default.
	 */
	@Override
	public boolean filterByDefault() {
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
