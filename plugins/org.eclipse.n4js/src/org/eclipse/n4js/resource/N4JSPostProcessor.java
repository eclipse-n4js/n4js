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
package org.eclipse.n4js.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.resource.PostProcessingAwareResource.PostProcessor;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.typesbuilder.N4JSTypesBuilder;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

/**
 * Performs post-processing of N4JS resources. Main responsibilities are proxy resolution, types model creation, and
 * ASTNodeInfo computation. When post-processing has completed, the following is guaranteed:
 * <ol>
 * <li>ensure all proxies are resolved,
 * <li>complete {@link TModule} has been created (including all type information; not a stripped-down TModule as created
 * by the {@link N4JSTypesBuilder} during pre-indexing phase),
 * <li>each AST node has a valid ASTNodeInfo,
 * <li>referenced internal types have been exposed.
 * </ol>
 */
public class N4JSPostProcessor implements PostProcessor {

	@Inject
	private ASTProcessor astProcessor;

	@Override
	public boolean expectsLazyLinkResolution() {
		// we do our lazy link resolution while walking the AST together with scoping, typing, etc.
		// -> suppress the automatic up-front resolution by returning false here
		return false;
	}

	@Override
	public void performPostProcessing(PostProcessingAwareResource resource, CancelIndicator cancelIndicator) {
		final boolean hasBrokenAST = !resource.getErrors().isEmpty();
		try {
			// we assume this will not be called for other PostProcessingAwareResource than N4JSResource
			postProcessN4JSResource((N4JSResource) resource, cancelIndicator);
		} catch (Throwable th) {
			if (hasBrokenAST) {
				// swallow exception, AST is broken due to parse error anyway
			} else {
				// make sure this error is being reported, even if exception will be suppressed by calling code!
				throw UtilN4.reportError(
						// use Error instead of RuntimeException to ensure smoke tests will fail
						new RuntimeException("exception while post-processing resource " + resource.getURI(), th));
			}
		}
	}

	private void postProcessN4JSResource(N4JSResource resource, CancelIndicator cancelIndicator) {
		// step 1: process the AST (resolve all proxies in AST, infer type of all typable AST nodes, etc.)
		astProcessor.processAST(resource, cancelIndicator);
		// step 2: expose internal types visible from outside
		// (i.e. if they are referenced from a type that is visible form the outside)
		exposeReferencedInternalTypes(resource);
		// step 3: resolve remaining proxies in TModule
		// (the TModule was created programmatically, so it usually does not contain proxies; however, in case of
		// explicitly declared types, the types builder copies type references from the AST to the corresponding
		// TModule element *without* resolving proxies, so the TModule might contain lazy-cross-ref proxies; most of
		// these should have been resolved during AST traversal and exposing internal types, but some can be left)
		EcoreUtil.resolveAll(resource.getModule());
	}

	/**
	 * Moves all types contained in 'internalTypes' to 'exposedInternalTypes' that are referenced from any top level
	 * type or a variable.
	 */
	private static void exposeReferencedInternalTypes(N4JSResource res) {
		final TModule module = res.getModule();
		if (module == null) {
			return;
		}

		// reset, i.e. make all exposed types internal again
		module.getInternalTypes().addAll(module.getExposedInternalTypes());

		// move internal types to exposedInternalTypes if referenced from topLevelTypes or variables
		final List<EObject> stuffToScan = new ArrayList<>();
		stuffToScan.addAll(module.getTopLevelTypes());
		stuffToScan.addAll(module.getVariables());
		for (EObject currRoot : stuffToScan) {
			exposeTypesReferencedBy(currRoot);
		}
	}

	private static void exposeTypesReferencedBy(EObject root) {
		final TreeIterator<EObject> i = root.eAllContents();
		while (i.hasNext()) {
			final EObject object = i.next();
			for (EReference currRef : object.eClass().getEAllReferences()) {
				if (!currRef.isContainment() && !currRef.isContainer()) {
					final Object currTarget = object.eGet(currRef);
					if (currTarget instanceof Collection<?>) {
						for (Object currObj : (Collection<?>) currTarget) {
							exposeType(currObj);
						}
					} else {
						exposeType(currTarget);
					}
				}
			}
		}
	}

	/**
	 * If 'object' is a type or a constituent of a type (e.g. field of a class) in 'internalTypes', then move the type
	 * to 'exposedInternalTypes'.
	 */
	private static void exposeType(Object object) {
		if (!(object instanceof EObject) || ((EObject) object).eIsProxy()) {
			return;
		}

		// object might not be a type but reside inside a type, e.g. field of a class
		// --> so search for the root, i.e. the ancestor directly below the TModule
		EObject rootTMP = (EObject) object;
		while (rootTMP != null && !(rootTMP.eContainer() instanceof TModule)) {
			rootTMP = rootTMP.eContainer();
		}
		final EObject root = rootTMP; // must be final for the lambda below

		if (root instanceof Type
				&& root.eContainingFeature() == TypesPackage.eINSTANCE.getTModule_InternalTypes()) {
			final TModule module = (TModule) root.eContainer();
			EcoreUtilN4.doWithDeliver(false, () -> {
				module.getExposedInternalTypes().add((Type) root);
			}, module, root); // note: root already contained in resource, so suppress notifications also in root!

			// everything referenced by the type we just moved to 'exposedInternalTypes' has to be exposed as well
			// (this is required, for example, if 'root' is a structural type, see:
			// org.eclipse.n4js.xpect.ui.tests/testdata_ui/typesystem/structuralTypeRefWithMembersAcrossFiles/Main.n4js.xt)
			exposeTypesReferencedBy(root);
		}
	}
}
