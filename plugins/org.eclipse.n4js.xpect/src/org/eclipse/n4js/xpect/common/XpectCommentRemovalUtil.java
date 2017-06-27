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
package org.eclipse.n4js.xpect.common;

/**
 */
// TODO fix naive comment lookup
/*
 * This is naive comment lookup, see http://ostermiller.org/findcomment.html
 */
public class XpectCommentRemovalUtil {

	/**
	 * remove XPECT comments from provided String
	 */
	public static String removeAllXpectComments(String text) {
		String res = "";
		res = replaceSingleLineXpect(text);
		res = replaceMultiLineXpect(res);
		return res;
	}

	private static String replaceSingleLineXpect(String text) {
		String startString = "// XPECT";
		String endString = "\n";
		return replaceXpect(text, startString, endString);
	}

	private static String replaceMultiLineXpect(String text) {
		String startString = "/* XPECT";
		String endString = "*/";
		return replaceXpect(text, startString, endString);
	}

	private static String replaceXpect(String text, String startString, String endString) {
		boolean replace = false;
		int start = text.indexOf(startString);
		if (start > -1) {
			int end = text.substring(start).indexOf(endString);
			if (end > -1) {
				end = start + end + endString.length();
				text = text.substring(0, start) + text.substring(end);
				replace = true;
			}
		}
		if (replace) {
			return replaceXpect(text, startString, endString);
		}
		return text;
	}
}
