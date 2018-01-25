package org.eclipse.n4js.n4idl.transpiler.utils;

import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.VersionedElement;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.n4idl.versioning.VersionUtils;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TVersionable;

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

	/**
	 * Returns the internal versioned name of the given {@link NamedImportSpecifier}.
	 *
	 * This method is aware of aliases. If the specifier does not import a {@link TVersionable} element, this method
	 * returns the plain name.
	 *
	 * @param specifier
	 *            The {@link NamedImportSpecifier} to compute the versioned internal name for.
	 */
	public static String getVersionedInternalName(NamedImportSpecifier specifier) {
		final TExportableElement importedElement = specifier.getImportedElement();
		final String alias = specifier.getAlias();
		final String importedName = null != alias ? alias : importedElement.getExportedName();

		// for non-versionable elements apply
		if (!VersionUtils.isTVersionable(importedElement)) {
			return importedName;
		}

		final int version = ((TVersionable) importedElement).getVersion();

		return getVersionedInternalName(importedName, version);
	}

	/**
	 * Returns the internal versioned alias of the given versionable.
	 *
	 * @param alias
	 *            The alias to use.
	 * @param aliasedVersionable
	 *            The versionable that is aliased.
	 */
	public static String getVersionedInternalAlias(String alias, TVersionable aliasedVersionable) {
		if (!VersionUtils.isTVersionable(aliasedVersionable)) {
			return alias;
		}
		return getVersionedInternalName(alias, aliasedVersionable.getVersion());
	}

	private static String getVersionedInternalName(String name, int version) {
		if (version < 1) {
			throw new IllegalArgumentException("Version must be greater than zero.");
		}
		return name + N4IDLGlobals.COMPILED_VERSION_SEPARATOR + version;
	}

	private N4IDLTranspilerUtils() {
		// non-instantiable utility class
	}

}
