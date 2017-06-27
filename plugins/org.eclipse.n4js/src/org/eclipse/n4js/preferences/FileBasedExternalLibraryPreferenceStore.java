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
package org.eclipse.n4js.preferences;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.inject.Singleton;

/**
 */
@Singleton
public class FileBasedExternalLibraryPreferenceStore extends ExternalLibraryPreferenceStoreImpl {

	@Override
	protected IStatus doSave(ExternalLibraryPreferenceModel modelToSave) {
		return Status.OK_STATUS;
	}

	@Override
	protected ExternalLibraryPreferenceModel doLoad() {
		return ExternalLibraryPreferenceModel.createDefault();
	}

}
