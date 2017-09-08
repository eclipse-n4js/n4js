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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.organize.imports.ImportProvidedElement;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;

/**
 * Provides labels used in imports disambiguation dialog.
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
		if (element instanceof ImportableObject) {
			return n4Labelprovider.getImage(((ImportableObject) element).getTe());
		}
		return n4Labelprovider.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ImportableObject) {
			ImportableObject io = (ImportableObject) element;
			return getText(io.getTe());
		}
		if (element instanceof ImportProvidedElement) {
			ImportProvidedElement ele = ((ImportProvidedElement) element);
			TModule tm = ((ImportDeclaration) ele.importSpec.eContainer()).getModule();
			return ele.localname + " from " + findLocation(tm);
		}
		if (element instanceof IEObjectDescription) {
			IEObjectDescription ieod = (IEObjectDescription) element;
			EObject eo = ieod.getEObjectOrProxy();

			if (eo instanceof TExportableElement && !eo.eIsProxy()) {
				return getText(eo);
			}
			return ieod.getName().getLastSegment() + " from "
					+ qualifiedNameConverter.toString(ieod.getName().skipLast(1));
		}
		if (element instanceof TExportableElement) {
			TExportableElement te = (TExportableElement) element;
			return te.getName() + " (exported as " + te.getExportedName() + ") from "
					+ findLocation(te.getContainingModule());
		}

		return n4Labelprovider.getText(element);
	}

	private String findLocation(TModule module) {
		return module.getQualifiedName() + " in " + module.getProjectId();
	}
}
