package org.eclipse.n4js.json.ui.extension;

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
import org.eclipse.n4js.json.ui.contentassist.IJSONProposalProvider;
import org.eclipse.n4js.json.ui.editor.hyperlinking.IJSONHyperlinkHelperExtension;
import org.eclipse.n4js.json.validation.extension.IJSONValidatorExtension;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionProvider;

import com.google.inject.Singleton;

/**
 * A singleton registry for all registered JSON language extensions.
 */
@Singleton
public class JSONUiExtensionRegistry {
	private static final String JSON_QUICKFIXPROVIDER_EXTENSIONS_POINT_ID = "org.eclipse.n4js.json.ui.quickfixProvider";
	private static final String JSON_PROPOSALPROVIDER_EXTENSIONS_POINT_ID = "org.eclipse.n4js.json.ui.proposalProvider";
	private static final String JSON_HYPERLINKHELPER_EXTENSIONS_POINT_ID = "org.eclipse.n4js.json.ui.hyperlinkHelper";

	private static final String JSON_EXTENSIONS_POINT_CLASS_PROPERTY = "class";

	private static final Logger LOGGER = Logger.getLogger(JSONUiExtensionRegistry.class);

	private boolean isInitialized = false;

	// Cached result of the a query to the IExtensionRegistry.
	// Only access via #getQuickfixProviderExtensions().
	private Set<IssueResolutionProvider> quickfixProviderExtensions;

	// Cached result of the a query to the IExtensionRegistry.
	// Only access via #getProposalProviderExtensions().
	private Set<IJSONProposalProvider> proposalProviderExtensions;

	// Cached result of the a query to the IExtensionRegistry.
	// Only access via #getHyperlinkHelperExtensions().
	private Set<IJSONHyperlinkHelperExtension> hyperlinkHelperExtensions;

	/**
	 * Initializes the registry by querying the {@link IExtensionRegistry} for all
	 * registered JSON validator extensions.
	 */
	private void initialize() {
		if (this.isInitialized) {
			LOGGER.warn("JSONUiExtensionRegistry has already been initialized.");
			return;
		}
		// make sure fields are set to non-null value
		this.quickfixProviderExtensions = new HashSet<>();
		this.proposalProviderExtensions = new HashSet<>();
		this.hyperlinkHelperExtensions = new HashSet<>();

		this.isInitialized = true;

		// query the extension registry for JSON quickfix extensions and register them
		createExecutableExtensions(JSON_QUICKFIXPROVIDER_EXTENSIONS_POINT_ID, IssueResolutionProvider.class)
				.forEach(this::register);
		// query the extension registry for JSON proposal extensions and register them
		createExecutableExtensions(JSON_PROPOSALPROVIDER_EXTENSIONS_POINT_ID, IJSONProposalProvider.class)
				.forEach(this::register);
		// query the extension registry for JSON hyperlink extensions and register them
		createExecutableExtensions(JSON_HYPERLINKHELPER_EXTENSIONS_POINT_ID, IJSONHyperlinkHelperExtension.class)
				.forEach(this::register);
	}

	/**
	 * Creates a list of executable extensions of type {@code extensionClass} from
	 * all registered extension class for the given extension point IDs.
	 * 
	 * Returns an empty list if {@link IExtensionRegistry} is no available (platform
	 * not running).
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

		final IExtension[] extensions = registry.getExtensionPoint(extensionPointId).getExtensions();

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
							+ JSON_QUICKFIXPROVIDER_EXTENSIONS_POINT_ID, ex);
				}
			}
		}

		return executableExtensions;
	}

	/**
	 * Returns a list of all {@link IJSONValidatorExtension}s that were registered
	 * via the JSON quickfix extension point.
	 */
	public Collection<IssueResolutionProvider> getQuickfixProviderExtensions() {
		ensureInitialization(); // trigger lazy initialization, if required
		return this.quickfixProviderExtensions;
	}

	/**
	 * Returns a list of all {@link IJSONValidatorExtension}s that were registered
	 * via the JSON quickfix extension point.
	 */
	public Collection<IJSONProposalProvider> getProposalProviderExtensions() {
		ensureInitialization(); // trigger lazy initialization, if required
		return this.proposalProviderExtensions;
	}

	/**
	 * Returns a list of all {@link IJSONHyperlinkHelperExtension}s that were registered
	 * via the JSON hyperlink extension point.
	 */
	public Collection<IJSONHyperlinkHelperExtension> getHyperlinkHelperExtensions() {
		ensureInitialization(); // trigger lazy initialization, if required
		return this.hyperlinkHelperExtensions;
	}

	/**
	 * Registers the given {@code quickfixProviderExtension} with the
	 * {@link JSONUiExtensionRegistry}.
	 */
	private void register(IssueResolutionProvider quickfixProviderExtension) {
		ensureInitialization(); // trigger lazy initialization, if required
		this.quickfixProviderExtensions.add(quickfixProviderExtension);
	}

	/**
	 * Registers the given {@code proposalProviderExtension} with the
	 * {@link JSONUiExtensionRegistry}.
	 */
	private void register(IJSONProposalProvider proposalProviderExtension) {
		ensureInitialization(); // trigger lazy initialization, if required
		this.proposalProviderExtensions.add(proposalProviderExtension);
	}

	/**
	 * Registers the given {@code hyperlinkHelperExtension} with the
	 * {@link JSONUiExtensionRegistry}.
	 */
	private void register(IJSONHyperlinkHelperExtension hyperlinkHelperExtension) {
		ensureInitialization(); // trigger lazy initialization, if required
		this.hyperlinkHelperExtensions.add(hyperlinkHelperExtension);
	}

	/**
	 * Ensures that the registry is initialized and
	 * {@link #quickfixProviderExtensions} is not {@code null}.
	 */
	private void ensureInitialization() {
		// if un-initialized, populate validator extensions
		if (!this.isInitialized) {
			this.initialize();
		}
	}

}
