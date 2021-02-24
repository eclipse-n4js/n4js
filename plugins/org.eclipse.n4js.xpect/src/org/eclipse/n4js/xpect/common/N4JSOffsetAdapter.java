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
package org.eclipse.n4js.xpect.common;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.ide.tests.helper.server.xt.EObjectCoveringRegion;
import org.eclipse.n4js.ide.tests.helper.server.xt.IEObjectCoveringRegion;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtResourceUtil;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.EObjectCoveringRegionProvider;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectInvocation;
import org.eclipse.xpect.parameter.OffsetRegion;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.state.Creates;
import org.eclipse.xpect.text.IRegion;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Extension of default parameter adapter providing support for {@link IEObjectCoveringRegion}, which basically is an
 * offset adapter taking the length of the location into account.
 *
 * This adapter needs to be added to a test via annotation {@link org.eclipse.xpect.XpectImport}, i.e.
 * {@code @XpectImport(N4JSOffsetAdapter.class)}.
 */
@XpectSetupFactory
@XpectImport(EObjectCoveringRegionProvider.class)
public class N4JSOffsetAdapter {

	/**
	 * Provides an {@link IEObjectCoveringRegion} parameter, activated by {@link N4JSOffsetAdapter}.
	 */
	@XpectSetupFactory
	public static class EObjectCoveringRegionProvider {

		private final IRegion region; // can be null, then the matchedOffset is given:
		private final int matchedOffset; // matchedOffest if no region was specified
		private final @ThisResource XtextResource resource;
		@SuppressWarnings("unused")
		private final XpectInvocation invocation;

		/***/
		public EObjectCoveringRegionProvider(@ThisResource XtextResource resource, XpectInvocation invocation,
				OffsetRegion delegate) {
			this.resource = resource;
			this.region = delegate.getMatchedRegion();
			this.invocation = invocation;
			this.matchedOffset = delegate.getMatchedOffset();
		}

		/***/
		public EObjectCoveringRegionProvider(@ThisResource XtextResource resource, XpectInvocation invocation) {
			this.resource = resource;
			this.region = invocation.getExtendedRegion();
			this.invocation = invocation;
			this.matchedOffset = region.getOffset();
		}

		/***/
		@Creates
		public IEObjectCoveringRegion IEObjectCoveringRegion() {
			final boolean haveRegion = region != null;
			final int offset = haveRegion ? region.getOffset() : this.matchedOffset;
			final int length = haveRegion ? region.getLength() : 0;

			EObject semanticObject = XtResourceUtil.findEObject(resource, offset, length);
			EStructuralFeature structuralFeature = XtResourceUtil.findStructuralFeature(resource, offset);
			return new EObjectCoveringRegion(semanticObject, offset, structuralFeature);
		}
	}

}
