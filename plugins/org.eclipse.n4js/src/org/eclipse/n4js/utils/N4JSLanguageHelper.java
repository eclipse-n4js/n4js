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
package org.eclipse.n4js.utils;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshotForPackageJson;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.inject.Inject;

/**
 * This helper class offers access to ECMAScript as well as N4JS keywords. Its implementation directly accesses the
 * grammar which means that any grammar changes will be reflected by this class.
 */
public final class N4JSLanguageHelper {

	@Inject
	private N4JSGrammarAccess grammarAccess;

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * Returns the reserved ECMAScript keywords which are defined in the grammar. The result is cached.
	 * <a href="https://people.mozilla.org/~jorendorff/es6-draft.html#sec-keywords">ECMAScript ver. [6 11.6.2.1]
	 * Keywords</a>.
	 */
	public Collection<String> getECMAKeywords() {
		return reservedKeywordSupplier.get();
	}

	/**
	 * Returns the N4JS keywords which are defined in the grammar.
	 *
	 * The result is cached.
	 */
	public Collection<String> getN4Keywords() {
		return n4KeywordSupplier.get();
	}

	/**
	 * Returns {@code true} iff the given identifier is a reserved one.
	 *
	 * This includes all ECMAScript and some additional N4JS keywords as well as base type names and boolean literals.
	 */
	public boolean isReservedIdentifier(String identifier) {
		return getECMAKeywords().contains(identifier)
				|| N4JSLanguageConstants.ACCESS_MODIFIERS.contains(identifier)
				|| N4JSLanguageConstants.GETTER_SETTER.contains(identifier)
				|| N4JSLanguageConstants.BASE_TYPES.contains(identifier)
				|| N4JSLanguageConstants.BOOLEAN_LITERALS.contains(identifier);
	}

	private final Supplier<Collection<String>> reservedKeywordSupplier = Suppliers.memoize(() -> {
		return getKeywordAlternatives(grammarAccess.getReservedWordRule());
	});

	private final Supplier<Collection<String>> n4KeywordSupplier = Suppliers.memoize(() -> {
		return getKeywordAlternatives(grammarAccess.getN4KeywordRule());
	});

	private Collection<String> getKeywordAlternatives(ParserRule rule) {
		EList<EObject> ruleAlternatives = rule.getAlternatives().eContents();
		return ruleAlternatives.stream()
				.filter(alternative -> alternative instanceof Keyword)
				.map(keyword -> ((Keyword) keyword).getValue())
				.collect(Collectors.toList());
	}

	/** Convenience method for {@link N4JSLanguageUtils#isOpaqueModule(ProjectType, URI)}. */
	public boolean isOpaqueModule(Resource resource) {
		N4JSWorkspaceConfigSnapshot workspaceConfig = workspaceAccess.getWorkspaceConfig(resource);
		return N4JSLanguageUtils.isOpaqueModule(workspaceConfig, resource.getURI());
	}

	/**
	 * Iff the given project is a {@link ProjectType#DEFINITION definition project}, this method returns the
	 * {@link ProjectDescription#getDefinesPackage() defined project} as an {@link N4JSProjectConfigSnapshot}. Otherwise
	 * the given project is returned.
	 *
	 * @param returnNullOnError
	 *            tells how to deal with error cases: <code>true</code> means <code>null</code> will be returned,
	 *            <code>false</code> means the given project will be returned.
	 */
	public N4JSProjectConfigSnapshot replaceDefinitionProjectByDefinedProject(Notifier context,
			N4JSProjectConfigSnapshot project, boolean returnNullOnError) {
		if (project != null && project.getType() == ProjectType.DEFINITION) {
			N4JSPackageName definedPackageName = project.getDefinesPackage();
			if (definedPackageName != null) {
				String definedProjectId = project.getProjectIdForPackageName(definedPackageName.getRawName());
				if (definedProjectId != null) {
					N4JSProjectConfigSnapshot definedProject = workspaceAccess.findProjectByName(context,
							definedProjectId);
					if (definedProject != null) {
						return definedProject;
					}
				}
			}
			if (returnNullOnError) {
				return null;
			}
		}
		return project;
	}

	/**
	 * Tells whether the given module is an EcmaScript 2015 module, i.e. using {@code import} instead of
	 * {@code require()}, etc.
	 * <p>
	 * <b>WARNING</b>: regarding {@code index} the same warning applies as given
	 * {@link #getOutputFileExtension(IResourceDescriptions, TModule) here}.
	 */
	public boolean isES6Module(IResourceDescriptions index, TModule module) {
		// 1) decide based on the file extension of the target module
		Resource resource = module.eResource();
		URI uri = resource != null ? resource.getURI() : null;
		String ext = uri != null ? uri.fileExtension() : null;
		if (!module.isN4jsdModule() && N4JSGlobals.ALL_N4JS_FILE_EXTENSIONS.contains(ext)) {
			return true; // the N4JS transpiler always emits ES6 module code
		}
		String extActual = getOutputFileExtension(index, module);
		if (N4JSGlobals.CJS_FILE_EXTENSION.equals(extActual)) {
			return false;
		} else if (N4JSGlobals.MJS_FILE_EXTENSION.equals(extActual)) {
			return true;
		} else if (extActual.isEmpty()) {
			// empty string means directory import
			// -> we do not want to look into the package.json file and all its special properties used in case of
			// directory imports, so we cannot know whether it is ESM or CJS
			return true; // use 'true' as fall back
		}
		// (failed: file extension of target module does not tell whether it's commonjs or esm)

		// 2) decide based on the nature of the target project
		N4JSProjectConfigSnapshot targetProject = replaceDefinitionProjectByDefinedProject(resource,
				workspaceAccess.findProjectContaining(resource), true);
		if (targetProject == null) {
			return true; // use 'true' as fall back
		}

		if (targetProject.isESM()) {
			return true;
		}

		return false;
	}

	/**
	 * Returns the file extension to be used in a module specifier when importing from the given module.
	 * <p>
	 * Notes:
	 * <ul>
	 * <li>will return an empty string in case of directory imports.
	 * <li>does not support node's built-in modules (e.g. "fs").
	 * </ul>
	 * <p>
	 * <b>WARNING</b>: in case {@code module} represents an N4JSD file from a definition project, the given index must
	 * include resource descriptions of the corresponding defined project; therefore, an index obtained by using the
	 * given {@code module} as context will not suffice, because definition projects might not have a dependency to
	 * their defined project!
	 */
	public String getOutputFileExtension(IResourceDescriptions index, TModule targetModule) {
		if (targetModule.isN4jsdModule()) {
			// in case of .n4jsd files it is more tricky:
			return getActualFileExtensionForN4jsdFile(index, targetModule);
		}
		Resource targetResource = targetModule.eResource();
		URI uri = targetResource != null ? targetResource.getURI() : null;
		String ext = uri != null ? uri.fileExtension() : null;
		if (N4JSGlobals.ALL_JS_FILE_EXTENSIONS.contains(ext)) {
			if (N4JSGlobals.JSX_FILE_EXTENSION.equals(ext)) {
				// we assume .jsx files are transpiled to .js by other tools
				return N4JSGlobals.JS_FILE_EXTENSION;
			}
			return ext;
		}
		// We get here in two cases:
		// - target file has extension .n4js: return .js because the N4JS transpiler always emits .js files.
		// - or we have an error case: return .js as fall back.
		return N4JSGlobals.JS_FILE_EXTENSION;
	}

	/**
	 * In case of .n4jsd files, we have to find out the extension of the plain-JS file being described by the .n4jsd
	 * file *and* provide special handling for directory imports.
	 */
	private String getActualFileExtensionForN4jsdFile(IResourceDescriptions index, TModule targetModule) {
		QualifiedName targetQN = qualifiedNameConverter.toQualifiedName(targetModule.getQualifiedName());
		Iterable<IEObjectDescription> matchingTModules = index.getExportedObjects(TypesPackage.Literals.TMODULE,
				targetQN, false);
		boolean gotJS = false;
		boolean gotCJS = false;
		boolean gotMJS = false;
		for (IEObjectDescription desc : matchingTModules) {
			String ext = desc.getEObjectURI().fileExtension();
			if (N4JSGlobals.JS_FILE_EXTENSION.equals(ext)) {
				gotJS = true;
			} else if (N4JSGlobals.CJS_FILE_EXTENSION.equals(ext)) {
				gotCJS = true;
			} else if (N4JSGlobals.MJS_FILE_EXTENSION.equals(ext)) {
				gotMJS = true;
			}
			if (gotJS && gotCJS && gotMJS) {
				break;
			}
		}
		if (gotMJS) {
			return N4JSGlobals.MJS_FILE_EXTENSION;
		} else if (gotCJS) {
			return N4JSGlobals.CJS_FILE_EXTENSION;
		} else if (gotJS) {
			return N4JSGlobals.JS_FILE_EXTENSION;
		}

		// no plain JS file found, check for "directory import"
		if (isDirectoryWithPackageJson(index, targetModule, targetQN)) {
			return ""; // no file extension for directory imports
		}

		// use .js as fall back
		return N4JSGlobals.JS_FILE_EXTENSION;
	}

	private boolean isDirectoryWithPackageJson(IResourceDescriptions index, TModule targetModule,
			QualifiedName targetQN) {

		// NOTE: the following approach would be a more elegant implementation of this method, but would require a
		// different computation of FQNs for package.json files in source folders (in N4JSQualifiedNameProvider):
// @formatter:off
//		Iterable<IEObjectDescription> matchingPackageJsonDesc = index.getExportedObjects(
//				JSONPackage.Literals.JSON_DOCUMENT,
//				targetQN.append(N4JSQualifiedNameProvider.PACKAGE_JSON_SEGMENT), false);
//		if (matchingPackageJsonDesc.iterator().hasNext()) {
//			return true;
//		}
// @formatter:on

		N4JSProjectConfigSnapshot targetProject = replaceDefinitionProjectByDefinedProject(targetModule,
				workspaceAccess.findProjectContaining(targetModule), true);
		if (targetProject == null) {
			return false;
		}

		int segCount = targetQN.getSegments().size();
		String[] segments = new String[segCount + 1];
		for (int i = 0; i < segCount; i++) {
			segments[i] = targetQN.getSegments().get(i);
		}
		segments[segCount] = N4JSGlobals.PACKAGE_JSON;

		for (N4JSSourceFolderSnapshot srcFolder : targetProject.getSourceFolders()) {
			if (srcFolder instanceof N4JSSourceFolderSnapshotForPackageJson) {
				continue;
			}
			FileURI packageJsonURI = srcFolder.getPathAsFileURI().appendSegments(segments);
			if (index.getResourceDescription(packageJsonURI.toURI()) != null) {
				return true;
			}
		}
		return false;
	}
}
