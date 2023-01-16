/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.packagejson.projectDescription;

import java.util.Objects;

import org.eclipse.n4js.utils.ImmutableDataClass;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Reference to another project without version requirement.
 */
@SuppressWarnings("javadoc")
public class ProjectExports extends ImmutableDataClass {
	private final String exportsPath;
	private final String exportsPathClean;
	private final String main;
	private final String types;
	private final QualifiedName mainModule;
	private final Boolean moduleProperty;

	public ProjectExports(String exportsPath, String main, String types, QualifiedName mainModule,
			Boolean moduleProperty) {

		this.exportsPath = exportsPath;
		this.exportsPathClean = exportsPath.startsWith("./") ? exportsPath.substring(2) : exportsPath;
		this.main = main;
		this.types = types;
		this.mainModule = mainModule;
		this.moduleProperty = moduleProperty;
	}

	public String getExportsPath() {
		return exportsPath;
	}

	public String getExportsPathClean() {
		return exportsPathClean;
	}

	public String getMain() {
		return main;
	}

	public String getTypes() {
		return types;
	}

	public QualifiedName getMainModule() {
		return mainModule;
	}

	public Boolean getModuleProperty() {
		return moduleProperty;
	}

	@Override
	protected int computeHashCode() {
		return Objects.hash(exportsPath, main, types, moduleProperty);
	}

	@Override
	protected boolean computeEquals(Object obj) {
		ProjectExports other = (ProjectExports) obj;
		return Objects.equals(exportsPath, other.exportsPath)
				&& Objects.equals(main, other.main)
				&& Objects.equals(types, other.types)
				&& Objects.equals(moduleProperty, other.moduleProperty);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " { exportsPath: " + exportsPath
				+ ", main: " + main
				+ ", types: " + types
				+ ", moduleProperty: " + moduleProperty
				+ " }";
	}
}
