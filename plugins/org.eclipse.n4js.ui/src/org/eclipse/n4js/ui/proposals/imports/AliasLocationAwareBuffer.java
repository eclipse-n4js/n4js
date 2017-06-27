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
package org.eclipse.n4js.ui.proposals.imports;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.parsetree.reconstr.impl.TokenStringBuffer;

import org.eclipse.n4js.services.N4JSGrammarAccess;

/**
 * A token buffer that records the location of a specified alias in the produced output.
 *
 * Only client is the {@link ImportRewriter} so far.
 *
 * It can be obtained via {@link #getAliasLocation()}.
 */
class AliasLocationAwareBuffer extends TokenStringBuffer {
	int offset = 0;
	private AliasLocation aliasLocation;

	private final String optionalAlias;
	private final RuleCall aliasAssignment;
	private final int replaceOffset;

	/**
	 * @param optionalAlias
	 *            the optional alias the we want to record
	 */
	AliasLocationAwareBuffer(String optionalAlias, int replaceOffset, N4JSGrammarAccess grammarAccess) {
		this.optionalAlias = optionalAlias;
		this.replaceOffset = replaceOffset;
		this.aliasAssignment = grammarAccess.getNamedImportSpecifierAccess()
				.getAliasBindingIdentifierParserRuleCall_1_2_0();
	}

	@Override
	public void writeSemantic(EObject grammarElement, String value) throws IOException {
		if (optionalAlias != null && optionalAlias.equals(value)) {
			aliasLocation = new AliasLocation(replaceOffset, offset, value);
		}
		if (value != null)
			offset += value.length();
		super.writeSemantic(grammarElement, value);
	}

	@Override
	public void writeHidden(EObject grammarElement, String value) throws IOException {
		// bug in Xtext - everything's written via writeHidden
		if (optionalAlias != null && optionalAlias.equals(value) && grammarElement == aliasAssignment) {
			aliasLocation = new AliasLocation(replaceOffset, offset, value);
		}
		if (value != null)
			offset += value.length();
		super.writeHidden(grammarElement, value);
	}

	/**
	 * Yields the alias location or null.
	 */
	AliasLocation getAliasLocation() {
		return aliasLocation;
	}
}
