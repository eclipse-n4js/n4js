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
package org.eclipse.n4js.json.ui.labeling;

import static com.google.common.base.Optional.absent;
import static java.io.File.separator;

import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.n4js.json.ui.internal.JsonActivator;
import org.eclipse.swt.graphics.Image;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Cache for {@link ImageDescriptor image descriptor}s in the context of the
 * JSON bundle.
 */
public enum JSONImageDescriptorCache {

	/** The shared singleton cache for {@link ImageDescriptor image descriptor}s. */
	INSTANCE;

	private static final String PLUGIN_ID = JsonActivator.getInstance().getBundle().getSymbolicName();
	private static final String ICON_FOLDER = "icons";

	/**
	 * Enumeration of image references. Basically the enumeration holds a reference
	 * to the {@link Image} or {@link ImageDescriptor}. If the image descriptor not
	 * cached by the {@link JSONImageDescriptorCache cache} then it caches it.
	 */
	public static enum ImageRef {

		JSON_OBJECT("json_object.png"), JSON_ARRAY("json_array.png"), JSON_VALUE_PAIR("public_co.png"),
		JSON_VALUE("json_value.png");

		private static final Logger LOGGER = Logger.getLogger(ImageRef.class);

		private final String fileName;

		private ImageRef(final String fileName) {
			this.fileName = fileName;
		}

		/**
		 * Returns with the cached image descriptor for the given reference. May return
		 * with {@link Optional#absent() absent} if the resource does not exist in the
		 * plug-in's {@code icons} folder. Or the plug-in is not running.
		 *
		 * @return the image descriptor for the image reference. Could be absent but
		 *         never {@code null}.
		 */
		public Optional<ImageDescriptor> asImageDescriptor() {
			return JSONImageDescriptorCache.INSTANCE.getImageDescriptor(this);
		}

		/***
		 * Returns with a new image instance created from the cached image descriptor.
		 * May return with {@link Optional#absent() absent}, if the image descriptor
		 * cannot be created due to missing resource, or the image cannot be created
		 * from the image descriptor instance.
		 *
		 * <p>
		 * Do not dispose the returned image instance, as the resource is managed by the
		 * {@link JSONImageDescriptorCache}.
		 *
		 * @return the new image instance wrapped into an {@link Optional}. Can be
		 *         {@link Optional#absent() missing} if the image reference cannot be
		 *         created.
		 */
		public Optional<Image> asImage() {
			try {
				return JSONImageDescriptorCache.IMAGE_CACHE.get(asImageDescriptor());
			} catch (final ExecutionException e) {
				LOGGER.error("Error while trying to get image from image descriptor of: " + this);
				return Optional.absent();
			}
		}

	}

	private synchronized Optional<ImageDescriptor> getImageDescriptor(final ImageRef ref) {
		final JsonActivator activator = JsonActivator.getInstance();
		if (null == activator) {
			return absent();
		}
		final ImageRegistry registry = activator.getImageRegistry();
		if (null == registry) {
			return absent();
		}
		ImageDescriptor descriptor = registry.getDescriptor(ref.fileName);
		if (null == descriptor) {
			final String imageFilePath = ICON_FOLDER + separator + ref.fileName;
			descriptor = ResourceLocator.imageDescriptorFromBundle(PLUGIN_ID, imageFilePath).orElse(null);
			registry.put(ref.fileName, descriptor);
		}
		return Optional.fromNullable(descriptor);
	}

	private static final LoadingCache<Optional<ImageDescriptor>, Optional<Image>> IMAGE_CACHE = CacheBuilder
			.newBuilder().build(new CacheLoader<Optional<ImageDescriptor>, Optional<Image>>() {

				@Override
				public Optional<Image> load(Optional<ImageDescriptor> key) throws Exception {
					if (null == key || !key.isPresent()) {
						return Optional.fromNullable(ImageDescriptor.getMissingImageDescriptor().createImage());
					}
					return Optional.fromNullable(key.get().createImage());
				}

			});

}
