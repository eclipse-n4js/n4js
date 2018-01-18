package org.eclipse.n4js.n4idl.transpiler;

import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.EcmaScriptTranspiler;

/**
 * Transpiles N4IDL to ECMAScript.
 *
 * For now this transpiler does not differ from {@link EcmaScriptTranspiler}. Once N4IDL requires additional transpiler
 * transformations, they may be registered in this class.
 */
public class N4IDLTranspiler extends EcmaScriptTranspiler {
	@Override
	protected Iterable<Transformation> computeTransformationsToBeExecuted(TranspilerState state) {
		return super.computeTransformationsToBeExecuted(state);
	}
}
