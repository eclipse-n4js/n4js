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

import org.eclipse.n4js.jsdoc.tags.AbstractInlineTagDefinition;
import org.eclipse.n4js.jsdoc.tags.AbstractLineTagDefinition;
import org.eclipse.n4js.jsdoc.tags.DefaultInlineTagDefinition;
import org.eclipse.n4js.jsdoc.tags.DefaultLineTagDefinition;
import org.eclipse.n4js.jsdoc.tags.LineTagWithFullMemberReference;
import org.eclipse.n4js.jsdoc.tags.LineTagWithFullTypeReference;
import org.eclipse.n4js.jsdoc.tags.LineTagWithSimpleTextDefinition;

/**
 * Simple parser, only defined to parse spec related comments (cf. IDE-1356).
 */
public class N4JSDocletParser extends DocletParser {

	/**
	 * Code inside a description.
	 */
	public final static DefaultInlineTagDefinition TAG_CODE = new DefaultInlineTagDefinition("code");
	/**
	 * Link inside a description.
	 */
	public final static DefaultInlineTagDefinition TAG_LINK = new DefaultInlineTagDefinition("link");

	/**
	 * Todo marker (cf. IDE-2163)
	 */
	public final static LineTagWithSimpleTextDefinition TAG_TODO = new LineTagWithSimpleTextDefinition("todo");

	/**
	 * See marker, introduced for MDN link
	 */
	public final static LineTagWithSimpleTextDefinition TAG_SEE = new LineTagWithSimpleTextDefinition("see");

	/**
	 * Standard inline tags.
	 */
	final static AbstractInlineTagDefinition[] DESCRIPTION_INLINE_TAGS = { TAG_CODE, TAG_LINK };
	/**
	 * Dicitionary for standard description.
	 */
	final static TagDictionary<AbstractInlineTagDefinition> DESCRIPTION_DICT = new TagDictionary<>(
			DESCRIPTION_INLINE_TAGS);

	// Standard ************************************

	/**
	 * Author line.
	 */
	public final static LineTagWithSimpleTextDefinition TAG_AUTHOR = new LineTagWithSimpleTextDefinition("author");
	/**
	 * Parameter line (for methods).
	 */
	public final static DefaultLineTagDefinition TAG_PARAM = new DefaultLineTagDefinition("param", DESCRIPTION_DICT);
	/**
	 * Return line (for methods).
	 */
	public final static DefaultLineTagDefinition TAG_RETURN = new DefaultLineTagDefinition("return", DESCRIPTION_DICT);

	// Test Related ************************************

	/**
	 * Link to tested type or member.
	 */
	public final static LineTagWithFullMemberReference TAG_TESTEE = new LineTagWithFullMemberReference(
			"testee");

	/**
	 * Copy real tested type from test type doclet.
	 */
	public final static AbstractLineTagDefinition TAG_TESTEEFROMTYPE = new DefaultLineTagDefinition(
			"testeeFromType", TagDictionary.emptyDict());

	/**
	 * Link to tested type or member.
	 */
	public final static LineTagWithFullTypeReference TAG_TESTEETYPE = new LineTagWithFullTypeReference(
			"testeeType");

	/**
	 * Link to tested type or member.
	 */
	public final static LineTagWithSimpleTextDefinition TAG_TESTEEMEMBER = new LineTagWithSimpleTextDefinition(
			"testeeMember");

	// API related ************************************

	/**
	 * Tag for remarks to be shown in the API / implementation compare view. The tag may be used both on API and
	 * implementation side.
	 */
	public final static LineTagWithSimpleTextDefinition TAG_API_NOTE = new LineTagWithSimpleTextDefinition("apiNote");

	/**
	 * Tag for describing the state of the API
	 */
	public final static LineTagWithSimpleTextDefinition TAG_API_STATE = new LineTagWithSimpleTextDefinition("apiState");

	// Spec ************************************

	/**
	 * Documentation only for specification generation.
	 */
	public final static DefaultLineTagDefinition TAG_SPEC = new DefaultLineTagDefinition("spec", DESCRIPTION_DICT);
	/**
	 * Copy specification documentation from description.
	 */
	public final static AbstractLineTagDefinition TAG_SPECFROMDESCR = new DefaultLineTagDefinition(
			"specFromDescription", TagDictionary.emptyDict());

	/**
	 * Link to Jira Task
	 */
	public final static LineTagWithSimpleTextDefinition TAG_TASK = new LineTagWithSimpleTextDefinition("task");
	/**
	 * Requirement ID
	 */
	public final static LineTagWithSimpleTextDefinition TAG_REQID = new LineTagWithSimpleTextDefinition("reqid");

	/**
	 * All N4JS tag comments. The order of these tags also define the default order in which the
	 * {@link JSDoc2HoverSerializer} orders them.
	 */
	final static AbstractLineTagDefinition[] N4JS_LINE_TAGS = {
			TAG_SPEC, TAG_PARAM, TAG_RETURN, TAG_AUTHOR, TAG_TODO, TAG_SEE, TAG_TASK, TAG_REQID,
			TAG_API_NOTE,
			TAG_API_STATE, TAG_SPECFROMDESCR,
			TAG_TESTEE, TAG_TESTEEFROMTYPE, TAG_TESTEETYPE, TAG_TESTEEMEMBER
	};

	/**
	 * Creates N4JSDoclet parser with default N4JS line tags.
	 */
	public N4JSDocletParser() {
		super(new TagDictionary<>(N4JS_LINE_TAGS), DESCRIPTION_DICT);
	}

}
