/**
 * Copyright (c) 2019 NumberFour AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.doctools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Command line tool converting an asciidoc generated xml file (for docbook) to an Eclipse help table of contents file.
 */
public class EclipseHelpTOCGenerator extends DefaultHandler {

	private static final String XML_ID = "xml:id";

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
			EclipseHelpTOCGenerator handler = new EclipseHelpTOCGenerator();
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
				+ "-t string   The tocfile to create (incl. xml). Existing file will be overwritten. Default is 'toc.xml'\n"
				+ "-p path     Path to prepend to all links in the tocfile. Default is empty string.");
	}

	/**
	 * Creates an Eclipse help TOC from given asciidoc generated XML file.
	 */
	public String generateTOC(File file, String linkPrefix)
			throws ParserConfigurationException, SAXException, IOException {

		FileInputStream fis = new FileInputStream(file);
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();

			dir = computeBasedir(file, linkPrefix);

			reader.setContentHandler(this);
			reader.setEntityResolver(new EntityResolver() {

				@Override
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(new StringReader(""));
				}

			});

			InputSource input = new InputSource(fis);
			reader.parse(input);

			return getResult();
		} finally {
			fis.close();
		}
	}

	/**
	 * Computes the base dir (to dir) in which all chapters reside. The folder is names after the main xml file. May be
	 * overridden in subclasses.
	 */
	public String computeBasedir(File file, String linkPrefix) {
		return linkPrefix + file.getName().substring(0, file.getName().indexOf(".xml"));
	}

	/**
	 * Topic entry for TOC.
	 */
	protected static class Topic {
		String id;
		String label;
		List<Topic> subtopics = new ArrayList<>(5);
		/**
		 * Not used for Eclipse Help but required for html index generation. In the Eclipse help, we use a topic
		 * "Appendix".
		 */
		boolean isAppendix = false;
	}

	String title;
	List<Topic> maintopics = new ArrayList<>(5);
	List<Topic> appendix = new ArrayList<>(5);
	Stack<Topic> topics = new Stack<>();
	StringBuilder titleBuffer = new StringBuilder();
	boolean requireTitle = false;

	/**
	 * The folder in which all files are found.
	 */
	protected String dir;

	/**
	 * Visible for testing.
	 */
	EclipseHelpTOCGenerator() {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		if ("chapter".equals(qName)) {
			startChapter(attributes, false);
		} else if ("section".equals(qName)) {
			startSection(attributes);
		} else if ("book".equals(qName)) {
			startBook();
		} else if ("appendix".equals(qName)) {
			startChapter(attributes, true);
		}
	}

	private void startBook() {
		requireTitle = true;
	}

	private void startChapter(Attributes attributes, boolean isAppendix) {
		requireTitle = true;
		topics.clear();
		Topic topic = new Topic();
		topic.isAppendix = isAppendix;
		if (isAppendix) {
			appendix.add(topic);
		} else {
			maintopics.add(topic);

		}
		topics.push(topic);

		topic.id = attributes.getValue(XML_ID);

	}

	private void startSection(Attributes attributes) {
		requireTitle = true;
		Topic topic = new Topic();
		Topic parent = topics.peek();
		parent.subtopics.add(topic);
		topics.push(topic);

		topic.id = attributes.getValue(XML_ID);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);

		if ("chapter".equals(qName) || "appendix".equals(qName)) {
			endChapter();
		} else if ("section".equals(qName)) {
			endSection();
		} else if ("title".equals(qName)) {
			endTitle();
		}

	}

	private void endChapter() {
		topics.clear();
	}

	private void endSection() {
		topics.pop();
	}

	private void endTitle() {
		if (requireTitle) {
			if (topics.isEmpty()) {
				title = titleBuffer.toString().trim();
			} else {
				topics.peek().label = titleBuffer.toString().trim();
			}
			requireTitle = false;
			titleBuffer.setLength(0);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (requireTitle) {
			titleBuffer.append(ch, start, length);
		}
	}

	/**
	 * Emits the result (xml) file as string. Maybe overridden if other formats are to be emitted.
	 */
	protected String getResult() {
		StringBuilder out = new StringBuilder();

		out.append("<?xml version='1.0' encoding='utf-8' ?>\n");
		out.append("<toc topic=\"" + dir + "/index.html\" label=\"" + title + "\">");
		for (Topic topic : maintopics) {
			appendTopic(out, "\t", topic, getFilename(topic.label));
		}

		if (!appendix.isEmpty()) {
			out.append("\n<topic label=\"Appendix\">");
			char appendixNo = 'A';
			for (Topic topic : appendix) {
				appendTopic(out, "\t", topic, getFilename("Appendix " + appendixNo + ": " + topic.label));
				appendixNo++;
			}
			out.append("\n</topic>");
		}
		out.append("\n</toc>");
		return out.toString();
	}

	/**
	 * Returns the output file name of a specific topic. Maybe overridden if other formats are to be emitted.
	 */
	protected String getFilename(String label) {
		String name = "index";
		if (label != null) {
			name = ChunkHelper.extractFileNameFromTitle(label, 0, label.length());
		}
		return name + ".html";
	}

	/**
	 * Print out subtopics of a topic. Maybe overridden if other formats are to be emitted.
	 */
	protected void appendTopic(StringBuilder out, String indent, Topic topic, String fileName) {
		out.append("\n" + indent);
		String href = dir + "/" + fileName + "#" + topic.id;
		out.append("<topic href=\"" + href + "\" label=\"" + topic.label + "\">");
		for (Topic sub : topic.subtopics) {
			appendTopic(out, indent + "\t", sub, fileName);
		}
		if (!topic.subtopics.isEmpty()) {
			out.append("\n" + indent);
		}
		out.append("</topic>");
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