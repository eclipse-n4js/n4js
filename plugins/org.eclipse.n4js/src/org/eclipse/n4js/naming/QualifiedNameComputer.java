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
package org.eclipse.n4js.naming;

import static org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.ProjectUtils;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeDefs;

/**
 * Calculates qualified names as described in N4JS Spec, Chapter 3.4: Qualified Names.
 */
@Singleton
public class QualifiedNameComputer {
	@Inject
	N4JSQualifiedNameProvider qualifiedNameProvider;
	@Inject
	IQualifiedNameConverter converter;
	@Inject
	SpecifierConverter specifierConverter;
	@Inject
	ProjectUtils projectUtils;

	/**
	 * The qualified name of a declared type is its simple name, prefixed by the fully qualified module name it is
	 * defined in.
	 * <p>
	 * Example: <code>p.C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getQualifiedModuleName(Script script) {
		return converter.toString(qualifiedNameProvider.getFullyQualifiedName(script));
	}

	/**
	 * The qualified name of a declared type is its simple name, prefixed by the fully qualified module name it is
	 * defined in.
	 * <p>
	 * Example: <code>p.C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getQualifiedModuleName(TModule module) {
		return converter.toString(qualifiedNameProvider.getFullyQualifiedName(module));
	}

	/**
	 * The simple name of a declared type is the name which is used in its declaration. If type is anonymous, dummy name
	 * will be generated.
	 * <p>
	 * Example: <code>C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getSimpleTypeName(Type type) {
		String name = type.getName();
		if (name == null || name.isEmpty()) {
			name = "__Anonymous_" + type.hashCode();
		}
		return name;
	}

	/**
	 * The fully qualified name (FQN) of a declared type is its simple name, prefixed by the fully qualified module name
	 * it is defined in.
	 * <p>
	 * Example: <code>p/C/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getFullyQualifiedTypeName(Type type) {
		final EObject rootContainer = getRootContainer(type);
		if (rootContainer instanceof TypeDefs) {
			// 'type' is a built-in type
			return getSimpleTypeName(type);
		}
		QualifiedName moduleFQN = qualifiedNameProvider.getFullyQualifiedName(rootContainer);
		if (N4TSQualifiedNameProvider.isModulePolyfill(moduleFQN)) {
			// IDE-1735 strip the extra ModulePolyfill-marker
			moduleFQN = moduleFQN.skipFirst(1);
		}
		return converter.toString(moduleFQN.append(getSimpleTypeName(type)));
	}

	/**
	 * Like {@link #getFullyQualifiedTypeName(Type)}, but uses "." instead of the correct delimiter.
	 * <p>
	 * <b>THIS IS ONLY INTENDED FOR LEGACY PURPOSES WHEN CREATING THE QUALIFIED NAMES FOR THE META-DATA (e.g. N4Class in
	 * transpiler, test catalog)!</b>
	 * <p>
	 * TODO IDE-2227 remove legacy support for old FQNs
	 */
	public String getFullyQualifiedTypeName_WITH_LEGACY_SUPPORT(Type type) {
		return getFullyQualifiedTypeName(type).replace(N4JSQualifiedNameConverter.DELIMITER, ".");
	}

	/**
	 * In import declarations, modules are references using the qualified module path. This is the file name relative to
	 * the source folder without any extensions. We use the slash ’/’ as a delimiter.
	 * <p>
	 * Example: <code>p/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getModuleSpecifier(Script script) {
		return specifierConverter.toString(qualifiedNameProvider.getFullyQualifiedName(script));
	}

	/**
	 * In import declarations, modules are references using the qualified module path. This is the file name relative to
	 * the source folder without any extensions. We use the slash ’/’ as a delimiter.
	 * <p>
	 * Example: <code>p/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 */
	public String getModuleSpecifier(TModule module) {
		return specifierConverter.toString(qualifiedNameProvider.getFullyQualifiedName(module));
	}

	/**
	 * The versioned module specifier is used only internally. It is derived from the module specifier with the version
	 * (separated with a dash '-’) appended.
	 * <p>
	 * Based on provided file resource URI and extension will generate descriptor in form of
	 * Project-0.0.1/module/path/Module Convenience method. Delegates to {@link ProjectUtils#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided {@link TModule}.
	 * <p>
	 * Example: <code>project-1.0.0/p/C</code> for class C in file/module C in package p of project with version 1.0.0.
	 * </p>
	 *
	 * @module {@link TModule} for which we generate descriptor
	 */
	public String getCompleteModuleSpecifier(TModule module) {
		return ProjectUtils.formatDescriptor(resolveProject(module), module.getModuleSpecifier(), "-", ".", "/", false);
	}

	/**
	 * Based on provided file resource URI and extension will generate descriptor in form of
	 * Project_0_0_1_module_path_Module Convenience method. Delegates to {@link ProjectUtils#formatDescriptor} For
	 * delegation both project and unitPath are calculated from provided {@link TModule}. Characters that would be
	 * invalid in a Javascript identifier will be replaced with the unicode sign e.g. _u002e for the dot character (_ is
	 * used instead of / as Javascript would translate the unicode sign again to the not allowed character).
	 * <p>
	 * Example: <code>_import_project_1_0_0_p_C</code> for class C in file/module C in package p of project with version
	 * 1.0.0.
	 * </p>
	 *
	 * @module {@link TModule} for which we generate descriptor
	 */
	public String getCompleteModuleSpecifierAsIdentifier(TModule module) {
		return projectUtils.getValidJavascriptIdentifierName(projectUtils.formatDescriptorAsIdentifier(
				resolveProject(module), module.getModuleSpecifier(), "_", "_", "_", false));
	}

	private IN4JSProject resolveProject(TModule module) {
		return projectUtils.resolveProject(module.eResource().getURI());
	}

	/**
	 * The versioned type specifier is used only internally. It is derived from the complete module specifier with the
	 * qualified name of the type separated by dashes.
	 * <p>
	 * <p>
	 * Example: <code>project-1.0.0/p/C/C</code> for class C in file/module C in package p of project with version
	 * 1.0.0.
	 * </p>
	 */
	public String getCompleteTypeSpecifier(Type type) {
		TModule module = EcoreUtil2.getContainerOfType(type, TModule.class);
		return getCompleteModuleSpecifier(module) + "/" + getSimpleTypeName(type);
	}
}
