package org.eclipse.n4js.transpiler.es.n4idl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.EcmaScriptTranspiler;
import org.eclipse.n4js.transpiler.es.transform.ModuleWrappingTransformation;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Transpiles N4IDL to ECMAScript.
 *
 * Based on {@link EcmaScriptTranspiler}. Adds/replaces transformations to accommodate for the special features of
 * N4IDL.
 */
public class N4IDLTranspiler extends EcmaScriptTranspiler {
	@Inject
	private Provider<N4IDLModuleWrappingTransformation> moduleWrappingTransformationProvider;

	@Inject
	private Provider<N4IDLVersionedImportsTransformation> versionedImportsTransformationProvider;

	@Inject
	private Provider<N4IDLVersionedTypesTransformation> versionedTypesTransformation;

	@Override
	protected Transformation[] computeTransformationsToBeExecuted(TranspilerState state) {
		List<Transformation> transformations = new ArrayList<>(
				Arrays.asList(super.computeTransformationsToBeExecuted(state)));

		transformations.addAll(0, Arrays.asList(
				// add versioned types transformation as first step
				versionedTypesTransformation.get(),
				// add versioned imports transformation as second step
				versionedImportsTransformationProvider.get()));

		// replace ModuleWrappingTransformation with custom implementation
		transformations.replaceAll(t -> {
			if (t instanceof ModuleWrappingTransformation) {
				return moduleWrappingTransformationProvider.get();
			}

			return t;
		});

		return transformations.toArray(new Transformation[0]);
	}
}
