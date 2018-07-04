package org.eclipse.n4js.json.resource

import com.google.inject.Inject
import java.util.Collection
import org.eclipse.n4js.json.^extension.JSONExtensionRegistry
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager
import org.eclipse.n4js.utils.resources.IBuildSuppressingResourceDescriptionManager
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource

/**
 * Resource description manager for JSON files. Delegates to registered JSON extensions.
 */
class JSONResourceDescriptionManager extends DefaultResourceDescriptionManager implements IBuildSuppressingResourceDescriptionManager {

	@Inject
	private JSONExtensionRegistry extensionRegistry;

	/**
	 * By default, JSON files won't be built by the incremental builder, but extensions can decide to build certain JSON files.
	 */
	override boolean isToBeBuilt(URI uri, Resource resource) {
		for (ext : extensionRegistry.resourceDescriptionExtensions) {
			if (ext.isToBeBuilt(uri, resource)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Delegates to registered resource description extensions.
	 */
	override boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate,
		IResourceDescriptions context) {
		for (ext : extensionRegistry.resourceDescriptionExtensions) {
			if (ext.isAffected(deltas, candidate, context)) {
				return true;
			}
		}
		return false;
	}
}
