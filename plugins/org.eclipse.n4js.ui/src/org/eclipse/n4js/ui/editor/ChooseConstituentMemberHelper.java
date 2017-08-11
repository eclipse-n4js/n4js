/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.editor;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.window.Window;
import org.eclipse.n4js.ui.labeling.N4JSLabelProvider;
import org.eclipse.swt.widgets.Display;

/**
 *
 */
public class ChooseConstituentMemberHelper {

	@Inject
	private N4JSLabelProvider labelProvider;

	/***
	 * Choose a constituent member if needed
	 */
	public EObject chooseConstituentMemberDialogIfRequired(EObject source,
			List<EObject> choices) {
		final AtomicReference<EObject> result = new AtomicReference<>();
		final ChooseConstituentMemberDialog dlg = new ChooseConstituentMemberDialog(null, choices, labelProvider);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if (dlg.open() == Window.OK) {
					result.set((EObject) dlg.getResult()[0]);
				} else {
					// Otherwise, simply return the source
					result.set(source);
				}
			}
		});

		return result.get();
	}
}
