package org.eclipse.n4js.packagejson.xpect.tests;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EValidator.Registry;
import org.eclipse.n4js.N4JSStandaloneSetup;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.validation.JSONValidator;
import org.eclipse.n4js.json.validation.extension.JSONValidatorExtensionRegistry;
import org.eclipse.n4js.validation.validators.packagejson.PackageJsonValidatorExtension;
import org.eclipse.xpect.XpectFile;
import org.eclipse.xpect.XpectJavaModel;
import org.eclipse.xpect.XpectReplace;
import org.eclipse.xpect.setup.XpectSetupFactory;
import org.eclipse.xpect.state.Creates;
import org.eclipse.xpect.xtext.lib.setup.InjectorSetup;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Injector;

/**
 * Custom injector setup for package.json X!PECT tests that makes sure that 
 * both the JSON and the N4JS injectors are initialized and the N4JS package.json
 * validation extension is registered properly. 
 */
@XpectSetupFactory
@XpectReplace(InjectorSetup.class)
public class PackageJsonXpectInjectorSetup extends InjectorSetup {
	public PackageJsonXpectInjectorSetup(XpectJavaModel xjm, XpectFile file) {
		super(xjm, file);
	}

	@Creates
	public Injector createInjector() {
		// make sure N4JS injector is initialized
		final Injector n4jsInjector = N4JSStandaloneSetup.doSetup();
		// obtain JSON injector using the common X!PECT method
		final Injector jsonInjector = super.createInjector();
		
		// obtain N4JS-specific package.json validator
		final PackageJsonValidatorExtension validatorExtension = n4jsInjector.getInstance(PackageJsonValidatorExtension.class);
		// obtain JSON validation extension registry
		final JSONValidatorExtensionRegistry extensionRegistry = jsonInjector.getInstance(JSONValidatorExtensionRegistry.class);
		
		// X!PECTs injector initialization causes an invalid state of the EValidator registry 
		// in which the registered validators use a different injector than the rest of the
		// language infrastructure (see https://github.com/eclipse/Xpect/issues/233). Therefore, 
		// at this point we clear the validator registry and re-initialize it to restore a consistent state.
		final Registry validatorRegistry = EValidator.Registry.INSTANCE;
		validatorRegistry.remove(JSONPackage.eINSTANCE);
		
		// register the correct instance of the generic JSON validator as sole validator for the JSON package
		final JSONValidator jsonValidator = jsonInjector.getInstance(JSONValidator.class);
		jsonValidator.register(jsonInjector.getInstance(EValidatorRegistrar.class));
		
		// finally, manually register the N4JS package.json validation extension
		extensionRegistry.register(validatorExtension);
		
		return jsonInjector;
	}
}
