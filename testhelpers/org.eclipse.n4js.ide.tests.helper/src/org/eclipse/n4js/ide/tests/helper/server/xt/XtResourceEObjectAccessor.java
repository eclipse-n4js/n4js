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
import org.eclipse.lsp4j.Position;
import org.eclipse.n4js.ide.tests.helper.server.AbstractStructuredIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Preconditions;

/**
 *
 */
public class XtResourceEObjectAccessor {
	static final String CURSOR = AbstractStructuredIdeTest.CURSOR_SYMBOL;

	final XtFileData xtData;
	final XtextResource resource;

	public XtResourceEObjectAccessor(XtFileData xtData, XtextResource resource) {
		this.xtData = xtData;
		this.resource = resource;
	}

	public Position checkAndGetPosition(MethodData data, String checkArg1, String optionalDelimiter) {
		int offset = checkAndGetOffset(data, checkArg1, optionalDelimiter);
		Position position = xtData.getPosition(offset);
		return position;
	}

	public IEObjectCoveringRegion getObjectCoveringRegion(int offset) {
		EObject eObject = XtResourceUtil.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	public IEObjectCoveringRegion getObjectCoveringRegion(int searchFromOffset, String optionalLocationStr) {
		int offset = getOffset(searchFromOffset, optionalLocationStr);
		EObject eObject = XtResourceUtil.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	public IEObjectCoveringRegion checkAndGetObjectCoveringRegion(MethodData data, String checkArg1,
			String optionalLocation) {

		int offset = checkAndGetOffset(data, checkArg1, optionalLocation);
		EObject eObject = XtResourceUtil.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	public int checkAndGetOffset(MethodData data, String checkArg1, String optionalLocation) {
		Preconditions.checkArgument(data.name.equals(checkArg1));
		String optionalLocationStr = null;
		if (data.args.length > 1) {
			Preconditions.checkArgument(data.args[0].equals(optionalLocation));
			Preconditions.checkArgument(data.args[1].startsWith("'"));
			Preconditions.checkArgument(data.args[1].endsWith("'"));
			optionalLocationStr = data.args[1].substring(1, data.args[1].length() - 1);
		}
		return getOffset(data.offset, optionalLocationStr);
	}

	public int getOffset(int searchFromOffset, String optionalLocationStr) {
		int offset;
		if (optionalLocationStr != null) {
			int relOffset = optionalLocationStr.contains(CURSOR) ? optionalLocationStr.indexOf(CURSOR) : 0;
			String locationStr = optionalLocationStr.replace(CURSOR, "");
			int absOffset = skipCommentsAndWhitespace(xtData.content, locationStr, searchFromOffset);
			offset = absOffset + relOffset;
		} else {
			offset = skipCommentsAndWhitespace(xtData.content, null, searchFromOffset);
		}
		return offset;
	}

	public int skipCommentsAndWhitespace(String content, String str, int startOffset) {
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
				offset = oSLComment < oMLComment ? oSLComment : oMLComment;

			} else {
				return offset;
			}
		}

		return -1;
	}

	public int skipWhitespace(String content, int startOffset) {
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

	public int minusToMax(int offset) {
		return offset < 0 ? Integer.MAX_VALUE : offset;
	}

}
