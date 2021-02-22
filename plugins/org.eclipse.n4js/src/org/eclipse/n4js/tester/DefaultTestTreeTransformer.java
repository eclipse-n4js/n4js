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
package org.eclipse.n4js.tester;

import static com.google.common.collect.HashMultimap.create;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.util.Collections.singletonMap;
import static java.util.Collections.sort;
import static org.apache.log4j.Logger.getLogger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.n4js.N4JSModuleDefaults;
import org.eclipse.n4js.tester.domain.TestTree;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * The default {@link TestTreeTransformer test tree transformer} implementation. <br>
 * Transforms {@link TestTree test tree} instances into objects that complies the Mangelhaft requirements.
 */
public class DefaultTestTreeTransformer implements TestTreeTransformer {

	private static final Logger LOGGER = getLogger(DefaultTestTreeTransformer.class);

	@Inject
	@Named(N4JSModuleDefaults.HTTP_SERVER_PORT_KEY)
	private String httpServerPort;

	@Override
	public Object apply(final TestTree tree) {
		return apply(tree, singletonMap(MTestTree.PORT_KEY, getHttpServerPort()));
	}

	@Override
	public Object apply(final TestTree tree, final Map<String, Object> properties) {
		// Keys: test class origins: project name with the version.
		// Values: a multimap of test class FQNs and the test method names.
		final Map<String, Multimap<String, String>> tests = newHashMap();
		tree.forEach(testCase -> {
			final String origin = testCase.getOrigin();
			Multimap<String, String> fqnsWithTests = tests.get(origin);
			if (null == fqnsWithTests) {
				fqnsWithTests = create();
			}
			fqnsWithTests.put(testCase.getClassName(), testCase.getName());
			tests.put(origin, fqnsWithTests);
		});

		final List<MTestDescriptor> testDescriptors = newArrayList();
		tests.entrySet().forEach(new Consumer<Entry<String, Multimap<String, String>>>() {

			@Override
			public void accept(final Entry<String, Multimap<String, String>> entry) {
				final String origin = entry.getKey();
				entry.getValue().asMap().entrySet().forEach(new Consumer<Entry<String, Collection<String>>>() {

					@Override
					public void accept(final Entry<String, Collection<String>> fqnWithTests) {
						final MTestDescriptor testDescriptor = new MTestDescriptor();
						testDescriptor.origin = origin;
						testDescriptor.fqn = fqnWithTests.getKey();
						if (null != fqnWithTests.getValue() && !fqnWithTests.getValue().isEmpty()) {
							testDescriptor.testMethods = newArrayList(fqnWithTests.getValue());
						}
						testDescriptors.add(testDescriptor);
					}
				});

			}
		});
		sort(testDescriptors);
		testDescriptors.forEach(desc -> {
			if (null != desc.testMethods) {
				sort(desc.testMethods);
			}
		});

		final MTestTree transformedTree = new MTestTree();
		transformedTree.sessionId = tree.getSessionId().getValue();
		transformedTree.testDescriptors = testDescriptors;
		trySetEndpoint(properties, transformedTree);

		return transformedTree;
	}

	private void trySetEndpoint(final Map<String, Object> properties, final MTestTree transformedTree) {
		if (null != properties) {
			final Object portValue = properties.get(MTestTree.PORT_KEY);
			if (null != portValue) {
				transformedTree.endpoint = getEndpoint(tryParsePort(portValue));
			}
		}
	}

	private String getEndpoint(int port) {
		return "http://localhost:" + port;
	}

	private int tryParsePort(final Object portValue) {
		try {
			return parseInt(valueOf(portValue));
		} catch (final NumberFormatException e) {
			LOGGER.error("Error while trying to parse port number: " + portValue, e);
			return MTestTree.ILLEGAL_PORT_NUMBER;
		}
	}

	/** Query httpSeverPort, first injected but may overridden */
	public String getHttpServerPort() {
		return httpServerPort;
	}

	/**
	 * Override the server port used in the "endpoint" flag
	 *
	 * @param httpServerPort
	 *            a valid tcp port.
	 */
	public void setHttpServerPort(String httpServerPort) {
		this.httpServerPort = httpServerPort;
	}

	/**
	 * Transformed representation of the test tree.
	 */
	@JsonAutoDetect
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	protected static final class MTestTree {

		private static final String PORT_KEY = "port";
		private static final int ILLEGAL_PORT_NUMBER = -1;

		@JsonProperty
		private String endpoint;
		@JsonProperty
		private String sessionId;
		@JsonProperty
		private List<MTestDescriptor> testDescriptors;

		/**
		 * Returns with the wrapped {@link MTestDescriptor test descriptor} instances.
		 *
		 * @return the test descriptors.
		 */
		public List<MTestDescriptor> getTestDescriptors() {
			return testDescriptors;
		}

	}

	/**
	 * Representation of a test descriptor that complies Mangelhaft.
	 */
	@JsonAutoDetect
	@JsonInclude(value = JsonInclude.Include.NON_NULL)
	protected static final class MTestDescriptor implements Comparable<MTestDescriptor> {
		@JsonProperty
		private String origin;
		@JsonProperty
		private String fqn;
		@JsonProperty
		private List<String> testMethods;

		@Override
		public int compareTo(final MTestDescriptor o) {
			final int result = origin.compareTo(o.origin);
			if (0 == result) {
				return fqn.compareTo(o.fqn);
			}
			return result;
		}
	}

}
