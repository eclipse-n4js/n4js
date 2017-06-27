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
package de.itemis.xcore2java;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent2;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.mwe.PathTraverser;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;

/**
 * A reader that allows to parse Xcore files and use the contained EPackages.
 */
// TODO is to be back-ported to Xtext or Xcore
public class XcoreReader extends AbstractWorkflowComponent2 {
	private static final String XCORE_FILE_EXT = "xcore";

	private static final Logger LOGGER = Logger.getLogger(XcoreReader.class);

	private final List<String> pathes = Lists.newArrayList();

	/**
	 * <p>
	 * A path pointing to a folder, jar or zip which contains Xcore resources.
	 * </p>
	 * <p>
	 * Example use:
	 * </p>
	 * <code>
	 * &lt;path value="./foo/bar.jar"/&gt;
	 * </code>
	 */
	public void addPath(String path) {
		this.pathes.add(path);
	}

	/**
	 * @return all configured pathes.
	 */
	public List<String> getPathes() {
		return pathes;
	}

	private String slot;

	/**
	 * @return the name of the slot that is used to store the parsed Xcore packages.
	 */
	public String getSlot() {
		return slot;
	}

	/**
	 * @param slot
	 *            the name that will be used to associate the parsed Xcore package
	 */
	public void setSlot(String slot) {
		this.slot = slot;
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues) {
		ResourceSet resourceSet = getResourceSet();

		// due to some Xcore peculiarity we have to access the IAllContainerState here
		// to trigger some lazy init logic
		IAllContainersState allContainerState = (IAllContainersState) EcoreUtil.getAdapter(resourceSet.eAdapters(),
				IAllContainersState.class);
		allContainerState.isEmpty("");

		Multimap<String, URI> uris = getPathTraverser().resolvePathes(pathes,
				new Predicate<URI>() {
			@Override
			public boolean apply(URI input) {
				return input.fileExtension().equals(XCORE_FILE_EXT);
			}
		});
		List<Resource> resources = new ArrayList<>();
		for (URI uri : uris.values()) {
			LOGGER.info(uri);
			try {
				resources.add(parse(uri, resourceSet));
			} catch (Exception e) {
				LOGGER.error("Problem during loading of resource @ " + uri, e);
			}
		}
		installIndex(resourceSet);
		for (Resource r : resources) {
			EcoreUtil.resolveAll(r);
			for (Diagnostic x : r.getErrors()) {
				issues.addError(x.getMessage(), x);
			}

		}
		ctx.set(slot, resources);
	}

	/**
	 * @param resourceSet
	 *            the resource set to index
	 */
	private void installIndex(ResourceSet resourceSet) {
		// Fill index
		ResourceDescriptionsData index = new ResourceDescriptionsData(Lists.newArrayList());
		List<Resource> resources = Lists.newArrayList(resourceSet.getResources());
		for (Resource resource : resources) {
			index(resource, resource.getURI(), index);
		}
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index);
	}

	private void index(Resource resource, URI uri, ResourceDescriptionsData index) {
		IResourceServiceProvider serviceProvider = IResourceServiceProvider.Registry.INSTANCE
				.getResourceServiceProvider(uri);
		if (serviceProvider != null) {
			IResourceDescription resourceDescription = serviceProvider.getResourceDescriptionManager()
					.getResourceDescription(resource);
			if (resourceDescription != null) {
				index.addDescription(uri, resourceDescription);
			}
		}
	}

	private Resource parse(URI uriToUse, ResourceSet resourceSet) {
		return resourceSet.getResource(uriToUse, true);
	}

	private PathTraverser getPathTraverser() {
		return new PathTraverser();
	}

	private ResourceSet getResourceSet() {
		if (!injectors.isEmpty()) {
			ResourceSet instance = injectors.get(0).getInstance(
					ResourceSet.class);
			return instance;
		}
		return new ResourceSetImpl();
	}

	private final List<Injector> injectors = Lists.newArrayList();

	/**
	 * Registers a language initializer.
	 */
	public void addRegister(ISetup setup) {
		injectors.add(setup.createInjectorAndDoEMFRegistration());
	}

}
