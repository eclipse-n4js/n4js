package org.eclipse.n4js.json.resource;

import java.util.Collection;

import org.eclipse.n4js.json.extension.IJSONResourceDescriptionExtension;
import org.eclipse.n4js.json.extension.JSONExtensionRegistry;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.inject.Inject;

/**
 * Resource description manager for JSON files. Delegates to registered JSON extensions.
 */
public class JSONResourceDescriptionManager extends DefaultResourceDescriptionManager {

	@Inject
	private JSONExtensionRegistry extensionRegistry;

	/**
	 * Delegates to registered resource description extensions.
	 */
	@Override
	public boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context) {
		for (IJSONResourceDescriptionExtension ext : extensionRegistry.getResourceDescriptionExtensions()) {
			if (ext.isAffected(deltas, candidate, context)) {
				return true;
			}
		}
		return false;
	}
}
