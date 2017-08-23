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
package org.eclipse.n4js.ui.editor;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Region;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;

/**
 * This class provides multiple hyperlinks for composed members.
 */
public class ComposedMemberAwareHyperlinkHelper extends HyperlinkHelper {

	@Override
	public void createHyperlinksTo(XtextResource from, Region region, EObject target, IHyperlinkAcceptor acceptor) {
		if (target instanceof TMember) {
			TMember member = (TMember) target;
			if (member.isComposed()) {
				List<TMember> constituentMembers = member.getConstituentMembers();
				for (TMember constituentMember : constituentMembers) {
					super.createHyperlinksTo(from, region, constituentMember, acceptor);
				}
				return;
			}
		}
		super.createHyperlinksTo(from, region, target, acceptor);
	}
}
