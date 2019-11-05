package org.eclipse.n4js.xpect.common;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectTestFiles.XpectTestFileCollector;
import org.eclipse.xtext.util.UriExtensions;

import com.google.common.collect.Lists;

/**
 * Customization that ensures that all produced file URIs do have an empty authority.
 */
public class SafeURIFileCollector extends XpectTestFileCollector {

	private final UriExtensions uriExtensions = new UriExtensions();

	/** Constructor */
	public SafeURIFileCollector(Class<?> owner, XpectTestFiles ctx) {
		super(owner, ctx);
	}

	@Override
	protected URI createURI(File file) {
		throw new UnsupportedOperationException("Never called by super impl");
	}

	@Override
	public URI deresolveToProject(URI uri) {
		return uri.deresolve(uriExtensions.withEmptyAuthority(getBundle().getRootURI()));
	}

	@Override
	public Collection<URI> getAllURIs() {
		List<URI> result = Lists.newArrayList(super.getAllURIs());
		for (int i = 0; i < result.size(); i++) {
			try {
				URI uri = uriExtensions.withEmptyAuthority(result.get(i));
				result.set(i, uri);
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"Wrong URI (" + result.get(i) + "). Check given directory and/or files");
			}
		}
		return result;
	}

	@Override
	protected URI resolvePlatformResourceURI(URI uri) {
		URI result = super.resolvePlatformResourceURI(uri);
		return uriExtensions.withEmptyAuthority(result);
	}

	@Override
	protected URI resolveProjectRelativeURI(URI uri) {
		URI result = super.resolveProjectRelativeURI(uri);
		return uriExtensions.withEmptyAuthority(result);
	}

	@Override
	public URI resolveURI(URI base, String newURI) {
		URI uri = super.resolveURI(base, newURI);
		return uriExtensions.withEmptyAuthority(uri);
	}
}
