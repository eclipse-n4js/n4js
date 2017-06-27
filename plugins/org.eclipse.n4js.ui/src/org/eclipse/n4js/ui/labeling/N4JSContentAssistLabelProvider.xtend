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
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider

/**
 * This is the default provider for labels shown in the proposals in the content assists.
 * As labels shown in outline and hyperlinks can look differently (as different use case),
 * this is separated here.
 * <br/><br/>
 * Everytime \@ContentProposalLabelProvider private ILabelProvider labelProvider; is used
 * this label provider is injected.
 */
class N4JSContentAssistLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	// Labels and icons can be computed like this:

	def text(IdentifiableElement identifiableElement) {
		identifiableElement.name
	}
//
//	def image(Greeting ele) {
//		'Greeting.gif'
//	}
}
