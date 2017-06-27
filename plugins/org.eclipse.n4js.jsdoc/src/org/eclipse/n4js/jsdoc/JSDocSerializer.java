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
package org.eclipse.n4js.jsdoc;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.jsdoc.dom.Composite;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc.dom.FullTypeReference;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.JSDocNode;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.Literal;
import org.eclipse.n4js.jsdoc.dom.SimpleTypeReference;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.dom.util.DomSwitch;

/**
 * Reproduces comment string from doclet, basically for debugging and testing purposes. This serializer tries to create
 * the original doclet as close as possible.
 */
public class JSDocSerializer extends DomSwitch<Boolean> {

	/**
	 * new line
	 */
	public final static String NL = "\n * ";

	/**
	 * prefix of new line in multi-line comments (does not apply to first&last markers)
	 */
	public final static String NL_PREFIX = " * ";

	StringBuffer strb = new StringBuffer();

	/**
	 * serialize given {@link EObject} as string representing JSDoc
	 */
	public static String toJSDocString(EObject obj) {
		JSDocSerializer serializer = new JSDocSerializer();
		if (obj == null) {
			return "";
		}
		serializer.doSwitch(obj);
		return serializer.strb.toString();
	}

	/**
	 * Serializes the content of the given composite.
	 */
	public static String contentAsString(Composite composite) {
		JSDocSerializer serializer = new JSDocSerializer();
		serializer.appendContents(composite.getContents());
		return serializer.strb.toString();
	}

	@Override
	public Boolean caseDoclet(Doclet doclet) {
		strb.append("/**");

		if (!doclet.getContents().isEmpty()) {

			strb.append(NL);
		}

		appendContents(doclet.getContents());
		for (LineTag tag : doclet.getLineTags()) {
			doSwitch(tag);
		}
		if (!doclet.getLineTags().isEmpty()) {
			strb.append("\n");
		}
		strb.append(" */");
		return false;
	}

	@Override
	public Boolean caseLiteral(Literal object) {
		strb.append(object.getValue());
		return false;
	}

	@Override
	public Boolean caseSimpleTypeReference(SimpleTypeReference object) {
		strb.append(object.getTypeName());
		return false;
	}

	@Override
	public Boolean caseFullTypeReference(FullTypeReference object) {
		strb.append(object.getModuleName()).append(".");
		caseSimpleTypeReference(object);
		return false;
	}

	@Override
	public Boolean caseFullMemberReference(FullMemberReference object) {
		caseFullTypeReference(object);
		if (!object.getMemberName().isEmpty()) {
			if (object.isStaticMember()) {
				strb.append('#');
			} else {
				strb.append('.');
			}
			strb.append(object.getMemberName());
		}
		return false;
	}

	@Override
	public Boolean caseComposite(Composite object) {
		appendContents(object.getContents());
		return false;
	}

	private void appendContents(EList<ContentNode> contents) {

		for (ContentNode content : contents) {
			doSwitch(content);
			if (lastChar() == '\n') {
				strb.append(NL_PREFIX);
			}
		}

	}

	char lastChar() {
		if (strb.length() > 0)
			return strb.charAt(strb.length() - 1);
		return 0;
	}

	@Override
	public Boolean caseText(Text text) {
		strb.append(text.getText().replaceAll("\\r?\\n", NL));
		return false;
	}

	@Override
	public Boolean caseLineTag(LineTag lineTag) {
		strb.append(NL).append(JSDocCharScanner.TAG_START).append(lineTag.getTitle().getTitle());

		if (!lineTag.getValues().isEmpty()) {
			strb.append(" ");
		}

		for (JSDocNode node : lineTag.getValues()) {
			doSwitch(node);
		}
		return false;
	}

	@Override
	public Boolean caseTagTitle(TagTitle object) {
		strb.append(object.getTitle());
		return false;
	}

	@Override
	public Boolean caseInlineTag(InlineTag object) {
		strb.append("{").append(JSDocCharScanner.TAG_START).append(object.getTitle().getTitle());
		for (TagValue tagValue : object.getValues()) {
			doSwitch(tagValue);
		}
		strb.append("}");
		return false;

	}

}
