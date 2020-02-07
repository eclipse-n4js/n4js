package org.eclipse.n4js.xtext.resourceset;

import java.lang.reflect.Field;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 * A resource locator implementation that uses the exact same logic as
 * {@link XtextResourceSet#getResource(URI, boolean)}.
 *
 * This is used to avoid the mutation of the locator field in {@link #basicGetResource(URI, boolean)}
 */
public class XtextResourceLocator extends StandardResourceLocator {

	/*
	 * The getter on XtextResourceSet will return an unmodifiable map instead of the real object. Unfortunately we need
	 * to change the map's content thus we do need to access the actual instance reflectively.
	 */
	private static final Field normalizationMap;

	static {
		try {
			Field fld = XtextResourceSet.class.getDeclaredField("normalizationMap");
			fld.setAccessible(true);
			normalizationMap = fld;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Standard constructor.
	 */
	public XtextResourceLocator(XtextResourceSet resourceSet) {
		super(resourceSet);
	}

	@Override
	public Resource getResource(URI uri, boolean loadOnDemand) {
		Map<URI, Resource> map = resourceSet.getURIResourceMap();
		if (map == null)
			return super.getResource(uri, loadOnDemand);

		@SuppressWarnings("hiding")
		XtextResourceSet resourceSet = (XtextResourceSet) this.resourceSet;
		Resource resource = map.get(uri);
		if (resource == null) {
			URI normalizedURI = resourceSet.getURIConverter().normalize(uri);
			resource = map.get(normalizedURI);
			if (resource != null) {
				normalizedMapping(uri, normalizedURI);
			}
		}
		if (resource != null) {
			if (loadOnDemand && !resource.isLoaded()) {
				demandLoadHelper(resource);
			}
			return resource;
		}

		Resource delegatedResource = delegatedGetResource(uri, loadOnDemand);
		if (delegatedResource != null) {
			return delegatedResource;
		}

		if (loadOnDemand) {
			resource = demandCreateResource(uri);
			if (resource == null) {
				throw new RuntimeException(
						"Cannot create a resource for '" + uri + "'; a registered resource factory is needed");
			}

			demandLoadHelper(resource);

			return resource;
		}

		return null;
	}

	/**
	 * Register a normalization entry.
	 */
	protected void normalizedMapping(URI from, URI to) {
		try {
			@SuppressWarnings("unchecked")
			Map<URI, URI> map = (Map<URI, URI>) normalizationMap.get(resourceSet);
			map.put(from, to);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
