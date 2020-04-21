/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.editor.contentassist;

import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory;

import com.google.inject.Inject;

/**
 * {@link ContentAssistContextFactory} with data collection.
 */
public class N4JSContentAssistContextFactory extends ContentAssistContextFactory {

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Override
	protected ContentAssistContext[] doCreateContexts(int offset) {
		try (Measurement m = dataCollectors.dcCreateContexts().getMeasurement()) {
			return super.doCreateContexts(offset);
		}
	}

}
