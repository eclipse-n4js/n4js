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
package org.eclipse.n4js.ide.server;

import java.util.function.Supplier;

import org.eclipse.n4js.ide.xtext.server.DebugService.DebugServiceDefaultImpl;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.inject.Singleton;

/**
 * Extends {@link DebugServiceDefaultImpl} to provide additional, N4JS-specific debug information.
 */
@Singleton
public class N4JSDebugService extends DebugServiceDefaultImpl {

	@Override
	protected void compileDebugInfo(StringBuilder sb, String separator) {
		sb.append("Language version: " + getFailSafe(N4JSLanguageUtils::getLanguageVersion));
		sb.append('\n');
		sb.append("Language commit : " + getFailSafe(N4JSLanguageUtils::getLanguageCommit));
		sb.append(separator);
		super.compileDebugInfo(sb, separator);
	}

	private String getFailSafe(Supplier<String> supplier) {
		try {
			return supplier.get();
		} catch (Throwable th) {
			return "<exception: " + th.getMessage() + ">";
		}
	}
}
