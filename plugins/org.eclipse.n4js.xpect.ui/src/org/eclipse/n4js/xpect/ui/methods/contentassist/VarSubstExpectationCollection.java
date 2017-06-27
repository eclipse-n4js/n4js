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
package org.eclipse.n4js.xpect.ui.methods.contentassist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xpect.expectation.impl.ExpectationCollection;

import com.google.common.base.Preconditions;

import org.eclipse.n4js.xpect.config.ValueList;
import org.eclipse.n4js.xpect.config.VarDef;
import org.eclipse.n4js.xpect.config.XpEnvironmentData;

/**
 */
@SuppressWarnings("restriction")
class VarSubstExpectationCollection extends ExpectationCollection {

	final static Pattern pVar = Pattern.compile("<\\$(\\p{Alpha}*)>");
	private final XpEnvironmentData runData;

	public VarSubstExpectationCollection(XpEnvironmentData data) {
		Preconditions.checkNotNull(data);
		this.runData = data;
	}

	protected List<ExpectationItem> createItems(String item, boolean negated, boolean quoted2, boolean escaped) {
		Matcher m = pVar.matcher(item);
		boolean foundMatch = m.find();
		if (foundMatch) {
			// get variablename
			String name = m.group(1);
			VarDef varDef = runData.getVar(name);
			ArrayList<ExpectationItem> ret = new ArrayList<>();

			for (ValueList mem : varDef.mlist) {
				for (String s : mem.evaluate(runData.getResourceUnderTest())) {
					ret.add(super.createItem(s, negated, quoted2, escaped));
				}
			}

			return ret;
		} else {
			return Arrays.asList(super.createItem(item, negated, quoted2, escaped));
		}
	}

	@Override
	public void init(String expectation) {
		items = createCollection();
		boolean esc = false, escaped = true, quote2 = false, quoted2 = false, neg = false;
		StringBuilder item = new StringBuilder();
		StringBuilder ws = new StringBuilder();
		for (int i = 0; i < expectation.length(); i++) {
			char c = expectation.charAt(i);
			if (!esc) {
				if (!quote2) {
					if (c == separator) {
						if (item.length() > 0)
							items.addAll(createItems(item.toString(), neg, quoted2, escaped));
						neg = quoted2 = escaped = false;
						item = new StringBuilder();
						ws = new StringBuilder();
						continue;
					} else if (Character.isWhitespace(c)) {
						ws.append(c);
						continue;
					} else if (c == '!' && item.length() == 0) {
						neg = true;
						continue;
					}
				}
				if (this.quoted && c == this.quote) {
					if (quote2)
						item.append(ws);
					ws = new StringBuilder();
					quote2 = !quote2;
					quoted2 = true;
					continue;
				} else if (c == '\\') {
					escaped = esc = true;
					continue;
				}
			} else {
				esc = false;
				switch (c) {
				case 'n':
					c = '\n';
					break;
				case 'r':
					c = '\r';
					break;
				}
			}
			if (ws.length() > 0) {
				if (item.length() > 0)
					item.append(ws);
				ws = new StringBuilder();
			}
			item.append(c);
		}
		if (item.length() > 0)
			items.addAll(createItems(item.toString(), neg, quoted2, escaped));
	}

}
