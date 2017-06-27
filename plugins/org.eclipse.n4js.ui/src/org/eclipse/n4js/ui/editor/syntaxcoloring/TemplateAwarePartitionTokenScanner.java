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
package org.eclipse.n4js.ui.editor.syntaxcoloring;

import org.eclipse.xtext.ui.editor.model.PartitionTokenScanner;

/**
 */
public class TemplateAwarePartitionTokenScanner extends PartitionTokenScanner {

	@Override
	protected boolean shouldMergePartitions(String contentType) {
		if (super.shouldMergePartitions(contentType)) {
			return true;
		}
		if (TokenTypeToPartitionMapper.REG_EX_PARTITION.equals(contentType)) {
			// matches the leading /= or / of a regular expression
			return getTokenLength() <= 2;
		}
		if (TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION.equals(contentType)) {
			// matches the single } of a template substitution
			return getTokenLength() == 1;
		}
		return false;
	}

}
