/**
 * Copyright (c) 2018 Jens von Pilgrim.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jens von Pilgrim - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.sourcemap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser used by {@link SourceMap#parse(CharSequence)}. The parser expects a JSON source map string, e.g. <code>
 * { "version":3,
 *   "file":"C.js",
 *   "sourceRoot":"",
 *   "sources":["C.ts"],
 *   "names":[],
 *   "mappings":"AAAA;IACC,OAAO,CAAC,GAAG,CAAC,OAAO,CAAC,CAAA;IACpB,OAAO,CAAC,GAAG,CAAC,OAAO,CAAC,CAAA;AACrB,CAAC;AAED,CAAC,EAAE,CAAC"
 * }
 * </code> For details about the format, see {@link SourceMap} and {@link SourceMapRev3Generator}.
 *
 * We do not use a JSON parser here on purpose to be robust to invalid JSON syntax which may be added on purposes for
 * security reasons (to make the source map not executable).
 */
class SourceMapParser {
	private final static String SP = "\\s*";
	private final static String STR = "(?:\"((?:[^\"]|\\\\\")*)\")";
	private final static String STRING_ARRAY = "\\[" + SP + "(?:" + STR + ",?)*" + SP + "\\]";
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

	static SourceMap parse(CharSequence s) {
		SourceMap map = new SourceMap();
		map.version = parseString(patternVersion, s);
		map.file = parseString(patternFile, s);
		map.sourceRoot = parseString(patternSourceRoot, s);
		map.sources.addAll(parseStrings(patternSources, s));
		map.names.addAll(parseStrings(patternNames, s));

		Matcher m = patternMappingsStart.matcher(s);
		MappingEntry.PreviousEntry prev = new MappingEntry.PreviousEntry();
		if (m.find()) {
			int mappingStart = m.end();
			int i = mappingStart;
			int outLine = 0;
			StringBuilder vlq = new StringBuilder();
			while (i < s.length()) {
				char c = s.charAt(i);
				if (c == ';' || c == '"' || c == ',') {
					if (vlq.length() > 0) {
						int[] nums = Base64VLQ.decode(vlq);
						MappingEntry entry = new MappingEntry(outLine, nums, prev);
						entry.updatePrev(prev);
						map.addMappig(entry);
						vlq.setLength(0);
					}
					if (c == ';') {
						outLine++;
						prev.genColumn = 0;
					}
					if (c == '"') {
						break;
					}
				} else {
					vlq.append(c);
				}
				i++;
			}
			if (vlq.length() > 0) {
				int[] nums = Base64VLQ.decode(vlq);
				MappingEntry mapping = new MappingEntry(outLine, nums, prev);
				map.addMappig(mapping);
			}
		}
		return map;
	}

	private static String parseString(Pattern pattern, CharSequence s) {
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}

	private static List<String> parseStrings(Pattern pattern, CharSequence s) {
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			int n = matcher.groupCount();
			List<String> list = new ArrayList<>(n);
			for (int i = 1; i <= n; i++) {
				String g = matcher.group(i);
				if (g != null) {
					list.add(g);
				} else {
					if (n == 1) {
						return Collections.emptyList();
					}
				}
			}
			return list;
		} else {
			return Collections.emptyList();
		}
	}

}