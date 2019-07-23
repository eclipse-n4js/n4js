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
package org.eclipse.n4js.ui.navigator.internal;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.n4js.ui.workingsets.WorkingSet;
import org.eclipse.n4js.ui.workingsets.WorkingSetManager;
import org.eclipse.n4js.ui.workingsets.WorkingSetManagerBroker;
import org.eclipse.ui.internal.navigator.resources.workbench.ResourceExtensionSorter;

import com.google.inject.Inject;

/**
 * Viewer sorter extension for the {@code Project Explorer} with N4JS content. Uses the default behavior when dealing
 * with Eclipse {@link IResource resource} instances and also handles {@link ResourceNode resource node} elements.
 */
@SuppressWarnings("restriction")
public class N4JSResourceExtensionSorter extends ResourceExtensionSorter {

	@Inject
	private WorkingSetManagerBroker workingSetManagerBroker;

	@Override
	@SuppressWarnings({ "deprecation" })
	protected int compareClass(Object e1, Object e2) {
		if (e1 instanceof ResourceNode && e2 instanceof ResourceNode) {
			final File f1 = ((ResourceNode) e1).getLocation().toJavaIoFile();
			final File f2 = ((ResourceNode) e2).getLocation().toJavaIoFile();
			if (f1.isFile() == f2.isFile()) {
				return getComparator().compare(f1.getName(), f2.getName());
			} else if (f1.isDirectory() && f2.isFile()) {
				return -1;
			} else if (f1.isFile() && f2.isDirectory()) {
				return 1;
			}
		} else if (e1 instanceof WorkingSet && e2 instanceof WorkingSet) {
			final WorkingSetManager workingSetManager = workingSetManagerBroker.getActiveManager();
			if (null != workingSetManager) {
				final WorkingSet ws1 = (WorkingSet) e1;
				final WorkingSet ws2 = (WorkingSet) e2;
				return workingSetManager.compare(ws1, ws2);
			}
		}
		return super.compareClass(e1, e2);
	}

}
