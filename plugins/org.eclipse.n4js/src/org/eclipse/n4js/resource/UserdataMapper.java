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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.utils.EcoreUtilN4;

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
	 * The key in the user data map of static-polyfill contents-hash
	 */
	public final static String USERDATA_KEY_STATIC_POLYFILL_CONTENTHASH = "staticPolyfillContentHash";

	/**
	 * The key in the user data map of time stamp data. Value is a String representations of long (milliseconds).
	 */
	public static final String USERDATA_KEY_TIMESTAMP = "timestamp";

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

		String serializedScript = BINARY ? XMLTypeFactory.eINSTANCE.convertBase64Binary(baos.toByteArray())
				: baos.toString(TRANSFORMATION_CHARSET_NAME);

		final HashMap<String, String> ret = new HashMap<>();
		ret.put(USERDATA_KEY_SERIALIZED_SCRIPT, serializedScript);

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
		return Collections.singletonMap(USERDATA_KEY_TIMESTAMP, String.valueOf(timestamp));
	}

	private static Map<Object, Object> getOptions(URI resourceURI, Boolean binary) {
		return ImmutableMap.<Object, Object> of(XMLResource.OPTION_BINARY, binary, XMLResource.OPTION_URI_HANDLER,
				new LocalResourceAwareURIHandler(resourceURI));
	}

	/**
	 * <b>ONLY INTENDED FOR TESTS OR DEBUGGING. DON'T USE IN PRODUCTION CODE.</b>
	 * <p>
	 * Same as {@link #getDeserializedModuleFromDescription(IEObjectDescription, URI)}, but always returns the module as
	 * an XMI-serialized string.
	 */
	public static String getDeserializedModuleFromDescriptionAsString(IEObjectDescription eObjectDescription,
			URI uri) throws IOException {
		final TModule module = getDeserializedModuleFromDescription(eObjectDescription, uri);
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
					binary ? XMLTypeFactory.eINSTANCE.createBase64Binary(serializedData)
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
}
