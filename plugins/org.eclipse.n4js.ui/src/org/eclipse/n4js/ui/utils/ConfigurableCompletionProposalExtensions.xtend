/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.utils

import org.eclipse.n4js.ui.proposals.imports.N4JSReplacementTextApplier
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal

/**
 * Type-safe access to some user data entries of completion proposals.
 */
class ConfigurableCompletionProposalExtensions {

	private static final String KEY_REPLACEMENT_SUFFIX = "REPLACEMENT_SUFFIX";
	private static final String KEY_CURSOR_OFFSET = "CURSOR_OFFSET";


	/** See {@link #setReplacementSuffix(ConfigurableCompletionProposal, String)}. */
	def public static String getReplacementSuffix(ConfigurableCompletionProposal proposal) {
		val suffix = proposal.getAdditionalData(KEY_REPLACEMENT_SUFFIX);
		return if (suffix instanceof String) suffix else "";
	}

	/**
	 * If set, an additional string will be appended by {@link N4JSReplacementTextApplier} after ordinary
	 * replacement text when the given proposal is applied. The cursor will be placed after this suffix,
	 * unless a {@link #setCursorOffset(ConfigurableCompletionProposal, int) cursor offset} is set to
	 * change this default behavior.
	 */
	def public static void setReplacementSuffix(ConfigurableCompletionProposal proposal, String suffix) {
		proposal.setAdditionalData(KEY_REPLACEMENT_SUFFIX, suffix);
	}

	/** See {@link #setCursorOffset(ConfigurableCompletionProposal, int)}. */
	def public static int getCursorOffset(ConfigurableCompletionProposal proposal) {
		val offset = proposal.getAdditionalData(KEY_CURSOR_OFFSET);
		return if (offset instanceof Integer) offset.intValue else 0;
	}

	/**
	 * If set to a non-zero value, the cursor won't appear at the default location after the given proposal
	 * is applied but instead be shifted by the given offset (handled by {@link N4JSReplacementTextApplier}).
	 */
	def public static void setCursorOffset(ConfigurableCompletionProposal proposal, int offset) {
		proposal.setAdditionalData(KEY_CURSOR_OFFSET, Integer.valueOf(offset));
	}
}
