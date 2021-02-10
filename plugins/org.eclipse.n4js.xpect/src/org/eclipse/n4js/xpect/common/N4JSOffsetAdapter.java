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
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodHelper;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.EObjectCoveringRegionProvider;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectInvocation;
import org.eclipse.xpect.parameter.OffsetRegion;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.state.Creates;
import org.eclipse.xpect.text.IRegion;
import org.eclipse.xpect.xtext.lib.setup.ThisResource;
import org.eclipse.xpect.xtext.lib.util.XtextOffsetAdapter.IEObjectOwner;
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
	 * Provides an AST element of type {@link EObject}, previously found in the AST via
	 * {@link EObjectCoveringRegionProvider}. The AST related node covers the complete region defined by the offset of
	 * the specified location and the length of the specified location. That is, for a given test class with a parameter
	 * 'of' as OFFSET (via {@code @ParameterParser(syntax = "('of' arg1=OFFSET)?")}), the following two tests <br/>
	 * {@code // XpECT ... of 'a as B'} <br/>
	 * and <br/>
	 * {@code // XpECT ... of 'a'} <br/>
	 * will return different objects (CastExpression vs. IdentifierRef). If no region is defined, this test will return
	 * the next element after the test line.
	 */
	public static interface IEObjectCoveringRegion extends IEObjectOwner {
		// no new fields
		/**
		 * Return the offset of the region
		 */
		public int getOffset();
	}

	/***/
	public static class EObjectCoveringRegion implements IEObjectCoveringRegion {
		final EObject eObj;
		int offset;

		/***/
		public EObjectCoveringRegion(EObject eObj, int offset) {
			this.eObj = eObj;
			this.offset = offset;
		}

		@Override
		public EObject getEObject() {
			return eObj;
		}

		@Override
		public int getOffset() {
			return offset;
		}
	}

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

			EObject semanticObject = XtMethodHelper.getEObject(resource, offset, length);
			return new EObjectCoveringRegion(semanticObject, offset);
		}
	}

}
