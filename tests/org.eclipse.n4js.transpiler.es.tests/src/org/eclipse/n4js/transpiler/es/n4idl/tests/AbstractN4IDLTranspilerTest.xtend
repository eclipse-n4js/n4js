/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl.tests

import com.google.inject.Inject
import org.eclipse.emf.common.util.WrappedException
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.n4idl.tests.helper.N4IDLParseHelper
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.transpiler.TranspilerState
import org.eclipse.n4js.transpiler.es.n4idl.N4IDLTranspiler
import org.eclipse.n4js.transpiler.es.tests.AbstractTranspilerTest

/**
 * Abstract base class for N4IDL-transpiler tests.
 */
abstract class AbstractN4IDLTranspilerTest extends AbstractTranspilerTest {
	@Inject private extension N4IDLParseHelper
	@Inject private N4IDLTranspiler transpiler
	
	override protected Script createScript(CharSequence code, ResourceSet resourceSet) {
		try {
			if(resourceSet!==null) {
				return code.parse(toTestProjectURI("Main." + N4IDLGlobals.N4IDL_FILE_EXTENSION), resourceSet);
			} else {
				return code.parseN4IDL;
			}
		} catch(Exception e) { // catching exception here only to get rid of "unhandled exception" warning
			throw new WrappedException("exception while parsing", e);
		}
	}
	
	override protected TranspilerState prepareAndTransform(N4JSResource resourceToTranspile) {
		val state = transpiler.testPrepare(resourceToTranspile, GENERATOR_OPTIONS);
		transpiler.testTransform(state);
		return state;
	}

	/** applies all transformation on given prepared {@code state}, returns the state again.  */
	override protected TranspilerState transform(TranspilerState state) {
		transpiler.testTransform(state);
		return state
	}
}
