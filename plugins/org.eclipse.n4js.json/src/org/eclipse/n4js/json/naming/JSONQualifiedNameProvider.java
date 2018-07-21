package org.eclipse.n4js.json.naming;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.extension.IJSONResourceDescriptionExtension;
import org.eclipse.n4js.json.extension.JSONExtensionRegistry;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

/**
 * Provides qualified names for entities in the JSON data model, e.g. {@link JSONDocument}.
 */
public class JSONQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	@Inject
	private JSONExtensionRegistry extensionRegistry;

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		for (IJSONResourceDescriptionExtension ext : extensionRegistry.getResourceDescriptionExtensions()) {
			QualifiedName qn = ext.getFullyQualifiedName(obj);
			if (qn != null) {
				return qn;
			}
		}
		return null;
	}
}
