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

import java.io.InputStream;

import org.eclipse.n4js.xpect.ui.common.XtextResourceCleanUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.testing.util.ResourceLoadHelper;

import com.google.inject.Inject;

/**
 */
public class N4ContentAssistProcessorTestBuilderHelper {
	@Inject
	private N4ContentAssistProcessorTestBuilder.Factory testBuilder;

	/**
	 * @param resource
	 *            the resource under test
	 * @return the configured content assist test builder (resource set and resource content appended).
	 * @throws Exception
	 *             some exception
	 */
	public N4ContentAssistProcessorTestBuilder createTestBuilderForResource(XtextResource resource) throws Exception {

		N4ContentAssistProcessorTestBuilder fixture = testBuilder.create(new ResourceLoadHelper() {

			@Override
			public XtextResource getResourceFor(InputStream stream) {
				return XtextResourceCleanUtil.cleanXtextResource(resource);
			}
		});

		fixture = fixture.append(resource.getParseResult().getRootNode().getText());
		return fixture;
	}

}
