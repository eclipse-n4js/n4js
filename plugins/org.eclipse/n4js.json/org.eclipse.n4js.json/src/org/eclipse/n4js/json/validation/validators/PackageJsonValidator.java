package org.eclipse.n4js.json.validation.validators;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.json.validation.AbstractResourceDependentJSONValidator;
import org.eclipse.n4js.json.validation.ResourceDependentValidator;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * A JSON validator that only applies to {@code package.json} resources.
 */
public class PackageJsonValidator extends AbstractResourceDependentJSONValidator 
	implements ResourceDependentValidator {
	
	private static final String PACKAGE_JSON_FILENAME = "package.json";

	@Override
	public boolean isApplicable(Resource resource) {
		// this validator only applies, if resource represents a 'package.json' file
		return resource.getURI().lastSegment().equals(PACKAGE_JSON_FILENAME);
	}
	
	/**
	 * NEEDED
	 *
	 * When removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator.
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}
	
//	@Check
//	public void checkPackageJSON(JSONObject object) {
//		// Add package.json-specific validation
//	}
}
