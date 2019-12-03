package org.eclipse.n4js.xtext.resourceset;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;

/**
 * A resource locator implementation that uses the exact same logic as
 * {@link ResourceSetImpl#getResource(URI, boolean)}.
 *
 * This is used to avoid the mutation of the locator field in {@link #basicGetResource(URI, boolean)}
 */
class StandardResourceLocator extends ResourceLocator {

	public StandardResourceLocator(ResourceSetImpl resourceSet) {
		super(resourceSet);
	}

	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		return basicGetResource(uri, loadOnDemand);
	}

	@Override
	protected Resource basicGetResource(URI uri, boolean loadOnDemand) {
		Map<URI, Resource> map = resourceSet.getURIResourceMap();
		if (map != null) {
			Resource resource = map.get(uri);
			if (resource != null) {
				if (loadOnDemand && !resource.isLoaded()) {
					demandLoadHelper(resource);
				}
				return resource;
			}
		}

		URIConverter theURIConverter = resourceSet.getURIConverter();
		URI normalizedURI = theURIConverter.normalize(uri);
		for (Resource resource : resourceSet.getResources()) {
			if (theURIConverter.normalize(resource.getURI()).equals(normalizedURI)) {
				if (loadOnDemand && !resource.isLoaded()) {
					demandLoadHelper(resource);
				}

				if (map != null) {
					map.put(uri, resource);
				}
				return resource;
			}
		}

		Resource delegatedResource = delegatedGetResource(uri, loadOnDemand);
		if (delegatedResource != null) {
			if (map != null) {
				map.put(uri, delegatedResource);
			}
			return delegatedResource;
		}

		if (loadOnDemand) {
			Resource resource = demandCreateResource(uri);
			if (resource == null) {
				throw new RuntimeException(
						"Cannot create a resource for '" + uri + "'; a registered resource factory is needed");
			}

			demandLoadHelper(resource);

			if (map != null) {
				map.put(uri, resource);
			}
			return resource;
		}

		return null;
	}

}
