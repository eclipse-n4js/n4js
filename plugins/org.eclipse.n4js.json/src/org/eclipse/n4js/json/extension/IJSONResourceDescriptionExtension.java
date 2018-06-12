package org.eclipse.n4js.json.extension;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Manager;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.util.IAcceptor;

/**
 * An extension to provide custom behavior for resource description manager, i.e. {@link Manager}, and
 * {@link IDefaultResourceDescriptionStrategy} for certain JSON files. Also handles creation of qualified names.
 */
public interface IJSONResourceDescriptionExtension {

	/**
	 * Batch operation to check whether a description is affected by any given delta in the given context. See
	 * {@link Manager#isAffected(Collection, IResourceDescription, IResourceDescriptions)} for details.
	 */
	public boolean isAffected(Collection<Delta> deltas, IResourceDescription candidate, IResourceDescriptions context);

	/**
	 * Calculates the {@link IEObjectDescription}s for <code>document</code> and passes them to the acceptor. See
	 * {@link IDefaultResourceDescriptionStrategy#createEObjectDescriptions(EObject, IAcceptor)} for details.
	 */
	public void createJSONDocumentDescriptions(JSONDocument document, IAcceptor<IEObjectDescription> acceptor);

	/**
	 * @return the qualified name for the given object, <code>null</code> if this
	 *         {@link IJSONResourceDescriptionExtension} is not responsible or if the given object doesn't have
	 *         qualified name.
	 * @see IQualifiedNameProvider#getFullyQualifiedName(EObject)
	 */
	public QualifiedName getFullyQualifiedName(EObject obj);
}
