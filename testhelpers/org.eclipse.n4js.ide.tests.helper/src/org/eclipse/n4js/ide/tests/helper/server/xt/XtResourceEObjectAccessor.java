/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.lsp4j.Position;
import org.eclipse.n4js.ide.tests.helper.server.AbstractStructuredIdeTest;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Utility to retrieve offset, position, or {@link IEObjectCoveringRegion} from a resource
 */
public class XtResourceEObjectAccessor {
	static final String CURSOR = AbstractStructuredIdeTest.CURSOR_SYMBOL;

	final XtFileData xtData;
	final XtextResource resource;

	/** Constructor */
	public XtResourceEObjectAccessor(XtFileData xtData, XtextResource resource) {
		this.xtData = xtData;
		this.resource = resource;
	}

	/** @return {@link EObject} with offset for a given offset in this resource */
	public IEObjectCoveringRegion getObjectCoveringRegion(int offset) {
		EObject eObject = XtResourceUtil.findEObject(resource, offset, 0);
		EStructuralFeature structuralFeature = XtResourceUtil.findStructuralFeature(resource, offset);
		return new EObjectCoveringRegion(resource, eObject, offset, structuralFeature);
	}

	/**
	 * Searches for an {@link EObject} starting from the given offset. Skips white spaces. Iff not null, the search
	 * starts behind the optional location string.
	 *
	 * @param optionalLocationStr
	 *            can be null
	 * @return {@link EObject} with offset for a given offset in this resource.
	 */
	public IEObjectCoveringRegion getObjectCoveringRegion(int searchFromOffset, String optionalLocationStr) {
		int offset = getOffset(searchFromOffset, optionalLocationStr, false);
		if (offset < 0) {
			return null;
		}
		int length = optionalLocationStr == null ? 0 : lengthWithoutCursor(optionalLocationStr);
		EObject eObject = XtResourceUtil.findEObject(resource, offset, length);
		EStructuralFeature structuralFeature = XtResourceUtil.findStructuralFeature(resource, offset);
		return new EObjectCoveringRegion(resource, eObject, offset, structuralFeature);
	}

	/**
	 * Searches for a {@link Position} starting behind the given {@link XtMethodData}. In case the method expects an
	 * location argument (such as 'at' or 'of'), the search starts behind the given location string.
	 *
	 * Checks that the grammar of the given method conforms to: {@code <METHOD NAME> '<arg1>' <VALUE>}.
	 *
	 * @return {@link EObject} with offset for the given {@link XtMethodData}
	 */
	public Position checkAndGetPosition(XtMethodData data, String methodName, String arg1) {
		String optionalLocationStr = checkAndGetArgAfter(data, methodName, arg1);
		int offset = getOffset(data.offset, optionalLocationStr, true);
		Position position = xtData.getPosition(offset);
		if (position == null) {
			Preconditions.checkState(false, "Position not found for string: " + optionalLocationStr);
			// exception thrown above
		}
		return position;
	}

	/**
	 * Searches for an {@link EObject} starting behind the given {@link XtMethodData}. In case the method expects an
	 * location argument (such as 'at' or 'of'), the search starts behind the given location string.
	 *
	 * Checks that the grammar of the given method conforms to: {@code <METHOD NAME> '<arg1>' <VALUE>}.
	 *
	 * @return {@link EObject} with offset for the given {@link XtMethodData}
	 */
	public IEObjectCoveringRegion checkAndGetObjectCoveringRegion(XtMethodData data, String methodName,
			String arg1) {

		String optionalLocationStr = checkAndGetArgAfter(data, methodName, arg1);
		int offset = getOffset(data.offset, optionalLocationStr, false);
		if (offset < 0) {
			return null;
		}
		EObject eObject = XtResourceUtil.findEObject(resource, offset, 0);
		EStructuralFeature structuralFeature = XtResourceUtil.findStructuralFeature(resource, offset);
		return new EObjectCoveringRegion(resource, eObject, offset, structuralFeature);
	}

	/**
	 * Parses the given data string and skips all given keywords and arguments in between. Returns the argument stated
	 * behind the last given keyword, or null otherwise. Arguments are supposed to be escaped using '.
	 *
	 * @return the argument after the last given keyword
	 */
	public String checkAndGetArgAfter(XtMethodData data, String methodName, String... optKeyword) {
		Preconditions.checkArgument(data.name.equals(methodName));
		return parseLastArgument(data, optKeyword);
	}

	private String parseLastArgument(XtMethodData data, String... optKeyword) {
		if (Strings.isNullOrEmpty(data.args)) {
			return null;
		}

		String args = data.args.trim();
		if (optKeyword == null || optKeyword.length == 0) {
			return parseArgument(args);
		}

		// skip args and keywords
		for (String keyword : optKeyword) {
			while (args.startsWith("'")) {
				String someArg = parseArgument(args);
				args = args.substring(someArg.length() + 2).trim();
			}
			if (keyword != null && args.startsWith(keyword)) {
				args = args.substring(keyword.length()).trim();
			} else {
				return null;
			}
		}

		return parseArgument(args);
	}

	private String parseArgument(String argumentPlusRest) {
		if (Strings.isNullOrEmpty(argumentPlusRest)) {
			return null;
		}
		if (Character.isDigit(argumentPlusRest.charAt(0))) {
			int endIdx = 1;
			while (endIdx < argumentPlusRest.length() && Character.isDigit(argumentPlusRest.charAt(endIdx))) {
				endIdx++;
			}
			return argumentPlusRest.substring(0, endIdx);
		}
		if (argumentPlusRest.startsWith("'")) {
			int idxEnd = 1;
			while (argumentPlusRest.charAt(++idxEnd) != '\'') {
				Preconditions.checkState(idxEnd < argumentPlusRest.length());
			}
			return argumentPlusRest.substring(1, idxEnd);
		}
		return null;
	}

	private int getOffset(int searchFromOffset, String optionalLocationStr, boolean atEnd) {
		int offset;
		if (!Strings.isNullOrEmpty(optionalLocationStr)) {
			int relOffset = 0;
			if (atEnd) {
				relOffset = lengthWithoutCursor(optionalLocationStr) - 1;
			} else if (optionalLocationStr.contains(CURSOR)) {
				relOffset = optionalLocationStr.indexOf(CURSOR);
			}
			relOffset = Math.max(0, relOffset);

			String locationStr = optionalLocationStr.replace(CURSOR, "");
			int absOffset = skipCommentsAndWhitespace(xtData.content, locationStr, searchFromOffset);
			offset = absOffset + relOffset;
		} else {
			offset = skipCommentsAndWhitespace(xtData.content, null, searchFromOffset);
		}
		return offset;
	}

	private int lengthWithoutCursor(String str) {
		int length = str.length();
		if (str.contains(CURSOR)) {
			length -= CURSOR.length();
		}
		return length;
	}

	private int skipCommentsAndWhitespace(String content, String str, int startOffset) {
		int offset = skipWhitespace(content, startOffset);

		while (offset < content.length()) {

			if (content.startsWith("//", offset)) {
				offset = minusToMax(content.indexOf("\n", offset));
				offset = skipWhitespace(content, offset);
			} else if (content.startsWith("/*", offset)) {
				offset = minusToMax(content.indexOf("*/", offset));
				offset = skipWhitespace(content, offset);
			} else if (str != null) {
				int oMatch = content.indexOf(str, offset);
				int oSLComment = minusToMax(content.indexOf("//", offset));
				int oMLComment = minusToMax(content.indexOf("/*", offset));
				if (oMatch < oSLComment && oMatch < oMLComment) {
					return oMatch;
				}
				if (oSLComment < oMLComment) {
					offset = oSLComment;
				} else {
					offset = content.indexOf("*/", oMLComment);
				}

			} else {
				return offset;
			}
		}

		return -1;
	}

	private int skipWhitespace(String content, int startOffset) {
		int offset = startOffset - 1;

		while (++offset < content.length()) {
			boolean startsWithWhiteSpace = false;
			startsWithWhiteSpace |= content.startsWith(" ", offset);
			startsWithWhiteSpace |= content.startsWith("\t", offset);
			startsWithWhiteSpace |= content.startsWith("\r", offset);
			startsWithWhiteSpace |= content.startsWith("\n", offset);
			if (!startsWithWhiteSpace) {
				break;
			}
		}

		return minusToMax(offset);
	}

	private int minusToMax(int offset) {
		return offset < 0 ? Integer.MAX_VALUE : offset;
	}

}
