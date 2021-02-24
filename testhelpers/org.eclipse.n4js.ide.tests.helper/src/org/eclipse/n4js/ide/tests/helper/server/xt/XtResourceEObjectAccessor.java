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
		EObject eObject = XtResourceUtil.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
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
		int offset = getOffset(searchFromOffset, optionalLocationStr);
		if (offset < 0) {
			return null;
		}
		EObject eObject = XtResourceUtil.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	/**
	 * Searches for a {@link Position} starting behind the given {@link MethodData}. In case the method expects an
	 * location argument (such as 'at' or 'of), the search starts behind the given location string.
	 *
	 * Checks that the grammar of the given method conforms to: {@code <METHOD NAME> '<arg1>' <VALUE>}.
	 *
	 * @return {@link EObject} with offset for the given {@link MethodData}
	 */
	public Position checkAndGetPosition(MethodData data, String methodName, String arg1) {
		int offset = checkAndGetOffset(data, methodName, arg1);
		Position position = xtData.getPosition(offset);
		return position;
	}

	/**
	 * Searches for an {@link EObject} starting behind the given {@link MethodData}. In case the method expects an
	 * location argument (such as 'at' or 'of), the search starts behind the given location string.
	 *
	 * Checks that the grammar of the given method conforms to: {@code <METHOD NAME> '<arg1>' <VALUE>}.
	 *
	 * @return {@link EObject} with offset for the given {@link MethodData}
	 */
	public IEObjectCoveringRegion checkAndGetObjectCoveringRegion(MethodData data, String methodName,
			String arg1) {

		int offset = checkAndGetOffset(data, methodName, arg1);
		EObject eObject = XtResourceUtil.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	private int checkAndGetOffset(MethodData data, String checkArg1, String optionalLocation) {
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

	private int getOffset(int searchFromOffset, String optionalLocationStr) {
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
				offset = oSLComment < oMLComment ? oSLComment : oMLComment;

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
