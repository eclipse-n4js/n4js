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
package org.eclipse.n4js.jsdoc2spec.adoc;

import org.eclipse.n4js.jsdoc2spec.RepoRelativePath;
import org.eclipse.n4js.jsdoc2spec.SpecElementRef;
import org.eclipse.n4js.jsdoc2spec.SpecInfo;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;

/**
 * Keys are used for caching maps.
 */
class KeyUtils {

	static String getSpecKey(RepoRelativePathHolder rrph, IdentifiableElement element) {
		return getSpecKeyPrefix(rrph, element) + "." + element.getName();
	}

	static String getSpecKey(RepoRelativePathHolder rrph, SpecInfo specInfo) {
		SpecElementRef ser = specInfo.specElementRef;
		if (ser.requirementID != null) {
			return ser.requirementID;
		}
		if (ser.identifiableElement != null) {
			return getSpecKey(rrph, ser.identifiableElement);
		}
		return null;
	}

	static String getSpecModuleKey(RepoRelativePathHolder rrph, SpecInfo specInfo) {
		SpecElementRef ser = specInfo.specElementRef;
		if (ser.requirementID != null) {
			return ser.requirementID;
		}
		if (ser.identifiableElement != null) {
			return getSpecKeyPrefix(rrph, ser.identifiableElement);
		}
		return null;
	}

	static String getSpecModuleKey(RepoRelativePathHolder rrph, IdentifiableElement element) {
		return getSpecKeyPrefix(rrph, element);
	}

	static String getSpecKeyPrefix_old(RepoRelativePathHolder rrph, IdentifiableElement element) {
		if (element instanceof TMember) {
			ContainerType<?> containingType = ((TMember) element).getContainingType();
			return getSpecKeyPrefix(rrph, containingType);
		}
		TModule module = element.getContainingModule();
		if (module == null) {
			return "GLOBAL.";
		} else {
			return module.getModuleSpecifier();
		}
	}

	static String getSpecKeyPrefix(RepoRelativePathHolder rrph, IdentifiableElement element) {
		if (element instanceof TMember) {
			ContainerType<?> containingType = ((TMember) element).getContainingType();
			return getSpecKeyPrefix(rrph, containingType);
		}

		TModule module = element.getContainingModule();
		if (module == null) {
			return "GLOBAL.";
		} else {
			RepoRelativePath rrp = rrph.get(element);
			String key = rrp.getFullPath();
			return key;
		}
	}

}
