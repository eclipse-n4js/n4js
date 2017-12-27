package org.eclipse.n4js.n4idl.transpiler;

import java.util.Arrays;

import org.eclipse.n4js.n4idl.transpiler.transform.MigrationTransformation;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.EcmaScriptTranspiler;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Transpiles N4IDL to ECMAScript.
 */
public class N4IDLTranspiler extends EcmaScriptTranspiler {
	@Inject
	Provider<MigrationTransformation> migrationTransformationProvider;

	@Override
	protected Iterable<Transformation> computeTransformationsToBeExecuted(TranspilerState state) {
		return Iterables.concat(
				Arrays.asList(
						migrationTransformationProvider.get()),
				super.computeTransformationsToBeExecuted(state));
	}
}
