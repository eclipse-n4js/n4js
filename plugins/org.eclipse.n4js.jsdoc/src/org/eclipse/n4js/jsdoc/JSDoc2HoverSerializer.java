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
package org.eclipse.n4js.jsdoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.jsdoc.dom.Composite;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc.dom.FullTypeReference;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.Literal;
import org.eclipse.n4js.jsdoc.dom.SimpleTypeReference;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.dom.util.DomSwitch;
import org.eclipse.xtext.util.Strings;

/**
 * Creates a HTML version of the JSDoc string to be shown in the hover. It uses the {@link MD2HTMLConvertingBuilder} to
 * convert markdown to HTML.
 * <p>
 * Tags are added as items of a definition list with the tag name as title.
 */
public class JSDoc2HoverSerializer extends DomSwitch<Boolean> {

	/**
	 * Used internally for registering handlers for different types of tags.
	 */
	private abstract static class TagHandler {
		@SuppressWarnings("unused")
		void open(ITagDefinition tagDef, JSDoc2HoverSerializer serializer) {
			// nothing per default
		}

		void content(LineTag lineTag, JSDoc2HoverSerializer serializer, boolean isFirstLine) {
			if (!isFirstLine) {
				serializer.md2HtmlBuilder.append("<p>");
			}
			for (TagValue val : lineTag.getValues()) {
				serializer.appendContents(val.getContents());
			}
		}

		@SuppressWarnings("unused")
		void close(ITagDefinition tagDef, JSDoc2HoverSerializer serializer) {
			// nothing per default
		}
	}

	private static TagHandler TAG_IGNORE = new TagHandler() {
		@Override
		void content(LineTag lineTag, JSDoc2HoverSerializer serializer, boolean isFirstLine) {
			// ignore
		}
	};

	/**
	 * This is the default handler if no handler is registered for a tag.
	 */
	private static TagHandler TAG_SECTION = new TagHandler() {
		@Override
		void open(ITagDefinition tagDef, JSDoc2HoverSerializer serializer) {
			serializer.md2HtmlBuilder.append("\n<dt>").append(Strings.toFirstUpper(tagDef.getTitle()))
					.append(":</dt>\n<dd>");
		}
	};

	/**
	 * Creates an unordered list of all tags with the same definition.
	 */
	private static TagHandler TAG_LIST = new TagHandler() {
		@Override
		void open(ITagDefinition tagDef, JSDoc2HoverSerializer serializer) {
			serializer.md2HtmlBuilder.append("\n<dt>").append(Strings.toFirstUpper(tagDef.getTitle()))
					.append(":</dt>\n<dd>");
			serializer.md2HtmlBuilder.append("\n<ul>");
		}

		@Override
		void content(LineTag lineTag, JSDoc2HoverSerializer serializer, boolean isFirstLine) {
			serializer.md2HtmlBuilder.append("<li>");
			for (TagValue val : lineTag.getValues()) {
				serializer.appendContents(val.getContents());
			}
			serializer.md2HtmlBuilder.append("</li>");
		}

		@Override
		void close(ITagDefinition tagDef, JSDoc2HoverSerializer serializer) {
			serializer.md2HtmlBuilder.append("</ul></dd>");
		}
	};

	static Map<ITagDefinition, TagHandler> LINETAG_HANDLERS;

	static {
		LINETAG_HANDLERS = new HashMap<>();
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_SPEC, TAG_SECTION);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_AUTHOR, TAG_SECTION);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_TODO, TAG_LIST);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_PARAM, TAG_LIST);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_RETURN, TAG_SECTION);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_SPECFROMDESCR, TAG_IGNORE);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_TASK, TAG_IGNORE);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_REQID, TAG_IGNORE);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_API_NOTE, TAG_SECTION);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_API_STATE, TAG_IGNORE);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_TESTEE, TAG_IGNORE);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_TESTEEFROMTYPE, TAG_IGNORE);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_TESTEETYPE, TAG_IGNORE);
		LINETAG_HANDLERS.put(N4JSDocletParser.TAG_TESTEEMEMBER, TAG_IGNORE);
	}

	/**
	 * new line
	 *
	 * @author x
	 */
	public final static String NL = "\n * ";

	/**
	 * prefix of new line in multi-line comments (does not apply to first&last markers)
	 */
	public final static String NL_PREFIX = " * ";

	MD2HTMLConvertingBuilder md2HtmlBuilder = new MD2HTMLConvertingBuilder();

	/**
	 * serialize given {@link EObject} as string representing JSDoc
	 */
	public static String toJSDocString(EObject obj) {
		JSDoc2HoverSerializer serializer = new JSDoc2HoverSerializer();
		if (obj == null) {
			return "";
		}
		serializer.doSwitch(obj);
		return serializer.md2HtmlBuilder.done();
	}

	@Override
	public Boolean caseDoclet(Doclet doclet) {
		md2HtmlBuilder.resetMarkdownConverter();
		appendContents(doclet.getContents());

		// Reset md2HtmlBuilder to enforce all open blocks to closes, so that we have a defined status when
		// handling the line tags.
		md2HtmlBuilder.resetMarkdownConverter();

		Map<ITagDefinition, List<LineTag>> groupedLineTags = new HashMap<>();
		for (LineTag lineTag : doclet.getLineTags()) {
			ITagDefinition tagDef = lineTag.getTagDefinition();
			List<LineTag> group = groupedLineTags.get(tagDef);
			if (group == null) {
				group = new ArrayList<>(5);
				groupedLineTags.put(tagDef, group);
			}
			group.add(lineTag);
		}
		if (!groupedLineTags.isEmpty()) {
			md2HtmlBuilder.append("\n<dl>");
			for (ITagDefinition tagDef : N4JSDocletParser.N4JS_LINE_TAGS) {
				TagHandler tagHandler = LINETAG_HANDLERS.get(tagDef);
				if (tagHandler == null) {
					tagHandler = TAG_SECTION; // default tag handler
				}
				if (tagHandler != null) {
					List<LineTag> lineTags = groupedLineTags.get(tagDef);
					handleLineTags(tagDef, tagHandler, lineTags);
				}
			}
			md2HtmlBuilder.append("\n</dl>");
		}
		return false;
	}

	private void handleLineTags(ITagDefinition tagDef, TagHandler tagHandler, List<LineTag> lineTags) {
		if (lineTags != null && !lineTags.isEmpty()) {
			tagHandler.open(tagDef, this);
			boolean first = true;
			for (LineTag lineTag : lineTags) {
				md2HtmlBuilder.resetMarkdownConverter();
				tagHandler.content(lineTag, this, first);
				first = false;
			}
			tagHandler.close(tagDef, this);
		}
	}

	@Override
	public Boolean caseLiteral(Literal object) {
		md2HtmlBuilder.append(object.getValue());
		return false;
	}

	@Override
	public Boolean caseSimpleTypeReference(SimpleTypeReference object) {
		md2HtmlBuilder.append("<code>").append(object.getTypeName()).append("</code>");
		return false;
	}

	@Override
	public Boolean caseFullTypeReference(FullTypeReference object) {
		md2HtmlBuilder.append("<code>");
		md2HtmlBuilder.append(object.getModuleName()).append(".");
		md2HtmlBuilder.append(object.getTypeName());
		md2HtmlBuilder.append("</code>");
		return false;
	}

	@Override
	public Boolean caseFullMemberReference(FullMemberReference object) {
		md2HtmlBuilder.append("<code>");
		md2HtmlBuilder.append(object.getModuleName()).append(".");
		md2HtmlBuilder.append(object.getTypeName());
		if (!object.getMemberName().isEmpty()) {
			if (object.isStaticMember()) {
				md2HtmlBuilder.append("#");
			} else {
				md2HtmlBuilder.append("'.");
			}
			md2HtmlBuilder.append(object.getMemberName());
		}
		md2HtmlBuilder.append("</code>");
		return false;
	}

	@Override
	public Boolean caseComposite(Composite object) {
		appendContents(object.getContents());
		return false;
	}

	private void appendContents(EList<ContentNode> contents) {
		if (contents.isEmpty()) {
			return;
		}

		for (ContentNode content : contents) {
			doSwitch(content);
		}

	}

	@Override
	public Boolean caseText(Text text) {
		md2HtmlBuilder.convertAndAppend(text.getText());
		return false;
	}

	@Override
	public Boolean caseLineTag(LineTag lineTag) {
		// handled in caseDoclet
		return false;
	}

	@Override
	public Boolean caseTagTitle(TagTitle object) {
		return false;
	}

	@Override
	public Boolean caseInlineTag(InlineTag tag) {
		// strb.append("{").append(JSDocCharScanner.TAG_START).append(object.getTitle().getTitle());
		String htag = "i";
		if (tag.getTagDefinition() == N4JSDocletParser.TAG_CODE) {
			htag = "code";
		}

		md2HtmlBuilder.append("<" + htag + ">");
		for (TagValue tagValue : tag.getValues()) {
			doSwitch(tagValue);
		}
		md2HtmlBuilder.append("</" + htag + ">");
		return false;

	}

}
