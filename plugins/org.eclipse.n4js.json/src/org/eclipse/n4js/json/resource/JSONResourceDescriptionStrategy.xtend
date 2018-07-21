package org.eclipse.n4js.json.resource

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.^extension.IJSONResourceDescriptionExtension
import org.eclipse.n4js.json.^extension.JSONExtensionRegistry
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.util.IAcceptor

/**
 * JSON resource description strategy based on {@link IJSONResourceDescriptionExtension}.
 * 
 * Does nothing per default, except for the case in which an extension provides a custom resource description.
 */
public class JSONResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {

	@Inject
	private JSONExtensionRegistry extensionRegistry;

	/** Delegates to registered resource description extensions. */
	override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
		if (eObject instanceof JSONDocument) {
			for (ext : extensionRegistry.resourceDescriptionExtensions) {
				ext.createJSONDocumentDescriptions(eObject, acceptor);
			}
		}
		return false;
	}
}
