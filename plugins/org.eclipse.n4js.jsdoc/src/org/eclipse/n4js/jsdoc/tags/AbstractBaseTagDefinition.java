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
package org.eclipse.n4js.jsdoc.tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.jsdoc.ITagDefinition;
import org.eclipse.n4js.jsdoc.JSDocToken;
import org.eclipse.n4js.jsdoc.JSDocUtils;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;

/**
 * Base class for implementations of {@link ITagDefinition}s
 */
public abstract class AbstractBaseTagDefinition implements ITagDefinition {

	/**
	 * Factory for doclet DOM elements
	 */
	protected final static DomFactory DOM = DomFactory.eINSTANCE;

	/**
	 * List of titles tag can have. Used to delegate parsing given part of comment to proper parser.
	 */
	protected List<String> titles;

	/**
	 * setter for {@link AbstractBaseTagDefinition#titles}. Creates unmodifiable list.
	 */
	public void setTitles(String... titles) {
		this.titles = Collections.unmodifiableList(new ArrayList<>(Arrays.asList(titles)));
	}

	@Override
	public String getTitle() {
		return titles.get(0);
	}

	@Override
	public List<String> getAllTitles() {
		return titles;
	}

	@Override
	public void validate() {
		// todo -- including error handling
	}

	/**
	 * Convenience method.
	 */
	protected TagValue addValue(Tag tag, String key, ContentNode contents) {
		return addValue(tag, key, Collections.singleton(contents));
	}

	/**
	 * Creates {@link TagValue} that will hold provided contents. It will be stored in provided {@link Tag} values under
	 * provided key.
	 *
	 * @param tag
	 *            To which we add value
	 * @param key
	 *            That will associated with the value
	 * @param contents
	 *            That will be added as value
	 * @return Created {@link TagValue} or null
	 */
	protected TagValue addValue(Tag tag, String key, Collection<? extends ContentNode> contents) {
		TagValue tagValue = DOM.createTagValue();
		tagValue.setKey(key);
		tagValue.setBegin(JSDocUtils.getBegin(contents));
		tagValue.setEnd(JSDocUtils.getEnd(contents));
		tagValue.getContents().addAll(contents);
		tag.getValues().add(tagValue);
		return tagValue;
	}

	/**
	 * Convenience method.
	 */
	protected TagValue addValue(Tag tag, String key, JSDocToken token) {
		Text text = DOM.createText();
		text.setText(token.token);
		text.setBegin(token.start);
		text.setEnd(token.end);
		return addValue(tag, key, text);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + getTitle();
	}

}
