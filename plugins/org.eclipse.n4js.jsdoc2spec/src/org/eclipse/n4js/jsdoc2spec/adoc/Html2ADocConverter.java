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
package org.eclipse.n4js.jsdoc2spec.adoc;

import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.NL;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.xtext.xbase.lib.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

/**
 * Converts basic HTML tags to AsciiDoc.<br/>
 * Note: To escape code symbols like '~', the macro '+++' is used. A configurable alternative is the pass macro, e.g.:
 * <code>pass:q[*_Mytest_*]</code>.
 */
public class Html2ADocConverter {
	private static final Map<String, String> htmlElementsToADoc = new HashMap<>();
	private static final Map<String, String> entitiesToADoc = new HashMap<>();
	private static final char ENTITY_START = '&';
	private static final char TAG_START = '<';
	private static final char TAG_END = '>';
	private static final char QUOTE = '"';
	private static final char TAG_END_MARK = '/';
	private static final String VISIT_REGION = "«";

	private static DocumentBuilder XMLBUILDER = initBuilder();
	private static String XMLPROLOG = "<?xml version=\"1.0\"?>";

	static {
		htmlElementsToADoc.put("tex", VISIT_REGION);
		htmlElementsToADoc.put("/tex", "--");
		htmlElementsToADoc.put("p", NL + NL);
		htmlElementsToADoc.put("/p", NL + NL);
		htmlElementsToADoc.put("br", NL + NL);
		htmlElementsToADoc.put("/br", NL + NL);
		htmlElementsToADoc.put("ol", NL + NL);
		htmlElementsToADoc.put("/ol", "");
		htmlElementsToADoc.put("ul", NL + NL);
		htmlElementsToADoc.put("/ul", "");
		htmlElementsToADoc.put("li", "* ");
		htmlElementsToADoc.put("/li", "");
		htmlElementsToADoc.put("dl", NL + "...." + NL);
		htmlElementsToADoc.put("/dl", NL + "...." + NL);
		htmlElementsToADoc.put("dt", "* ");
		htmlElementsToADoc.put("/dt", "");
		htmlElementsToADoc.put("dd", "");
		htmlElementsToADoc.put("/dd", "");
		htmlElementsToADoc.put("em", VISIT_REGION);
		htmlElementsToADoc.put("/em", "##");
		htmlElementsToADoc.put("b", VISIT_REGION);
		htmlElementsToADoc.put("/b", "**");
		htmlElementsToADoc.put("i", VISIT_REGION);
		htmlElementsToADoc.put("/i", "__");
		htmlElementsToADoc.put("pre", VISIT_REGION);
		htmlElementsToADoc.put("/pre", "++");
		htmlElementsToADoc.put("code", VISIT_REGION);
		htmlElementsToADoc.put("/code", "--``");
		htmlElementsToADoc.put("cite", VISIT_REGION);
		htmlElementsToADoc.put("/cite", "");
		htmlElementsToADoc.put("quote", VISIT_REGION);
		htmlElementsToADoc.put("/quote", "__");

		entitiesToADoc.put("&lt;", "<");
		entitiesToADoc.put("&gt;", ">");
		entitiesToADoc.put("&quot;", "\"");
		entitiesToADoc.put("&amp;", "&");
	}

	private static DocumentBuilder initBuilder() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException exc) {
			throw new RuntimeException(exc);
		}
	}

	/**
	 * Use this method when you use non-code as a label. This method will escape closing square brackets, which would
	 * otherwise close the label.
	 */
	public String escADocInLabel(String s) {
		String newS = s.replaceAll("[^\\\\]]", "\\\\]");
		return newS;
	}

	/**
	 * Use this method to markup code as code. Escaping special characters like '~' is also done.
	 */
	public String passThenMonospace(CharSequence cs) {
		return monospace(pass(cs));
	}

	/**
	 * Use this method to markup code as code. Escaping special characters like '~' is also done.
	 */
	public String monospace(CharSequence cs) {
		return "``" + cs + "``";
	}

	/**
	 * Use this method to escape code without the monospace markup.
	 */
	public String pass(CharSequence cs) {
		if (cs == null)
			return null;
		return pass(cs.toString());
	}

	private String pass(String s) {
		return "++" + s + "++";
	}

	/**
	 * Transforms html to adoc. In case there are code blocks or code inline regions in the html, these sections are
	 * also escaped properly.
	 */
	public CharSequence transformHTML(CharSequence html) {
		StringBuilder strb = new StringBuilder();
		transformHTML(html, 0, strb, new LinkedList<String>(), "");
		return strb;
	}

	private int transformHTML(CharSequence html, int startRegionIdx, StringBuilder strb, List<String> tagStack,
			String myTagRegion) {

		int length = html.length();
		int i = startRegionIdx;

		/**
		 * Terminology:
		 *
		 * <pre>
		 * Tag open:   <XY>
		 * Tag close: </XY>
		 * Tag start: <...
		 * Tag end:    ...>
		 * </pre>
		 */

		while (i < length) {
			char c = html.charAt(i);

			switch (c) {
			case ENTITY_START: {
				// note: Xtend has problems detecting chars, cf. https://bugs.eclipse.org/bugs/show_bug.cgi?id=408764
				i = replaceEntities(html, strb, i, c);
				break;
			}

			case TAG_START: {
				boolean isCloseTag = false;
				int tagStart = i;

				// skip empty elements
				while (i + 1 < length && Character.isWhitespace(html.charAt(i + 1))) {
					i++;
				}

				// is end tag?
				if (i + 1 < length && html.charAt(i + 1) == TAG_END_MARK) {
					isCloseTag = true;
					i++;
				}

				// get element and its end position
				Pair<Element, Integer> elementWithOffset = parseHTMLElement(html, i);
				int tagEnd = elementWithOffset.getValue();
				i = tagEnd - 1;
				Element element = elementWithOffset.getKey();

				// get html tag name and corresponding adoc string
				String adocString = null;
				String elementName = null;
				if (element != null) {
					elementName = element.getNodeName();
					String tagName = elementName;
					if (isCloseTag)
						tagName = "/" + tagName;

					adocString = htmlElementsToADoc.get(tagName);
				}

				// check if closing this region
				if (isCloseTag && elementName != null) {
					boolean isClosingMyRegion = myTagRegion.equals(elementName);
					if (isClosingMyRegion) {
						return i;
					}
				}

				// append text representing the html tag (and region)
				if (adocString == null) {
					strb.append(html.subSequence(tagStart, tagEnd));

				} else if (adocString == VISIT_REGION) {
					String[] tags = getADocTags(element, html, tagEnd);
					strb.append(tags[0]);
					tagStack.add(0, getADocTagName(tags[0]));
					i = transformHTML(html, tagEnd, strb, tagStack, elementName);
					tagStack.remove(0);
					strb.append(tags[1]);

				} else {
					strb.append(adocString);
				}

				break;
			}

			default:
				strb.append(c);
			}

			i++;
		}

		return i;
	}

	private String getADocTagName(String openTag) {
		String adocTagName = openTag.replace("\\[(\\w*),.*\\]", "$1");
		if (adocTagName == null || adocTagName.length() == 0)
			adocTagName = openTag;
		return adocTagName;
	}

	private String[] getADocTags(Element element, CharSequence html, int regionStart) {
		char charRegionStart = html.charAt(regionStart);
		boolean lastIsNewline = charRegionStart == '\n';

		switch (element.getTagName()) {
		case "quote": {
			String author = element.getAttribute("author");
			boolean hasAuthor = author != null && author.length() > 0;
			if (lastIsNewline) {
				if (hasAuthor) {
					return new String[] { "\n[verse," + escADocInLabel(author) + "]" + NL + "--", NL + "--" + NL };
				} else {
					return new String[] { "\n[verse]" + NL + "--", NL + "--" + NL };
				}
			} else {
				if (hasAuthor) {
					return new String[] { "__", "__ [" + escADocInLabel(author) + "]" };
				} else {
					return new String[] { "__", "__" };
				}
			}
		}

		case "cite": {
			String title = element.getAttribute("title");
			boolean hasTitle = title != null && title.length() > 0;
			if (hasTitle) {
				return new String[] { "cite:[", "(" + escADocInLabel(title) + ")]" };
			} else {
				return new String[] { "cite:[", "]" };
			}
		}

		case "code": {
			String clazz = element.getAttribute("class");
			boolean hasClass = clazz != null && clazz.length() > 0;
			if (lastIsNewline) {
				if (hasClass) {
					return new String[] { "\n[source," + clazz + "]" + NL + "--", NL + "--" + NL };
				} else {
					return new String[] { "\n[source,n4js]" + NL + "--", NL + "--" + NL };
				}
			} else {
				return new String[] { "``++", "++``" };
			}
		}

		case "tex": {
			// tex is not supported anymore
			return new String[] { "", "" };
		}

		case "pre": {
			String clazz = element.getAttribute("class");
			boolean hasClass = clazz != null && clazz.length() > 0;

			if (lastIsNewline) {
				if (hasClass) {
					return new String[] { "\n[source," + clazz + "]" + NL + "--", NL + "--" + NL };
				} else {
					return new String[] { "\n[source]" + NL + "--", NL + "--" + NL };
				}
			} else {
				return new String[] { "++", "++" };
			}
		}

		case "em": {
			return new String[] { "##", "##" };
		}

		case "b": {
			return new String[] { "**", "**" };
		}

		case "i": {
			return new String[] { "__", "__" };
		}
		}

		return null;
	}

	/**
	 * Replaces entities like &gt to >.
	 */
	private int replaceEntities(CharSequence html, StringBuilder s, int i, char c) {
		int offset = i;
		String entity = find(html, entitiesToADoc.keySet(), offset);
		if (entity != null) {
			i += entity.length() - 1;
			s.append(entitiesToADoc.get(entity));
		} else {
			s.append(c);
		}
		return i;
	}

	/**
	 * Finds element in searchStrings that matches the substring in html at given offset; the first character of the
	 * searchString is ignored as it is already tested by the caller (which happens to be only
	 * {@link #transformHTML(CharSequence)}. Case is ignored.
	 */
	private static Pair<Element, Integer> parseHTMLElement(CharSequence html, int offset) {
		int maxLength = html.length();
		int pos = offset + 1; // skip "<" (or "/" of "</")
		boolean inString = false;
		while (pos < maxLength && Character.isWhitespace(html.charAt(pos))) {
			pos++;
		}
		int tagNameStart = pos;
		while (pos < maxLength && Character.isAlphabetic(html.charAt(pos))) {
			pos++;
		}
		CharSequence tagName = html.subSequence(tagNameStart, pos);
		if (!htmlElementsToADoc.containsKey(tagName)) { // only further process known HTML tags
			return Pair.of(null, pos);
		}

		char c = ' ';
		while (pos < maxLength && !(c == TAG_END && !inString)) {
			c = html.charAt(pos);
			if (c == QUOTE) {
				inString = !inString;
			}
			pos++;
		}

		int endOffset = pos;
		if (c == TAG_END) {
			pos--;
		} // back to TAG_END (or max length)
		if (pos - offset < 1) {
			return Pair.of(null, endOffset);
		}
		String tagContent = html.subSequence(offset + 1, pos).toString();

		String strTagWithAttributes = "<" + tagContent +
				((tagContent.endsWith("/")) ? ">" : "/>");
		String xmlFragment = XMLPROLOG + strTagWithAttributes;
		try {
			Document doc = XMLBUILDER.parse(new InputSource(new ByteArrayInputStream(xmlFragment.getBytes("utf-8"))));
			Element element = doc.getDocumentElement();
			return Pair.of(element, endOffset);
		} catch (Exception exc) {
			// System.out.println("Error parsing HTML '" + strTagWithAttributes + "': " + exc.toString);
			// throw new IllegalArgumentException("Error parsing HTML '" + strTagWithAttributes + "': " + exc.toString,
			// exc)
		}
		return Pair.of(null, endOffset);
	}

	/**
	 * Finds element in searchStrings that matches the substring in html at given offset; the first character of the
	 * searchString is ignored as it is already tested by the caller (which happens to be only
	 * {@link #transformHTML(CharSequence)}. Case is ignored.
	 */
	private static String find(CharSequence html, Iterable<String> searchStrings, int offset) {
		int length = html.length();

		for (String s : searchStrings) {
			int k = s.length();
			if (k + offset <= length) {
				do {
					k--;
				} while (k >= 0 && Character.toLowerCase(html.charAt(offset + k)) == s.charAt(k));
				if (k < 0)
					return s;
			}
		}
		return null;
	}
}
