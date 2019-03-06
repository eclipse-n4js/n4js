/*******************************************************************************
 * Copyright (c) 2011, 2017 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ui.refactoring;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.link.LinkedPositionGroup;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.xtext.ui.refactoring.ui.DefaultLinkedPositionGroupCalculator;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Calculates the linked positions for simultaneous editing when a refactoring is triggered in linked mode.
 *
 * @author Jan Koehnlein - Initial contribution and API
 */
@SuppressWarnings("restriction")
public class N4JSLinkedPositionGroupCalculator extends DefaultLinkedPositionGroupCalculator {

	@Inject
	private FileExtensionsRegistry fileExtensionsRegistry;

	@Override
	public Provider<LinkedPositionGroup> getLinkedPositionGroup(
			IRenameElementContext renameElementContext,
			IProgressMonitor monitor) {
		System.out.println("Refactoring's fileExtensionsRegistry = " + fileExtensionsRegistry);
		return super.getLinkedPositionGroup(renameElementContext, monitor);
	}

}
