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
package org.eclipse.n4js.xpect.config

import java.util.List
import org.eclipse.xpect.XpectImport
import org.eclipse.xpect.setup.XpectSetupRoot
import org.eclipse.xpect.xtext.lib.setup.InjectorSetup
import org.eclipse.xtext.resource.XtextResource

/**
 */
@XpectSetupRoot
@XpectImport( # [ Config , VarDef , InjectorSetup, StringList ] )
class XpEnvironmentData {

	List<VarDef> vars = newArrayList;

	Config root;

	XtextResource resourceUnderTest = null

	def add( Config cRoot) {
		root = cRoot
	}

	def add( VarDef aVar ) {
		vars.add(aVar)
	}

	/** Searches for Variable with name sname recursivly, first toplevel, then in config. */
	def VarDef getVar(String sname) {
		val topVar = vars.findFirst[name == sname]
		if( topVar !== null ) {return topVar;}
		else {
			val ret = root.getVar(sname)
			if( ret !== null  )	return ret
			throw new UnknownError("undefined Variable in setup: '"+sname+"'")
		}
	}

	override toString() {
		'''XpEnvironmentData with config=«root» vars=«vars».'''
	}


	public def setResourceUnderTest(XtextResource resource) {
		this.resourceUnderTest = resource
	}

	public def getResourceUnderTest() { resourceUnderTest }

}
