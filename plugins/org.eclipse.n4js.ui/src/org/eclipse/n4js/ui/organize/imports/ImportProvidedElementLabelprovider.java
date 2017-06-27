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
package org.eclipse.n4js.ui.organize.imports;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.organize.imports.ImportProvidedElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;

/**
 */
public class ImportProvidedElementLabelprovider implements ILabelProvider {

	@Inject
	private N4JSLabelProvider n4Labelprovider;
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Override
	public void addListener(ILabelProviderListener listener) {
		n4Labelprovider.addListener(listener);
	}

	@Override
	public void dispose() {
		n4Labelprovider.dispose();
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return n4Labelprovider.isLabelProperty(element, property);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		n4Labelprovider.removeListener(listener);
	}

	@Override
	public Image getImage(Object element) {
		// return n4Labelprovider.getImage(element);
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ImportableObject) {
			ImportableObject io = (ImportableObject) element;
			return getText(io.getEobj());
		} else if (element instanceof ImportProvidedElement) {
			ImportProvidedElement ele = ((ImportProvidedElement) element);
			// return n4Labelprovider.getText( ele.ambiguityList.get(0) );
			TModule tm = ((ImportDeclaration) ele.importSpec.eContainer()).getModule();
			String moduleText = n4Labelprovider.getText(tm);
			return "" + ele.localname + " from " + moduleText;
		} else if (element instanceof IEObjectDescription) {
			IEObjectDescription ieO = (IEObjectDescription) element;
			return ieO.getName().getLastSegment() + " from "
					+ qualifiedNameConverter.toString(ieO.getName().skipLast(1));
		}

		return n4Labelprovider.getText(element);
	}
}
