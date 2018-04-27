/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json;

import org.eclipse.n4js.json.conversion.JSONValueConverterService;
import org.eclipse.n4js.json.formatting2.JSONFormatter;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.formatting2.IFormatter2;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
@SuppressWarnings("restriction")
public class JSONRuntimeModule extends AbstractJSONRuntimeModule {
	/** Bind custom value converter for JSON-specific literals */
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return JSONValueConverterService.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.formatting.Formatter2Fragment2
	public Class<? extends IFormatter2> bindIFormatter2() {
		return JSONFormatter.class;
	}
}
