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
package org.eclipse.n4js.tooling.organizeImports;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.TModule;

/**
 * Holds information about (script) dependency.
 */
public final class ScriptDependency {

	/**
	 * Local name of dependency. This name is how dependency is referred to inside script. Can be actual name, or alias.
	 */
	public final String localName;

	/**
	 * Actual name of dependency. This name is how dependency is exported.
	 */
	public final String actualName;

	/**
	 * Type of dependency. This is object represented by dependency.
	 */
	public final EObject type;

	/**
	 * Module that exports dependency.
	 */
	public final TModule dependencyModule;

	/**
	 * Creates dependency descriptor
	 *
	 * @param localName
	 *            by which dependency is referred to in importing module
	 * @param actualName
	 *            under which dependency was exported form defining module
	 * @param type
	 *            dependency {@link EObject}
	 * @param module
	 *            from which referred {@link EObject} is imported
	 */
	public ScriptDependency(String localName, String actualName, EObject type, TModule module) {
		this.localName = localName;
		this.actualName = actualName;
		this.type = type;
		this.dependencyModule = module;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualName == null) ? 0 : actualName.hashCode());
		result = prime * result + ((localName == null) ? 0 : localName.hashCode());
		result = prime * result + ((dependencyModule == null) ? 0 : dependencyModule.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ScriptDependency)) {
			return false;
		}
		ScriptDependency other = (ScriptDependency) obj;
		if (actualName == null) {
			if (other.actualName != null) {
				return false;
			}
		} else if (!actualName.equals(other.actualName)) {
			return false;
		}
		if (localName == null) {
			if (other.localName != null) {
				return false;
			}
		} else if (!localName.equals(other.localName)) {
			return false;
		}
		if (dependencyModule == null) {
			if (other.dependencyModule != null) {
				return false;
			}
		} else if (!dependencyModule.equals(other.dependencyModule)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

}
