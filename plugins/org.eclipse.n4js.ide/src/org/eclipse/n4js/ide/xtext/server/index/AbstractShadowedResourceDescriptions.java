package org.eclipse.n4js.ide.xtext.server.index;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IShadowedResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.collect.FluentIterable;

/**
 * Abstract non-UI dependent implementation of shadowing resource descriptions.
 *
 * They will be found by the {@link ResourceDescriptionsProvider} as CurrentDescriptions.
 */
public abstract class AbstractShadowedResourceDescriptions
		implements IResourceDescriptions, IShadowedResourceDescriptions {

	private final ImmutableResourceDescriptions parent;

	/**
	 * Constructor
	 */
	AbstractShadowedResourceDescriptions(ImmutableResourceDescriptions parent) {
		this.parent = parent;
	}

	abstract IResourceDescriptions getLocalData();

	abstract Set<URI> getRemovedResources();

	ImmutableResourceDescriptions getParentData() {
		return parent;
	}

	@Override
	public boolean isShadowed(EClass type, QualifiedName name, boolean ignoreCase) {
		return FluentIterable.from(parent.getExportedObjects(type, name, ignoreCase)).anyMatch(this::isShadowed);
	}

	/**
	 * Answers true, if the found description is no longer present according to the locally added descriptions.
	 */
	protected boolean isShadowed(IEObjectDescription fromParent) {
		return isShadowed(fromParent.getEObjectURI().trimFragment());
	}

	/**
	 * Answers true if local state for the given resource URI would shadow results from the parent.
	 */
	public boolean isShadowed(URI resourceURI) {
		if (getLocalData().getResourceDescription(resourceURI) != null || getRemovedResources().contains(resourceURI)) {
			return true;
		}
		return false;
	}

	@Override
	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		FluentIterable<IResourceDescription> parentResults = FluentIterable.from(
				parent.getAllResourceDescriptions()).filter(rd -> !isShadowed(rd.getURI()));
		return FluentIterable.from(getLocalData().getAllResourceDescriptions()).append(parentResults);
	}

	@Override
	public IResourceDescription getResourceDescription(URI uri) {
		IResourceDescription result = getLocalData().getResourceDescription(uri);
		if (result == null) {
			return parent.getResourceDescription(uri);
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return getLocalData().isEmpty() && parent.isEmpty();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects() {
		return FluentIterable.from(getAllResourceDescriptions()).transformAndConcat(rd -> rd.getExportedObjects());
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects(EClass type, QualifiedName name, boolean ignoreCase) {
		Iterable<IEObjectDescription> local = getLocalData().getExportedObjects(type, name, ignoreCase);
		Iterable<IEObjectDescription> fromParent = () -> parent.getExportedObjects(type, name, ignoreCase).iterator();
		return joinIterables(local, fromParent);
	}

	Iterable<IEObjectDescription> joinIterables(
			Iterable<IEObjectDescription> local,
			Iterable<IEObjectDescription> fromParent) {
		return FluentIterable.from(local).append(FluentIterable.from(fromParent).filter(ioe -> !isShadowed(ioe)));
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass type) {
		Iterable<IEObjectDescription> local = getLocalData().getExportedObjectsByType(type);
		Iterable<IEObjectDescription> fromParent = () -> parent.getExportedObjectsByType(type).iterator();
		return joinIterables(local, fromParent);
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByObject(EObject object) {
		URI resourceURI = EcoreUtil2.getPlatformResourceOrNormalizedURI(object).trimFragment();
		if (getRemovedResources().contains(resourceURI)) {
			return Collections.emptyList();
		}
		IResourceDescription resourceDescription = getLocalData().getResourceDescription(resourceURI);
		if (resourceDescription != null) {
			return resourceDescription.getExportedObjectsByObject(object);
		}
		return parent.getExportedObjectsByObject(object);
	}

}