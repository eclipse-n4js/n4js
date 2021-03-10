package org.eclipse.n4js.json.resource

import com.google.inject.Inject
import java.util.Collection
import org.eclipse.n4js.json.^extension.JSONExtensionRegistry
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescription.Delta
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager

/**
 * Resource description manager for JSON files. Delegates to registered JSON extensions.
 */
class JSONResourceDescriptionManager extends DefaultResourceDescriptionManager {

	@Inject
	private JSONExtensionRegistry extensionRegistry;

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
