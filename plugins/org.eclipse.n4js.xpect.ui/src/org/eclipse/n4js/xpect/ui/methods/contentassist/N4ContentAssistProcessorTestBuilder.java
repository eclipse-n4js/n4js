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
package org.eclipse.n4js.xpect.ui.methods.contentassist;

import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.junit4.AbstractXtextTests;
import org.eclipse.xtext.junit4.ui.ContentAssistProcessorTestBuilder;
import org.eclipse.xtext.junit4.util.ResourceLoadHelper;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class N4ContentAssistProcessorTestBuilder extends ContentAssistProcessorTestBuilder {

	/**
	 */
	public static class Factory extends ContentAssistProcessorTestBuilder.Factory {

		private final Injector injector;

		/***/
		@Inject
		public Factory(Injector injector) {
			super(injector);
			this.injector = injector;
		}

		@Override
		public N4ContentAssistProcessorTestBuilder create(ResourceLoadHelper resourceLoadHelper) throws Exception {
			return new N4ContentAssistProcessorTestBuilder(this.injector, resourceLoadHelper);
		}
	}

	/***/
	public N4ContentAssistProcessorTestBuilder(ISetup setupClazz, AbstractXtextTests tests) throws Exception {
		super(setupClazz, tests);
	}

	/***/
	public N4ContentAssistProcessorTestBuilder(Injector injector, ResourceLoadHelper helper) throws Exception {
		super(injector, helper);
	}

	@Override
	public N4ContentAssistProcessorTestBuilder append(String model) throws Exception {
		return (N4ContentAssistProcessorTestBuilder) super.append(model);
	}

	@Override
	public N4ContentAssistProcessorTestBuilder reset() throws Exception {
		return (N4ContentAssistProcessorTestBuilder) super.reset();
	}

}
