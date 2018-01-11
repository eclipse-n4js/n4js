package org.eclipse.n4js.n4idl.transpiler.utils;

import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.VersionedElement;
import org.eclipse.n4js.n4idl.versioning.VersionUtils;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * Transpiler utilities for handling versioned elements in N4IDL.
 */
public class N4IDLTranspilerUtils {

	/**
	 * Returns the internal versioned name of the given {@link IdentifiableElement}.
	 *
	 * Returns the plain name if the given identifiable isn't versioned.
	 */
	public static String getVersionedInternalName(NamedElement element) {
		if (VersionUtils.isTVersionable(element)) {
			return element.getName() + "$" + ((Versionable) element).getVersion();
		} else if (VersionUtils.isVersioned(element)) {
			return element.getName() + "$" + ((VersionedElement) element).getDeclaredVersion();
		} else {
			return element.getName();
		}
	}

	/**
	 * Returns the internal versioned name of the given {@link IdentifiableElement}.
	 *
	 * Returns the plain name if the given identifiable isn't versioned.
	 */
	public static String getVersionedInternalName(IdentifiableElement element) {
		if (VersionUtils.isTVersionable(element)) {
			return element.getName() + "$" + ((Versionable) element).getVersion();
		} else {
			return element.getName();
		}
	}

	private N4IDLTranspilerUtils() {
		// non-instantiable utility class
	}

}
