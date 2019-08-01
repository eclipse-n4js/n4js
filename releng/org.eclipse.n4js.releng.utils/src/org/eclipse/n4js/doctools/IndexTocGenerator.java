/**
 * Copyright (c) 2019 HAW Hamburg and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jens von Pilgrim (HAW Hamburg) - Initial API and implementation
 */
package org.eclipse.n4js.doctools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Command line tool converting an asciidoc generated xml file (for docbook) to an toc.html which can be included in the
 * main index file.
 */
public class IndexTocGenerator extends EclipseHelpTOCGenerator {

	/**
	 * If called without arguments prints out help. In general: Parses commands, initializes an instance and use that to
	 * create the toc.
	 */
	public static void main(String[] args) {

		if (args.length % 2 != 1) {
			printHelp();
			System.exit(1);
		}

		String in = null;
		String tocName = "toc.xml";
		String linkPrefix = "";

		for (int f = 0; f < args.length - 1; f += 2) {
			String flag = args[f];
			String value = args[f + 1];
			switch (flag) {
			case "-t":
				tocName = value;
				break;
			case "-p":
				linkPrefix = value;
				break;
			default:
				System.out.println("Flag " + flag + " not recognized.");
				printHelp();
				System.exit(2);
			}
		}
		in = args[args.length - 1];

		File f = new File(in);
		try {
			IndexTocGenerator handler = new IndexTocGenerator();
			String toc = handler.generateTOC(f, linkPrefix);
			Files.write(Paths.get(tocName), toc.getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(2);
		}

		System.exit(0);
	}

	private static void printHelp() {
		System.out.println("etoc [-t tocfilename] [p link prefix] inputfile\n"
				+ "-t string   The tocfile to create (incl. xml). Existing file will be overwritten. Default is 'toc.html'\n"
				+ "-p path     Path to prepend to all links in the tocfile. Default is empty string.");
	}

	/**
	 * Visible for testing.
	 */
	IndexTocGenerator() {
	}

	/**
	 * only the linkPrefix
	 */
	@Override
	public String computeBasedir(File file, String linkPrefix) {
		return linkPrefix + ".";
	}

	/**
	 * Emits the result (xml) file as string. Maybe overridden if other formats are to be emitted.
	 */
	@Override
	protected String getResult() {
		StringBuilder out = new StringBuilder();
		out.append("<ol>\n");

		for (Topic topic : maintopics) {
			appendTopic(out, "\t", topic, getFilename(topic.label));
		}
		out.append("\n</ol>");
		return out.toString();
	}

	/**
	 * Print out subtopics of a topic. Maybe overridden if other formats are to be emitted.
	 */
	@Override
	protected void appendTopic(StringBuilder out, String indent, Topic topic, String fileName) {
		out.append("\n" + indent);
		String href = dir + "/" + fileName + "#" + topic.id;
		out.append("<li><a href=\"" + href + "\">" + topic.label);
		if (!topic.subtopics.isEmpty()) {
			// no subtopics at the moment
			// out.append(indent + "<ol>");
			// for (Topic sub : topic.subtopics) {
			// appendTopic(out, indent + "\t", sub, fileName);
			// }
			// out.append(indent + "</ol>");
			out.append("\n" + indent);
		}
		out.append("</li>");
	}
}

/*
 *
 * <?xml version="1.0" encoding="UTF-8"?> <?asciidoc-toc maxdepth="5"?> <?asciidoc-numbered maxdepth="5"?> <!DOCTYPE
 * book [<!ENTITY % db5ent PUBLIC "-//FOPUB//ENTITIES Entities for DocBook 5" "db5.ent"> %db5ent;]> <book
 * xmlns="http://docbook.org/ns/docbook" xmlns:xl="http://www.w3.org/1999/xlink" version="5.0" xml:lang="en"> <info>
 * <title>Sample</title> <!-- ... --> </info> <!-- ... --> <chapter xml:id="_introduction"> <title>Chapter Title</title>
 * <simpara>Some text.</simpara> <section xml:id="_notation" role="language-n4js"> <title>Main Section</title> <section
 * xml:id="_grammar-notation"> <title>Subsection</title> <simpara>Simple paragraph</simpara> </section> <!-- ... -->
 * </section> <!-- ... --> </chapter> <!-- ... --> </book>
 *
 */