/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.ts.types.RunTimeDependency;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

/**
 * The user data for exported modules contains a serialized representation of the module's content. This allows to
 * restore the type model without parsing or linking the complete JS file.
 *
 * The {@link UserdataMapper} provides this serialized representation and the logic to recreate the {@link EObject
 * types} from that.
 */
public final class UserdataMapper {

	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = Logger.getLogger(UserdataMapper.class);

	/**
	 * The key in the user data map of the module's description.
	 */
	public final static String USERDATA_KEY_SERIALIZED_SCRIPT = "serializedScript";

	/**
	 * Comma-separated list of resource URIs this resource directly depends on.
	 */
	public final static String USERDATA_KEY_DEPENDENCIES = "dependencies";

	/**
	 * Comma-separated list of resource URIs this resource directly depends on during load time, taking into account
	 * only such load-time dependencies that are caused by extends/implements clauses (see
	 * {@link RunTimeDependency#isLoadTimeForInheritance()}).
	 */
	public final static String USERDATA_KEY_DEPENDENCIES_LOAD_TIME_FOR_INHERITANCE = "dependenciesLoadTimeForInheritance";

	/**
	 * The key in the user data map of static-polyfill contents-hash
	 */
	public final static String USERDATA_KEY_STATIC_POLYFILL_CONTENTHASH = "staticPolyfillContentHash";

	/**
	 * The key in the user data map of time stamp data. Value is a String representations of long (milliseconds).
	 */
	public static final String USERDATA_KEY_TIMESTAMP = "timestamp";

	/**
	 * The key in the user data map for storing the value of transient property {@link TModule#getAstMD5()
	 * TModule#astMD5}.
	 */
	public static final String USERDATA_KEY_AST_MD5 = "astMD5";

	/**
	 * Flag indicating whether the string representation contains binary or human readable data.
	 */
	private final static Boolean BINARY = Boolean.TRUE;

	private final static String TRANSFORMATION_CHARSET_NAME = Charsets.UTF_8.name();

	private static class LocalResourceAwareURIHandler extends URIHandlerImpl {

		private final URI resourceURI;

		LocalResourceAwareURIHandler(URI resourceURI) {
			this.resourceURI = resourceURI;
		}

		@Override
		public URI deresolve(URI uri) {
			if (uri.trimFragment().equals(resourceURI)) {
				return super.deresolve(URI.createURI("#" + uri.fragment()));
			}
			return super.deresolve(uri);
		}

		@Override
		public URI resolve(URI uri) {
			if (uri.trimFragment().toString().isEmpty()) {
				return resourceURI.appendFragment(uri.fragment());
			}
			return super.resolve(uri);
		}
	}

	/**
	 * Serializes an exported script (or other {@link EObject}) stored in given resource content at index 1, and stores
	 * that in a map under key {@link #USERDATA_KEY_SERIALIZED_SCRIPT}.
	 */
	public static Map<String, String> createUserData(final TModule exportedModule) throws IOException,
			UnsupportedEncodingException {
		if (exportedModule.isPreLinkingPhase()) {
			throw new AssertionError("Module may not be from the preLinkingPhase");
		}
		// TODO GH-230 consider disallowing serializing reconciled modules to index with fail-fast
		// if (exportedModule.isReconciled()) {
		// throw new IllegalArgumentException("module must not be reconciled");
		// }
		final Resource originalResourceUncasted = exportedModule.eResource();
		if (!(originalResourceUncasted instanceof N4JSResource)) {
			throw new IllegalArgumentException("module must be contained in an N4JSResource");
		}
		final N4JSResource originalResource = (N4JSResource) originalResourceUncasted;

		// resolve resource (i.e. resolve lazy cross-references, resolve DeferredTypeRefs, etc.)
		originalResource.performPostProcessing();
		if (EcoreUtilN4.hasUnresolvedProxies(exportedModule) || TypeUtils.containsDeferredTypeRefs(exportedModule)) {
			// don't write invalid TModule to index
			// TODO GHOLD-193 reconsider handling of this error case
			// 2016-05-11: keeping fail-safe behavior for now (in place at least since end of 2014).
			// Fail-fast behavior not possible, because common case (e.g. typo in an identifier in the source code, that
			// leads to an unresolvable proxy in the TModule)
			return createTimestampUserData(exportedModule);
		}

		// add copy -- EObjects can only be contained in a single resource, and
		// we do not want to mess up the original resource
		URI resourceURI = originalResource.getURI();
		XMIResource resourceForUserData = new XMIResourceImpl(resourceURI);

		resourceForUserData.getContents().add(TypeUtils.copyWithProxies(exportedModule));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		resourceForUserData.save(baos, getOptions(resourceURI, BINARY));

		String serializedScript = BINARY ? Base64.getEncoder().encodeToString(baos.toByteArray())
				: baos.toString(TRANSFORMATION_CHARSET_NAME);

		final HashMap<String, String> ret = new HashMap<>();
		ret.put(USERDATA_KEY_SERIALIZED_SCRIPT, serializedScript);

		final String astMD5 = exportedModule.getAstMD5();
		if (astMD5 != null) {
			ret.put(USERDATA_KEY_AST_MD5, astMD5);
		}

		ret.put(N4JSResourceDescriptionStrategy.MAIN_MODULE_KEY, Boolean.toString(exportedModule.isMainModule()));

		// in case of filling file store fingerprint to keep filled type updated by the incremental builder.
		// required to trigger rebuilds even if only minor changes happened to the content.
		if (exportedModule.isStaticPolyfillModule()) {
			final String contentHash = Integer.toHexString(originalResource.getParseResult().getRootNode().hashCode());
			ret.put(USERDATA_KEY_STATIC_POLYFILL_CONTENTHASH, contentHash);
		}
		return ret;
	}

	/**
	 * Creates user data with the modification stamp of the resource. This is done to have a nice reaction on changed
	 * models that are otherwise broken, e.g no module will be written to the index. We still want to have change
	 * affection, though.
	 */
	public static Map<String, String> createTimestampUserData(TModule module) {
		Resource resource = module.eResource();
		long timestamp = 0L;
		if (resource instanceof N4JSResource) {
			timestamp = ((N4JSResource) resource).getModificationStamp();
		} else {
			timestamp = System.currentTimeMillis();
		}
		final HashMap<String, String> result = new HashMap<>();
		result.put(USERDATA_KEY_TIMESTAMP, String.valueOf(timestamp));
		return result;
	}

	private static Map<Object, Object> getOptions(URI resourceURI, Boolean binary) {
		return ImmutableMap.<Object, Object> of(XMLResource.OPTION_BINARY, binary, XMLResource.OPTION_URI_HANDLER,
				new LocalResourceAwareURIHandler(resourceURI));
	}

	/**
	 * <b>ONLY INTENDED FOR TESTS OR DEBUGGING. DON'T USE IN PRODUCTION CODE.</b>
	 * <p>
	 * Same as {@link #getDeserializedModuleFromDescription(IEObjectDescription, URI)}, but always returns the module as
	 * an XMI-serialized string. If no module is found, returns null.
	 */
	public static String getDeserializedModuleFromDescriptionAsString(IEObjectDescription eObjectDescription,
			URI uri) throws IOException {
		final TModule module = getDeserializedModuleFromDescription(eObjectDescription, uri);
		if (module == null) {
			return null;
		}
		final XMIResource resourceForUserData = new XMIResourceImpl(uri);
		resourceForUserData.getContents().add(module);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		resourceForUserData.save(baos, getOptions(uri, false));
		return baos.toString(TRANSFORMATION_CHARSET_NAME);
	}

	/**
	 * Reads the TModule stored in the given IEObjectDescription.
	 */
	public static TModule getDeserializedModuleFromDescription(IEObjectDescription eObjectDescription, URI uri) {
		final String serializedData = eObjectDescription.getUserData(USERDATA_KEY_SERIALIZED_SCRIPT);
		if (Strings.isNullOrEmpty(serializedData)) {
			return null;
		}
		final XMIResource xres = new XMIResourceImpl(uri);
		try {
			final boolean binary = !serializedData.startsWith("<");
			final ByteArrayInputStream bais = new ByteArrayInputStream(
					binary ? Base64.getDecoder().decode(serializedData)
							: serializedData.getBytes(TRANSFORMATION_CHARSET_NAME));
			xres.load(bais, getOptions(uri, binary));
		} catch (Exception e) {
			LOGGER.warn("error deserializing module from IEObjectDescription: " + uri); //$NON-NLS-1$
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("error deserializing module from IEObjectDescription=" + eObjectDescription
						+ ": " + uri, e); //$NON-NLS-1$
			}
			// fail safe, because not uncommon (serialized data might have been created with an old version of the N4JS
			// IDE, so the format could be out of date (after an update of the IDE))
			return null;
		}
		final List<EObject> contents = xres.getContents();
		if (contents.isEmpty() || !(contents.get(0) instanceof TModule)) {
			return null;
		}
		final TModule module = (TModule) contents.get(0);
		xres.getContents().clear();

		final String astMD5 = eObjectDescription.getUserData(USERDATA_KEY_AST_MD5);
		module.setAstMD5(astMD5);

		return module;
	}

	/**
	 * Checks whether the given {@link IEObjectDescription} instance contains a serialized types module in its user
	 * data.
	 * <p>
	 * Note that this method does not check whether the serialized types module is an empty string, or whether the
	 * deserialized module is empty.
	 * </p>
	 *
	 * @param eObjectDescription
	 *            the object to check
	 * @return <code>true</code> if the given object description contains a serialized types module and
	 *         <code>false</code> otherwise
	 */
	public static boolean hasSerializedModule(IEObjectDescription eObjectDescription) {
		return eObjectDescription.getUserData(USERDATA_KEY_SERIALIZED_SCRIPT) != null;
	}

	private static Joiner joiner = Joiner.on(",");

	/**
	 * Computes list of dependencies of given resource and stores the list in the given user data. For details, see
	 * {@link #readDependenciesFromDescription(IResourceDescription)}.
	 */
	public static void writeDependenciesToUserData(N4JSResource resource, Map<String, String> userData) {
		final Set<URI> dependencies = computeCrossRefs(resource);
		final Set<URI> dependenciesLoadTimeForInheritance = getDependenciesLoadTimeForInheritance(resource);
		userData.put(USERDATA_KEY_DEPENDENCIES,
				joiner.join(dependencies));
		userData.put(USERDATA_KEY_DEPENDENCIES_LOAD_TIME_FOR_INHERITANCE,
				joiner.join(dependenciesLoadTimeForInheritance));
	}

	private static Set<URI> computeCrossRefs(N4JSResource resource) {
		final Set<URI> result = new LinkedHashSet<>();
		final Script script = resource.getScript();
		if (script != null && !script.eIsProxy()) {
			for (ScriptElement elem : script.getScriptElements()) {
				if (elem instanceof ImportDeclaration) {
					final TModule module = ((ImportDeclaration) elem).getModule();
					if (module != null && !module.eIsProxy()) {
						final Resource targetRes = module.eResource();
						if (targetRes != null) {
							final URI uri = targetRes.getURI();
							if (uri != null) {
								result.add(uri);
							}
						}
					}
				}
			}
		}
		return result;
	}

	private static Set<URI> getDependenciesLoadTimeForInheritance(N4JSResource resource) {
		final Set<URI> result = new LinkedHashSet<>();
		final TModule module = resource.getModule();
		if (module != null && !module.eIsProxy()) {
			for (RunTimeDependency dep : module.getDependenciesRunTime()) {
				if (dep.isLoadTimeForInheritance()) {
					final TModule targetModule = dep.getTarget();
					if (targetModule != null && !targetModule.eIsProxy()) {
						final Resource targetRes = targetModule.eResource();
						if (targetRes != null) {
							final URI uri = targetRes.getURI();
							if (uri != null) {
								result.add(uri);
							}
						}
					}
				}
			}
		}
		return result;
	}

	private static final Splitter splitter = Splitter.on(',').omitEmptyStrings();

	/**
	 * Reads the list of direct dependencies of the resource R represented by the given resource description from its
	 * user data. Returns a list of strings, in which each string represents the URI of a resource D that is a direct
	 * dependency of R (i.e. R depends on D).
	 * <p>
	 * Definition: a resource R <em>directly depends on</em> a resource D, if R imports an identifiable element from D.
	 * In this case, we also say D <em>is a dependency of</em> R.
	 *
	 * Returns none if the information is missing in the resource description.
	 */
	public static Optional<List<String>> readDependenciesFromDescription(IResourceDescription description) {
		return readDependenciesFromDescription(description, USERDATA_KEY_DEPENDENCIES);
	}

	/**
	 * Same as {@link #readDependenciesFromDescription(IResourceDescription)}, but for load-time dependencies, taking
	 * into account only such load-time dependencies that are caused by extends/implements clauses.
	 */
	public static Optional<List<String>> readDependenciesLoadTimeForInheritanceFromDescription(
			IResourceDescription description) {
		return readDependenciesFromDescription(description, USERDATA_KEY_DEPENDENCIES_LOAD_TIME_FOR_INHERITANCE);
	}

	private static Optional<List<String>> readDependenciesFromDescription(IResourceDescription description,
			String userDataKey) {
		final Iterable<IEObjectDescription> modules = description
				.getExportedObjectsByType(TypesPackage.Literals.TMODULE);
		for (IEObjectDescription module : modules) {
			final String dependenciesStr = module.getUserData(userDataKey);
			if (dependenciesStr != null) {
				return Optional.of(splitter.splitToList(dependenciesStr));
			}
		}
		return Optional.empty();
	}

}
