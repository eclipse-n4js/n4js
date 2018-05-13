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
package org.eclipse.n4js.transpiler.sourcemap;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.transpiler.sourcemap.SourceMap.Mapping;

/**
 * <code>
 * { "version":3,
 *   "file":"C.js",
 *   "sourceRoot":"",
 *   "sources":["C.ts"],
 *   "names":[],
 *   "mappings":"AAAA;IACC,OAAO,CAAC,GAAG,CAAC,OAAO,CAAC,CAAA;IACpB,OAAO,CAAC,GAAG,CAAC,OAAO,CAAC,CAAA;AACrB,CAAC;AAED,CAAC,EAAE,CAAC"
 * }
 * </code>
 */
class SourceMapParser {
	private final static String SP = "\\s*";
	private final static String STR = "(?:\"((?:[^\"]|\\\\\")*)\")";
	private final static String STRING_ARRAY = "\\[" + SP + "(" + STR + ",?)*" + SP + "\\]";
	private final static String NUM = "([0-9]+)";

	private static Pattern propPattern(String name, String type) {
		return Pattern.compile("\"" + name + "\"" + SP + ":" + SP + type);
	}

	private final static Pattern patternVersion = propPattern("version", NUM);
	private final static Pattern patternFile = propPattern("file", STR);
	private final static Pattern patternSourceRoot = propPattern("sourceRoot", STR);
	private final static Pattern patternSources = propPattern("sources", STRING_ARRAY);
	private final static Pattern patternNames = propPattern("names", STRING_ARRAY);
	private final static Pattern patternMappingsStart = Pattern.compile("\"mappings\"" + SP + ":" + SP + "\"");

	public static SourceMap parse(String s) {
		SourceMap map = new SourceMap();

		String versionStr = patternVersion.matcher(s).group(1);
		String file = patternFile.matcher(s).group(1);
		String sourceRoot = patternSourceRoot.matcher(s).group(1);
		List<String> sources = strings(patternSources, s);
		List<String> names = strings(patternNames, s);
		Matcher m = patternMappingsStart.matcher(s);

		Mapping prev = Mapping.init();

		if (m.matches()) {
			int mappingStart = patternMappingsStart.matcher(s).end();
			int i = mappingStart;
			int inputLine = 0;
			String vlq = "";
			while (i < s.length()) {
				char c = s.charAt(i);
				if (c == ';' || c == '"' || c == ',') {
					if (!vlq.isEmpty()) {
						int[] nums = Base64VLQ.decode(vlq);
						Mapping mapping = new Mapping(inputLine, nums, prev);
						map.addMappig(mapping);
					}
					if (c == ';') {
						inputLine++;
						prev.inputColumn = 0;
					}
					if (c == '"') {
						break;
					}
				} else {
					vlq += c;
				}
				i++;
			}

		}

		return map;
	}

	private static List<String> strings(Pattern pattern, CharSequence s) {
		Matcher matcher = pattern.matcher(s);
		int n = matcher.groupCount();
		List<String> list = new ArrayList<>(n);
		for (int i = 1; i <= n; i++) {
			list.add(matcher.group(i));
		}
		return list;
	}

}