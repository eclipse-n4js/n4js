package org.eclipse.n4js.json.validation.extension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;

/**
 * An abstract implementation of {@link IJSONValidatorExtension} that enables the functionality of 
 * an {@link AbstractDeclarativeValidator} (check methods, addIssue methods, etc.).
 * 
 * Subclasses of this can be registered as {@code org.eclipse.n4js.json.validation} extension.
 */
public class AbstractJSONValidatorExtension extends AbstractDeclarativeValidator implements IJSONValidatorExtension {
	@Override
	protected List<EPackage> getEPackages() {
		return Arrays.asList(JSONPackage.eINSTANCE);
	}
	
	
	@Override
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		// by default, JSON validator extensions are responsible for all elements of the JSON model
		return eObject.eClass().getEPackage() == JSONPackage.eINSTANCE; 
	}

	@Override
	public final void validateJSON(JSONDocument document, DiagnosticChain diagnosticChain) {
		// use an empty context for the JSON validation
		final HashMap<Object, Object> context = new HashMap<>();

		// early exit, if this validator is not responsible for 'document'
		if (!this.isResponsible(context, document)) {
			return;
		}
		
		// validate document itself
		this.validate(JSONPackage.Literals.JSON_DOCUMENT, document, diagnosticChain, context);
		
		// validate all contents
		document.eAllContents().forEachRemaining(child -> {
			this.validate(child.eClass(), child, diagnosticChain, context);
		});
	}
}
