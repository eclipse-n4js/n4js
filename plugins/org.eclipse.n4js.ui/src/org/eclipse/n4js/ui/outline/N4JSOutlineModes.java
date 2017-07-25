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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider.ModeAware;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineMode;

/**
 * Used in N4JSOutlineTreeProvider to filter what kind of members to show. Must be bound in module. This is the mode
 * used in the quick outline view, the user can cycle through the modes by pressing Ctrl/CMD-O. This is NOT the filter
 * in the outline view, for that, see {@link N4JSShowInheritedMembersOutlineContribution}.
 */
public class N4JSOutlineModes implements ModeAware {
	/**
	 * Show only owned members of classifiers.
	 */
	public static final OutlineMode SHOW_OWNED_MODE = new OutlineMode("owned", "show only owned members");

	/**
	 * Show owned, inherited, consumed, or polyfilled members of classifiers.
	 */
	public static final OutlineMode SHOW_INHERITED_MODE = new OutlineMode("inherited",
			"show inherited, consumed, or polyfilled members");

	private static final List<OutlineMode> MODES = newArrayList(SHOW_OWNED_MODE, SHOW_INHERITED_MODE);

	private int index = 0;

	@Override
	public List<OutlineMode> getOutlineModes() {
		return MODES;
	}

	@Override
	public OutlineMode getCurrentMode() {
		return getOutlineModes().get(index);
	}

	@Override
	public OutlineMode getNextMode() {
		return getOutlineModes().get((index + 1) % getOutlineModes().size());
	}

	@Override
	public void setCurrentMode(OutlineMode outlineMode) {
		int newIndex = getOutlineModes().indexOf(outlineMode);
		if (newIndex != -1) {
			index = newIndex;
		}
	}
}