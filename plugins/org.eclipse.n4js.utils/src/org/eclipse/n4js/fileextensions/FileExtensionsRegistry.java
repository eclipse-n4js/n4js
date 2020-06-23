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
package org.eclipse.n4js.fileextensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.xtext.resource.FileExtensionProvider;

import com.google.common.base.Splitter;
import com.google.inject.Singleton;

/**
 * This class is providing alternative to {@link FileExtensionProvider}. Implementations of that interface are language
 * specific, thus instances you use are context specific. When using those you need to be certain if you want to use
 * that specific implementation. When working against interface you also need to be sure you get instance you want
 * (especially when you work with injected instances, you might get different implementation at runtime than you expect
 * at compile time).
 * <p>
 * This class provides a way to get extensions with certain characteristics (defined by the {@link FileExtensionType}
 * across all supported languages (in practice all supported languages that have registered themselves to this
 * registry). Main use case it to allow caller ask about file extensions from a given language, without directly
 * depending on that language. Note that while extension point provides some hints about meaning of the characteristics
 * (defined by the {@link FileExtensionType} it is up to the caller to interpret this information at use site. When
 * meanings get blurred it may be required to introduce more fined grained {@link FileExtensionType file extension
 * types} and re-examine all use sites.
 * <p>
 * Note that is different from {@code FileExtensionInfoRegistry} in Xpect. That registry is used to setup different
 * languages for Xpect tests.
 * <p>
 * This class is thread safe, i.e. its public methods may be invoked from an arbitrary thread.
 */
// TODO IDE-2509 how does this relate to org.eclipse.xpect.registry.FileExtensionInfoRegistry
@Singleton
public class FileExtensionsRegistry {

	private final static Logger LOGGER = Logger.getLogger(FileExtensionsRegistry.class);

	/* The extension point to file extensions */
	private static final String FILE_EXTENSIONS_POINT_ID = "org.eclipse.n4js.utils.fileExtensions";
	private static final String ATT_TRANSPILABLE_FILE_EXTENSIONS = "transpilableFileExtensions";
	private static final String ATT_TEST_FILE_EXTENSIONS = "testFileExtensions";
	private static final String ATT_RUNNABLE_FILE_EXTENSIONS = "runnableFileExtensions";
	private static final String ATT_TYPABLE_FILE_EXTENSIONS = "typableFileExtensions";
	private static final String ATT_RAW_FILE_EXTENSIONS = "rawFileExtensions";

	private static final String ATT_FILE_EXTENSION = "extensions";
	private boolean isInitialized = false;
	private final Collection<String> transpilableFileExtensions = new ArrayList<>();
	private final Collection<String> testFileExtensions = new ArrayList<>();
	private final Collection<String> runnableFileExtensions = new ArrayList<>();
	private final Collection<String> typableFileExtensions = new ArrayList<>();
	private final Collection<String> rawFileExtensions = new ArrayList<>();

	/**
	 * Register a file extension. This method should only be invoked by client code directly in headless mode. When
	 * running in Eclipse, file extensions will be registered via the 'fileExtensions' extension point.
	 *
	 * @param fileExtension
	 *            without the leading dot e.g. {@code txt} (not {@code .txt})
	 */
	public synchronized void register(String fileExtension, FileExtensionType extensionType) {
		switch (extensionType) {
		case TRANSPILABLE_FILE_EXTENSION:
			transpilableFileExtensions.add(fileExtension);
			break;
		case TESTABLE_FILE_EXTENSION:
			testFileExtensions.add(fileExtension);
			break;
		case RUNNABLE_FILE_EXTENSION:
			runnableFileExtensions.add(fileExtension);
			break;
		case TYPABLE_FILE_EXTENSION:
			typableFileExtensions.add(fileExtension);
			break;
		case RAW_FILE_EXTENSION:
			rawFileExtensions.add(fileExtension);
			break;
		default:
			throw new UnsupportedOperationException(
					"This file extension type " + extensionType + " is not supported yet");
		}
	}

	/**
	 * Return registered file extensions.
	 */
	public synchronized Collection<String> getFileExtensions(FileExtensionType extensionType) {
		if (!isInitialized) {
			initialize();
		}
		switch (extensionType) {
		case TRANSPILABLE_FILE_EXTENSION:
			return Collections.unmodifiableCollection(transpilableFileExtensions);
		case TESTABLE_FILE_EXTENSION:
			return Collections.unmodifiableCollection(testFileExtensions);
		case RUNNABLE_FILE_EXTENSION:
			return Collections.unmodifiableCollection(runnableFileExtensions);
		case TYPABLE_FILE_EXTENSION:
			return Collections.unmodifiableCollection(typableFileExtensions);
		case RAW_FILE_EXTENSION:
			return Collections.unmodifiableCollection(rawFileExtensions);
		default:
			throw new UnsupportedOperationException(
					"This file extension type " + extensionType + " is not supported yet");
		}
	}

	/**
	 * Read information from extensions defined in plugin.xml files
	 */
	protected synchronized void initialize() {
		if (isInitialized) {
			throw new IllegalStateException("may invoke method initialize() only once");
		}
		isInitialized = true;

		final IExtensionRegistry registry = RegistryFactory.getRegistry();
		if (registry != null) {
			final IExtension[] extensions = registry.getExtensionPoint(FILE_EXTENSIONS_POINT_ID).getExtensions();
			for (IExtension extension : extensions) {
				final IConfigurationElement[] configElems = extension.getConfigurationElements();
				for (IConfigurationElement elem : configElems) {
					try {
						List<String> fileExtensions = Splitter.on(',').trimResults().omitEmptyStrings()
								.splitToList(elem.getAttribute(ATT_FILE_EXTENSION));

						String elementName = elem.getName();
						if (ATT_TRANSPILABLE_FILE_EXTENSIONS.equals(elementName)) {
							transpilableFileExtensions.addAll(fileExtensions);
						} else if (ATT_TEST_FILE_EXTENSIONS.equals(elementName)) {
							testFileExtensions.addAll(fileExtensions);
						} else if (ATT_RUNNABLE_FILE_EXTENSIONS.equals(elementName)) {
							runnableFileExtensions.addAll(fileExtensions);
						} else if (ATT_TYPABLE_FILE_EXTENSIONS.equals(elementName)) {
							typableFileExtensions.addAll(fileExtensions);
						} else if (ATT_RAW_FILE_EXTENSIONS.equals(elementName)) {
							rawFileExtensions.addAll(fileExtensions);
						} else {
							LOGGER.error(new UnsupportedOperationException(
									"This file extension type " + elementName + " is not supported yet"));
						}
					} catch (Exception ex) {
						LOGGER.error("Error while reading extensions for extension point " + FILE_EXTENSIONS_POINT_ID,
								ex);
					}
				}
			}
		}
	}

	/**
	 * Reset the lists of file extensions to empty
	 */
	public synchronized void reset() {
		isInitialized = false;
		transpilableFileExtensions.clear();
		testFileExtensions.clear();
		runnableFileExtensions.clear();
	}
}
