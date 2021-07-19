/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.bugreports;

import java.util.List;
import java.util.Objects;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

/**
 * This class contains some extracted code from Xtend files to suppress warnings which is not possible in Xtend.
 */
@SuppressWarnings("deprecation")
public class HoverSuppressDeprecationUtil {

	/** used in {@link GH_1956_RelinkTModuleLeavingResourceInInvalidState} */
	static public boolean assertHoverResult(String expectedLanguage, String expectedText, Hover actualResult) {
		List<Either<String, MarkedString>> contents = actualResult.getContents().getLeft();

		boolean success = contents.stream().filter(elem -> elem.isRight()).anyMatch(elem -> {
			return Objects.equals(elem.getRight().getLanguage(), expectedLanguage)
					&& Objects.equals(elem.getRight().getValue(), expectedText);
		});

		return success;
	}

}
