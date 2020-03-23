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
package org.eclipse.n4js.ide.server.codeActions.util;

import org.eclipse.lsp4j.CodeActionKind;

/**
 * Utility methods for dealing with LSP code actions.
 */
public class CodeActionUtils {

	/**
	 * Some {@link CodeActionKind}s are special cases of other, more generic {@code CodeActionKind}s. For example,
	 * {@link CodeActionKind#RefactorInline RefactorInline} is a special case of {@link CodeActionKind#Refactor
	 * Refactor}.
	 * <p>
	 * This method tells whether <code>kind</code> is a special case of <code>genericKind</code>.
	 */
	public static boolean isSpecialKindOf(String kind, String genericKind) {
		return kind.equals(genericKind) || kind.startsWith(genericKind + ".");
	}
}
