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
package org.eclipse.n4js.ui.preferences.external;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARY_NAMES;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.n4js.preferences.ExternalLibraryPreferenceModel;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.n4js.ui.navigator.internal.N4JSProjectExplorerHelper;
import org.eclipse.swt.graphics.Image;

/**
 * Simple label provider for external library locations.
 */
class BuiltInLibrariesLabelProvider extends LabelProvider implements IStyledLabelProvider {
	private final N4JSProjectExplorerHelper projectExplorerhelper;

	public BuiltInLibrariesLabelProvider(N4JSProjectExplorerHelper projectExplorerhelper) {
		this.projectExplorerhelper = projectExplorerhelper;
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof URI) {
			return getCategoryText((URI) element).getString();
		} else if (element instanceof IN4JSProject) {
			return ((IN4JSProject) element).getProjectName();
		}
		return super.getText(element);
	}

	private StyledString getCategoryText(final URI location) {
		final String externalLibId = ExternalLibraryPreferencePage.BUILT_IN_LIBS.get(location);
		if (!isNullOrEmpty(externalLibId)) {
			return new StyledString(EXTERNAL_LIBRARY_NAMES.get(externalLibId));
		}

		File file = new File(location);
		if (ExternalLibraryPreferenceModel.isNodeModulesLocation(location)) {
			Path path = file.toPath();
			int pCount = path.getNameCount();
			StyledString styledString = new StyledString(path.getName(pCount - 1).toString());
			styledString.append(" (" + path.getName(pCount - 2) + ")", StyledString.QUALIFIER_STYLER);
			return styledString;
		}
		return new StyledString(file.getAbsolutePath());
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof URI) {
			return ImageRef.LIB_PATH.asImage().orNull();
		} else if (element instanceof IN4JSProject) {
			return ImageRef.EXTERNAL_LIB_PROJECT.asImage().orNull();
		}
		return super.getImage(element);
	}

	@Override
	public StyledString getStyledText(final Object element) {
		if (element instanceof URI) {
			return getCategoryText((URI) element);
		} else if (element instanceof IN4JSProject) {
			return projectExplorerhelper.getStyledTextForExternalProject((IN4JSProject) element, null);
		}
		return new StyledString("unknown");
	}

}
