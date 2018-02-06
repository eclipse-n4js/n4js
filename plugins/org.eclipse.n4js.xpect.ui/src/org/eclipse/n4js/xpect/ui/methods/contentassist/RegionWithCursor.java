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

import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.XpectInvocation;
import org.eclipse.xpect.parameter.DerivedRegion;
import org.eclipse.xpect.parameter.IStatementRelatedRegion;
import org.eclipse.xpect.parameter.StringRegion;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.state.Creates;

/**
 * Region with global Cursor. The cursor is not necessarily inside of the region.
 */
@XpectImport(RegionWithCursor.Provider.class)
public class RegionWithCursor extends DerivedRegion {

	private final int globalCursorOffset;

	/***/
	@XpectSetupFactory
	public static class Provider {

		private final StringRegion delegate;

		/***/
		public Provider(StringRegion delegate) {
			this.delegate = delegate;
		}

		/***/
		@Creates
		public RegionWithCursor createRegionWithCursor() {
			String val = delegate.getRegionText();
			StringBuffer sb = new StringBuffer(val);

			@SuppressWarnings("unused")
			boolean hasCursor = CursorMarkerHelper.exists(val, CursorMarkerHelper.markerCursor);
			boolean hasEndSelection = CursorMarkerHelper.exists(val, CursorMarkerHelper.markerSelectionEnd);
			boolean hasStartSelection = CursorMarkerHelper.exists(val, CursorMarkerHelper.markerSelectionStart);

			int cursorOffset = CursorMarkerHelper.deleteMarker(sb, CursorMarkerHelper.markerCursor);
			int selStartOffset = CursorMarkerHelper.deleteMarker(sb, CursorMarkerHelper.markerSelectionStart);
			int selEndOffset = CursorMarkerHelper.deleteMarker(sb, CursorMarkerHelper.markerSelectionEnd);
			val = sb.toString();

			// reduce cursor offset if selection markers were inserted in front of.
			if (hasStartSelection && selStartOffset < cursorOffset)
				cursorOffset -= CursorMarkerHelper.markerSelectionStart.length();
			if (hasEndSelection && selEndOffset < cursorOffset)
				cursorOffset -= CursorMarkerHelper.markerSelectionEnd.length();

			XpectInvocation invocation = delegate.getStatement();

			IStatementRelatedRegion extendedRegion = invocation.getExtendedRegion();
			int offset = extendedRegion.getOffset() + extendedRegion.getLength();

			String text = invocation.getFile().getDocument();
			int result = text.indexOf(val, offset);
			if (result < 0)
				throw new RuntimeException("OFFSET '" + val + "' not found");

			int globalCursorOffset = result + cursorOffset;
			if (hasEndSelection) {
				if (hasStartSelection) {
					return new RegionWithCursor(delegate, result + selStartOffset, selEndOffset - selStartOffset,
							globalCursorOffset);
				} else {
					// no startselection, so cursor is startselecction.

					if (selEndOffset < cursorOffset)
						throw new RuntimeException("OFFSET '" + delegate + " has no selection start("
								+ CursorMarkerHelper.markerSelectionStart + ").");
					else
						return new RegionWithCursor(delegate, globalCursorOffset, selEndOffset - cursorOffset,
								globalCursorOffset);
				}
			} else {
				// No end Selection
				if (!hasStartSelection) {
					// not start & no end -> empty
					return new RegionWithCursor(delegate, globalCursorOffset);
				} else {
					// start but no end selection -> error
					throw new RuntimeException("OFFSET '" + delegate + " has no end selection marker ("
							+ CursorMarkerHelper.markerSelectionEnd + ").");
				}
			}
		}

	}

	/**
	 * @param offset
	 *            region start
	 * @param lenght
	 *            region length
	 * @param globalCursorOffset
	 *            cursor position
	 */
	public RegionWithCursor(IStatementRelatedRegion origin, int offset, int lenght, int globalCursorOffset) {
		super(origin, offset, lenght);
		this.globalCursorOffset = globalCursorOffset;
	}

	/**
	 * Empty Selection
	 *
	 * @param globalCursorOffset2
	 *            position
	 */
	public RegionWithCursor(IStatementRelatedRegion origin, int globalCursorOffset2) {
		this(origin, globalCursorOffset2, 0, globalCursorOffset2);
	}

	/**
	 *
	 * @return cursor position
	 */
	public int getGlobalCursorOffset() {
		return globalCursorOffset;
	}

	/**
	 *
	 * @return true if selection length gt 0
	 */
	public boolean hasSelection() {
		return getLength() > 0;
	}

	@Override
	public String toString() {
		return "Region[" + (hasSelection() ? "" + getOffset() + " + " + getLength()
				+ "" : "empty")
				+ "]withCursorAt<" + getGlobalCursorOffset()
				+ ">	"
				+ ";";
	}
}
