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
package org.eclipse.n4js.ide.xtext.server;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;

import com.google.common.collect.Multimap;

/**
 * Adds {@link #argumentTypes()} to {@link ExecutableCommandRegistry}
 */
public class XExecutableCommandRegistry extends ExecutableCommandRegistry implements ExecuteCommandParamsDescriber {

	@Override
	public Map<String, Type[]> argumentTypes() {
		try {
			Multimap<String, IExecutableCommandService> registeredCommands = new ReflectExtensions().get(this,
					"registeredCommands");
			Map<String, Type[]> result = new HashMap<>();
			for (IExecutableCommandService service : new HashSet<>(registeredCommands.values())) {
				if (service instanceof ExecuteCommandParamsDescriber) {
					result.putAll(((ExecuteCommandParamsDescriber) service).argumentTypes());
				}
			}
			return result;
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			return Collections.emptyMap();
		}
	}

}
