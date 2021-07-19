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
package org.eclipse.n4js.json.tests.ide.symbol;

import org.eclipse.lsp4j.DocumentSymbol;

/**
 * This class contains some extracted code from Xtend files to suppress warnings which is not possible in Xtend.
 */
@SuppressWarnings("deprecation")
public class DocumentSymbolSuppressDeprecationUtil {

	/** used in {@link JSONHierarchicalSymbolServiceTest} */
	static public boolean getDeprecated(DocumentSymbol documentSymbol) {
		return documentSymbol.getDeprecated();
	}

}
