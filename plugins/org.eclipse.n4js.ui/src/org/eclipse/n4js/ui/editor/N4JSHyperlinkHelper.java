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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkLabelProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This class provides multiple hyperlinks for composed members.
 */
public class N4JSHyperlinkHelper extends HyperlinkHelper {

	@Inject
	@HyperlinkLabelProvider
	private ILabelProvider labelProvider;

	@Inject
	private Provider<XtextHyperlink> hyperlinkProvider;

	@Override
	public void createHyperlinksTo(XtextResource from, Region region, EObject target, IHyperlinkAcceptor acceptor) {
		boolean linksProvided = provideHyperlinksForComposedTypes(from, region, target, acceptor);
		if (!linksProvided) {
			internalCreateHyperlinksTo(from, region, target, acceptor);
		}
	}

	/** This method provides multiple hyperlinks for composed members. */
	private boolean provideHyperlinksForComposedTypes(XtextResource from, Region region, EObject target,
			IHyperlinkAcceptor acceptor) {

		if (target instanceof TMember) {
			TMember member = (TMember) target;
			if (member.isComposed()) {
				List<TMember> constituentMembers = member.getConstituentMembers();
				for (TMember constituentMember : constituentMembers) {
					internalCreateHyperlinksTo(from, region, constituentMember, acceptor);
				}
				return true;
			}
		}
		return false;
	}

	private void internalCreateHyperlinksTo(XtextResource from, Region region, EObject target,
			IHyperlinkAcceptor acceptor) {

		boolean linksProvided = provideHyperlinksForExternalFiles(region, target, acceptor);
		if (!linksProvided) {
			super.createHyperlinksTo(from, region, target, acceptor);
		}
	}

	/** This method converts file URIs to platform URIs for external library files. */
	private boolean provideHyperlinksForExternalFiles(Region region, EObject target, IHyperlinkAcceptor acceptor) {
		URI targetUriWithFragment = EcoreUtil.getURI(target);
		if (targetUriWithFragment.isFile()) {
			URI uri = URIUtils.tryToPlatformUri(targetUriWithFragment);
			superCreateHyperlinksTo(region, target, acceptor, uri);
			return true;
		}
		return false;
	}

	private void superCreateHyperlinksTo(Region region, EObject target, IHyperlinkAcceptor acceptor, URI normalized) {
		String hyperlinkText = labelProvider.getText(target);
		XtextHyperlink result = hyperlinkProvider.get();
		result.setHyperlinkRegion(region);
		result.setURI(normalized);
		result.setHyperlinkText(hyperlinkText);
		acceptor.accept(result);
	}
}
