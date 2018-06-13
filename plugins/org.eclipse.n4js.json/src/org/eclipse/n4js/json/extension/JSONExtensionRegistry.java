package org.eclipse.n4js.json.extension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.n4js.json.validation.extension.IJSONValidatorExtension;

import com.google.inject.Singleton;

/**
 * A singleton registry for all registered JSON language extensions (e.g. {@link IJSONValidatorExtension}).
 * 
 * Language extensions may also be registered programmatically via {@link #register(IJSONValidatorExtension)}.
 */
@Singleton
public class JSONExtensionRegistry {
	private static final String JSON_VALIDATORS_EXTENSIONS_POINT_ID = "org.eclipse.n4js.json.validation";
	private static final String JSON_RESOURCE_DESCRIPTION_EXTENSIONS_POINT_ID = "org.eclipse.n4js.json.resourceDescription";

	private static final String JSON_EXTENSIONS_POINT_CLASS_PROPERTY = "class";

	private static final Logger LOGGER = Logger.getLogger(JSONExtensionRegistry.class);

	private boolean isInitialized = false;

	// Cached result of the a query to the IExtensionRegistry.
	// Only access via #getValidatorExtensions().
	private Set<IJSONValidatorExtension> validatorExtensions;

	// Cached result of the a query to the IExtensionRegistry.
	// Only access via #getValidatorExtensions().
	private Set<IJSONResourceDescriptionExtension> resourceDescriptionExtensions;

	/**
	 * Initializes the registry by querying the {@link IExtensionRegistry} for all registered JSON validator extensions.
	 */
	private void initialize() {
		if (this.isInitialized) {
			LOGGER.warn("JSONValidatorExtensionRegistry has already been initialized.");
			return;
		}
		// make sure fields are set to non-null value
		this.validatorExtensions = new HashSet<>();
		this.resourceDescriptionExtensions = new HashSet<>();

		this.isInitialized = true;

		// query the extension registry for JSON validator extensions and register them
		createExecutableExtensions(JSON_VALIDATORS_EXTENSIONS_POINT_ID, IJSONValidatorExtension.class)
				.forEach(this::register);

		// query the extension registry for JSON resource description extensions and register them
		createExecutableExtensions(JSON_RESOURCE_DESCRIPTION_EXTENSIONS_POINT_ID,
				IJSONResourceDescriptionExtension.class)
						.forEach(this::register);
	}

	/**
	 * Creates a list of executable extensions of type {@code extensionClass} from all registered extension class for
	 * the given extension point IDs.
	 * 
	 * Returns an empty list if {@link IExtensionRegistry} is no available (platform not running).
	 * 
	 * @param extensionPointId
	 *            The extension point ID.
	 * @param extensionClass
	 *            The expected extension class.
	 */
	private <T> List<T> createExecutableExtensions(String extensionPointId, Class<T> extensionClass) {
		final IExtensionRegistry registry = RegistryFactory.getRegistry();
		if (registry == null) {
			return Collections.emptyList();
		}

		final IExtension[] extensions = registry.getExtensionPoint(extensionPointId)
				.getExtensions();

		final List<T> executableExtensions = new ArrayList<>();

		for (IExtension extension : extensions) {
			final IConfigurationElement[] configElems = extension.getConfigurationElements();
			for (IConfigurationElement elem : configElems) {
				try {
					final Object executableExtension = elem
							.createExecutableExtension(JSON_EXTENSIONS_POINT_CLASS_PROPERTY);
					executableExtensions.add(extensionClass.cast(executableExtension));
				} catch (Exception ex) {
					LOGGER.error("Error while reading extensions for extension point "
							+ JSON_VALIDATORS_EXTENSIONS_POINT_ID, ex);
				}
			}
		}

		return executableExtensions;
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
	 * Returns a list of all {@link IJSONResourceDescriptionExtension}s that were registered via the JSON resource
	 * description extension point.
	 */
	public Collection<IJSONResourceDescriptionExtension> getResourceDescriptionExtensions() {
		ensureInitialization(); // trigger lazy initialization, if required
		return this.resourceDescriptionExtensions;
	}

	/**
	 * Registers the given {@code validatorExtension} with the {@link JSONExtensionRegistry}.
	 */
	public void register(IJSONValidatorExtension validatorExtension) {
		ensureInitialization(); // trigger lazy initialization, if required
		this.validatorExtensions.add(validatorExtension);
	}

	/**
	 * Registers the given {@code resourceDescriptionExtension} with the {@link JSONExtensionRegistry}.
	 */
	public void register(IJSONResourceDescriptionExtension resourceDescriptionExtension) {
		ensureInitialization(); // trigger lazy initialization, if required
		this.resourceDescriptionExtensions.add(resourceDescriptionExtension);
	}

	/** Ensures that the registry is initialized and {@link #validatorExtensions} is not {@code null}. */
	private void ensureInitialization() {
		// if un-initialized, populate validator extensions
		if (!this.isInitialized) {
			this.initialize();
		}
	}
}
