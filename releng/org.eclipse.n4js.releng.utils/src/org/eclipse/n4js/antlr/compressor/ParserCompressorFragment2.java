/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.antlr.compressor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.xtext.xtext.generator.AbstractXtextGeneratorFragment;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.eclipse.n4js.antlr.compressor.IfElseCascade.Replacement;

/**
 * MWE fragment that injects the necessary code to compress the generated ANTLR V3 parser in order to avoid problems
 * with methods exceeding the 65535 bytes limit.
 */
public class ParserCompressorFragment2 extends AbstractXtextGeneratorFragment {

	private final static Logger LOGGER = Logger.getLogger(ParserCompressorFragment2.class);

	/**
	 * Pattern for detecting const integer definitions. Package visible for testing.
	 */
	final static Pattern CONST_DEF_PATTERN = Pattern
			.compile("^\\s*public\\ static\\ final\\ int\\ ([A-Za-z_0-9]+)=(-?[0-9]+);$", Pattern.MULTILINE);
	/**
	 * Pattern for detecting state initializer which indicates the start of a if-else cascade. Package visible for
	 * testing.
	 */
	final static Pattern STATE_CHANGE_INITIALIZER_PATTERN = Pattern.compile("^\\s+s\\ =\\ -1;$", Pattern.MULTILINE);

	private final List<String> grammarFiles;
	private boolean backup = false;
	private int cascadeThreshold = 10;

	/**
	 *
	 */
	public ParserCompressorFragment2() {
		grammarFiles = new ArrayList<>();
	}

	/**
	 * Adds a grammar file name.
	 */
	public void addGrammarFile(String fileName) {
		grammarFiles.add(fileName);
	}

	@Override
	public void generate() {
		for (String fileName : grammarFiles) {
			File file = new File(fileName);
			String javaSource = null;
			try {
				javaSource = Files.toString(file, Charsets.UTF_8);
			} catch (Exception ex) {
				LOGGER.error("Error reading file " + fileName + ": " + ex.getMessage());
			}
			if (javaSource != null) {
				String compressed = process(javaSource, file);
				LOGGER.info("File " + readableFileName(file) + " compressed: " + javaSource.length() + " --> "
						+ compressed.length() + " ("
						+ 100 * compressed.length() / javaSource.length() + "%)");

				if (backup) {
					try {
						Files.copy(file, new File(file.getParentFile(), file.getName() + ".bak"));
					} catch (IOException e) {
						LOGGER.error("Error creating backup of " + readableFileName(file) + ": " + e.getMessage());
						return;
					}
				}

				try {
					Files.write(compressed, file, Charsets.UTF_8);
				} catch (IOException e) {
					LOGGER.error("Error writing compressed file " + readableFileName(file) + ": " + e.getMessage());
				}
			}
		}
	}

	String readableFileName(File f) {
		String path = f.getPath();
		int firstChar = 0;
		for (; firstChar < path.length(); firstChar++) {
			if (Character.isLetterOrDigit(path.charAt(firstChar))) {
				break;
			}
		}
		if (firstChar == path.length()) {
			firstChar = 0;
		}
		int firstSeg = path.indexOf(File.separatorChar, firstChar);
		if (firstSeg > 0) {
			return path.substring(firstChar, firstSeg) + "/.../" + f.getName();
		}
		return f.getName();
	}

	String process(String javaSource, File file) {

		Map<String, Integer> parserConstMap = createConstMap(javaSource);
		if (parserConstMap.isEmpty()) {
			LOGGER.info("No integer constants found in " + readableFileName(file));
			return null;
		}

		return processCascades(javaSource, parserConstMap);
	}

	String processCascades(String javaSource, Map<String, Integer> parserConstMap) {

		List<IfElseCascade> cascades = findCascades(javaSource);
		StringBuilder strb = new StringBuilder(javaSource.length());
		int offset = 0;
		int counter = 0;
		List<String> transMatrixDefs = new ArrayList<>(cascades.size());
		Map<String, String> arrayLiterals = new HashMap<>();
		for (IfElseCascade cascade : cascades) {
			if (cascade.size() >= cascadeThreshold) {
				strb.append(javaSource.substring(offset, cascade.start));
				Replacement replacements = cascade.getReplacements(parserConstMap, counter);

				String delegate = arrayLiterals.get(replacements.arrayLiteral);
				strb.append("\n                        ");
				if (delegate == null) {
					transMatrixDefs.add(replacements.getMatrixDefinition());
					arrayLiterals.put(replacements.arrayLiteral, IfElseCascade.getMatrixName(counter));
					strb.append(replacements.getStatement());
				} else {
					strb.append(replacements.getStatement(delegate));
				}
				strb.append("\n                        ");
				offset = cascade.end;
				counter++;
			}
		}
		strb.append(javaSource.substring(offset, javaSource.length()));

		strb.append("\nfinal class ").append(IfElseCascade.MATRIX_CLASS).append("{");
		for (String def : transMatrixDefs) {
			strb.append("\n\t").append(def);
		}
		strb.append("\n}");
		String compressed = strb.toString();
		return compressed;

	}

	private List<IfElseCascade> findCascades(String grammarContent) {
		Matcher matcher = STATE_CHANGE_INITIALIZER_PATTERN.matcher(grammarContent);
		IfElseCascadeParser ifElseCascadeParser = new IfElseCascadeParser(grammarContent);
		List<IfElseCascade> cascades = new ArrayList<>();
		int cascadeEnd = 0;
		while (matcher.find()) {
			int offset = matcher.end();
			if (offset < cascadeEnd) {
				throw new IllegalStateException("New state initializer in cascade found");
			}

			List<IfElseCascade> moreCascades = ifElseCascadeParser.findCascades(offset);
			if (moreCascades != null) {
				cascades.addAll(moreCascades);
			}
		}
		if (cascades.isEmpty()) {
			throw new IllegalStateException("No if-else cascades found.");
		}
		return cascades;
	}

	/**
	 * Parses definition of token constants. Package visible for testing.
	 */
	Map<String, Integer> createConstMap(String grammarContent) {
		Map<String, Integer> parserConstMap = new HashMap<>();
		Matcher matcher = CONST_DEF_PATTERN.matcher(grammarContent);
		while (matcher.find()) {
			String name = matcher.group(1);
			int value = Integer.parseInt(matcher.group(2));
			parserConstMap.put(name, value);
		}
		return parserConstMap;
	}

	/**
	 * If backup is true, the original file is copied to *.bak". Default value is false.
	 */
	public void setBackup(boolean backup) {
		this.backup = backup;
	}

	/**
	 * The cascade threshold, if a cascade has less if/else statements, it is not replaced. Default value is 10. It is a
	 * string due to limitations of MWE2.
	 */
	public void setCascadeThreshold(String cascadeThreshold) {
		this.cascadeThreshold = Integer.parseInt(cascadeThreshold);
	}

}
