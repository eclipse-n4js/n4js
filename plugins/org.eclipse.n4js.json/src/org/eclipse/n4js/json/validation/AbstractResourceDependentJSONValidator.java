package org.eclipse.n4js.json.validation;

import java.lang.reflect.Method;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.validation.ComposedChecks;

/**
 * An abstract JSON validator that is aware of {@link ResourceDependentValidator} composed validators.
 * 
 * More specifically, if an {@link AbstractResourceDependentJSONValidator} holds references
 * to sub-validators (via {@link ComposedChecks}), it excludes {@link ResourceDependentValidator}
 * instances in case the {@link ResourceDependentValidator#isApplicable(Resource)} returns {@code false}
 * for the currently validated element.
 */
public class AbstractResourceDependentJSONValidator extends AbstractJSONValidator {
	@Override
	protected MethodWrapper createMethodWrapper(AbstractDeclarativeValidator validatorInstance, Method method) {
		// create custom resource-aware method wrapper
		return new ResourceAwareMethodWrapper(validatorInstance, method);
	}

	/**
	 * A {@link org.eclipse.xtext.validation.AbstractDeclarativeValidator.MethodWrapper} that is 
	 * aware of {@link ResourceDependentValidator}s and checks for the applicability of a validator before executing it.
	 */
	public static class ResourceAwareMethodWrapper extends MethodWrapper {
		
		/** Instantiates a new {@link ResourceAwareMethodWrapper} for the given method and validator instance. */
		protected ResourceAwareMethodWrapper(AbstractDeclarativeValidator validatorInstance, Method method) {
			super(validatorInstance, method);
		}
		
		@Override
		public void invoke(State state) {
			final Resource resource = state.currentObject.eResource();
			AbstractDeclarativeValidator validator = this.getInstance();
			
			// if validator is resource dependent and not applicable to the current resource
			if (validator instanceof ResourceDependentValidator &&
					!((ResourceDependentValidator) validator).isApplicable(resource)) {
				// do not perform validation given by state
				return;
			}
			
			// otherwise perform validation
			super.invoke(state);
		}
	}
}
