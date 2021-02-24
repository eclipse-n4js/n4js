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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.xtext.server.DebugService.DebugServiceDefaultImpl;

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

	@Override
	protected void appendResourceInfo(StringBuilder sb, Resource res) {
		URI uri = res.getURI();
		sb.append(uri != null ? uri.lastSegment() : "<null>");
		if (res instanceof N4JSResource) {
			N4JSResource resCasted = (N4JSResource) res;
			Script script = resCasted.getScript();
			TModule module = resCasted.getModule();
			sb.append(" script: ");
			sb.append(script != null ? (script.eIsProxy() ? "<proxy>" : "<no proxy>") : "<null>");
			sb.append(" module: ");
			sb.append(module != null ? (module.eIsProxy() ? "<proxy>" : "<no proxy>") : "<null>");
		}
		sb.append(" uri: ");
		sb.append(uri);
	}

	private String getFailSafe(Supplier<String> supplier) {
		try {
			return supplier.get();
		} catch (Throwable th) {
			return "<exception: " + th.getMessage() + ">";
		}
	}
}
