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
package org.eclipse.n4js.jsdoc2spec;

import org.eclipse.n4js.jsdoc2spec.adoc.RepoRelativePathHolder;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;

/**
 * Keys are used for caching maps.
 */
public class KeyUtils {

	/**
	 * @return a complete spec key comprised of the whole {@link RepoRelativePath} and the element name. Also deals with
	 *         requirement IDs.
	 */
	public static String getSpecKey(RepoRelativePathHolder rrph, SpecInfo specInfo) {
		SpecElementRef ser = specInfo.specElementRef;
		if (ser.requirementID != null) {
			return ser.requirementID;
		}
		if (ser.identifiableElement != null) {
			return getSpecKey(rrph, ser.identifiableElement);
		}
		return null;
	}

	/**
	 * @return a complete spec key comprised of the whole {@link RepoRelativePath} <i>without the element name</i>. Also
	 *         deals with requirement IDs.
	 */
	public static String getSpecModuleKey(RepoRelativePathHolder rrph, SpecInfo specInfo) {
		SpecElementRef ser = specInfo.specElementRef;
		if (ser.requirementID != null) {
			return ser.requirementID;
		}
		if (ser.identifiableElement != null) {
			return getSpecKeyPrefix(rrph, ser.identifiableElement);
		}
		return null;
	}

	/**
	 * @return a complete spec key comprised of the whole {@link RepoRelativePath} of the given type and the element
	 *         name
	 */
	public static String getSpecKey(RepoRelativePathHolder rrph, IdentifiableElement type,
			IdentifiableElement element) {

		return getSpecKeyPrefix(rrph, type) + "." + element.getName();
	}

	/** @return a complete spec key comprised of the whole {@link RepoRelativePath} and the element name */
	public static String getSpecKey(RepoRelativePathHolder rrph, IdentifiableElement element) {
		return getSpecKeyPrefix(rrph, element) + "." + element.getName();
	}

	/** @return a complete spec key comprised of the whole {@link RepoRelativePath} */
	public static String getSpecKeyPrefix(RepoRelativePathHolder rrph, IdentifiableElement element) {
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

	/** @return a unique key for elements in the given {@link RepoRelativePath} and element name. */
	public static String getSpecKeyWithoutProjectFolder(RepoRelativePath rrp, String elementName) {
		String key = "";
		if (rrp != null) { // this happens in an error case only, we created a warning before
			key += rrp.repositoryName;
			if (!rrp.pathOfProjectInRepo.isEmpty()) {
				key += "." + rrp.pathOfProjectInRepo;
			}
			key += "." + rrp.projectName;
			key += ".";
		}
		key += elementName;
		return key;
	}

	/**
	 * @return a unique key for the given element. The source folder is not part of the key. Instead, only the module
	 *         name is used.
	 */
	public static String getSpecKeyWithoutProjectFolder(RepoRelativePathHolder rrph, IdentifiableElement element) {
		if (element instanceof TMember) {
			ContainerType<?> containingType = ((TMember) element).getContainingType();
			String specKeyOfType = getSpecKeyWithoutProjectFolder(rrph, containingType);
			String specKey = specKeyOfType + "." + element.getName();
			return specKey;
		}

		TModule module = element.getContainingModule();
		if (module == null) {
			return "GLOBAL.";
		} else {
			RepoRelativePath rrp = rrph.get(element);
			String elementName = nameFromElement(rrph, element);
			String key = getSpecKeyWithoutProjectFolder(rrp, elementName);
			return key;
		}
	}

	private static String nameFromElement(RepoRelativePathHolder rrph, IdentifiableElement element) {
		if (element instanceof TMember) {
			TMember tMember = (TMember) element;
			String name = nameFromElement(rrph, tMember.getContainingType());
			return name + "#" + element.getName();
		}
		TModule module = element.getContainingModule();
		if (module == null) {

			String name = "##global##." + element.getName();
			return name;
		} else {
			String name = module.getModuleSpecifier() + "." + element.getName();
			return name;
		}
	}
}
