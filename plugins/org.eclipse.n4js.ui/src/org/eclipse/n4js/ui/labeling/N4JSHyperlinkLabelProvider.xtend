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
package org.eclipse.n4js.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.n4js.ui.N4JSHierarchicalNameComputerHelper

/**
 * This is the default provider for labels shown for hyperlink hovers.
 * As labels shown in outline and content assist can look differently (as different use case),
 * this is separated here.
 * <br/><br/>
 * Every time {@code link org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkLabelProvider private ILabelProvider labelProvider} is used
 * this label provider is injected.
 */
class N4JSHyperlinkLabelProvider extends N4JSLabelProvider {

	@Inject N4JSLabelProvider labelProviderDelegate;

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	/**
	 * Customized text for hyperlinks.
	 */
	override String getText(Object element) {
		if (element instanceof EObject) {
			val text = N4JSHierarchicalNameComputerHelper.calculateHierarchicalDisplayName(element,
				labelProviderDelegate, true);
			return text;
		} else {
			return labelProviderDelegate.getText(element);
		}

	}
}
