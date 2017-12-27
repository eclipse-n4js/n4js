package org.eclipse.n4js.n4idl.transpiler.transform;

import org.eclipse.n4js.n4idl.versioning.N4IDLTranspilerUtils;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.operations.SymbolTableManagement;
import org.eclipse.n4js.ts.typeRefs.Versionable;
import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * Renames all symbol table entries for {@link IdentifiableElement} that are also {@link Versionable}.
 *
 */
public class VersionedIdentifiablesNamesTransformation extends Transformation {

	@Override
	public void analyze() {
		// nothing to do
	}

	@Override
	public void transform() {
		collectNodes(getState().im, IdentifiableElement.class, false)
			.forEach(this::renameVersionedElementSTE);
	}

	public void renameVersionedElementSTE(IdentifiableElement element) {
		// skip non-versionable elements
		if (!(element instanceof Versionable)) {
			return;
		}

		final SymbolTableEntry entry = SymbolTableManagement.getSteOriginal(getState(), element);
		final String versionedInternalName = N4IDLTranspilerUtils.getVersionedInternalName(element);

		// if no STE exists yet...
		if (null == entry) {
			throw new IllegalStateException("Encountered IdentifiablElement without symbol table entry during transpilation.");
		} else {
			SymbolTableManagement.rename(getState(), entry, versionedInternalName);
		}
	}

	@Override
	public void assertPreConditions() {
		// nothing to assert
	}

	@Override
	public void assertPostConditions() {
		// nothing to assert
	}

}
