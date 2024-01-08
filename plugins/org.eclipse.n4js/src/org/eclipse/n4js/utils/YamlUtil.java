/**
 * Copyright (c) 2024 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Utility for loading yaml files
 */
public class YamlUtil {
	private final static Logger LOGGER = Logger.getLogger(YamlUtil.class);

	/** Loads a yaml file from the give location */
	static public Multimap<String, String> loadYamlAtLocation(Path path) {
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			return loadYamlFromLines(lines);

		} catch (IOException e) {
			LOGGER.error("Could not load " + path.toString(), e);
		}
		return LinkedHashMultimap.create();
	}

	/** Loads yaml content from the given string */
	static public Multimap<String, String> loadYamlFromString(String str) {
		return loadYamlFromLines(Arrays.asList(str.split("\n")));
	}

	/** Loads yaml content from the given lines */
	static public Multimap<String, String> loadYamlFromLines(List<String> lines) {
		Multimap<String, String> result = LinkedHashMultimap.create();
		Map<Integer, String> stack = new LinkedHashMap<>();
		for (String line : lines) {
			if (line.startsWith("---") || line.startsWith("#")) {
				continue;
			}
			int commentIdx = line.indexOf("#");
			if (commentIdx >= 0) {
				line = line.substring(0, commentIdx);
			}
			int leadingSpaces = line.indexOf(line.trim());
			line = line.trim();

			if (line.startsWith("-")) {
				line = line.substring(1).trim();
			}
			int propNameIdx = line.indexOf(":");
			if (propNameIdx >= 0) {
				String propName = line.substring(0, propNameIdx).trim();

				stack.put(leadingSpaces, propName);
				stack.keySet().removeIf(k -> k > leadingSpaces);

				String value = line.substring(propNameIdx + 1).trim();
				if (!isNullOrEmpty(value)) {
					value = trimQuotes(value);
					String qpn = join(":", stack.values());
					result.put(qpn, value);
				}
			} else {
				line = trimQuotes(line);
				String qpn = join(":", e -> e.getValue(), filter(stack.entrySet(), e -> e.getKey() <= leadingSpaces));
				result.put(qpn, line);
			}
		}
		return result;
	}

	static private String trimQuotes(String str) {
		if (str.startsWith("\"") && str.endsWith("\"")) {
			return str.substring(1, str.length() - 1); // no trim
		} else if (str.startsWith("'") && str.endsWith("'")) {
			return str.substring(1, str.length() - 1); // no trim
		}
		return str;
	}
}
