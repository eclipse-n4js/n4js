package org.eclipse.n4js.json.validation.validators;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.validation.AbstractResourceDependentJSONValidator;
import org.eclipse.n4js.json.validation.IssueCodes;
import org.eclipse.n4js.json.validation.ResourceDependentValidator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * A JSON validator that only applies to {@code package.json} resources.
 */
public class PackageJsonValidator extends AbstractResourceDependentJSONValidator 
	implements ResourceDependentValidator {
	
	@Override
	public boolean isApplicable(Resource resource) {
		// this validator only applies, if resource represents a 'package.json' file
		return resource.getURI().lastSegment().equals("package.json");
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
	
	/**
	 * Add dummy error on any JSON object.
	 */
	@Check
	public void checkObject(JSONObject object) {
		this.addIssue(IssueCodes.getMessageForPACKAGE_JSON_OBJECT_ERROR(), 
				object, IssueCodes.PACKAGE_JSON_OBJECT_ERROR);
	}
}
