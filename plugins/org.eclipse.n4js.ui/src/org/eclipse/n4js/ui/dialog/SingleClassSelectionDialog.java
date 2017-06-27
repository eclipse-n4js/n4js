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
package org.eclipse.n4js.ui.dialog;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import org.eclipse.n4js.ui.typesearch.OpenTypeSelectionDialog;
import org.eclipse.n4js.ui.typesearch.TypeSearchKind;

/**
 * Browse dialog to select a single class.
 *
 * Returns values of type {@link IEObjectDescription}.
 */
public class SingleClassSelectionDialog extends OpenTypeSelectionDialog {
	/**
	 * Instantiates a new SingleClassSelectionDialog.
	 */
	public SingleClassSelectionDialog() {
		super(false);
		setTitle("Choose a super class");
	}

	@Override
	public int open() {
		return super.open(TypeSearchKind.CLASS);
	}

	/** Change the filter to match absolute type specifiers as well as simple type names */
	@Override
	protected ItemsFilter createFilter() {
		this.setCurrentFilter(new AbsoluteOrSimpleSpecifierFilter());
		return this.getCurrentFilter();
	}

	@Override
	protected void okPressed() {
		computeResult();
		setReturnCode(OK);
		close();
	}

	/**
	 * This filter matches absolute type specifiers as well as simple type names.
	 */
	private final class AbsoluteOrSimpleSpecifierFilter extends ItemsFilter {

		@Override
		public boolean matchItem(final Object item) {
			if (item instanceof IEObjectDescription) {
				final QualifiedName fqn = ((IEObjectDescription) item).getQualifiedName();
				if (null != fqn) {
					return matches(fqn.getLastSegment()) || matches(fqn.toString());
				}
			}
			return false;
		}

		@Override
		public boolean isConsistentItem(final Object item) {
			return true;
		}
	}

}
