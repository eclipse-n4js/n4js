package org.eclipse.n4js.n4idl.transpiler;

import java.util.Arrays;

import org.eclipse.n4js.n4idl.transpiler.transform.MigrationTransformation;
import org.eclipse.n4js.n4idl.transpiler.transform.VersionedIdentifiablesNamesTransformation;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.EcmaScriptTranspiler;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class N4IDLTranspiler extends EcmaScriptTranspiler {
	@Inject
	Provider<MigrationTransformation> migrationTransformationProvider;
	@Inject
	Provider<VersionedIdentifiablesNamesTransformation> versionedIdentifiableNamesTransformation;

	@Override
	protected Iterable<Transformation> computeTransformationsToBeExecuted(TranspilerState state) {
		return Iterables.concat(
				Arrays.asList(
						migrationTransformationProvider.get(),
						versionedIdentifiableNamesTransformation.get()),
				super.computeTransformationsToBeExecuted(state));
	}
}
