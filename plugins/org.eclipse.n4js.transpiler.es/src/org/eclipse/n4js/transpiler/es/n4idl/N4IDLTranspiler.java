package org.eclipse.n4js.transpiler.es.n4idl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.es.EcmaScriptTranspiler;
import org.eclipse.n4js.transpiler.es.transform.ClassDeclarationTransformation;
import org.eclipse.n4js.transpiler.es.transform.InterfaceDeclarationTransformation;

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
	private Provider<N4IDLVersionedImportsTransformation> versionedImportsTransformationProvider;

	@Inject
	private Provider<N4IDLVersionedTypesTransformation> versionedTypesTransformation;

	@Inject
	private Provider<N4IDLClassDeclarationTransformation> classDeclarationTransformation;

	@Inject
	private Provider<N4IDLInterfaceDeclarationTransformation> interfaceDeclarationTransformation;

	@Inject
	private Provider<N4IDLMigrationTransformation> migrationTransformation;

	@Override
	protected Transformation[] computeTransformationsToBeExecuted(TranspilerState state) {
		List<Transformation> transformations = new ArrayList<>(
				Arrays.asList(super.computeTransformationsToBeExecuted(state)));

		// add additional N4IDL-specific transformations
		transformations.addAll(0, Arrays.asList(
				// add versioned types transformation as first step
				versionedTypesTransformation.get(),
				// add versioned imports transformation as second step
				versionedImportsTransformationProvider.get(),
				// add migration transformation
				migrationTransformation.get()));

		// replace some N4JS transformations with N4IDL-specific transformations
		transformations.replaceAll(t -> {
			if (t instanceof ClassDeclarationTransformation) {
				return classDeclarationTransformation.get();
			}

			if (t instanceof InterfaceDeclarationTransformation) {
				return interfaceDeclarationTransformation.get();
			}

			// otherwise, keep the existing transformation
			return t;
		});

		return transformations.toArray(new Transformation[0]);
	}
}
