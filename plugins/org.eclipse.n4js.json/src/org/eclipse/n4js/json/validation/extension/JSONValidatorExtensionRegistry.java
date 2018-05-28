package org.eclipse.n4js.json.validation.extension;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;

import com.google.inject.Singleton;

/**
 * A singleton registry for all registered {@link IJSONValidatorExtension}.
 * 
 * Validator extensions may also be registered programmatically via {@link #register(IJSONValidatorExtension)}.
 */
@Singleton
public class JSONValidatorExtensionRegistry {
	private static final String JSON_VALIDATORS_EXTENSIONS_POINT_ID = "org.eclipse.n4js.json.validation";
	private static final String JSON_VALIDATORS_EXTENSIONS_POINT_CLASS_PROPERTY = "class";

	private static final Logger LOGGER = Logger.getLogger(JSONValidatorExtensionRegistry.class);

	private boolean isInitialized = false;

	// Cached result of the a query to the IExtensionRegistry.
	// Only access via #getValidatorExtensions().
	private Set<IJSONValidatorExtension> validatorExtensions;

	/**
	 * Initializes the registry by querying the {@link IExtensionRegistry} for all registered JSON validator extensions.
	 */
	private void initialize() {
		if (this.isInitialized) {
			LOGGER.warn("JSONValidatorExtensionRegistry has already been initialized.");
			return;
		}
		// make sure validatorExtensions is set to non-null value
		this.validatorExtensions = new HashSet<>();

		this.isInitialized = true;

		// query the extension registry for JSON validator extensions and register them
		final IExtensionRegistry registry = RegistryFactory.getRegistry();
		if (registry != null) {
			final IExtension[] extensions = registry.getExtensionPoint(JSON_VALIDATORS_EXTENSIONS_POINT_ID)
					.getExtensions();

			for (IExtension extension : extensions) {
				final IConfigurationElement[] configElems = extension.getConfigurationElements();
				for (IConfigurationElement elem : configElems) {
					try {
						final IJSONValidatorExtension validatorExtension = (IJSONValidatorExtension) elem
								.createExecutableExtension(JSON_VALIDATORS_EXTENSIONS_POINT_CLASS_PROPERTY);

						register(validatorExtension);
					} catch (Exception ex) {
						LOGGER.error(
								"Error while reading extensions for extension point "
										+ JSON_VALIDATORS_EXTENSIONS_POINT_ID,
								ex);
					}
				}
			}
		}
	}

	/**
	 * Returns a list of all {@link IJSONValidatorExtension}s that were registered via the JSON validation extension
	 * point.
	 */
	public Collection<IJSONValidatorExtension> getValidatorExtensions() {
		ensureInitialization(); // trigger lazy initialization, if required
		return this.validatorExtensions;
	}

	/**
	 * Registers the given {@code validatorExtension} with the {@link JSONValidatorExtensionRegistry}.
	 */
	public void register(IJSONValidatorExtension validatorExtension) {
		ensureInitialization(); // trigger lazy initialization, if required
		this.validatorExtensions.add(validatorExtension);
	}

	/** Ensures that the registry is initialized and {@link #validatorExtensions} is not {@code null}. */
	private void ensureInitialization() {
		// if un-initialized, populate validator extensions
		if (!this.isInitialized) {
			this.initialize();
		}
	}
}
