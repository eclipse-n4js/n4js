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
package org.eclipse.n4js.transpiler.es.transform

import org.eclipse.n4js.n4JS.ArrowFunction
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.transpiler.Transformation

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 */
class BlockTransformation extends Transformation {

	/** Name for capturing local-arguments-environment in distinct variable on entering a block.*/
	public static final String $CAPTURE_ARGS = "$capturedArgs";


	override assertPreConditions() {
		// true
	}

	override assertPostConditions() {
		// true
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, FunctionOrFieldAccessor, true).toList.forEach[transformArguments];
	}

	/** capture arguments-variable to be accessible in re-written arrow-functions  */
	private def void transformArguments(FunctionOrFieldAccessor funcOrAccess ) {

		val argsVar = funcOrAccess.implicitArgumentsVariable
		if(argsVar === null) {
			return;
		}

		// 1. rename old IdentifierRef_IM pointing to --> arguments with fresh name.
		// 2. introduce new IdentifierRef_IM with (new) name 'arguments'
		// 3. wire up freshname to new arguments in first line of block.
		val newName = $CAPTURE_ARGS;

		val arguments_STE = getSymbolTableEntryOriginal(argsVar, false);
		if(arguments_STE===null || arguments_STE.referencingElements.empty) {
			// no references to this local arguments variable -> no capturing required
			return;
		}

		// arguments_STE.referencingElements.empty // + also need to check if we contain a arrow-function accessing arguments.

		// 1.) RENAME
		rename( arguments_STE, newName );

		// skip ArrowFunctions: (this is the main reason for the capturing here :-)
		if( funcOrAccess instanceof ArrowFunction ) return;
		// skip empty bodies:
		if( funcOrAccess.body.statements.empty ) return;

		// 2 + 3.) CAPTURE arguments:

		val bodyFirstStatement = funcOrAccess.body.statements.get(0);
		// note, there must be something in the body otherwise the 'arguments' variable could not have been accessed!

		// new SymbolTableEntry for 'real' arguments ( the other was renamed above )
		val argumentsSTE = steFor_arguments() ;

		insertBefore( bodyFirstStatement,
			_VariableStatement( _VariableDeclaration(
				newName, _IdentRef( argumentsSTE )
			))
		);
	}
}
