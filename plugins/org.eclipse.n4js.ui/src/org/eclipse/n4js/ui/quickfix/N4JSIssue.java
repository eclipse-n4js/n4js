/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Parts originally copied from org.eclipse.xtext.ui.util.IssueUtil
 *	in bundle org.eclipse.xtext.ui
 *	available under the terms of the Eclipse Public License 2.0
 *  Copyright (c) 2010 itemis AG (http://www.itemis.eu) and others.
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.quickfix;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.ui.MarkerTypes;
import org.eclipse.xtext.ui.util.IssueUtil;
import org.eclipse.xtext.ui.validation.MarkerTypeProvider;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;

import com.google.inject.Inject;

/**
 * Same as standard Xtext issue, but adds the {@link #getMarker() marker} property.
 */
public class N4JSIssue extends IssueImpl {

	private IMarker marker;

	/**
	 * The {@link IMarker} the receiving issue was created from.
	 */
	public IMarker getMarker() {
		return marker;
	}

	/**
	 * @see #getMarker()
	 */
	public void setMarker(IMarker marker) {
		this.marker = marker;
	}

	/**
	 * getData() wrapped as key-value map
	 *
	 * @param key
	 *            key of element
	 */
	public String getUserData(String key) {
		for (int i = 0; i < this.getData().length; i += 2) {
			if (this.getData()[i].equals(key) && i + 1 < this.getData().length) {
				return this.getData()[i + 1];
			}
		}
		return null;
	}

	/**
	 * Customization of Xtext's {@link IssueUtil}. Only modification is that {@link N4JSIssue}s are created instead of
	 * {@link IssueImpl}s.
	 */
	public static class Util extends IssueUtil {

		@Inject(optional = true)
		private MarkerTypeProvider markerTypeProvider;

		@Override
		public Issue createIssue(IMarker marker) {
			final N4JSIssue issue = new N4JSIssue();
			issue.setMarker(marker);

			// ---- BEGIN: copied from super class ----

			try {
				Map<String, Object> attributes = marker.getAttributes();
				String markerType = marker.getType();
				Object message = attributes.get(IMarker.MESSAGE);
				issue.setMessage(message instanceof String ? (String) message : null);
				Object lineNumber = attributes.get(IMarker.LINE_NUMBER);
				issue.setLineNumber(lineNumber instanceof Integer ? (Integer) lineNumber - 1 : null);
				Object offset = attributes.get(IMarker.CHAR_START);
				Object endOffset = attributes.get(IMarker.CHAR_END);
				if (offset instanceof Integer && endOffset instanceof Integer) {
					issue.setOffset((Integer) offset);
					issue.setLength((Integer) endOffset - (Integer) offset);
				} else {
					issue.setOffset(-1);
					issue.setLength(0);
				}
				Object code = attributes.get(Issue.CODE_KEY);
				issue.setCode(code instanceof String ? (String) code : null);
				Object data = attributes.get(Issue.DATA_KEY);
				issue.setData(data instanceof String ? Strings.unpack((String) data) : null);
				Object uri = attributes.get(Issue.URI_KEY);
				issue.setUriToProblem(uri instanceof String ? URI.createURI((String) uri) : null);
				Object severity = attributes.get(IMarker.SEVERITY);
				Severity translatedSeverity = translateSeverity(severity instanceof Integer ? (Integer) severity : 0);
				if (translatedSeverity == null)
					throw new IllegalArgumentException(marker.toString());
				issue.setSeverity(translatedSeverity);
				if (markerTypeProvider != null)
					issue.setType(markerTypeProvider.getCheckType(markerType));
				else
					issue.setType(MarkerTypes.toCheckType(markerType));
			} catch (CoreException e) {
				return null;
			}
			return issue;

			// ---- END: copied from super class ----
		}
	}
}
