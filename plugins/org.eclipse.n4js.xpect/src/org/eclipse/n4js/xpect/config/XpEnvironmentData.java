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
package org.eclipse.n4js.xpect.config;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.setup.XpectSetupRoot;
import org.eclipse.xpect.xtext.lib.setup.InjectorSetup;
import org.eclipse.xtext.resource.XtextResource;

/**
 */
@XpectSetupRoot
@XpectImport({ Config.class, VarDef.class, InjectorSetup.class, StringList.class })
public class XpEnvironmentData {

	List<VarDef> vars = new ArrayList<>();

	Config root;

	XtextResource resourceUnderTest = null;

	/***/
	public void add(Config cRoot) {
		root = cRoot;
	}

	/***/
	public void add(VarDef aVar) {
		vars.add(aVar);
	}

	/** Searches for Variable with name sname recursively, first toplevel, then in config. */
	public VarDef getVar(String sname) {
		VarDef topVar = findFirst(vars, v -> Objects.equals(v.name, sname));
		if (topVar != null) {
			return topVar;
		} else {
			VarDef ret = root.getVar(sname);
			if (ret != null) {
				return ret;
			}
			throw new UnknownError("undefined Variable in setup: '" + sname + "'");
		}
	}

	@Override
	public String toString() {
		return "XpEnvironmentData with config=%s vars=%s.".formatted(root, vars);
	}

	/***/
	public void setResourceUnderTest(XtextResource resource) {
		this.resourceUnderTest = resource;
	}

	/***/
	public XtextResource getResourceUnderTest() {
		return resourceUnderTest;
	}

}
