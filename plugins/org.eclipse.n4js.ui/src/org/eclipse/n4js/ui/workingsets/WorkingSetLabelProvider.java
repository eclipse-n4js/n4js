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
package org.eclipse.n4js.ui.workingsets;

import static org.eclipse.n4js.utils.collections.Arrays2.transform;
import static org.eclipse.jface.viewers.StyledString.COUNTER_STYLER;

import java.util.List;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;

/**
 * Label provider for working sets.
 */
public class WorkingSetLabelProvider extends LabelProvider implements IStyledLabelProvider, ILabelDecorator {

	/**
	 * Shared label provider instance for working sets.
	 */
	public static WorkingSetLabelProvider INSTANCE = new WorkingSetLabelProvider();

	private WorkingSetLabelProvider() {
		// Singleton.
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof WorkingSet) {
			return ImageRef.WORKING_SET.asImage().orNull();
		}
		return super.getImage(element);
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof WorkingSet) {
			return ((WorkingSet) element).getName();
		}
		return super.getText(element);
	}

	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof WorkingSet) {
			final WorkingSet workingSet = (WorkingSet) element;
			final WorkingSetManager manager = workingSet.getWorkingSetManager();

			final String name = workingSet.getName();
			final List<String> allNames = transform(manager.getAllWorkingSets(), ws -> ws.getName());
			if (containsDuplicates(name, allNames)) {
				final String suffix = " [" + workingSet.getId() + "]";
				final StyledString string = new StyledString(name);
				string.append(suffix, COUNTER_STYLER);
				return string;
			}
		}
		return new StyledString(getText(element));
	}

	private boolean containsDuplicates(String toCheck, Iterable<String> elements) {
		int counter = 0;
		for (String element : elements) {
			if (counter > 1) {
				return true;
			}
			if (toCheck.equals(element)) {
				counter++;
			}
		}
		return false;
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		return image;
	}

	@Override
	public String decorateText(String text, Object element) {
		return text;
	}

}
